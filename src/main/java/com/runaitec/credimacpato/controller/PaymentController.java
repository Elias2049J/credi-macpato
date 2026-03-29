package com.runaitec.credimacpato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runaitec.credimacpato.usecase.PaymentUseCase;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    public PaymentController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @GetMapping
    public String paymentHome() {
        return "payment/index";
    }

    @GetMapping("/make")
    public String makePaymentGet() {
        return "payment/make";
    }

    @PostMapping("/make")
    public String makePaymentPost(@RequestParam Long usuarioId, RedirectAttributes redirectAttributes) {
        paymentUseCase.makePayment(usuarioId);
        redirectAttributes.addFlashAttribute("success", "Pago realizado correctamente.");
        return "redirect:/payment";
    }
}