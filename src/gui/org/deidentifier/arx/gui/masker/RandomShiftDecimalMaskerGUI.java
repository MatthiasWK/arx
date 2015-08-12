

import org.apache.commons.math3.distribution.RealDistribution;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.RandomShiftDecimalMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class RandomShiftDecimalMaskerGUI implements ConfigurationComponent{
	
	private Label lblDist;
	private Label lblShift;
	private Label lblDescription;
	
	private Button btnShiftConstant;
	
	private Spinner spnShiftConstantInput;
	
	private Boolean shiftConstantValid = false;
	private Boolean distributionValid = false;
	
	private RealDistributionGUI distribution;

	private Composite cmpRoot;
	private Composite cmpDesc;
	private Composite cmpConfig;
	private Composite cmpDist;
	private Composite cmpShift;
	
	public RandomShiftDecimalMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);	
		this.cmpRoot.setLayout(SWTUtil.createGridLayout(1));
		
		// Description
//		this.cmpDesc = new Composite(cmpRoot, SWT.BORDER);
//		this.cmpDesc.setLayoutData(SWTUtil.createFillHorizontallyGridData());
//		this.cmpDesc.setLayout(SWTUtil.createGridLayout(1));
//		this.lblDescription = new Label(this.cmpDesc, SWT.NONE);
// 		this.lblDescription.setText("A masker that shifts decimal numbers randomly according to a given probability distribution.\nThe shift is calculated by sampling from the provided distribution.\nOptionally, a shift constant can be added to the sampled value as to allow a quick and very basic modification of the distribution.");
//		this.lblDescription.setLayoutData(SWTUtil.createGridData());

      	// Real distribution
 		
		this.lblDist = new Label(this.cmpRoot, SWT.NONE);
 		this.lblDist.setText("Real distribution:");
		this.lblDist.setLayoutData(SWTUtil.createNoFillGridData());
		
 		this.distribution = new RealDistributionGUI(this.cmpRoot);
 		
		// shift constant
		this.cmpShift = new Composite(cmpRoot, SWT.NONE);
		this.cmpShift.setLayout(SWTUtil.createGridLayout(3));
		this.cmpShift.setLayoutData(SWTUtil.createFillGridData());
		
		this.lblShift = new Label(this.cmpShift, SWT.NONE);
		this.lblShift.setText("Shift constant: ");
		this.lblShift.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
 			
		this.btnShiftConstant = new Button(this.cmpShift, SWT.CHECK);
		this.btnShiftConstant.setLayoutData(SWTUtil.createGridData());
		
		this.spnShiftConstantInput = new Spinner(this.cmpShift, SWT.BORDER);
		this.spnShiftConstantInput.setEnabled(false);
		this.spnShiftConstantInput.setDigits(2);
		this.spnShiftConstantInput.setMaximum(100000);
		this.spnShiftConstantInput.setMinimum(-100000);
		this.spnShiftConstantInput.setLayoutData(SWTUtil.createGridData());
		
		this.btnShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnShiftConstant.getSelection()) {
					spnShiftConstantInput.setEnabled(true);
					validateSpnShiftConstantInput();
				} else {
					spnShiftConstantInput.setEnabled(false);
					validateSpnShiftConstantInput();
				}
			}
		});
		
		this.spnShiftConstantInput.addModifyListener(new ModifyListener() {
		    public void modifyText(ModifyEvent arg0) {
                validateSpnShiftConstantInput();
            }

		});
		
		this.distribution.addModifyListener(new ModifyListener() {
		    public void modifyText(ModifyEvent arg0) {
                validateDistribution();
            }

		});
		this.validateSpnShiftConstantInput();
		this.validateDistribution();
	}
	
	private void validateDistribution() {
		this.distributionValid = this.distribution.isValid();		
	}

	private void validateSpnShiftConstantInput() {
		if(this.spnShiftConstantInput.getEnabled()){
			double input = this.spnShiftConstantInput.getSelection()*.01d;
			this.shiftConstantValid = !(input == 0.00d);
		}
		else
			this.shiftConstantValid = true;
	}

	public boolean isValid() {
		return this.distribution.isValid() && shiftConstantValid;		
	}

	public void addModifyListener(ModifyListener listener) {
	    this.distribution.addModifyListener(listener);
	    this.spnShiftConstantInput.addModifyListener(listener);
	}
	
	public void addSelectionListener(SelectionAdapter adapter){
		this.btnShiftConstant.addSelectionListener(adapter);
		this.distribution.addSelectionListener(adapter);
	}
	
	public RandomShiftDecimalMasker getMasker(){
		RealDistribution dist = this.distribution.getDistribution(); // TODO: getDistribution() 
		if(!this.btnShiftConstant.getSelection()){
			return new RandomShiftDecimalMasker(dist);
		}
		else{
			return new RandomShiftDecimalMasker(dist, this.spnShiftConstantInput.getSelection()*.01d);
		}
	}
	// For testing purposes
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
		shell.setLayout(SWTUtil.createGridLayout(1));
		Composite root = new Composite(shell, SWT.NONE);	
		root.setLayout(new FillLayout() );
		root.setLayoutData(SWTUtil.createFillGridData());
		
		final ConfigurationComponent cmp = new RandomShiftDecimalMaskerGUI(root);
		
		Composite buttons = new Composite(shell, SWT.NONE);
	    buttons.setLayout(SWTUtil.createGridLayout(2));
	    buttons.setLayoutData(SWTUtil.createFillHorizontallyGridData());
	    
		final Button next = new Button(buttons, SWT.PUSH);
		next.setText("ok");
		next.setLayoutData(SWTUtil.createFillHorizontallyGridData());
		next.setEnabled(cmp.isValid());
		
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				RandomShiftDecimalMasker masker = ((RandomShiftDecimalMaskerGUI) cmp).getMasker();
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

