package shared.locations;

import java.util.ArrayList;

import model.Game;


/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation
{
	
	private VertexDirection direction;
	private int x;
	private int y;
	
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
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SW),
										  VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.S),
										  VertexDirection.NorthWest);
			case SouthEast:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.S),
										  VertexDirection.NorthEast);
			case East:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SE),
										  VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}

	public boolean isAvailable(Game theGame, int playerIndex) {
		ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
		ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
		VertexLocation normalized = this.getNormalizedLocation();
		if (normalized.getDir()== direction.NorthWest) {
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NW));
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
				edges.add(new EdgeLocation(new HexLocation (normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
										   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
										   EdgeDirection.NE));
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.West).getNormalizedLocation());
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NorthEast));
				vertices.add(new VertexLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
												normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
												VertexDirection.NorthEast));
		}else if (normalized.getDir()== direction.NorthEast) {
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NE));
				edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
				edges.add(new EdgeLocation(new HexLocation (normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
						   				   normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
						   				   EdgeDirection.NW));
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.East).getNormalizedLocation());
				vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NorthWest));
				vertices.add(new VertexLocation(new HexLocation (normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getX(),
												normalized.getHexLoc().getNeighborLoc(edges.get(0).getDir()).getY()),
												VertexDirection.NorthWest));
		}
		
		for (int k = 0; k < theGame.getMap().getsettlements().size(); k++) {
			for (int l=0; l < vertices.size(); l++) {
				if (theGame.getMap().getsettlements().get(k).getLocation().getNormalizedLocation().equals(vertices.get(l).getNormalizedLocation())) {
					return false;
				}
			}
			if (theGame.getMap().getsettlements().get(k).getLocation().getNormalizedLocation().equals(normalized)) 
				return false;
		}
		
		return true;
	}
}

