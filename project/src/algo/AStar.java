package algo;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import game.Coord;
import game.Move;
import game.State;

public class AStar {

	protected HashMap<Coord, Coord> cameFrom = new HashMap<>();
	protected HashMap<Coord, Integer> gScore = new HashMap<>();
	protected HashMap<Coord, Integer> fScore = new HashMap<>();
	protected State state;

	public AStar(State state) {
		this.state = state;

	}

	public List<Coord> reconstructPath(Map<Coord, Coord> cameFrom, Coord current) {
		ArrayList<Coord> totalPath = new ArrayList<>();
		totalPath.add(current);
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			totalPath.add(current);
		}
		return totalPath;
	}

	// distance = heuristic function
	public int distance(Coord startCoord, Coord nextCoord) {
		int x = (int) nextCoord.getX() - (int) startCoord.getX();
		int y = (int) nextCoord.getY() - (int) startCoord.getY();
		return Math.abs(x) + Math.abs(y);
	}

	public Comparator<Coord> getCoordComparator(HashMap<Coord, Integer> fScore) {
		return (Coord p1, Coord p2) -> {
			int fScoreP1 = fScore.get(p1);
			int fScoreP2 = fScore.get(p2);
			return Integer.compare(fScoreP1, fScoreP2);
		};
	}

	public List<Coord> aStar() {
		final Coord start = state.getSpecialRobot().getCoord();
		final Coord goal = state.getCurrentTarget().getCoord();
		int tentativeGScore;
		Comparator<Coord> comparator = getCoordComparator(fScore);
		PriorityQueue<Coord> openQueue = new NoDuplicateQueue<>(1, comparator);
		Set<Coord> openSet = new HashSet<>();
		openQueue.add(start);
		openSet.add(start);
		gScore.put(start, 0);
		fScore.put(start, distance(start, goal));

		while (!openQueue.isEmpty()) {
			System.out.println(openQueue.toString());
			final Coord current = openQueue.poll();
			if (current.isSameCoord(goal)) {
				return reconstructPath(cameFrom, current);
			}

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
					fScore.put(neighbor, tentativeGScore + distance(neighbor, goal));
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
