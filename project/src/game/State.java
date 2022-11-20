package game;

import java.util.ArrayList;
import java.util.Random;

import tab.Board;

public class State{

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
		for(Robot robot : robots) {
			if(robot.getName() == currentTarget.getcolor()) {
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

	public Robot getSpecialRobot() {
		return specialRobot;
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
	
	/***********************************************************************************************/
	
	public void moveRobot(Robot robot, Coord coord) {
		int x = coord.getX();
		int y = coord.getY();
		int xi = robot.getCoord().getX();
		int yi = robot.getCoord().getY();
		for(int i = 0; i < 4; i++) {
			if(robotsCoord[i].isSameCoord(robot.getCoord())) {
				robotsCoord[i] = coord;
				setRobotsCoord(robotsCoord);
			}
		}
		String[][] Board = board.getGameBoard();
		StringBuilder sr = new StringBuilder(Board[xi][yi]);
		sr.deleteCharAt(Board[xi][yi].length() - 1);
		Board[xi][yi] = sr.toString();
		Board[x][y] = Board[x][y] + robot.getName();
		robot.setCoord(coord);
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
	
	public ArrayList<Move> getValidMoves(State state) {
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
			Robot roboti = new Robot(robots[i].getName() , robots[i].getCoord());
			robotsCopy[i] = roboti;
			coordCopy[i] = robotsCopy[i].getCoord();
		}
		return new State(coordCopy, boardCopy, targetCopy, robotsCopy, specialRobotCopy);
	}
	
	/***********************************************************************************************/
	
	public State moveRobot2(Robot robot, Coord coord) {
		State newState = this.copy();
		int x = coord.getX();
		int y = coord.getY();
		int xi = robot.getCoord().getX();
		int yi = robot.getCoord().getY();
		for(int i = 0; i < 4; i++) {
			if(newState.robotsCoord[i].isSameCoord(robot.getCoord())) {
				newState.robotsCoord[i] = coord;
				newState.robots[i].setCoord(coord);
			}
		}
		String[][] Board = newState.getBoard().getGameBoard();
		StringBuilder sr = new StringBuilder(Board[xi][yi]);
		sr.deleteCharAt(Board[xi][yi].length() - 1);
		Board[xi][yi] = sr.toString();
		Board[x][y] = Board[x][y] + robot.getName();
		//robot.setCoord(coord);
		
		return newState;
	}

	/***********************************************************************************************/
	
	public boolean isOver() {
		boolean ok = false;
		if(specialRobot.getCoord().isSameCoord(currentTarget.getCoord())) {
			ok = true;
		}
		return ok;
	}
	

	/***********************************************************************************************/
	
	public boolean isSameState(State state) {
		boolean ok = true;
		for(int i = 0;i<4;i++) {
			if(!(state.getRobotsCoord()[i].isSameCoord(this.getRobotsCoord()[i]))) {
				ok = false;
				break;
			}
		}		
		return ok;
	}
	
	/***********************************************************************************************/
	 @Override
	    public boolean equals(Object o) {
	        // If the object is compared with itself then return true 
	        if (o == this) {
	            return true;
	        }
	        /* Check if o is an instance of Complex or not
	          "null instanceof [type]" also returns false */
	        if (!(o instanceof State)) {
	            return false;
	        }
	        // typecast o to Complex so that we can compare data members
	        State c = (State) o;
	         
	        // Compare the data members and return accordingly
	        return isSameState(c);
	    }
	 
		/***********************************************************************************************/
	 @Override
	 public String toString() {
		 String res = "";
		 for(int i=0;i<4;i++) {
			 res +=  this.robots[i].toString() + " ";
		 }
		 return res;
	 }

	
	public Coord[] getRobotsCoord() {
		return robotsCoord;
	}

	public void setRobotsCoord(Coord[] robotsCoord) {
		this.robotsCoord = robotsCoord;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Target getCurrentTarget() {
		return currentTarget;
	}

	public void setCurrentTarget(Target currentTarget) {
		this.currentTarget = currentTarget;
	}

	public Robot[] getRobots() {
		return robots;
	}

	public void setRobots(Robot[] robots) {
		this.robots = robots;
	}

	
	
	
	
}