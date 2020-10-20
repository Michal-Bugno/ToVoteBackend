package net.tovote.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "voting_options")
public class VotingOption {

    @Id
    @GeneratedValue
    @Column(name = "voting_option_id", nullable = false)
    private long votingOptionId;

    @Column(name = "option_number")
    private int optionNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Voting voting;

    @Column(name = "name")
    private String name;

    public VotingOption(){

    }

    public VotingOption(long id, String name, int optionNumber) {
        votingOptionId = id;
        this.name = name;
        this.optionNumber = optionNumber;
    }

    public long getId() {
        return votingOptionId;
    }

    public void setId(long id) {
        votingOptionId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
