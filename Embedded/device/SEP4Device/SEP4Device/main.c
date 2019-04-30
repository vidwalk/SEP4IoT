/*
 * SEP4Device.c
 *
 * Created: 4/26/2019 11:02:22 AM
 * Author : skorejen
 */ 

// Parameters for OTAA join
#define LORA_appEUI "11dc3bc663ea64c5"
#define LORA_appKEY "5d5e991d0e6d9c165e30a32b35817126"

#define LED_TASK_PRIORITY 8
#define initializeLora_TASK_PRIORITY 7
#define getTemperature_TASK_PRIORITY 6
#define getCO2_TASK_PRIORITY 5
#include "reading.c"
#include <avr/io.h>
#include <avr/interrupt.h>
#include "FreeRTOS/FreeRTOSTraceDriver/FreeRTOSTraceDriver.h"
#include <stdio_driver.h>
#include <ATMEGA_FreeRTOS.h>
#include <ihal.h>
#include <lora_driver.h>
#include <stdio.h>
#include <hih8120.h>
#include <mh_z19.h>

TaskHandle_t xLora_Init_Handler = NULL;
TaskHandle_t xGet_Temperature_Handler = NULL;
TaskHandle_t xGet_CO2_Handler = NULL;
void vTaskInitalizeLora(void* pvParameters);
void vTaskGetTemperatureAndHumidity(void* pvParameters);
void vTaskGetCO2(void* pvParameters);
void my_co2_call_back(uint16_t ppm);
uint16_t ppm;
mh_z19_return_code_t rc;
int main(void)
{
	
	
	stdioCreate(0);
	sei();
	
	hal_create(LED_TASK_PRIORITY); // Must be called first!! LED_TASK_PRIORITY must be a high priority in your system
	lora_driver_create(ser_USART1); // The parameter is the USART port the RN2483 module is connected to - in this case USART1
    /* Replace with your application code */
	
	xTaskCreate(vTaskInitalizeLora, "Task read EUI", configMINIMAL_STACK_SIZE, NULL, initializeLora_TASK_PRIORITY, &xLora_Init_Handler); // Initialize Lora
	
	//Start CO2
	xTaskCreate(vTaskGetCO2, "Task get CO2", configMINIMAL_STACK_SIZE, NULL, getCO2_TASK_PRIORITY, &xGet_CO2_Handler);
	
	//Start Temperature
	//xTaskCreate(vTaskGetTemperatureAndHumidity, "Task Get Temp&Hum", configMINIMAL_STACK_SIZE, NULL, getTemperature_TASK_PRIORITY, &xGet_Temperature_Handler); 
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
		printf("Taking measurment CO2\n");
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

void vTaskGetTemperatureAndHumidity(void* pvParameters){
	(void)pvParameters;
	float humidity = 0.0;
	float temperature = 0.0;
	while(1){
		if ( HIH8120_OK != hih8120Wakeup() )
		{
			printf("Could not wake up temp&hum sensor.\n");
			
			// Something went wrong
			// Investigate the return code further
		}
		vTaskDelay(200);
		printf("Delay passed\n");
		vTaskDelay(2);
		
		if ( HIH8120_OK !=  hih8120Meassure() )
		{
			printf("Something went wrong with meassureing temperature and humidity\n");
			vTaskDelay(2);
			// Something went wrong
			// Investigate the return code further
			
		}
		humidity = (float) hih8120GetHumidity();
		temperature = (float) hih8120GetTemperature();
		printf("%f %f\n", humidity, temperature);
		vTaskDelay(200);
	}
	
}

void vTaskInitalizeLora(void* pvParameters) { 
	(void)pvParameters;
	lora_driver_reset_rn2483(1); // Activate reset line
	vTaskDelay(2);
	lora_driver_reset_rn2483(0); // Release reset line
	vTaskDelay(150); // Wait for tranceiver module to wake up after reset
	lora_driver_flush_buffers(); // get rid of first version string from module after reset!
	

	while(1)
	{
		if (lora_driver_rn2483_factory_reset() != LoRA_OK)
		{
			printf("Something went wrong with reseting the factory");
		} 
		if (lora_driver_configure_to_eu868() != LoRA_OK)
		{
			printf("Something went wrong with frequence plan and settings");
		}
		static char dev_eui[17]; // It is static to avoid it to occupy stack space in the task
		if (lora_driver_get_rn2483_hweui(dev_eui) != LoRA_OK)
		{
			printf("Something went wrong with unique devEUI");
		} else {
			printf("EUI of the device: %s\n", dev_eui);
			vTaskDelay(1);
		}
		if (lora_driver_set_otaa_identity(LORA_appEUI,LORA_appKEY,dev_eui) != LoRA_OK)
		{
			printf("Something went wrong with LoRaWAN parameters for an OTAA join");
		}
		if ( HIH8120_OK == hih8120Create() ) // Initialize the Temp&Hum sensor
		{
			// Driver created OK
			printf("Humidity and Temperature sensor initialized!\n");
			// Always check what hih8120Create() returns
		}
		// The first parameter is the USART port the MH-Z19 sensor is connected to - in this case USART3
		// The second parameter is the address of the call back function
		mh_z19_create(ser_USART3, my_co2_call_back);
		printf("Running!");
		vTaskDelay(1);
		
		if( xLora_Init_Handler != NULL )
		{
			vTaskDelete( xLora_Init_Handler );
		}
	}
	}

