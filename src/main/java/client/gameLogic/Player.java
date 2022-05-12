package client.gameLogic;

public class Player {
	
	private String name;
	private String surname;
	private String uaccount;
	private PlayerID playerId;
	private PlayerType playerType;
	
	public Player() {
		name = "";
		surname = "";
		uaccount = "";
		playerId = null;
		
	}
	
	public Player(String name, String surname, String uaccount, PlayerID playerId) {
		super();
		this.name = name;
		this.surname = surname;
		this.uaccount = uaccount;
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getUaccount() {
		return uaccount;
	}

	public PlayerID getPlayerId() {
		return playerId;
	}
	
	public PlayerType getPlayerType() {
		return playerType;
	}


	@Override
	public String toString() {
		return "Player{" +
				"name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", uaccount='" + uaccount + '\'' +
				", playerId=" + playerId.getiD() +
				", playerType=" + playerType +
				'}';
	}
}
