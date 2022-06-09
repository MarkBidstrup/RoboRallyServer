package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

// @author Mark Bidstrup & Xiao Chen
public interface ISavedGamesService {
    List<String> findAll();
    public GameStateTemplate getGameStateTemplate(String boardname_gameID);
    public boolean addGameStateTemplate(GameStateTemplate p);
    public boolean updateGameStateTemplate(GameStateTemplate p);
    public boolean deleteGameStateTemplate(String boardname_gameID);
    public List<String> getAvailablePlayers(String boardname_gameID);
    public boolean joinLoadedGame(String boardname_gameID, String playerName);
    public boolean allPlayersJoined(String boardname_gameID);
    public void leaveJoinedGame(String boardname_gameID, String playerName);
    public boolean addActiveGame(String boardname_gameID);
    public List<String> getActiveGames();
}
