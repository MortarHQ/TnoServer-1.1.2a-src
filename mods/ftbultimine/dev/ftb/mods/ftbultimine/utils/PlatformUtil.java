package dev.ftb.mods.ftbultimine.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbultimine.utils.neoforge.PlatformUtilImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PlatformUtil {
   @ExpectPlatform
   @Transformed
   public static boolean playerHasCorrectTool(Player player, BlockPos pos, BlockState state) {
      return PlatformUtilImpl.playerHasCorrectTool(player, pos, state);
   }

   @ExpectPlatform
   @Transformed
   public static boolean canAxeStrip(ItemStack stack) {
      return PlatformUtilImpl.canAxeStrip(stack);
   }

   @ExpectPlatform
   @Transformed
   public static boolean canTillSoil(ItemStack stack) {
      return PlatformUtilImpl.canTillSoil(stack);
   }

   @ExpectPlatform
   @Transformed
   public static boolean canFlattenPath(ItemStack stack) {
      return PlatformUtilImpl.canFlattenPath(stack);
   }
}
