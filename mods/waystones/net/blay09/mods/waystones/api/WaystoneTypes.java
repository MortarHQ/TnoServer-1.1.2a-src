package net.blay09.mods.waystones.api;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

public class WaystoneTypes {
   public static final ResourceLocation WAYSTONE = ResourceLocation.fromNamespaceAndPath("waystones", "waystone");
   public static final ResourceLocation WARP_PLATE = ResourceLocation.fromNamespaceAndPath("waystones", "warp_plate");
   public static final ResourceLocation[] SHARESTONES = new ResourceLocation[]{
      ResourceLocation.fromNamespaceAndPath("waystones", "orange_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "magenta_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "light_blue_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "yellow_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "lime_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "pink_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "gray_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "light_gray_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "cyan_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "purple_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "blue_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "brown_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "green_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "red_sharestone"),
      ResourceLocation.fromNamespaceAndPath("waystones", "black_sharestone")
   };

   public static Optional<ResourceLocation> getSharestone(@Nullable DyeColor color) {
      return color != null && color != DyeColor.WHITE
         ? Optional.of(ResourceLocation.fromNamespaceAndPath("waystones", color.getSerializedName() + "_sharestone"))
         : Optional.empty();
   }

   public static boolean isSharestone(ResourceLocation waystoneType) {
      return waystoneType.getPath().endsWith("_sharestone");
   }
}
