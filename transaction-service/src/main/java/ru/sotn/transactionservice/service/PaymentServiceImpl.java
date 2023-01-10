//package ru.sotn.transactionservice.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.sotn.transactionservice.model.UserBalance;
//import ru.sotn.transactionservice.repositoty.UserBalanceRepository;
//import ru.sotn.transactionservice.repositoty.UserTransactionRepository;
//
//import javax.annotation.PostConstruct;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentServiceImpl {
//    private UserBalanceRepository userBalanceRepository;
//    private UserTransactionRepository userTransactionRepository;
//
//
//    /**
//     * // get the user id
//     * // check the balance availability
//     * // if balance sufficient -> Payment completed and deduct amount from DB
//     * // if payment not sufficient -> cancel order event and update the amount in DB
//     **/
//    @Transactional
//    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
//        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
//
//        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
//                orderRequestDto.getUserId(), orderRequestDto.getAmount());
//
//        return userBalanceRepository.findById(orderRequestDto.getUserId())
//                .filter(ub -> ub.getPrice() > orderRequestDto.getAmount())
//                .map(ub -> {
//                    ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
//                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount()));
//                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
//                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
//
//    }
//
//    @Transactional
//    public void cancelOrderEvent(OrderEvent orderEvent) {
//
//        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
//                .ifPresent(ut->{
//                    userTransactionRepository.delete(ut);
//                    userTransactionRepository.findById(ut.getUserId())
//                            .ifPresent(ub->ub.setAmount(ub.getAmount()+ut.getAmount()));
//                });
//    }
//}
