import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    public static List<Integer> readFile(String filename) {
        // Read csv file and get the 7th column as a list of strings
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                list.add(Integer.parseInt(values[6]));
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return list;
    }    
}
