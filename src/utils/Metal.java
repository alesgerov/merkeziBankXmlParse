package utils;

import java.math.BigDecimal;

public class Metal extends ValCurs{
    private String code;
    private String name;
    private BigDecimal value;
    private String nominal;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public Metal() {
    }

    public Metal(String code, String name, BigDecimal value, String nominal) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "Metal{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", nominal='" + nominal + '\'' +
                '}';
    }
}
