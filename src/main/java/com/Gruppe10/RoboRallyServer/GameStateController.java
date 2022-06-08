package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @author Deniz Isikli, Xiao Chen & Mark Bidstrup
@RestController
public class GameStateController {
    @Autowired
    private IGameState gamesService;

    @GetMapping("/gameState/{gameID}")
    public ResponseEntity<String> getGameStateTemplate(@PathVariable String gameID) {
        GameStateTemplate p = gamesService.getGameStateTemplate(gameID);
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        String boardJSON = gson.toJson(p);
        return ResponseEntity.ok().body(boardJSON);
    }

    @PostMapping("/gameState")
    public ResponseEntity<String> addGameStateTemplate(@RequestBody String gameState) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(gameState, GameStateTemplate.class);
        boolean added = gamesService.updateGameStateTemplate(t);
        String gameID = t.board.boardName+"_"+t.gameId;
        gamesService.addNewProgrammingCounter(gameID);
        return ResponseEntity.ok().body("updated");
    }

    @PutMapping("/gameState/{gameID}")
    public ResponseEntity<String> updateGameStateTemplate(@PathVariable String gameID, @RequestBody String gameStateTemplate) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(gameStateTemplate, GameStateTemplate.class);
        gamesService.updateGameStateTemplate(t);
        return ResponseEntity.ok().body("updated");
    }

    @GetMapping("/gameState/{gameID}/programmingCounter")
    public ResponseEntity<Integer> getProgrammingCounter(@PathVariable String gameID) {
        Integer programmingCounter = gamesService.getProgrammingCounter(gameID);
        return ResponseEntity.ok().body(programmingCounter);
    }

    @PutMapping("/gameState/{gameID}/programmingCounter/set")
    public ResponseEntity<String> setProgrammingCounter(@PathVariable String gameID, @RequestBody String value) {
        gamesService.setProgrammingCounter(gameID,Integer.parseInt(value));
        return ResponseEntity.ok().body("updated");
    }

    @PutMapping("/gameState/{gameID}/programmingCounter/increment")
    public ResponseEntity<String> incrementProgrammingCounter(@PathVariable String gameID, @RequestBody String notUsed) {
        gamesService.incrementProgrammingCounter(gameID);
        return ResponseEntity.ok().body("updated");
    }

    @PutMapping("/gameState/{gameID}/playerMat")
    public ResponseEntity<String> updatePlayerMat(@PathVariable String gameID, @RequestBody String playerTemplate) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        PlayerTemplate t = gson.fromJson(playerTemplate, PlayerTemplate.class);
        gamesService.updatePlayerMat(gameID, t);
        return ResponseEntity.ok().body("updated");
    }

}
