package net.bluetab.sautodoc;

import net.bluetab.sautodoc.utils.FileUtils;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SAutoDoc {

    private static String RESULT_PATH = "target/generated-sources/sautodoc/";
    private Log log;

    public SAutoDoc(Log log){
        this.log = log;
        List<File> fileList = getFileList("src/main/scala");

        for (File file: fileList) {
            this.log.info("Comment for => " + file.getParent() + file.getName());
            commentFile(file);
        }
    }

    public void commentFile(File file){
        List<String> fileLines = FileUtils.readFileLineByLine(file.getAbsolutePath());
        List<String> fileCommentLines = putComments(fileLines);

        String folderPath = RESULT_PATH + file.getParent();
        //FileUtils.createPath(folderPath);
        FileUtils.writeFileLineByLine(file.getAbsolutePath(), fileCommentLines);
    }

    public List<File> getFileList(String path){
        List<File> filesInFolder = null;
        try {
            filesInFolder = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }

        return filesInFolder;
    }

    public List<String> putComments(List<String> fileLines){
        return new DefComment(
                new ClassComment(fileLines).generate()
        ).generate();
    }


}
