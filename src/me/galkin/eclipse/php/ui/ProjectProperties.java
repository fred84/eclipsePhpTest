package me.galkin.eclipse.php.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class ProjectProperties extends PropertyPage implements IPropertyChangeListener {

	private static final String BOOTSTRAP = "BOOTSTRAP";

	private QualifiedName fieldName = new QualifiedName(BOOTSTRAP, BOOTSTRAP); 
	
	private FileFieldEditor bootstrap;
	
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		createBootstrapField(parent);
		
		return composite;
	}
	
	public boolean performOk() {
		try {
			getProject().setPersistentProperty(fieldName, bootstrap.getStringValue());
		} catch (CoreException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(FieldEditor.IS_VALID)) {
            setValid(bootstrap.isValid());
        }
	}

	private IProject getProject() {
		return ((IScriptProject)getElement()).getProject();
	}
	
	private String getBootstrapValue() {
		String result;
		
		try {
			result = getProject().getPersistentProperty(fieldName);
		} catch (CoreException e) {
			return "";
		}

		if (null == result) {
			return "";
		}
		
		return result;
	}
	
	private void createBootstrapField(Composite parent) {
		bootstrap = new FileFieldEditor("bootstrap", "bootstrap", false, createDefaultComposite(parent));
		bootstrap.setPage(this);
		
		bootstrap.setPropertyChangeListener(this);
		bootstrap.setStringValue(getBootstrapValue());
		
		setValid(bootstrap.isValid());
	}
	
	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}
}