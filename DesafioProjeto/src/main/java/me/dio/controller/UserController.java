package me.dio.controller;

import me.dio.domain.model.User;
import me.dio.service.TransactionService;
import me.dio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<?> getAllTransactionsByUser(@PathVariable Long userId) {
        // Primeiro, buscar o usuário pelo ID
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Em seguida, buscar todas as transações desse usuário
        var transactions = transactionService.getAllTransactionsByUser(user);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{userId}/transactions")
    public ResponseEntity<?> createTransaction(@PathVariable Long userId,
                                               @RequestParam String transactionType,
                                               @RequestParam BigDecimal amount) {
        // Primeiro, buscar o usuário pelo ID
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Criar a transação para o usuário encontrado
        var transaction = transactionService.createTransaction(user, transactionType, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}