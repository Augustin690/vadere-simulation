package org.vadere.gui.topographycreator.control;

import org.vadere.gui.components.utils.ResourceStrings;
import org.vadere.gui.components.utils.Resources;
import org.vadere.gui.topographycreator.model.IDrawPanelModel;
import org.vadere.util.config.VadereConfig;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Action: Reset the size of the scenario to the default value.
 * 
 * 
 */
public class ActionZoomFit extends TopographyAction {

	private static final long serialVersionUID = 3142209351101181343L;
	private static final Resources RESOURCE = Resources.getInstance("global");
	private static final int ICON_SIZE = (int)(1.5*VadereConfig.getConfig().getInt("ProjectView.icon.height.value")*VadereConfig.getConfig().getFloat("Gui.scale"));


	public ActionZoomFit(final IDrawPanelModel panelModel) {
		super("zoom fit", RESOURCE.getIconSVG("zoom_fit", ICON_SIZE,ICON_SIZE, Color.lightGray), ResourceStrings.TOPOGRAPHY_CREATOR_BTN_MAXIMIZE_TOPOGRAPHY_TOOLTIP, panelModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getScenarioPanelModel().resetTopographySize();
		getScenarioPanelModel().notifyObservers();
	}
}
