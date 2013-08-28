package gauvenerator.generator;

import gauvanator.generator.fragments.ToStringFragmentGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.testplugin.JavaProjectHelper;
import org.junit.Before;
import org.junit.Test;

public class ToStringGeneratorTest {

	protected static final String TEST_PROJECT = "TestProject";
	
  protected static final String SRC = "src";
	
	ICompilationUnit compilationUnit;

	@Before
	public void setUp() throws Exception {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		InputStream is = ToStringGeneratorTest.class.getResourceAsStream("PersonDotJava.txt");
		String source = toString(is);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		System.out.println(source);
		parser.setSource(source.toCharArray());
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		compilationUnit = (ICompilationUnit) cu.getJavaElement();

		// TODO: figure out how to get a ICompilationUnit
//		 IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject("cat");
//		 
//		IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
//		newProject.create(desc, null);
//		newProject.open(null);
//		
//		 // Create the test project
//    IJavaProject p = JavaProjectHelper.createJavaProject(TEST_PROJECT, "bin");
//    JavaProjectHelper.addRTJar(getJavaProject());
//
//    // Create default 'src'
//    JavaProjectHelper.addSourceContainer(getJavaProject(), SRC);
//   
//		
////		Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().
	
		is.close();
	}
	
  /**
   * Returns the Java project for this test.
   *
   * @return An IJavaProject.
   */
  protected static IJavaProject getJavaProject() {
      return JavaCore.create(getWorkspaceRoot()).getJavaProject(TEST_PROJECT);
  }

  /**
   * Returns the project for this test.
   *
   * @return An IProject.
   */
  protected static IProject getProject() {
      return getJavaProject().getProject();
  }

  protected static IWorkspaceRoot getWorkspaceRoot() {
      return ResourcesPlugin.getWorkspace().getRoot();
  }

	@Test
	public void test() {
		String source = ToStringFragmentGenerator.generate(compilationUnit);
		System.out.println(source);
	}

	public static String toString(final InputStream is)
	{
		final char[] buffer = new char[1024];
		final StringBuilder out = new StringBuilder();
		try {
			final Reader in = new InputStreamReader(is, "UTF-8");
			try {
				for (;;) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					out.append(buffer, 0, rsz);
				}
			} finally {
				in.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return out.toString();
	}

}
