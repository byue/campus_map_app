package hw5.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import hw5.Graph;
import hw5.LabeledEdge;
import hw5.test.CheckAsserts;

public class GraphTestString {

///////////////////////////////////////////////////////////////////////////////////////
//// Setup
///////////////////////////////////////////////////////////////////////////////////////
   /**
    * checks that Java asserts are enabled, and exits if not
    */
   @Before
   public void testAssertsEnabled() {
	  CheckAsserts.checkAssertsEnabled();
   }

   Random r = new Random();
   
///////////////////////////////////////////////////////////////////////////////////////
////Check Graph(String nodeData)
///////////////////////////////////////////////////////////////////////////////////////     
   @Test 
   public void checkGraph2() {
      Graph<String, String> g = new Graph<String, String>("3");
      assertEquals(0, g.numEdges());
      assertEquals(1, g.numNodes());
      assertEquals(g.getAllEdges().size(), 0);
      assertEquals(g.getAllNodeData().size(), 1);
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check Graph(List<String> nodeList)
///////////////////////////////////////////////////////////////////////////////////////     
	@Test 
	public void checkGraph3() {
		List<String> nodes = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			nodes.add("" + i);
		}
		Graph<String, String> g = new Graph<String, String>(nodes);
		assertEquals(0, g.numEdges());
		assertEquals(10, g.numNodes());
		assertEquals(g.getAllEdges().size(), 0);
		assertEquals(g.getAllNodeData().size(), 10);
	}
  
///////////////////////////////////////////////////////////////////////////////////////
////Check Graph()
///////////////////////////////////////////////////////////////////////////////////////     
	@Test 
	public void checkGraph() {
		Graph<String, String> g = new Graph<String, String>();
		assertEquals(0, g.numEdges());
		assertEquals(0, g.numNodes());
		assertEquals(g.getAllEdges().size(), 0);
		assertEquals(g.getAllNodeData().size(), 0);
	}
	  
///////////////////////////////////////////////////////////////////////////////////////
//// Check addData()
///////////////////////////////////////////////////////////////////////////////////////   
	
    @Test 
	public void nullAddData() {
    	Graph<String, String> g = new Graph<String, String>();
	    assertFalse(g.addData(null));
	}

	@Test 
   public void simpleAddData() {
		Graph<String, String> g = new Graph<String, String>();
      boolean added = g.addData("foo");
      assertTrue(added);
      assertEquals(g.numNodes(), 1);
   }
   
   @Test 
   public void duplicateAddData() {
	   Graph<String, String> g = new Graph<String, String>();
      g.addData("foo");
      assertFalse(g.addData("foo"));
   }
   
   @Test 
   public void stressAddData() {
	   Graph<String, String> g = new Graph<String, String>();
      for (int i = 1; i <= 100000; i++) {
    	  g.addData("" + i);
    	  assertEquals(g.numNodes(), i);
      }
   }
   
   @Test 
   public void multiOpAddData() {
	   Graph<String, String> g = new Graph<String, String>();
      for (int i = 0; i < 1000; i++) {
    	  g.addData("" + i);  
      }
      for (int i = 1; i <= 1000; i++) {
    	  if (i % 3 == 0) {
    		  String source = r.nextInt(1000) + "";
    		  String dest = r.nextInt(1000) + "";
    		  String label = r.nextInt(1000) + "";
    		  g.connectData(source, dest, label);
    	  } else {
    		  g.getAllEdges();
    	  }
    	  assertEquals(g.numNodes(), 1000);
      }
      for (int i = 0; i < 1000; i++) {
    	  g.addData("" + i);  
      }
      assertEquals(g.numNodes(), 1000);
      for (int i = 1000; i < 2000; i++) {
    	  g.addData("" + i);  
      }
      assertEquals(g.numNodes(), 2000);
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check connectData() / isConnected()
///////////////////////////////////////////////////////////////////////////////////////   
   @Test 
   public void simpleConnectData() {
	  Graph<String, String> g = new Graph<String, String>();
      g.addData("foo");
      assertFalse(g.isConnected("foo", "bar"));
      g.addData("bar");
      assertFalse(g.isConnected("foo", "bar"));
      g.connectData("foo", "bar", "baz");
      assertTrue(g.isConnected("foo", "bar"));
   }
   
   @Test 
   public void falseConnectData() {
	  Graph<String, String> g = new Graph<String, String>();
      g.addData("foo");
      assertFalse(g.isConnected("foo", "bar"));
      g.addData("bar");
      g.addData("baz");
      assertFalse(g.isConnected("a", "b"));
      assertTrue(g.connectData("foo", "bar", "baz"));
      assertTrue(g.isConnected("foo", "bar"));
      assertFalse(g.connectData("foo", "bar", "baz"));
      assertFalse(g.connectData("foo", null, null));
      assertFalse(g.connectData("foo", "baz", null));
      assertFalse(g.connectData(null, "baz", "a"));
      assertFalse(g.connectData("foo", null, "a"));
      assertFalse(g.connectData(null, null, "a"));
      assertFalse(g.connectData(null, null, null));
      assertTrue(g.connectData("foo", "bar", "bz"));
   }
   
   @Test 
   public void stressConnectData() {
	  Map<String, String> m = new HashMap<String, String>();
	  Graph<String, String> g = new Graph<String, String>();
      for (int i = 0; i <= 100000; i++) {
    	  g.addData(i + "");
      }
      for (int i = 1; i <= 100000; i++) {
          String source = r.nextInt(10000) + "";
    	  String dest = r.nextInt(10000) + "";
    	  
    	  String label = r.nextInt(10000) + "";
    	  g.connectData(source, dest, label);
    	  m.put(source, dest); 
      }
      for (String data1 : m.keySet()) {
    	  String data2 = m.get(data1);
    	  assertTrue(g.isConnected(data1, data2));
      }
   }
   
   @Test 
   public void selfConnected() {
	   Graph<String, String> g = new Graph<String, String>();
      g.addData("foo");
      assertFalse(g.isConnected("foo", "foo"));
      g.connectData("foo", "foo", "baz");
      assertTrue(g.isConnected("foo", "foo"));
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check getOutEdges(), also tests inner class AllEdges
/////////////////////////////////////////////////////////////////////////////////////// 
   @Test 
   public void nullGetOutEdges() {
	  Graph<String, String> g = new Graph<String, String>();
      assertNull(g.getOutEdges("a"));
      assertNull(g.getOutEdges(null));
   }
   
   @Test 
   public void verifyOutEdges() {
	  Graph<String, String> g = new Graph<String, String>();
	  for (int i = 0; i < 10; i++) {
		  g.addData(i + "");
	  }
	  g.connectData("1", "2", "a");
	  g.connectData("1", "4", "a");
	  g.connectData("1", "2", "a");
	  g.connectData("1", "2", "b");
	  g.connectData("1", "5", "a");
	  assertEquals(0, g.getOutEdges("3").size());
	  g.connectData("8", "1", "a");
	  assertEquals(4, g.getOutEdges("1").size());
	  g.connectData("2", "2", "c");
	  assertEquals(1, g.getOutEdges("2").size());
	  assertEquals(1, g.getOutEdges("8").size());
	  assertEquals(0, g.getOutEdges("9").size());
   }
   
   @Test(expected=UnsupportedOperationException.class)
   public void geOutEdgesUnmodifiableSet() {
	  Graph<String, String> g = new Graph<String, String>();
	  for (int i = 0; i < 10; i++) {
		  g.addData(i + "");
	  }
	  g.connectData("1", "2", "a");
	  g.connectData("1", "4", "a");
	  g.connectData("1", "2", "a");
	  g.connectData("1", "2", "b");
	  g.connectData("1", "5", "a");
	  g.connectData("8", "1", "a");
	  g.connectData("2", "2", "c");
	  Set<LabeledEdge<String, String>> e = g.getOutEdges("2");
      e.add(new LabeledEdge<String, String>("5", "6", "7"));
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check getInEdges(), also tests inner class AllEdges
/////////////////////////////////////////////////////////////////////////////////////// 
	@Test 
	public void nullGetInEdges() {
		Graph<String, String> g = new Graph<String, String>();
		assertNull(g.getInEdges("a"));
		assertNull(g.getInEdges(null));
	}
	
	@Test 
	public void verifyInEdges() {
		Graph<String, String> g = new Graph<String, String>();
		for (int i = 0; i < 10; i++) {
			g.addData(i + "");
		}
		g.connectData("1", "2", "a");
		g.connectData("1", "4", "a");
		g.connectData("1", "2", "a");
		g.connectData("1", "2", "b");
		g.connectData("1", "5", "a");
		assertEquals(0, g.getInEdges("3").size());
		g.connectData("8", "1", "a");
		assertEquals(1, g.getInEdges("4").size());
		assertEquals(0, g.getInEdges("9").size());
		g.connectData("2", "2", "c");
		assertEquals(3, g.getInEdges("2").size());
	}
	
	  @Test(expected=UnsupportedOperationException.class)
	   public void geInEdgesUnmodifiableSet() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  g.connectData("2", "2", "c");
		  Set<LabeledEdge<String, String>> e = g.getInEdges("1");
	      e.add(new LabeledEdge<String, String>("5", "6", "7"));
	   }
	
///////////////////////////////////////////////////////////////////////////////////////
////Check containsData()
///////////////////////////////////////////////////////////////////////////////////////    
	@Test 
	public void simplecontainsData() {
		Graph<String, String> g = new Graph<String, String>();
		for (int i = 0; i < 10; i++) {
			g.addData(i + "");
		}
		assertTrue(g.containsData("1"));
		assertTrue(g.containsData("9"));
	}
	
	@Test 
	public void doesNotContainData() {
		Graph<String, String> g = new Graph<String, String>();
		for (int i = 0; i < 10; i++) {
			g.addData(i + "");
		}
		assertFalse(g.containsData("10"));
		assertFalse(g.containsData("12"));
	}
	
	@Test 
	public void nullContainsData() {
		Graph<String, String> g = new Graph<String, String>();
		for (int i = 0; i < 10; i++) {
			g.addData(i + "");
		}
		assertFalse(g.containsData(null));
	}
	
	@Test 
	public void stressContainsData() {
		HashSet<String> a = new HashSet<String>();
		Graph<String, String> g = new Graph<String, String>();
		for (int i = 0; i < 100000; i++) {
			int num = r.nextInt(10000);
			g.addData(num + "");
			a.add("" + num);
		}
		for (String num : a) {
			assertTrue(g.containsData(num));
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Check containsEdge()
/////////////////////////////////////////////////////////////////////////////////////// 	
	@Test 
	public void containsEdgeNull() {
		Graph<String, String> g = new Graph<String, String>();
		assertFalse(g.containsEdge(null));
	}
	
	@Test 
	public void verifyContainsEdge() {
	   Graph<String, String> g = new Graph<String, String>();
	   g.addData("foo");
	   g.addData("bar");
	   g.connectData("foo", "bar", "a");
	   assertTrue(g.containsEdge(new LabeledEdge<String, String>("foo", "bar", "a")));
	   g.connectData("foo", "foo", "a");
	   assertTrue(g.containsEdge(new LabeledEdge<String, String>("foo", "foo", "a")));  
	}
	
	@Test 
	public void doesNotContainEdge() {
		Graph<String, String> g = new Graph<String, String>();
	   g.addData("foo");
	   g.addData("bar");
	   g.connectData("foo", "bar", "a");
	   assertFalse(g.containsEdge(new LabeledEdge<String, String>("foo", "bar", "b")));
	   assertFalse(g.containsEdge(new LabeledEdge<String, String>("1", "2", "3")));
	   g.connectData("foo", "foo", "a");
	   assertFalse(g.containsEdge(new LabeledEdge<String, String>("foo", "foo", "b")));   
	}
	
	 @Test 
	   public void verifyContainsEdgeLocal() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  assertTrue(g.containsEdge("1", new LabeledEdge<String, String>("1", "4", "a")));
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  assertFalse(g.containsEdge("1", new LabeledEdge<String, String>("8", "1", "b")));
		  g.connectData("2", "2", "c");
		  assertTrue(g.containsEdge("1", new LabeledEdge<String, String>("1", "2", "a")));
		  assertFalse(g.containsEdge("1", null));
		  assertFalse(g.containsEdge(null, null));
		  assertFalse(g.containsEdge("1", null));
	   }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check getAllNodeData()
/////////////////////////////////////////////////////////////////////////////////////// 
	@Test 
	public void EmptyGetAllNodeData() {
		Graph<String, String> g = new Graph<String, String>();
		assertEquals(0, g.getAllNodeData().size());
	}
	 
	 @Test 
	 public void verifyGetAllNodeData() {
		Graph<String, String> g = new Graph<String, String>();
		for (int i = 0; i < 1000; i++) {
	       g.addData(i + "");
		}
	    Set<String> a = g.getAllNodeData();
		assertEquals(1000, a.size());
		for (String num : a) {
			assertTrue(g.containsData(num));
		}
	}	 
	 
    @Test(expected=UnsupportedOperationException.class)
    public void getAllNodesUnmodifiableSet() {
       Graph<String, String> g = new Graph<String, String>();
	   for (int i = 0; i < 10; i++) {
		   g.addData(i + "");
	   }
	   g.connectData("1", "2", "a");
	   g.connectData("1", "4", "a");
	   g.connectData("1", "2", "a");
	   g.connectData("1", "2", "b");
	   g.connectData("1", "5", "a");
	   g.connectData("8", "1", "a");
	   g.connectData("2", "2", "c");
	   Set<String> e = g.getAllNodeData();
       e.add("1");
    }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check getAllEdges()
///////////////////////////////////////////////////////////////////////////////////////
	 @Test 
	   public void verifygetAllEdges() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  g.connectData("2", "2", "c");
		  Set<LabeledEdge<String, String>> e = g.getAllEdges();
		  assertEquals(6, e.size());
		  for (LabeledEdge<String, String> edge : e) {
			  assertTrue(g.containsEdge(edge));
		  }
	   }
	 
	 @Test(expected=UnsupportedOperationException.class)
	   public void getAllEdgesUnmodifiableSet() {
		 Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  g.connectData("2", "2", "c");
		  Set<LabeledEdge<String, String>> e = g.getAllEdges();
          e.add(new LabeledEdge<String, String>("5", "6", "7"));
	   }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numNodes()
/////////////////////////////////////////////////////////////////////////////////////// 
	 @Test 
	 public void zeroNumNOdes() {
		 Graph<String, String> g = new Graph<String, String>();
		 assertEquals(0, g.numNodes());
	 }
	 
	 @Test 
	 public void verifynumNodes() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  assertEquals(10, g.numNodes());
	 }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numEdges()
/////////////////////////////////////////////////////////////////////////////////////// 
	 @Test 
	   public void checknNumEdges() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  g.connectData("2", "2", "c");
		  assertEquals(6, g.numEdges());
	 }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numInEdges()
/////////////////////////////////////////////////////////////////////////////////////// 
	 @Test 
	   public void checknNumInEdges() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  g.connectData("2", "2", "c");
		  assertEquals(1, g.numInEdges("1"));
		  assertEquals(3, g.numInEdges("2"));
		  assertEquals(4, g.numOutEdges("1"));
		  assertEquals(1, g.numOutEdges("2"));  
	 }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numOutEdges()
/////////////////////////////////////////////////////////////////////////////////////// 	 
	 @Test 
	   public void checknNumOutEdges() {
		  Graph<String, String> g = new Graph<String, String>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i + "");
		  }
		  g.connectData("1", "2", "a");
		  g.connectData("1", "4", "a");
		  g.connectData("1", "2", "a");
		  g.connectData("1", "2", "b");
		  g.connectData("1", "5", "a");
		  g.connectData("8", "1", "a");
		  g.connectData("2", "2", "c");
		  assertEquals(4, g.numOutEdges("1"));
		  assertEquals(1, g.numOutEdges("2"));  
	 }
}
