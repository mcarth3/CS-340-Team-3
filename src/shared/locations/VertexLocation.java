package shared.locations;

import java.util.ArrayList;

import model.Game;


/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation
{
	
	public VertexDirection direction;
	//public String direction;
	public int x;
	public int y;
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public HexLocation getHexLoc()
	{
		HexLocation hexLoc = new HexLocation(x,y);
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.x = hexLoc.getX();
		this.y = hexLoc.getY();
	}
	
	public VertexDirection getDir()
	{
//		VertexDirection result = VertexDirection.East;
//		if(direction.equals("NE")){
//			result = VertexDirection.NorthEast;
//		}else if(direction.equals("NW")){
//			result = VertexDirection.NorthWest;
//		}else if(direction.equals("E")){
//			result = VertexDirection.East;
//		}else if(direction.equals("SE")){
//			result = VertexDirection.SouthEast;
//		}else if(direction.equals("SW")){
//			result = VertexDirection.SouthWest;
//		}else if(direction.equals("W")){
//			result = VertexDirection.West;
//		}
		return direction; 
	}
	
	private void setDir(VertexDirection direction)
	{
		this.direction = direction;
	}
	
	@Override
	public String toString()
	{
		HexLocation hexLoc = new HexLocation(x,y);
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + direction + "]";
	}
	
	@Override
	public int hashCode()
	{
		HexLocation hexLoc = new HexLocation(x,y);
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		HexLocation hexLoc = new HexLocation(x,y);
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		VertexLocation other = (VertexLocation)obj;
		if(direction != other.direction)
			return false;
		HexLocation hexLoc2 = new HexLocation(other.x,other.x);
		if(hexLoc == null)
		{
			if(hexLoc2 != null)
				return false;
		}
		else if(!hexLoc.equals(hexLoc2))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation()
	{
		
		// Return location that has direction NW or NE
		HexLocation hexLoc = new HexLocation(x,y);
		switch (direction)
		{
			case NW:
			case NE:
				return this;
			case W:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SW),
										  VertexDirection.NE);
			case SW:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.S),
										  VertexDirection.NW);
			case SE:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.S),
										  VertexDirection.NE);
			case E:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SE),
										  VertexDirection.NW);
			default:
				assert false;
				return null;
		}
	}

	public boolean isAvailable(Game theGame, int playerIndex) {
		ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
		ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
		VertexLocation normalized = this.getNormalizedLocation();
		if (normalized.getDir()== direction.NW) {
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NW));
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
				edges.add(new EdgeLocation(new HexLocation (normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
										   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
										   EdgeDirection.NE));
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.W).getNormalizedLocation());
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NE));
				vertices.add(new VertexLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
												normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
												VertexDirection.NE));
		}else if (normalized.getDir()== direction.NE) {
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NE));
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
				edges.add(new EdgeLocation(new HexLocation (normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
						   				   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
						   				   EdgeDirection.NW));
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.E).getNormalizedLocation());
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NW));
				vertices.add(new VertexLocation(new HexLocation (normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
												normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
												VertexDirection.NW));
		}
		
		for (int k = 0; k < theGame.getMap().getsettlements().size(); k++) {
			for (int l=0; l < vertices.size(); l++) {
				if (theGame.getMap().getsettlements().get(k).getVertextLocation().getNormalizedLocation().equals(vertices.get(l).getNormalizedLocation())) {
					return false;
				}
			}
			if (theGame.getMap().getsettlements().get(k).getVertextLocation().getNormalizedLocation().equals(normalized)) 
				return false;
		}
		
		return true;
	}

	public boolean hadconnectingroad(Game gettheGame, int playerIndex) {
		ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
		ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
		VertexLocation normalized = this.getNormalizedLocation();
		switch (normalized.getDir()) {
			case NW:
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NW));
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
				edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
						   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
										   EdgeDirection.NE));
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.W).getNormalizedLocation());
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NE));
				vertices.add(new VertexLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
						   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
												VertexDirection.NE));
				break;
			case NE:
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NE));
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
				edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
						   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
						   				   EdgeDirection.NW));
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.E).getNormalizedLocation());
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NW));
				vertices.add(new VertexLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
						   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
												VertexDirection.NW));
				break;
			default: 
		}
		for (int i=0; i < gettheGame.getMap().getRoads().size(); i++) {
			for (int j=0; j < edges.size(); j++) {
				if (gettheGame.getMap().getRoads().get(i).getLocation().getNormalizedLocation().toString().equals(edges.get(j).getNormalizedLocation().toString())) {
					if (gettheGame.getMap().getRoads().get(i).getOwner() == playerIndex) {
						for (int k = 0; k < gettheGame.getMap().getsettlements().size(); k++) {
							for (int l=0; l < vertices.size(); l++) {
								if (gettheGame.getMap().getsettlements().get(k).getVertextLocation().getNormalizedLocation().toString().equals(vertices.get(l).getNormalizedLocation().toString())) {
									return false;
								}
							}
						}
						for (int k = 0; k < gettheGame.getMap().getcities().size(); k++) {
							for (int l=0; l < vertices.size(); l++) {
								if (gettheGame.getMap().getcities().get(k).getVertextLocation().getNormalizedLocation().toString().equals(vertices.get(l).getNormalizedLocation().toString())) {
									return false;
								}
							}
						}
						return true;
					}
				}		
			}
		}
		return false;
	}

	public ArrayList<HexLocation> getAdjacentHexes() {
		ArrayList<HexLocation> hexLocs = new ArrayList<HexLocation>();
		HexLocation normalized = this.getNormalizedLocation().getHexLoc();
		hexLocs.add(normalized);
		switch (this.getNormalizedLocation().getDir()) {
			case NE:
				hexLocs.add(new HexLocation(normalized.getX()+1, normalized.getY()-1));
				hexLocs.add(new HexLocation(normalized.getX(), normalized.getY()-1));
				break;
			case NW:
				hexLocs.add(new HexLocation(normalized.getX()-1,normalized.getY()));
				hexLocs.add(new HexLocation(normalized.getX(), normalized.getY()-1));
				break;
			default:
		}
		
		return hexLocs;
	}
}

