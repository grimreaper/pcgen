/*
 * Copyright 2008 Connor Petty <cpmeister@users.sourceforge.net>
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

import java.io.IOException;

import pcgen.gui3.behavior.EnabledOnlyWithSources;
import pcgen.gui3.component.RootToolBar;
import pcgen.util.Logging;

import javafx.scene.control.ToolBar;

/**
 * The toolbar that is displayed in PCGen's main window. Provides shortcuts to
 * common PCGen activities.
 *
 * @see pcgen.gui2.PCGenFrame
 */
final class PCGenToolBar
{
	private final PCGenFrame rootFrame;
	PCGenToolBar(final PCGenFrame rootFrame)
	{
		this.rootFrame = rootFrame;
	}

	public ToolBar buildMenu()
	{
		RootToolBar toolBar = null;
		try
		{
			toolBar = new RootToolBar(rootFrame);
		} catch (IOException e)
		{
			Logging.errorPrint("failed to load root toolbar", e);
			return null;
		}

		return toolBar;
	}

	private void onNew(final javafx.event.ActionEvent actionEvent)
	{
		rootFrame.createNewCharacter(null);
	}



	private void initComponents()
	{
//
//		add(createToolBarButton(actionMap.get(NEW_COMMAND)));
//		add(createToolBarButton(actionMap.get(PCGenActionMap.OPEN_COMMAND)));
//		add(createToolBarButton(actionMap.get(PCGenActionMap.CLOSE_COMMAND)));
//		add(createToolBarButton(actionMap.get(PCGenActionMap.SAVE_COMMAND)));
//		addSeparator();
//
//		add(createToolBarButton(actionMap.get(PCGenActionMap.PRINT_COMMAND)));
//		add(createToolBarButton(actionMap.get(PCGenActionMap.EXPORT_COMMAND)));
//		addSeparator();
//
//		add(createToolBarButton(actionMap.get(PCGenActionMap.PREFERENCES_COMMAND)));
	}

}
