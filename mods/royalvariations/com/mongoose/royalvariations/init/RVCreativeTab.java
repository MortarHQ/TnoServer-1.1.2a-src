package com.mongoose.royalvariations.init;

import com.mongoose.royalvariations.common.items.RVItems;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RVCreativeTab {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "royalvariations");
   public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register(
      "royalvariations",
      () -> CreativeModeTab.builder()
         .icon(() -> ((Item)RVItems.SPIRITUAL_CROWN.get()).getDefaultInstance())
         .title(Component.translatable("item_group.royal_variations"))
         .displayItems((parameters, output) -> {
            output.accept((ItemLike)RVItems.ROYAL_ZOMBIE_SPAWN_EGG.get());
            output.accept((ItemLike)RVItems.ROYAL_SKELETON_SPAWN_EGG.get());
            output.accept((ItemLike)RVItems.ROYAL_CREEPER_SPAWN_EGG.get());
            output.accept((ItemLike)RVItems.ROYAL_ENDERMAN_SPAWN_EGG.get());
            output.accept((ItemLike)RVItems.SPIRITUAL_CROWN_SHARD.get());
            output.accept((ItemLike)RVItems.SPIRITUAL_CROWN.get());
            output.accept((ItemLike)RVItems.ROYAL_STAFF.get());
            output.accept((ItemLike)RVItems.ROYAL_BONE.get());
            output.accept((ItemLike)RVItems.ROYAL_BONE_MEAL.get());
            output.accept((ItemLike)RVItems.ROYAL_GUNPOWDER.get());
            output.accept((ItemLike)RVItems.ROYAL_BOMB.get());
            output.accept((ItemLike)RVItems.ROYAL_BOW.get());
            output.accept((ItemLike)RVItems.ROYAL_ARROW.get());
            output.accept((ItemLike)RVItems.ROYAL_ENDER_PEARL.get());
            output.accept((ItemLike)RVItems.SPECTRAL_GAUNTLET.get());
            output.accept((ItemLike)RVItems.ROYAL_TELELOCATOR.get());
            output.accept((ItemLike)RVItems.EMPTY_ROYAL_TELELOCATOR.get());
            output.accept((ItemLike)RVItems.ROYAL_ARMOR_FRAGMENTS.get());
            output.accept((ItemLike)RVItems.ROYAL_UPGRADE.get());
            output.accept((ItemLike)RVItems.ROYAL_KNIGHT_HELMET.get());
            output.accept((ItemLike)RVItems.ROYAL_KNIGHT_CUIRASS.get());
            output.accept((ItemLike)RVItems.ROYAL_KNIGHT_LEGGINGS.get());
            output.accept((ItemLike)RVItems.ROYAL_KNIGHT_BOOTS.get());
         })
         .build()
   );
}
