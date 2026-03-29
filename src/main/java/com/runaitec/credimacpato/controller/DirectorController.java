package com.runaitec.credimacpato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runaitec.credimacpato.usecase.DirectorsUseCase;

@Controller
@RequestMapping("/director")
public class DirectorController {

    private final DirectorsUseCase directorsUseCase;

    public DirectorController(DirectorsUseCase directorsUseCase) {
        this.directorsUseCase = directorsUseCase;
    }

    @GetMapping
    public String directorHome() {
        return "director/index";
    }

    @GetMapping("/disburse")
    public String disburseMoneyGet() {
        return "director/disburse";
    }

    @PostMapping("/disburse")
    public String disburseMoneyPost(@RequestParam Long solicitudId, RedirectAttributes redirectAttributes) {
        directorsUseCase.disburseMoney(solicitudId);
        redirectAttributes.addFlashAttribute("success", "Desembolso realizado correctamente.");
        return "redirect:/director";
    }

    @GetMapping("/evaluate")
    public String evaluateLoanRequestGet() {
        return "director/evaluate";
    }

    @PostMapping("/evaluate")
    public String evaluateLoanRequestPost(@RequestParam Long solicitudId, RedirectAttributes redirectAttributes) {
        directorsUseCase.evaluateLoanRequest(solicitudId);
        redirectAttributes.addFlashAttribute("success", "Solicitud evaluada correctamente.");
        return "redirect:/director";
    }

    @GetMapping("/approve")
    public String approveRequestGet() {
        return "director/approve";
    }

    @PostMapping("/approve")
    public String approveRequestPost(@RequestParam Long solicitudId, RedirectAttributes redirectAttributes) {
        directorsUseCase.approveRequest(solicitudId);
        redirectAttributes.addFlashAttribute("success", "Solicitud aprobada correctamente.");
        return "redirect:/director";
    }

    @GetMapping("/deny")
    public String denyRequestGet() {
        return "director/deny";
    }

    @PostMapping("/deny")
    public String denyRequestPost(@RequestParam Long solicitudId, RedirectAttributes redirectAttributes) {
        directorsUseCase.denyRequest(solicitudId);
        redirectAttributes.addFlashAttribute("success", "Solicitud denegada correctamente.");
        return "redirect:/director";
    }

    @GetMapping("/vote")
    public String voteRequestGet() {
        return "director/vote";
    }

    @PostMapping("/vote")
    public String voteRequestPost(@RequestParam Long solicitudId,
                                  @RequestParam Long usuarioId,
                                  RedirectAttributes redirectAttributes) {
        directorsUseCase.voteRequest(solicitudId, usuarioId);
        redirectAttributes.addFlashAttribute("success", "Voto registrado correctamente.");
        return "redirect:/director";
    }
}