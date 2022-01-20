package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class GameProgress {
    long gameId;
    int score;
    GameState gameState;
    List<Feedback> feedbackHistory;
    List<Character> hint;

    public GameProgress(int score, GameState gameState, List<Feedback> feedbackHistory, List<Character> hint) {
        this.score = score;
        this.gameState = gameState;
        this.feedbackHistory = feedbackHistory;
        this.hint = hint;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
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
