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

// @author Mark Bidstrup & Xiao Chen
@RestController
public class SavedGamesController {

    @Autowired
    private ISavedGamesService gamesService;

    @GetMapping("/savedGames")
    public ResponseEntity<List> getListOfGames() {
        List<String> list = gamesService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/savedGames/{gameID}")
    public ResponseEntity<String> getGameStateTemplate(@PathVariable String gameID) {
        GameStateTemplate p = gamesService.getGameStateTemplate(gameID);
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        String boardJSON = gson.toJson(p);
        return ResponseEntity.ok().body(boardJSON);
    }

    @PostMapping("/savedGames")
    public ResponseEntity<String> addGameStateTemplate(@RequestBody String p) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(p, GameStateTemplate.class);
        boolean added = gamesService.addGameStateTemplate(t);
        if(added)
            return ResponseEntity.ok().body("added");
        else
            return ResponseEntity.internalServerError().body("not added");       }

    @PutMapping("/savedGames/{gameID}")
    public ResponseEntity<String > updateGameStateTemplate(@PathVariable String gameID, @RequestBody String p) {
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate t = gson.fromJson(p, GameStateTemplate.class);
        gamesService.updateGameStateTemplate(t);
        return ResponseEntity.ok().body("updated");
    }

    @DeleteMapping("/savedGames/{gameID}")
    public ResponseEntity<String> deleteGame(@PathVariable String gameID) {
        boolean deleted = gamesService.deleteGameStateTemplate(gameID);
        if(deleted)
            return ResponseEntity.ok().body("deleted");
        else
            return ResponseEntity.internalServerError().body("not deleted");    }
}
