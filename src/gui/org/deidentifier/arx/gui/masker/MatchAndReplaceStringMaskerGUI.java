

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.masking.MatchAndReplaceStringMasker;

public class MatchAndReplaceStringMaskerGUI implements ConfigurationComponent {

	private Label lbl;
	
	private Text txtRegEx;
	private Text txtReplacementString;
	
	private Button btnMatches;
	private Button btnChars;

	private Composite cmpRoot;
	
	public MatchAndReplaceStringMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
	  	this.cmpRoot.setLayout(new GridLayout (2, false));
		
		this.lbl = new Label (this.cmpRoot, SWT.NULL);
		this.lbl.setText("Regular Expression");
		
		this.txtRegEx = new Text (this.cmpRoot, SWT.BORDER | SWT.FILL);
		this.txtRegEx.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		this.lbl = new Label (this.cmpRoot, SWT.NULL);
		this.lbl.setText("Replacement String");
		
		this.txtReplacementString = new Text(this.cmpRoot, SWT.BORDER);
		this.txtReplacementString.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		new Label(this.cmpRoot, SWT.NONE);
		
		this.btnMatches = new Button(this.cmpRoot, SWT.CHECK);
		this.btnMatches.setText("replace all matches");
		
		new Label(this.cmpRoot, SWT.NONE);
	  	
		this.btnChars = new Button(this.cmpRoot, SWT.CHECK);
		this.btnChars.setText("replace each character");
		
	}
	
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addModifyListener(ModifyListener listener) {
		// TODO Auto-generated method stub
		
	}
}

