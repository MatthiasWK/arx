import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ReplaceInstMaskerGUI implements ConfigurationComponent {
	static String replacementValueDouble = "";
	static String replacementValueDate = "";
	static String replacementValueString = "";
	
	private Label lbl;
	
	private Combo cmbDropdown;
	
	private Composite cmp2;
	
	private Spinner spnReplacementValue;
	private DateTime dtReplacementValue2;
	private Text txtReplacementValue3;
	
	private Composite cmpRoot;
	
	public ReplaceInstMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(new GridLayout (3, false));
      	
      	this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Replacement value: ");
		
		this.cmbDropdown = new Combo(this.cmpRoot, SWT.DROP_DOWN);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    gridData.horizontalSpan = 2;
	    this.cmbDropdown.setLayoutData(gridData);
	    this.cmbDropdown.setText("Choose data type...");
		String[] dataType = {	"Double",
								"Date",
								"String"
								 };
		for (String s: dataType) {
			this.cmbDropdown.add(s);
		}
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		
		this.cmp2 = new Composite(this.cmpRoot, SWT.NONE);
		gridData = new GridData();
	    gridData.horizontalSpan = 2;
	    this.cmp2.setLayoutData(gridData);
	    
	    this.cmbDropdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int selectedIndex = cmbDropdown.getSelectionIndex();
				for (Control child: cmp2.getChildren()) {
					child.dispose();
				}
				switch (selectedIndex) {
					case 0: // Double
						cmp2.setLayout(new RowLayout());
						
						spnReplacementValue = new Spinner(cmp2, SWT.BORDER);
						spnReplacementValue.setDigits(2);
						spnReplacementValue.setMaximum(100000);
						spnReplacementValue.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								replacementValueDouble = spnReplacementValue.getText();
							}
						});
						
						cmp2.layout();
						cmp2.setSize(cmp2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 1: // Date
						cmp2.setLayout(new RowLayout());
						
						dtReplacementValue2 = new DateTime(cmp2, SWT.CALENDAR);
						dtReplacementValue2.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								replacementValueDate = dtReplacementValue2.getDay() + "." +
														(dtReplacementValue2.getMonth()+1) + "." +
														dtReplacementValue2.getYear();
							}
						});
						
						cmp2.layout();
						cmp2.setSize(cmp2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
					case 2: // String
						cmp2.setLayout(new RowLayout());
						
						txtReplacementValue3 = new Text(cmp2, SWT.BORDER);
						txtReplacementValue3.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent e) {
								replacementValueString = txtReplacementValue3.getText();
							}
						});
						
						cmp2.layout();
						cmp2.setSize(cmp2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						cmpRoot.layout();
						cmpRoot.setSize(cmpRoot.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						break;
				}
				cmpRoot.layout(true, true);
			}
	    });
      	
	}
	
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Composite getCmpRoot() {
		return cmpRoot;
	}

	@Override
	public void setCmpRoot(Composite cmpRoot) {
		this.cmpRoot = cmpRoot;		
	}
}
