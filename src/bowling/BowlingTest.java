package bowling;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stev.bowling.Game;
import stev.bowling.NormalFrame;
import stev.bowling.LastFrame;
import stev.bowling.Frame;
import stev.bowling.BowlingException;




/**
 * 
 * @author ethicnology.
 */
public class BowlingTest {
	private Game game;

	/**
	 * Initialize a global Game variable.
	 */
	@Before
	public void setUp() throws Exception {
		this.game = new Game();
	}

	/**
	 * NormalFrameSuccess should success with valid integers, between 1 to 9.
	 */
	@Test
	public void normalFrameSuccess() {
		assertTrue(new NormalFrame(1) instanceof Frame);
		assertTrue(new NormalFrame(2) instanceof Frame);
		assertTrue(new NormalFrame(3) instanceof Frame);
		assertTrue(new NormalFrame(4) instanceof Frame);
		assertTrue(new NormalFrame(5) instanceof Frame);
		assertTrue(new NormalFrame(6) instanceof Frame);
		assertTrue(new NormalFrame(7) instanceof Frame);
		assertTrue(new NormalFrame(8) instanceof Frame);
		assertTrue(new NormalFrame(9) instanceof Frame);
	}

	/**
	 * NormalFrameFailure should throw a BowlingException with a non-valid integer
	 * below 1 or over 9 (10 should works only with LastFrame).
	 */
	@Test(expected = BowlingException.class)
	public void normalFrameFailure() throws Exception {
		new NormalFrame(0);
		new NormalFrame(10); // Should fail -> LastFrame
	}

	/**
	 * LastFrameSuccess should success with 10 a.k.a. last frame.
	 */
	@Test
	public void lastFrameSuccess() {
		assertTrue(new LastFrame(10) instanceof Frame);
	}

	/**
	 * LastFrameException should throw exception if value != 10.
	 */
	@Test(expected = BowlingException.class)
	public void lastFrameFailure() throws Exception {
		new LastFrame(0);
		new LastFrame(1);
		new LastFrame(2);
		new LastFrame(3);
		new LastFrame(4);
		new LastFrame(5);
		new LastFrame(6);
		new LastFrame(7);
		new LastFrame(8);
		new LastFrame(9);
	}
	
	/**
	 * TODO
	 */
	@Test
	public void setPinsDownSuccess() {
		assertEquals("36", new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6).toString());
		assertEquals("X ", new NormalFrame(2).setPinsDown(1, 10).toString());
		assertEquals("--", new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 0).toString());
		assertEquals("- ", new NormalFrame(7).setPinsDown(1, 0).toString());
	}
	
	/**
	 * setPinsDownFailure should throw exception because it revert the throw order (2 instead of 1) and because it use non-valid value such as 3.
	 */
	@Test(expected = BowlingException.class)
	public void setPinsDownFailure() {
		new NormalFrame(1).setPinsDown(2, 3).setPinsDown(1, 6);
		new NormalFrame(1).setPinsDown(3, 3);
	}


	
}
