import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader reader = null;
		try {
			File votes = new File("F:/xml/Votes.xml");
			File posts = new File("F:/xml/Posts.xml");
			String line;
			int i = 0;
			
			System.out.println(new Date() + " Lese Votes ein...");
			reader = new BufferedReader(new FileReader(posts));
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				i++;
				if(i>=80000)
					break;
			}
			reader.close();
			
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

}
