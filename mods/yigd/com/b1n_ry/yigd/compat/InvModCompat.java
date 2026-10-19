package com.b1n_ry.yigd.compat;

import com.b1n_ry.yigd.compat.misc_compat_mods.TwilightCompat;
import com.b1n_ry.yigd.config.CompatConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.events.YigdEvents;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;

public interface InvModCompat<T> {
   List<InvModCompat<?>> invCompatMods = new ArrayList<>();

   static void reloadModCompat() {
      invCompatMods.clear();
      ModList modList = ModList.get();
      CompatConfig compatConfig = YigdConfig.getConfig().compatConfig;
      boolean accessoriesPresent = modList.isLoaded("accessories");
      boolean curiosPresent = modList.isLoaded("curios");
      boolean ccLayerLoaded = modList.isLoaded("cclayer");
      if (compatConfig.enableAccessoriesCompat && accessoriesPresent) {
         invCompatMods.add(new AccessoriesCompat());
      }

      if (compatConfig.enableCuriosCompat && curiosPresent && !ccLayerLoaded) {
         invCompatMods.add(new CuriosCompat());
      }

      if (modList.isLoaded("travelersbackpack")
         && compatConfig.enableTravelersBackpackCompat
         && (!accessoriesPresent && !curiosPresent || !TravelersBackpackCompat.isIntegrationEnabled())) {
         invCompatMods.add(new TravelersBackpackCompat());
      }

      if (modList.isLoaded("cosmeticarmorreworked") && compatConfig.enableCosmeticArmorCompat) {
         invCompatMods.add(new CosmeticArmorCompat());
      }

      if (modList.isLoaded("twilightforest")) {
         TwilightCompat.init();
      }

      NeoForge.EVENT_BUS.post(new YigdEvents.LoadModCompatEvent(invCompatMods));
   }

   String getModName();

   void clear(ServerPlayer var1);

   CompatComponent<T> readNbt(CompoundTag var1, Provider var2);

   CompatComponent<T> getNewComponent(ServerPlayer var1);
}
