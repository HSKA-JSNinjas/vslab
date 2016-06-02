package hska.iwi.eShopMaster.model;

/**
 * Created by d059314 on 02.06.16.
 */
public class User {

    private Long id;
    private String name;
    private String passwd;
    private String role;

    public User(){}

    public User(Long id, String name, String pwd, String role) {
        this.id = id;
        this.name = name;
        this.passwd = pwd;
        this.role = role;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", passwd=" + passwd + ", role=" + role + "]";
    }
}
