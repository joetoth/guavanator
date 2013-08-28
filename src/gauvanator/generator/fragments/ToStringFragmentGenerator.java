package gauvanator.generator.fragments;

import gauvanator.utils.CompilationUtils;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;

public class ToStringFragmentGenerator {

	private final ICompilationUnit compilationUnit;

	private ToStringFragmentGenerator(ICompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}

	private String generate() {
		List<IField> fields = CompilationUtils.findNonStaticFIelds(compilationUnit);
		StringBuilder builder = new StringBuilder()
				.append("  @Override\n")
				.append("  public String toString() {\n")
				.append("    return Objects.toStringHelper(this)\n");

		for (IField field : fields) {
			builder.append("      .add(\"" + field.getElementName() + "\", " + field.getElementName()
					+ ")\n");
		}

		return builder
				.append("      .toString();\n")
				.append("  }\n")
				.toString();
	}

	public static String generate(ICompilationUnit compilationUnit) {
		ToStringFragmentGenerator generator = new ToStringFragmentGenerator(compilationUnit);
		return generator.generate();
	}

}
