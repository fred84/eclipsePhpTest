package me.galkin.eclipse.php.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ResultBar extends Canvas {

	public static final String FAIL = "fail";
	public static final String OK = "ok";
	
	private final int HEIGHT = 18;
	
	private final Color GREEN;
	private final Color RED;
	
	private int max = 1;
	private int selection = 0;
	private String status = OK;

	public ResultBar(Composite parent, int style) {
		super(parent, style);
		setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));

		GREEN = new Color(getDisplay(), 95, 191, 95);
		RED = new Color(getDisplay(), 159, 63, 63);
		
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(status.equals(OK) ? GREEN : RED);
				
				Rectangle rect = e.display.getClientArea();
				e.gc.fillRectangle(rect.x, rect.y, rect.width * (selection/max), rect.height);
			}
		});
		
		this.addDisposeListener(new DisposeListener() {
			
			public void widgetDisposed(DisposeEvent e) {
				GREEN.dispose();
				RED.dispose();
			}
		});
	}

	public Point computeSize (int wHint, int hHint, boolean changed) {
		return super.computeSize(wHint, HEIGHT, changed);
	}
	
	public void update(int maximum, int selection, String status) {
		this.max = maximum;
		this.selection = selection;
		this.status = status;
		redraw();
	}
}
