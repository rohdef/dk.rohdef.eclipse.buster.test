package dk.rohdef.eclipse.buster.tests;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.rohdef.eclipse.buster.models.Failure;
import dk.rohdef.eclipse.buster.models.MockModel;
import dk.rohdef.eclipse.buster.models.RootTestSuite;
import dk.rohdef.eclipse.buster.models.TestCase;
import dk.rohdef.eclipse.buster.models.TestSuite;
import dk.rohdef.eclipse.buster.views.StatusTexts;
import dk.rohdef.eclipse.buster.views.providers.IStatusTextProvider;
import dk.rohdef.eclipse.buster.views.providers.StatusTextProvider;
import dk.rohdef.eclipse.buster.views.providers.TestSuiteContentProvider;

public class ViewsTest {
	private Shell shell;
	private Display display;

	@Before
	public void setUp() {
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
	}
	
	@After
	public void tearDown() {
		shell.dispose();
		shell = null;
		display.dispose();
		display = null;
	}
	
	@Test
	public void testStatusTextProvider() {
		IStatusTextProvider provider = new StatusTextProvider();
		RootTestSuite suite = (new MockModel()).getSuite(MockModelTest.xml);
		provider.inputChanged(null, null, suite);
		
		assertEquals("8", provider.getRuns());
		assertEquals("1", provider.getErrors());
		assertEquals("2", provider.getFailures());
	}
	
	@Test
	public void testStatusTexts() {
		StatusTexts texts = new StatusTexts(shell, SWT.NONE);
		texts.setContentProvider(new StatusTextProvider());
		
		RootTestSuite suite = (new MockModel()).getSuite(MockModelTest.xml);
		texts.setInput(suite);
		
		assertEquals("8", texts.getRunsText());
		assertEquals("1", texts.getErrorsText());
		assertEquals("2", texts.getFailuresText());
	}
	
	@Test
	public void testTestSuiteContentProviderHasChildren() {
		TestSuiteContentProvider provider = new TestSuiteContentProvider();
		
		RootTestSuite rootSuite = new RootTestSuite();
		assertFalse("New root suite should not have children",
				provider.hasChildren(rootSuite));
		
		TestSuite suite = new TestSuite();
		assertFalse("New suite should not have children",
				provider.hasChildren(suite));
		
		rootSuite.getSuites().add(suite);
		assertTrue("RootSuite have one test suite child",
				provider.hasChildren(rootSuite));
		assertFalse("Test suite should still not have any children",
				provider.hasChildren(suite));
		
		TestCase testCase = new TestCase();
		assertFalse("Test case should never have children",
				provider.hasChildren(testCase));

		suite.getTestCases().add(testCase);
		assertTrue("RootSuite have one test suite child",
				provider.hasChildren(rootSuite));
		assertTrue("Test suite have one test case child",
				provider.hasChildren(suite));
		assertFalse("Test case should never have children",
				provider.hasChildren(testCase));
		 
		testCase.setName("Foo bar is open");
		testCase.setTime(3.1415);
		testCase.setFailure(new Failure("Type", "Hello", "No text"));
		assertFalse("Test case should never have children",
				provider.hasChildren(testCase));
	}
}
