package dk.rohdef.eclipse.buster.tests;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ViewsTest {
	private Shell shell;

	@Before
	public void setUp() {
		Display display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
	}
	
	@After
	public void tearDown() {
		shell.dispose();
		shell = null;
	}
	
	@Test
	public void testStatusTexts() {
		
	}
	
}
