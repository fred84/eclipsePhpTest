package org.fred.plugin.php.test.views;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.fred.plugin.php.test.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("PHPUnit preferences");
	}

	public void createFieldEditors() {
		addField(new FileFieldEditor(
				"executable", 
				"PHPUnitPath", 
				true, 
				getFieldEditorParent()
				) 
		);
	}

	public void init(IWorkbench workbench) {}	
}