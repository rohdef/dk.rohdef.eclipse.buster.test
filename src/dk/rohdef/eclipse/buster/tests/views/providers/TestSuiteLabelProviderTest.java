package dk.rohdef.eclipse.buster.tests.views.providers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.eclipse.swt.graphics.Image;
import org.junit.Before;
import org.junit.Test;

import dk.rohdef.eclipse.buster.models.Failure;
import dk.rohdef.eclipse.buster.models.TestCase;
import dk.rohdef.eclipse.buster.models.TestSuite;
import dk.rohdef.eclipse.buster.tests.wrappers.FileLocatorMock;
import dk.rohdef.eclipse.buster.views.providers.TestSuiteLabelProvider;

public class TestSuiteLabelProviderTest {
	private TestSuiteLabelProvider labelProvider;
	private TestSuite suite;
	private TestCase testCase;
	private TestCase failTestCase;
	
	@Before
	public void setUp() {
		labelProvider = new TestSuiteLabelProvider(new FileLocatorMock());
		
		suite = new TestSuite("Royal suite", 1, 3, 2, 3.1415);
		testCase = new TestCase("Mental case", "No class", 1.013);
		failTestCase = new TestCase("Mental case",
				"No class",
				1.013,
				new Failure("Fail", "Fail", "Fail"));
	}

	@Test
	public void testGetTextObject() {
		assertThat("Royal suite (3.1415 s)",
				equalTo(labelProvider.getText(suite)));
		assertThat("Mental case (1.013 s)",
				equalTo(labelProvider.getText(testCase)));
		
		// Perhaps some better error handling would be a good idea?
		assertThat("Error parsin",
				is(equalTo(labelProvider.getText("Foo"))));
	}

	@Test
	public void testGetImageObject() {
		Image suiteIcon = labelProvider.getImage(suite);
		Image successIcon = labelProvider.getImage(testCase);
		Image failIcon = labelProvider.getImage(failTestCase);
		
		assertThat(suiteIcon, notNullValue());
		assertThat(successIcon, notNullValue());
		assertThat(failIcon, notNullValue());
		
		assertThat(suiteIcon, not(equalTo(successIcon)));
		assertThat(suiteIcon, not(equalTo(failIcon)));
		assertThat(successIcon, not(equalTo(failIcon)));
	}
}
