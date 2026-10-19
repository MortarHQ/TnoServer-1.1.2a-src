package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.util.ItemDataUtils;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.block.HipokuteGrass;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class GardenerSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Gardener CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Gardener;

   public GardenerSkill() {
      super(SkillType.UNIQUE);
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "gardener.blessing";
         case 1 -> "gardener.overgrow";
         case 2 -> "gardener.entwine";
         default -> super.getModeId(instance, mode);
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return (mode + (reverse ? 2 : 1)) % 3;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/gardener.png");
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity living) {
      if (instance.isToggled() && !living.level().isClientSide) {
         if (!EnergyHelper.isOutOfEnergy(living, 0.0, CONFIG.cropMasteryCost)) {
            Level level = living.level();
            BlockPos origin = living.blockPosition();
            boolean mastery = instance.isMastered(living);
            CompoundTag tag = instance.getOrCreateTag();
            boolean overgrowRunning = tag.getBoolean("OvergrowRunning");
            int radius = 10;
            BlockPos.betweenClosedStream(origin.offset(-radius, -2, -radius), origin.offset(radius, 2, radius))
               .forEach(
                  pos -> {
                     BlockState state = level.getBlockState(pos);
                     if (state.getBlock() instanceof CropBlock crop && !crop.isMaxAge(state)) {
                        if (level.random.nextFloat() < (mastery ? (float)CONFIG.cropMasteryChanceMastered : (float)CONFIG.cropMasteryChance) / 100.0F) {
                           IntegerProperty ageProp = crop.getAgeProperty();
                           int currentAge = (Integer)state.getValue(ageProp);
                           int nextAge = Math.min(overgrowRunning ? currentAge + 2 : currentAge + 1, crop.getMaxAge());
                           level.setBlock(pos, (BlockState)state.setValue(ageProp, nextAge), 2);
                           level.playSound(
                              null, origin.getX(), origin.getY(), origin.getZ(), SoundEvents.PLAYER_LEVELUP, TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F
                           );
                           ((ServerLevel)level)
                              .sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.3, 0.3, 0.3, 0.05);
                        }
                     } else if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)) {
                        int matured = tag.getInt("MaturedCrops") + 1;
                        tag.putInt("MaturedCrops", matured);
                        if (matured >= 25) {
                           tag.putInt("MaturedCrops", 0);
                           tag.putInt("EntwineCharges", tag.getInt("EntwineCharges") + 1);
                        }
                     }

                     if (state.getBlock() instanceof HipokuteGrass hipokuteGrass
                        && (Integer)state.getValue(hipokuteGrass.getAgeProperty()) < hipokuteGrass.getMaxAge()) {
                        if (level.random.nextFloat()
                           < (mastery ? (float)CONFIG.cropMasteryChanceHipokuteMastered : (float)CONFIG.cropMasteryChanceHipokute) / 100.0F) {
                           IntegerProperty ageProp = hipokuteGrass.getAgeProperty();
                           int currentAge = (Integer)state.getValue(ageProp);
                           int nextAge = Math.min(overgrowRunning ? currentAge + 2 : currentAge + 1, hipokuteGrass.getMaxAge());
                           level.setBlock(pos, (BlockState)state.setValue(ageProp, nextAge), 2);
                           level.playSound(
                              null, origin.getX(), origin.getY(), origin.getZ(), SoundEvents.PLAYER_LEVELUP, TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F
                           );
                           ((ServerLevel)level)
                              .sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.3, 0.3, 0.3, 0.05);
                        }
                     } else if (state.getBlock() instanceof HipokuteGrass hipokuteGrass
                        && (Integer)state.getValue(hipokuteGrass.getAgeProperty()) >= hipokuteGrass.getMaxAge()) {
                        int matured = tag.getInt("MaturedCrops") + 1;
                        tag.putInt("MaturedCrops", matured);
                        if (matured >= 25) {
                           tag.putInt("MaturedCrops", 0);
                           tag.putInt("EntwineCharges", tag.getInt("EntwineCharges") + 1);
                        }
                     }

                     if (state.getBlock() instanceof StemBlock && (Integer)state.getValue(StemBlock.AGE) < 7) {
                        if (level.random.nextFloat() < (mastery ? (float)CONFIG.cropMasteryChanceMastered : (float)CONFIG.cropMasteryChance) / 100.0F) {
                           int age = (Integer)state.getValue(StemBlock.AGE);
                           int nextAge = Math.min(overgrowRunning ? age + 2 : age + 1, 7);
                           level.setBlock(pos, (BlockState)state.setValue(StemBlock.AGE, nextAge), 2);
                           ((ServerLevel)level)
                              .sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.3, 0.3, 0.3, 0.05);
                        }
                     } else if (state.getBlock() instanceof StemBlock && (Integer)state.getValue(StemBlock.AGE) >= 7) {
                        int matured = tag.getInt("MaturedCrops") + 1;
                        tag.putInt("MaturedCrops", matured);
                        if (matured >= 25) {
                           tag.putInt("MaturedCrops", 0);
                           tag.putInt("EntwineCharges", tag.getInt("EntwineCharges") + 1);
                        }
                     }
                  }
               );
         }
      }
   }

   private static Optional<? extends Holder<MobEffect>> parseEffectFromResourceLocation(String resource) {
      ResourceLocation id = ResourceLocation.tryParse(resource);
      if (id == null) {
         return Optional.empty();
      }

      ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, id);
      return BuiltInRegistries.MOB_EFFECT.getHolder(key);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            this.blessing(instance, entity, mode);
            break;
         case 1:
            this.overgrow(instance, entity, mode);
            break;
         case 2:
            this.entwine(instance, entity, mode);
      }
   }

   private void blessing(ManasSkillInstance instance, LivingEntity entity, int mode) {
      ItemStack item = entity.getMainHandItem();
      boolean success = false;
      if (ItemDataUtils.getFlag(item, "GardenerBlessed")) {
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.gardener.blessing.already_blessed", new Object[]{this.getChatDisplayName(true)})
                  .withStyle(ChatFormatting.RED),
               false
            );
            player.level()
               .playSound(null, player.getX(), player.getY(), player.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         }
      } else {
         if (!entity.level().isClientSide && item.get(DataComponents.FOOD) != null) {
            double costPer = instance.isMastered(entity) ? CONFIG.blessingCostMastered : CONFIG.blessingCost;
            if (EnergyHelper.isOutOfEnergy(entity, 0.0, costPer * item.getCount())) {
               return;
            }

            success = true;
            ItemDataUtils.setFlag(item, "GardenerBlessed", true);
            ItemDataUtils.setFlag(item, "GardenerMastery", instance.isMastered(entity));
            long timeOfDay = entity.level().getDayTime() % 24000L;
            GardenerSkill.DayPeriod period;
            if (timeOfDay < 6000L) {
               period = GardenerSkill.DayPeriod.DAWN;
               ItemDataUtils.setFlag(item, "DaytimeBlessing", true);
               ItemDataUtils.addLoreLine(
                  item,
                  Component.translatable("mysticism.skill.mode.gardener.blessing.bless", new Object[]{this.getChatDisplayName(true), "Dawn"})
                     .withStyle(Style.EMPTY)
                     .withStyle(ChatFormatting.GOLD)
               );
            } else if (timeOfDay < 12000L) {
               period = GardenerSkill.DayPeriod.NOON;
               ItemDataUtils.setFlag(item, "NoonBlessing", true);
               ItemDataUtils.addLoreLine(
                  item,
                  Component.translatable("mysticism.skill.mode.gardener.blessing.bless", new Object[]{this.getChatDisplayName(true), "Noon"})
                     .withStyle(Style.EMPTY)
                     .withStyle(ChatFormatting.YELLOW)
               );
            } else {
               period = GardenerSkill.DayPeriod.NIGHT;
               ItemDataUtils.setFlag(item, "NightBlessing", true);
               ItemDataUtils.addLoreLine(
                  item,
                  Component.translatable("mysticism.skill.mode.gardener.blessing.bless", new Object[]{this.getChatDisplayName(true), "Night"})
                     .withStyle(Style.EMPTY)
                     .withStyle(ChatFormatting.DARK_PURPLE)
               );
            }

            applyBlessingToFoodComponent(item, instance.isMastered(entity), period);
         }

         if (success) {
            double base = item.getCount() / 10.0;
            double bonus = this.getDefaultLearningIncrement(instance, entity, mode);
            this.addMasteryPoint(instance, entity, base + bonus);
            instance.setCoolDown(instance.isMastered(entity) ? CONFIG.blessingCooldownMastered : CONFIG.blessingCooldown, 0);
            entity.swing(InteractionHand.MAIN_HAND, true);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         }
      }
   }

   private void overgrow(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (instance.getOrCreateTag().getBoolean("OvergrowRunning")) {
         instance.getOrCreateTag().putBoolean("OvergrowRunning", false);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      } else {
         TimerUtils.runLater(
            "Overgrow",
            CONFIG.overgrowDuration,
            () -> {
               instance.getOrCreateTag().putBoolean("OvergrowRunning", false);
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               instance.setCoolDown(CONFIG.overgrowCooldown, mode);
            }
         );
      }
   }

   private void entwine(ManasSkillInstance instance, LivingEntity entity, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getInt("EntwineCharges") != 0) {
         List p = entity.level()
            .getEntitiesOfClass(
               LivingEntity.class, entity.getBoundingBox().inflate(10.0), living -> !living.is(entity) && living.isAlive() && !living.isAlliedTo(entity)
            );
      } else {
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         if (entity instanceof Player p) {
            p.displayClientMessage(
               Component.translatable(
                     "mysticism.skill.mode.gardener.overgrow.not_enough_crops", new Object[]{CONFIG.overgrowCropRequirement - tag.getInt("MatureCrops")}
                  )
                  .withStyle(ChatFormatting.RED),
               false
            );
         }
      }
   }

   private static void applyBlessingToFoodComponent(ItemStack stack, boolean mastered, GardenerSkill.DayPeriod period) {
      FoodProperties base = (FoodProperties)stack.get(DataComponents.FOOD);
      if (base != null) {
         Builder b = new Builder().nutrition(base.nutrition()).saturationModifier(base.saturation());
         if (base.canAlwaysEat()) {
            b.alwaysEdible();
         }

         for (PossibleEffect eff : base.effects()) {
            b.effect(eff.effect(), eff.probability());
         }

         switch (period) {
            case DAWN: {
               Optional<? extends Holder<MobEffect>> effect = parseEffectFromResourceLocation(CONFIG.dawnBlessingEffect);
               effect.ifPresent(
                  mobEffectHolder -> b.effect(
                     new MobEffectInstance(
                        mobEffectHolder,
                        mastered ? CONFIG.dawnDurationMastered : CONFIG.dawnDuration,
                        (mastered ? CONFIG.dawnEffectLevelMastered : CONFIG.dawnEffectLevel) - 1,
                        false,
                        true,
                        true
                     ),
                     1.0F
                  )
               );
               break;
            }
            case NOON: {
               Optional<? extends Holder<MobEffect>> effect = parseEffectFromResourceLocation(CONFIG.noonBlessingEffect);
               effect.ifPresent(
                  mobEffectHolder -> b.effect(
                     new MobEffectInstance(
                        mobEffectHolder,
                        mastered ? CONFIG.noonDurationMastered : CONFIG.noonDuration,
                        (mastered ? CONFIG.noonEffectLevelMastered : CONFIG.noonEffectLevel) - 1,
                        false,
                        true,
                        true
                     ),
                     1.0F
                  )
               );
               break;
            }
            case NIGHT: {
               Optional<? extends Holder<MobEffect>> effect = parseEffectFromResourceLocation(CONFIG.nightBlessingEffect);
               effect.ifPresent(
                  mobEffectHolder -> b.effect(
                     new MobEffectInstance(
                        mobEffectHolder,
                        mastered ? CONFIG.nightDurationMastered : CONFIG.nightDuration,
                        (mastered ? CONFIG.nightEffectLevelMastered : CONFIG.nightEffectLevel) - 1,
                        false,
                        true,
                        true
                     ),
                     1.0F
                  )
               );
            }
         }

         stack.set(DataComponents.FOOD, b.build());
      }
   }

   private enum DayPeriod {
      DAWN,
      NOON,
      NIGHT;
   }
}
