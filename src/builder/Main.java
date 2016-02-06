package builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sqlprocedurebuilder.MSSQLProcBuilder;


public class Main {
	
	
	private enum DomainObjectLanguage{
		VISUALBASIC,
		CSHARP,
		JAVA			
	}
	
	private enum DatabaseServerType{
		MICROSOFTSQLSERVER,
		ORACLE,
		MYSQL		
	}
	
	
	private enum ProcedureBuildType{
		ONEFILEALLTABLES,
		ONEFILEPERTABLE
	}
	
	  
	public static void main(String[] args) {
		
	
		
	      
	    MSSQLProcBuilder lMSSQLProcBuilder = new MSSQLProcBuilder(MSSQLProcBuilder.DbConnection, MSSQLProcBuilder.DbUser, MSSQLProcBuilder.DbPassword);	    	    	        
	   // lMSSQLProcBuilder.PrintTables();
	    lMSSQLProcBuilder.PrintAllMetaRows();
	
	    
	      
	      // x.CreateProcFile("C:/Temp", "test.sql");
	     // List<String> lAllTables = new ArrayList<String>();
	      
	      //lAllTables = lMSSQLProcBuilder.GetAllTables(lMSSQLProcBuilder.get_all_tables_sql());
	      
	      //lAllTables = lMSSQLProcBuilder.get_all_tables_list();
	      //System.out.println("Get all tables in list<string>");
	      
	    
	    /*
	      List<MetaRow> lMetaRowList = new ArrayList<MetaRow>(); 
	      
	      for (int tablecounter = 0; tablecounter < lAllTables.size();tablecounter++){
	    	  
	    	  System.out.println("Working on table: " + lAllTables.get(tablecounter));
	    	  String lSQLMetaColumnSelect = lGetAllMetaColumnsForTable.replace("TABLENAME", lAllTables.get(tablecounter));
	    	  //System.out.println(lSQLMetaColumnSelect);
	    	  lMetaRowList = lMSSQLProcBuilder.GetAllMetaRowsForTable(lSQLMetaColumnSelect, lAllTables.get(tablecounter));
	    	  
	    	  
	    
	    
	    	 
	    	 
	    	 
	    	 // update specific arguments
	    	 String lUpdateParameterColumns = "@aaa int,\n@bbb nvarchar(20)\n";
	    	 String lUpdateSetColumns = "aaa=@aaa,\nbbb=@bbb\n";
	    	 
	    	 
	    
	    	  for (int rowcounter = 0; rowcounter < lMetaRowList.size(); rowcounter++){
	    		  MetaRow lMetaRow = new MetaRow();
	    		  lMetaRow = lMetaRowList.get(rowcounter);
	    		 // MSSQLProcBuilder.printMetaRow(lMetaRow);
	    		  
	    		  if (lMetaRow.get_isprimarykey() == true)
	    		  {	    			  
	    			  lPrimaryKeyName = lMetaRow.get_name();
	    			  lPrimaryKeyDataType = lMetaRow.get_data_type();
	    			  lTableName = lMetaRow.get_parent_table();
	    		  }
	    		  	    		 	    		  
	    		  if (rowcounter  <  lMetaRowList.size() - 1){	    			  
	    			  lSelectColumns =  lSelectColumns + "\t\t" + lMetaRow.get_name() + ",\n"; 
	    		  }
    			  else{    				  
    				  lSelectColumns =  lSelectColumns + "\t\t" +lMetaRow.get_name() + "\n"; 
	    		  }	    		  
	    	  }
	    	  
	    	
	    	  */
	    	  
	    	 
	    	
	    	//  String lCreateSelectAllRowsProcedure = lMSSQLProcBuilder.CreateSelectAllRowsProcedure(lDatabaseName, lTableName, lColumns, lAuthorName, lCreateDate, "Select All Rows From Table");	    	  
	    	//  System.out.print(lCreateSelectAllRowsProcedure); 
	    	  
	    	  
	    	//  String lCreateSelectRowProcedure = lMSSQLProcBuilder.CreateSelectRowProcedure(lDatabaseName, lTableName, lColumns, lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Select One Row From Table Based on PK");
	    	//  System.out.print(lCreateSelectRowProcedure);    
	    	  
	    	//  String lCreateDeleteRowProcedure = lMSSQLProcBuilder.CreateDeleteRowProcedure(lDatabaseName, lTableName, lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Delete One Row From Table Based on PK");  
	    	//  System.out.print(lCreateDeleteRowProcedure);
	    
	    
	    
		 		// get all variables that are needed for the create select all
   	 		//	String lDatabaseName = "NOP360DEV";
   	 		//	String lTableName = "";
   	 		//	String lSelectColumns = "";
   	 		//	String lPrimaryKeyName = "";
   	 		//	String lPrimaryKeyDataType = "";
   	 		//	String lAuthorName = "Giancarlo Rhodes";
   	 		//	Date lDate = new Date();
   	 		//    String lCreateDate = lDate.toString();
	    	  
	    	  
	    	//  String lCreateUpdateRowProcedure = lMSSQLProcBuilder.CreateUpdateRowProcedure(lDatabaseName, lTableName, lUpdateParameterColumns, lUpdateSetColumns, lPrimaryKeyName, lPrimaryKeyDataType, lAuthorName, lCreateDate, "Update One Row, All Columns From Table Based on PK");  
	    	//  System.out.print(lCreateUpdateRowProcedure);
	    	  
	    	  
	      }
	      
	      

	}




	
	  


