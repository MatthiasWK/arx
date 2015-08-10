import java.sql.Date;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.gui.view.SWTUtil;

public class ReplaceInstMaskerGUI implements ConfigurationComponent {
	static Double replacementValueDouble = null;
	static Date replacementValueDate = null;
	static String replacementValueString = "";
	
	private Label lbl;
	
	private Combo cmbDropdown;
	
	private Composite cmp2;
	
	private Spinner spnReplacementValue;
	private DateTime dtReplacementValue2;
	private Text txtReplacementValue3;
	
	private Composite cmpRoot;
	
	private boolean doubleValueValid = false;
	private boolean dateValueValid = false;
	private boolean stringValueValid = false;
	
	public ReplaceInstMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(SWTUtil.createGridLayout(2));
      	
      	this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Replacement value: ");
		
		this.cmbDropdown = new Combo(this.cmpRoot, SWT.DROP_DOWN);
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
	    this.cmp2.setLayoutData(SWTUtil.createFillGridData());
	    
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
								try {
									replacementValueDouble = Double.parseDouble(spnReplacementValue.getText());
						            doubleValueValid = true;
						        } catch (NumberFormatException exc) {
						        	doubleValueValid = false;
						        }
							}
						});
						
						break;
					case 1: // Date
						cmp2.setLayout(new RowLayout());
						
						dtReplacementValue2 = new DateTime(cmp2, SWT.CALENDAR);
						dtReplacementValue2.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								try {
									replacementValueDate = Date.valueOf(dtReplacementValue2.getYear() + "-" + 
																		(dtReplacementValue2.getMonth()+1) + "-" +
																		dtReplacementValue2.getDay());
									dateValueValid = true;
								} catch (IllegalArgumentException exc) {
									dateValueValid = false;
								}
							}
						});
						
						break;
					case 2: // String
						cmp2.setLayout(new RowLayout());
						
						txtReplacementValue3 = new Text(cmp2, SWT.BORDER);
						txtReplacementValue3.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent e) {
								if (txtReplacementValue3.getText() != "") {
									replacementValueString = txtReplacementValue3.getText();
									stringValueValid = true;
								} else {
									stringValueValid = false;
								}
							}
						});
						
						break;
				}
				cmpRoot.layout(true, true);
			}
	    });
      	
	}
	
	public boolean isValid() {
		int selectedIndex = cmbDropdown.getSelectionIndex();
		switch (selectedIndex) {
			case 0: return doubleValueValid;
			case 1: return dateValueValid;
			case 2: return stringValueValid;
			default: return false;
		}
	}

	public void addModifyListener(ModifyListener listener) {
		// TODO Auto-generated method stub
		
	}
}
