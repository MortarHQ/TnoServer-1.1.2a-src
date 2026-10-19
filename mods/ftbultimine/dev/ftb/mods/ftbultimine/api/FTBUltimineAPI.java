package dev.ftb.mods.ftbultimine.api;

import dev.ftb.mods.ftbultimine.api.blockselection.BlockSelectionHandler;
import java.util.Collection;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus.Internal;

public class FTBUltimineAPI {
   public static final String MOD_ID = "ftbultimine";
   private static FTBUltimineAPI.API instance;

   public static boolean isTooExhausted(ServerPlayer player) {
      if (player.isCreative()) {
         return false;
      }

      FoodData data = player.getFoodData();
      return data.getExhaustionLevel() / 4.0F > data.getSaturationLevel() + data.getFoodLevel();
   }

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("ftbultimine", path);
   }

   public static FTBUltimineAPI.API api() {
      return instance;
   }

   @Internal
   public static void _init(FTBUltimineAPI.API instance) {
      if (FTBUltimineAPI.instance != null) {
         throw new IllegalStateException("can't init more than once!");
      }

      FTBUltimineAPI.instance = instance;
   }

   public interface API {
      Optional<Collection<BlockPos>> currentBlockSelection(Player var1);

      BlockSelectionHandler.Result customSelectionCheck(Player var1, BlockPos var2, BlockPos var3, BlockState var4, BlockState var5);
   }
}
