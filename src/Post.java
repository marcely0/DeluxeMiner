
public class Post {
	
	public void set(int voteTypeId) {
		switch (voteTypeId) {
		case 1:
			this.accepted = true;
			break;
		case 2:
			this.upvotes++;
			break;
		case 3:
			this.downvotes++;
			break;

		default:
			break;
		}
	}
	
	private boolean accepted;

	private int upvotes;
	private int downvotes;
	
	@Override
	public String toString() {
		return "" + accepted + " - " + upvotes + " - " +  downvotes;
	}
	
	public boolean isPostGood(){
		if(accepted)
			return true;
		
		if(upvotes - downvotes > 0)
			return true;
		
		return false;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public int getUpvotes() {
		return upvotes;
	}
	
	public int getDownvotes() {
		return downvotes;
	}
}
