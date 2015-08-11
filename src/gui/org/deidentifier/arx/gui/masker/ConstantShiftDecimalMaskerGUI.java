

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.ConstantShiftDecimalMasker;

public class ConstantShiftDecimalMaskerGUI implements ConfigurationComponent{
	private Composite cmpRoot;
	
	private Label lblShift;
	
	private Spinner spnInput;
	
	private Boolean spnInputValid;
	
	public ConstantShiftDecimalMaskerGUI(Composite root) {
		this.cmpRoot = new Composite(root, SWT.BORDER);
		this.cmpRoot.setLayout(new GridLayout (2, false));
		this.lblShift = new Label(cmpRoot, SWT.NONE);
		this.lblShift.setText("Shift Distance:");
		this.spnInput = new Spinner(cmpRoot, SWT.BORDER);
		this.spnInput.setDigits(2);
		this.spnInput.setMaximum(10000);
		this.spnInput.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					spnInputValid = true;
		        } catch (NumberFormatException exc) {
		        	spnInputValid = false;
		        }
			}
		});
	}

	public boolean isValid() {
		return false;
	}

	public void addModifyListener(ModifyListener listener) {
		// TODO Auto-generated method stub
		
	}

	
	public void addSelectionListener(SelectionAdapter adapter) {
		// TODO Auto-generated method stub
		
	}


}