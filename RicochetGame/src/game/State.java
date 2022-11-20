package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import tab.Board;
import util.AbstractListenableModel;

public class State extends AbstractListenableModel {

	private Coord[] robotsCoord = new Coord[4];
	private Board board;
	private Target currentTarget;
	private Robot[] robots = new Robot[4];
	private Robot specialRobot;
	

	/***********************************************************************************************/

	public State(Board board) {
		this.board = board;
		ArrayList<Coord> list = new ArrayList<>();
		char[] chars = { 'R', 'B', 'G', 'Y', 'T', 'Q', 'C', 'H' };
		boolean ok = true;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (char c : chars) {
					if (board.getGameBoard()[i][j].indexOf(c) != -1) {
						ok = false;
					}
				}
				if (ok == true) {
					Coord coord = new Coord(i, j);
					list.add(coord);
				} else {
					ok = true;
				}
			}
		}
		for (int k = 0; k < 4; k++) {
			char c = chars[k];
			Random rand = new Random();
			int random = rand.nextInt(list.size());
			int xC = list.get(random).getX();
			int yC = list.get(random).getY();
			while (board.getGameBoard()[xC][yC].length() == 3) {
				random = rand.nextInt(list.size());
				xC = list.get(random).getX();
				yC = list.get(random).getY();
			}
			board.getGameBoard()[xC][yC] = board.getGameBoard()[xC][yC] + c;
			this.robotsCoord[k] = new Coord(xC, yC);
			this.robots[k] = new Robot(c, robotsCoord[k]);
			list.remove(random);
		}

		ArrayList<Target> targets = board.findTargets();
		Random rand = new Random();
		currentTarget = targets.get(rand.nextInt(targets.size()));
		for (Robot robot : robots) {
			if (robot.getName() == currentTarget.getcolor()) {
				this.specialRobot = robot;
			}
		}
	}

	/***********************************************************************************************/

	public State(Coord[] robotsCoord, Board board, Target currentTarget, Robot[] robots, Robot specialRobot) {
		super();
		this.robotsCoord = robotsCoord;
		this.board = board;
		this.currentTarget = currentTarget;
		this.robots = robots;
		this.specialRobot = specialRobot;
	}

	/***********************************************************************************************/

	public boolean isOccupied(Coord coord) {
		boolean ok = false;
		for (int i = 0; i < 4; i++) {
			if (coord.isSameCoord(robotsCoord[i])) {
				ok = true;
			}
		}
		return ok;
	}

	/***********************************************************************************************/

	public Coord possibleMoves(Robot robot, char direction) {
		String[][] Board = board.getGameBoard();
		int x = robot.getCoord().getX();
		int y = robot.getCoord().getY();
		switch (direction) {
		case 'S':
			while (x < 15 && Board[x][y].indexOf('S') == -1 && !isOccupied(new Coord(x + 1, y))) {
				x++;
			}
			break;

		case 'N':
			while (x > 0 && Board[x][y].indexOf('N') == -1 && !isOccupied(new Coord(x - 1, y))) {
				x--;
			}
			break;

		case 'E':
			while (y < 15 && Board[x][y].indexOf('E') == -1 && !isOccupied(new Coord(x, y + 1))) {
				y++;
			}
			break;

		case 'W':
			while (y > 0 && Board[x][y].indexOf('W') == -1 && !isOccupied(new Coord(x, y - 1))) {
				y--;
			}
			break;
		}
		return new Coord(x, y);
	}

	public void next(List<State> listOfStates, int k) {
		List<Move> moves = moveMade(listOfStates);
		Move move = moves.get(k);
		Robot robot = move.getRobot();
		Coord coord = move.getfinalCoord();
		this.moveRobot(robot, coord);
	
	}

	/************************************************************************************************/

	public void playMoves(List<Move> moves) {
		for (Move move : moves) {
			Robot robot = move.getRobot();
			Coord coord = move.getfinalCoord();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Thread Interrupted");
			}
			this.moveRobot(robot, coord);
			System.out.println(this.toString());
			System.out.println("***********************************************************");
			
		}
	}
	
	/************************************************************************************************/
	
	public Move moveMade(State s1, State s2){
		Move move = new Move();
		for(int i = 0; i < 4; i++) {
			if(!s1.getRobotsCoord()[i].isSameCoord(s2.getRobotsCoord()[i])) {
				move = new Move(s1.getRobots()[i], s2.getRobotsCoord()[i]);
			}
		}
		return move;
	}

	/***********************************************************************************************/
	
	public List<Move> moveMade(List<State> listOfStates){
		List<Move> list = new ArrayList<>();
		for(int i = listOfStates.size() - 1; i > 0; i--) {
			Move move = moveMade(listOfStates.get(i), listOfStates.get(i-1));
			list.add(move);
		}
		return list;
	}
	
	/***********************************************************************************************/

	public void moveRobot(Robot robot, Coord coord) {
		for (int i = 0; i < 4; i++) {
			if (robotsCoord[i].isSameCoord(robot.getCoord())) {
				robotsCoord[i] = coord;
				setRobotsCoord(robotsCoord);
				robots[i].setCoord(coord);
				//fireChange();
			}
		}
	}

	/***********************************************************************************************/

	public ArrayList<Move> getValidMoves() {
		char[] direction = { 'N', 'S', 'E', 'W' };
		ArrayList<Move> listOfMoves = new ArrayList<>();
		for (Robot robot : robots) {
			for (char dir : direction) {
				Coord coordFinal = possibleMoves(robot, dir);
				if (!robot.getCoord().isSameCoord(coordFinal)) {
					listOfMoves.add(new Move(robot, coordFinal));
				}
			}
		}
		return listOfMoves;
	}

	/***********************************************************************************************/


	public ArrayList<Move> getOneRobotMoves(Robot robot) {
		char[] direction = { 'N', 'S', 'E', 'W' };
		ArrayList<Move> listOfMoves = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			char dir = direction[i];
			Coord coordFinal = possibleMoves(robot, dir);
			if (!coordFinal.isSameCoord(robot.getCoord())) {
				listOfMoves.add(new Move(robot, coordFinal));
			}
		}
		return listOfMoves;
	}

	/***********************************************************************************************/

	public State copy() {
		Robot specialRobotCopy = new Robot(specialRobot.getName(), specialRobot.getCoord());
		Target targetCopy = new Target(currentTarget.getcolor(), currentTarget.getShape(), currentTarget.getCoord());
		Board boardCopy = board.copyBoard();
		Robot[] robotsCopy = new Robot[robots.length];
		Coord[] coordCopy = new Coord[robotsCoord.length];
		for (int i = 0; i < robots.length; i++) {
			Robot roboti = new Robot(robots[i].getName(), robots[i].getCoord());
			robotsCopy[i] = roboti;
			coordCopy[i] = robotsCopy[i].getCoord();
		}
		return new State(coordCopy, boardCopy, targetCopy, robotsCopy, specialRobotCopy);
	}

	/***********************************************************************************************/

	public State moveRobot2(Robot robot, Coord coord) {
		State newState = this.copy();
		Robot[] robots = new Robot[4];
		robots = newState.getRobots();
		Coord[] robotsCoord = new Coord[4];
		robotsCoord = newState.getRobotsCoord();
		for (int i = 0; i < 4; i++) {
			if (robotsCoord[i].isSameCoord(robot.getCoord())) {
				robotsCoord[i] = coord;
				robots[i].setCoord(coord);
			}
		}
		newState.setRobots(robots);
		newState.setRobotsCoord(robotsCoord);
		if (robot.isSameRobot(specialRobot)) {
			newState.getSpecialRobot().setCoord(coord);
		}
		return newState;
	}

	/***********************************************************************************************/

	public void updateBoard(Move move) {
		int x = move.getfinalCoord().getX();
		int y = move.getfinalCoord().getY();
		int xi = move.getRobot().getCoord().getX();
		int yi = move.getRobot().getCoord().getY();
		String[][] Board = board.getGameBoard();
		StringBuilder sr = new StringBuilder(Board[xi][yi]);
		sr.deleteCharAt(Board[xi][yi].length() - 1);
		Board[xi][yi] = sr.toString();
		Board[x][y] = Board[x][y] + move.getRobot().getName();
	}


	/**************************************************************************************************/

	public boolean isOver() {
		boolean ok = false;
		if (specialRobot.getCoord().isSameCoord(currentTarget.getCoord())) {
			ok = true;
		}
		return ok;
	}

	/***********************************************************************************************/

	public boolean isSameState(State state) {
		boolean ok = true;
		for (int i = 0; i < 4; i++) {
			if (!(state.getRobotsCoord()[i].isSameCoord(this.getRobotsCoord()[i]))) {
				ok = false;
				break;
			}
		}
		return ok;
	}

	/***********************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(robotsCoord);
		return result;
	}

	/***********************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (!Arrays.equals(robotsCoord, other.robotsCoord))
			return false;
		return true;
	}

	/***********************************************************************************************/

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < 4; i++) {
			res += this.robotsCoord[i].toString() + " ";
		}
		return res;
	}

	//GETTERS AND SETTERS
	
	/**
	 * @return the robotsCoord
	 */
	public Coord[] getRobotsCoord() {
		return robotsCoord;
	}

	/**
	 * @param robotsCoord the robotsCoord to set
	 */
	public void setRobotsCoord(Coord[] robotsCoord) {
		this.robotsCoord = robotsCoord;
		fireChange();
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return the currentTarget
	 */
	public Target getCurrentTarget() {
		return currentTarget;
	}

	/**
	 * @param currentTarget the currentTarget to set
	 */
	public void setCurrentTarget(Target currentTarget) {
		this.currentTarget = currentTarget;
	}

	/**
	 * @return the robots
	 */
	public Robot[] getRobots() {
		return robots;
	}

	/**
	 * @param robots the robots to set
	 */
	public void setRobots(Robot[] robots) {
		this.robots = robots;
	}

	/**
	 * @return the specialRobot
	 */
	public Robot getSpecialRobot() {
		return specialRobot;
	}

	/**
	 * @param specialRobot the specialRobot to set
	 */
	public void setSpecialRobot(Robot specialRobot) {
		this.specialRobot = specialRobot;
	}



}