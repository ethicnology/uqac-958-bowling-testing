package bowling;


import static org.junit.Assert.*;

import org.junit.Test;

import stev.bowling.Game;
import stev.bowling.NormalFrame;
import stev.bowling.LastFrame;
import stev.bowling.Frame;
import stev.bowling.BowlingException;




/**
 * 
 * @author Antoine Bouabana.
 */
public class BowlingTestAntoine {

	/******************************************
	 * TEST DE LA CLASSE NORMAL FRAME
	 ******************************************/
	
	/**
	 * Teste le cas normal de création des normal frame (c'est à dire des frame de 1 à 9)
	 */
	@Test
	public void normalFrameCreateTestSuccess() {
		for(int frameNumber = 1; frameNumber < 10; frameNumber++) {
			assertTrue("valid normal frame number " + frameNumber, new NormalFrame(frameNumber) instanceof Frame);
		}
	}
	
	/**
	 * Teste le cas où la création des normal frame devrait échouer (c'est à dire des frame qui ne sont pas dans l'intervale [1;9])
	 */
	@Test
	public void normalFrameCreateTestFailure() {
		assertThrows("invalid normal frame number -1", BowlingException.class, () -> { new NormalFrame(-1); });
		assertThrows("invalid normal frame number 0", BowlingException.class, () -> { new NormalFrame(0); });
		assertThrows("invalid normal frame number 10", BowlingException.class, () -> { new NormalFrame(10); });
		assertThrows("invalid normal frame number 11", BowlingException.class, () -> { new NormalFrame(11); });
	}
	
	/**
	 * Teste que le numéro des frame est bien celui donné au constructeur
	 */
	@Test
	public void normalFrameFrameNumberIsValid() {
		for(int frameNumber = 1; frameNumber < 10; frameNumber++) {
			Frame frame = new NormalFrame(frameNumber);
			assertEquals("frame number passed is kept " + frameNumber, frame.getFrameNumber(), frameNumber);
			Game game = new Game();
			game.addFrame(frame);
			assertEquals("game doesn't change frame number " + frameNumber, game.m_frames.get(0).getFrameNumber(), frameNumber);
		}
	}
	
	

	/******************************************
	 * TEST DE LA CLASSE LAST FRAME
	 ******************************************/
	
	/**
	 * Teste le cas normal de création des last frame (c'est à dire une frame 10)
	 */
	@Test
	public void lastFrameCreateTestSuccess() {
		assertTrue("valid last frame number 10", new LastFrame(10) instanceof Frame);
	}
	
	/**
	 * Teste le cas où la création des last frame devrait échouer (tout ce qui n'est pas 10)
	 */
	@Test
	public void lastFrameCreateTestFailure() {
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
	 * Teste que le numéro des frame est bien celui donné au constructeur
	 */
	@Test
	public void lastFrameFrameNumberIsValid() {
		Frame frame = new LastFrame(10);
		assertEquals("frame number passed is kept 10", frame.getFrameNumber(), 10);
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
	 * Teste l'ajout d'une normal frame en 10 ème position (à la place d'une last frame)
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
	 * Teste l'ajout d'une last frame à la place d'une normale frame (position 1 à 9)
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
	 * Teste que le score cummulée de frame "vide" est bien 0
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
	 * Teste que le score cummulé ne fonctionne pas sur une frame inexistante
	 */
	@Test
	public void gameGetCumulativeScoreFailOnUndefinedFrame() {
		Game game = new Game();
		
		BowlingException thrown = assertThrows(BowlingException.class, () -> {
			game.getCumulativeScore(1);
		});

		assertEquals("exception should say that frame #1 doesn't exists", thrown.getMessage(), "Frame #1 does not exist in this game");
	}
	
	/**
	 * Teste que le jeu utilise les numéro de frame cohéremment (si j'ajoute une frame x et que je demande 
	 * son score, il ne devrait pas me dire que la frame n'existe pas)
	 */
	@Test
	public void gameUsesCoherentlyFrameNumbers() {
		Game game = new Game();
		
		int frameNumber = 5;
		
		// add a frame with number 5
		game.addFrame(new NormalFrame(frameNumber));
		
		BowlingException thrown = assertThrows("get the cumulative score without all frame shouldn't work", BowlingException.class, () -> {
			int score = game.getCumulativeScore(frameNumber);
		});
		
		String message = "exception should not say that frame #" + frameNumber+ " doesn't exists";

		assertNotEquals(message, thrown.getMessage(), "Frame #" + frameNumber + " does not exist in this game");
	}

	/******************************************
	 * UTILS
	 ******************************************/
	
	/**
	 * Génère une partie vide (mais respectant les règles d'initialisation)
	 * @return une partie vide valide
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
