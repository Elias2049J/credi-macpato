package com.runaitec.credimacpato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runaitec.credimacpato.usecase.LoanUseCase;

@Controller
@RequestMapping("/loan")
public class LoanController {

    private final LoanUseCase loanUseCase;

    public LoanController(LoanUseCase loanUseCase) {
        this.loanUseCase = loanUseCase;
    }

    @GetMapping
    public String loanHome() {
        return "loan/index";
    }

    @GetMapping("/approve")
    public String approveLoanGet() {
        return "loan/approve";
    }

    @PostMapping("/approve")
    public String approveLoanPost(@RequestParam Long loanId, RedirectAttributes redirectAttributes) {
        loanUseCase.approveLoan(loanId);
        redirectAttributes.addFlashAttribute("success", "Préstamo aprobado correctamente.");
        return "redirect:/loan";
    }

    @GetMapping("/deny")
    public String denyLoanGet() {
        return "loan/deny";
    }

    @PostMapping("/deny")
    public String denyLoanPost(@RequestParam Long loanId, RedirectAttributes redirectAttributes) {
        loanUseCase.denyLoan(loanId);
        redirectAttributes.addFlashAttribute("success", "Préstamo denegado correctamente.");
        return "redirect:/loan";
    }
}