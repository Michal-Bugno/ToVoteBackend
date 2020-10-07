package net.tovote.enums;

public enum VotingType {
    FIRST_PAST_THE_POST("First past the post"),
    MULTIPLE_CHOICE("Multiple choice"),
    PREFERENTIAL("Preferential"),
    PROPORTIONAL("Proportional");

    private String name;

    VotingType(String name){
        this.name = name;
    }

    public static VotingType getVotingType(String typeName){
        if(typeName.equals(FIRST_PAST_THE_POST.name))
            return FIRST_PAST_THE_POST;
        if(typeName.equals(MULTIPLE_CHOICE.name))
            return MULTIPLE_CHOICE;
        if(typeName.equals(PREFERENTIAL.name))
            return PREFERENTIAL;
        if(typeName.equals(PROPORTIONAL.name))
            return PROPORTIONAL;
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
