package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.CurrentRoundIsNotFinishedException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.PlayerIsEliminatedException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Test
    @DisplayName("Starts a new game and a new round")
    void startNewGame() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("skunk");

        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TrainerService trainerService = new TrainerService(wordService, gameRepository);

        GameProgress gameProgress = trainerService.startNewGame();

        assertEquals(List.of('s', '.', '.', '.', '.'), gameProgress.getHint());
        assertEquals(GameState.PLAYING, gameProgress.getGameState());
    }

    @Test
    @DisplayName("guesses a word correctly, gains points")
    void guessWord(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("skunk");

        SpringGameRepository gameRepository = mock(SpringGameRepository.class);

        TrainerService trainerService = new TrainerService(wordService, gameRepository);

        Game game = new Game();
        game.startNewRound("skunk");

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        when(gameRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        GameProgress gameProgress = trainerService.guess(0L, "skunk");

        assertEquals(1, gameProgress.getScore());
        assertEquals(GameState.WAITING_FOR_ROUND, gameProgress.getGameState());
        assertEquals(1, gameProgress.getFeedbackHistory().size());
    }
}