package model;

import poller.modeljsonparser.AbstractModelPartition;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
/**Represents a Port on the port, which contains a specific ratio on a specified location on the board. 
 * @author Jesse McArthur
 */
public class Port extends AbstractModelPartition {

    HexLocation location;
    String resource;
    EdgeDirection direction;
    int ratio;
    int owner = -1;

    public Port(int x, int y, EdgeDirection dir, int rat) {
        location = new HexLocation(x, y);
        resource = PortType.THREE.toString();
        direction = dir;
        ratio = rat;
    }

    public Port(int x, int y, String resrc, EdgeDirection dir, int rat) {
        location = new HexLocation(x, y);
        resource = resrc;
        direction = dir;
        ratio = rat;
    }
    public Port(HexLocation newlocation, String resrc, EdgeDirection dir, int rat, int newowner) {
        location = newlocation;
        resource = resrc;
        direction = dir;
        ratio = rat;
        owner = newowner;
    }


    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public EdgeDirection getDirection() {
        return direction;
    }

    public void setDirection(EdgeDirection direction) {
        this.direction = direction;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getOwner() {
        return this.owner;
    }

    public void setOwner(int playerID) {
        this.owner = playerID;
    }

    public EdgeLocation getEdgeLocation()
    {
        EdgeLocation theEdge = new EdgeLocation(location, direction);
        return theEdge;
    }
}
