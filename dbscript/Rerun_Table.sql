USE [JAC]
GO

/****** Object:  Table [dbo].[RERUN_TABLE]    Script Date: 3/11/2018 9:16:56 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[RERUN_TABLE](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CURRENT_THREAD] [int] NOT NULL,
	[CURRENT_PID] [varchar](255) NOT NULL,
	[PREVIOUS_PID] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

