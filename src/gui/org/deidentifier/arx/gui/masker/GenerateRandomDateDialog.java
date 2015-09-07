
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.GenerateRandomDateMasker;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GenerateRandomDateDialog extends Dialog {
  private Text txtOutput;
  
  private Label lblOut;
  
  private Composite cmpInOut;
  private Composite cmpButtons;
  
  private Button btnOk;
  private Button btnCancel;
  
  private GenerateRandomDateMasker masker;
  

  /**
   * InputDialog constructor
   * 
   * @param parent the parent
   */
  public GenerateRandomDateDialog(Shell parent, GenerateRandomDateMasker masker) {
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
    this.btnOk.setText("OK");
    this.btnOk.setLayoutData(SWTUtil.createFillHorizontallyGridData());
    this.btnOk.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        txtOutput.setText(df.format(masker.createReplacement()));
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

  }

}
