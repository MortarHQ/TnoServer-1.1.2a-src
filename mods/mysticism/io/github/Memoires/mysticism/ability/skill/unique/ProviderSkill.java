package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.skill.ProviderHaloEntity;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ProviderSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Provider CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Provider;
   private static final String NBT_HALO_ID = "ProviderHaloId";
   private static final String NBT_HALO_DIM = "ProviderHaloDim";

   public ProviderSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/provider.png");
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "provider.generosity";
         case 1 -> "provider.pay_it_forward";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return entity.isShiftKeyDown() && mode == 1 && entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD));
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.generosityHaloCost;
         case 1 -> CONFIG.payItForwardCostTicking;
         default -> 500.0;
      };
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!owner.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD))) {
         return true;
      }

      double multiplier = getDamageMultiplier(getTier(instance.getOrCreateTag().getInt("Contributions")));
      Float damage = (Float)amount.get();
      if (damage != null) {
         amount.set((float)(damage.floatValue() * multiplier));
      }

      return true;
   }

   private static int getTier(int contributions) {
      int tier;
      if (contributions >= CONFIG.contributionsTier5) {
         tier = 5;
      } else if (contributions >= CONFIG.contributionsTier4) {
         tier = 4;
      } else if (contributions >= CONFIG.contributionsTier3) {
         tier = 3;
      } else if (contributions >= CONFIG.contributionsTier2) {
         tier = 2;
      } else if (contributions >= CONFIG.contributionsTier1) {
         tier = 1;
      } else {
         tier = 0;
      }

      return tier;
   }

   private static double getDamageMultiplier(int tier) {
      return switch (tier) {
         case 1 -> 1.5;
         case 2 -> 2.0;
         case 3 -> 3.0;
         case 4 -> 4.0;
         case 5 -> 5.0;
         default -> 1.0;
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      switch (mode) {
         case 0:
            if (!entity.isShiftKeyDown()) {
               if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                  return;
               }

               if (tag.contains("ProviderHaloId", 11)) {
                  if (entity instanceof Player p) {
                     p.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.provider.generosity.halo_already_spawned").withStyle(ChatFormatting.RED), false
                     );
                  }

                  return;
               }

               if (!(entity.level() instanceof ServerLevel level)) {
                  return;
               }

               ProviderHaloEntity halo = (ProviderHaloEntity)((EntityType)MysticismSkillEntityTypes.PROVIDER_HALO.get()).create(level);
               if (halo == null) {
                  return;
               }

               float yaw = entity.getYRot();
               Vec3 forward = entity.getLookAngle().normalize();
               Vec3 pos = entity.position().add(forward.scale(2.0)).add(0.0, 1.2, 0.0);
               halo.moveTo(pos.x, pos.y, pos.z, yaw, 0.0F);
               halo.setSpawnYaw(yaw);
               halo.setOwner(entity);
               halo.setMpCost(CONFIG.generosityHaloCostSecond);
               halo.setLife(CONFIG.generosityHaloLifespan);
               level.addFreshEntity(halo);
               level.playSound(null, halo.getX(), halo.getY(), halo.getZ(), TensuraSoundEvents.BUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               tag.putUUID("ProviderHaloId", halo.getUUID());
               tag.putString("ProviderHaloDim", level.dimension().location().toString());
               int cooldown = instance.isMastered(entity) ? CONFIG.generosityCooldownMastery : CONFIG.generosityCooldown;
               instance.setCoolDown(cooldown, mode);
            } else {
               if (!(entity.level() instanceof ServerLevel level)) {
                  return;
               }

               if (tag.contains("ProviderHaloId", 11)) {
                  UUID haloId = tag.getUUID("ProviderHaloId");
                  String dimStr = tag.getString("ProviderHaloDim");
                  ResourceLocation haloDimId = ResourceLocation.tryParse(dimStr);
                  if (haloDimId != null) {
                     ResourceKey<Level> haloDimKey = ResourceKey.create(Registries.DIMENSION, haloDimId);
                     MinecraftServer server = level.getServer();
                     ServerLevel haloLevel = server.getLevel(haloDimKey);
                     if (haloLevel != null && haloLevel.getEntity(haloId) instanceof ProviderHaloEntity halo) {
                        halo.remove(RemovalReason.DISCARDED);
                        level.playSound(null, halo.getX(), halo.getY(), halo.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                     }
                  }

                  tag.remove("ProviderHaloId");
                  tag.remove("ProviderHaloDim");
                  if (entity instanceof Player p) {
                     p.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.provider.generosity.halo_removed").withStyle(ChatFormatting.GREEN), false
                     );
                  }
               } else if (entity instanceof Player p) {
                  p.displayClientMessage(Component.translatable("mysticism.skill.mode.provider.generosity.no_halo").withStyle(ChatFormatting.RED), false);
               }
            }
            break;
         case 1:
            if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD)) && entity.isShiftKeyDown()) {
               entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD));
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               return;
            }

            if (!EnergyHelper.isOutOfEnergy(entity, 0.0, CONFIG.payItForwardCost)) {
               int contributions = tag.getInt("Contributions");
               int tier = getTier(contributions);
               int duration = contributions / 100 * 20;
               if (tier != 0 && !entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD))) {
                  TensuraMobEffect.addEffect(
                     entity,
                     new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD), duration, tier - 1, false, false, false),
                     entity,
                     this,
                     mode
                  );
                  entity.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               } else if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD))) {
                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.provider.pay_it_forward.already_active").withStyle(ChatFormatting.RED), false
                     );
                  }

                  entity.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               } else {
                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.provider.pay_it_forward.too_little_contributions", new Object[]{CONFIG.contributionsTier1})
                           .withStyle(ChatFormatting.RED),
                        false
                     );
                  }

                  entity.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               }
            }
      }
   }
}
