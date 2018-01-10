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
		ASTParser parser = ASTParser.newParser(AST.JLS4); //��ʼ��    
		parser.setKind(ASTParser.K_COMPILATION_UNIT);     //���óɷ���CompilationUnit 
		parser.setResolveBindings(true); 
		parser.setSource(unit);          //�洢java source ��String  
		
		CompilationUnit result = (CompilationUnit) parser.createAST(null);  
		return result;
	}
}
