package nl.hu.cisq1.lingo.trainer.domain;

import java.util.*;

public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public Feedback(String attempt) {
        this.attempt = attempt;
        this.marks = new ArrayList<>();
    }

    public boolean isWordGuessed(){
        return marks.stream().allMatch(i -> i == Mark.CORRECT);
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public String getAttempt(){
        return attempt;
    }

    public void markGuessAttempt(String wordToGuess) {
        if (attempt.length() == wordToGuess.length()) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (attempt.charAt(i) == wordToGuess.charAt(i)) {
                    marks.add(i, Mark.CORRECT);
                } else if (wordToGuess.indexOf(attempt.charAt(i)) != -1) {
                    marks.add(i, Mark.PRESENT);
                } else {
                    marks.add(i, Mark.ABSENT);
                }
            }
        } else {
            marks.addAll(Collections.nCopies(attempt.length(), Mark.INVALID));
        }
    }
}
