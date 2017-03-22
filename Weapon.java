
/**
 * Write a description of class Weapons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weapon extends Items
{
    private int dam;
    private String name;
    private int manaBoost;
    public Weapon(String Name, int damage)
    {
        name = Name;
        dam = damage;
    }

    public Weapon(String Name, int mana, int damage)
    {
        name = Name;
        manaBoost = mana;
        dam = damage;
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
        return manaBoost;
    }

    public String getDescription()
    {
        return "Name: " + getName() + ", damage: " + getDamage() + ", mana boost: "
         + getMana();
    }
}
