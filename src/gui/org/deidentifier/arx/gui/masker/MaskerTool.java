import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.masking.GenerateRandomDateMasker;
import org.deidentifier.arx.masking.GenerateRandomStringMasker;
import org.deidentifier.arx.masking.MatchAndReplaceStringMasker;
import org.deidentifier.arx.masking.RandomShiftDecimalMasker;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MaskerTool {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell (display);
		shell.setText("Masker Tool");
		shell.setLayout(SWTUtil.createGridLayout(1));
		
		final TabFolder tabFolder = new TabFolder(shell, SWT.BORDER);
		
		String[] maskerTypes = {	"GenerateRandomDateMasker",
									"GenerateRandomStringMasker",
									"RandomShiftDecimalMasker",
									"MatchAndReplaceStringMasker" 
								};

		final ConfigurationComponent[] GUIs = new ConfigurationComponent[maskerTypes.length];
		
	    for (int loopIndex = 0; loopIndex < GUIs.length; loopIndex++) {
	    	TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
	      	tabItem.setText(maskerTypes[loopIndex]);
	      
	      	Composite base = new Composite(tabFolder, SWT.NONE);
	      	tabItem.setControl(base);
	      	base.setLayout(new FillLayout());
	      	
	      	switch (loopIndex) {
	      		case 0: 
	      			GUIs[loopIndex] = new GenerateRandomDateMaskerGUI(base);
	      			break;
	      		case 1:
	      			GUIs[loopIndex] = new GenerateRandomStringMaskerGUI(base);
	      			break;
	      	    case 2:
	      	    	GUIs[loopIndex] = new RandomShiftDecimalMaskerGUI(base);
	      			break;
	      		case 3:
	      			GUIs[loopIndex] = new MatchAndReplaceStringMaskerGUI(base);
	      			break;
	      			
	      	}
	    }

	    tabFolder.setLayoutData(SWTUtil.createFillGridData());
	    
	    Composite buttons = new Composite(shell, SWT.NONE);
	    buttons.setLayout(SWTUtil.createGridLayout(2));
	    buttons.setLayoutData(SWTUtil.createFillHorizontallyGridData());
	    
	    final Button ok = new Button(buttons, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(SWTUtil.createFillHorizontallyGridData());
	    ok.setEnabled(GUIs[tabFolder.getSelectionIndex()].isValid());
	    
	    Button cancel = new Button(buttons, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(SWTUtil.createFillHorizontallyGridData());
	    
	    tabFolder.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
	          System.out.println(tabFolder.getSelection()[0].getText() + " selected");
	          ok.setEnabled(GUIs[tabFolder.getSelectionIndex()].isValid());
	        }
	      });
	    
	    for (final ConfigurationComponent GUI : GUIs){
	    	GUI.addModifyListener(new ModifyListener(){
				public void modifyText(ModifyEvent arg0) {
					ok.setEnabled(GUI.isValid());
				}
	    	});
	    	GUI.addSelectionListener(new SelectionAdapter(){
	    		public void widgetSelected(SelectionEvent event) {
					ok.setEnabled(GUI.isValid());				
				}
	    	});
	    }
	    
	    
	    ok.addSelectionListener(new SelectionAdapter(){
	    	public void widgetSelected(SelectionEvent event) {
	    		
	    		switch(tabFolder.getSelectionIndex()){
	    		case 0:
	    			GenerateRandomDateMasker masker0 = ((GenerateRandomDateMaskerGUI) GUIs[0]).getMasker();
	    			GenerateRandomDateDialog dlg0 = new GenerateRandomDateDialog(shell, masker0);
	    			dlg0.open();
	    			break;
	    		case 1:
	    			GenerateRandomStringMasker masker1 = ((GenerateRandomStringMaskerGUI) GUIs[1]).getMasker();
	    			GenerateRandomStringDialog dlg1 = new GenerateRandomStringDialog(shell, masker1);
		    		dlg1.open();
	    			break;
	    		case 2:
	    			RandomShiftDecimalMasker masker2 = ((RandomShiftDecimalMaskerGUI) GUIs[2]).getMasker();
	    			RandomShiftDecimalDialog dlg2 = new RandomShiftDecimalDialog(shell, masker2);
		    		dlg2.open();
	    			break;
	    		case 3:
	    			MatchAndReplaceStringMasker masker3 = ((MatchAndReplaceStringMaskerGUI) GUIs[3]).getMasker();
	    			MatchAndReplaceStringDialog dlg3 = new MatchAndReplaceStringDialog(shell, masker3);
		    		dlg3.open();
	    			break;
	    		}
	    	}
	    });
	    
	    cancel.addSelectionListener(new SelectionAdapter(){
	    	public void widgetSelected(SelectionEvent event) {
	    		display.close();
	    	}
	    });
	    
		shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	}
	
}
