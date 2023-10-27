import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vaadin.example.data.entity.Account;
import com.vaadin.example.data.service.AccountService;

public class AccountServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1L);
        account.setName("John");
        account.setPasswort("password");
        account.setRolle("admin");
        MockitoAnnotations.openMocks(this);
        this.accountService = new AccountService(jdbcTemplate);
    }

    @Test
    public void testAddAccount() {
        final String sql = AccountService.AccountRowMapper.INSERT_INTO;
        when(jdbcTemplate.update(sql, account.getName(), account.getPasswort(), account.getRolle())).thenReturn(1);

        accountService.addAccount(account);

        verify(jdbcTemplate).update(sql, account.getName(), account.getPasswort(), account.getRolle());
    }



}