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


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A boolean preference.
 */
public class BooleanPCGenPreference implements PCGenPreference<Boolean>
{
	private final BooleanProperty property = new SimpleBooleanProperty();
	private final String i18nKey;
	private final String persistenceKey;

	public BooleanPCGenPreference(final String i18nKey, final String persistenceKey)
	{
		this.i18nKey = i18nKey;
		this.persistenceKey = persistenceKey;
	}


	@Override
	public String getPersistanceKey()
	{
		return persistenceKey;
	}

	@Override
	public String getLocalizedText()
	{
		return i18nKey;
	}

	@Override
	public Property<Boolean> getProperty()
	{
		return property;
	}
}
