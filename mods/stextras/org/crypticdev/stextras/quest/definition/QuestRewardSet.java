package org.crypticdev.stextras.quest.definition;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class QuestRewardSet {
   public List<QuestRewardItem> items;
   public List<QuestRewardSkill> skills;
   public Integer re;
   public Integer xp;
   public List<String> commands;

   public boolean isEmpty() {
      return (this.items == null || this.items.isEmpty())
         && (this.skills == null || this.skills.isEmpty())
         && (this.re == null || this.re <= 0)
         && (this.xp == null || this.xp <= 0)
         && (this.commands == null || this.commands.isEmpty());
   }

   public CompoundTag toNbt() {
      CompoundTag tag = new CompoundTag();
      if (this.re != null && this.re > 0) {
         tag.putInt("re", this.re);
      }

      if (this.xp != null && this.xp > 0) {
         tag.putInt("xp", this.xp);
      }

      if (this.items != null && !this.items.isEmpty()) {
         ListTag itemList = new ListTag();

         for (QuestRewardItem item : this.items) {
            itemList.add(item.toNbt());
         }

         tag.put("items", itemList);
      }

      if (this.skills != null && !this.skills.isEmpty()) {
         ListTag skillList = new ListTag();

         for (QuestRewardSkill skill : this.skills) {
            skillList.add(skill.toNbt());
         }

         tag.put("skills", skillList);
      }

      if (this.commands != null && !this.commands.isEmpty()) {
         ListTag cmdList = new ListTag();

         for (String cmd : this.commands) {
            cmdList.add(StringTag.valueOf(cmd));
         }

         tag.put("commands", cmdList);
      }

      return tag;
   }

   public static QuestRewardSet fromNbt(CompoundTag tag) {
      QuestRewardSet rewards = new QuestRewardSet();
      if (tag.contains("re")) {
         rewards.re = tag.getInt("re");
      }

      if (tag.contains("xp")) {
         rewards.xp = tag.getInt("xp");
      }

      rewards.items = new ArrayList<>();
      if (tag.contains("items")) {
         ListTag itemList = tag.getList("items", 10);

         for (int i = 0; i < itemList.size(); i++) {
            rewards.items.add(QuestRewardItem.fromNbt(itemList.getCompound(i)));
         }
      }

      rewards.skills = new ArrayList<>();
      if (tag.contains("skills")) {
         ListTag skillList = tag.getList("skills", 10);

         for (int i = 0; i < skillList.size(); i++) {
            rewards.skills.add(QuestRewardSkill.fromNbt(skillList.getCompound(i)));
         }
      }

      rewards.commands = new ArrayList<>();
      if (tag.contains("commands")) {
         ListTag cmdList = tag.getList("commands", 8);

         for (int i = 0; i < cmdList.size(); i++) {
            rewards.commands.add(cmdList.getString(i));
         }
      }

      return rewards;
   }
}
