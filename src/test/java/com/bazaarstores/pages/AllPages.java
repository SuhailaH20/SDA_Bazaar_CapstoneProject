package com.bazaarstores.pages;


import org.openqa.selenium.WebDriver;

public class AllPages {
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private DashboardPage dashboardPage;
    private StorsPage storsPage;
    private AddStoreAsAdminPage addStoreAsAdminPage;

    private HomePage homePage;

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public RegistrationPage getRegistrationPage() {
        if (registrationPage == null) {
            registrationPage = new RegistrationPage();
        }
        return registrationPage;
    }

    public DashboardPage getDashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage();
        }
        return dashboardPage;
    }

    public StorsPage getStorsPage() {//rajja
        if (storsPage == null) {
            storsPage = new StorsPage();
        }
        return storsPage;
    }


    public AddStoreAsAdminPage getAddStoreAsAdminPage() {//rajja
        if (addStoreAsAdminPage == null) {
            addStoreAsAdminPage = new AddStoreAsAdminPage();
        }
        return addStoreAsAdminPage;
    }
    //Lama
    private UserPage UserPage;
    public UserPage getUserPage() {
        if (UserPage == null) {
            UserPage = new UserPage();
        }
        return UserPage;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }
}