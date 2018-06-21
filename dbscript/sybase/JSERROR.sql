use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.JSERROR'))
begin
		drop table dbo.JSERROR
end
go

CREATE TABLE dbo.JSERROR(
	ID bigint IDENTITY NOT NULL,
	P_ID varchar(255) NOT NULL,
	TSTAMP timestamp NOT NULL,
	URL varchar(255) NOT NULL,
	JSERROR varchar(255) NOT NULL
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.JSERROR  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.JSERROR  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.JSERROR  to DEVELOPER_SUPP_ROLE 
go

