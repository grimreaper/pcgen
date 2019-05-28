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

package pcgen.gui3.component;

import java.io.IOException;

import pcgen.gui2.PCGenFrame;
import pcgen.gui3.behavior.EnabledOnlyWithSources;
import pcgen.system.LanguageBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

/**
 * Reusuable status bar. Displays a message + Progress Bar.
 */
public class RootToolBar extends ToolBar
{
	@FXML
	private Button newButton;

	private final PCGenFrame rootFrame;
	public RootToolBar(final PCGenFrame rootFrame) throws IOException
	{
		this.rootFrame = rootFrame;
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(LanguageBundle.getBundle());
		loader.setController(this);
		loader.setRoot(this);
		loader.setLocation(getClass().getResource("RootToolBar.fxml"));
		loader.load();
	}

	/**
	 * note well: this overrides a private method of a super.
	 * sadly, this is required by JavaFX but may result in confusion or even bugs
	 * should a future update make initialize public.
	 */
	@SuppressWarnings("MethodOverridesInaccessibleMethodOfSuper")
	@FXML
	void initialize()
	{
		newButton.setOnAction(this::onNew);
		// this is weird: creating the object has a side effect of binding the object to the listener
		new EnabledOnlyWithSources(newButton, rootFrame);


	}

	private void onNew(final javafx.event.ActionEvent actionEvent)
	{
		rootFrame.createNewCharacter(null);
	}



}
