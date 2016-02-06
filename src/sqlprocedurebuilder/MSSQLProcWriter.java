package sqlprocedurebuilder;

public class MSSQLProcWriter extends ProcWriter {
	
	
	public MSSQLProcWriter(){
		super();
		this.
	}
	
	
	
	public String CreateSelectRowProcedure(String iDatabaseName, String iTableName, String iColumns, String iPrimaryKeyName, String iPrimaryKeyType, 
			String iAuthorName, String iCreateDate, String iDescription){
		String lProcedureString = null;
		
		lProcedureString = 								"USE ["+ iDatabaseName + "]\n";
		lProcedureString = lProcedureString  + 			"GO\n\n";
		lProcedureString = lProcedureString  + 			"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_SELECT_ROW_BY_PRIMARY_KEY\n";
		lProcedureString = lProcedureString  + 			"@PRIMARY_KEY_VALUE " + iPrimaryKeyType + "\n";
		lProcedureString = lProcedureString  + 			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  + 			"\tSELECT\n";
		lProcedureString = lProcedureString  + 			iColumns + "\n";		
		lProcedureString = lProcedureString  +			"\tFROM\n";
		lProcedureString = lProcedureString  +			"\t\t" + iTableName + "\n";
		lProcedureString = lProcedureString  +			"\tWHERE " + iPrimaryKeyName + " = @PRIMARY_KEY_VALUE\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
						
		return lProcedureString;
	}
	
	public String CreateSelectAllRowsProcedure(String iDatabaseName, String iTableName, String iColumns, String iAuthorName, String iCreateDate, String iDescription){
		String lProcedureString = null;
		
		lProcedureString = 								"USE ["+ iDatabaseName + "]\n";
		lProcedureString = lProcedureString  + 			"GO\n\n";
		lProcedureString = lProcedureString  + 			"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_SELECT_ALL_ROWS\n";
		lProcedureString = lProcedureString  + 			"\n";
		lProcedureString = lProcedureString  + 			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  + 			"\tSELECT\n";
		lProcedureString = lProcedureString  + 			iColumns + "\n";		
		lProcedureString = lProcedureString  +			"\tFROM\n";
		lProcedureString = lProcedureString  +			"\t\t" + iTableName + "\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
						
		return lProcedureString;
	}
	
	
		
	
	public String CreateDeleteRowProcedure(String iDatabaseName, String iTableName, String iPrimaryKeyName, String iPrimaryKeyType, 
			String iAuthorName, String iCreateDate, String iDescription){
		String lProcedureString = null;
		
		lProcedureString = 								"USE ["+ iDatabaseName + "]\n";
		lProcedureString = lProcedureString  + 			"GO\n\n";
		lProcedureString = lProcedureString  + 			"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_DELETE_ROW_BY_PRIMARY_KEY\n";
		lProcedureString = lProcedureString  + 			"@PRIMARY_KEY_VALUE " + iPrimaryKeyType + "\n";
		lProcedureString = lProcedureString  + 			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  + 			"\tDELETE\n";
		lProcedureString = lProcedureString  +			"\tFROM\n"; 
		lProcedureString = lProcedureString  +			"\t\t" + iTableName + "\n";
		lProcedureString = lProcedureString  +			"\tWHERE " + iPrimaryKeyName + " = @PRIMARY_KEY_VALUE\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
						
		return lProcedureString;
	}
	
	
	
	public String CreateUpdateRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSetColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, 
			String iAuthorName, String iCreateDate, String iDescription)
	{				
		String lProcedureString = null;
		
		lProcedureString = 								"USE ["+ iDatabaseName + "]\n";
		lProcedureString = lProcedureString  + 			"GO\n\n";
		lProcedureString = lProcedureString  + 			"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_UPDATE_ROW_BY_PRIMARY_KEY\n";
		lProcedureString = lProcedureString  +			iParameterColumns + "\n";
		lProcedureString = lProcedureString  +			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  + 			"\tUPDATE " + iTableName +"\n";
		lProcedureString = lProcedureString  + 			"\tSET\n";
		lProcedureString = lProcedureString  + 			iSetColumns + "\n";
		lProcedureString = lProcedureString  +			"\tWHERE " + iPrimaryKeyName + " = @PRIMARY_KEY_VALUE\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
				
		return lProcedureString;
		
	}
	
	
	

	public String CreateInsertRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns,
			String iSetColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName,
			String iCreateDate, String iDescription) {
		// TODO Auto-generated method stub
		String lProcedureString = null;
		return lProcedureString;
	}

	

}
