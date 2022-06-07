package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

// @author Xiao Chen  && @Golbas
public interface IGamesService {
    public boolean createGame(GameStateTemplate template);
    public boolean createLobby(String boardname, String gameId, int players);
    public int getNumberOfJoinedPlayers(String boardname, String gameId);
    public int getMaxNumberOfPlayers(String boardname,String gameId);
    public GameStateTemplate getLobbyGame(String boardname, int gameId);
    public List<String> getLobbies();
    public boolean joinLobby(String boardname,int gameId);
    List<String> findAll();
    public GameStateTemplate getGameStateTemplate(String boardname_gameID);
    public boolean addGameStateTemplate(GameStateTemplate p);
    public boolean updateGameStateTemplate(GameStateTemplate p);
    public boolean deleteGameStateTemplate(String boardname_gameID);
}
