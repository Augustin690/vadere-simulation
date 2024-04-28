package org.vadere.gui.topographycreator.control;

import org.vadere.gui.components.control.IMode;
import org.vadere.gui.components.utils.ResourceStrings;
import org.vadere.gui.components.utils.Resources;
import org.vadere.gui.topographycreator.model.IDrawPanelModel;
import org.vadere.util.config.VadereConfig;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Action: Sets the selection mode to ZoomOutMode, so the user can zoom out if he hit the mouse
 * button.
 * 
 * 
 */
public class ActionZoomOut extends TopographyAction {

	private static final long serialVersionUID = 804732305457511954L;
	private static final Resources RESOURCE = Resources.getInstance("global");
	private final IMode mode;
	private static final int ICON_SIZE = (int)(1.5*VadereConfig.getConfig().getInt("ProjectView.icon.height.value")*VadereConfig.getConfig().getFloat("Gui.scale"));

	public ActionZoomOut(final IDrawPanelModel panelModel) {
		super("zoom out", RESOURCE.getIconSVG("zoom_out", ICON_SIZE,ICON_SIZE, Color.lightGray), ResourceStrings.TOPOGRAPHY_CREATOR_BTN_ZOOM_OUT_TOOLTIP, panelModel);
		mode = new ZoomOutMode(panelModel);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		getScenarioPanelModel().setMouseSelectionMode(mode);
		getScenarioPanelModel().notifyObservers();
	}

}
