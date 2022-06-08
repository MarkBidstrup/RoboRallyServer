package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.Adapter;
import com.Gruppe10.RoboRallyServer.Model.FieldAction;
import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

// @author Deniz Isikli, Xiao Chen & Mark Bidstrup
@Service
public class GameStateService implements IGameState{
    ArrayList<GameStateTemplate> currentGames;
    HashMap<String, Integer> programmingCounter;

    public GameStateService() {
        currentGames = new ArrayList<>();
        programmingCounter = new HashMap<>();
    }

    @Override
    public GameStateTemplate getGameStateTemplate(String boardname_gameID) {
        if (!currentGames.isEmpty()) {
            for(GameStateTemplate p : currentGames) {
                String str = p.board.boardName + "_" +p.gameId;
                if(str.equals(boardname_gameID)) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateGameStateTemplate(GameStateTemplate p) {
        String gameID = p.board.boardName + "_" + p.gameId;
        if (getGameStateTemplate(gameID) != null)
            currentGames.remove(getGameStateTemplate(gameID));
        currentGames.add(p);
        return true;
    }

    @Override
    public Integer getProgrammingCounter(String gameID) {
        return programmingCounter.get(gameID);
    }

    @Override
    public void incrementProgrammingCounter(String gameID) {
        Integer value = programmingCounter.get(gameID) + 1;
        programmingCounter.replace(gameID,value);
    }

    @Override
    public void setProgrammingCounter(String gameID, Integer value) {
        programmingCounter.replace(gameID, value);
    }

    @Override
    public void addNewProgrammingCounter(String gameID) {
        programmingCounter.put(gameID,0);
    }
}
