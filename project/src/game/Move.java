package game;

public class Move {
	private Robot robot;
	private Coord finalCoord;
	
	public Move(Robot robot, Coord finalCoord) {
		this.robot = robot;
		this.finalCoord = finalCoord;
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public Coord getfinalCoord() {
		return finalCoord;
	}

	public void setfinalCoord(Coord finalCoord) {
		this.finalCoord = finalCoord;
	}

	@Override
	public String toString() {
		return "Move [robot=" + robot + ", finalCoord=" + finalCoord.toString() + "]";
	}
	
	
	

}
