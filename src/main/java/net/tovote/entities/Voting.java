package net.tovote.entities;

import net.tovote.enums.VotingType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "votings")
public class Voting {

    @Id
    @GeneratedValue
    @Column(name = "voting_id", nullable = false)
    private long votingId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "start_time_stamp", nullable = false)
    private long startTimeStamp;

    @Column(name = "end_time_stamp", nullable = true)
    private long endTimeStamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "voting_type", length = 25, nullable = false)
    private VotingType votingType;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "id")

    private List<VotingOption> options;

    @ManyToMany(targetEntity = Group.class, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "votings_groups",
            joinColumns = @JoinColumn(name = "voting_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    public Voting(){

    }

    public Voting(long id, User user, long startTimeStamp, long endTimeStamp, VotingType votingType, String description, List<VotingOption> options) {
        votingId = id;
        this.user = user;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.votingType = votingType;
        this.description = description;
        this.options = options;
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
}
