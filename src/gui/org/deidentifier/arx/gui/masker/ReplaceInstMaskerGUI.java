import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ReplaceInstMaskerGUI implements MaskerGUI {
	static String replacementValueDouble = "";
	static String replacementValueDate = "";
	static String replacementValueString = "";
	
	public static void main (String[] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		
		final Composite mainComposite = new Composite(sc, SWT.NONE);
		mainComposite.setLayout(new GridLayout());
		
		final Composite c1 = new Composite(mainComposite, SWT.BORDER);	
		c1.setLayout (new GridLayout (3, false));
		
		Label label = new Label(c1, SWT.NULL);
		label.setText("Replacement value: ");
		
		final Combo dropdown = new Combo(c1, SWT.DROP_DOWN);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 2;
	    dropdown.setLayoutData(gridData);
	    dropdown.setText("Choose data type...");
		String[] dataType = {	"Double",
								"Date",
								"String"
								 };
		for (String s: dataType) {
			dropdown.add(s);
		}
		
		label = new Label(c1, SWT.NULL);
		
		final Composite c2 = new Composite(c1, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = 2;
	    c2.setLayoutData(gridData);
	    
	    dropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = dropdown.getSelectionIndex();
				for (Control child: c2.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
					case 0: // Double
						c2.setLayout(new RowLayout());
						
						final Spinner replacementValue = new Spinner(c2, SWT.BORDER);
						replacementValue.setDigits(2);
						replacementValue.setMaximum(100000);
						replacementValue.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								replacementValueDouble = replacementValue.getText();
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 1: // Date
						c2.setLayout(new RowLayout());
						
						final DateTime replacementValue2 = new DateTime(c2, SWT.CALENDAR);
						replacementValue2.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								replacementValueDate = replacementValue2.getDay() + "." +
														(replacementValue2.getMonth()+1) + "." +
														replacementValue2.getYear();
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 2: // String
						c2.setLayout(new RowLayout());
						
						final Text replacementValue3 = new Text(c2, SWT.BORDER);
						replacementValue3.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent e) {
								replacementValueString = replacementValue3.getText();
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
				}
				mainComposite.layout(true, true);
			}
	    });
		
		// GUI control
		Composite c3 = new Composite(mainComposite, SWT.NONE);
		c3.setLayout(new GridLayout (1, false));
		Button next = new Button(c3, SWT.PUSH);
		next.setText("next >");
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = dropdown.getSelectionIndex();
				switch (selectedIndex) {
					case 0: // Double
						System.out.println("Double: " + replacementValueDouble);
						break;
					case 1: // Date
						System.out.println("Date: " + replacementValueDate);
						break;
					case 2: // String
						System.out.println("String: " + replacementValueString);
						break;
				}
			}
		});
		
		mainComposite.layout(true, true);
		
		sc.setContent(mainComposite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		shell.pack ();
		shell.open ();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}

	public Composite loadMasker() {
		Shell shell = new Shell ();
		
		final Composite mainComposite = new Composite(shell, SWT.NONE);
      	mainComposite.setLayout(new GridLayout (9, false));
      	
      	return mainComposite;
	}
}
