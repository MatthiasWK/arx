

import org.apache.commons.lang.math.NumberUtils;
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
	
	private Button btnShiftConstant;
	
	private Text txtShiftConstantInput;
	
	private Boolean shiftConstantValid = false;
	
	private RealDistributionGUI distribution;

	private Composite cmpRoot;
	private Composite cmpShift;
	
	public RandomShiftDecimalMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);	
		this.cmpRoot.setLayout(SWTUtil.createGridLayout(1));
		
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
		
		this.txtShiftConstantInput = new Text(this.cmpShift, SWT.BORDER);
		this.txtShiftConstantInput.setEnabled(false);
		this.txtShiftConstantInput.setLayoutData(SWTUtil.createGridData());
		
		this.btnShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (btnShiftConstant.getSelection()) {
					txtShiftConstantInput.setEnabled(true);
					validateTxtShiftConstantInput();
				} else {
					txtShiftConstantInput.setEnabled(false);
					validateTxtShiftConstantInput();
				}
			}
		});
		
		this.txtShiftConstantInput.addModifyListener(new ModifyListener() {
		    public void modifyText(ModifyEvent arg0) {
                validateTxtShiftConstantInput();
            }

		});
		
		this.validateTxtShiftConstantInput();
	}
	
	private void validateTxtShiftConstantInput() {
		if(!this.txtShiftConstantInput.getEnabled()){
			this.shiftConstantValid = true;
		}
		else if(NumberUtils.isNumber(this.txtShiftConstantInput.getText())){
			this.shiftConstantValid = true;
			this.txtShiftConstantInput.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}
		else{
			this.shiftConstantValid = false;
			this.txtShiftConstantInput.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
		}
	}

	public boolean isValid() {
		return this.distribution.isValid() && shiftConstantValid;		
	}

	public void addModifyListener(ModifyListener listener) {
	    this.distribution.addModifyListener(listener);
	    this.txtShiftConstantInput.addModifyListener(listener);
	}
	
	public void addSelectionListener(SelectionAdapter adapter){
		this.btnShiftConstant.addSelectionListener(adapter);
		this.distribution.addSelectionListener(adapter);
	}
	
	public RandomShiftDecimalMasker getMasker(){
		RealDistribution dist = this.distribution.getDistribution();
		if(!this.btnShiftConstant.getSelection()){
			return new RandomShiftDecimalMasker(dist);
		}
		else{
			return new RandomShiftDecimalMasker(dist, Double.parseDouble(this.txtShiftConstantInput.getText()));
		}
	}
}