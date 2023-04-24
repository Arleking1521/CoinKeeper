package com.example.CoinKeeper.controllers;

import com.example.CoinKeeper.models.Images;
import com.example.CoinKeeper.models.Income;
import com.example.CoinKeeper.models.Accounts;
import com.example.CoinKeeper.models.Expenses;
import com.example.CoinKeeper.services.ImagesService;
import com.example.CoinKeeper.services.IncomeService;
import com.example.CoinKeeper.services.AccountsService;
import com.example.CoinKeeper.services.ExpensesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {

    private final IncomeService incomeService;
    private final ExpensesService expensesService;
    private final AccountsService accountsService;
    private final ImagesService imagesService;

    public HomeController(IncomeService incomeService, ExpensesService expensesService, AccountsService accountsService, ImagesService imagesService) {
        this.incomeService = incomeService;
        this.expensesService = expensesService;
        this.accountsService = accountsService;
        this.imagesService = imagesService;
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<Income> income = incomeService.findAll();
        List<Expenses> expenses = expensesService.findAll();
        List<Accounts> accounts = accountsService.findAll();
        int inc_bal [] = new int[2];
        int exp_bal [] = new int [2];
        int acc_bal [] = new int[1];
        for (int i = 0; i < income.size(); i++) {
            if(income.get(i).getPlans() != null){
                inc_bal[1] += (income.get(i).getPlans());
            }
            inc_bal[0] += (income.get(i).getBalance());
        }
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getPlans() != null){
                exp_bal[1] += (expenses.get(i).getPlans());
            }
            exp_bal[0] += (expenses.get(i).getBalance());
        }
        for (int i = 0; i < accounts.size(); i++) {
            acc_bal[0] += (accounts.get(i).getBalance());
        }
        model.addAttribute("inc_bal1", inc_bal[0]);
        model.addAttribute("inc_bal2", inc_bal[1]);
        model.addAttribute("exp_bal1", exp_bal[0]);
        model.addAttribute("exp_bal2", exp_bal[1]);
        model.addAttribute("acc_bal", acc_bal[0]);
        model.addAttribute("income", income);
        model.addAttribute("expenses", expenses);
        model.addAttribute("accounts", accounts);
        return "index";
    }

    @GetMapping("/add-income")
    public String createIncomeForm(Income income, Model model){
        List<Images> images = imagesService.findAll();
        model.addAttribute("image", images);
        return "add-income";
    }

    @PostMapping("/add-income")
    public String addIncome(Income income, @RequestParam(name = "img_id")Long id){
        Images image = imagesService.findById(id);
        income.setImages(image);
        incomeService.saveIncomes(income);
        return "redirect:/";
    }

    @GetMapping("income-delete/{id}")
    public String deletePlayer(@PathVariable("id") Long id){
        incomeService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/income-update/{id}")
    public String updateCarForm(@PathVariable("id") Long id, Model model){
        Income player = incomeService.findById(id);
        model.addAttribute("player", player);
        return "income-update";
    }

    @PostMapping("/player-update")
    public String updatePlayer(Income income, @RequestParam(name = "band_id")Long id){
        incomeService.saveIncomes(income);
        return "redirect:/players";
    }

}
