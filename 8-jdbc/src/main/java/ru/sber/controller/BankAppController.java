package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.PayCard;
import ru.sber.proxy.BankAppProxy;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/bank")
public class BankAppController {
    private BankAppProxy bankAppProxyInterface;
    public BankAppController(BankAppProxy bankAppProxyInterface) {
        this.bankAppProxyInterface = bankAppProxyInterface;
    }

    @PostMapping({"basket/{basketId}/card/{cardNum}"})
    public ResponseEntity<PayCard> getPayment(@PathVariable long basketId, @PathVariable long cardNum ) {
        if (bankAppProxyInterface.isPayment(cardNum,basketId)) {
            Optional<PayCard> payCard = bankAppProxyInterface.getPayCard(cardNum);
            if (payCard.isPresent()) {
                return ResponseEntity.accepted().body(payCard.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

}
