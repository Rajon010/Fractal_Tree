package fractalTree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

import java.util.PriorityQueue;

abstract class GraphSearchDrawTree {
	
	static int X_MIDDLE;
	//static Color color[] = {Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE};
	Collection<Branch> frontier = null;
	Graphics2D g2 = null;
	private int LENGTH_MIN;
	final private int H_W_RATIO = 5;

	GraphSearchDrawTree() {
	}

	void drawTree(Graphics g, int width, int height, double lengthLeft, int angleLeft, double lengthRight,
			int angleRight) {

		X_MIDDLE = width / 2;
		g2 = (Graphics2D) g;
		g2.clearRect(0, 0, width, height);
		LENGTH_MIN = height / 100;
		int trunkHeight = height / 6;
		g2.setStroke(new BasicStroke(trunkHeight / H_W_RATIO));
		//g2.setColor(color[3]);
		g2.setColor(Color.GREEN);
		g2.drawLine(X_MIDDLE, height, X_MIDDLE, height - trunkHeight);
		
		//int COLOR_INTERVAL = (int) Math.ceil(Math.log(Math.sqrt(Math.sqrt(trunkHeight))));
		frontier_push(new Branch(X_MIDDLE, height - trunkHeight, trunkHeight, 90));
		for (int i = 1; !frontier.isEmpty(); i++) {
			Branch b = frontier_pop();
			int x = b.getX(), y = b.getY(), len = b.getLength(), angle = b.getAngle();
			Graphics2D gNow = (Graphics2D) g2.create(); // create a copy gNow of g2 so that rotating gNow does not effect g2
			gNow.rotate(-Math.toRadians(angle), x, y);
			gNow.setStroke(new BasicStroke(len / H_W_RATIO));
			//g2.setColor(color[(int) (Math.log(lengthLeft / COLOR_INTERVAL))]);
			
			gNow.drawLine(x, y, x + len, y);

			if (b.getLength() > LENGTH_MIN) {
				int newX = b.getX() + (int) (len * Math.cos(Math.toRadians(angle)));
				int newY = b.getY() - (int) (len * Math.sin(Math.toRadians(angle)));
				frontier_push(new Branch(newX, newY, (int) (len * lengthLeft), angle + angleLeft));
				frontier_push(new Branch(newX, newY, (int) (len * lengthRight), angle - angleRight));
			}
			if (i % 10 == 0)
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
		}
	}

	abstract void frontier_push(Branch b);

	abstract Branch frontier_pop();

}

class BFS extends GraphSearchDrawTree {
	BFS() {
		super();
		frontier = new LinkedList<Branch>();
	}

	@Override
	void frontier_push(Branch b) {
		((LinkedList<Branch>) frontier).addLast(b);
	}

	@Override
	Branch frontier_pop() {
		return ((LinkedList<Branch>) frontier).removeFirst();
	}
}

class DFS extends GraphSearchDrawTree {
	DFS() {
		super();
		frontier = new Stack<Branch>();
	}

	@Override
	void frontier_push(Branch b) {
		((Stack<Branch>) frontier).push(b);
	}

	@Override
	Branch frontier_pop() {
		return ((Stack<Branch>) frontier).pop();
	}
}

class MiddleFirst extends GraphSearchDrawTree {
	MiddleFirst() {
		super();
		frontier = new PriorityQueue<Branch>(1, new branchToMiddle());
	}

	@Override
	void frontier_push(Branch b) {
		((PriorityQueue<Branch>) frontier).add(b);
	}

	@Override
	Branch frontier_pop() {
		return ((PriorityQueue<Branch>) frontier).remove();
	}
}