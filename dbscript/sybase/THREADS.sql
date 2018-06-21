use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.THREADS'))
begin
		drop table dbo.THREADS
end
go

create table dbo.THREADS (
    ID bigint IDENTITY NOT NULL,
	THREAD int NOT NULL,
	P_ID varchar(255) NOT NULL,
	TESTNAME varchar(255) NOT NULL,
	BROWSER varchar(255) NOT NULL,
	BROWSER_VERSION varchar(255) NULL,
	OS varchar(255) NULL,
    USERID varchar(255) NULL,
    HOSTNAME varchar(255) NULL,
    START_TIMESTAMP datetime NULL
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.THREADS  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.THREADS  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.THREADS  to DEVELOPER_SUPP_ROLE 
go

