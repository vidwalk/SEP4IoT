INSERT INTO climatizerDimensional.dbo.D_Device
(DeviceId, deviceName)
SELECT DeviceId, deviceName
FROM stage_D_Device
