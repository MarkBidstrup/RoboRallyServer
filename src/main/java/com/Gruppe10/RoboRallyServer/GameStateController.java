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

// @author Deniz Isikli & Xiao Chen
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
    public ResponseEntity<String> addGameStateTemplate(@RequestBody String p) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(p, GameStateTemplate.class);
        boolean added = gamesService.updateGameStateTemplate(t);
        return ResponseEntity.ok().body("updated");
    }

    @PutMapping("/gameState/{gameID}")
    public ResponseEntity<String > updateGameStateTemplate(@PathVariable String gameID, @RequestBody String p) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(p, GameStateTemplate.class);
        gamesService.updateGameStateTemplate(t);
        return ResponseEntity.ok().body("updated");
    }
}
