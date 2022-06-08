package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import com.Gruppe10.RoboRallyServer.Model.Lobby;
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
    ArrayList<Lobby> lobbies;

    public OnlineGameServices() {
        onlineGames= new ArrayList<>();
        lobbies= new ArrayList<>();
    }

    @Override
    public boolean createGame(GameStateTemplate template) {
        if (onlineGames.stream().filter(game-> (game.board.boardName.equals(template.board.boardName) &&
                game.gameId== template.gameId)).findAny().orElse(null) != null)
            return false;
        else {
            onlineGames.add(template);
            return true;
        }
    }


    @Override
    public int getNumberOfJoinedPlayers(String boardname, int gameId){
        int numberOfJoined=0;
        for(GameStateTemplate game:onlineGames ){
            if(game.board.boardName.equals(boardname)  && game.gameId == gameId){
                for(PlayerTemplate p: game.players){
                    if(p.joined== true){
                        numberOfJoined++;
                    }
                }
            }
        }
        return numberOfJoined;
    }


    @Override
    public int getMaxNumberOfPlayers(String boardname, int gameId){
        int max=0;
        for(GameStateTemplate game:onlineGames ){
            if(game.board.boardName.equals(boardname)  && game.gameId == gameId){
                max= game.players.size();
            }
        }
        return max;
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
        if (!onlineGames.isEmpty()) {
            for(GameStateTemplate game : onlineGames) {
                if(game.board.boardName.equals(boardname)  && game.gameId == gameId ){
                    return game;
                }
            }
        }
        return null;
    }


    @Override
    public boolean joinOnlineGame(String boardname,int gameId, int playernr){
        for(GameStateTemplate game:onlineGames ){
            if(game.board.boardName.equals(boardname)  && game.gameId == gameId){
                game.players.get((playernr-1)).joined=true;
                return true;
            };
        }
        return false;
    }
}
