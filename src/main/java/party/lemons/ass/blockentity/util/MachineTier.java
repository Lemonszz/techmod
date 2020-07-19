package party.lemons.ass.blockentity.util;

import com.google.common.collect.Sets;
import net.minecraft.util.Lazy;

import java.util.Set;

public class MachineTier
{
	private static final Set<MachineTier> TIERS = Sets.newHashSet();

	public static MachineTier fromName(String name)
	{
		for(MachineTier tier : TIERS)
			if(tier.name.equals(name))
				return tier;

		return null;
	}

	public static final MachineTier BASE = new MachineTier("base", 1.0F, 1.0F, new Lazy<>(()->MachineTier.IRON));
	public static final MachineTier IRON = new MachineTier("iron", 1.25F, 1.0F, new Lazy<>(()->MachineTier.GOLD));
	public static final MachineTier GOLD = new MachineTier("gold", 1.4F, 1.25F, new Lazy<>(()->MachineTier.EMERALD));
	public static final MachineTier EMERALD = new MachineTier("emerald", 1.5F, 1.5F, new Lazy<>(()->MachineTier.DIAMOND));
	public static final MachineTier DIAMOND = new MachineTier("diamond", 1.75F, 1.75F, new Lazy<>(()->MachineTier.CREATIVE));
	public static final MachineTier CREATIVE = new MachineTier("creative", 5F, 0F, new Lazy<>(()->MachineTier.CREATIVE));

	public final float workSpeedMultiplier;
	public final float powerUseMultiplier;
	public final Lazy<MachineTier> nextTier;
	private final String name;

	public MachineTier(String name, float workSpeedMultiplier, float powerUseMultiplier, Lazy<MachineTier> nextTier)
	{
		this.workSpeedMultiplier = workSpeedMultiplier;
		this.powerUseMultiplier = powerUseMultiplier;
		this.nextTier = nextTier;
		this.name = name;

		TIERS.add(this);
	}

	public String getName()
	{
		return name;
	}

	public String getTranslationKey()
	{
		return "tier." + name;
	}
}
