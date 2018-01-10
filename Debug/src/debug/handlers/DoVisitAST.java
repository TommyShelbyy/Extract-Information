package debug.handlers;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import java.util.*;
public class DoVisitAST 
{
	
	List funList ;
	
	ArrayList<String> packagelist;
	DoVisitAST()
	{
		funList = new ArrayList();
		
		packagelist = new ArrayList<String>();
		
		packagelist.add("java.lang.String");
		packagelist.add("java.util.ArrayList");
		packagelist.add("java.util.LinkedList");
		packagelist.add("java.util.HashSet");
		packagelist.add("java.util.HashMap");
		packagelist.add("java.util.Stack");
		packagelist.add("java.util.Vector");
		packagelist.add("java.util.LinkedHashMap");
		packagelist.add("java.util.LinkedHashSet");
		packagelist.add("java.util.Iterator");
		packagelist.add("java.util.ListIterator");
	}
	
	public boolean judgePackage(String packagename)
	{
		
		for(int i = 0;i<packagelist.size();i++)
		{
			if(packagename.trim().startsWith(packagelist.get(i)))
				return true;	
		}
		return false;
	}
	
	public List<ResultMethodList> visitAST(CompilationUnit unit)
	{
		ArrayList<ResultMethodList> classMethodList = new ArrayList<ResultMethodList>();
		ASTVisitor visitor = new ASTVisitor() 
		{
			//访问函数声明
		    public boolean visit(MethodDeclaration node) 
		    {
		    	String caller = node.getName().toString(); 
		    	
		        System.out.println("函数: " + caller);
		        return true;
		    }
		    //访问ifstatement
		    public boolean visit(IfStatement if_node)
		    {
		    	funList.add(if_node.getExpression());
		    	return true;
		    }
		    //访问throwstatement
		    public boolean visit(ThrowStatement throw_statement)
		    {
		    	ITypeBinding typebind = (ITypeBinding)throw_statement.getExpression().resolveTypeBinding();
		    	ThrowList throwlist = new ThrowList();
		    	if(typebind != null)
		    		throwlist.exceptionType = typebind.getQualifiedName() ;
		    	else
		    		throwlist.exceptionType = "";
		    	
		    	ASTNode astnode;
		        astnode = throw_statement;
		    	while(!astnode.getParent().equals(unit.getRoot()))
		        {
		    		if(astnode instanceof IfStatement )
		        	{
		    			if(((IfStatement) astnode).getThenStatement()!=null && ((IfStatement) astnode).getThenStatement().toString().contains(throw_statement.toString()))
			        	{
			    			 throwlist.exceptionCondition.add(((IfStatement) astnode).getExpression());			    			 
		        		}
		    			 else if(((IfStatement) astnode).getElseStatement() != null && ((IfStatement) astnode).getElseStatement().toString().contains(throw_statement.toString()))
		        		 {
		    				 AST ast = AST.newAST(AST.JLS4);
		    				 PrefixExpression expressionCondition = ast.newPrefixExpression();
		    				 expressionCondition.setOperator(PrefixExpression.Operator.NOT);
		    				 expressionCondition.setOperand((Expression)ASTNode.copySubtree(ast,((IfStatement) astnode).getExpression()));		    		
		    				 throwlist.exceptionCondition.add(expressionCondition);
		    				 
		        		 }	
		        	}
		    		astnode = astnode.getParent();
		        }
		    	
		    	//过滤exception
		    	if(throwlist.exceptionType.startsWith("java."))
		    		funList.add(throwlist);
		    	return true;
		    } 
		    
		    //访问invaction
		    public boolean visit(MethodInvocation node) 
		    {
		    	 //String methodName = node.getName().toString();
		    	 MethodList methodlist = new MethodList();		  
		       	 IMethodBinding method = (IMethodBinding)node.resolveMethodBinding();
		    	 if(method != null && method.getDeclaringClass()!=null )
		    	 {
			    	 if(judgePackage(method.getDeclaringClass().getQualifiedName().toString()))
			    	 {
			    		 if(node.getExpression() != null)     //若有objedt.method(),则加上object.
			    		 {
			    			 methodlist.methodExpression = "object.";
			    		 }			    		 
			    		 methodlist.methodName = node.getName().toString();
			    		 methodlist.methodPackageName = method.getDeclaringClass().getQualifiedName().toString();
			   
			    		 methodlist.methodArguments.addAll(node.arguments());		

				         ASTNode astnode;
				         astnode = node;
				    	 while(!astnode.getParent().equals(unit.getRoot()))
				         {	
				    		 astnode = astnode.getParent();
				        	 if(astnode instanceof IfStatement)
				        	 {
				        		 if(((IfStatement) astnode).getThenStatement() != null && ((IfStatement) astnode).getThenStatement().toString().contains(node.toString()))
				        		 {				   
				        			 methodlist.ifConditionList.add(((IfStatement) astnode).getExpression());  					        			
				        		 }
				        		 else if(((IfStatement) astnode).getElseStatement() != null && ((IfStatement) astnode).getElseStatement().toString().contains(node.toString()))
				        		 {
				        			 AST ast = AST.newAST(AST.JLS4);
				    				 PrefixExpression expressionCondition = ast.newPrefixExpression();
				    				 expressionCondition.setOperator(PrefixExpression.Operator.NOT);
				    				 expressionCondition.setOperand((Expression)ASTNode.copySubtree(ast,((IfStatement) astnode).getExpression()));		    		
				        			 methodlist.ifConditionList.add(expressionCondition);  				   				     
				        		 }
				        	 }
		        		 
				         }				    	 
				    	 funList.add(methodlist);
			    	 }
		    	 }
		    	 		    	 
		         return true;
		    }
		     
		    public void endVisit(MethodDeclaration node)
		    {
	    	
		    	for(int i = 0;i<funList.size();i++)
		    	{
		    		MyMethod tempMethod = new MyMethod();
		    		//put method information into MyMethod
		    		if(funList.get(i) instanceof MethodList)
		    		{			        	 
		    			tempMethod.methodName = ((MethodList) funList.get(i)).methodName;
		    			tempMethod.methodPackageName = ((MethodList) funList.get(i)).methodPackageName;
		    			tempMethod.methodArguments.addAll(((MethodList) funList.get(i)).methodArguments) ;
		    				    			
    					ArrayList<MyPair> tempPairList = new ArrayList<MyPair>(); 
    					
    					//put the relate to arguments' methdoPreCondition into MyMethod
    					if((i-1) >= 0 &&funList.get(i-1) instanceof Expression && !funList.get(i-1).toString().contains(tempMethod.methodName) )
    					{
    						boolean isRelated = false;
    						for(int conditionCount = 0;conditionCount<tempMethod.methodArguments.size();conditionCount++)
    						{
    							if(funList.get(i-1).toString().contains(tempMethod.methodArguments.get(conditionCount).toString()))
    							{
    								isRelated = true;
    								break;
    							}
    						}
    						if(isRelated)
    							tempMethod.methodPreCondition.add(((Expression)funList.get(i-1)));
    					}	
    					for(int k = i-2 ;k>=0;k--)
    					{
    						if(funList.get(k) instanceof Expression )
    						{
    							boolean isRelated = false;
        						for(int conditionCount = 0;conditionCount<tempMethod.methodArguments.size();conditionCount++)
        						{
        							if(funList.get(k).toString().contains(tempMethod.methodArguments.get(conditionCount).toString()))
        							{
        								isRelated = true;
        								break;
        							}
        						}
        						if(isRelated)
        							tempMethod.methodPreCondition.add(((Expression)funList.get(k)));
    						}
    					}
    					
    					//get the probability of exception to the method ,then put MyPair into MyMethod
			    		for(int j = i+1 ;j< funList.size();j++)
			    		{
			    			if(funList.get(j) instanceof ThrowList)
			    			{
			    				String methodFirstCondition = (((MethodList) funList.get(i)).ifConditionList.size() == 0) ? "": ((MethodList) funList.get(i)).ifConditionList.get(0).toString();

			    				String throwFirstCondition = (((ThrowList)funList.get(j)).exceptionCondition.size() == 0) ? "":((ThrowList)funList.get(j)).exceptionCondition.get(0).toString();
			    				MyException tempException = new MyException();
			    				MyPair tempPair = new MyPair();
		    					tempException.exceptionType = ((ThrowList)funList.get(j)).exceptionType;
		    					tempException.exceptionCondition.addAll(((ThrowList)funList.get(j)).exceptionCondition); 		   	
		    					tempPair.exception = tempException;
			    				
			    				if(methodFirstCondition != "" && throwFirstCondition.equals("!"+methodFirstCondition))
			    				{	    					
			    					tempPair.probability = 1.0;
			    				}
			    				else
			    				{
			    					tempPair.probability = 0.5;
			    				}
			    				tempPairList.add(tempPair);		    				
			    			}
			    		}
			    		tempMethod.methodExceptionPair = tempPairList;
			    		
			    		boolean isSame = false;
			    		for(int classmethodlistcount = 0;classmethodlistcount<classMethodList.size();classmethodlistcount++)
			    		{
			    			if(tempMethod.methodName.equals(classMethodList.get(classmethodlistcount).methodName) && tempMethod.methodPackageName.equals(classMethodList.get(classmethodlistcount).methodPackageName))
			    			{
			    				MethodArgAndException tempMethodArgAndException = new MethodArgAndException();
			    				tempMethodArgAndException.methodExpression = tempMethod.methodExpression;
			    				tempMethodArgAndException.methodArguments.addAll(tempMethod.methodArguments);
			    				tempMethodArgAndException.methodExceptionPair.addAll(tempMethod.methodExceptionPair);
			    				tempMethodArgAndException.methodPreCondition.addAll(tempMethod.methodPreCondition);
			    				classMethodList.get(classmethodlistcount).methodArgAndExceptionList.add(tempMethodArgAndException);
			    				isSame = true;
			    				break;
			    			}
			    		}
			    		if(!isSame)
			    		{
			    			MethodArgAndException tempMethodArgAndException = new MethodArgAndException();
		    				tempMethodArgAndException.methodExpression = tempMethod.methodExpression;
		    				tempMethodArgAndException.methodArguments.addAll(tempMethod.methodArguments);
		    				tempMethodArgAndException.methodExceptionPair.addAll(tempMethod.methodExceptionPair);
		    				tempMethodArgAndException.methodPreCondition.addAll(tempMethod.methodPreCondition);
		    				ResultMethodList tempResultMethodList = new ResultMethodList();
		    				tempResultMethodList.methodName = tempMethod.methodName;
		    				tempResultMethodList.methodPackageName = tempMethod.methodPackageName;
		    				tempResultMethodList.methodArgAndExceptionList.add(tempMethodArgAndException);
			    			classMethodList.add(tempResultMethodList);
			    		}	    		
		    		}	
		    	}
		    	funList.clear();
		    }
		    
		};	
		unit.accept(visitor);
	
		
		//---------------------test code-------------------
		for(int i = 0 ;i<classMethodList.size();i++)
		{
			ResultMethodList m = classMethodList.get(i);
    		//debug content
    		System.out.println();
			System.out.println("call method's name is :"+m.methodName);
			System.out.println("call method's package name is :" +m.methodPackageName);
			//System.out.println("call method's arguments is： "+m.methodArguments.toString());
			//System.out.println("call method's pre condition is:"+m.methodPreCondition.toString());
			for(int resultcount = 0 ;resultcount<m.methodArgAndExceptionList.size();resultcount++)
			{
				System.out.println("------------------one record:--------------------");
			//	if(m.methodArgAndExceptionList.get(resultcount).methodArguments !=null)
				System.out.println("call method arguments: "+m.methodArgAndExceptionList.get(resultcount).methodArguments);
			//	if(m.methodArgAndExceptionList.get(resultcount).methodPreCondition != null)
				System.out.println("call method preCondition :"+m.methodArgAndExceptionList.get(resultcount).methodPreCondition);
				for(int k = 0 ; k<m.methodArgAndExceptionList.get(resultcount).methodExceptionPair.size();k++)
				{
					
					MyPair tempPair = new MyPair();
					tempPair = m.methodArgAndExceptionList.get(resultcount).methodExceptionPair.get(k);
					System.out.println("call method's exception type is :" +tempPair.exception.exceptionType);
					//for(int s = 0 ;s<m.methodExceptionPair.get(k).exception.exceptionCondition.size();s++)
					System.out.println("call method's exception condition is :" +tempPair.exception.exceptionCondition);
					System.out.println("call method's probability is :" +tempPair.probability);
				}
				System.out.println("-------------------------------------------------");
			}
				System.out.println();
			
		}
		//--------------------test code end-----------------
		return classMethodList;
	}
	public List<ResultMethodList> allMethodInformation(List<ResultMethodList> result,List<ResultMethodList>source )
	{	
		for(int sourceListCount = 0;sourceListCount<source.size();sourceListCount++)
		{
			ResultMethodList tempSourceMethod = source.get(sourceListCount);
			boolean isOccur = false;
			for(int resultListCount = 0 ;resultListCount<result.size();resultListCount++)
			{
				ResultMethodList tempResultMethod = result.get(resultListCount);
				if(tempSourceMethod.methodName.equals(tempResultMethod.methodName)&&tempSourceMethod.methodPackageName.equals(tempResultMethod.methodPackageName))
				{
					result.get(resultListCount).methodArgAndExceptionList.addAll(tempSourceMethod.methodArgAndExceptionList);
					isOccur = true;
					break;
				}
			}
			if(!isOccur)
			{
				result.add(tempSourceMethod);
			}

		}	
		
		return result;
	}
	
	public void showInformation(List<ResultMethodList> wantToShow)
	{
		//---------------------test code-------------------
		for(int i = 0 ;i<wantToShow.size();i++)
		{
			ResultMethodList m = wantToShow.get(i);
    		//debug content
    		System.out.println();
			System.out.println("call method's name is :"+m.methodName);
			System.out.println("call method's package name is :" +m.methodPackageName);
			for(int resultcount = 0 ;resultcount<m.methodArgAndExceptionList.size();resultcount++)
			{
				System.out.println("------------------one record:--------------------");
				System.out.println("call method arguments: "+m.methodArgAndExceptionList.get(resultcount).methodArguments);			
				System.out.println("call method preCondition :"+m.methodArgAndExceptionList.get(resultcount).methodPreCondition);
				for(int k = 0 ; k<m.methodArgAndExceptionList.get(resultcount).methodExceptionPair.size();k++)
				{
					
					MyPair tempPair = new MyPair();
					tempPair = m.methodArgAndExceptionList.get(resultcount).methodExceptionPair.get(k);
					System.out.println("call method's exception type is :" +tempPair.exception.exceptionType);
					System.out.println("call method's exception condition is :" +tempPair.exception.exceptionCondition);
					System.out.println("call method's probability is :" +tempPair.probability);
				}
				System.out.println("-------------------------------------------------");
			}
				System.out.println();		
		}
	}

	public static void replaceArg(ASTNode ex)
	{
		AST ast = AST.newAST(AST.JLS4); 
		
		InfixExpression newInfixExpression = ast.newInfixExpression();
		if(ex != null)
		{
			if(ex instanceof InfixExpression)
			{
				if(((InfixExpression) ex).getLeftOperand() instanceof InfixExpression)
				{
					System.out.println();
					replaceArg(((InfixExpression ) ex).getLeftOperand());
				}
				else if(((InfixExpression ) ex).getLeftOperand() instanceof ParenthesizedExpression)
				{
					if(((ParenthesizedExpression ) ((InfixExpression ) ex).getLeftOperand()).getExpression() != null)
						replaceArg(((ParenthesizedExpression ) ((InfixExpression ) ex).getLeftOperand()).getExpression());
				}	
				
				if(((InfixExpression ) ex).getRightOperand() instanceof InfixExpression)
					replaceArg(((InfixExpression ) ex).getRightOperand());
				else if(((InfixExpression ) ex).getRightOperand() instanceof ParenthesizedExpression)
				{
					if(((ParenthesizedExpression ) ((InfixExpression ) ex).getRightOperand()).getExpression() != null)
						replaceArg(((ParenthesizedExpression ) ((InfixExpression ) ex).getRightOperand()).getExpression());
				}else
				{
					ASTNode node =ASTNode.copySubtree(ast,((InfixExpression ) ex).getLeftOperand());
					newInfixExpression.setLeftOperand((Expression)node);
					newInfixExpression.setOperator(((InfixExpression ) ex).getOperator());
					newInfixExpression.setRightOperand(ast.newSimpleName("arg"));
				}
				System.out.println("after change is :"+newInfixExpression.toString());
				
			}
			if(ex instanceof ParenthesizedExpression)
			{
				
				if(((ParenthesizedExpression ) ex).getExpression() != null)
					replaceArg(((ParenthesizedExpression ) ex).getExpression());
			}
		}
	}
}
