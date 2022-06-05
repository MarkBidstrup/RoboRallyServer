package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GamesService implements IGamesService { // @author Xiao Chen
    ArrayList<GameStateTemplate> games;

    public GamesService() {
        games = new ArrayList<>();
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
