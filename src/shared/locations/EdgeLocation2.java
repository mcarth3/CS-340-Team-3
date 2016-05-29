package shared.locations;

import java.util.ArrayList;

import model.Game;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation2 {

	private EdgeDirection direction;
	private int x;
	private int y;

	public EdgeLocation2(HexLocation hexLoc, EdgeDirection direction) {
		setHexLoc(hexLoc);
		setDir(direction);
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public HexLocation getHexLoc() {
		HexLocation hexLoc = new HexLocation(x, y);
		return hexLoc;
	}

	private void setHexLoc(HexLocation hexLoc) {
		if (hexLoc == null) {
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.x = hexLoc.getX();
		this.y = hexLoc.getY();
	}

	public EdgeDirection getDir() {
		return direction;
	}

	private void setDir(EdgeDirection direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		HexLocation hexLoc = new HexLocation(x, y);
		return "EdgeLocation [hexLoc=" + hexLoc + ", direction=" + direction + "]";
	}

	@Override
	public int hashCode() {
		HexLocation hexLoc = new HexLocation(x, y);
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		HexLocation hexLoc = new HexLocation(x, y);
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeLocation2 other = (EdgeLocation2) obj;
		if (direction != other.direction)
			return false;
		HexLocation hexLoc2 = new HexLocation(other.x, other.x);
		if (hexLoc == null) {
			if (hexLoc2 != null)
				return false;
		} else if (!hexLoc.equals(hexLoc2))
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
	public EdgeLocation2 getNormalizedLocation() {
		HexLocation hexLoc = new HexLocation(x, y);
		// Return an EdgeLocation that has direction NW, N, or NE

		switch (direction) {
		case NW:
		case N:
		case NE:
			return this;
		case SW:
		case S:
		case SE:
			return new EdgeLocation2(hexLoc.getNeighborLoc(direction), direction.getOppositeDirection());
		default:
			assert false;
			return null;
		}
	}

	public boolean hadconnectingroad(Game theGame, int playerIndex) {
		ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
		EdgeLocation2 normalized = this.getNormalizedLocation();
		switch (normalized.getDir()) {
		case NW:
			edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
			edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.SW).getNormalizedLocation());
			edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getX(), normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getY()), EdgeDirection.NE));
			edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getX(), normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getY()), EdgeDirection.S).getNormalizedLocation());
			break;
		case N:
			edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NW));
			edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.NE));
			edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getX(), normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getY()), EdgeDirection.SW).getNormalizedLocation());
			edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getX(), normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getY()), EdgeDirection.SE).getNormalizedLocation());
			break;
		case NE:
			edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.N));
			edges.add(new EdgeLocation(normalized.getHexLoc(), EdgeDirection.SE).getNormalizedLocation());
			edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getX(), normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getY()), EdgeDirection.NW));
			edges.add(new EdgeLocation(new HexLocation(normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getX(), normalized.getHexLoc().getNeighborLoc(normalized.getDir()).getY()), EdgeDirection.S).getNormalizedLocation());
			break;
		default:
		}
		// System.out.print("playerindex = "+playerIndex);
		for (int i = 0; i < theGame.getMap().getRoads().size(); i++) {
			for (int j = 0; j < edges.size(); j++) {
				if (theGame.getMap().getRoads().get(i).getLocation().getNormalizedLocation().toString().equals(edges.get(j).getNormalizedLocation().toString())) {
					if (theGame.getMap().getRoads().get(i).getOwner() == playerIndex) {
						// System.out.println("return true ");
						return true;
					}
				}
			}
		}
		// return new RoadCollection(roads);
		// System.out.println("return false ");
		return false;
	}

	public ArrayList<VertexLocation> getVertices() {
		ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
		EdgeLocation2 normalized = this.getNormalizedLocation();
		switch (normalized.getDir()) {
		case NW:
			vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.W).getNormalizedLocation());
			vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NW));
			break;
		case N:
			vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NW).getNormalizedLocation());
			vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NE));
			break;
		case NE:
			vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.E).getNormalizedLocation());
			vertices.add(new VertexLocation(normalized.getHexLoc(), VertexDirection.NE));
			break;
		default:
		}
		return vertices;
	}
}
