package com.example.CoinKeeper.controllers;

import com.example.CoinKeeper.models.*;
import com.example.CoinKeeper.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class HomeController {

    private final IncomeService incomeService;
    private final ExpensesService expensesService;
    private final AccountsService accountsService;
    private final ImagesService imagesService;
    private final ForexService forexService;
    private final HistoryService historyService;

    public HomeController(IncomeService incomeService, ExpensesService expensesService, AccountsService accountsService, ImagesService imagesService, ForexService forexService, HistoryService historyService) {
        this.incomeService = incomeService;
        this.expensesService = expensesService;
        this.accountsService = accountsService;
        this.imagesService = imagesService;
        this.forexService = forexService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String findAll(Model model, History history){
        List<Income> income = incomeService.findAll();
        List<Expenses> expenses = expensesService.findAll();
        List<Accounts> accounts = accountsService.findAll();
        List<Forex> forex = forexService.findAll();
        Float gen_inc = (float) 0;
        Float gen_exp = (float) 0;
        String[] inc_names = new String[income.size()];
        Float[] inc_data = new Float[income.size()];
        String[] exp_names = new String[expenses.size()];
        Float[] exp_data = new Float[expenses.size()];
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
            inc_names[i] = income.get(i).getName();
            inc_data[i] = income.get(i).getBalance() * income.get(i).getForex().getRatio();
            gen_inc += income.get(i).getBalance() * income.get(i).getForex().getRatio();
        }
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getPlans() != null){
                exp_bal[1] += (expenses.get(i).getPlans() * expenses.get(i).getForex().getRatio());
            }
            exp_bal[0] += (expenses.get(i).getBalance() * expenses.get(i).getForex().getRatio());
            exp_names[i] = expenses.get(i).getName();
            exp_data[i] = expenses.get(i).getBalance() * expenses.get(i).getForex().getRatio();
            gen_exp += expenses.get(i).getBalance() * expenses.get(i).getForex().getRatio();
        }
        for (int i = 0; i < accounts.size(); i++) {
            acc_bal[0] += (accounts.get(i).getBalance() * accounts.get(i).getForex().getRatio());
        }
        Float geni [] = new Float[1];
        Float gene [] = new Float[1];
        geni[0] = gen_inc;
        gene[0] = gen_exp;
        System.out.println(gen_inc);
        System.out.println(gen_exp);
        List<History> hs = historyService.findAll();
        model.addAttribute("gen_exp", gene);
        model.addAttribute("gen_inc", geni);
        model.addAttribute("exp_data", exp_data);
        model.addAttribute("exp", exp_names);
        model.addAttribute("ina_data", inc_data);
        model.addAttribute("ina", inc_names);
        model.addAttribute("hs", hs);
        model.addAttribute("history", history);
        model.addAttribute("forex", forex);
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
        if(expenses.getBalance() == null){
            expenses.setBalance((float) 0);
        }
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

    @PostMapping("/transfer")
    public String transferCash(History history, @RequestParam(name="from_id")String id, @RequestParam(name="to_id")String id1, @RequestParam(name = "forex") Long id2) {
        Long from_id = Long.valueOf(id.substring(1));
        Long to_id = Long.valueOf(id1.substring(1));
        char ident_from = id.charAt(0);
        char ident_to = id1.charAt(0);
        Float temp;
        if (ident_from == '1') {
            Income income = incomeService.findById(from_id);
            temp = income.getBalance() * income.getForex().getRatio();
            income.setBalance((history.getSum() * history.getForex().getRatio() + temp) / income.getForex().getRatio());
            history.setFrom(income.getName());
            incomeService.saveIncomes(income);
        } else if (ident_from == '2') {
            Accounts accounts = accountsService.findById(from_id);
            temp = accounts.getBalance() * accounts.getForex().getRatio();
            accounts.setBalance((temp - (history.getSum() * history.getForex().getRatio())) / accounts.getForex().getRatio());
            history.setTo(accounts.getName());
            accountsService.saveAccounts(accounts);
        }
        if (ident_to == '2') {
            Accounts accounts = accountsService.findById(to_id);
            temp = accounts.getBalance() * accounts.getForex().getRatio();
            accounts.setBalance((history.getSum() * history.getForex().getRatio() + temp) / accounts.getForex().getRatio());
            history.setTo(accounts.getName());
            accountsService.saveAccounts(accounts);
        } else if (ident_to == '3') {
            Expenses expenses = expensesService.findById(to_id);
            temp = expenses.getBalance() * expenses.getForex().getRatio();
            expenses.setBalance((history.getSum() * history.getForex().getRatio() + temp) / expenses.getForex().getRatio());
            history.setTo(expenses.getName());
            expensesService.saveExpenses(expenses);
        }
        String year = history.getDate().substring(0, 4);
        String month = history.getDate().substring(5, 7);
        Integer day = Integer.valueOf(history.getDate().substring(8));
        switch (month) {
            case "01":
                month = "Января";
                break;
            case "02":
                month = "Февраля";
                break;
            case "03":
                month = "Марта";
                break;
            case "04":
                month = "Апреля";
                break;
            case "05":
                month = "Мая";
                break;
            case "06":
                month = "Июня";
                break;
            case "07":
                month = "Июля";
                break;
            case "08":
                month = "Августа";
                break;
            case "09":
                month = "Сентября";
                break;
            case "10":
                month = "Октября";
                break;
            case "11":
                month = "Ноября";
                break;
            case "12":
                month = "Декабря";
                break;
        }
        String date = day + " " + month + ", " + year;
        history.setDate(date);
        historyService.saveHistory(history);
        return "redirect:/";
    }
    @GetMapping("/update-income/{id}")
    public String updateIncomeForm(@PathVariable("id") Long id, Model model){
        List<Images> images = imagesService.findAll();
        model.addAttribute("image", images);
        List<Forex> forex = forexService.findAll();
        model.addAttribute("forex", forex);
        Income income = incomeService.findById(id);
        model.addAttribute("income", income);
        return "update-income";
    }

    @PostMapping("/update-income")
    public String updateIncome(Income income, @RequestParam(name = "img_id")Long id, @RequestParam(name = "cur_id")Long id1){
        Images image = imagesService.findById(id);
        income.setImages(image);
        Forex forex = forexService.findById(id1);
        income.setForex(forex);
        incomeService.saveIncomes(income);
        return "redirect:/";
    }

    @GetMapping("/update-accounts/{id}")
    public String updateAccForm(@PathVariable("id") Long id, Model model){
        List<Images> images = imagesService.findAll();
        model.addAttribute("image", images);
        List<Forex> forex = forexService.findAll();
        model.addAttribute("forex", forex);
        Accounts accounts = accountsService.findById(id);
        model.addAttribute("accounts", accounts);
        return "update-accounts";
    }

    @PostMapping("/update-accounts")
    public String updateAcc(Accounts accounts, @RequestParam(name = "img_id")Long id, @RequestParam(name = "cur_id")Long id1){
        Images image = imagesService.findById(id);
        accounts.setImages(image);
        Forex forex = forexService.findById(id1);
        accounts.setForex(forex);
        accountsService.saveAccounts(accounts);
        return "redirect:/";
    }
    @GetMapping("/update-expenses/{id}")
    public String updateExpForm(@PathVariable("id") Long id, Model model){
        List<Images> images = imagesService.findAll();
        model.addAttribute("image", images);
        List<Forex> forex = forexService.findAll();
        model.addAttribute("forex", forex);
        Expenses expenses = expensesService.findById(id);
        model.addAttribute("expenses", expenses);
        return "update-expenses";
    }

    @PostMapping("/update-expenses")
    public String updateExpenses(Expenses expenses, @RequestParam(name = "img_id")Long id, @RequestParam(name = "cur_id")Long id1){
        Images image = imagesService.findById(id);
        expenses.setImages(image);
        Forex forex = forexService.findById(id1);
        expenses.setForex(forex);
        expensesService.saveExpenses(expenses);
        return "redirect:/";
    }

    @GetMapping("/delete-history")
    public String deleteHist(){
        historyService.deleteAll();
        return "redirect:/remove";
    }
}
