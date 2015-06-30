

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class RandomShiftDateMaskerGUI implements MaskerGUI{
	public static void main (String [] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		
		final Composite mainComposite = new Composite(sc, SWT.NONE);
		mainComposite.setLayout(new GridLayout());

		final Composite c1 = new Composite(mainComposite, SWT.BORDER);	
		c1.setLayout (new GridLayout (9, false));
		
		// Integer distribution
		Label label = new Label(c1, SWT.NULL);
		label.setText("Integer distribution: ");
		
		final Combo dropdown = new Combo(c1, SWT.DROP_DOWN);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 8;
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
		
		label = new Label(c1, SWT.NULL);
		
		// composite for parameters of integer distribution
		final Composite c2 = new Composite(c1, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = 8;
	    c2.setLayoutData(gridData);
		
		// basePeriod
		label = new Label(c1, SWT.NULL);
		label.setText("Base period: ");
		
		final Button checkBasePeriod = new Button(c1, SWT.CHECK);
		
		final Spinner days = new Spinner(c1, SWT.WRAP);
		days.setEnabled(false);
		label = new Label(c1, SWT.NULL);
		label.setText("days");
		
		final Spinner months = new Spinner(c1, SWT.WRAP);
		months.setEnabled(false);
		label = new Label(c1, SWT.NULL);
		label.setText("months");
		
		final Spinner years = new Spinner(c1, SWT.WRAP);
		years.setEnabled(false);
		label = new Label(c1, SWT.NULL);
		label.setText("years");
		
		final DateTime timePeriod = new DateTime(c1, SWT.TIME);
		timePeriod.setTime(0, 0, 0);
		timePeriod.setEnabled(false);
	    
		// shiftConstant
		label = new Label(c1, SWT.NULL);
		label.setText("Shift constant: ");
		
		final Button checkShiftConstant = new Button(c1, SWT.CHECK);
		checkShiftConstant.setEnabled(false);
		
		final Spinner shiftConstant = new Spinner(c1, SWT.BORDER);
		shiftConstant.setEnabled(false);
		
		label = new Label(c1, SWT.NULL);
		label.setText("(int)");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 6;
	    label.setLayoutData(gridData);
		

		checkBasePeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (checkBasePeriod.getSelection()) {
					days.setEnabled(true);
					months.setEnabled(true);
					years.setEnabled(true);
					timePeriod.setEnabled(true);
					checkShiftConstant.setEnabled(true);
				} else {
					days.setEnabled(false);
					months.setEnabled(false);
					years.setEnabled(false);
					timePeriod.setEnabled(false);
					checkShiftConstant.setEnabled(false);
					shiftConstant.setEnabled(false);
				}
			}
		});
		
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
					case 0: // Abstract Integer Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": RandomGenerator rng");
						break;
					case 1: // Binomial Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int trials, double p");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner trials = new Spinner(c2, SWT.BORDER);
						Label label2 = new Label(c2, SWT.NULL);
						label2.setText("trials");
						
						Spinner p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("probability of success");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 3: // Geometric Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p");
						c2.setLayout(new GridLayout (2, false));
						
						p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("probability of success");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 4: // Hypergeometric Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int populationSize, int numberOfSuccesses, int sampleSize");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner populationSize = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("population size");
						
						Spinner numberOfSuccesses = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("number of successes");
						
						Spinner sampleSize = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("sample size");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 5: // Pascal Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int r, double p");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner r = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("number of successes");
						
						p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("probability of success");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 6: // Poisson Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p [, double epsilon] [, int maxIterations]");
						c2.setLayout(new GridLayout (3, false));
						
						p = new Spinner(c2, SWT.BORDER);
						p.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("specified mean");
						gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
					    gridData.horizontalSpan = 2;
					    label2.setLayoutData(gridData);
					    
					    final Button checkEpsilon = new Button(c2, SWT.CHECK);
					    
					    final Spinner epsilon = new Spinner(c2, SWT.BORDER);
					    epsilon.setDigits(2);
					    epsilon.setEnabled(false);
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
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 7: // Uniform Integer Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int lower, int upper");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner lower = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("Lower bound (inclusive) of this distribution");
						
						Spinner upper = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("Upper bound (inclusive) of this distribution");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 8: // Zipf Distribution
						System.out.println("Needed parameters for " + dropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int numberOfElements, double exponent");
						c2.setLayout(new GridLayout (2, false));
						
						Spinner numberOfElements = new Spinner(c2, SWT.BORDER);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("number of elements");
						
						Spinner exponent = new Spinner(c2, SWT.BORDER);
						exponent.setDigits(2);
						label2 = new Label(c2, SWT.NULL);
						label2.setText("exponent");
						
						c2.layout();
						c2.setSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						c1.layout();
						c1.setSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
				}
				mainComposite.layout(true, true);
			}
		});
		
		Composite c3 = new Composite(shell, SWT.NONE);
		c3.setLayout(new GridLayout (1, false));
		Button next = new Button(c3, SWT.PUSH);
		next.setText("next >");
		next.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
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
		
		final Composite mainComposite = new Composite(shell, SWT.NONE);
	  	mainComposite.setLayout(new GridLayout (9, false));
	  	
	  	// Integer distribution
 		Label label = new Label(mainComposite, SWT.NULL);
 		label.setText("Integer distribution: ");
 		Composite distributions = MaskerTool.loadIntegerDistributions(8);
 		distributions.setParent(mainComposite);
	 		
 		GridData gridData = new GridData();
 		gridData.horizontalAlignment = GridData.FILL;
 		gridData.grabExcessHorizontalSpace = true;
 	    gridData.horizontalSpan = 8;
 		distributions.setLayoutData(gridData);
 		distributions.layout(true);
		
		// basePeriod
		label = new Label(mainComposite, SWT.NULL);
		label.setText("Base period: ");
		
		final Button checkBasePeriod = new Button(mainComposite, SWT.CHECK);
		
		final Spinner days = new Spinner(mainComposite, SWT.WRAP);
		days.setEnabled(false);
		label = new Label(mainComposite, SWT.NULL);
		label.setText("days");
		
		final Spinner months = new Spinner(mainComposite, SWT.WRAP);
		months.setEnabled(false);
		label = new Label(mainComposite, SWT.NULL);
		label.setText("months");
		
		final Spinner years = new Spinner(mainComposite, SWT.WRAP);
		years.setEnabled(false);
		label = new Label(mainComposite, SWT.NULL);
		label.setText("years");
		
		final DateTime timePeriod = new DateTime(mainComposite, SWT.TIME);
		timePeriod.setTime(0, 0, 0);
		timePeriod.setEnabled(false);
	    
		// shiftConstant
		label = new Label(mainComposite, SWT.NULL);
		label.setText("Shift constant: ");
		
		final Button checkShiftConstant = new Button(mainComposite, SWT.CHECK);
		checkShiftConstant.setEnabled(false);
		
		final Spinner shiftConstant = new Spinner(mainComposite, SWT.BORDER);
		shiftConstant.setEnabled(false);
		
		label = new Label(mainComposite, SWT.NULL);
		label.setText("(int)");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 6;
	    label.setLayoutData(gridData);
		

		checkBasePeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (checkBasePeriod.getSelection()) {
					days.setEnabled(true);
					months.setEnabled(true);
					years.setEnabled(true);
					timePeriod.setEnabled(true);
					checkShiftConstant.setEnabled(true);
				} else {
					days.setEnabled(false);
					months.setEnabled(false);
					years.setEnabled(false);
					timePeriod.setEnabled(false);
					checkShiftConstant.setEnabled(false);
					shiftConstant.setEnabled(false);
				}
			}
		});
		
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