package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class GreenThumbSkill extends Skill {
   public GreenThumbSkill() {
      super(SkillType.UNIQUE);
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/green_thumb.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)MoreSkillsConfigs.GRAND.greenThumb.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)MoreSkillsConfigs.GRAND.greenThumb.acquirementMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 2 : mode - 1;
      } else {
         return mode >= 2 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "green_thumb.blessing";
         case 1 -> "green_thumb.growth";
         case 2 -> "green_thumb.offering";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> ((Integer)MoreSkillsConfigs.GRAND.greenThumb.blessingCost.get()).intValue();
         case 1 -> ((Integer)MoreSkillsConfigs.GRAND.greenThumb.growthCost.get()).intValue();
         case 2 -> ((Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringCost.get()).intValue();
         default -> 0.0;
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            switch (mode) {
               case 0:
                  this.blessing(instance, player, mode);
                  break;
               case 1:
                  this.growth(instance, player, mode);
                  break;
               case 2:
                  this.offering(instance, player, mode);
            }
         }
      }
   }

   private void blessing(ManasSkillInstance instance, ServerPlayer player, int mode) {
      RandomSource random = player.getRandom();
      ItemStack seed = new ItemStack((ItemLike)seedPool().get(random.nextInt(seedPool().size())));
      if (!player.getInventory().add(seed.copy())) {
         player.drop(seed.copy(), false);
      }

      instance.addMasteryPoint(player);
      instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.greenThumb.blessingCooldownTicks.get(), mode);
      player.swing(InteractionHand.MAIN_HAND, true);
      player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.blessing", new Object[]{seed.getHoverName()}), true);
      player.level()
         .playSound(
            (Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F
         );
   }

   private void growth(ManasSkillInstance instance, ServerPlayer player, int mode) {
      ServerLevel level = player.serverLevel();
      BlockHitResult hit = level.clip(
         new ClipContext(
            player.getEyePosition(),
            player.getEyePosition().add(player.getLookAngle().scale((Double)MoreSkillsConfigs.GRAND.greenThumb.growthRange.get())),
            Block.OUTLINE,
            Fluid.NONE,
            player
         )
      );
      if (hit.getType() != Type.BLOCK) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.no_plant"), true);
      } else {
         BlockPos pos = hit.getBlockPos();
         BlockState state = level.getBlockState(pos);
         net.minecraft.world.level.block.Block block = state.getBlock();
         boolean changed = false;
         if (block instanceof CropBlock crop) {
            int age = crop.getAge(state);
            if (age < crop.getMaxAge()) {
               level.setBlock(pos, crop.getStateForAge(Math.min(crop.getMaxAge(), age + 1)), 3);
               changed = true;
            }
         } else if (block instanceof BonemealableBlock growable
            && growable.isValidBonemealTarget(level, pos, state)
            && growable.isBonemealSuccess(level, level.random, pos, state)) {
            growable.performBonemeal(level, level.random, pos, state);
            changed = true;
         }

         if (changed) {
            instance.addMasteryPoint(player);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.greenThumb.growthCooldownTicks.get(), mode);
            player.swing(InteractionHand.MAIN_HAND, true);
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.growth"), true);
            level.playSound(
               (Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.2F
            );
         } else {
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.no_plant"), true);
         }
      }
   }

   private void offering(ManasSkillInstance instance, ServerPlayer player, int mode) {
      int slot = -1;

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (GreenThumbSkillHelper.isFood(stack) && stack.getCount() >= 64) {
            slot = i;
            break;
         }
      }

      if (slot < 0) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.no_offering"), true);
      } else {
         player.getInventory().getItem(slot).shrink(64);
         int duration = (Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringDurationTicks.get();
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
               duration,
               (Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringRegenerationAmplifier.get(),
               true,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.HEALTHCARE),
               duration,
               (Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringHealthcareAmplifier.get(),
               true,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_REGENERATION),
               duration,
               (Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringMagiculeRegenerationAmplifier.get(),
               true,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
               duration,
               (Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringHakiCoatAmplifier.get(),
               true,
               false,
               true
            )
         );
         instance.addMasteryPoint(player);
         instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.greenThumb.offeringCooldownTicks.get(), mode);
         player.swing(InteractionHand.MAIN_HAND, true);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.green_thumb.offering"), true);
         player.level()
            .playSound(
               (Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.8F
            );
      }
   }

   private static List<Item> seedPool() {
      List<Item> items = new ArrayList<>();
      items.add(Items.WHEAT_SEEDS);
      items.add(Items.BEETROOT_SEEDS);
      items.add(Items.MELON_SEEDS);
      items.add(Items.PUMPKIN_SEEDS);
      items.add(Items.TORCHFLOWER_SEEDS);
      items.add(Items.PITCHER_POD);
      items.add((Item)TensuraMaterialItems.HIPOKUTE_SEEDS.get());
      return items;
   }
}
