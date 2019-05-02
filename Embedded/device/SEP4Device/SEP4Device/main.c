/*
 * SEP4Device.c
 *
 * Created: 4/26/2019 11:02:22 AM
 * Author : skorejen
 */ 



#define LED_TASK_PRIORITY 7
#define initializeLora_TASK_PRIORITY 6
#define Temperature_TASK_PRIORITY 4
#define CO2_TASK_PRIORITY 5
#include "reading.c"

#include "FreeRTOS/FreeRTOSTraceDriver/FreeRTOSTraceDriver.h"

#include <ihal.h>
#include "lora_handler.h"
#include "temp_hum_handler.h"
#include "ios_io.h"
#include "co2_handler.h"

int main(void)
{
	stdioCreate(0);
	sei();
	
	create_lora_connection(initializeLora_TASK_PRIORITY, LED_TASK_PRIORITY);
	//initialize_temper_hum(Temperature_TASK_PRIORITY);
	//initialize_co2(CO2_TASK_PRIORITY);
	
	vTaskStartScheduler();
    while (1) 
    {
    }
}






