package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import com.Gruppe10.RoboRallyServer.Model.Lobby;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GamesService implements IGamesService { // @author Xiao Chen
    ArrayList<GameStateTemplate> games;
    ArrayList<GameStateTemplate> onlineGames;
    ArrayList<Lobby> lobbies;

    public GamesService() {
        games = new ArrayList<>();
        onlineGames= new ArrayList<>();
        lobbies= new ArrayList<>();
    }

    //Golbas
    @Override
    public boolean createGame(GameStateTemplate template) {
        String str = template.board.boardName + "_" +template.gameId;
        if (getGameStateTemplate(str) != null)
            return false;
        else {
            onlineGames.add(template);
            return true;
        }
    }

    //Golbas
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
            lobby.numberOfJoined= 0;
            lobbies.add(lobby);
            return true;
        }
        return false;
    }

    //Golbas
    @Override
    public int getNumberOfJoinedPlayers(String boardname, int gameId){
        for(Lobby lb:lobbies ){
            if(lb.boardname.equals(boardname)  && lb.gameId == gameId){
                return lb.numberOfJoined;
            }
        }
        return 0;
    }

    //Golbas
    @Override
    public int getMaxNumberOfPlayers(String boardname, int gameId){
        for(Lobby lb:lobbies ){
            if(lb.boardname.equals(boardname)  && lb.gameId == gameId){
                return lb.players;
            }
        }
        return 0;
    }

    //Golbas
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

    //Golbas
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

    //Golbas
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

    @Override
    public List<String> findAll() {
        List<String> result = new ArrayList<>();
        for (GameStateTemplate g : games)
            result.add("Board: " + g.board.boardName + " - GameID: " + g.gameId);
        return result;
    }

    @Override
    public GameStateTemplate getGameStateTemplate(String boardname_gameID) {
        if (!games.isEmpty()) {
            for(GameStateTemplate p : games) {
                String str = p.board.boardName + "_" +p.gameId;
                if(str.equals(boardname_gameID)) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public boolean addGameStateTemplate(GameStateTemplate p) {
        String str = p.board.boardName + "_" +p.gameId;
        if (getGameStateTemplate(str) != null)
            return false;
        else {
            games.add(p);
            return true;
        }
    }

    @Override
    public boolean updateGameStateTemplate(GameStateTemplate p) {
        String gameID = p.board.boardName + "_" + p.gameId;
        if (getGameStateTemplate(gameID) != null)
            games.remove(getGameStateTemplate(gameID));
        games.add(p);
        return true;
    }

    @Override
    public boolean deleteGameStateTemplate(String boardname_gameID) {
        GameStateTemplate temp = getGameStateTemplate(boardname_gameID);
        games.remove(temp);
        return !games.contains(temp);
    }
}
