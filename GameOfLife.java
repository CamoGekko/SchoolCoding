import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JPanel implements ActionListener {
    private final int rows = 50;
    private final int cols = 50;
    private final int cellSize = 10;
    private int[][] grid = new int[rows][cols];
    private Timer timer;

    public GameOfLifeGUI() {
        initialize();
        timer = new Timer(100, this);
        timer.start();
    }

    // Initialize the grid with random values
    public void initialize() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = (Math.random() < 0.3) ? 1 : 0; // 30% chance of being alive
            }
        }
    }

    // Get the number of live neighbors around a specific cell
    private int getLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    liveNeighbors += grid[r][c];
                }
            }
        }
        return liveNeighbors;
    }

    // Evolve the grid to the next generation
    public void nextGeneration() {
        int[][] newGrid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = getLiveNeighbors(i, j);

                if (grid[i][j] == 1) { // Alive
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[i][j] = 0; // Dies
                    } else {
                        newGrid[i][j] = 1; // Survives
                    }
                } else { // Dead
                    if (liveNeighbors == 3) {
                        newGrid[i][j] = 1; // Becomes alive
                    }
                }
            }
        }

        grid = newGrid;
        repaint();
    }

    // Draw the grid on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextGeneration();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GameOfLifeGUI game = new GameOfLifeGUI();

        frame.setSize(game.cols * game.cellSize + 15, game.rows * game.cellSize + 38);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }
}
