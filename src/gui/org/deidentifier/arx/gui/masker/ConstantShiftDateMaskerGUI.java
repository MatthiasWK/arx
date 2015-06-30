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

public class ConstantShiftDateMaskerGUI extends MaskerTool implements MaskerGUI{
	
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setMinimumSize(new Point(200, 150));
		shell.setSize(204, 150);
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
		shell.setLayout (layout);
		shell.setText("ConstantShiftDateMasker");

		Composite c1 = new Composite(shell, SWT.BORDER);	
		c1.setLayout (new GridLayout (4, false));
		
		Label label1 = new Label(c1, SWT.NULL);
		Label years = new Label(c1, SWT.NULL);
		years.setText("years");
		Label months = new Label(c1, SWT.NULL);
		months.setText("months");
		Label days = new Label(c1, SWT.NULL);
		days.setText("days");
		
		// Shift Period
		final Button p = new Button (c1, SWT.RADIO);
		p.setText ("Shift Period:");
		
        final Spinner[] pInput = new Spinner[3];
   
        for (int i=0; i<3 ; i++){
        	      pInput[i] = new Spinner (c1, SWT.BORDER);
        	      pInput[i].setEnabled(false);
        }

        // Shift Distance
		final Button d = new Button (c1, SWT.RADIO);
		d.setText ("Shift Distance:");
		
		Label label2 = new Label(c1, SWT.NULL);
		Label label3 = new Label(c1, SWT.NULL);
		
		final Spinner dInput = new Spinner (c1, SWT.BORDER);
		dInput.setEnabled(false);
		
		p.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				for (int i=0; i<3 ; i++){pInput[i].setEnabled(true);}
				dInput.setEnabled(false);
			}
		}
		);
			
		d.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent event) {
        		dInput.setEnabled(true);
        		for (int i=0; i<3 ; i++){pInput[i].setEnabled(false);}
        	}
        }
        );
		
		Composite c2 = new Composite(shell, SWT.NONE);
		c2.setLayout(new GridLayout (2, false));	
		
		Button cancel = new Button(c2, SWT.PUSH);
		cancel.setText("cancel");
		cancel.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				
			    }
			}
		);

		Button next = new Button(c2, SWT.PUSH);
		next.setText("next >");
		next.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				
				Date myDate = new Date(2006, 5, 1);
				
				if (d.getSelection()==true){
						
					int shiftDistance = dInput.getSelection()*86400000; //convert days to milliseconds					
					ConstantShiftDateMasker masker = new ConstantShiftDateMasker(shiftDistance);
					myDate=masker.mask(myDate);

				}
				else {
					
					int year = pInput[0].getSelection();
					int month = pInput[1].getSelection();
					int day = pInput[2].getSelection();
					ReadablePeriod shiftPeriod= new Period(year, month, day, 0);					
					ConstantShiftDateMasker masker = new ConstantShiftDateMasker(shiftPeriod);
					myDate=masker.mask(myDate);
				}
				
				System.out.println(myDate.getYear());
		        System.out.println(myDate.getMonth());
		        System.out.println(myDate.getDay());
				
			}
		}
				);
		
		shell.pack ();
		shell.open ();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}

	@Override
	public Composite loadMasker() {
		Shell shell = new Shell ();
		
		final Composite mainComposite = new Composite(shell, SWT.NONE);
      	mainComposite.setLayout(new GridLayout (4, false));
      	
      	Label label1 = new Label(mainComposite, SWT.NULL);
		Label years = new Label(mainComposite, SWT.NULL);
		years.setText("years");
		Label months = new Label(mainComposite, SWT.NULL);
		months.setText("months");
		Label days = new Label(mainComposite, SWT.NULL);
		days.setText("days");
		
		// Shift Period
		final Button p = new Button (mainComposite, SWT.RADIO);
		p.setText ("Shift Period:");
		
        final Spinner[] pInput = new Spinner[3];
   
        for (int i=0; i<3 ; i++){
        	      pInput[i] = new Spinner (mainComposite, SWT.BORDER);
        	      pInput[i].setEnabled(false);
        }

        // Shift Distance
		final Button d = new Button (mainComposite, SWT.RADIO);
		d.setText ("Shift Distance:");
		
		Label label2 = new Label(mainComposite, SWT.NULL);
		Label label3 = new Label(mainComposite, SWT.NULL);
		
		final Spinner dInput = new Spinner (mainComposite, SWT.BORDER);
		dInput.setEnabled(false);
		
		p.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				for (int i=0; i<3 ; i++){pInput[i].setEnabled(true);}
				dInput.setEnabled(false);
			}
		}
		);
			
		d.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent event) {
        		dInput.setEnabled(true);
        		for (int i=0; i<3 ; i++){pInput[i].setEnabled(false);}
        	}
        }
        );
		
		return mainComposite;
	}
}


