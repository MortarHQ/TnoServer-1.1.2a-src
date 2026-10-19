package org.crypticdev.stextras.mixin.client.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.storage.player.client.ClientSTExtrasPlayerStorage;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.storage.quest.client.ClientSTExtrasQuests;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements ClientSTExtrasPlayerStorage, ClientSTExtrasQuests {
   @Unique
   private int stextras$re;
   @Unique
   private int stextras$soulGrade;
   @Unique
   private final List<ResourceLocation> stextras$lockedSkills = new ArrayList<>();
   @Unique
   private final List<ResourceLocation> stextras$adminLockedSkills = new ArrayList<>();
   @Unique
   private final STExtrasQuestStorage stextras$clientQuests = new STExtrasQuestStorage();

   @Override
   public int getReincarnationEssence() {
      return this.stextras$re;
   }

   @Override
   public void setReincarnationEssence(int amount) {
      this.stextras$re = Math.max(0, amount);
   }

   @Override
   public int getSoulGrade() {
      return this.stextras$soulGrade;
   }

   @Override
   public void setSoulGrade(int value) {
      this.stextras$soulGrade = Math.max(0, value);
   }

   @Override
   public List<ResourceLocation> getLockedSkills() {
      return Collections.unmodifiableList(this.stextras$lockedSkills);
   }

   @Override
   public void setLockedSkills(List<ResourceLocation> list) {
      this.stextras$lockedSkills.clear();
      if (list != null) {
         this.stextras$lockedSkills.addAll(list);
      }
   }

   @Override
   public List<ResourceLocation> getAdminLockedSkills() {
      return Collections.unmodifiableList(this.stextras$adminLockedSkills);
   }

   @Override
   public void setAdminLockedSkills(List<ResourceLocation> list) {
      this.stextras$adminLockedSkills.clear();
      if (list != null) {
         this.stextras$adminLockedSkills.addAll(list);
      }
   }

   @Override
   public STExtrasQuestStorage stextras$clientQuests() {
      return this.stextras$clientQuests;
   }
}
