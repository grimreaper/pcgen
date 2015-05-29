/*
 * Copyright 2014 (C) Tom Parker <thpr@users.sourceforge.net>
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pcgen.cdom.content.fact;

import java.util.ArrayList;
import java.util.List;

import pcgen.base.util.Indirect;
import pcgen.cdom.base.CDOMObject;
import pcgen.cdom.base.Constants;
import pcgen.cdom.enumeration.FactKey;
import pcgen.rules.context.LoadContext;
import pcgen.rules.persistence.token.AbstractNonEmptyToken;
import pcgen.rules.persistence.token.CDOMSecondaryToken;
import pcgen.rules.persistence.token.ParseResult;

/**
 * A FactParser is a dynamically built subtoken created when a FACT: is defined
 * 
 * @param <T>
 *            The type of of object upon which the FactParser can be used
 * @param <F>
 *            The format of the data stored in the Factt
 */
public class FactParser<T extends CDOMObject, F> extends
		AbstractNonEmptyToken<T> implements CDOMSecondaryToken<T>
{

	/**
	 * The underlying FactInfo indicating static information about the Fact for
	 * which this FactParser can parse the LST information
	 */
	private final FactInfo<T, F> def;

	/**
	 * Constructs a new FactParser with the given FactInfo.
	 * 
	 * @param fi
	 *            The FactInfo underlying this FactParser
	 * @throws IllegalArgumentException
	 *             if the given FactInfo is null
	 */
	public FactParser(FactInfo<T, F> fi)
	{
		if (fi == null)
		{
			throw new IllegalArgumentException("Fact Info cannot be null");
		}
		def = fi;
	}

	/**
	 * @see pcgen.rules.persistence.token.AbstractNonEmptyToken#parseNonEmptyToken(pcgen.rules.context.LoadContext,
	 *      java.lang.Object, java.lang.String)
	 */
	@Override
	protected ParseResult parseNonEmptyToken(LoadContext context, T obj,
		String value)
	{
		FactKey<F> fk = def.getFactKey();
		if (Constants.LST_DOT_CLEAR.equals(value))
		{
			context.getObjectContext().remove(obj, fk);
		}
		else
		{
			Indirect<F> indirect =
					def.getFormatManager().convertIndirect(context, value);
			context.getObjectContext().put(obj, fk, indirect);
		}
		return ParseResult.SUCCESS;
	}

	/**
	 * @see pcgen.rules.persistence.token.CDOMToken#getTokenClass()
	 */
	@Override
	public Class<T> getTokenClass()
	{
		return def.getUsableLocation();
	}

	/**
	 * @see pcgen.rules.persistence.token.AbstractToken#getTokenName()
	 */
	@Override
	public String getTokenName()
	{
		return def.getFactName();
	}

	/**
	 * @see pcgen.rules.persistence.token.CDOMSubToken#getParentToken()
	 */
	@Override
	public String getParentToken()
	{
		return "FACT";
	}

	/**
	 * @see pcgen.rules.persistence.token.CDOMSecondaryToken#unparse(pcgen.rules.context.LoadContext,
	 *      java.lang.Object)
	 */
	@Override
	public String[] unparse(LoadContext context, T obj)
	{
		FactKey<F> fk = def.getFactKey();
		boolean removed = context.getObjectContext().wasRemoved(obj, fk);
		List<String> results = new ArrayList<String>(2);
		if (removed)
		{
			results.add(Constants.LST_DOT_CLEAR);
		}
		Indirect<F> fact = context.getObjectContext().getFact(obj, fk);
		if (fact != null)
		{
			results.add(fact.getUnconverted());
		}
		return results.toArray(new String[results.size()]);
	}

}
