package debug.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class MyException 
{
	public String exceptionType;            			//exceptionType类型
	public ArrayList<Expression> exceptionCondition;			//exception抛出的条件
	MyException()
	{
		exceptionType = "";
		exceptionCondition = new ArrayList<Expression>();
	}
}
