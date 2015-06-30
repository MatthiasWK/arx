import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class MaskerTool {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
		shell.setText("Masker Tool");
		
		final TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
		
		String[] maskerTypes = {	"GenerateRandomDateMasker",
									"GenerateRandomDecimalMasker",
									"GenerateRandomIntegerDecimalMasker",
									"GenerateRandomStringMakser",
									"ReplaceDictMasker",
									"ReplaceInstMasker",
									/*"ConstantShiftDateMasker",
									"ConstantShiftDecimalMasker",
									"MatchAndReplaceStringMasker",
									"RandomShiftDateMasker",
									"RandomShiftDecimalMasker",
									"ShuffleMasker",*/
									"SplitAndReplaceStringMasker"
								};

	    for (int loopIndex = 0; loopIndex < 7; loopIndex++) {
	    	TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
	      	tabItem.setText(maskerTypes[loopIndex]);
	      
	      	MaskerGUI gui = null;
	      	switch (loopIndex) {
	      		case 0:
	      			gui = new GenerateRandomDateMaskerGUI();
	      			break;
	      		case 1:
	      			gui = new GenerateRandomDecimalMaskerGUI();
	      			break;
	      		case 2:
	      			gui = new GenerateRandomIntegerDecimalMaskerGUI();
	      			break;
	      		case 3:
	      			gui = new GenerateRandomStringMaskerGUI();
	      			break;
	      		case 4:
	      			gui = new ReplaceDictMaskerGUI();
	      			break;
	      		case 5:
	      			gui = new ReplaceInstMaskerGUI();
	      			break;
	      		/*case 6:
	      			gui = new ConstantShiftDateMaskerGUI();
	      			break;
	      		case 6:
	      			gui = new ConstantShiftDecimalMaskerGUI();
	      			break;
	      		case 6:
	      			gui = new MatchAndReplaceStringMaskerGUI();
	      			break;
	      		case 6:
	      			gui = new RandomShiftDateMaskerGUI();
	      			break;
	      		case 6:
	      			gui = new RandomShiftDecimalMaskerGUI();
	      			break;
	      		case 6:
	      			gui = new ShuffleMaskerGUI();
	      			break;*/
	      		case 6:
	      			gui = new SplitAndReplaceStringMaskerGUI();
	      			break;
	      			
	      	}
	      	Composite c = gui.loadMasker();
	      	c.setParent(tabFolder);
	      	tabItem.setControl(c);
	    }

	    tabFolder.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
	          System.out.println(tabFolder.getSelection()[0].getText() + " selected");
	        }
	      });
	    
	    tabFolder.pack();
		shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	}
	
	public static Composite loadIntegerDistributions(int comboHorizontalSpan) {
		Shell shell = new Shell ();
		
		final Composite mainComposite = new Composite(shell, SWT.NONE);
		mainComposite.setLayout(new GridLayout (comboHorizontalSpan, false));
		
		final Combo dropdown = new Combo(mainComposite, SWT.DROP_DOWN);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = comboHorizontalSpan;
	    dropdown.setLayoutData(gridData);
	    dropdown.setText("Choose your distribution...");
		String[] distributions = {	"Abstract Integer Distribution",
									"Binomial Distribution",
									"Enumerated Integer Distribution",
									"Geometric Distribution",
									"Hypergeometric Distribution",
									"Pascal Distribution",
									"Poisson Distribution",
									"Uniform Integer Distribution",
									"Zipf Distribution"
								 };
		for (String s: distributions) {
			dropdown.add(s);
		}
		
		// composite for parameters of integer distribution
		final Composite c2 = new Composite(mainComposite, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = comboHorizontalSpan;
	    c2.setLayoutData(gridData);
	    
	    dropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = dropdown.getSelectionIndex();
				for (Control child: c2.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
					case 0: // Abstract Integer Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": RandomGenerator rng");
						break;
					case 1: // Binomial Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int trials, double p");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner trials = new Spinner(c2, SWT.BORDER);
						trials.setMaximum(100000);
						Label label2 = new Label(c2, SWT.NULL);
						label2.setText("trials");
						
						Spinner p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						p.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("probability of success");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 2: // Enumerated Integer Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int[] singletons, double[] probabilities");
						c2.setLayout(new GridLayout (2, false));
						
						label2 = new Label(c2, SWT.NULL);
						label2.setText("array of random\n variable values\n (int values)");
						final Text singletons = new Text(c2, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
						GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
						gridData.heightHint = 30;
						singletons.setLayoutData(gridData);
						
						label2 = new Label(c2, SWT.NULL);
						label2.setText("array of probabilities\n (double values)");
						final Text probabilities = new Text(c2, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
						gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
						gridData.heightHint = 30;
						probabilities.setLayoutData(gridData);
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 3: // Geometric Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p");
						c2.setLayout(new GridLayout (2, false));
						
						p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						p.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("probability of success");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 4: // Hypergeometric Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int populationSize, int numberOfSuccesses, int sampleSize");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner populationSize = new Spinner(c2, SWT.BORDER);
						populationSize.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("population size");
						
						Spinner numberOfSuccesses = new Spinner(c2, SWT.BORDER);
						numberOfSuccesses.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("number of successes");
						
						Spinner sampleSize = new Spinner(c2, SWT.BORDER);
						sampleSize.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("sample size");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 5: // Pascal Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int r, double p");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner r = new Spinner(c2, SWT.BORDER);
						r.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("number of successes");
						
						p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						p.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("probability of success");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 6: // Poisson Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p [, double epsilon] [, int maxIterations]");
						c2.setLayout(new GridLayout (3, false));
						
						p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						p.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("specified mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
					    final Button checkEpsilon = new Button(c2, SWT.CHECK);
					    
					    final Spinner epsilon = new Spinner(c2, SWT.BORDER);
					    epsilon.setDigits(2);
					    epsilon.setEnabled(false);
					    epsilon.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("convergence criterion");
						
						checkEpsilon.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkEpsilon.getSelection()) {
									epsilon.setEnabled(true);
								} else {
									epsilon.setEnabled(false);
								}
							}
						});
						
						final Button checkMaxIterations = new Button(c2, SWT.CHECK);
					    
					    final Spinner maxIterations = new Spinner(c2, SWT.BORDER);
					    maxIterations.setEnabled(false);
					    maxIterations.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum number of iterations");
						
						checkMaxIterations.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkMaxIterations.getSelection()) {
									maxIterations.setEnabled(true);
								} else {
									maxIterations.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 7: // Uniform Integer Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int lower, int upper");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner lower = new Spinner(c2, SWT.BORDER);
						lower.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("Lower bound (inclusive) of this distribution");
						
						Spinner upper = new Spinner(c2, SWT.BORDER);
						upper.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("Upper bound (inclusive) of this distribution");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 8: // Zipf Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int numberOfElements, double exponent");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner numberOfElements = new Spinner(c2, SWT.BORDER);
						numberOfElements.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("number of elements");
						
						Spinner exponent = new Spinner(c2, SWT.BORDER);
						exponent.setDigits(2);
						exponent.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("exponent");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
				}
				mainComposite.layout(true, true);
			}
		});

		mainComposite.layout(true, true);
		return mainComposite;
	}
	
	public static Composite loadRealDistributions(int comboHorizontalSpan) {
		Shell shell = new Shell ();
		
		final Composite mainComposite = new Composite(shell, SWT.NONE);
		mainComposite.setLayout(new GridLayout (comboHorizontalSpan, false));
		
		final Combo dropdown = new Combo(mainComposite, SWT.DROP_DOWN);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = comboHorizontalSpan;
	    dropdown.setLayoutData(gridData);
	    dropdown.setText("Choose your distribution...");
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
			dropdown.add(s);
		}
		
		// composite for parameters of real distribution
		final Composite c2 = new Composite(mainComposite, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = comboHorizontalSpan;
	    c2.setLayoutData(gridData);
	    
	    dropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = dropdown.getSelectionIndex();
				for (Control child: c2.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
					case 0: // Abstract Real Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng]");
						break;
					case 1: // Beta Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner alpha = new Spinner(c2, SWT.BORDER);
						alpha.setDigits(2);
						alpha.setMaximum(100000);
						Label label2 = new Label(c2, SWT.NULL);
						label2.setText("first shape parameter");
						GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						Spinner beta = new Spinner(c2, SWT.BORDER);
						beta.setDigits(2);
						beta.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("second shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy.setEnabled(false);
						inverseCumAccuracy.setDigits(2);
						inverseCumAccuracy.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy.getSelection()) {
									inverseCumAccuracy.setEnabled(true);
								} else {
									inverseCumAccuracy.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 2: // Cauchy Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double median, double scale [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner median = new Spinner(c2, SWT.BORDER);
						median.setDigits(2);
						median.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("median");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						Spinner scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy2 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy2 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy2.setEnabled(false);
						inverseCumAccuracy2.setDigits(2);
						inverseCumAccuracy2.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy2.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy2.getSelection()) {
									inverseCumAccuracy2.setEnabled(true);
								} else {
									inverseCumAccuracy2.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 3: // Chi Squared Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner doubleDegreesOfFreedom = new Spinner(c2, SWT.BORDER);
						doubleDegreesOfFreedom.setDigits(2);
						doubleDegreesOfFreedom.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy3 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy3 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy3.setEnabled(false);
						inverseCumAccuracy3.setDigits(2);
						inverseCumAccuracy3.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy3.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy3.getSelection()) {
									inverseCumAccuracy3.setEnabled(true);
								} else {
									inverseCumAccuracy3.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 4: // Empirical Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int binCount");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner binCount = new Spinner(c2, SWT.BORDER);
						binCount.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("bin count");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 5: // Enumerated Real Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double[] singletons, double[] probabilites");
						c2.setLayout(new GridLayout (2, false));
						
						label2 = new Label(c2, SWT.NULL);
						label2.setText("array of random\n variable values\n (double values)");
						final Text singletons = new Text(c2, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
						gridData = new GridData(SWT.FILL, 0, false, false);
						gridData.heightHint = 50;
						singletons.setLayoutData(gridData);
						
						label2 = new Label(c2, SWT.NULL);
						label2.setText("array of probabilities\n (double values)");
						final Text probabilities = new Text(c2, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
						gridData = new GridData(SWT.FILL, 0, false, false);
						gridData.heightHint = 50;
						probabilities.setLayoutData(gridData);
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 6: // Exponential Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner mean = new Spinner(c2, SWT.BORDER);
						mean.setDigits(2);
						mean.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy4 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy4 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy4.setEnabled(false);
						inverseCumAccuracy4.setDigits(2);
						inverseCumAccuracy4.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy4.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy4.getSelection()) {
									inverseCumAccuracy4.setEnabled(true);
								} else {
									inverseCumAccuracy4.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 7: // F Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner numeratorDegreesOfFreedom = new Spinner(c2, SWT.BORDER);
						numeratorDegreesOfFreedom.setDigits(2);
						numeratorDegreesOfFreedom.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("numerator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						Spinner denominatorDegreesOfFreedom = new Spinner(c2, SWT.BORDER);
						denominatorDegreesOfFreedom.setDigits(2);
						denominatorDegreesOfFreedom.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("denominator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy5 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy5 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy5.setEnabled(false);
						inverseCumAccuracy5.setDigits(2);
						inverseCumAccuracy5.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy5.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy5.getSelection()) {
									inverseCumAccuracy5.setEnabled(true);
								} else {
									inverseCumAccuracy5.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 8: // Gamma Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double shape, double scale [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy6 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy6 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy6.setEnabled(false);
						inverseCumAccuracy6.setDigits(2);
						inverseCumAccuracy6.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy6.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy6.getSelection()) {
									inverseCumAccuracy6.setEnabled(true);
								} else {
									inverseCumAccuracy6.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 9: // Levy Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": RandomGenerator rng, double mu, double c ");
						// mu: location, c: scale parameter
						c2.setLayout(new GridLayout (2, false));
						
						Spinner mu = new Spinner(c2, SWT.BORDER);
						mu.setDigits(2);
						mu.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("location");
						
						Spinner c = new Spinner(c2, SWT.BORDER);
						c.setDigits(2);
						c.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 10: // Log Normal Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy7 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy7 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy7.setEnabled(false);
						inverseCumAccuracy7.setDigits(2);
						inverseCumAccuracy7.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy7.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy7.getSelection()) {
									inverseCumAccuracy7.setEnabled(true);
								} else {
									inverseCumAccuracy7.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 11: // Normal Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean, double sd [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						mean = new Spinner(c2, SWT.BORDER);
						mean.setDigits(2);
						mean.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						Spinner sd = new Spinner(c2, SWT.BORDER);
						sd.setDigits(2);
						sd.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("sd");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy91 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy91 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy91.setEnabled(false);
						inverseCumAccuracy91.setDigits(2);
						inverseCumAccuracy91.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy91.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy91.getSelection()) {
									inverseCumAccuracy91.setEnabled(true);
								} else {
									inverseCumAccuracy91.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 12: // Pareto Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy9 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy9 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy9.setEnabled(false);
						inverseCumAccuracy9.setDigits(2);
						inverseCumAccuracy9.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy9.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy9.getSelection()) {
									inverseCumAccuracy9.setEnabled(true);
								} else {
									inverseCumAccuracy9.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 13: // T Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy10 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy10 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy10.setEnabled(false);
						inverseCumAccuracy10.setDigits(2);
						inverseCumAccuracy10.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy10.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy10.getSelection()) {
									inverseCumAccuracy10.setEnabled(true);
								} else {
									inverseCumAccuracy10.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 14: // Triangular Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double a, double c, double b");
						// a: lower limit of this distribution (inclusive), b: upper limit of this distribution (inclusive), c: mode of this distribution
						c2.setLayout(new GridLayout (2, false));
						
						Spinner a = new Spinner(c2, SWT.BORDER);
						a.setDigits(2);
						a.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("lower limit of this distribution (inclusive)");
					    
					    Spinner b = new Spinner(c2, SWT.BORDER);
						b.setDigits(2);
						b.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("upper limit of this distribution (inclusive)");
					    
					    c = new Spinner(c2, SWT.BORDER);
						c.setDigits(2);
						c.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("mode of this distribution");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 15: // Uniform Real Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double lower, double upper [, double inverseCumAccuracy]");
						// lower: lower bound of this distribution (inclusive), upper: upper bound of this distribution (exclusive)
						c2.setLayout(new GridLayout (3, false));
						
						Spinner lower = new Spinner(c2, SWT.BORDER);
						lower.setDigits(2);
						lower.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("lower bound of this distribution (inclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						Spinner upper = new Spinner(c2, SWT.BORDER);
						upper.setDigits(2);
						upper.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("upper bound of this distribution (exclusive)");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy11 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy11 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy11.setEnabled(false);
						inverseCumAccuracy11.setDigits(2);
						inverseCumAccuracy11.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy11.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy11.getSelection()) {
									inverseCumAccuracy11.setEnabled(true);
								} else {
									inverseCumAccuracy11.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 16: // Weibull Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						// alpha: shape parameter, beta: scale parameter
						c2.setLayout(new GridLayout (3, false));

						shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						shape.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						scale.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy12 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy12 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy12.setEnabled(false);
						inverseCumAccuracy12.setDigits(2);
						inverseCumAccuracy12.setMaximum(100000);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("maximum absolute error\n in inverse cumulative\n probability estimates");
						
						checkInverseCumAccuracy12.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent event) {
								if (checkInverseCumAccuracy12.getSelection()) {
									inverseCumAccuracy12.setEnabled(true);
								} else {
									inverseCumAccuracy12.setEnabled(false);
								}
							}
						});
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						mainComposite.layout();
						mainComposite.setSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
				}
				mainComposite.layout(true, true);
			}
		});
	    
	    mainComposite.layout(true, true);
		return mainComposite;
	}
}
