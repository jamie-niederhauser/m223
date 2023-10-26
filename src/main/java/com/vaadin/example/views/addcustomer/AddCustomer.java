package com.vaadin.example.views.addcustomer;


import com.vaadin.example.data.entity.Kunde;
import com.vaadin.example.data.service.KundenService;
import com.vaadin.example.views.MainView;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Addcustomer")
@Route(value="Addcustomer", layout = MainView.class)
@RouteAlias(value = "Addcustomer", layout = MainView.class)
@RolesAllowed("ADMIN")
public class AddCustomer extends VerticalLayout {
    private final KundenService kundenService;
    Binder<Kunde> binder = new BeanValidationBinder<>(Kunde.class);
    private Kunde kunde;



    @Autowired
    public AddCustomer(KundenService kundenService) {
        this.kundenService = kundenService;

        kunde = new Kunde();
        H1 title  = new H1("Put name down for queue");
        TextField vorname = new TextField("Name");
        TextField nachname = new TextField("Vorname");
        EmailField email = new EmailField("E-Mail");
        Button cancel = new Button("Cancel");
        Button create = new Button("Create");


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
        setHorizontalComponentAlignment(Alignment.CENTER,title,formLayout);

        add(title , formLayout );
    }


}
