package de.ellpeck.naturesaura.items.tools;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;

public class ItemAxe extends AxeItem implements IModItem, ICustomItemModel {
   private final String baseName;

   public ItemAxe(String baseName, Tier material, float damage, float speed) {
      super(material, new Properties().attributes(AxeItem.createAttributes(material, damage, speed)));
      this.baseName = baseName;
      ModRegistry.ALL_ITEMS.add(this);
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      return state.is(BlockTags.LEAVES) ? this.getTier().getSpeed() : super.getDestroySpeed(stack, state);
   }

   public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
      if ((stack.getItem() == ModItems.SKY_AXE || stack.getItem() == ModItems.DEPTH_AXE)
         && Helper.isToolEnabled(stack)
         && level.getBlockState(pos).is(BlockTags.LOGS)) {
         int horRange = stack.getItem() == ModItems.DEPTH_AXE ? 6 : 1;
         Helper.mineRecursively(level, pos, pos, stack, horRange, 32, s -> s.is(BlockTags.LOGS));
      }

      return super.mineBlock(stack, level, state, pos, miningEntity);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      return (stack.getItem() == ModItems.SKY_AXE || stack.getItem() == ModItems.DEPTH_AXE) && Helper.toggleToolEnabled(player, stack)
         ? InteractionResultHolder.success(stack)
         : super.use(level, player, hand);
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      if (this != ModItems.SKY_AXE && this != ModItems.DEPTH_AXE) {
         ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
      }
   }
}
