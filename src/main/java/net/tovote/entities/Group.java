package net.tovote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id", nullable = false)
    private long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private List<User> users;

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private List<Voting> votings;

    public Group() {
    }

    public Group(long id, String name, List<User> users) {
        this.groupId = id;
        this.name = name;
        this.users = users;
    }


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
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
