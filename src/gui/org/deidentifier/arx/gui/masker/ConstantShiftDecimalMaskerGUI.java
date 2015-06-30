

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ConstantShiftDecimalMasker;

public class ConstantShiftDecimalMaskerGUI extends MaskerTool implements MaskerGUI{
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
		shell.setLayout (layout);
		shell.setText("ConstantShiftDecimalMasker");

		Composite c1 = new Composite(shell, SWT.BORDER);	
		c1.setLayout (new GridLayout (2, false));
		
		// Shift Distance
		Label shift = new Label (c1, SWT.NULL);
		shift.setText("Shift Distance:");
		final Spinner dInput = new Spinner (c1, SWT.BORDER);
		dInput.setDigits(2);
		
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
				double test = 50;
				
				double shiftDistance =dInput.getSelection();
				ConstantShiftDecimalMasker masker = new ConstantShiftDecimalMasker(shiftDistance);
				
				test=masker.mask(test);
				
			    System.out.println(test);
				
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
	  	
	 // Shift Distance
	 		Label shift = new Label (mainComposite, SWT.NULL);
	 		shift.setText("Shift Distance:");
	 		final Spinner dInput = new Spinner (mainComposite, SWT.BORDER);
	 		dInput.setDigits(2);
	 		
			return mainComposite;
	}
}