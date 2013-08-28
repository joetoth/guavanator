package gauvanator.generator.fragments;

import gauvanator.utils.CompilationUtils;

import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;

public class GettersSettersFragmentGenerator {

  // Many used for GWT and other serialization frameworks that can't have a field marked final
  private static final String[] NON_FINAL_ANNOTATION_NAMES = {"NonFinalForGwt"};

  private final ICompilationUnit compilationUnit;

  private GettersSettersFragmentGenerator(ICompilationUnit compilationUnit) {
    this.compilationUnit = compilationUnit;
  }

  public String generate() {
    String className = CompilationUtils.getClassName(compilationUnit);
    StringBuilder builder = new StringBuilder();

    List<IField> fields = CompilationUtils.findNonStaticFIelds(compilationUnit);

    for (IField field : fields) {
      String fieldName = CompilationUtils.getName(field);
      String type = CompilationUtils.getType(field);
      String capitalizedFieldName = CompilationUtils.capitalize(fieldName);

      builder.append(
          "public " + type + " get" + capitalizedFieldName + "() { return " + fieldName + "; } ");

      if (isFieldMutable(field)) {
        builder.append("public " + className + " set" + capitalizedFieldName + "(" + type + " "
            + fieldName + ") { this. " + fieldName + " = " + fieldName + "; return this; } ");
      }
    }

    return builder.append("\n").toString();
  }

  public static String generate(ICompilationUnit compilationUnit) {
    GettersSettersFragmentGenerator generator =
        new GettersSettersFragmentGenerator(compilationUnit);
    return generator.generate();
  }

  private boolean isFieldMutable(IField field) {
    try {
      for (IAnnotation annotation : field.getAnnotations()) {
        for (String notNullAnnotationName : NON_FINAL_ANNOTATION_NAMES) {
          if (Flags.isFinal(field.getFlags())
              || annotation.getElementName().equals(notNullAnnotationName)) {
            return false;
          }
        }
      }
    } catch (JavaModelException e) {
      throw new RuntimeException(e);
    }

    return true;
  }
}
