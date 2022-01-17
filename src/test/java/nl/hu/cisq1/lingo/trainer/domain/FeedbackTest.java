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
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.INVALID));

        assertFalse(feedback.isWordGuessed());
    }


    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("straat", new Feedback("stroom", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT)), Arrays.asList('s', 't', '*', '*', '*'), Arrays.asList('s', 't', 'r', '*', '*')),
                Arguments.of("straat", new Feedback("strak", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)), Arrays.asList('s', 't', '*', '*', '*'), Arrays.asList('s', 't', '*', '*', '*')),
                Arguments.of("straat", new Feedback("stoorm", List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT)), Arrays.asList('s', 't', '*', '*', '*'), Arrays.asList('s', 't', '*', '*', '*'))
                );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct if marks are correctly used when generating a new hint")
    void correctHintIsGiven(String wordToGuess, Feedback feedback, List<Character> previousHint, List<Character> newHint){
        List<Character> hint = feedback.giveHint(previousHint, wordToGuess);
        assertEquals(newHint, hint);
    }


}