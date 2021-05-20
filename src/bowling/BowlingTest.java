package bowling;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stev.bowling.Game;
import stev.bowling.NormalFrame;
import stev.bowling.LastFrame;
import stev.bowling.Frame;
import stev.bowling.BowlingException;


public class BowlingTest {
	private Game game;

	/**
	 * Initialize a global Game variable.
	 */
	@Before
	public void setUp() throws Exception {
		this.game = new Game();
	}

	
	/******************************************
	 * TESTS ON CLASS NORMAL FRAME
	 ******************************************/

	/**
	 * NormalFrameSuccess should success with valid integers, between 1 to 9.
	 */
	@Test
	public void normalFrameSuccess() {
		for(int frameNumber = 1; frameNumber < 10; frameNumber++) {
			assertTrue("valid normal frame number " + frameNumber, new NormalFrame(frameNumber) instanceof Frame);
		}
		/*assertTrue(new NormalFrame(1) instanceof Frame);
		assertTrue(new NormalFrame(2) instanceof Frame);
		assertTrue(new NormalFrame(3) instanceof Frame);
		assertTrue(new NormalFrame(4) instanceof Frame);
		assertTrue(new NormalFrame(5) instanceof Frame);
		assertTrue(new NormalFrame(6) instanceof Frame);
		assertTrue(new NormalFrame(7) instanceof Frame);
		assertTrue(new NormalFrame(8) instanceof Frame);
		assertTrue(new NormalFrame(9) instanceof Frame);*/
	}

	/**
	 * NormalFrameFailure should throw a BowlingException with a non-valid integer
	 * below 1 or over 9 (10 should works only with LastFrame).
	 */
	@Test
	public void normalFrameFailure() {
		assertThrows("invalid normal frame number -1", BowlingException.class, () -> { new NormalFrame(-1); });
		assertThrows("invalid normal frame number 0", BowlingException.class, () -> { new NormalFrame(0); });
		assertThrows("invalid normal frame number 10", BowlingException.class, () -> { new NormalFrame(10); });
		assertThrows("invalid normal frame number 11", BowlingException.class, () -> { new NormalFrame(11); });
	}

	/**
	 * verify that getFrameNumber returns frame number passed in the constructor (for normal frame)
	 */
	@Test
	public void normalFrameGetFrameNumberValid() {
		for(int frameNumber = 1; frameNumber < 10; frameNumber++) {
			assertEquals("NormalFrame - getFrameNumber returns passed value for" + frameNumber,new NormalFrame(frameNumber).getFrameNumber(), frameNumber);
		}
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
		// todo replace with assert throws ?
		new NormalFrame(1).setPinsDown(2, 3).setPinsDown(1, 6);
		new NormalFrame(1).setPinsDown(3, 3);
	}

	
	/******************************************
	 * TESTS ON CLASS LAST FRAME
	 ******************************************/

	/**
	 * LastFrameSuccess should success with 10 a.k.a. last frame.
	 */
	@Test
	public void lastFrameSuccess() {
		assertTrue("valid last frame number 10", new LastFrame(10) instanceof Frame);
	}

	/**
	 * LastFrameException should throw exception if value != 10.
	 */
	@Test
	public void lastFrameFailure() {
		assertThrows("invalid last frame number -1", BowlingException.class, () -> { new LastFrame(-1); });
		assertThrows("invalid last frame number 0", BowlingException.class, () -> { new LastFrame(0); });
		assertThrows("invalid last frame number 1", BowlingException.class, () -> { new LastFrame(1); });
		assertThrows("invalid last frame number 2", BowlingException.class, () -> { new LastFrame(2); });
		assertThrows("invalid last frame number 3", BowlingException.class, () -> { new LastFrame(3); });
		assertThrows("invalid last frame number 4", BowlingException.class, () -> { new LastFrame(4); });
		assertThrows("invalid last frame number 5", BowlingException.class, () -> { new LastFrame(5); });
		assertThrows("invalid last frame number 6", BowlingException.class, () -> { new LastFrame(6); });
		assertThrows("invalid last frame number 7", BowlingException.class, () -> { new LastFrame(7); });
		assertThrows("invalid last frame number 8", BowlingException.class, () -> { new LastFrame(8); });
		assertThrows("invalid last frame number 9", BowlingException.class, () -> { new LastFrame(9); });
		assertThrows("invalid last frame number 11", BowlingException.class, () -> { new LastFrame(11); });
	}

	/**
	 * verify that getFrameNumber returns frame number passed in the constructor (for last frame)
	 */
	@Test
	public void lastFrameGetFrameNumberValid() {
		assertEquals("LastFrame - getFrameNumber returns passed value for 10",new LastFrame(10).getFrameNumber(), 10);
	}

	
	/******************************************
	 * TESTS ON CLASS GAME
	 ******************************************/
	
	/**
	 * test that a normal game initialization
	 */
	@Test
	public void gameInitializationTestSuccess() {
		Game game = new Game();
		
		assertEquals("new game has no frame", game.m_frames.size(), 0);

		game.addFrame(new NormalFrame(1));
		game.addFrame(new NormalFrame(2));
		game.addFrame(new NormalFrame(3));
		game.addFrame(new NormalFrame(4));
		game.addFrame(new NormalFrame(5));
		game.addFrame(new NormalFrame(6));
		game.addFrame(new NormalFrame(7));
		game.addFrame(new NormalFrame(8));
		game.addFrame(new NormalFrame(9));
		game.addFrame(new LastFrame(10));
		
		assertEquals("initialized game has 10 frames", game.m_frames.size(), 10);
	}

	
	/**
	 * test that we can't add a normal frame instead of a last frame (at the 10th position)
	 */
	@Test
	public void gameAddNormalFrameInsteadOfLastFrame() {
		Game game = new Game();

		game.addFrame(new NormalFrame(1));
		game.addFrame(new NormalFrame(2));
		game.addFrame(new NormalFrame(3));
		game.addFrame(new NormalFrame(4));
		game.addFrame(new NormalFrame(5));
		game.addFrame(new NormalFrame(6));
		game.addFrame(new NormalFrame(7));
		game.addFrame(new NormalFrame(8));
		game.addFrame(new NormalFrame(9));
		assertThrows("invalid 10th frame (isn't a last frame)", BowlingException.class, () -> { game.addFrame(new NormalFrame(10)); });
		
		assertEquals("invalid frame isn't added", game.m_frames.size(), 9); // last frame isn't added
	}
	
	/**
	 * test that we can't add a last frame instead of a normal frame (first to the 9th position)
	 */
	@Test
	public void gameAddLastFrameInsteadOfNormalFrame() {
		for(int invalidFrameNumber = 1; invalidFrameNumber < 10; invalidFrameNumber++) {
			Game game = new Game();
			
			for(int frameNumber = 1; frameNumber < 11; frameNumber++) {
				if(frameNumber != invalidFrameNumber) {
					if(frameNumber == 10)
						game.addFrame(new LastFrame(10));
					else
						game.addFrame(new NormalFrame(frameNumber));
				}
				else {
					String message = "invalid " + frameNumber + " th frame shouldn't be last frame";
					int oldFrameCount = game.m_frames.size();
					assertThrows(message, BowlingException.class, () -> { game.addFrame(new LastFrame(10)); });
					assertEquals("invalid frame isn't added", game.m_frames.size(), oldFrameCount); // last frame isn't added
					break; // no need to continue this invalid number
				}
			}
		}
	}
	
	/**
	 * test that cumulative score is 0 when done on empty frames
	 */
	@Test
	public void gameGetCumulativeScoreShouldBe0WithEmptyFrames() {
		Game game = generateEmptyGameWithValidFrames();
		
		for(Frame frame: game.m_frames) {
			int frameNumber = frame.getFrameNumber();
			String message = "cumulative score for " + frameNumber + " should be 0";
			assertEquals(message, game.getCumulativeScore(frameNumber), 0);
		}
	}
	
	/**
	 * test that cumulative score doesn't work on undefined frames
	 */
	@Test
	public void gameGetCumulativeScoreFailOnUndefinedFrame() {
		Game game = new Game();
		
		BowlingException thrown = assertThrows(BowlingException.class, () -> {
			game.getCumulativeScore(1);
		});

		assertEquals("exception should say that frame #1 doesn't exists", thrown.getMessage(), "Frame #1 does not exist in this game");
	}

	
	
	
	/******************************************
	 * UTILS
	 ******************************************/
	
	/**
	 * generate a valid empty game (order and type of frame is valid)
	 * @return a valid game
	 */
	private static Game generateEmptyGameWithValidFrames() {
		Game game = new Game();

		game.addFrame(new NormalFrame(1));
		game.addFrame(new NormalFrame(2));
		game.addFrame(new NormalFrame(3));
		game.addFrame(new NormalFrame(4));
		game.addFrame(new NormalFrame(5));
		game.addFrame(new NormalFrame(6));
		game.addFrame(new NormalFrame(7));
		game.addFrame(new NormalFrame(8));
		game.addFrame(new NormalFrame(9));
		game.addFrame(new LastFrame(10));
		
		return game;
	}
}
