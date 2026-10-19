package com.tno_tensura_extras.item;

import com.tno_tensura_extras.compat.ProtectionCompat;
import com.tno_tensura_extras.data.BoundCargo;
import com.tno_tensura_extras.registry.ExtrasDataComponents;
import com.tno_tensura_extras.util.CargoTeleportHandler;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TeleportMagicTubeItem extends Item {
   private static final int VALIDATE_INTERVAL = 20;
   private static final int DELIVERY_INTERVAL = 10;

   public TeleportMagicTubeItem(Properties properties) {
      super(properties);
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      if (player == null || !player.isSecondaryUseActive()) {
         return InteractionResult.PASS;
      }

      if (!CargoTeleportHandler.isCargoChest(level, pos)) {
         return InteractionResult.PASS;
      }

      if (!level.isClientSide()) {
         if (!ProtectionCompat.mayBind(player, pos)) {
            return InteractionResult.FAIL;
         }

         context.getItemInHand().set(ExtrasDataComponents.BOUND_CARGO.get(), new BoundCargo(level.dimension(), pos.immutable(), player.getUUID()));
         level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      return InteractionResult.sidedSuccess(level.isClientSide());
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      if (!entity.level().isClientSide() && entity.onGround() && entity.tickCount % 10 == 0 && CargoTeleportHandler.deliver(entity, stack)) {
         entity.discard();
         return true;
      } else {
         return false;
      }
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (!level.isClientSide() && level.getGameTime() % 20L == 0L) {
         BoundCargo bound = (BoundCargo)stack.get(ExtrasDataComponents.BOUND_CARGO.get());
         if (bound != null) {
            MinecraftServer server = level.getServer();
            if (server != null) {
               ServerLevel boundLevel = server.getLevel(bound.dimension());
               if (boundLevel != null && boundLevel.hasChunk(bound.pos().getX() >> 4, bound.pos().getZ() >> 4)) {
                  if (!CargoTeleportHandler.isCargoChest(boundLevel, bound.pos())) {
                     stack.remove(ExtrasDataComponents.BOUND_CARGO.get());
                  }
               }
            }
         }
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(Component.translatable("item.tno_tensura_extras.teleport_magic_tube.desc").withStyle(ChatFormatting.GRAY));
      BoundCargo bound = (BoundCargo)stack.get(ExtrasDataComponents.BOUND_CARGO.get());
      if (bound != null) {
         BlockPos pos = bound.pos();
         tooltipComponents.add(
            Component.translatable("item.tno_tensura_extras.teleport_magic_tube.bound", new Object[]{pos.getX(), pos.getY(), pos.getZ()})
               .withStyle(ChatFormatting.AQUA)
         );
      }
   }
}
