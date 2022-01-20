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
//    private TrainerService trainerService;
//    private WordService wordService;
//    private SpringGameRepository gameRepository;

//    @BeforeEach
//    void setUp() throws IOException {
//        wordService = mock(WordService.class);
//        gameRepository = mock(SpringGameRepository.class);
//        trainerService = new TrainerService(wordService, gameRepository);
//    }

    @Test
    @DisplayName("Starts a new game and a new round")
    void startNewGame() throws PlayerIsEliminatedException, CurrentRoundIsNotFinishedException {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("skunk");

        Game game = new Game();
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.save(any())).thenReturn(game);

        TrainerService trainerService = new TrainerService(wordService, gameRepository);

        game.setId((long)1);
        GameProgress gameProgress = trainerService.startNewGame();

        assertEquals(List.of('s', '.', '.', '.', '.'), gameProgress.getHint());
        assertEquals(GameState.PLAYING, gameProgress.getGameState());
    }

//    @Test
//    @DisplayName("game score is one after the word has been correctly guessed")
//    void gameScoreIsOne() throws PlayerIsEliminatedException, CurrentRoundIsNotFinishedException {
//        WordService wordService = mock(WordService.class);
//        when(wordService.provideRandomWord(6))
//                .thenReturn("hoeden");
//
//        // Setup a fake game to return from the
//        // mocked SpringGameRepository (WORD_EXISTS gives true when called)
//        Game game = new Game();
//        game.setId(0L);
//        game.startNewRound("hoeden");
//        game.attemptWordGuess("hoeden");
//
//        SpringGameRepository repository = mock(SpringGameRepository.class);
//        when(repository.findById(anyLong()))
//                .thenReturn(Optional.of(game));
//
//        TrainerService trainerService = new TrainerService(wordService, repository);
//        GameProgress progress = trainerService.startNewRound(0L);
//
//        assertEquals(1, progress.getScore());
//        assertEquals(GameState.WAITING_FOR_ROUND, progress.getGameState());
//    }




}