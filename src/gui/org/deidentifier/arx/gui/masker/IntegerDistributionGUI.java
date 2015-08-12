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
	private Spinner spn3;
	
	private Text txtSingletons;
	private Text txtProbabilities;
	
	private Button btn1;
	private Button btn2;
	
	private boolean dropDownValid;
	
	private ModifyListener MListener;
	
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
								cmpParam.setLayout(new GridLayout (2, false));
								
								lbl1 = new Label(cmpParam, SWT.NONE);
								lbl1.setText("Trials:");
								lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(1000000);
								spn1.setLayoutData(SWTUtil.createGridData());
								spn1.addModifyListener(MListener);
								
								lbl2 = new Label(cmpParam, SWT.NONE);
								lbl2.setText("Probability of Success:");
								lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn2 = new Spinner(cmpParam, SWT.BORDER);
								spn2.setDigits(2);
								spn2.setLayoutData(SWTUtil.createGridData());
								break;
							case 1: // Poisson Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p [, double epsilon] [, int maxIterations]");
								cmpParam.setLayout(new GridLayout (3, false));

								lbl1 = new Label(cmpParam, SWT.NONE);
								lbl1.setText("Specified Mean:");
								lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setDigits(2);
								spn1.setMaximum(100000);
								spn1.setMinimum(1);
								spn1.setLayoutData(SWTUtil.createGridData());
								
								lbl2 = new Label(cmpParam, SWT.NONE);
								lbl2.setText("Convergence Criterion:");
								lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
							    btn1 = new Button(cmpParam, SWT.CHECK);
							    btn1.setLayoutData(SWTUtil.createGridData());
							    
							    spn2 = new Spinner(cmpParam, SWT.BORDER);
							    spn2.setDigits(2);
							    spn2.setEnabled(false);
							    spn2.setMaximum(100000);
							    spn2.setLayoutData(SWTUtil.createGridData());
							    
								btn1.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent event) {
										if (btn1.getSelection()) {
											spn2.setEnabled(true);
										} else {
											spn2.setEnabled(false);
										}
									}
								});

								lbl3 = new Label(cmpParam, SWT.NONE);
								lbl3.setText("Maximum Number of Iterations:");
								lbl3.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
							    btn2 = new Button(cmpParam, SWT.CHECK);
							    btn2.setLayoutData(SWTUtil.createGridData());
							    
							    spn3 = new Spinner(cmpParam, SWT.BORDER);
							    spn3.setEnabled(false);
							    spn3.setMaximum(10000000);
							    spn3.setLayoutData(SWTUtil.createGridData());
							    
								btn2.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent event) {
										if (btn2.getSelection()) {
											spn3.setEnabled(true);
										} else {
											spn3.setEnabled(false);
										}
									}
								});
								break;
							case 2: // Uniform Integer Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int lower, int upper");
								cmpParam.setLayout(new GridLayout (2, false));
								
								lbl1 = new Label(cmpParam, SWT.NULL);
								lbl1.setText("Lower Bound (inclusive):");
								lbl1.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(1000000);
								spn1.setLayoutData(SWTUtil.createGridData());

								lbl2 = new Label(cmpParam, SWT.NULL);
								lbl2.setText("Upper Bound (inclusive):");
								lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(1));
								
								spn2= new Spinner(cmpParam, SWT.BORDER);
								spn2.setMaximum(1000000);
								spn2.setLayoutData(SWTUtil.createGridData());
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
						cmpRoot.layout(true, true);
					}
			    });
			    
			    this.cmbDropdown.addModifyListener(new ModifyListener() {
				    public void modifyText(ModifyEvent arg0) {
		                validateCmbDropdown();
		            }

				});
			    
			    validateCmbDropdown();
	}
	
	private void validateCmbDropdown() {
        this.dropDownValid = this.cmbDropdown.getSelectionIndex() != -1;
    }

	
	public boolean isValid() {
		//ToDo: add validation
		return this.dropDownValid;
	}

	public void addModifyListener(ModifyListener listener) {
		//ToDo: add modifyListeners
		this.cmbDropdown.addModifyListener(listener);	
		this.MListener = listener;
	}

	public void addSelectionListener(SelectionAdapter adapter) {		
		//ToDo: add selectionListeners
	}
	
	// For testing purposes
	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (SWTUtil.createGridLayout(1));
		shell.setText("IntegerDistribution");

		IntegerDistributionGUI c1 = new IntegerDistributionGUI(shell);
		
		c1.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent arg0) {
				System.out.println("bla");
			}
    	});
		
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("ok");
		ok.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				System.out.println("ok");
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
