package game;

public class Coord {
	private int x,y;
	public Coord(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isSameCoord(Coord other) {
		return (this.x == other.getX() && this.y == other.getY());
	}

	public boolean equals(int otherX, int otherY) {
		return (this.x == otherX && this.y == otherY);
	}
	
	public String toString() {
		return "("+this.x+","+this.y+")";
	}

}
