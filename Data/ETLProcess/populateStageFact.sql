INSERT INTO climatizerDimensional.dbo.stage_F_Reading
(DeviceId, TimeValue, DateValue, CO2Value, HumidityValue, TemperatureValue)
SELECT
DeviceId,
convert(varchar(8), convert(time, [Date])) as [Time],
convert(date, [Date]) as [Date],
CO2,
Humidity,
Temperature
FROM climatizerDB.dbo.Reading