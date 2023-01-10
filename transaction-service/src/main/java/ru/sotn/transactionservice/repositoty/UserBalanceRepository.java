package ru.sotn.transactionservice.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sotn.transactionservice.model.UserBalance;

public interface UserBalanceRepository extends JpaRepository<UserBalance,Integer> {
}
