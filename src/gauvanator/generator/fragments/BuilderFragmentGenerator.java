package gauvanator.generator.fragments;

import gauvanator.utils.CompilationUtils;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;

public class BuilderFragmentGenerator {

	private final ICompilationUnit compilationUnit;

	private BuilderFragmentGenerator(ICompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}

	public String generate() {
		StringBuilder builder = new StringBuilder();

		List<IField> fields = CompilationUtils.findNonStaticFIelds(compilationUnit);

		builder.append("public static class Builder {");

		for (IField field : fields) {
			builder.append("private " + CompilationUtils.getType(field) + " "
					+ CompilationUtils.getName(field) + ";");
		}

		//
		for (IField field : fields) {
			String name = CompilationUtils.getName(field);
			String type = CompilationUtils.getType(field);
			builder
					.append("public Builder set" + CompilationUtils.capitalize(name) + "(" + type + " "
							+ name + ") {");
			builder.append("this." + name + "=" + name + ";");
			builder.append("return this;\n}");
		}

		// Build with private constructor
		String clazzName = CompilationUtils.getClassName(compilationUnit);
		builder.append("public " + clazzName + " build() {");
		builder.append("return new " + clazzName + "(this);");
		builder.append("}");

		builder.append("}");

		// Create private constructor
		builder.append("private " + clazzName + "(Builder builder) {");
		for (IField field : fields) {
			String name = CompilationUtils.getName(field);
			builder.append("this." + name + " = builder." + name + ";");
		}
		builder.append("}");

		// builder() method
		builder.append("public static Builder builder() {\n" +
				"		return new Builder();\n" +
				"	}");

		return builder.toString();
	}

	public static String generate(ICompilationUnit compilationUnit) {
		BuilderFragmentGenerator generator = new BuilderFragmentGenerator(
				compilationUnit);
		return generator.generate();
	}

}
