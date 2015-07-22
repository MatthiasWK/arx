import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Point;
import org.joda.time.ReadablePeriod;
import org.joda.time.Period;

import java.util.Date;

import org.deidentifier.arx.masking.ConstantShiftDateMasker;

public class ConstantShiftDateMaskerGUI implements ConfigurationComponent{
	
	private Label lbl;
	
	private Button btnPeriod;
	private Button btnDistance;
	
	private Spinner[] spnPeriodInput;
	private Spinner spnDistanceInput;

	private Composite cmpRoot;
	
	public ConstantShiftDateMaskerGUI(Composite root) {
		
		this.cmpRoot = new Composite(root, SWT.NONE);
      	this.cmpRoot.setLayout(new GridLayout (4, false));
      	
      	new Label(this.cmpRoot, SWT.NULL);
      	
      	this.lbl = new Label(this.cmpRoot, SWT.NULL);
      	this.lbl.setText("years");
      	this.lbl = new Label(this.cmpRoot, SWT.NULL);
      	this.lbl.setText("months");
		this.lbl = new Label(this.cmpRoot, SWT.NULL);
		this.lbl.setText("days");
		
		// Shift Period
		this.btnPeriod = new Button (this.cmpRoot, SWT.RADIO);
		this.btnPeriod.setText ("Shift Period:");
		
        this.spnPeriodInput = new Spinner[3];
   
        for (int i=0; i<3 ; i++){
        	this.spnPeriodInput[i] = new Spinner (this.cmpRoot, SWT.BORDER);
        	this.spnPeriodInput[i].setEnabled(false);
        }

        // Shift Distance
	    this.btnDistance = new Button (this.cmpRoot, SWT.RADIO);
	    this.btnDistance.setText ("Shift Distance:");
		
	    new Label(this.cmpRoot, SWT.NULL);
	    new Label(this.cmpRoot, SWT.NULL);
		
		this.spnDistanceInput = new Spinner (this.cmpRoot, SWT.BORDER);
		this.spnDistanceInput.setEnabled(false);
		
		this.btnPeriod.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				for (int i=0; i<3 ; i++){spnPeriodInput[i].setEnabled(true);}
				spnDistanceInput.setEnabled(false);
			}
		}
		);
			
		this.btnDistance.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent event) {
        		spnDistanceInput.setEnabled(true);
        		for (int i=0; i<3 ; i++){spnPeriodInput[i].setEnabled(false);}
        	}
        }
        );
		
	}


	@Override
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


