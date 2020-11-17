package net.tovote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.tovote.enums.VotingType;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "votings")
public class Voting {

    @Id
    @GeneratedValue
    @Column(name = "voting_id", nullable = false)
    private long votingId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "start_time_stamp")
    private long startTimeStamp;

    @Column(name = "end_time_stamp")
    private long endTimeStamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "voting_type", length = 25, nullable = false)
    private VotingType votingType;

    @Column(name = "name")
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "votingOptionId")
    private List<VotingOption> options;

    @JsonIgnore
    @ManyToMany(targetEntity = Group.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "votings_groups",
            joinColumns = @JoinColumn(name = "voting_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    public Voting(){

    }

    public Voting(long id, User user, long startTimeStamp, long endTimeStamp, VotingType votingType, String description, List<VotingOption> options, Set<Group> groups, String name) {
        votingId = id;
        this.user = user;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.votingType = votingType;
        this.description = description;
        this.options = options;
        this.groups = groups;
        this.name = name;
    }

    public void addGroup(Group group){
        groups.add(group);
        group.getVotings().add(this);
    }

    public void removeGroup(Group group){
        groups.remove(group);
        group.getVotings().remove(this);
    }



    public long getVotingId() {
        return votingId;
    }

    public void setVotingId(long votingId) {
        this.votingId = votingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public VotingType getVotingType() {
        return votingType;
    }

    public void setVotingType(VotingType votingType) {
        this.votingType = votingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<VotingOption> getOptions() {
        return options;
    }

    public void setOptions(List<VotingOption> options) {
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
