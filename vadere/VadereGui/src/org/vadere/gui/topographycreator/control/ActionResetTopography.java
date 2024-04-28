package org.vadere.gui.topographycreator.control;

import org.vadere.gui.components.utils.ResourceStrings;
import org.vadere.gui.components.utils.Resources;
import org.vadere.gui.topographycreator.model.IDrawPanelModel;
import org.vadere.state.scenario.Topography;

import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;
import java.awt.event.ActionEvent;

/**
 * Action: Resets the Topography. This means all ScenarioElements will be removed.
 * 
 * 
 */
public class ActionResetTopography extends TopographyAction {
	private static final Resources resources = Resources.getInstance("topographycreator");
	private static final long serialVersionUID = -5557013510457451231L;

	private final UndoableEditSupport undoSupport;

	public ActionResetTopography(IDrawPanelModel panelModel,
			UndoableEditSupport undoSupport) {
		super("reset scenario", ResourceStrings.ICONS_TOPOGRAPHY_RESET, ResourceStrings.TOPOGRAPHY_CREATOR_BTN_NEW_TOPOGRAPHY_TOOLTIP, panelModel);
		this.undoSupport = undoSupport;
	}

	public ActionResetTopography(String name, IDrawPanelModel panelModel, UndoableEditSupport undoSupport) {
		super(name, panelModel);
		this.undoSupport = undoSupport;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Topography beforeTopography = getScenarioPanelModel().build();
		getScenarioPanelModel().resetScenario();
		UndoableEdit edit = new EditResetScenario(getScenarioPanelModel(), beforeTopography);
		undoSupport.postEdit(edit);
		resources.removeProperty("last_save_point");
		getScenarioPanelModel().notifyObservers();
	}
}
