package sqlprocedurebuilder;

import java.util.ArrayList;
import java.util.List;

import common.MetaRow;

abstract public class ProcBuilder implements IProcBuilder  {
		
	public static String _dbname;
	public static String _dbconnection;
	public static String _dbusername;
	public static String _dbuserpassword;		
	protected List<String> _all_tables_list = new ArrayList<String>();
	protected List<List<MetaRow>> _all_meta_lists_for_all_tables = new ArrayList<List<MetaRow>>();
	
	public ProcBuilder(){
		
		ProcBuilder._dbname = "";
		ProcBuilder._dbconnection = "";
		ProcBuilder._dbusername = "";
		ProcBuilder._dbuserpassword = "";
	}
	
	public ProcBuilder(String iDbName, String iDbConnection, String iDbUser, String iDbPassword){
		
		ProcBuilder._dbname = iDbName;
		ProcBuilder._dbconnection = iDbConnection;
		ProcBuilder._dbusername = iDbUser;
		ProcBuilder._dbuserpassword = iDbPassword;
	}
			
	public void PrintAllMetaRows(){
		
		List<List<MetaRow>> lListOfMetaRows = new ArrayList<List<MetaRow>>();
		lListOfMetaRows = this._all_meta_lists_for_all_tables;
		for(int listcounter = 0; listcounter < lListOfMetaRows.size(); listcounter++){	
			List<MetaRow> lMetaRows = new ArrayList<MetaRow>();
			lMetaRows = lListOfMetaRows.get(listcounter);
			for(int rowcounter = 0; rowcounter < lMetaRows.size(); rowcounter++){			 
				this.printMetaRow(lMetaRows.get(rowcounter));					
			}
		}
				
	}
		
	public void printMetaRow(MetaRow iMetaRow) {
		
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
	
	
	
}
