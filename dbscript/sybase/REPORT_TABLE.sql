use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.REPORT_TABLE'))
begin
		drop table dbo.REPORT_TABLE
end
go

CREATE TABLE dbo.REPORT_TABLE(
	ID bigint IDENTITY NOT NULL,
	P_ID varchar (255) NOT NULL,
	START_TIME datetime NOT NULL,
	TOTAL_STEPS int NOT NULL,
	STEPS_PASSED int NOT NULL,
	STEPS_FAILED int NOT NULL,
	TOTAL_EXECUTION_TIME varchar(255) NOT NULL,
	RESULT varchar (255) NOT NULL,
	BUILD varchar(255) NOT NULL,
	CONTENT_VERSION varchar(255) NOT NULL,
	COOKIE varchar(255) NOT NULL
) 
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.REPORT_TABLE  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.REPORT_TABLE  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.REPORT_TABLE  to DEVELOPER_SUPP_ROLE 
go

