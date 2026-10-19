package org.crypticdev.stextras.mixin.server.level;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.net.S2C.STExtrasPlayerStorageSync;
import org.crypticdev.stextras.storage.player.ISTExtrasPlayer;
import org.crypticdev.stextras.storage.player.STExtrasPlayerStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements ISTExtrasPlayer, ISTExtrasQuests {
   @Unique
   private final STExtrasQuestStorage stextras$quests = new STExtrasQuestStorage();
   @Unique
   private final STExtrasPlayerStorage stextras$storage = new STExtrasPlayerStorage();

   @Override
   public int getReincarnationEssence() {
      return this.stextras$storage.getReincarnationEssence();
   }

   @Override
   public void setReincarnationEssence(int amount) {
      this.stextras$storage.setReincarnationEssence(amount);
   }

   @Override
   public int getSoulGrade() {
      return this.stextras$storage.getSoulGrade();
   }

   @Override
   public void setSoulGrade(int value) {
      this.stextras$storage.setSoulGrade(value);
   }

   @Override
   public List<ResourceLocation> getLockedSkills() {
      return this.stextras$storage.getLockedSkills();
   }

   @Override
   public boolean addLockedSkill(ResourceLocation id) {
      return this.stextras$storage.addLockedSkill(id);
   }

   @Override
   public boolean removeLockedSkill(ResourceLocation id) {
      return this.stextras$storage.removeLockedSkill(id);
   }

   @Override
   public void clearLockedSkills() {
      this.stextras$storage.clearLockedSkills();
   }

   @Override
   public List<ResourceLocation> getAdminLockedSkills() {
      return this.stextras$storage.getAdminLockedSkills();
   }

   @Override
   public boolean addAdminLockedSkill(ResourceLocation id) {
      return this.stextras$storage.addAdminLockedSkill(id);
   }

   @Override
   public boolean removeAdminLockedSkill(ResourceLocation id) {
      return this.stextras$storage.removeAdminLockedSkill(id);
   }

   @Override
   public void clearAdminLockedSkills() {
      this.stextras$storage.clearAdminLockedSkills();
   }

   @Override
   public boolean stextras$isDirty() {
      return this.stextras$storage.isDirty();
   }

   @Override
   public void stextras$clearDirty() {
      this.stextras$storage.clearDirty();
   }

   @Override
   public void stextras$clearAllData() {
      this.stextras$storage.stextras$clearAllData();
   }

   @Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("HEAD"))
   private void stextras$writeExtraData(CompoundTag root, CallbackInfo ci) {
      this.stextras$storage.writeNbt(root);
   }

   @Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("HEAD"))
   private void stextras$readExtraData(CompoundTag root, CallbackInfo ci) {
      this.stextras$storage.readNbt(root);
   }

   @Override
   public STExtrasQuestStorage stextras$getQuests() {
      return this.stextras$quests;
   }

   @Inject(method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
   private void stextras$saveQuests(CompoundTag tag, CallbackInfo ci) {
      this.stextras$quests.writeNbt(tag);
   }

   @Inject(method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
   private void stextras$loadQuests(CompoundTag tag, CallbackInfo ci) {
      this.stextras$quests.readNbt(tag);
   }

   @Inject(method = "tick()V", at = @At("RETURN"))
   private void stextras$tickSync(CallbackInfo ci) {
      ServerPlayer self = (ServerPlayer)this;
      if (this.stextras$storage.isDirty()) {
         STExtrasPlayerStorageSync.sendTo(self, this.stextras$storage.toNet());
         this.stextras$storage.clearDirty();
      }

      if (this.stextras$quests.isDirty()) {
         STExtrasQuestSync.sendTo(self, this.stextras$quests.toNet());
         this.stextras$quests.clearDirty();
      }
   }
}
