import org.deidentifier.arx.gui.view.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReplaceDictMaskerGUI implements ConfigurationComponent {
	static String replacementValueDouble = "";
	static String replacementValueDate = "";
	static String replacementValueString = "";
	
	private Label lbl1;
	private Label lbl2;
	
	private Combo cmbDropdown;
	private Text txtDictionary;
	
	private Composite cmpRoot;
	
	private boolean dropDownValid = false;
	private boolean dictionaryValid = false;
	
	public ReplaceDictMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(SWTUtil.createGridLayout(2));
      	
      	this.lbl1 = new Label(this.cmpRoot, SWT.NONE);
		this.lbl1.setText("Datatype:");
		this.lbl1.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.cmbDropdown = new Combo(this.cmpRoot, SWT.READ_ONLY);
	    this.cmbDropdown.setLayoutData(SWTUtil.createFillHorizontallyGridData());
		String[] dataType = {	"Double",
								"Date",
								"String"
								 };
		for (String s: dataType) {
			this.cmbDropdown.add(s);
		}
		
		this.lbl2 = new Label(this.cmpRoot, SWT.NONE);
	    this.lbl2.setText("Dictionary: ");
	    this.lbl2.setLayoutData(SWTUtil.createNoFillGridData());
	    
	    this.txtDictionary = new Text(this.cmpRoot, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		this.txtDictionary.setLayoutData(SWTUtil.createFillGridData());
      	
		this.cmbDropdown.addModifyListener(new ModifyListener() {
		    public void modifyText(ModifyEvent arg0) {
                validateCmbDropdown();
            }

		});
		this.txtDictionary.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent arg0) {
                validateTxtDictionary();
            }
        });
		
		this.validateCmbDropdown();
		this.validateTxtDictionary();
	}

    private void validateCmbDropdown() {
        dropDownValid = cmbDropdown.getSelectionIndex() != -1;
    }

    private void validateTxtDictionary() {
        dictionaryValid = txtDictionary.getText().length() != 0;
        // TODO: Add more
    }
    
	public boolean isValid() {
	    return dropDownValid && dictionaryValid;
	}

	public void addModifyListener(ModifyListener listener) {
	    cmbDropdown.addModifyListener(listener);
	    txtDictionary.addModifyListener(listener);
	}
}
