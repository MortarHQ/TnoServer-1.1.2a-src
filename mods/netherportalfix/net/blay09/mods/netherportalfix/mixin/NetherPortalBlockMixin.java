package net.blay09.mods.netherportalfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.Optional;
import net.blay09.mods.netherportalfix.NetherPortalFix;
import net.blay09.mods.netherportalfix.ReturnPortal;
import net.blay09.mods.netherportalfix.ReturnPortalManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {
   @ModifyExpressionValue(
      method = "getExitPortal(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/level/border/WorldBorder;)Lnet/minecraft/world/level/portal/DimensionTransition;",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/portal/PortalForcer;findClosestPortalPosition(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/level/border/WorldBorder;)Ljava/util/Optional;"
      )
   )
   public Optional<BlockPos> getExitPortal(Optional<BlockPos> original, ServerLevel level, Entity entity) {
      BlockPos fromPos = entity.blockPosition();
      ResourceKey<Level> fromDim = entity.level().dimension();
      ReturnPortal returnPortal = ReturnPortalManager.findReturnPortal(entity, fromDim, fromPos);
      if (returnPortal == null) {
         if (entity instanceof ServerPlayer) {
            NetherPortalFix.logger.info("No return portal found");
         }

         return original;
      } else if (!level.getBlockState(returnPortal.getPos()).is(Blocks.NETHER_PORTAL)) {
         if (entity instanceof ServerPlayer) {
            NetherPortalFix.logger.info("Return portal is no longer valid");
         }

         return original;
      } else {
         NetherPortalFix.logger.debug("Return portal found, redirecting! :)");
         return Optional.of(returnPortal.getPos());
      }
   }
}
