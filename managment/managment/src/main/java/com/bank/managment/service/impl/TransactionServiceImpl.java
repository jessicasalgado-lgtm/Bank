package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.request.UpdateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.Transaction;
import com.bank.managment.exception.NotFoundException;
import com.bank.managment.exception.TransactionNotValidException;
import com.bank.managment.mapper.TransactionMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.repository.TransactionRepository;
import com.bank.managment.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionDTO save(CreateTransactionDTO dto) {
        Account account = accountRepository.findById(dto.getIdAccount())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + dto.getIdAccount()));

        // Validación de monto
        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new TransactionNotValidException("El monto de la transacción debe ser mayor a 0");
        }

        // Actualizar saldo según tipo de transacción
        String type = dto.getTransactionType().toUpperCase();
        switch (type) {
            case "CREDIT": // depósito
                account.setBalance(account.getBalance() + dto.getAmount());
                break;

            case "DEBIT": // retiro
                if (dto.getAmount() > account.getBalance()) {
                    throw new TransactionNotValidException("Fondos insuficientes en la cuenta " + account.getAccountNumber());
                }
                account.setBalance(account.getBalance() - dto.getAmount());
                break;

            default:
                throw new TransactionNotValidException("Tipo de transacción no válido: " + dto.getTransactionType());
        }

        // Guardar el cambio en el saldo de la cuenta
        accountRepository.save(account);

        // Guardar la transacción
        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setAccount(account);
        Transaction saved = transactionRepository.save(transaction);

        return transactionMapper.toDTO(saved);
    }

    @Override
    public TransactionDTO update(UpdateTransactionDTO dto) {
        return transactionRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setAmount(dto.getAmount());
                    existing.setTransactionType(dto.getType());

                    Account account = accountRepository.findById(dto.getIdAccount())
                            .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con id " + dto.getIdAccount()));
                    existing.setAccount(account);

                    Transaction updated = transactionRepository.save(existing);
                    return transactionMapper.toDTO(updated);
                })
                .orElseThrow(() -> new NotFoundException("Transacción no encontrada con id " + dto.getId()));
    }

    @Override
    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new NotFoundException("Transacción no encontrada con id " + id);
        }
        transactionRepository.deleteById(id);
    }

    @Override
    public Optional<TransactionDTO> getById(Long id) {
        return Optional.ofNullable(transactionRepository.findById(id)
                .map(transactionMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Transacción no encontrada con id " + id)));
    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO transfer(Long idCuentaOrigen, Long idCuentaDestino, Double monto) {
        if (monto == null || monto <= 0) {
            throw new TransactionNotValidException("El monto de la transferencia debe ser mayor a 0");
        }

        Account origen = accountRepository.findById(idCuentaOrigen)
                .orElseThrow(() -> new NotFoundException("Cuenta origen no encontrada con id " + idCuentaOrigen));

        Account destino = accountRepository.findById(idCuentaDestino)
                .orElseThrow(() -> new NotFoundException("Cuenta destino no encontrada con id " + idCuentaDestino));

        if (origen.getBalance() < monto) {
            throw new TransactionNotValidException("Fondos insuficientes en la cuenta origen " + origen.getAccountNumber());
        }

        // Actualizacion de saldos
        origen.setBalance(origen.getBalance() - monto);
        destino.setBalance(destino.getBalance() + monto);

        accountRepository.save(origen);
        accountRepository.save(destino);

        // Transacción de salida
        Transaction transOut = new Transaction();
        transOut.setTransactionType("TRANSFER_OUT");
        transOut.setAmount(monto);
        transOut.setAccount(origen);
        transactionRepository.save(transOut);

        // Transacción de entrada
        Transaction transIn = new Transaction();
        transIn.setTransactionType("TRANSFER_IN");
        transIn.setAmount(monto);
        transIn.setAccount(destino);
        transactionRepository.save(transIn);


        return transactionMapper.toDTO(transOut);
    }

}






















/*package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateTransactionDTO;
import com.bank.managment.dto.request.UpdateTransactionDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.entity.Account;
import com.bank.managment.entity.Transaction;
import com.bank.managment.mapper.TransactionMapper;
import com.bank.managment.repository.AccountRepository;
import com.bank.managment.repository.TransactionRepository;
import com.bank.managment.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
    }
    @Override
    public TransactionDTO save(CreateTransactionDTO dto) {
        Account account = accountRepository.findById(dto.getIdAccount())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + dto.getIdAccount()));

        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setAccount(account); // ✅ enlazar cuenta

        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toDTO(saved);
    }
    @Override
    public TransactionDTO update(UpdateTransactionDTO dto) {
        return transactionRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setAmount(dto.getAmount());
                    existing.setTransactionType(dto.getType());

                    Account account = accountRepository.findById(dto.getIdAccount())
                            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + dto.getIdAccount()));
                    existing.setAccount(account); // ✅ enlazar cuenta

                    Transaction updated = transactionRepository.save(existing);
                    return transactionMapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con id " + dto.getId()));
    }

    /*
    @Override
    public TransactionDTO save(CreateTransactionDTO dto) {

        Account account = accountRepository.findById(dto.getIdAccount()).get();
        Transaction transaction = transactionMapper.toEntity(dto);
        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toDTO(saved);
    }


    @Override
    public TransactionDTO update(UpdateTransactionDTO dto) {
        return transactionRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setAmount(dto.getAmount());
                    existing.setTransactionType(dto.getType());
                    existing.setIdTransaction(dto.getIdAccount());
                    Transaction updated = transactionRepository.save(existing);
                    return transactionMapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con id " + dto.getId()));
    }

    @Override
    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transacción no encontrada con id " + id);
        }
        transactionRepository.deleteById(id);
    }

    @Override
    public Optional<TransactionDTO> getById(Long id) {
        return transactionRepository.findById(id).map(transactionMapper::toDTO);
    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

}
*/