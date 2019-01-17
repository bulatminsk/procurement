package by.htp.procurement.entity;

import java.util.Objects;

public class Company extends Entity {

    private Integer id;
    private String taxNumber;
    private String name;
    private String web;
    private String country;
    private Boolean isArchieved;

    public Company() {
    }

    public Company(Integer id, String taxNumber, String name, String web, String country, Boolean isArchieved) {
        this.id = id;
        this.taxNumber = taxNumber;
        this.name = name;
        this.web = web;
        this.country = country;
        this.isArchieved = isArchieved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsArchieved() {
        return isArchieved;
    }

    public void setIsArchieved(Boolean isArchieved) {
        this.isArchieved = isArchieved;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.taxNumber);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.web);
        hash = 19 * hash + Objects.hashCode(this.country);
        hash = 19 * hash + Objects.hashCode(this.isArchieved);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if (!Objects.equals(this.taxNumber, other.taxNumber)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.web, other.web)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.isArchieved, other.isArchieved)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Company{" + "companyId=" + id + ", taxNumber=" + taxNumber + ", name=" + name + ", web=" + web + ", country=" + country + ", isArchieved=" + isArchieved + '}';
    }
}
