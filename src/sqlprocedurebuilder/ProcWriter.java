package sqlprocedurebuilder;

abstract public class ProcWriter {
	
	protected String SQLSelectRowProcedure;
	protected String SQLSelectAllRowsProcedure;
	protected String SQLDeleteRowProcedure;
	protected String SQLUpdateRowProcedure;
	protected String SQLInsertRowProcedure;
	
		
	abstract public String CreateSelectRowProcedure(String iDatabaseName, String iTableName, String iSelectColumns, String iPrimaryKeyName, String iPrimaryKeyType, String iAuthorName, String iCreateDate, String iDescription);
	abstract public String CreateSelectAllRowsProcedure(String iDatabaseName, String iTableName, String iSelectColumns, String iAuthorName, String iCreateDate, String iDescription);
	abstract public String CreateDeleteRowProcedure(String iDatabaseName, String iTableName, String iPrimaryKeyName, String iPrimaryKeyType, String iAuthorName, String iCreateDate, String iDescription);
	abstract public String CreateUpdateRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSetColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription);
	abstract public String CreateInsertRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSetColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription);

}
