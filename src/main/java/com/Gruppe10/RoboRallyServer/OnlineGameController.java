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


    @RequestMapping(path = "/joinedPlayers/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getNumberOfJoinedPlayers(@PathVariable String boardname, @PathVariable int gameId){
        int result= services.getNumberOfJoinedPlayers(boardname,gameId);
        return ResponseEntity.ok().body(String.valueOf(result));
    }


    @RequestMapping(path = "/joinOnlineGame/{boardname}/{gameId}/{playerName}", method = RequestMethod.GET)
    public ResponseEntity<String> joinOnlineGame(@PathVariable String boardname, @PathVariable int gameId, @PathVariable String playerName){
        boolean joined= services.joinOnlineGame(boardname,gameId, playerName);
        if(joined == true) {
            return ResponseEntity.ok().body("joined");
        }
        else {
            return ResponseEntity.internalServerError().body("not joines");
        }
    }


    @RequestMapping(path = "/maxPlayers/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getMaxNumberOfPlayers(@PathVariable String boardname, @PathVariable int gameId){
        int result= services.getMaxNumberOfPlayers(boardname,gameId);
        return ResponseEntity.ok().body(String.valueOf(result));
    }


    @RequestMapping(path = "/onlineGame/{boardname}/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<String> getOnlineGame(@PathVariable String boardname, @PathVariable int gameId){
        GameStateTemplate template = services.getOnlineGame(boardname,gameId);
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();
        String result = gson.toJson(template);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/onlineGamesList")
    public ResponseEntity<List> getOnlineGames() {
        List<String> list = services.getOnlineGames();
        return ResponseEntity.ok().body(list);
    }
}
