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

package pcgen.preferences;


import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class is the "model" for a "Preference".
 * A preference is an object which can
 * - change behavior in code
 * - be modified by the UI
 * - be saved and restored to persistent storage
 * - is attached to the application and not a character
 *
 * Notable a PCGenPreference is not a "house rule" which differs in the following ways:
 * - is attached to a character
 * - can not change behavior in code
 *
 * The typical way to work with a PCGenPreference is to grab the underlying Property<T> and bind to it (or add a
 * listener).
 *
 * A PCGenPreference knows everything about itself: e.g., what it is called, what it's persistance key is, and so on.
 */
public interface PCGenPreference<T>
{
	/**
	 * Gets the persistent key.
	 */
	public String getPersistanceKey();

	/**
	 * Gets the text to prompt the user
	 */
	public String getLocalizedText();

	/**
	 * Gets the property to bind
	 */
	public Property<T> getProperty();
}
