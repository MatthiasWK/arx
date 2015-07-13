


import java.util.List;
import java.util.Arrays;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ShuffleMasker;

public class ShuffleMaskerGUI implements MaskerGUI{
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
		shell.setLayout (layout);
		shell.setText("ShuffleMasker");

		final List<String> list = Arrays.asList("all", "your", "base", "are");
		
		for( String s : list){
			System.out.println(s);
		}
		
		Composite c1 = new Composite(shell, SWT.BORDER);	
		c1.setLayout (new GridLayout (2, false));
			
		Button cancel = new Button(c1, SWT.PUSH);
		cancel.setText("cancel");
		cancel.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				
				
			    }
			}
		);

		Button next = new Button(c1, SWT.PUSH);
		next.setText("next");
		next.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				
				ShuffleMasker<String> masker = new ShuffleMasker<String>();
				masker.maskList(list);
				System.out.println("\nshuffle:");
				for( String s : list){
					System.out.println(s);
				}
				
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
		
		final Composite mainComposite = new Composite(shell, SWT.BORDER);	
		mainComposite.setLayout (new GridLayout (9, false));
		
		Label label = new Label(mainComposite, SWT.NULL);
 		label.setText("Shuffle Information");
		
		return mainComposite;
	}
}
