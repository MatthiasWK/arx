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
	
//	private Text txtSingletons;
//	private Text txtProbabilities;
	
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
									"Chi Squared Distribution"/*,
									"Abstract Real Distribution",
									"Beta Distribution",
									"Cauchy Distribution",
									"Empirical Distribution",
									"Enumerated Real Distribution",
									"F Distribution",
									"Gamma Distribution",
									"Levy Distribution",
									"Log Normal Distribution",
									"Pareto Distribution",
									"T Distribution",
									"Triangular Distribution",
									"Uniform Real Distribution",
									"Weibull Distribution"*/
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
					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean [, double inverseCumAccuracy]");
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
					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean, double sd [, double inverseCumAccuracy]");
					
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
					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
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
					/*
					case 3: // Abstract Real Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng]");
						break;
					case 4: // Beta Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						cmpParam.setLayout(SWTUtil.createGridLayout(3));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("first shape parameter");
						GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("second shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3= new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));						
						break;
					case 5: // Cauchy Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double median, double scale [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("median");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
					    txt2 = new Spinner(cmpParam, SWT.BORDER);
					    txt2.setDigits(2);
					    txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					
					case 6: // Empirical Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int binCount");
						cmpParam.setLayout(new GridLayout (2, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("bin count");
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 7: // Enumerated Real Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double[] singletons, double[] probabilites");
						cmpParam.setLayout(new GridLayout (2, false));
						
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("array of random\n variable values\n (double values)");
						txtSingletons = new Text(cmpParam, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
						gridData = new GridData(SWT.FILL, 0, false, false);
						gridData.heightHint = 50;
						txtSingletons.setLayoutData(gridData);
						
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("array of probabilities\n (double values)");
						txtProbabilities = new Text(cmpParam, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
						gridData = new GridData(SWT.FILL, 0, false, false);
						gridData.heightHint = 50;
						txtProbabilities.setLayoutData(gridData);
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					
					case 8: // F Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("numerator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("denominator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 9: // Gamma Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double shape, double scale [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 10: // Levy Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": RandomGenerator rng, double mu, double c ");
						// mu: location, c: scale parameter
						cmpParam.setLayout(new GridLayout (2, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("location");
						
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 11: // Log Normal Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					
					case 12: // Pareto Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 13: // T Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setEnabled(false);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt2.setEnabled(true);
								} else {
									txt2.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 14: // Triangular Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double a, double c, double b");
						// a: lower limit of this distribution (inclusive), b: upper limit of this distribution (inclusive), c: mode of this distribution
						cmpParam.setLayout(new GridLayout (2, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("lower limit of this distribution (inclusive)");
					    
					    txt2 = new Spinner(cmpParam, SWT.BORDER);
					    txt2.setDigits(2);
					    txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("upper limit of this distribution (inclusive)");
					    
					    txt3 = new Spinner(cmpParam, SWT.BORDER);
					    txt3.setDigits(2);
					    txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("mode of this distribution");
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 15: // Uniform Real Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double lower, double upper [, double inverseCumAccuracy]");
						// lower: lower bound of this distribution (inclusive), upper: upper bound of this distribution (exclusive)
						cmpParam.setLayout(new GridLayout (3, false));
						
						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("lower bound of this distribution (inclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("upper bound of this distribution (exclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 16: // Weibull Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						// alpha: shape parameter, beta: scale parameter
						cmpParam.setLayout(new GridLayout (3, false));

						txt1 = new Spinner(cmpParam, SWT.BORDER);
						txt1.setDigits(2);
						txt1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						txt2 = new Spinner(cmpParam, SWT.BORDER);
						txt2.setDigits(2);
						txt2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						txt3 = new Spinner(cmpParam, SWT.BORDER);
						txt3.setEnabled(false);
						txt3.setDigits(2);
						txt3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									txt3.setEnabled(true);
								} else {
									txt3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
						*/
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
				this.txt1Valid = true;
				this.txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}
			else{
				this.txt1Valid = false;
				this.txt1.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
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
	
	// For testing purposes
		public static void main(String[] args) {
			Display display = new Display ();
			Shell shell = new Shell (display);
			shell.setLayout (SWTUtil.createGridLayout(1));
			shell.setText("RealDistribution");

			final RealDistributionGUI c1 = new RealDistributionGUI(shell);
			
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
