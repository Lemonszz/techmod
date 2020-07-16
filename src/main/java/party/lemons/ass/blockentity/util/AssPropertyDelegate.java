package party.lemons.ass.blockentity.util;

import com.google.common.collect.Maps;
import net.minecraft.screen.PropertyDelegate;

import java.util.Map;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class AssPropertyDelegate implements PropertyDelegate
{
	protected Map<Integer, IntSupplier> getters = Maps.newHashMap();
	protected Map<Integer, IntConsumer> setters = Maps.newHashMap();

	public void addProperty(int index, IntSupplier getter, IntConsumer setter)
	{
		getters.put(index, getter);
		setters.put(index, setter);
	}

	@Override
	public int get(int index)
	{
		if(getters.containsKey(index))
			return getters.get(index).getAsInt();

		return 0;
	}

	@Override
	public void set(int index, int value)
	{
		if(setters.containsKey(index))
			setters.get(index).accept(value);
	}

	@Override
	public int size()
	{
		return getters.size();
	}
}
