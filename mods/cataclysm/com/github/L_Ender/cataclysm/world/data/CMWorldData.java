package com.github.L_Ender.cataclysm.world.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class CMWorldData extends SavedData {
   private static final String IDENTIFIER = "cataclysm_world_data";
   private boolean LeviathanBossDefeatedOnce = false;
   private boolean IgnisBossDefeatedOnce = false;

   private CMWorldData() {
   }

   public static Factory<CMWorldData> factory() {
      return new Factory(CMWorldData::new, CMWorldData::load);
   }

   public static CMWorldData get(Level world, ResourceKey<Level> dim) {
      if (world instanceof ServerLevel serverLevel) {
         ServerLevel targetLevel = serverLevel.getServer().getLevel(dim);
         if (targetLevel == null) {
            targetLevel = serverLevel.getServer().overworld();
         }

         DimensionDataStorage storage = targetLevel.getDataStorage();
         CMWorldData data = (CMWorldData)storage.computeIfAbsent(factory(), "cataclysm_world_data");
         if (data != null) {
            data.setDirty();
         }

         return data;
      } else {
         return null;
      }
   }

   public static CMWorldData load(CompoundTag nbt, Provider p_323806_) {
      CMWorldData data = new CMWorldData();
      data.LeviathanBossDefeatedOnce = nbt.getBoolean("LeviathanDefeatedOnce");
      data.IgnisBossDefeatedOnce = nbt.getBoolean("IgnisDefeatedOnce");
      return data;
   }

   public CompoundTag save(CompoundTag compound, Provider p_323890_) {
      compound.putBoolean("LeviathanDefeatedOnce", this.LeviathanBossDefeatedOnce);
      compound.putBoolean("IgnisDefeatedOnce", this.IgnisBossDefeatedOnce);
      return compound;
   }

   public boolean isLeviathanDefeatedOnce() {
      return this.LeviathanBossDefeatedOnce;
   }

   public void setLeviathanDefeatedOnce(boolean defeatedOnce) {
      this.LeviathanBossDefeatedOnce = defeatedOnce;
   }

   public boolean isIgnisDefeatedOnce() {
      return this.IgnisBossDefeatedOnce;
   }

   public void setIgnisDefeatedOnce(boolean defeatedOnce) {
      this.IgnisBossDefeatedOnce = defeatedOnce;
   }
}
