package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.item.weapon.RitualScytheItem;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class SpiritualistSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Spiritualist CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(
         UniqueMysticismSkillConfig.class
      ))
      .Spiritualist;
   protected static final ResourceLocation SPIRITUALIST = ResourceLocation.fromNamespaceAndPath("mysticism", "spiritualist");

   public SpiritualistSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/spiritualist.png");
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyChantSpeed(entity, CONFIG.chantSpeed);
      ThoughtAccelerationSkill.onToggle(instance, entity, SPIRITUALIST, true);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeChantSpeed(entity, CONFIG.chantSpeed);
      ThoughtAccelerationSkill.onToggle(instance, entity, SPIRITUALIST, false);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "spiritualist.summon_scythe";
         case 1 -> "spiritualist.carve";
         case 2 -> "spiritualist.enhance";
         default -> super.getModeId(instance, mode);
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 2 : mode - 1;
      } else {
         return mode == 2 ? 0 : mode + 1;
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.summonScytheCost;
         case 1 -> CONFIG.carveCost;
         case 2 -> CONFIG.enhanceCost;
         default -> 500.0;
      };
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      Float valueObj = (Float)amount.get();
      if (valueObj == null) {
         return true;
      }

      float value = valueObj;
      if (TensuraDamageHelper.isSpiritual(source) || TensuraDamageHelper.isNaturalEffects(source)) {
         amount.set(value * 0.75F);
      } else if (TensuraDamageHelper.isPhysicalAttack(source)) {
         amount.set(value * 0.9F);
      }

      return true;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      ItemStack mainHand = owner.getMainHandItem();
      if (mainHand.is((Item)MysticismWeaponItems.RITUAL_SCYTHE.get())) {
         if (owner instanceof Player player) {
            float cooldown = player.getAttackStrengthScale(0.5F);
            if (cooldown >= 1.0F) {
               TensuraDamageHelper.directSpiritualHurt(target, player, RitualScytheItem.calculateSHPDamage(player));
               mainHand.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            } else {
               TensuraDamageHelper.directSpiritualHurt(target, player, RitualScytheItem.calculateSHPDamage(player) / 10.0F);
               player.swing(InteractionHand.MAIN_HAND, true);
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (this.isInSlot(owner) && TensuraDamageHelper.isNaturalEffects(source)) {
         Float current = (Float)amount.get();
         if (current == null) {
            return true;
         }

         float storedScale = instance.getOrCreateTag().getFloat("soulPercent");
         storedScale = Math.max(0.0F, Math.min(10.0F, storedScale));
         float userPercent = storedScale * 10.0F;
         float bonusPercent = 10.0F + userPercent;
         float newDamage = current * (1.0F + bonusPercent / 100.0F);
         amount.set(newDamage);
         if (userPercent > 0.0F) {
            IExistence existence = TensuraStorages.getExistenceFrom(owner);
            int soulPoints = existence.getSoulPoints();
            int reduceBy = Math.round(soulPoints * (userPercent / 100.0F));
            existence.setSoulPoints(soulPoints - reduceBy);
         }

         return true;
      } else {
         return true;
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            this.toggleRitualScythe(entity);
            break;
         case 1:
            this.performCarve(instance, entity, mode);
      }
   }

   private void toggleRitualScythe(LivingEntity entity) {
      ItemStack mainHand = entity.getMainHandItem();
      boolean holdingScythe = mainHand.is((Item)MysticismWeaponItems.RITUAL_SCYTHE.get());
      if (holdingScythe) {
         entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
      } else if (!entity.hasItemInSlot(EquipmentSlot.MAINHAND)) {
         entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack((ItemLike)MysticismWeaponItems.RITUAL_SCYTHE.get()));
      }
   }

   private void performCarve(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (entity instanceof Player player) {
         if (entity.getMainHandItem().is((Item)MysticismWeaponItems.RITUAL_SCYTHE.get())) {
            this.addMasteryPoint(instance, entity);
            List<LivingEntity> targets = entity.level()
               .getEntitiesOfClass(
                  LivingEntity.class,
                  entity.getBoundingBox().inflate(CONFIG.carveRange),
                  living -> !living.is(entity) && living.isAlive() && !living.isAlliedTo(entity)
               );
            if (!targets.isEmpty()) {
               ItemStack scythe = entity.getMainHandItem();
               float weaponDamage = TensuraDamageHelper.getWeaponBaseDamage(scythe, EquipmentSlotGroup.MAINHAND);

               for (LivingEntity target : targets) {
                  if (target.hurt(this.createSource(instance, entity, DamageTypes.PLAYER_ATTACK, mode), weaponDamage)) {
                     scythe.getItem().hurtEnemy(scythe, target, entity);
                     if (entity.level() instanceof ServerLevel serverLevel) {
                        serverLevel.getChunkSource().broadcastAndSend(entity, new ClientboundAnimatePacket(entity, 4));
                     }
                  }

                  IExistence existence = TensuraStorages.getExistenceFrom(entity);
                  double critMultiplier = player.getAttributeValue(ManasCoreAttributes.CRITICAL_DAMAGE_MULTIPLIER) / 5.0;
                  double baseAttack = player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                  int gainedSouls = (int)((weaponDamage + baseAttack) * critMultiplier * 1000.0);
                  existence.setSoulPoints(existence.getSoulPoints() + gainedSouls);
               }
            }

            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)MysticismSoundEvents.SCYTHE_SLASH.get(),
                  TensuraSkill.ABILITY_SOUND,
                  2.0F,
                  0.25F
               );
            instance.setCoolDown(instance.isMastered(entity) ? 10 : 5, mode);
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 2) {
         CompoundTag skillTag = instance.getOrCreateTag();
         float soulPercent = skillTag != null ? skillTag.getFloat("soulPercent") : 0.0F;
         if (living instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.output_percentage", new Object[]{soulPercent * 10.0F})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)),
               true
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return this.isInSlot(entity);
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.getMastery() >= 0.0 && mode == 2;
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity living, double delta, int mode) {
      if (mode == 2 && TickingSkill.isTickingSkill(living, this, 2)) {
         CompoundTag skillTag = instance.getOrCreateTag();
         float currentScale = skillTag.getFloat("soulPercent");
         float newScale = currentScale + (float)delta;
         if (newScale > 10.0F) {
            newScale = 10.0F;
         } else if (newScale < 0.0F) {
            newScale = 0.0F;
         }

         if (currentScale != newScale) {
            skillTag.putFloat("soulPercent", newScale);
            instance.markDirty();
         }
      }
   }
}
