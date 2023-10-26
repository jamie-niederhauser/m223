package com.vaadin.example.views.customerlist;

import com.vaadin.example.data.entity.Kunde;
import com.vaadin.example.views.MainView;
import com.vaadin.example.views.addcustomer.AddCustomer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.example.ApplicationServiceInitListener;
import com.vaadin.example.data.service.KundenService;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

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

    private final KundenService kundenService;
    private Kunde kunde;
    private Grid<Kunde> grid = new Grid<>(Kunde.class, false);

    Button add = new Button("Add");
    Button delete = new Button("Delete");

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
        HorizontalLayout horizontalLayout = new HorizontalLayout(add, delete);

        setHorizontalComponentAlignment(Alignment.END, horizontalLayout);




        add(grid, horizontalLayout);
    }

    private void configureGrid() {

        grid.addClassName("ContactList");
        setSizeFull();
        grid.addColumn(Kunde::getName).setHeader("Firstname").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(Kunde::getVorname).setHeader("Lastname").setSortable(true);
        grid.addColumn(Kunde::getEmail).setHeader("Emails").setSortable(true);
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
