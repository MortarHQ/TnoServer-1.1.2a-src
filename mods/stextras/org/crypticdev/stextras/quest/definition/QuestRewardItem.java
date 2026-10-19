package org.crypticdev.stextras.quest.definition;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class QuestRewardItem {
   public ResourceLocation id;
   public int count = 1;

   public CompoundTag toNbt() {
      CompoundTag tag = new CompoundTag();
      if (this.id != null) {
         tag.putString("id", this.id.toString());
      }

      tag.putInt("count", this.count);
      return tag;
   }

   public static QuestRewardItem fromNbt(CompoundTag tag) {
      QuestRewardItem item = new QuestRewardItem();
      if (tag.contains("id")) {
         item.id = ResourceLocation.parse(tag.getString("id"));
      }

      item.count = tag.getInt("count");
      return item;
   }
}
