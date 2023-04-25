package com.example.CoinKeeper.controllers;

import com.example.CoinKeeper.models.*;
import com.example.CoinKeeper.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;


@Controller
public class HomeController {

    private final IncomeService incomeService;
    private final ExpensesService expensesService;
    private final AccountsService accountsService;
    private final ImagesService imagesService;
    private final ForexService forexService;

    public HomeController(IncomeService incomeService, ExpensesService expensesService, AccountsService accountsService, ImagesService imagesService, ForexService forexService) {
        this.incomeService = incomeService;
        this.expensesService = expensesService;
        this.accountsService = accountsService;
        this.imagesService = imagesService;
        this.forexService = forexService;
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<Income> income = incomeService.findAll();
        List<Expenses> expenses = expensesService.findAll();
        List<Accounts> accounts = accountsService.findAll();
        float inc_bal [] = new float[2];
        float exp_bal [] = new float[2];
        float acc_bal [] = new float[1];
        for (int i = 0; i < income.size(); i++) {
            if(income.get(i).getPlans() != null){
                inc_bal[1] += (income.get(i).getPlans() * income.get(i).getForex().getRatio());
            }
            if(income.get(i).getBalance() == null){
                inc_bal [0] = 0;
            }
            inc_bal[0] += (income.get(i).getBalance() * income.get(i).getForex().getRatio());
        }
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getPlans() != null){
                exp_bal[1] += (expenses.get(i).getPlans() * expenses.get(i).getForex().getRatio());
            }
            exp_bal[0] += (expenses.get(i).getBalance() * expenses.get(i).getForex().getRatio());
        }
        for (int i = 0; i < accounts.size(); i++) {
            acc_bal[0] += (accounts.get(i).getBalance() * accounts.get(i).getForex().getRatio());
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

    @GetMapping("/remove")
    public String findAllDeletion(Model model){
        List<Income> income = incomeService.findAll();
        List<Expenses> expenses = expensesService.findAll();
        List<Accounts> accounts = accountsService.findAll();
        model.addAttribute("income", income);
        model.addAttribute("expenses", expenses);
        model.addAttribute("accounts", accounts);
        return "delete";
    }

    @GetMapping("/add-income")
    public String createIncomeForm(Income income, Model model){
        List<Images> images = imagesService.findAll();
        List<Forex> forex = forexService.findAll();
        model.addAttribute("image", images);
        model.addAttribute("forex", forex);
        return "add-income";
    }

    @PostMapping("/add-income")
    public String addIncome(Income income, @RequestParam(name = "img_id")Long id, @RequestParam(name = "cur_id")Long id1){
        Images image = imagesService.findById(id);
        income.setImages(image);
        Forex forex = forexService.findById(id1);
        income.setForex(forex);
        if(income.getBalance() == null){
            income.setBalance(Float.valueOf(0));
        }
        incomeService.saveIncomes(income);
        return "redirect:/";
    }

    @GetMapping("/add-account")
    public String createAccountForm(Accounts accounts, Model model){
        List<Images> images = imagesService.findAll();
        List<Forex> forex = forexService.findAll();
        model.addAttribute("image", images);
        model.addAttribute("forex", forex);
        return "add-account";
    }

    @PostMapping("/add-account")
    public String addAccount(Accounts accounts, @RequestParam(name = "img_id")Long id, @RequestParam(name = "cur_id")Long id1){
        Images image = imagesService.findById(id);
        accounts.setImages(image);
        Forex forex = forexService.findById(id1);
        accounts.setForex(forex);
        accountsService.saveAccounts(accounts);
        return "redirect:/";
    }

    @GetMapping("/add-expense")
    public String createExpense(Expenses expenses, Model model){
        List<Images> images = imagesService.findAll();
        List<Forex> forex = forexService.findAll();
        model.addAttribute("image", images);
        model.addAttribute("forex", forex);
        return "add-expense";
    }

    @PostMapping("/add-expense")
    public String addExpense(Expenses expenses, @RequestParam(name = "img_id")Long id, @RequestParam(name = "cur_id")Long id1){
        Images image = imagesService.findById(id);
        expenses.setImages(image);
        Forex forex = forexService.findById(id1);
        expenses.setForex(forex);
        expensesService.saveExpenses(expenses);
        return "redirect:/";
    }

    @GetMapping("delete/{name}/{id}")
    public String deleteCard(@PathVariable("id") Long id, @PathVariable("name") String name){
        if(Objects.equals(name, "income")){
            incomeService.deleteById(id);
        }
        else if(Objects.equals(name, "account")){
            accountsService.deleteById(id);
        }
        else if (Objects.equals(name, "expense")){
            expensesService.deleteById(id);
        }
        return "redirect:/remove";
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
