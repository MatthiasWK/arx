import org.deidentifier.arx.masking.GenerateRandomStringMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomStringMaskerGUI implements ConfigurationComponent {
	
	private Label lbl;
	
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btnCheckLettersNumbers;
	private Button btnCheckCharSet;
	private Button btnLetters;
	private Button btnNumbers;
	private Button btnLetters2;
	private Button btnNumbers2;
	
	private Composite cmp2;
	private Composite cmp3;
	
	private Text txtCharSet;
	private Text txtCharSet2;
	
	private Spinner spnLength;

	private Composite cmpRoot;
	
	private Boolean charSet1Valid = false;
	private Boolean charSet2Valid = false;
	
	public GenerateRandomStringMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(new GridLayout (3, false));
      	
      	// no parameter
 		this.btn1 = new Button(this.cmpRoot, SWT.RADIO);
 		this.lbl = new Label(this.cmpRoot, SWT.NONE);
 		this.lbl.setText("No parameters (Creates a masker generating strings with the same length as the input, using only alphabetic characters.)");

 		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
 	    gridData.horizontalSpan = 2;
 	    this.lbl.setLayoutData(gridData);
 		
 	    // boolean letters, boolean numbers
 		this.btn2 = new Button(this.cmpRoot, SWT.RADIO);
 		this.btn2.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		this.lbl = new Label(this.cmpRoot, SWT.NONE);
 		this.lbl.setText("Alphabetic and/or numeric characters:");
 		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		
 		this.cmp2 = new Composite(this.cmpRoot, SWT.NONE);
 		this.cmp2.setLayout(new GridLayout(2, false));
 		
 		this.btnLetters = new Button(this.cmp2, SWT.CHECK);
 		this.btnLetters.setEnabled(false);
 		this.lbl = new Label(this.cmp2, SWT.NONE);
 		this.lbl.setText("alphabetic characters");
 		
 		this.btnNumbers = new Button(this.cmp2, SWT.CHECK);
 		this.btnNumbers.setEnabled(false);
 		this.lbl = new Label(this.cmp2, SWT.NONE);
 		this.lbl.setText("numeric characters");
 		
 		this.btn2.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btn2.getSelection()) {
 					btnLetters.setEnabled(true);
 					btnNumbers.setEnabled(true);
 				} else {
 					btnLetters.setEnabled(false);
 					btnNumbers.setEnabled(false);
 				}
 			}
 		});
 		
 		// char[] charSet
 		this.btn3 = new Button(this.cmpRoot, SWT.RADIO);
 		this.btn3.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		this.lbl = new Label(this.cmpRoot, SWT.NONE);
 		this.lbl.setText("Character set:");
 		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

 		this.txtCharSet = new Text(this.cmpRoot, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
 		this.txtCharSet.setEnabled(false);
 		gridData = new GridData(SWT.FILL, 0, false, false);
 		gridData.heightHint = 50;
 		this.txtCharSet.setLayoutData(gridData);
 		
 		this.btn3.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btn3.getSelection()) {
 					txtCharSet.setEnabled(true);
 					validateCharSet();
 				} else {
 					txtCharSet.setEnabled(false);
 					validateCharSet();
 				}
 			}
 		});
 		
 		this.txtCharSet.addModifyListener(new ModifyListener() {
 			public void modifyText(ModifyEvent arg0) {
 				validateCharSet();
 			}
 		});
 		
 		// length + other parameters
 		this.btn4 = new Button(this.cmpRoot, SWT.RADIO);
 		this.btn4.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		this.lbl = new Label(this.cmpRoot, SWT.NONE);
 		this.lbl.setText("Length + other parameters:");
 		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		
 		this.cmp3 = new Composite(this.cmpRoot, SWT.NONE);
 		this.cmp3.setLayout(new GridLayout(4, false));
 		
 		this.spnLength = new Spinner(this.cmp3, SWT.WRAP);
 		this.spnLength.setEnabled(false);
 		this.spnLength.setMaximum(100000);
 		
 		this.lbl = new Label(this.cmp3, SWT.NONE);
 		this.lbl.setText("(int length)");
 		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
 	    gridData.horizontalSpan = 3;
 	    this.lbl.setLayoutData(gridData);
 		
 		this.btnCheckCharSet = new Button(this.cmp3, SWT.CHECK);
 		this.btnCheckCharSet.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
 		this.btnCheckCharSet.setEnabled(false);
 		
 		this.lbl = new Label(this.cmp3, SWT.NONE);
 		this.lbl.setText("Character set:");
 		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		
 		this.txtCharSet2 = new Text(this.cmp3, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
 		this.txtCharSet2.setEnabled(false);
 		gridData = new GridData(SWT.FILL, 0, false, false);
 		gridData.heightHint = 50;
 	    gridData.horizontalSpan = 2;
 	    this.txtCharSet2.setLayoutData(gridData);
 		
 		this.btnCheckLettersNumbers = new Button(this.cmp3, SWT.CHECK);
 		this.btnCheckLettersNumbers.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));
 		this.btnCheckLettersNumbers.setEnabled(false);
 		
 		this.lbl = new Label(this.cmp3, SWT.NONE);
 		this.lbl.setText("Alphabetic/numeric characters:");
 		this.lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
 		
 		this.btnLetters2 = new Button(this.cmp3, SWT.CHECK);
 		this.btnLetters2.setEnabled(false);
 		this.lbl = new Label(this.cmp3, SWT.NONE);
 		this.lbl.setText("alphabetic characters");

 		this.lbl = new Label(this.cmp3, SWT.NONE);
 		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
 	    gridData.horizontalSpan = 2;
 	    this.lbl.setLayoutData(gridData);
 	    
 	    this.btnNumbers2 = new Button(this.cmp3, SWT.CHECK);
 	    this.btnNumbers2.setEnabled(false);
 		this.lbl = new Label(this.cmp3, SWT.NONE);
 		this.lbl.setText("numeric characters");
 		
 		this.btn4.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btn4.getSelection()) {
 					spnLength.setEnabled(true);
 					btnCheckCharSet.setEnabled(true);
 					btnCheckLettersNumbers.setEnabled(true);
 				} else {
 					spnLength.setEnabled(false);
 					btnCheckCharSet.setEnabled(false);
 					btnCheckLettersNumbers.setEnabled(false);
 				}
 			}
 		});
 		
 		this.btnCheckCharSet.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btnCheckCharSet.getSelection()) {
 					txtCharSet2.setEnabled(true);
 					validateCharSet();
 				} else {
 					txtCharSet2.setEnabled(false);
 					validateCharSet();
 				}
 			}
 		});
 		
 		this.txtCharSet2.addModifyListener(new ModifyListener() {
 			public void modifyText(ModifyEvent arg0) {
 				validateCharSet();
 			}
 		});
 		
 		this.btnCheckLettersNumbers.addSelectionListener(new SelectionAdapter() {
 			public void widgetSelected(SelectionEvent event) {
 				if (btnCheckLettersNumbers.getSelection()) {
 					btnLetters2.setEnabled(true);
 					btnNumbers2.setEnabled(true);
 				} else {
 					btnLetters2.setEnabled(false);
 					btnNumbers2.setEnabled(false);
 				}
 			}
 		});
      	
 		this.validateCharSet();
	}
	
	private void validateCharSet() {
		if (this.btn3.getSelection()) {
			if (this.txtCharSet.getEnabled()) {
				this.charSet1Valid = (this.txtCharSet.getText() == "") ? false : true;
			}
			this.charSet2Valid = true;
		} else {
			this.charSet1Valid = true;
		}
		
		if (this.btn4.getSelection()) {
			if (this.txtCharSet2.getEnabled()) {
				this.charSet2Valid = (this.txtCharSet2.getText() == "") ? false : true;
			} else {
				this.charSet2Valid = true;
			}
			this.charSet1Valid = true;
		} else {
			this.charSet2Valid = true;
		}
	}
	
	public boolean isValid() {
		return (this.btn1.getSelection() || this.btn2.getSelection() || this.btn3.getSelection() || this.btn4.getSelection()) &&
				this.charSet1Valid && this.charSet2Valid;
	}

	public void addModifyListener(ModifyListener listener) {
		this.txtCharSet.addModifyListener(listener);
		this.spnLength.addModifyListener(listener);
		this.txtCharSet2.addModifyListener(listener);
	}

	
	public void addSelectionListener(SelectionAdapter adapter) {
		this.btn1.addSelectionListener(adapter);
		this.btn2.addSelectionListener(adapter);
		this.btn3.addSelectionListener(adapter);
		this.btn4.addSelectionListener(adapter);
		this.btnLetters.addSelectionListener(adapter);
		this.btnNumbers.addSelectionListener(adapter);
		this.btnCheckCharSet.addSelectionListener(adapter);
		this.btnCheckLettersNumbers.addSelectionListener(adapter);
		this.btnLetters2.addSelectionListener(adapter);
		this.btnNumbers2.addSelectionListener(adapter);
	}
	
	public GenerateRandomStringMasker getMasker() {
		if (this.btn2.getSelection()) {
			return new GenerateRandomStringMasker(this.btnLetters.getSelection(), this.btnNumbers.getSelection());
		} else if (this.btn3.getSelection()) {
			return new GenerateRandomStringMasker(this.txtCharSet.getText().toCharArray());
		} else if (this.btn4.getSelection()) {
			int length = this.spnLength.getDigits();
			if (this.btnCheckCharSet.getSelection() && this.btnCheckLettersNumbers.getSelection() == false) {
				return new GenerateRandomStringMasker(length, this.txtCharSet2.getText().toCharArray());
			} else if (this.btnCheckCharSet.getSelection() == false && this.btnCheckLettersNumbers.getSelection()) {
				return new GenerateRandomStringMasker(length, this.btnLetters2.getSelection(), this.btnNumbers2.getSelection());
			} else if (this.btnCheckCharSet.getSelection() && this.btnCheckLettersNumbers.getSelection()) {
				return new GenerateRandomStringMasker(	length,
														this.btnLetters2.getSelection(),
														this.btnNumbers2.getSelection(),
														this.txtCharSet2.getText().toCharArray());
			} else {
				return new GenerateRandomStringMasker(length);
			}
		} else {
			return new GenerateRandomStringMasker();
		}
	}
}
