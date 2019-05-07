/*
* TempHumHandler.c
*
* Created: 5/1/2019 3:53:13 PM
*  Author: skorejen
*/

#include "temp_hum_handler.h"
#include "ios_io.h"
#include <hih8120.h>

void _vTaskGetTemperatureAndHumidity(void* pvParameters);

TaskHandle_t xGet_Temperature_Handler = NULL;
QueueHandle_t xSendingQueue;
bool _writeFlag;

void initialize_temper_hum(char getTemperature_TASK_PRIORITY, void* ptrQueue, void* writeFlag) {
	xSendingQueue = *(QueueHandle_t*)ptrQueue;
	_writeFlag = writeFlag;
	int a = hih8120Create();
	printf("Humidity and Temperature sensor initialization returned code: %d\n",a);
	xTaskCreate(_vTaskGetTemperatureAndHumidity, "Task Get Temp&Hum",
	configMINIMAL_STACK_SIZE+200, NULL, getTemperature_TASK_PRIORITY, NULL);
}

void _vTaskGetTemperatureAndHumidity(void* pvParameters){
	(void)pvParameters;
	uint16_t humidity = 0;
	uint16_t temperature = 0;
	struct reading measurmentTemp;
	struct reading measurmentHum;
	while(1){
		hih8120Wakeup();
		vTaskDelay(200);
		hih8120Meassure();
		vTaskDelay(100);
		
		humidity = hih8120GetHumidityPercent_x10(); // get int instead of floats
		temperature = hih8120GetTemperature_x10();  // get int instead of floats
		vTaskDelay(2);
		printf("Temperature is: %d and humidity is: %d\n", humidity, temperature);
		
		
		measurmentTemp.readingLabel = TEMPERATURE_LABEL;
		measurmentTemp.value = temperature;
		measurmentHum.readingLabel = HUMIDITY_LABEL;
		measurmentHum.value = humidity;
		if(_writeFlag){
			if(!xQueueSend(xSendingQueue, (void*)&measurmentTemp, 100)) {
				printf("Failed to send temperature to the queue\n");
				vTaskDelay(200); // wait a little and try write data again
				} else {
				printf("@@@@ ---- >>> Succeeded in temperature to the queue <<< ---- @@@@\n");
				vTaskDelay(100); // let other sensors write their data to the queue
			};
			if(!xQueueSend(xSendingQueue, (void*)&measurmentHum, 100)) {
				printf("Failed to send humidity to the queue\n");
				vTaskDelay(200);
				} else {
				printf("@@@@ ---- >>> Succeeded in writing humidity to the queue <<< ---- @@@@\n");
				UBaseType_t itemsNumber = uxQueueMessagesWaiting( xSendingQueue );
				if(itemsNumber == QUEUE_READINGS_NUMBER){
					// queue is full
					printf("Queue is full, setting the flag to false\n");
					_writeFlag = false;
				}
				vTaskDelay(2000);
			};
		} else {
			printf("Write flag is false, cannot write things in the queue\n");
			vTaskDelay(100);
		}
		
		
	}
}


