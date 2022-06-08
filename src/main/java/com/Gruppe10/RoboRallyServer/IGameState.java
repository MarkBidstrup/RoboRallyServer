package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

// @author Deniz Isikli & Xiao Chen
public interface IGameState {
    public GameStateTemplate getGameStateTemplate(String boardname_gameID);
    public boolean updateGameStateTemplate(GameStateTemplate p);
    public Integer getProgrammingCounter(String key);
    public void incrementProgrammingCounter(String key);
    public void setProgrammingCounter(String key, Integer value);
    public void addNewProgrammingCounter(String key);
    }
