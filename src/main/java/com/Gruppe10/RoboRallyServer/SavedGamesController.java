package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.Adapter;
import com.Gruppe10.RoboRallyServer.Model.FieldAction;
import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

// @author Xiao Chen
@RestController
public class SavedGamesController {

    @Autowired
    private IGamesService gamesService;

    //Golbas
    @PostMapping("/createGame")
    public ResponseEntity<String> createGame(@RequestBody String game) {
        GsonBuilder simpleBuilder = new GsonBuilder().registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate template = gson.fromJson(game, GameStateTemplate.class);
        boolean created = gamesService.createGame(template);
        if(created) {
            return ResponseEntity.ok().body("created");
        }
        else{
            return ResponseEntity.internalServerError().body("not created");
        }
    }

    //Golbas
    @RequestMapping(path = "/createLobby/{boardname}/{gameId}/{players}", method = RequestMethod.GET)
    public ResponseEntity<String> createLobby(@PathVariable String boardname, @PathVariable int gameId,@PathVariable int players ) {
        boolean created = gamesService.createLobby( boardname, gameId,  players);
        if(created == true) {
            return ResponseEntity.ok().body("created");
        }
        else {
            return ResponseEntity.internalServerError().body("not created");
        }
    }

    //Golbas
    @RequestMapping(path = "/lobby/joinedPlayers/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getNumberOfJoinedPlayers(@PathVariable String boardname, @PathVariable int gameId){
        int result= gamesService.getNumberOfJoinedPlayers(boardname,gameId);
        return ResponseEntity.ok().body(String.valueOf(result));
    }

    //Golbas
    @RequestMapping(path = "/joinLobby/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> joinLobby(@PathVariable String boardname, @PathVariable int gameId){
        boolean joined= gamesService.joinLobby(boardname,gameId);
        if(joined == true) {
            return ResponseEntity.ok().body("joined");
        }
        else {
            return ResponseEntity.internalServerError().body("not joines");
        }
    }

    //Golbas
    @RequestMapping(path = "/lobby/maxPlayers/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getMaxNumberOfPlayers(@PathVariable String boardname, @PathVariable int gameId){
        int result= gamesService.getMaxNumberOfPlayers(boardname,gameId);
        return ResponseEntity.ok().body(String.valueOf(result));
    }

    //Golbas
    @RequestMapping(path = "/lobby/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getLobbyGame(@PathVariable String boardname, @PathVariable int gameId){
        GameStateTemplate template = gamesService.getLobbyGame(boardname,gameId);
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        String result = gson.toJson(template);
        return ResponseEntity.ok().body(result);
    }

    //Golbas
    @GetMapping("/lobbyGames")
    public ResponseEntity<List> getListOflobbyGames() {
        List<String> list = gamesService.getLobbies();
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        String result = gson.toJson(list);
        return ResponseEntity.ok().body(list);
    }



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
