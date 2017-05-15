import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

public class MazeTester {

	@Test
	public void testFill() {
		Maze maze = new Maze(4);
		maze.fill(new Random(0));
		ArrayList<String> p = maze.displayMaze(Maze.PATH);

		ArrayList<String> e = new ArrayList<>();
		e.add("+ +-+-+-+");
		e.add("|     | |");
		e.add("+-+-+ + +");
		e.add("|   |   |");
		e.add("+ +-+-+ +");
		e.add("|     | |");
		e.add("+ +-+ + +");
		e.add("|   |   |");
		e.add("+-+-+-+ +");
		
		assertEquals(e, p);
	}

	@Test
	public void testBfs() {
		Maze maze = new Maze(4);
		maze.fill(new Random(0));
        maze.bfs();
        
		ArrayList<String> p = maze.displayMaze(Maze.PATHBFS);

		ArrayList<String> e = new ArrayList<>();
		e.add("+ +-+-+-+");
		e.add("|0 1 2|6|");
		e.add("+-+-+ + +");
		e.add("|   |3 4|");
		e.add("+ +-+-+ +");
		e.add("|     |5|");
		e.add("+ +-+ + +");
		e.add("|   |  7|");
		e.add("+-+-+-+ +");
		
		assertEquals(e, p);
	}

	@Test
	public void testDfs() {
		Maze maze = new Maze(4);
		maze.fill(new Random(0));
        maze.dfs();
 
        ArrayList<String> p = maze.displayMaze(Maze.PATHDFS);

		ArrayList<String> e = new ArrayList<>();
		e.add("+ +-+-+-+");
		e.add("|0 1 2| |");
		e.add("+-+-+ + +");
		e.add("|   |3 4|");
		e.add("+ +-+-+ +");
		e.add("|     |5|");
		e.add("+ +-+ + +");
		e.add("|   |  6|");
		e.add("+-+-+-+ +");
		
		assertEquals(e, p);
	}

	@Test
	public void testSamePath() {
		Maze maze1 = new Maze(10);
		maze1.fill(new Random(0));
        maze1.bfs();
        ArrayList<String> p1 = maze1.displayMaze(Maze.PATH);

		Maze maze2 = new Maze(10);
		maze2.fill(new Random(0));
        maze2.dfs();
        ArrayList<String> p2 = maze2.displayMaze(Maze.PATH);
		assertEquals(p1, p2);
	}
        
}