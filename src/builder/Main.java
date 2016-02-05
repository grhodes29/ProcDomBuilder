package builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	
	  
	public static void main(String[] args) {
		
		String lDbConnection = "jdbc:sqlserver://RHODES-PC\\RHODES_HOME_PC;database=NOP360DEV";
		String lDbUser= "sa";
		String lDbPassword="gianC07";
		String lGetAllTablesSelect = "SELECT TABLE_NAME FROM NOP360DEV.INFORMATION_SCHEMA.Tables";
		String lGetAllMetaColumnsForTable ="SELECT c.name 'columnname', t.Name 'datatype', c.max_length 'maxlength', c.precision, c.scale, c.is_nullable 'isnullable', " +
	     "ISNULL(i.is_primary_key, 0) 'primarykey' FROM sys.columns c INNER JOIN sys.types t ON c.user_type_id = t.user_type_id LEFT OUTER JOIN " +
	     "sys.index_columns ic ON ic.object_id = c.object_id AND ic.column_id = c.column_id LEFT OUTER JOIN " +
	     "sys.indexes i ON ic.object_id = i.object_id AND ic.index_id = i.index_id WHERE " +
	     "c.object_id = OBJECT_ID('TABLENAME')";
		
	      
	    MSSQLProcBuilder lMSSQLProcBuilder = new MSSQLProcBuilder(lDbConnection,lDbUser, lDbPassword, lGetAllTablesSelect);
	      
	    //   x.CreateProcFile("C:/Temp", "test.sql");
	      List<String> lAllTables = new ArrayList<String>();
	      
	      lAllTables = lMSSQLProcBuilder.GetAllTables(lMSSQLProcBuilder.get_all_tables_sql());
	      System.out.println("Get all tables in list<string>");
	      	      	            
	      List<MetaRow> lMetaRowList = new ArrayList<MetaRow>(); 
	      
	      for (int tablecounter = 0; tablecounter < lAllTables.size();tablecounter++){
	    	  
	    	  System.out.println("Working on table: " + lAllTables.get(tablecounter));
	    	  String lSQLMetaColumnSelect = lGetAllMetaColumnsForTable.replace("TABLENAME", lAllTables.get(tablecounter));
	    	  //System.out.println(lSQLMetaColumnSelect);
	    	  lMetaRowList = lMSSQLProcBuilder.GetAllMetaRowsForTable(lSQLMetaColumnSelect, lAllTables.get(tablecounter));
	    	  
	    	  //TODO - iterate thru the columns 
	    	  for (int rowcounter = 0; rowcounter < lMetaRowList.size();rowcounter++){
	    		  MetaRow lMetaRow = new MetaRow();
	    		  lMetaRow = lMetaRowList.get(rowcounter);
	    		  MSSQLProcBuilder.printMetaRow(lMetaRow);
	    	  }
	    	      	  
	      }
	      
	      

	}




	
	  

}
