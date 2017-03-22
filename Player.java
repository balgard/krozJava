import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.awt.*;
/** 
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private int hp;
    private int atk;
    private int mana;
    private int armor = 0;
    private ArrayList<Potions> inventory;
    private Items[] equipped;
    private ArrayList<Spell> spellList;
    private String PlayerName;
    private int turnsFrozen;
    private int turnsBurning;
    private int turnsStunned;
    private int turnsBuffed;
    private int cooldown;
    private String Class;
    Scanner reader = new Scanner(System.in);
    public Player (String name, String CLASS)
    {
        PlayerName = name;
        Class = CLASS;
    }

    public String getClasses()
    {
        return Class;
    }

    public void ClassCreation()
    {
        if((Class.equals("Mage")) || (Class.equals("mage")))
        {
            hp = 100; 
            atk = 5;
            mana = 200;
            spellList = new ArrayList<Spell>();
            inventory = new ArrayList<Potions>();
            inventory.add(new Potions());
            inventory.add(new Potions("Mana Potion", 50));
            equipped = new Items[3];
            equipped[0] = new Gear("Spell Tome", 0, 200);
            equipped[1] = new Gear("Robe", 10, 100);
            equipped[2] = new Weapon("Staff", 100, 5);
            spellList.add(new Spell("Fireblast", 40, 30));
            spellList.add(new Spell("Icebolt", 5, 30, 3));
            spellList.add(new Spell(3, "Combust", 10, 30));
            spellList.add(new Spell(3, 10, "Lightning Shock", 40));
        }
        else if((Class.equals("Warrior")) || (Class.equals("warrior")))
        {
            hp = 150;
            atk = 10;
            inventory = new ArrayList<Potions>();
            inventory.add(new Potions());
            inventory.add(new Potions());
            inventory.add(new Potions());
            spellList = new ArrayList<Spell>();
            spellList.add(new Spell(20, "Enraged"));
            spellList.add(new Spell(5, 2, 4, 0, "Shield Slam"));
            equipped = new Items[3];
            equipped[0] = new Gear("Shield", 10);
            equipped[1] = new Gear("Chain Mail", 50);
            equipped[2] = new Weapon("Sword", 20);
        }            
        /**else if(Class.equals("Paladin"))
        {
        hp = 150;
        atk = 10;
        mana = 100;
        inventory = new Items[10];
        inventory[0] = new Potions();
        inventory[1] = new Potions("Mana Potion", 50);
        for(int i = 2; i < inventory.length; i++)
        {
        inventory[i] = null;
        }
        equipped = new Items[3];
        equipped[0] = new Gear("Holy Shield", 20, 25);
        equipped[1] = new Gear("Holy Vestments", 75, 100);
        equipped[2] = new Weapon("Mace", 15);
        }
        else if(Class.equals("Thief"))
        {
        hp = 75;
        atk = 5;
        mana = 200;
        armor = 0;
        inventory = new Items[15];
        inventory[0] = new Potions();
        inventory[1] = new Potions();
        for(int i = 2; i < inventory.length; i++)
        {
        inventory[i] = null;
        }
        equipped = new Items[3];
        equipped[0] = new Gear("Rabbit's Foot", 5);
        equipped[1] = new Gear("Leather Armor", 50);
        equipped[2] = new Weapon("Dagger", 10);
        }*/
        else if(Class.equals("NPC"))
        {
            hp = 1000000;
            atk = 1000000;
            mana = 1000000;
            armor = 1000000;
        }
    }

    public void setTrueAtk(int Atk)
    {
        atk = Atk;
    }
    
    public void setEquipment(Gear NewGear, int replaced)
    {
        equipped[replaced] = NewGear;
    }

    public void setTrueHp(int Hp)
    {   
        hp = Hp;
    }
    
    public void setTrueMana(int Mana)
    {
        mana = Mana;
    }
    
    public void setHp(int HpIncrease)
    {
        hp = getHp() + HpIncrease;
    }

    public void setAtk(int AtkIncrease)
    {
        atk = getAtk() + AtkIncrease;
    }

    public void setMana(int ManaIncrease)
    {
        mana = mana + ManaIncrease;
    }

    public void setArmor(int Armor)
    {
        armor = armor + Armor;
    }

    public int getBurning()
    {
        return turnsBurning;
    }

    public void setBurning(int turns)
    {
        turnsBurning = turns;
    }

    public void reduceTurnsBurning()
    {
        turnsBurning --;
    }

    public int getFrozen()
    {
        return turnsFrozen;
    }

    public void setFrozen(int turns)
    {
        turnsFrozen = turns;
    }

    public void reduceTurnsFrozen()
    {
        turnsFrozen --;
    }

    public int getStunned()
    {
        return turnsStunned;
    }

    public void setStunned(int turns)
    {
        turnsStunned = turns;
    }

    public void reduceTurnsStunned()
    {
        turnsStunned --;
    }

    public void setStats(int Hp, int Atk, int Mana)
    {
        setHp(Hp);
        setAtk(Atk);
        setMana(Mana);
    }

    public int getHp()
    {
        return hp;
    }

    public int getMana()
    {
        return mana;
    }

    public int getAtk()
    {
        return atk;
    }

    public ArrayList<Spell> getSpellList()
    {
        return spellList;
    }

    public ArrayList<Potions> getInventoryList()
    {
        return inventory;
    }

    public String getSpells()
    {
        String spells;
        spells = "Abilities: ";
        for (int i = 0; i < spellList.size(); i++)
        {
            if(i == 0)
            {
                spells += spellList.get(i).getName();
            }
            else 
            {
                spells += ", " + spellList.get(i).getName();
            }
        }
        return spells;
    }

    public String getSpellsDescriptionsM()
    {
        String spells;
        spells = "Abilities: " + "\n";
        for (int i = 0; i < spellList.size(); i++)
        {
            if(i == 0)
            {
                spells += spellList.get(i).getDescriptionFireblast() +"\n";
            }
            else if (i == 1)
            {
                spells += spellList.get(i).getDescriptionIcebolt()  +"\n";
            }
            else if (i == 2)
            {
                spells += spellList.get(i).getDescriptionCombust()  +"\n";
            }
            else if (i == 3)
            {
                spells += spellList.get(i).getDescriptionLightning()  +"\n";
            }
        }
        return spells;
    }

    public String getSpellsDescriptionsW()
    {
        String spells;
        spells = "Abilities: " + "\n";
        for (int i = 0; i < spellList.size(); i++)
        {
            if(i == 0)
            {
                spells += spellList.get(i).getDescriptionEnraged()  +"\n";
            }
            else if(i == 1)
            {
                spells += spellList.get(i).getDescriptionShield()  +"\n";
            }
        }
        return spells;
    }

    public String getEquippedDescriptions()
    {
        String InventoryList;
        InventoryList = "Equipped Gear: " +"\n";
        for(int i = 0; i < 3; i ++)
        {
            InventoryList += equipped[i].getDescription() + "\n";
        }
        return InventoryList;
    }

    public String getEquipped()
    {
        String InventoryList;
        InventoryList = "";
        for(int i = 0; i < 3; i ++)
        {
            if(equipped[i] instanceof Weapon)
            {
                if(i == 0)
                {
                    InventoryList += equipped[i].getName();
                }
                else
                {
                    InventoryList += ", " + equipped[i].getName();
                }
            }
            else if (equipped[i] instanceof Gear)
            {
                if(i == 0)
                {
                    InventoryList += equipped[i].getName();
                }
                else
                {
                    InventoryList += ", " + equipped[i].getName();
                }
            }
            else
            {
                InventoryList += ".";
            }
        }
        return InventoryList;
    }

    public Items[] getEquipmentList()
    {
        Items[] temp = new Items[3];
        for(int i = 0; i < equipped.length; i ++)
        {
            temp[i] = equipped[i];
        }
        return temp;
    }

    public String getInventory()
    {
        String Inventory;
        Inventory = "";
        for(int i = 0; i < inventory.size(); i++)
        {
            if(i == 0)
            {
                Inventory +=inventory.get(i).getName();
            }
            else
            {
                Inventory += ", " + inventory.get(i).getName();
            }
        }
        return Inventory;
    }

    public int getDam()
    {
        return equipped[2].getDamage();
    }

    public int getGearManaBoost()
    {
        return equipped[0].getMana();
    }

    public int getArmorManaBoost()
    {
        return equipped[1].getMana();
    }

    public int getWeaponManaBoost()
    {
        return equipped[2].getMana();
    }

    public int getTotalManaBoost()
    {
        int mana = 0;
        for(int i = 0; i < 3; i++)
        {
            mana += equipped[i].getMana();
        }
        return mana;
    }

    public int getArmor()
    {
        int arm = 0;
        for(int i = 0; i < 3; i ++)
        {
            arm += equipped[i].getArm();
        }
        return arm;
    }

    public String getCurrentStats()
    {
        String str = "Health: " + getHp() +  " Attack: " +
            getAtk() + " Armor: " + getArmor() 
            + " Mana: " + getMana();
        return str;
    }

    public double getDamageReduction()
    {
        double x =  getArmor()/2;
        double y =  x/100;
        return 1.00- y;
    }

    public String getName()
    {
        return PlayerName;
    }

    public String[][] get2dSpellList()
    {
        String[][] x = new String[2][spellList.size()];
        for(int i = 0; i < spellList.size(); i++)
        {
            x[0][i] = spellList.get(i).getName();
        }
        for(int j = 0; j < spellList.size(); j++)
        {
            String str = "[" + j + "]";
            x[1][j] = str;
        }
        return x;
    }

    public String get2dSpellsM()
    {
        String x = "";
        for(int i = 0; i < spellList.size(); i++)
        {
            x += "[" + spellList.get(i).getName() + "] ";
        }
        System.out.println(x);
        x = "";
        for(int j = 0; j < spellList.size(); j++)
        {
            String str = "";
            if(j == 0)
            {
                str += "    ";
            }
            else if(j == 1)
            {
                str += "       ";
            }
            else if(j == 2)
            {
                str += "      ";
            }
            else if (j == 3)
            {
                str += "          ";
            }
            str += "[" + j + "] ";
            x += str;
        }
        return x;
    }

    public String get2dSpellsW()
    {
        String x = "";
        for(int i = 0; i < spellList.size(); i++)
        {
            x += "[" + spellList.get(i).getName() + "] ";
        }
        System.out.println(x);
        x = "";
        for(int j = 0; j < spellList.size(); j++)
        {
            String str = "";
            if(j == 0)
            {
                str += "";
            }
            else if(j == 1)
            {
                str += "      ";
            }
            else if(j == 2)
            {
                str += "      ";
            }
            else if (j == 3)
            {
                str += "          ";
            }
            if(j == 0)
            {
                str += "[Passive]";
            }
            else
            {
                str += "[" + j + "] ";
            }
            x += str;
        }
        return x;
    }

    public String get2dSpellsMTutorial()
    {
        String x = "";
        for(int i = 0; i < 1; i++)
        {
            x += "[" + spellList.get(i).getName() + "] ";
        }
        System.out.println(x);
        x = "";
        for(int j = 0; j < 1; j++)
        {
            String str = "";
            str += "    ";
            str += "[" + j + "] ";
            x += str;
        }
        return x;
    }

    public String getCLass()
    {
        return Class;
    }

    public int getBuffed()
    {
        return turnsBuffed;
    }

    public void setBuffed(int turns)
    {
        turnsBuffed = turns;
    }

    public void reduceTurnsBuffed()
    {
        turnsBuffed --;
    }

    public int getCooldown()
    {
        return cooldown;
    }

    public void setCd(int turns)
    {
        cooldown = turns;
    }

    public void reduceCooldown()
    {
        cooldown --;
    }
}