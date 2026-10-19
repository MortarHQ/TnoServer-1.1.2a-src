package net.unusual.block_factorys_bosses.mixins.server;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.dimension.end.EndDragonFight.Data;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(EndDragonFight.class)
public class EndDragonFightMixin {
   @Shadow
   @Final
   private ServerBossEvent dragonEvent;

   @Inject(
      method = "<init>(Lnet/minecraft/server/level/ServerLevel;JLnet/minecraft/world/level/dimension/end/EndDragonFight$Data;Lnet/minecraft/core/BlockPos;)V",
      at = @At("TAIL")
   )
   private void onInit(ServerLevel level, long seed, Data data, BlockPos origin, CallbackInfo ci) {
      BossHandling.TRACKED_BOSSES.put(this.dragonEvent.getId(), new BossHandling.TrackedBoss(1, false));
   }
}
