package sqlprocedurebuilder;

import java.util.List;
import common.MetaRow;

public interface IProcBuilder {
						
	public void dbConnect(String db_connect_string, String db_userid, String db_password);	
	public void dbDisconnect();			
	public List<String> GetAllTables(String iSQLSelect);	
	public List<List<MetaRow>> GetListOfListsMetaRows(List<String> iTableList, String iMetaRowSQLSelect);
	public List<MetaRow> GetAllMetaRowsForTable(String iMetaRowSQLSelect, String iParentTableName);			
	public void PrintAllMetaRows();	
	public void printMetaRow(MetaRow iMetaRow);
	public void PrintTables();
	public List<List<MetaRow>> get_all_meta_lists_for_all_tables();
	public void set_all_meta_lists_for_all_tables(List<List<MetaRow>> _all_meta_rows_list);
	public List<String> get_all_tables_list();
	public void set_all_tables_list(List<String> _all_tables_list);
	
	
}
