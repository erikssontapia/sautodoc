package net.bluetab.sautodoc;

import java.util.ArrayList;
import java.util.List;

public class GenericComment {

    protected String CLASS = "class ";
    protected String DEF = "def ";

    protected List<String> fileLines;

    public GenericComment(List<String> fileLines){
        this.fileLines = fileLines;
    }

    private String getName(String line, String reservedWord){
        int beginIndex = line.lastIndexOf(reservedWord) + reservedWord.length();
        int endIndex = line.indexOf("(");
        if (endIndex < 0)
            endIndex = line.indexOf("{");
        return line.substring(beginIndex, endIndex).trim();
    }

    protected String getClassName(String line){
        return getName(line, CLASS);
    }

    protected String getMethodName(String line){
        return getName(line, DEF);
    }

    protected List<String> getParameters(String line){
        List<String> parameters = new ArrayList<String>();
        int beginIndex = line.indexOf("(") + 1;
        int endIndex = line.indexOf(")");
        if((beginIndex != 0) && (beginIndex != endIndex)) {
            String strParameters = line.substring(beginIndex, endIndex);
            String[] splitParameters = strParameters
                    .replace("\\[[a-zA-Z]\\]", "")
                    .split(",");
            for (int i = 0; i < splitParameters.length; i++) {
                parameters.add(splitParameters[i].split(":")[0].trim());

            }
        }

        return parameters;
    }

}
