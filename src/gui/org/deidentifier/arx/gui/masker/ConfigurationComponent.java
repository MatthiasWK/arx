import org.eclipse.swt.events.ModifyListener;


public interface ConfigurationComponent {
	
	public boolean isValid();
	
	public void addModifyListener(ModifyListener listener);

}
