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

/*
@author Golbas Haidari
 */
@RestController
public class OnlineGameController {

    @Autowired
    private IOnlineGameServices services;

    @PostMapping("/createGame")
    public ResponseEntity<String> createGame(@RequestBody String game) {
        GsonBuilder simpleBuilder = new GsonBuilder().registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        GameStateTemplate template = gson.fromJson(game, GameStateTemplate.class);
        boolean created = services.createGame(template);
        if(created) {
            return ResponseEntity.ok().body("created");
        }
        else{
            return ResponseEntity.internalServerError().body("not created");
        }
    }


    @RequestMapping(path = "/createLobby/{boardname}/{gameId}/{players}", method = RequestMethod.GET)
    public ResponseEntity<String> createLobby(@PathVariable String boardname, @PathVariable int gameId, @PathVariable int players ) {
        boolean created = services.createLobby( boardname, gameId,  players);
        if(created == true) {
            return ResponseEntity.ok().body("created");
        }
        else {
            return ResponseEntity.internalServerError().body("not created");
        }
    }

    @RequestMapping(path = "/lobby/joinedPlayers/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getNumberOfJoinedPlayers(@PathVariable String boardname, @PathVariable int gameId){
        int result= services.getNumberOfJoinedPlayers(boardname,gameId);
        return ResponseEntity.ok().body(String.valueOf(result));
    }


    @RequestMapping(path = "/joinLobby/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> joinLobby(@PathVariable String boardname, @PathVariable int gameId){
        boolean joined= services.joinLobby(boardname,gameId);
        if(joined == true) {
            return ResponseEntity.ok().body("joined");
        }
        else {
            return ResponseEntity.internalServerError().body("not joines");
        }
    }


    @RequestMapping(path = "/lobby/maxPlayers/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getMaxNumberOfPlayers(@PathVariable String boardname, @PathVariable int gameId){
        int result= services.getMaxNumberOfPlayers(boardname,gameId);
        return ResponseEntity.ok().body(String.valueOf(result));
    }


    @RequestMapping(path = "/lobby/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getLobbyGame(@PathVariable String boardname, @PathVariable int gameId){
        GameStateTemplate template = services.getLobbyGame(boardname,gameId);
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        String result = gson.toJson(template);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lobbyGames")
    public ResponseEntity<List> getListOflobbyGames() {
        List<String> list = services.getLobbies();
        return ResponseEntity.ok().body(list);
    }
}
