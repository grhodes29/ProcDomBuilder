package domainclassbuilder;

import java.util.List;
import common.MetaRow;

public interface IClassWriter {
					
	public String get_ClassBegin();
	public void set_ClassBegin(String _ClassBegin);
	public String get_ClassFields();
	public void set_ClassFields(String _ClassFields);
	public String get_ClassGettersSetters();
	public void set_ClassGettersSetters(String _ClassGettersSetters);
	public String get_ClassConstructors();
	public void set_ClassConstructors(String _ClassConstructors);
	public String get_ClassEnd();
	public void set_ClassEnd(String _ClassEnd);
	public String CreateClass(String iClassName, String iAuthorName, String iDescription, List<MetaRow> iFieldList);
	public void CreateClassBegin(String iClassName, String iAuthorName, String iCreateDate, String iDescription);
	public void CreateClassFields(List<MetaRow> iFieldList);
	public void CreateClassGettersSetters(List<MetaRow> iFieldList);
	public void CreateClassConstructor(List<MetaRow> iFieldList);
	public void CreateClassEnd();
	public void BuildAllClasses();
	public void ClassFileWriter(String iClassDirectoryLocation, String iClass, String iClassName);


}
