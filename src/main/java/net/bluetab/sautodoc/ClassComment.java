package net.bluetab.sautodoc;

import net.bluetab.sautodoc.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class ClassComment extends GenericComment{

    private static int HEADER_COMMENT_LINE_1 = 0;
    private static int HEADER_COMMENT_LINE_2 = 1;
    private static int CONSTRUCTOR_COMMENT_LINE = 2;
    private static int PARAM_COMMENT_LINE = 3;
    private static int CLOSE_COMMENT_LINE = 4;

    private static List<String> template;

    public ClassComment(List<String> fileLines){
        super(fileLines);
        template = FileUtils.readFileLineByLine("src\\main\\resources\\templates\\class.template");
    }
    
    public List<String> generate(){
        List<String> fileCommentLines = new ArrayList<String>();

        for (String line: fileLines) {
            if(line != null) {
                if (line.contains(super.CLASS)) {
                    String classLine = getClassLine(line, fileLines);
                    fileCommentLines.add(getHeaderComment(classLine));
                    fileCommentLines.add(getConstructorComment(classLine));
                    fileCommentLines.add(getParametersComment(classLine));
                    fileCommentLines.add(getCloseComment());
                    fileCommentLines.add(line);
                } else {
                    fileCommentLines.add(line);
                }
            }
        }

        return fileCommentLines;
    }

    private String getClassLine(String line, List<String> fileLines){
        StringBuilder newLine = new StringBuilder(line);
        String sufixBegin = "{";
        String sufixEnd = "{";

        if(line.contains("case class")){
            sufixBegin = "(";
            sufixEnd = ")";
        }

        if(!line.endsWith(sufixBegin)){
            for(int i = fileLines.indexOf(line) + 1; i < fileLines.size(); i++){
                newLine.append(fileLines.get(i));
                if(fileLines.get(i).endsWith(sufixEnd))
                    break;
            }
        }

        return newLine.toString();
    }

    private String getHeaderComment(String line){
        String headerCommentTemplate = template.get(HEADER_COMMENT_LINE_1) + "\n" + template.get(HEADER_COMMENT_LINE_2);
        return headerCommentTemplate.replace("${classname}", getClassName(line));
    }

    private String getConstructorComment(String line){
        String constructorCommentTemplate =  template.get(CONSTRUCTOR_COMMENT_LINE);
        return constructorCommentTemplate
                .replace("${classname}", getClassName(line))
                .replace("${parameters}", getParametersCommentForConstructor(line));
    }

    private String getParametersCommentForConstructor(String line){
        List<String> parameters = getParameters(line);
        StringBuilder parametersComment = new StringBuilder();

        for (String parameter: parameters) {
            parametersComment.append(parameter);
            if(parameters.get(parameters.size() - 1).compareTo(parameter) != 0){
                parametersComment.append(" and ");
            }
        }

        return parametersComment.toString();
    }

    private String getParametersComment(String line){
        List<String> parameters = getParameters(line);
        StringBuilder parametersComment = new StringBuilder();
        String paramCommentTemplate = template.get(PARAM_COMMENT_LINE);

        for (String parameter : parameters) {
            parametersComment.append(
                    paramCommentTemplate.replace("${paramname}", parameter)
            );

            if(parameters.get(parameters.size() - 1).compareTo(parameter) != 0){
                parametersComment.append("\n");
            }
        }
        return parametersComment.toString();
    }
    public String getCloseComment(){
        return template.get(CLOSE_COMMENT_LINE);
    }
}
