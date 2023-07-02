package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.proxy.BankAppProxy;


@Slf4j
@RestController
@RequestMapping("/bank")
public class BankAppController {
    private BankAppProxy bankAppProxyInterface;
    public BankAppController(BankAppProxy bankAppProxyInterface) {
        this.bankAppProxyInterface = bankAppProxyInterface;
    }

    @PostMapping("/basket/{basketId}/card/{cardNum}")
    public ResponseEntity<String> getPayment(@PathVariable long basketId, @PathVariable long cardNum) {
        ResponseEntity<String> response = bankAppProxyInterface.isPayment(cardNum, basketId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.accepted().body("Оплата прошла успешно");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody().toString());
        }
    }



}
