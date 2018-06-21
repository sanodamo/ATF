use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.STEP_LEVEL_REPORT'))
begin
		drop table dbo.STEP_LEVEL_REPORT
end
go

CREATE TABLE dbo.STEP_LEVEL_REPORT(
	ID bigint IDENTITY NOT NULL,
	P_ID varchar(255) NOT NULL,
	TSTAMP datetime NOT NULL,
	STEP_NUMBER int NOT NULL,
	KEYWORD varchar(255) NOT NULL,
	PARAMETERS varchar(255) NOT NULL,
	DESCRIPTION varchar(255) NOT NULL,
	RESULT varchar(255) NOT NULL
) 

lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.STEP_LEVEL_REPORT  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.STEP_LEVEL_REPORT  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.STEP_LEVEL_REPORT  to DEVELOPER_SUPP_ROLE 
go

