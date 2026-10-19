package de.ellpeck.naturesaura.items;

import com.mojang.datafixers.util.Pair;
import de.ellpeck.naturesaura.entities.EntityStructureFinder;
import de.ellpeck.naturesaura.entities.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ItemStructureFinder extends ItemImpl {
   private final ResourceKey<Structure> structure;
   private final int color;

   public ItemStructureFinder(String baseName, ResourceKey<Structure> structure, int color) {
      super(baseName);
      this.structure = structure;
      this.color = color;
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      if (!levelIn.isClientSide && ((ServerLevel)levelIn).structureManager().shouldGenerateStructures()) {
         Registry<Structure> registry = levelIn.registryAccess().registryOrThrow(Registries.STRUCTURE);
         Direct<Structure> holderSet = registry.getHolder(this.structure).map(xva$0 -> HolderSet.direct(new Holder[]{xva$0})).orElse(null);
         if (holderSet != null) {
            Pair<BlockPos, Holder<Structure>> pos = ((ServerLevel)levelIn)
               .getChunkSource()
               .getGenerator()
               .findNearestMapStructure((ServerLevel)levelIn, holderSet, playerIn.blockPosition(), 100, false);
            if (pos != null) {
               EntityStructureFinder entity = new EntityStructureFinder(ModEntities.STRUCTURE_FINDER, levelIn);
               entity.setPos(playerIn.getX(), playerIn.getY(0.5), playerIn.getZ());
               entity.setItem(stack);
               entity.getEntityData().set(EntityStructureFinder.COLOR, this.color);
               entity.signalTo(((BlockPos)pos.getFirst()).above(64));
               levelIn.addFreshEntity(entity);
               stack.shrink(1);
            }
         }
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }
}
