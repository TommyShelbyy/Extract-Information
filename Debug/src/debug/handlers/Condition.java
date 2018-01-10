package debug.handlers;

import org.eclipse.jdt.core.dom.Expression;

public class Condition 
{
	public Expression conditionExpression;			//if statement condition expression
	public boolean isNagative;						//flag that judge if this condition expression is nagative 
	Condition()
	{
		conditionExpression = null;
		isNagative = false;
	}
}
