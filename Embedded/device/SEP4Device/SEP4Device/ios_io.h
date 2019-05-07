/*
 * ios_io.h
 *
 * Created: 5/1/2019 4:01:55 PM
 *  Author: skorejen
 */ 
#pragma once

 #include <stddef.h>
 #include <stdio.h>
 #include <ATMEGA_FreeRTOS.h>
 #include <queue.h>
 #include <stdio_driver.h>
 #include <avr/io.h>
 #include <avr/interrupt.h>
 #include "reading.h"
 #include <stdbool.h>
 #include <semphr.h>
 
 #define QUEUE_READINGS_NUMBER 3
 
 #define TEMPERATURE_LABEL 0
 #define HUMIDITY_LABEL 2
 #define CO2_LABEL 4
 #define LIGHT_LABEL 6