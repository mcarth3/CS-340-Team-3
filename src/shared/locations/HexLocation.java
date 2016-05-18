package shared.locations;

/**
 * Represents the location of a hex on a hex map
 */
public class HexLocation implements Comparable {
	
	private int x;
	private int y;
	
	public HexLocation(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public int getX()
	{
		return x;
	}
	
	private void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	private void setY(int y)
	{
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "HexLocation [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		HexLocation other = (HexLocation)obj;
		if(x != other.x)
			return false;
		if(y != other.y)
			return false;
		return true;
	}

	
	
	@Override
	public int compareTo(Object arg0) {
		HexLocation comparehex = (HexLocation)arg0;
		if (comparehex.getY()<y){
			return -1;
		}else if (comparehex.getY()>y){
			return 1;
		}else{
			if (comparehex.getX()<x){
				return -1;
			}else if (comparehex.getX()>x){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	public HexLocation getNeighborLoc(EdgeDirection dir)
	{
		switch (dir)
		{
			case NW:
				return new HexLocation(x - 1, y);
			case N:
				return new HexLocation(x, y - 1);
			case NE:
				return new HexLocation(x + 1, y - 1);
			case SW:
				return new HexLocation(x - 1, y + 1);
			case S:
				return new HexLocation(x, y + 1);
			case SE:
				return new HexLocation(x + 1, y);
			default:
				assert false;
				return null;
		}
	}


	
	
	
	
}

