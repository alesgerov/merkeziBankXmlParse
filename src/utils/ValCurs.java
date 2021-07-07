package utils;

import java.time.LocalDate;

public class ValCurs {
    private LocalDate dateOfCurrency;
    private String name;
    private String description;


    public ValCurs() {
    }

    public LocalDate getDateOfCurrency() {
        return dateOfCurrency;
    }

    public void setDateOfCurrency(LocalDate dateOfCurrency) {
        this.dateOfCurrency = dateOfCurrency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ValCurs{" +
                "dateOfCurrency=" + dateOfCurrency +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ValCurs(LocalDate dateOfCurrency, String name, String description) {
        this.dateOfCurrency = dateOfCurrency;
        this.name = name;
        this.description = description;
    }
}
