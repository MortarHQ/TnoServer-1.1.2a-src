package com.github.wal_bos.moreskills.item;

import com.github.wal_bos.moreskills.handler.asmoday.AsmodayPrisonRealmManager;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AsmodayPrisonCubeItem extends Item {
   private static final String TARGET = "PrisonTarget";
   private static final String TARGET_NAME = "PrisonTargetName";
   private static final String OWNER = "PrisonOwner";
   private static final String RETURN_DIMENSION = "ReturnDimension";
   private static final String RETURN_X = "ReturnX";
   private static final String RETURN_Y = "ReturnY";
   private static final String RETURN_Z = "ReturnZ";

   public AsmodayPrisonCubeItem(Properties properties) {
      super(properties.stacksTo(1).fireResistant());
   }

   public static ItemStack createBoundCube(UUID target, String targetName, UUID owner, ResourceKey<Level> returnDimension, Vec3 returnPos) {
      ItemStack stack = new ItemStack((ItemLike)TenaddexItems.ASMODAY_PRISON_CUBE.get());
      CompoundTag tag = new CompoundTag();
      tag.putUUID("PrisonTarget", target);
      tag.putString("PrisonTargetName", targetName);
      tag.putUUID("PrisonOwner", owner);
      tag.putString("ReturnDimension", returnDimension.location().toString());
      tag.putDouble("ReturnX", returnPos.x);
      tag.putDouble("ReturnY", returnPos.y);
      tag.putDouble("ReturnZ", returnPos.z);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      stack.set(DataComponents.CUSTOM_NAME, Component.literal("Prison Cube: " + targetName).withStyle(ChatFormatting.DARK_RED));
      return stack;
   }

   public static UUID getTarget(ItemStack stack) {
      CompoundTag tag = getTag(stack);
      return tag != null && tag.hasUUID("PrisonTarget") ? tag.getUUID("PrisonTarget") : null;
   }

   public static String getTargetName(ItemStack stack) {
      CompoundTag tag = getTag(stack);
      return tag == null ? "" : tag.getString("PrisonTargetName");
   }

   public static CompoundTag getTag(ItemStack stack) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag tag = data.copyTag();
      return tag.isEmpty() ? null : tag;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
      ItemStack stack = player.getItemInHand(usedHand);
      if (level.isClientSide()) {
         return InteractionResultHolder.success(stack);
      }

      if (player instanceof ServerPlayer serverPlayer) {
         UUID target = getTarget(stack);
         if (target == null) {
            serverPlayer.sendSystemMessage(Component.literal("This cube is empty.").withStyle(ChatFormatting.GRAY));
            return InteractionResultHolder.fail(stack);
         } else {
            AsmodayPrisonRealmManager.releaseByCube(serverPlayer, stack, false);
            return InteractionResultHolder.success(stack);
         }
      } else {
         return InteractionResultHolder.fail(stack);
      }
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      if (!level.isClientSide()) {
         UUID target = getTarget(stack);
         if (target != null) {
            if (entity instanceof ItemEntity itemEntity) {
               AsmodayPrisonRealmManager.trackDroppedCube(itemEntity);
            }
         }
      }
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      if (!entity.level().isClientSide()) {
         AsmodayPrisonRealmManager.trackDroppedCube(entity);
      }

      return false;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
      String name = getTargetName(stack);
      if (name != null && !name.isEmpty()) {
         tooltip.add(Component.literal("Contains: " + name).withStyle(ChatFormatting.RED));
         tooltip.add(Component.literal("Anyone holding this cube can open the prison.").withStyle(ChatFormatting.DARK_GRAY));
         tooltip.add(Component.literal("If destroyed, the prisoner releases violently.").withStyle(ChatFormatting.DARK_RED));
      } else {
         tooltip.add(Component.literal("Empty closed space.").withStyle(ChatFormatting.GRAY));
      }

      super.appendHoverText(stack, context, tooltip, tooltipFlag);
   }
}
