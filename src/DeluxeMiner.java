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

		for(Entry<Integer, User> entry : users.entrySet()){
			System.out.println(entry.getKey() + " ### " + entry.getValue());
			if(entry.getKey() >= 200)
				break;
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
				if(i>=100000){
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
				if(i>=100000){
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
		if(line.trim().startsWith("<row")){
			int postTypeId = Integer.parseInt(fetchAttribute(line, "PostTypeId"));
			if(postTypeId == 1 || postTypeId == 2){
				int userId = Integer.parseInt(fetchAttribute(line, "OwnerUserId"));
				if(userId > 0){
					int postId = Integer.parseInt(fetchAttribute(line, "Id"));
					
					User u = users.get(userId);
					if(u == null){
						u = new User();
						users.put(userId, u);
					}
					
					u.raisePostCount();
					
					Post p = posts.remove(postId);
					if(p != null){
						if(p.isPostGood()){
							u.raiseGoodPostCount();
						}
					
					}
				}
			}
		}
	}

	private static void processVoteLine(String line) {
		if(line.trim().startsWith("<row")){
			int postId = Integer.parseInt(fetchAttribute(line, "PostId"));
			int voteTypeId = Integer.parseInt(fetchAttribute(line, "VoteTypeId"));
			
			Post p = posts.get(postId);
			if(p == null){
				p = new Post();
				posts.put(postId, p);
			}
			
			p.set(voteTypeId);
			
		}
	}

	private static String fetchAttribute(String line, String attributeName) {
		int index = line.indexOf(attributeName);
		index += attributeName.length();
		
		if(line.charAt(index) != '=' || line.charAt(index + 1) != '"'){
			return fetchAttribute(line.substring(index), attributeName);
		}
		int length = 0;
		while (line.charAt(index + length) != '"')
			length++;
		String attribute = line.substring(index, index + length);
		return attribute;
	}

}
