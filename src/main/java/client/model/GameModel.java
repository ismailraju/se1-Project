package client.model;

import client.controller.GameController;
import client.gameLogic.Map;
import client.gameLogic.Player;

import java.util.List;

public class GameModel {

    Map map;
    List<Player> players;
    GameController controller;
}
