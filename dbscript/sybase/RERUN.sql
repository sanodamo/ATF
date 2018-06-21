use rad_precert
go


if exists (select * from sysobjects where id = OBJECT_ID('dbo.RERUN'))
begin
		drop table dbo.RERUN
end
go

CREATE TABLE dbo.RERUN(
	ID bigint IDENTITY NOT NULL,
	CURRENT_THREAD int NOT NULL,
	CURRENT_PID varchar(255) NOT NULL,
	PREVIOUS_PID varchar(255) NOT NULL
)
lock datarows
WITH IDENTITY_GAP=1000

go

/***** grants for table dbo.THREADS *****/

grant Select on dbo.RERUN  to RADIMASIS_APP_ROLE 
go
grant Select on dbo.RERUN  to RADPORTAL_APP_ROLE 
go
grant Select on dbo.RERUN  to DEVELOPER_SUPP_ROLE 
go

