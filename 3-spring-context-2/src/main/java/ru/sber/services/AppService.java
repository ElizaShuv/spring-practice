package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.model.Aplication;
import ru.sber.proxies.BankClientsApp;
import ru.sber.proxies.TransferByPhoneApp;
import ru.sber.repositories.DataBase;
@Service
public class AppService {
    private BankClientsApp bankClientsApp = new BankClientsApp();
    private TransferByPhoneApp transferByPhoneApp = new TransferByPhoneApp();
    private DataBase dataBase = new DataBase();
    public void transferProcess(Aplication aplication){
         if (bankClientsApp.itBankClient(aplication.getPhone())){
             transferByPhoneApp.transferMassage(aplication);
             dataBase.addInformation(aplication);
         }
         else System.out.println("Ошибка! Пользователь не является клиентом банка");
    }
}
