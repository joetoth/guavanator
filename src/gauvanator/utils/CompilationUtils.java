package gauvanator.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public class CompilationUtils {

  private CompilationUtils() {}

  public static String getType(IField field) {
    try {
      return Signature.toString(field.getTypeSignature());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (JavaModelException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<IField> findNonStaticFIelds(ICompilationUnit compilationUnit) {
    List<IField> fields = new ArrayList<IField>();
    try {
      IType clazz = compilationUnit.getTypes()[0];

      for (IField field : clazz.getFields()) {
        int flags = field.getFlags();
        boolean notStatic = !Flags.isStatic(flags);
        if (notStatic) {
          fields.add(field);
        }
      }

    } catch (JavaModelException e) {
      e.printStackTrace();
    }
    return fields;
  }

  public static String getName(IField field) {
    return field.getElementName();
  }

  public static String capitalize(String word) {
    return word.substring(0, 1).toUpperCase() + word.substring(1);
  }

  public static String getClassName(ICompilationUnit compilationUnit) {
    try {
      return compilationUnit.getTypes()[0].getElementName();
    } catch (JavaModelException e) {
      throw new RuntimeException(e);
    }
  }

}
