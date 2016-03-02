package sqlprocedurebuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.MetaRow;
import common.ProcedureBuildType;

public class MSSQLProcWriter extends ProcWriter {
	
	private MSSQLProcBuilder _MSSQLProcBuilder;
	private String _AbsoluteProcedureDirectorylocation;
	private String _CreateFileHeader;
	private String _CreateSelectAllRowsProcedure;
	private String _CreateSelectRowProcedure;
	private String _CreateDeleteRowProcedure;
	private String _CreateUpdateRowProcedure;
	private String _CreateInsertRowProcedure;
		
	
	public MSSQLProcWriter(String iAbsoluteProcedureDirectorylocation, IProcBuilder lProcBuilder){
		
		this._MSSQLProcBuilder = (MSSQLProcBuilder) lProcBuilder;
		this._AbsoluteProcedureDirectorylocation = iAbsoluteProcedureDirectorylocation;
		
	}
	
	public void Write(ProcedureBuildType iProcedureBuildType, String iAuthorName, String iOneFileName){
		
		String lAuthorName = iAuthorName;
		
		Date lDate = new Date();
		String lCreateDate = lDate.toString();
		String lDatabaseName = MSSQLProcBuilder._dbname;
				
		List<List<MetaRow>> lListOfMetaRowLists = new ArrayList<List<MetaRow>>();
		lListOfMetaRowLists = _MSSQLProcBuilder.get_all_meta_lists_for_all_tables();
		
		String lTableName = "";			
		String lPrimaryKeyName = "";
		String lPrimaryKeyDataType = "";
				  
		List<List<String>> lListOfFiles = new ArrayList<List<String>>(); 
						
		  for (int listcounter = 0; listcounter < lListOfMetaRowLists.size(); listcounter++){
			  
			  List<MetaRow> lListOfMetaRows = new ArrayList<MetaRow>();
			  lListOfMetaRows = lListOfMetaRowLists.get(listcounter);
			  String lSelectColumns = "";
			  String lFileName = "";
			  String lParameterColumns = "";
			  String lSetColumns = "";
			  String lInsertValues = "";
			  String lInsertValuesNoPK = "";
			  String lInsertSelectNoPK = "";
			  
			  // reset for next file
			  this._CreateSelectAllRowsProcedure = "";
			  this._CreateSelectRowProcedure = "";
		  	  this._CreateDeleteRowProcedure = "";
		  	  this._CreateInsertRowProcedure = "";
		  
			  for (int rowcounter = 0; rowcounter < lListOfMetaRows.size(); rowcounter++){
				  							  		  
				  MetaRow lMetaRow = new MetaRow();
				  lMetaRow = lListOfMetaRows.get(rowcounter);
				  					  				  
				  if (lMetaRow.get_isprimarykey() == true)
				  {	    			  
					  lPrimaryKeyName = lMetaRow.get_name();
					  lPrimaryKeyDataType = lMetaRow.get_data_type();
					  lTableName = lMetaRow.get_parent_table();
					  
				  }
				  	
				  // all MetaRows but the last one
				  if (rowcounter  <  lListOfMetaRows.size() - 1){	    			  
					  
					  // get all of them
					  lSelectColumns =  lSelectColumns + "\t\t[" + lMetaRow.get_name() + "],\n"; 
					  lInsertValues = lInsertValues + "\t\t@" + lMetaRow.get_name() + ",\n";
					  										  
					  
					  if (lMetaRow.get_isprimarykey() == false){		// not the primary key rows				  
						  lSetColumns = lSetColumns + "[" + lMetaRow.get_name() + "] = @" + lMetaRow.get_name() + ",\n";
						  lParameterColumns = lParameterColumns + "@" + lMetaRow.get_name() + " " + "[" + lMetaRow.get_data_type() + "]" + lMetaRow.ConvertSizeToString() +",\n";
						  lInsertValuesNoPK = lInsertValuesNoPK + "\t\t@" + lMetaRow.get_name() + ",\n";
						  lInsertSelectNoPK = lInsertSelectNoPK + "\t\t[" + lMetaRow.get_name() + "],\n";
						  
					  }else{
						
						  lParameterColumns = lParameterColumns + "@" + lMetaRow.get_name() + " " + "[" + lMetaRow.get_data_type() + "]" + lMetaRow.ConvertSizeToString() + " = NULL,\n";
					  }
					  
				  }
				  else{  
					  
					  // the last MetaRow
					  lSelectColumns =  lSelectColumns + "\t\t[" +lMetaRow.get_name() + "]\n";
					  lInsertValues = lInsertValues + "\t\t@" + lMetaRow.get_name() + "\n";
					  lInsertValuesNoPK = lInsertValuesNoPK + "\t\t@" + lMetaRow.get_name() + "\n";
					  lInsertSelectNoPK = lInsertSelectNoPK + "\t\t[" + lMetaRow.get_name() + "]\n";
					  lParameterColumns = lParameterColumns + "@" + lMetaRow.get_name() + " " + "[" + lMetaRow.get_data_type() + "]" + lMetaRow.ConvertSizeToString() + "\n";
					  if (lMetaRow.get_isprimarykey() == false){							  
						  lSetColumns = lSetColumns + "[" + lMetaRow.get_name() + "] = @" + lMetaRow.get_name() + "\n";
					  } else{
						  lSetColumns = this.removeLastNumberOfChars(lSetColumns, 2) + "\n";
					  }
					  
					  
					  lFileName = lMetaRow.get_parent_table() + "_CREATE_PROCEDURES.SQL";
				  }	    				  
			  } // inside for loop
			 
			 
			  this._CreateFileHeader =  this.CreateFileHeader(lDatabaseName);
			  this._CreateSelectAllRowsProcedure = this.CreateSelectAllRowsProcedure(lDatabaseName, lTableName, lSelectColumns, lAuthorName, lCreateDate, "Select all rows from table");
			  this._CreateSelectRowProcedure = this.CreateSelectRowProcedure(lDatabaseName, lTableName, lSelectColumns, lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Select one row based on PK");
			  this._CreateDeleteRowProcedure = this.CreateDeleteRowProcedure(lDatabaseName, lTableName, lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Delete one row based on PK");
			  this._CreateInsertRowProcedure = this.CreateInsertRowProcedure(lDatabaseName, lTableName, lParameterColumns, lSelectColumns, lInsertValues, lInsertSelectNoPK, lInsertValuesNoPK, 
					  lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Insert one row");
			  this._CreateUpdateRowProcedure = this.CreateUpdateRowProcedure(lDatabaseName, lTableName, lParameterColumns, lSetColumns, lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Update one row based on PK");
			  

			 		  
			  if (iProcedureBuildType == ProcedureBuildType.ONEFILEPERTABLE){
				  
				  List<String> lListOfProcedures = new ArrayList<String>();
				  lListOfProcedures = this.WriteFileList(this._CreateFileHeader, this._CreateSelectAllRowsProcedure, this._CreateSelectRowProcedure, this._CreateDeleteRowProcedure, this._CreateInsertRowProcedure, this._CreateUpdateRowProcedure);
				  this.WriteOneFilePerTable(lFileName, lListOfProcedures);
			
			  }else if (iProcedureBuildType == ProcedureBuildType.ONEFILEALLTABLES){
				 if (listcounter == 0){
					 lListOfFiles.add(this.WriteFileList(this._CreateFileHeader, this._CreateSelectAllRowsProcedure, this._CreateSelectRowProcedure, this._CreateDeleteRowProcedure, this._CreateInsertRowProcedure, this._CreateUpdateRowProcedure));
				 }else{					 
					 lListOfFiles.add(this.WriteFileList("\n", this._CreateSelectAllRowsProcedure, this._CreateSelectRowProcedure, this._CreateDeleteRowProcedure, this._CreateInsertRowProcedure, this._CreateUpdateRowProcedure)); 
				 }
			  }
			  			  
		  } // outside for loop
		  
		  
		  if (iProcedureBuildType == ProcedureBuildType.ONEFILEALLTABLES){
			  
			  this.WriteOneFile(iOneFileName, lListOfFiles);
		  }
			  
																		
		
	}
	
	
	private String removeLastNumberOfChars(String iStringToShorten, int iSizeToRemove){
		
		if (iStringToShorten == null || iStringToShorten.length() == 0) {
			return iStringToShorten;
		}
		return iStringToShorten.substring(0, iStringToShorten.length() - iSizeToRemove);
	}
	
	
	private void WriteOneFile(String iOneFileName, List<List<String>> lListOfFiles) {
				
		File lFile;
		PrintWriter writer;
				
		try {
			
		lFile = new File(this._AbsoluteProcedureDirectorylocation + "/" + iOneFileName);
		lFile.getParentFile().mkdirs();			
		writer = new PrintWriter(lFile);
		
		for (List<String> tempList : lListOfFiles){
			
			List<String> lProcedureList = new ArrayList<String>();
			lProcedureList = tempList;
												
				for (String temp : lProcedureList){				
					writer.println(temp);				
				}													
		}
				
		writer.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	private List<String> WriteFileList(String iCreateFileHeader, String iCreateSelectAllRowsProcedure, String iCreateSelectRowProcedure, 
			String iCreateDeleteRowProcedure, String iCreateInsertRowProcedure, String iCreateUpdateRowProcedure) {
		
		List<String> lReturnList = new ArrayList<String>();
		lReturnList.add(iCreateFileHeader);
		lReturnList.add(iCreateSelectAllRowsProcedure);
		lReturnList.add(iCreateSelectRowProcedure);
		lReturnList.add(iCreateDeleteRowProcedure);
		lReturnList.add(iCreateInsertRowProcedure);
		lReturnList.add(iCreateUpdateRowProcedure);
		return lReturnList;
		
	}
	
		

	private void WriteOneFilePerTable(String iFileName, List<String> iProcedureList)
	{
						
		List<String> lProcedureList = new ArrayList<String>();
		lProcedureList = iProcedureList;
				
		File lFile;
		PrintWriter writer;
		try {
			lFile = new File(this._AbsoluteProcedureDirectorylocation + "/" + iFileName);
			lFile.getParentFile().mkdirs();		
			writer = new PrintWriter(lFile);
			
			for (String temp : lProcedureList){				
				writer.println(temp);				
			}			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}
	
				
	
	protected String CreateFileHeader(String iDatabaseName){
		String lProcedureString = null;
		
		lProcedureString = 								"USE ["+ iDatabaseName + "]\n";
		lProcedureString = lProcedureString  + 			"GO\n\n";
		
		return lProcedureString;
		
	}
	
	
	protected String CreateSelectRowProcedure(String iDatabaseName, String iTableName, String iSelectColumns, String iPrimaryKeyName, String iPrimaryKeyType, 
			String iAuthorName, String iCreateDate, String iDescription){
		String lProcedureString = null;
		
		lProcedureString = 					 			"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_SELECT_ROW_BY_PRIMARY_KEY\n";
		lProcedureString = lProcedureString  + 			"@" + iPrimaryKeyName + " " + iPrimaryKeyType + "\n";
		lProcedureString = lProcedureString  + 			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  + 			"\tSELECT\n";
		lProcedureString = lProcedureString  + 			iSelectColumns + "\n";		
		lProcedureString = lProcedureString  +			"\tFROM\n";
		lProcedureString = lProcedureString  +			"\t\t[" + iTableName + "]\n";
		lProcedureString = lProcedureString  +			"\tWHERE [" + iPrimaryKeyName + "] = @" + iPrimaryKeyName + "\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
						
		return lProcedureString;
	}
	
	public String CreateSelectAllRowsProcedure(String iDatabaseName, String iTableName, String iSelectColumns, String iAuthorName, String iCreateDate, String iDescription){
		String lProcedureString = null;
		
		lProcedureString = 					 			"SET ANSI_NULLS ON\n";
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
		lProcedureString = lProcedureString  + 			iSelectColumns + "\n";		
		lProcedureString = lProcedureString  +			"\tFROM\n";
		lProcedureString = lProcedureString  +			"\t\t[" + iTableName + "]\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
						
		return lProcedureString;
	}
	
			
	
	public String CreateDeleteRowProcedure(String iDatabaseName, String iTableName, String iPrimaryKeyName, String iPrimaryKeyType, 
			String iAuthorName, String iCreateDate, String iDescription){
		String lProcedureString = null;
		
		lProcedureString = 								"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_DELETE_ROW_BY_PRIMARY_KEY\n";
		lProcedureString = lProcedureString  + 			"@" + iPrimaryKeyName + " " + iPrimaryKeyType + "\n";
		lProcedureString = lProcedureString  + 			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  + 			"\tDELETE\n";
		lProcedureString = lProcedureString  +			"\tFROM\n"; 
		lProcedureString = lProcedureString  +			"\t\t[" + iTableName + "]\n";
		lProcedureString = lProcedureString  +			"\tWHERE [" + iPrimaryKeyName + "] = @" + iPrimaryKeyName + "\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
						
		return lProcedureString;
	}
	
	
	
	public String CreateUpdateRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSetColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, 
			String iAuthorName, String iCreateDate, String iDescription)
	{				
		String lProcedureString = null;
		
		lProcedureString = 								"SET ANSI_NULLS ON\n";
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
		lProcedureString = lProcedureString  + 			"\tUPDATE [" + iTableName + "]\n";
		lProcedureString = lProcedureString  + 			"\tSET\n";
		lProcedureString = lProcedureString  + 			iSetColumns + "\n";
		lProcedureString = lProcedureString  +			"\tWHERE [" + iPrimaryKeyName + "] = @" + iPrimaryKeyName + "\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
				
		return lProcedureString;
		
	}
	
	
	

	public String CreateInsertRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSelectColumns, String iInsertValues, String iInsertSelectNoPK, 
			String iInsertValuesNoPK, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription) {
	String lProcedureString = null;
		
		lProcedureString = 								"SET ANSI_NULLS ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"SET QUOTED_IDENTIFIER ON\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"-- Author:		 " + iAuthorName + "\n";
		lProcedureString = lProcedureString  + 			"-- Create date: " + iCreateDate + "\n";
		lProcedureString = lProcedureString  + 			"-- Description: " + iDescription + "\n";
		lProcedureString = lProcedureString  + 			"-- =============================================\n";
		lProcedureString = lProcedureString  + 			"CREATE PROCEDURE " + iTableName.toUpperCase() + "_INSERT_ROW_PRIMARY_KEY_OPTIONAL\n";
		lProcedureString = lProcedureString  +			iParameterColumns + "\n";
		lProcedureString = lProcedureString  +			"AS\n";
		lProcedureString = lProcedureString  + 			"BEGIN\n";
		lProcedureString = lProcedureString  + 			"SET NOCOUNT ON;\n";
		lProcedureString = lProcedureString  +			"IF (@" + iPrimaryKeyName + " IS NULL)\n";
		lProcedureString = lProcedureString  + 			"\tBEGIN\n";
		lProcedureString = lProcedureString  + 			"\tINSERT INTO [" + iTableName + "]\n";
		lProcedureString = lProcedureString  + 			"\t(\n";
		lProcedureString = lProcedureString  + 			iSelectColumns;
		lProcedureString = lProcedureString  +			"\t)\n";
		lProcedureString = lProcedureString  +			"\tVALUES\n";
		lProcedureString = lProcedureString  +			"\t(\n";
		lProcedureString = lProcedureString  +			iInsertValues;
		lProcedureString = lProcedureString  +			 "\t)\n";
		lProcedureString = lProcedureString  + 			"\tEND\n";
		lProcedureString = lProcedureString  +			"ELSE\n";
		lProcedureString = lProcedureString  + 			"\tBEGIN\n";
		lProcedureString = lProcedureString  + 			"\tINSERT INTO [" + iTableName + "]\n";
		lProcedureString = lProcedureString  + 			"\t(\n";
		lProcedureString = lProcedureString  + 			iInsertSelectNoPK;
		lProcedureString = lProcedureString  +			"\t)\n";
		lProcedureString = lProcedureString  +			"\tVALUES\n";
		lProcedureString = lProcedureString  +			"\t(\n";
		lProcedureString = lProcedureString  +			iInsertValuesNoPK;
		lProcedureString = lProcedureString  +			 "\t)\n";	
		lProcedureString = lProcedureString  + 			"\tEND\n";
		lProcedureString = lProcedureString  + 			"END\n";
		lProcedureString = lProcedureString  + 			"GO\n";
		lProcedureString = lProcedureString  + 			"\n";
				
		return lProcedureString;
	}

	

}
