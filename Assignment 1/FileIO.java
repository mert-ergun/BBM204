import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    public static String readFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;
            List<String> lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            return String.join("\n", lines);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
