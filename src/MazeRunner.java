import java.util.Random;

public class MazeRunner {
	 public static void main(String[] arg){
	        int SIZE = 4;

	        Maze maze = new Maze(SIZE);
	        maze.fill(new Random());
	        System.out.println("Maze 1 for DFS");
	        maze.printMaze(Maze.PATH);
	        maze.dfs();
	        System.out.println("Single Path :");
	        maze.printPath(maze.graph[0][0], maze.graph[SIZE-1][SIZE-1]);
	        System.out.println("");
	        maze.printMaze(Maze.PATH);
	        System.out.println("\nDFS: ");
	        maze.printMaze(Maze.PATHDFS);

	        Maze maze1 = new Maze(SIZE);
	        maze1.fill(new Random());
	        System.out.println("\n\nMaze 2 for BFS");
	        maze1.printMaze(Maze.PATH);
	        maze1.bfs();
	        System.out.println("Single Path: ");
	        maze1.printPath(maze1.graph[0][0], maze1.graph[SIZE-1][SIZE-1]);
	        System.out.println("");
	        maze1.printMaze(Maze.PATH);
	        System.out.println("BFS: ");
	        maze1.printMaze(Maze.PATHBFS);
	        
	        
	    }

}
