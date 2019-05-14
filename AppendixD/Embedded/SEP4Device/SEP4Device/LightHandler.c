/*
* LightHandler.c
*
* Created: 5/6/2019 4:15:29 PM
*  Author: skorejen
*/
#include <tsl2591.h>
#include "ios_io.h"
#include "light_handler.h"

void _vTaskGetLight(void* pvParameters);
void light_callback(tsl2591ReturnCode_t rc);
TaskHandle_t xGet_Light_Handler = NULL;
QueueHandle_t xSendingQueue;
bool _writeFlag;
float _lux;
struct reading measurmentLightRaw;


void initialize_light(char LIGHT_TASK_PRIORITY, void* ptrQueue, void* writeFlag){
	xSendingQueue = *(QueueHandle_t*)ptrQueue;
	_writeFlag = writeFlag;
	printf("Light sensor initialized with code: %d\n",tsl2591Create(light_callback));
	xTaskCreate(_vTaskGetLight, "Task Get Light",
	configMINIMAL_STACK_SIZE, NULL, LIGHT_TASK_PRIORITY, NULL);
}

void _vTaskGetLight(void* pvParameters){
	(void)pvParameters;
	
	
	printf("Light sensor enabled with code: %d\n",tsl2591Enable());
	
	while(1){
		
		tsl2591FetchData();
		vTaskDelay(20);
		measurmentLightRaw.readingLabel = LIGHT_LABEL;
		measurmentLightRaw.value = (uint16_t) _lux;
		if(_writeFlag){ 
			if(!xQueueSend(xSendingQueue, (void*)&measurmentLightRaw, 100)) {
				printf("Failed to send light spec to the queue\n");
				vTaskDelay(200);
				} else {
				printf("@@@@ ---- >>> Succeeded in writing light to the queue <<< ---- @@@@\n");
				UBaseType_t itemsNumber = uxQueueMessagesWaiting( xSendingQueue );
				if(itemsNumber == QUEUE_READINGS_NUMBER){
					// queue is full
					printf("LIGHT SENSOR: Queue is full, setting the flag to false\n");
					_writeFlag = false;
				}
				vTaskDelay(2000);
			};
			} else {
				
			printf("LIGHT SENSOR: Write flag is false, cannot write things in the queue\n");
			printf(" =========> The value of the LUX is: %d", (uint16_t) _lux);
			vTaskDelay(100);
		}
	}
}



void light_callback(tsl2591ReturnCode_t rc)
{
	
	if(rc == TSL2591_DATA_READY){
		if(TSL2591_OK == tsl2591GetLux(&_lux)){
			printf("The lux is equal to %d\n",(uint16_t)_lux);
		}
	}
	
}