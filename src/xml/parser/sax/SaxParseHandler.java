package xml.parser.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.Metal;
import utils.MoneyCurrency;
import utils.ValCurs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SaxParseHandler extends DefaultHandler {

    private ValCurs valCurs=new ValCurs();
    private MoneyCurrency moneyCurrency=null;
    private Metal metal=null;
    private Map<String,Boolean> onOff=new HashMap<>();
    private List<Metal> metalList=new ArrayList<>();
    private List<MoneyCurrency> moneyCurrencyList=new ArrayList<>();
    private boolean isMetal=false;
    private boolean isCurrency=false;


    public List<Metal> getMetalList() {
        return metalList;
    }

    public ValCurs getValCurs() {
        return valCurs;
    }

    public List<MoneyCurrency> getMoneyCurrencyList() {
        return moneyCurrencyList;
    }

    public SaxParseHandler() {
        super();
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Process basladi.");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Process bitdi.");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (qName.toLowerCase(Locale.ROOT).equals("valtype")){
            if (attributes.getValue("Type").toLowerCase(Locale.ROOT).equals("bank metallari")){
                this.isMetal=true;
            }else if (attributes.getValue("Type").toLowerCase(Locale.ROOT).equals("xarici valyutalar")){
                this.isCurrency=true;
            }
        }else if (qName.toLowerCase(Locale.ROOT).equals("valute")){
            if (isMetal){
                this.metal=new Metal();
                this.metal.setCode(attributes.getValue("Type"));
            } else if (isCurrency) {
                this.moneyCurrency=new MoneyCurrency();
                this.moneyCurrency.setCode(attributes.getValue("Code"));
            }
        }else if (qName.toLowerCase(Locale.ROOT).equals("valcurs")){
//            System.out.println(attributes.getValue("Name"));
            this.valCurs.setName(attributes.getValue("Name"));
            this.valCurs.setDateOfCurrency(LocalDate.parse(attributes.getValue("Date"),formatter));
            this.valCurs.setDescription(attributes.getValue("Description"));
        }
        this.onOff.put(qName.toLowerCase(Locale.ROOT),true);

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.toLowerCase(Locale.ROOT).equals("valtype")){
            this.isCurrency=false;
            this.isMetal=false;
        }
        else if (qName.toLowerCase(Locale.ROOT).equals("valute")){
            if (isMetal){
                this.metalList.add(this.metal);
                this.metal=null;
            }else if (isCurrency){
                this.moneyCurrencyList.add(this.moneyCurrency);
                this.moneyCurrency=null;
            }
        }
        this.onOff.put(qName.toLowerCase(Locale.ROOT),false);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data=new String(ch,start,length);
        if (onOff.getOrDefault("nominal",false)){
            if (isMetal){
                this.metal.setNominal(data);
                System.out.println("ok");
            }else if (this.isCurrency){
                this.moneyCurrency.setNominal(Integer.parseInt(data));
            }
        }else if (onOff.getOrDefault("name",false)){
            if (isMetal){
                this.metal.setName(data);
            }else if (this.isCurrency){
                this.moneyCurrency.setName(data);
            }
        }else if (onOff.getOrDefault("value",false)){
            if (isMetal){
                this.metal.setValue(new BigDecimal(data));
            }else if (this.isCurrency){
                this.moneyCurrency.setValue(new BigDecimal(data));
            }
        }
    }
}
