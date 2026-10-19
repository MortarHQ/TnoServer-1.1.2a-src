package org.crypticdev.stextras.quest.definition;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class QuestRewardSkill {
   public ResourceLocation id;
   public double mastery = -1.0;
   public int removeTime = -1;
   public boolean noMagiculeCost = false;

   public CompoundTag toNbt() {
      CompoundTag tag = new CompoundTag();
      if (this.id != null) {
         tag.putString("id", this.id.toString());
      }

      if (this.mastery >= 0.0) {
         tag.putDouble("mastery", this.mastery);
      }

      if (this.removeTime >= 0) {
         tag.putInt("removeTime", this.removeTime);
      }

      if (this.noMagiculeCost) {
         tag.putBoolean("noMagiculeCost", true);
      }

      return tag;
   }

   public static QuestRewardSkill fromNbt(CompoundTag tag) {
      QuestRewardSkill skill = new QuestRewardSkill();
      if (tag.contains("id")) {
         skill.id = ResourceLocation.parse(tag.getString("id"));
      }

      if (tag.contains("mastery")) {
         skill.mastery = tag.getDouble("mastery");
      }

      if (tag.contains("removeTime")) {
         skill.removeTime = tag.getInt("removeTime");
      }

      if (tag.contains("noMagiculeCost")) {
         skill.noMagiculeCost = tag.getBoolean("noMagiculeCost");
      }

      return skill;
   }
}
