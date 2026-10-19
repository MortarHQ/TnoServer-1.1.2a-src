package com.notunanancyowen.spears.dataholders;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;

public interface SpearUser {
   float getTimeSinceLastKineticAttack(float var1);

   boolean isInPiercingCooldown(Entity var1, int var2);

   void startPiercingCooldown(Entity var1);

   int countSpearedMobs();

   boolean pierce(EquipmentSlot var1, Entity var2, float var3, boolean var4, boolean var5, boolean var6);
}
