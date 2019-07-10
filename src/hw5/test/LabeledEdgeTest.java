package hw5.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import hw5.LabeledEdge;
import hw5.test.CheckAsserts;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the LabeledEdge class.
 * <p>
 */
public final class LabeledEdgeTest {
	
///////////////////////////////////////////////////////////////////////////////////////
////Setup
///////////////////////////////////////////////////////////////////////////////////////
   /**
    * checks that Java asserts are enabled, and exits if not
    */
   @Before
   public void testAssertsEnabled() {
	  CheckAsserts.checkAssertsEnabled();
   }
   
  
  // checks behavior of checkRep after constructor is called 
  public boolean checkRepFail(String source, String dest, String label) {
	  try {
	     LabeledEdge<String, String> edge = new LabeledEdge<String, String>(source, dest, label);
	  } catch (AssertionError e) {
		  return true;
	  }
	  return false;
  }
  
///////////////////////////////////////////////////////////////////////////////////////
//// Constructor tests on valid/invalid input
///////////////////////////////////////////////////////////////////////////////////////

  @Test 
  public void checkRepConstructor() {
     assertTrue(checkRepFail(null, null, null));
     assertTrue(checkRepFail(null, "hi", null));
     assertTrue(checkRepFail(null, "hi", "hello"));
     assertFalse(checkRepFail("hello", "how", "are"));   
     assertFalse(checkRepFail("hello", "hello", "hello"));     
     assertFalse(checkRepFail("", "", ""));
  }
  
///////////////////////////////////////////////////////////////////////////////////////
//// get() tests
///////////////////////////////////////////////////////////////////////////////////////
  @Test
  public void checkGet() {
	  String source = "foo";
	  String dest = "bar";
	  String label = "baz";
	  LabeledEdge<String, String> edge = new LabeledEdge<String, String>(source, dest, label);
	  assertEquals(source, edge.getSourceData());
	  assertEquals(dest, edge.getDestData());
	  assertEquals(label, edge.getLabel());
  }
  
///////////////////////////////////////////////////////////////////////////////////////
//// equals() test
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkEquals() {
		String source = "foo";
		String dest = "bar";
		String label = "baz";
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge3 = new LabeledEdge<String, String>(source, dest, label);
		assertTrue(edge1.equals(edge2));
		assertTrue(edge2.equals(edge1));
		assertTrue(edge2.equals(edge3));
		assertTrue(edge1.equals(edge3));
		assertTrue(edge3.equals(edge2));
		assertTrue(edge3.equals(edge1));
	}  
	
	@Test
	public void checkNotEquals() {
		String source = "foo";
		String dest = "bar";
		String label = "baz";
		String source2 = "foo1";
		String dest2 = "bar1";
		String label2 = "baz1";
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>(source2, dest, label);
		LabeledEdge<String, String> edge3 = new LabeledEdge<String, String>(source, dest2, label);
		LabeledEdge<String, String> edge4 = new LabeledEdge<String, String>(source, dest, label2);
		assertFalse(edge1.equals(edge2));
		assertFalse(edge1.equals(edge3));
		assertFalse(edge1.equals(edge4));
		assertFalse(edge2.equals(edge1));
		assertFalse(edge3.equals(edge1));
		assertFalse(edge4.equals(edge1));
	}  
	
///////////////////////////////////////////////////////////////////////////////////////
//// hashCode() tests
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void sameHashCode() {
		String source = "foo";
		String dest = "bar";
		String label = "baz";
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge3 = new LabeledEdge<String, String>(source, dest, label);
		assertEquals(edge1.hashCode(), edge2.hashCode());
		assertEquals(edge2.hashCode(), edge3.hashCode());
		assertEquals(edge1.hashCode(), edge3.hashCode());
	}  
	
	@Test
	public void differentHashCode() {
		String source = "foo";
		String dest = "bar";
		String label = "baz";
		String source2 = "foo1";
		String dest2 = "bar1";
		String label2 = "baz1";
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>(source2, dest, label);
		LabeledEdge<String, String> edge3 = new LabeledEdge<String, String>(source, dest2, label);
		LabeledEdge<String, String> edge4 = new LabeledEdge<String, String>(source, dest, label2);
		assertFalse(edge1.hashCode() == edge2.hashCode());
		assertFalse(edge1.hashCode() == edge3.hashCode());
		assertFalse(edge1.hashCode() == edge4.hashCode());
	} 
	
	@Test
	public void equalsimpliesSameHash() {
		String source = "foo";
		String dest = "bar";
		String label = "baz";
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>(source, dest, label);
		assertTrue(edge1.equals(edge2));
		assertEquals(edge1.hashCode(), edge2.hashCode());
	} 
	
	@Test
	public void addToHashSet() {
		String source = "foo";
		String dest = "bar";
		String label = "baz";
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>(source, dest, label);
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>(source, dest, label);
		assertTrue(edge1 != edge2);
		Set<LabeledEdge<String, String>> a = new HashSet<LabeledEdge<String, String>>();
		a.add(edge1);
		assertEquals(1, a.size());
		String source2 = "foo1";
		String dest2 = "bar1";
		String label2 = "baz1";
		LabeledEdge<String, String> edge3 = new LabeledEdge<String, String>(source2, dest, label);
		LabeledEdge<String, String> edge4 = new LabeledEdge<String, String>(source, dest2, label);
		LabeledEdge<String, String> edge5 = new LabeledEdge<String, String>(source, dest, label2);
		a.add(edge3);
		a.add(edge4);
		a.add(edge5);
		assertEquals(4, a.size());
	} 
}
