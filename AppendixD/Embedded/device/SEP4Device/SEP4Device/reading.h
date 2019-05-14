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
	uint16_t readingLabel; // e.g. 0, 1 etc.
	uint16_t value; // the value read by the sensor
};
#endif /* READING_H_ */