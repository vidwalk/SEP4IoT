INSERT INTO climatizerDimensional.dbo.F_Reading
(DeviceKey, TimeKey, DateKey, CO2Value, HumidityValue, TemperatureValue, LightValue)
SELECT DeviceKey, TimeKey, DateKey, CO2Value, HumidityValue, TemperatureValue, LightValue
FROM stage_F_Reading
