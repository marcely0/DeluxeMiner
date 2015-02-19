
public class User {

	private int postCount;
	private int goodPostCount;
	
	public void raisePostCount(){
		postCount++;
	}

	public void raiseGoodPostCount(){
		goodPostCount++;
	}

	@Override
	public String toString() {
		return "" + "Posts: " + postCount + " Good Posts: " + goodPostCount + " Prozent: " + (goodPostCount / postCount);
	}
}
