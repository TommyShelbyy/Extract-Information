package debug.handlers;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Expression;

public class MethodArgAndException 
{
	public String methodExpression;							//����object.method(),object��Ϊmethod��expression
	public ArrayList<Expression> methodArguments;				//��ǰ����method�Ĳ�������func(arg1,arg2) [arg1,arg2]��Ϊ����
	public ArrayList<MyPair> methodExceptionPair;			//��ǰ����method��ecxption��Ϣ���ο�MyPair��
	public ArrayList<Expression> methodPreCondition;        //��ǰ����method֮ǰ��if statement condition
    
	MethodArgAndException()
	{
		methodExpression = null;	
		methodArguments = new ArrayList<Expression>();
		methodExceptionPair = new ArrayList<MyPair>();
		methodPreCondition = new ArrayList<Expression>();
	}

}
