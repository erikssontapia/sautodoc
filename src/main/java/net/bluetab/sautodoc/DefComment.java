package net.bluetab.sautodoc;

import net.bluetab.sautodoc.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DefComment extends GenericComment {

    private static int HEADER_COMMENT_LINE_1 = 0;
    private static int HEADER_COMMENT_LINE_2 = 1;
    private static int PARAMETER_COMMENT_LINE = 2;
    private static int RETURN_COMMENT_LINE = 3;
    private static int CLOSE_COMMENT_LINE = 4;

    private static List<String> template;

    public DefComment(List<String> fileLines) {
        super(fileLines);
        template = FileUtils.readFileLineByLine("src\\main\\resources\\templates\\method.template");
    }

    public List<String> generate() {
        List<String> fileCommentLines = new ArrayList<String>();

        for (String line: fileLines) {
            if(line != null) {
                if (line.contains(super.DEF)) {
                    String methodLine = getMethodLine(line, fileLines);
                    fileCommentLines.add(getHeaderComment(methodLine));

                    String parameterComment = getParametersComment(methodLine);
                    if(parameterComment.compareTo("") != 0)
                        fileCommentLines.add(parameterComment);

                    fileCommentLines.add(getReturnComment(methodLine));
                    fileCommentLines.add(getCloseComment());
                    fileCommentLines.add(line);
                }else {
                    fileCommentLines.add(line);
                }
            }
        }
        return fileCommentLines;
    }

    private String getMethodLine(String line, List<String> fileLines){
        StringBuilder newLine = new StringBuilder(line);
        if(!line.contains("{")){
            for(int i = fileLines.indexOf(line) + 1; i < fileLines.size(); i++){
                newLine.append(fileLines.get(i));
                if(fileLines.get(i).contains("{"))
                    break;
            }
        }

        return newLine.toString();
    }

    private String getHeaderComment(String line){
        String headerCommentTemplate = template.get(HEADER_COMMENT_LINE_1) + "\n" + template.get(HEADER_COMMENT_LINE_2);
        return headerCommentTemplate
                .replace("${methodname}", camelCaseToWords(getMethodName(line)))
                .replace("${parameters}", getParametersCommentForHeader(line));
    }

    private String getParametersCommentForHeader(String line){
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
        String paramCommentTemplate = template.get(PARAMETER_COMMENT_LINE);

        for (String parameter : parameters) {
            parametersComment.append(
                    paramCommentTemplate.replace("${paramname}", parameter)
            );

            if (parameters.get(parameters.size() - 1).compareTo(parameter) != 0) {
                parametersComment.append("\n");
            }
        }
        return parametersComment.toString();
    }

    private String getReturnComment(String line){
        String returnCommentTemplate = template.get(RETURN_COMMENT_LINE);
        int beginIndex = line.indexOf(")") + 3;
        int endIndex = line.indexOf("=");

        String returnVal = line.substring(beginIndex, endIndex).trim();
        return returnCommentTemplate
                .replace("${return}", returnVal)
                .replace("${parameters}", getParametersCommentForHeader(line));
    }

    public String getCloseComment(){
        return template.get(CLOSE_COMMENT_LINE);
    }

    private String camelCaseToWords(String camelcase){
        return camelcase.replaceAll("([A-Z][a-z])", " $1");
    }
}