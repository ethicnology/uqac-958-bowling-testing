package testBowling;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stev.bowling.BowlingException;
import stev.bowling.Frame;
import stev.bowling.Game;
import stev.bowling.LastFrame;
import stev.bowling.NormalFrame;

public class GameDemoTest {
	private Game gameUnderTest;

	@Before
	public void setUp() throws Exception {
		this.gameUnderTest = new Game();
	}

	@After
	public void tearDown() throws Exception {
		gameUnderTest = null;
	}
	
	/*
	 *====================
	 * setPinsDown(x,y)
	 *====================
	 */
	
	/**
	 * secondThrowBeforeFirst should return an exception.
	 * On a normal frame shot, try throw 2nd shot before 1st shot
	 */
	@Test(expected = BowlingException.class)
	public void secondThrowBeforeFirst() throws Exception {
		gameUnderTest.addFrame(new NormalFrame(2).setPinsDown(2, 1).setPinsDown(1, 4));
	}
	
	/**
	 * secondShotAfterStike should return an exception.
	 * Shooting again after scoring a strike isn't legit move.
	 */
	@Test(expected = BowlingException.class)
	public void secondShotAfterStike() throws Exception {
		gameUnderTest.addFrame(new NormalFrame(1).setPinsDown(1, 10).setPinsDown(2, 3));
	}

	/**
	 * thirdThrowWithoutStrikeNorSpare should return an exception.
	 * On a last frame shot, try throw 3nd without spare or strike
	 */
	@Test(expected = BowlingException.class)
	public void thirdThrowWithoutStrikeNorSpare() throws Exception {
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 6).setPinsDown(3, 4));
	}
	
	/**
	 * thirdThrowExpectedStrike should return an exception.
	 * After strike should throw 3 times
	 */
	@Test(expected = BowlingException.class)
	public void thirdThrowExpectedStrike() throws Exception {
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(1, 10));
		gameUnderTest = null;
		this.gameUnderTest = new Game();
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 6));
	}
	
	/**
	 * thirdThrowExpectedSpare should return an exception.
	 * After spare should throw a third time times
	 */
	@Test(expected = BowlingException.class)
	public void thirdThrowExpectedSpare() throws Exception {
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(1, 4).setPinsDown(2, 6));
	}
	
	
	
	/*====================================================
	 * Trouver plus opti
	 * ===================================================
	 */
	/**
	 * secondThrowBeforeFirstLastFrame should return an exception.
	 * On a last frame shot, try throw 2nd shot before 1st shot
	 */
	@Test(expected = BowlingException.class)
	public void secondThrowBeforeFirstLastFrame() throws Exception {
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(2, 1).setPinsDown(1, 4));
	}
	
	/**
	 * thirdThrowBeforeFirstLastFrame should return an exception.
	 * On a last frame shot, try throw 3nd shot before 1st shot
	 */
	@Test(expected = BowlingException.class)
	public void thirdThrowBeforeFirstLastFrame() throws Exception {
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(3, 1).setPinsDown(1, 4));
	}
	
	/**
	 * thirdThrowBeforeSecondLastFrame should return an exception.
	 * On a last frame shot, try throw 3nd shot before 2nd shot
	 */
	@Test(expected = BowlingException.class)
	public void thirdThrowBeforeSecondLastFrame() throws Exception {
		gameUnderTest.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(3, 9).setPinsDown(2, 4));
	}
	
	
	/*
	 *=======================================================================================================================
	 *=======================================================================================================================
	 *======================================================================================================================= 
	 *======================================================================================================================= 
	 */
	
	/*
	 *====================
	 * TEST reset()
	 *====================
	 */
	
	/*
	 *=======================================================================================================================
	 *=======================================================================================================================
	 *======================================================================================================================= 
	 *======================================================================================================================= 
	 */
	
	/*
	 *====================
	 * TEST countRolls(
	 *====================
	 */
	
	/*
	 *=======================================================================================================================
	 *=======================================================================================================================
	 *======================================================================================================================= 
	 *======================================================================================================================= 
	 */
	
	/*
	 *====================
	 * TEST countPinsDown()
	 *====================
	 */
	
	/*
	 *=======================================================================================================================
	 *=======================================================================================================================
	 *======================================================================================================================= 
	 *======================================================================================================================= 
	 */
	
	/*
	 *====================
	 * TEST countPinsDown(x)
	 *====================
	 */
	
	/*
	 *=======================================================================================================================
	 *=======================================================================================================================
	 *======================================================================================================================= 
	 *======================================================================================================================= 
	 */
	
	/*
	 *====================
	 * TEST toString(x)
	 *====================
	 */
	
	/*
	 *=======================================================================================================================
	 *=======================================================================================================================
	 *======================================================================================================================= 
	 *======================================================================================================================= 
	 */
	
	/*
	 *====================
	 * TEST else
	 *====================
	 */
	
	/**
	 * lastFrameNotLast should return an exception.
	 * try adding last frame at shot < 10
	 */
	@Test(expected = BowlingException.class)
	public void lastFrameNotLast() throws Exception {
		gameUnderTest.addFrame(new LastFrame(8).setPinsDown(1, 1).setPinsDown(2, 4));
	}
	
	/**
	 * lastFrameAsNormalFrame should return an exception.
	 * try adding normalFrame at lastFrame
	 */
	@Test(expected = BowlingException.class)
	public void lastFrameAsNormalFrame() throws Exception {
		gameUnderTest.addFrame(new NormalFrame(10).setPinsDown(1, 1).setPinsDown(2, 4));
	}
	
}
