package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;

import java.util.List;

// @author Mark Bidstrup & Xiao Chen
public interface IGamesService {
    List<String> findAll();
    public GameStateTemplate getGameStateTemplate(String boardname_gameID);
    public boolean addGameStateTemplate(GameStateTemplate p);
    public boolean updateGameStateTemplate(GameStateTemplate p);
    public boolean deleteGameStateTemplate(String boardname_gameID);
    }
