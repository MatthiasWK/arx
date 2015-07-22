import org.eclipse.swt.SWT;
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


public class IntegerDistribution implements ConfigurationComponent{

	static int comboHorizontalSpan = 8;
	
	private Composite cmpRoot;
	private Combo cmbDropdown;
	private Composite cmpParam;
	
	private Label lbl;
	
	private Spinner spn1;
	private Spinner spn2;
	private Spinner spn3;
	
	private Text txtSingletons;
	private Text txtProbabilities;
	
	private Button btn1;
	
	public IntegerDistribution(Composite root){
		this.cmpRoot = new Composite(root, SWT.NONE);
		this.cmpRoot.setLayout(new GridLayout (comboHorizontalSpan, false));
		
		this.cmbDropdown = new Combo(cmpRoot, SWT.DROP_DOWN);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
	    gridData.horizontalSpan = comboHorizontalSpan;
	    this.cmbDropdown.setLayoutData(gridData);
	    this.cmbDropdown.setText("Choose your distribution...");
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
			this.cmbDropdown.add(s);
		}
		
		// composite for parameters of integer distribution
				this.cmpParam = new Composite(cmpRoot, SWT.NONE);
				gridData = new GridData();
			    gridData.horizontalSpan = comboHorizontalSpan;
			    this.cmpParam.setLayoutData(gridData);
			    
			    this.cmbDropdown.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						int selectedIndex = cmbDropdown.getSelectionIndex();
						for (Control child: cmpParam.getChildren()) {
							child.dispose();
						}
						switch (selectedIndex) {
							case 0: // Abstract Integer Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": RandomGenerator rng");
								break;
							case 1: // Binomial Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int trials, double p");
								cmpParam.setLayout(new GridLayout (2, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("trials");
								
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
							case 2: // Enumerated Integer Distribution
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
							case 3: // Geometric Distribution
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
							case 4: // Hypergeometric Distribution
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
							case 5: // Pascal Distribution
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
							case 6: // Poisson Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] double p [, double epsilon] [, int maxIterations]");
								cmpParam.setLayout(new GridLayout (3, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setDigits(2);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("specified mean");
								gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
							    gridData.horizontalSpan = 2;
							    lbl.setLayoutData(gridData);
							    
							    final Button checkEpsilon = new Button(cmpParam, SWT.CHECK);
							    
							    spn2 = new Spinner(cmpParam, SWT.BORDER);
							    spn2.setDigits(2);
							    spn2.setEnabled(false);
							    spn2.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("convergence criterion");
								
								checkEpsilon.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent event) {
										if (checkEpsilon.getSelection()) {
											spn2.setEnabled(true);
										} else {
											spn2.setEnabled(false);
										}
									}
								});
								
								btn1 = new Button(cmpParam, SWT.CHECK);
							    
							    spn3 = new Spinner(cmpParam, SWT.BORDER);
							    spn3.setEnabled(false);
							    spn3.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("maximum number of iterations");
								
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
							case 7: // Uniform Integer Distribution
								System.out.println("Needed parameters for " + cmbDropdown.getItem(selectedIndex) + ": [RandomGenerator rng,] int lower, int upper");
								cmpParam.setLayout(new GridLayout (2, false));
								
								spn1 = new Spinner(cmpParam, SWT.BORDER);
								spn1.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("Lower bound (inclusive) of this distribution");
								
								spn2= new Spinner(cmpParam, SWT.BORDER);
								spn2.setMaximum(100000);
								lbl = new Label(cmpParam, SWT.NULL);
								lbl.setText("Upper bound (inclusive) of this distribution");
								
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
						}
						cmpRoot.layout(true, true);
					}
			    });
	}
	
	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
		shell.setLayout (layout);
		shell.setText("IntegerDistribution");

		IntegerDistribution c1 = new IntegerDistribution(shell);
		
		shell.pack ();
		shell.open ();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}
	
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public Composite getCmpRoot() {
		return cmpRoot;
	}

	public void setCmpRoot(Composite cmpRoot) {
		this.cmpRoot = cmpRoot;		
	}
	
}
