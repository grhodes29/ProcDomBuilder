package sqlprocedurebuilder;

import java.util.List;
import builder.MetaRow;

abstract public class ProcBuilder {
		
	public static String _dbconnection;
	public static String _dbusername;
	public static String _dbuserpassword;
	
	public ProcBuilder(){
		ProcBuilder._dbconnection = "";
		ProcBuilder._dbusername = "";
		ProcBuilder._dbuserpassword = "";
	}
	
	public ProcBuilder(String iDbConnection, String iDbUser, String iDbPassword){
		ProcBuilder._dbconnection = iDbConnection;
		ProcBuilder._dbusername = iDbUser;
		ProcBuilder._dbuserpassword = iDbPassword;
	}
	
	//abstract protected  Connection dbConnect(String db_connect_string, String db_userid, String db_password);
	abstract protected List<String> GetAllTables(String iSQLSelect);
	abstract protected List<MetaRow> GetAllMetaRowsForTable(String iSQLSelect, String iParentTableName);
	abstract protected List<List<MetaRow>> GetListOfListsMetaRows(List<String> iTableList, String iMetaRowSQLSelect);
	
	
	
}
