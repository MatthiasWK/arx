

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ConstantShiftDecimalMasker;

public class ConstantShiftDecimalMaskerGUI extends MaskerGUI{
	private Label lblShift;
	private Spinner spnInput;
	
	public ConstantShiftDecimalMaskerGUI(Composite root) {
		this.cmpRoot = new Composite(root, SWT.NONE);
		this.cmpRoot.setLayout(new GridLayout (2, false));
		this.lblShift = new Label(cmpRoot, SWT.NONE);
		this.lblShift.setText("Shift Distance:");
		this.spnInput = new Spinner(cmpRoot, SWT.BORDER);
		this.spnInput.setDigits(2);
		}

}