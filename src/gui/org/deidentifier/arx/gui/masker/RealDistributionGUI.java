import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.Random;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;


public class RealDistributionGUI implements ConfigurationComponent {
	
	private Composite cmpRoot;
	
	private Combo cmbDropdown;
	private Composite cmpParam;
	
	private Label lbl1;
	private Label lbl2;
	private Label lbl3;
	
	private Text txt1;
	private Text txt2;
	private Text txt3;
	
	private boolean dropDownValid;	
	private Boolean txt1Valid = false;
	private Boolean txt2Valid = false;
	private Boolean txt3Valid = false;
	
	private Button btn1;

private SelectionAdapter SAdapter;

private ModifyListener MListener;


	public RealDistributionGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.BORDER);
		this.cmpRoot.setLayoutData(SWTUtil.createFillGridData());
		this.cmpRoot.setLayout(SWTUtil.createGridLayout(1));
		
		this.cmbDropdown = new Combo(cmpRoot, SWT.READ_ONLY);
		this.cmbDropdown.setLayoutData(SWTUtil.createFillHorizontallyGridData());

	    this.cmbDropdown.setText("Choose your distribution...");
	    String[] distributions = {	"Exponential Distribution",
									"Normal Distribution",
									"Chi Squared Distribution"
								 };
		for (String s: distributions) {
			this.cmbDropdown.add(s);
		}
		
		// composite for parameters of real distribution
		this.cmpParam = new Composite(cmpRoot, SWT.NONE);
	    this.cmpParam.setLayoutData(SWTUtil.createFillGridData());
	    
	    this.cmbDropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = cmbDropdown.getSelectionIndex();
				for (Control child: cmpParam.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
				case 0: // Exponential Distribution
//					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean [, double inverseCumAccuracy]");
					cmpParam.setLayout(SWTUtil.createGridLayout(3));
	
					lbl1 = new Label(cmpParam, SWT.NONE);
					lbl1.setText("Mean:");
				    lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					txt1 = new Text(cmpParam, SWT.BORDER);
					txt1.setLayoutData(SWTUtil.createGridData());
					
					lbl2 = new Label(cmpParam, SWT.NONE);
					lbl2.setText("Maximum absolute error\nin inverse cumulative\nprobability estimates:");
					lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
				    
					btn1 = new Button(cmpParam, SWT.CHECK);
					btn1.setLayoutData(SWTUtil.createGridData());
					
					txt2 = new Text(cmpParam, SWT.BORDER);
					txt2.setEnabled(false);
					txt2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
					
					btn1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							if (btn1.getSelection()) {
								txt2.setEnabled(true);
								validateTxt2();
							} else {
								txt2.setEnabled(false);
								validateTxt2();
							}
						}
					});
					
					txt1.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt1();
						}						
					});
					
					txt2.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt2();
						}						
					});
					
					btn1.addSelectionListener(SAdapter);
					txt1.addModifyListener(MListener);
					txt2.addModifyListener(MListener);
					break;
				case 1: // Normal Distribution
//					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean, double sd [, double inverseCumAccuracy]");
					
					cmpParam.setLayout(SWTUtil.createGridLayout(3));

					lbl1 = new Label(cmpParam, SWT.NONE);
					lbl1.setText("Mean:");
				    lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					txt1 = new Text(cmpParam, SWT.BORDER);
					txt1.setLayoutData(SWTUtil.createGridData());
					
					lbl2 = new Label(cmpParam, SWT.NONE);
					lbl2.setText("Standard deviation:");
				    lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					txt2 = new Text(cmpParam, SWT.BORDER);
					txt2.setLayoutData(SWTUtil.createGridData());
					
					lbl3 = new Label(cmpParam, SWT.NONE);
					lbl3.setText("Maximum absolute error\nin inverse cumulative\nprobability estimates:");
					lbl3.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
				    
					btn1 = new Button(cmpParam, SWT.CHECK);
					btn1.setLayoutData(SWTUtil.createGridData());
					
					txt3 = new Text(cmpParam, SWT.BORDER);
					txt3.setEnabled(false);
					txt3.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
					
					btn1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							if (btn1.getSelection()) {
								txt3.setEnabled(true);
								validateTxt3();
							} else {
								txt3.setEnabled(false);
								validateTxt3();
							}
						}
					});
					
					txt1.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt1();
						}						
					});
					
					txt2.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt2();
						}						
					});
					
					txt3.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt3();
						}						
					});
					
					btn1.addSelectionListener(SAdapter);
					txt1.addModifyListener(MListener);
					txt2.addModifyListener(MListener);
					txt3.addModifyListener(MListener);
					break;
				case 2: // Chi Squared Distribution
//					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
					cmpParam.setLayout(SWTUtil.createGridLayout(3));

					lbl1 = new Label(cmpParam, SWT.NONE);
					lbl1.setText("Degrees of freedom:");
				    lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					txt1 = new Text(cmpParam, SWT.BORDER);
					txt1.setLayoutData(SWTUtil.createGridData());
					
					lbl2 = new Label(cmpParam, SWT.NONE);
					lbl2.setText("Maximum absolute error\nin inverse cumulative\nprobability estimates:");
					lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
				    
					btn1 = new Button(cmpParam, SWT.CHECK);
					btn1.setLayoutData(SWTUtil.createGridData());
					
					txt2 = new Text(cmpParam, SWT.BORDER);
					txt2.setEnabled(false);
					txt2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
					
					btn1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							if (btn1.getSelection()) {
								txt2.setEnabled(true);
								validateTxt2();
							} else {
								txt2.setEnabled(false);
								validateTxt2();
							}
						}
					});
					
					txt1.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt1();
						}						
					});
					
					txt2.addModifyListener(new ModifyListener(){
						public void modifyText(ModifyEvent arg0) {
							validateTxt2();
						}						
					});
					
					btn1.addSelectionListener(SAdapter);
					txt1.addModifyListener(MListener);
					txt2.addModifyListener(MListener);
					break;
					
				}

		        validateTxt1();
		        validateTxt2();
		        validateTxt3();
				validateCmbDropdown();
				cmpRoot.layout(true, true);
			}
		});
	    
	    validateCmbDropdown();
	}
	
	private void validateTxt1(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Exponential
			if(NumberUtils.isNumber(this.txt1.getText())){
				double inputDouble = Double.parseDouble(this.txt1.getText());
				if(inputDouble > 0.0){
					this.txt1Valid = true;
					this.txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}
				else{
					this.txt1Valid = false;
					txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
				}
			}
			else{
				this.txt1Valid = false;
				txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		case 1: // Normal
			if(NumberUtils.isNumber(this.txt1.getText())){
				this.txt1Valid = true;
				this.txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}
			else{
				this.txt1Valid = false;
				this.txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		case 2: // Chi Squared
			if(NumberUtils.isNumber(this.txt1.getText())){
				double inputDouble = Double.parseDouble(this.txt1.getText());
				if(inputDouble > 0.0){
					this.txt1Valid = true;
					this.txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}
				else{
					this.txt1Valid = false;
					txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
				}
			}
			else{
				this.txt1Valid = false;
				txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		}
	}
	
	private void validateTxt2(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Exponential
			if(!this.txt2.getEnabled()){
				this.txt2Valid = true;
			}
			else if(NumberUtils.isNumber(this.txt2.getText())){
				this.txt2Valid = true;
				this.txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}
			else{
				this.txt2Valid = false;
				this.txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		case 1: // Normal
			if(NumberUtils.isNumber(this.txt2.getText())){
				double inputDouble = Double.parseDouble(this.txt2.getText());
				if(inputDouble > 0.0){
					this.txt2Valid = true;
					this.txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}
				else{
					this.txt2Valid = false;
					txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
				}
			}
			else{
				this.txt2Valid = false;
				txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		case 2: // Chi Squared
			if(!this.txt2.getEnabled()){
				this.txt2Valid = true;
			}
			else if(NumberUtils.isNumber(this.txt2.getText())){
				this.txt2Valid = true;
				this.txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}
			else{
				this.txt2Valid = false;
				this.txt2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		}
	}

	private void validateTxt3(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Exponential
			this.txt3Valid = true;
			break;
		case 1: // Normal
			if(!this.txt3.getEnabled()){
				this.txt3Valid = true;
			}
			else if(NumberUtils.isNumber(this.txt3.getText())){
				this.txt3Valid = true;
				this.txt3.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}
			else{
				this.txt3Valid = false;
				this.txt3.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			break;
		case 2: // Chi Squared
			this.txt3Valid = true;
			break;
		}
	}
	
	private void validateCmbDropdown() {
        this.dropDownValid = this.cmbDropdown.getSelectionIndex() != -1;
    }
	
	public boolean isValid() {		
		return this.dropDownValid && this.txt1Valid && this.txt2Valid && this.txt3Valid;
		}

	public void addModifyListener(ModifyListener listener) {
		this.MListener = listener;
	}

	public void addSelectionListener(SelectionAdapter adapter) {		
		this.cmbDropdown.addSelectionListener(adapter);	
		this.SAdapter = adapter;
	}
	
	public RealDistribution getDistribution(){
		RealDistribution dist = null;
		switch(this.cmbDropdown.getSelectionIndex()){
			case 0: // Exponential
				if(!btn1.getSelection())
					dist = new ExponentialDistribution(Double.parseDouble(txt1.getText()));
				else
					dist = new ExponentialDistribution(Double.parseDouble(txt1.getText()), Double.parseDouble(txt2.getText()));
				break;
			case 1: // Normal
				if(!btn1.getSelection())
					dist = new NormalDistribution(Double.parseDouble(txt1.getText()), Double.parseDouble(txt2.getText()));
				else
					dist = new NormalDistribution(Double.parseDouble(txt1.getText()), Double.parseDouble(txt2.getText()), Double.parseDouble(txt3.getText()));
				break;
			case 2: // Chi Squared
				if(!btn1.getSelection())
					dist = new ExponentialDistribution(Double.parseDouble(txt1.getText()));
				else
					dist = new ExponentialDistribution(Double.parseDouble(txt1.getText()), Double.parseDouble(txt2.getText()));
				break;
		}
		return dist;
	}
	
	
}
