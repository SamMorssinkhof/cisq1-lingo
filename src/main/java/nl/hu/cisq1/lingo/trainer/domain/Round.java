package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void guessWord(String guessAttempt){
        attempts++;
        Feedback feedback = new Feedback(guessAttempt);
        feedback.markGuessAttempt(wordToGuess);
        feedbackHistory.add(feedback);
    }

//    public Feedback getFeedback(String guessAttempt){
//        List<Mark> marks = new ArrayList<>();
//        if(guessAttempt.length() == wordToGuess.length()){
//            for (int i = 0; i < wordToGuess.length(); i++){
//                if(guessAttempt.charAt(i) == wordToGuess.charAt(i)){
//                    marks.add(i, Mark.CORRECT);
//                } else if (wordToGuess.indexOf(guessAttempt.charAt(i)) != -1) {
//                    marks.add(i, Mark.PRESENT);
//                } else {
//                    marks.add(i, Mark.ABSENT);
//                }
//            }
//        } else {
//            marks.addAll(Collections.nCopies(guessAttempt.length(), Mark.INVALID));
//        }
//        return new Feedback(guessAttempt, marks);
//    }


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

}
