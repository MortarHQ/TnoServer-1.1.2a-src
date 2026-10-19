package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class ItemNetheriteFinder extends ItemImpl implements ICustomItemModel {
   public ItemNetheriteFinder() {
      super("netherite_finder", new Properties().stacksTo(1));
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      NaturesAuraAPI.IInternalHooks inst = NaturesAuraAPI.instance();
      if (!inst.extractAuraFromPlayer(playerIn, 200000, false)) {
         return new InteractionResultHolder(InteractionResult.FAIL, stack);
      }

      if (levelIn.isClientSide) {
         inst.setParticleDepth(false);
         inst.setParticleSpawnRange(64);
         inst.setParticleCulling(false);
         BlockPos pos = playerIn.blockPosition();
         int range = 12;

         for (int x = -range; x <= range; x++) {
            for (int y = 0; y <= 128; y++) {
               for (int z = -range; z <= range; z++) {
                  BlockPos offset = new BlockPos(pos.getX() + x, y, pos.getZ() + z);
                  BlockState state = levelIn.getBlockState(offset);
                  if (state.getBlock() == Blocks.ANCIENT_DEBRIS || BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString().contains("netherite")) {
                     inst.spawnMagicParticle(
                        offset.getX() + 0.5F, offset.getY() + 0.5F, offset.getZ() + 0.5F, 0.0, 0.0, 0.0, 11226424, 6.0F, 1200, 0.0F, false, true
                     );
                  }
               }
            }
         }

         inst.setParticleDepth(true);
         inst.setParticleSpawnRange(32);
         inst.setParticleCulling(true);
         playerIn.swing(handIn);
      }

      playerIn.getCooldowns().addCooldown(this, 1200);
      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
   }
}
