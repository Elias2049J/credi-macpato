package com.runaitec.credimacpato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runaitec.credimacpato.dto.ContributionDTO;
import com.runaitec.credimacpato.entity.Contribution;
import com.runaitec.credimacpato.usecase.CrudUseCase;

@Controller
@RequestMapping("/contribution")
public class ContributionRestController {

    private final CrudUseCase<ContributionDTO, Contribution, Integer> contributionUseCase;

    public ContributionRestController(CrudUseCase<ContributionDTO, Contribution, Integer> contributionUseCase) {
        this.contributionUseCase = contributionUseCase;
    }

    @GetMapping
    public String contributionHome() {
        return "contribution/index";
    }

    @GetMapping("/make")
    public String makeContributionGet(Model model) {
        model.addAttribute("contribucion", new ContributionDTO());
        return "contribution/make";
    }

    @PostMapping("/make")
    public String makeContributionPost(@ModelAttribute("contribucion") ContributionDTO contributionDTO) {
        if (contributionDTO.getId() == null) {
            contributionUseCase.create(contributionDTO);
        } else {
            contributionUseCase.update(contributionDTO);
        }
        return "redirect:/contribution";
    }
}