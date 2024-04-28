package org.vadere.gui.topographycreator.control;

import org.vadere.gui.components.control.IMode;
import org.vadere.gui.components.utils.ResourceStrings;
import org.vadere.gui.components.utils.Resources;
import org.vadere.gui.topographycreator.model.IDrawPanelModel;
import org.vadere.util.config.VadereConfig;

import javax.swing.undo.UndoableEditSupport;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Action: Selects the SelectShapeMode, so after this action the user can select ScenarioElements.
 * 
 *
 */
public class ActionSelectSelectShape extends TopographyAction {

	private static final long serialVersionUID = 7909552006335330920L;
	private final IMode mode;

	private static final Resources RESOURCE = Resources.getInstance("global");

	private static final int ICON_SIZE = (int)(1.5*VadereConfig.getConfig().getInt("ProjectView.icon.height.value")*VadereConfig.getConfig().getFloat("Gui.scale"));
/*
	public ActionSelectSelectShape(final String name, String iconPath, String shortDescription, final IDrawPanelModel panelModel,
                                   final UndoableEditSupport undoSupport) {
		this(name, iconPath,shortDescription,panelModel,  undoSupport);
	}
*/
	public ActionSelectSelectShape(final IDrawPanelModel panelModel,
			final UndoableEditSupport undoSupport) {
		super("select shape mode", RESOURCE.getIconSVG(ResourceStrings.ICONS_SELECT_SHAPES_ICON_PNG,ICON_SIZE,ICON_SIZE,Color.lightGray), ResourceStrings.SELECT_SHAPE_TOOLTIP,panelModel);
		mode = new SelectElementMode(panelModel, undoSupport);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getScenarioPanelModel().setMouseSelectionMode(mode);
		getScenarioPanelModel().setCursorColor(Color.MAGENTA);
		getScenarioPanelModel().notifyObservers();
	}
}
