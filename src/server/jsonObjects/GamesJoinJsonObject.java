package server.jsonObjects;

public class GamesJoinJsonObject {
	private Integer id;
	private String color; 
	
	public GamesJoinJsonObject(Integer i, String c){
		id = i;
		color = c; // which is a lowercase color enum
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}