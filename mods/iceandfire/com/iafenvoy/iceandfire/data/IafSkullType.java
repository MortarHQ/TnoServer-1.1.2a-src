package com.iafenvoy.iceandfire.data;

import com.iafenvoy.iceandfire.item.MobSkullItem;
import com.iafenvoy.iceandfire.registry.IafItems;
import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SkullBlock.Type;

public enum IafSkullType implements Type {
   HIPPOGRYPH,
   CYCLOPS,
   COCKATRICE,
   STYMPHALIAN,
   TROLL,
   AMPHITHERE,
   SEASERPENT,
   HYDRA;

   private final String itemResourceName = this.name().toLowerCase(Locale.ROOT) + "_skull";

   public static void initItems() {
      for (IafSkullType skull : values()) {
         IafItems.registerItem(skull.itemResourceName, () -> new MobSkullItem(skull));
      }
   }

   public Item getSkullItem() {
      return (Item)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("iceandfire", this.itemResourceName));
   }

   public String getSerializedName() {
      return this.itemResourceName;
   }
}
