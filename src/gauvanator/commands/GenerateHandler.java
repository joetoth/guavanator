package gauvanator.commands;

import gauvanator.generator.BeanGenerator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.IWorkingCopyManager;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class GenerateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getActiveEditor();

		IWorkingCopyManager manager = JavaUI.getWorkingCopyManager();
		IEditorInput editorInput = editor.getEditorInput();
		try {
			manager.connect(editorInput);
			ICompilationUnit workingCopy = manager.getWorkingCopy(editorInput);

			BeanGenerator generator = new BeanGenerator();

			generator.generate(workingCopy);

			synchronized (workingCopy) {
				workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} finally {
			manager.disconnect(editorInput);
		}
		return null;
	}

}