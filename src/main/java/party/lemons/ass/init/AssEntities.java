package party.lemons.ass.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import party.lemons.ass.entity.FlyingBlockEntity;
import party.lemons.ass.util.registry.AutoReg;

@AutoReg(type = EntityType.class, registry = "entity_type")
public class AssEntities
{
	public static final EntityType<FlyingBlockEntity> FLYING_BLOCK = FabricEntityTypeBuilder.<FlyingBlockEntity>
			create(SpawnGroup.MISC, (t,w)->new FlyingBlockEntity(w))
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F))
			.trackable(10, 1)
			.build();
}
