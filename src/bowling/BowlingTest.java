package bowling;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;

import stev.bowling.Game;
import stev.bowling.NormalFrame;
import stev.bowling.LastFrame;
import stev.bowling.Frame;
import stev.bowling.BowlingException;

/**
 * Rendu du travail #1 "des tests à en perdre la boule"
 */

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
		NormalFrameTest.ConstructorTest.ParameterizedValidFrameNumberTest.class,
		NormalFrameTest.ConstructorTest.ParameterizedInvalidFrameNumberTest.class,
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
			 * Test valid frame number cases
			 */
			@RunWith(Parameterized.class)
			public static class ParameterizedValidFrameNumberTest {
				@Parameters(name = "{index}: frameNumber {0}")
				public static Object[] data() {
				    return new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
				}

			    @Parameter
			    public int frameNumber;

			    @Test
			    public void test() {
			        assertTrue("valid normal frame number " + frameNumber, new NormalFrame(frameNumber) instanceof Frame);
			    }
			}
			
			/**
			 * Test invalid frame number cases
			 */
			@RunWith(Parameterized.class)
			public static class ParameterizedInvalidFrameNumberTest {
				@Parameters(name = "{index}: frameNumber {0}")
				public static Object[] data() {
				    return new Object[] { -1, 0, 10, 11, 12 };
				}

			    @Parameter
			    public int frameNumber;

			    @Test
			    public void test() {
			    	assertThrows("invalid normal frame number " + frameNumber, BowlingException.class, () -> { new NormalFrame(frameNumber); });
			    }
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
					assertEquals("NormalFrame - getFrameNumber returns passed value for " + frameNumber, frameNumber, new NormalFrame(frameNumber).getFrameNumber());
				}
			}
		}
		
		/**
		 * Test method setPinsDown()
		 */
		public static class SetPinsDownTest {			
            /**
             * normal frame roll 1 then roll 2 should work just fine
             */
            @Test
            public void setPinsDownRoll1Then2() {
                Frame frame = new NormalFrame(1).setPinsDown(1, 2).setPinsDown(2, 1);
                assertEquals(2, frame.countRolls());
            }
			
            /**
             * normal frame roll 2 then roll 1 should throw exception (because roll order is reversed) + state shouldn't be changed
             */
            @Test
            public void setPinsDownRoll2ThenRoll1() {
            	Frame frame = new NormalFrame(1);
            	
            	assertThrows("roll 2 before roll 1 should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(2, 3);
            		frame.setPinsDown(1, 6);
            	});
            	
            	assertEquals("frame state has not changed after exception", 0, frame.countRolls());
		    }
			
			/**
			 * Test setPinsDown with invalid roll number (0 and 3) should throw exception and not change the state
			 */
			@Test
			public void setPinsDownRollInvalidRollsNumber() {
				Frame frame = new NormalFrame(1);
		    	assertThrows("There is no such roll 0", BowlingException.class, () -> { frame.setPinsDown(0, 3); });
            	assertEquals("frame state has not changed after exception", 0, frame.countRolls());
            	
            	Frame frame2 = new NormalFrame(1);
		    	assertThrows("There is no such roll 3", BowlingException.class, () -> { frame2.setPinsDown(3, 3); });
            	assertEquals("frame state has not changed after exception", 0, frame2.countRolls());
			}
			
            /**
             * normal frame roll 1 with more than 10 pins should throw exception and not change the state
             */
            @Test
            public void setPinsDownRoll1WithMoreThan10Pins() {
            	Frame frame = new NormalFrame(1);
            	
            	assertThrows("roll 1 with more than 10 pins should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(1, 15); // 15 pins down
            	});
            	
            	assertEquals("frame state has not changed after exception", 0, frame.countRolls());
		    }
			
            /**
             * normal frame roll 1 and 2 with more than 10 pins in total should throw exception and not change the state
             */
            @Test
            public void setPinsDownRoll1And2WithMoreThan10Pins() {
            	Frame frame = new NormalFrame(1);
            	
        		frame.setPinsDown(1, 8);
            	
            	assertThrows("roll 2 with more than 10 pins in total should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(2, 6); // total pins = 14
            	});
            	
            	assertEquals("frame state has not changed after exception", 1, frame.countRolls());
		    }
			
            /**
             * normal frame re rolling roll 1 should throw exception
             */
            @Test
            public void setPinsDownReRoll1() {
            	Frame frame = new NormalFrame(1);
            	
        		frame.setPinsDown(1, 8);
            	
            	assertThrows("roll 1 with already roll 1 done should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(1, 6);
            	});
            	
            	assertEquals("frame state has not changed after exception", 8, frame.getPinsDown(1));
		    }
			
            /**
             * normal frame re rolling roll 2 should throw exception
             */
            @Test
            public void setPinsDownReRoll2() {
            	Frame frame = new NormalFrame(1);

        		frame.setPinsDown(1, 8);
        		frame.setPinsDown(2, 1);
            	
            	assertThrows("roll 2 with already roll 2 done should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(2, 6);
            	});
            	
            	assertEquals("frame state has not changed after exception", 1, frame.getPinsDown(2));
		    }
			
            /**
             * normal frame roll 2 after strike in roll 1 shouldn't be possible and throw error
             */
            @Test
            public void setPinsDownRoll2AfterStrike() {
            	Frame frame = new NormalFrame(1);

        		frame.setPinsDown(1, 10);
            	
            	assertThrows("roll 2 with strike in roll 1 should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(2, 0);
            	});
            	
            	assertEquals("frame state has not changed after exception", 1, frame.countRolls());
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
			/**
			 * Test dalot sign if no pins knocked down
			 */
			@Test
			public void setPinsDownWithDalot() {
				String toString = new NormalFrame(7).setPinsDown(1, 0).toString();
				assertEquals("- ", toString);
				assertEquals(2, toString.length());
			}
			
			/**
			 * Test double dalot sign if no pins knocked down after 2 throws
			 */
			@Test
			public void setPinsDownWithDoubleDalot() {
				String toString = new NormalFrame(7).setPinsDown(1, 0).setPinsDown(2, 0).toString();
				assertEquals("--", toString);
				assertEquals(2, toString.length());
			}
			
			/**
			 * Test spare sign if all pins knocked down
			 */
			@Test
			public void setPinsDownWithSpare() {
				String toString = new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 7).toString();
				assertEquals("3/", toString);
				assertEquals(2, toString.length());
			}

			/**
			 * Test numbers with open frame
			 */
			@Test
			public void setPinsDownWithOpenFrame() {
				String toString = new NormalFrame(1).setPinsDown(1, 2).setPinsDown(2, 6).toString();
				assertEquals("26", toString);
				assertEquals(2, toString.length());
			}
			
			/**
			 * Test strike sign "X "
			 */
			@Test
			public void setPinsDownWithStrike() {
				String toString = new NormalFrame(1).setPinsDown(1, 10).toString();
				assertEquals("X ", toString);
				assertEquals(2, toString.length());
			}
		}
		
		/**
		 * Test method reset()
		 */
		public static class ResetTest {			
			@Test
			public void normalFrameResetWith0RollGetRoll1() {
				Frame frame = new NormalFrame(1);
				frame.reset();
				assertEquals("reset with 0 roll -> roll 1 should be empty", -1, frame.getPinsDown(1));
			}
			
			@Test
			public void normalFrameResetWith1RollGetRoll1() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 5);
				frame.reset();
				assertEquals("reset with 1 roll -> roll 1 should be empty", -1, frame.getPinsDown(1));
			}
			
			@Test
			public void normalFrameResetWith2RollGetRoll1() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 4);
				frame.reset();
				assertEquals("reset with 2 roll -> roll 1 should be empty", -1, frame.getPinsDown(1));
			}
			
			@Test
			public void normalFrameResetWith2RollGetRoll2() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 4);
				frame.reset();
				assertEquals("reset with 2 roll -> roll 2 should be empty", -1, frame.getPinsDown(2));
			}
			
			@Test
			public void normalFrameResetWith1RollReRoll1() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 5);
				frame.reset();
				frame.setPinsDown(1, 6);
				assertEquals("reset with 1 roll then set 6 pins down -> roll 1 should return 6", 6, frame.getPinsDown(1));
			}
			
			@Test
			public void normalFrameResetWith2RollReRoll2() {
				Frame frame = new NormalFrame(1);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 4);
				frame.reset();
				frame.setPinsDown(1, 6);
				frame.setPinsDown(2, 3);
				assertEquals("reset with 2 roll then set 3 pins down on roll 2 -> roll 2 should return 3", 3, frame.getPinsDown(2));
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
		LastFrameTest.ConstructorTest.ParameterizedValidFrameNumberTest.class,
		LastFrameTest.ConstructorTest.ParameterizedInvalidFrameNumberTest.class,
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
			 * Test valid frame number cases
			 */
			public static class ParameterizedValidFrameNumberTest {
				/**
				 * LastFrameSuccess should success with 10 a.k.a. last frame.
				 */
				@Test
				public void lastFrameSuccess() {
					assertTrue("valid last frame number 10", new LastFrame(10) instanceof Frame);
				}
			}
			
			/**
			 * Test invalid frame number cases
			 */
			@RunWith(Parameterized.class)
			public static class ParameterizedInvalidFrameNumberTest {
				@Parameters(name = "{index}: frameNumber {0}")
				public static Object[] data() {
				    return new Object[] { -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11 };
				}

			    @Parameter
			    public int frameNumber;

			    @Test
			    public void test() {
			    	assertThrows("invalid last frame number " + frameNumber, BowlingException.class, () -> { new LastFrame(frameNumber); });
			    }
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
			/**
			 *  If strike third rolls should be allowed 
			 */
			@Test
			public void setPinsDown3RollsOnStrike() {
				Frame z = new LastFrame(10);
				z.setPinsDown(1, 10);
				z.setPinsDown(2, 3);
				z.setPinsDown(3, 3);
				assertEquals(3, z.getPinsDown(3));
			}
			
			/**
			 *  test that triple strike works
			 */
			@Test
			public void setPinsDownTripleStrike() {
				Frame z = new LastFrame(10);
				z.setPinsDown(1, 10);
				z.setPinsDown(2, 10);
				z.setPinsDown(3, 10);
				assertEquals(3, z.countRolls());
			}
			
			/**
			 *  test that the third roll works even if 2nd roll is empty (but first roll was a strike)
			 */
			@Test
			public void setPinsDown3RollsWithEmptyRoll2() {
				Frame z = new LastFrame(10);
				z.setPinsDown(1, 10);
				z.setPinsDown(2, 0);
				z.setPinsDown(3, 10);
				assertEquals(3, z.countRolls());
			}
			
			/**
			 *  If spare third rolls should be allowed 
			 */
			@Test
			public void setPinsDown3RollsOnSpare() {
				Frame z = new LastFrame(10);
				z.setPinsDown(1, 5);
				z.setPinsDown(2, 5);
				z.setPinsDown(3, 9);
				assertEquals(9, z.getPinsDown(3));
			}
			
			
			/**
			 *  If no spare nor strike there are only 2 rolls 
			 */
			@Test
			public void setPinsDown2RollsOnElse() {
				Frame z = new LastFrame(10);
				z.setPinsDown(1, 5);
				z.setPinsDown(2, 4);
		    	assertThrows("There is no such roll 3", BowlingException.class, () -> { z.setPinsDown(3, 1); });
		    	assertEquals("last frame state hasn't changed", 2, z.countRolls());
			}
			
			// OTHER REGULAR TEST IMPORTED FROM NORMAL FRAME
			
            
			/**
             * last frame roll 2 before roll 1 should throw exception (because roll order is reversed)
             */
            @Test
            public void setPinsDownRoll2First() {
				Frame frame = new LastFrame(10);
            	
            	assertThrows(BowlingException.class, () -> { 
            		frame.setPinsDown(2, 3);
            	});
		    }
			
            /**
             * last frame roll 3 before previous rolls should throw exception (because roll order is reversed)
             */
            @Test
            public void setPinsDownRoll3First() {
				Frame frame = new LastFrame(10);
            	
            	assertThrows(BowlingException.class, () -> { 
            		frame.setPinsDown(3, 3);
            	});
		    }
			
			/**
			 * Test setPinsDown with invalid roll number (0 and 4) should throw exception
			 */
			@Test
			public void setPinsDownRollInvalidRollsNumber() {
				Frame frame = new LastFrame(10);
		    	assertThrows("There is no such roll 0", BowlingException.class, () -> { frame.setPinsDown(0, 3); });

				Frame frame2 = new LastFrame(10);
		    	assertThrows("There is no such roll 3", BowlingException.class, () -> { frame2.setPinsDown(4, 3); });
			}
			
            /**
             * last frame roll 1 with more than 10 pins should throw exception
             */
            @Test
            public void setPinsDownRoll1WithMoreThan10Pins() {
				Frame frame = new LastFrame(10);
            	
            	assertThrows("roll 1 with more than 10 pins should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(1, 15); // 15 pins down
            	});
		    }
			
            /**
             * last frame roll 1 and 2 with more than 10 pins in total should throw exception
             */
            @Test
            public void setPinsDownRoll1And2WithMoreThan10Pins() {
				Frame frame = new LastFrame(10);
            	
        		frame.setPinsDown(1, 8);
            	
            	assertThrows("roll 2 with more than 10 pins in total should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(2, 6); // total pins = 14
            	});
		    }
			
            /**
             * last frame re rolling roll 1 should throw exception
             */
            @Test
            public void setPinsDownReRoll1() {
				Frame frame = new LastFrame(10);
            	
        		frame.setPinsDown(1, 8);
            	
            	assertThrows("roll 1 with already roll 1 done should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(1, 6);
            	});
            	
            	assertEquals("frame state has not changed after exception", 8, frame.getPinsDown(1));
		    }
			
            /**
             * last frame re rolling roll 2 should throw exception
             */
            @Test
            public void setPinsDownReRoll2() {
				Frame frame = new LastFrame(10);

        		frame.setPinsDown(1, 8);
        		frame.setPinsDown(2, 1);
            	
            	assertThrows("roll 2 with already roll 2 done should throw BowlingException", BowlingException.class, () -> { 
            		frame.setPinsDown(2, 6);
            	});
            	
            	assertEquals("frame state has not changed after exception", 1, frame.getPinsDown(2));
		    }
			
            /**
             * last frame re rolling roll 3 should throw exception
             */
            @Test
            public void setPinsDownReRoll3() {
				Frame frame = new LastFrame(10);

        		frame.setPinsDown(1, 8);
        		frame.setPinsDown(2, 2);
        		frame.setPinsDown(3, 2);
            	
            	assertThrows(BowlingException.class, () -> { 
            		frame.setPinsDown(3, 6);
            	});
            	
            	assertEquals("frame state has not changed after exception", 2, frame.getPinsDown(3));
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
			/**
			 *   Two rolls should return 3 characters every time (event if there is only 2 rolls)
			 */
			@Test
			public void setPinsDown2Rolls() {
				String toString = new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 7).toString();
				assertEquals("17 ", toString);
				assertEquals(3, toString.length());
			}
			
			/**
			 *  Three rolls should return 3 char
			 */
			@Test
			public void setPinsDown3Rolls() {
				String toString = new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 10).toString();
				assertEquals("1/X", toString);
				assertEquals(3, toString.length());
			}
		}
		
		/**
		 * Test method reset()
		 */
		public static class ResetTest {
			@Test
			public void lastFrameResetWith0RollGetRoll1() {
				Frame frame = new LastFrame(10);
				frame.reset();
				assertEquals("reset with 0 roll -> roll 1 should be empty", -1, frame.getPinsDown(1));
			}
			
			@Test
			public void lastFrameResetWith1RollGetRoll1() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.reset();
				assertEquals("reset with 1 roll -> roll 1 should be empty", -1, frame.getPinsDown(1));
			}
			
			@Test
			public void lastFrameResetWith2RollGetRoll1() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 4);
				frame.reset();
				assertEquals("reset with 2 roll -> roll 1 should be empty", -1, frame.getPinsDown(1));
			}
			
			@Test
			public void lastFrameResetWith2RollGetRoll2() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 4);
				frame.reset();
				assertEquals("reset with 2 roll -> roll 2 should be empty", -1, frame.getPinsDown(2));
			}
			
			@Test
			public void lastFrameResetWith3RollGetRoll3() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 5);
				frame.setPinsDown(3, 4);
				frame.reset();
				assertEquals("reset with 3 rolls -> roll 3 should be empty", -1, frame.getPinsDown(3));
			}
			
			@Test
			public void lastFrameResetWith1RollReRoll1() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.reset();
				frame.setPinsDown(1, 6);
				assertEquals("reset with 1 roll then set 6 pins down -> roll 1 should return 6", 6, frame.getPinsDown(1));
			}
			
			@Test
			public void lastFrameResetWith2RollReRoll2() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 4);
				frame.reset();
				frame.setPinsDown(1, 6);
				frame.setPinsDown(2, 3);
				assertEquals("reset with 2 roll then set 3 pins down on roll 2 -> roll 2 should return 3", 3, frame.getPinsDown(2));
			}
			
			@Test
			public void lastFrameResetWith3RollReRoll3() {
				Frame frame = new LastFrame(10);
				frame.setPinsDown(1, 5);
				frame.setPinsDown(2, 5);
				frame.setPinsDown(3, 6);
				frame.reset();
				frame.setPinsDown(1, 6);
				frame.setPinsDown(2, 4);
				frame.setPinsDown(3, 3);
				assertEquals("reset with 3 roll then set 3 pins down on roll 3 -> roll 3 should return 3", 3, frame.getPinsDown(3));
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
			
			/**
			 * noStrikeNorSpareCumulativeScore is  an addition score of all the same shot in the game without stike nor spare
			 */
			@Test
			public void noStrikeNorSpareCumulativeScore() {
				int scoreEachRoll = 6;
				int realCumulativeScore = 0;
				
				Game game = Utils.generateNoSpareNorStrikeGame(scoreEachRoll);				
				
				for(Frame frame: game.m_frames) {
					realCumulativeScore += scoreEachRoll;
					int frameNumber = frame.getFrameNumber();
					String message = "cumulative score for " + frameNumber + " should be " + realCumulativeScore;
					assertEquals(message, realCumulativeScore, game.getCumulativeScore(frameNumber));
				}
			}
			
			/**
			 * spareCumulativeNotMissed test for one spare and a success shot (3 and 3)
			 */
			@Test
			public void spareCumulativeNotMissed() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 3));
				
				assertEquals("Score expected 19", 19, game.getCumulativeScore(2));				
			}
			
			/**
			 * spareCumulativeMissed test for one spare and a missed shot the cumulative (0 and 3)
			 */
			@Test
			public void spareCumulativeMissed() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 0).setPinsDown(2, 3));
				
				assertEquals("Score expected 13", 13, game.getCumulativeScore(2));	
			}
			
			
			/**
			 * strikeCumulativeNotMissed test for one strike and two success shot (5 and 4)
			 */
			@Test
			public void strikeCumulativeNotMissed() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 5).setPinsDown(2, 4));
				
				assertEquals("Score expected 28", 28, game.getCumulativeScore(2));	
			}
			
			/**
			 * strikeCumulativeFirstShotMissed test for one strike and a missed shot and second success (0 and 5)
			 */
			@Test
			public void strikeCumulativeFirstShotMissed() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 0).setPinsDown(2, 5));
				
				assertEquals("Score expected 20", 20, game.getCumulativeScore(2));	
			}
			
			/**
			 * strikeCumulativeSecondShotMissed test for one strike and a missed second shot and first shot success (3 and 0)
			 */
			@Test
			public void strikeCumulativeSecondShotMissed() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 0));
				
				assertEquals("Score expected 16", 16, game.getCumulativeScore(2));	
			}
			
			/**
			 * strikeCumulativeBothShotMissed test for one strike and both missed
			 */
			@Test
			public void strikeCumulativeBothShotMissed() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 0).setPinsDown(2, 0));
				
				assertEquals("Score expected 10", 10, game.getCumulativeScore(2));	
			}
			
			/**
			 * spareCumulativeStrike test for one spare and then a strike followed with two success shot (5 and 3)
			 */
			@Test
			public void spareCumulativeStrike() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 5).setPinsDown(2, 3));
				
				assertEquals("Score expected 46", 46, game.getCumulativeScore(3));	
			}
			
			/**
			 * strikeCumulativeTwoInRow test for two strikes in a row and a two success shot (5 and 3)
			 */
			@Test
			public void strikeCumulativeTwoInRow() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 5).setPinsDown(2, 3));
				
				assertEquals("Score expected 51", 51, game.getCumulativeScore(3));
			}
			
			/**
			 * strikeCumulativeThreeInRow test for three strikes in a row and a two success shot (5 and 3)
			 */
			@Test
			public void strikeCumulativeThreeInRow() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(3).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(4).setPinsDown(1, 5).setPinsDown(2, 3));
				
				assertEquals("Score expected 81", 81, game.getCumulativeScore(4));
			}
			
			/**
			 * strikeCumulativeSpare test for a strike followed by a spare and two success shot (5 and 3)
			 */
			@Test
			public void strikeCumulativeSpare() {
				Game game = new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 5).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(4).setPinsDown(1, 5).setPinsDown(2, 3));
				
				assertEquals("Score expected 43", 43, game.getCumulativeScore(3));
			}
			
			/**
			 * fullStrikeGame test for a game with only strikes
			 */
			@Test
			public void fullStrikeGame() {
				Game game = Utils.generateFullStrikeGame();
				
				assertEquals("Score expected 300", 300, game.getCumulativeScore(10));
			}
			
			/**
			 * spareNotFinished is a Frame spared without next shot
			 */
			@Test
			public void spareNotFinished() {
				Game game =  new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 4));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5));
				
				assertEquals("Score expected -1", -1, game.getCumulativeScore(3));
			}
			
			/**
			 * strikeNotFinished is a Frame Strike without next shot
			 */
			@Test
			public void strikeNotFinished() {
				Game game =  new Game();
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 4));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
				
				assertEquals("Score expected -1", -1, game.getCumulativeScore(3));
			}
			
			
			
		}
	
		/**
		 * Test method toString()
		 */
		public static class ToStringTest {

			/**
			 * completeGameNoStrikeOrSpare is a game with alternative success/missed shot
			 */
			@Test
			public void completeGameNoStrikeOrSpare() {
				Game game = Utils.generateNoSpareNorStrikeGame(5); 
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|  5-|  -5|  5-|  -5|  5-|  -5|  5-|  -5|  5-|  -5|\n"
									  + "|5   |10  |15  |20  |25  |30  |35  |40  |45  |50  |";
							
				assertEquals(expectedOutput, game.toString());
			}
			
			/**
			 * completeOnlyMissedShot is a game with only missed shot
			 */
			@Test
			public void completeOnlyMissedShot() {
				Game game = Utils.generateNoSpareNorStrikeGame(0); 
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|  --|  --|  --|  --|  --|  --|  --|  --|  --|  --|\n"
									  + "|0   |0   |0   |0   |0   |0   |0   |0   |0   |0   |";
				assertEquals(expectedOutput, game.toString());				
			}
			
			/**
			 * fewShotNoStrike is a game not complete with few shots
			 */
			@Test
			public void fewShotNoStrike() {
				Game game = new Game(); 
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 4));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 5));
				game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 3));
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|  54|  35|  33|    |    |    |    |    |    |    |\n"
									  + "|9   |17  |23  |    |    |    |    |    |    |    |";
				assertEquals(expectedOutput, game.toString());				
			}
			
			/**
			 * spareUnfinished is a game with a unfinished spare
			 */
			@Test
			public void spareUnfinished() {
				Game game = new Game(); 
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 4));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 7));
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|  54|  3/|    |    |    |    |    |    |    |    |\n"
									  + "|9   |    |    |    |    |    |    |    |    |    |";
				assertEquals(expectedOutput, game.toString());				
			}
			
			/**
			 * strikeUnfinished is a game with a unfinished strike
			 */
			@Test
			public void strikeUnfinished() {
				Game game = new Game(); 
				game.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 4));
				game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|  54|  X |    |    |    |    |    |    |    |    |\n"
									  + "|9   |    |    |    |    |    |    |    |    |    |";
				assertEquals(expectedOutput, game.toString());				
			}
			
			
			
			/**
			 * emptyGame is a game without any shots
			 */
			@Test
			public void emptyGame() {
				Game game = new Game(); 
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|    |    |    |    |    |    |    |    |    |    |\n"
									  + "|    |    |    |    |    |    |    |    |    |    |";
				assertEquals(expectedOutput, game.toString());				
			}
			
			/**
			 * gameThreeShotsLastFram is a game with 3 shots at last with spare frame
			 */
			@Test
			public void gameThreeShotsSpareLastFrame() {
				Game game = Utils.exampleGame(); 
				
				
				String expectedOutput = "|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |\n"
									  + "|----+----+----+----+----+----+----+----+----+----+\n"
									  + "|  36|  X |  5-|  1/|  X |  --|  -6|  X |  2/| 1/3|\n"
									  + "|9   |24  |29  |49  |59  |59  |65  |85  |96  |109 |";
				assertEquals("Expected",expectedOutput, game.toString());				
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
		
		
		/**
		 * generate a valid regular game, all the shot have the same value
		 * @return a valid game
		 */
		static Game generateNoSpareNorStrikeGame(int scoreEachRoll) {
			Game game = new Game();
			
			game.addFrame(new NormalFrame(1).setPinsDown(1, scoreEachRoll).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 0).setPinsDown(2, scoreEachRoll));
			game.addFrame(new NormalFrame(3).setPinsDown(1, scoreEachRoll).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(4).setPinsDown(1, 0).setPinsDown(2, scoreEachRoll));
			game.addFrame(new NormalFrame(5).setPinsDown(1, scoreEachRoll).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, scoreEachRoll));
			game.addFrame(new NormalFrame(7).setPinsDown(1, scoreEachRoll).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(8).setPinsDown(1, 0).setPinsDown(2, scoreEachRoll));
			game.addFrame(new NormalFrame(9).setPinsDown(1, scoreEachRoll).setPinsDown(2, 0));
			game.addFrame(new LastFrame(10).setPinsDown(1, 0).setPinsDown(2, scoreEachRoll));
			
			return game;
		}
		
		/**
		 * generate full strike game
		 * @return a valid game
		 */
		static Game generateFullStrikeGame() {
			Game game = new Game();
			
			game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(3).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(4).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(5).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(6).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(7).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(8).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(9).setPinsDown(1, 10));
			game.addFrame(new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 10).setPinsDown(3, 10));
			
			return game;
		}
	
		/**
		 * generate the game as example in the subject
		 * @return a valid game
		 */
		static Game exampleGame() {
			Game game = new Game();
			
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(3).setPinsDown(1, 5).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(4).setPinsDown(1, 1).setPinsDown(2, 9));
			game.addFrame(new NormalFrame(5).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(7).setPinsDown(1, 0).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(8).setPinsDown(1, 10));
			game.addFrame(new NormalFrame(9).setPinsDown(1, 2).setPinsDown(2, 8));
			game.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 3));
			
			return game;
		}
	}
}
