import xml.parser.sax.SaxParseHandler;
import xml.parser.sax.SaxUtils;
import xml.parser.stax.StaxParser;

import javax.xml.stream.XMLStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RunMain {
    public static void main(String[] args) {
        StaxParser staxParser=new StaxParser();
        LocalDate localDate=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date=localDate.format(formatter);
        SaxUtils saxUtils=new SaxUtils();
        String url="https://www.cbar.az/currencies/"+date+".xml";
//        staxParser.runXmlParser(url);
        saxUtils.startParsing(url);


    }


}
