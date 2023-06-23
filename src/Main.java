import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        copyFile("d:/simple/project","d:/simple/backup");
        print(new File("."), "", true);
    }



    private static void copyFile(final String src, final String dst) throws IOException {
        File srcFile = new File(src);
        File dstFile = new File(dst);
        if (srcFile.exists() && srcFile.isDirectory() && !dstFile.exists()) {
            dstFile.mkdir();
            File nextSrcFile,nextDstFile;
            String nextSrcFilename, nextDstFilename;
            for (String filename : srcFile.list()) {
                nextSrcFilename = srcFile.getAbsolutePath()
                        + File.separator + filename;
                nextDstFilename = dstFile.getAbsolutePath()
                        + File.separator + filename;
                nextSrcFile = new File(nextSrcFilename);
                nextDstFile = new File(nextDstFilename);
                if (nextSrcFile.isDirectory()) {
                    nextDstFile.mkdir();
                    System.out.println("папка скопирована");
                } else {
                    File srcOtherFile = new File(nextSrcFilename);
                    File dstOtherFile = new File(nextDstFilename);
                    if (srcOtherFile.exists() && srcOtherFile.isFile() && !dstOtherFile.exists()) {
                        try (InputStream in = new FileInputStream(srcOtherFile);
                             OutputStream out = new FileOutputStream(dstOtherFile)) {
                            byte[] buffer = new byte[1024];
                            int bytes;
                            while ((bytes = in.read(buffer)) > 0) {
                                out.write(buffer, 0, bytes);
                            }
                            System.out.println("файл скопирован");
                        } catch (FileNotFoundException ex) {
                            System.out.println(ex.getMessage());

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * TODO: Доработать метод print, необходимо распечатывать директории и файлы
     * @param file
     * @param indent
     * @param isLast
     */
    public static void print(File file, String indent, boolean isLast){
        System.out.print(indent); // рисуем отступ
        if (isLast){
            System.out.print("└─");
            indent += "  ";
        }
        else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if (files == null)
            return;

        int subDirTotal = 0;
        int subFileTotal = 0;
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory() || files[i].isFile()) {
                subDirTotal++;
            }
        }

        int subDirCounter = 0;
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory() || files[i].isFile()){
                print(files[i], indent, subDirCounter  == subDirTotal - 1);
                subDirCounter++;
            }
        }
    }

}

