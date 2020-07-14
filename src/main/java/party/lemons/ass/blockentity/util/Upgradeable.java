package party.lemons.ass.blockentity.util;

/*
	Machine that can be upgraded tier
 */
public interface Upgradeable
{
	MachineTier getCurrentTier();
	boolean canAcceptTier(MachineTier tier);
	void upgrade(MachineTier tier);
}
