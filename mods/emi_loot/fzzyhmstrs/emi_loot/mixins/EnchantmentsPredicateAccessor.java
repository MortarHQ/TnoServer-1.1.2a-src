package fzzyhmstrs.emi_loot.mixins;

import java.util.List;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEnchantmentsPredicate.class)
public interface EnchantmentsPredicateAccessor {
   @Accessor
   List<EnchantmentPredicate> getEnchantments();
}
