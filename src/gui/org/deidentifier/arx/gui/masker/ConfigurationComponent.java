import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;


public interface ConfigurationComponent {
	
	public boolean isValid();
	
	public void addModifyListener(ModifyListener listener);
	
	public void addSelectionListener (SelectionAdapter adapter);

}
