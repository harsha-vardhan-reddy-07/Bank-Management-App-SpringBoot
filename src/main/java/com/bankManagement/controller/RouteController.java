package com.bankManagement.controller;

import com.bankManagement.model.DepositModel;
import com.bankManagement.model.LoansModel;
import com.bankManagement.model.TransactionsModel;
import com.bankManagement.model.UserModel;
import com.bankManagement.repos.DepositRepo;
import com.bankManagement.repos.LoanRepo;
import com.bankManagement.repos.TransactionsRepo;
import com.bankManagement.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@RestController
@Controller
public class RouteController {


    @Autowired
    UserRepo userRepo;
    @Autowired
    LoanRepo loanRepo;

    @Autowired
    DepositRepo depositRepo;

    @Autowired
    TransactionsRepo transactionsRepo;



    @RequestMapping("/")
    public ModelAndView home()
    {

        return new ModelAndView("landing");
    }


    @RequestMapping("/login")
    public ModelAndView login()
    {
        return new ModelAndView("login");
    }


    @PostMapping("/login-user")
    public UserModel loginUser(@ModelAttribute UserModel userData, Model model) {
        try {
            UserModel user = userRepo.findByEmail(userData.getEmail());

            if (user.getPassword().equals(userData.getPassword())) {
                // Return userData as JSON response
                return user;
            }
            return null;
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return null;
        }
    }






    @RequestMapping("/register")
    public ModelAndView register()
    {
        return new ModelAndView("register");
    }

    @PostMapping("/register-user")
    public UserModel registerUser(@ModelAttribute UserModel userData, Model model){
        try{

            userData.setBalance(0);

            Dictionary<String, String> ifsc= new Hashtable<>();
                ifsc.put("hyderabad", "SB007HYD25");
                ifsc.put("bangalore", "SB007BLR30");
                ifsc.put("chennai", "SB007CNI99");
                ifsc.put("mumbai", "SB007MBI12");
                ifsc.put("tirupati", "SB007TPTY05");
                ifsc.put("vizag", "SB007VZG229");
                ifsc.put("pune", "SB007PN77");
                ifsc.put("delhi", "SB007DLI09");
                ifsc.put("kochi", "SB007KCI540");
                ifsc.put("Venkatagiri", "SB007VGR313");


            userData.setIFSCCode(ifsc.get(userData.getHomeBranch()));
            userData.setUserType("user");

            return userRepo.save(userData);

        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/admin-register")
    public ModelAndView adminRegister(){
        return new ModelAndView("admin-register");
    }


    @PostMapping("/register-admin")
    public ModelAndView registerAdmin(@ModelAttribute UserModel userData, Model model){
        try{

            userData.setUserType("admin");

            userRepo.save(userData);

            return new ModelAndView("redirect:/admin");

        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/user/{id}")
    public ModelAndView userPage(@PathVariable("id") String id){
        List<UserModel> usersList = userRepo.findAll();

        List<TransactionsModel> transactionsList = transactionsRepo.findAll();

        Optional<UserModel> user = userRepo.findById(id);
        int userBalance = user.get().getBalance();

        ModelMap modelmap = new ModelMap();
        modelmap.addAttribute("usersList", usersList);
        modelmap.addAttribute("userBalance", userBalance);
        modelmap.addAttribute("transactionsList", transactionsList);

        return new ModelAndView("user/userHome", modelmap);
    }

    @RequestMapping("/user-loan")
    public ModelAndView userLoan(){
        return new ModelAndView("user/loans");
    }

    @RequestMapping("/user-loan/{id}")
    public ModelAndView userLoanPage(@PathVariable("id") String id){

        List<LoansModel> loansList = loanRepo.findAll();

        List<LoansModel> filteredLoanList = loansList.stream()
                .filter(deposit -> id.equals(deposit.getCustomerId()))
                .collect(Collectors.toList());

        List<LoansModel> reversedList = new ArrayList<>(filteredLoanList);

        Collections.reverse(reversedList);

        return new ModelAndView("user/loans", "loansList", reversedList);
    }


    @RequestMapping("/user-deposits")
    public ModelAndView userDeposits(){

        List<DepositModel> depositsList = depositRepo.findAll();
        System.out.println(depositsList);

        return new ModelAndView("user/deposits", "depositsList", depositsList);
    }

    @RequestMapping("/user-deposits/{id}")
    public ModelAndView userDeposit(@PathVariable("id") String id){

        List<DepositModel> depositsList = depositRepo.findAll();

        List<DepositModel> filteredDepositsList = depositsList.stream()
                .filter(deposit -> id.equals(deposit.getCustomerId()))
                .collect(Collectors.toList());

        List<DepositModel> reversedList = new ArrayList<>(filteredDepositsList);

        Collections.reverse(reversedList);

        System.out.println(filteredDepositsList);

        return new ModelAndView("user/deposits", "depositsList", reversedList);
    }

    @RequestMapping("/user-transactions")
    public ModelAndView userTransactions(){
        return new ModelAndView("user/transactions");
    }

    @RequestMapping("/user-transactions/{id}")
    public ModelAndView userTransactionsPage(@PathVariable("id") String id){

        List<TransactionsModel> transactionsList = transactionsRepo.findAll();

        List<TransactionsModel> filteredTransactionsList = transactionsList.stream()
                .filter(transaction -> id.equals(transaction.getSenderId()) || id.equals(transaction.getReceiverId()))
                .collect(Collectors.toList());

        List<TransactionsModel> reversedList = new ArrayList<>(filteredTransactionsList);

        Collections.reverse(reversedList);

        return new ModelAndView("user/transactions", "transactionsList", reversedList);
    }



    @RequestMapping("/send-money")
    public Optional<UserModel> sendMoney(@ModelAttribute TransactionsModel transactionData, Model model){
        try{

            LocalDateTime currentTime = LocalDateTime.now();

            String currentTimeString = currentTime.toString();

            transactionData.setTime(currentTimeString);

            Optional<UserModel> sender = userRepo.findById(transactionData.getSenderId());
            Optional<UserModel> receiver = userRepo.findById(transactionData.getReceiverId());

            receiver.ifPresent(userModel -> transactionData.setReceiverName(userModel.getUsername()));
            if(sender.isPresent() && receiver.isPresent()){
                UserModel senderData = sender.get();
                senderData.setBalance(senderData.getBalance() - transactionData.getAmount());

                UserModel receiverData = receiver.get();
                receiverData.setBalance(receiverData.getBalance() + transactionData.getAmount());

                userRepo.save(senderData);
                userRepo.save(receiverData);
            }

            transactionsRepo.save(transactionData);

            System.out.println(transactionData);

            return sender;

        }catch(Exception e){
            return null;
        }
    }


    @RequestMapping("/apply-loan")
    public ModelAndView applyLoan(@ModelAttribute LoansModel loanData, Model model){
        try{
//
            LocalDateTime currentTime = LocalDateTime.now();

            String currentTimeString = currentTime.toString();

            loanData.setCreatedDate(currentTimeString);
            loanData.setBalance(loanData.getLoanAmount());
            loanData.setLoanStatus("pending");

            System.out.println(loanData);

            loanRepo.save(loanData);

            String path = "redirect:/user-loan/" + loanData.getCustomerId();

            return new ModelAndView(path, "loanData", loanData);


        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/new-deposit")
    public ModelAndView newDeposit(@ModelAttribute DepositModel depositData, Model model){
        try{
//
            LocalDateTime currentTime = LocalDateTime.now();

            String currentTimeString = currentTime.toString();

            depositData.setCreatedDate(currentTimeString);

            Optional<UserModel> user = userRepo.findById(depositData.getCustomerId());

            if(user.isPresent()){
                UserModel userData = user.get();
                userData.setBalance(userData.getBalance() - depositData.getAmount());
                userRepo.save(userData);
            }
            System.out.println(depositData);

            depositRepo.save(depositData);

            String path = "redirect:/user-deposits/" + depositData.getCustomerId();

            return new ModelAndView(path, "depositData", depositData);


        }catch(Exception e){
            return null;
        }
    }


    @RequestMapping("/approve-loan")
    public ModelAndView approveLoan(@ModelAttribute LoansModel loanData, Model model){
        try{

            System.out.println(loanData);
            Optional<UserModel> user = userRepo.findById(loanData.getCustomerId());

//            here using _id as the name in form doesn't retrieve data, so we're using customerName to store _id and use it here
            Optional<LoansModel> loan = loanRepo.findById(loanData.getCustomerName());

            System.out.println(loan);

            System.out.println(user);

            if(user.isPresent() && loan.isPresent()){
                UserModel userData = user.get();

                LoansModel loanInfo = loan.get();

                loanInfo.setLoanStatus("approved");
                System.out.println("loan infooo" + loanInfo);
                userData.setBalance(userData.getBalance() + loanInfo.getLoanAmount());

                loanRepo.save(loanInfo);
                userRepo.save(userData);
            }
            return new ModelAndView("redirect:/all-loans");


        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/decline-loan")
    public ModelAndView declineLoan(@ModelAttribute LoansModel loanData, Model model){
        try{



            Optional<LoansModel> loan = loanRepo.findById(loanData.getCustomerName());

            if(loan.isPresent()){
                LoansModel loanInfo = loan.get();

                loanInfo.setLoanStatus("declined");

                loanRepo.save(loanInfo);
            }
            System.out.println(loanData);

            return new ModelAndView("redirect:/all-loans");


        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/loan-repayment")
    public ModelAndView repayLoan(@ModelAttribute LoansModel loanData, Model model){
        try{

            System.out.println(loanData);

            Optional<LoansModel> loan = loanRepo.findById(loanData.getCustomerId());

            if(loan.isPresent()){
                LoansModel loanInfo = loan.get();

                loanInfo.setBalance(loanInfo.getBalance() - loanData.getBalance());

                Optional<UserModel> user = userRepo.findById(loanInfo.getCustomerId());
                System.out.println(user);
                UserModel userData = user.get();
                userData.setBalance(userData.getBalance() - loanData.getBalance());

                loanRepo.save(loanInfo);
                userRepo.save(userData);
            }

            System.out.println(loan);


            String path = "redirect:/user-loan/" + loan.get().getCustomerId();

            return new ModelAndView(path, "loanData", loan);


        }catch(Exception e){
            return null;
        }
    }


    @RequestMapping("/admin")
    public ModelAndView adminHome(){

        List<UserModel> usersList = userRepo.findAll();
        List<LoansModel> loansList = loanRepo.findAll();
        List<DepositModel> depositsList = depositRepo.findAll();
        List<TransactionsModel> transactionsList = transactionsRepo.findAll();

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("usersList", usersList);
        modelMap.addAttribute("depositsList", depositsList);
        modelMap.addAttribute("loansList", loansList);
        modelMap.addAttribute("transactionsList", transactionsList);

        return new ModelAndView("admin/home", modelMap);

        // here we're sending multiple models to html page
    }

    @RequestMapping("/all-deposits")
    public ModelAndView allDeposits(){

        List<DepositModel> depositsList = depositRepo.findAll();

        return new ModelAndView("admin/allDeposits", "depositsList", depositsList);
    }

    @RequestMapping("/all-users")
    public ModelAndView allUsers(){
        List<UserModel> usersList = userRepo.findAll();


        //        ModelAndView model = new ModelAndView();
        //        model.setViewName("admin/allUsers");
        //        model.addObject("usersList", usersList);   // here, "usersList" is the name we set to the object to use with thymeleaf
        //        return model;
        //
        //        Instead of the above 4 lines of code, the simplified version is used below

        return new ModelAndView("admin/allUsers", "usersList", usersList);
    }

    @RequestMapping("/all-loans")
    public ModelAndView allLoans(){

        List<LoansModel> loansList = loanRepo.findAll();

        return new ModelAndView("admin/allLoans", "loansList", loansList);
    }

    @RequestMapping("/all-transactions")
    public ModelAndView allTransactions(){

        List<TransactionsModel> transactionsList = transactionsRepo.findAll();

        return new ModelAndView("admin/allTransactions", "transactionsList", transactionsList);
    }








}
