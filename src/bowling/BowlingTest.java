package bowling;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import stev.bowling.Game;
import stev.bowling.NormalFrame;
import stev.bowling.LastFrame;
import stev.bowling.Frame;
import stev.bowling.BowlingException;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	BowlingTest.NormalFrameTest.class,
	BowlingTest.LastFrameTest.class,
	BowlingTest.GameTest.class,
})
public class BowlingTest {

	
	/******************************************
	 * TESTS ON CLASS NORMAL FRAME
	 ******************************************/
	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		NormalFrameTest.ConstructorTest.class,
		NormalFrameTest.GetFrameNumberTest.class,
		NormalFrameTest.SetPinsDownTest.class,
		NormalFrameTest.CountRollsTest.class,
		NormalFrameTest.CountPinsDownsTest.class,
		NormalFrameTest.ToStringTest.class,
		NormalFrameTest.ResetTest.class,
		NormalFrameTest.GetPinsDownTest.class,
	})
	public static class NormalFrameTest {
		/**
		 * TEST CONSTRUCTOR
		 */
		public static class ConstructorTest {
			/**
			 * NormalFrameSuccess should success with valid integers, between 1 to 9.
			 */
			@Test
			public void normalFrameSuccess() {
				for(int frameNumber = 1; frameNumber < 10; frameNumber++) {
					assertTrue("valid normal frame number " + frameNumber, new NormalFrame(frameNumber) instanceof Frame);
				}
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
		}
			
		/**
		 * Test method getFrameNumber()
		 */
		public static class GetFrameNumberTest {
			/**
			 * verify that getFrameNumber returns frame number passed in the constructor (for normal frame)
			 */
			@Test
			public void normalFrameGetFrameNumberValid() {
				for(int frameNumber = 1; frameNumber < 10; frameNumber++) {
					assertEquals("NormalFrame - getFrameNumber returns passed value for" + frameNumber, frameNumber, new NormalFrame(frameNumber).getFrameNumber());
				}
			}
		}
		
		/**
		 * Test method setPinsDown()
		 */
		public static class SetPinsDownTest {
			/**
			 * TODO
			 */
			@Test
			public void setPinsDownSuccess() {
				assertEquals("36", new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6).toString());
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
		}
		
		/**
		 * Test method countRolls()
		 */
		public static class CountRollsTest {
			/**
			 * Test that normal frame count rolls works for 0 roll
			 */
			@Test
			public void normalFrameCountRollsWith0Roll() {
				Frame frame = new NormalFrame(1);
				assertEquals("normal frame with 0 roll should return 0 roll", 0, frame.countRolls());
			}
			
			/**
			 * Test that normal frame count rolls works for 1 roll
			 */
			@Test
			public void normalFrameCountRollsWith1Roll() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 0);
				assertEquals("normal frame with 1 roll should return 1 roll", 1, frame.countRolls());
			}
			
			/**
			 * Test that normal frame count rolls works for 2 rolls
			 */
			@Test
			public void normalFrameCountRollsWith2Rolls() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 0);
				frame.setPinsDown(2, 0);
				assertEquals("normal frame with 2 rolls should return 2 rolls", 2, frame.countRolls());
			}
		}
		
		/**
		 * Test method countPinsDown()
		 */
		public static class CountPinsDownsTest {
			/**
			 * Test that normal frame count pins down work for 0 roll
			 */
			@Test
			public void normalFrameCountPinsDownWith0Roll() {
				Frame frame = new NormalFrame(1);
				assertEquals("normal frame with 0 rolls should return 0 pins down", 0, frame.countPinsDown());
			}
			
			/**
			 * Test that normal frame count pins down work for 1 roll
			 */
			@Test
			public void normalFrameCountPinsDownWith1Roll() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 4);
				assertEquals("normal frame with 1 rolls and 4 pins down should return 4 pins down", 4, frame.countPinsDown());
			}
			
			/**
			 * Test that normal frame count pins down work for 2 rolls
			 */
			@Test
			public void normalFrameCountPinsDownWith2Rolls() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 2);
				assertEquals("normal frame with 2 rolls and 6 pins down should return 6 pins down", 6, frame.countPinsDown());
			}
		}
	
		/**
		 * Test method toString()
		 */
		public static class ToStringTest {
			// TODO
			@Test
			public void Todo() {
				assertTrue(true);
			}
		}
		
		/**
		 * Test method reset()
		 */
		public static class ResetTest {
			// TODO
			@Test
			public void Todo() {
				assertTrue(true);
			}
		}
	
		/**
		 * Test method getPinsDownTest()
		 */
		public static class GetPinsDownTest {
			/**
			 * Test that get pins down for invalid roll throws exception
			 */
			@Test
			public void normalFrameGetPinsDownInvalidRollFail() {
				Frame frame = new NormalFrame(1);
				assertThrows("normal frame should fail when get pins down of invalid roll", BowlingException.class, () -> {
					frame.getPinsDown(3);
				});
			}
			
			/**
			 * Test that get pins down for roll 1 when 0 roll has been done returns -1
			 */
			@Test
			public void normalFrameGetPinsDownRoll1With0Roll() {
				Frame frame = new NormalFrame(1);
				assertEquals("normal frame with 0 rolls should return -1 for get pins down of roll 1", -1, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 1 when 1 roll has been done works
			 */
			@Test
			public void normalFrameGetPinsDownRoll1With1Roll() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 4);
				assertEquals("normal frame with 1 rolls and 4 pins down in roll 1 should return 4 pins down for roll 1", 4, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 1 when 2 roll has been done works
			 */
			@Test
			public void normalFrameGetPinsDownRoll1With2Roll() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				assertEquals("normal frame with 2 rolls and 4 pins down in roll 1 should return 4 pins down for roll 1", 4, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 2 when 0 roll has been done returns -1
			 */
			@Test
			public void normalFrameGetPinsDownRoll2With0Roll() {
				Frame frame = new NormalFrame(1);
				assertEquals("normal frame with 0 roll should return -1 for get pins down of roll 2", -1, frame.getPinsDown(2));
			}
			
			/**
			 * Test that get pins down for roll 2 when 1 roll has been done returns -1
			 */
			@Test
			public void normalFrameGetPinsDownRoll2With1Roll() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 4);
				assertEquals("normal frame with 1 roll should return -1 for get pins down of roll 2", -1, frame.getPinsDown(2));
			}
			
			/**
			 * Test that get pins down for roll 2 when 2 roll has been done works
			 */
			@Test
			public void normalFrameGetPinsDownRoll2With2Roll() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				assertEquals("normal frame with 2 rolls and 6 pins down in roll 2 should return 6 pins down for roll 2", 6, frame.getPinsDown(2));
			}
		}
	}

	
	/******************************************
	 * TESTS ON CLASS LAST FRAME
	 ******************************************/
	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		LastFrameTest.ConstructorTest.class,
		LastFrameTest.GetFrameNumberTest.class,
		LastFrameTest.SetPinsDownTest.class,
		LastFrameTest.CountRollsTest.class,
		LastFrameTest.CountPinsDownsTest.class,
		LastFrameTest.ToStringTest.class,
		LastFrameTest.ResetTest.class,
		LastFrameTest.GetPinsDownTest.class,
	})
	public static class LastFrameTest {
		/**
		 * TEST CONSTRUCTOR
		 */
		public static class ConstructorTest {
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
		}
			
		/**
		 * Test method getFrameNumber()
		 */
		public static class GetFrameNumberTest {
			/**
			 * verify that getFrameNumber returns frame number passed in the constructor (for last frame)
			 */
			@Test
			public void lastFrameGetFrameNumberValid() {
				assertEquals("LastFrame - getFrameNumber returns passed value for 10", 10, new LastFrame(10).getFrameNumber());
			}
		}
		
		/**
		 * Test method setPinsDown()
		 */
		public static class SetPinsDownTest {
			// TODO
			@Test
			public void Todo() {
				assertTrue(true);
			}
		}
		
		/**
		 * Test method countRolls()
		 */
		public static class CountRollsTest {
			/**
			 * Test that last frame count rolls works for 0 roll
			 */
			@Test
			public void lastFrameCountRollsWith0Roll() {
				Frame frame = new LastFrame(10);
				assertEquals("last frame with 0 roll should return 0 roll", 0, frame.countRolls());
			}
			
			/**
			 * Test that last frame count rolls works for 1 roll
			 */
			@Test
			public void lastFrameCountRollsWith1Roll() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 0);
				assertEquals("last frame with 1 roll should return 1 roll", 1, frame.countRolls());
			}
			
			/**
			 * Test that last frame count rolls works for 2 rolls
			 */
			@Test
			public void lastFrameCountRollsWith2Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 0);
				frame.setPinsDown(2, 0);
				assertEquals("last frame with 2 rolls should return 2 rolls", 2, frame.countRolls());
			}
			
			/**
			 * Test that last frame count rolls works for 3 rolls
			 */
			@Test
			public void lastFrameCountRollsWith3Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				frame.setPinsDown(3, 5);
				assertEquals("last frame with 3 rolls should return 3 rolls", 3, frame.countRolls());
			}
		}
		
		/**
		 * Test method countPinsDown()
		 */
		public static class CountPinsDownsTest {
			/**
			 * Test that last frame count pins down work for 0 roll
			 */
			@Test
			public void lastFrameCountPinsDownWith0Roll() {
				Frame frame = new LastFrame(10);
				assertEquals("last frame with 0 rolls should return 0 pins down", 0, frame.countPinsDown());
			}
			
			/**
			 * Test that last frame count pins down work for 1 roll
			 */
			@Test
			public void lastFrameCountPinsDownWith1Roll() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				assertEquals("last frame with 1 rolls and 4 pins down should return 4 pins down", 4, frame.countPinsDown());
			}
			
			/**
			 * Test that last frame count pins down work for 2 rolls
			 */
			@Test
			public void lastFrameCountPinsDownWith2Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 2);
				assertEquals("last frame with 2 rolls and 6 pins down should return 6 pins down", 6, frame.countPinsDown());
			}
			
			/**
			 * Test that last frame count pins down work for 3 rolls
			 */
			@Test
			public void lastFrameCountPinsDownWith3Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 6);
				frame.setPinsDown(2, 4);
				frame.setPinsDown(3, 4);
				assertEquals("last frame with 3 rolls and 14 pins down should return 14 pins down", 14, frame.countPinsDown());
			}
		}
	
		/**
		 * Test method toString()
		 */
		public static class ToStringTest {
			// TODO
			@Test
			public void Todo() {
				assertTrue(true);
			}
		}
		
		/**
		 * Test method reset()
		 */
		public static class ResetTest {
			// TODO
			@Test
			public void Todo() {
				assertTrue(true);
			}
		}
	
		/**
		 * Test method getPinsDownTest()
		 */
		public static class GetPinsDownTest {
			/**
			 * Test that get pins down for invalid roll throws exception
			 */
			@Test
			public void lastFrameGetPinsDownInvalidRollFail() {
				Frame frame = new LastFrame(10);
				assertThrows("last frame should fail when get pins down of invalid roll", BowlingException.class, () -> {
					frame.getPinsDown(4);
				});
			}
			
			/**
			 * Test that get pins down for roll 1 when 0 roll has been done returns -1
			 */
			@Test
			public void lastFrameGetPinsDownRoll1With0Roll() {
				Frame frame = new LastFrame(10);
				assertEquals("last frame with 0 rolls should return -1 for get pins down of roll 1", -1, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 1 when 1 roll has been done works
			 */
			@Test
			public void lastFrameGetPinsDownRoll1With1Roll() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				assertEquals("last frame with 1 rolls and 4 pins down in roll 1 should return 4 pins down for roll 1", 4, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 1 when 2 roll has been done works
			 */
			@Test
			public void lastFrameGetPinsDownRoll1With2Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				assertEquals("last frame with 2 rolls and 4 pins down in roll 1 should return 4 pins down for roll 1", 4, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 1 when 3 roll has been done works
			 */
			@Test
			public void lastFrameGetPinsDownRoll1With3Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				frame.setPinsDown(3, 4);
				assertEquals("last frame with 3 rolls and 4 pins down in roll 1 should return 4 pins down for roll 1", 4, frame.getPinsDown(1));
			}
			
			/**
			 * Test that get pins down for roll 2 when 0 roll has been done returns -1
			 */
			@Test
			public void lastFrameGetPinsDownRoll2With0Roll() {
				Frame frame = new LastFrame(10);
				assertEquals("last frame with 0 roll should return -1 for get pins down of roll 2", -1, frame.getPinsDown(2));
			}
			
			/**
			 * Test that get pins down for roll 2 when 1 roll has been done returns -1
			 */
			@Test
			public void lastFrameGetPinsDownRoll2With1Roll() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				assertEquals("last frame with 1 roll should return -1 for get pins down of roll 2", -1, frame.getPinsDown(2));
			}
			
			/**
			 * Test that get pins down for roll 2 when 2 roll has been done works
			 */
			@Test
			public void lastFrameGetPinsDownRoll2With2Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				assertEquals("last frame with 2 rolls and 6 pins down in roll 2 should return 6 pins down for roll 2", 6, frame.getPinsDown(2));
			}
			
			/**
			 * Test that get pins down for roll 2 when 3 rolls has been done works
			 */
			@Test
			public void lastFrameGetPinsDownRoll2With3Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				frame.setPinsDown(3, 5);
				assertEquals("last frame with 3 rolls and 6 pins down in roll 2 should return 6 pins down for roll 2", 6, frame.getPinsDown(2));
			}

			/**
			 * Test that get pins down for roll 3 when 0 roll has been done returns -1
			 */
			@Test
			public void lastFrameGetPinsDownRoll3With0Roll() {
				Frame frame = new LastFrame(10);
				assertEquals("last frame with 0 roll should return -1 for get pins down of roll 3", -1, frame.getPinsDown(3));
			}
			
			/**
			 * Test that get pins down for roll 3 when 1 roll has been done returns -1
			 */
			@Test
			public void lastFrameGetPinsDownRoll3With1Roll() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				assertEquals("last frame with 1 roll should return -1 for get pins down of roll 3", -1, frame.getPinsDown(3));
			}
			
			/**
			 * Test that get pins down for roll 3 when 2 rolls has been done returns -1
			 */
			@Test
			public void lastFrameGetPinsDownRoll3With2Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				assertEquals("last frame with 2 rolls should return -1 for get pins down of roll 3", -1, frame.getPinsDown(3));
			}
			
			/**
			 * Test that get pins down for roll 3 when 3 rolls has been done works
			 */
			@Test
			public void lastFrameGetPinsDownRoll3With3Rolls() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 4);
				frame.setPinsDown(2, 6);
				frame.setPinsDown(3, 5);
				assertEquals("last frame with 3 rolls and 5 pins down in roll 2 should return 5 pins down for roll 3", 5, frame.getPinsDown(3));
			}
		}
	}
		

	/******************************************
	 * TESTS ON CLASS GAME
	 ******************************************/
	@RunWith(Suite.class)
	@Suite.SuiteClasses({
		GameTest.AddFrameTest.class,
		GameTest.GetCumulativeScoreTest.class,
		GameTest.ToStringTest.class,
	})
	public static class GameTest {
		
		/**
		 * Test method addFrame()
		 */
		public static class AddFrameTest {
			/**
			 * test that a normal game initialization
			 */
			@Test
			public void gameInitializationTestSuccess() {
				Game game = new Game();
				
				assertEquals("new game has no frame", 0, game.m_frames.size());
		
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
				
				assertEquals("initialized game has 10 frames", 10, game.m_frames.size());
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
				
				assertEquals("invalid frame isn't added", 9, game.m_frames.size()); // last frame isn't added
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
							assertEquals("invalid frame isn't added", oldFrameCount, game.m_frames.size()); // last frame isn't added
							break; // no need to continue this invalid number
						}
					}
				}
			}
		}
		
		/**
		 * Test method getCumulativeScore()
		 */
		public static class GetCumulativeScoreTest {
			/**
			 * test that cumulative score is 0 when done on empty frames
			 */
			@Test
			public void gameGetCumulativeScoreShouldBe0WithEmptyFrames() {
				Game game = Utils.generateEmptyGameWithValidFrames();
				
				for(Frame frame: game.m_frames) {
					int frameNumber = frame.getFrameNumber();
					String message = "cumulative score for " + frameNumber + " should be 0";
					assertEquals(message, 0, game.getCumulativeScore(frameNumber));
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
		
				assertEquals("exception should say that frame #1 doesn't exists", "Frame #1 does not exist in this game", thrown.getMessage());
			}
		}
	
		/**
		 * Test method toString()
		 */
		public static class ToStringTest {
			// TODO
			@Test
			public void Todo() {
				assertTrue(true);
			}
		}
	}
	
	
	
	
	
	/******************************************
	 * UTILS CLASS
	 ******************************************/
	private static class Utils {
		
		/**
		 * generate a valid empty game (order and type of frame is valid)
		 * @return a valid game
		 */
		static Game generateEmptyGameWithValidFrames() {
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
}
