package bug.jdk.sort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SortBug {
    
    interface Sorted extends Comparable<Sorted> {
        String getValue(); 
        
        @Override
        default int compareTo(Sorted o) {
            return compareInsensitive(getValue(), o.getValue());
        }
    }
    
    
    static class Wrapper implements Sorted {
        final String content;

        Wrapper(String s) {
            this.content = s;
        }

        @Override
        public String getValue() {
            return content;
        }
    }

    public static void main(String... args) throws IOException {
        final List<String> linesToSort = Files.readAllLines(Paths.get("LinesToSort.txt"));
        final List<String> listStrings = new ArrayList<>(linesToSort);

        listStrings.sort(null);
        System.out.println(listStrings.size() + " Strings successfully sorted!");

        final List<Wrapper> listWrappers = new ArrayList<>(); 
        linesToSort.forEach(line->listWrappers.add(new Wrapper(line)));
        listWrappers.sort(null);
        System.out.println(listWrappers.size() + " Wrappers successfully sorted!");
    }

    static int compareInsensitive(final String c1, final String c2) {
        if (c1 == c2) {
            return 0; // same object or both null
        }
        if (c1 == null || c2 == null) {
            return c1 == null ? -1 : 1;
        }
        return c1.compareToIgnoreCase(c2);
    }
}
