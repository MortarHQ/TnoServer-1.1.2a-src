package com.tensura_tno.mixin.client;

import com.tensura_tno.client.compat.MoreSkillsPathsPortalScanBatch;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.github.wal_bos.moreskills.client.fx.PathsPortalStrandRenderer", remap = false)
public abstract class MoreSkillsPathsPortalStrandPerformanceMixin {
   @Unique
   private static final String tensuraTno$supportedVersion = "0.0.4.5";
   @Unique
   private static final int tensuraTno$horizontalRadius = 32;
   @Unique
   private static final int tensuraTno$verticalRadius = 16;
   @Unique
   private static final int tensuraTno$verticalDiameter = 33;
   @Unique
   private static final int tensuraTno$columnCount = 4225;
   @Unique
   private static final int tensuraTno$totalPositions = 139425;
   @Unique
   private static final int tensuraTno$positionsPerFrame = 1024;
   @Unique
   private static int[] tensuraTno$columnX;
   @Unique
   private static int[] tensuraTno$columnZ;
   @Unique
   private static ClientLevel tensuraTno$scanLevel;
   @Unique
   private static BlockPos tensuraTno$scanCenter;
   @Unique
   private static int tensuraTno$scanIndex;
   @Unique
   private static Boolean tensuraTno$useOptimization;

   @Unique
   private static void tensuraTno$initializeColumns() {
      if (tensuraTno$columnX == null) {
         tensuraTno$columnX = new int[4225];
         tensuraTno$columnZ = new int[4225];
         int column = 0;

         for (int radius = 0; radius <= 32; radius++) {
            for (int x = -radius; x <= radius; x++) {
               for (int z = -radius; z <= radius; z++) {
                  if (Math.max(Math.abs(x), Math.abs(z)) == radius) {
                     tensuraTno$columnX[column] = x;
                     tensuraTno$columnZ[column] = z;
                     column++;
                  }
               }
            }
         }
      }
   }

   @ModifyConstant(method = "tickStrands", constant = @Constant(intValue = 20), remap = false, require = 0)
   private static int tensuraTno$scanEveryFrame(int originalCooldown) {
      return tensuraTno$shouldUseOptimization() ? 0 : originalCooldown;
   }

   @Redirect(
      method = "tickStrands",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/core/BlockPos;betweenClosed(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Ljava/lang/Iterable;",
         remap = true
      ),
      remap = false,
      require = 0
   )
   private static Iterable<BlockPos> tensuraTno$scanPortalBlocksIncrementally(BlockPos lower, BlockPos upper) {
      if (!tensuraTno$shouldUseOptimization()) {
         return BlockPos.betweenClosed(lower, upper);
      }

      ClientLevel level = Minecraft.getInstance().level;
      if (level == null) {
         return List.of();
      }

      tensuraTno$initializeColumns();
      BlockPos currentCenter = new BlockPos(lower.getX() + upper.getX() >> 1, lower.getY() + upper.getY() >> 1, lower.getZ() + upper.getZ() >> 1);
      if (tensuraTno$mustRestartScan(level, currentCenter)) {
         tensuraTno$scanLevel = level;
         tensuraTno$scanCenter = currentCenter;
         tensuraTno$scanIndex = 0;
      }

      int start = tensuraTno$scanIndex;
      int end = Math.min(start + 1024, 139425);
      tensuraTno$scanIndex = end;
      BlockPos anchor = tensuraTno$scanCenter;
      return MoreSkillsPathsPortalScanBatch.iterable(anchor, tensuraTno$columnX, tensuraTno$columnZ, 33, 16, start, end);
   }

   @Unique
   private static boolean tensuraTno$mustRestartScan(ClientLevel level, BlockPos currentCenter) {
      return tensuraTno$scanLevel == level && tensuraTno$scanCenter != null && tensuraTno$scanIndex < 139425
         ? Math.abs(currentCenter.getX() - tensuraTno$scanCenter.getX()) > 32
            || Math.abs(currentCenter.getY() - tensuraTno$scanCenter.getY()) > 16
            || Math.abs(currentCenter.getZ() - tensuraTno$scanCenter.getZ()) > 32
         : true;
   }

   @Unique
   private static boolean tensuraTno$shouldUseOptimization() {
      if (tensuraTno$useOptimization == null) {
         String version = ModList.get().getModContainerById("tensuramoreskills").map(container -> container.getModInfo().getVersion().toString()).orElse("");
         if (version.startsWith("v")) {
            version = version.substring(1);
         }

         tensuraTno$useOptimization = version.equals("0.0.4.5") || version.startsWith("0.0.4.5+");
      }

      return tensuraTno$useOptimization;
   }
}
