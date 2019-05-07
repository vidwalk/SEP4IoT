use climatizerDB

insert into climatizerDimensional.dbo.D_Device
(DeviceId
,deviceName
,validFrom
,validTo
)
select DeviceId
,deviceName
,convert(date, CURRENT_TIMESTAMP) as [Date]
,'2099/01/01'
from climatizerDB.dbo.Device --- today
Where DeviceId in
((
--- today
select DeviceId
from climatizerDB.dbo.Device
)
EXCEPT
--- yesterday
(select DeviceId
from climatizerDimensional.dbo.D_Device
)
)

UPDATE climatizerDimensional.dbo.DateUpdate SET lastUpdate = CURRENT_TIMESTAMP