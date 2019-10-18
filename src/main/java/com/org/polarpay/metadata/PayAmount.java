package com.org.polarpay.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayAmount {
    @JsonProperty(value="amount")
    private double amount;
    @JsonProperty(value="country")
    private String country;
    @JsonProperty(value="from vc id")
    private String fromVirtualCardId;
    @JsonProperty(value="to vc id")
    private String toVirtualCardId;

    public double getAmount()
    {
        return amount;
    }
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    public String getToVirtualAccount()
    {
        return toVirtualCardId;
    }
    public void setToVirtualAccount(String toVirtualAccount)
    {
        this.toVirtualCardId = toVirtualAccount;
    }

    public String getToVirtualCardId() {
        return toVirtualCardId;
    }

    public void setToVirtualCardId(String toVirtualCardId) {
        this.toVirtualCardId = toVirtualCardId;
    }

    public String getFromVirtualCardId() {
        return fromVirtualCardId;
    }

    public void setFromVirtualCardId(String fromVirtualCardId) {
        this.fromVirtualCardId = fromVirtualCardId;
    }
}
