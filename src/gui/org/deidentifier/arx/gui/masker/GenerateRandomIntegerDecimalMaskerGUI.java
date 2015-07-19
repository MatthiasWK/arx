import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomIntegerDecimalMaskerGUI implements ConfigurationComponent {
	
	private Label lbl;
	
	private Button btnCheckShiftConstant;
	
	private Spinner spnShiftConstant;
	
	private IntegerDistribution distribution;

	private Composite cmpRoot;
	
	
	public GenerateRandomIntegerDecimalMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
		this.cmpRoot.setLayout(new GridLayout (9, false));
      	
      	// Integer distribution
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Integer distribution: ");
		this.distribution = new IntegerDistribution(this.cmpRoot);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = 8;
	    this.distribution.getCmpRoot().setLayoutData(gridData);
	    this.distribution.getCmpRoot().layout(true);
      	
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
