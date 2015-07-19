import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomDateMaskerGUI implements ConfigurationComponent {
	
	private Composite cmpRoot;
	
	private Label lbl;
	
	private DateTime dtDate;
	private DateTime dtTime;
	private DateTime dtTimePeriod;
	
	private Button btnCheckShiftConstant;
	private Button btnCheckBasePeriod;
	
	private Spinner spnShiftConstant;
	private Spinner spnDays;
	private Spinner spnMonths;
	private Spinner spnYears;
	
	private IntegerDistribution distribution;

	
	public GenerateRandomDateMaskerGUI(Composite root) {
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(new GridLayout (9, false));
		
		// Integer distribution
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Integer distribution: ");
//		Composite distributions = MaskerTool.loadIntegerDistributions(8);
//		distributions.setParent(this.cmpRoot);
		
		this.distribution = new IntegerDistribution(this.cmpRoot);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = 8;
//		distributions.setLayoutData(gridData);
//		distributions.layout(true);
	    this.distribution.getCmpRoot().setLayoutData(gridData);
	    this.distribution.getCmpRoot().layout(true);
	    
		// baseDate
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Base Date: ");
		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		this.dtDate = new DateTime(this.cmpRoot, SWT.CALENDAR);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 7;
	    this.dtDate.setLayoutData(gridData);
		this.dtTime = new DateTime(this.cmpRoot, SWT.TIME);
		this.dtTime.setTime(0, 0, 0);
		
		// shiftConstant
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Shift constant: ");
		
		this.btnCheckShiftConstant = new Button(this.cmpRoot, SWT.CHECK);
		
		this.spnShiftConstant = new Spinner(this.cmpRoot, SWT.BORDER);
		this.spnShiftConstant.setEnabled(false);
		this.spnShiftConstant.setMaximum(100000);
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("(int)");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 6;
	    this.lbl.setLayoutData(gridData);
		
	    this.btnCheckShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnCheckShiftConstant.getSelection()) {
					spnShiftConstant.setEnabled(true);
				} else {
					spnShiftConstant.setEnabled(false);
				}
			}
		});
		
		// basePeriod
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Base period: ");
		
		this.btnCheckBasePeriod = new Button(this.cmpRoot, SWT.CHECK);
		
		this.spnDays = new Spinner(this.cmpRoot, SWT.WRAP);
		this.spnDays.setEnabled(false);
		this.spnDays.setMaximum(100000);
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("days");
		
		this.spnMonths = new Spinner(this.cmpRoot, SWT.WRAP);
		this.spnMonths.setEnabled(false);
		this.spnMonths.setMaximum(100000);
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("months");
		
		this.spnYears = new Spinner(this.cmpRoot, SWT.WRAP);
		this.spnYears.setEnabled(false);
		this.spnYears.setMaximum(100000);
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("years");
		
		this.dtTimePeriod = new DateTime(this.cmpRoot, SWT.TIME);
		this.dtTimePeriod.setTime(0, 0, 0);
		this.dtTimePeriod.setEnabled(false);
		
		this.btnCheckBasePeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnCheckBasePeriod.getSelection()) {
					spnDays.setEnabled(true);
					spnMonths.setEnabled(true);
					spnYears.setEnabled(true);
					dtTimePeriod.setEnabled(true);
				} else {
					spnDays.setEnabled(false);
					spnMonths.setEnabled(false);
					spnYears.setEnabled(false);
					dtTimePeriod.setEnabled(false);
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