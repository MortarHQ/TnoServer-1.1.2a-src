package dev.ftb.mods.ftbchunks.client.map.color;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import java.util.HashMap;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class BlockColors {
   private static final HashMap<String, BlockColor> TYPE_MAP = new HashMap<>();
   public static final BlockColor ERROR = register("error", (world, pos) -> Color4I.RED);
   public static final BlockColor FOLIAGE = register(
      "foliage", (world, pos) -> Color4I.rgb(BiomeColors.getAverageFoliageColor(world, pos)).withTint(Color4I.BLACK.withAlpha(50))
   );
   public static final BlockColor GRASS = register(
      "grass", (world, pos) -> Color4I.rgb(BiomeColors.getAverageGrassColor(world, pos)).withTint(Color4I.BLACK.withAlpha(50))
   );
   public static final BlockColor IGNORED = register("ignored", new IgnoredBlockColor());
   public static final BlockColor BOP_RAINBOW = register(
      "bop_rainbow", (world, pos) -> Color4I.hsb((pos.getX() + Mth.sin(((float)pos.getZ() + pos.getX()) / 35.0F) * 35.0F) % 150.0F / 150.0F, 0.6F, 0.5F)
   );

   public static BlockColor register(String type, BlockColor color) {
      TYPE_MAP.put(type, color);
      return color;
   }

   @Nullable
   public static BlockColor getFromType(String value) {
      return value.indexOf(35) == 0 ? new CustomBlockColor(Color4I.fromString(value)) : TYPE_MAP.get(value);
   }
}
