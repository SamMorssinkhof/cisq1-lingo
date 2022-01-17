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
            round.guessWord(guess);
        }
        assertEquals(hint, round.giveHint());
    }


}