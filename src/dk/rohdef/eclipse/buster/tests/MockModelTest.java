package dk.rohdef.eclipse.buster.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.rohdef.eclipse.buster.models.Failure;
import dk.rohdef.eclipse.buster.models.MockModel;
import dk.rohdef.eclipse.buster.models.RootTestSuite;
import dk.rohdef.eclipse.buster.models.TestCase;
import dk.rohdef.eclipse.buster.models.TestSuite;

public class MockModelTest {
	private List<TestSuite> suites;
	private RootTestSuite suite;
	private MockModel model;
	
	@Before
	public void setUp() {
		model = new MockModel();
		suite = model.getSuite(new File("resources/test-result.xml"));
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
