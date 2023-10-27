package com.vaadin.example.views.loginView;
import com.vaadin.example.views.registerView.Registration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@RouteAlias("login")
@AnonymousAllowed
public class Login extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();

    public Login() {
        addClassName("login-view");
        setSizeFull();

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setForgotPassword("Registration");
        login.setI18n(i18n);


        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");

        login.addForgotPasswordListener( e-> UI.getCurrent().navigate(Registration.class));

        add(new H1("Ticketverkauf"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }



}
