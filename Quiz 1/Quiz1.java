import java.util.*;
import java.io.*;

class IndexEntry implements Comparable<IndexEntry> {
    String entry;
    int originalPosition;
    int keywordPosition;

    public IndexEntry(String entry, int originalPosition, int keywordPosition) {
        this.entry = entry;
        this.originalPosition = originalPosition;
        this.keywordPosition = keywordPosition;
    }

    @Override
    public int compareTo(IndexEntry other) {
        int compareKeyword = this.entry.split(" ")[keywordPosition].compareTo(other.entry.split(" ")[other.keywordPosition]);
        if (compareKeyword != 0) {
            return compareKeyword;
        }
        if (this.originalPosition != other.originalPosition) {
            return this.originalPosition - other.originalPosition;
        }
        return this.keywordPosition - other.keywordPosition;
    }
}

public class Quiz1 {
    public static void main(String[] args) throws IOException {

        Locale.setDefault(Locale.ENGLISH);

        List<String> ignoreWords = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<IndexEntry> indexEntries = new ArrayList<>();

        // Read the input file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            boolean readingIgnoreWords = true;

            while ((line = reader.readLine()) != null) {
                if (line.equals("...")) {
                    readingIgnoreWords = false;
                    continue;
                }

                if (readingIgnoreWords) {
                    ignoreWords.add(line.toLowerCase());
                } else {
                    titles.add(line);
                }
            }
        }

        int titleIndex = 0;
        for (String title : titles) {
            String[] words = title.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                if (!ignoreWords.contains(words[i].toLowerCase())) {
                    StringBuilder entryBuilder = new StringBuilder();
                    for (int j = 0; j < words.length; j++) {
                        if (i == j) {
                            entryBuilder.append(words[j].toUpperCase());
                        } else {
                            entryBuilder.append(words[j].toLowerCase());
                        }
                        if (j < words.length - 1) {
                            entryBuilder.append(" ");
                        }
                    }
                    indexEntries.add(new IndexEntry(entryBuilder.toString(), titleIndex, i));
                }
            }
            titleIndex++;
        }

        Collections.sort(indexEntries);

        for (IndexEntry entry : indexEntries) {
            System.out.println(entry.entry);
        }
    }
}
