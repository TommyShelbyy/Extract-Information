package debug.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class MyException 
{
	public String exceptionType;            			//exceptionType����
	public ArrayList<Expression> exceptionCondition;			//exception�׳�������
	MyException()
	{
		exceptionType = "";
		exceptionCondition = new ArrayList<Expression>();
	}
}
