package hw8.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import hw7.test.CheckAsserts;
import hw8.Coordinates;

public class CoordinatesTest {
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
		Coordinates c = new Coordinates(3.0, 4.0);
		assertTrue(c != null);
	}

///////////////////////////////////////////////////////////////////////////////////////
////Test getX()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkGetX() {
		Coordinates c = new Coordinates(9.02, 4.04);
		assertEquals(((Double) c.getX()), ((Double) 9.02));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test getY()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkGetY() {
		Coordinates c = new Coordinates(9.02, 4.04);
		assertEquals(((Double) c.getY()), ((Double) 4.04));
	}

///////////////////////////////////////////////////////////////////////////////////////
////Test hashCode()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkHashCode() {
		Coordinates c = new Coordinates(9.02, 4.04);
		Coordinates d = new Coordinates(9.02, 4.04);
		Coordinates e = new Coordinates(9.01, 4.04);
		Coordinates f = new Coordinates(9.01, 4.09);
		assertEquals(c.hashCode(), d.hashCode());
		assertTrue(c.hashCode() != e.hashCode());
		assertTrue(c.hashCode() != f.hashCode());
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test equals()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkEquals() {
		Coordinates c = new Coordinates(9.02, 4.04);
		Coordinates d = new Coordinates(9.02, 4.04);
		Coordinates e = new Coordinates(9.01, 4.04);
		Coordinates f = new Coordinates(9.01, 4.09);
		assertEquals(c, d);
		assertFalse(c.equals(e));
		assertFalse(c.equals(f));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test equals()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkCompareTo() {
		Coordinates c = new Coordinates(1.02, 1.04);
		Coordinates d = new Coordinates(3.02, 1.01);
		Coordinates e = new Coordinates(1.02, 1.04);
		assertTrue(c.compareTo(d) < 0);
		assertTrue(d.compareTo(c) > 0);
		assertEquals(c.compareTo(e), 0);
	}
}
