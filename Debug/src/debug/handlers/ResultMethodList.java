package debug.handlers;

import java.util.ArrayList;
import java.util.List;

public class ResultMethodList 
{
	public String methodName;        						//method������
	public String methodPackageName;  						//method���ڰ�������		
	public List<MethodArgAndException> methodArgAndExceptionList;
	
	ResultMethodList()
	{
		methodName = "";        						//method������
	    methodPackageName = "";  						//method���ڰ�������	
	    methodArgAndExceptionList = new ArrayList<MethodArgAndException>();
	}
}
