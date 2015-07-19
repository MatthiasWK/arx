import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ReplaceDictMaskerGUI implements ConfigurationComponent {
	static String replacementValueDouble = "";
	static String replacementValueDate = "";
	static String replacementValueString = "";
	
	private Label lbl;
	
	private Combo cmbDropdown;
	
	private Text txtDictionary;
	private Composite cmpRoot;
	
	public ReplaceDictMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(new GridLayout (3, false));
      	
      	this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Replacement dictionary: ");
		
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
	    
	    this.txtDictionary = new Text(this.cmpRoot, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		gridData = new GridData(SWT.FILL, 0, false, false);
		gridData.heightHint = 100;
		gridData.widthHint = 100;
		this.txtDictionary.setLayoutData(gridData);
      	
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
