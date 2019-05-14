/*
* CO2Handler.c
*
* Created: 5/2/2019 9:13:03 AM
*  Author: skorejen
*/

#include "co2_handler.h"
#include <mh_z19.h>
#include "ios_io.h"

void vTaskGetCO2(void* pvParameters);
void my_co2_call_back(uint16_t ppm);

uint16_t _ppm;
mh_z19_return_code_t rc;
QueueHandle_t xSendingQueue;
bool _writeFlag;
struct reading measurmentCO2;

void initialize_co2(char CO2_TASK_PRIORITY, void* ptrQueue, void* writeFlag) {
	xSendingQueue = *(QueueHandle_t*)ptrQueue;
	 // initialize the sending queue variable with the same queue from the main method
	_writeFlag = *(bool*)writeFlag;
	mh_z19_create(ser_USART3, my_co2_call_back);
	xTaskCreate(vTaskGetCO2, "Task get CO2", configMINIMAL_STACK_SIZE+200,
	 NULL,CO2_TASK_PRIORITY, NULL);
}

void my_co2_call_back(uint16_t ppm)
{
	_ppm = ppm;
	printf("CO2 measurement: %d ppm\n", ppm);
	
	// Here you can use the CO2 ppm value
}

void vTaskGetCO2(void* pvParameters){
	(void)pvParameters;
	while (1)
	{
		rc = mh_z19_take_meassuring();
		vTaskDelay(20); // to make sure the callback wrote the ppm value
		measurmentCO2.readingLabel = CO2_LABEL;
		measurmentCO2.value = _ppm;
		
		if(_writeFlag){
			if(!xQueueSend(xSendingQueue, (void*)&measurmentCO2, 100)) {
				printf("Failed to send CO2 to the queue\n");
				vTaskDelay(200); // wait a little and try write data again
				} else {
				printf(" @@@@ ---- >>> Succeeded in writing CO2 to the queue <<< ---- @@@@\n");
				UBaseType_t itemsNumber = uxQueueMessagesWaiting( xSendingQueue );
				if(itemsNumber == QUEUE_READINGS_NUMBER){
					// queue is full
					printf("Queue is full, setting the flag to false\n");
					_writeFlag = false;
				}
				vTaskDelay(2000); // let other sensors write data to the queue
			};
		} else {
			printf("Write flag is false, cannot write things in the queue\n");
			vTaskDelay(100);
		}
		
		
	}
}