package server.jsonObjects;

public class GamesCreateJsonObject {

	private boolean randomTiles;
	private boolean randomNumbers; 
	private boolean randomPorts;
	private String name;
	
	public GamesCreateJsonObject(boolean t, boolean n, boolean p, String nm){
		randomTiles = t;
		randomNumbers = n;
		randomPorts = p;
		name = nm; 
	}

	public boolean isRandomTiles() {
		return randomTiles;
	}

	public void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}

	public boolean isRandomNumbers() {
		return randomNumbers;
	}

	public void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}

	public boolean isRandomPorts() {
		return randomPorts;
	}

	public void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
