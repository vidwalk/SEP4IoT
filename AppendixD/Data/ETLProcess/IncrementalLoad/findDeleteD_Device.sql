use climatizerDB
go
--- find deleted rows-
update climatizerDimensional.dbo.D_Device
SET ValidTo = DATEADD(DAY, -1, (SELECT lastUpdate FROM climatizerDimensional.dbo.DateUpdate)) --- last Updated
Where DeviceId in
(
--- yesterday
(select DeviceId
from
climatizerDimensional.dbo.D_Device
)
EXCEPT
(
--- today
select DeviceId
from dbo.Device
)
)

UPDATE climatizerDimensional.dbo.DateUpdate SET lastUpdate = CURRENT_TIMESTAMP