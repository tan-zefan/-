package lifegame;

import java.awt.Color;

public class WorldUtils {
    public static final int SIZE = 30;
    public static final int CELL_Size =20;
    public static Color cell =new Color(32,98,40);
    public static Color space =new Color(255, 255, 255);//Color(226,245,226);
    //当代的状况，格子中是否有生命
    public static boolean[][] table = new boolean[SIZE][SIZE];
    //格子的邻居数目
    public static int[][] neighbors = new int[SIZE][SIZE];

    public static void getNeighbors() {
        for (int r = 0; r < SIZE; r++){//row
            for (int c = 0; c < SIZE; c++){//col
                if(r-1 >= 0 && c-1 >= 0   && table[r-1][c-1] )neighbors[r][c]++;
                if(r-1 >= 0 && table[r-1][c])neighbors[r][c]++;
                if(r-1 >= 0 && c+1 < SIZE && table[r-1][c+1])neighbors[r][c]++;
                if(c-1 >= 0   && table[r][c-1]) neighbors[r][c]++;
                if(c+1 < SIZE && table[r][c+1]) neighbors[r][c]++;
                if(r+1 < SIZE && table[r+1][c]) neighbors[r][c]++;
                if(r+1 < SIZE && c+1 < SIZE && table[r+1][c+1])neighbors[r][c]++;
                if(r+1 < SIZE && c-1 >=0 && table[r+1][c-1])neighbors[r][c]++;
            }
        }
    }

    public static void nextWorld() { //
        for (int r = 0; r < SIZE; r++) { //row
            for (int c = 0; c < SIZE; c++) { //col
                if (neighbors[r][c] == 3) {
                    table[r][c] = true;
                }
                if (neighbors[r][c] < 2) {
                    table[r][c] = false; 
                }
                if (neighbors[r][c] >= 4) {
                    table[r][c] = false; 
                }              
                neighbors[r][c] = 0;                
            }           
        }
    }
}
