import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Maze {

	static final int WHITE = 1;
	static final int GREY = 2;
	static final int BLACK = 3;
	static final int PATH = 1;
	static final int PATHDFS = 2;
	static final int PATHBFS = 3;

	final int SIZE;
	int[][] matrix;
	Vertex[][] graph;
	Stack<Vertex> stack;
	Node[] list;
	int totalCells;

	public Maze(int size) {
		this.SIZE = size;
		totalCells = SIZE * SIZE;
		matrix = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrix[i][j] = 0;
			}
		}
		list = new Node[totalCells];
		for (int i = 0; i < totalCells; i++) {
			list[i] = null;
		}
		stack = new Stack<Vertex>();
		graph = new Vertex[SIZE][SIZE];
	}

	// method to display Maze
	public void printMaze(int path) {
		ArrayList<String> maze = displayMaze(path);
		for (String s : maze) {
			System.out.println(s);
		}
	}

	
	// method to display Maze
	public ArrayList<String> displayMaze(int path) {
		ArrayList<String> result = new ArrayList<>();

		StringBuffer s = new StringBuffer();

		for (int i = 0; i < SIZE; i++) {
			s = new StringBuffer();
			for (int j = 0; j < SIZE; j++) {
				if (graph[j][i].hasNorthWall) {
					if (graph[j][i] == graph[0][0]) {
						s.append("+ ");
					} else {
						s.append("+-");
					}
				} else { // open walls
					s.append("+ ");
				}
			}
			s.append("+");
			result.add(s.toString());

			s = new StringBuffer();
			for (int j = 0; j < SIZE; j++) {

				if (graph[j][i].hasWestWall) {
					s.append("|");
				} else {
					s.append(" ");
				}

				if (path == PATH) {
					if (graph[j][i].isInPath) {
						s.append("#");
					} else {
						s.append(" ");
					}
				} else if (path == PATHBFS) {
					if (graph[j][i].visitBfsOrder <= graph[SIZE - 1][SIZE - 1].visitBfsOrder) {
						s.append(graph[j][i].visitBfsOrder % 10);
					} else {
						s.append(" ");
					}
				} // else PATHBFS
				else {
					if (graph[j][i].dtime <= graph[SIZE - 1][SIZE - 1].dtime) {
						s.append(graph[j][i].visitedOrder % 10);
					} else {
						s.append(" ");
					}
				} // else PATHDFS

			}
			s.append("|");
			result.add(s.toString());
		}
		s = new StringBuffer();
		for (int j = 0; j < SIZE; j++) {
			if (j == SIZE - 1)
				s.append("+ ");
			else
				s.append("+-");

		}
		s.append("+");
		result.add(s.toString());
		return result;
	}

	// method to generate maze
	public void mazeGenerator(Random generator) {
		Vertex currentCell;
		int visitedCells;
		currentCell = graph[0][0];
		currentCell.isVisited = true;
		visitedCells = 1;

		while (visitedCells < totalCells) {
			if (validNeighbors(currentCell) != 0) {
				Vertex vtx = null;

				int random = generator.nextInt(validNeighbors(currentCell));
				vtx = currentCell.neighbors.get(random);

				removeWall(currentCell, vtx);

				Node newNode = new Node(vtx);
				Node currentNode = list[currentCell.label - 1];
				if (currentNode == null) {
					currentNode = newNode;
					list[currentCell.label - 1] = currentNode;
				} else {
					while (currentNode.next != null) {
						currentNode = currentNode.next;
					}
					currentNode.next = newNode;
				}

				Node newNode2 = new Node(currentCell);
				currentNode = list[vtx.label - 1];
				if (currentNode == null) {
					currentNode = newNode2;
					list[vtx.label - 1] = newNode2;
				} else {
					while (currentNode.next != null) {
						currentNode = currentNode.next;
					}
					currentNode.next = newNode2;
				}
				stack.push(currentCell);
				currentCell = vtx;
				currentCell.isVisited = true;
				visitedCells++;
			} else {
				if (!stack.isEmpty()) {

					Vertex v = stack.pop();
					currentCell = v;
				}
			}

		}
	}

	// bf
	int visitBfsOrd;

	public void bfs() {
		Vertex s = this.graph[0][0];
		s.color = GREY;
		s.distance = 0;
		s.parent = null;

		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(s);

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			Node v = list[u.label - 1];
			while (v != null) {
				if (v.vxt.color == WHITE) {
					v.vxt.color = GREY;
					visitBfsOrd = visitBfsOrd + 1;
					v.vxt.visitBfsOrder = visitBfsOrd;
					v.vxt.distance = u.distance + 1;
					v.vxt.parent = u;
					queue.add(v.vxt);

				}
				v = v.next;
			}
		}
	}

	// dfs
	int time;
	int visitOrd;

	public void dfs() {
		time = 0;
		Vertex currentNode;
		for (int i = 0; i < totalCells; i++) {

			currentNode = graph[i / SIZE][i % SIZE];
			if (currentNode.color == WHITE)
				dfsVisit(currentNode);
		}
	}

	public void dfsVisit(Vertex u) {
		time = time + 1;
		u.visitedOrder = visitOrd;
		visitOrd = visitOrd + 1;
		u.dtime = time;
		u.color = GREY;
		Node v = list[u.label - 1];
		while (v != null) {
			if (v.vxt.color == WHITE) {
				v.vxt.parent = u;
				dfsVisit(v.vxt);
			}
			v = v.next;
		}
		u.color = BLACK;
		time = time + 1;
		u.ftime = time;
	}

	// method to print path
	public void printPath(Vertex startVtx, Vertex endVtx) {

		if (startVtx.label == endVtx.label)
			System.out.print(startVtx.label + " ");
		else if (endVtx.parent == null)
			System.out.print(" No path exists");
		else
			printPath(startVtx, endVtx.parent);
		endVtx.isInPath = true;
		System.out.print(endVtx.label + " ");
	}

	public void printAdjList(Node[] graph) {
		Node currentNode = null;
		for (int i = 0; i < totalCells; i++) {
			System.out.print("\nRow : [" + (i + 1) + "]");
			currentNode = graph[i];
			while (currentNode != null) {
				System.out.print("->" + currentNode.vxt.label);
				currentNode = currentNode.next;
			}
		}
	}

	// method to check if neighbors are valid
	public int validNeighbors(Vertex v) {
		Iterator<Vertex> it = v.neighbors.iterator();
		while (it.hasNext()) {
			Vertex vtx = it.next();
			if (vtx.isVisited) {
				it.remove();
			}
		}
		int neighbors = 0;
		// loop to check neighbors that have already visited
		for (int x = 0; x < v.neighbors.size(); x++) {
			if (!v.neighbors.get(x).isVisited) {
				neighbors++;
			}
		}
		return neighbors;
	}

	// method to remove walls
	public void removeWall(Vertex current, Vertex next) {

		if (current.label + SIZE == next.label) {
			// remove south wall
			current.hasSouthWall = false;
			next.hasNorthWall = false;
		} else if (current.label + 1 == next.label) {
			// remove east wall
			current.hasEastWall = false;
			next.hasWestWall = false;
		} else if (current.label - 1 == next.label) {
			// remove the west wall
			current.hasWestWall = false;
			next.hasEastWall = false;
		} else if (current.label - SIZE == next.label) {
			// remove the northern wall
			current.hasNorthWall = false;
			next.hasSouthWall = false;
		}

		current.neighbors.remove(next);
		next.neighbors.remove(current);
	}

	// assign attributes to vertex
	public void fill(Random generator) {
		int vertexNumber = 1;

		// This loop creates a new vertex
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Vertex v = new Vertex(j, i);
				graph[j][i] = v;
			}
		}

		// adds values to vertex
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				graph[j][i].label = vertexNumber;
				graph[j][i].parent = null;
				vertexNumber++;
			}
		}

		// assigns the neighbors
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				assignNeighbors(graph[j][i]);
			}
		}
		mazeGenerator(generator);
	}

	// assign neighbors to the special cells
	public void assignNeighbors(Vertex v) {
		// cell north of current cell
		if (v.y != 0) {
			v.neighbors.add(graph[v.x][v.y - 1]);
		}

		// cell south of the current cell
		if (v.y != (SIZE - 1)) {
			v.neighbors.add(graph[v.x][v.y + 1]);
		}

		// cell left of the current cell
		if (v.x != 0) {
			v.neighbors.add(graph[v.x - 1][v.y]);
		}

		// right of the current
		if (v.x != SIZE - 1) {
			v.neighbors.add(graph[v.x + 1][v.y]);
		}
	}

	// inner class
	class Node {
		Vertex vxt;
		Node next = null;

		public Node() {
		}

		public Node(Vertex x) {
			vxt = x;
		}
	}

	// inner class
	class Vertex {
		int x;
		int y;
		int visitBfsOrder;
		int distance;
		int label;
		int visitNum = 0;
		int color = WHITE;
		boolean isVisited = false;
		boolean isInPath = false;
		int dtime;
		int ftime;
		Vertex parent;
		int visitedOrder = 0;

		boolean hasNorthWall = true;
		boolean hasSouthWall = true;
		boolean hasEastWall = true;
		boolean hasWestWall = true;
		boolean hasAllWalls = true;
		ArrayList<Vertex> neighbors = new ArrayList<Vertex>();

		public Vertex(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
