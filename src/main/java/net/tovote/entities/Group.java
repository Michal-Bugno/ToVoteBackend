package net.tovote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id", nullable = false)
    private long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToMany(targetEntity = User.class, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private Set<User> users;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner; // TODO Consider changing it to OneToMany/ManyToMany

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private Set<Voting> votings;

    public Group() {
    }

    public Group(long id, String name, Set<User> users, String description, Set<Voting> votings) {
        this.groupId = id;
        this.name = name;
        this.users = users;
        this.description = description;
        this.votings = votings;
    }


    public void addUser(User user){
        users.add(user);
        user.getGroups().add(this);
    }

    public void removeUser(User user){
        users.remove(user);
        user.getGroups().remove(this);
    }


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId, User owner) {
        this.groupId = groupId;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Voting> getVotings() {
        return votings;
    }

    public void setVotings(Set<Voting> votings) {
        this.votings = votings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
