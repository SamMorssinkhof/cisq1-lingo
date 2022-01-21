package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TrainerService {

    private WordService wordService;
    private SpringGameRepository gameRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public GameProgress startNewGame() {
        Game game = new Game();

        game.startNewRound(getNewWord(game));
        game = gameRepository.save(game);

        return game.showProgress();
    }

    public GameProgress guess(Long gameId, String guessAttempt) {
        Game game = getGameById(gameId);

        game.attemptWordGuess(guessAttempt);
        gameRepository.save(game);

        return  game.showProgress();
    }

    public GameProgress startNewRound(Long gameId) {
        Game game = getGameById(gameId);

        game.startNewRound(getNewWord(game));
        gameRepository.save(game);

        return game.showProgress();
    }

    public GameProgress getGameProgress(Long gameId) {
        return getGameById(gameId).showProgress();
    }

    public Game getGameById(Long gameId) throws EntityNotFoundException {
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("No game found with id: " + gameId));
    }

    private String getNewWord(Game game){
        return wordService.provideRandomWord(game.provideNewWordLength());
    }
}
