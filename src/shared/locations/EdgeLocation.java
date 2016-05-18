package shared.locations;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation
{

	private EdgeDirection direction;
	private int x;
	private int y;
	
	public EdgeLocation(HexLocation hexLoc, EdgeDirection direction)
	{
		setHexLoc(hexLoc);
		setDir(direction);
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
	
	public EdgeDirection getDir()
	{
		return direction;
	}
	
	private void setDir(EdgeDirection direction)
	{
		this.direction = direction;
	}
	
	@Override
	public String toString()
	{
		HexLocation hexLoc = new HexLocation(x,y);
		return "EdgeLocation [hexLoc=" + hexLoc + ", direction=" + direction + "]";
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
		EdgeLocation other = (EdgeLocation)obj;
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
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation()
	{
		HexLocation hexLoc = new HexLocation(x,y);
		// Return an EdgeLocation that has direction NW, N, or NE
		
		switch (direction)
		{
			case NW:
			case N:
			case NE:
				return this;
			case SW:
			case S:
			case SE:
				return new EdgeLocation(hexLoc.getNeighborLoc(direction),
										direction.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}
}

