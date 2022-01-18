package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.MaximumAttemptsExceededException;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Round> rounds;
    private Round currentRound;
    private int score;
    private GameState gameState;

    public Game() {
        this.rounds = new ArrayList<>();
    }

    public void startNewRound(String newWord){
        Round newRound = new Round(newWord);
        currentRound = newRound;
        rounds.add(newRound);
    }

    public void attemptWordGuess(String guessAttempt) throws MaximumAttemptsExceededException {
        if (currentRound.getAttempts() < 5) {
            currentRound.giveFeedback(guessAttempt);
        } else {
            throw new MaximumAttemptsExceededException();
        }
    }

    public int getNewWordLength() {
        if(currentRound == null){
            return 5;
        }
        switch (currentRound.getWordToGuess().length()) {
            case 5:
                return 6;
            case 6:
                return 7;
            default:
                return 5;
        }
    }
}
