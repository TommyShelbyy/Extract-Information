package debug.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class MethodList 
{
	public String methodPackageName;
	public String methodExpression;  //ÀàËÆobject.method(),object¼´ÎªmethodµÄexpression
	public ArrayList<Expression> methodArguments;
	public String methodName;
	public ArrayList<Expression> ifConditionList;
	//public List<String> whileExpressionList;
	
	MethodList()
	{
		methodPackageName = "";
		methodExpression = null;
		methodArguments = new ArrayList<Expression>();
		methodName = "";
		ifConditionList = new ArrayList<Expression>();
		
	}
}
