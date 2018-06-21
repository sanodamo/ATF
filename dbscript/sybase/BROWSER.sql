use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.BROWSER'))
begin
		drop table dbo.BROWSER
end
go

CREATE TABLE dbo.BROWSER(
	ID bigint IDENTITY NOT NULL,
	NAME varchar(255) NOT NULL,
	BROWSER_NAME varchar(255) NOT NULL,
    BROWSER_VERSION varchar(255) NOT NULL,
	OS varchar(255) NOT NULL
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.BROWSER  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.BROWSER  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.BROWSER  to DEVELOPER_SUPP_ROLE 
go

