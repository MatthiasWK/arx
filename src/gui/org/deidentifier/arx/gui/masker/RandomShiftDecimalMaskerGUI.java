

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class RandomShiftDecimalMaskerGUI implements MaskerGUI{
	public static void main (String [] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		
		final Composite mainComposite = new Composite(sc, SWT.NONE);
		mainComposite.setLayout(new GridLayout());

		final Composite c1 = new Composite(mainComposite, SWT.BORDER);	
		c1.setLayout (new GridLayout (3, false));
		
		// Real distribution
		Label label = new Label(c1, SWT.NULL);
		label.setText("Real distribution: ");
		
		final Combo dropdown = new Combo(c1, SWT.DROP_DOWN);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 2;
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
		
		label = new Label(c1, SWT.NULL);
		
		final Composite c2 = new Composite(c1, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = 2;
	    c2.setLayoutData(gridData);
		
		// shift constant
		label = new Label(c1, SWT.NULL);
		label.setText("Shift constant: ");
		
		final Button checkShiftConstant = new Button(c1, SWT.CHECK);
		
		final Spinner shiftConstant = new Spinner(c1, SWT.BORDER);
		shiftConstant.setEnabled(false);
		shiftConstant.setDigits(2);
		
		checkShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (checkShiftConstant.getSelection()) {
					shiftConstant.setEnabled(true);
				} else {
					shiftConstant.setEnabled(false);
				}
			}
		});
		
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
						Label label2 = new Label(c2, SWT.NULL);
						label2.setText("first shape parameter");
						GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						Spinner beta = new Spinner(c2, SWT.BORDER);
						beta.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("second shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy.setEnabled(false);
						inverseCumAccuracy.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 2: // Cauchy Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double median, double scale [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner median = new Spinner(c2, SWT.BORDER);
						median.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("median");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						Spinner scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy2 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy2 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy2.setEnabled(false);
						inverseCumAccuracy2.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 3: // Chi Squared Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner doubleDegreesOfFreedom = new Spinner(c2, SWT.BORDER);
						doubleDegreesOfFreedom.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy3 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy3 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy3.setEnabled(false);
						inverseCumAccuracy3.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 4: // Empirical Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int binCount");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner binCount = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("bin count");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 6: // Exponential Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner mean = new Spinner(c2, SWT.BORDER);
						mean.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy4 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy4 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy4.setEnabled(false);
						inverseCumAccuracy4.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 7: // F Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double numeratorDegreesOfFreedom, double denominatorDegreesOfFreedom [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner numeratorDegreesOfFreedom = new Spinner(c2, SWT.BORDER);
						numeratorDegreesOfFreedom.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("numerator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						Spinner denominatorDegreesOfFreedom = new Spinner(c2, SWT.BORDER);
						denominatorDegreesOfFreedom.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("denominator degrees of freedom");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy5 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy5 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy5.setEnabled(false);
						inverseCumAccuracy5.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 8: // Gamma Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double shape, double scale [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						Spinner shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy6 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy6 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy6.setEnabled(false);
						inverseCumAccuracy6.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 9: // Levy Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": RandomGenerator rng, double mu, double c ");
						// mu: location, c: scale parameter
						c2.setLayout(new GridLayout (2, false));
						
						Spinner mu = new Spinner(c2, SWT.BORDER);
						mu.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("location");
						
						Spinner c = new Spinner(c2, SWT.BORDER);
						c.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 10: // Log Normal Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy7 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy7 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy7.setEnabled(false);
						inverseCumAccuracy7.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 11: // Normal Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double mean, double sd [, double inverseCumAccuracy]");
						break;
					case 12: // Pareto Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double scale, double shape [, double inverseCumAccuracy]");
						c2.setLayout(new GridLayout (3, false));
						
						scale = new Spinner(c2, SWT.BORDER);
						scale.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("scale parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
						shape = new Spinner(c2, SWT.BORDER);
						shape.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("shape parameter");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
						
						final Button checkInverseCumAccuracy9 = new Button(c2, SWT.CHECK);
						final Spinner inverseCumAccuracy9 = new Spinner(c2, SWT.BORDER);
						inverseCumAccuracy9.setEnabled(false);
						inverseCumAccuracy9.setDigits(2);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 13: // T Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double degreesOfFreedom [, double inverseCumAccuracy]");
						break;
					case 14: // Triangular Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double a, double c, double b");
						// a: lower limit of this distribution (inclusive), b: upper limit of this distribution (inclusive), c: mode of this distribution
						break;
					case 15: // Uniform Real Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double lower, double upper [, double inverseCumAccuracy]");
						// lower: lower bound of this distribution (inclusive), upper: upper bound of this distribution (exclusive)
						break;
					case 16: // Weibull Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double alpha, double beta [, double inverseCumAccuracy]");
						// alpha: shape parameter, beta: scale parameter
						break;
				}
				mainComposite.layout(true, true);
			}
		});
		
		final Composite c3 = new Composite(mainComposite, SWT.NONE);
		c3.setLayout(new GridLayout (1, false));
		Button next = new Button(c3, SWT.PUSH);
		next.setText("next >");
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("");
			}
		});
		
		mainComposite.layout(true, true);
		
		sc.setContent(mainComposite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		shell.pack ();
		shell.open ();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}

	@Override
	public Composite loadMasker() {
		Shell shell = new Shell ();
		
		final Composite mainComposite = new Composite(shell, SWT.BORDER);	
		mainComposite.setLayout (new GridLayout (9, false));
		
      	// Real distribution
 		Label label = new Label(mainComposite, SWT.NULL);
 		label.setText("Real distribution: ");
 		Composite distributions = MaskerTool.loadRealDistributions(8);
 		distributions.setParent(mainComposite);
 		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = 8;
		distributions.setLayoutData(gridData);
		distributions.layout(true);
		
		// shift constant
		label = new Label(mainComposite, SWT.NULL);
		label.setText("Shift constant: ");
		
		final Button checkShiftConstant = new Button(mainComposite, SWT.CHECK);
		
		final Spinner shiftConstant = new Spinner(mainComposite, SWT.BORDER);
		shiftConstant.setEnabled(false);
		shiftConstant.setDigits(2);
		
		checkShiftConstant.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (checkShiftConstant.getSelection()) {
					shiftConstant.setEnabled(true);
				} else {
					shiftConstant.setEnabled(false);
				}
			}
		});
		return mainComposite;
	}
}

