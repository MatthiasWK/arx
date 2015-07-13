

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.SplitAndReplaceStringMasker;

public class SplitAndReplaceStringMaskerGUI implements MaskerGUI{

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
		txtRegEx.setText("Regular Expression:");
		
		final Text reInput = new Text (c1, SWT.BORDER | SWT.FILL);
		reInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label txtReplacementString = new Label(c1, SWT.NULL);
		txtReplacementString.setText("Replacement String:");
		
		final Text rsInput = new Text(c1, SWT.BORDER);
		rsInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label txtReplaceGroup = new Label(c1, SWT.NULL);
		txtReplaceGroup.setText("Replace Group:");
		
		final Spinner rgInput = new Spinner(c1, SWT.BORDER);
		
		new Label(c1, SWT.NONE);
		
		final Button btnCheck = new Button(c1, SWT.CHECK);
		btnCheck.setText("replace each character");
		
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
				
				int rg = rgInput.getSelection();
				String re = reInput.getText();
				String rs = rsInput.getText();
				
				SplitAndReplaceStringMasker masker = new SplitAndReplaceStringMasker(re, rs, rg, btnCheck.getSelection());
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
		
		Composite mainComposite = new Composite(shell, SWT.BORDER);
		mainComposite.setLayout(new GridLayout(2, false));
		
		Label txtRegEx = new Label (mainComposite, SWT.NULL);
		txtRegEx.setText("Regular Expression:");
		
		final Text reInput = new Text (mainComposite, SWT.BORDER | SWT.FILL);
		reInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label txtReplacementString = new Label(mainComposite, SWT.NULL);
		txtReplacementString.setText("Replacement String:");
		
		final Text rsInput = new Text(mainComposite, SWT.BORDER);
		rsInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label txtReplaceGroup = new Label(mainComposite, SWT.NULL);
		txtReplaceGroup.setText("Replace Group:");
		
		final Spinner rgInput = new Spinner(mainComposite, SWT.BORDER);
		
		new Label(mainComposite, SWT.NONE);
		
		final Button btnCheck = new Button(mainComposite, SWT.CHECK);
		btnCheck.setText("replace each character");
		
		Composite c2 = new Composite(shell, SWT.NONE);
		c2.setLayout(new GridLayout (2, false));
		
		return mainComposite;
	}
}
