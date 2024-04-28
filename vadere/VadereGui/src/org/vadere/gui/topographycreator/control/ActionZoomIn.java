package org.vadere.gui.topographycreator.control;

import org.vadere.gui.components.control.IMode;
import org.vadere.gui.components.utils.ResourceStrings;
import org.vadere.gui.components.utils.Resources;
import org.vadere.gui.topographycreator.model.IDrawPanelModel;
import org.vadere.util.config.VadereConfig;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Action: Sets the selection mode to ZoomInMode, so the user can zoom in if he hit the mouse
 * button.
 * 
 * 
 */
public class ActionZoomIn extends TopographyAction {

	private static final long serialVersionUID = 6346468270486683058L;
	private static final Resources RESOURCE = Resources.getInstance("global");
	private final IMode mode;

	private static final int ICON_SIZE = (int)(1.5*VadereConfig.getConfig().getInt("ProjectView.icon.height.value")*VadereConfig.getConfig().getFloat("Gui.scale"));

	public ActionZoomIn(final IDrawPanelModel panelModel) {
		super("zoom in", RESOURCE.getIconSVG("zoom_in", ICON_SIZE,ICON_SIZE, Color.lightGray), ResourceStrings.TOPOGRAPHY_CREATOR_BTN_ZOOM_IN_TOOLTIP, panelModel);
		mode = new ZoomInMode(panelModel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		getScenarioPanelModel().setMouseSelectionMode(mode);
		getScenarioPanelModel().notifyObservers();
	}

}
