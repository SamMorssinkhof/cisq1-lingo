package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GuessDto;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.CurrentRoundIsNotFinishedException;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.PlayerIsEliminatedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("trainer")
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/game")
    private GameProgress startNewGame() {
        return trainerService.startNewGame();
    }

    @PostMapping("/game/{id}/round")
    private GameProgress startNewRound(@PathVariable Long id) {
        return trainerService.startNewRound(id);
    }

    @PostMapping("/game/{id}/guess")
    private GameProgress guess(@PathVariable Long id, @RequestBody GuessDto guessDto) {
        return trainerService.guess(id, guessDto.guess);
    }
}
