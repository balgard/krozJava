import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import TurtleGraphics.*;
/**
 * Write a description of class Kroz here.
 * 
 * @author Brendan Algard
 * @version (Date)
 */
public class Kroz
{
    private static String[][] Cleared;
    private static BoundedGrid<Block> Map;
    private static Player One;
    private static Scanner reader = new Scanner(System.in);
    private static ArrayList<Spell> spellList;
    private static ArrayList<Potions> inventory;
    private static Items[] EquipmentList;
    private static Color color;
    private static double restore;
    private static int maxHp;
    private static int maxMana;
    private static BlockDisplay display;
    private static Location currentloc;
    private static Block currentsetting;
    private static String Name;
    private static String CLASS;
    public static void main(String [] args)
    {
        currentloc = new Location(0,0);
        Map = new BoundedGrid<Block>(1,6);
        for(int j = 0; j < Map.getNumRows(); j++)
        {
            for (int i = 0; i < Map.getNumCols(); i++)
            {
                Location loc = new Location(j,i,false);
                Block b = new Block();
                b.putSelfInGrid(Map, loc);
                b.setColor(Color.RED);
            }
        }
        System.out.println("As you enter the ancient kingdom of Ecclesia, a dark figure approaches you,");
        System.out.println("Once it reaches you you see that it towers over you,\nAs you tremble in terror the figure asks you,");
        System.out.println("\"What are you, a Warrior or a Mage?\"");
        System.out.print("Name: ");
        Naming();
        System.out.println("Classes: Warrior, Mage");
        System.out.print("Class: ");
        setClass();
        One = new Player(Name, CLASS);
        One.ClassCreation();
        if(One.getHp() == 0)
        {
            return;
        }
        EquipmentList = One.getEquipmentList();
        System.out.println("");
        System.out.print('\u000C');
        System.out.println("Character Base Stats: ");
        System.out.println("Health-" + One.getHp() + "\n" + "Attack-" + One.
            getAtk() + "\n" + "Equipped- " + 
            One.getEquipped() + "\n" + "Inventory- " + One.getInventory()); 
        One.setAtk(One.getDam());
        One.setMana(One.getTotalManaBoost());
        System.out.println("Stats with Gear- " + One.getCurrentStats());
        spellList = One.getSpellList();
        System.out.println(One.getSpells());
        inventory = One.getInventoryList();
        restore = 0.00;
        maxHp = One.getHp();
        maxMana = One.getMana();
        if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
        {
            restore = One.getHp() * .08; 
        }
        else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
        {
            restore = One.getHp() * .08; 
        }
        String answer = "";
        try { Thread.sleep(1000); } catch(Exception e) {}
        System.out.println("Press Enter to Continue");
        answer = reader.nextLine();
        if(answer.equals(""))
        {
            System.out.print('\u000C');
            String str = "The sacred land of Ecclesia, a land of prosper and freedom.\nThe hope of Humanity.  Its ruler just, and his rule indefinite.\nBut no kingdom can truly last forever.\nA darkness has fallen over Ecclesia, and with it came the Giants.\nThe Giants came from the sea and waged war against Ecclesia and its people.\nAnd as the light faded, so did Ecclesia.\nA hundred years past,  Ecclesia, a kingdom ruled by Giants,\nposes the greatest threat Humanity has ever faced,\nit is time for a hero to save her.";            
            System.out.println(str);
            try { Thread.sleep(1000); } catch(Exception e) {}
            System.out.println("");
            System.out.println("Press Enter To Continue");
            answer =reader.nextLine();
            if(answer.equals(""))
            {
                TutorialBattle();
            }
        }
        TutorialBattle();
    }

    private static void Naming()
    {
        Name = reader.nextLine();
        if(Name.equals("Quit"))
        {
            System.exit(0);
        }
        else if(Name.equals(""))
        {
            System.out.print("Please Enter a valid name: ");
            Naming();
        }
    }

    private static void setClass()
    {
        CLASS = reader.nextLine();
        if(CLASS.equals("Quit"))
        {
            System.exit(0);
        }
        else if((!CLASS.equals("Mage")) && (!CLASS.equals("Warrior") && (!CLASS.equals("mage")) && (!CLASS.equals("warrior"))))
        {
            System.out.print("Please enter a valid class: ");
            setClass();
        }
    }

    private static void drawMapTETRIS()
    {
        display = new BlockDisplay(Map);
        BoundedGrid<Block> Temp = new BoundedGrid<Block>(1,6);
        for(int r = 0; r < Map.getNumRows(); r++)
        {
            for (int c = 0; c < Map.getNumCols(); c++)
            {
                Location loc = new Location(r,c);
                Block b = Map.get(loc);
                b.setColor(Color.RED);
                loc = b.getLocation();
                if(loc.equals(currentloc) == true)
                {
                    b.setColor(Color.BLUE);
                }
                else if ((loc.getRow() == 0) && (loc.getCol() == 3))
                {
                    b.setColor(Color.GRAY);
                }
                else if ((loc.getRow() == 0) && (loc.getCol() == 5))
                {
                    b.setColor(Color.BLACK);
                }
                else if(loc.getCleared() == true)
                {
                    b.setColor(Color.GREEN);
                }

                b.putSelfInGrid(Temp, loc);
            }
        }
        display.showBlocks();
        System.out.println("Key: \nGreen = Cleared \nRed = Unexplored \nBlue = Current Location \nGray = Mini-Boss \nBlack = Boss");
    }

    private static void TutorialBattle()
    {
        currentloc = new Location(0,0);
        currentsetting = Map.get(currentloc);
        currentloc = currentsetting.getLocation();
        if(currentloc.getCleared() == false)
        {
            String answer;
            //insert description of tutorial room here
            System.out.println("Are you ready for your first fight?");
            System.out.println("Yes");
            System.out.println("No");
            answer = reader.nextLine();
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                System.out.print('\u000C');
                Enemy enemy = new Enemy("Training Dummy", 100, 4,0,0,null);
                int tempAttack = enemy.getAtk();
                int playerAttack = One.getAtk();
                int halfhealth = maxHp / 2;
                while((enemy.getHealth() > 0) && (One.getHp() > 0))
                {  
                    int tempHealth = One.getHp();
                    int enemyHealth = enemy.getHealth();
                    if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < halfhealth && One.getAtk() == playerAttack)
                        {
                            One.setAtk(spellList.get(0).getAtkBoost());
                        }
                    }
                    if(One.getFrozen() > 0)
                    {
                        One.setHp(-1 * enemy.getAtk());
                    }
                    if(enemy.getFrozen() > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(enemy.getStunned()  > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(One.getStunned() > 0)//balance
                    {
                        One.setHp(-1 * enemy.getAtk());
                    }
                    System.out.print('\u000C');
                    System.out.println("Your stats: " + One.getCurrentStats());
                    if(One.getFrozen() > 0)
                    {
                        System.out.println(One.getName() + " is frozen for "+ One.getFrozen() + " turns.");
                    }
                    if(One.getCooldown() > 0)
                    {
                        System.out.println(spellList.get(1).getName() +  " is on cooldown for " + One.getCooldown() + " more turns.");
                    }
                    if(One.getStunned() > 0)
                    {
                        System.out.println(One.getName() + " is stunned for " + One.getStunned() + " turns.");
                    }
                    System.out.println("");
                    System.out.println("Enemy stats: " + enemy.getCurrentStats());
                    if(enemy.getFrozen() > 0)
                    {
                        System.out.println(enemy.getName() + " is frozen for " + enemy.getFrozen() + " turns.");
                    }
                    if(enemy.getBurning() > 0)
                    {
                        System.out.println(enemy.getName() + " is burning for " + enemy.getBurning() + " turns, taking "
                            + spellList.get(2).getDamage() + " damage per turn.");
                    }
                    if(enemy.getStunned() > 0)
                    {
                        System.out.println(enemy.getName() + " is stunned for " + enemy.getStunned() + " turns.");
                    }
                    System.out.println("");
                    if((One.getStunned() == 0) && (One.getFrozen() == 0))
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("A: Attack Enemy");
                        System.out.println("B: Use an ability");
                        System.out.println("C: Use an item");
                        answer = reader.nextLine();
                        double enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                        if((answer.equals("A"))  || (answer.equals("a")))
                        {
                            if(enemy.getStunned() > 0) //balance
                            {
                                enemy.setHp(-1 * One.getAtk() - 2);
                                enemy.setStunned(0);
                                enemy.setAtk(tempAttack);
                            }
                            else
                            {
                                enemy.setHp(-1 * One.getAtk());
                                if(enemy.getHealth() > 0)
                                {
                                    One.setHp((int)enemyDamage);
                                }
                            }
                        }
                        else if ((answer.equals("B"))  || (answer.equals("b")))
                        {
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                int counter = 0;
                                String temp = "";
                                System.out.println(One.get2dSpellsMTutorial());
                                for(int i = 0; i < spellList.size(); i ++)
                                {
                                    if(One.getMana() < spellList.get(i).getMana())
                                    {
                                        temp += spellList.get(i).getName();
                                        counter ++;
                                    }
                                }
                                if(counter == 0)
                                {
                                    System.out.println("You can cast all spells this turn.");
                                }
                                else
                                {
                                    System.out.println("You don't have enough mana to cast " + temp + " this turn");
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                System.out.println(One.get2dSpellsW());
                            }
                            System.out.println("What ability do you want to use?");
                            answer = reader.nextLine();
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                if(answer.equals("0"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(0).getDamage());
                                        One.setMana(-1 * spellList.get(0).getMana());
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("That is not an ability.");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                if((answer.equals("1") && (One.getCooldown() == 0)))
                                {
                                    enemy.setHp(-1 * spellList.get(1).getDamage());
                                    enemy.setStunned(spellList.get(1).getTurnsS());
                                    One.setCd(spellList.get(1).getCooldown());
                                }
                            }
                        }
                        else if ((answer.equals("C"))  || (answer.equals("c")))
                        {
                            int temp = -1;
                            for (int i  = 0; i < inventory.size(); i ++)
                            {
                                System.out.println(inventory.get(i).getName() + "- ["  + i + "]");
                            }
                            System.out.println("What potion do you want to drink?");
                            answer = reader.nextLine();
                            if(answer.equals("0"))
                            {       
                                String tempName = inventory.get(0).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());                      
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                One.setHp((int)enemyDamage);
                            }
                            else if(answer.equals("1"))
                            {
                                String tempName = inventory.get(1).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                One.setHp((int)enemyDamage);
                            }
                            else if(answer.equals("2"))
                            {
                                String tempName = inventory.get(2).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                One.setHp((int)enemyDamage);
                            }
                            else if(answer.equals("3"))
                            {
                                String tempName = inventory.get(3).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                One.setHp((int)enemyDamage);
                            }
                        }
                        else if(answer.equals("scripting"))
                        {
                            enemy.setHp(-1 * enemy.getHealth());
                        }
                        else if (answer.equals("q"))
                        {
                            System.exit(0);
                        }
                    }
                    else
                    {
                        if(One.getStunned() > 0)
                        {
                            System.out.println("You are stunned and can't do anything this turn.");
                        }             
                        else if (One.getFrozen() > 0)
                        {
                            System.out.println("You are frozen and can't do anything this turn.");
                        }
                    }
                    if((One.getHp() != tempHealth) || (enemy.getHealth() != enemyHealth)) 
                    {
                        if((enemy.getFrozen() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if((enemy.getFrozen() > 0))
                        {
                            enemy.reduceTurnsFrozen();
                        }
                        if((One.getFrozen() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getFrozen() > 0))
                        {
                            One.reduceTurnsFrozen();
                        }
                        if(enemy.getBurning() > 0)
                        {
                            enemy.setHp(-1 * spellList.get(2).getDamage());
                            enemy.reduceTurnsBurning();
                        }
                        if((enemy.getStunned() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if(enemy.getStunned() > 0)
                        {
                            enemy.reduceTurnsStunned();
                        }
                        if((One.getStunned() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getStunned() > 0))
                        {
                            One.reduceTurnsStunned();
                        }
                        if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                        {
                            if(One.getHp() > halfhealth && One.getAtk() < playerAttack)
                            {
                                One.setTrueAtk(playerAttack);
                            }
                        }
                        if(One.getCooldown() > 0)
                        {
                            One.reduceCooldown();
                        }
                        int healthchange = tempHealth - One.getHp();
                        int healthchangee = enemyHealth - enemy.getHealth();
                        int damchange = One.getAtk() - playerAttack;
                        int damchangee = enemy.getAtk() - tempAttack;
                        System.out.println("This turn you took " + healthchange + " damage and your damage changed by " + damchange);
                        System.out.println("This turn your enemy took " + healthchangee + " damage and their damage changed by " + damchangee);
                    }
                    try { Thread.sleep(3000); } catch(Exception e) {}
                }
                if(enemy.getHealth() > 0)
                {
                    System.out.println("You Lost");
                    System.out.println("Game Over");
                    System.exit(0);
                }
                else if (One.getHp() > 0)
                {
                    One.setTrueAtk(playerAttack);
                    System.out.println("You won");
                    try { Thread.sleep(1000); } catch(Exception e) {}
                    currentloc.setCleared();
                    currentsetting.putSelfInGrid(Map, currentloc);
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < 150)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        if(One.getHp() < 100)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    if(One.getHp() > maxHp)
                    {
                        One.setTrueHp(maxHp);
                    }
                    inventory.add(new Potions());
                }
            }
            else if (answer.equals("q"))
            {
                System.exit(0);
            }
            else
            {
                System.out.print('\u000C');
                TutorialBattle();
            }
        }
        else
        {
            System.out.println("You see the training dummy you completely destroyed and can't help but feel slightly bad for it.");           
        }
        System.out.print('\u000C');
        afterTutorialBattle();
    }

    private static void afterTutorialBattle()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        System.out.print('\u000C');
        System.out.println("With the dummy defeated, you prepare to move on to the next room.");  
        //insert scene of tutorialbattle here
        System.out.println("(Map, Inventory, Equipment, Stats, Abilities, Quit, and Continue)\nWhat do you want to do?"); 
        System.out.println("After entering in what you want to do, press enter to return to this screen.");
        String answer = reader.nextLine();
        if(answer.equals("Map"))
        {
            System.out.print('\u000C');
            drawMapTETRIS();
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle();      
            }
        }
        else if(answer.equals("q"))
        {
            System.exit(0);
        }
        else if (answer.equals("Inventory"))
        {
            int counterh = 0;
            int counterm = 0;
            int innercounter = 0;
            System.out.println("");
            System.out.println(One.getInventory());
            for(int i = 0; i < inventory.size(); i++)
            {
                if(inventory.get(i).getName().equals("Health Potion"))
                {
                    counterh ++;
                }
                else if (inventory.get(i).getName().equals("Mana Potion"))
                {
                    counterm ++;
                }
            }
            if((counterm > 0) && (counterh > 0))
            {
                System.out.println("(Drink Health Potion, Drink Mana Potion)");
            }
            else if ((counterh > 0) && (counterm == 0))
            {
                System.out.println("(Drink Health Potion)");
            }
            else if ((counterh == 0) && (counterm > 0))
            {
                System.out.println("(Drink Mana Potion)");                
            }
            answer = reader.nextLine();
            if(answer.equals("Drink Health Potion"))
            {
                int healthchange = 0;
                int temphealth = One.getHp();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {   
                        if(inventory.get(i).getName().equals("Health Potion"))
                        {
                            One.setHp(inventory.get(i).getHealth());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getHp() > maxHp)
                {
                    One.setTrueHp(maxHp);
                }
                healthchange = One.getHp() - temphealth;
                System.out.println("You were healed for " + healthchange + ".");
                answer = reader.nextLine();

                if(answer.equals(""))
                {
                    afterTutorialBattle();
                }
            }
            else if (answer.equals("Drink Mana Potion"))
            {
                int manachange = 0;
                int tempmana = One.getMana();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {
                        if(inventory.get(i).getName().equals("Mana Potion"))
                        {
                            One.setMana(inventory.get(i).getMana());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getMana() > maxMana)
                {
                    One.setTrueMana(maxMana);
                }
                manachange = One.getMana() - tempmana;
                System.out.println("You gained " + manachange + " mana.");
                answer = reader.nextLine();

                if(answer.equals(""))
                {
                    afterTutorialBattle();                    
                }
            }
            else if(answer.equals(""))
            {
                afterTutorialBattle();      
            }       
        }
        else if (answer.equals("Stats"))
        {
            System.out.println("");
            System.out.println(One.getCurrentStats());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle();
            }
        }
        else if (answer.equals("Equipment"))
        {
            System.out.println("");
            System.out.println(One.getEquippedDescriptions());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle();      
            }
        }
        else if(answer.equals("Abilities"))
        {
            System.out.println("");
            if((One.getCLass().equals("Mage")) || (One.getCLass().equals("mage")))
            {
                System.out.println(One.getSpellsDescriptionsM());
            }
            else if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
            {
                System.out.println(One.getSpellsDescriptionsW());
            }
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle();      
            }
        }
        else if (answer.equals("Quit"))
        {
            System.out.println("Are you sure that you want to quit?");
            answer = reader.nextLine();
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                System.exit(0);
            }
            else
            {
                afterTutorialBattle();                
            }
        }
        else if (answer.equals("Continue"))
        {
            TutorialBattle2();
        }
        else 
        {
            afterTutorialBattle();
        }
    }

    private static void TutorialBattle2()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        currentloc = new Location(0,1);
        currentsetting = Map.get(currentloc);
        currentloc = currentsetting.getLocation();
        if(currentloc.getCleared() == false)
        {
            String answer;
            System.out.print('\u000C');
            System.out.println("Entering the next room you see another dummy, \nbut this one looks like he does steroids.");//goes offscreen
            System.out.println("");
            System.out.println("Beware some enemies can cast spells as well, \nthis one can buff his damage for a short time.");
            System.out.println("");
            System.out.println("Are you ready for the fight?");
            System.out.println("Yes");
            System.out.println("No");
            answer = reader.nextLine();            
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                ArrayList<Spell> spelllist = new ArrayList<Spell>();
                spelllist.add(new Spell(3, 10, 10,"Juice Up"));
                Enemy enemy = new Enemy("Buff Dummy", 200, 20, 50, 10, spelllist);
                int tempAttack = enemy.getAtk();
                int playerAttack = One.getAtk();
                int halfhealth = maxHp / 2;
                while((enemy.getHealth() > 0) && (One.getHp() > 0))
                {  
                    int tempHealth = One.getHp();
                    int enemyHealth = enemy.getHealth();
                    if(One.getFrozen() > 0)
                    {
                        One.setHp(-1 * enemy.getAtk());
                    }
                    if(enemy.getFrozen() > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(enemy.getStunned()  > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(One.getStunned() > 0)//balance
                    {
                        One.setHp(-1 * enemy.getAtk());
                    }
                    if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < halfhealth && One.getAtk() == playerAttack)
                        {
                            One.setAtk(spellList.get(0).getAtkBoost());
                        }
                    }
                    System.out.print('\u000C');
                    System.out.println("Your stats- " + One.getCurrentStats());
                    if(One.getFrozen() > 0)
                    {
                        System.out.println(One.getName() + " is frozen for " + One.getFrozen() + " turns.");
                    }
                    if(One.getCooldown() > 0)
                    {
                        System.out.println(spellList.get(1).getName() +  " is on cooldown for "  + One.getCooldown() + " more turns.");
                    }
                    System.out.println("");
                    System.out.println("Enemy stats- " + enemy.getCurrentStats());
                    if(enemy.getFrozen() > 0)
                    {
                        System.out.println(enemy.getName() + " is frozen for " + enemy.getFrozen() + " turns.");
                    }
                    if(enemy.getBurning() > 0)
                    {
                        System.out.println(enemy.getName() + " is burning for " + enemy.getBurning() + " turns, taking "
                            + spellList.get(2).getDamage() + " damage per turn.");
                    }
                    if(enemy.getStunned() > 0)
                    {
                        System.out.println(enemy.getName() + " is stunned for " + enemy.getStunned() + " turns.");
                    }
                    if(enemy.getBuffed() > 0)
                    {
                        System.out.println(enemy.getName() + " is buffed for " + enemy.getBuffed() + " turns.");
                    }
                    System.out.println("");
                    if((One.getStunned() == 0) && (One.getFrozen() == 0))
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("A: Attack Enemy");
                        System.out.println("B: Use an ability");
                        System.out.println("C: Use an potion");
                        answer = reader.nextLine();
                        double enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                        double playerDamage = -1 * One.getAtk() * enemy.getDamageReduction();
                        if((answer.equals("A"))  || (answer.equals("a")))
                        {
                            if(enemy.getStunned() > 0) //balance
                            {
                                enemy.setHp(-1 * One.getAtk() - 2);
                                enemy.setStunned(0);
                                enemy.setAtk(tempAttack);
                            }
                            else
                            {
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if ((answer.equals("B"))  || (answer.equals("b")))
                        {
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                System.out.println(One.get2dSpellsM());
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                System.out.println(One.get2dSpellsW());
                            }
                            System.out.println("What ability do you want to use?");
                            answer = reader.nextLine();
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                if(answer.equals("0"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(0).getDamage());
                                        One.setMana(-1 * spellList.get(0).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                        }
                                        else
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("1"))
                                {
                                    if(One.getMana() > spellList.get(1).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(1).getDamage());
                                        One.setMana(-1 * spellList.get(1).getMana());
                                        enemy.setFrozen(spellList.get(1).getTurnsF());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("2"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(2).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                        {
                                            enemy.setHp(-1);
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                        }
                                        else
                                        {
                                            enemy.setHp(-1);
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                        enemy.setBurning(spellList.get(2).getTurnsB());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("3"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(3).getMana());
                                        enemy.setHp(-1 * spellList.get(3).getDamage());
                                        enemy.setStunned(spellList.get(3).getTurnsS());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }                            
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                if((answer.equals("1")) && (One.getCooldown() == 0))
                                {
                                    enemy.setHp(-1 * spellList.get(1).getDamage());
                                    enemy.setStunned(spellList.get(1).getTurnsS());
                                    One.setCd(spellList.get(1).getCooldown());
                                }
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                        }
                        else if ((answer.equals("C"))  || (answer.equals("c")))
                        {
                            int temp = -1;
                            for (int i  = 0; i < inventory.size(); i ++)
                            {
                                System.out.println(inventory.get(i).getName() + " - ["  + i + "]");
                            }
                            System.out.println("What potion do you want to drink?");
                            answer = reader.nextLine();
                            if(answer.equals("0"))
                            {                        
                                String tempName = inventory.get(0).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();                                
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("1"))
                            {
                                String tempName = inventory.get(1).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("2"))
                            {
                                String tempName = inventory.get(2).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("3"))
                            {
                                String tempName = inventory.get(3).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    One.setHp((int)enemyDamage);
                                }
                                else
                                {
                                    One.setHp((int)enemyDamage);
                                }
                            }
                        }
                        else if(answer.equals("scripting"))
                        {
                            enemy.setHp(-1 * enemy.getHealth());
                        }
                        else if (answer.equals("q"))
                        {
                            System.exit(0);
                        }
                    }
                    else
                    {
                        if(One.getStunned() > 0)
                        {
                            System.out.println("You are stunned and can't do anything this turn.");
                        }             
                        else if (One.getFrozen() > 0)
                        {
                            System.out.println("You are frozen and can't do anything this turn.");
                        }
                    }
                    if((One.getHp() != tempHealth) || (enemy.getHealth() != enemyHealth)) 
                    {
                        if((enemy.getFrozen() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if((enemy.getFrozen() > 0))
                        {
                            enemy.reduceTurnsFrozen();
                        }
                        if((One.getFrozen() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getFrozen() > 0))
                        {
                            One.reduceTurnsFrozen();
                        }
                        if(enemy.getBurning() > 0)
                        {
                            enemy.setHp(-1 * spellList.get(2).getDamage());
                            enemy.reduceTurnsBurning();
                            enemy.setHp(1);
                        }
                        if((enemy.getStunned() == 0) &&(enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if(enemy.getStunned() > 0)
                        {
                            enemy.reduceTurnsStunned();
                        }
                        if((One.getStunned() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getStunned() > 0))
                        {
                            One.reduceTurnsStunned();
                        }
                        if(enemy.getBuffed() > 0)
                        {
                            enemy.reduceTurnsBuffed();   
                        }
                        if((enemy.getBuffed() == 0) && (enemy.getAtk() != tempAttack))
                        {
                            enemy.setAtk(-1 * spelllist.get(0).getAtkBoost()); 
                        }
                        if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                        {
                            if(One.getHp() > halfhealth && One.getAtk() < playerAttack)
                            {
                                One.setTrueAtk(playerAttack);
                            }
                        }
                        if(One.getCooldown() > 0)
                        {
                            One.reduceCooldown();
                        }
                        int healthchange = tempHealth - One.getHp();
                        int healthchangee = enemyHealth - enemy.getHealth();
                        int damchange = One.getAtk() - playerAttack;
                        int damchangee = enemy.getAtk() - tempAttack;
                        System.out.println("This turn you took " + healthchange + " damage and your damage changed by " + damchange);
                        System.out.println("This turn your enemy took " + healthchangee + " damage and their damage changed by " + damchangee);
                    }
                    try { Thread.sleep(2000); } catch(Exception e) {}
                }
                if(enemy.getHealth() > 0)
                {
                    System.out.println("You Lost");
                    System.out.println("Game over");
                    return;
                }
                else if (One.getHp() > 0)
                {
                    One.setTrueAtk(playerAttack);
                    System.out.println("You won");
                    try { Thread.sleep(1000); } catch(Exception e) {}
                    currentloc.setCleared();
                    currentsetting.putSelfInGrid(Map, currentloc);
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < 150)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        if(One.getHp() < 100)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    if(One.getHp() > maxHp)
                    {
                        One.setTrueHp(maxHp);
                    }
                }
            }
            else
            {
                System.out.print('\u000C');
                TutorialBattle2();
            }
        }
        else
        {
            System.out.println("You see the training dummy you completely destroyed and can't help but feel slightly bad for it.");                
        }
        //insert scene of tutorialbattle2 here
        afterTutorialBattle2();
    }

    private static void afterTutorialBattle2()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        System.out.print('\u000C');
        System.out.println("With the buff dummy defeated, you prepare to leave the inn,\nbefore you leave, though, \nthe mysterious challenger reminds you to always remember your training.");  
        //insert scene leaving tavern/inn where tutorialbattles happened here
        System.out.println("(Map, Inventory, Equipment, Stats, Abilities, Quit, Go Backwards, and Continue)\nWhat do you want to do?");  
        System.out.println("After entering in what you want to do, press enter to return to this screen.");
        String answer = reader.nextLine();
        if(answer.equals("Go Backwards"))
        {
            TutorialBattle();
        }
        else if(answer.equals("Map"))
        {
            System.out.print('\u000C');
            drawMapTETRIS();
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle2();      
            }
        }
        else if (answer.equals("Stats"))
        {
            System.out.println("");
            System.out.println(One.getCurrentStats());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle2();
            }
        }
        else if(answer.equals("q"))
        {
            System.exit(0);
        }
        else if (answer.equals("Inventory"))
        {
            int counterh = 0;
            int counterm = 0;
            int innercounter = 0;
            System.out.println("");
            System.out.println(One.getInventory());
            for(int i = 0; i < inventory.size(); i++)
            {
                if(inventory.get(i).getName().equals("Health Potion"))
                {
                    counterh ++;
                }
                else if (inventory.get(i).getName().equals("Mana Potion"))
                {
                    counterm ++;
                }
            }
            if((counterm > 0) && (counterh > 0))
            {
                System.out.println("(Drink Health Potion, Drink Mana Potion)");
            }
            else if ((counterh > 0) && (counterm == 0))
            {
                System.out.println("(Drink Health Potion)");
            }
            else if ((counterh == 0) && (counterm > 0))
            {
                System.out.println("(Drink Mana Potion)");                
            }
            answer = reader.nextLine();
            if(answer.equals("Drink Health Potion"))
            {
                int healthchange = 0;
                int temphealth = One.getHp();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {   
                        if(inventory.get(i).getName().equals("Health Potion"))
                        {
                            One.setHp(inventory.get(i).getHealth());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getHp() > maxHp)
                {
                    One.setTrueHp(maxHp);
                }
                healthchange = One.getHp() - temphealth;
                System.out.println("You were healed for " + healthchange + ".");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterTutorialBattle2();
                }
            }
            else if (answer.equals("Drink Mana Potion"))
            {
                int manachange = 0;
                int tempmana = One.getMana();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {
                        if(inventory.get(i).getName().equals("Mana Potion"))
                        {
                            One.setMana(inventory.get(i).getMana());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getMana() > maxMana)
                {
                    One.setTrueMana(maxMana);
                }
                manachange = One.getMana() - tempmana;
                System.out.println("You gained " + manachange + " mana.");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterTutorialBattle2();                    
                }
            }
            else if(answer.equals(""))
            {
                afterTutorialBattle2();      
            }       
        }
        else if (answer.equals("Equipment"))
        {
            System.out.println("");
            System.out.println(One.getEquippedDescriptions());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle2();      
            }    
        }
        else if(answer.equals("Abilities"))
        {
            System.out.println("");
            if((One.getCLass().equals("Mage")) || (One.getCLass().equals("mage")))
            {
                System.out.println(One.getSpellsDescriptionsM());
            }
            else if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
            {
                System.out.println(One.getSpellsDescriptionsW());
            }
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterTutorialBattle2();      
            }
        }
        else if (answer.equals("Quit"))
        {
            System.out.println("Are you sure that you want to quit?");
            answer = reader.nextLine();
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                System.exit(0);
            }
            else
            {
                afterTutorialBattle2();                
            }
        }
        else if (answer.equals("Continue"))
        {
            GiantBattle();
        }
        else 
        {
            afterTutorialBattle2();
        }
    }

    private static void GiantBattle()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        currentloc = new Location(0,2);
        currentsetting = Map.get(currentloc);
        currentloc = currentsetting.getLocation();
        if(currentloc.getCleared() == false)
        {
            String answer;
            System.out.print('\u000C');
            System.out.println("Leaving the Inn, you start your long and arduous journey to save Ecclesia.");
            //insert scene with first giant here
            System.out.println("You make your way to the towns' gates but are stopped by the towns' gatekeeper,\nwho tells you he has heard of your \"Quest\" and that he will stop you.");
            System.out.println("Are you ready for the fight?");
            System.out.println("Yes");
            System.out.println("No");
            answer = reader.nextLine();            
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                ArrayList<Spell> spelllist = new ArrayList<Spell>();
                spelllist.add(new Spell("Enkindle",10,20));
                Enemy enemy = new Enemy("Mage Giant", 180, 10, 100, 5, spelllist);
                int tempAttack = enemy.getAtk();
                int playerAttack = One.getAtk();
                int halfhealth = maxHp / 2;
                while((enemy.getHealth() > 0) && (One.getHp() > 0))
                {  
                    int tempHealth = One.getHp();
                    int enemyHealth = enemy.getHealth();
                    if(One.getFrozen() > 0)
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if(enemy.getFrozen() > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(enemy.getStunned()  > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(One.getStunned() > 0)//balance
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < halfhealth && One.getAtk() == playerAttack)
                        {
                            One.setAtk(spellList.get(0).getAtkBoost());
                        }
                    }

                    System.out.print('\u000C');
                    System.out.println("Your stats- " + One.getCurrentStats());
                    if(One.getFrozen() > 0)
                    {
                        System.out.println(One.getName() + " is frozen for " + One.getFrozen() + " turns.");
                    }
                    if(One.getCooldown() > 0)
                    {
                        System.out.println(spellList.get(1).getName() +  " is on cooldown for " + One.getCooldown() + " more turns.");
                    }
                    if(One.getStunned() > 0)
                    {
                        System.out.println(One.getName() + " is stunned for " + One.getStunned() + " turns.");
                    }
                    System.out.println("");
                    System.out.println("Enemy stats- " + enemy.getCurrentStats());
                    if(enemy.getFrozen() > 0)
                    {
                        System.out.println(enemy.getName() + " is frozen for " + enemy.getFrozen() + " turns.");
                    }
                    if(enemy.getBurning() > 0)
                    {
                        System.out.println(enemy.getName() + " is burning for " + enemy.getBurning() + " turns, taking "
                            + spellList.get(2).getDamage() + " damage per turn.");
                    }
                    if(enemy.getStunned() > 0)
                    {
                        System.out.println(enemy.getName() + " is stunned for " + enemy.getStunned() + " turns.");
                    }
                    if(enemy.getBuffed() > 0)
                    {
                        System.out.println(enemy.getName() + " is buffed for " + enemy.getBuffed() + " turns.");
                    }
                    System.out.println("");
                    if((One.getStunned() == 0) && (One.getFrozen() == 0))
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("A: Attack Enemy");
                        System.out.println("B: Use an ability");
                        System.out.println("C: Use an potion");
                        answer = reader.nextLine();
                        double enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                        double playerDamage = -1 * One.getAtk() * enemy.getDamageReduction();
                        if((answer.equals("A"))  || (answer.equals("a")))
                        {
                            if(enemy.getStunned() > 0) //balance
                            {
                                enemy.setHp(-1 * One.getAtk() - 2);
                                enemy.setStunned(0);
                                enemy.setAtk(tempAttack);
                            }
                            else
                            {
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(-1 * spelllist.get(0).getDamage());
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if ((answer.equals("B"))  || (answer.equals("b")))
                        {
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                System.out.println(One.get2dSpellsM());
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                System.out.println(One.get2dSpellsW());
                            }
                            System.out.println("What ability do you want to use?");
                            answer = reader.nextLine();
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                if(answer.equals("0"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(0).getDamage());
                                        One.setMana(-1 * spellList.get(0).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp(-1 * spelllist.get(0).getDamage());
                                            }
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                        }
                                        else
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }                                    
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("1"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(1).getDamage());
                                        One.setMana(-1 * spellList.get(1).getMana());                                   
                                        enemy.setFrozen(spellList.get(1).getTurnsF());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("2"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(2).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp(-1 * spelllist.get(0).getDamage());
                                            }
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                        }
                                        else
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                        enemy.setBurning(spellList.get(2).getTurnsB());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("3"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(3).getMana());
                                        enemy.setHp(-1 * spellList.get(3).getDamage());
                                        enemy.setStunned(spellList.get(3).getTurnsS());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                if((answer.equals("1")) && (One.getCooldown() == 0))
                                {
                                    enemy.setHp(-1 * spellList.get(1).getDamage());
                                    enemy.setStunned(spellList.get(1).getTurnsS());
                                    One.setCd(spellList.get(1).getCooldown());
                                }
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                        }
                        else if ((answer.equals("C"))  || (answer.equals("c")))
                        {
                            int temp = -1;
                            for (int i  = 0; i < inventory.size(); i ++)
                            {
                                System.out.println(inventory.get(i).getName() + " - ["  + i + "]");
                            }
                            System.out.println("What potion do you want to drink?");
                            answer = reader.nextLine();
                            if(answer.equals("0"))
                            {  String tempName = inventory.get(0).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(-1 * spelllist.get(0).getDamage());
                                    }
                                    enemy.setMana(-1 * spelllist.get(0).getMana());                                
                                    enemy.setHp((int)playerDamage);
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("1"))
                            {
                                String tempName = inventory.get(1).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(-1 * spelllist.get(0).getDamage());
                                    }
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setHp((int)playerDamage);
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("2"))
                            {
                                String tempName = inventory.get(2).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(-1 * spelllist.get(0).getDamage());
                                    }
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setHp((int)playerDamage);
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("3"))
                            {
                                String tempName = inventory.get(3).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(-1 * spelllist.get(0).getDamage());
                                    }
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setHp((int)playerDamage);
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if(answer.equals("scripting"))
                        {
                            enemy.setHp(-1 * enemy.getHealth());
                        }
                        else if (answer.equals("q"))
                        {
                            System.exit(0);
                        }
                    }
                    else
                    {
                        if(One.getStunned() > 0)
                        {
                            System.out.println("You are stunned and can't do anything this turn.");
                        }             
                        else if (One.getFrozen() > 0)
                        {
                            System.out.println("You are frozen and can't do anything this turn.");
                        }
                    }
                    if((One.getHp() != tempHealth) || (enemy.getHealth() != enemyHealth)) 
                    {
                        if((enemy.getFrozen() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if((enemy.getFrozen() > 0))
                        {
                            enemy.reduceTurnsFrozen();
                        }
                        if((One.getFrozen() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getFrozen() > 0))
                        {
                            One.reduceTurnsFrozen();
                        }
                        if(enemy.getBurning() > 0)
                        {
                            enemy.setHp(-1 * spellList.get(2).getDamage());
                            enemy.reduceTurnsBurning();
                            enemy.setHp(1);
                        }
                        if((enemy.getStunned() == 0) &&(enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if(enemy.getStunned() > 0)
                        {
                            enemy.reduceTurnsStunned();
                        }
                        if((One.getStunned() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getStunned() > 0))
                        {
                            One.reduceTurnsStunned();
                        }
                        if(enemy.getBuffed() > 0)
                        {
                            enemy.reduceTurnsBuffed();   
                        }
                        if((enemy.getBuffed() == 0) && (enemy.getAtk() != tempAttack))
                        {
                            enemy.setAtk(-1 * spelllist.get(0).getAtkBoost()); 
                        }
                        if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                        {
                            if(One.getHp() > halfhealth && One.getAtk() < playerAttack)
                            {
                                One.setTrueAtk(playerAttack);
                            }
                        }
                        if(One.getCooldown() > 0)
                        {
                            One.reduceCooldown();
                        }
                        int healthchange = tempHealth - One.getHp();
                        int healthchangee = enemyHealth - enemy.getHealth();
                        int damchange = One.getAtk() - playerAttack;
                        int damchangee = enemy.getAtk() - tempAttack;
                        System.out.println("This turn you took " + healthchange + " damage and your damage changed by " + damchange);
                        System.out.println("This turn your enemy took " + healthchangee + " damage and their damage changed by " + damchangee);
                    }
                    try { Thread.sleep(2000); } catch(Exception e) {}
                }
                if(enemy.getHealth() > 0)
                {
                    System.out.println("You Lost");
                    System.out.println("Game over");
                    return;
                }
                else if (One.getHp() > 0)
                {
                    One.setTrueAtk(playerAttack);
                    System.out.println("You won");
                    try { Thread.sleep(1000); } catch(Exception e) {}
                    currentloc.setCleared();
                    currentsetting.putSelfInGrid(Map, currentloc);
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < 150)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        if(One.getHp() < 100)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    if(One.getHp() > maxHp)
                    {
                        One.setTrueHp(maxHp);
                    }
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        inventory.add(new Potions());
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        inventory.add(new Potions("Mana Potion", 50));
                    }
                }
            }
            else
            {
                System.out.print('\u000C');
                GiantBattle();
            }
        }
        else
        {
            System.out.println("You see the giant you slaughtered and can't help but feel slightly bad.");    
        }
        //insert scene with first giant here
        afterGiantBattle();
    }

    private static void afterGiantBattle()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        System.out.print('\u000C');
        System.out.println("With the gatekeeper's final breaths he unleashes a petrifying roar,\nwhich catches the attention of a passing Giant formation.");
        System.out.println("The formation surrounds you and tells you that to pay for your crimes,\nby being forced to fight Ssobinim, the Prince of Ecclesia, in the arena.");
        System.out.println("What do you want to do?"); 
        String answer = reader.nextLine();
        if(answer.equals("Go Backwards"))
        {
            TutorialBattle2();
        }
        else if(answer.equals("Map"))
        {
            System.out.print('\u000C');
            drawMapTETRIS();
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterGiantBattle();      
            }
        }
        else if(answer.equals("q"))
        {
            System.exit(0);
        }
        else if (answer.equals("Stats"))
        {
            System.out.println("");
            System.out.println(One.getCurrentStats());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterGiantBattle();
            }
        }
        else if (answer.equals("Inventory"))
        {
            int counterh = 0;
            int counterm = 0;
            int innercounter = 0;
            System.out.println("");
            System.out.println(One.getInventory());
            for(int i = 0; i < inventory.size(); i++)
            {
                if(inventory.get(i).getName().equals("Health Potion"))
                {
                    counterh ++;
                }
                else if (inventory.get(i).getName().equals("Mana Potion"))
                {
                    counterm ++;
                }
            }
            if((counterm > 0) && (counterh > 0))
            {
                System.out.println("(Drink Health Potion, Drink Mana Potion)");
            }
            else if ((counterh > 0) && (counterm == 0))
            {
                System.out.println("(Drink Health Potion)");
            }
            else if ((counterh == 0) && (counterm > 0))
            {
                System.out.println("(Drink Mana Potion)");                
            }
            answer = reader.nextLine();
            if(answer.equals("Drink Health Potion"))
            {
                int healthchange = 0;
                int temphealth = One.getHp();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {   
                        if(inventory.get(i).getName().equals("Health Potion"))
                        {
                            One.setHp(inventory.get(i).getHealth());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getHp() > maxHp)
                {
                    One.setTrueHp(maxHp);
                }
                healthchange = One.getHp() - temphealth;
                System.out.println("You were healed for " + healthchange + ".");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterGiantBattle();
                }
            }
            else if (answer.equals("Drink Mana Potion"))
            {
                int manachange = 0;
                int tempmana = One.getMana();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {
                        if(inventory.get(i).getName().equals("Mana Potion"))
                        {
                            One.setMana(inventory.get(i).getMana());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getMana() > maxMana)
                {
                    One.setTrueMana(maxMana);
                }
                manachange = One.getMana() - tempmana;
                System.out.println("You gained " + manachange + " mana.");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterGiantBattle();                    
                }
            }
            else if(answer.equals(""))
            {
                afterGiantBattle();      
            }           
        }
        else if (answer.equals("Equipment"))
        {
            System.out.println("");
            System.out.println(One.getEquippedDescriptions());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterGiantBattle();      
            } 
        }
        else if(answer.equals("Abilities"))
        {
            System.out.println("");
            if((One.getCLass().equals("Mage")) || (One.getCLass().equals("mage")))
            {
                System.out.println(One.getSpellsDescriptionsM());
            }
            else if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
            {
                System.out.println(One.getSpellsDescriptionsW());
            }
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterGiantBattle();      
            }
        }
        else if (answer.equals("Quit"))
        {
            System.out.println("Are you sure that you want to quit?");
            answer = reader.nextLine();
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                System.exit(0);
            }
            else
            {
                afterGiantBattle();                
            }
        }
        else if (answer.equals("Continue"))
        {
            minibossfight();
        }
        else 
        {
            afterGiantBattle();
        }
    }

    private static void minibossfight()//fix combat
    {
        currentloc = new Location(0,3);
        currentsetting = Map.get(currentloc);
        currentloc = currentsetting.getLocation();
        if(currentloc.getCleared() == false)
        {
            String answer;
            System.out.print('\u000C');
            System.out.println("You enter the arena and see Ssobinim and hear the roaring of the crowd.");//goes offscreen
            //insert scene of mini-boss here
            System.out.println("Are you ready to fight for your freedom?");
            System.out.println("Yes");
            System.out.println("No");
            answer = reader.nextLine();            
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                ArrayList<Spell> spelllist = new ArrayList<Spell>();
                spelllist.add(new Spell(3, 20, 20,"Berzerk"));
                spelllist.add(new Spell(2,0,"Petrifying Gaze",20));
                Enemy enemy = new Enemy("Ssobinim", 150, 20, 200, 20, spelllist);
                int tempAttack = enemy.getAtk();
                int playerAttack = One.getAtk();
                int halfhealth = maxHp / 2;
                while((enemy.getHealth() > 0) && (One.getHp() > 0))
                {  
                    int tempHealth = One.getHp();
                    int enemyHealth = enemy.getHealth();
                    if(One.getFrozen() > 0)
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if(enemy.getFrozen() > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(enemy.getStunned()  > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(One.getStunned() > 0)
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < halfhealth && One.getAtk() == playerAttack)
                        {
                            One.setAtk(spellList.get(0).getAtkBoost());
                        }
                    }

                    System.out.print('\u000C');
                    System.out.println("Your stats- " + One.getCurrentStats());
                    if(One.getFrozen() > 0)
                    {
                        System.out.println(One.getName() + " is frozen for " + One.getFrozen() + " turns.");
                    }
                    if(One.getCooldown() > 0)
                    {
                        System.out.println(spellList.get(1).getName() +  " is on cooldown for "  + One.getCooldown() + " more turns.");
                    }
                    if(One.getStunned() > 0)
                    {
                        System.out.println(One.getName() +  " is stunned for " + One.getStunned() + " turns.");
                    }
                    System.out.println("");
                    System.out.println("Enemy stats- " + enemy.getCurrentStats());
                    if(enemy.getFrozen() > 0)
                    {
                        System.out.println(enemy.getName() + " is frozen for " + enemy.getFrozen() + " turns.");
                    }
                    if(enemy.getBurning() > 0)
                    {
                        System.out.println(enemy.getName() + " is burning for " + enemy.getBurning() + " turns, taking "
                            + spellList.get(2).getDamage() + " damage per turn.");
                    }
                    if(enemy.getStunned() > 0)
                    {
                        System.out.println(enemy.getName() + " is stunned for " + enemy.getStunned() + " turns.");
                    }
                    if(enemy.getBuffed() > 0)
                    {
                        System.out.println(enemy.getName() + " is buffed for " + enemy.getBuffed() + " turns.");
                    }
                    System.out.println("");
                    if((One.getStunned() == 0) && (One.getFrozen() == 0))
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("A: Attack Enemy");
                        System.out.println("B: Use an ability");
                        System.out.println("C: Use an potion");
                        answer = reader.nextLine();
                        double enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                        double playerDamage = -1 * One.getAtk() * enemy.getDamageReduction();
                        if((answer.equals("A"))  || (answer.equals("a")))
                        {
                            if(enemy.getStunned() > 0) //balance
                            {
                                enemy.setHp(-1 * One.getAtk());
                                enemy.setStunned(0);
                                enemy.setAtk(tempAttack);
                            }
                            else
                            {
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(1).getMana());
                                    enemy.setHp((int)playerDamage);
                                    One.setStunned(spelllist.get(1).getTurnsS());
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if ((answer.equals("B"))  || (answer.equals("b")))
                        {
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                System.out.println(One.get2dSpellsM());
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                System.out.println(One.get2dSpellsW());
                            }
                            System.out.println("What ability do you want to use?");
                            answer = reader.nextLine();
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                if(answer.equals("0"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(0).getDamage());
                                        One.setMana(-1 * spellList.get(0).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                        }
                                        else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                        {
                                            enemy.setMana(-1 * spelllist.get(1).getMana());
                                            One.setStunned(spelllist.get(1).getTurnsS());
                                        }
                                        else
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("1"))
                                {
                                    if(One.getMana() > spellList.get(1).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(1).getDamage());
                                        One.setMana(-1 * spellList.get(1).getMana());
                                        enemy.setFrozen(spellList.get(1).getTurnsF());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("2"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(2).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                        {
                                            enemy.setHp(-1);
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                        }
                                        else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                        {
                                            enemy.setMana(-1 * spelllist.get(1).getMana());
                                            enemy.setHp(-1);
                                            One.setStunned(spelllist.get(1).getTurnsS());
                                        }
                                        else
                                        {
                                            enemy.setHp(-1);
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                        enemy.setBurning(spellList.get(2).getTurnsB());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("3"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(3).getMana());
                                        enemy.setHp(-1 * spellList.get(3).getDamage());
                                        enemy.setStunned(spellList.get(3).getTurnsS());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }                            
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                if((answer.equals("1")) && (One.getCooldown() == 0))
                                {
                                    enemy.setHp(-1 * spellList.get(1).getDamage());
                                    enemy.setStunned(spellList.get(1).getTurnsS());
                                    One.setCd(spellList.get(1).getCooldown());
                                }
                                else if((answer.equals("1")) && (One.getCooldown() > 0))
                                {
                                    System.out.println("That spell is on cooldown, and was therefore not cast.");
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                        }
                        else if ((answer.equals("C"))  || (answer.equals("c")))
                        {
                            int temp = -1;
                            for (int i  = 0; i < inventory.size(); i ++)
                            {
                                System.out.println(inventory.get(i).getName() + " - ["  + i + "]");
                            }
                            System.out.println("What potion do you want to drink?");
                            answer = reader.nextLine();
                            if(answer.equals("0"))
                            {                        
                                String tempName = inventory.get(0).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();                                
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(1).getMana());
                                    One.setStunned(spelllist.get(1).getTurnsS());
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("1"))
                            {
                                String tempName = inventory.get(1).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(1).getMana());
                                    One.setStunned(spelllist.get(1).getTurnsS());
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("2"))
                            {
                                String tempName = inventory.get(2).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(1).getMana());
                                    One.setStunned(spelllist.get(1).getTurnsS());
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("3"))
                            {
                                String tempName = inventory.get(3).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getBuffed() == 0) && (enemy.getMana() > 10))
                                {
                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                    enemy.setHp((int)playerDamage);
                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                                else if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (One.getStunned() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(1).getMana());
                                    One.setStunned(spelllist.get(1).getTurnsS());
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if(answer.equals("scripting"))
                        {
                            enemy.setHp(-1 * enemy.getHealth());
                        }
                        else if (answer.equals("q"))
                        {
                            System.exit(0);
                        }
                    }
                    else 
                    {
                        One.setHp(-1 * enemy.getAtk());
                        if(One.getStunned() > 0)
                        {
                            System.out.println("You are stunned and can't do anything this turn.");
                        }             
                        else if (One.getFrozen() > 0)
                        {
                            System.out.println("You are frozen and can't do anything this turn.");
                        }
                    }
                    if((One.getHp() != tempHealth) || (enemy.getHealth() != enemyHealth)) 
                    {
                        if((enemy.getFrozen() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if((enemy.getFrozen() > 0))
                        {
                            enemy.reduceTurnsFrozen();
                        }
                        if((One.getFrozen() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getFrozen() > 0))
                        {
                            One.reduceTurnsFrozen();
                        }
                        if(enemy.getBurning() > 0)
                        {
                            enemy.setHp(-1 * spellList.get(2).getDamage());
                            enemy.reduceTurnsBurning();
                            enemy.setHp(1);
                        }
                        if((enemy.getStunned() == 0) &&(enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if(enemy.getStunned() > 0)
                        {
                            enemy.reduceTurnsStunned();
                        }
                        if((One.getStunned() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getStunned() > 0))
                        {
                            One.reduceTurnsStunned();
                        }
                        if(enemy.getBuffed() > 0)
                        {
                            enemy.reduceTurnsBuffed();   
                        }
                        if((enemy.getBuffed() == 0) && (enemy.getAtk() != tempAttack))
                        {
                            enemy.setAtk(-1 * spelllist.get(0).getAtkBoost()); 
                        }
                        if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                        {
                            if(One.getHp() > halfhealth && One.getAtk() < playerAttack)
                            {
                                One.setTrueAtk(playerAttack);
                            }
                        }
                        if(One.getCooldown() > 0)
                        {
                            One.reduceCooldown();
                        }
                        int healthchange = tempHealth - One.getHp();
                        int healthchangee = enemyHealth - enemy.getHealth();
                        int damchange = One.getAtk() - playerAttack;
                        int damchangee = enemy.getAtk() - tempAttack;
                        System.out.println("This turn you took " + healthchange + " damage and your damage changed by " + damchange);
                        System.out.println("This turn your enemy took " + healthchangee + " damage and their damage changed by " + damchangee);
                    }
                    try { Thread.sleep(2000); } catch(Exception e) {}
                }
                if(enemy.getHealth() > 0)
                {
                    System.out.println("You Lost");
                    System.out.println("Game over");
                    return;
                }
                else if (One.getHp() > 0)
                {
                    One.setTrueAtk(playerAttack);
                    System.out.println("You won");
                    System.out.println("With Ssobinim dead at your feet, you look and find a chest near the exit."); 
                    System.out.println("Looking inside the chest you find new armor,\nwhich is conveniently able to help you defeat Chrysaor.");
                    try { Thread.sleep(2000); } catch(Exception e) {}
                    currentloc.setCleared();
                    currentsetting.putSelfInGrid(Map, currentloc);
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < 150)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        if(One.getHp() < 100)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    if(One.getHp() > maxHp)
                    {
                        One.setTrueHp(maxHp);
                    }
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        One.setEquipment(new Gear("Acid-Proof Plate Mail of Unending Strength", 100, true), 1);
                        EquipmentList[1] = new Gear("Acid-Proof Plate Mail of Unending Strength", 100, true);
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        One.setEquipment(new Gear("Robes of Infinite Mana", 20, 300, true), 1);
                        EquipmentList[1] = new Gear("Robes of Infinite Mana", 20, 300, true);
                        One.setMana(200);
                        maxMana = maxMana + 200;
                    }
                }
            }
            else
            {
                System.out.print('\u000C');
                minibossfight();
            }
        }
        else
        {
            System.out.println("returning to the arena you find that Ssobinim's body has been left unburied as punishment for failing to protect " + EquipmentList[1].getName() + ".");
        }
        //insert scene of mini-boss here, tells what drops off of mini-boss here
        afterminibossfight();
    }

    private static void afterminibossfight()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        System.out.print('\u000C');
        //insert scene after mini-boss here
        System.out.println("You have equipped " + EquipmentList[1].getName());
        System.out.println("What do you want to do?");        
        String answer = reader.nextLine();
        if(answer.equals("Go Backwards"))
        {
            GiantBattle();
        }
        else if(answer.equals("Map"))
        {
            System.out.print('\u000C');
            drawMapTETRIS();
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterminibossfight();      
            }
        }
        else if(answer.equals("q"))
        {
            System.exit(0);
        }
        else if (answer.equals("Inventory"))
        {
            int counterh = 0;
            int counterm = 0;
            int innercounter = 0;
            System.out.println("");
            System.out.println(One.getInventory());
            for(int i = 0; i < inventory.size(); i++)
            {
                if(inventory.get(i).getName().equals("Health Potion"))
                {
                    counterh ++;
                }
                else if (inventory.get(i).getName().equals("Mana Potion"))
                {
                    counterm ++;
                }
            }
            if((counterm > 0) && (counterh > 0))
            {
                System.out.println("(Drink Health Potion, Drink Mana Potion)");
            }
            else if ((counterh > 0) && (counterm == 0))
            {
                System.out.println("(Drink Health Potion)");
            }
            else if ((counterh == 0) && (counterm > 0))
            {
                System.out.println("(Drink Mana Potion)");                
            }
            answer = reader.nextLine();
            if(answer.equals("Drink Health Potion"))
            {
                int healthchange = 0;
                int temphealth = One.getHp();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {   
                        if(inventory.get(i).getName().equals("Health Potion"))
                        {
                            One.setHp(inventory.get(i).getHealth());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getHp() > maxHp)
                {
                    One.setTrueHp(maxHp);
                }
                healthchange = One.getHp() - temphealth;
                System.out.println("You were healed for " + healthchange + ".");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterminibossfight();
                }
            }
            else if (answer.equals("Drink Mana Potion"))
            {
                int manachange = 0;
                int tempmana = One.getMana();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {
                        if(inventory.get(i).getName().equals("Mana Potion"))
                        {
                            One.setMana(inventory.get(i).getMana());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getMana() > maxMana)
                {
                    One.setTrueMana(maxMana);
                }
                manachange = One.getMana() - tempmana;
                System.out.println("You gained " + manachange + " mana.");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterminibossfight();                    
                }
            }
            else if(answer.equals(""))
            {
                afterminibossfight();      
            }           
        }
        else if (answer.equals("Equipment"))
        {
            System.out.println("");
            System.out.println(One.getEquippedDescriptions());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterminibossfight();      
            }    
        }
        else if (answer.equals("Stats"))
        {
            System.out.println("");
            System.out.println(One.getCurrentStats());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterminibossfight();
            }
        }
        else if(answer.equals("Abilities"))
        {
            System.out.println("");
            if((One.getCLass().equals("Mage")) || (One.getCLass().equals("mage")))
            {
                System.out.println(One.getSpellsDescriptionsM());
            }
            else if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
            {
                System.out.println(One.getSpellsDescriptionsW());
            }
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterminibossfight();      
            }
        }
        else if (answer.equals("Quit"))
        {
            System.out.println("Are you sure that you want to quit?");
            answer = reader.nextLine();
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                System.exit(0);
            }
            else
            {
                afterminibossfight();                
            }
        }
        else if (answer.equals("Continue"))
        {
            mageBattle();//fix
        }
        else 
        {
            afterminibossfight();
        }
    }

    private static void mageBattle()//fix combat
    {
        currentloc = new Location(0,4);
        currentsetting = Map.get(currentloc);
        currentloc = currentsetting.getLocation();
        if(currentloc.getCleared() == false)
        {
            String answer;
            System.out.print('\u000C');
            System.out.println("Coming to a crossroads, you are suddenly surprised when a mage ambushes you.");//goes offscreen
            //insert crossroads scene here
            System.out.println("Are you ready for the fight?");
            System.out.println("Yes");
            System.out.println("No");
            answer = reader.nextLine();            
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                ArrayList<Spell> spelllist = new ArrayList<Spell>();//fix spells
                spelllist.add(new Spell("Cone of Coldness", 10,20,2));//freeze
                spelllist.add(new Spell(2,20,"Mind Blast",20));//stun
                spelllist.add(new Spell(3,"Scorch",20,10));//burn
                spelllist.add(new Spell("Arcance Blast", 20, 20));//damage
                Enemy enemy = new Enemy("Crazed Mage", 175, 10, 300, 5, spelllist);
                int tempAttack = enemy.getAtk();
                int playerAttack = One.getAtk();
                int halfhealth = maxHp / 2;
                while((enemy.getHealth() > 0) && (One.getHp() > 0))
                {  
                    int tempHealth = One.getHp();
                    int enemyHealth = enemy.getHealth();
                    int randomspell = (int)(Math.random() * 4);
                    if(One.getFrozen() > 0)
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if(enemy.getFrozen() > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(enemy.getStunned()  > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(One.getStunned() > 0)//balance
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < halfhealth && One.getAtk() == playerAttack)
                        {
                            One.setAtk(spellList.get(0).getAtkBoost());
                        }
                    }
                    System.out.print('\u000C');
                    System.out.println("Your stats- " + One.getCurrentStats());
                    if(One.getFrozen() > 0)
                    {
                        System.out.println(One.getName() + " is frozen for " + One.getFrozen() + " turns.");
                    }
                    if(One.getCooldown() > 0)
                    {
                        System.out.println(spellList.get(1).getName() +  " is on cooldown for "  + One.getCooldown() + " more turns.");
                    }
                    if(One.getStunned() > 0)
                    {
                        System.out.println(One.getName() +  " is stunned for "  + One.getStunned() + " turns.");
                    }
                    System.out.println("");
                    System.out.println("Enemy stats- " + enemy.getCurrentStats());
                    if(enemy.getFrozen() > 0)
                    {
                        System.out.println(enemy.getName() + " is frozen for " + enemy.getFrozen() + " turns.");
                    }
                    if(enemy.getBurning() > 0)
                    {
                        System.out.println(enemy.getName() + " is burning for " + enemy.getBurning() + " turns, taking "
                            + spellList.get(2).getDamage() + " damage per turn.");
                    }
                    if(enemy.getStunned() > 0)
                    {
                        System.out.println(enemy.getName() + " is stunned for " + enemy.getStunned() + " turns.");
                    }
                    if(enemy.getBuffed() > 0)
                    {
                        System.out.println(enemy.getName() + " is buffed for " + enemy.getBuffed() + " turns.");
                    }
                    System.out.println("");
                    if((One.getStunned() == 0) && (One.getFrozen() == 0))
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("A: Attack Enemy");
                        System.out.println("B: Use an ability");
                        System.out.println("C: Use an potion");
                        answer = reader.nextLine();
                        double enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                        double playerDamage = -1 * One.getAtk() * enemy.getDamageReduction();
                        if((answer.equals("A"))  || (answer.equals("a")))
                        {
                            if(enemy.getStunned() > 0)
                            {
                                enemy.setHp(-1 * One.getAtk());
                                enemy.setStunned(0);
                                enemy.setAtk(tempAttack);
                            }
                            else
                            {
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {                                    
                                    enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                    One.setStunned(spelllist.get(randomspell).getTurnsS());
                                    One.setBurning(spelllist.get(randomspell).getTurnsB());
                                    One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(spelllist.get(randomspell).getDamage());
                                    }
                                    if((One.getStunned() > 0 ) && (One.getFrozen() > 0))
                                    {
                                        enemy.setHp((int)playerDamage);
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if ((answer.equals("B"))  || (answer.equals("b")))
                        {
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                System.out.println(One.get2dSpellsM());
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                System.out.println(One.get2dSpellsW());
                            }
                            System.out.println("What ability do you want to use?");
                            answer = reader.nextLine();
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                if(answer.equals("0"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(0).getDamage());
                                        One.setMana(-1 * spellList.get(0).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                        {
                                            enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                            One.setStunned(spelllist.get(randomspell).getTurnsS());
                                            One.setBurning(spelllist.get(randomspell).getTurnsB());
                                            One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp(spelllist.get(randomspell).getDamage());
                                            }
                                        }
                                        else
                                        {
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("1"))
                                {
                                    if(One.getMana() > spellList.get(1).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(1).getDamage());
                                        One.setMana(-1 * spellList.get(1).getMana());
                                        enemy.setFrozen(spellList.get(1).getTurnsF());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("2"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(2).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                        {
                                            enemy.setHp(-1);
                                            enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                            One.setStunned(spelllist.get(randomspell).getTurnsS());
                                            One.setBurning(spelllist.get(randomspell).getTurnsB());
                                            One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp(spelllist.get(randomspell).getDamage());
                                            }
                                        }
                                        else
                                        {
                                            enemy.setHp(-1);
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                        enemy.setBurning(spellList.get(2).getTurnsB());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("3"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(3).getMana());
                                        enemy.setHp(-1 * spellList.get(3).getDamage());
                                        enemy.setStunned(spellList.get(3).getTurnsS());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }                            
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                if((answer.equals("1")) && (One.getCooldown() == 0))
                                {
                                    enemy.setHp(-1 * spellList.get(1).getDamage());
                                    enemy.setStunned(spellList.get(1).getTurnsS());
                                    One.setCd(spellList.get(1).getCooldown());
                                }
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                        }
                        else if ((answer.equals("C"))  || (answer.equals("c")))
                        {
                            int temp = -1;
                            for (int i  = 0; i < inventory.size(); i ++)
                            {
                                System.out.println(inventory.get(i).getName() + " - ["  + i + "]");
                            }
                            System.out.println("What potion do you want to drink?");
                            answer = reader.nextLine();
                            if(answer.equals("0"))
                            {                        
                                String tempName = inventory.get(0).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                    One.setStunned(spelllist.get(randomspell).getTurnsS());
                                    One.setBurning(spelllist.get(randomspell).getTurnsB());
                                    One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(spelllist.get(randomspell).getDamage());
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("1"))
                            {
                                String tempName = inventory.get(1).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                    One.setStunned(spelllist.get(randomspell).getTurnsS());
                                    One.setBurning(spelllist.get(randomspell).getTurnsB());
                                    One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(spelllist.get(randomspell).getDamage());
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("2"))
                            {
                                String tempName = inventory.get(2).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                    One.setStunned(spelllist.get(randomspell).getTurnsS());
                                    One.setBurning(spelllist.get(randomspell).getTurnsB());
                                    One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(spelllist.get(randomspell).getDamage());
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("3"))
                            {
                                String tempName = inventory.get(3).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    enemy.setMana(-1 * spelllist.get(randomspell).getMana());
                                    One.setStunned(spelllist.get(randomspell).getTurnsS());
                                    One.setBurning(spelllist.get(randomspell).getTurnsB());
                                    One.setFrozen(spelllist.get(randomspell).getTurnsF());
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp(spelllist.get(randomspell).getDamage());
                                    }
                                }
                                else
                                {
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if(answer.equals("scripting"))
                        {
                            enemy.setHp(-1 * enemy.getHealth());
                        }
                        else if (answer.equals("q"))
                        {
                            System.exit(0);
                        }
                    }
                    else 
                    {
                        One.setHp(-1 * enemy.getAtk());
                        if(One.getStunned() > 0)
                        {
                            System.out.println("You are stunned and can't do anything this turn.");
                        }             
                        else if (One.getFrozen() > 0)
                        {
                            System.out.println("You are frozen and can't do anything this turn.");
                        }
                    }
                    if((One.getHp() != tempHealth) || (enemy.getHealth() != enemyHealth)) 
                    {
                        if((enemy.getFrozen() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if((enemy.getFrozen() > 0))
                        {
                            enemy.reduceTurnsFrozen();
                        }
                        if((One.getFrozen() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getFrozen() > 0))
                        {
                            One.reduceTurnsFrozen();
                        }
                        if(enemy.getBurning() > 0)
                        {
                            enemy.setHp(-1 * spellList.get(2).getDamage());
                            enemy.reduceTurnsBurning();
                            enemy.setHp(1);
                        }
                        if((enemy.getStunned() == 0) &&(enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if(enemy.getStunned() > 0)
                        {
                            enemy.reduceTurnsStunned();
                        }
                        if((One.getStunned() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getStunned() > 0))
                        {
                            One.reduceTurnsStunned();
                        }
                        if(enemy.getBuffed() > 0)
                        {
                            enemy.reduceTurnsBuffed();   
                        }
                        if((enemy.getBuffed() == 0) && (enemy.getAtk() != tempAttack))
                        {
                            enemy.setAtk(-1 * spelllist.get(0).getAtkBoost()); 
                        }
                        if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                        {
                            if(One.getHp() > halfhealth && One.getAtk() < playerAttack)
                            {
                                One.setTrueAtk(playerAttack);
                            }
                        }
                        if(One.getCooldown() > 0)
                        {
                            One.reduceCooldown();
                        }
                        int healthchange = tempHealth - One.getHp();
                        int healthchangee = enemyHealth - enemy.getHealth();
                        int damchange = One.getAtk() - playerAttack;
                        int damchangee = enemy.getAtk() - tempAttack;
                        System.out.println("This turn you took " + healthchange + " damage and your damage changed by " + damchange);
                        System.out.println("This turn your enemy took " + healthchangee + " damage and their damage changed by " + damchangee);
                    }
                    try { Thread.sleep(2000); } catch(Exception e) {}
                }
                if(enemy.getHealth() > 0)
                {
                    System.out.println("You Lost");
                    System.out.println("Game over");
                    return;
                }
                else if (One.getHp() > 0)
                {
                    One.setTrueAtk(playerAttack);
                    System.out.println("You won");
                    try { Thread.sleep(1000); } catch(Exception e) {}
                    currentloc.setCleared();
                    currentsetting.putSelfInGrid(Map, currentloc);
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < 150)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        if(One.getHp() < 100)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    if(One.getHp() > maxHp)
                    {
                        One.setTrueHp(maxHp);
                    }
                    inventory.add(new Potions());
                }
            }
            else
            {
                System.out.print('\u000C');
                mageBattle();
            }
        }
        else
        {
            System.out.println("You see the empty crossroads and remember the foolish mage who thought he could destroy you.");                
        }
        //insert scene of mini-boss here, tells what drops off of mini-boss here
        afterMageBattle();
    }

    private static void afterMageBattle()
    {
        try { Thread.sleep(1000); } catch(Exception e) {}
        System.out.print('\u000C');
        //insert scene after mini-boss here
        System.out.println("What do you want to do?");        
        String answer = reader.nextLine();
        if(answer.equals("Go Backwards"))
        {
            minibossfight();
        }
        else if(answer.equals("Map"))
        {
            System.out.print('\u000C');
            drawMapTETRIS();
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterMageBattle();      
            }
        }
        else if(answer.equals("q"))
        {
            System.exit(0);
        }
        else if (answer.equals("Inventory"))
        {
            int counterh = 0;
            int counterm = 0;
            int innercounter = 0;
            System.out.println("");
            System.out.println(One.getInventory());
            for(int i = 0; i < inventory.size(); i++)
            {
                if(inventory.get(i).getName().equals("Health Potion"))
                {
                    counterh ++;
                }
                else if (inventory.get(i).getName().equals("Mana Potion"))
                {
                    counterm ++;
                }
            }
            if((counterm > 0) && (counterh > 0))
            {
                System.out.println("(Drink Health Potion, Drink Mana Potion)");
            }
            else if ((counterh > 0) && (counterm == 0))
            {
                System.out.println("(Drink Health Potion)");
            }
            else if ((counterh == 0) && (counterm > 0))
            {
                System.out.println("(Drink Mana Potion)");                
            }
            answer = reader.nextLine();
            if(answer.equals("Drink Health Potion"))
            {
                int healthchange = 0;
                int temphealth = One.getHp();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {   
                        if(inventory.get(i).getName().equals("Health Potion"))
                        {
                            One.setHp(inventory.get(i).getHealth());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getHp() > maxHp)
                {
                    One.setTrueHp(maxHp);
                }
                healthchange = One.getHp() - temphealth;
                System.out.println("You were healed for " + healthchange + ".");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterMageBattle();
                }
            }
            else if (answer.equals("Drink Mana Potion"))
            {
                int manachange = 0;
                int tempmana = One.getMana();
                for(int i = 0; i < inventory.size(); i++)
                {
                    if(innercounter == 0)
                    {
                        if(inventory.get(i).getName().equals("Mana Potion"))
                        {
                            One.setMana(inventory.get(i).getMana());
                            inventory.remove(i);
                            innercounter++;
                        }
                    }
                }
                if(One.getMana() > maxMana)
                {
                    One.setTrueMana(maxMana);
                }
                manachange = One.getMana() - tempmana;
                System.out.println("You gained " + manachange + " mana.");
                answer = reader.nextLine();
                if(answer.equals(""))
                {
                    afterMageBattle();                    
                }
            }
            else if(answer.equals(""))
            {
                afterMageBattle();      
            }           
        }
        else if (answer.equals("Equipment"))
        {
            System.out.println("");
            System.out.println(One.getEquippedDescriptions());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterMageBattle();      
            }    
        }
        else if (answer.equals("Stats"))
        {
            System.out.println("");
            System.out.println(One.getCurrentStats());
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterMageBattle();
            }
        }
        else if(answer.equals("Abilities"))
        {
            System.out.println("");
            if((One.getCLass().equals("Mage")) || (One.getCLass().equals("mage")))
            {
                System.out.println(One.getSpellsDescriptionsM());
            }
            else if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
            {
                System.out.println(One.getSpellsDescriptionsW());
            }
            answer = reader.nextLine();
            if(answer.equals(""))
            {
                afterMageBattle();      
            }
        }
        else if (answer.equals("Quit"))
        {
            System.out.println("Are you sure that you want to quit?");
            answer = reader.nextLine();
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                System.exit(0);
            }
            else
            {
                afterMageBattle();                
            }
        }
        else if (answer.equals("Continue"))
        {
            bossBattle();
        }
        else 
        {
            afterMageBattle();
        }
    }

    private static void bossBattle()//fix combat
    {
        currentloc = new Location(0,5);
        currentsetting = Map.get(currentloc);
        currentloc = currentsetting.getLocation();
        if(currentloc.getCleared() == false)
        {
            String answer;
            System.out.print('\u000C');
            System.out.println("Arriving at the throne room you challenge Chrysaor and his right to rule.");//goes offscreen
            //insert throne room scene here
            System.out.println("Are you ready for the fight?");
            System.out.println("Yes");
            System.out.println("No");
            answer = reader.nextLine();            
            if((answer.equals("Yes")) || (answer.equals("yes")))
            {
                ArrayList<Spell> spelllist = new ArrayList<Spell>();//fix spells
                spelllist.add(new Spell(3, 20, 20,"Berzerk"));
                spelllist.add(new Spell(10,"Shatter/Harden", 40));// either reduces or increases armor
                spelllist.add(new Spell("Execute", 3,false));//gets cast when player hp is < half, sets turns to 3
                Enemy enemy = new Enemy("Chrysaor", 275, 20, 400, 20, spelllist);//balance stats
                int tempAttack = enemy.getAtk();
                int playerAttack = One.getAtk();
                int halfhealth = maxHp / 2;
                while((enemy.getHealth() > 0) && (One.getHp() > 0))
                {  
                    int tempHealth = One.getHp();
                    int enemyHealth = enemy.getHealth();
                    if((enemy.getTurnsC() == 0) && (spelllist.get(2).getCast() == true))
                    {
                        One.setTrueHp(0);
                        System.out.println("You have been executed by Chrysaor");
                        return;
                    }
                    if(One.getFrozen() > 0)
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if(enemy.getFrozen() > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(enemy.getStunned()  > 0)
                    {
                        enemy.setAtk(-1 * enemy.getAtk());
                    }
                    if(One.getStunned() > 0)//balance
                    {
                        One.setAtk(-1 * One.getAtk());
                    }
                    if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < halfhealth && One.getAtk() == playerAttack)
                        {
                            One.setAtk(spellList.get(0).getAtkBoost());
                        }
                    }

                    System.out.print('\u000C');
                    System.out.println("Your stats- " + One.getCurrentStats());
                    if(One.getFrozen() > 0)
                    {
                        System.out.println(One.getName() + " is frozen for " + One.getFrozen() + " turns.");
                    }
                    if(One.getCooldown() > 0)
                    {
                        System.out.println(spellList.get(1).getName() +  " is on cooldown for "  + One.getCooldown() + " more turns.");
                    }
                    if(One.getStunned() > 0)
                    {
                        System.out.println(One.getName() + " is stunned for "+ One.getStunned() + " turns.");                      
                    }
                    System.out.println("");
                    System.out.println("Enemy stats- " + enemy.getCurrentStats());
                    if(enemy.getFrozen() > 0)
                    {
                        System.out.println(enemy.getName() + " is frozen for " + enemy.getFrozen() + " turns.");
                    }
                    if(enemy.getBurning() > 0)
                    {
                        System.out.println(enemy.getName() + " is burning for " + enemy.getBurning() + " turns, taking "
                            + spellList.get(2).getDamage() + " damage per turn.");
                    }
                    if(enemy.getStunned() > 0)
                    {
                        System.out.println(enemy.getName() + " is stunned for " + enemy.getStunned() + " turns.");
                    }
                    if(enemy.getBuffed() > 0)
                    {
                        System.out.println(enemy.getName() + " is buffed for " + enemy.getBuffed() + " turns.");
                    }
                    if(enemy.getTurnsC() > 0)
                    {
                        System.out.println("Chrysaor will execute you in " + enemy.getTurnsC() + " turns.");
                    }
                    System.out.println("");
                    if((One.getStunned() == 0) && (One.getFrozen() == 0))
                    {
                        System.out.println("What do you want to do?");
                        System.out.println("A: Attack Enemy");
                        System.out.println("B: Use an ability");
                        System.out.println("C: Use an potion");
                        answer = reader.nextLine();
                        double enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                        double playerDamage = -1 * One.getAtk() * enemy.getDamageReduction();
                        if((answer.equals("A"))  || (answer.equals("a")))
                        {
                            if(enemy.getStunned() > 0) //balance
                            {
                                enemy.setHp(-1 * One.getAtk() - 2);
                                enemy.setStunned(0);
                                enemy.setAtk(tempAttack);
                            }
                            else
                            {
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                    {
                                        enemy.setCasting(spelllist.get(2).getTurnsC());
                                        enemy.setHp((int)playerDamage);
                                        enemy.setMana(- 1 * spelllist.get(2).getMana());
                                        spelllist.get(2).setCast();
                                    }
                                    else
                                    {
                                        if(enemy.getBuffed() == 0)
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemy.setHp((int)playerDamage);
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if ((answer.equals("B"))  || (answer.equals("b")))
                        {
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                System.out.println(One.get2dSpellsM());
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                System.out.println(One.get2dSpellsW());
                            }
                            System.out.println("What ability do you want to use?");
                            answer = reader.nextLine();
                            if(One.getCLass().equals("Mage") || One.getCLass().equals("mage"))
                            {
                                if(answer.equals("0"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(0).getDamage());
                                        One.setMana(-1 * spellList.get(0).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                        {
                                            if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                            {
                                                enemy.setCasting(spelllist.get(2).getTurnsC());
                                                enemy.setMana(- 1 * spelllist.get(2).getMana());
                                                spelllist.get(2).setCast();
                                            }
                                            else
                                            {
                                                if(enemy.getBuffed() == 0)
                                                {
                                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                                    if(enemy.getHealth() > 0)
                                                    {
                                                        One.setHp((int)enemyDamage);
                                                    }
                                                }
                                                else
                                                {
                                                    if(enemy.getHealth() > 0)
                                                    {
                                                        One.setHp((int)enemyDamage);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("1"))
                                {
                                    if(One.getMana() > spellList.get(1).getMana())
                                    {
                                        enemy.setHp(-1 * spellList.get(1).getDamage());
                                        One.setMana(-1 * spellList.get(1).getMana());
                                        enemy.setFrozen(spellList.get(1).getTurnsF());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("2"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(2).getMana());
                                        if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                        {
                                            if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                            {
                                                enemy.setCasting(spelllist.get(2).getTurnsC());
                                                enemy.setHp(-1);
                                                enemy.setMana(- 1 * spelllist.get(2).getMana());
                                                spelllist.get(2).setCast();
                                            }
                                            else
                                            {
                                                if(enemy.getBuffed() == 0)
                                                {
                                                    enemy.setAtk(spelllist.get(0).getAtkBoost());
                                                    enemy.setMana(-1 * spelllist.get(0).getMana());
                                                    enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                                    enemy.setHp(-1);
                                                    enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                                    if(enemy.getHealth() > 0)
                                                    {
                                                        One.setHp((int)enemyDamage);
                                                    }
                                                }
                                            }
                                        }
                                        enemy.setBurning(spellList.get(2).getTurnsB());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }
                                else if (answer.equals("3"))
                                {
                                    if(One.getMana() > spellList.get(0).getMana())
                                    {
                                        One.setMana(-1 * spellList.get(3).getMana());
                                        enemy.setHp(-1 * spellList.get(3).getDamage());
                                        enemy.setStunned(spellList.get(3).getTurnsS());
                                    }
                                    else
                                    {
                                        System.out.println("Your attempt to cast the spell failed due to lack of mana.");
                                        if(enemy.getHealth() > 0)
                                        {
                                            One.setHp((int)enemyDamage);
                                        }
                                    }
                                }                            
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                            else if(One.getCLass().equals("Warrior") || One.getCLass().equals("warrior"))
                            {
                                if((answer.equals("1")) && (One.getCooldown() == 0))
                                {
                                    enemy.setHp(-1 * spellList.get(1).getDamage());
                                    enemy.setStunned(spellList.get(1).getTurnsS());
                                    One.setCd(spellList.get(1).getCooldown());
                                }
                                else
                                {
                                    System.out.println("That is not an ability");
                                    try { Thread.sleep(1000); } catch(Exception e) {}
                                }
                            }
                        }
                        else if ((answer.equals("C"))  || (answer.equals("c")))
                        {
                            int temp = -1;
                            for (int i  = 0; i < inventory.size(); i ++)
                            {
                                System.out.println(inventory.get(i).getName() + " - ["  + i + "]");
                            }
                            System.out.println("What potion do you want to drink?");
                            answer = reader.nextLine();
                            if(answer.equals("0"))
                            {                        
                                String tempName = inventory.get(0).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                    {
                                        enemy.setCasting(spelllist.get(2).getTurnsC());
                                        enemy.setHp((int)playerDamage);
                                        enemy.setMana(- 1 * spelllist.get(2).getMana());
                                        spelllist.get(2).setCast();
                                    }
                                    else
                                    {
                                        if(enemy.getBuffed() == 0)
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemy.setHp((int)playerDamage);
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("1"))
                            {
                                String tempName = inventory.get(1).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                    {
                                        enemy.setCasting(spelllist.get(2).getTurnsC());
                                        enemy.setHp((int)playerDamage);
                                        enemy.setMana(- 1 * spelllist.get(2).getMana());
                                        spelllist.get(2).setCast();
                                    }
                                    else
                                    {
                                        if(enemy.getBuffed() == 0)
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemy.setHp((int)playerDamage);
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("2"))
                            {
                                String tempName = inventory.get(2).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                    {
                                        enemy.setCasting(spelllist.get(2).getTurnsC());
                                        enemy.setHp((int)playerDamage);
                                        enemy.setMana(- 1 * spelllist.get(2).getMana());
                                        spelllist.get(2).setCast();
                                    }
                                    else
                                    {
                                        if(enemy.getBuffed() == 0)
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemy.setHp((int)playerDamage);
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                            else if(answer.equals("3"))
                            {
                                String tempName = inventory.get(3).getName();
                                for(int i = 0; i < inventory.size(); i ++)
                                {
                                    if(inventory.get(i).getName().equals(tempName))
                                    {
                                        temp = i;
                                    }                           
                                }
                                One.setHp(inventory.get(temp).getHealth());   
                                One.setMana(inventory.get(temp).getMana());
                                inventory.remove(temp);
                                if(One.getHp() > maxHp)
                                {
                                    One.setTrueHp(maxHp);
                                }
                                if(One.getMana() > maxMana)
                                {
                                    One.setTrueMana(maxMana);
                                }
                                if((enemy.getStunned() == 0)  && (enemy.getFrozen() == 0) && (enemy.getMana() > 20))
                                {
                                    if((One.getHp() < halfhealth) && (spelllist.get(2).getCast() == false))
                                    {
                                        enemy.setCasting(spelllist.get(2).getTurnsC());
                                        enemy.setHp((int)playerDamage);
                                        enemy.setMana(- 1 * spelllist.get(2).getMana());
                                        spelllist.get(2).setCast();
                                    }
                                    else
                                    {
                                        if(enemy.getBuffed() == 0)
                                        {
                                            enemy.setAtk(spelllist.get(0).getAtkBoost());
                                            enemy.setMana(-1 * spelllist.get(0).getMana());
                                            enemy.setBuffed(spelllist.get(0).getTurnsBu());
                                            enemy.setHp((int)playerDamage);
                                            enemyDamage = -1 * enemy.getAtk() * One.getDamageReduction();
                                            if(enemy.getHealth() > 0)
                                            {
                                                One.setHp((int)enemyDamage);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    enemy.setHp((int)playerDamage);
                                    if(enemy.getHealth() > 0)
                                    {
                                        One.setHp((int)enemyDamage);
                                    }
                                }
                            }
                        }
                        else if(answer.equals("scripting"))
                        {
                            enemy.setHp(-1 * enemy.getHealth());
                        }
                        else if (answer.equals("q"))
                        {
                            System.exit(0);
                        }
                    }
                    else
                    {
                        if(One.getStunned() > 0)
                        {
                            System.out.println("You are stunned and can't do anything this turn.");
                        }             
                        else if (One.getFrozen() > 0)
                        {
                            System.out.println("You are frozen and can't do anything this turn.");
                        }
                    }
                    if((One.getHp() != tempHealth) || (enemy.getHealth() != enemyHealth)) 
                    {
                        if((enemy.getFrozen() == 0) && (enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if((enemy.getFrozen() > 0))
                        {
                            enemy.reduceTurnsFrozen();
                        }
                        if((One.getFrozen() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getFrozen() > 0))
                        {
                            One.reduceTurnsFrozen();
                        }
                        if((enemy.getTurnsC() > 0) && (enemy.getStunned() == 0) && (enemy.getFrozen() == 0) && (One.getHp() < halfhealth))
                        {
                            enemy.reduceTurnsCasting();
                        }
                        else if ((One.getHp() > halfhealth) && (spelllist.get(2).getCast() == true))
                        {
                            spelllist.get(2).setCast();
                        }
                        if(enemy.getBurning() > 0)
                        {
                            enemy.setHp(-1 * spellList.get(2).getDamage());
                            enemy.reduceTurnsBurning();
                            enemy.setHp(1);
                        }
                        if((enemy.getStunned() == 0) &&(enemy.getAtk() == 0))
                        {
                            enemy.setAtk(tempAttack);
                        }
                        else if(enemy.getStunned() > 0)
                        {
                            enemy.reduceTurnsStunned();
                        }
                        if((One.getStunned() == 0) && (One.getAtk() == 0))
                        {
                            One.setAtk(playerAttack);
                        }
                        else if((One.getStunned() > 0))
                        {
                            One.reduceTurnsStunned();
                        }
                        if(enemy.getBuffed() > 0)
                        {
                            enemy.reduceTurnsBuffed();   
                        }
                        if((enemy.getBuffed() == 0) && (enemy.getAtk() != tempAttack))
                        {
                            enemy.setAtk(-1 * spelllist.get(0).getAtkBoost()); 
                        }
                        if((One.getCLass().equals("Warrior")) || (One.getCLass().equals("warrior")))
                        {
                            if(One.getHp() > halfhealth && One.getAtk() < playerAttack)
                            {
                                One.setTrueAtk(playerAttack);
                            }
                        }
                        if(One.getCooldown() > 0)
                        {
                            One.reduceCooldown();
                        }
                        int healthchange = tempHealth - One.getHp();
                        int healthchangee = enemyHealth - enemy.getHealth();
                        int damchange = One.getAtk() - playerAttack;
                        int damchangee = enemy.getAtk() - tempAttack;
                        System.out.println("This turn you took " + healthchange + " damage and your damage changed by " + damchange);
                        System.out.println("This turn your enemy took " + healthchangee + " damage and their damage changed by " + damchangee);
                    }
                    try { Thread.sleep(2000); } catch(Exception e) {}
                }
                if(enemy.getHealth() > 0)
                {
                    System.out.println("You Lost");
                    System.out.println("Game over");
                    return;
                }
                else if (One.getHp() > 0)
                {
                    One.setTrueAtk(playerAttack);
                    System.out.println("You won");
                    try { Thread.sleep(1000); } catch(Exception e) {}
                    currentloc.setCleared();
                    currentsetting.putSelfInGrid(Map, currentloc);
                    if(One.getCLass().equals("Warrior") || (One.getCLass().equals("warrior")))
                    {
                        if(One.getHp() < 150)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    else if(One.getCLass().equals("Mage") || (One.getCLass().equals("mage")))
                    {
                        if(One.getHp() < 100)
                        {
                            One.setHp((int)restore);
                        }
                    }
                    if(One.getHp() > maxHp)
                    {
                        One.setTrueHp(maxHp);
                    }
                }
            }
            else
            {
                System.out.print('\u000C');
                bossBattle();
            }
        }
        afterBossBattle();
    }

    private static void afterBossBattle()
    {
        String answer;
        System.out.print('\u000C');
        System.out.println("With Chrysaor defeated the giants flee and you begin your reign as king.");
        System.out.println("Thank You for playing and Well Played");
    }
}
