package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.PHPUnitPlugin;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(PHPUnitPlugin.getDefault().getPreferenceStore());
		setDescription("PHPUnit preferences");
	}

	public void createFieldEditors() {
		addField(new FileFieldEditor(
				"executable", 
				"PHPUnit path", 
				true, 
				getFieldEditorParent()
				) 
		);
	}

	public void init(IWorkbench workbench) {}	
}