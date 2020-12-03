package net.tovote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.tovote.enums.VotingType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "votings")
public class Voting {

    @Id
    @GeneratedValue
    @Column(name = "voting_id", nullable = false)
    private long votingId;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "start_time_stamp")
    private long startTimeStamp;

    @Column(name = "end_time_stamp")
    private long endTimeStamp;

    @Column(name = "explicit")
    private boolean explicit;

    @Column(name = "winning_options_number")
    private int winningOptionsNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "voting_type", length = 25, nullable = false)
    private VotingType votingType;

    @Column(name = "name")
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "voting_id")
    private Set<VotingOption> options;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "voting_id")
    private Set<Vote> votes;

    @JsonIgnore
    @ManyToMany(targetEntity = Group.class, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "votings_groups",
            joinColumns = @JoinColumn(name = "voting_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    public Voting(){

    }

    public Voting(long id, User user, long startTimeStamp, long endTimeStamp, VotingType votingType, String description, Set<VotingOption> options, Set<Vote> votes, Set<Group> groups, String name, int winningOptionsNumber) {
        votingId = id;
        this.user = user;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.votingType = votingType;
        this.description = description;
        this.options = options;
        this.groups = groups;
        this.name = name;
        this.votes = votes;
        this.winningOptionsNumber = winningOptionsNumber;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public void addGroup(Group group){
        groups.add(group);
        group.getVotings().add(this);
    }

    public void removeGroup(Group group){
        groups.remove(group);
        group.getVotings().remove(this);
    }

    public int getWinningOptionsNumber() {
        return winningOptionsNumber;
    }

    public void setWinningOptionsNumber(int winningOptionsNumber) {
        this.winningOptionsNumber = winningOptionsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voting voting = (Voting) o;
        return votingId == voting.votingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(votingId);
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
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

    public Set<VotingOption> getOptions() {
        return options;
    }

    public void setOptions(Set<VotingOption> options) {
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
