USE [climatizerDimensional]
GO

/****** Object:  Table [dbo].[changed_product]    Script Date: 12-Apr-19 5:19:20 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[changed_device](
	[DeviceKey] [int] IDENTITY(1,1) NOT NULL,
	[DeviceId] [varchar(17)] NOT NULL,
	[deviceName] [nvarchar](40) NOT NULL,
 CONSTRAINT [PK_changed_device] PRIMARY KEY CLUSTERED 
(
	[DeviceKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

--- insert & update for changed Products
---
use climatizerDB
go
--- 1. INSERT INTO TEMP TABLE
Insert into
climatizerDimensional.dbo.changed_device(
       [DeviceId]
      ,[deviceName]
)
(
--- today
select [DeviceId]
,[deviceName]
from Device
)
EXCEPT
(
--- yesterday
select [DeviceId]
,[deviceName]
from climatizerDimensional.dbo.D_Device
)
EXCEPT
(select [DeviceId]
,[deviceName]
from Device
where DeviceId NOT IN (SELECT DeviceId
FROM climatizerDimensional.dbo.D_Device)
)

use climatizerDimensional
go
--- 2. Update exsisting row in dimension table
---
UPDATE D_Device
SET ValidTo = DATEADD(DAY, -1, (SELECT lastUpdate FROM climatizerDimensional.dbo.DateUpdate)) --- last Updated
WHERE DeviceId IN
(SELECT DeviceId
FROM [changed_device])
--- 3. Insert new row in dimension table
---
INSERT INTO D_Device
([DeviceId]
      ,[deviceName]
,validFrom
,validTo
)
SELECT [DeviceId]
      ,[deviceName]
,DATEADD(DAY, +1, (SELECT lastUpdate FROM climatizerDimensional.dbo.DateUpdate)) ---- last updated + 1
,'01/01/2099'
FROM [changed_device]

DROP TABLE climatizerDimensional.dbo.changed_device

UPDATE climatizerDimensional.dbo.DateUpdate SET lastUpdate = CURRENT_TIMESTAMP