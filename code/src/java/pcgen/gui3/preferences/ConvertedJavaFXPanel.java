/*
 * Copyright 2019 (C) Eitan Adler <lists@eitanadler.com>
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
 */

package pcgen.gui3.preferences;

import java.io.IOException;
import java.net.URL;

import pcgen.gui2.prefs.PCGenPrefsPanel;
import pcgen.gui3.GuiAssertions;
import pcgen.gui3.GuiUtility;
import pcgen.gui3.ResettableController;
import pcgen.system.LanguageBundle;
import pcgen.util.Logging;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;

public final class ConvertedJavaFXPanel<T extends ResettableController> extends PCGenPrefsPanel
{
	private final String titleTextKey;
	private final FXMLLoader fxmlLoader = new FXMLLoader();


	public ConvertedJavaFXPanel(Class<T> klass, String resourceName, String titleTextKey)
	{
		GuiAssertions.assertIsNotJavaFXThread();
		this.titleTextKey = titleTextKey;
		URL resource = klass.getResource(resourceName);
		Logging.debugPrint(String.format("location for %s (%s) is %s", resourceName, klass, resource));

		fxmlLoader.setLocation(resource);
		fxmlLoader.setResources(LanguageBundle.getBundle());
		Platform.runLater(() -> {
			try
			{
				ScrollPane pane = fxmlLoader.load();
				this.getChildren().add(pane);
			} catch (IOException e)
			{
				Logging.errorPrint(String.format("failed to load stream fxml (%s/%s/%s)",
						resourceName, klass, resource
				), e);
			}
		});
	}

	@Override
	public String getTitle()
	{
		return LanguageBundle.getString(titleTextKey);
	}

	@Override
	public void applyOptionValuesToControls()
	{
		GuiAssertions.assertIsNotJavaFXThread();
		Platform.runLater(() ->
			fxmlLoader.<T>getController().reset()
		);
	}

	@Override
	public void setOptionsBasedOnControls()
	{
		fxmlLoader.<T>getController().apply();
	}

	public T getController()
	{
		GuiAssertions.assertIsNotJavaFXThread();
		return GuiUtility.runOnJavaFXThreadNow(() -> fxmlLoader.getController());
	}
}
