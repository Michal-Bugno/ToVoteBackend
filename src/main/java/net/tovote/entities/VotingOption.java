package net.tovote.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "voting_options")
public class VotingOption {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "option_number")
    private int optionNumber;

    @Column(name = "name")
    private String name;

    public VotingOption(){

    }

    public VotingOption(long votingOptionId, String name, int optionNumber, Voting voting) {
        this.name = name;
        this.optionNumber = optionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(int optionNumber) {
        this.optionNumber = optionNumber;
    }
}
