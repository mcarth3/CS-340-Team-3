package shared.locations;

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
}

