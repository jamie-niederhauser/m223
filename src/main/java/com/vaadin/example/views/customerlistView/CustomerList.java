package com.vaadin.example.views.customerlistView;

import java.util.UUID;
import com.vaadin.example.data.entity.Kunde;
import com.vaadin.example.views.MainView;
import com.vaadin.example.views.addcustomerView.AddCustomer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;


import com.vaadin.example.ApplicationServiceInitListener;
import com.vaadin.example.data.service.KundenService;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;


/**
 * A simple Vaadin View class that shows all Movies in a database.
 * <p>
 * See {@link KundenService} for details on the database, and
 * {@link ApplicationServiceInitListener} for adding more demo data.
 */

@PageTitle("")
@Route(value="", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@AnonymousAllowed
public class CustomerList extends VerticalLayout {

    UUID uuid = UUID.randomUUID();
    String uniqueId = uuid.toString();

    private static final Logger log = LoggerFactory.getLogger(CustomerList.class);
    private final KundenService kundenService;
    private Kunde kunde;
    private Grid<Kunde> grid = new Grid<>(Kunde.class, false);

    Button add = new Button("Add");
    Button delete = new Button("Delete");

    Button changeMail = new Button("Change your e-mail");

    String allowedUUID = "Ihre_erlaubte_UUID"; // Ersetzen Sie dies durch Ihre gewünschte UUID


    @Autowired
    public CustomerList(KundenService kundenService) {
        this.kundenService = kundenService;
        setSizeFull();
        configureGrid();

        add.addClickListener(e -> UI.getCurrent().navigate(AddCustomer.class));
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e-> {
                    Set<Kunde> selectedItems = grid.getSelectedItems();

                    for (Kunde kunde : selectedItems) {
                        // Führen Sie die Löschaktion für jede ausgewählte Zeile durch, z.B. mit einem KundenService
                        kundenService.deleteKunde(kunde.getId());
                        refreshGrid(grid);
                    }
            // Aktualisieren Sie das Grid nach dem Löschen

        });
        changeMail.addClickListener(event -> {
            Set<Kunde> selectedItems = grid.getSelectedItems();
            if (!selectedItems.isEmpty()) {
                Kunde selectedKunde = selectedItems.iterator().next();

                Dialog emailEditDialog = new Dialog();
                TextField emailField = new TextField("Neue E-Mail-Adresse");
                emailField.setValue(selectedKunde.getEmail());

                Button saveButton = new Button("Speichern");
                saveButton.addClickListener(saveEvent -> {
                    String newEmail = emailField.getValue();
                    selectedKunde.setEmail(newEmail);

                    // Aktualisieren Sie die E-Mail-Adresse in der Datenbank (verwenden Sie Ihre Datenbankmethode)
                    kundenService.updateKundeEmail(selectedKunde.getId(), newEmail);

                    // Aktualisieren Sie das Grid, um die Änderungen anzuzeigen
                    grid.getDataProvider().refreshItem(selectedKunde);
                    emailEditDialog.close();
                });

                emailEditDialog.add(emailField, saveButton);
                emailEditDialog.open();
            }
        });



        HorizontalLayout horizontalLayout = new HorizontalLayout(add, delete,changeMail);

        setHorizontalComponentAlignment(Alignment.END, horizontalLayout);




        add(grid, horizontalLayout);
    }



    private void configureGrid() {

        grid.addClassName("ContactList");
        setSizeFull();
        grid.addColumn(Kunde::getName).setHeader("Firstname").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(Kunde::getVorname).setHeader("Lastname").setSortable(true);
        grid.addColumn(Kunde::getEmail).setHeader("Emails").setSortable(true);
        grid.addColumn(Kunde::getUid).setHeader("Uid").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setItems(kundenService.getKunden());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(event -> {
            Set<Kunde> selected = event.getAllSelectedItems();
            Notification.show(((Set<?>) selected).size() + " items selected");
        });
    }

    private void refreshGrid(Grid<Kunde> grid) {
        List<Kunde> updatedKunden = kundenService.getKunden();
        grid.setItems(updatedKunden);
    }



}
