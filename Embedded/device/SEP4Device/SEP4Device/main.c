/*
 * SEP4Device.c
 *
 * Created: 4/26/2019 11:02:22 AM
 * Author : skorejen
 */ 



#define LED_TASK_PRIORITY 8
#define initializeLora_TASK_PRIORITY 7
#define getTemperature_TASK_PRIORITY 6
#define getCO2_TASK_PRIORITY 5
#include "reading.c"

#include "FreeRTOS/FreeRTOSTraceDriver/FreeRTOSTraceDriver.h"

#include <ihal.h>
#include <mh_z19.h>
#include "lora_handler.h"
#include "temp_hum_handler.h"
#include "ios_io.h"

TaskHandle_t xGet_CO2_Handler = NULL;
void vTaskGetCO2(void* pvParameters);
void my_co2_call_back(uint16_t ppm);
uint16_t ppm;
mh_z19_return_code_t rc;
int main(void)
{
	stdioCreate(0);
	sei();

	create_lora_connection(initializeLora_TASK_PRIORITY, LED_TASK_PRIORITY);
	initialize_temper_hum(getTemperature_TASK_PRIORITY);
	//Start CO2
	// xTaskCreate(vTaskGetCO2, "Task get CO2", configMINIMAL_STACK_SIZE, NULL, getCO2_TASK_PRIORITY, &xGet_CO2_Handler);
	
	// Start Temperature
	// 
	vTaskStartScheduler();
    while (1) 
    {
    }
}

void my_co2_call_back(uint16_t ppm)
{
	printf("CO2 measurement: %d\n", ppm);
	vTaskDelay(5);
	// Here you can use the CO2 ppm value
}

void vTaskGetCO2(void* pvParameters){
	(void)pvParameters;
	
	while (1)
	{
		printf("Taking measurement CO2\n");
		vTaskDelay(2);
		rc = mh_z19_take_meassuring();
		if (rc != MHZ19_OK)
		{
			// Something went wrong
			printf("Something went wrong with the CO2 sensor\n");
		}
		vTaskDelay(500);
	}
}




