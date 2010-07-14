package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.domain.ResultsCount;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ResultBar extends Canvas {

	public static final String FAIL = "fail";
	public static final String OK = "ok";
	public static final String RUNNING = "running";
	
	private final int HEIGHT = 18;
	
	private final Color GREEN;
	private final Color RED;
	private final Color GRAY;
	
	private double percentage = 0;
	private String status = OK;

	public ResultBar(Composite parent, int style) {
		super(parent, style);

		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		data.horizontalSpan = 6;
		setLayoutData(data);
		
		GREEN = new Color(getDisplay(), 95, 191, 95);
		RED = new Color(getDisplay(), 159, 63, 63);
		GRAY = new Color(getDisplay(), 155, 155, 155);
		
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(getColor(status));
				e.gc.fillRectangle(e.x, e.y, (int) ((e.width - 10) * percentage), e.height);
			}
		});
		
		this.addDisposeListener(new DisposeListener() {
			
			public void widgetDisposed(DisposeEvent e) {
				GREEN.dispose();
				RED.dispose();
				GRAY.dispose();
			}
		});
	}

	public Point computeSize (int wHint, int hHint, boolean changed) {
		return super.computeSize(wHint, HEIGHT, changed);
	}
	
	public void update(ResultsCount count, String status) {
		this.percentage = count.getPercentage();
		this.status = status;
		redraw();
	}
	
	private Color getColor(String status) {
		if (status.equals(OK)) {
			return GREEN;
		}
		
		return status.equals(FAIL) ? RED : GRAY;
	}
}
