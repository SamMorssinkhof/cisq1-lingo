package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest
@Import(CiTestConfiguration.class)
class TrainerServiceIntegrationTest {
    @Autowired
    private TrainerService trainerService;

    @Test
    @DisplayName("start a game")
    void startGame() {
        GameProgress gameProgress = trainerService.startNewGame();

        assertTrue(gameProgress.getGameId() > -1);
        assertEquals(GameState.PLAYING, gameProgress.getGameState());
        assertEquals(5, gameProgress.getHint().size());
        assertEquals(0, gameProgress.getScore());
        assertEquals(0, gameProgress.getFeedbackHistory().size());
    }

    @Test
    @DisplayName("guess a word")
    void guess(){
        GameProgress gameProgress = trainerService.startNewGame();

        GameProgress gameProgressAfterGuess = trainerService.guess(gameProgress.getGameId(), "pizza");

        assertEquals(1, gameProgressAfterGuess.getFeedbackHistory().size());
    }

}
