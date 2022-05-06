package client.gamePackage;

import java.util.List;

public class Path {

	private Boolean treasureFound;
	private Boolean castleFound;
	private List<Movement> moves;
	
	
	public Path() {
		treasureFound = false;
		castleFound = false;
		
	}
	
	
	
	public Path(Boolean treasureFound, Boolean castleFound, List<Movement> moves) {
		super();
		this.treasureFound = treasureFound;
		this.castleFound = castleFound;
		this.moves = moves;
	}


	
	

	public Boolean getTreasureFound() {
		return treasureFound;
	}


	public Boolean getCastleFound() {
		return castleFound;
	}


//	public List<Movement> findPath () {
//		
//	}
	
	
	
	
}
