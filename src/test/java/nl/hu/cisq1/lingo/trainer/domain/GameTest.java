package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.CurrentRoundIsNotFinishedException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.PlayerIsEliminatedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    static Stream<Arguments> provideWordLengthExamples() {
        return Stream.of(
                Arguments.of("example", 5),
                Arguments.of("appel", 6),
                Arguments.of("lekker", 7)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWordLengthExamples")
    @DisplayName("length is correctly generated when ")
    void correctLengthIsGivenForNewWordGeneration(String previousRoundWord, int newWordLength) throws PlayerIsEliminatedException, CurrentRoundIsNotFinishedException {
        Game game = new Game();
        game.startNewRound(previousRoundWord);
        assertEquals(newWordLength, game.provideNewWordLength());
    }

    @Test
    @DisplayName("length is five when it is a fresh game with no previous rounds")
    void wordLengthIsFiveForNewGames(){
        Game game = new Game();
        assertEquals(5, game.provideNewWordLength());
    }

    @Test
    @DisplayName("exception is thrown when a round exceeds five attempts and a 6th attempt is made")
    void maximumAttemptsIsExceeded() {
        try{
            Game game = new Game();
            game.startNewRound("lychee");
            List<String> attempts = Arrays.asList("Banana", "apples", "orange", "fruits", "drapes");

            for(String attempt : attempts){
                game.attemptWordGuess(attempt);
            }
            assertThrows(PlayerIsEliminatedException.class,
                    ()-> game.attemptWordGuess("lemons"),
                    "expected attemptWordGuess to throw error on the 6th attempt, but it did not.");
        } catch (PlayerIsEliminatedException | CurrentRoundIsNotFinishedException ignored) {
        }
    }

    @Test
    @DisplayName("Player is eliminated after five wrong guess attempts")
    void playerIsEliminated() throws PlayerIsEliminatedException, CurrentRoundIsNotFinishedException {
        Game game = new Game();
        game.startNewRound("orange");
        List<String> attempts = Arrays.asList("Banana", "apples", "grapes", "fruits", "drapes");

        for(String attempt : attempts){
            game.attemptWordGuess(attempt);
        }
        assertEquals(GameState.ELIMINATED, game.getGameState());
    }

    @Test
    @DisplayName("exception is thrown when a new round is started while a round is still ongoing")
    void newRoundIsStartedWhileCurrentRoundIsNotFinished() {
        try{
            Game game = new Game();
            game.startNewRound("lychee");
            List<String> attempts = Arrays.asList("Banana", "apples");

            for(String attempt : attempts){
                game.attemptWordGuess(attempt);
            }
            assertThrows(CurrentRoundIsNotFinishedException.class,
                    ()-> game.startNewRound("lemons"),
                    "expected startNewRound to throw an error because round has not finished but it did not");
        } catch (PlayerIsEliminatedException | CurrentRoundIsNotFinishedException ignored) {
        }
    }

}