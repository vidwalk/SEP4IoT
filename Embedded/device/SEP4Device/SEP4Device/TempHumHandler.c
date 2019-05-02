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
void initialize_temper_hum(char getTemperature_TASK_PRIORITY) {
	
	xTaskCreate(_vTaskGetTemperatureAndHumidity, "Task Get Temp&Hum",
	configMINIMAL_STACK_SIZE+200, NULL, getTemperature_TASK_PRIORITY, NULL);
}

void _vTaskGetTemperatureAndHumidity(void* pvParameters){
	(void)pvParameters;
	float humidity = 0.0;
	float temperature = 0.0;
	int a = hih8120Create();
	printf("Humidity and Temperature sensor initialization returned code: %d\n",a);
	while(1){
		hih8120Wakeup();
		vTaskDelay(2000);
		printf("Temp and humidity sensor waking up returned  the code\n" );
		printf("Measuring the humidity and temperature returned with the code: %d\n", hih8120Meassure());
		vTaskDelay(100);
		
		humidity = hih8120GetHumidity();
		temperature = hih8120GetTemperature();
		vTaskDelay(2);
		printf("%f %f\n", humidity, temperature);
		vTaskDelay(2);
		
	}
	
}