/*
 * MotorHandler.c
 *
 * Created: 5/9/2019 11:36:35 AM
 *  Author: skorejen
 */ 

#include "motor_handler.h"
#include "ios_io.h"
#include <rcServo.h>
bool state;
void _vTaskRunMotor(void* pvParameters);

void initialize_motor(char MOTOR_TASK_PRIORITY)
{
	rcServoCreate();
	xTaskCreate(_vTaskRunMotor, "Task motor run",
	configMINIMAL_STACK_SIZE, NULL, MOTOR_TASK_PRIORITY, NULL);
	
}

void _vTaskRunMotor(void* pvParameters){
	(void)pvParameters;
	
	while(1){
		printf("Moving right 100\n");
		vTaskDelay(1);
		rcServoSet(0, 100);
		printf("Moving middle 100\n");
		vTaskDelay(1);
		rcServoSet(0, 0);
	}
}