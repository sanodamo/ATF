use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.MASTER_TABLE'))
begin
		drop table dbo.MASTER_TABLE
end
go

CREATE TABLE dbo.MASTER_TABLE(
	ID bigint IDENTITY NOT NULL,
	P_ID varchar(255) NOT NULL,
	KEY_VAL varchar(255) NOT NULL,
	VAL varchar(2000) NOT NULL
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.MASTER_TABLE  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.MASTER_TABLE  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.MASTER_TABLE  to DEVELOPER_SUPP_ROLE 
go

