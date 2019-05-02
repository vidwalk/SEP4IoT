/*
* LoraHandler.c
*
* Created: 5/1/2019 3:13:40 PM
*  Author: skorejen
*/
// Parameters for OTAA join
#define LORA_appEUI "11dc3bc663ea64c5"
#define LORA_appKEY "5d5e991d0e6d9c165e30a32b35817126"
#include "ios_io.h"
 #include <lora_driver.h>
 #include <iled.h>
#include "lora_handler.h"
void _vTaskInitalizeLora(void* pvParameters);

TaskHandle_t xLora_Init_Handler = NULL;

void create_lora_connection(UBaseType_t initializeLora_TASK_PRIORITY,UBaseType_t LED_TASK_PRIORITY){
	
	// Initialize Lora
		
		hal_create(LED_TASK_PRIORITY); // Must be called first!! LED_TASK_PRIORITY must be a high priority in your system
		lora_driver_create(ser_USART1); // The parameter is the USART port the RN2483 module is connected to - in this case USART1
		
	xTaskCreate(_vTaskInitalizeLora, "Task read EUI", configMINIMAL_STACK_SIZE, NULL, initializeLora_TASK_PRIORITY, NULL); 
}

void _vTaskInitalizeLora(void* pvParameters) {
	(void)pvParameters;
	
	lora_driver_reset_rn2483(1); // Activate reset line
	vTaskDelay(2);
	lora_driver_reset_rn2483(0); // Release reset line
	vTaskDelay(150); // Wait for transceiver module to wake up after reset
	lora_driver_flush_buffers(); // get rid of first version string from module after reset!
	
		printf("Ran factory reset with code: %d\n", lora_driver_rn2483_factory_reset() );
		printf("Ran frequency plan and settings with code : %d\n", lora_driver_configure_to_eu868());
	static char dev_eui[17]; // It is static to avoid it to occupy stack space in the task
	if (lora_driver_get_rn2483_hweui(dev_eui) != LoRA_OK)
	{
		printf("Something went wrong with unique devEUI");
		vTaskDelay(1);
		} else {
		printf("EUI of the device: %s\n", dev_eui);
		vTaskDelay(1);
	}
		printf("Ran Parameters for an OTAA join with code : %d\n",lora_driver_set_otaa_identity(LORA_appEUI,LORA_appKEY,dev_eui));
		vTaskDelay(1);
		
	// the sending "task" has to be started here (not in a separate task) as we 
	// don't want it to be executed in the meantime of delays of this one
	while(true)
	{
		
	}
}


