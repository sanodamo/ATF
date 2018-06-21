use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.TESTDATA'))
begin
		drop table dbo.TESTDATA
end
go

create table dbo.TESTDATA (
    ID bigint IDENTITY NOT NULL,
	KEY_GROUP  varchar(255) NOT NULL,
	KEY_NAME varchar(255) NOT NULL,
	VALUE varchar(255) NOT NULL	
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.TESTDATA  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.TESTDATA  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.TESTDATA  to DEVELOPER_SUPP_ROLE 
go

