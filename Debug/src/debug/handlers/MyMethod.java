package debug.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class MyMethod 
{
	public String methodName;        						//method的名称
	public String methodPackageName;  						//method所在包的名称		
	public String methodExpression;							//类似object.method(),object即为method的expression
	public ArrayList<Expression> methodArguments;				//当前调用method的参数，如func(arg1,arg2) [arg1,arg2]即为参数
	public ArrayList<MyPair> methodExceptionPair;			//当前调用method的ecxption信息，参考MyPair类
	public ArrayList<Expression> methodCondtion;
	public ArrayList<Expression> methodPreCondition;        //当前调用method之前的if statement condition
    
	MyMethod()
	{
		methodPackageName = "";
		methodName = "";
		methodExpression = null;
		methodArguments = new ArrayList<Expression>();
		methodExceptionPair = new ArrayList<MyPair>();
		methodCondtion = new ArrayList<Expression>();
		methodPreCondition = new ArrayList<Expression>();
		
	}
}
