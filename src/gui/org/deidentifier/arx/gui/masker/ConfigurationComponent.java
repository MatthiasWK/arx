import org.eclipse.swt.widgets.*;

public interface ConfigurationComponent {
	
	public boolean isValid();

	public Composite getCmpRoot();

	public void setCmpRoot(Composite cmpRoot);
	
}
