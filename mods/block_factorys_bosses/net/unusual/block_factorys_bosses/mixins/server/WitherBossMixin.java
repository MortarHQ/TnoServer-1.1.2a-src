package net.unusual.block_factorys_bosses.mixins.server;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(WitherBoss.class)
public class WitherBossMixin {
   @Shadow
   @Final
   private ServerBossEvent bossEvent;

   @Inject(method = "<init>", at = @At("TAIL"))
   private void onInit(EntityType<? extends WitherBoss> entityType, Level level, CallbackInfo ci) {
      BossHandling.TRACKED_BOSSES.put(this.bossEvent.getId(), new BossHandling.TrackedBoss(2, false));
   }
}
