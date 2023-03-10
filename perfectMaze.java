/*
 * Write a Maze ADT that takes a command line parameter N, and generates a random 
 * N-by-N perfect maze. A maze is perfect if it has exactly one path between 
 * every pair of points in the maze, i.e., no inaccessible locations, no cycles, 
 * and no open spaces.
 * 
 * @Kenneth Fan
 * @3/10/23
 * @java 8
 * 
 */

import java.util.*;

public class perfectMaze {
    private final int cols, rows;
    private boolean[][] visited;
    private boolean[][] n;
    private boolean[][] e;
    private boolean[][] w;
    private boolean[][] s;

    public perfectMaze(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        int width = (int)(1.0 * 800 * cols / rows);
        StdDraw.setCanvasSize(width, 800);
        StdDraw.setXscale(0, cols + 2);
        StdDraw.setYscale(0, rows + 2);
        
        visited = new boolean[cols + 2][rows + 2];
        for (int col = 0; col < cols + 2; col++) {
            visited[col][0] = true;
            visited[col][rows + 1] = true;
        }
        for (int row = 0; row < rows + 2; row++) {
            visited[0][row] = true;
            visited[cols + 1][row] = true;
        }
        n = new boolean[cols + 2][rows + 2];
        e = new boolean[cols + 2][rows + 2];
        s = new boolean[cols + 2][rows + 2];
        w = new boolean[cols + 2][rows + 2];
        for (int col = 0; col < cols + 2; col++) {
            for (int row = 0; row < rows + 2; row++) {
                n[col][row] = true;
                e[col][row] = true;
                s[col][row] = true;
                w[col][row] = true;
            }
        }
        generate(1,1);
    }
    
    private void generate(int col, int row) {
        visited[col][row] = true;
        while (!visited[col][row + 1] || !visited[col + 1][row] || !visited[col][row - 1] || !visited[col - 1][row]) {
            while (true) {
                double r = StdRandom.uniformInt(4);
                if (r == 0 && !visited[col][row + 1]) {
                    n[col][row] = false;
                    s[col][row + 1] = false;
                    generate(col, row + 1);
                    break;
                }
                else if (r == 1 && !visited[col + 1][row]) {
                    e[col][row] = false;
                    w[col + 1][row] = false;
                    generate(col + 1, row);
                    break;
                }
                else if (r == 2 && !visited[col][row - 1]) {
                    s[col][row] = false;
                    n[col][row - 1] = false;
                    generate(col, row - 1);
                    break;
                }
                else if (r == 3 && !visited[col - 1][row]) {
                    w[col][row] = false;
                    e[col - 1][row] = false;
                    generate(col - 1, row);
                    break;
                }
            }
        }
    }

    public void draw() {
        for (int col = 1; col <= cols; col++) {
            for (int row = 1; row <= rows; row++) {
                if (n[col][row]) StdDraw.line(col, row + 1, col + 1, row + 1);
                if (s[col][row]) StdDraw.line(col, row, col + 1, row);
                if (e[col][row]) StdDraw.line(col + 1, row, col + 1, row + 1);
                if (w[col][row]) StdDraw.line(col, row, col, row + 1);
            }
        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("col and row: ");
        int cols = sc.nextInt();
        int rows = sc.nextInt();
        sc.close();
        perfectMaze maze = new perfectMaze(cols,rows);
        StdDraw.enableDoubleBuffering();
        maze.draw();
    }

}
