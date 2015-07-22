

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.SplitAndReplaceStringMasker;

public class SplitAndReplaceStringMaskerGUI implements ConfigurationComponent{

	private Label lbl;
	
	private Text txtRegEx;
	private Text txtReplacementString;
	
	private Spinner spnReplaceGroup;
	
	private Button btnEachCharacter;

	private Composite cmpRoot;
	
	public SplitAndReplaceStringMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.BORDER);
		this.cmpRoot.setLayout(new GridLayout(2, false));
		
		this.lbl = new Label (this.cmpRoot, SWT.NULL);
		this.lbl.setText("Regular Expression:");
		
		this.txtRegEx = new Text (this.cmpRoot, SWT.BORDER | SWT.FILL);
		this.txtRegEx.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Replacement String:");
		
		this.txtReplacementString = new Text(this.cmpRoot, SWT.BORDER);
		this.txtReplacementString.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("Replace Group:");
		
		this.spnReplaceGroup = new Spinner(this.cmpRoot, SWT.BORDER);
		
		new Label(this.cmpRoot, SWT.NONE);
		
		this.btnEachCharacter = new Button(this.cmpRoot, SWT.CHECK);
		this.btnEachCharacter.setText("replace each character");
		
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
