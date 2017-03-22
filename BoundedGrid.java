/**
 * BoundedGrid class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;

//A BoundedGrid is a rectangular grid with a finite number of rows and columns.
public class BoundedGrid<E>
{
    private Object[][] occupantArray;  // the array storing the grid elements

    //Constructs an empty BoundedGrid with the given dimensions.
    //(Precondition:  rows > 0 and cols > 0.)
    public BoundedGrid(int rows, int cols)
    {
        occupantArray = new Object[rows][cols];
    }

    //returns the number of rows
    public int getNumRows()
    {
        return occupantArray.length;
    }

    //returns the number of columns
    public int getNumCols()
    {
        return occupantArray[0].length;
    }

    //returns the object at location loc (or null if the location is unoccupied)
    //precondition:  loc is valid in this grid
    @SuppressWarnings("unchecked")  // needed to suppress compilation warnings
    public E get(Location loc)
    {
        return (E)occupantArray[loc.getRow()][loc.getCol()];
    }

    //puts obj at location loc in this grid and returns the object previously at that location (or null if the
    //location is unoccupied)
    //precondition:  loc is valid in this grid
    public E put(Location loc, E obj)
    {
        E oldOccupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }

    //returns true if loc is valid in this grid, false otherwise
    //precondition:  loc is not null
    public boolean isValid(Location loc)
    {
        //hint:  use getNumRows and getNumCols
        return (0 <= loc.getRow()) && (loc.getRow() < getNumRows())
        && (0 <= loc.getCol()) && (loc.getCol() < getNumCols());
    }

    //removes the object at location loc from this grid and returns the object that was removed (or null if the
    //location is unoccupied
    //precondition:  loc is valid in this grid
    public E remove(Location loc)
    {
        //hint:  use the put method to store a null at this location
        if (isValid(loc))
        {
            E r = get(loc);
            occupantArray[loc.getRow()][loc.getCol()] = null;
            return r;
        }
        return null;
    }

    //returns a list of all occupied locations in this grid
    public ArrayList<Location> getOccupiedLocations()
    {
        //hint:  use the get method to determine if a location is empty
        ArrayList<Location> loc = new ArrayList<Location>();
        for(int r = 0; r < getNumRows(); r ++)
        {
            for(int c = 0; c < getNumCols(); c++)
            {
                Location x = new Location(r,c,false);
                if((get(x) != null))
                {
                    loc.add(x);
                }
            }
        }
        return loc;
    }
}