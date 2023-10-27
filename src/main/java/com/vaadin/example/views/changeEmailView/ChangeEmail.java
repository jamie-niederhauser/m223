package com.vaadin.example.views.changeEmailView;

import com.vaadin.example.data.entity.Kunde;
import com.vaadin.example.data.service.KundenService;
import com.vaadin.example.views.MainView;
import com.vaadin.example.views.customerlistView.CustomerList;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Changemail")
@Route(value="Changemail", layout = MainView.class)
@RouteAlias(value = "Changemail", layout = MainView.class)
public class ChangeEmail extends VerticalLayout {

    private final KundenService kundenService;
    Binder<Kunde> binder = new BeanValidationBinder<>(Kunde.class);
    private Kunde kunde;


    @Autowired
    public ChangeEmail(KundenService kundenService) {
        this.kundenService = kundenService;

        H1 title = new H1("Put name down for queue");
        EmailField email = new EmailField("E-Mail");
        Button cancel = new Button("Cancel");
        Button create = new Button("Create", (e) -> {
            String semail = email.getValue();

            UI.getCurrent().navigate(CustomerList.class);

        });

        FormLayout formLayout = new FormLayout(email,cancel, create);
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        create.addClickShortcut(Key.ENTER);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(event -> email.clear());
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formLayout.setColspan(email, 2);
        formLayout.setWidth("300px");
        formLayout.setHeight("200px");
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, title, formLayout);

        add(title , formLayout );
    }


}
