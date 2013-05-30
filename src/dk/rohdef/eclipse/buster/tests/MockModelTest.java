package dk.rohdef.eclipse.buster.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.rohdef.eclipse.buster.models.Failure;
import dk.rohdef.eclipse.buster.models.MockModel;
import dk.rohdef.eclipse.buster.models.RootTestSuite;
import dk.rohdef.eclipse.buster.models.TestCase;
import dk.rohdef.eclipse.buster.models.TestSuite;

public class MockModelTest {
	public static final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" +
			"<testsuites>\r\n" +
			"    <testsuite errors=\"0\" tests=\"2\" time=\"0.001\" failures=\"0\" name=\"Suite1\">\r\n" +
			"        <testcase time=\"0.011\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Normal success\"/>\r\n" +
			"        <testcase time=\"0\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Another success\"/>\r\n" +
			"    </testsuite>\r\n" +
			"    <testsuite errors=\"1\" tests=\"6\" time=\"0\" failures=\"2\" name=\"Suite2\">\r\n" +
			"        <testcase time=\"0.269\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Normal success\"/>\r\n" +
			"        <testcase time=\"0\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Another success\"/>\r\n" +
			"        <testcase time=\"0\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Simple failure\">\r\n" +
			"            <failure type=\"AssertionError\" message=\"[expect.toBe] true expected to be the same object as false\">\r\n" +
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
			"            </failure>\r\n" +
			"        </testcase>\r\n" +
			"        <testcase time=\"0\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Auch an error\">\r\n" +
			"            <failure type=\"ReferenceError\" message=\"undefined1 is not defined\">\r\n" +
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
			"            </failure>\r\n" +
			"        </testcase>\r\n" +
			"        <testcase time=\"0\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Async timeout\">\r\n" +
			"            <failure type=\"TimeoutError\" message=\"test function timed out after 250ms\"></failure>\r\n" +
			"        </testcase>\r\n" +
			"        <testcase time=\"0\" classname=\"Chrome 27.0.1453.81, Linux.Some dummy browser test\" name=\"Async success\"/>\r\n" +
			"    </testsuite>\r\n" +
			"</testsuites> ";
	private List<TestSuite> suites;
	private RootTestSuite suite;
	private MockModel model;
	
	@Before
	public void setUp() {
		model = new MockModel();
		suite = model.getSuite(xml);
		suites = suite.getSuites();
	}
	
	@Test
	public void testGetSuiteStats() {
		assertEquals(8, suite.getTests());
		assertEquals(1, suite.getErrors());
		assertEquals(2, suite.getFailues());
	}
	
	@Test
	public void testGetSuitesSuiteList() {
		assertEquals(2, suites.size());
		
		TestSuite suite1 = suites.get(0);
		TestSuite suite2 = suites.get(1);
		
		assertEquals("Suite1", suite1.getName());
		assertEquals("Suite 1 Test count", 2, suite1.getTests());
		assertEquals("Suite 1 Failure count", 0, suite1.getFailures());
		assertEquals("Suite 1 Error count", 0, suite1.getErrors());
		
		assertEquals("Suite2", suite2.getName());
		assertEquals("Suite 2 Test count", 6, suite2.getTests());
		assertEquals("Suite 2 Failure count", 2, suite2.getFailures());
		assertEquals("Suite 2 Error count", 1, suite2.getErrors());
	}
	
	@Test
	public void testGetSuitesSuite1() {
		TestSuite suite1 = suites.get(0);
		assertEquals("Suite1", suite1.getName());
		
		List<TestCase> testCases = suite1.getTestCases();
		assertEquals(2, testCases.size());
		assertEquals("Normal success", testCases.get(0).getName());
		assertEquals("Another success", testCases.get(1).getName());
	}
	
	@Test
	public void testGetSuitesSuite2() {
		TestSuite suite2 = suites.get(1);
		assertEquals("Suite2", suite2.getName());
		
		List<TestCase> testCases = suite2.getTestCases();
		assertEquals(6, testCases.size());
		assertEquals("Normal success", testCases.get(0).getName());
		assertEquals("Another success", testCases.get(1).getName());
		assertEquals("Simple failure", testCases.get(2).getName());
		assertEquals("Auch an error", testCases.get(3).getName());
		assertEquals("Async timeout", testCases.get(4).getName());
		assertEquals("Async success", testCases.get(5).getName());
	}
	
	@Test
	public void testGetSuitesTestCaseSuccesses() {
		// Names is already being tested in testGetSuitesSuite2
		TestSuite suite2 = suites.get(1);
		
		TestCase successTest;
		successTest = suite2.getTestCases().get(0);
		assertEquals("Chrome 27.0.1453.81, Linux.Some dummy browser test", successTest.getClassName());
		assertEquals(0.269, successTest.getTime(), 0.01);
		assertNull(successTest.getFailure());
		
		successTest = suite2.getTestCases().get(1);
		assertEquals("Chrome 27.0.1453.81, Linux.Some dummy browser test", successTest.getClassName());
		assertEquals(0.0, successTest.getTime(), 0.01);
		assertNull(successTest.getFailure());
		
		successTest = suite2.getTestCases().get(5);
		assertEquals("Chrome 27.0.1453.81, Linux.Some dummy browser test", successTest.getClassName());
		assertEquals(0.0, successTest.getTime(), 0.01);
		assertNull(successTest.getFailure());
	}
	
	@Test
	public void testGetSuitesTestCaseFailures() {
		// Names is already being tested in testGetSuitesSuite2
		TestSuite suite2 = suites.get(1);
		
		TestCase failureTest;
		failureTest = suite2.getTestCases().get(2);
		assertEquals("Chrome 27.0.1453.81, Linux.Some dummy browser test", failureTest.getClassName());
		assertEquals(0.0, failureTest.getTime(), 0.01);
		assertNotNull(failureTest.getFailure());
		System.out.println(failureTest.getFailure().getText());
		
		failureTest = suite2.getTestCases().get(3);
		assertEquals("Chrome 27.0.1453.81, Linux.Some dummy browser test", failureTest.getClassName());
		assertEquals(0.0, failureTest.getTime(), 0.01);
		assertNotNull(failureTest.getFailure());
		
		failureTest = suite2.getTestCases().get(4);
		assertEquals("Chrome 27.0.1453.81, Linux.Some dummy browser test", failureTest.getClassName());
		assertEquals(0.0, failureTest.getTime(), 0.01);
		assertNotNull(failureTest.getFailure());
	}
	
	@Test
	public void testGetSuitesFailures() {
		TestSuite suite2 = suites.get(1);
		
		Failure failure;
		failure = suite2.getTestCases().get(2).getFailure();
		assertEquals("AssertionError", failure.getType());
		assertEquals("[expect.toBe] true expected to be the same object as false", failure.getMessage());
		assertTrue(failure.getText().length()>0);
		
		failure = suite2.getTestCases().get(3).getFailure();
		assertEquals("ReferenceError", failure.getType());
		assertEquals("undefined1 is not defined", failure.getMessage());
		assertTrue(failure.getText().length()>0);
		
		failure = suite2.getTestCases().get(4).getFailure();
		assertEquals("TimeoutError", failure.getType());
		assertEquals("test function timed out after 250ms", failure.getMessage());
		assertEquals(0, failure.getText().length());
		assertEquals("", failure.getText());
	}
}
