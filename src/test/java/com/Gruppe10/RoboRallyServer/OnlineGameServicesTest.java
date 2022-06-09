package com.Gruppe10.RoboRallyServer;

import com.Gruppe10.RoboRallyServer.Model.GameStateTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.Gruppe10.RoboRallyServer.SavedGamesService.loadGame;
import static org.junit.jupiter.api.Assertions.*;

/*
@author Golbas Haidari
 */
class OnlineGameServicesTest {
    OnlineGameServices onlineGameServices;
    String boardname;
    int gameId;
    @BeforeEach
    void setUp() {
        onlineGameServices = new OnlineGameServices();
        boardname="EasyIntro";
        gameId= 1;
    }

    // UTC 20
    @Test
    void createGame() {
        GameStateTemplate template= loadGame(boardname+"_"+gameId);
        template.players.forEach(player -> player.playerName="null");

        boolean created = onlineGameServices.createGame(template);
        assertEquals(true, created);

    }

    // UTC 21
    @Test
    void getOnlineGames() {
        createGame();
        int nr = onlineGameServices.getOnlineGames().size();
        assertEquals(1, nr);
    }

    // UTC 22
    @Test
    void getOnlineGame() {
        createGame();
        GameStateTemplate game= onlineGameServices.getOnlineGame(boardname,gameId);
        assertEquals("EasyIntro", game.board.boardName);
        assertEquals(1, game.gameId);
    }

    // UTC 23
    @Test
    void getMaxNumberOfPlayers() {
        createGame();
        int maxNr= onlineGameServices.getMaxNumberOfPlayers(boardname,gameId);
        assertEquals(2, maxNr);
    }

    // UTC 24
    @Test
    void joinOnlineGame() {
        createGame();
        boolean joined= onlineGameServices.joinOnlineGame(boardname,gameId,"Golbas");
        assertEquals(true,joined);
    }

    // UTC 25
    @Test
    void getNumberOfJoinedPlayers_1() {
        createGame();
        int joinedPlayer= onlineGameServices.getNumberOfJoinedPlayers(boardname, gameId);
        assertEquals(0, joinedPlayer);
    }

    // UTC 26
    @Test
    void getNumberOfJoinedPlayers_2() {
        createGame();
        onlineGameServices.joinOnlineGame(boardname,gameId,"Golbas");
        int joinedPlayer= onlineGameServices.getNumberOfJoinedPlayers(boardname, gameId);
        assertEquals(1, joinedPlayer);
    }
}