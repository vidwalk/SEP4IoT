USE [climatizerDB]
GO
/****** Object:  Table [dbo].[Device]    Script Date: 14-May-19 11:28:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Device](
	[DeviceId] [varchar](17) NOT NULL,
	[deviceName] [varchar](50) NULL,
 CONSTRAINT [Device_PK] PRIMARY KEY CLUSTERED 
(
	[DeviceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reading]    Script Date: 14-May-19 11:28:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reading](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[CO2] [float] NOT NULL,
	[Temperature] [float] NOT NULL,
	[Humidity] [float] NOT NULL,
	[Date] [datetime2](3) NOT NULL,
	[DeviceId] [varchar](17) NULL,
	[Light] [float] NOT NULL,
 CONSTRAINT [PK_Reading] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Reading]  WITH CHECK ADD  CONSTRAINT [Reading_Device_FK] FOREIGN KEY([DeviceId])
REFERENCES [dbo].[Device] ([DeviceId])
GO
ALTER TABLE [dbo].[Reading] CHECK CONSTRAINT [Reading_Device_FK]
GO
