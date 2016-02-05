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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import builder.MetaRow;

public class MSSQLProcBuilder extends ProcBuilder {
	
	
	private String _all_tables_sql;
	private String _all_columns_sql;
	
	
	/**
	 * @return the _all_tables_sql
	 */
	public String get_all_tables_sql() {
		return _all_tables_sql;
	}

	/**
	 * @param _all_tables_sql the _all_tables_sql to set
	 */
	public void set_all_tables_sql(String _all_tables_sql) {
		this._all_tables_sql = _all_tables_sql;
	}
	
	
	/**
	 * @return the _all_columns_sql
	 */
	public String get_all_columns_sql() {
		return _all_columns_sql;
	}

	/**
	 * @param _all_columns_sql the _all_columns_sql to set
	 */
	public void set_all_columns_sql(String _all_columns_sql) {
		this._all_columns_sql = _all_columns_sql;
	}
			
	public MSSQLProcBuilder(){
		super();
		// any additional logic for default constructor
	}
		
	public MSSQLProcBuilder(String iDbConnection, String iDbUser, String iDbPassword, String iSQLSelectAllTables){		
		super(iDbConnection, iDbUser, iDbPassword);
		this._all_tables_sql = iSQLSelectAllTables;
	}
	
	
	
		
	public Connection dbConnect(String db_connect_string, String db_userid, String db_password)		
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
	return lConnection;
	}
	
	
	
	
	public List<String> GetAllTables(String iSQLSelect)
	{				
		List<String> lAllTables = new ArrayList<String>();
		try{
			Connection lConnection = this.dbConnect(super.GetDbConnection(), super.GetDbUserName(), super.GetDbUserPassword());
			Statement lStatement = lConnection.createStatement();
			ResultSet lResultSet = lStatement.executeQuery(iSQLSelect);
			while (lResultSet.next()){
				lAllTables.add(lResultSet.getString(1));
			}
			
		} catch (Exception e){
			
		}
		return lAllTables;
	}
		
	
	public List<MetaRow> GetAllMetaRowsForTable(String iSQLSelect, String iParentTableName)	
	{						
		List<MetaRow> lMetaRowList = new ArrayList<MetaRow>();
		try{
			Connection lConnection = this.dbConnect(super.GetDbConnection(), super.GetDbUserName(), super.GetDbUserPassword());
			Statement lStatement = lConnection.createStatement();
			ResultSet lResultSet = lStatement.executeQuery(iSQLSelect);
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
		
	
	
	public static void printMetaRow(MetaRow iMetaRow) {
		
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
	
				
	public String CreateSelectSingleProc(){
		String lProcedureString = "CreateSelectSingleProc";
		return lProcedureString;
	}
	
	public String CreateSelectAllProc(){
		String lProcedureString = "CreateSelectAllProc";
		return lProcedureString;
	}
	
	public String CreateInsertProc(){
		String lProcedureString = "CreateInsertProc";
		return lProcedureString;
	}
		
	public String CreateUpdateProc(){
		String lProcedureString = "CreateUpdateProc";
		return lProcedureString;
	}
	
	private String CreateDeleteProc(){		
		String lProcedureString = "CreateDeleteProc";
		return lProcedureString;
	}
	
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


	
		
}
