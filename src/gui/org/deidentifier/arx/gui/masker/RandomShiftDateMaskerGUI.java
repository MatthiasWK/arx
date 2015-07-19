

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class RandomShiftDateMaskerGUI implements ConfigurationComponent{

	private Label lbl;
	
	private IntegerDistribution distribution;

	private Button btnBasePeriod;
	private Button btnShiftConstant;
	
	private Spinner spnDays;
	private Spinner spnMonths;
	private Spinner spnYears;
	private Spinner spnShiftConstantInput;
	
	private DateTime dtTimePeriod;

	private Composite cmpRoot;
	
	public RandomShiftDateMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
	  	this.cmpRoot.setLayout(new GridLayout (9, false));
	  	
	  	// Integer distribution
 		this.lbl = new Label(this.cmpRoot, SWT.NULL);
 		this.lbl.setText("Integer distribution: ");
 		this.distribution= new IntegerDistribution(this.cmpRoot);
	 		
 		GridData gridData = new GridData();
 		gridData.horizontalAlignment = GridData.FILL;
 		gridData.grabExcessHorizontalSpace = true;
 	    gridData.horizontalSpan = 8;
 		this.distribution.getCmpRoot().setLayoutData(gridData);
 		this.distribution.getCmpRoot().layout(true);
		
		// basePeriod
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Base period: ");
		
		this.btnBasePeriod = new Button(this.cmpRoot, SWT.CHECK);
		
		this.spnDays = new Spinner(this.cmpRoot, SWT.WRAP);
		this.spnDays.setEnabled(false);
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("days");
		
		this.spnMonths = new Spinner(this.cmpRoot, SWT.WRAP);
		this.spnMonths.setEnabled(false);
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("months");
		
		this.spnYears = new Spinner(this.cmpRoot, SWT.WRAP);
		this.spnYears.setEnabled(false);
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("years");
		
		this.dtTimePeriod = new DateTime(this.cmpRoot, SWT.TIME);
		this.dtTimePeriod.setTime(0, 0, 0);
		this.dtTimePeriod.setEnabled(false);
	    
		// shiftConstant
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Shift constant: ");
		
		this.btnShiftConstant = new Button(this.cmpRoot, SWT.CHECK);
		this.btnShiftConstant.setEnabled(false);
		
		this.spnShiftConstantInput = new Spinner(this.cmpRoot, SWT.BORDER);
		this.spnShiftConstantInput.setEnabled(false);
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("(int)");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 6;
	    this.lbl.setLayoutData(gridData);
		

	    this.btnBasePeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnBasePeriod.getSelection()) {
					spnDays.setEnabled(true);
					spnMonths.setEnabled(true);
					spnYears.setEnabled(true);
					dtTimePeriod.setEnabled(true);
					btnShiftConstant.setEnabled(true);
				} else {
					spnDays.setEnabled(false);
					spnMonths.setEnabled(false);
					spnYears.setEnabled(false);
					dtTimePeriod.setEnabled(false);
					btnShiftConstant.setEnabled(false);
					spnShiftConstantInput.setEnabled(false);
				}
			}
		});
		
		btnShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnShiftConstant.getSelection()) {
					spnShiftConstantInput.setEnabled(true);
				} else {
					spnShiftConstantInput.setEnabled(false);
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