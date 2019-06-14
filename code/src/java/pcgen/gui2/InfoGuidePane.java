/*
 * Copyright 2011 Connor Petty <cpmeister@users.sourceforge.net>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package pcgen.gui2;

import java.util.Objects;

import javax.swing.plaf.UIResource;

import pcgen.cdom.base.Constants;
import pcgen.core.Campaign;
import pcgen.facade.core.SourceSelectionFacade;
import pcgen.gui2.tools.Icons;
import pcgen.gui2.tools.TipOfTheDayHandler;
import pcgen.gui2.util.HtmlInfoBuilder;
import pcgen.gui3.GuiAssertions;
import pcgen.system.LanguageBundle;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

/**
 * This class provides a guide for first time 
 * users on what to do next and what sources are loaded.
 * Note: this class extends UIResource so that the component can be added
 * as a child of a JTabbedPane without it becoming a tab
 */
class InfoGuidePane extends Pane implements UIResource
{

	/**
	 * The context indicating what items are currently loaded/being processed in the UI
	 */
	private final UIContext uiContext;
	private final PCGenFrame frame;
	private final WebView gameModeLabel;
	private final WebView campaignList;
	private final WebView tipPane;

	InfoGuidePane(PCGenFrame frame, UIContext uiContext)
	{
		GuiAssertions.assertIsJavaFXThread();
		this.uiContext = Objects.requireNonNull(uiContext);
		this.frame = frame;
		this.gameModeLabel = new WebView();
		this.campaignList = new WebView();
		this.tipPane = new WebView();
		TipOfTheDayHandler.getInstance().loadTips();
		initComponents();
		initListeners();

	}

	private void initComponents()
	{
		GuiAssertions.assertIsJavaFXThread();
		BorderPane mainPanel = new BorderPane();
		VBox sourcesPanel = new VBox();
		sourcesPanel.setPrefSize(650, 250);


		sourcesPanel.getChildren().add(new Text(LanguageBundle.getString("in_si_intro")));
		sourcesPanel.getChildren().add(new Text(LanguageBundle.getString("in_si_gamemode")));
		sourcesPanel.getChildren().add(gameModeLabel);
		sourcesPanel.getChildren().add(new Text(LanguageBundle.getString("in_si_sources")));
		sourcesPanel.getChildren().add(campaignList);

		WebView guidePane = new WebView();
		guidePane.getEngine().loadContent(LanguageBundle.getFormattedString(
						"in_si_whatnext",
						Icons.New16.getImageIcon(),
						Icons.Open16.getImageIcon()
				));
		mainPanel.getChildren().add(sourcesPanel);
		mainPanel.getChildren().add(guidePane);
		mainPanel.getChildren().add(tipPane);

		refreshDisplayedSources(null);

		getChildren().add(mainPanel);

		tipPane.getEngine()
		       .loadContent(LanguageBundle.getFormattedString(
				       "in_si_tip",
				       TipOfTheDayHandler.getInstance().getNextTip()
		       ));
	}

	private void initListeners()
	{
		frame.getSelectedCharacterRef().addReferenceListener(e -> {
			if (e.getNewReference() == null)
			{
				this.setVisible(true);
				tipPane.getEngine().loadContent(
						LanguageBundle.getFormattedString(
								"in_si_tip",
								TipOfTheDayHandler.getInstance().getNextTip()
						));
			}
			else
			{
				this.setVisible(false);
			}
		});

		uiContext.getCurrentSourceSelectionRef()
			.addReferenceListener(e -> refreshDisplayedSources(e.getNewReference()));
	}

	private void refreshDisplayedSources(SourceSelectionFacade sources)
	{
		GuiAssertions.assertIsJavaFXThread();
		if (sources == null)
		{
			gameModeLabel.getEngine().loadContent(Constants.WRAPPED_NONE_SELECTED);
		}
		else
		{
			gameModeLabel.getEngine().loadContent(sources.getGameMode().get().getDisplayName());
		}
		if (sources == null || sources.getCampaigns().isEmpty())
		{
			campaignList.getEngine().loadContent(LanguageBundle.getString("in_si_nosources"));
		}
		else
		{
			HtmlInfoBuilder builder = new HtmlInfoBuilder();
			for (Campaign campaign : sources.getCampaigns())
			{
				builder.append(campaign.getKeyName()).appendLineBreak();
			}
			campaignList.getEngine().loadContent(builder.toString());
		}
	}

}
