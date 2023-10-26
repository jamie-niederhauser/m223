package com.vaadin.example.views.registerView;

import com.vaadin.example.data.entity.Kunde;
import com.vaadin.example.data.service.KundenService;
import com.vaadin.example.views.MainView;
import com.vaadin.example.views.customerlist.CustomerList;
import com.vaadin.example.views.loginview.Login;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Registrate")
@Route(value="Registrate")
@RouteAlias(value = "Registrate")
@AnonymousAllowed
public class Registration extends VerticalLayout {

    private final KundenService kundenService;
    private Kunde kunde;
    public Registration(KundenService kundenService) {
        this.kundenService = kundenService;


        H1 title  = new H1("Registrate");
        TextField vorname = new TextField("Name");
        TextField nachname = new TextField("Vorname");
        EmailField email = new EmailField("E-Mail");
        Button cancel = new Button("Cancel");
        Button create = new Button("Registrate and back to login",(e)->{
            String svorname = vorname.getValue();
            String snachname = nachname.getValue();
            String semail = email.getValue();
            kunde = new Kunde(svorname,snachname,semail);
            kundenService.addKunde(kunde);
            UI.getCurrent().navigate(Login.class);

        });




        FormLayout formLayout = new FormLayout(vorname,nachname,email,cancel, create);
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        create.addClickShortcut(Key.ENTER);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(event -> vorname.clear());
        cancel.addClickListener(event -> nachname.clear());
        cancel.addClickListener(event -> email.clear());
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formLayout.setColspan(vorname, 2);
        formLayout.setColspan(nachname, 2);
        formLayout.setColspan(email, 2);
        formLayout.setWidth("300px");
        formLayout.setHeight("200px");
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER,title,formLayout);

        add(title , formLayout );
    }

}
