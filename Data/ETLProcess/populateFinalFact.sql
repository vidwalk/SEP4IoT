INSERT INTO climatizerDimensional.dbo.F_Reading
(DeviceKey, TimeKey, DateKey, CO2Value, HumidityValue, TemperatureValue)
SELECT DeviceKey, TimeKey, DateKey, CO2Value, HumidityValue, TemperatureValue
FROM stage_F_Reading
