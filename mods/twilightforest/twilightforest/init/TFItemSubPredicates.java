package twilightforest.init;

import net.minecraft.advancements.critereon.ItemSubPredicate.Type;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.advancements.predicate.ItemColorPredicate;

public class TFItemSubPredicates {
   public static final DeferredRegister<Type<?>> TYPES = DeferredRegister.create(Registries.ITEM_SUB_PREDICATE_TYPE, "twilightforest");
   public static final DeferredHolder<Type<?>, Type<ItemColorPredicate>> COLOR = TYPES.register("color", () -> new Type(ItemColorPredicate.CODEC));
}
