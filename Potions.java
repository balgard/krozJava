
/**
 * Write a description of class Potions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Potions extends Items
{
    private int health;
    private int mana;
    private String Name;
    public Potions()
    {
        health = 50;
        Name = "Health Potion";
    }

    public Potions(String name, int Mana)
    {
        Name = name;
        mana = Mana;
    }

    public String getName()
    {
        return Name;
    }
    
    public int getMana()
    {
        return mana;
    }
    
    public int getHealth()
    {
        return health;
    }
}
