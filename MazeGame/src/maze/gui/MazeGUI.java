package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import maze.logic.Game.Direction;
import maze.logic.Game.GameMode;
import maze.logic.Game.GameState;
import maze.logic.Maze;
import maze.logic.RandomMazeGenerator;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Font;

public class MazeGUI {

	private JFrame mazeGameMenu;
	private JTextField mazeDimensionTextField;
	private JTextField dragonNumberTextField;
	private JButton upButton;
	private JButton downButton;
	private JButton rightButton;
	private JButton leftButton;
	private JLabel instructionsLabel;
	private static final String STATIONARY_DRAGON_TEXT = "Stationary";
	private static final String RANDOM_DRAGON_TEXT = "Random";
	private static final String SLEEPING_DRAGON_TEXT = "Sleeping";
	private Maze maze;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeGUI window = new MazeGUI();
					window.mazeGameMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MazeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		mazeGameMenu = new JFrame();
		mazeGameMenu.setTitle("Maze Game");
		mazeGameMenu.setResizable(false);
		mazeGameMenu.setBounds(100, 100, 450, 300);
		mazeGameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mazeGameMenu.getContentPane().setLayout(null);

		JTextArea mazeTextArea = new JTextArea();
		mazeTextArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		mazeTextArea.setEditable(false);
		mazeTextArea.setBounds(10, 98, 210, 163);
		mazeGameMenu.getContentPane().add(mazeTextArea);
		
		mazeDimensionTextField = new JTextField();
		mazeDimensionTextField.setText("11");
		mazeDimensionTextField.setBounds(114, 14, 93, 14);
		mazeGameMenu.getContentPane().add(mazeDimensionTextField);
		mazeDimensionTextField.setColumns(10);

		JLabel mazeDimensionLabel = new JLabel("Maze Dimension:");
		mazeDimensionLabel.setBounds(10, 11, 84, 20);
		mazeGameMenu.getContentPane().add(mazeDimensionLabel);

		JLabel dragonNumberLabel = new JLabel("Number of Dragons:");
		dragonNumberLabel.setBounds(10, 42, 108, 20);
		mazeGameMenu.getContentPane().add(dragonNumberLabel);

		dragonNumberTextField = new JTextField();
		dragonNumberTextField.setText("1");
		dragonNumberTextField.setColumns(10);
		dragonNumberTextField.setBounds(114, 45, 93, 14);
		mazeGameMenu.getContentPane().add(dragonNumberTextField);

		JLabel dragonModeLabel = new JLabel("Dragon Mode:");
		dragonModeLabel.setBounds(10, 73, 84, 14);
		mazeGameMenu.getContentPane().add(dragonModeLabel);

		JComboBox gameModeComboBox = new JComboBox();
		gameModeComboBox.setBounds(114, 73, 93, 20);
		mazeGameMenu.getContentPane().add(gameModeComboBox);
		gameModeComboBox.addItem(STATIONARY_DRAGON_TEXT);
		gameModeComboBox.addItem(RANDOM_DRAGON_TEXT);
		gameModeComboBox.addItem(SLEEPING_DRAGON_TEXT);
		
		upButton = new JButton("UP");
		upButton.setEnabled(false);
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextTurnAndPrint(mazeTextArea, Direction.UP);		
			}
		});
		upButton.setBounds(285, 124, 71, 23);
		mazeGameMenu.getContentPane().add(upButton);

		downButton = new JButton("DOWN");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextTurnAndPrint(mazeTextArea, Direction.DOWN);
			}
		});
		downButton.setEnabled(false);
		downButton.setBounds(285, 191, 71, 23);
		mazeGameMenu.getContentPane().add(downButton);

		leftButton = new JButton("LEFT");
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextTurnAndPrint(mazeTextArea, Direction.LEFT);
			}
		});
		leftButton.setEnabled(false);
		leftButton.setBounds(243, 158, 55, 23);
		mazeGameMenu.getContentPane().add(leftButton);

		rightButton = new JButton("RIGHT");
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextTurnAndPrint(mazeTextArea, Direction.RIGHT);
			}
		});
		rightButton.setEnabled(false);
		rightButton.setBounds(336, 157, 63, 23);
		mazeGameMenu.getContentPane().add(rightButton);

		JButton finishGameButton = new JButton("Finish Game");
		finishGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		finishGameButton.setBounds(247, 45, 137, 23);
		mazeGameMenu.getContentPane().add(finishGameButton);
		
		instructionsLabel = new JLabel("");
		instructionsLabel.setBounds(230, 247, 169, 14);
		mazeGameMenu.getContentPane().add(instructionsLabel);

		JButton generateNewMazeButton = new JButton("Create New Maze");
		generateNewMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = 11;

				try{
					size = Integer.parseInt(mazeDimensionTextField.getText());
				}
				catch (NumberFormatException e){
					JOptionPane.showMessageDialog(mazeGameMenu, "Invalid maze dimension!\nPlease insert a valid integer.");
					return;
				}

				int dragonNumber=1;

				try{
					dragonNumber= Integer.parseInt(dragonNumberTextField.getText());
				}
				catch (NumberFormatException e){
					JOptionPane.showMessageDialog(mazeGameMenu, "Invalid number of dragons!\nPlease insert a valid integer.");
					return;
				}

				RandomMazeGenerator rmg = new RandomMazeGenerator(size, dragonNumber);
				GameMode gameMode = GameMode.STATIONARY;
				
				switch ((String) gameModeComboBox.getSelectedItem()){
				case MazeGUI.STATIONARY_DRAGON_TEXT :
					gameMode = GameMode.STATIONARY;
					break;
				case MazeGUI.RANDOM_DRAGON_TEXT :
					gameMode = GameMode.RANDOM_MOVEMENT;
					break;
				case MazeGUI.SLEEPING_DRAGON_TEXT :
					gameMode = GameMode.SLEEP_RANDOM_MOVEMENT;
					break;
				}

				maze = new Maze(rmg.getMaze(), gameMode);
				mazeTextArea.setText(maze.toString());

				instructionsLabel.setText("Ready to play!");
				
				enableMovementButtons();
			}
		});
		
		generateNewMazeButton.setBounds(247, 10, 137, 23);
		mazeGameMenu.getContentPane().add(generateNewMazeButton);
	}
	
	//FIXME: Change the method name to a more mnemonic one.
	private void nextTurnAndPrint(JTextArea mazeTextArea, Direction direction){
		maze.nextTurn(direction);
		mazeTextArea.setText(maze.toString());
		checkGameState();
	}
	
	private void checkGameState() {
		if(maze.getGameState() != GameState.RUNNING) {
			disableMovementButtons();
			instructionsLabel.setText("Game over.");
		}
	}
	
	private void enableMovementButtons() {
		upButton.setEnabled(true);
		downButton.setEnabled(true);
		rightButton.setEnabled(true);
		leftButton.setEnabled(true);
	}
	
	private void disableMovementButtons() {
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		rightButton.setEnabled(false);
		leftButton.setEnabled(false);
	}

	//TODO acabar 3.4 e fazer 3.5
}