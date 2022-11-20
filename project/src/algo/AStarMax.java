package algo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import game.Coord;
import game.Move;
import game.Robot;
import game.State;
import game.Target;

public class AStarMax extends AStar {

	public AStarMax(State state) {
		super(state);
	}

	/*public int[][] heuristicTable(Target target){
		int n = 5;
		Robot r = state.getSpecialRobot();
		String[][] Board = state.getBoard().getGameBoard();
		int[][] table = new int[16][16];
		ArrayList<Coord>[] list =  new ArrayList[n];
		for(int i=0;i<n;i++) {
			list[i] = new ArrayList<Coord>();
		}
		int x = target.getCoord().getX();
		int y = target.getCoord().getY();
		for (Coord c : state.getRobotsCoord()) {
			table[c.getX()][c.getY()] = -1;
		}
		table[r.getCoord().getX()][r.getCoord().getY()] = 0;

		if (Board[x][y].indexOf('N') != -1) {
			int xs = x;
			while (xs < 15 && Board[xs][y].indexOf('S') == -1 && table[xs + 1][y] != -1) {
				xs++;
				table[xs][y] = 1;
				list[0].add(new Coord(xs, y));
			}
		}
		if (Board[x][y].indexOf('S') != -1) {
			int xn = x;
			while (xn > 0 && Board[xn][y].indexOf('N') == -1 && table[xn - 1][y] != -1) {
				xn--;
				table[xn][y] = 1;
				list[0].add(new Coord(xn, y));
			}
		}
		if (Board[x][y].indexOf('W') != -1) {
			int ye = y;
			while (ye < 15 && Board[x][ye].indexOf('E') == -1 && table[x][ye + 1] != -1) {
				ye++;
				table[x][ye] = 1;
				list[0].add(new Coord(x, ye));
			}
		}
		if (Board[x][y].indexOf('E') != -1) {
			int yw = y;
			while (yw > 0 && Board[x][yw].indexOf('W') == -1 && table[x][yw - 1] != -1) {
				yw--;
				table[x][yw] = 1;
				list[0].add(new Coord(x, yw));
			}
		}

		for (int k = 1; k < 5; k++) {
			for (Coord c : list[k-1]) {
				int xk = c.getX();
				int yk = c.getY();
				int xn = xk;
				int xs = xk;
				int ye = yk;
				int yw = yk;
				while (xs < 15 && Board[xs][yk].indexOf('S') == -1 && table[xs + 1][yk] == 0) {
					xs++;
					table[xs][yk] = k+1;
					list[k].add(new Coord(xs, yk));
				}
				while (xn > 0 && Board[xn][yk].indexOf('N') == -1 && table[xn - 1][yk] == 0) {
					xn--;
					table[xn][yk] = k+1;
					list[k].add(new Coord(xn, yk));
				}
				while (ye < 15 && Board[xk][ye].indexOf('E') == -1 && table[xk][ye + 1] == 0) {
					ye++;
					table[xk][ye] = k+1;
					list[k].add(new Coord(xk, ye));
				}
				while (yw > 0 && Board[xk][yw].indexOf('W') == -1 && table[xk][yw - 1] == 0) {
					yw--;
					table[xk][yw] = k+1;
					list[k].add(new Coord(xk, yw));
				}
			}
		}
		table[x][y] = 0;
		return table;
	}*/

	public int[][] heuristicTable1(Target target) {
		int n = 5;
		String[][] Board = state.getBoard().getGameBoard();
		ArrayList<ArrayList<Coord>> list = new ArrayList<>(n);
		int[][] table = new int[16][16];
		ArrayList<Coord> list0 = new ArrayList<>();
		ArrayList<Coord> list1 = new ArrayList<>();
		ArrayList<Coord> list2 = new ArrayList<>();
		ArrayList<Coord> list3 = new ArrayList<>();
		ArrayList<Coord> list4 = new ArrayList<>();
		ArrayList<Coord> list5 = new ArrayList<>();
		list0.add(target.getCoord());
		list.add(list0); list.add(list1); list.add(list2); list.add(list3); list.add(list4); list.add(list5);
		for (int k = 0; k < 5; k++) {
			for (Coord c : list.get(k)) {
				int xk = c.getX();
				int yk = c.getY();
				int xn = xk;
				int xs = xk;
				int ye = yk;
				int yw = yk;
				while (xs < 15 && Board[xs][yk].indexOf('S') == -1 && table[xs + 1][yk] == 0) {
					xs++;
					table[xs][yk] = k+1;
					list.get(k+1).add(new Coord(xs, yk));
				}
				while (xn > 0 && Board[xn][yk].indexOf('N') == -1 && table[xn - 1][yk] == 0) {
					xn--;
					table[xn][yk] = k+1;
					list.get(k+1).add(new Coord(xn, yk));
				}
				while (ye < 15 && Board[xk][ye].indexOf('E') == -1 && table[xk][ye + 1] == 0) {
					ye++;
					table[xk][ye] = k+1;
					list.get(k+1).add(new Coord(xk, ye));
				}
				while (yw > 0 && Board[xk][yw].indexOf('W') == -1 && table[xk][yw - 1] == 0) {
					yw--;
					table[xk][yw] = k+1;
					list.get(k+1).add(new Coord(xk, yw));
				}
			}
		}
		table[target.getCoord().getX()][target.getCoord().getY()] = 0;
		return table;
	}
	

	public List<Coord> aStarMax() {
		Coord start = state.getSpecialRobot().getCoord();
		Coord goal = state.getCurrentTarget().getCoord();
		int[][] table = this.heuristicTable1(state.getCurrentTarget());
		int tentativeGScore;
		Comparator<Coord> comparator = getCoordComparator(fScore);
		PriorityQueue<Coord> openQueue = new NoDuplicateQueue<>(1, comparator);
		Set<Coord> openSet = new HashSet<>();
		openQueue.add(start);
		openSet.add(start);
		gScore.put(start, 0);
		fScore.put(start, table[start.getX()][start.getY()]);

		while (!openQueue.isEmpty()) {
			System.out.println(openQueue.toString());
			System.out.println("*******************************");
			Coord current = openQueue.peek();
			if (current.isSameCoord(goal)) {
				return reconstructPath(cameFrom, current);
			}
			openQueue.remove(current);
			state.moveRobot(state.getSpecialRobot(), current);
			ArrayList<Move> listOfMoves = state.getOneRobotMoves(state.getSpecialRobot());
			ArrayList<Coord> listOfCoord = new ArrayList<Coord>();
			for (Move move : listOfMoves) {
				listOfCoord.add(move.getfinalCoord());
			}
			for (Coord neighbor : listOfCoord) {
				tentativeGScore = gScore.get(current) + 1;
				if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, tentativeGScore);
					fScore.put(neighbor, tentativeGScore + table[neighbor.getX()][neighbor.getY()]);
					boolean ok = false;
					for (Coord c : openSet) {
						if (neighbor.isSameCoord(c)) {
							ok = true;
							break;
						}
					}
					if (ok == false) {
						openQueue.add(neighbor);
						openSet.add(neighbor);
					}
				}
			}
		}

		System.out.println("No Solution");
		return Collections.emptyList();

	}

}
