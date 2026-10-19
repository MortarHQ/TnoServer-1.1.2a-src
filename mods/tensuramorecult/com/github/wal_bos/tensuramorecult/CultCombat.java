package com.github.wal_bos.tensuramorecult;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.entity.BelieverEntity;
import com.github.wal_bos.tensuramorecult.mixin.BelieverAccessor;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

public final class CultCombat {
   private static final Set<LivingEntity> PENDING = Collections.newSetFromMap(new WeakHashMap<>());
   private static final Map<LivingEntity, Long> NEXT_DEBUFF = new WeakHashMap<>();

   public static String name(Entity entity) {
      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
      return id.getNamespace().equals("netherman") ? id.getPath() : "";
   }

   public static boolean boss(Entity entity) {
      return entity instanceof AzazelEntity || entity instanceof AzazelHumanEntity;
   }

   public static boolean ending(Entity entity) {
      if (entity instanceof AzazelEntity azazel) {
         int state = (Integer)azazel.getEntityData().get(AzazelEntity.ATTACK_STATE);
         return state >= 6 && state <= 10;
      } else if (!(entity instanceof AzazelHumanEntity human)) {
         return false;
      } else {
         int state = (Integer)human.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
         return state == 100 || state == 101 || state == 110 || state == 111;
      }
   }

   public static void attributes(EntityAttributeModificationEvent event) {
      for (EntityType<? extends LivingEntity> type : event.getTypes()) {
         if (BuiltInRegistries.ENTITY_TYPE.getKey(type).getNamespace().equals("netherman")) {
            event.add(type, TensuraMoreCult.DAMAGE_MULTIPLIER);
            if (!event.has(type, Attributes.ATTACK_DAMAGE)) {
               event.add(type, Attributes.ATTACK_DAMAGE, 0.0);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void join(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide()) {
         if (event.getEntity() instanceof LivingEntity entity && CultBalance.MOBS.containsKey(name(entity))) {
            applyAttributes(entity);
            PENDING.add(entity);
         }

         if (event.getEntity() instanceof Projectile projectile
            && cultProjectile(projectile)
            && projectile.getOwner() instanceof Player player
            && CultEquipment.weapon(player.getMainHandItem())) {
            projectile.getPersistentData().putDouble("tensuramorecult_weapon_attack", player.getAttributeValue(Attributes.ATTACK_DAMAGE));
         }
      }
   }

   private static double hinata(Holder<Attribute> attribute) {
      EntityType<? extends LivingEntity> type = (EntityType<? extends LivingEntity>)BuiltInRegistries.ENTITY_TYPE
         .get(ResourceLocation.parse("tensura:hinata_sakaguchi"));
      return DefaultAttributes.getSupplier(type).getBaseValue(attribute);
   }

   private static void stat(LivingEntity entity, Holder<Attribute> attribute, double desired, String key) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         ResourceLocation id = TensuraMoreCult.id(key);
         instance.removeModifier(id);
         instance.addPermanentModifier(new AttributeModifier(id, desired - instance.getBaseValue(), Operation.ADD_VALUE));
      }
   }

   public static void applyAttributes(LivingEntity entity) {
      CultBalance.Profile profile = CultBalance.MOBS.get(name(entity));
      if (profile != null) {
         float oldMaximum = entity.getMaxHealth();
         float health = entity.getHealth();
         boolean human = entity instanceof AzazelHumanEntity;
         double maximum = boss(entity)
            ? human ? (Double)CultConfig.HUMAN_HEALTH.get() : (Double)CultConfig.AZAZEL_HEALTH.get()
            : entity.getAttributeBaseValue(Attributes.MAX_HEALTH) * profile.healthMultiplier() * (Double)CultConfig.MOB_HEALTH_SCALE.get();
         double damageScale = boss(entity) ? profile.damageMultiplier() : profile.damageMultiplier() * (Double)CultConfig.MOB_DAMAGE_SCALE.get();
         stat(entity, Attributes.MAX_HEALTH, maximum, "health");
         stat(entity, TensuraMoreCult.DAMAGE_MULTIPLIER, damageScale, "damage_scale");
         if (boss(entity)) {
            double stats = human ? (Double)CultConfig.HUMAN_STATS.get() : (Double)CultConfig.AZAZEL_STATS.get();
            double damage = human ? (Double)CultConfig.HUMAN_DAMAGE.get() : (Double)CultConfig.AZAZEL_DAMAGE.get();
            stat(entity, Attributes.ATTACK_DAMAGE, hinata(Attributes.ATTACK_DAMAGE) * damage, "attack");
            stat(entity, Attributes.ARMOR, hinata(Attributes.ARMOR) * stats, "armor");
            stat(entity, TensuraAttributes.PRESENCE_SENSE, hinata(TensuraAttributes.PRESENCE_SENSE) * stats, "presence");
            stat(
               entity,
               TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION,
               hinata(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION) * stats,
               "spiritual_regeneration"
            );
            stat(entity, TensuraAttributes.AURA_REGENERATION_MULTIPLIER, hinata(TensuraAttributes.AURA_REGENERATION_MULTIPLIER) * stats, "aura_regeneration");
            stat(
               entity,
               TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER,
               hinata(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER) * stats,
               "magicule_regeneration"
            );
         }

         if (!ending(entity) && health > 0.0F) {
            entity.setHealth(CultBalance.rescaleHealth(health, oldMaximum, entity.getMaxHealth()));
         }
      }
   }

   private static void initializeExistence(LivingEntity entity) {
      CultBalance.Profile profile = CultBalance.MOBS.get(name(entity));
      if (profile != null) {
         boolean human = entity instanceof AzazelHumanEntity;
         double desiredEP = boss(entity)
            ? human ? (Double)CultConfig.HUMAN_EP.get() : (Double)CultConfig.AZAZEL_EP.get()
            : profile.ep() * (Double)CultConfig.MOB_EP_SCALE.get();
         double desiredSpiritualHealth = boss(entity)
            ? human ? (Double)CultConfig.HUMAN_SPIRITUAL_HEALTH.get() : (Double)CultConfig.AZAZEL_SPIRITUAL_HEALTH.get()
            : profile.spiritualHealth() * (Double)CultConfig.MOB_SPIRITUAL_HEALTH_SCALE.get();
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         double oldEP = EnergyHelper.getBaseMaxEP(entity);
         double oldAura = existence.getAura();
         double oldMagicule = existence.getMagicule();
         double oldSpiritual = existence.getSpiritualHealth();
         double halfEP = desiredEP / 2.0;
         EnergyHelper.setBaseMaxEP(entity, desiredEP);
         AttributeInstance shp = entity.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
         if (shp != null) {
            shp.setBaseValue(desiredSpiritualHealth);
         }

         existence.setAura(oldEP > 0.0 ? Math.min(oldAura, halfEP) : halfEP);
         existence.setMagicule(oldEP > 0.0 ? Math.min(oldMagicule, halfEP) : halfEP);
         existence.setSpiritualHealth(oldEP > 0.0 ? Math.min(oldSpiritual, desiredSpiritualHealth) : desiredSpiritualHealth);
         existence.markDirty();
         SkillStorage storage = SkillAPI.getSkillsFrom(entity);
         if (storage.getSkill((ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get()).isPresent()) {
            storage.forgetSkill((ManasSkill)ExtraSkills.ULTRASPEED_REGENERATION.get());
         }

         Registry<EntityExistenceData> registry = entity.registryAccess().registryOrThrow(TensuraCustomData.ENTITY_EXISTENCE);
         EntityExistenceData data = (EntityExistenceData)registry.get(TensuraMoreCult.id(name(entity)));
         if (data != null) {
            data.abilities().ifPresent(ids -> ids.forEach(id -> {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               if (skill != null && !skill.equals(ExtraSkills.ULTRASPEED_REGENERATION.get())) {
                  if (storage.getSkill(skill).isEmpty()) {
                     TensuraSkillInstance instance = new TensuraSkillInstance(skill);
                     instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                     SkillHelper.learnSkill(entity, instance);
                  }

                  storage.getSkill(skill).ifPresent(instancex -> {
                     if (!ending(entity) && instancex.canBeToggled(entity) && !instancex.isToggled()) {
                        instancex.setToggled(true);
                        instancex.onToggleOn(entity);
                        instancex.markDirty();
                     }
                  });
               }
            }));
         }

         if (boss(entity)) {
            configureBossSkills(entity);
         }
      }
   }

   private static void configureBossSkills(LivingEntity entity) {
      boolean human = entity instanceof AzazelHumanEntity;
      configureBossSkill(
         entity,
         human ? (Boolean)CultConfig.HUMAN_GREED.get() : (Boolean)CultConfig.AZAZEL_GREED.get(),
         (String)CultConfig.GREED_SKILL_ID.get(),
         (ManasSkill)UniqueSkills.GREED.get(),
         true
      );
      configureBossSkill(
         entity,
         human ? (Boolean)CultConfig.HUMAN_PRIDE.get() : (Boolean)CultConfig.AZAZEL_PRIDE.get(),
         (String)CultConfig.PRIDE_SKILL_ID.get(),
         (ManasSkill)UniqueSkills.PRIDE.get(),
         true
      );
      boolean resistances = human ? (Boolean)CultConfig.HUMAN_RESISTANCES.get() : (Boolean)CultConfig.AZAZEL_RESISTANCES.get();

      for (String configuredId : ((String)CultConfig.BOSS_RESISTANCE_SKILL_IDS.get()).split(",")) {
         String id = configuredId.trim();
         if (!id.isEmpty()) {
            configureBossSkill(entity, resistances, id, null, false);
         }
      }
   }

   private static void configureBossSkill(LivingEntity entity, boolean enabled, String configuredId, ManasSkill fallback, boolean activeSlot) {
      ManasSkill skill = resolveSkill(configuredId, fallback);
      if (skill != null) {
         SkillStorage storage = SkillAPI.getSkillsFrom(entity);
         if (!enabled) {
            if (storage.getSkill(skill).isPresent()) {
               storage.forgetSkill(skill);
            }
         } else {
            if (storage.getSkill(skill).isEmpty()) {
               TensuraSkillInstance instance = new TensuraSkillInstance(skill);
               instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               SkillHelper.learnSkill(entity, instance);
            }

            storage.getSkill(skill).ifPresent(instancex -> {
               if ((Boolean)CultConfig.TOGGLE_BOSS_SKILLS.get() && !ending(entity) && instancex.canBeToggled(entity) && !instancex.isToggled()) {
                  instancex.setToggled(true);
                  instancex.onToggleOn(entity);
                  instancex.markDirty();
               }
            });
            if (activeSlot && storage.getSkill(skill).isPresent()) {
               slot(entity, skill);
            }
         }
      }
   }

   private static ManasSkill resolveSkill(String configuredId, ManasSkill fallback) {
      ResourceLocation id = ResourceLocation.tryParse(configuredId.trim());
      if (id == null) {
         return fallback;
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
      return skill == null ? fallback : skill;
   }

   private static void slot(LivingEntity entity, ManasSkill skill) {
      IAbility ability = TensuraStorages.getAbilityFrom(entity);
      if (!ability.isAbilityInActivePreset(skill)) {
         List<AbilitySlot> slots = ability.getAbilitySlots();

         for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getSkill() == null) {
               ability.setAbilitySlot(i, skill, 0);
               ability.markDirty();
               return;
            }
         }
      }
   }

   @SubscribeEvent
   public static void beforeTick(Pre event) {
      if (event.getEntity() instanceof LivingEntity entity && !entity.level().isClientSide() && ending(entity)) {
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
         if (entity.getHealth() <= 0.0F) {
            entity.setHealth(1.0F);
         }
      }
   }

   @SubscribeEvent
   public static void afterTick(Post event) {
      if (event.getEntity() instanceof LivingEntity entity && !entity.level().isClientSide()) {
         if (entity.tickCount >= 2 && PENDING.remove(entity)) {
            initializeExistence(entity);
         }

         if (entity instanceof BelieverEntity believer
            && entity.tickCount % 80 == 0
            && (Boolean)CultConfig.PRAYER.get()
            && entity.isAlive()
            && (Boolean)believer.getEntityData().get(BelieverEntity.IS_PRAYING)) {
            AzazelEntity azazel = ((BelieverAccessor)believer).tensuramorecult$getAzazel();
            if (azazel != null && azazel.isAlive() && !ending(azazel) && !(believer.distanceToSqr(azazel) >= 1600.0)) {
               TensuraMobEffect.addEffect(
                  azazel,
                  new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 100, 1, false, false, true),
                  believer,
                  (ManasSkill)ExtraSkills.INFINITE_REGENERATION.get()
               );
            }
         }
      }
   }

   @SubscribeEvent
   public static void heal(LivingHealEvent event) {
      if (ending(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void death(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide() && ending(entity)) {
         event.setCanceled(true);
         if (entity.getHealth() <= 0.0F) {
            entity.setHealth(1.0F);
         }
      }
   }

   public static boolean cultProjectile(Entity entity) {
      return switch (name(entity)) {
         case "azazel_spike", "azazel_spike_projectile", "azazel_splash" -> true;
         default -> false;
      };
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void incoming(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      if (!target.level().isClientSide()) {
         if (ending(target)) {
            event.setCanceled(true);
         } else if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            DamageSource var7 = event.getSource();
            if (boss(attacker)) {
               boolean vanilla = var7.typeHolder().unwrapKey().map(key -> key.location().getNamespace().equals("minecraft")).orElse(false);
               if (vanilla) {
                  event.setAmount(
                     CultBalance.scriptedDamage(event.getAmount(), attacker.getAttributeValue(Attributes.ATTACK_DAMAGE), attacker instanceof AzazelHumanEntity)
                  );
               }
            } else if (CultBalance.MOBS.containsKey(name(attacker))) {
               event.setAmount((float)(event.getAmount() * attacker.getAttributeValue(TensuraMoreCult.DAMAGE_MULTIPLIER)));
            } else if (attacker instanceof Player && var7.getDirectEntity() instanceof Projectile projectile && cultProjectile(projectile)) {
               double attack = projectile.getPersistentData().getDouble("tensuramorecult_weapon_attack");
               if (attack > 0.0) {
                  event.setAmount((float)(event.getAmount() * Math.max(1.0, attack / 20.0)));
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void damage(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post event) {
      LivingEntity target = event.getEntity();
      if (!target.level().isClientSide()
         && !(event.getNewDamage() <= 0.0F)
         && (Boolean)CultConfig.DEBUFFS.get()
         && event.getSource().getEntity() instanceof LivingEntity attacker
         && attacker != target
         && !attacker.isAlliedTo(target)) {
         String type = name(attacker);
         Entity direct = event.getSource().getDirectEntity();
         boolean weapon = attacker instanceof Player
            && (
               direct == attacker && CultEquipment.weapon(attacker.getMainHandItem())
                  || direct != null && cultProjectile(direct) && direct.getPersistentData().getDouble("tensuramorecult_weapon_attack") > 0.0
            );
         if (weapon || Set.of("azazel", "azazel_human", "guardian", "bell_guardian", "statue_bossunit", "welcomer", "manipulator").contains(type)) {
            long now = target.level().getGameTime();
            if (NEXT_DEBUFF.getOrDefault(target, 0L) <= now) {
               NEXT_DEBUFF.put(target, now + 80L);
               if (type.equals("azazel_human")) {
                  debuff(target, attacker, TensuraMobEffects.CURSE, 1);
                  debuff(target, attacker, TensuraMobEffects.MAGICULE_POISON, 1);
               } else if (type.equals("azazel")) {
                  debuff(target, attacker, TensuraMobEffects.FRAGILITY, 1);
                  debuff(target, attacker, TensuraMobEffects.FEAR, 0);
               } else if (type.equals("manipulator")) {
                  debuff(target, attacker, TensuraMobEffects.CONFUSION, 0);
               } else if (!weapon && !type.equals("welcomer")) {
                  debuff(target, attacker, TensuraMobEffects.FRAGILITY, 0);
               } else {
                  debuff(target, attacker, TensuraMobEffects.CURSE, 0);
               }
            }
         }
      }
   }

   private static void debuff(LivingEntity target, LivingEntity source, RegistrySupplier<MobEffect> effect, int amplifier) {
      ManasSkill sourceSkill = source instanceof AzazelHumanEntity
         ? resolveSkill((String)CultConfig.PRIDE_SKILL_ID.get(), (ManasSkill)UniqueSkills.PRIDE.get())
         : resolveSkill((String)CultConfig.GREED_SKILL_ID.get(), (ManasSkill)UniqueSkills.GREED.get());
      TensuraMobEffect.addEffect(target, new MobEffectInstance(TensuraMobEffects.getReference(effect), 100, amplifier), source, sourceSkill);
   }

   public static boolean ownedDamage(LivingEntity target, DamageSource original, float amount, Entity direct) {
      Entity owner = direct instanceof Projectile projectile ? projectile.getOwner() : direct;
      if (direct.getPersistentData().hasUUID("tensuramorecult_owner") && direct.level() instanceof ServerLevel level) {
         owner = level.getEntity(direct.getPersistentData().getUUID("tensuramorecult_owner"));
      }

      return target.hurt(owner == null ? original : new DamageSource(original.typeHolder(), direct, owner), amount);
   }

   private CultCombat() {
   }
}
