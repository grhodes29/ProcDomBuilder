package sqlprocedurebuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import builder.MetaRow;

public class MSSQLProcBuilder extends ProcBuilder {
			
	// private members
	private List<String> _all_tables_list = new ArrayList<String>();
	private List<List<MetaRow>> _all_meta_lists_for_all_tables = new ArrayList<List<MetaRow>>();
		
	// static members
	private static String DbName = "NOP360DEV";
	public static String DbConnection = "jdbc:sqlserver://RHODES-PC\\RHODES_HOME_PC;database=" + MSSQLProcBuilder.DbName;
	public static String DbUser= "sa";
	public static String DbPassword="gianC07";
	private static String _getAllTablesSelect = "SELECT TABLE_NAME FROM " + MSSQLProcBuilder.DbName + ".INFORMATION_SCHEMA.Tables";
	private static String _getAllMetaColumnsSelectForTable = "SELECT c.name 'columnname', t.Name 'datatype', c.max_length 'maxlength', c.precision, c.scale, c.is_nullable 'isnullable', " +
     "ISNULL(i.is_primary_key, 0) 'primarykey' FROM sys.columns c INNER JOIN sys.types t ON c.user_type_id = t.user_type_id LEFT OUTER JOIN " +
     "sys.index_columns ic ON ic.object_id = c.object_id AND ic.column_id = c.column_id LEFT OUTER JOIN " +
     "sys.indexes i ON ic.object_id = i.object_id AND ic.index_id = i.index_id WHERE " +
     "c.object_id = OBJECT_ID('<TABLENAME>')";
	private static Connection _connection = null;
	
	
	// getters and setters
	/**
	 * @return the _all_meta_lists_for_all_tables
	 */
	public List<List<MetaRow>> get_all_meta_lists_for_all_tables() {
		return _all_meta_lists_for_all_tables;
	}

	/**
	 * @param _all_meta_lists_for_all_tables the _all_meta_lists_for_all_tables to set
	 */
	public void set_all_meta_lists_for_all_tables(List<List<MetaRow>> _all_meta_rows_list) {				
		this._all_meta_lists_for_all_tables = _all_meta_rows_list;		    	 
	}
		
				
	
	/**
	 * @return the _all_tables_list
	 */
	public List<String> get_all_tables_list() {
		return _all_tables_list;
	}

	/**
	 * @param _all_tables_list the _all_tables_list to set
	 */
	public void set_all_tables_list(List<String> _all_tables_list) {
		this._all_tables_list = _all_tables_list;
	}
	
	
	
			
	public MSSQLProcBuilder(){
		super();
		// any additional logic for default constructor
		ProcBuilder._dbconnection = MSSQLProcBuilder.DbConnection;
		ProcBuilder._dbusername = MSSQLProcBuilder.DbUser;
		ProcBuilder._dbuserpassword = MSSQLProcBuilder.DbPassword;

	}
		
	public MSSQLProcBuilder(String iDbConnection, String iDbUser, String iDbPassword){		
		super(iDbConnection, iDbUser, iDbPassword);
		
		MSSQLProcBuilder.dbConnect(ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);
				
		// set the tables list
		List<String> lListOfTables = new ArrayList<String>();		
		lListOfTables = this.GetAllTables(MSSQLProcBuilder._getAllTablesSelect);
		this.set_all_tables_list(lListOfTables);
		
		
		// set the meta lists
		List<List<MetaRow>> lListOfMetaLists = new ArrayList<List<MetaRow>>();
		lListOfMetaLists = this.GetListOfListsMetaRows(this.get_all_tables_list(), MSSQLProcBuilder._getAllMetaColumnsSelectForTable);
		this.set_all_meta_lists_for_all_tables(lListOfMetaLists);
		
		MSSQLProcBuilder.dbDisconnect();
					
	}
		
	public static void dbConnect(String db_connect_string, String db_userid, String db_password)		
	{    
		Connection lConnection = null;
		try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         lConnection = DriverManager.getConnection(db_connect_string,
                  db_userid, db_password);
         System.out.println("connected");
         
      } catch (Exception e) {
         e.printStackTrace();
      }      
	  MSSQLProcBuilder._connection = lConnection;
	}
	
	
	public static void dbDisconnect(){
		
		try {
			MSSQLProcBuilder._connection.close();
			System.out.println("disconnected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
			
	protected List<String> GetAllTables(String iSQLSelect)
	{				
		List<String> lAllTables = new ArrayList<String>();
		try{

			Statement lStatement = MSSQLProcBuilder._connection.createStatement();
			ResultSet lResultSet = lStatement.executeQuery(iSQLSelect);
			while (lResultSet.next()){
				lAllTables.add(lResultSet.getString(1));
			}

		} catch (Exception e){
			
		}
		return lAllTables;			
	}
	
	
	
	protected List<List<MetaRow>> GetListOfListsMetaRows(List<String> iTableList, String iMetaRowSQLSelect){
		
		List<List<MetaRow>> lListOfMetaLists = new ArrayList<List<MetaRow>>();
		
		 for (int tablecounter = 0; tablecounter < _all_tables_list.size();tablecounter++){
	    	  
	    	  String lSQLMetaColumnSelect = MSSQLProcBuilder._getAllMetaColumnsSelectForTable.replace("<TABLENAME>", _all_tables_list.get(tablecounter));
	    	  List<MetaRow> lMetaRowList = new ArrayList<MetaRow>();
	    	  lMetaRowList = this.GetAllMetaRowsForTable(lSQLMetaColumnSelect, _all_tables_list.get(tablecounter));
	    	  lListOfMetaLists.add(lMetaRowList);	    	  	  	    	
		 }		
			return lListOfMetaLists;		
	}
		
	
	protected List<MetaRow> GetAllMetaRowsForTable(String iMetaRowSQLSelect, String iParentTableName)	
	{						
		List<MetaRow> lMetaRowList = new ArrayList<MetaRow>();
		try{
			
			Statement lStatement = MSSQLProcBuilder._connection.createStatement();
			ResultSet lResultSet = lStatement.executeQuery(iMetaRowSQLSelect);
			while (lResultSet.next()){
				MetaRow lMetaRow = new MetaRow();
				lMetaRow.set_parent_table(iParentTableName);
				lMetaRow.set_name(lResultSet.getString("columnname"));
				lMetaRow.set_data_type(lResultSet.getString("datatype"));
				lMetaRow.set_max_length(lResultSet.getInt("maxlength"));
				lMetaRow.set_precision(lResultSet.getInt("precision"));
				lMetaRow.set_scale(lResultSet.getInt("scale"));
				lMetaRow.set_isnullable(lResultSet.getBoolean("isnullable"));
				lMetaRow.set_isprimarykey(lResultSet.getBoolean("primarykey"));
				lMetaRowList.add(lMetaRow);				
			}	
			
		} catch (Exception e){
						
		}
		return lMetaRowList;
	}
	
	
	
	public void PrintAllMetaRows(){
				
		List<List<MetaRow>> lListOfMetaRows = new ArrayList<List<MetaRow>>();
		lListOfMetaRows = this._all_meta_lists_for_all_tables;
		for(int listcounter = 0; listcounter < lListOfMetaRows.size(); listcounter++){	
			List<MetaRow> lMetaRows = new ArrayList<MetaRow>();
			lMetaRows = lListOfMetaRows.get(listcounter);
			for(int rowcounter = 0; rowcounter < lMetaRows.size(); rowcounter++){			 
				MSSQLProcBuilder.printMetaRow(lMetaRows.get(rowcounter));					
			}
		}
				
	}
		
	
	
	private static void printMetaRow(MetaRow iMetaRow) {
		
		  System.out.println("********************************************");
		  System.out.println("Parent Table: " + iMetaRow.get_parent_table());		  
		  System.out.println("Column Name: " + iMetaRow.get_name());
		  System.out.println("Data Type: " + iMetaRow.get_data_type());
		  System.out.println("Max Length: " + iMetaRow.get_max_length());
		  System.out.println("Precision: " + iMetaRow.get_precision());
		  System.out.println("Scale: " + iMetaRow.get_scale());
		  System.out.println("IsNullable: " + iMetaRow.get_isnullable());
		  System.out.println("IsPrimaryKey: " + iMetaRow.get_isprimarykey());
		  System.out.println("********************************************");
		  System.out.println("");		  		  
	}
	
	
	
	public void PrintTables(){
		
		System.out.println("********************************************");		
		List<String> lTableNames = new ArrayList<String>();
		lTableNames = this._all_tables_list;
		
		for(int rowcounter=0; rowcounter < lTableNames.size();rowcounter++){			
			System.out.println(lTableNames.get(rowcounter));			
		}					
		System.out.println("********************************************");	
		
	}
	
				
	


	
	
	
	/*
	public void CreateProcFile(String iPath, String iFileName){

		List<String> lProcedureList = new ArrayList<String>();
		lProcedureList.add(CreateSelectSingleProc());
		lProcedureList.add(CreateSelectAllProc());
		lProcedureList.add(CreateInsertProc());
		lProcedureList.add(CreateUpdateProc());
		lProcedureList.add(CreateDeleteProc());
		
		File lFile;
		PrintWriter writer;
		try {
			lFile = new File(iPath + "/" + iFileName);
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
	*/


	
		
}
