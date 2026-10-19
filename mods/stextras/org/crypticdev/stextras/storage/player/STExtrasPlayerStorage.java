package org.crypticdev.stextras.storage.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

public class STExtrasPlayerStorage implements ISTExtrasPlayer {
   private static final int DATA_VERSION = 3;
   private int reincarnationEssence;
   private int soulGrade;
   private final List<ResourceLocation> lockedSkills = new ArrayList<>();
   private final List<ResourceLocation> adminLockedSkills = new ArrayList<>();
   private boolean dirty;

   @Override
   public int getReincarnationEssence() {
      return this.reincarnationEssence;
   }

   @Override
   public void setReincarnationEssence(int amount) {
      int clamped = Math.max(0, amount);
      if (clamped != this.reincarnationEssence) {
         this.reincarnationEssence = clamped;
         this.dirty = true;
      }
   }

   @Override
   public int getSoulGrade() {
      return this.soulGrade;
   }

   @Override
   public void setSoulGrade(int value) {
      int clamped = Math.max(0, value);
      if (clamped != this.soulGrade) {
         this.soulGrade = clamped;
         this.dirty = true;
      }
   }

   @Override
   public List<ResourceLocation> getLockedSkills() {
      return Collections.unmodifiableList(this.lockedSkills);
   }

   @Override
   public boolean addLockedSkill(ResourceLocation id) {
      Objects.requireNonNull(id, "locked skill id");
      if (!this.lockedSkills.contains(id)) {
         boolean ok = this.lockedSkills.add(id);
         if (ok) {
            this.dirty = true;
         }

         return ok;
      } else {
         return false;
      }
   }

   @Override
   public boolean removeLockedSkill(ResourceLocation id) {
      boolean ok = this.lockedSkills.remove(id);
      if (ok) {
         this.dirty = true;
      }

      return ok;
   }

   @Override
   public void clearLockedSkills() {
      if (!this.lockedSkills.isEmpty()) {
         this.lockedSkills.clear();
         this.dirty = true;
      }
   }

   @Override
   public List<ResourceLocation> getAdminLockedSkills() {
      return Collections.unmodifiableList(this.adminLockedSkills);
   }

   @Override
   public boolean addAdminLockedSkill(ResourceLocation id) {
      Objects.requireNonNull(id, "admin locked skill id");
      if (!this.adminLockedSkills.contains(id)) {
         boolean ok = this.adminLockedSkills.add(id);
         if (ok) {
            this.dirty = true;
         }

         return ok;
      } else {
         return false;
      }
   }

   @Override
   public boolean removeAdminLockedSkill(ResourceLocation id) {
      boolean ok = this.adminLockedSkills.remove(id);
      if (ok) {
         this.dirty = true;
      }

      return ok;
   }

   @Override
   public void clearAdminLockedSkills() {
      if (!this.adminLockedSkills.isEmpty()) {
         this.adminLockedSkills.clear();
         this.dirty = true;
      }
   }

   @Override
   public void stextras$clearAllData() {
      this.reincarnationEssence = 0;
      this.soulGrade = 0;
      this.lockedSkills.clear();
      this.adminLockedSkills.clear();
      this.dirty = true;
   }

   @Override
   public boolean stextras$isDirty() {
      return this.dirty;
   }

   @Override
   public void stextras$clearDirty() {
      this.dirty = false;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public void clearDirty() {
      this.dirty = false;
   }

   public void writeNbt(CompoundTag root) {
      CompoundTag tag = root.getCompound("stextras:player");
      tag.putInt("data_version", 3);
      tag.putInt("reincarnation_essence", this.reincarnationEssence);
      tag.putInt("soul_grade", this.soulGrade);
      ListTag list = new ListTag();

      for (ResourceLocation rl : this.lockedSkills) {
         list.add(StringTag.valueOf(rl.toString()));
      }

      tag.put("locked_skills", list);
      ListTag adminList = new ListTag();

      for (ResourceLocation rl : this.adminLockedSkills) {
         adminList.add(StringTag.valueOf(rl.toString()));
      }

      tag.put("admin_locked_skills", adminList);
      root.put("stextras:player", tag);
      this.dirty = false;
   }

   public void readNbt(CompoundTag root) {
      if (root.contains("stextras:player")) {
         CompoundTag tag = root.getCompound("stextras:player");
         this.reincarnationEssence = tag.getInt("reincarnation_essence");
         this.soulGrade = tag.getInt("soul_grade");
         this.lockedSkills.clear();
         if (tag.contains("locked_skills")) {
            ListTag list = tag.getList("locked_skills", 8);

            for (int i = 0; i < list.size(); i++) {
               String s = list.getString(i);

               try {
                  ResourceLocation rl = ResourceLocation.tryParse(s);
                  if (rl != null) {
                     this.lockedSkills.add(rl);
                  }
               } catch (Exception var8) {
               }
            }
         }

         this.adminLockedSkills.clear();
         if (tag.contains("admin_locked_skills")) {
            ListTag list = tag.getList("admin_locked_skills", 8);

            for (int i = 0; i < list.size(); i++) {
               String s = list.getString(i);

               try {
                  ResourceLocation rl = ResourceLocation.tryParse(s);
                  if (rl != null) {
                     this.adminLockedSkills.add(rl);
                  }
               } catch (Exception var7) {
               }
            }
         }

         this.dirty = false;
      }
   }

   public CompoundTag toNet() {
      CompoundTag t = new CompoundTag();
      t.putInt("data_version", 3);
      t.putInt("reincarnation_essence", this.reincarnationEssence);
      t.putInt("soul_grade", this.soulGrade);
      ListTag list = new ListTag();

      for (ResourceLocation rl : this.lockedSkills) {
         list.add(StringTag.valueOf(rl.toString()));
      }

      t.put("locked_skills", list);
      ListTag adminList = new ListTag();

      for (ResourceLocation rl : this.adminLockedSkills) {
         adminList.add(StringTag.valueOf(rl.toString()));
      }

      t.put("admin_locked_skills", adminList);
      return t;
   }

   public void fromNet(CompoundTag t) {
      this.reincarnationEssence = t.getInt("reincarnation_essence");
      this.soulGrade = t.getInt("soul_grade");
      this.lockedSkills.clear();
      if (t.contains("locked_skills")) {
         ListTag list = t.getList("locked_skills", 8);

         for (int i = 0; i < list.size(); i++) {
            String s = list.getString(i);

            try {
               ResourceLocation rl = ResourceLocation.tryParse(s);
               if (rl != null) {
                  this.lockedSkills.add(rl);
               }
            } catch (Exception var7) {
            }
         }
      }

      this.adminLockedSkills.clear();
      if (t.contains("admin_locked_skills")) {
         ListTag list = t.getList("admin_locked_skills", 8);

         for (int i = 0; i < list.size(); i++) {
            String s = list.getString(i);

            try {
               ResourceLocation rl = ResourceLocation.tryParse(s);
               if (rl != null) {
                  this.adminLockedSkills.add(rl);
               }
            } catch (Exception var6) {
            }
         }
      }
   }
}
