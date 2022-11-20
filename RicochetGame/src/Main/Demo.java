package Main;

import algo.AStarFinalForm;
import game.Robot;
import game.State;
import tab.Board;
import tab.Builder;
import view.GameGUI;


public class Demo {

	public static void main(String[] args) {
		
		Builder builder = new Builder();
		
		
		System.out.println("*******************************");
		State state = new State(builder.board);
		//State newState = state.copy();
		state.getBoard().printBoard();
		for(Robot robot : state.getRobots()) {
			System.out.println(robot.toString());
		}
		System.out.println(state.getRobotsCoord().toString());
		System.out.println("*******************************");
		
		//Robot winnerRobot = state.getSpecialRobot();
		//System.out.println(winnerRobot.toString());
		System.out.println(state.getCurrentTarget().toString());
		/*Coord coord = newState.possibleMoves(winnerRobot, 'W');
		newState.moveRobot(winnerRobot, coord);
		//State newState = state.moveRobot2(winnerRobot, coord);
		//System.out.println(newState.getSpecialRobot().toString());
		//System.out.println(state.isSameState(newState));
		//newState.getBoard().printBoard();
		for(Robot robot : newState.getRobots()) {
			System.out.println(robot.toString());
		}
		System.out.println("*******************************");
		for(Robot robot : state.getRobots()) {
			System.out.println(robot.toString());
		}
		Coord coord = state.possibleMoves(winnerRobot, 'E');
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
		AStarFinalForm astar = new AStarFinalForm(state);
		int [][] table = astar.heuristicTable(state.getCurrentTarget());
		for(int i =0;i<16;i++) {
			for(int j=0;j<16;j++) {
				System.out.print(table[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		//AStar astar = new AStar(state);
		
		//AStarMax astar = new AStarMax(state);
		//long startTime = System.currentTimeMillis();
	//System.out.println("*******************************");
		/*for(State s : astar.aStarFF()) {
			System.out.println(s.toString());
		}
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		 System.out.println("Elapsed Time in milli seconds: " + duration);*/
		 
		//List<Move> list = state.moveMade(astar.aStarFF());
		/* for(int i = 0; i < list.size(); i++) {
			 System.out.println("Move " + (i+1) + " " + list.get(i).toString());
		 }*/
		
		//state.playMoves(list);
		
		 GameGUI gui = new GameGUI(state);
		
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
		
		/*ArrayList<Move> listOfMoves = state.getOneRobotMoves(winnerRobot);
		for (Move move : listOfMoves) {
			Robot robot = move.getRobot();
			Coord coord = move.getfinalCoord();
			State newState = state.moveRobot2(robot, coord);
			System.out.println(state.toString());
			System.out.println(newState.toString());
			System.out.println(state.isSameState(newState));
			System.out.println("*******************************");
		}*/
		
		
		
	}
}