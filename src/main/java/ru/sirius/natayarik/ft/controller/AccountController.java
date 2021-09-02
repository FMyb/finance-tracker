package ru.sirius.natayarik.ft.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.AccountCreateDTO;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.services.AccountService;
import ru.sirius.natayarik.ft.services.UserToAccountService;

import java.util.List;

/**
 * @author Natalia Nikonova
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Метод для создания кошелька")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDTO createAccount(@RequestBody AccountCreateDTO account) {
        return accountService.create(account);
    }

    @Operation(summary = "Метод для получения всех кошельков пользователя")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAll();
    }

    @Operation(summary = "Метод для получения кошелька по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDTO getAccountById(@PathVariable("id") long accountId) {
        return accountService.getAccountById(accountId);
    }

    @Operation(summary = "Метод для удаления кошелька по id")
    @DeleteMapping(value = "/{id}")
    public void deleteAccount(@PathVariable("id") long accountId) {
        accountService.delete(accountId);
    }

    @Operation(summary = "Метод для изменения кошелька по id")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDTO changeAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.change(accountDTO);
    }
}
