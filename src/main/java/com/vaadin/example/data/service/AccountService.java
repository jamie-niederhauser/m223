package com.vaadin.example.data.service;

import com.vaadin.example.ApplicationServiceInitListener;
import com.vaadin.example.data.entity.Account;
import com.vaadin.example.data.entity.Kunde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService {

        /**
         * Access point to our DB, automatically configured by Spring.
         */
        @Autowired
        private JdbcTemplate template;

        /**
         * @return All movies in the database, with director information.
         */

        public void addAccount(Account account) {

            final String sql = AccountService.AccountRowMapper.INSERT_INTO;
            template.update(
                    sql,
                    account.getName(),
                    account.getPasswort(),
                    account.getRolle()
            );

        }


        public void deleteAccount(Long accountid) {

            final String sql = com.vaadin.example.data.service.KundenService.KundeRowMapper.DELETE_ALL;
            template.update(sql, accountid);

        }


        public void updateKundeEmail(long accountid, String newEmail) {
            final String updateSql = "UPDATE account SET passwort = ? WHERE id = ?";

            template.update(updateSql, newEmail, accountid);
        }


        public List<Account> getAccount() {

            // Use a query the sql mapper class understands
            final String sql = AccountService.AccountRowMapper.SELECT_ALL;


            // Use Spring's JdbcTemplate helper class to run the sql
            final List<Account> list = template.query(sql, new AccountService.AccountRowMapper());

            return list;
        }




        /**
         * JDBCTemplate mapper class, where we take a SQL ResultSet and parse out our
         * Java DTO(s).
         * <p>
         * The ResultSet is dependent on the SQL query we do in the Service class, so we
         * define the query here as a public constant.
         */
        public static class AccountRowMapper implements RowMapper<Account> {

            /**
             * SQL clause to fetch all Movies from the DB, with Director names included.
             */
            public static final String SELECT_ALL = "SELECT * FROM account acc";
            public static final String INSERT_INTO = "INSERT INTO account(name,passwort,rolle) values(?,?,?)";

            public static final String DELETE_ALL = "DELETE FROM account WHERE id = ?";



            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

                final Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setName(rs.getString("name"));
                account.setPasswort(rs.getString("passwort"));
                account.setRolle(rs.getString("rolle"));



                return account;
            }
        }


}
