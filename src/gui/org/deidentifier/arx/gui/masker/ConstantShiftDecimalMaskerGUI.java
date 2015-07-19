

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ConstantShiftDecimalMasker;

public class ConstantShiftDecimalMaskerGUI implements ConfigurationComponent{
	private Composite cmpRoot;
	
	private Label lblShift;
	private Spinner spnInput;
	
	public ConstantShiftDecimalMaskerGUI(Composite root) {
		this.cmpRoot = new Composite(root, SWT.BORDER);
		this.cmpRoot.setLayout(new GridLayout (2, false));
		this.lblShift = new Label(getCmpRoot(), SWT.NONE);
		this.lblShift.setText("Shift Distance:");
		this.spnInput = new Spinner(getCmpRoot(), SWT.BORDER);
		this.spnInput.setDigits(2);
		}

	@Override
	public Composite getCmpRoot() {
		return cmpRoot;
	}

	@Override
	public void setCmpRoot(Composite cmpRoot) {
		this.cmpRoot = cmpRoot;		
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}


}