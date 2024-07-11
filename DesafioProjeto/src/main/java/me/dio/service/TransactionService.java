package me.dio.service;

import me.dio.domain.model.Transaction;
import me.dio.domain.model.User;
import me.dio.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    public Transaction createTransaction(User user, String transactionType, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getAllTransactionsByUser(User user) {
        return transactionRepository.findByUser(user);
    }
}
