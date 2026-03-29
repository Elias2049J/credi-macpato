package com.runaitec.credimacpato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runaitec.credimacpato.usecase.UserUseCase;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public String userHome() {
        return "user/index";
    }

    @GetMapping("/leave")
    public String leaveOrganizationGet() {
        return "user/leave";
    }

    @PostMapping("/leave")
    public String leaveOrganizationPost(@RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        userUseCase.leaveOrganization(usuarioId);
        redirectAttributes.addFlashAttribute("success", "El usuario salió de la organización correctamente.");
        return "redirect:/user";
    }

    @GetMapping("/request-loan")
    public String requestLoanGet() {
        return "user/request-loan";
    }

    @PostMapping("/request-loan")
    public String requestLoanPost(@RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        userUseCase.requestLoan(usuarioId);
        redirectAttributes.addFlashAttribute("success", "Solicitud de préstamo registrada correctamente.");
        return "redirect:/user";
    }

    @GetMapping("/payments-calendar")
    public String getPaymentsCalendary(@RequestParam Long usuarioId, Model model) {
        userUseCase.getPaymentsCalendary(usuarioId);
        model.addAttribute("usuarioId", usuarioId);
        return "user/payments-calendar";
    }

    @GetMapping("/payment-history")
    public String getPaymentHistory(@RequestParam Long usuarioId, Model model) {
        userUseCase.getPaymentHistory(usuarioId);
        model.addAttribute("usuarioId", usuarioId);
        return "user/payment-history";
    }
}