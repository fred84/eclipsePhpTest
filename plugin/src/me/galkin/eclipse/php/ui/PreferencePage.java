package me.galkin.eclipse.php.ui;

import me.galkin.eclipse.php.PHPUnitPlugin;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private FileFieldEditor field;
	
	public PreferencePage() {
		super(GRID);
		setPreferenceStore(PHPUnitPlugin.getDefault().getPreferenceStore());
		setDescription("PHPUnit");
	}

	public void createFieldEditors() {
		field = new FileFieldEditor(
				PHPUnitPlugin.EXECUTABLE, 
				"PHPUnit path", 
				true, 
				getFieldEditorParent()
		);
		
		field.setStringValue(
				PHPUnitPlugin.getDefault().getPreferenceStore().getString(PHPUnitPlugin.EXECUTABLE)
		);
		
		addField(field);
	}

	public void init(IWorkbench workbench) {}	
}