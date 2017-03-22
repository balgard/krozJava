import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.awt.*;
/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy
{
    private int hp;
    private int atk;
    private int mana; 
    private int armor = 0;
    private int turnsFrozen;
    private int turnsBurning;
    private int turnsStunned;
    private int turnsBuffed;
    private int turnsCasting;
    private String type;
    private ArrayList<Spell> spellList;
    private String EnemyType;
    Scanner reader = new Scanner(System.in);

    public Enemy(String name, int health, int Atk, int Mana, int Armor, ArrayList<Spell> SpellList)
    {
        hp = health;
        atk = Atk;
        mana = Mana;
        armor = Armor;
        spellList = SpellList;
        EnemyType = name;
    }

    public String getType()
    {
        return type;
    }

    public int getHealth()
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

    public int getArmor()
    {
        return armor;
    }

    public String getName()
    {
        return EnemyType;
    }

    public int getTurnsC()
    {
        return turnsCasting;
    }

    public void setCasting(int turns)
    {
        turnsCasting = turns;
    }

    public void reduceTurnsCasting()
    {
        turnsCasting --;
    }

    public String getCurrentStats()
    {
        String str = "Name: " + getName() + " Health: " + getHealth() +  " Attack: " +
            getAtk() + " Armor: " + getArmor() 
            + " Mana: " + getMana();
        return str;
    }   

    public void setHp(int HpIncrease)
    {
        hp = getHealth() + HpIncrease;
    }

    public void setAtk(int AtkIncrease)
    {
        atk = getAtk() + AtkIncrease;
    }

    public void setMana(int ManaIncrease)
    {
        mana = mana + ManaIncrease;
    }

    public void setArmor(int Armor)//fix
    {
        armor = armor + Armor;
    }

    public int getFrozen()
    {
        return turnsFrozen;
    }

    public void reduceTurnsFrozen()
    {
        turnsFrozen --;
    }

    public void setBurning(int turns)
    {
        turnsBurning = turns;
    }

    public int getBurning()
    {
        return turnsBurning;
    }

    public void reduceTurnsBurning()
    {
        turnsBurning --;
    }

    public void setFrozen(int turns)
    {
        turnsFrozen = turns;
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

    public double getDamageReduction()
    {
        double x =  getArmor()/2;
        double y =  x/100;
        return 1.00- y;
    }

}
