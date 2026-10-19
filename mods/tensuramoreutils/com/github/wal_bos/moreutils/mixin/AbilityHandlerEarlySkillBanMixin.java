package com.github.wal_bos.moreutils.mixin;

import com.github.wal_bos.moreutils.data.MoreUtilsData;
import com.github.wal_bos.moreutils.data.SkillBanType;
import com.github.wal_bos.moreutils.handler.MoreUtilsSkillCap;
import com.github.wal_bos.moreutils.util.MoreUtilsPurge;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.tensura.handler.AbilityHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbilityHandler.class, remap = false)
public abstract class AbilityHandlerEarlySkillBanMixin {
   private static boolean moreutils$registeredEarlySkillHook;

   @Inject(method = "init", at = @At("HEAD"), remap = false)
   private static void moreutils$registerEarlySkillHook(CallbackInfo ci) {
      if (!moreutils$registeredEarlySkillHook) {
         moreutils$registeredEarlySkillHook = true;
         SkillEvents.UNLOCK_SKILL.register((UnlockSkillEvent)(instance, entity, unlockMessage) -> {
            if (entity instanceof LivingEntity living) {
               if (living.level().isClientSide()) {
                  return EventResult.pass();
               }

               if (MoreUtilsPurge.isRefundingMaxMagicules()) {
                  return EventResult.pass();
               }

               if (living instanceof ServerPlayer player) {
                  MoreUtilsSkillCap.processResetCounter(player);
                  if (MoreUtilsSkillCap.blocksUnlock(player, instance)) {
                     unlockMessage.set(Component.empty());
                     return EventResult.interruptFalse();
                  }
               }

               SkillBanType type = MoreUtilsData.getSkillBan(instance.getSkillId());
               if (type != null && type.blocksLearning()) {
                  unlockMessage.set(Component.empty());
                  return EventResult.interruptFalse();
               } else {
                  return EventResult.pass();
               }
            } else {
               return EventResult.pass();
            }
         });
      }
   }
}
