package org.vadere.gui.topographycreator.view;

import org.vadere.gui.projectview.view.VDialogManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class JLinkLabel extends JLabel {

	private String shortName;

	public JLinkLabel(String fullName){
		this(fullName, "", "");
	}

	public JLinkLabel(String fullName, String prefix,  String suffix){
		super();
		String[] tmp = fullName.split("\\.");
		this.shortName = tmp[tmp.length-1];
		setText(prefix + shortName + suffix);
		setForeground(Color.BLUE.darker());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VDialogManager.showHelpDialogForClass(fullName);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
			}
		});

	}
}
