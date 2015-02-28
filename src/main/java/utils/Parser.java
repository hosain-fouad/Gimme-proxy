package utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Tag;
import org.jsoup.nodes.Element;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by hosainfathelbab on 2/21/15.
 */
public class Parser {

    public static String getIp(Element ipElement) throws IOException, SAXException, ParserConfigurationException {

        String entireHtml = ipElement.html();
        List<Element> elements = ipElement.getAllElements();
        StringBuffer str = new StringBuffer();

        Set<String> hiddenClasses = getHiddenClasses(elements.get(1));

        int index = entireHtml.indexOf(elements.get(1).toString()) + elements.get(1).toString().length();

        for(int i = 2 ; i < elements.size(); i++){
            String dataWithoutTag = entireHtml.substring(index, entireHtml.indexOf(elements.get(i).toString(), index));
            str.append(dataWithoutTag);
            Tag tag = new Tag(elements.get(i).toString(), hiddenClasses);
            str.append(tag.getTagBodyAfterApplyingStyles());
            index = entireHtml.indexOf(elements.get(i).toString(), index)+ elements.get(i).toString().length();
        }

        str.append(entireHtml.substring(index));

        return str.toString().replaceAll("\n","").replaceAll(" ","");
    }

    private static Set<String> getHiddenClasses (Element styleTag) throws ParserConfigurationException, IOException, SAXException {
        Set<String> hiddenClasses = new HashSet<String>();
        String tag = styleTag.toString();
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(tag));
        Document doc = db.parse(is);

        org.w3c.dom.Element styleElement = (org.w3c.dom.Element) doc.getElementsByTagName("style").item(0);

        String styleData[] = getCharacterDataFromElement(styleElement).replaceAll(" ","").replaceAll("\n","").split("\\.");

        for(String style : styleData){
            if(style.contains("none")){
               hiddenClasses.add(style.substring(0,style.indexOf("{")));
            }
        }

        return hiddenClasses;
    }

    public static String getCharacterDataFromElement(org.w3c.dom.Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
