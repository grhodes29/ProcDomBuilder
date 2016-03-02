package sqlprocedurebuilder;

abstract public class ProcWriter {
	
	protected String SQLSelectRowProcedure;
	protected String SQLSelectAllRowsProcedure;
	protected String SQLDeleteRowProcedure;
	protected String SQLUpdateRowProcedure;
	protected String SQLInsertRowProcedure;
		
	public ProcWriter(){}
	
	public ProcWriter(String iSQLSelectRowProcedure, String iSQLSelectAllRowsProcedure, String iSQLDeleteRowProcedure, String iSQLUpdateRowProcedure, String iSQLInsertRowProcedure){
		
		this.SQLSelectRowProcedure = iSQLSelectRowProcedure;
		this.SQLSelectAllRowsProcedure = iSQLSelectAllRowsProcedure;
		this.SQLDeleteRowProcedure = iSQLDeleteRowProcedure;
		this.SQLUpdateRowProcedure = iSQLUpdateRowProcedure;
		this.SQLInsertRowProcedure = iSQLInsertRowProcedure;
	}
			
	abstract protected String CreateSelectRowProcedure(String iDatabaseName, String iTableName, String iSelectColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription);
	abstract protected String CreateSelectAllRowsProcedure(String iDatabaseName, String iTableName, String iSelectColumns, String iAuthorName, String iCreateDate, String iDescription);
	abstract protected String CreateDeleteRowProcedure(String iDatabaseName, String iTableName, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription);
	abstract protected String CreateUpdateRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSetColumns, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription);
	abstract protected String CreateInsertRowProcedure(String iDatabaseName, String iTableName, String iParameterColumns, String iSelectColumns, String iInsertValues, String iInsertSelectNoPK, 
			String iInsertValuesNoPK, String iPrimaryKeyName, String iPrimaryKeyDataType, String iAuthorName, String iCreateDate, String iDescription);

}
