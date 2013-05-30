package dk.rohdef.eclipse.buster.tests.views.providers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import dk.rohdef.eclipse.buster.models.Failure;
import dk.rohdef.eclipse.buster.models.MockModel;
import dk.rohdef.eclipse.buster.models.RootTestSuite;
import dk.rohdef.eclipse.buster.models.TestCase;
import dk.rohdef.eclipse.buster.models.TestSuite;
import dk.rohdef.eclipse.buster.tests.MockModelTest;
import dk.rohdef.eclipse.buster.views.providers.TestSuiteContentProvider;

public class TestSuiteContentProviderTest {
	private TestSuiteContentProvider provider;
	private MockModel model;
	private RootTestSuite suite;
	
	private String failure1Text = "\r\n" +
			"                Error: [assert.same] true expected to be the same object as false\r\n" +
			"                    at Object.ba.fail (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:1483:25)\r\n" +
			"                    at Object.fail (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:1350:12)\r\n" +
			"                    at Function.ba.(anonymous function).(anonymous function) (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:1284:29)\r\n" +
			"                    at Object.ba.expectation.(anonymous function) [as toBe] (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:2036:33)\r\n" +
			"                    at Object.buster.testCase.Simple failure (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/test/foo-browser.js:11:18)\r\n" +
			"                    at asyncFunction (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:6710:19)\r\n" +
			"                    at callTestFn (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:6816:27)\r\n" +
			"                    at http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:790:27\r\n" +
			"                    at http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:790:27\r\n" +
			"                    at p.then (http://localhost:1111/sessions/519d7f72-26ea-4f1e-b79f-fbdf354edc37/resources/buster/bundle-0.6.js:71:31)\r\n" +
			"            ";
	private String failure2Text = "\r\n" +
			"                ReferenceError: undefined1 is not defined\r\n" +
            "                    at http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/test/foo-browser.js:15:42\r\n" +
            "                    at Object.buster.testCase.Auch an error (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/test/foo-browser.js:15:66)\r\n" +
            "                    at asyncFunction (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:6710:19)\r\n" +
            "                    at callTestFn (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:6816:27)\r\n" +
            "                    at http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:790:27\r\n" +
            "                    at http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:790:27\r\n" +
            "                    at p.then (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:71:31)\r\n" +
            "                    at Object.then (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:177:11)\r\n" +
            "                    at Object.B.extend.runTest (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:7019:26)\r\n" +
            "                    at Object.&lt;anonymous&gt; (http://localhost:1111/sessions/b1f178b2-e878-4751-aa0d-62ee57c7da7b/resources/buster/bundle-0.6.js:6994:29)\r\n" +
			"            ";

	@Before
	public void setUp() {
		provider = new TestSuiteContentProvider();
		model = new MockModel();
		suite = model.getSuite(MockModelTest.xml);
	}
	
	@Test
	public void testHasChildren() {
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
	
	@Test
	public void testGetParent() {
		RootTestSuite root = new RootTestSuite();
		TestSuite suite = new TestSuite();
		TestCase testCase = new TestCase();
		
		suite.getTestCases().add(testCase);
		root.getSuites().add(suite);
		
		// Currently there's no obvious reason to implement 
		// this, so currently we don't.
		assertNull(provider.getParent(suite));
		assertNull(provider.getParent(testCase));
	}
	
	@Test
	public void testGetChildren() {
		RootTestSuite root = new RootTestSuite();
		TestSuite suite = new TestSuite();
		TestCase testCase = new TestCase();
		
		Object[] emptyArray = {};
		assertArrayEquals(emptyArray, provider.getChildren(root));
		assertArrayEquals(emptyArray, provider.getChildren(suite));
		assertNull(provider.getChildren(testCase));
		
		
		TestSuite suite1 = new TestSuite("Suite1", 0, 2, 0, 0.001);
		TestCase testCase1_1 = new TestCase("Normal success",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0.011);
		TestCase testCase1_2 = new TestCase("Another success",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0);
		TestCase[] cases1 = {testCase1_1, testCase1_2};
		suite1.getTestCases().addAll(Arrays.asList(cases1));
		
		TestSuite suite2 = new TestSuite("Suite2", 1, 6, 2, 0);
		TestCase testCase2_1 = new TestCase("Normal success",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0.269);
		TestCase testCase2_2 = new TestCase("Another success",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0);
		
		TestCase testCase2_3 = new TestCase("Simple failure",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0);
		Failure case2_3 = new Failure("AssertionError",
				"[expect.toBe] true expected to be the same object as false",
				failure1Text);
		testCase2_3.setFailure(case2_3);
		
		TestCase testCase2_4 = new TestCase("Auch an error",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0);
		Failure case2_4_fail = new Failure("ReferenceError",
				"undefined1 is not defined", failure2Text);
		testCase2_4.setFailure(case2_4_fail);
		
		TestCase testCase2_5 = new TestCase("Async timeout",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0);
		Failure case2_5_fail = new Failure("TimeoutError",
				"test function timed out after 250ms", "");
		testCase2_5.setFailure(case2_5_fail);
		
		TestCase testCase2_6 = new TestCase("Async success",
				"Chrome 27.0.1453.81, Linux.Some dummy browser test", 0);
		TestCase[] cases2 = {testCase2_1, testCase2_2, testCase2_3, 
				testCase2_4, testCase2_5, testCase2_6};
		suite2.getTestCases().addAll(Arrays.asList(cases2));
		
		TestSuite[] suites = {suite1, suite2};
		
		assertArrayEquals(suites, provider.getChildren(root));
		assertArrayEquals(cases1, provider.getChildren(suite1));
		assertArrayEquals(cases2, provider.getChildren(suite2));
		assertNull(provider.getChildren(testCase2_5));
	}
}