package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.MaximumAttemptsExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.Max;
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
    void correctLengthIsGivenForNewWordGeneration(String previousRoundWord, int newWordLength){
        Game game = new Game();
        game.startNewRound(previousRoundWord);
        assertEquals(newWordLength, game.getNewWordLength());
    }

    @Test
    @DisplayName("length is five when it is a fresh game with no previous rounds")
    void wordLengthIsFiveForNewGames(){
        Game game = new Game();
        assertEquals(5, game.getNewWordLength());
    }

    @Test
    @DisplayName("exception is thrown when a round exceeds five attempts")
    void maximumGuessAttemptsIsExceeded() {
        try{
            Game game = new Game();
            game.startNewRound("lychee");
            List<String> attempts = Arrays.asList("Banana", "apples", "orange", "fruits", "drapes");

            for(String attempt : attempts){
                game.attemptWordGuess(attempt);
            }
            assertThrows(MaximumAttemptsExceededException.class,
                    ()-> game.attemptWordGuess("lemons"),
                    "expected attemptWordGuess to throw on the 6th attempt, but it did not.");
        } catch (MaximumAttemptsExceededException ignored) {
        }
    }

}