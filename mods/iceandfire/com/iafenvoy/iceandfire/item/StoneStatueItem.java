package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.entity.StoneStatueEntity;
import com.iafenvoy.iceandfire.item.component.StoneStatusComponent;
import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafEntities;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;

public class StoneStatueItem extends Item {
   public StoneStatueItem() {
      super(
         new Properties().stacksTo(1).component((DataComponentType)IafDataComponents.STONE_STATUS.get(), new StoneStatusComponent(true, "", new CompoundTag()))
      );
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      if (stack.has((DataComponentType)IafDataComponents.STONE_STATUS.get())) {
         StoneStatusComponent component = (StoneStatusComponent)stack.get((DataComponentType)IafDataComponents.STONE_STATUS.get());
         Optional<EntityType<?>> optional = EntityType.byString(component.entityType());
         if (optional.isPresent()) {
            MutableComponent untranslated;
            if (component.isPlayer()) {
               untranslated = Component.translatable("entity.minecraft.player");
            } else {
               untranslated = Component.translatable(optional.get().getDescriptionId());
            }

            tooltip.add(untranslated.withStyle(ChatFormatting.GRAY));
         }
      }
   }

   public InteractionResult useOn(UseOnContext context) {
      if (context.getClickedFace() != Direction.UP) {
         return InteractionResult.FAIL;
      }

      assert context.getPlayer() != null;
      ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
      if (stack.has((DataComponentType)IafDataComponents.STONE_STATUS.get())) {
         StoneStatusComponent component = (StoneStatusComponent)stack.get((DataComponentType)IafDataComponents.STONE_STATUS.get());
         StoneStatueEntity statue = new StoneStatueEntity((EntityType<? extends LivingEntity>)IafEntities.STONE_STATUE.get(), context.getLevel());
         statue.readAdditionalSaveData(component.nbt());
         statue.setTrappedEntityTypeString(component.entityType());
         double d1 = context.getPlayer().getX() - (context.getClickedPos().getX() + 0.5);
         double d2 = context.getPlayer().getZ() - (context.getClickedPos().getZ() + 0.5);
         float yaw = (float)(Mth.atan2(d2, d1) * 180.0F / (float)Math.PI) - 90.0F;
         statue.yRotO = yaw;
         statue.setYRot(yaw);
         statue.yHeadRot = yaw;
         statue.yBodyRot = yaw;
         statue.yBodyRotO = yaw;
         statue.absMoveTo(context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5, yaw, 0.0F);
         if (!context.getLevel().isClientSide) {
            context.getLevel().addFreshEntity(statue);
         }

         statue.setCrackAmount(0);
         if (!context.getPlayer().isCreative()) {
            stack.shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.SUCCESS;
      }
   }
}
