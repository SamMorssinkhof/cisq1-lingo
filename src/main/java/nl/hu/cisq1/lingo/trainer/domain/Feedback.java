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

//    public List<Character> giveHint(List<Character> previousHint, String wordToGuess){
//        if (marks.stream().allMatch(i -> i == Mark.INVALID)){
//            return previousHint;
//        } else {
//            for (int i = 0; i < wordToGuess.length()-1; i++){
//                if(previousHint.get(i) == '*' && marks.get(i) == Mark.CORRECT){
//                    previousHint.set(i, attempt.charAt(i));
//                }
//            }
//        }
//        return previousHint;
//    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }
}
