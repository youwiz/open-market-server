package me.jjeda.mall.accounts.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAdapter extends User {

    private final Account account;

    private AccountAdapter(Account account) {
        super(account.getEmail(), account.getPassword(), authorities(account.getAccountRole()));
        this.account = account;
    }

    public static AccountAdapter from(Account account) {
        return new AccountAdapter(account);
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toSet());
    }

    public Account getAccount() {
        return this.account;
    }
}
