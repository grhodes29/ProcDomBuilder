package domainclassbuilder;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.DatabaseServerType;
import common.MetaRow;
import sqlprocedurebuilder.MSSQLProcBuilder;
import sqlprocedurebuilder.ProcBuilder;

public class JavaClassWriter implements IClassWriter {
		
	// private members
	private String _classBegin;
	private String _classFields;
	private String _classGettersSetters;
	private String _classConstructors;
	private String _classEnd;
	private MSSQLProcBuilder _MSSQLProcBuilder = new MSSQLProcBuilder();
	private String _AbsoluteClassDirectoryLocation;
	private String _author = "";
	private DatabaseServerType _dbservertype;


public JavaClassWriter(String iAbsoluteClassDirectoryLocation, ProcBuilder lProcBuilder, String iAuthorName, DatabaseServerType lDatabaseServerType){
		
		this._MSSQLProcBuilder = (MSSQLProcBuilder)lProcBuilder;
		this._AbsoluteClassDirectoryLocation = iAbsoluteClassDirectoryLocation;		
		this._dbservertype = lDatabaseServerType;
	}

@Override
public String CreateClass(String iClassName, String iAuthorName, String iDescription, List<MetaRow> iFieldList){
	String lReturn = "";
	Date lDate = new Date();
	
	this.CreateClassBegin(iClassName, iAuthorName, lDate.toString(), iDescription);
	this.CreateClassFields(iFieldList);
	this.CreateClassGettersSetters(iFieldList);
	this.CreateClassConstructor(iFieldList);
	this.CreateClassEnd();	
	
	lReturn = this.get_ClassBegin() + this.get_ClassFields() + this.get_ClassGettersSetters() +
			this.get_ClassConstructors() + this.get_ClassEnd();

	return lReturn;

}

@Override
public void CreateClassBegin(String iClassName, String iAuthorName, String iCreateDate, String iDescription){
	String lClassBegin = "\n\n";
	lClassBegin += "import java.util.Date;\n\n";
	
	lClassBegin += 							"// =============================================\n";
	lClassBegin = lClassBegin  + 			"// Author:		 " + iAuthorName + "\n";
	lClassBegin = lClassBegin  + 			"// Create date: " + iCreateDate + "\n";
	lClassBegin = lClassBegin  + 			"// Description: " + iDescription + "\n";
	lClassBegin = lClassBegin  + 			"// =============================================\n";
	lClassBegin = lClassBegin  + 			"\n";
	lClassBegin = lClassBegin  + 			"\n";
	lClassBegin = lClassBegin  + 			"public class " + iClassName + " {\n\n";
	
	this.set_ClassBegin(lClassBegin);

}

@Override
public void CreateClassFields(List<MetaRow> iFieldList){
	
	String lClassFields = "";	

	for (int rowcounter = 0; rowcounter < iFieldList.size(); rowcounter++){
		
		String lDataMemberName = "";
		String lDataMemberType =  "";
		MetaRow lMetaRow = new MetaRow();
		lMetaRow = iFieldList.get(rowcounter);
		lDataMemberName = lMetaRow.get_name();
		
		if (this._dbservertype == DatabaseServerType.MICROSOFTSQLSERVER){
			lDataMemberType = this.MSSQLServerDbTypeToJavaType(lMetaRow.get_data_type());
		} else if (this._dbservertype == DatabaseServerType.ORACLE) {
			
		} else if (this._dbservertype == DatabaseServerType.MYSQL){
		
		}
		
		
		lClassFields = lClassFields + "private " + lDataMemberType + " _" + lDataMemberName + ";\n";
		
	}
	
	lClassFields = lClassFields + "\n\n";
	this.set_ClassFields(lClassFields);
		
}

@Override	
public void CreateClassGettersSetters(List<MetaRow> iFieldList){
		String lGettersSetters = "";	
		
	
		for (int rowcounter = 0; rowcounter < iFieldList.size(); rowcounter++){
			
			String lDataMemberName = "";
			String lDataMemberType =  "";
			MetaRow lMetaRow = new MetaRow();
			lMetaRow = iFieldList.get(rowcounter);
			lDataMemberName = lMetaRow.get_name();
			
			if (this._dbservertype == DatabaseServerType.MICROSOFTSQLSERVER){
				lDataMemberType = this.MSSQLServerDbTypeToJavaType(lMetaRow.get_data_type());
			} else if (this._dbservertype == DatabaseServerType.ORACLE) {
				
			} else if (this._dbservertype == DatabaseServerType.MYSQL){
			
			}
			
			
			lGettersSetters = lGettersSetters + "public " + lDataMemberType + " get_" + lDataMemberName + "() {\n";
			lGettersSetters = lGettersSetters + "\treturn _" + lDataMemberName + ";\n";
			lGettersSetters = lGettersSetters +	"}\n\n";
			lGettersSetters = lGettersSetters + "public void set_" + lDataMemberName + "(" + lDataMemberType + " i" + lDataMemberName + "){\n";
			lGettersSetters = lGettersSetters + "\tthis._" + lDataMemberName + " = " + "i" + lDataMemberName + ";\n";
			lGettersSetters = lGettersSetters + "}\n\n";
		}
			
		lGettersSetters = lGettersSetters + "\n\n";
		this.set_ClassGettersSetters(lGettersSetters);		
}

@Override
public void CreateClassConstructor(List<MetaRow> iFieldList) {

	String lConstructor = "";
	// default
	lConstructor = "public " + iFieldList.get(0).get_parent_table() + "(){}\n\n";
	// constructor with parameters
	lConstructor = lConstructor + "public " + iFieldList.get(0).get_parent_table() + "(";
	String lParameters = "";
	
	for (int rowcounter = 0; rowcounter < iFieldList.size(); rowcounter++){
		
		String lDataMemberName = "";
		String lDataMemberType =  "";
		MetaRow lMetaRow = new MetaRow();
		lMetaRow = iFieldList.get(rowcounter);
		lDataMemberName = lMetaRow.get_name();
		
		if (this._dbservertype == DatabaseServerType.MICROSOFTSQLSERVER){
			lDataMemberType = this.MSSQLServerDbTypeToJavaType(lMetaRow.get_data_type());
		} else if (this._dbservertype == DatabaseServerType.ORACLE) {
			
		} else if (this._dbservertype == DatabaseServerType.MYSQL){
		
		}
		
		
		
		if (rowcounter < iFieldList.size() - 1){
		lParameters = lParameters + lDataMemberType + " i" + lDataMemberName + ", ";
		}
		else{			
			lParameters = lParameters + lDataMemberType + " i" + lDataMemberName + ")\n";
		}
		
		
	}
	
	lConstructor = lConstructor + lParameters;
	lConstructor = lConstructor + "{\n\n";	
	String lThis = "";
	
	for (int rowcounter =0; rowcounter < iFieldList.size(); rowcounter ++){
		
		String lDataMemberName = "";
		MetaRow lMetaRow = new MetaRow();
		lMetaRow = iFieldList.get(rowcounter);
		lDataMemberName = lMetaRow.get_name();
		lThis = lThis + "this._" + lDataMemberName + " = i" + lDataMemberName + ";\n";
		
	
	}
			
	lConstructor = lConstructor + lThis + "\n";
	lConstructor = lConstructor + "}\n\n"; 
	
	
	this.set_ClassConstructors(lConstructor);
				
}


@Override
public void CreateClassEnd(){
	String lClassEnd = "";
	lClassEnd = "}\n\n";	
	this.set_ClassEnd(lClassEnd);
	
}




@Override
public void BuildAllClasses(){
	
	List<List<MetaRow>> lListOfMetaRowLists = new ArrayList<List<MetaRow>>();
	lListOfMetaRowLists = _MSSQLProcBuilder.get_all_meta_lists_for_all_tables();
	String lClassName = "";
	String lClass = "";
	String lAuthorName = this._author;
	String lDescription = "";
							
	  for (int listcounter = 0; listcounter < lListOfMetaRowLists.size(); listcounter++){
		  
		  List<MetaRow> lListOfMetaRows = new ArrayList<MetaRow>();
		  lListOfMetaRows = lListOfMetaRowLists.get(listcounter);		 		  			  		
		  lClassName = lListOfMetaRows.get(0).get_parent_table();
		  lClass = this.CreateClass(lClassName, lAuthorName, lDescription, lListOfMetaRows);
		  this.ClassFileWriter(this._AbsoluteClassDirectoryLocation, lClass, lClassName);
		  
	  }
}

@Override
public void ClassFileWriter(String iClassDirectoryLocation, String iClass, String iClassName)
{
	
	File lFile;
	PrintWriter writer;
	try {
		lFile = new File(iClassDirectoryLocation + "/" + iClassName + ".java");
		lFile.getParentFile().mkdirs();		
		writer = new PrintWriter(lFile);						
		writer.print(iClass);									
		writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}



private String MSSQLServerDbTypeToJavaType(String iDataType){
	String lReturn = "";
	
	switch (iDataType){
	case "int":
		lReturn = "int";
		break;
	case "nvarchar":
		lReturn = "String";
		break;
	case "varchar":
		lReturn = "String";
		break;
	case "decimal":
		lReturn = "double";
		break;
	case "datetime":
		lReturn = "Date";
		break;
	case "bit":
		lReturn = "Boolean";
		break;
	default:
		lReturn = "Unknown";
		break;		
	}
	
	return lReturn;
}


@Override
public String get_ClassBegin() {
	return this._classBegin;
}


@Override
public void set_ClassBegin(String _ClassBegin) {
	this._classBegin = _ClassBegin;	
}


@Override
public String get_ClassFields() {
	return this._classFields;
}


@Override
public void set_ClassFields(String _ClassFields) {
	this._classFields = _ClassFields;	
}


@Override
public String get_ClassGettersSetters() {
	return this._classGettersSetters;
}


@Override
public void set_ClassGettersSetters(String _ClassGettersSetters) {
	this._classGettersSetters = _ClassGettersSetters;	
}


@Override
public String get_ClassConstructors() {
	return this._classConstructors;
}


@Override
public void set_ClassConstructors(String _ClassConstructors) {
	this._classConstructors = _ClassConstructors;	
}


@Override
public String get_ClassEnd() {
	return this._classEnd;
}


@Override
public void set_ClassEnd(String _ClassEnd) {
	this._classEnd = _ClassEnd;
	
}


}
