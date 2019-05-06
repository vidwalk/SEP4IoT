DECLARE @StartDate DATETIME
DECLARE @EndDate DATETIME
SET @StartDate = '1996-01-01'
SET @EndDate = DATEADD(d, 1095,
@StartDate)
WHILE @StartDate <= @EndDate
BEGIN
INSERT INTO [D_Date]
(
CalendarDate
,WeekDayName
,MonthName
,Year
)
SELECT
@StartDate
,DATENAME(weekday,@startDate)
,DATENAME(month, @StartDate )
,DATENAME(year,@StartDate)
SET @StartDate = DATEADD(dd, 1, @StartDate)
END