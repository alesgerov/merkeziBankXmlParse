package utils;

import java.math.BigDecimal;

public class MoneyCurrency extends ValCurs{
    private String code;
    private String name;
    private BigDecimal value;
    private int nominal;

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

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public MoneyCurrency() {
    }

    public MoneyCurrency(String code, String name, BigDecimal value, int nominal) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "MoneyCurrency{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", nominal=" + nominal +
                '}';
    }
}
