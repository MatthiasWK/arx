

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.MatchAndReplaceStringMasker;

public class MatchAndReplaceStringMaskerGUI implements ConfigurationComponent {

	private Label lblRegEx;
	private Label lblReplace;
	private Label lblMatches;
	private Label lblChars;
	
	private Text txtRegEx;
	private Text txtReplacementString;
	
	private Button btnMatches;
	private Button btnChars;

	private boolean regExValid;
	private boolean replaceValid;
	
	private Composite cmpRoot;
	
	public MatchAndReplaceStringMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
	  	this.cmpRoot.setLayout(SWTUtil.createGridLayout(2));
	  	
		this.lblRegEx = new Label (this.cmpRoot, SWT.NONE);
		this.lblRegEx.setText("Regular Expression:");
		this.lblRegEx.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.txtRegEx = new Text (this.cmpRoot, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		this.txtRegEx.setLayoutData(SWTUtil.createFillGridData());
		this.txtRegEx.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				validateRegEx();
			}
		});
		
		this.lblReplace = new Label (this.cmpRoot, SWT.NULL);
		this.lblReplace.setText("Replacement String:");
		this.lblReplace.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.txtReplacementString = new Text(this.cmpRoot, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		this.txtReplacementString.setLayoutData(SWTUtil.createFillGridData());
		this.txtReplacementString.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				validateReplacement();
			}
		});
		this.lblMatches = new Label(this.cmpRoot, SWT.NONE);
		this.lblMatches.setText("Replace all Matches:");
		this.lblMatches.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.btnMatches = new Button(this.cmpRoot, SWT.CHECK);
		this.btnMatches.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.lblChars = new Label(this.cmpRoot, SWT.NONE);
		this.lblChars.setText("Replace each Character:");
		this.lblChars.setLayoutData(SWTUtil.createNoFillGridData());
		
		this.btnChars = new Button(this.cmpRoot, SWT.CHECK);
		this.btnChars.setLayoutData(SWTUtil.createNoFillGridData());
		
		validateRegEx();
		validateReplacement();
	}
	
	private void validateRegEx(){
		boolean isRegEx = false;
		String input = this.txtRegEx.getText();
		try {
			  Pattern.compile(input);
			  isRegEx = true;
			  txtRegEx.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			} catch (PatternSyntaxException e) {
				isRegEx= false;
			  txtRegEx.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
		regExValid = isRegEx && !input.isEmpty();
	}
	
	private void validateReplacement(){
		replaceValid = !txtReplacementString.getText().isEmpty();
	}
	
	public MatchAndReplaceStringMasker getMasker(){
		return new MatchAndReplaceStringMasker(txtRegEx.getText(), txtReplacementString.getText(), btnMatches.getSelection(), btnChars.getSelection());
	}
	
	public boolean isValid() {
		return this.regExValid && this.replaceValid;
	}
	
	public void addModifyListener(ModifyListener listener) {
		this.txtRegEx.addModifyListener(listener);
		this.txtReplacementString.addModifyListener(listener);
	}
	
	public void addSelectionListener(SelectionAdapter adapter) {
		this.btnChars.addSelectionListener(adapter);
		this.btnMatches.addSelectionListener(adapter);
	}
}

