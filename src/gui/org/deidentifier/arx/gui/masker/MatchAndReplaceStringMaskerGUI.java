

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.MatchAndReplaceStringMasker;

public class MatchAndReplaceStringMaskerGUI extends MaskerTool implements MaskerGUI {

	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
		shell.setLayout (layout);
		shell.setText("SplitAndReplaceStringMasker");

		Composite c1 = new Composite(shell, SWT.BORDER);
		c1.setLayout(new GridLayout(2, false));
		
		Label txtRegEx = new Label (c1, SWT.NULL);
		txtRegEx.setText("Regular Expression");
		
		final Text reInput = new Text (c1, SWT.BORDER | SWT.FILL);
		reInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label txtReplacementString = new Label (c1, SWT.NULL);
		txtReplacementString.setText("Replacement String");
		
		final Text rsInput = new Text(c1, SWT.BORDER);
		rsInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		new Label(c1, SWT.NONE);
		
		final Button btnMatches = new Button(c1, SWT.CHECK);
		btnMatches.setText("replace all matches");
		
		new Label(c1, SWT.NONE);
		
		final Button btnChars = new Button(c1, SWT.CHECK);
		btnChars.setText("replace each character");
		
		Composite c2 = new Composite(shell, SWT.NONE);
		c2.setLayout(new GridLayout (2, false));
		
		Button cancel = new Button(c2, SWT.PUSH);
		cancel.setText("cancel");
		cancel.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				
			    }
			}
		);

		Button next = new Button(c2, SWT.PUSH);
		next.setText("next >");
		next.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				
				String test = "Hello_World";
				
				String re = reInput.getText();
				String rs = rsInput.getText();
				
				MatchAndReplaceStringMasker masker = new MatchAndReplaceStringMasker(re, rs, btnMatches.getSelection(), btnChars.getSelection());
				System.out.println(masker.mask(test));
			}
		}
				);
		
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
	  	mainComposite.setLayout(new GridLayout (2, false));
	  	
	  	mainComposite.setLayout(new GridLayout(2, false));
		
		Label txtRegEx = new Label (mainComposite, SWT.NULL);
		txtRegEx.setText("Regular Expression");
		
		final Text reInput = new Text (mainComposite, SWT.BORDER | SWT.FILL);
		reInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label txtReplacementString = new Label (mainComposite, SWT.NULL);
		txtReplacementString.setText("Replacement String");
		
		final Text rsInput = new Text(mainComposite, SWT.BORDER);
		rsInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		new Label(mainComposite, SWT.NONE);
		
		final Button btnMatches = new Button(mainComposite, SWT.CHECK);
		btnMatches.setText("replace all matches");
		
		new Label(mainComposite, SWT.NONE);
	  	
		final Button btnChars = new Button(mainComposite, SWT.CHECK);
		btnChars.setText("replace each character");
		
	  	return mainComposite;
	}
}

