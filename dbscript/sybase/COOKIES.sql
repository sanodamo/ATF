use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.COOKIES'))
begin
		drop table dbo.COOKIES
end
go

CREATE TABLE dbo.COOKIES(
	ID bigint IDENTITY NOT NULL,
	P_ID varchar(255) NOT NULL,
	TSTAMP timestamp NOT NULL,
	NAME varchar(255) NOT NULL,
	DOMAIN varchar(255) NOT NULL,
	VALUE varchar(255) NOT NULL
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.COOKIES  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.COOKIES  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.COOKIES  to DEVELOPER_SUPP_ROLE 
go

