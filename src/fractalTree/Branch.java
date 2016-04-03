package fractalTree;

import java.util.Comparator;

class Branch {

	private int x, y;
	private int length, angle;

	Branch(int x, int y, int length, int angle) {
		this.x = x;
		this.y = y;
		this.length = length;
		this.angle = angle;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	int getLength() {
		return length;
	}

	int getAngle() {
		return angle;
	}

}

class branchToMiddle implements Comparator<Branch>{

	@Override
	public int compare(Branch o1, Branch o2) {
		int d1 = Math.abs(o1.getX() - GraphSearchDrawTree.X_MIDDLE), d2 = Math.abs(o2.getX() - GraphSearchDrawTree.X_MIDDLE);
		if (d1 < d2) return -1;
		if(d1 == d2 )return 0;
 		return 1;
	}
	
}
