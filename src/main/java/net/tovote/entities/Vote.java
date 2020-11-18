package net.tovote.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

   // @JsonIgnore
    //@ManyToOne(fetch = FetchType.LAZY)
    //private Voting voting;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "representation", nullable = false)
    private String voteRepresentation;

    public Vote() {
    }

    public Vote(long id, Voting voting, String representation, User user) {
        this.id = id;
        //this.voting = voting;
        voteRepresentation = representation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //public Voting getVoting() {
    //    return voting;
  //  }

  //  public void setVoting(Voting voting) {
   //     this.voting = voting;
  //  }

    public String getVoteRepresentation() {
        return voteRepresentation;
    }

    public void setVoteRepresentation(String voteRepresentation) {
        this.voteRepresentation = voteRepresentation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
