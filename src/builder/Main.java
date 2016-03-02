package builder;

import common.DatabaseServerType;
import common.DomainObjectLanguage;
import common.ProcedureBuildType;
import domainclassbuilder.CSharpClassWriter;
import domainclassbuilder.JavaClassWriter;
import domainclassbuilder.VisualBasicClassWriter;
import sqlprocedurebuilder.MSSQLProcBuilder;
import sqlprocedurebuilder.MSSQLProcWriter;
import sqlprocedurebuilder.ProcBuilder;


public class Main {
	
	  
	public static void main(String[] args) {
		
		DatabaseServerType lDatabaseServerType = DatabaseServerType.MICROSOFTSQLSERVER;
		ProcedureBuildType lProcedureBuildType = ProcedureBuildType.ONEFILEALLTABLES;
		DomainObjectLanguage lDomainObjLang = DomainObjectLanguage.VISUALBASIC;
				
		String lAbsoluteProcedureDirectoryLocation = "C:/Build/Procedures";
		String lAbsoluteClassDirectoryLocation = "C:/Build/Classes";
		String lDbName = "NOP360DEV";		
		String lDbConnection = "jdbc:sqlserver://RHODES-PC\\RHODES_HOME_PC;database=NOP360DEV";		
		String lDbUser = "sa";		
		String lDbPassword = "gianC07";
		String lAuthor = "Giancarlo Rhodes";
		String lOneFileName = "AllProcedures.SQL";
		
		ProcBuilder lProcBuilder = null; 
				
		switch (lDatabaseServerType){
		
		case MICROSOFTSQLSERVER:
									
			ProcBuilder._dbname = lDbName;
			ProcBuilder._dbconnection = lDbConnection;
			ProcBuilder._dbusername = lDbUser;
			ProcBuilder._dbuserpassword = lDbPassword;
			
		    //MSSQLProcBuilder lMSSQLProcBuilder = new MSSQLProcBuilder(ProcBuilder._dbname, ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);	    	    	        
			lProcBuilder =  new MSSQLProcBuilder(ProcBuilder._dbname, ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);
		    
		    // lMSSQLProcBuilder.PrintTables();   // TESTING
			// lMSSQLProcBuilder.PrintAllMetaRows();  //  TESTING
			MSSQLProcWriter lMSSQLProcWriter = new MSSQLProcWriter(lAbsoluteProcedureDirectoryLocation, lProcBuilder);
			lMSSQLProcWriter.Write(lProcedureBuildType, lAuthor, lOneFileName);
			break;
			
		case ORACLE:						
			// TODO - IMPLEMENT
		    break;
						
		case MYSQL:
			
			// TODO - IMPLEMENT
			break;
			
		default:
			break;
				
		}
		 
		
		switch (lDomainObjLang){
						
		case VISUALBASIC:
				
			//if (lDatabaseServerType == DatabaseServerType.MICROSOFTSQLSERVER){
			//	lProcBuilder = new MSSQLProcBuilder(ProcBuilder._dbname, ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);	
			//}else if(lDatabaseServerType == DatabaseServerType.ORACLE){
			//	// TODO - IMPLEMENT
			//}else if(lDatabaseServerType == DatabaseServerType.MYSQL){
			//	// TODO - IMPLEMENT
			//}
			VisualBasicClassWriter lVBClassWriter = new VisualBasicClassWriter(lAbsoluteClassDirectoryLocation, lProcBuilder, lAuthor, lDatabaseServerType);				
			lVBClassWriter.BuildAllClasses();
			break;
					
		case CSHARP:
			
			if (lDatabaseServerType == DatabaseServerType.MICROSOFTSQLSERVER){
				lProcBuilder = new MSSQLProcBuilder(ProcBuilder._dbname, ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);	
			}else if(lDatabaseServerType == DatabaseServerType.ORACLE){
				// TODO - IMPLEMENT
			}else if(lDatabaseServerType == DatabaseServerType.MYSQL){
				// TODO - IMPLEMENT
			}
						
		 	CSharpClassWriter lCSharpClassWriter = new CSharpClassWriter(lAbsoluteClassDirectoryLocation, lProcBuilder, lAuthor, lDatabaseServerType);	
		 	lCSharpClassWriter.BuildAllClasses();
			break;
						
		case JAVA:
			
			if (lDatabaseServerType == DatabaseServerType.MICROSOFTSQLSERVER){
				lProcBuilder = new MSSQLProcBuilder(ProcBuilder._dbname, ProcBuilder._dbconnection, ProcBuilder._dbusername, ProcBuilder._dbuserpassword);	
			}else if(lDatabaseServerType == DatabaseServerType.ORACLE){
				// TODO - IMPLEMENT
			}else if(lDatabaseServerType == DatabaseServerType.MYSQL){
				// TODO - IMPLEMENT
			}
			
		 	JavaClassWriter lJavaClassWriter = new JavaClassWriter(lAbsoluteClassDirectoryLocation, lProcBuilder, lAuthor, lDatabaseServerType);	
		 	lJavaClassWriter.BuildAllClasses();
			break;
						
		default:
			break;
			
		}
					    	  
	}
	     	      
}




	
	  


