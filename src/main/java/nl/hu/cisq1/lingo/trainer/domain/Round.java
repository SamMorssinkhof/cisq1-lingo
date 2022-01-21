package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue
    private Long id;

    private String wordToGuess;

    private int attempts;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn
    private List<Feedback> feedbackHistory;

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.feedbackHistory = new ArrayList<>();
        this.attempts = 0;
    }

    public Round() {
    }

    public Feedback giveFeedback(String guessAttempt){
        attempts++;
        Feedback feedback = new Feedback(guessAttempt);
        feedback.markGuessAttempt(wordToGuess);
        feedbackHistory.add(feedback);
        return feedback;
    }

    public List<Character> giveHint(){
        List<Character> hint = new ArrayList<>(Collections.nCopies(wordToGuess.length(), '.'));

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

    public List<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    public boolean isWordGuessed(){
        return feedbackHistory.get(feedbackHistory.size()-1).isWordGuessed();
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
