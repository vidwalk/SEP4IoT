DELETE FROM climatizerDimensional.dbo.stage_F_Reading

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
WHERE [Date] > (SELECT lastUpdate FROM DateUpdate)

---Matching the keys in the temporary fact order with the keys in the dimension
UPDATE stage_F_Reading
SET DeviceKey = (SELECT DeviceKey from D_Device WHERE D_Device.DeviceId = stage_F_Reading.DeviceId)
UPDATE stage_F_Reading
SET TimeKey = (SELECT TimeKey from D_Time WHERE D_Time.Time30 = stage_F_Reading.TimeValue)
UPDATE stage_F_Reading
SET DateKey = (SELECT DateKey from D_Date WHERE D_Date.CalendarDate = stage_F_Reading.DateValue)

INSERT INTO climatizerDimensional.dbo.F_Reading
(DeviceKey, TimeKey, DateKey, CO2Value, HumidityValue, TemperatureValue)
SELECT DeviceKey, TimeKey, DateKey, CO2Value, HumidityValue, TemperatureValue
FROM stage_F_Reading

UPDATE climatizerDimensional.dbo.DateUpdate SET lastUpdate = CURRENT_TIMESTAMP