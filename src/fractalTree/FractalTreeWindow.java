package fractalTree;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

class FractalTreeWindow {

	final int BRANCH_N = 2;
	final int SEARCH_N = 3;
	final int LEFT = 0, RIGHT = 1;
	final int BFS = 0, DFS = 1, MID = 2;
	final String SEARCH_NAME[] = { "BFS", "DFS", "Middle First" };
	final String SEARCH_CLASS_NAME[] = { "BFS", "DFS", "MiddleFirst" };
	JLabel branchLabel[] = new JLabel[BRANCH_N];
	JLabel lengthLabel[] = new JLabel[BRANCH_N];
	JLabel angleLabel[] = new JLabel[BRANCH_N];
	JSlider lengthSlider[] = new JSlider[BRANCH_N];
	JSlider angleSlider[] = new JSlider[BRANCH_N];
	JButton drawButton[] = new JButton[SEARCH_N];

	ActionListener buttonListener = new ButtonListener();

	JFrame frame = new JFrame();
	Container mainPanel = frame.getContentPane();
	JPanel textPanel = new JPanel();
	JPanel configPanel = new JPanel();
	JPanel drawPanel = new JPanel();
	JComponent canvas = new JComponent() {
	};
	// Canvas canvas = new Canvas();

	private GraphSearchDrawTree searchDrawer = null;

	FractalTreeWindow() {

		branchLabel[LEFT] = new JLabel("Left Branch");
		branchLabel[RIGHT] = new JLabel("Right Branch");
		for (int i = 0; i < BRANCH_N; i++) {
			lengthLabel[i] = new JLabel("Length %");
			angleLabel[i] = new JLabel("Angle deg.");
			lengthSlider[i] = setSlider(45, 85, 65, 5, 20);
			angleSlider[i] = setSlider(0, 90, 45, 10, 30);
		}
		for (int i = 0; i < SEARCH_N; i++) {
			drawButton[i] = new JButton(SEARCH_NAME[i]);
			drawButton[i].addActionListener(buttonListener);
		}

		GridBagLayout gridBag = new GridBagLayout();
		configPanel.setLayout(gridBag);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2.0;
		gbc.gridwidth = 2;
		// gbc.gridx = 0; gbc.gridy = 0;
		addConponentToGridBag(branchLabel[LEFT], configPanel, gridBag, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		// gbc.gridx += 2;
		addConponentToGridBag(branchLabel[RIGHT], configPanel, gridBag, gbc);
		gbc.gridwidth = 1;
		// gbc.gridx = 0; gbc.gridy++;
		addConponentToGridBag(lengthLabel[LEFT], configPanel, gridBag, gbc);
		// gbc.gridx++;
		addConponentToGridBag(angleLabel[LEFT], configPanel, gridBag, gbc);
		// gbc.gridx++;
		addConponentToGridBag(lengthLabel[RIGHT], configPanel, gridBag, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		// gbc.gridx++;
		addConponentToGridBag(angleLabel[RIGHT], configPanel, gridBag, gbc);
		gbc.gridwidth = 1;
		// gbc.gridx = 0; gbc.gridy++;
		addConponentToGridBag(lengthSlider[LEFT], configPanel, gridBag, gbc);
		addConponentToGridBag(angleSlider[LEFT], configPanel, gridBag, gbc);
		addConponentToGridBag(lengthSlider[RIGHT], configPanel, gridBag, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		addConponentToGridBag(angleSlider[RIGHT], configPanel, gridBag, gbc);

		drawPanel.setLayout(new GridLayout(1, 3));
		for (int i = 0; i < SEARCH_N; i++)
			drawPanel.add(drawButton[i]);

		textPanel.setLayout(new BorderLayout());
		textPanel.add(configPanel, BorderLayout.CENTER);
		textPanel.add(drawPanel, BorderLayout.SOUTH);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(textPanel, BorderLayout.NORTH);
		mainPanel.add(canvas, BorderLayout.CENTER);

		frame.setTitle("Fractal Tree");
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	private JSlider setSlider(int min, int max, int value, int minTick, int majTick) {
		JSlider s = new JSlider(min, max, value);
		s.setMinorTickSpacing(minTick);
		s.setMajorTickSpacing(majTick);
		s.setPaintTicks(true);
		s.setPaintLabels(true);
		return s;
	}

	private void addConponentToGridBag(JComponent comp, JPanel panel, GridBagLayout gridBag, GridBagConstraints gbc) {
		gridBag.setConstraints(comp, gbc);
		panel.add(comp);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			String search = ((JButton) (event.getSource())).getText();
			int i = 0;
			while (i < SEARCH_N) {
				if (search.equals(SEARCH_NAME[i]))
					break;
				i++;
			}

			try {
				searchDrawer = (GraphSearchDrawTree) (Class.forName("fractalTree." + SEARCH_CLASS_NAME[i])
						.getDeclaredConstructor().newInstance());
			} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
					| java.lang.reflect.InvocationTargetException e) {
				System.out.println(e);
				Runtime.getRuntime().exit(1);
			}
			searchDrawer.drawTree(canvas.getGraphics(), canvas.getWidth(), canvas.getHeight(),
					(double) (lengthSlider[LEFT].getValue()) / 100, angleSlider[LEFT].getValue(),
					(double) (lengthSlider[RIGHT].getValue()) / 100, angleSlider[RIGHT].getValue());
		}

	}
}
