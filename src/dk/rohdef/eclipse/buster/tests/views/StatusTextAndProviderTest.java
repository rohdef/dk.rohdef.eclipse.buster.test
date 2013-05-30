package dk.rohdef.eclipse.buster.tests.views;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.rohdef.eclipse.buster.models.MockModel;
import dk.rohdef.eclipse.buster.models.RootTestSuite;
import dk.rohdef.eclipse.buster.tests.MockModelTest;
import dk.rohdef.eclipse.buster.views.StatusTexts;
import dk.rohdef.eclipse.buster.views.providers.IStatusTextProvider;
import dk.rohdef.eclipse.buster.views.providers.StatusTextProvider;

public class StatusTextAndProviderTest extends IGuiCase {

	@Before
	public void setUp() {
		super.setUp();
	}
	
	@After
	public void tearDown() {
		super.tearDown();
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
		StatusTexts texts = new StatusTexts(this.getShell(), SWT.NONE);
		texts.setContentProvider(new StatusTextProvider());
		
		RootTestSuite suite = (new MockModel()).getSuite(MockModelTest.xml);
		texts.setInput(suite);
		
		assertEquals("8", texts.getRunsText());
		assertEquals("1", texts.getErrorsText());
		assertEquals("2", texts.getFailuresText());
	}
	
	
}
