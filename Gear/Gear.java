
/**
 * Write a description of class Gear here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gear extends Items
{
    private String name;
    private int arm;
    private int manaBoost;
    private boolean BossCounter;
    public Gear(String Name, int armor)
    {
        name = Name;
        arm = armor;
    }

    public Gear(String Name, int armor, boolean counter)
    {
        name = Name;
        arm = armor;
        BossCounter = counter;
    }

    public Gear(String Name, int armor, int mana)
    {
        name = Name;
        arm = armor;
        manaBoost = mana;
    }
    
    public Gear(String Name, int armor, int mana, boolean counter)
    {
        name = Name;
        arm = armor;
        manaBoost = mana;
        BossCounter = counter;
    }

    public Gear(int mana, String Name)
    {
        name = Name;
        manaBoost = mana;
    }
    
    public boolean getCounter()
    {
        return BossCounter;
    }

    public String getName()
    {
        return name;
    }

    public int getMana()
    {
        return manaBoost;
    }

    public int getArm()
    {
        return arm;
    }

    public String getDescription()
    {
        return "Name: " + getName() + ", armor: " + getArm() + ", mana boost: "
        + getMana();
    }
}
