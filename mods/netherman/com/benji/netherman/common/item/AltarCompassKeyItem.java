package com.benji.netherman.common.item;

import com.benji.netherman.NetherExp;
import com.benji.netherman.init.ModBlocks;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class AltarCompassKeyItem extends Item {
   public AltarCompassKeyItem(Properties properties) {
      super(properties);
   }

   public boolean isFoil(ItemStack stack) {
      CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      return customData.copyTag().getBoolean("HeadAltarMode") || super.isFoil(stack);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component compass = Component.translatable("tooltip.netherman.compass").withStyle(ChatFormatting.GOLD);
      tooltipComponents.add(compass);
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      ItemStack stack = context.getItemInHand();
      if (level.getBlockState(pos).is((Block)ModBlocks.SAMSONIT_KEY.get())) {
         if (!level.isClientSide()) {
            CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();
            if (!tag.getBoolean("HeadAltarMode")) {
               tag.putBoolean("HeadAltarMode", true);
               tag.remove("TargetX");
               tag.remove("TargetZ");
               stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
               level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
               level.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1.0F, 0.7F);
               LightningBolt lightning = (LightningBolt)EntityType.LIGHTNING_BOLT.create(level);
               if (lightning != null) {
                  lightning.moveTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                  lightning.setVisualOnly(true);
                  level.addFreshEntity(lightning);
               }
            }
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useOn(context);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)level;
         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
         CompoundTag tag = customData.copyTag();
         boolean isHeadAltarMode = tag.getBoolean("HeadAltarMode");
         String targetStructureName = isHeadAltarMode ? "head_altar" : "monument";
         ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE, NetherExp.location(targetStructureName));
         Registry<Structure> registry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
         Reference<Structure> holder = (Reference<Structure>)registry.getHolder(structureKey).orElse(null);
         if (holder != null) {
            Pair<BlockPos, Holder<Structure>> pair = serverLevel.getChunkSource()
               .getGenerator()
               .findNearestMapStructure(serverLevel, HolderSet.direct(new Holder[]{holder}), player.blockPosition(), 100, false);
            if (pair != null) {
               BlockPos structureOrigin = (BlockPos)pair.getFirst();
               BlockPos preciseTarget = structureOrigin;
               if (!isHeadAltarMode) {
                  int scanRadius = 48;
                  boolean foundKey = false;

                  for (int x = -scanRadius; x <= scanRadius; x++) {
                     for (int y = -40; y <= 40; y++) {
                        for (int z = -scanRadius; z <= scanRadius; z++) {
                           BlockPos checkPos = structureOrigin.offset(x, y, z);
                           if (serverLevel.getBlockState(checkPos).is((Block)ModBlocks.SAMSONIT_KEY.get())) {
                              preciseTarget = checkPos;
                              foundKey = true;
                              break;
                           }
                        }

                        if (foundKey) {
                           break;
                        }
                     }

                     if (foundKey) {
                        break;
                     }
                  }
               }

               tag.putInt("TargetX", preciseTarget.getX());
               tag.putInt("TargetZ", preciseTarget.getZ());
               stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
               level.playSound(null, player.blockPosition(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
               player.sendSystemMessage(Component.literal("§4§kYOU ARE ALONE"));
            }
         }
      }

      return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
   }
}
