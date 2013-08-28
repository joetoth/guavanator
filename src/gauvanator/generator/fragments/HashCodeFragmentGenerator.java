package gauvanator.generator.fragments;

import gauvanator.utils.CompilationUtils;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;

public class HashCodeFragmentGenerator {

  private final ICompilationUnit compilationUnit;

  private HashCodeFragmentGenerator(ICompilationUnit compilationUnit) {
    this.compilationUnit = compilationUnit;
  }

  public String generate() {
    List<IField> fields = CompilationUtils.findNonStaticFIelds(compilationUnit);
    StringBuilder builder = new StringBuilder().append("  @Override\n")
        .append("  public int hashCode() {\n").append("    return Objects.hashCode(\n");

    for (int i = 0; i < fields.size(); i++) {
      builder.append("      ").append(fields.get(i).getElementName());
      if (i != fields.size() - 1) {
        builder.append(",\n");
      } else {
        builder.append(");\n");
      }
    }
    return builder.append("  }\n").toString();
  }

  public static String generate(ICompilationUnit compilationUnit) {
    HashCodeFragmentGenerator generator = new HashCodeFragmentGenerator(compilationUnit);
    return generator.generate();
  }
}
