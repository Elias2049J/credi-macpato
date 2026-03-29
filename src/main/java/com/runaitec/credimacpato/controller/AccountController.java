package com.runaitec.credimacpato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runaitec.credimacpato.dto.AccountDTO;
import com.runaitec.credimacpato.usecase.AccountUseCase;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountUseCase accountUseCase;

    public AccountController(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @GetMapping
    public String accountHome() {
        return "account/index";
    }

    @GetMapping("/open")
    public String openAccountGet(Model model) {
        model.addAttribute("accountDTO", new AccountDTO());
        return "account/open";
    }

    @PostMapping("/open")
    public String openAccountPost(@ModelAttribute AccountDTO accountDTO) {
        accountUseCase.openAccount(accountDTO.getUsuarioId());
        return "redirect:/account";
    }

    @GetMapping("/close")
    public String closeAccountGet() {
        return "account/close";
    }

    @PostMapping("/close")
    public String closeAccountPost(@RequestParam Long accountId) {
        accountUseCase.closeAccount(accountId);
        return "redirect:/account";
    }

    @GetMapping("/block")
    public String blockAccountGet() {
        return "account/block";
    }

    @PostMapping("/block")
    public String blockAccountPost(@RequestParam Long accountId) {
        accountUseCase.blockAccount(accountId);
        return "redirect:/account";
    }
}