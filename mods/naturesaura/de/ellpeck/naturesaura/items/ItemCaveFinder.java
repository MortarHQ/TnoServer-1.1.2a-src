package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class ItemCaveFinder extends ItemImpl implements ICustomItemModel {
   public ItemCaveFinder() {
      super("cave_finder", new Properties().stacksTo(1));
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      NaturesAuraAPI.IInternalHooks inst = NaturesAuraAPI.instance();
      if (!inst.extractAuraFromPlayer(playerIn, 20000, levelIn.isClientSide)) {
         return new InteractionResultHolder(InteractionResult.FAIL, stack);
      }

      if (levelIn.isClientSide) {
         inst.setParticleDepth(false);
         inst.setParticleSpawnRange(64);
         inst.setParticleCulling(false);
         BlockPos pos = playerIn.blockPosition();
         int range = 30;

         for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
               for (int z = -range; z <= range; z++) {
                  BlockPos offset = pos.offset(x, y, z);
                  BlockState state = levelIn.getBlockState(offset);
                  if (state.isFaceSturdy(levelIn, offset, Direction.UP)) {
                     BlockPos offUp = offset.above();
                     BlockState stateUp = levelIn.getBlockState(offUp);
                     if (!stateUp.isCollisionShapeFullBlock(levelIn, offUp) && !stateUp.liquid()) {
                        int sky = levelIn.getBrightness(LightLayer.SKY, offUp);
                        int block = levelIn.getBrightness(LightLayer.BLOCK, offUp);
                        if (sky <= 0 && block <= 0) {
                           inst.spawnMagicParticle(
                              offset.getX() + 0.5F, offset.getY() + 1.5F, offset.getZ() + 0.5F, 0.0, 0.0, 0.0, 10035457, 2.5F, 600, 0.0F, false, true
                           );
                        }
                     }
                  }
               }
            }
         }

         inst.setParticleDepth(true);
         inst.setParticleSpawnRange(32);
         inst.setParticleCulling(true);
         playerIn.swing(handIn);
      }

      playerIn.getCooldowns().addCooldown(this, 600);
      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
   }
}
