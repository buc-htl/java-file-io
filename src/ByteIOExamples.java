import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ByteIOExamples {


    public static void main(String[] args) {
        ByteIOExamples.runExamples();
    }

    public static void runExamples() {
        try {
           ByteIOExamples.copyFile("resources/schwarzspecht.jpg", "resources/schwarzspecht2.jpg");
           ByteIOExamples.copyFile2("resources/schwarzspecht.jpg", "resources/schwarzspecht3.jpg");

           ByteIOExamples.writeBytesToFile(new byte[]{22,46, 0x55, 0x23, -23}, "resources/test.io");

           ByteIOExamples.readByteExperiments("resources/test.io");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Liest den binären Inhalt von srcFile und schreibt ihn unverändert nach destFile unter Verwendung von Files.readAllBytes und Files.write
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(String srcFile, String destFile) throws IOException {

        // Alle Bytes einer Datei in ein byte-Array einlesen
        byte[] content = Files.readAllBytes(Paths.get(srcFile));

        //Das byte-Array wird in das destFile geschrieben
        Files.write(Paths.get(destFile),content);
    }

    /**
     * Liest den binären Inhalt von srcFile und schreibt ihn unverändert nach destFile
     * unter Verwendung von Input- und Outputstreams.
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile2(String srcFile, String destFile) throws IOException {
        // Die Dateien werden mit einem try-mit-resources geöffnet
        try (
                // Öffnet eine Datei zum byteweisen-Lesen,
                InputStream in = Files.newInputStream(Paths.get(srcFile) // die zu lesende Datei
                );

                // Öffnet eine Datei zum byteweisen-Schreiben
                OutputStream out = Files.newOutputStream(
                        Paths.get(destFile) // die zu schreibende Datei
                );
                // Beide Dateien werden automatisch geschlossen, sobald der
                // try-Block verlassen wird!
        ) {
            byte[] buffer = new byte[2048]; // Lesebuffer
            int bytesRead; // Anzahl der gelesenen Bytes

            // Kopiert die gesamte Datei
            //in.read(...) versucht mit jedem Aufruf buffer.length bytes in buffer einzulesen
            //in.read(...) liefert dabei die Anzahl der wirklich gelesenen Bytes zurück
            // (z.B. weniger, wenn weniger als buffer.length bytes übrig sind zu lesen),
            //bzw. -1 wenn das Ende des Streams erreicht ist.
            while ((bytesRead = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     *  Schreibt einen beliebiges byte Array in destFile.
     *
     * @param content
     * @param destFile
     * @throws IOException
     */
    public static void writeBytesToFile(byte[] content, String destFile) throws IOException {
        Files.write(Paths.get(destFile),content);
    }


    /**
     * Ein Beispiel das die Verwendung von skip() zeigt.
     *
     * @param srcFile
     * @throws IOException
     */
    public static void readByteExperiments(String srcFile ) throws IOException{
        // Die Dateien werden mit einem try-mit-resources geöffnet
        try (
                // Öffnet eine Datei zum byteweisen-Lesen,
                InputStream in = Files.newInputStream(Paths.get(srcFile) // die zu lesende Datei
                );

        ) {
            byte[] buffer = new byte[5]; // Lesebuffer
            int bytesRead; // Anzahl der gelesenen Bytes

            //liest max. 2 bytes vom Inputstream in buffer
            //die gelesenen bytes werden in buffer beginnend bei index 0 gespeichert
            bytesRead = in.read(buffer, 0, 2);

            System.out.println("# gelesene Bytes: "+bytesRead);
            System.out.println("gelesene Bytes: "+ Arrays.toString(buffer));

            System.out.print("gelesene Bytes als hexadezimale Zahlen: ");
            for (byte b: buffer) {
                System.out.printf("0x%02X ", b);
            }
            System.out.println("\n");

            //überspringt 2 Bytes vom Inputstream
            in.skip(2);

            //liest max. die nächsten 3 bytes vom Inputstream
            //in das Array buffer, beginnend beim Index 2
            bytesRead = in.read(buffer, 2,3);

            System.out.println("# gelesene Bytes: "+bytesRead);
            System.out.println("gelesene Bytes: "+ Arrays.toString(buffer));
        }

    }
}
