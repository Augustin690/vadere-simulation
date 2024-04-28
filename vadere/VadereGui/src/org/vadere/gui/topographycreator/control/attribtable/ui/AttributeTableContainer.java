package org.vadere.gui.topographycreator.control.attribtable.ui;

import org.vadere.gui.components.view.ISelectScenarioElementListener;
import org.vadere.gui.topographycreator.control.attribtable.ViewListener;
import org.vadere.gui.topographycreator.control.attribtable.tree.TreeException;
import org.vadere.gui.topographycreator.control.attribtable.tree.TreeModelCache;
import org.vadere.gui.topographycreator.model.TopographyCreatorModel;
import org.vadere.simulator.context.VadereContext;
import org.vadere.state.attributes.Attributes;
import org.vadere.state.attributes.AttributesScenarioElement;

import org.vadere.state.scenario.ScenarioElement;
import org.vadere.util.observer.NotifyContext;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static org.vadere.gui.topographycreator.control.attribtable.util.Layouts.initGridBagConstraint;

/**
 * AttributeTableContainer is the root element for the attribute table component stack. It serves as teh interface between the
 * TopographyCreatorModel and the AttributeTree nodes. When instantiated it will scan the project directory 'org.vadere' to
 * find any class subclassing AttributesScenarioElement and make the AttributeTableView precreate a Model/Page for each class.
 */
public class AttributeTableContainer extends JPanel implements ISelectScenarioElementListener, Observer, ViewListener {
    AttributeTableView attrView;
    private final NotifyContext ctx = new NotifyContext(this.getClass());
    ScenarioElement selectedElement;
    TopographyCreatorModel panelModel;

    /**
     * Initializes the AttributeTableView and Help panel.
     * Creates a AttributeTablePage for each Class subclassing AttributesScenarioElement
     * Adds itself as a listener and observer to the TopographyCreatorModel
     * @param defaultModel
     */
    public AttributeTableContainer(final TopographyCreatorModel defaultModel) {
        super(new GridBagLayout());
        attrView = new AttributeTableView(this);

        this.panelModel = defaultModel;

        var gbcPage = initGridBagConstraint(1.0);

        this.add(new JScrollPane(attrView), gbcPage);

        var cache  = (TreeModelCache) VadereContext.getCtx("GUI").get(VadereContext.TREE_NODE_CTX);
        for(var clazz : cache.getSubTypeOff(AttributesScenarioElement.class)){
            attrView.buildPageFor(clazz);
        }

        defaultModel.addSelectScenarioElementListener(this);
        defaultModel.addObserver(this);
    }

    /**
     * This method implements the necessary method of the ISelectScenarioElementListener interface. It will be called when the user
     * selects a new ScenarioElement in the Editor and will delegate hiding/changing the current Page to the AttributeTableView
     * @param scenarioElement
     */
    @Override
    public void selectionChange(ScenarioElement scenarioElement) {
        this.selectedElement = scenarioElement;
        if (isNoElementSelected(scenarioElement))
            attrView.clear();
        if (isAnElementSelected(scenarioElement)) {
            var attributes = scenarioElement.getAttributes();
            attrView.selectionChange(attributes);
        }
    }

    /**
     * This method implements the necessary method of the Observer interface. It will be called everytime something changes in the editor.
     * (which is probably very inefficient e.g. this package does not need to update on mouse movement in the viewport). It delegates all
     * calls tho the AttributeTableView.
     */
    @Override
    public void update(Observable o, Object arg) {
        /*
         * AttributeTableContainer is an Observer of the TopographyCreatorModel and also notifies its Observers.
         * Since we don't want to act on an update notified by ourselves we use the NotifyContext class to check who
         * initiated the call to notifyObservers()
         */
        if (arg instanceof NotifyContext) {
            var ctx = (NotifyContext) arg;
            if (AttributeTableContainer.class.isAssignableFrom(ctx.getNotifyContext())) {
                return;
            }
        }

        try {
            if (panelModel.getSelectedElement() != null)
                attrView.updateModel(panelModel.getSelectedElement().getAttributes());
        } catch (TreeException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is needed for the AttributTableView so that the idea of AttributeTableContainer
     * being the interface between the TopographyCreatorModel and the AttributeTree model still holds.
     * TreeAdapter which is a subclass of TreeNode which is used as a root node and stores a reference to an Object
     * implementing this method via the Revalidatable interface will call this method once updateParentsFieldValue
     * was called on it from a child node.
     */
    @Override
    public void viewChanged(Object object) {
        var element = panelModel.getSelectedElement();
        element.setAttributes((Attributes) object);
        panelModel.setElementHasChanged(element);
        panelModel.notifyObservers(ctx);
    }


    //// utility methods for prettier code
    private static boolean isAnElementSelected(ScenarioElement scenarioElement) {
        return scenarioElement != null;
    }

    private static boolean isNoElementSelected(ScenarioElement scenarioElement) {
        return scenarioElement == null;
    }
}
