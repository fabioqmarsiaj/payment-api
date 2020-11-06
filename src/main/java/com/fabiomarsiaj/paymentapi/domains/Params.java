package com.fabiomarsiaj.paymentapi.domains;

import lombok.Data;

@Data
public class Params {

    private String type;
    private CardRequest card;
}
