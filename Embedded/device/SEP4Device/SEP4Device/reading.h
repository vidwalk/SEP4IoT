/*
 * reading.h
 *
 * Created: 4/29/2019 1:08:00 PM
 *  Author: skorejen
 */ 


#ifndef READING_H_
#define READING_H_

/*
This struct represents the reading from one sensor,
*/
struct reading {
	char *readingName; // e.g. temperature, humidity etc.
	float value; // the value read by the sensor
};
#endif /* READING_H_ */