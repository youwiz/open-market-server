package me.jjeda.mall.accounts.controller;

import me.jjeda.mall.accounts.Service.AccountService;
import me.jjeda.mall.accounts.domain.AccountStatus;
import me.jjeda.mall.accounts.dto.AccountDto;
import me.jjeda.mall.common.CurrentUser;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto requestAccount) {
        AccountDto accountDto = accountService.saveAccount(requestAccount);
        URI uri = ControllerLinkBuilder.linkTo(AccountController.class).slash(accountDto.getId()).toUri();

        return ResponseEntity.created(uri).body(accountDto);
    }

    @GetMapping
    public ResponseEntity getAccount(@CurrentUser AccountDto accountDto) {
        return ResponseEntity.ok(accountService.getAccount(accountDto.getId()));
    }

    @DeleteMapping
    public ResponseEntity withdrawFromMembership(@CurrentUser AccountDto accountDto) {
        accountService.changeAccountStatus(accountDto.getId(), AccountStatus.DELETED);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateAccount(@RequestBody @Valid AccountDto accountDto, @CurrentUser AccountDto currentUser) {
        AccountDto updateAccount = accountService.updateAccount(currentUser.getId(), accountDto);

        return ResponseEntity.ok(updateAccount);
    }
}
