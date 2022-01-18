package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Round {
    private String wordToGuess;
    private int attempts;
    private List<Feedback> feedbackHistory;

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.feedbackHistory = new ArrayList<>();
        this.attempts = 0;
    }

    public void giveFeedback(String guessAttempt){
        attempts++;
        Feedback feedback = new Feedback(guessAttempt);
        feedback.markGuessAttempt(wordToGuess);
        feedbackHistory.add(feedback);
    }

    public List<Character> giveHint(){
        List<Character> hint = Arrays.asList(new Character[wordToGuess.length()]);

        if(feedbackHistory.isEmpty()){
            hint.set(0, wordToGuess.charAt(0));
        } else {
            for(Feedback feedback : feedbackHistory){
                for(int i = 0; i < feedback.getMarks().size(); i++){
                    if(feedback.getMarks().get(i) == Mark.CORRECT){
                        hint.set(i, feedback.getAttempt().charAt(i));
                    }
                }
            }
        }
        return hint;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getAttempts() {
        return attempts;
    }
}
