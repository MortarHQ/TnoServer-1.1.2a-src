package org.crypticdev.stextras.mixin.tensura.storage.player;

import io.github.manasmods.tensura.storage.player.TensuraPlayerStorage;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TensuraPlayerStorage.class, remap = false)
public class TensuraPlayerStorageMixin {
   @Shadow(remap = false)
   private int bonusSkillLock;
   @Shadow(remap = false)
   private List<ResourceLocation> lockedSkills;

   @Inject(method = "getBonusSkillLock", at = @At("HEAD"), cancellable = true, remap = false)
   private void stextras$disableBonusSkillLock(CallbackInfoReturnable<Integer> cir) {
      this.bonusSkillLock = 0;
      cir.setReturnValue(0);
   }

   @Inject(method = "setBonusSkillLock", at = @At("HEAD"), cancellable = true, remap = false)
   private void stextras$ignoreBonusSkillLock(int value, CallbackInfo ci) {
      this.bonusSkillLock = 0;
      ci.cancel();
   }

   @Inject(method = "addLockedSkill", at = @At("HEAD"), cancellable = true, remap = false)
   private void stextras$blockLockedSkillAdd(ResourceLocation id, CallbackInfo ci) {
      this.lockedSkills.clear();
      ci.cancel();
   }

   @Inject(method = "getLockedSkills", at = @At("HEAD"), cancellable = true, remap = false)
   private void stextras$clearLockedSkillsView(CallbackInfoReturnable<List<ResourceLocation>> cir) {
      this.lockedSkills.clear();
      cir.setReturnValue(this.lockedSkills);
   }

   @Inject(method = "limitLockedSkills", at = @At("HEAD"), cancellable = true, remap = false)
   private void stextras$disableLockedSkillLimit(Level level, CallbackInfo ci) {
      this.lockedSkills.clear();
      ci.cancel();
   }

   @Inject(method = "load", at = @At("TAIL"), remap = false)
   private void stextras$clearBonusSkillLockOnLoad(CompoundTag tag, CallbackInfo ci) {
      this.bonusSkillLock = 0;
      this.lockedSkills.clear();
   }

   @Inject(method = "save", at = @At("HEAD"), remap = false)
   private void stextras$clearBonusSkillLockOnSave(CompoundTag tag, CallbackInfo ci) {
      this.bonusSkillLock = 0;
      this.lockedSkills.clear();
   }
}
