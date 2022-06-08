package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import com.Gruppe10.RoboRallyServer.Model.PlayerTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
@author Golbas Haidari
 */
@Service
public class OnlineGameServices implements IOnlineGameServices{
    ArrayList<GameStateTemplate> onlineGames;

    public OnlineGameServices() {
        onlineGames= new ArrayList<>();
    }

    @Override
    public boolean createGame(GameStateTemplate template) {
        if ( onlineGames.stream().filter(game-> (game.board.boardName.equals(template.board.boardName) && game.gameId== template.gameId)).findAny().isPresent()){
            return false;
        }

        onlineGames.add(template);
        return true;
    }


    @Override
    public int getNumberOfJoinedPlayers(String boardname, int gameId){
        int numberOfJoined=0;
        GameStateTemplate onlineGame= onlineGames.stream().filter(game -> game.board.boardName.equals(boardname)  && game.gameId == gameId).findAny().orElse(null);
        if(onlineGames != null){
            numberOfJoined= onlineGame.players.stream().filter(player -> (!player.playerName.equals("null"))).toList().size();
        }
        return numberOfJoined;
    }


    @Override
    public int getMaxNumberOfPlayers(String boardname, int gameId){
        return onlineGames.stream().filter(game -> game.board.boardName.equals(boardname)  && game.gameId == gameId ).findAny().get().players.size();
    }


    @Override
    public List<String> getOnlineGames(){
        List<String> list = new ArrayList<>();
        for (GameStateTemplate game : onlineGames) {
            int joinedNr=getNumberOfJoinedPlayers(game.board.boardName, game.gameId);
            int maxNr= getMaxNumberOfPlayers(game.board.boardName, game.gameId);
            if( joinedNr < maxNr ) {
                list.add("Board: " + game.board.boardName + " - GameID: " + game.gameId);
            }
        }

        return list;
    }


    @Override
    public GameStateTemplate getOnlineGame(String boardname, int gameId) {
        return onlineGames.stream().filter(game -> (game.board.boardName.equals(boardname)  && game.gameId == gameId )).findAny().orElse(null);
    }


    @Override
    public boolean joinOnlineGame(String boardname,int gameId, String playerName){
        GameStateTemplate onlineGame= onlineGames.stream().filter(game -> game.board.boardName.equals(boardname)  && game.gameId == gameId).findAny().orElse(null);
        if(onlineGame != null){
            int index= onlineGame.players.stream().filter(player -> (!player.playerName.equals("null"))).toList().size();
            onlineGame.players.get(index).playerName=playerName;
            return true;
        }
        return false;
    }
}
