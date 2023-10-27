package com.vaadin.example.data.entity;

public class Account {


    private Long id;
        private String name;

        private String passwort;

        private String rolle;



        public Account() {
        }

        public Account(String name, String passwort, String rolle) {
            this.name = name;
            this.passwort = passwort;
            this.rolle = rolle;

        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
            return name;
        }

        public String getPasswort() {
            return passwort;
        }

        public String getRolle() {
            return rolle;
        }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setName(String name) {
            this.name = name;
        }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }


    }


