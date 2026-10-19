package de.ellpeck.naturesaura.api.aura.chunk;

import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public interface IDrainSpotEffect {
   void update(Level var1, LevelChunk var2, IAuraChunk var3, BlockPos var4, Integer var5, IAuraChunk.DrainSpot var6);

   boolean appliesHere(LevelChunk var1, IAuraChunk var2, IAuraType var3);

   ResourceLocation getName();

   default IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      return IDrainSpotEffect.ActiveType.INACTIVE;
   }

   default ItemStack getDisplayIcon() {
      return ItemStack.EMPTY;
   }

   enum ActiveType {
      INACTIVE,
      INHIBITED,
      ACTIVE;
   }
}
