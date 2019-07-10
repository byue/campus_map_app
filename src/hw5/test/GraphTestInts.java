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

public class GraphTestInts {

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
      Graph<Integer, Integer> g = new Graph<Integer, Integer>(3);
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
		List<Integer> nodes = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			nodes.add(i);
		}
		Graph<Integer, Integer> g = new Graph<Integer, Integer>(nodes);
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
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
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
    	Graph<Integer, Integer> g = new Graph<Integer, Integer>();
	    assertFalse(g.addData(null));
	}

	@Test 
   public void simpleAddData() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      boolean added = g.addData(1);
      assertTrue(added);
      assertEquals(g.numNodes(), 1);
   }
   
   @Test 
   public void duplicateAddData() {
	   Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      g.addData(1);
      assertFalse(g.addData(1));
   }
   
   @Test 
   public void stressAddData() {
	   Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      for (int i = 1; i <= 100000; i++) {
    	  g.addData(i);
    	  assertEquals(g.numNodes(), i);
      }
   }
   
   @Test 
   public void multiOpAddData() {
	   Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      for (int i = 0; i < 1000; i++) {
    	  g.addData(i);  
      }
      for (int i = 1; i <= 1000; i++) {
    	  if (i % 3 == 0) {
    		  int source = r.nextInt(1000);
    		  int dest = r.nextInt(1000);
    		  int label = r.nextInt(1000);
    		  g.connectData(source, dest, label);
    	  } else {
    		  g.getAllEdges();
    	  }
    	  assertEquals(g.numNodes(), 1000);
      }
      for (int i = 0; i < 1000; i++) {
    	  g.addData(i);  
      }
      assertEquals(g.numNodes(), 1000);
      for (int i = 1000; i < 2000; i++) {
    	  g.addData(i);  
      }
      assertEquals(g.numNodes(), 2000);
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check connectData() / isConnected()
///////////////////////////////////////////////////////////////////////////////////////   
   @Test 
   public void simpleConnectData() {
	  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      g.addData(1);
      assertFalse(g.isConnected(1, 2));
      g.addData(2);
      assertFalse(g.isConnected(1, 2));
      g.connectData(1, 2, 3);
      assertTrue(g.isConnected(1, 2));
   }
   
   @Test 
   public void falseConnectData() {
	  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      g.addData(1);
      assertFalse(g.isConnected(1, 2));
      g.addData(2);
      g.addData(3);
      assertFalse(g.isConnected(5, 7));
      assertTrue(g.connectData(1, 2, 3));
      assertTrue(g.isConnected(1, 2));
      assertFalse(g.connectData(1, 2, 3));
      assertFalse(g.connectData(1, null, null));
      assertFalse(g.connectData(1, 3, null));
      assertFalse(g.connectData(null, 3, 9));
      assertFalse(g.connectData(1, null, 9));
      assertFalse(g.connectData(null, null, 9));
      assertFalse(g.connectData(null, null, null));
      assertTrue(g.connectData(1, 2, 89));
   }
   
   @Test 
   public void stressConnectData() {
	  Map<Integer, Integer> m = new HashMap<Integer, Integer>();
	  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      for (int i = 0; i <= 100000; i++) {
    	  g.addData(i);
      }
      for (int i = 1; i <= 100000; i++) {
          int source = r.nextInt(10000);
    	  int dest = r.nextInt(10000);
    	  
    	  int label = r.nextInt(10000);
    	  g.connectData(source, dest, label);
    	  m.put(source, dest); 
      }
      for (int data1 : m.keySet()) {
    	  int data2 = m.get(data1);
    	  assertTrue(g.isConnected(data1, data2));
      }
   }
   
   @Test 
   public void selfConnected() {
	   Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      g.addData(1);
      assertFalse(g.isConnected(1, 1));
      g.connectData(1, 1, 3);
      assertTrue(g.isConnected(1, 1));
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check getOutEdges(), also tests inner class AllEdges
/////////////////////////////////////////////////////////////////////////////////////// 
   @Test 
   public void nullGetOutEdges() {
	  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
      assertNull(g.getOutEdges(1));
      assertNull(g.getOutEdges(null));
   }
   
   @Test 
   public void verifyOutEdges() {
	  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
	  for (int i = 0; i < 10; i++) {
		  g.addData(i);
	  }
	  g.connectData(1, 2, 88);
	  g.connectData(1, 4, 88);
	  g.connectData(1, 2, 88);
	  g.connectData(1, 2, 88);
	  g.connectData(1, 5, 88);
	  assertEquals(0, g.getOutEdges(3).size());
	  g.connectData(8, 1, 88);
	  assertEquals(3, g.getOutEdges(1).size());
	  g.connectData(2, 2, 89);
	  assertEquals(1, g.getOutEdges(2).size());
	  assertEquals(1, g.getOutEdges(8).size());
	  assertEquals(0, g.getOutEdges(9).size());
   }
   
   @Test(expected=UnsupportedOperationException.class)
   public void geOutEdgesUnmodifiableSet() {
	  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
	  for (int i = 0; i < 10; i++) {
		  g.addData(i);
	  }
	  g.connectData(1, 2, 77);
	  g.connectData(1, 4, 77);
	  g.connectData(1, 2, 77);
	  g.connectData(1, 2, 88);
	  g.connectData(1, 5, 77);
	  g.connectData(8, 1, 77);
	  g.connectData(2, 2, 99);
	  Set<LabeledEdge<Integer, Integer>> e = g.getOutEdges(2);
      e.add(new LabeledEdge<Integer, Integer>(5, 6, 7));
   }
   
///////////////////////////////////////////////////////////////////////////////////////
////Check getInEdges(), also tests inner class AllEdges
/////////////////////////////////////////////////////////////////////////////////////// 
	@Test 
	public void nullGetInEdges() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		assertNull(g.getInEdges(2));
		assertNull(g.getInEdges(null));
	}
	
	@Test 
	public void verifyInEdges() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			g.addData(i);
		}
		g.connectData(1, 2, 88);
		g.connectData(1, 4, 88);
		g.connectData(1, 2, 88);
		g.connectData(1, 2, 99);
		g.connectData(1, 5, 99);
		assertEquals(0, g.getInEdges(3).size());
		g.connectData(8, 1, 88);
		assertEquals(1, g.getInEdges(4).size());
		assertEquals(0, g.getInEdges(9).size());
		g.connectData(2, 2, 77);
		assertEquals(3, g.getInEdges(2).size());
	}
	
	  @Test(expected=UnsupportedOperationException.class)
	   public void geInEdgesUnmodifiableSet() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 77);
		  g.connectData(1, 4, 77);
		  g.connectData(1, 2, 77);
		  g.connectData(1, 2, 88);
		  g.connectData(1, 5, 77);
		  g.connectData(8, 1, 77);
		  g.connectData(2, 2, 99);
		  Set<LabeledEdge<Integer, Integer>> e = g.getInEdges(1);
	      e.add(new LabeledEdge<Integer, Integer>(5, 6, 7));
	   }
	
///////////////////////////////////////////////////////////////////////////////////////
////Check containsData()
///////////////////////////////////////////////////////////////////////////////////////    
	@Test 
	public void simplecontainsData() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			g.addData(i);
		}
		assertTrue(g.containsData(1));
		assertTrue(g.containsData(9));
	}
	
	@Test 
	public void doesNotContainData() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			g.addData(i);
		}
		assertFalse(g.containsData(10));
		assertFalse(g.containsData(12));
	}
	
	@Test 
	public void nullContainsData() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			g.addData(i);
		}
		assertFalse(g.containsData(null));
	}
	
	@Test 
	public void stressContainsData() {
		HashSet<Integer> a = new HashSet<Integer>();
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		for (int i = 0; i < 100000; i++) {
			int num = r.nextInt(10000);
			g.addData(num);
			a.add(num);
		}
		for (Integer num : a) {
			assertTrue(g.containsData(num));
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Check containsEdge()
/////////////////////////////////////////////////////////////////////////////////////// 	
	@Test 
	public void containsEdgeNull() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		assertFalse(g.containsEdge(null));
	}
	
	@Test 
	public void verifyContainsEdge() {
	   Graph<Integer, Integer> g = new Graph<Integer, Integer>();
	   g.addData(1);
	   g.addData(2);
	   g.connectData(1, 2, 3);
	   assertTrue(g.containsEdge(new LabeledEdge<Integer, Integer>(1, 2, 3)));
	   g.connectData(1, 1, 3);
	   assertTrue(g.containsEdge(new LabeledEdge<Integer, Integer>(1, 2, 3)));  
	}
	
	@Test 
	public void doesNotContainEdge() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
	   g.addData(1);
	   g.addData(2);
	   g.connectData(1, 2, 3);
	   assertFalse(g.containsEdge(new LabeledEdge<Integer, Integer>(1, 2, 4)));
	   assertFalse(g.containsEdge(new LabeledEdge<Integer, Integer>(1, 2, 9)));
	   g.connectData(1, 1, 3);
	   assertFalse(g.containsEdge(new LabeledEdge<Integer, Integer>(1, 1, 4)));   
	}
	
	 @Test 
	   public void verifyContainsEdgeLocal() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 99);
		  g.connectData(1, 4, 99);
		  g.connectData(1, 2, 99);
		  g.connectData(1, 2, 100);
		  assertTrue(g.containsEdge(1, new LabeledEdge<Integer, Integer>(1, 4, 99)));
		  g.connectData(1, 5, 99);
		  g.connectData(8, 1, 99);
		  assertFalse(g.containsEdge(1, new LabeledEdge<Integer, Integer>(8, 1, 100)));
		  g.connectData(2, 2, 101);
		  assertTrue(g.containsEdge(1, new LabeledEdge<Integer, Integer>(1, 2, 99)));
		  assertFalse(g.containsEdge(1, null));
		  assertFalse(g.containsEdge(null, null));
		  assertFalse(g.containsEdge(1, null));
	   }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check getAllNodeData()
/////////////////////////////////////////////////////////////////////////////////////// 
	@Test 
	public void EmptyGetAllNodeData() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		assertEquals(0, g.getAllNodeData().size());
	}
	 
	 @Test 
	 public void verifyGetAllNodeData() {
		Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		for (int i = 0; i < 1000; i++) {
	       g.addData(i);
		}
	    Set<Integer> a = g.getAllNodeData();
		assertEquals(1000, a.size());
		for (Integer num : a) {
			assertTrue(g.containsData(num));
		}
	}	 
	 
    @Test(expected=UnsupportedOperationException.class)
    public void getAllNodesUnmodifiableSet() {
       Graph<Integer, Integer> g = new Graph<Integer, Integer>();
	   for (int i = 0; i < 10; i++) {
		   g.addData(i);
	   }
	   g.connectData(1, 2, 100);
	   g.connectData(1, 4, 100);
	   g.connectData(1, 2, 100);
	   g.connectData(1, 2, 101);
	   g.connectData(1, 5, 100);
	   g.connectData(8, 1, 100);
	   g.connectData(2, 2, 102);
	   Set<Integer> e = g.getAllNodeData();
       e.add(1);
    }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check getAllEdges()
///////////////////////////////////////////////////////////////////////////////////////
	 @Test 
	   public void verifygetAllEdges() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 100);
		  g.connectData(1, 4, 100);
		  g.connectData(1, 2, 100);
		  g.connectData(1, 2, 101);
		  g.connectData(1, 5, 100);
		  g.connectData(8, 1, 100);
		  g.connectData(2, 2, 102);
		  Set<LabeledEdge<Integer, Integer>> e = g.getAllEdges();
		  assertEquals(6, e.size());
		  for (LabeledEdge<Integer, Integer> edge : e) {
			  assertTrue(g.containsEdge(edge));
		  }
	   }
	 
	 @Test(expected=UnsupportedOperationException.class)
	   public void getAllEdgesUnmodifiableSet() {
		 Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 100);
		  g.connectData(1, 4, 100);
		  g.connectData(1, 2, 100);
		  g.connectData(1, 2, 101);
		  g.connectData(1, 5, 100);
		  g.connectData(8, 1, 100);
		  g.connectData(2, 2, 102);
		  Set<LabeledEdge<Integer, Integer>> e = g.getAllEdges();
          e.add(new LabeledEdge<Integer, Integer>(5, 6, 7));
	   }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numNodes()
/////////////////////////////////////////////////////////////////////////////////////// 
	 @Test 
	 public void zeroNumNOdes() {
		 Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		 assertEquals(0, g.numNodes());
	 }
	 
	 @Test 
	 public void verifynumNodes() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  assertEquals(10, g.numNodes());
	 }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numEdges()
/////////////////////////////////////////////////////////////////////////////////////// 
	 @Test 
	   public void checknNumEdges() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 100);
		  g.connectData(1, 4, 100);
		  g.connectData(1, 2, 100);
		  g.connectData(1, 2, 101);
		  g.connectData(1, 5, 100);
		  g.connectData(8, 1, 100);
		  g.connectData(2, 2, 102);
		  assertEquals(6, g.numEdges());
	 }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numInEdges()
/////////////////////////////////////////////////////////////////////////////////////// 
	 @Test 
	   public void checknNumInEdges() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 100);
		  g.connectData(1, 4, 100);
		  g.connectData(1, 2, 100);
		  g.connectData(1, 2, 101);
		  g.connectData(1, 5, 100);
		  g.connectData(8, 1, 100);
		  g.connectData(2, 2, 102);
		  assertEquals(1, g.numInEdges(1));
		  assertEquals(3, g.numInEdges(2));
		  assertEquals(4, g.numOutEdges(1));
		  assertEquals(1, g.numOutEdges(2));  
	 }
	 
///////////////////////////////////////////////////////////////////////////////////////
////Check numOutEdges()
/////////////////////////////////////////////////////////////////////////////////////// 	 
	 @Test 
	   public void checknNumOutEdges() {
		  Graph<Integer, Integer> g = new Graph<Integer, Integer>();
		  for (int i = 0; i < 10; i++) {
			  g.addData(i);
		  }
		  g.connectData(1, 2, 100);
		  g.connectData(1, 4, 100);
		  g.connectData(1, 2, 100);
		  g.connectData(1, 2, 101);
		  g.connectData(1, 5, 100);
		  g.connectData(8, 1, 100);
		  g.connectData(2, 2, 102);
		  assertEquals(4, g.numOutEdges(1));
		  assertEquals(1, g.numOutEdges(2));  
	 }
}
