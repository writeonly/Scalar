package org.eclipse.swt.snippets;

/*
 * Virtual Table example snippet: create a table with 1,000,000 items (lazy, page size 64)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet201 {

	static final int PAGE_SIZE = 64;
	static final int COUNT = 100000;

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		final Table table = new Table(shell, SWT.VIRTUAL | SWT.BORDER);
		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				int index = table.indexOf(item);
				int start = index / PAGE_SIZE * PAGE_SIZE;
				int end = Math.min(start + PAGE_SIZE, table.getItemCount());
				for (int i = start; i < end; i++) {
					item = table.getItem(i);
					item.setText("Item " + i);
				}
			}
		});
		table.setLayoutData(new RowData(200, 200));
		final Button button = new Button(shell, SWT.PUSH);
		button.setText("Add Items");
		final Label label = new Label(shell, SWT.NONE);
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				long t1 = System.currentTimeMillis();
				table.setItemCount(COUNT);
				long t2 = System.currentTimeMillis();
				label.setText("Items: " + COUNT + ", Time: " + (t2 - t1)
						+ " (ms) [page=" + PAGE_SIZE + "]");
				shell.layout();
			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}