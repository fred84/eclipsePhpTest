package org.fred.plugin.php.test.views;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
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

	private FieldEditor bootstrap;
	
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		bootstrap = new FileFieldEditor("bootstrap", "bootstrap", true, parent);
		bootstrap.setPage(this);
		bootstrap.setPropertyChangeListener(this);
		bootstrap.setPreferenceStore(getPreferenceStore());
		bootstrap.load();
		
		bootstrap.isValid();
		
		return composite;
	}
	
	public boolean performOk() {
		try {
			((IResource) getElement()).setPersistentProperty(
					new QualifiedName("", BOOTSTRAP), 
					"123"
			);
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


}