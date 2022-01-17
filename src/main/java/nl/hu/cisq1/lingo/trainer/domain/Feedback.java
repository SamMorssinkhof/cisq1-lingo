package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean isWordGuessed(){
        return marks.stream().allMatch(i -> i == Mark.CORRECT);
    }

//    public List<Character> giveHint(List<Feedback> feedbackHistory, String wordToGuess){
//        List<Character> hint = Arrays.asList(new Character[wordToGuess.length()]);
//
//        for(Feedback feedback : feedbackHistory){
//            for(Mark mark : feedback.marks){
//                if(mark == Mark.CORRECT){
//                    int markIndex = feedback.marks.indexOf(mark);
//                    hint.add(markIndex, feedback.attempt.charAt(markIndex));
//                }
//            }
//        }
//        return hint;
//    }

    public List<Mark> getMarks() {
        return marks;
    }

    public String getAttempt(){
        return attempt;
    }

    public List<Character> giveHint(List<Character> previousHint, String wordToGuess){
        if (marks.stream().allMatch(i -> i == Mark.INVALID)){
            return previousHint;
        } else {
            for (int i = 0; i < wordToGuess.length()-1; i++){
                if(previousHint.get(i) == '*' && marks.get(i) == Mark.CORRECT){
                    previousHint.set(i, attempt.charAt(i));
                }
            }
        }
        return previousHint;
    }



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
