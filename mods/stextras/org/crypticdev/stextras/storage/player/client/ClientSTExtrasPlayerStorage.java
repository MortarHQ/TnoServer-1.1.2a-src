package org.crypticdev.stextras.storage.player.client;

import java.util.List;
import net.minecraft.resources.ResourceLocation;

public interface ClientSTExtrasPlayerStorage {
   int getReincarnationEssence();

   void setReincarnationEssence(int var1);

   int getSoulGrade();

   void setSoulGrade(int var1);

   List<ResourceLocation> getLockedSkills();

   void setLockedSkills(List<ResourceLocation> var1);

   List<ResourceLocation> getAdminLockedSkills();

   void setAdminLockedSkills(List<ResourceLocation> var1);
}
