import org.apache.commons.lang.StringUtils;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.GenerateRandomStringMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomStringMaskerGUI implements ConfigurationComponent {
	
	private Label lblLength;
	private Label lblChars;
	private Label lblSet;
	private Label lblAny;
	private Label lblNum;
	private Label lblAlph;
	
	private Button btn1;
	private Button btn2;
	private Button btnLength;
	private Button btnLetters;
	private Button btnNumbers;
	
	private Text txtCharSet;
	
	private Spinner spnLength;

	private Composite cmpRoot;
	private Composite cmpChars;
	private Composite cmpLength;
	private Composite cmpAny;
	
	private Boolean charSetValid = false;
	private Boolean alphaNumValid = false;
	
	public GenerateRandomStringMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(SWTUtil.createGridLayout(1));
 		
      	
 	    // characters

 		this.lblChars = new Label(cmpRoot, SWT.NONE);
 		this.lblChars.setLayoutData(SWTUtil.createNoFillGridData());
 		this.lblChars.setText("Specify Characters:");
 		

 		this.cmpChars = new Composite(cmpRoot, SWT.BORDER);
        this.cmpChars.setLayout(SWTUtil.createGridLayout(3));
		this.cmpChars.setLayoutData(SWTUtil.createFillGridData());
		
 		// any alphabetic and/or numeric characters
 		   	
 		this.btn1 = new Button(this.cmpChars, SWT.RADIO);
 		this.btn1.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		this.lblAny = new Label(this.cmpChars, SWT.NONE);
 		this.lblAny.setText("Any Alphabetic and/or Numeric Characters:");
 		this.lblAny.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		
 		this.cmpAny = new Composite(this.cmpChars, SWT.NONE);
 		this.cmpAny.setLayoutData(SWTUtil.createGridData());
 		this.cmpAny.setLayout(SWTUtil.createGridLayout(2));
 		
 		this.btnLetters = new Button(this.cmpAny, SWT.CHECK);
 		this.btnLetters.setEnabled(false);
 		this.lblAlph = new Label(this.cmpAny, SWT.NONE);
 		this.lblAlph.setText("Alphabetic");
 		
 		this.btnNumbers = new Button(this.cmpAny, SWT.CHECK);
 		this.btnNumbers.setEnabled(false);
 		this.lblNum = new Label(this.cmpAny, SWT.NONE);
 		this.lblNum.setText("Numeric");
 		
 		this.btnLetters.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				validateAlphaNum();
 			}
 		});
 		
 		this.btnNumbers.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				validateAlphaNum();
 			}
 		});
 		
 		this.btn1.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btn1.getSelection()) {
 					btnLetters.setEnabled(true);
 					btnNumbers.setEnabled(true);
 					validateCharSet();
 			 		validateAlphaNum();
 				} else {
 					btnLetters.setEnabled(false);
 					btnNumbers.setEnabled(false);
 					validateCharSet();
 			 		validateAlphaNum();
 				}
 			}
 		});
 		
 		// specific character set
 		this.btn2 = new Button(this.cmpChars, SWT.RADIO);
 		this.btn2.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		this.lblSet = new Label(this.cmpChars, SWT.NONE);
 		this.lblSet.setText("Specific Alphanumeric Character Set:");
 		this.lblSet.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

 		this.txtCharSet = new Text(this.cmpChars, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
 		this.txtCharSet.setEnabled(false);
 		this.txtCharSet.setLayoutData(SWTUtil.createFillGridData());
 		
 		this.btn2.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btn2.getSelection()) {
 					txtCharSet.setEnabled(true);
 					validateCharSet();
 			 		validateAlphaNum();
 				} else {
 					txtCharSet.setEnabled(false);
 					validateCharSet();
 			 		validateAlphaNum();
 				}
 			}
 		});
 		
 		this.txtCharSet.addModifyListener(new ModifyListener() {
 			public void modifyText(ModifyEvent arg0) {
 				validateCharSet();
 			}
 		});
 		
 		// length
 		this.cmpLength = new Composite(cmpRoot, SWT.NONE);
        this.cmpLength.setLayout(SWTUtil.createGridLayout(3));
		this.cmpLength.setLayoutData(SWTUtil.createFillGridData());
        
 	 	this.lblLength = new Label(this.cmpLength, SWT.NONE);
 		this.lblLength.setText("Length:");
  	    this.lblLength.setLayoutData(SWTUtil.createGridData());
 	 	    
  	    this.btnLength = new Button(this.cmpLength, SWT.CHECK);
  	    this.btnLength.setLayoutData(SWTUtil.createGridData());
 	 	
       	this.spnLength = new Spinner(this.cmpLength, SWT.BORDER);
  		this.spnLength.setEnabled(false);
  		this.spnLength.setMaximum(100000);
  		this.spnLength.setLayoutData(SWTUtil.createGridData());
 		
  		this.btnLength.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				spnLength.setEnabled(btnLength.getSelection());
			}
		});
  		
 		this.validateCharSet();
 		this.validateAlphaNum();
	}
	
	private void validateCharSet() {
		if (this.btn2.getSelection()) {
			String input = this.txtCharSet.getText();
			if(!input.isEmpty() && StringUtils.isAlphanumeric(input)){
				this.charSetValid = true;
				this.txtCharSet.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			}
			else{
				this.charSetValid = false;
				this.txtCharSet.setForeground(cmpRoot.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			
		} else {
			this.charSetValid = true;
		}
		
	}
	
	private void validateAlphaNum(){
		if (this.btn1.getSelection()) {
			alphaNumValid = btnLetters.getSelection() || btnNumbers.getSelection();			
		} else {
			this.alphaNumValid = true;
		}
	}
	
	public boolean isValid() {
		return (this.btn1.getSelection() || this.btn2.getSelection()) &&
				this.charSetValid && this.alphaNumValid;
	}

	public void addModifyListener(ModifyListener listener) {
		this.txtCharSet.addModifyListener(listener);
		this.spnLength.addModifyListener(listener);
	}

	
	public void addSelectionListener(SelectionAdapter adapter) {
		this.btn1.addSelectionListener(adapter);
		this.btn2.addSelectionListener(adapter);
		this.btnLetters.addSelectionListener(adapter);
		this.btnNumbers.addSelectionListener(adapter);
	}
	
	public GenerateRandomStringMasker getMasker() {
		int len = -1;
		if (this.btnLength.getSelection()) len = this.spnLength.getSelection();
		
		if (this.btn1.getSelection()) {
			return new GenerateRandomStringMasker(len, this.btnLetters.getSelection(), this.btnNumbers.getSelection());
		} 
		else {
			return new GenerateRandomStringMasker(len, this.txtCharSet.getText().toCharArray());
		} 
	}
}
