import java.util.*;

//Rashaad Ratliff-Brown
//July 9, 2020
//800833460

public class Queens {
    
    //create the needed variables
    private int restarts = 0;
    private int moves = 0;
    private boolean newMap = true;
    private int neighbors = 8;
    private int heuristic = 0;
    private int queenLocation = 0;
    final private int [][] map = new int[8][8];
    final private int [][] trialMap = new int[8][8];

  
    //init map
    public Queens( )
    { 
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                map[i][j] = 0;
            }
        }
    }
  
    //rand map
    public void randomizeMap( )
    { 
      Random rand = new Random( );
      int num;
      
      while(queenLocation < 8)
      {
            for(int i = 0; i < 8; i++)
            {
                map[rand.nextInt(7)][i] = 1;
                queenLocation++;
                }
            }
      heuristic = heuristic(map);
    }
  
    //evaluate row
    public boolean evalRow(int [][] trial, int x)
    { 
        boolean result = false;
        int count = 0;
      
        for(int i = 0; i < 8; i++)
        {
            if(trial[i][x] == 1)
            {
                count++;
            }
        }
        if(count > 1)
        {
            result = true;
        }
        return result;
    }
    
    //evaluate column
    public boolean evalColumn(int [][] trial, int j)
    { 
        boolean result = false;
        int count = 0;
        for(int i = 0; i < 8; i++)
        {
            if(trial[j][i] == 1)
            {
                count++;
            }
        }
        if(count > 1)
        {
          result = true;
        }
        return result;
    }
    
    //evaluate diagonal
    public boolean evalDiagonal(int [][] trial, int x, int y)
    {
        boolean result = false;
      
        for(int i = 1; i < 8; i++)
        {
            if(result)
            {
                break;
            }

            if((x + i < 8) && (y + i < 8))
            {
                if(trial[x + i][y + i] == 1)
                {
                    result = true;
                }
            }
            if((x - i >= 0) && (y - i >= 0))
            {
                if(trial[x- i][y - i] == 1)
                {
                    result = true;
                }
            }
            if((x + i < 8) && (y - i >= 0))
            {
                if(trial[x + i][y - i] == 1)
                {
                    result = true;
                }
            }  
            if((x - i >= 0) && (y + i < 8))
            {
                if(trial[x - i][y + i] == 1)
                {
                    result = true;
                }
            }  
        }
        return result;
    }
    
    //count queens
    public int heuristic(int [][] trial)
    {
    int count = 0;
    
    boolean column;
    boolean row;
    boolean diagonal;
      
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(trial[i][j] == 1)
                {
                    row = evalRow(trial, j);
                    column = evalColumn(trial, i);
                    diagonal = evalDiagonal(trial, i, j);
                  
                    if(row || column || diagonal){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    //moves queen
    public void moveQueen( )
    {   
        int minimumColumn;
        int minimumRow;
        int[][] hArray = new int[8][8];
        int columnCount;
    
        int previousColumnQueen = 0;
        newMap = false;
      
        while(true)
        {
            columnCount = 0;
      
            for(int i = 0; i < 8; i++)
            {
                System.arraycopy(map[i], 0, trialMap[i], 0, 8);
            }
            while(columnCount < 8)
            {
                for(int i = 0; i < 8;i++)
                {
                    trialMap[i][columnCount] = 0;
                }
                for(int i = 0; i < 8; i++)
                {
                    if(map[i][columnCount] == 1)
                    {
                        previousColumnQueen = i;
                    }
                    trialMap[i][columnCount] = 1;
                    hArray[i][columnCount] = heuristic(trialMap);
                    trialMap[i][columnCount] = 0;
                }
                trialMap[previousColumnQueen][columnCount] = 1;
                columnCount++;
            }
          
            if(determineRestart(hArray))
            {
                queenLocation = 0;
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                        map[i][j] = 0;
                    }
                }
                randomizeMap( );
                System.out.println("RESTART");
                restarts++;
            }
      
            minimumRow = findminimumRow(hArray);
            minimumColumn = findminimumColumn(hArray);
      
            for(int i = 0; i < 8; i++)
            {
                map[i][minimumColumn] = 0;
            }
      
            map[minimumRow][minimumColumn] = 1;
            moves++;
            heuristic = heuristic(map);
          
            if(heuristic(map) == 0)
            {
                System.out.println("\nCurrent State");
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                        System.out.print(map[i][j] + " ");
                    }
                    System.out.print("\n");
                }
            System.out.println("Solution Found!");
            System.out.println("State changes: " + moves);
            System.out.println("Restarts: " + restarts);
            break;
            }

            System.out.println("\n");
            System.out.println("Current h: " + heuristic);
            System.out.println("Current State");
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    System.out.print(map[i][j] + " ");
                }
                System.out.print("\n");
            }
            System.out.println("Neighbors found with lower h: " + neighbors);
            System.out.println("Setting new current state");
        }
    }
    
     //find min col
    public int findminimumColumn(int[][] trial)
    {
        int minimumColumn = 8;
        int minimumValue = 8;
        int count = 0;
      
        for(int i = 0; i < 8; i++)
        {
          for(int j = 0; j < 8; j++)
          {
              if(trial[i][j] < minimumValue)
              {
                  minimumValue = trial[i][j];
                  minimumColumn = j;
              }
              if(trial[i][j] < heuristic)
              {
                  count++;
              }
          }
        }
        neighbors = count;
        return minimumColumn;
    }
    
    //find min row
    public int findminimumRow(int[][] trial)
    { 
        int minimumRow = 8;
        int minimumValue = 8;
      
        for(int i = 0; i < 8; i++){
          for(int j = 0; j < 8; j++){
              if(trial[i][j] < minimumValue){
                  minimumValue = trial[i][j];
                  minimumRow = i;
              }
          }
        }
        return minimumRow;
    }
    
    //determines restart
    public boolean determineRestart(int [][] trial)
    {
        int minimumValue = 8;
        boolean restart = false;
      
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(trial[i][j] < minimumValue){
                    minimumValue = trial[i][j];
                }
            }
        }
        if(neighbors == 0){
            restart = true;
        }
        return restart;
    }
    
    //main
    public static void main(String[] args) 
    {
     Queens board = new Queens( );
     board.randomizeMap();
     board.moveQueen();
    }
}