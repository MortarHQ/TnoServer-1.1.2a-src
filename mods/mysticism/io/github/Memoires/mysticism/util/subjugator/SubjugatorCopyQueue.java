package io.github.Memoires.mysticism.util.subjugator;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;

public class SubjugatorCopyQueue {
   public static final String NBT_COPY_FLAG = "CopyingPowerfulSkill";
   public static final String NBT_COPY_SEQ = "CopySeq";
   public static final String NBT_COPY_QUEUE = "CopyQueue";

   public static ListTag getQueue(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getList("CopyQueue", 10);
   }

   public static void setQueue(ManasSkillInstance instance, ListTag list) {
      instance.getOrCreateTag().put("CopyQueue", list);
      instance.markDirty();
   }

   public static void setAnyCopyFlag(ManasSkillInstance instance, boolean v) {
      instance.getOrCreateTag().putBoolean("CopyingPowerfulSkill", v);
      instance.markDirty();
   }

   public static boolean hasAnyCopies(ManasSkillInstance instance) {
      return !getQueue(instance).isEmpty();
   }

   public static int nextCopyId(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      int seq = tag.getInt("CopySeq") + 1;
      if (seq == Integer.MAX_VALUE) {
         seq = 1;
      }

      tag.putInt("CopySeq", seq);
      instance.markDirty();
      return seq;
   }

   public static int startCopy(ManasSkillInstance instance, UUID target) {
      int id = nextCopyId(instance);
      ListTag list = getQueue(instance);
      CompoundTag entry = new CompoundTag();
      entry.putInt("Id", id);
      entry.putUUID("Target", target);
      list.add(entry);
      setQueue(instance, list);
      setAnyCopyFlag(instance, true);
      return id;
   }

   public static boolean isCopyActive(ManasSkillInstance instance, int id) {
      ListTag list = getQueue(instance);

      for (int i = 0; i < list.size(); i++) {
         if (list.getCompound(i).getInt("Id") == id) {
            return true;
         }
      }

      return false;
   }

   public static void removeById(ManasSkillInstance instance, int id) {
      ListTag list = getQueue(instance);

      for (int i = 0; i < list.size(); i++) {
         if (list.getCompound(i).getInt("Id") == id) {
            list.remove(i);
            break;
         }
      }

      setQueue(instance, list);
      if (!hasAnyCopies(instance)) {
         setAnyCopyFlag(instance, false);
      }
   }

   public static boolean containsTarget(ManasSkillInstance instance, UUID target) {
      ListTag list = getQueue(instance);

      for (int i = 0; i < list.size(); i++) {
         if (list.getCompound(i).getUUID("Target").equals(target)) {
            return true;
         }
      }

      return false;
   }

   public static List<Integer> removeAllForTarget(ManasSkillInstance instance, UUID target) {
      ListTag list = getQueue(instance);
      List<Integer> removed = new ArrayList<>();

      for (int i = list.size() - 1; i >= 0; i--) {
         CompoundTag e = list.getCompound(i);
         if (e.getUUID("Target").equals(target)) {
            removed.add(e.getInt("Id"));
            list.remove(i);
         }
      }

      setQueue(instance, list);
      if (!hasAnyCopies(instance)) {
         setAnyCopyFlag(instance, false);
      }

      return removed;
   }

   public static List<Integer> clearAll(ManasSkillInstance instance) {
      List<Integer> removed = new ArrayList<>();
      ListTag list = getQueue(instance);

      for (int i = 0; i < list.size(); i++) {
         removed.add(list.getCompound(i).getInt("Id"));
      }

      setQueue(instance, new ListTag());
      setAnyCopyFlag(instance, false);
      return removed;
   }

   public static String timerKey(Entity owner, int id) {
      return "SubjugatorPowerfulSkill|" + owner.getUUID() + "|" + id;
   }

   public static int countForTarget(ManasSkillInstance instance, UUID target) {
      int count = 0;
      ListTag list = getQueue(instance);

      for (int i = 0; i < list.size(); i++) {
         if (list.getCompound(i).getUUID("Target").equals(target)) {
            count++;
         }
      }

      return count;
   }
}
