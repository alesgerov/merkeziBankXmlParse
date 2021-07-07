package xml.parser.sax;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SaxUtils {


    private SaxParseHandler openConnection(String url,SaxParseHandler saxParseHandler){
        try {
            SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
            SAXParser parser=saxParserFactory.newSAXParser();
            URL url1=new URL(url);
            parser.parse(url1.openStream(),saxParseHandler);
            return saxParseHandler;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return saxParseHandler;

    }

    public void startParsing(String url){
        SaxParseHandler parseHandler=openConnection(url,new SaxParseHandler());
        System.out.println(parseHandler.getValCurs());
        parseHandler.getMetalList().forEach(metal -> System.out.printf("%s %s %s\n",metal.getCode(),metal.getName(),metal.getValue()));
        parseHandler.getMoneyCurrencyList().forEach(moneyCurrency -> System.out.printf("%s %s %s\n",moneyCurrency.getCode(),moneyCurrency.getName(),moneyCurrency.getValue()));
    }

}
