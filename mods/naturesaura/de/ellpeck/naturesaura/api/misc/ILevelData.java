package de.ellpeck.naturesaura.api.misc;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public interface ILevelData {
   static ILevelData getLevelData(Level level) {
      return NaturesAuraAPI.instance().getLevelData(level);
   }

   static ILevelData getOverworldData(Level level) {
      return !level.isClientSide ? getLevelData(level.getServer().getLevel(Level.OVERWORLD)) : getLevelData(level);
   }

   IItemHandlerModifiable getEnderStorage(String var1);

   boolean isEnderStorageLocked(String var1);
}
