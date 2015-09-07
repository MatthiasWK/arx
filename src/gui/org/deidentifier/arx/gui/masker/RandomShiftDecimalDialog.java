
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.GenerateRandomStringMasker;
import org.deidentifier.arx.masking.RandomShiftDecimalMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class RandomShiftDecimalDialog extends Dialog {
  private Text txtInput;
  private Text txtOutput;
  
  private Label lblIn;
  private Label lblOut;
  
  private Composite cmpInOut;
  private Composite cmpButtons;
  
  private Boolean inputValid = false; 
  
  private Button btnOk;
  private Button btnCancel;
  
  private RandomShiftDecimalMasker masker;
  

  /**
   * InputDialog constructor
   * 
   * @param parent the parent
   */
  public RandomShiftDecimalDialog(Shell parent, RandomShiftDecimalMasker masker) {
    // Pass the default styles here
	  super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
	  this.masker = masker;
	  setText("Masker Input");
  }


  public void open() {
    // Create the dialog window
    Shell shell = new Shell(getParent(), getStyle());
    shell.setText(getText());
    shell.setLayout(SWTUtil.createGridLayout(1));
    createContents(shell);
    shell.pack();
    shell.open();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Creates the dialog's contents
   * 
   * @param shell the dialog window
   */
  private void createContents(final Shell shell) {
	this.cmpInOut = new Composite(shell, SWT.NONE);
	this.cmpInOut.setLayout(SWTUtil.createGridLayout(2));
    this.cmpInOut.setLayoutData(SWTUtil.createFillGridData());
    // input
    this.lblIn = new Label(cmpInOut, SWT.NONE);
    this.lblIn.setText("input: ");
    this.lblIn.setLayoutData(SWTUtil.createGridData());

    this.txtInput = new Text(cmpInOut, SWT.BORDER);
    this.txtInput.setLayoutData(SWTUtil.createFillHorizontallyGridData());

    // output
    this.lblOut = new Label(cmpInOut, SWT.NONE);
    this.lblOut.setText("output: ");
    this.lblOut.setLayoutData(SWTUtil.createGridData());

    this.txtOutput = new Text(cmpInOut, SWT.BORDER|SWT.READ_ONLY);
    this.txtOutput.setLayoutData(SWTUtil.createFillHorizontallyGridData());

    // buttons
    this.cmpButtons= new Composite(shell, SWT.NONE);
    this.cmpButtons.setLayout(SWTUtil.createGridLayout(2));
    this.cmpButtons.setLayoutData(SWTUtil.createFillHorizontallyGridData());
    
    this.btnOk = new Button(this.cmpButtons, SWT.PUSH);
    this.btnOk.setEnabled(false);
    this.btnOk.setText("OK");
    this.btnOk.setLayoutData(SWTUtil.createFillHorizontallyGridData());
    this.btnOk.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        txtOutput.setText(masker.mask(Double.parseDouble(txtInput.getText())).toString());
      }
    });

    this.btnCancel = new Button(this.cmpButtons, SWT.PUSH);
    this.btnCancel.setText("Cancel");
    this.btnCancel.setLayoutData(SWTUtil.createFillHorizontallyGridData());
    this.btnCancel.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        shell.close();
      }
    });
    
    this.txtInput.addModifyListener(new ModifyListener() {
 			public void modifyText(ModifyEvent arg0) {
 				validateInput();
 				btnOk.setEnabled(inputValid);
 			}
 		});

  }

  public void validateInput() {
	  if(NumberUtils.isNumber(this.txtInput.getText())){
			this.inputValid = true;
			this.txtInput.setForeground(cmpInOut.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}
		else{
			this.inputValid = false;
			this.txtInput.setForeground(cmpInOut.getDisplay().getSystemColor(SWT.COLOR_RED));
		}
  }

}
