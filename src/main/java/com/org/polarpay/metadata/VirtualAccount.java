package com.org.polarpay.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccount {
    @JsonProperty(value = "amount")
    private double amount;
    @JsonProperty(value = "credit card associated")
    private String creditCardAssociated;
    @JsonProperty(value ="virtual card number")
    private String virtualCardNumber;
    @JsonProperty(value = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount()
    {
        return amount;
    }
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public String getCreditCardAssociated()
    {
        return creditCardAssociated;
    }
    public void setCreditCardAssociated(String creditCardAssociated)
    {
        this.creditCardAssociated = creditCardAssociated;
    }
    public String getVirtualCardNumber()
    {
        return virtualCardNumber;
    }
    public void setVirtualCardNumber(String virtualCardNumber)
    {
        this.virtualCardNumber = virtualCardNumber;
    }


}
