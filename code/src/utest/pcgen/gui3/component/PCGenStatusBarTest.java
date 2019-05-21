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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.     See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package pcgen.gui3.component;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

@ExtendWith(ApplicationExtension.class)
class PCGenStatusBarTest
{
	Scene x;
	@Start
	private void Start(Stage stage) throws IOException
	{
		PCGenStatusBar pcGenStatusBar = new PCGenStatusBar();
		Scene scene = new Scene(pcGenStatusBar);
		x = scene;
		stage.setScene(scene);
		stage.show();
	}

	@Test
	void test_status_bar_has_no_text_at_creation(final FxRobotInterface robot)
	{
		System.out.println(" yyy" + x.getWindow().getScene().getRoot().getChildrenUnmodifiable());
		FxAssert.verifyThat(".Text",  LabeledMatchers.hasText(""));
//		FxAssert.verifyThat(".", NodeMatchers.isVisible());
	}

}
