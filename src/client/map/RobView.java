package client.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.base.OverlayView;
import client.data.RobPlayerInfo;
import shared.definitions.CatanColor;

/**
 * Implementation for the rob view, which lets the user select a player to rob
 */
@SuppressWarnings({ "serial", "unused" })
public class RobView extends OverlayView implements IRobView {

	private final int LABEL_TEXT_SIZE = 40;
	private final int BORDER_WIDTH = 10;
	private final Dimension ITEM_SIZE = new Dimension(80, 100);
	private final int TOP_SIZE = 110;
	private final int BOTTOM_SIZE = 130;

	private JLabel label;
	private JButton defaultButton = null;
	private JPanel buttonPanel;
	RobPlayerInfo[] victims;
	private ArrayList<JButton> victimButtons;

	public RobView() {

		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		this.setPreferredSize(new Dimension(400, 250));

		label = new JLabel("Choose who to Rob");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		this.add(label, BorderLayout.NORTH);

		buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

		defaultButton = new JButton("<html>" + "<body style=\"text-align:center\">" + "<p style=\"font-size:" + TOP_SIZE + "%\">None</p>" + "<p></p>" + "<p style=\"font-size:" + BOTTOM_SIZE + "%\">OK</p>" + "</body>" + "</html>");
		defaultButton.addActionListener(actionListener);
		defaultButton.setPreferredSize(ITEM_SIZE);
		defaultButton.setContentAreaFilled(false);
		defaultButton.setOpaque(true);
		defaultButton.setAlignmentY(0);
		defaultButton.setBackground(Color.LIGHT_GRAY);

		buttonPanel.add(defaultButton);

		this.add(buttonPanel, BorderLayout.CENTER);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == defaultButton) {
				getController().robPlayer();
				closeModal();
			} else {

				for (int i = 0; i < victimButtons.size(); i++) {
					if (e.getSource() == victimButtons.get(i)) {
						closeModal();
						getController().robPlayer(victims[i]);
					}
				}

			}
		}
	};

	@Override
	public IMapController getController() {

		return (IMapController) super.getController();
	}

	@Override
	public void setPlayers(RobPlayerInfo[] candidateVictims) {
		victims = candidateVictims;

		int numberOfPlayers = 0;
		if (candidateVictims != null)
			numberOfPlayers = candidateVictims.length;

		if (numberOfPlayers != 0) {
			this.remove(buttonPanel);
			buttonPanel = new JPanel();
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

			victimButtons = new ArrayList<JButton>();

			for (int i = 0; i < numberOfPlayers; i++) {
				JButton victimButton = new JButton("<html>" + "<body style=\"text-align:center\">" + "<p style=\"font-size:" + TOP_SIZE + "%\">" + victims[i].getName() + "</p>" + "<p></p>" + "<p style=\"font-size:" + BOTTOM_SIZE + "%\">" + victims[i].getNumCards() + "</p>" + "</body>" + "</html>");
				victimButton.addActionListener(actionListener);
				victimButton.setPreferredSize(ITEM_SIZE);
				victimButton.setContentAreaFilled(false);
				victimButton.setOpaque(true);
				victimButton.setAlignmentY(0);
				victimButton.setBackground(CatanColor.toColor(victims[i].getColor()).getJavaColor());

				buttonPanel.add(victimButton);
				victimButtons.add(victimButton);
				this.add(buttonPanel, BorderLayout.CENTER);
				revalidate();
			}
		}
	}

}
