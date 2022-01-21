package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.CurrentRoundIsNotFinishedException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.PlayerIsEliminatedException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn
    private List<Round> rounds;

    private int score;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    private static final int MAX_ATTEMPTS = 5;


    public Game() {
        this.rounds = new ArrayList<>();
        this.gameState = GameState.WAITING_FOR_ROUND;
    }

    public void startNewRound(String newWord) throws PlayerIsEliminatedException, CurrentRoundIsNotFinishedException {
        switch (gameState) {
            case WAITING_FOR_ROUND:
                Round newRound = new Round(newWord);
                rounds.add(newRound);
                this.gameState = GameState.PLAYING;
                break;
            case PLAYING:
                throw new CurrentRoundIsNotFinishedException();
            case ELIMINATED:
                throw new PlayerIsEliminatedException();
        }
    }

    public void attemptWordGuess(String guessAttempt) throws PlayerIsEliminatedException {
        if (getCurrentRound().getAttempts() >= MAX_ATTEMPTS) {
            throw new PlayerIsEliminatedException();
        }

        getCurrentRound().giveFeedback(guessAttempt);

        if (getCurrentRound().isWordGuessed()) {
            score += 1;
            gameState = GameState.WAITING_FOR_ROUND;
        } else if (getCurrentRound().getAttempts() == 5) {
            gameState = GameState.ELIMINATED;
        } else {
            gameState = GameState.PLAYING;
        }

    }

    public Round getCurrentRound(){
        return this.rounds.get(rounds.size()-1);
    }

    public int provideNewWordLength() {
        if(rounds.isEmpty()){
            return 5;
        }
        switch (getCurrentRound().getWordToGuess().length()) {
            case 5:
                return 6;
            case 6:
                return 7;
            default:
                return 5;
        }
    }

    public GameProgress showProgress(){
        return new GameProgress(this.id, this.score, this.gameState, getCurrentRound().getFeedbackHistory(), getCurrentRound().giveHint());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public GameState getGameState() {
        return gameState;
    }
}
