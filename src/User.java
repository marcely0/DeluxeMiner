
public class User {

	private int postCount;
	private int goodPostCount;

	public float getPercentage(){
		return ((float) goodPostCount / (float) postCount);
	}
	
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
	
	public int getPosts(){
		return postCount;
	}
}
