package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed(){
        Feedback feedback = new Feedback("woord");
        feedback.markGuessAttempt("woord");

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("woord");
        feedback.markGuessAttempt("banaan");

        assertFalse(feedback.isWordGuessed());
    }

    static Stream<Arguments> provideGuessExamples() {
        return Stream.of(
                Arguments.of("straat", "stroom", Arrays.asList(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("kelder", "lelijk", Arrays.asList(Mark.PRESENT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT)),
                Arguments.of("banaan", "appel", Arrays.asList(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID))
        );
    }

    @ParameterizedTest
    @MethodSource("provideGuessExamples")
    @DisplayName("Feedback is correctly marked when each letter is correctly marked")
    void guessAttemptIsCorrectlyMarked(String wordToGuess, String guessAttempt, List<Mark> marks){
        Feedback feedback = new Feedback(guessAttempt);
        feedback.markGuessAttempt(wordToGuess);
        assertEquals(marks, feedback.getMarks());
    }

    static Stream<Arguments> provideIncorrectGuessExamples() {
        return Stream.of(
                Arguments.of("straat", "stroom", Arrays.asList(Mark.CORRECT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("kelder", "lelijk", Arrays.asList(Mark.PRESENT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)),
                Arguments.of("banaan", "appel", Arrays.asList(Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT))
        );
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectGuessExamples")
    @DisplayName("Feedback is incorrectly marked when not all letters are correctly marked")
    void guessAttemptIsInCorrectlyMarked(String wordToGuess, String guessAttempt, List<Mark> marks){
        Feedback feedback = new Feedback(guessAttempt);
        feedback.markGuessAttempt(wordToGuess);
        assertNotEquals(marks, feedback.getMarks());
    }
}