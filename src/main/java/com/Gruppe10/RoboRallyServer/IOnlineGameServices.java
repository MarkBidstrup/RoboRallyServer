package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

public interface IOnlineGameServices {
    public boolean createGame(GameStateTemplate template);
    public int getNumberOfJoinedPlayers(String boardname, int gameId);
    public int getMaxNumberOfPlayers(String boardname,int gameId);
    public GameStateTemplate getOnlineGame(String boardname, int gameId);
    public List<String> getOnlineGames();
    public boolean joinOnlineGame(String boardname,int gameId, String playerName);
}
