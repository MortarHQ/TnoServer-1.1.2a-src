package earth.terrarium.handcrafted.common.blocks.base.properties;

import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum OptionalColorProperty implements StringRepresentable {
   NONE,
   WHITE,
   ORANGE,
   MAGENTA,
   LIGHT_BLUE,
   YELLOW,
   LIME,
   PINK,
   GRAY,
   LIGHT_GRAY,
   CYAN,
   PURPLE,
   BLUE,
   BROWN,
   GREEN,
   RED,
   BLACK;

   @NotNull
   public String getSerializedName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   @Override
   public String toString() {
      return this.getSerializedName();
   }

   public static OptionalColorProperty fromCushion(Item cushion) {
      return valueOf(BuiltInRegistries.ITEM.getKey(cushion).getPath().replace("_cushion", "").toUpperCase(Locale.ROOT));
   }

   public ItemStack toCushion() {
      return this == NONE
         ? ItemStack.EMPTY
         : ((Item)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("handcrafted", this.getSerializedName() + "_cushion"))).getDefaultInstance();
   }

   public static OptionalColorProperty fromSheet(Item sheet) {
      return valueOf(BuiltInRegistries.ITEM.getKey(sheet).getPath().replace("_sheet", "").toUpperCase(Locale.ROOT));
   }

   public ItemStack toSheet() {
      return this == NONE
         ? ItemStack.EMPTY
         : ((Item)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("handcrafted", this.getSerializedName() + "_sheet"))).getDefaultInstance();
   }
}
