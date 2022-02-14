package com.alex.musicart.model.entity;

public class User extends CustomEntity {

    public enum UserRole {
        CLIENT((short) 1), ADMIN((short) 2), GUEST((short) 2);
        private short roleId;

        UserRole(short id){
            roleId = id;
        }

        public short getRoleId(){
            return roleId;
        }
    }

    private long userId;
    private String name;
    private String login;
    private String password;
    private String email;
    private String phone;
    private UserRole role;
    //private UserState state;

    public User(){
    }

    public User(long userId, String name, String login, String password, String email, String phone, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
