import com.vaadin.example.data.entity.Account;
import com.vaadin.example.data.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SecurityConfigurationTest {

    @Autowired
    private AccountService accountService;

    @Configuration
    static class Config {
        @Bean
        public UserDetailsManager userDetailsManager(AccountService accountService) {
            List<Account> accounts = accountService.getAccounts();
            List<UserDetails> userDetailsList = Arrays.asList(
                User.withUsername("admin")
                    .password("admin")
                    .roles("ADMIN")
                    .build(),
                User.withUsername("user")
                    .password("user")
                    .roles("USER")
                    .build()
            );

            return new InMemoryUserDetailsManager(userDetailsList);
        }
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAdminAccess() throws Exception {
        // Test a scenario where a user with the "ADMIN" role tries to access a secured resource.
        // Replace "/secured-resource" with the actual endpoint you want to test.
        mockMvc.perform(get("/secured-resource"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUserAccess() throws Exception {
        // Test a scenario where a user with the "USER" role tries to access a secured resource.
        // Replace "/secured-resource" with the actual endpoint you want to test.
        mockMvc.perform(get("/secured-resource"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPublicAccess() throws Exception {
        // Test a scenario where a non-authenticated user tries to access a public resource.
        // Replace "/public-resource" with the actual public endpoint you want to test.
        mockMvc.perform(get("/public-resource"))
                .andExpect(status().isOk());
    }
}
