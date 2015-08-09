import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.Random;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;


public class RealDistributionGUI implements ConfigurationComponent {
	
	private Composite cmpRoot;
	
	private Combo cmbDropdown;
	private Composite cmpParam;
	private Composite cmpParamSub1;
	private Composite cmpParamSub2;
	
	private Label lbl1;
	private Label lbl2;
	private Label lbl3;
	
	private Spinner spn1;
	private Spinner spn2;
	private Spinner spn3;
	
//	private Boolean spn1Valid = false;
//	private Boolean spn2Valid = false;
//	private Boolean spn3Valid = false;
	
//	private Text txtSingletons;
//	private Text txtProbabilities;
	
	private Button btn1;

	public RealDistributionGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
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
		this.cmpParam = new Composite(cmpRoot, SWT.BORDER);
	    this.cmpParam.setLayoutData(SWTUtil.createFillGridData());
	    
		final GridData center = new GridData();
	    center.horizontalAlignment = SWT.LEFT;
		center.verticalAlignment = SWT.CENTER;
		center.grabExcessHorizontalSpace = false;
		center.grabExcessVerticalSpace = false;
	    
	    this.cmbDropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = cmbDropdown.getSelectionIndex();
				for (Control child: cmpParam.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
				case 0: // Exponential Distribution
					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean [, double inverseCumAccuracy]");
					cmpParam.setLayout(SWTUtil.createGridLayout(1));
					
					cmpParamSub1 = new Composite(cmpParam, SWT.NONE);
					cmpParamSub1.setLayoutData(SWTUtil.createFillGridData());
					cmpParamSub1.setLayout(SWTUtil.createGridLayout(1));

					lbl1 = new Label(cmpParamSub1, SWT.NONE);
					lbl1.setText("Mean:");
				    lbl1.setLayoutData(SWTUtil.createNoFillGridData());
				    
					spn1 = new Spinner(cmpParamSub1, SWT.BORDER);
					spn1.setDigits(2);
					spn1.setMaximum(100000);
					spn1.setMinimum(1);
					spn1.setLayoutData(SWTUtil.createNoFillGridData());
					
				    cmpParamSub2 = new Composite(cmpParam, SWT.NONE);
					cmpParamSub2.setLayoutData(SWTUtil.createFillGridData());
				    cmpParamSub2.setLayout(SWTUtil.createGridLayout(2));
					
					lbl2 = new Label(cmpParamSub2, SWT.NONE);
					lbl2.setText("Maximum absolute error\nin inverse cumulative\nprobability estimates:");
					lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					btn1 = new Button(cmpParamSub2, SWT.CHECK);
					btn1.setLayoutData(SWTUtil.createGridData());
					
					spn2 = new Spinner(cmpParamSub2, SWT.BORDER);
					spn2.setEnabled(false);
					spn2.setDigits(2);
					spn2.setMaximum(100000);
					spn2.setMinimum(-100000);
					spn2.setLayoutData(SWTUtil.createNoFillGridData());
					
					btn1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							if (btn1.getSelection()) {
								spn2.setEnabled(true);
							} else {
								spn2.setEnabled(false);
							}
						}
					});
					
					break;
				case 1: // Normal Distribution
					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean, double sd [, double inverseCumAccuracy]");
					
					cmpParam.setLayout(SWTUtil.createGridLayout(1));
					
					cmpParamSub1 = new Composite(cmpParam, SWT.NONE);
					cmpParamSub1.setLayoutData(SWTUtil.createFillGridData());
					cmpParamSub1.setLayout(SWTUtil.createGridLayout(1));

					lbl1 = new Label(cmpParamSub1, SWT.NONE);
					lbl1.setText("Mean:");
				    lbl1.setLayoutData(SWTUtil.createGridData());
				    
					spn1 = new Spinner(cmpParamSub1, SWT.BORDER);
					spn1.setDigits(2);
					spn1.setMaximum(100000);
					spn1.setMinimum(-100000);
					spn1.setLayoutData(SWTUtil.createNoFillGridData());
					
					lbl2 = new Label(cmpParamSub1, SWT.NONE);
					lbl2.setText("Standard deviation:");
				    lbl2.setLayoutData(SWTUtil.createGridData());
				    
					spn2 = new Spinner(cmpParamSub1, SWT.BORDER);
					spn2.setDigits(2);
					spn2.setMaximum(100000);
					spn2.setMinimum(1);
					spn2.setLayoutData(SWTUtil.createNoFillGridData());
					
				    cmpParamSub2 = new Composite(cmpParam, SWT.NONE);
					cmpParamSub2.setLayoutData(SWTUtil.createFillGridData());
				    cmpParamSub2.setLayout(SWTUtil.createGridLayout(2));
					
					lbl3 = new Label(cmpParamSub2, SWT.NONE);
					lbl3.setText("Maximum absolute error\nin inverse cumulative\nprobability estimates:");
					lbl3.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					btn1 = new Button(cmpParamSub2, SWT.CHECK);
					btn1.setLayoutData(SWTUtil.createGridData());
					
					spn3 = new Spinner(cmpParamSub2, SWT.BORDER);
					spn3.setEnabled(false);
					spn3.setDigits(2);
					spn3.setMaximum(100000);
					spn3.setMinimum(-100000);
					spn3.setLayoutData(SWTUtil.createNoFillGridData());
					
					btn1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							if (btn1.getSelection()) {
								spn3.setEnabled(true);
							} else {
								spn3.setEnabled(false);
							}
						}
					});
					
					break;
				case 2: // Chi Squared Distribution
					System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
					cmpParam.setLayout(SWTUtil.createGridLayout(1));
					
					cmpParamSub1 = new Composite(cmpParam, SWT.NONE);
					cmpParamSub1.setLayoutData(SWTUtil.createFillGridData());
					cmpParamSub1.setLayout(SWTUtil.createGridLayout(1));

					lbl1 = new Label(cmpParamSub1, SWT.NONE);
					lbl1.setText("Degrees of freedom:");
				    lbl1.setLayoutData(SWTUtil.createNoFillGridData());
				    
					spn1 = new Spinner(cmpParamSub1, SWT.BORDER);
					spn1.setDigits(2);
					spn1.setMaximum(100000);
					spn1.setMinimum(-100000);
					spn1.setLayoutData(SWTUtil.createNoFillGridData());
					
				    cmpParamSub2 = new Composite(cmpParam, SWT.NONE);
					cmpParamSub2.setLayoutData(SWTUtil.createFillGridData());
				    cmpParamSub2.setLayout(SWTUtil.createGridLayout(2));
					
					lbl2 = new Label(cmpParamSub2, SWT.NONE);
					lbl2.setText("Maximum absolute error\nin inverse cumulative\nprobability estimates:");
					lbl2.setLayoutData(SWTUtil.createSpanColumnsGridData(2));
				    
					btn1 = new Button(cmpParamSub2, SWT.CHECK);
					btn1.setLayoutData(SWTUtil.createGridData());
					
					spn2 = new Spinner(cmpParamSub2, SWT.BORDER);
					spn2.setEnabled(false);
					spn2.setDigits(2);
					spn2.setMaximum(100000);
					spn2.setMinimum(-100000);
					spn2.setLayoutData(SWTUtil.createNoFillGridData());
					
					btn1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							if (btn1.getSelection()) {
								spn2.setEnabled(true);
							} else {
								spn2.setEnabled(false);
							}
						}
					});
					
					break;
					/*
					case 3: // Abstract Real Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng]");
						break;
					case 4: // Beta Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						cmpParam.setLayout(SWTUtil.createGridLayout(3));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("first shape parameter");
						GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("second shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3= new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));						
						break;
					case 5: // Cauchy Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double median, double scale [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("median");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
					    spn2 = new Spinner(cmpParam, SWT.BORDER);
					    spn2.setDigits(2);
					    spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					
					case 6: // Empirical Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int binCount");
						cmpParam.setLayout(new GridLayout (2, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setMaximum(100000);
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
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("numerator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("denominator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 9: // Gamma Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double shape, double scale [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
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
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("location");
						
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 11: // Log Normal Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					
					case 12: // Pareto Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 13: // T Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setEnabled(false);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn2.setEnabled(true);
								} else {
									spn2.setEnabled(false);
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
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("lower limit of this distribution (inclusive)");
					    
					    spn2 = new Spinner(cmpParam, SWT.BORDER);
					    spn2.setDigits(2);
					    spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("upper limit of this distribution (inclusive)");
					    
					    spn3 = new Spinner(cmpParam, SWT.BORDER);
					    spn3.setDigits(2);
					    spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("mode of this distribution");
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
					case 15: // Uniform Real Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double lower, double upper [, double inverseCumAccuracy]");
						// lower: lower bound of this distribution (inclusive), upper: upper bound of this distribution (exclusive)
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("lower bound of this distribution (inclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("upper bound of this distribution (exclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
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

						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						btn1 = new Button(cmpParam, SWT.CHECK);
						spn3 = new Spinner(cmpParam, SWT.BORDER);
						spn3.setEnabled(false);
						spn3.setDigits(2);
						spn3.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						btn1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (btn1.getSelection()) {
									spn3.setEnabled(true);
								} else {
									spn3.setEnabled(false);
								}
							}
						});
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
						
						break;
						*/
				}
				cmpRoot.layout(true, true);
			}
		});
	}
	
	private void validateSpn1(){
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Exponential
			
			break;
		case 1: // Normal
			break;
		case 2: // Chi Squared
			break;
	}
	}
	
	private void validateSpn2(){
		
	}

	private void validateSpn3(){
	
	}
	
	public boolean isValid() {		
		return true;
	}

	public void addModifyListener(ModifyListener listener) {
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Exponential
			this.spn1.addModifyListener(listener);
			this.spn2.addModifyListener(listener);
			break;
		case 1: // Normal
			this.spn1.addModifyListener(listener);
			this.spn2.addModifyListener(listener);
			this.spn3.addModifyListener(listener);
			break;
		case 2: // Chi Squared
			this.spn1.addModifyListener(listener);
			this.spn2.addModifyListener(listener);
			break;
	}
		
	}

	public void addSelectionListener(SelectionAdapter adapter) {
		switch(this.cmbDropdown.getSelectionIndex()){
		case 0: // Exponential
			this.btn1.addSelectionListener(adapter);
			break;
		case 1: // Normal
			this.btn1.addSelectionListener(adapter);
			break;
		case 2: // Chi Squared
			this.btn1.addSelectionListener(adapter);
			break;
		}
		this.cmbDropdown.addSelectionListener(adapter);
	}
	
	public RealDistribution getDistribution(){
		RealDistribution dist = null;
		switch(this.cmbDropdown.getSelectionIndex()){
			case 0: // Exponential
				if(!btn1.getSelection())
					dist = new ExponentialDistribution(spn1.getSelection()*.01d);
				else
					dist = new ExponentialDistribution(spn1.getSelection()*.01d, spn2.getSelection()*.01d);
				break;
			case 1: // Normal
				if(!btn1.getSelection())
					dist = new NormalDistribution(spn1.getSelection()*.01d, spn2.getSelection()*.01d);
				else
					dist = new NormalDistribution(spn1.getSelection()*.01d, spn2.getSelection()*.01d, spn3.getSelection()*.01d);
				break;
			case 2: // Chi Squared
				if(!btn1.getSelection())
					dist = new ExponentialDistribution(spn1.getSelection()*.01d);
				else
					dist = new ExponentialDistribution(spn1.getSelection()*.01d, spn2.getSelection()*.01d);
				break;
		}
		return dist;
	}
}
