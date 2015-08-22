import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.lang.math.NumberUtils;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;


public class IntegerDistributionGUI implements ConfigurationComponent{
	
	private Composite cmpRoot;
	private Combo cmbDropdown;
	private Composite cmpParam;
	
	private Label lbl1;
	private Label lbl2;
	private Label lbl3;
	
	private Spinner spn1;
	private Spinner spn2;
	
	private Text txtSingletons;
	private Text txtProbabilities;
	private Text txt1;
	private Text txt2;
	
	private Button btn1;
	private Button btn2;
	
	private boolean dropDownValid = false;
	private boolean spn1Valid = false;
	private boolean spn2Valid = false;
	private boolean txt1Valid = false;
	private boolean txt2Valid = false;
	
	private ModifyListener MListener;
	private SelectionAdapter SAdapter;
	
	public IntegerDistributionGUI(Composite root){
		this.cmpRoot = new Composite(root, SWT.BORDER);
		this.cmpRoot.setLayoutData(SWTUtil.createFillGridData());
		this.cmpRoot.setLayout(SWTUtil.createGridLayout(1));
		
		this.cmbDropdown = new Combo(cmpRoot, SWT.READ_ONLY);
		this.cmbDropdown.setLayoutData(SWTUtil.createFillHorizontallyGridData());

	    this.cmbDropdown.setText("Choose your distribution...");
		String[] distributions = {	
									"Binomial Distribution",
									"Poisson Distribution",
									"Uniform Integer Distribution"/*,
									"Abstract Integer Distribution",
									"Enumerated Integer Distribution",
									"Geometric Distribution",
									"Hypergeometric Distribution",
									"Pascal Distribution",
									"Zipf Distribution"*/
								 };
		for (String s: distributions) {
			this.cmbDropdown.add(s);
		}
		
		// composite for parameters of integer distribution
		this.cmpParam = new Composite(cmpRoot, SWT.NONE);
	    this.cmpParam.setLayoutData(SWTUtil.createFillGridData());
	    
			    this.cmbDropdown.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						int selectedIndex = cmbDropdown.getSelectionIndex();
						for (Control child: cmpParam.getChildren()) {
							child.dispose();
						}
						switch (selectedIndex) {
							
							case 0: // Binomial Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int trials, double p");
								cmpParam.setLayout(SWTUtil.createGridLayout(2));
								
								lbl1 = new Label(cmpParam, SWT.NONE);
								lbl1.setText("Trials:");
								lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(1000000);
								spn1.setLayoutData(SWTUtil.createGridData());
								
								lbl2 = new Label(cmpParam, SWT.NONE);
								lbl2.setText("Probability of Success:");
								lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								txt1 = new Text(cmpParam, SWT.BORDER);
								txt1.setLayoutData(SWTUtil.createGridData());
								txt1.addModifyListener(new ModifyListener(){
									public void modifyText(ModifyEvent arg0) {
										validateTxt1();
									}						
								});
								
								txt1.addModifyListener(MListener);
								break;
							case 1: // Poisson Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p [, double epsilon] [, int maxIterations]");
								cmpParam.setLayout(SWTUtil.createGridLayout(3));

								lbl1 = new Label(cmpParam, SWT.NONE);
								lbl1.setText("Specified Mean:");
								lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
								
								txt1 = new Text(cmpParam, SWT.BORDER);
							    txt1.setLayoutData(SWTUtil.createGridData());
								
								lbl2 = new Label(cmpParam, SWT.NONE);
								lbl2.setText("Convergence Criterion:");
								lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
							    btn1 = new Button(cmpParam, SWT.CHECK);
							    btn1.setLayoutData(SWTUtil.createGridData());
							    
							    txt2 = new Text(cmpParam, SWT.BORDER);
							    txt2.setEnabled(false);
							    txt2.setLayoutData(SWTUtil.createGridData());
							    
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

								lbl3 = new Label(cmpParam, SWT.NONE);
								lbl3.setText("Maximum Number of Iterations:");
								lbl3.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
							    btn2 = new Button(cmpParam, SWT.CHECK);
							    btn2.setLayoutData(SWTUtil.createGridData());
							    
							    spn1 = new Spinner(cmpParam, SWT.BORDER);
							    spn1.setEnabled(false);
							    spn1.setMaximum(10000000);
							    spn1.setLayoutData(SWTUtil.createGridData());
							    
								btn2.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent event) {
										if (btn2.getSelection()) {
											spn1.setEnabled(true);
										} else {
											spn1.setEnabled(false);
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
								
								txt1.addModifyListener(MListener);
								txt2.addModifyListener(MListener);
								btn1.addSelectionListener(SAdapter);
								break;
							case 2: // Uniform Integer Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int lower, int upper");
								cmpParam.setLayout(SWTUtil.createGridLayout(2));
								
								lbl1 = new Label(cmpParam, SWT.NONE);
								lbl1.setText("Lower Bound (inclusive):");
								lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(1000000);
								spn1.setLayoutData(SWTUtil.createGridData());

								lbl2 = new Label(cmpParam, SWT.NONE);
								lbl2.setText("Upper Bound (inclusive):");
								lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn2= new Spinner(cmpParam, SWT.BORDER);
								spn2.setMaximum(1000000);
								spn2.setLayoutData(SWTUtil.createGridData());
								
								spn1.addModifyListener(new ModifyListener(){
									public void modifyText(ModifyEvent arg0) {
										validateSpn1();
										validateSpn2();
									}						
								});
								
								spn2.addModifyListener(new ModifyListener(){
									public void modifyText(ModifyEvent arg0) {
										validateSpn1();
										validateSpn2();
									}						
								});
								
								spn1.addModifyListener(MListener);
								spn2.addModifyListener(MListener);

								break;
								/*
							case 3: // Enumerated Integer Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int[] singletons, double[] probabilities");
								cmpParam.setLayout(new GridLayout (2, false));
								
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("array of random\n variable values\n (int values)");
								txtSingletons = new Text(cmpParam, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
								GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
								gridData.heightHint = 30;
								txtSingletons.setLayoutData(gridData);
								
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("array of probabilities\n (double values)");
								txtProbabilities = new Text(cmpParam, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
								gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
								gridData.heightHint = 30;
								txtProbabilities.setLayoutData(gridData);
								
								cmpParam.layout();
								cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								cmpRoot.layout();
								cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								break;
							case 4: // Abstract Integer Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": RandomGenerator rng");
								break;
							case 5: // Geometric Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p");
								cmpParam.setLayout(new GridLayout (2, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setDigits(2);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("probability of success");
								
								cmpParam.layout();
								cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								cmpRoot.layout();
								cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								break;
							case 6: // Hypergeometric Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int populationSize, int numberOfSuccesses, int sampleSize");
								cmpParam.setLayout(new GridLayout (2, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("population size");
								
								spn2 = new Spinner(cmpParam, SWT.BORDER);
								spn2.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("number of successes");
								
								spn3 = new Spinner(cmpParam, SWT.BORDER);
								spn3.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("sample size");
								
								cmpParam.layout();
								cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								cmpRoot.layout();
								cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								break;
							case 7: // Pascal Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int r, double p");
								cmpParam.setLayout(new GridLayout (2, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("number of successes");
								
								spn2 = new Spinner(cmpParam, SWT.BORDER);
								spn2.setDigits(2);
								spn2.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("probability of success");
								
								cmpParam.layout();
								cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								cmpRoot.layout();
								cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								break;
							case 8: // Zipf Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int numberOfElements, double exponent");
								cmpParam.setLayout(new GridLayout (2, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("number of elements");
								
								spn2 = new Spinner(cmpParam, SWT.BORDER);
								spn2.setDigits(2);
								spn2.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("exponent");
								
								cmpParam.layout();
								cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								cmpRoot.layout();
								cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
								break;
								*/
						}

					    validateSpn1();
					    validateSpn2();
					    validateTxt1();
					    validateTxt2();
						validateCmbDropdown();
						cmpRoot.layout(true, true);
					}
			    });
			    validateCmbDropdown();
	}
	
	private void validateCmbDropdown() {
        this.dropDownValid = this.cmbDropdown.getSelectionIndex() != -1;
    }

	private void validateSpn1(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Binomial
			this.spn1Valid = true;
			break;
		case 1: // Poisson
			this.spn1Valid = true;
			break;
		case 2: // Uniform Integer
			if (this.spn1.getSelection() < this.spn2.getSelection()){
				spn1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				this.spn1Valid = true;
			}
			else{
				spn1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
				this.spn1Valid = false;
			}
			break;
		}
	}
	
	private void validateSpn2(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Binomial
			this.spn2Valid = true;
			break;
		case 1: // Poisson
			this.spn2Valid = true;
			break;
		case 2: // Uniform Integer
			if (this.spn1.getSelection() < this.spn2.getSelection()){
				spn2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				this.spn2Valid = true;
			}
			else{
				spn2.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
				this.spn2Valid = false;
			}
			break;
		}
	}

	
	private void validateTxt1(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Binomial
			if(NumberUtils.isNumber(this.txt1.getText())){
				double inputDouble = Double.parseDouble(this.txt1.getText());
				if(inputDouble <= 1.0 && inputDouble >=0.0){
					this.txt1Valid = true;
					txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
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
		case 1: // Poisson
			if(NumberUtils.isNumber(this.txt1.getText())){
				double inputDouble = Double.parseDouble(this.txt1.getText());
				if(inputDouble > 0.0){
					this.txt1Valid = true;
					txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
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
		case 2: // Uniform Integer
			this.txt1Valid = true;
			break;
		}
	}
	
	private void validateTxt2(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Binomial
			this.txt2Valid = true;
			break;
		case 1: // Poisson
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
		case 2: // Uniform Integer
			this.txt2Valid = true;
			break;
		}
	}
	public boolean isValid() {
		//ToDo: add validation
		return this.dropDownValid && this.spn1Valid && this.spn2Valid && this.txt1Valid && this.txt2Valid;
	}

	public void addModifyListener(ModifyListener listener) {
		//ToDo: add modifyListeners
		this.MListener = listener;
	}

	public void addSelectionListener(SelectionAdapter adapter) {		
		this.cmbDropdown.addSelectionListener(adapter);	
		this.SAdapter = adapter;
	}
	
	public IntegerDistribution getDistribution(){
		IntegerDistribution dist = null;
		switch(this.cmbDropdown.getSelectionIndex()){
			case 0: // Binomial
				double txtInput = Double.parseDouble(this.txt1.getText());
				dist = new BinomialDistribution(this.spn1.getSelection(), txtInput);
				break;
			case 1: // Poisson
				double txt1Input = Double.parseDouble(this.txt1.getText());
				double txt2Input = Double.parseDouble(this.txt2.getText());
				
				if(!btn1.getSelection() && !btn2.getSelection())
					dist = new PoissonDistribution(txt1Input);
				else if(btn1.getSelection() && !btn2.getSelection())
					dist = new PoissonDistribution(txt1Input, txt2Input);
				else if(!btn1.getSelection() && btn2.getSelection())
					dist = new PoissonDistribution(txt1Input, spn1.getSelection());	
				else if(btn1.getSelection() && btn2.getSelection())
					dist = new PoissonDistribution(txt1Input, txt2Input, spn1.getSelection());					
				break;
			case 2: // Uniform Integer
				dist = new UniformIntegerDistribution(spn1.getSelection(), spn2.getSelection());
				break;
		}
		return dist;
	}
	// For testing purposes
	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (SWTUtil.createGridLayout(1));
		shell.setText("IntegerDistribution");

		final IntegerDistributionGUI c1 = new IntegerDistributionGUI(shell);
		
		final Button ok = new Button(shell, SWT.PUSH);
		ok.setText("ok");
		ok.setEnabled(c1.isValid());
		ok.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				c1.getDistribution();
			}
		});
		
		c1.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent arg0) {
				ok.setEnabled(c1.isValid());
			}
    	});
		c1.addSelectionListener(new SelectionAdapter(){
			public void  widgetSelected(SelectionEvent event) {
				ok.setEnabled(c1.isValid());				
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
