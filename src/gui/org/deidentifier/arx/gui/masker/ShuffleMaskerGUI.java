


import java.util.List;
import java.util.Arrays;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ShuffleMasker;

public class ShuffleMaskerGUI implements ConfigurationComponent{
	
	private Label lbl;
	
	private Composite cmpRoot;
	
	public ShuffleMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.BORDER);	
		this.cmpRoot.setLayout (new GridLayout (9, false));
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Shuffle Information");
		
	}
	
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Composite getCmpRoot() {
		return cmpRoot;
	}

	@Override
	public void setCmpRoot(Composite cmpRoot) {
		this.cmpRoot = cmpRoot;		
	}
}