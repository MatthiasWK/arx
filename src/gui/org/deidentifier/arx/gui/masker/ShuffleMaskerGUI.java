


import java.util.List;
import java.util.Arrays;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ShuffleMasker;

public class ShuffleMaskerGUI extends MaskerGUI{
	
	private Label lbl;
	
	public ShuffleMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.BORDER);	
		this.cmpRoot.setLayout (new GridLayout (9, false));
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Shuffle Information");
		
	}
}
