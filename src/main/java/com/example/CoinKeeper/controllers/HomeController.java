package com.example.CoinKeeper.controllers;

import com.example.CoinKeeper.models.Income;
import com.example.CoinKeeper.models.Accounts;
import com.example.CoinKeeper.models.Expenses;
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
    public HomeController(IncomeService incomeService, ExpensesService expensesService, AccountsService accountsService) {
        this.incomeService = incomeService;
        this.expensesService = expensesService;
        this.accountsService = accountsService;
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<Income> income = incomeService.findAll();
        List<Expenses> expenses = expensesService.findAll();
        List<Accounts> accounts = accountsService.findAll();
        var balance1 = 0;
        for (int i = 0; i < income.size(); i++) {
            balance1 += (income.get(i).getBalance());
        }
        model.addAttribute("sum_balance1", balance1);
        model.addAttribute("income", income);
        model.addAttribute("expenses", expenses);
        model.addAttribute("accounts", accounts);
        return "index";
    }

    @GetMapping("/add-income")
    public String createIncomeForm(Income income, Model model){
        return "index";
    }

    @PostMapping("/add-income")
    public String addIncome(Income income, @RequestParam(name = "income_id") Long id){
        incomeService.saveIncomes(income);
        return "redirect:/add-income";
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
