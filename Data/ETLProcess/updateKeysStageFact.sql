---Matching the keys in the temporary fact order with the keys in the dimension
UPDATE stage_F_Reading
SET DeviceKey = (SELECT DeviceKey from D_Device WHERE D_Device.DeviceId = stage_F_Reading.DeviceId)
UPDATE stage_F_Reading
SET TimeKey = (SELECT TimeKey from D_Time WHERE D_Time.Time30 = stage_F_Reading.TimeValue)
UPDATE stage_F_Reading
SET DateKey = (SELECT DateKey from D_Date WHERE D_Date.CalendarDate = stage_F_Reading.DateValue)