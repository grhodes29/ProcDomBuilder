package sqlprocedurebuilder;

import java.sql.Connection;
import java.util.List;
import builder.MetaRow;

abstract public class ProcBuilder {
	
	
	private String _dbusername;
	private String _dbuserpassword;	 
	private String _dbconnection;
	
	
	public String GetDbUserName(){		  
		  return _dbusername;
  	}
	
	public void SetDbUserName(String value){		  
		  _dbusername = value;		  
	}
	  
	public String GetDbUserPassword(){
		  return _dbuserpassword;		  
	}
	
	public void SetDbUserPassword(String value){
		  _dbuserpassword = value;
	}
	  
	public String GetDbConnection(){
		  return _dbconnection;		  
	}
	  
	public void SetDbConnection(String value){
		  _dbconnection = value;
	}
	
	
	public ProcBuilder(){
		
	}
	
	public ProcBuilder(String iDbConnection, String iDbUser, String iDbPassword){
		this._dbconnection = iDbConnection;
		this._dbusername = iDbUser;
		this._dbuserpassword = iDbPassword;
	}
	
	abstract public Connection dbConnect(String db_connect_string, String db_userid, String db_password);
	abstract public List<String> GetAllTables(String iSQLSelect);
	abstract public List<MetaRow> GetAllMetaRowsForTable(String iSQLSelect, String iParentTableName);	

	
	
}
