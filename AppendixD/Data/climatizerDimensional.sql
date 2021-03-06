USE [climatizerDimensional]
GO
/****** Object:  Table [dbo].[D_Date]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[D_Date](
	[DateKey] [int] IDENTITY(1,1) NOT NULL,
	[CalendarDate] [datetime2](3) NULL,
	[WeekDayName] [nvarchar](50) NULL,
	[MonthName] [nvarchar](50) NULL,
	[Year] [int] NULL,
 CONSTRAINT [PK_D_Date] PRIMARY KEY CLUSTERED 
(
	[DateKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[D_Device]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[D_Device](
	[DeviceKey] [int] IDENTITY(1,1) NOT NULL,
	[DeviceId] [varchar](17) NOT NULL,
	[deviceName] [varchar](50) NULL,
	[validFrom] [date] NULL,
	[validTo] [date] NULL,
 CONSTRAINT [PK_D_Device] PRIMARY KEY CLUSTERED 
(
	[DeviceKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[D_Time]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[D_Time](
	[TimeKey] [int] IDENTITY(1,1) NOT NULL,
	[TimeAltKey] [int] NOT NULL,
	[Time30] [varchar](8) NOT NULL,
	[Hour30] [tinyint] NOT NULL,
	[MinuteNumber] [tinyint] NOT NULL,
	[SecondNumber] [tinyint] NOT NULL,
	[TimeInSecond] [int] NOT NULL,
	[HourlyBucket] [varchar](15) NOT NULL,
	[DayTimeBucketGroupKey] [int] NOT NULL,
	[DayTimeBucket] [varchar](100) NOT NULL,
 CONSTRAINT [PK_D_Time] PRIMARY KEY CLUSTERED 
(
	[TimeKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DateUpdate]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DateUpdate](
	[lastUpdate] [datetime2](3) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[F_Reading]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[F_Reading](
	[DeviceKey] [int] NOT NULL,
	[TimeKey] [int] NOT NULL,
	[DateKey] [int] NOT NULL,
	[CO2Value] [float] NOT NULL,
	[HumidityValue] [float] NOT NULL,
	[TemperatureValue] [float] NOT NULL,
	[LightValue] [float] NOT NULL,
 CONSTRAINT [F_Reading_PK] PRIMARY KEY CLUSTERED 
(
	[DeviceKey] ASC,
	[TimeKey] ASC,
	[DateKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[stage_D_Device]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[stage_D_Device](
	[DeviceKey] [int] IDENTITY(1,1) NOT NULL,
	[DeviceId] [varchar](17) NOT NULL,
	[deviceName] [varchar](50) NULL,
	[validFrom] [date] NULL,
	[validTo] [date] NULL,
 CONSTRAINT [PK_stage_D_Device] PRIMARY KEY CLUSTERED 
(
	[DeviceKey] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[stage_F_Reading]    Script Date: 15/05/2019 08:38:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[stage_F_Reading](
	[DeviceKey] [int] NULL,
	[TimeKey] [int] NULL,
	[DateKey] [int] NULL,
	[DeviceId] [varchar](17) NULL,
	[CO2Value] [float] NOT NULL,
	[HumidityValue] [float] NOT NULL,
	[TemperatureValue] [float] NOT NULL,
	[TimeValue] [varchar](8) NULL,
	[DateValue] [datetime2](3) NULL,
	[LightValue] [float] NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[F_Reading]  WITH CHECK ADD  CONSTRAINT [F_Reading_D_Time_FK] FOREIGN KEY([TimeKey])
REFERENCES [dbo].[D_Time] ([TimeKey])
GO
ALTER TABLE [dbo].[F_Reading] CHECK CONSTRAINT [F_Reading_D_Time_FK]
GO
ALTER TABLE [dbo].[F_Reading]  WITH CHECK ADD  CONSTRAINT [FK_F_Reading_D_Date] FOREIGN KEY([DateKey])
REFERENCES [dbo].[D_Date] ([DateKey])
GO
ALTER TABLE [dbo].[F_Reading] CHECK CONSTRAINT [FK_F_Reading_D_Date]
GO
ALTER TABLE [dbo].[F_Reading]  WITH CHECK ADD  CONSTRAINT [FK_F_Reading_D_Device] FOREIGN KEY([DeviceKey])
REFERENCES [dbo].[D_Device] ([DeviceKey])
GO
ALTER TABLE [dbo].[F_Reading] CHECK CONSTRAINT [FK_F_Reading_D_Device]
GO
