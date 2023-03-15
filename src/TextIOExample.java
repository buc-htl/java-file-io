import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class TextIOExample {


    public static void main(String[] args) {
        TextIOExample.runExamples();
    }

    public static void runExamples() {
        try {

            copyTextFile("resources/random_text.txt", "resources/output_text.txt");
            appendTextFile("resources/random_text.txt", "resources/random_text2.txt");
            copyTextFileWithStreams("resources/random_text.txt", "resources/output_text2.txt");

            writeTextToFile(Arrays.asList(new String[]{"erste Zeile","zweite Zeile", " ", "Ende"}),"resources/new_text.txt");
            writeTextToFileWithStreams("Java\nJavaScript\npython.","resources/new_text2.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Liest den Inhalt von srcFile und schreibt ihn unverändert nach destFile unter Verwendung von Files.readAllLines und Files.write
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyTextFile(String srcFile, String destFile) throws IOException {

        //Das charset muss übereinstimmen mit dem Charset in dem das File abgespeichert wurde.
        //Ansonst wird der Inhalt möglicherweise falsch interpretiert.
        List<String> allLines = Files.readAllLines(Paths.get(srcFile), Charset.forName("UTF-8"));

        Files.write(Paths.get(destFile), allLines, Charset.forName("UTF-8"));
    }

    /**
     *  Liest den Inhalt von srcFile und hängt ihn unverändert an den Inhalt von destFile an.
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void appendTextFile (String srcFile, String destFile) throws IOException {

        List<String> allLines = Files.readAllLines(Paths.get(srcFile), Charset.forName("UTF-8"));

        //allLines werden angehängt, existierender Inhalt bleibt erhalten
        Files.write(Paths.get(destFile), allLines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }

    /**
     * Liest den Inhalt von srcFile und schreibt ihn unverändert nach destFile unter Verwendung von Input- und Outputstreams
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyTextFileWithStreams(String srcFile, String destFile) throws IOException {
        // Die Dateien werden mit einem try-mit-resources geöffnet. Dies stellt sicher, dass die Dateien in jedem Fall
        // auch wieder geschlossen werden.
        try (
                // Öffnet eine Datei zum Lesen,
                BufferedReader in = Files.newBufferedReader(
                        Paths.get(srcFile),          // die zu lesende Datei
                        Charset.forName("UTF-8")     // ihr Zeichensatz
                );

                // Öffnet eine Datei zum Schreiben
                BufferedWriter out = Files.newBufferedWriter(
                        Paths.get(destFile),         // die zu schreibende Datei
                        Charset.forName("UTF-8")     // ihr Zeichensatz
                );

                // Beide Dateien werden automatisch geschlossen, sobald der try-Block verlassen wird!
        ) {
            String line;

            // Lies die gesamte Eingabe-Datei (in) zeilenweise, das Dateiende wird daran erkannt,
            // dass "in.readLine()" null zurück gibt.
            while ((line = in.readLine()) != null) {
                // Schreibt die gesamte Zeile in die Ausgabe-Datei (out).
                // Zusätzlich muss noch ein Zeilenumbruch (+ System.lineSeparator()) erzeugt werden.
                out.write(line + System.lineSeparator());
            }
        } // Ende try-Block -- close
    }

    /**
     *  Schreibt beliebige Zeilen in destFile.
     *
     * @param content
     * @param destFile
     * @throws IOException
     */
    public static void writeTextToFile(List<String> content, String destFile) throws IOException {
        Files.write(Paths.get(destFile),content, Charset.forName("UTF-8"));
    }

    /**
     *  Dieses Beispiel zeigt neben dem Schreiben in ein File mit Streams auch die Verwendung StandardOpenOptions
     *  und die Verwendung von flash()
     *
     * @param line
     * @param destFile
     * @throws IOException
     */
    public static void writeTextToFileWithStreams (String line, String destFile) throws IOException {
        try (
                // Öffnet eine Datei zum Schreiben
                BufferedWriter out = Files.newBufferedWriter(
                        Paths.get(destFile),         // die zu schreibende Datei
                        Charset.forName("UTF-8"),     // ihr Zeichensatz
                        StandardOpenOption.CREATE,    //die Datei wird erzeugt falls sie nicht existiert
                        StandardOpenOption.APPEND     //wenn sie bereits existiert, wird der neue Inhalt am Ende angehängt
                );

                // Die Dateien wird automatisch geschlossen, sobald der try-Block verlassen wird!
        ) {
                out.write("Das muss unbedingt gleich gespeichert werden." + System.lineSeparator());

                // ein BufferedWriter sammelt (also "buffert") den Output bevor er ihn an die darunter liegenden Systeme
                // gesammelt weiterleitet. Mit jedem Aufruf wird der gebufferte Inhalt geschrieben
                // (was sonst nur automatisch geschieht wenn der Buffer voll ist).
                out.flush();

                out.write(line + System.lineSeparator());
            }
        } // Ende try-Block -- close

}
