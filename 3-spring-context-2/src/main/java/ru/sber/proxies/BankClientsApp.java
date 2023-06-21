package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.model.Client;
import ru.sber.repositories.ClientsRepository;
import ru.sber.repositories.DBClientsRepository;

import java.util.List;
/**
 * Класс для проверки клиента
 */
@Component
public class BankClientsApp {

   private DBClientsRepository clients = new DBClientsRepository();
   private List<Client> clientsList = clients.getClients();

   public Boolean itBankClient(String phone){
      for (Client obj : clientsList) {
         if (obj.getPhone().equals(phone)) {
            return true;
         }
      } return false;
   }
}
