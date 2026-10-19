package de.ellpeck.naturesaura.items.tools;

import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class ItemShovel extends ShovelItem implements IModItem, ICustomItemModel {
   private final String baseName;

   public ItemShovel(String baseName, Tier material, float damage, float speed) {
      super(material, new Properties().attributes(ShovelItem.createAttributes(material, damage, speed)));
      this.baseName = baseName;
      ModRegistry.ALL_ITEMS.add(this);
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      Player player = context.getPlayer();
      ItemStack stack = player.getItemInHand(context.getHand());
      BlockPos pos = context.getClickedPos();
      BlockState state = level.getBlockState(pos);
      if ((this == ModItems.INFUSED_IRON_SHOVEL || this == ModItems.DEPTH_SHOVEL)
         && (state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.MYCELIUM)
         && level.getBlockState(pos.above()).isAir()) {
         level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
         float damage = 5.0F;
         if (this == ModItems.DEPTH_SHOVEL && !level.isClientSide) {
            ArrayList<BlockPos> possible = new ArrayList<>();

            for (int x = -5; x <= 5; x++) {
               for (int z = -5; z <= 5; z++) {
                  for (int y = -1; y <= 1; y++) {
                     BlockPos offset = pos.offset(x, y, z);
                     BlockState offState = level.getBlockState(offset);
                     if ((offState.getBlock() == Blocks.DIRT || offState.getBlock() == Blocks.MYCELIUM) && level.getBlockState(offset.above()).isAir()) {
                        possible.add(offset);
                     }
                  }
               }
            }

            for (int i = 0; i < 63 && !possible.isEmpty(); i++) {
               level.setBlockAndUpdate(possible.get(level.random.nextInt(possible.size())), Blocks.GRASS_BLOCK.defaultBlockState());
               damage += 0.25F;
            }
         }

         level.playSound(player, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
         stack.hurtAndBreak(Mth.ceil(damage), player, LivingEntity.getSlotForHand(context.getHand()));
         return InteractionResult.SUCCESS;
      } else {
         if (this == ModItems.INFUSED_IRON_SHOVEL) {
            boolean flattened = false;
            int range = player.isShiftKeyDown() ? 0 : 1;

            for (int x = -range; x <= range; x++) {
               for (int y = -range; y <= range; y++) {
                  BlockPos actualPos = pos.offset(x, 0, y);
                  Direction facing = context.getClickedFace();
                  if (player.mayUseItemAt(actualPos.relative(facing), facing, stack)
                     && facing != Direction.DOWN
                     && level.getBlockState(actualPos.above()).isAir()
                     && level.getBlockState(actualPos).getBlock() == Blocks.GRASS_BLOCK) {
                     if (!level.isClientSide) {
                        level.setBlock(actualPos, Blocks.DIRT_PATH.defaultBlockState(), 11);
                     }

                     flattened = true;
                  }
               }
            }

            if (flattened) {
               level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
               return InteractionResult.SUCCESS;
            }
         }

         if (this == ModItems.SKY_SHOVEL) {
            if (this.getDestroySpeed(stack, state) <= 1.0F) {
               return super.useOn(context);
            } else {
               InteractionHand otherHand = context.getHand() == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
               ItemStack other = player.getItemInHand(otherHand);
               if (!other.isEmpty() && other.getItem() instanceof BlockItem) {
                  level.removeBlock(pos, false);
                  BlockEntity tile = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                  Block.dropResources(state, level, pos, tile, null, stack);
                  UseOnContext newContext = new UseOnContext(
                     player, otherHand, new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside())
                  );
                  other.useOn(newContext);
                  stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                  return InteractionResult.SUCCESS;
               } else {
                  return super.useOn(context);
               }
            }
         } else {
            return super.useOn(context);
         }
      }
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
   }
}
