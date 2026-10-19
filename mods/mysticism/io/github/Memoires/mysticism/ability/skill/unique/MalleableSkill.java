package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.golems.ClayGolemEntity;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.FireAttackResistance;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MalleableSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Malleable CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Malleable;
   protected static final ResourceLocation MALLEABLE = ResourceLocation.fromNamespaceAndPath("mysticism", "malleable");

   public MalleableSkill() {
      super(SkillType.UNIQUE);
      if (CONFIG.hardenShouldDisableAttack) {
         this.addHeldAttributeModifier(Attributes.ATTACK_DAMAGE, MALLEABLE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      }

      if (CONFIG.hardenShouldDisableMovement) {
         this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, MALLEABLE, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      }

      this.addHeldAttributeModifier(Attributes.ARMOR, MALLEABLE, CONFIG.hardenArmorPoints, Operation.ADD_VALUE);
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 2 : mode - 1;
      } else {
         return mode == 2 ? 0 : mode + 1;
      }
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/malleable.png");
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 0 && entity.isShiftKeyDown();
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "malleable.alchemy";
         case 1 -> "malleable.harden";
         case 2 -> "malleable.vessel";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         ManasSkillInstance flameAttackRes = ((FireAttackResistance)ResistanceSkills.FLAME_ATTACK_RESISTANCE.get()).createDefaultInstance();
         flameAttackRes.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         SkillHelper.learnSkill(entity, flameAttackRes);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag skillTag = instance.getOrCreateTag();
      switch (mode) {
         case 0:
            if (!entity.isShiftKeyDown()) {
               if (CONFIG.alchemyToggleableMastery && this.isMastered(instance, entity)) {
                  if (!skillTag.getBoolean("AlchemyActive")) {
                     if (entity instanceof Player player) {
                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.mode.malleable.alchemy.activated_toggle").withStyle(ChatFormatting.GREEN), false
                        );
                     }

                     entity.level()
                        .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                     skillTag.putBoolean("AlchemyActive", true);
                  } else {
                     if (entity instanceof Player player) {
                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.mode.malleable.alchemy.deactivated").withStyle(ChatFormatting.RED), false
                        );
                     }

                     entity.level()
                        .playSound(
                           null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
                        );
                     skillTag.putBoolean("AlchemyActive", false);
                  }
               } else {
                  int alchemyDuration = instance.isMastered(entity) ? CONFIG.alchemyCooldownMastered : CONFIG.alchemyDuration;
                  if (!skillTag.getBoolean("AlchemyActive")) {
                     if (entity instanceof Player player) {
                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.mode.malleable.alchemy.activated", new Object[]{alchemyDuration / 20})
                              .withStyle(ChatFormatting.GREEN),
                           false
                        );
                     }

                     entity.level()
                        .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                     skillTag.putBoolean("AlchemyActive", true);
                     AtomicInteger ticksRun = new AtomicInteger();
                     TimerUtils.getOrCreateRepeating("MalleableAlchemy")
                        .start(
                           alchemyDuration,
                           () -> {
                              ticksRun.getAndIncrement();
                              if (entity instanceof Player player) {
                                 player.displayClientMessage(
                                    Component.translatable(
                                          "mysticism.skill.mode.constant.time_remaining", new Object[]{(CONFIG.alchemyDuration - ticksRun.get()) / 20}
                                       )
                                       .withStyle(ChatFormatting.DARK_AQUA),
                                    true
                                 );
                              }
                           },
                           () -> {
                              if (entity instanceof Player player) {
                                 player.displayClientMessage(
                                    Component.translatable("mysticism.skill.mode.malleable.alchemy.deactivated").withStyle(ChatFormatting.RED), false
                                 );
                              }

                              entity.level()
                                 .playSound(
                                    null,
                                    entity.getX(),
                                    entity.getY(),
                                    entity.getZ(),
                                    TensuraSoundEvents.GENERIC_CAST_FAIL,
                                    TensuraSkill.ABILITY_SOUND,
                                    1.0F,
                                    1.0F
                                 );
                              instance.getOrCreateTag().putBoolean("AlchemyActive", false);
                              instance.addMasteryPoint(entity);
                              instance.setCoolDown(CONFIG.alchemyCooldown, 0);
                           }
                        );
                  }
               }
            }
            break;
         case 1:
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("mysticism.skill.mode.malleable.harden.activated").withStyle(ChatFormatting.GREEN), false);
            }

            int duration = instance.isMastered(entity) ? CONFIG.maximumHardenDurationMastery : CONFIG.maximumHardenDuration;
            TensuraMobEffect.addEffect(
               entity, new MobEffectInstance(MobEffects.REGENERATION, duration, CONFIG.regenerationLevel - 1, false, true, true), entity, this, mode
            );
            TensuraMobEffect.addEffect(
               entity, new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, CONFIG.resistanceLevel - 1, false, true, true), entity, this, mode
            );
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.DEFENCE_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            break;
         case 2:
            ServerLevel serverLevel = (ServerLevel)entity.level();
            BlockHitResult targetLocation = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, 10.0);
            if (spawnVessel(serverLevel, targetLocation.getBlockPos().above(), entity, instance)) {
               instance.addMasteryPoint(entity);
               instance.setCoolDown(instance.isMastered(entity) ? CONFIG.vesselCooldownMastered : CONFIG.vesselCooldown, 2);
            }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      return switch (mode) {
         case 0 -> {
            if (!(living.level() instanceof ServerLevel)) {
               yield false;
            } else if (!living.isShiftKeyDown()) {
               yield false;
            } else {
               CompoundTag tag = instance.getOrCreateTag();
               String selectedAlchemy = tag != null ? tag.getString("SelectedAlchemy") : "Physical";
               if (living instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.malleable.alchemy.currently_selected", new Object[]{selectedAlchemy})
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)),
                     true
                  );
               }

               yield true;
            }
         }
         case 1 -> {
            int maximumHardenDuration = instance.isMastered(living) ? CONFIG.maximumHardenDurationMastery : CONFIG.maximumHardenDuration;
            if (heldTicks > maximumHardenDuration) {
               instance.addMasteryPoint(living);
               int cooldown = instance.isMastered(living) ? CONFIG.hardenCooldownMastered : CONFIG.hardenCooldown;
               living.removeEffect(MobEffects.REGENERATION);
               living.removeEffect(MobEffects.DAMAGE_RESISTANCE);
               instance.setCoolDown(cooldown, 1);
               living.level()
                  .playSound(null, living.getX(), living.getY(), living.getZ(), TensuraSoundEvents.DEFENCE_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               yield false;
            } else {
               if (living instanceof Player player && player.getAbilities().mayfly) {
                  player.getAbilities().flying = false;
               }

               yield true;
            }
         }
         default -> false;
      };
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1 && TickingSkill.isTickingSkill(entity, this, 1)) {
         instance.addMasteryPoint(entity);
         int cooldown = instance.isMastered(entity) ? CONFIG.hardenCooldownMastered : CONFIG.hardenCooldown;
         entity.removeEffect(MobEffects.REGENERATION);
         entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
         instance.setCoolDown(cooldown, 1);
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.DEFENCE_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      if (mode == 0) {
         CompoundTag tag = instance.getOrCreateTag();
         List<String> selectedAlchemy = List.of(
            "Physical", "Fire", "Ice", "Earth", "Water", "Light", "Dark", "Holy", "Poison", "Corrosion", "Wind", "Lightning"
         );
         int newScale = tag.getInt("AlchemyIndex") + (int)delta;
         if (newScale >= selectedAlchemy.size()) {
            newScale = 0;
         } else if (newScale < 0) {
            newScale = selectedAlchemy.size() - 1;
         }

         if (tag.getInt("AlchemyIndex") != newScale) {
            tag.putInt("AlchemyIndex", newScale);
            tag.putString("SelectedAlchemy", selectedAlchemy.get(newScale));
            instance.markDirty();
         }
      }
   }

   public static boolean spawnVessel(ServerLevel level, BlockPos pos, LivingEntity owner, ManasSkillInstance inst) {
      EntityType<ClayGolemEntity> type = (EntityType<ClayGolemEntity>)MysticismMonsterEntityTypes.CLAY_GOLEM.get();
      ClayGolemEntity golem = (ClayGolemEntity)type.create(level);
      if (golem == null) {
         return false;
      }

      golem.moveTo(pos, owner.getYRot(), owner.getXRot());
      golem.setNoAi(true);
      golem.setOwnerUUID(owner.getUUID());
      double hp = inst.isMastered(owner) ? CONFIG.vesselHPMastered : CONFIG.vesselHP;
      AttributeInstance attr = golem.getAttribute(Attributes.MAX_HEALTH);
      if (attr != null) {
         attr.setBaseValue(hp);
         golem.setHealth((float)hp);
         double armor = inst.isMastered(owner) ? CONFIG.vesselArmorMastered : CONFIG.vesselArmor;
         AttributeInstance armorAttr = golem.getAttribute(Attributes.ARMOR);
         if (armorAttr != null) {
            armorAttr.setBaseValue(armor);
            if (level.addFreshEntity(golem)) {
               level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), TensuraSoundEvents.GENERIC_CAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               return true;
            } else {
               return false;
            }
         } else {
            throw new IllegalStateException("ClayGolem has no ARMOR attribute");
         }
      } else {
         throw new IllegalStateException("ClayGolem has no MAX_HEALTH attribute");
      }
   }
}
