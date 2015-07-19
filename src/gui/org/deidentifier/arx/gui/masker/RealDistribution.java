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
						
						Spinner numeratorDegreesOfFreedom = new Spinner(cmpParam, SWT.BORDER);
						numeratorDegreesOfFreedom.setDigits(2);
						numeratorDegreesOfFreedom.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("numerator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						Spinner denominatorDegreesOfFreedom = new Spinner(cmpParam, SWT.BORDER);
						denominatorDegreesOfFreedom.setDigits(2);
						denominatorDegreesOfFreedom.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("denominator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy5 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy5 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy5.setEnabled(false);
						inverseCumAccuracy5.setDigits(2);
						inverseCumAccuracy5.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy5.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy5.getSelection()) {
									inverseCumAccuracy5.setEnabled(true);
								} else {
									inverseCumAccuracy5.setEnabled(false);
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
						
						Spinner shape = new Spinner(cmpParam, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						Spinner scale = new Spinner(cmpParam, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy6 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy6 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy6.setEnabled(false);
						inverseCumAccuracy6.setDigits(2);
						inverseCumAccuracy6.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy6.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy6.getSelection()) {
									inverseCumAccuracy6.setEnabled(true);
								} else {
									inverseCumAccuracy6.setEnabled(false);
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
						
						Spinner mu = new Spinner(cmpParam, SWT.BORDER);
						mu.setDigits(2);
						mu.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("location");
						
						Spinner c = new Spinner(cmpParam, SWT.BORDER);
						c.setDigits(2);
						c.setMaximum(100000);
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
						
						scale = new Spinner(cmpParam, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						shape = new Spinner(cmpParam, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy7 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy7 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy7.setEnabled(false);
						inverseCumAccuracy7.setDigits(2);
						inverseCumAccuracy7.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy7.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy7.getSelection()) {
									inverseCumAccuracy7.setEnabled(true);
								} else {
									inverseCumAccuracy7.setEnabled(false);
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
						
						Spinner mean = new Spinner(cmpParam, SWT.BORDER);
						mean.setDigits(2);
						mean.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						Spinner sd = new Spinner(cmpParam, SWT.BORDER);
						sd.setDigits(2);
						sd.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("sd");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy91 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy91 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy91.setEnabled(false);
						inverseCumAccuracy91.setDigits(2);
						inverseCumAccuracy91.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy91.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy91.getSelection()) {
									inverseCumAccuracy91.setEnabled(true);
								} else {
									inverseCumAccuracy91.setEnabled(false);
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
						
						scale = new Spinner(cmpParam, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						shape = new Spinner(cmpParam, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy9 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy9 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy9.setEnabled(false);
						inverseCumAccuracy9.setDigits(2);
						inverseCumAccuracy9.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy9.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy9.getSelection()) {
									inverseCumAccuracy9.setEnabled(true);
								} else {
									inverseCumAccuracy9.setEnabled(false);
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
						
						scale = new Spinner(cmpParam, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy10 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy10 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy10.setEnabled(false);
						inverseCumAccuracy10.setDigits(2);
						inverseCumAccuracy10.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy10.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy10.getSelection()) {
									inverseCumAccuracy10.setEnabled(true);
								} else {
									inverseCumAccuracy10.setEnabled(false);
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
						
						Spinner a = new Spinner(cmpParam, SWT.BORDER);
						a.setDigits(2);
						a.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("lower limit of this distribution (inclusive)");
					    
					    Spinner b = new Spinner(cmpParam, SWT.BORDER);
						b.setDigits(2);
						b.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("upper limit of this distribution (inclusive)");
					    
					    c = new Spinner(cmpParam, SWT.BORDER);
						c.setDigits(2);
						c.setMaximum(100000);
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
						
						Spinner lower = new Spinner(cmpParam, SWT.BORDER);
						lower.setDigits(2);
						lower.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("lower bound of this distribution (inclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						Spinner upper = new Spinner(cmpParam, SWT.BORDER);
						upper.setDigits(2);
						upper.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("upper bound of this distribution (exclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy11 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy11 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy11.setEnabled(false);
						inverseCumAccuracy11.setDigits(2);
						inverseCumAccuracy11.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy11.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy11.getSelection()) {
									inverseCumAccuracy11.setEnabled(true);
								} else {
									inverseCumAccuracy11.setEnabled(false);
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

						shape = new Spinner(cmpParam, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
					    
						scale = new Spinner(cmpParam, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    lbl.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy12 = new Button(cmpParam, SWT.CHECK);
						final Spinner inverseCumAccuracy12 = new Spinner(cmpParam, SWT.BORDER);
						inverseCumAccuracy12.setEnabled(false);
						inverseCumAccuracy12.setDigits(2);
						inverseCumAccuracy12.setMaximum(100000);
						lbl = new Label(cmpParam, SWT.NULL);
						lbl.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy12.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy12.getSelection()) {
									inverseCumAccuracy12.setEnabled(true);
								} else {
									inverseCumAccuracy12.setEnabled(false);
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
