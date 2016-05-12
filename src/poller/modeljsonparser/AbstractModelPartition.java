package poller.modeljsonparser;

public abstract class AbstractModelPartition {
	@Override
	public String toString() {
		return ClassToJSON.converttojsonstring(this);
	}
}
