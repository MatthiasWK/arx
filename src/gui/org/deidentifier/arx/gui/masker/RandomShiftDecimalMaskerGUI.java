

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class RandomShiftDecimalMaskerGUI implements ConfigurationComponent{
	
	private Label lbl;
	
	private Button btnShiftConstant;
	
	private Spinner spnShiftConstanatInput;
	
	private RealDistribution distribution;

	private Composite cmpRoot;
	
	public RandomShiftDecimalMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.BORDER);	
		this.cmpRoot.setLayout (new GridLayout (9, false));
		
      	// Real distribution
 		this.lbl = new Label(this.cmpRoot, SWT.NULL);
 		this.lbl.setText("Real distribution: ");
 		this.distribution = new RealDistribution(this.cmpRoot);
 		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = 8;
	    this.distribution.getCmpRoot().setLayoutData(gridData);
	    this.distribution.getCmpRoot().layout(true);
		
		// shift constant
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Shift constant: ");
		
		this.btnShiftConstant = new Button(this.cmpRoot, SWT.CHECK);
		
		this.spnShiftConstanatInput = new Spinner(this.cmpRoot, SWT.BORDER);
		this.spnShiftConstanatInput.setEnabled(false);
		this.spnShiftConstanatInput.setDigits(2);
		
		this.btnShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnShiftConstant.getSelection()) {
					spnShiftConstanatInput.setEnabled(true);
				} else {
					spnShiftConstanatInput.setEnabled(false);
				}
			}
		});
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

