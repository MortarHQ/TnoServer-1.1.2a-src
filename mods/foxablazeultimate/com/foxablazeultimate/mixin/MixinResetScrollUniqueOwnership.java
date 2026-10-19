package com.foxablazeultimate.mixin;

import com.foxablazeultimate.event.UltimateSkillProtector;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ResetScrollItem.class, remap = false)
public abstract class MixinResetScrollUniqueOwnership {
   @Unique
   private static final ThreadLocal<Set<ResourceLocation>> foxablazeultimate$protectedUniqueSkills = new ThreadLocal<>();

   @Inject(method = "resetEverything(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("HEAD"))
   private static void foxablazeultimate$captureCharacterReset(ServerPlayer player, CallbackInfo ci) {
      foxablazeultimate$capture(player);
   }

   @Inject(method = "resetEverything(Lnet/minecraft/server/level/ServerPlayer;)V", at = @At("RETURN"))
   private static void foxablazeultimate$restoreCharacterReset(ServerPlayer player, CallbackInfo ci) {
      foxablazeultimate$restore(player);
   }

   @Inject(method = "resetSkill(Lnet/minecraft/server/level/ServerPlayer;Z)V", at = @At("HEAD"))
   private static void foxablazeultimate$captureSkillReset(ServerPlayer player, boolean coverEp, CallbackInfo ci) {
      foxablazeultimate$capture(player);
   }

   @Inject(method = "resetSkill(Lnet/minecraft/server/level/ServerPlayer;Z)V", at = @At("RETURN"))
   private static void foxablazeultimate$restoreSkillReset(ServerPlayer player, boolean coverEp, CallbackInfo ci) {
      foxablazeultimate$restore(player);
   }

   @Unique
   private static void foxablazeultimate$capture(ServerPlayer player) {
      foxablazeultimate$protectedUniqueSkills.set(UltimateSkillProtector.captureResetProtectedUniqueSkills(player));
   }

   @Unique
   private static void foxablazeultimate$restore(ServerPlayer player) {
      Set<ResourceLocation> protectedSkills = foxablazeultimate$protectedUniqueSkills.get();
      foxablazeultimate$protectedUniqueSkills.remove();
      UltimateSkillProtector.restoreResetUniqueOwnership(player, protectedSkills);
   }
}
