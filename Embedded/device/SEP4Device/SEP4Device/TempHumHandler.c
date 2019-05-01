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
	
	printf("Humidity and Temperature sensor initialization returned code: %d\n",hih8120Create());
	
	xTaskCreate(_vTaskGetTemperatureAndHumidity, "Task Get Temp&Hum",
	configMINIMAL_STACK_SIZE, NULL, getTemperature_TASK_PRIORITY, NULL);
}

void _vTaskGetTemperatureAndHumidity(void* pvParameters){
	(void)pvParameters;
	float humidity = 0.0;
	float temperature = 0.0;
	while(1){
		vTaskDelay(200);
		hih8120Wakeup();
		vTaskDelay(2000);
		printf("Temp and humidity sensor waking up returned  the code\n" );
		printf("Measuring the humidity and temperature returned with the code: %d\n", hih8120Meassure());
		vTaskDelay(3);
		
		humidity = (float) hih8120GetHumidity();
		temperature = (float) hih8120GetTemperature();
		printf("%f %f\n", humidity, temperature);
		
	}
	
}