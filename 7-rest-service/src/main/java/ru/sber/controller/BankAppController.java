package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.PayCard;
import ru.sber.repository.BankAppRepository;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/bank")
public class BankAppController {
    private BankAppRepository bankAppProxyInterface;
    public BankAppController(BankAppRepository bankAppProxyInterface) {
        this.bankAppProxyInterface = bankAppProxyInterface;
    }

    @PostMapping
    public Optional<PayCard> getPayCard(@RequestBody PayCard payCard) {
        return bankAppProxyInterface.getPayCard(payCard.getClientId(), payCard.getBalance());
    }

    @PostMapping({"basket"})
    public ResponseEntity<PayCard> getPayment(@RequestParam long clientId) {
        if (bankAppProxyInterface.isPayment(clientId)) {
            Optional<PayCard> payCard = bankAppProxyInterface.findById(clientId);
            if (payCard.isPresent()) {
                return ResponseEntity.accepted().body(payCard.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

}
