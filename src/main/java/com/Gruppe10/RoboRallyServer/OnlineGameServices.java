package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import com.Gruppe10.RoboRallyServer.Model.Lobby;
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
    public boolean createLobby(String boardname, int gameId, int players) {

        boolean exist= false;
        for(Lobby lb:lobbies ){
            if(lb.boardname.equals(boardname)  && lb.gameId == gameId){
                exist= true;
            };
        }

        if(exist == false){
            Lobby lobby = new Lobby();
            lobby.boardname= boardname;
            lobby.gameId= gameId;
            lobby.players=players;
            lobby.numberOfJoined= 1;
            lobbies.add(lobby);
            return true;
        }
        return false;
    }


    @Override
    public int getNumberOfJoinedPlayers(String boardname, int gameId){
        for(Lobby lb:lobbies ){
            if(lb.boardname.equals(boardname)  && lb.gameId == gameId){
                return lb.numberOfJoined;
            }
        }
        return 0;
    }


    @Override
    public int getMaxNumberOfPlayers(String boardname, int gameId){
        for(Lobby lb:lobbies ){
            if(lb.boardname.equals(boardname)  && lb.gameId == gameId){
                return lb.players;
            }
        }
        return 0;
    }


    @Override
    public List<String> getLobbies(){
        List<String> lobbyList = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            if(lobby.numberOfJoined < lobby.players) {
                lobbyList.add("Board: " + lobby.boardname + " - GameID: " + lobby.gameId);
            }
        }
        return lobbyList;
    }


    @Override
    public GameStateTemplate getLobbyGame(String boardname, int gameId) {
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
    public boolean joinLobby(String boardname,int gameId){
        for(Lobby lb:lobbies ){
            if(lb.boardname.equals(boardname)  && lb.gameId == gameId){
                lb.numberOfJoined+= 1;
                return true;
            };
        }
        return false;
    }
}
