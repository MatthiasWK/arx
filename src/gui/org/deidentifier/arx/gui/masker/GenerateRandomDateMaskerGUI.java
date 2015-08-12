import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.GenerateRandomDateMasker;
import org.deidentifier.arx.masking.RandomShiftDecimalMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomDateMaskerGUI implements ConfigurationComponent {
	
	private Composite cmpRoot;
	private Composite cmpDist;
	private Composite cmpConfig;
	
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
	
	private IntegerDistributionGUI distribution;
	private Label lblDist;


	
	public GenerateRandomDateMaskerGUI(Composite root) {
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(SWTUtil.createGridLayout(1));
		
		// Integer distribution
		this.lblDist = new Label(this.cmpRoot, SWT.NONE);
		this.lblDist.setText("Integer distribution:");
		this.lblDist.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.distribution = new IntegerDistributionGUI(this.cmpRoot);
		
		this.cmpConfig = new Composite(cmpRoot, SWT.NONE);
		this.cmpConfig.setLayoutData(SWTUtil.createFillGridData());
      	this.cmpConfig.setLayout(SWTUtil.createGridLayout(9));
	    
		// baseDate
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("Base Date: ");
		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		this.dtDate = new DateTime(this.cmpConfig, SWT.CALENDAR);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 7;
	    this.dtDate.setLayoutData(gridData);
		this.dtTime = new DateTime(this.cmpConfig, SWT.TIME);
		this.dtTime.setTime(0, 0, 0);
		
		// shiftConstant
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("Shift constant: ");
		
		this.btnCheckShiftConstant = new Button(this.cmpConfig, SWT.CHECK);
		
		this.spnShiftConstant = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnShiftConstant.setEnabled(false);
		this.spnShiftConstant.setMaximum(100000);
		
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
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
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("Base period: ");
		
		this.btnCheckBasePeriod = new Button(this.cmpConfig, SWT.CHECK);
		
		this.spnDays = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnDays.setEnabled(false);
		this.spnDays.setMaximum(100000);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("days");
		
		this.spnMonths = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnMonths.setEnabled(false);
		this.spnMonths.setMaximum(100000);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("months");
		
		this.spnYears = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnYears.setEnabled(false);
		this.spnYears.setMaximum(100000);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("years");
		
		this.dtTimePeriod = new DateTime(this.cmpConfig, SWT.TIME);
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

	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addModifyListener(ModifyListener listener) {
		this.distribution.addModifyListener(listener);
		// TODO Auto-generated method stub
		
	}

	public void addSelectionListener(SelectionAdapter adapter) {
		this.distribution.addSelectionListener(adapter);
		// TODO Auto-generated method stub
		
	}

	public GenerateRandomDateMasker getMasker() {
		// TODO Auto-generated method stub
		return null;
	}

	// For testing purposes
		public static void main(String[] args) {
			Display display = new Display();
			Shell shell = new Shell (display);
			shell.setLayout(SWTUtil.createGridLayout(1));
			Composite root = new Composite(shell, SWT.NONE);	
			root.setLayout(new FillLayout());
			root.setLayoutData(SWTUtil.createFillGridData());
			
			final ConfigurationComponent cmp = new GenerateRandomDateMaskerGUI(root);
			
			Composite buttons = new Composite(shell, SWT.NONE);
		    buttons.setLayout(SWTUtil.createGridLayout(2));
		    buttons.setLayoutData(SWTUtil.createFillHorizontallyGridData());
		    
			final Button next = new Button(buttons, SWT.PUSH);
			next.setText("ok");
			next.setLayoutData(SWTUtil.createFillHorizontallyGridData());
			next.setEnabled(cmp.isValid());
			
			next.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					GenerateRandomDateMasker masker = ((GenerateRandomDateMaskerGUI) cmp).getMasker();
				}
			});

			cmp.addModifyListener(new ModifyListener(){
				public void modifyText(ModifyEvent arg0) {
					next.setEnabled(cmp.isValid());				
				}
				
			});
			
			cmp.addSelectionListener(new SelectionAdapter(){
				public void  widgetSelected(SelectionEvent event) {
					next.setEnabled(cmp.isValid());				
				}
				
			});
			shell.pack ();
			shell.open ();

			while (!shell.isDisposed ()) {
				if (!display.readAndDispatch ())
					display.sleep ();
			}
			display.dispose ();
		}
}