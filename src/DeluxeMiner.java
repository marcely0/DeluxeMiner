import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

public class DeluxeMiner {

	private static HashMap<Integer, User> users;
	private static HashMap<Integer, Post> posts;

	public static void main(String[] args) {

		posts = new HashMap<>();
		users = new HashMap<>();

		System.out.println(new Date() + " >>>Lese XML Dateien ein...");
		readFiles();
		System.out.println(new Date() + " >>>XML eingelesen");
		System.out.println(new Date() + " >>>Berechne Prozentuale Verteilung");
		
		int[] areas = new int[10];
		int[] postCounts = new int[10];
		int[] upvotes = new int[10];
		int[] downvotes = new int[10];
		int[] acceptedCount = new int[10];
		
		for (Entry<Integer, User> entry : users.entrySet()) {
			float x = entry.getValue().getPercentage();
			areas[(int) ((x-0.000001) * 10)]++;
			postCounts[(int) ((x-0.000001) * 10)]+= entry.getValue().getPosts();
			upvotes[(int) ((x-0.000001) * 10)]+= entry.getValue().getUpvotes();
			downvotes[(int) ((x-0.000001) * 10)]+= entry.getValue().getDownvotes();
			acceptedCount[(int) ((x-0.000001) * 10)]+= entry.getValue().getAcceptedCount();
		}
		
		System.out.println(new Date() + " >>>Prozentwertebereiche:");
		for (int i = 0; i < areas.length; i++) {
			System.out.println(i + "0% - " + (i+1) + "0%\tUser:\t" + areas[i] + "\tPosts:\t" + postCounts[i] + "\tUpvotes:\t" + upvotes[i] + "\tDownvotes:\t" + downvotes[i] + "\tAccepted:\t" + acceptedCount[i]);
		}
	}

	private static void readFiles() {
		BufferedReader reader = null;
		try {
			File votes = new File("E:/xml/Votes.xml");
			File posts = new File("E:/xml/Posts.xml");
			String line;
			int i = 0;

			int voteCounter = 67258373;
			int postCounter = 21736597;

			System.out.println(new Date() + " Lese Votes ein...");
			reader = new BufferedReader(new FileReader(votes));
			while ((line = reader.readLine()) != null) {
				processVoteLine(line);
				i++;
				if (i >= 100000) {
					voteCounter -= 100000;
					System.out.println(voteCounter);
					i = 0;
				}
			}
			reader.close();

			i = 0;
			System.out.println(new Date() + " Lese Posts ein...");
			reader = new BufferedReader(new FileReader(posts));
			while ((line = reader.readLine()) != null) {
				processPostLine(line);
				i++;
				if (i >= 100000) {
					postCounter -= 100000;
					System.out.println(postCounter);
					i = 0;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void processPostLine(String line) {
		if (line.trim().startsWith("<row")) {
			int postTypeId = Integer
					.parseInt(fetchAttribute(line, "PostTypeId"));
			if (postTypeId == 1 || postTypeId == 2) {
				int userId = 0;
				try {
					userId = Integer.parseInt(fetchAttribute(line,
							"OwnerUserId"));
				} catch (NumberFormatException e) {
					System.out
							.println("ups, da wurde was nicht richtig formatiert");
				}
				if (userId > 0) {
					int postId = Integer.parseInt(fetchAttribute(line, "Id"));

					User u = users.get(userId);
					if (u == null) {
						u = new User();
						users.put(userId, u);
					}

					u.raisePostCount();

					Post p = posts.remove(postId);
					if (p != null) {
						u.raiseDownvotes(p.getDownvotes());
						u.raiseUpvotes(u.getUpvotes());

						if(p.isAccepted()){
							u.raiseAcceptedCount();
						}
						
						if (p.isPostGood()) {
							u.raiseGoodPostCount();
						}

					}
				}
			}
		}
	}

	private static void processVoteLine(String line) {
		if (line.trim().startsWith("<row")) {
			int postId = Integer.parseInt(fetchAttribute(line, "PostId"));
			int voteTypeId = Integer
					.parseInt(fetchAttribute(line, "VoteTypeId"));

			Post p = posts.get(postId);
			if (p == null) {
				p = new Post();
				posts.put(postId, p);
			}

			p.set(voteTypeId);

		}
	}

	private static String fetchAttribute(String line, String attributeName) {
		int index = line.indexOf(attributeName);
		if (index == -1)
			return "0";
		index += attributeName.length();

		if (line.charAt(index) != '=' || line.charAt(index + 1) != '"') {
			return fetchAttribute(line.substring(index), attributeName);
		}
		index += 2;
		int length = 0;
		while (line.charAt(index + length) != '"')
			length++;
		String attribute = line.substring(index, index + length);
		return attribute;
	}

}
