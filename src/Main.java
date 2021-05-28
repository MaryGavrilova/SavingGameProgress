import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(10, 3, 2, 4.5);
        GameProgress gameProgress2 = new GameProgress(1, 6, 7, 30.2);
        GameProgress gameProgress3 = new GameProgress(5, 7, 1, 0.5);

        saveGame("/Users/gavri/Games/savegames/save1.dat", gameProgress1);
        saveGame("/Users/gavri/Games/savegames/save2.dat", gameProgress2);
        saveGame("/Users/gavri/Games/savegames/save3.dat", gameProgress3);

        List<String> listOfFilesToZip = new ArrayList<>();
        listOfFilesToZip.add("/Users/gavri/Games/savegames/save1.dat");
        listOfFilesToZip.add("/Users/gavri/Games/savegames/save2.dat");
        listOfFilesToZip.add("/Users/gavri/Games/savegames/save3.dat");

        zipFiles("/Users/gavri/Games/savegames/zip.zip", listOfFilesToZip);
        deleteFile("/Users/gavri/Games/savegames/save1.dat");
        deleteFile("/Users/gavri/Games/savegames/save2.dat");
        deleteFile("/Users/gavri/Games/savegames/save3.dat");

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Файл сохранения игры " + path + " записан");

        } catch (Exception ex) {
            System.out.println("Файл сохранения игры " + path + " записать не получилось");
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> listOfFilesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String s : listOfFilesToZip) {
                File file = new File(s);
                if (file.exists()) {
                    String name = file.getName();
                    FileInputStream fis = new FileInputStream(s);
                    ZipEntry entry = new ZipEntry(name);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    fis.close();
                } else System.out.println("Файл " + s + " найти и заархивировать не удалось");
            }
            System.out.println("Архив создан");
        } catch (Exception ex) {
            System.out.println("Создать архив не получилось");
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.delete()) {
            System.out.println("Файл " + path + " удален");
        } else System.out.println("Файл " + path + " удалить не получилось");
    }
}
