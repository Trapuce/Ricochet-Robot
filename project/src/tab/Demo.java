package tab;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import algo.*;
import game.*;



public class Demo {

	public static void main(String[] args) {

		String[][] QUAD_1A = { { "NW", "N", "N", "N", "NE", "NW", "N", "N" },
								{ "W", "S", "X", "X", "X", "X", "SEYH", "W" }, 
								{ "WE", "NWGT", "X", "X", "X", "X", "N", "X" },
								{ "W", "X", "X", "X", "X", "X", "X", "X" }, 
								{ "W", "X", "X", "X", "X", "X", "S", "X" },
								{ "SW", "X", "X", "X", "X", "X", "NEBQ", "W" }, 
								{ "NW", "X", "E", "SWRC", "X", "X", "X", "S" },
								{ "W", "X", "X", "N", "X", "X", "E", "NWX" }, };

		String[][] QUAD_1B = { { "NW", "NE", "NW", "N", "NS", "N", "N", "N" },
				{ "W", "S", "X", "E", "NWRC", "X", "X", "X" }, { "W", "NEGT", "W", "X", "X", "X", "X", "X" },
				{ "W", "X", "X", "X", "X", "X", "SEYH", "W" }, { "W", "X", "X", "X", "X", "X", "N", "X" },
				{ "SW", "X", "X", "X", "X", "X", "X", "X" }, { "NW", "X", "E", "SWBQ", "X", "X", "X", "S" },
				{ "W", "X", "X", "N", "X", "X", "E", "NWX" }, };

		String[][] QUAD_2A = { { "NW", "N", "N", "NE", "NW", "N", "N", "N" },
				{ "W", "X", "X", "X", "X", "E", "SWBC", "X" }, { "W", "S", "X", "X", "X", "X", "N", "X" },
				{ "W", "NEYT", "W", "X", "X", "S", "X", "X" }, { "W", "X", "X", "X", "E", "NWGQ", "X", "X" },
				{ "W", "X", "SERH", "W", "X", "X", "X", "X" }, { "SW", "X", "N", "X", "X", "X", "X", "S" },
				{ "NW", "X", "X", "X", "X", "X", "E", "NWX" }, };

		String[][] QUAD_2B = { { "NW", "N", "N", "N", "NE", "NW", "N", "N" },
				{ "W", "X", "SERH", "W", "X", "X", "X", "X" }, { "W", "X", "N", "X", "X", "X", "X", "X" },
				{ "WE", "SWGQ", "X", "X", "X", "X", "S", "X" }, { "SW", "N", "X", "X", "X", "E", "NWYT", "X" },
				{ "NW", "X", "X", "X", "X", "S", "X", "X" }, { "W", "X", "X", "X", "X", "NEBC", "W", "S" },
				{ "W", "X", "X", "X", "X", "X", "E", "NWX" }, };

		String[][] QUAD_3A = { { "NW", "N", "N", "NE", "NW", "N", "N", "N" },
				{ "W", "X", "X", "X", "X", "SEGH", "W", "X" }, { "WE", "SWRQ", "X", "X", "X", "N", "X", "X" },
				{ "SW", "N", "X", "X", "X", "X", "S", "X" }, { "NW", "X", "X", "X", "X", "E", "NWYC", "X" },
				{ "W", "X", "S", "X", "X", "X", "X", "X" }, { "W", "X", "NEBT", "W", "X", "X", "X", "S" },
				{ "W", "X", "X", "X", "X", "X", "E", "NWX" }, };

		String[][] QUAD_3B = { { "NW", "N", "NS", "N", "NE", "NW", "N", "N" },
				{ "W", "E", "NWYC", "X", "X", "X", "X", "X" }, { "W", "X", "X", "X", "X", "X", "X", "X" },
				{ "W", "X", "X", "X", "X", "E", "SWBT", "X" }, { "SW", "X", "X", "X", "S", "X", "N", "X" },
				{ "NW", "X", "X", "X", "NERQ", "W", "X", "X" }, { "W", "SEGH", "W", "X", "X", "X", "X", "S" },
				{ "W", "N", "X", "X", "X", "X", "E", "NWX" }, };

		String[][] QUAD_4A = { { "NW", "N", "N", "NE", "NW", "N", "N", "N" },
				{ "W", "X", "X", "X", "X", "X", "X", "X" }, { "W", "X", "X", "X", "X", "SEBH", "W", "X" },
				{ "W", "X", "S", "X", "X", "N", "X", "X" }, { "SW", "X", "NEGC", "W", "X", "X", "X", "X" },
				{ "NW", "S", "X", "X", "X", "X", "E", "SWRT" }, { "WE", "NWYQ", "X", "X", "X", "X", "X", "NS" },
				{ "W", "X", "X", "X", "X", "X", "E", "NWX" }, };

		String[][] QUAD_4B = { { "NW", "N", "N", "NE", "NW", "N", "N", "N" },
				{ "WE", "SWRT", "X", "X", "X", "X", "S", "X" }, { "W", "N", "X", "X", "X", "X", "NEGC", "W" },
				{ "W", "X", "X", "X", "X", "X", "X", "X" }, { "W", "X", "SEBH", "W", "X", "X", "X", "S" },
				{ "SW", "X", "N", "X", "X", "X", "E", "NWYQ" }, { "NW", "X", "X", "X", "X", "X", "X", "S" },
				{ "W", "X", "X", "X", "X", "X", "E", "NWX" }, };
		
		String[][]Copy_Q1A = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q1A[i][j] = QUAD_1A[i][j];
			}
		}
		String[][]Copy_Q1B = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q1B[i][j] = QUAD_1B[i][j];
			}
		}
		String[][]Copy_Q2A = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q2A[i][j] = QUAD_2A[i][j];
			}
		}
		String[][]Copy_Q2B = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q2B[i][j] = QUAD_2B[i][j];
			}
		}
		String[][]Copy_Q3A = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q3A[i][j] = QUAD_3A[i][j];
			}
		}
		String[][]Copy_Q3B = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q3B[i][j] = QUAD_3B[i][j];
			}
		}
		String[][]Copy_Q4A = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q4A[i][j] = QUAD_4A[i][j];
			}
		}
		String[][]Copy_Q4B = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0; j<8;j++) {
				Copy_Q4B[i][j] = QUAD_4B[i][j];
			}
		}
		
		
		Quarter QUAD1 = new Quarter(Copy_Q1A, Copy_Q1B);
		Quarter QUAD2 = new Quarter(Copy_Q2A, Copy_Q2B);
		Quarter QUAD3 = new Quarter(Copy_Q3A, Copy_Q3B);
		Quarter QUAD4 = new Quarter(Copy_Q4A, Copy_Q4B);
	
		Quarter[] quaters = {QUAD1, QUAD2, QUAD3, QUAD4};
		
		Board board = new Board(quaters);
		
		
		
		//gameBoard.printBoard();
		System.out.println("*******************************");
		State state = new State(board);
		state.getBoard().printBoard();
		for(Robot robot : state.getRobots()) {
			System.out.println(robot.toString());
		}
		System.out.println("*******************************");
		
		Robot winnerRobot = state.getSpecialRobot();
		System.out.println(winnerRobot.toString());
		System.out.println(state.getCurrentTarget().toString());
		
		/*Coord coord = state.possibleMoves(winnerRobot, 'E');
		System.out.println("*******************************");
		State newState = state.moveRobot2(winnerRobot, coord);
		newState.getBoard().printBoard();
		System.out.println("*******************************");
		for(Robot robot : newState.getRobots()) {
			System.out.println(robot.toString());
		}
		System.out.println("*******************************");
		state.getBoard().printBoard();
		System.out.println("*******************************");*/
		AStarMax astar = new AStarMax(state);
		int [][] table = astar.heuristicTable1(state.getCurrentTarget());
		for(int i =0;i<16;i++) {
			for(int j=0;j<16;j++) {
				System.out.print(table[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		
		
		//AStarMax astar = new AStarMax(state);
		for(Coord coord : astar.aStarMax()) {
			System.out.println(coord.toString());
		}
		
		/*HashMap<Coord, Integer> map = new HashMap<>();
		Comparator<Coord> comparator = astar.getCoordComparator(map);
		PriorityQueue<Coord> queue = new NoDuplicateQueue<>(1,comparator);
		for(int i=0;i<5;i++) {
			for(int j=0; j<5;j++) {
				Coord coord3 = new Coord(i,j);
				map.put(coord3, i+j);
				queue.add(coord3);
				System.out.println(coord3.toString() + (i+j));
			}
		}
		Coord c = new Coord(0,0);
		map.put(c,10);
		queue.add(c);
		while(!queue.isEmpty()){
	        System.out.print(" "+queue.poll());
	        }*/
		
		
		
		
		
		
	}
}