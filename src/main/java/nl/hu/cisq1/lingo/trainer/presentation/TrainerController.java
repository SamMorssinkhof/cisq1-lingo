package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GuessDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("trainer")
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/game/{id}")
    GameProgress getGameProgress(@PathVariable Long id) {return trainerService.getGameProgress(id);
    }

    @PostMapping("/game")
    GameProgress startNewGame() {
        return trainerService.startNewGame();
    }

    @PostMapping("/game/{id}/round")
    GameProgress startNewRound(@PathVariable Long id) {
        return trainerService.startNewRound(id);
    }

    @PostMapping("/game/{id}/guess")
    GameProgress guess(@PathVariable Long id, @RequestBody GuessDto guessDto) {
        return trainerService.guess(id, guessDto.guess);
    }
}
