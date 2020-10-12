package net.tovote.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = "false")
    private String name;

    @ManyToMany
    private List<User> users;

    public Group() {
    }

    public Group(long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
