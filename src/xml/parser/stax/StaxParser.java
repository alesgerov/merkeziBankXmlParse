package xml.parser.stax;

import utils.Metal;
import utils.MoneyCurrency;
import utils.ValCurs;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//todo with saxParser
public class StaxParser {

    private XMLStreamReader openConnection(String url1){
        try {
            URL url=new URL(url1);
            XMLInputFactory factory=XMLInputFactory.newFactory();
            XMLStreamReader reader=factory.createXMLStreamReader(url.openStream());
            return reader;
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void runXmlParser(String url1){
        ValCurs valCurs=null;
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        MoneyCurrency currency=null;
        List<MoneyCurrency> currencyList=new ArrayList<>();
        Metal metal=null;
        boolean isMetal=false;
        boolean isMoney=false;
        List<Metal> metalList=new ArrayList<>();
        Map<String,Boolean> onOff=new HashMap<>();
        XMLStreamReader reader=openConnection(url1);
        try {
            while (reader.hasNext()){
                int eventType=reader.getEventType();
                if (eventType== XMLStreamConstants.START_DOCUMENT){
                    System.out.println("Process started");
                }else if (eventType== XMLStreamConstants.END_DOCUMENT){
                    System.out.println("Process ended");
                }else if (eventType==XMLStreamConstants.START_ELEMENT){

                    onOff.put(reader.getLocalName().toLowerCase(Locale.ROOT),true);
                    if (reader.getLocalName().equals("ValCurs")){
                        valCurs=new ValCurs();
                        valCurs.setDateOfCurrency(LocalDate.parse(reader.getAttributeValue(0),formatter));
                        valCurs.setName(reader.getAttributeValue(1));
                        valCurs.setDescription(reader.getAttributeValue(2));

                    }else if (reader.getLocalName().equals("Valute")){
                        if (isMetal){
                            metal=new Metal();
                            metal.setCode(reader.getAttributeValue(0));
                        }else if (isMoney){
                            currency=new MoneyCurrency();
                            currency.setCode(reader.getAttributeValue(0));
                        }
                    }else if (reader.getLocalName().equals("ValType")){
                        if (reader.getAttributeValue(0).equals("Bank metalları")){
                            isMetal=true;
                        }else  if (reader.getAttributeValue(0).equals("Xarici valyutalar")){
                            isMoney=true;
                        }
                    }

                }else if (eventType==XMLStreamConstants.END_ELEMENT){
                    if (reader.getLocalName().equals("Valute")){
                        if (isMetal){
                            metalList.add(metal);
                            metal=null;
                        }else if (isMoney){
                            currencyList.add(currency);
                            currency=null;
                        }
                    }else if (reader.getLocalName().equals("ValType")){
                        isMetal=false;
                        isMoney=false;
                    }
                    onOff.put(reader.getLocalName().toLowerCase(Locale.ROOT),false);
                }else if (eventType==XMLStreamConstants.CHARACTERS){
                    String data=reader.getText();
                    if (isMetal){
                        if (onOff.getOrDefault("nominal",false)){
                            metal.setNominal(data);
                        }else if (onOff.getOrDefault("name",false)){
                            metal.setName(data);
                        }else if (onOff.getOrDefault("value",false)){
                            metal.setValue(new BigDecimal(data));
                        }

                    } else if (isMoney) {
                        if (onOff.getOrDefault("nominal",false)){
                            currency.setNominal(Integer.parseInt(data));
                        }else if (onOff.getOrDefault("name",false)){
                            currency.setName(data);
                        }else if (onOff.getOrDefault("value",false)){
                            currency.setValue(new BigDecimal(data));
                        }
                    }
                }

                reader.next();
            }
            System.out.println(valCurs);
            metalList.forEach(System.out::println);
            currencyList.forEach(System.out::println);

        }catch (XMLStreamException  e) {
            e.printStackTrace();
        }
    }

}

