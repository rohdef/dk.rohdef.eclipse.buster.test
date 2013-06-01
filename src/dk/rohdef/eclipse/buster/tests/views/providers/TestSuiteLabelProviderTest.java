package dk.rohdef.eclipse.buster.tests.views.providers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

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
		assertThat(labelProvider.getText(suite),
				equalTo("Royal suite (3.1415 s)"));
		assertThat(labelProvider.getText(testCase),
				equalTo("Mental case (1.013 s)"));
		
		// Perhaps some better error handling would be a good idea?
		assertThat(labelProvider.getText("Foo"),
				is(equalTo("Error parsing")));
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
		
		assertThat(labelProvider.getImage("Foo"), nullValue());
	}
}
