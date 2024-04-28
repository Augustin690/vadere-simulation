package org.vadere.gui.topographycreator.control.attribtable;

import org.vadere.gui.components.control.HelpTextView;
import org.vadere.gui.components.utils.Resources;
import org.vadere.gui.projectview.view.VDialogManager;
import org.vadere.util.other.Strings;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * JCollapsablePanel implements a panel with a header which hides its content
 * when the header is clicked upon.
 */
public class JCollapsablePanel extends JPanel implements Observer {
    /**
     * contentPanel is used as a container for all added components
     */
    private final JPanel contentPanel;
    /**
     * head is used as the header displayed at the top
     */
    private final JLabel headerLabel;
    private final JPanel headerPanel;

    private final JButton helpButton;
    private GridBagConstraints gbc;


    /**
     * hidden is storing the visibility state of the contentPanel container
     */
    private boolean hidden = false;

    private final Observable observable;

    public JCollapsablePanel(Class<?> refClass) {
        super(new GridBagLayout());
        this.observable = new Observable();
        this.contentPanel = new JPanel(new GridBagLayout());
        this.headerPanel = new JPanel();
        this.headerLabel = new JLabel(Strings.generateHeaderName(refClass));
        this.helpButton = new JButton();

        //add headerLabel into headerPanel anchored left
        this.headerPanel.setLayout(new BorderLayout());
        this.headerPanel.add(this.headerLabel, BorderLayout.WEST);
        this.headerPanel.add(this.helpButton, BorderLayout.EAST);
        initializeGridBagConstraint();

        initializeHeaderPanelStyle(headerPanel);
        initializeHeaderLabelStyle(headerLabel);
        initializeHelpButtonStyle(helpButton,refClass);

        headerPanel.addMouseListener(new SectionHeaderMouseInputAdapter(contentPanel,helpButton));
        helpButton.addMouseListener(new HelpButtonMouseInputAdapter(helpButton,refClass));

        this.add(this.headerPanel,gbc);
        this.add(this.contentPanel,gbc);
    }

    private static void initializeHeaderPanelStyle(JPanel headerPanel) {
        var margin = new EmptyBorder(4,4,4,4);
        var boderColor = UIManager.getColor("Component.borderColor");
        //
        MatteBorder lineBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, boderColor);
        headerPanel.setBorder(new CompoundBorder(lineBorder ,margin));
        headerPanel.setBackground(UIManager.getColor("Menu.background"));
    }
    private static void initializeHelpButtonStyle(JButton helpButton,Class<?> refClass) {
        helpButton.setIcon(Resources.getInstance("global").getIcon("help.png", 12, 12));
        helpButton.setBorderPainted(false);
        if(HelpTextView.exists(refClass.getName())) {
            helpButton.setVisible(true);
        }
    }

    private static void initializeHeaderLabelStyle(JLabel headerLabel) {
        headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));
    }
    private void initializeGroupHeaderStyle(JLabel head) {
        var c= UIManager.getColor("Table.selectionBackground");
        setBackground(new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)(c.getAlpha()*0.5)));
        head.setBorder(new EmptyBorder(4,4,4,4));
        head.setIcon(UIManager.getIcon("Tree.expandedIcon"));
    }

    private void initializeGridBagConstraint() {
        this.gbc = new GridBagConstraints();
        this.gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.weightx = 1;
    }

    /**
     * This method is used for adding components directly with the wanted
     * layout contraints
     * @param comp   the component to be added
     * @return
     */
    public Component add(Component comp) {
        comp.setVisible(!hidden);
        if(comp instanceof Observer){
            this.observable.addObserver((Observer) comp);
        }
        this.contentPanel.add(comp,gbc);
        return comp;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.observable.notifyObservers(arg);
    }

    /**
     * SectionHeaderMouseInputAdapter is used as a listener
     * for mouse input to hande the state switching of the
     * contents visibility if the panel is section panel
     */
    private class SectionHeaderMouseInputAdapter extends MouseInputAdapter {

        private final JPanel contentPanel;

        private final JButton headerButton;

        private SectionHeaderMouseInputAdapter(JPanel contentPanel,JButton headerButton) {

            this.contentPanel = contentPanel;
            this.headerButton = headerButton;
            headerLabel.setIcon(UIManager.getIcon("Tree.expandedIcon"));
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if (hidden) {
                contentPanel.setVisible(true);
                hidden = false;
                headerLabel.setIcon(UIManager.getIcon("Tree.expandedIcon"));

            } else {
                contentPanel.setVisible(false);
                hidden = true;
                headerLabel.setIcon(UIManager.getIcon("Tree.collapsedIcon"));
            }
            getParent().invalidate();
        }
    }

    private class HelpButtonMouseInputAdapter extends MouseInputAdapter {

        private final JButton headerButton;
        private final Class<?> refClass;

        private HelpButtonMouseInputAdapter(JButton headerButton, Class<?> refClass) {
            this.headerButton = headerButton;
            this.refClass = refClass;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            VDialogManager.showHelpDialogForClass(refClass);
            getParent().invalidate();
        }
    }

}
