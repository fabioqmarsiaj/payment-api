package com.fabiomarsiaj.paymentapi.domains;

import lombok.Data;

@Data
public class CardRequest {

    private String number;
    private int expMonth;
    private int expYear;
    private String cvc;

}
