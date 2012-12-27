package booksandfilms.client.helper;

public class ClickPoint {
	private int top, left;
	
	public ClickPoint(int top, int left) {
		this.top = top;
		this.left = left;
	}
	
	public int getTop() {
		return top;
	}
	
	public int getLeft() {
		return left;
	}
}
