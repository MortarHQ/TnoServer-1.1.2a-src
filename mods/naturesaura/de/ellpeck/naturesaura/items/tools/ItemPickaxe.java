package de.ellpeck.naturesaura.items.tools;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.misc.LevelData;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.Tags.Blocks;

public class ItemPickaxe extends PickaxeItem implements IModItem, ICustomItemModel {
   private final String baseName;

   public ItemPickaxe(String baseName, Tier material, int damage, float speed) {
      super(material, new Properties().attributes(PickaxeItem.createAttributes(material, damage, speed)));
      this.baseName = baseName;
      ModRegistry.ALL_ITEMS.add(this);
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   public InteractionResult useOn(UseOnContext context) {
      if (this == ModItems.INFUSED_IRON_PICKAXE) {
         Player player = context.getPlayer();
         Level level = context.getLevel();
         BlockPos pos = context.getClickedPos();
         ItemStack stack = player.getItemInHand(context.getHand());
         BlockState state = level.getBlockState(pos);
         BlockState result = (BlockState)NaturesAuraAPI.BOTANIST_PICKAXE_CONVERSIONS.get(state);
         if (result != null) {
            if (!level.isClientSide) {
               level.setBlockAndUpdate(pos, result);
               LevelData data = (LevelData)ILevelData.getLevelData(level);
               data.addMossStone(pos);
            }

            level.playSound(player, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.hurtAndBreak(15, player, LivingEntity.getSlotForHand(context.getHand()));
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   public void inventoryTick(ItemStack stack, Level levelIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (this == ModItems.SKY_PICKAXE) {
         if (!(entityIn instanceof Player)) {
            return;
         }

         if (!isSelected || levelIn.isClientSide) {
            return;
         }

         AABB bounds = new AABB(entityIn.blockPosition()).inflate(4.0);

         for (ItemEntity item : levelIn.getEntitiesOfClass(ItemEntity.class, bounds)) {
            if (item.tickCount < 5 && item.isAlive()) {
               item.setPickUpDelay(0);
               item.playerTouch((Player)entityIn);
            }
         }
      }
   }

   public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
      if (stack.getItem() == ModItems.DEPTH_PICKAXE && Helper.isToolEnabled(stack) && level.getBlockState(pos).is(Blocks.ORES)) {
         Helper.mineRecursively(level, pos, pos, stack, 5, 5, s -> s.is(Blocks.ORES));
      }

      return super.mineBlock(stack, level, state, pos, miningEntity);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      return stack.getItem() == ModItems.DEPTH_PICKAXE && Helper.toggleToolEnabled(player, stack)
         ? InteractionResultHolder.success(stack)
         : super.use(level, player, hand);
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      if (this != ModItems.DEPTH_PICKAXE) {
         ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
      }
   }
}
