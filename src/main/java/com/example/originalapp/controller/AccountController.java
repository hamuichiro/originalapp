package com.example.originalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.originalapp.entity.Account;
import com.example.originalapp.entity.Account.Authority;
import com.example.originalapp.form.AccountForm;
import com.example.originalapp.repository.AccountRepository;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//「@PostMapping」を付与すると画面からPOSTメソッドで送られてきた場合の処理ができる引数には「hello.html」のフォームで設定したaction属性のパスを設定する
//メソッドの引数に「@RequestParam」を付与すると画面で入力した値が受け取れる引数にはformのname属性を指定する
//直後に記述した変数で値を受け取る「String responseVal」

@Controller
public class AccountController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository repository;

    @GetMapping(path = "/users/new")
    public String newUser(Model model) {
        model.addAttribute("form", new AccountForm()); //model.addAttribute("属性名", 渡したいデータ)で画面に渡したいデータをModelオブジェクトに追加
        return "users/new";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute("form") AccountForm form, BindingResult result, Model model, RedirectAttributes redirAttrs) {
        String name = form.getName();
        String password = form.getPassword();
        String passwordConfirmation = form.getPasswordConfirmation();

        if (repository.findByUsername(name) != null) {
            FieldError fieldError = new FieldError(result.getObjectName(), "name", "その名前はすでに使用されています。");
            result.addError(fieldError);
        }
        if (result.hasErrors()) {
        	model.addAttribute("hasMessage", true);
        	model.addAttribute("class", "alert-danger");
        	model.addAttribute("message", "ユーザー登録に失敗しました。");
            return "users/new";
        }

        Account entity = new Account(name, passwordEncoder.encode(password), Authority.ROLE_USER);
        repository.saveAndFlush(entity);
        
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-info");
        model.addAttribute("message", "ユーザー登録が完了しました。");


        return "pages/index";
    }
}