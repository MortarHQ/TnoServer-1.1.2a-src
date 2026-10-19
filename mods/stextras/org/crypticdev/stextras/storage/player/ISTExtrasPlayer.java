package org.crypticdev.stextras.storage.player;

import java.util.List;
import net.minecraft.resources.ResourceLocation;

public interface ISTExtrasPlayer {
   int getReincarnationEssence();

   void setReincarnationEssence(int var1);

   int getSoulGrade();

   void setSoulGrade(int var1);

   List<ResourceLocation> getLockedSkills();

   boolean addLockedSkill(ResourceLocation var1);

   boolean removeLockedSkill(ResourceLocation var1);

   void clearLockedSkills();

   List<ResourceLocation> getAdminLockedSkills();

   boolean addAdminLockedSkill(ResourceLocation var1);

   boolean removeAdminLockedSkill(ResourceLocation var1);

   void clearAdminLockedSkills();

   boolean stextras$isDirty();

   void stextras$clearDirty();

   void stextras$clearAllData();
}
