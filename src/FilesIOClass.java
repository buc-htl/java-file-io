import java.io.*;
import java.nio.file.*;

public class FilesIOClass {

    public static void runExamples() {
        try {

            //erzeugt ordner »pictures« und falls notwendig auch »resources«, »demo« und »byteio«
            Files.createDirectories(Paths.get("resources/demo/byteio/pictures"));

            //erzeugt eine leere Datei
             // Files.createFile(Paths.get("resources/demo/byteio/emptyTextFile.txt"));

            Files.copy(
                    Paths.get("resources/schwarzspecht.jpg"), // Source-File
                    Paths.get("resources/schwarzspecht_neu.jpg"), // Destination-File
                    StandardCopyOption.COPY_ATTRIBUTES, // optional, kopiert auch Datei-Attribute (The exact file attributes supported are file system and platform dependent, but last-modified-time is supported across platforms and is copied to the target file.)
                    StandardCopyOption.REPLACE_EXISTING // optional: schreibt über eine vorhandene Datei
            );

           // Verschiebt (unbenennen) eine Datei oder einen Ordner samt seinem gesamten Inhalt
            Files.move(
                    Paths.get("resources/demo/byteio/pictures"),
                    Paths.get("resources/demo/byteio/pictures_neu"),
                    StandardCopyOption.REPLACE_EXISTING // optional: schreibt über eine vorhandene Datei/Ordner
            );


            //Löschte einen leeren, existierenden Ordner (eine leere Datei), wenn er nicht leer ist,
            // oder er nicht existiert, dann wird eine Exception geworfen (DirectoryNotEmptyExceptionbzw. NoSuchFileException)
            //Files.delete(Paths.get("resources/demo/byteio/pictures"));

            //Löscht eine Datei oder einen leeren Ordner und sagt, ob gelöscht werden konnte (true/false) - false wenn die Datei nicht existierte
            // Wenn der Ordner nicht leer ist, dann wird eine Exception geworfen (DirectoryNotEmptyException)

       //     boolean couldDeleteFile = Files.deleteIfExists(Paths.get("resources/emptyTextFile.txt"));

            Path file = Paths.get("resources/schwarzspecht.jpg");
            System.out.printf ("Die Datei »%s«:%n", file);
            System.out.println("existiert              : " + Files.exists(file));
            System.out.println("ist ein Verzeichnis    : " + Files.isDirectory(file));
            System.out.println("ist eine reguläre Datei: " + Files.isRegularFile(file));
            System.out.println("ist lesbar             : " + Files.isReadable(file));
            System.out.println("ist schreibbar         : " + Files.isWritable(file));
            System.out.println("hat die Größe in Bytes : " + Files.size(file));

            // Das Beispiel zählt alle Dateien im Projekt-Ordner (in der Variable counter[0]
            int[] counter = new int[1];
            Files.walk(
                    Paths.get(""), // der zu durchwandernde Ordner
                    2 // optional: maximale Ordnertiefe (= max. Ebenen)
            ).forEach(path -> { // foreach wird für jede Datei und jeden Ordner aufgerufen.
                // path ist dann dieses Datei- oder Ordner-Objekt
                // Hier darf beliebiger Code stehen. Man kann jedoch nicht auf eine normale Variable außerhalb
                // der geschwungenen Klammern schreibend zugreifen, deswegen der Trick, dass wir ein Array verwenden.
                if (Files.isDirectory(path)) {
                    counter[0]++;
                }
            });


            try (DirectoryStream<Path> stream = Files.newDirectoryStream(
                    Paths.get("resources"), // Ordner
                    "*.csv") // Muster (String oder Lambda) for (Path entry : stream) {
            ) {
                //TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
