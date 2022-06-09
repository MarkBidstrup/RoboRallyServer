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

    @PutMapping("/savedGames/leaveJoinedGame/{gameID}")
    public ResponseEntity<String> leaveJoinedGame(@PathVariable String gameID, @RequestBody String name) {
        gamesService.leaveJoinedGame(gameID, name);
        return ResponseEntity.ok().body("updated");
    }

    @GetMapping("/savedGames/getAvailPlayers/{gameID}")
    public ResponseEntity<List<String>> getAvailablePlayers(@PathVariable String gameID) {
        return ResponseEntity.ok().body(gamesService.getAvailablePlayers(   gameID));
    }

    @PutMapping("/savedGames/joinLoadedGame/{gameID}")
    public ResponseEntity<String> joinLoadedGame(@PathVariable String gameID, @RequestBody String name) {
        boolean joined = gamesService.joinLoadedGame(gameID, name);
        if (joined)
            return ResponseEntity.ok().body("Joined");
        else return ResponseEntity.badRequest().body("Not joined");
    }

    @GetMapping ("/savedGames/allJoinedStatus/{gameID}")
    public ResponseEntity<String> allPlayersJoined(@PathVariable String gameID) {
        boolean allJoined = gamesService.allPlayersJoined(gameID);
        if (allJoined)
            return ResponseEntity.ok().body("Ready");
        else return ResponseEntity.badRequest().body("Not ready");
    }

    @GetMapping ("/savedGames/activeGames")
    public ResponseEntity<List<String>> getActiveGames() {
        return ResponseEntity.ok().body(gamesService.getActiveGames() );
    }

    @PostMapping("/savedGames/activeGames")
    public ResponseEntity<String> addActiveGame(@RequestBody String p) {
        boolean added = gamesService.addActiveGame(p);
        if (added)
            return ResponseEntity.ok().body("Added");
        else return ResponseEntity.badRequest().body("Not added");    }
}
