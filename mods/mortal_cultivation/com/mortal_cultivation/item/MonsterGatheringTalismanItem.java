package com.mortal_cultivation.item;

import com.mortal_cultivation.entity.MonsterGatheringTalismanEntity;
import com.mortal_cultivation.registry.EntityRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MonsterGatheringTalismanItem extends Item {
   private static final String PROGRESS_CHAR = "■";
   private static final int PROGRESS_BAR_LENGTH = 10;

   public MonsterGatheringTalismanItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      tooltip.add(Component.translatable("item.mortal_cultivation.monster_gathering_talisman.desc").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.mortal_cultivation.monster_gathering_talisman.pickup").withStyle(ChatFormatting.DARK_GRAY));
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      if (tag != null && tag.contains("RemainingTicks")) {
         int remainingTicks = tag.getInt("RemainingTicks");
         float percentage = tag.getFloat("PercentageRemaining");
         int remainingMinutes = remainingTicks / 1200;
         tooltip.add(
            Component.translatable("item.mortal_cultivation.monster_gathering_talisman.time_left", new Object[]{remainingMinutes})
               .withStyle(ChatFormatting.GOLD)
         );
         ChatFormatting color;
         if (percentage > 0.7F) {
            color = ChatFormatting.GREEN;
         } else if (percentage > 0.4F) {
            color = ChatFormatting.YELLOW;
         } else if (percentage > 0.2F) {
            color = ChatFormatting.GOLD;
         } else {
            color = ChatFormatting.DARK_RED;
         }

         int filledChars = Math.round(percentage * 10.0F);
         StringBuilder progressBar = new StringBuilder();

         for (int i = 0; i < 10; i++) {
            if (i < filledChars) {
               progressBar.append("■");
            } else {
               progressBar.append("□");
            }
         }

         tooltip.add(Component.literal(progressBar.toString()).withStyle(color));
      }

      super.appendHoverText(stack, context, tooltip, flag);
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      ItemStack itemStack = context.getItemInHand();
      if (!level.isClientSide()) {
         Vec3 clickPos = new Vec3(context.getClickLocation().x(), context.getClickLocation().y(), context.getClickLocation().z());
         MonsterGatheringTalismanEntity talismanEntity = new MonsterGatheringTalismanEntity(
            (EntityType<? extends MonsterGatheringTalismanEntity>)EntityRegistry.MONSTER_GATHERING_TALISMAN_ENTITY.get(), level
         );
         talismanEntity.setPos(clickPos.x, clickPos.y, clickPos.z);
         CompoundTag tag = ((CustomData)itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
         if (tag != null && tag.contains("RemainingTicks")) {
            int remainingTicks = tag.getInt("RemainingTicks");
            CompoundTag entityTag = talismanEntity.getPersistentData();
            entityTag.putInt("RemainingTicks", remainingTicks);
         }

         level.addFreshEntity(talismanEntity);
         if (!context.getPlayer().getAbilities().instabuild) {
            itemStack.shrink(1);
         }
      }

      return InteractionResult.sidedSuccess(level.isClientSide());
   }
}
