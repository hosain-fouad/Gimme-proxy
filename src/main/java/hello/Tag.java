package hello;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hosainfathelbab on 2/21/15.
 */
public class Tag {

    String tagName;
    String tagStyle;
    String tagClass;
    String tagBody;
    Set<String> hiddenClasses;

    public Tag(String tagName, String tagStyle, String tagClass, String tagBody) {
        this.tagName = tagName;
        this.tagStyle = tagStyle;
        this.tagClass = tagClass;
        this.tagBody = tagBody;
    }

    public Tag(String tagHtml) {
        this(tagHtml, new HashSet<String>());
    }

    public Tag(String tagHtml, Set<String> hiddenClasses) {
        try{
            this.hiddenClasses = hiddenClasses;
            String firstPart = tagHtml.substring(tagHtml.indexOf("<") + 1, tagHtml.indexOf(">"));
            // first part may contain tag name only with/without style or class name
            if (!firstPart.contains(" ")) {
                this.tagName = firstPart;
            }
            else {
                String parts [] = firstPart.split(" ");
                this.tagName = parts[0];
                String classOrStyle = parts[1].substring(0, parts[1].indexOf("=") + 1).toLowerCase();
                if (classOrStyle.contains("class")) {
                    this.tagClass = firstPart.substring(firstPart.indexOf("\"")+1, firstPart.lastIndexOf("\""));
                }
                else if (classOrStyle.contains("style")) {
                    this.tagStyle = firstPart.substring(firstPart.indexOf("\"")+1, firstPart.lastIndexOf("\""));
                }
                else{
                    throw new IllegalParsingException(String.format("found things other than class and style : %s ",parts[1]));
                }
            }
            this.tagBody = tagHtml.substring(tagHtml.indexOf(">")+1 , tagHtml.lastIndexOf("<"));
        }
        catch(IllegalParsingException e){
            System.out.println(e);
            System.out.println("While Parsing" + tagHtml);
        }
    }

    public String getTagBodyAfterApplyingStyles(){
        String body = this.tagBody;
        if(this.tagClass != null){
            if(this.hiddenClasses.contains(tagClass)){
                body = "";
            }
        }
        else if (this.tagStyle != null) {
            if(this.tagStyle.toLowerCase().contains("none")) {
                body = "";
            }
        }
        return body;
    }
}
