package earth.terrarium.handcrafted.common.blocks.base.properties;

import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum CounterProperty implements StringRepresentable {
   ACACIA_PLANKS,
   ANDESITE,
   BAMBOO_PLANKS,
   BIRCH_PLANKS,
   BLACKSTONE,
   BRICKS,
   CALCITE,
   CHERRY_PLANKS,
   CRIMSON_PLANKS,
   DARK_OAK_PLANKS,
   DEEPSLATE,
   DIORITE,
   DRIPSTONE_BLOCK,
   GRANITE,
   JUNGLE_PLANKS,
   MANGROVE_PLANKS,
   OAK_PLANKS,
   QUARTZ_BLOCK,
   SMOOTH_STONE,
   SPRUCE_PLANKS,
   WARPED_PLANKS;

   @NotNull
   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.getSerializedName();
   }

   public static CounterProperty fromBlock(Item block) {
      return valueOf(BuiltInRegistries.ITEM.getKey(block).getPath().toUpperCase(Locale.ROOT));
   }

   public ItemStack toBlock() {
      return ((Item)BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(this.getSerializedName()))).getDefaultInstance();
   }
}
