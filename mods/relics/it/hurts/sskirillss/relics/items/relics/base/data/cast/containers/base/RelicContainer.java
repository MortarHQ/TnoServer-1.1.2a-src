package it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.base;

import it.hurts.sskirillss.relics.system.casts.abilities.AbilityReference;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class RelicContainer {
   public abstract Function<LivingEntity, List<ItemStack>> gatherRelics();

   @Deprecated(since = "1.21", forRemoval = true)
   public abstract Function<LivingEntity, List<AbilityReference>> gatherAbilities();
}
