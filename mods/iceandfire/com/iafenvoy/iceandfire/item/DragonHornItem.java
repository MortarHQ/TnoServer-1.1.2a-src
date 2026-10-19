package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.item.component.DragonHornComponent;
import com.iafenvoy.iceandfire.registry.IafDataComponents;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DragonHornItem extends Item {
   public DragonHornItem() {
      super(new Properties().stacksTo(1));
   }

   public static int getDragonType(ItemStack stack) {
      if (stack.has((DataComponentType)IafDataComponents.DRAGON_HORN.get())) {
         Optional<EntityType<?>> optional = BuiltInRegistries.ENTITY_TYPE
            .getOptional(((DragonHornComponent)stack.get((DataComponentType)IafDataComponents.DRAGON_HORN.get())).entityType());
         if (optional.isPresent()) {
            EntityType<?> entityType = optional.get();
            if (entityType == IafEntities.FIRE_DRAGON.get()) {
               return 1;
            }

            if (entityType == IafEntities.ICE_DRAGON.get()) {
               return 2;
            }

            if (entityType == IafEntities.LIGHTNING_DRAGON.get()) {
               return 3;
            }
         }
      }

      return 0;
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      stack = player.getItemInHand(hand);
      if (stack.is((Item)IafItems.DRAGON_HORN.get()) && !stack.has((DataComponentType)IafDataComponents.DRAGON_HORN.get())) {
         if (!player.level().isClientSide && target instanceof DragonBaseEntity dragon && dragon.isOwnedBy(player)) {
            CompoundTag entityTag = new CompoundTag();
            target.save(entityTag);
            stack.set(
               (DataComponentType)IafDataComponents.DRAGON_HORN.get(),
               new DragonHornComponent(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()), target.getUUID(), entityTag)
            );
            player.swing(hand);
            player.level().playSound(player, player.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.NEUTRAL, 3.0F, 0.75F);
            target.remove(RemovalReason.DISCARDED);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.CONSUME;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   public InteractionResult useOn(UseOnContext context) {
      if (context.getClickedFace() != Direction.UP) {
         return InteractionResult.PASS;
      }

      ItemStack stack = context.getItemInHand();
      if (stack.has((DataComponentType)IafDataComponents.DRAGON_HORN.get())) {
         DragonHornComponent component = (DragonHornComponent)stack.get((DataComponentType)IafDataComponents.DRAGON_HORN.get());
         Level world = context.getLevel();
         EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.getOptional(component.entityType()).orElse(null);
         if (type != null) {
            Entity entity = type.create(world);
            if (entity instanceof DragonBaseEntity dragon) {
               dragon.load(component.entityData());
            }

            UUID uuid = component.entityUuid();
            if (uuid != null) {
               assert entity != null;
               entity.setUUID(uuid);
            }

            assert entity != null;
            entity.absMoveTo(
               context.getClickedPos().getX() + 0.5,
               context.getClickedPos().getY() + 1,
               context.getClickedPos().getZ() + 0.5,
               180.0F + context.getHorizontalDirection().toYRot(),
               0.0F
            );
            if (world.addFreshEntity(entity)) {
               stack.remove((DataComponentType)IafDataComponents.DRAGON_HORN.get());
            }
         }
      }

      return InteractionResult.SUCCESS;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      if (stack.has((DataComponentType)IafDataComponents.DRAGON_HORN.get())) {
         DragonHornComponent component = (DragonHornComponent)stack.get((DataComponentType)IafDataComponents.DRAGON_HORN.get());
         CompoundTag entityTag = component.entityData();
         if (!entityTag.isEmpty()) {
            Optional<EntityType<?>> optional = BuiltInRegistries.ENTITY_TYPE.getOptional(component.entityType());
            if (optional.isPresent()) {
               EntityType<?> entityType = optional.get();
               tooltip.add(Component.translatable(entityType.getDescriptionId()).withStyle(this.getTextColorForEntityType(entityType)));
               String name = Component.translatable("dragon.unnamed").getString();
               if (!entityTag.getString("CustomName").isEmpty()) {
                  name = entityTag.getString("CustomName");
               }

               tooltip.add(Component.literal(name).withStyle(ChatFormatting.GRAY));
               String gender = Component.translatable("dragon.gender").getString()
                  + " "
                  + Component.translatable(entityTag.getBoolean("Gender") ? "dragon.gender.male" : "dragon.gender.female").getString();
               tooltip.add(Component.literal(gender).withStyle(ChatFormatting.GRAY));
               int stagenumber = entityTag.getInt("AgeTicks") / 24000;
               int stage1;
               if (stagenumber >= 100) {
                  stage1 = 5;
               } else if (stagenumber >= 75) {
                  stage1 = 4;
               } else if (stagenumber >= 50) {
                  stage1 = 3;
               } else if (stagenumber >= 25) {
                  stage1 = 2;
               } else {
                  stage1 = 1;
               }

               tooltip.add(
                  Component.translatable("dragon.stage")
                     .append(Component.literal(" " + stage1 + " "))
                     .append(Component.translatable("dragon.days.front"))
                     .append(Component.literal(stagenumber + " "))
                     .append(Component.translatable("dragon.days.back"))
                     .withStyle(ChatFormatting.GRAY)
               );
            }
         }
      }
   }

   private ChatFormatting getTextColorForEntityType(EntityType<?> type) {
      if (type == IafEntities.FIRE_DRAGON.get()) {
         return ChatFormatting.DARK_RED;
      } else if (type == IafEntities.ICE_DRAGON.get()) {
         return ChatFormatting.BLUE;
      } else {
         return type == IafEntities.LIGHTNING_DRAGON.get() ? ChatFormatting.DARK_PURPLE : ChatFormatting.GRAY;
      }
   }
}
