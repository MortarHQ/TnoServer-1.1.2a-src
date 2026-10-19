package net.blay09.mods.waystones.api;

import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;

public enum WaystoneVisibility {
   ACTIVATION,
   GLOBAL,
   SHARD_ONLY,
   ORANGE_SHARESTONE,
   MAGENTA_SHARESTONE,
   LIGHT_BLUE_SHARESTONE,
   YELLOW_SHARESTONE,
   LIME_SHARESTONE,
   PINK_SHARESTONE,
   GRAY_SHARESTONE,
   LIGHT_GRAY_SHARESTONE,
   CYAN_SHARESTONE,
   PURPLE_SHARESTONE,
   BLUE_SHARESTONE,
   BROWN_SHARESTONE,
   GREEN_SHARESTONE,
   RED_SHARESTONE,
   BLACK_SHARESTONE;

   private static final IntFunction<WaystoneVisibility> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
   public static final StreamCodec<ByteBuf, WaystoneVisibility> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);
   public static final StreamCodec<ByteBuf, List<WaystoneVisibility>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ByteBufCodecs.list());

   public static WaystoneVisibility fromWaystoneType(ResourceLocation waystoneType) {
      if (WaystoneTypes.isSharestone(waystoneType)) {
         return switch (waystoneType.getPath()) {
            case "orange_sharestone" -> ORANGE_SHARESTONE;
            case "magenta_sharestone" -> MAGENTA_SHARESTONE;
            case "light_blue_sharestone" -> LIGHT_BLUE_SHARESTONE;
            case "yellow_sharestone" -> YELLOW_SHARESTONE;
            case "lime_sharestone" -> LIME_SHARESTONE;
            case "pink_sharestone" -> PINK_SHARESTONE;
            case "gray_sharestone" -> GRAY_SHARESTONE;
            case "light_gray_sharestone" -> LIGHT_GRAY_SHARESTONE;
            case "cyan_sharestone" -> CYAN_SHARESTONE;
            case "purple_sharestone" -> PURPLE_SHARESTONE;
            case "blue_sharestone" -> BLUE_SHARESTONE;
            case "brown_sharestone" -> BROWN_SHARESTONE;
            case "green_sharestone" -> GREEN_SHARESTONE;
            case "red_sharestone" -> RED_SHARESTONE;
            case "black_sharestone" -> BLACK_SHARESTONE;
            default -> ACTIVATION;
         };
      } else {
         return waystoneType.equals(WaystoneTypes.WARP_PLATE) ? SHARD_ONLY : ACTIVATION;
      }
   }
}
