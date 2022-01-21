package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class GameProgress {
    private Long gameId;
    private int score;
    private GameState gameState;
    private List<Feedback> feedbackHistory;
    private List<Character> hint;

    public GameProgress(Long id, int score, GameState gameState, List<Feedback> feedbackHistory, List<Character> hint) {
        this.gameId = id;
        this.score = score;
        this.gameState = gameState;
        this.feedbackHistory = feedbackHistory;
        this.hint = hint;
    }

    public Long getGameId() {
        return gameId;
    }

    public int getScore() {
        return score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    public List<Character> getHint() {
        return hint;
    }
}
