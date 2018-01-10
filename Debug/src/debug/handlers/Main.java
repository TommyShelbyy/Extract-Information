package debug.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import org.eclipse.jdt.core.dom.CompilationUnit;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class Main extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(
//				window.getShell(),
//				"Debug",
//				"Hello, 12.18");
		 IWorkspace workspace = ResourcesPlugin.getWorkspace();
	     IWorkspaceRoot root = workspace.getRoot();
	     //IProject projects = root.getProject("apache-log4j-2.10.0-src");
	     IProject projects = root.getProject("testProject");
	     IJavaProject javaProject = JavaCore.create(projects);
	     DoCreateAST createObject = new DoCreateAST();
	     DoVisitAST visitObject = new DoVisitAST();
	     
	     List<ResultMethodList> result = new ArrayList<ResultMethodList>();
	     
	     System.out.println();	  
	     System.out.println("projcet's name is:"+javaProject.getElementName());
	     //System.out.println("dealing....");
	     try {
			IPackageFragment[] packages = javaProject.getPackageFragments();
			for(int i=0;i<packages.length;i++)
		     {
				if(packages[i].getKind() != IPackageFragmentRoot.K_SOURCE)
				{
					continue;
				}
				ICompilationUnit[] units = packages[i].getCompilationUnits(); 	
				if(units.length ==0)
					continue;
				
				for(int j=0;j<units.length;j++)
				{
					List<ResultMethodList> source = new ArrayList<ResultMethodList>();
					CompilationUnit c = createObject.CreatAST(units[j]);
					 visitObject.visitAST(c);
				}
		     }
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     //System.out.println("done!");
	    // visitObject.showInformation(result); 
		return null;
	}
	
	
	
	
	
}
