package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

// @author Deniz Isikli, Xiao Chen & Mark Bidstrup
public interface IGameState {
    public GameStateTemplate getGameStateTemplate(String boardname_gameID);
    public boolean updateGameStateTemplate(GameStateTemplate p);
    public Integer getProgrammingCounter(String gameID);
    public void incrementProgrammingCounter(String gameID);
    public void setProgrammingCounter(String gameID, Integer value);
    public void addNewProgrammingCounter(String gameID);
    }
