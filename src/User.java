
public class User {

	private int postCount;
	private int goodPostCount;
	
	private int acceptedCount;
	private int upvotes;
	private int downvotes;
	

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

	public int getUpvotes() {
		return upvotes;
	}
	
	public void raiseUpvotes(int upvotes) {
		this.upvotes += upvotes;
	}
	
	public int getDownvotes() {
		return downvotes;
	}
	
	public void raiseDownvotes(int downvotes) {
		this.downvotes += downvotes;
	}

	public int getAcceptedCount() {
		return acceptedCount;
	}

	public void raiseAcceptedCount() {
		this.acceptedCount++;
	}
	
}
