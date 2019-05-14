INSERT INTO climatizerDimensional.dbo.stage_D_Device
(DeviceId)
SELECT DISTINCT DeviceId
FROM climatizerDB.dbo.Reading
GO
UPDATE climatizerDimensional.dbo.stage_D_Device
SET deviceName='Horsens Sensor'
