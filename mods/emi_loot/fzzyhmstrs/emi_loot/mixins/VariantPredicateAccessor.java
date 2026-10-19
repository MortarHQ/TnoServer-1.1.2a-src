package fzzyhmstrs.emi_loot.mixins;

import net.minecraft.advancements.critereon.EntitySubPredicates.EntityVariantPredicateType.Instance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Instance.class)
public interface VariantPredicateAccessor<V> {
   @Accessor
   V getVariant();
}
