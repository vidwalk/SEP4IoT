/*
 * SEP4Device.c
 *
 * Created: 4/26/2019 11:02:22 AM
 * Author : skorejen
 */ 



#define LED_TASK_PRIORITY 8
#define initializeLora_TASK_PRIORITY 7
#define Temperature_TASK_PRIORITY 5
#define CO2_TASK_PRIORITY 6
#define Light_TASK_PRIORITY 4

#include "FreeRTOS/FreeRTOSTraceDriver/FreeRTOSTraceDriver.h"

#include <ihal.h>
#include "lora_handler.h"
#include "temp_hum_handler.h"
#include "ios_io.h"
#include "co2_handler.h"
#include "motor_handler.h"

QueueHandle_t xSendingQueue;
bool _writeFlag; // indicates if sensor can write into the queue - if true then yes
int main(void)
{
	_writeFlag = true;
	stdioCreate(0);
	// sei(); not needed as task scheduler does it
	xSendingQueue = xQueueCreate(QUEUE_READINGS_NUMBER, sizeof(struct reading));
	create_lora_connection(initializeLora_TASK_PRIORITY, LED_TASK_PRIORITY, &xSendingQueue, &_writeFlag);
	initialize_temper_hum(Temperature_TASK_PRIORITY, &xSendingQueue, &_writeFlag);
	initialize_co2(CO2_TASK_PRIORITY, &xSendingQueue, &_writeFlag);
	initialize_light(Light_TASK_PRIORITY, &xSendingQueue, &_writeFlag);
	//initialize_motor(3);
	vTaskStartScheduler();
    while (1) 
    {
    }
}






