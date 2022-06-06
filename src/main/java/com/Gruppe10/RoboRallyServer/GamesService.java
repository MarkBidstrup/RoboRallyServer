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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class GamesService implements IGamesService { // @author Xiao Chen & Mark Bidstrup
    ArrayList<GameStateTemplate> games;

    public GamesService() {
        games = new ArrayList<>();
        games.add(loadGame("EasyIntro"));
        games.add(loadGame("CheckpointChallenge"));
        games.add(loadGame("ConveyorBeltMayhem"));
    }

    @Override
    public List<String> findAll() {
        List<String> result = new ArrayList<>();
        for (GameStateTemplate g : games)
            result.add(g.board.boardName + "_" + g.gameId);
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

    public static GameStateTemplate loadGame(String boardname) { // @author Xiao Chen
        ClassLoader classLoader = GamesService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("savedgames" + "/" + boardname + "." + "json");
        if (inputStream == null) {
            return null;
        }

        // In simple cases, we can create a Gson object with new Gson():
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>());
        Gson gson = simpleBuilder.create();

        // FileReader fileReader = null;
        JsonReader reader = null;
        try {
            // fileReader = new FileReader(filename);
            reader = gson.newJsonReader(new InputStreamReader(inputStream));
            GameStateTemplate template = gson.fromJson(reader, GameStateTemplate.class);
            reader.close();
            return template;
        } catch (IOException e1) {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream = null;
                } catch (IOException e2) {}
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {}
            }
        }
        return null;
    }
}
