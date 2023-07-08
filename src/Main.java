import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {
    /**
     * 1. Написать функцию, создающую резервную копию всех файлов
     * в директории(без поддиректорий) во вновь созданную папку ./backup
     *
     * @param args
     */
    public static void main(String[] args) {
        Path backupDir = Path.of("./backup");
        Path directory = Path.of("C:/Users/Elena/Desktop/testBackup");
        System.out.println(backup(backupDir, directory));


    }

    static boolean backup(Path backupDir, Path directory) {


        try (Stream<Path> filesStream = Files.list(directory)) {
            Files.createDirectory(backupDir);
            for (Path file : filesStream.collect(Collectors.toList())) {
                if (Files.isRegularFile(file)) {
                    Files.copy(file, backupDir.resolve(file.getFileName()), REPLACE_EXISTING);
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
