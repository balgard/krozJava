
/**
 * Write a description of class Spells here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spell extends Items
{
    private int dam;
    private int mana;
    private String name;
    private int arm;
    private int atk;
    private int turnsBurning;
    private int turnsFrozen;
    private int turnsStunned;
    private int turnsBuffed;
    private int turnsCast; //only enemy spells
    private int cooldown; //only warrior spells
    private boolean hasbeencast;
    public Spell(String Name, int damage, int ManaCost)
    {
        name = Name;
        dam = damage;
        mana = ManaCost;
    }

    public Spell(String Name, int turns, boolean cast)
    {
        name = Name; 
        turnsCast = turns;
        hasbeencast = cast;
    }

    public Spell(int armor, String Name, int manaCost)
    {
        arm = armor;
        name = Name;
        mana = manaCost;
    }

    public Spell(String Name, int damage, int ManaCost, int turns)
    {
        name = Name;
        dam = damage;
        mana = ManaCost;
        turnsFrozen = turns;
    }

    public Spell(int turns, String Name, int damage, int manaCost)
    {
        turnsBurning = turns;
        name = Name;
        mana = manaCost;
        dam = damage;
    }

    public Spell(int turns, int damage, String Name, int manaCost)
    {
        turnsStunned = turns;
        name = Name;
        mana = manaCost;
        dam = damage;
    }

    public Spell(int turns, int Atk, int manaCost, String Name)
    {
        turnsBuffed = turns;
        atk = Atk;
        mana = manaCost;
        name = Name;
    }

    public Spell(int Atk, String Name)
    {
        atk = Atk;
        name = Name;
    }

    public Spell(int damage, int turns, int Cooldown,int manaCost, String Name)
    {
        dam = damage;
        turnsStunned = turns;
        name = Name;
        cooldown = Cooldown;
        mana = manaCost;
    }

    public Spell(String Name)
    {
        name = Name;
    }

    public boolean getCast()
    {
        return hasbeencast;
    }

    public String getName()
    {
        return name;
    }

    public int getDamage()
    {
        return dam;
    }

    public int getMana()
    {
        return mana;
    }

    public int getArmor()
    {
        return arm;
    }

    public int getTurnsC()
    {
        return turnsCast;
    }

    public int getTurnsF()
    {
        return turnsFrozen;
    }

    public int getTurnsB()
    {
        return turnsBurning;
    }

    public int getTurnsS()
    {
        return turnsStunned;
    }

    public int getTurnsBu()
    {
        return turnsBuffed;   
    }

    public int getAtkBoost()
    {
        return atk;
    }

    public int getCooldown()    
    {
        return cooldown;
    }

    public void setCast()
    {
        if(hasbeencast == true)
        {
            hasbeencast = false;
        }
        else
        {
            hasbeencast = true;
        }
    }

    public String getDescriptionFireblast()
    {
        return "Name: " + getName() + ", damage: " + getDamage() + ", mana cost: "
        + getMana();
    }

    public String getDescriptionIcebolt()
    {
        return "Name: " + getName() + ", freezes for: " + getTurnsF() + " turns, damage: " + getDamage() + ", mana cost: "
        + getMana();
    }

    public String getDescriptionCombust()
    {
        return "Name: " + getName()  + ", burns for: " + getTurnsB() + " turns, damage per turn: " + getDamage() + ", mana cost: "
        + getMana();
    }

    public String getDescriptionLightning()
    {
        return "Name: " + getName()  + ", stuns for: " + getTurnsS() + " turns, damage: " + getDamage() + ", mana cost: "
        + getMana();
    }

    public String getDescriptionShield()
    {
        return "Name: " + getName()  + ", stuns for: " + getTurnsS() + " turns, damage: " + getDamage() + ", cooldown: " + getCooldown();
    }

    public String getDescriptionEnraged()
    {
        return "Name: " + getName() + ", damage boost: " + getAtkBoost() + ", active when under 75 health.";
    }

}
