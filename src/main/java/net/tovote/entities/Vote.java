package net.tovote.entities;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = "false")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Voting voting;

    @Column(name = "representation", nullable = "false")
    private String voteRepresentation;

    public Vote() {
    }

    public Vote(long id, Voting voting, String representation) {
        this.id = id;
        this.voting = voting;
        voteRepresentation = representation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }

    public String getVoteRepresentation() {
        return voteRepresentation;
    }

    public void setVoteRepresentation(String voteRepresentation) {
        this.voteRepresentation = voteRepresentation;
    }
}
