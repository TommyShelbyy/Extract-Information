package debug.handlers;

import java.util.ArrayList;
import java.util.List;

public class ResultMethodList 
{
	public String methodName;        						//method的名称
	public String methodPackageName;  						//method所在包的名称		
	public List<MethodArgAndException> methodArgAndExceptionList;
	
	ResultMethodList()
	{
		methodName = "";        						//method的名称
	    methodPackageName = "";  						//method所在包的名称	
	    methodArgAndExceptionList = new ArrayList<MethodArgAndException>();
	}
}
