package debug.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class MyMethod 
{
	public String methodName;        						//method������
	public String methodPackageName;  						//method���ڰ�������		
	public String methodExpression;							//����object.method(),object��Ϊmethod��expression
	public ArrayList<Expression> methodArguments;				//��ǰ����method�Ĳ�������func(arg1,arg2) [arg1,arg2]��Ϊ����
	public ArrayList<MyPair> methodExceptionPair;			//��ǰ����method��ecxption��Ϣ���ο�MyPair��
	public ArrayList<Expression> methodCondtion;
	public ArrayList<Expression> methodPreCondition;        //��ǰ����method֮ǰ��if statement condition
    
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
