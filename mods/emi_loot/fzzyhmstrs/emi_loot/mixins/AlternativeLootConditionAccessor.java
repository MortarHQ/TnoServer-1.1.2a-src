package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CompositeLootItemCondition.class)
public interface AlternativeLootConditionAccessor {
   @Accessor("terms")
   List<LootItemCondition> getConditions();
}
