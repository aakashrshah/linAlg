import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BombearLinTest {

	BombearLinTool linTool = new BombearLinTool();
	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
    @DisplayName("😱")
	void testApproxEqualsDoubleArrayDoubleArrayDouble() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testCosine() {
		System.out.println("Testing complex-number implementation:\n");
		double u[] = {2.0,2.0};
		double v[] = {0.0,3.0};
		double diff = 0;
		assertEquals(0.70573, linTool.cosine(u, v) , diff);
//		assertTrue("0.7057383".equals(),
//	            () -> "Failed Test: Null");
		System.out.println("  => Passed Test 1: hardcoded\n");
	}

	@Test
	void testVectorLeftMult() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testFrobeniusNorm() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testApproxEqualsDoubleArrayArrayDoubleArrayArrayDouble() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetColumnAsVector() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetRowAsVector() {
		fail("Not yet implemented"); // TODO
	}

}
