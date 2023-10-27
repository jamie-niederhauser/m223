package com.vaadin.example.views;




import com.vaadin.example.data.service.SecurityService;
import com.vaadin.example.views.addcustomerView.AddCustomer;
import com.vaadin.example.views.customerlistView.CustomerList;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;



public class MainView  extends AppLayout {


    private H2 viewTitle;

    public MainView(@Autowired SecurityService securityService) {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent(securityService);
    }

    private void addHeaderContent(@Autowired SecurityService securityService) {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);


                Button logout = new Button("Logout", click ->
                        securityService.logout());


        addToNavbar(true, toggle, viewTitle,logout);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Ticketverkauf");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Que with Customer", CustomerList.class));
        nav.addItem(new SideNavItem("AddCustomer", AddCustomer.class));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
    }


