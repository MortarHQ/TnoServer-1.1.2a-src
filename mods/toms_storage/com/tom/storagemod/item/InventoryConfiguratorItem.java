package com.tom.storagemod.item;

import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.InventoryConnectorBlockEntity;
import com.tom.storagemod.client.ClientUtil;
import com.tom.storagemod.components.ConfiguratorComponent;
import com.tom.storagemod.inventory.BlockFilter;
import com.tom.storagemod.inventory.PlatformInventoryAccess;
import com.tom.storagemod.menu.InventoryConfiguratorMenu;
import com.tom.storagemod.platform.PlatformItem;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class InventoryConfiguratorItem extends PlatformItem implements ILeftClickListener {
   public InventoryConfiguratorItem() {
      super(new Properties().stacksTo(1).component(Content.configuratorComponent.get(), ConfiguratorComponent.empty()));
   }

   @Override
   public InteractionResult onRightClick(Player player, ItemStack stack, BlockPos pos, InteractionHand hand) {
      if (!player.level().isClientSide) {
         this.action(player.level(), player, stack, true, pos, hand);
      }

      return InteractionResult.SUCCESS;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      this.action(level, player, player.getItemInHand(hand), true, null, hand);
      return InteractionResultHolder.success(player.getItemInHand(hand));
   }

   public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
      return false;
   }

   @Override
   public boolean onLeftClick(ItemStack itemstack, BlockPos pos, Player player) {
      if (!player.level().isClientSide) {
         this.action(player.level(), player, itemstack, false, pos, InteractionHand.MAIN_HAND);
      }

      return true;
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
      if (!level.isClientSide
         && entity instanceof Player player
         && stack != player.getItemInHand(InteractionHand.MAIN_HAND)
         && stack != player.getItemInHand(InteractionHand.OFF_HAND)) {
         ConfiguratorComponent c = (ConfiguratorComponent)stack.get(Content.configuratorComponent.get());
         if (c.showInvBox() || c.massSelect()) {
            stack.set(Content.configuratorComponent.get(), c.hiddenItem(level.getGameTime()));
         }
      }
   }

   private void action(Level level, Player player, ItemStack stack, boolean rclk, BlockPos useOn, InteractionHand hand) {
      ConfiguratorComponent.Configurator c = new ConfiguratorComponent.Configurator(stack, level.getGameTime());
      if (!c.debounce()) {
         if (c.isBound()) {
            int x = c.bound().getX();
            int y = c.bound().getY();
            int z = c.bound().getZ();
            BlockPos pos = new BlockPos(x, y, z);
            if (player.distanceToSqr(x, y, z) < 256.0 && level.isLoaded(pos)) {
               BlockFilter f = BlockFilter.getOrCreateFilterAt(level, pos);
               if (f == null) {
                  stack.set(Content.configuratorComponent.get(), ConfiguratorComponent.empty());
               } else if (useOn != null && c.selecting() && (!player.isSecondaryUseActive() || !rclk)) {
                  if (!rclk) {
                     if (!player.isSecondaryUseActive()) {
                        if (c.massSelect()) {
                           int sx = c.boxStart().getX();
                           int sy = c.boxStart().getY();
                           int sz = c.boxStart().getZ();
                           AABB bb = new AABB(sx, sy, sz, useOn.getX(), useOn.getY(), useOn.getZ());
                           if (bb.getXsize() * bb.getYsize() * bb.getZsize() < 64.0) {
                              BlockPos.betweenClosedStream(bb).forEach(p -> f.addConnected(level, p));
                              level.blockEntityChanged(pos);
                              c.massSelectEnd(f.getConnectedBlocks());
                           } else {
                              player.displayClientMessage(Component.translatable("chat.toms_storage.area_too_big"), true);
                              c.massSelectEnd();
                           }
                        } else {
                           c.massSelectStart(useOn);
                        }

                        return;
                     }

                     c.massSelectEnd();
                     return;
                  }

                  if (!useOn.equals(pos)) {
                     if (!f.getConnectedBlocks().remove(useOn)) {
                        f.addConnected(level, useOn);
                     }

                     c.setSelection(f.getConnectedBlocks());
                     level.blockEntityChanged(pos);
                     return;
                  }

                  c.clear();
               } else if (rclk) {
                  this.openMenu(level, player, pos, f, hand);
                  c.clear();
                  return;
               }
            }
         }

         if (useOn != null && player.distanceToSqr(useOn.getX(), useOn.getY(), useOn.getZ()) < 256.0) {
            if (!rclk && level.getBlockEntity(useOn) instanceof InventoryConnectorBlockEntity ic) {
               c.clear();
               c.showInvBox(ic.getConnectedBlocks());
               return;
            }

            BlockFilter f = BlockFilter.findBlockFilterAt(level, useOn);
            if (f == null) {
               return;
            }

            if (rclk) {
               this.openMenu(level, player, useOn, f, hand);
               f.getConnectedBlocks().forEach(posx -> {
                  if (!posx.equals(f.getMainPos())) {
                     PlatformInventoryAccess.removeBlockFilterAt(level, posx);
                  }
               });
            }

            c.showInvBox(f.getConnectedBlocks());
         }
      }
   }

   private void openMenu(Level level, Player player, final BlockPos pos, final BlockFilter f, final InteractionHand hand) {
      BlockEntity be = level.getBlockEntity(pos);
      Component blockName = null;
      if (be instanceof Nameable n) {
         blockName = n.getDisplayName();
      }

      if (blockName == null) {
         blockName = level.getBlockState(pos).getBlock().getName();
      }

      final Component fbn = blockName;
      player.openMenu(new MenuProvider() {
         public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
            return new InventoryConfiguratorMenu(p_39954_, p_39955_, pos, f, hand);
         }

         public Component getDisplayName() {
            return Component.translatable("menu.toms_storage.inventory_configurator", new Object[]{fbn});
         }
      });
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      ClientUtil.tooltip("inventory_configurator", tooltip);
   }
}
