package ru.sotn.transactionservice.repositoty;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sotn.transactionservice.model.UserTransaction;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,Integer> {
}
