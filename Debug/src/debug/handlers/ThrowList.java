package debug.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class ThrowList 
{
	public String exceptionType;
	public ArrayList<Expression> exceptionCondition;
	
	ThrowList()
	{
		exceptionType = "";
		exceptionCondition = new ArrayList<Expression> ();
		
	}
}
