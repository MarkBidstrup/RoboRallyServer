package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.Adapter;
import com.Gruppe10.RoboRallyServer.Model.FieldAction;
import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
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
    public ResponseEntity<String> addGameStateTemplate(@RequestBody String gameID) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(gameID, GameStateTemplate.class);
        boolean added = gamesService.updateGameStateTemplate(t);
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
    public ResponseEntity<String> setProgrammingCounter(@PathVariable String gameID, @RequestBody Integer value) {
        gamesService.setProgrammingCounter(gameID,value);
        return ResponseEntity.ok().body("updated");
    }

    @PutMapping("/gameState/{gameID}/programmingCounter/increment")
    public ResponseEntity<String> incrementProgrammingCounter(@PathVariable String gameID, @RequestBody Integer value) {
        gamesService.incrementProgrammingCounter(gameID);
        return ResponseEntity.ok().body("updated");
    }

}
