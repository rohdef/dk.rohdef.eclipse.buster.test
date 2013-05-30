package dk.rohdef.eclipse.buster.tests.views;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import dk.rohdef.eclipse.buster.models.MockModel;
import dk.rohdef.eclipse.buster.models.RootTestSuite;
import dk.rohdef.eclipse.buster.views.providers.TestSuiteContentProvider;

public abstract class IGuiCase {
	private TestSuiteContentProvider provider;
	private Display display;
	private Shell shell;
	private RootTestSuite rootSuite;
	
	public void setUp() {
		this.provider = new TestSuiteContentProvider();
		this.display = new Display();
		this.shell = new Shell(this.display, SWT.SHELL_TRIM);
		this.rootSuite = (new MockModel()).getSuite(new File("resources/test-result.xml"));
	}

	public void tearDown() {
		this.shell.dispose();
		this.shell = null;
		this.display.dispose();
		this.display = null;
	}
	
	protected Display getDisplay() {
		return display;
	}
	
	protected Shell getShell() {
		return shell;
	}
	protected TestSuiteContentProvider getProvider() {
		return provider;
	}
	public RootTestSuite getRootSuite() {
		return rootSuite;
	}
}
