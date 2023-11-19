package by.teachmeskills.lesson24;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuperHeroCard {

    private String name;
    private String imgName;

    private int spiderHandSkill;
    private int pumpkinSkill;
    private int sunSkill;

    private int intelligenceProperty;
    private int strengthSkillsProperty;
    private int speedAgilitySkillsProperty;
    private int specialSkillsProperty;
    private int fightingSkillsProperty;

    private String description;
}
