package debug.handlers;

import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class DoCreateAST 
{
	public  CompilationUnit CreatAST(ICompilationUnit unit) throws IOException
	{        
		ASTParser parser = ASTParser.newParser(AST.JLS4); //初始化    
		parser.setKind(ASTParser.K_COMPILATION_UNIT);     //设置成分析CompilationUnit 
		parser.setResolveBindings(true); 
		parser.setSource(unit);          //存储java source 的String  
		
		CompilationUnit result = (CompilationUnit) parser.createAST(null);  
		return result;
	}
}
