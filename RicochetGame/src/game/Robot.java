package game;

public class Robot {
	private char name;
	private Coord coord;
	
	public Robot(char name, Coord coord) {
		super();
		this.name = name;
		this.coord = coord;
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return "Robot [name=" + name + ", coord=" + coord.toString() + "]";
	}
	
	public boolean isSameRobot(Robot robot) {
		boolean ok = false;
		if(robot.getName() == this.getName() && robot.getCoord().isSameCoord(this.getCoord())) {
			ok = true;
		}
		return ok;
	}
	
	

}
