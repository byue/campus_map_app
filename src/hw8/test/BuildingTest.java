package hw8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import hw7.test.CheckAsserts;
import hw8.Building;
import hw8.Coordinates;

public class BuildingTest {
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
	
///////////////////////////////////////////////////////////////////////////////////////
////Test constructor()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkConstructor() {
		Building b = new Building("a", "b", 3.0, 4.7);
		assertTrue(b != null);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test getShortName()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkGetShortName() {
		Building b = new Building("a", "b", 3.0, 4.7);
		assertEquals(b.getShortName(), "a");
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test getFullName()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkGetFullName() {
		Building b = new Building("a", "b", 3.0, 4.7);
		assertEquals(b.getFullName(), "b");
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test getCoordinates()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkGetCoordinates() {
		Building b = new Building("a", "b", 3.0, 4.7);
		assertEquals(b.getCoordinates(), new Coordinates(3.0, 4.7));
	}

///////////////////////////////////////////////////////////////////////////////////////
////Test hashCode()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkHashCode() {
		Building c = new Building("a", "b", 9.02, 4.04);
		Building d = new Building("a", "b", 9.02, 4.04);
		Building e = new Building("a", "b", 9.01, 4.04);
		Building f = new Building("a", "b", 9.01, 4.09);
		Building g = new Building("g", "b", 9.02, 4.09);
		assertEquals(c.hashCode(), d.hashCode());
		assertTrue(c.hashCode() != e.hashCode());
		assertTrue(c.hashCode() != f.hashCode());
		assertTrue(c.hashCode() != g.hashCode());
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test equals()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkEquals() {
		Building c = new Building("a", "b", 9.02, 4.04);
		Building d = new Building("a", "b", 9.02, 4.04);
		Building e = new Building("a", "b", 9.01, 4.04);
		Building f = new Building("a", "b", 9.01, 4.09);
		Building g = new Building("g", "b", 9.02, 4.09);
		assertEquals(c, d);
		assertFalse(c.equals(e));
		assertFalse(c.equals(f));
		assertFalse(c.equals(g));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test equals()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkCompareTo() {
		Building c = new Building("a", "b", 9.02, 4.04);
		Building d = new Building("a", "b", 9.02, 4.04);
		Building e = new Building("a", "g", 9.01, 4.04);
		Building f = new Building("a", "b", 9.01, 4.09);
		Building g = new Building("g", "b", 9.02, 4.09);
		assertEquals(0, c.compareTo(d));
		assertEquals(0, c.compareTo(f));
		assertTrue(c.compareTo(g) < 0);
		assertTrue(g.compareTo(c) > 0);
		assertTrue(g.compareTo(f) > 0);
		assertTrue(f.compareTo(g) < 0);
	}
}
