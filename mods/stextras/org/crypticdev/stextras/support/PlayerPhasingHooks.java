package org.crypticdev.stextras.support;

import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.crypticdev.stextras.effect.skill.WispModeEffect;
import org.crypticdev.stextras.integration.OPAC.OpenPacIntegration;
import org.crypticdev.stextras.integration.OPAC.WispClientHooks;
import org.crypticdev.stextras.registary.effects.STExtrasMobEffects;

public final class PlayerPhasingHooks {
   private static final double PREDICTIVE_BUFFER = 0.1;

   public static boolean hasPhaseAbility(Player self) {
      boolean hasWisp = self.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)STExtrasMobEffects.WISP_MODE.get()));
      return !hasWisp ? false : !WispModeEffect.isDimensionBlacklisted(self.level().dimension().location());
   }

   public static boolean canPhase(Player self) {
      if (!hasPhaseAbility(self)) {
         return false;
      } else {
         AABB predictiveBounds = self.getBoundingBox().expandTowards(self.getDeltaMovement()).inflate(0.1);
         if (WispModeEffect.intersectsBlacklistedBlock(self.level(), predictiveBounds)) {
            return false;
         } else if (!WispModeEffect.isOpacSupportEnabled() || !Platform.isModLoaded("openpartiesandclaims")) {
            return true;
         } else if (self instanceof ServerPlayer sp) {
            return OpenPacIntegration.canPhaseThrough(sp, predictiveBounds);
         } else {
            return self.level().isClientSide ? WispClientHooks.canPhaseClientOpac(self, predictiveBounds) : true;
         }
      }
   }

   public static boolean pretendSpectatorWhenPhasing(Player self) {
      return self.isSpectator() || canPhase(self);
   }

   public static boolean isPhaseBlockedAt(Level level, BlockPos pos, Player contextPlayer) {
      if (WispModeEffect.isBlockBlacklisted(level.getBlockState(pos))) {
         return true;
      } else {
         return level.isClientSide && contextPlayer != null && WispModeEffect.isOpacSupportEnabled() && OpenPacIntegration.isOPACLoaded()
            ? !WispClientHooks.canPhaseClientOpac(contextPlayer, new AABB(pos))
            : false;
      }
   }
}
