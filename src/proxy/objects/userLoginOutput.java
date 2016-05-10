package proxy.objects;

public class userLoginOutput {
	
	// this needs to change
	boolean result;
	String firstname;
	String lastname;
	int numrecords;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getNumrecords() {
		return numrecords;
	}
	public void setNumrecords(int numrecords) {
		this.numrecords = numrecords;
	}
	
	@Override
	public String toString() {
		if(result)
		{
			return "TRUE\n" + firstname + "\n" + lastname
					+ "\n" + numrecords +"\n";
		}
		else{
			return "FALSE\n";
		}
	}
}
