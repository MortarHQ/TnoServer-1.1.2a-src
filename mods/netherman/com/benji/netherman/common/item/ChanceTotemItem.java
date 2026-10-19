package com.benji.netherman.common.item;

import com.benji.netherman.common.entity.GuardianEntity;
import com.benji.netherman.common.network.TotemAnimationPayload;
import com.benji.netherman.init.ModEntities;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class ChanceTotemItem extends Item {
   public ChanceTotemItem(Properties properties) {
      super(properties.durability(2));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component spawnpoint = Component.translatable("tooltip.netherman.chance_totem.spawnpoint")
         .withStyle(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.WHITE});
      tooltipComponents.add(Component.translatable("tooltip.netherman.chance_totem.line1", new Object[]{spawnpoint}).withStyle(ChatFormatting.GRAY));
      tooltipComponents.add(Component.translatable("tooltip.netherman.chance_totem.line2").withStyle(ChatFormatting.DARK_RED));
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      ItemStack stack = context.getItemInHand();
      if (player != null && player.isShiftKeyDown()) {
         if (!level.isClientSide()) {
            GuardianEntity guardian = (GuardianEntity)((EntityType)ModEntities.GUARDIAN.get()).create(level);
            if (guardian != null) {
               guardian.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
               guardian.setOwnerUUID(player.getUUID());
               guardian.startSpawning();
               level.addFreshEntity(guardian);
            }

            if (player instanceof ServerPlayer serverPlayer) {
               PacketDistributor.sendToPlayer(serverPlayer, new TotemAnimationPayload(new ItemStack(this)), new CustomPacketPayload[0]);
            }

            EquipmentSlot slot = context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            stack.hurtAndBreak(1, player, slot);
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useOn(context);
      }
   }
}
