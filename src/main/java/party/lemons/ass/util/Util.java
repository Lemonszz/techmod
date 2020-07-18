package party.lemons.ass.util;

import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public class Util
{
	public static <T> Lazy<T> lazy(Supplier<T> supplier)
	{
		return new Lazy<>(supplier);
	}
}
