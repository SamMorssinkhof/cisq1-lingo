package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private  Long id;

    private String attempt;

    @ElementCollection
    private List<Mark> marks;

    public Feedback(String attempt) {
        this.attempt = attempt;
        this.marks = new ArrayList<>();
    }

    public boolean isWordGuessed(){
        return marks.stream().allMatch(i -> i == Mark.CORRECT);
    }

    public Feedback() {
    }

    public String getAttempt() {
        return attempt;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void markGuessAttempt(String wordToGuess) {
        if (attempt.length() != wordToGuess.length()) {
            marks.addAll(Collections.nCopies(attempt.length(), Mark.INVALID));
            return;
        }

        List<Character> wordToGuessCharList = wordToGuess.chars()
                .mapToObj(e->(char)e).collect(Collectors.toList());

        for (int i = 0; i < wordToGuess.length(); i++) {

            if (attempt.charAt(i) == wordToGuess.charAt(i)) {
                marks.add(i, Mark.CORRECT);
                continue;
            }

            boolean contains = false;
            for (int x = 0; x < wordToGuessCharList.size(); x++) {
                if (wordToGuessCharList.get(x) == attempt.charAt(i)) {
                    contains = true;
                    wordToGuessCharList.remove(x);
                    break;
                }
            }
            if(contains){
                marks.add(i, Mark.PRESENT);
            } else {
                marks.add(i, Mark.ABSENT);
            }

        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
