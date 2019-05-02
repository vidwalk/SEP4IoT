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

uint16_t ppm;
mh_z19_return_code_t rc;

void initialize_co2(char CO2_TASK_PRIORITY) {
	
	
	xTaskCreate(vTaskGetCO2, "Task get CO2", configMINIMAL_STACK_SIZE+200, NULL,CO2_TASK_PRIORITY, NULL);
	
}

void my_co2_call_back(uint16_t ppm)
{
	printf("CO2 measurement: %d\n", ppm);
	vTaskDelay(5);
	// Here you can use the CO2 ppm value
}

void vTaskGetCO2(void* pvParameters){
	(void)pvParameters;
	mh_z19_create(ser_USART3, my_co2_call_back);
	while (1)
	{
		printf("Taking measurement CO2\n");
		vTaskDelay(2);
		rc = mh_z19_take_meassuring();
			// Something went wrong
			printf("CO2 Sensor initialized with %d\n", rc);
		
		vTaskDelay(200);
	}
}