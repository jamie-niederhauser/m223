package com.vaadin.example.views.UserView;

import com.vaadin.example.data.entity.Account;
import com.vaadin.example.data.entity.Kunde;
import com.vaadin.example.data.service.AccountService;
import com.vaadin.example.data.service.KundenService;
import com.vaadin.example.views.MainView;
import com.vaadin.example.views.addcustomerView.AddCustomer;
import com.vaadin.example.views.customerlistView.CustomerList;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.UUID;


    @PageTitle("User")
    @Route(value="User", layout = MainView.class)
    @RouteAlias(value = "User", layout = MainView.class)
    @AnonymousAllowed
    public class User extends VerticalLayout {
        private final AccountService accountService;
        private Account account;
        private Grid<Account> grid = new Grid<>(Account.class, false);


        @Autowired
        public User(AccountService accountService) {
            this.accountService = accountService;
            setSizeFull();
            configureGrid();

            setHorizontalComponentAlignment(Alignment.END);




            add(grid);
        }



        private void configureGrid() {

            grid.addClassName("Userlist");
            setSizeFull();
            grid.addColumn(Account::getName).setHeader("Firstname").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
            grid.addColumn(Account::getPasswort).setHeader("Password").setSortable(true);
            grid.addColumn(Account::getRolle).setHeader("Rolle").setSortable(true);
            grid.getColumns().forEach(col -> col.setAutoWidth(true));
            grid.setItems(accountService.getAccount());
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addSelectionListener(event -> {
                Set<Account> selected = event.getAllSelectedItems();
                Notification.show(((Set<?>) selected).size() + " items selected");
            });
        }






}
