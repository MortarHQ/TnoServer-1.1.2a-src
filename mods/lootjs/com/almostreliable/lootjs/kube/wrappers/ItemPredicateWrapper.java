package com.almostreliable.lootjs.kube.wrappers;

import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.kube.KubeOps;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.world.item.Item;

public class ItemPredicateWrapper {
   private static final TypeInfo ITEM_HOLDER_SET = TypeInfo.of(HolderSet.class).withParams(new TypeInfo[]{TypeInfo.of(Item.class)});

   public static ItemPredicate of(Context cx, Object o, TypeInfo target) {
      return switch (o) {
         case ItemPredicate i -> i;
         case ItemFilter filter -> Builder.item()
            .withSubPredicate(
               com.almostreliable.lootjs.core.filters.ItemFilterWrapper.TYPE, new com.almostreliable.lootjs.core.filters.ItemFilterWrapper(filter)
            )
            .build();
         case Map<?, ?> map -> {
            RegistryAccessContainer registries = ((KubeJSContext)cx).getRegistries();
            KubeOps ops = KubeOps.create(registries);
            yield (ItemPredicate)ItemPredicate.CODEC.parse(ops, o).getOrThrow();
         }
         default -> {
            HolderSet<Item> set = (HolderSet<Item>)cx.jsToJava(o, ITEM_HOLDER_SET);
            yield new ItemPredicate(Optional.of(set), Ints.ANY, DataComponentPredicate.EMPTY, new HashMap());
         }
      };
   }
}
