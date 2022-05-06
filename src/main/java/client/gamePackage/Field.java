package client.gamePackage;

import java.util.Objects;

public class Field {

	private Navigation navigationPoint;
	private Boolean FordPresent;
	private Territory territory;
	private PlayersNavigation playersNavigation;
	private TerritoryEntity territoryEntity;

	public Field() {

	}

	public Field(Navigation navigationPoint, Territory territory, PlayersNavigation playersNavigation,
			TerritoryEntity territoryEntity) {
		super();
		this.navigationPoint = navigationPoint;
		this.territory = territory;
		this.playersNavigation = playersNavigation;
		this.territoryEntity = territoryEntity;
	}

	public Field(Navigation navigationPoint, Boolean FordPresent, Territory territory,
			PlayersNavigation playersNavigation, TerritoryEntity territoryEntity) {

		this.navigationPoint = navigationPoint;
		this.FordPresent = FordPresent;
		this.territory = territory;
		this.playersNavigation = playersNavigation;
		this.territoryEntity = territoryEntity;
	}

	public Navigation getNavigationPoint() {
		return navigationPoint;
	}

	public void setNavigation(Navigation navigationPoint) {
		this.navigationPoint = navigationPoint;
	}

	public Boolean getFordPresent() {
		return FordPresent;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	public PlayersNavigation getPlayersNavigation() {
		return playersNavigation;
	}

	public void setPlayersNavigation(PlayersNavigation playersNavigation) {
		this.playersNavigation = playersNavigation;
	}

	public TerritoryEntity getTerritoryEntity() {
		return territoryEntity;
	}

	public void setTerritoryEntity(TerritoryEntity territoryEntity) {
		this.territoryEntity = territoryEntity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(FordPresent, navigationPoint, playersNavigation, territory);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		return Objects.equals(FordPresent, other.FordPresent) && Objects.equals(navigationPoint, other.navigationPoint)
				&& playersNavigation == other.playersNavigation && territory == other.territory;
	}

	@Override
	public String toString() {
		return "Field [navigationPoint=" + navigationPoint + ", FordPresent=" + FordPresent + ", territory=" + territory
				+ ", playersNavigation=" + playersNavigation + ", territoryEntity=" + territoryEntity + "]";
	}

}
