import org.apache.commons.math3.distribution.IntegerDistribution;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.GenerateRandomDateMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.joda.time.Period;

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
	private Spinner spnWeeks;
	private Spinner spnMonths;
	private Spinner spnYears;
	
	private IntegerDistributionGUI distribution;
	private Label lblDist;

	private Boolean shiftConstantValid = false;
	private Boolean basePeriodValid = false;
	
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
      	this.cmpConfig.setLayout(SWTUtil.createGridLayout(11));
	    
		// baseDate
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("Base Date: ");
		this.lbl.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.dtDate = new DateTime(this.cmpConfig, SWT.CALENDAR);
		GridData gridData = SWTUtil.createFillVerticallyGridData();
	    gridData.horizontalSpan = 9;
	    this.dtDate.setLayoutData(gridData);
		this.dtTime = new DateTime(this.cmpConfig, SWT.TIME);
		this.dtTime.setTime(0, 0, 0);
		
		// shiftConstant
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("Shift constant: ");
		
		this.btnCheckShiftConstant = new Button(this.cmpConfig, SWT.CHECK);
		
		this.spnShiftConstant = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnShiftConstant.setEnabled(false);
		this.spnShiftConstant.setMaximum(Integer.MAX_VALUE);
		this.spnShiftConstant.setMinimum(Integer.MIN_VALUE);
		
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("(int)");
	    this.lbl.setLayoutData(SWTUtil.createSpanColumnsAndFillGridData(8));
		
	    this.btnCheckShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnCheckShiftConstant.getSelection()) {
					spnShiftConstant.setEnabled(true);
				} else {
					spnShiftConstant.setEnabled(false);
				}
				validateShiftConstant();
			}
		});
	    
	    this.spnShiftConstant.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent arg0) {
	    		validateShiftConstant();
	    	}
	    });
		
		// basePeriod
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("Base period: ");
		
		this.btnCheckBasePeriod = new Button(this.cmpConfig, SWT.CHECK);
		
		this.spnDays = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnDays.setEnabled(false);
		this.spnDays.setMaximum(Integer.MAX_VALUE);
		this.spnDays.setMinimum(Integer.MIN_VALUE);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("days");
		
		this.spnWeeks = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnWeeks.setEnabled(false);
		this.spnWeeks.setMaximum(Integer.MAX_VALUE);
		this.spnWeeks.setMinimum(Integer.MIN_VALUE);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("weeks");
		
		this.spnMonths = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnMonths.setEnabled(false);
		this.spnMonths.setMaximum(Integer.MAX_VALUE);
		this.spnMonths.setMinimum(Integer.MIN_VALUE);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("months");
		
		this.spnYears = new Spinner(this.cmpConfig, SWT.BORDER);
		this.spnYears.setEnabled(false);
		this.spnYears.setMaximum(Integer.MAX_VALUE);
		this.spnYears.setMinimum(Integer.MIN_VALUE);
		this.lbl = new Label(this.cmpConfig, SWT.NULL);
		this.lbl.setText("years");
		
		this.dtTimePeriod = new DateTime(this.cmpConfig, SWT.TIME);
		this.dtTimePeriod.setTime(0, 0, 0);
		this.dtTimePeriod.setEnabled(false);
		
		this.btnCheckBasePeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnCheckBasePeriod.getSelection()) {
					spnDays.setEnabled(true);
					spnWeeks.setEnabled(true);
					spnMonths.setEnabled(true);
					spnYears.setEnabled(true);
					dtTimePeriod.setEnabled(true);
				} else {
					spnDays.setEnabled(false);
					spnWeeks.setEnabled(false);
					spnMonths.setEnabled(false);
					spnYears.setEnabled(false);
					dtTimePeriod.setEnabled(false);
				}
				validateBasePeriod();
			}
		});
		
		this.spnDays.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent arg0) {
	    		validateBasePeriod();
	    	}
	    });
		
		this.spnWeeks.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent arg0) {
	    		validateBasePeriod();
	    	}
	    });
		
		this.spnMonths.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent arg0) {
	    		validateBasePeriod();
	    	}
	    });
		
		this.spnYears.addModifyListener(new ModifyListener() {
	    	public void modifyText(ModifyEvent arg0) {
	    		validateBasePeriod();
	    	}
	    });
		
		this.dtTimePeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				validateBasePeriod();
			}
		});
		
		validateShiftConstant();
		validateBasePeriod();
	}

	private void validateShiftConstant() {
		if (this.spnShiftConstant.getEnabled()) {
			this.shiftConstantValid = (this.spnShiftConstant.getSelection() == 0) ? false : true;
		} else {
			this.shiftConstantValid = true;
		}
	}
	
	private void validateBasePeriod() {
		if (this.btnCheckBasePeriod.getSelection()) {
			if (this.spnDays.getSelection() != 0 || this.spnWeeks.getSelection() != 0 ||
					this.spnMonths.getSelection()!= 0 || this.spnYears.getSelection() != 0 ||
					this.dtTimePeriod.getHours() != 0 || this.dtTimePeriod.getMinutes() != 0 ||
					this.dtTimePeriod.getSeconds() != 0) {
				this.basePeriodValid = true;
			} else {
				this.basePeriodValid = false;
			}
		} else {
			this.basePeriodValid = true;
		}
	}
	
	public boolean isValid() {
		return this.distribution.isValid() && this.shiftConstantValid && this.basePeriodValid;
	}

	public void addModifyListener(ModifyListener listener) {
		this.distribution.addModifyListener(listener);
		this.spnShiftConstant.addModifyListener(listener);
		this.spnDays.addModifyListener(listener);
		this.spnWeeks.addModifyListener(listener);
		this.spnMonths.addModifyListener(listener);
		this.spnYears.addModifyListener(listener);
	}

	public void addSelectionListener(SelectionAdapter adapter) {
		this.distribution.addSelectionListener(adapter);
		this.dtDate.addSelectionListener(adapter);
		this.dtTime.addSelectionListener(adapter);
		this.btnCheckShiftConstant.addSelectionListener(adapter);
		this.btnCheckBasePeriod.addSelectionListener(adapter);
		this.dtTimePeriod.addSelectionListener(adapter);
	}

	public GenerateRandomDateMasker getMasker() {
		IntegerDistribution dist = this.distribution.getDistribution(); // TODO: getDistribution()
		org.joda.time.DateTime baseDate = new org.joda.time.DateTime(	this.dtDate.getYear(),
																		this.dtDate.getMonth()+1,
																		this.dtDate.getDay(),
																		this.dtTime.getHours(),
																		this.dtTime.getMinutes(),
																		this.dtTime.getSeconds()
																	);
		if (this.btnCheckShiftConstant.getSelection() && this.btnCheckBasePeriod.getSelection() == false) {
			return new GenerateRandomDateMasker(dist, this.spnShiftConstant.getSelection(), baseDate);
		} else if (this.btnCheckShiftConstant.getSelection() == false && this.btnCheckBasePeriod.getSelection()) {
			Period basePeriod = new Period(	this.spnYears.getSelection(),
											this.spnMonths.getSelection(),
											this.spnWeeks.getSelection(),
											this.spnDays.getSelection(),
											this.dtTimePeriod.getHours(),
											this.dtTimePeriod.getMinutes(),
											this.dtTimePeriod.getSeconds(),
											0);
			return new GenerateRandomDateMasker(dist, baseDate, basePeriod);
		} else if (this.btnCheckShiftConstant.getSelection() && this.btnCheckBasePeriod.getSelection()) {
			Period basePeriod = new Period(	this.spnYears.getSelection(),
											this.spnMonths.getSelection(),
											this.spnWeeks.getSelection(),
											this.spnDays.getSelection(),
											this.dtTimePeriod.getHours(),
											this.dtTimePeriod.getMinutes(),
											this.dtTimePeriod.getSeconds(),
											0);
			return new GenerateRandomDateMasker(dist, spnShiftConstant.getSelection(), baseDate, basePeriod);
		} else {
			return new GenerateRandomDateMasker(dist, baseDate);
		}
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