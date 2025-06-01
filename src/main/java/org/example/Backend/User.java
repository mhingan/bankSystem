package org.example;

public class User {

    private String CNP;
    private String fName;
    private String lName;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;
    private BankAccount bk;

    public User(String CNP, String fName, String lName, String email, String phone, String address, String username, String password) {
        this.CNP = CNP;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        String error = null;
        try {
            long cnpParsed = Long.parseLong(CNP);
            error = "CNP set successfully";
        } catch (NumberFormatException e) {
            error = "Error: CNP is not a number";
        }

        this.CNP = CNP;
        System.out.println(error);
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        if(fName.isEmpty() || fName.isEmpty()) {
            throw new IllegalArgumentException("Error: fName cannot be empty");
        }
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        if(lName.isEmpty() || lName.isEmpty()) {
            throw new IllegalArgumentException("Error: lName cannot be empty");
        }
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Error: Email cannot be empty");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone.isEmpty() || phone.isEmpty()) {
            throw new IllegalArgumentException("Error: Phone cannot be empty");
        }
        String error = null;
        try {
            long numberParsed = Long.parseLong(phone);
            error = "Phone set successfully";
        } catch (NumberFormatException e) {
            error = "Error: Phone is not a number";
        }
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address.isEmpty() || address.isEmpty()) {
            throw new IllegalArgumentException("Error: Email cannot be empty");
        }
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username.isEmpty() || username.isEmpty()) {
            throw new IllegalArgumentException("Error: Username cannot be empty");
        }
        //todo add more checks
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Error: Password cannot be empty");
        }
        //todo add more checks
        this.password = password;
    }

    public BankAccount getBk() {
        return bk;
    }

    public void setBk(BankAccount bk) {
        this.bk = bk;
    }
}
