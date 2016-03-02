package sqlprocedurebuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.MetaRow;

public class MSSQLProcBuilder extends ProcBuilder {
			
	// private members
	//private List<String> _all_tables_list = new ArrayList<String>();
	//private List<List<MetaRow>> _all_meta_lists_for_all_tables = new ArrayList<List<MetaRow>>();
		
	// static members
	private static String _getAllTablesSelect = "SELECT TABLE_NAME FROM " + ProcBuilder._dbname + ".INFORMATION_SCHEMA.Tables";
	private static String _getAllMetaColumnsSelectForTable = "SELECT c.name 'columnname', t.Name 'datatype', c.max_length 'maxlength', c.precision, c.scale, c.is_nullable 'isnullable', " +
     "ISNULL(i.is_primary_key, 0) 'primarykey' FROM sys.columns c INNER JOIN sys.types t ON c.user_type_id = t.user_type_id LEFT OUTER JOIN " +
     "sys.index_columns ic ON ic.object_id = c.object_id AND ic.column_id = c.column_id LEFT OUTER JOIN " +
     "sys.indexes i ON ic.object_id = i.object_id AND ic.index_id = i.index_id WHERE " +
     "c.object_id = OBJECT_ID('<TABLENAME>') " +
     "GROUP BY c.name, t.Name, c.max_length, c.precision, c.scale, c.is_nullable, i.is_primary_key";
	private static Connection _connection = null;
	
	@Override
	public List<List<MetaRow>> get_all_meta_lists_for_all_tables() {
		return super._all_meta_lists_for_all_tables;
	}

	@Override
	public void set_all_meta_lists_for_all_tables(List<List<MetaRow>> _all_meta_rows_list) {				
		super._all_meta_lists_for_all_tables = _all_meta_rows_list;		    	 
	}
					
	
	@Override
	public List<String> get_all_tables_list() {
		return super._all_tables_list;
	}

	@Override
	public void set_all_tables_list(List<String> _all_tables_list) {
		super._all_tables_list = _all_tables_list;
	}
	
		
	public MSSQLProcBuilder(){
		super();
		// any additional logic for default constructor	
	}
		
	public MSSQLProcBuilder(String iDbName, String iDbConnection, String iDbUser, String iDbPassword){		
		super(iDbName, iDbConnection, iDbUser, iDbPassword);
		
		this.dbConnect(ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);
				
		// set the tables list
		List<String> lListOfTables = new ArrayList<String>();		
		lListOfTables = this.GetAllTables(MSSQLProcBuilder._getAllTablesSelect);
		this.set_all_tables_list(lListOfTables);
		
		
		// set the meta lists
		List<List<MetaRow>> lListOfMetaLists = new ArrayList<List<MetaRow>>();
		lListOfMetaLists = this.GetListOfListsMetaRows(this.get_all_tables_list(), MSSQLProcBuilder._getAllMetaColumnsSelectForTable);
		this.set_all_meta_lists_for_all_tables(lListOfMetaLists);
		
		this.dbDisconnect();
					
	}
	
	@Override
	public void dbConnect(String db_connect_string, String db_userid, String db_password)		
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
	
	@Override
	public void dbDisconnect(){
		
		try {
			MSSQLProcBuilder._connection.close();
			System.out.println("disconnected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override		
	public List<String> GetAllTables(String iSQLSelect)
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
	
	
	@Override
	public List<List<MetaRow>> GetListOfListsMetaRows(List<String> iTableList, String iMetaRowSQLSelect){
		
		List<List<MetaRow>> lListOfMetaLists = new ArrayList<List<MetaRow>>();
		
		 for (int tablecounter = 0; tablecounter < _all_tables_list.size();tablecounter++){
	    	  
	    	  String lSQLMetaColumnSelect = MSSQLProcBuilder._getAllMetaColumnsSelectForTable.replace("<TABLENAME>", _all_tables_list.get(tablecounter));
	    	  List<MetaRow> lMetaRowList = new ArrayList<MetaRow>();
	    	  lMetaRowList = this.GetAllMetaRowsForTable(lSQLMetaColumnSelect, _all_tables_list.get(tablecounter));
	    	  lListOfMetaLists.add(lMetaRowList);	    	  	  	    	
		 }		
			return lListOfMetaLists;		
	}
		
	@Override
	public List<MetaRow> GetAllMetaRowsForTable(String iMetaRowSQLSelect, String iParentTableName)	
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
	
	
		
}
