package com.org.polarpay.controller;

import com.org.polarpay.metadata.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    public HashMap<String, BankAccount> bankAccountHashMap = new HashMap<>();
    public HashMap<String, CreditAccount> creditAccountHashMap = new HashMap<>();
    public HashMap<String, Double>  conversionFactorMap = new HashMap<>();
    public HashMap<String, VirtualAccount> virtualAccountHashMap = new HashMap<>();
    public HashMap<String, Double> poolBankAccounts = new HashMap<>();


    @CrossOrigin
    @PostMapping(value="/addPoolMoney")
    public HashMap<String, Double> addPools()
    {
        poolBankAccounts.put("USA", 100000.000);
        poolBankAccounts.put("UK", 100000.000);
        poolBankAccounts.put("ESP", 100000.000);
        poolBankAccounts.put("IND", 1000000.000);
        return poolBankAccounts;
    }

    @CrossOrigin
    @RequestMapping(value="/getCountryList", method=RequestMethod.GET, produces = "application/json")
    public List<String> getCountryList()
    {
        List<String> listOfCountries = new ArrayList<>();
        listOfCountries.add("USA");
        listOfCountries.add("UK");
        listOfCountries.add("ESP");
        return listOfCountries;
    }

    @CrossOrigin
    @RequestMapping(value="/payAmount", method=RequestMethod.POST, produces = "application/json")
    public HashMap<String,String> payFinalAmount(@RequestBody PayAmount payAmount)
    {
        HashMap<String, String> responseMap = new HashMap<>();
        double finalAmount = 0.00;

        conversionFactorMap.put("US", 0.014);
        conversionFactorMap.put("UK", 0.011);
        conversionFactorMap.put("ESP", 0.013);

        VirtualAccount dummyFromVirtualAccountObject = new VirtualAccount();
        VirtualAccount dummyToVirtualAccountObject = new VirtualAccount();

        double conversionFactor = conversionFactorMap.get(payAmount.getCountry());
        finalAmount = (payAmount.getAmount() /*- 0.028*payAmount.getAmount()*/) * conversionFactor;

        if(virtualAccountHashMap.get(payAmount.getFromVirtualCardId()).getAmount() < payAmount.getAmount())
        {
            responseMap.put("status","201");
            responseMap.put("message","Insufficient balance.");
            return responseMap;
        }

        System.out.println(poolBankAccounts);

        dummyFromVirtualAccountObject = virtualAccountHashMap.get(payAmount.getFromVirtualCardId());
        double currentFromBalance = dummyFromVirtualAccountObject.getAmount() - finalAmount;
        dummyFromVirtualAccountObject.setAmount(currentFromBalance);

        dummyToVirtualAccountObject = virtualAccountHashMap.get(payAmount.getToVirtualCardId());
        double currentToBalance = dummyToVirtualAccountObject.getAmount()  + finalAmount;
        dummyToVirtualAccountObject.setAmount(currentToBalance);

        double foreignPoolBalance1 = (double) poolBankAccounts.get(payAmount.getCountry());
        System.out.println(foreignPoolBalance1);
        double foreignPoolBalance = poolBankAccounts.get(payAmount.getCountry()) - finalAmount;
        poolBankAccounts.replace(payAmount.getCountry(), foreignPoolBalance);

        double homePoolBalance = poolBankAccounts.get("IND") +  1.03 * payAmount.getAmount();
        poolBankAccounts.replace("IND", homePoolBalance);
        // The profit earned by the App

        responseMap.put("status","200");
        responseMap.put("message","Transaction Successful");
        return responseMap;
    }

    @CrossOrigin
    @PostMapping("/addBankAccount")
    public BankAccount addBankAccount(@RequestBody BankAccount bankAccountObject) {
        bankAccountHashMap.put(bankAccountObject.getId(), bankAccountObject);
        return bankAccountObject;
    }

    @CrossOrigin
    @PostMapping("/addCreditCard")
    public CreditAccount addCreditCard(@RequestBody CreditAccount creditAccount) {
        creditAccountHashMap.put(creditAccount.getId(), creditAccount);
        System.out.println(creditAccount);
        return creditAccount;
    }

    @CrossOrigin
    @GetMapping(value = "/all/pools", produces = "application/json")
    public HashMap<String, Double> showPoolStatus()
    {
        return poolBankAccounts;
    }

    @CrossOrigin
    @PostMapping("/addVirtualCard")
    public VirtualAccount addVirtualCard(@RequestBody VirtualAccount virtualAccount) {
        virtualAccountHashMap.put(virtualAccount.getId(), virtualAccount);
        System.out.println(virtualAccount);
        return virtualAccount;
    }

    @CrossOrigin
    @GetMapping("/all/bankAccounts")
    public Object getAllBankAccounts() {
        return bankAccountHashMap;
    }

    @CrossOrigin
    @GetMapping("/all/creditAccounts")
    public Object getAllCreditAccounts() {
        return creditAccountHashMap;
    }

    @CrossOrigin
    @GetMapping("all/virtualAccounts")
    public Object getAllVirtualAccounts(){ return virtualAccountHashMap; }

    @CrossOrigin
    @PostMapping("/transferMoney")
    public HashMap<String, String> transferMoneyfromCreditCard(@RequestBody TransferMoney transferMoney)
    {
        HashMap<String, String> responseMap = new HashMap<>();

        CreditAccount newCreditAccountObject = new CreditAccount();
        if(creditAccountHashMap.get(transferMoney.getCreditCardId()).getAmount() < transferMoney.getAmount())
        {
            responseMap.put("status","201");
            responseMap.put("message","Insufficient balance");
            return responseMap;
        }
        double creditCardNewAmount = creditAccountHashMap.get(transferMoney.getCreditCardId()).getAmount()
                                        - transferMoney.getAmount();
        newCreditAccountObject = creditAccountHashMap.get(transferMoney.getCreditCardId());
        newCreditAccountObject.setAmount(creditCardNewAmount);

        VirtualAccount newVirtualAccountObject = new VirtualAccount();
        double virtualAccountNewAmount = virtualAccountHashMap.get(transferMoney.getVirtualAccountId()).getAmount()
                                        + transferMoney.getAmount();
        newVirtualAccountObject = virtualAccountHashMap.get(transferMoney.getVirtualAccountId());
        newVirtualAccountObject.setAmount(virtualAccountNewAmount);

        responseMap.put("status","200");
        responseMap.put("message1","Amount transferred : " + String.valueOf(transferMoney.getAmount()));
        responseMap.put("message2","Current balance : " + String.valueOf((double)newVirtualAccountObject.getAmount()));
        return responseMap;

    }




}
