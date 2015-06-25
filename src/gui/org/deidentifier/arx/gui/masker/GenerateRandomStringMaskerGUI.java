import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomStringMaskerGUI implements MaskerGUI {
	public static void main (String [] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
		shell.setLayout(layout);

		Composite c1 = new Composite(shell, SWT.BORDER);	
		c1.setLayout (new GridLayout (3, false));
		
		// no parameter
		final Button b1 = new Button(c1, SWT.RADIO);
		Label label = new Label(c1, SWT.NONE);
		label.setText("No parameters (Creates a masker generating strings with the same length as the input, using only alphabetic characters.)");

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 2;
	    label.setLayoutData(gridData);
		
	    // boolean letters, boolean numbers
		final Button b2 = new Button(c1, SWT.RADIO);
		b2.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		label = new Label(c1, SWT.NONE);
		label.setText("Alphabetic and/or numeric characters:");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Composite c2 = new Composite(c1, SWT.NONE);
		c2.setLayout(new GridLayout(2, false));
		
		final Button letters = new Button(c2, SWT.CHECK);
		letters.setEnabled(false);
		label = new Label(c2, SWT.NONE);
		label.setText("alphabetic characters");
		
		final Button numbers = new Button(c2, SWT.CHECK);
		numbers.setEnabled(false);
		label = new Label(c2, SWT.NONE);
		label.setText("numeric characters");
		
		b2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (b2.getSelection()) {
					letters.setEnabled(true);
					numbers.setEnabled(true);
				} else {
					letters.setEnabled(false);
					numbers.setEnabled(false);
				}
			}
		});
		
		// char[] charSet
		final Button b3 = new Button(c1, SWT.RADIO);
		b3.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		label = new Label(c1, SWT.NONE);
		label.setText("Character set:");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		final Text charSet = new Text(c1, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		charSet.setEnabled(false);
		gridData = new GridData(SWT.FILL, 0, false, false);
		gridData.heightHint = 50;
		charSet.setLayoutData(gridData);
		
		b3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (b3.getSelection()) {
					charSet.setEnabled(true);
				} else {
					charSet.setEnabled(false);
				}
			}
		});
		
		// length + other parameters
		final Button b4 = new Button(c1, SWT.RADIO);
		b4.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		label = new Label(c1, SWT.NONE);
		label.setText("Length + other parameters:");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		Composite c3 = new Composite(c1, SWT.NONE);
		c3.setLayout(new GridLayout(4, false));
		
		final Spinner length = new Spinner(c3, SWT.WRAP);
		length.setEnabled(false);
		length.setMaximum(100000);
		
		label = new Label(c3, SWT.NONE);
		label.setText("(int length)");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 3;
	    label.setLayoutData(gridData);
		
		final Button checkCharSet = new Button(c3, SWT.CHECK);
		checkCharSet.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
		checkCharSet.setEnabled(false);
		
		label = new Label(c3, SWT.NONE);
		label.setText("Character set:");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		final Text charSet2 = new Text(c3, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		charSet2.setEnabled(false);
		gridData = new GridData(SWT.FILL, 0, false, false);
		gridData.heightHint = 50;
	    gridData.horizontalSpan = 2;
		charSet2.setLayoutData(gridData);
		
		final Button checkLettersNumbers = new Button(c3, SWT.CHECK);
		checkLettersNumbers.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
		checkLettersNumbers.setEnabled(false);
		
		label = new Label(c3, SWT.NONE);
		label.setText("Alphabetic/numeric characters:");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		final Button letters2 = new Button(c3, SWT.CHECK);
		letters2.setEnabled(false);
		label = new Label(c3, SWT.NONE);
		label.setText("alphabetic characters");

		label = new Label(c3, SWT.NONE);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 2;
	    label.setLayoutData(gridData);
	    
		final Button numbers2 = new Button(c3, SWT.CHECK);
		numbers2.setEnabled(false);
		label = new Label(c3, SWT.NONE);
		label.setText("numeric characters");
		
		b4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (b4.getSelection()) {
					length.setEnabled(true);
					checkCharSet.setEnabled(true);
					checkLettersNumbers.setEnabled(true);
				} else {
					length.setEnabled(false);
					checkCharSet.setEnabled(false);
					checkLettersNumbers.setEnabled(false);
				}
			}
		});
		
		checkCharSet.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (checkCharSet.getSelection()) {
					charSet2.setEnabled(true);
				} else {
					charSet2.setEnabled(false);
				}
			}
		});
		
		checkLettersNumbers.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (checkLettersNumbers.getSelection()) {
					letters2.setEnabled(true);
					numbers2.setEnabled(true);
				} else {
					letters2.setEnabled(false);
					numbers2.setEnabled(false);
				}
			}
		});
		
		// GUI control
		Composite c4 = new Composite(shell, SWT.NONE);
		c4.setLayout(new GridLayout (1, false));
		Button next = new Button(c4, SWT.PUSH);
		next.setText("next >");
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (b1.getSelection()) {
					System.out.println("no parameters");
				} else if (b2.getSelection()) {
					System.out.println("boolean letters, boolean numbers");
				} else if (b3.getSelection()) {
					System.out.println("char[] charSet");
				} else if (b4.getSelection()) {
					System.out.println("int length [, char[] charSet] [, boolean letters, boolean numbers]");
				}
			}
		});

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
