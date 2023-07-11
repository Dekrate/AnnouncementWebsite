package pl.diakowski.announcementwebsite.client.dto;

public class NewClientDto {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;

    public NewClientDto(String username,
                        String password,
                        String name,
                        String lastName,
                        String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public NewClientDto() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getlastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
