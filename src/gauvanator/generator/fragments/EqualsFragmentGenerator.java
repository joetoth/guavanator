package gauvanator.generator.fragments;

import gauvanator.utils.CompilationUtils;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;

public class EqualsFragmentGenerator {
  
      private final ICompilationUnit compilationUnit;
      
      private EqualsFragmentGenerator(ICompilationUnit compilationUnit) {
	    this.compilationUnit = compilationUnit;
      }
	  
	  public String generate() {
		String className = CompilationUtils.getClassName(compilationUnit);
	    StringBuilder builder = new StringBuilder()
    	  .append("  @Override\n") 
    	  .append("  public boolean equals(Object obj) {\n")
    	  .append("  if(!(obj instanceof " + className + ")) {\n")
	      .append("    return false;\n")
	      .append("  }\n")
	      .append("\n")
	      .append("  "+className + " that = (" + className +") obj;\n")
	      .append("\n")
	      .append("  return Objects");
	    
	    List<IField> fields = CompilationUtils.findNonStaticFIelds(compilationUnit); 
	    	
    	for (int i = 0; i < fields.size(); i++) {
    	  builder.append("    ").append(
    		  ".equals(this." + fields.get(i).getElementName() + ", that."
    		      + fields.get(i).getElementName() + ")");
    	  if (i == fields.size() - 1) {
    		builder.append(";");
    	  }
    	  builder.append("\n");
    
    	}
    	return builder.append("  }\n").toString();
	  }
	  
	  public static String generate(ICompilationUnit compilationUnit) {
		EqualsFragmentGenerator generator = new EqualsFragmentGenerator(compilationUnit);
		return generator.generate();
	  }
}
