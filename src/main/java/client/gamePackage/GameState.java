package client.gamePackage;

import java.util.List;

public class GameState {

	private Map map;
	private String gameStateID;
	private List<Player> player;

	public GameState(Map map, String gameStateID, List<Player> player) {
		super();
		this.map = map;
		this.gameStateID = gameStateID;
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public String getGameStateID() {
		return gameStateID;
	}

	public List<Player> getPlayer() {
		return player;
	}

}
