package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("straat", Arrays.asList("stoomt", "strook"), Arrays.asList('s', 't', 'r', null, null, 't'))
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("hint is correct if marks are correctly used when generating a new hint")
    void correctHintIsGiven(String wordToGuess, List<String> wordGuesses, List<Character> hint){
        Round round = new Round(wordToGuess);
        for (String guess : wordGuesses){
            round.giveFeedback(guess);
        }
        assertEquals(hint, round.giveHint());
    }

    static Stream<Arguments> provideIncorrectHintExamples() {
        return Stream.of(
                Arguments.of("straat", Arrays.asList("stoomt", "strook"), Arrays.asList('s', 't', 'r', 'a', null, 't'))
        );
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectHintExamples")
    @DisplayName("hint is not correct if marks are correctly used when generating a new hint")
    void incorrectHintIsGiven(String wordToGuess, List<String> wordGuesses, List<Character> hint){
        Round round = new Round(wordToGuess);
        for (String guess : wordGuesses){
            round.giveFeedback(guess);
        }
        assertNotEquals(hint, round.giveHint());
    }

    @Test
    @DisplayName("hint with first letter is given on first attempt")
    void hintWithFirstLetterIsGiven(){
        Round round = new Round("banana");
        assertEquals(Arrays.asList('b', null, null, null, null, null), round.giveHint());
    }
}