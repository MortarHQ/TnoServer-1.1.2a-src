package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ProviderHaloEntity extends TensuraProjectile implements GeoEntity {
   protected static final UniqueMysticismSkillConfig.Provider CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Provider;
   private final AnimatableInstanceCache geckoCache = GeckoLibUtil.createInstanceCache(this);
   private static final int CONTRIBUTION_COOLDOWN_TICKS = 20;
   private final Object2IntOpenHashMap<UUID> contributionCooldown = new Object2IntOpenHashMap();
   private static final EntityDataAccessor<Float> DATA_SPAWN_YAW = SynchedEntityData.defineId(ProviderHaloEntity.class, EntityDataSerializers.FLOAT);

   public ProviderHaloEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.noPhysics = true;
   }

   protected void defineSynchedData(Builder b) {
      super.defineSynchedData(b);
      b.define(DATA_SPAWN_YAW, 0.0F);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("SpawnYaw")) {
         this.setSpawnYaw(tag.getFloat("SpawnYaw"));
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putFloat("SpawnYaw", this.getSpawnYaw());
   }

   public void tick() {
      this.lockYaw();
      super.tick();
      if (!this.level().isClientSide) {
         this.setDimensions(2.0, 7.1, 0.25);
      }

      if (this.tickCount % 20 == 0 && this.tickCount > 0 && this.getOwner() instanceof Player player && EnergyHelper.isOutOfEnergy(player, 0.0, this.mpCost)) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         player.displayClientMessage(Component.translatable("mysticism.skill.mode.provider.generosity.out_of_magicule").withStyle(ChatFormatting.RED), false);
         this.remove(RemovalReason.DISCARDED);
         Optional<ManasSkillInstance> instOpt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.PROVIDER.get());
         instOpt.ifPresent(inst -> {
            CompoundTag t = inst.getOrCreateTag();
            t.remove("ProviderHaloId");
            t.remove("ProviderHaloDim");
         });
      }

      if (!this.contributionCooldown.isEmpty()) {
         ObjectIterator<Entry<UUID>> it = this.contributionCooldown.object2IntEntrySet().fastIterator();

         while (it.hasNext()) {
            Entry<UUID> e = (Entry<UUID>)it.next();
            int left = e.getIntValue() - 1;
            if (left <= 0) {
               it.remove();
            } else {
               e.setValue(left);
            }
         }
      }

      AABB box = this.getBoundingBox().inflate(0.2);

      for (Entity e : this.level().getEntities(this, box, ex -> !(ex instanceof ProviderHaloEntity))) {
         if (!e.equals(this.getOwner())) {
            UUID id = e.getUUID();
            if (!this.contributionCooldown.containsKey(id)) {
               this.contributionCooldown.put(id, 20);
               if (this.getOwner() instanceof Player player) {
                  Optional<ManasSkillInstance> instOpt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.PROVIDER.get());
                  instOpt.ifPresent(inst -> {
                     CompoundTag t = inst.getOrCreateTag();
                     t.putInt("Contributions", t.getInt("Contributions") + 1);
                  });
               }

               if (e instanceof LivingEntity le && !e.equals(this.getOwner())) {
                  AttributeInstance move = le.getAttribute(Attributes.MOVEMENT_SPEED);
                  if (move != null) {
                     le.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.PAY_IT_FORWARD), 200, 0, false, false, true));
                  }
               }

               if (e instanceof Projectile proj && (proj.getOwner() == null || !proj.getOwner().equals(this.getOwner()))) {
                  Holder<MobEffect> providerEffect = TensuraMobEffects.getReference(MysticismMobEffects.PROVIDER_BOOST);
                  if (proj.getOwner() instanceof LivingEntity living && !living.hasEffect(providerEffect)) {
                     if (proj instanceof AbstractArrow arrow) {
                        arrow.setBaseDamage(arrow.getBaseDamage() * CONFIG.generosityBoost);
                     } else if (proj instanceof TensuraProjectile tensuraProjectile) {
                        tensuraProjectile.setDamage((float)(tensuraProjectile.getDamage() * CONFIG.generosityBoost));
                     }
                  }
               }
            }
         }
      }
   }

   public float getSpawnYaw() {
      return (Float)this.entityData.get(DATA_SPAWN_YAW);
   }

   private void setDimensions(double halfWidth, double height, double halfDepth) {
      double x = this.getX();
      double y = this.getY();
      double z = this.getZ();
      this.setBoundingBox(new AABB(x - halfWidth, y, z - halfDepth, x + halfWidth, y + height, z + halfDepth));
   }

   private void lockYaw() {
      this.yRotO = this.getSpawnYaw();
      this.setYRot(this.getSpawnYaw());
      this.setYBodyRot(this.getSpawnYaw());
      this.setYHeadRot(this.getSpawnYaw());
   }

   public void setSpawnYaw(float yaw) {
      this.entityData.set(DATA_SPAWN_YAW, yaw);
      this.setYRot(yaw);
      this.yRotO = yaw;
      this.setYBodyRot(yaw);
      this.setYHeadRot(yaw);
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geckoCache;
   }
}
