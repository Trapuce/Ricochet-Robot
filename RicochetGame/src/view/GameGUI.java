package view;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algo.AStarFinalForm;
import game.State;
import tab.Builder;

public class GameGUI extends JFrame{
	static final long serialVersionUID = 1L;
	private State state;
	private GameView view;
	private JButton buttonReset, buttonNext;
	List<State> listOfStates;
	int k = 0;
	int m;
	
	public GameGUI(State state) {
		super("Ricochet Robots");
		this.state = state;
		AStarFinalForm astar = new AStarFinalForm(state);
		listOfStates = astar.aStarFF();
		m = listOfStates.size() - 1;
		
		JPanel panelSouth = new JPanel();
		panelSouth.setSize(new Dimension(840,280));
		panelSouth.setLayout(new GridLayout(1,2));
		buttonReset = new JButton("Reset");
		buttonNext = new JButton("Next");
		buttonNext.setSize(new Dimension(180, 90));
		buttonReset.setSize(new Dimension(180, 90));
		//buttonReset.setBackground(Color.WHITE);
		
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		panelSouth.add(buttonNext);		
		panelSouth.add(buttonReset);
		container.add(panelSouth, BorderLayout.SOUTH);
		//buttonNext.setBounds(250, 660, 180, 90);
		//buttonReset.setBounds(400, 660, 180, 90);
		buttonReset.addActionListener(this::reset);
		this.addKeyListener(new Next());
		buttonNext.addActionListener(this::next);
		
		
		view = new GameView(state);
		view.setM(Integer.toString(m));
		container.add(view, BorderLayout.CENTER);
		
		this.pack();
		this.setSize(840, 820);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	public void next(ActionEvent e) {
		m = m-1;
		if(k >= listOfStates.size()-1) {
			view.setM("Robot at target location. Reset");
			repaint();
		}
		try {
			view.setM(Integer.toString(m));
			state.next(listOfStates, k);
			k++;
		} catch (IndexOutOfBoundsException e1) {
			view.setM("Robot at target location. Reset");
			repaint();
		}
	}
	
	public void reset(ActionEvent e) {
		k = 0;
		this.remove(view);
		Builder builder = new Builder();
		state = new State(builder.board);
		AStarFinalForm astar = new AStarFinalForm(state);
		listOfStates = astar.aStarFF();
		m = listOfStates.size() - 1;
		view = new GameView(state);
		view.setM(Integer.toString(m));
		this.getContentPane().add(view);
		SwingUtilities.updateComponentTreeUI(this);
		
	}
	
	/*public void start(ActionEvent e) {
		AStarFinalForm astar = new AStarFinalForm(state);
		List<State> listOfStates = astar.aStarFF();
		List<Move> listOfMoves = state.moveMade(listOfStates);
		state.playMoves(listOfMoves);
	}*/

	

	

	public class Next implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if(k >= listOfStates.size()-1) {
				view.setM("Robot at target location. Reset");
				repaint();
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				m--;
				try {
					view.setM(Integer.toString(m));
					state.next(listOfStates, k);
					k++;
				} catch (IndexOutOfBoundsException e1) {
					System.out.println("Robot at target location. \n Press Reset button");
					repaint();
				}
			}
			repaint();
		}
		@Override
		public void keyReleased(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}


}
