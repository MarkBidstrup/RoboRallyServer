package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

public interface IOnlineGameServices {
    public boolean createGame(GameStateTemplate template);
    public boolean createLobby(String boardname, int gameId, int players);
    public int getNumberOfJoinedPlayers(String boardname, int gameId);
    public int getMaxNumberOfPlayers(String boardname,int gameId);
    public GameStateTemplate getLobbyGame(String boardname, int gameId);
    public List<String> getLobbies();
    public boolean joinLobby(String boardname,int gameId);
}
