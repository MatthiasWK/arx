import org.eclipse.swt.SWT;
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


public class RealDistribution {
	static int comboHorizontalSpan = 8;
	
	public Composite cmpRoot;
	
	private Combo cmbDropdown;
	private Composite cmpParam;
	
	private Label lbl;
	
	private Spinner spn1;
	private Spinner spn2;
	private Spinner spn3;
	
	private Text txtSingletons;
	private Text txtProbabilities;
	
	private Button btn1;

	public RealDistribution(Composite root) {
		
		cmpRoot = new Composite(root, SWT.NONE);
		cmpRoot.setLayout(new GridLayout (comboHorizontalSpan, false));
		
		cmbDropdown = new Combo(cmpRoot, SWT.DROP_DOWN);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = comboHorizontalSpan;
	    cmbDropdown.setLayoutData(gridData);
	    cmbDropdown.setText("Choose your distribution...");
	    String[] distributions = {	"Abstract Real Distribution",
									"Beta Distribution",
									"Cauchy Distribution",
									"Chi Squared Distribution",
									"Empirical Distribution",
									"Enumerated Real Distribution",
									"Exponential Distribution",
									"F Distribution",
									"Gamma Distribution",
									"Levy Distribution",
									"Log Normal Distribution",
									"Normal Distribution",
									"Pareto Distribution",
									"T Distribution",
									"Triangular Distribution",
									"Uniform Real Distribution",
									"Weibull Distribution"
								 };
		for (String s: distributions) {
			cmbDropdown.add(s);
		}
		
		// composite for parameters of real distribution
		cmpParam = new Composite(cmpRoot, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = comboHorizontalSpan;
	    cmpParam.setLayoutData(gridData);
	    
	    cmbDropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = cmbDropdown.getSelectionIndex();
				for (Control child: cmpParam.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
					case 0: // Abstract Real Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng]");
						break;
					case 1: // Beta Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 2: // Cauchy Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 3: // Chi Squared Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 4: // Empirical Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int binCount");
						cmpParam.setLayout(new GridLayout (2, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("bin count");
						
						cmpParam.layout();
						cmpParam.setSize(cmpParam.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 5: // Enumerated Real Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 6: // Exponential Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("mean");
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 7: // F Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 8: // Gamma Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 9: // Levy Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 10: // Log Normal Distribution
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 11: // Normal Distribution
						System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean, double sd [, double inverseCumAccuracy]");
						cmpParam.setLayout(new GridLayout (3, false));
						
						spn1 = new Spinner(cmpParam, SWT.BORDER);
						spn1.setDigits(2);
						spn1.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						spn2 = new Spinner(cmpParam, SWT.BORDER);
						spn2.setDigits(2);
						spn2.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("sd");
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
				}
				cmpRoot.layout(true, true);
			}
		});
	}
	
	
}
