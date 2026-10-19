package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.sound.BossMusic;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.DieAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.advancement.SneakGroveTrigger;
import com.bobmowzie.mowziesmobs.server.ai.LookAtTargetGoal;
import com.bobmowzie.mowziesmobs.server.ai.NearestAttackableTargetPredicateGoal;
import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthiTrade;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;

public class EntityUmvuthi extends MowzieGeckoEntity implements LeaderSunstrikeImmune, Enemy {
   public static final AbilityType<EntityUmvuthi, DieAbility<EntityUmvuthi>> DIE_ABILITY = new AbilityType(
      "umvuthi_die", (type, entity) -> new DieAbility<EntityUmvuthi>(type, entity, RawAnimation.begin().thenPlay("death"), 115) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 1) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_HURT.get(), this.getUser().getSoundVolume(), this.getUser().getVoicePitch());
            }

            if (this.getTicksInUse() == 14) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_DIE.get(), this.getUser().getSoundVolume(), 1.0F);
            }

            if (this.getTicksInUse() == 80) {
               this.getUser().playSound((SoundEvent)MMSounds.MISC_METAL_IMPACT.get(), this.getUser().getSoundVolume(), 1.0F);
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthi, HurtAbility<EntityUmvuthi>> HURT_ABILITY = new AbilityType(
      "umvuthi_hurt", (type, entity) -> new HurtAbility<>(type, entity, RawAnimation.begin().thenPlay("hurt"), 13, 10)
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> BELLY_ABILITY = new AbilityType(
      "umvuthi_belly", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthi>(type, entity, RawAnimation.begin().thenPlay("belly_drum"), 40, true) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 9 || this.getTicksInUse() == 29) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_BELLY.get(), 3.0F, 1.0F);
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> TALK_ABILITY = new AbilityType<>(
      "umvuthi_talk", (type, entity) -> new SimpleAnimationAbility(type, entity, RawAnimation.begin().thenPlay("talk"), 23, true)
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> ROAR_ABILITY = new AbilityType(
      "umvuthi_roar", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthi>(type, entity, RawAnimation.begin().thenPlay("roar"), 70, false) {
         @Override
         public void tickUsing() {
            super.tickUsing();
            if (this.getTicksInUse() == 2) {
               this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_ROAR.get(), 3.0F, 1.0F);
            }
         }
      }
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SunstrikeAbility> SUNSTRIKE_ABILITY = new AbilityType(
      "umvuthi_sunstrike", EntityUmvuthi.SunstrikeAbility::new
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SolarFlareAbility> SOLAR_FLARE_ABILITY = new AbilityType(
      "umvuthi_flare", EntityUmvuthi.SolarFlareAbility::new
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SpawnFollowersAbility> SPAWN_ABILITY = new AbilityType(
      "umvuthi_spawn", (type, entity) -> new EntityUmvuthi.SpawnFollowersAbility(type, entity, false)
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SpawnFollowersAbility> SPAWN_SUNBLOCKERS_ABILITY = new AbilityType(
      "umvuthi_spawn_healers", (type, entity) -> new EntityUmvuthi.SpawnFollowersAbility(type, entity, true)
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SolarBeamAbility> SOLAR_BEAM_ABILITY = new AbilityType(
      "umvuthi_solar_beam", EntityUmvuthi.SolarBeamAbility::new
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> BLESS_ABILITY = new AbilityType(
      "umvuthi_bless",
      (type, entity) -> new SimpleAnimationAbility<EntityUmvuthi>(type, entity, RawAnimation.begin().thenPlay("bless"), 84) {
         @Override
         public boolean canCancelActiveAbility() {
            return this.getUser().getActiveAbilityType() == EntityUmvuthi.ROAR_ABILITY
               || this.getUser().getActiveAbilityType() == EntityUmvuthi.TALK_ABILITY
               || this.getUser().getActiveAbilityType() == EntityUmvuthi.BELLY_ABILITY;
         }
      }
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SupernovaAbility> SUPERNOVA_ABILITY = new AbilityType(
      "umvuthi_supernova", EntityUmvuthi.SupernovaAbility::new
   );
   protected AnimationController<MowzieGeckoEntity> maskController = new MowzieAnimationController(this, "mask_controller", 1, this::predicateMask, 0.0);
   protected AnimationController<MowzieGeckoEntity> blinkController = new MowzieAnimationController(this, "blink_controller", 1, this::predicateBlink, 0.0);
   private static final int MAX_HEALTH = 150;
   private static final int SUNSTRIKE_PAUSE_MAX = 50;
   private static final int SUNSTRIKE_PAUSE_MIN = 30;
   private static final int LASER_PAUSE = 230;
   private static final int SUPERNOVA_PAUSE = 230;
   private static final int UMVUTHANA_PAUSE = 200;
   private static final int ROAR_PAUSE = 300;
   private static final int HEAL_PAUSE = 75;
   private static int HEALTH_LOST_BETWEEN_SUNBLOCKERS = 45;
   private static final EntityDataAccessor<Integer> DIRECTION = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DIALOGUE = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<ItemStack> DESIRES = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<CompoundTag> TRADED_PLAYERS = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.COMPOUND_TAG);
   private static final EntityDataAccessor<Float> HEALTH_LOST = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Optional<UUID>> MISBEHAVED_PLAYER = SynchedEntityData.defineId(
      EntityUmvuthi.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Boolean> IS_TRADING = SynchedEntityData.defineId(EntityUmvuthi.class, EntityDataSerializers.BOOLEAN);
   public ControlledAnimation legsUp = new ControlledAnimation(15);
   public ControlledAnimation angryEyebrow = new ControlledAnimation(5);
   private Player customer;
   public int umvuthanaSpawnCount = 0;
   private int direction = 0;
   private boolean blocksByFeet = true;
   private int timeUntilSunstrike = 0;
   private int timeUntilLaser = 0;
   private int timeUntilUmvuthana = 0;
   private int timeUntilRoar = 0;
   private int timeUntilSupernova = 0;
   private int timeUntilHeal = 0;
   public Player blessingPlayer;
   private UmvuthanaHurtByTargetAI hurtByTargetAI;
   public Vec3[] betweenHandPos;
   public Vec3[] headPos;
   public Vec3[] blessingPlayerPos;
   private static final TargetingConditions GIVE_ACHIEVEMENT_PRED = TargetingConditions.forCombat().ignoreInvisibilityTesting();
   private float prevMaskRot = 0.0F;
   private boolean rattling = false;
   private static RawAnimation MASK_TWITCH_ANIM = RawAnimation.begin().thenLoop("mask_twitch");
   private static RawAnimation BLINK_ANIM = RawAnimation.begin().thenLoop("blink");

   public EntityUmvuthi(EntityType<? extends EntityUmvuthi> type, Level world) {
      super(type, world);
      if (this.getDirectionData() == 0) {
         this.setDirection(this.random.nextInt(4) + 1);
      }

      this.xpReward = 45;
      if (world.isClientSide) {
         this.headPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.betweenHandPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.blessingPlayerPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      this.active = true;
      HEALTH_LOST_BETWEEN_SUNBLOCKERS = (Integer)ConfigHandler.COMMON.MOBS.UMVUTHI.spawnHealersThreshold.get();
   }

   protected void registerGoals() {
      super.registerGoals();
      this.hurtByTargetAI = new UmvuthanaHurtByTargetAI(this, false);
      this.targetSelector.addGoal(3, this.hurtByTargetAI);
      this.targetSelector
         .addGoal(
            4,
            new NearestAttackableTargetPredicateGoal<Player>(
               this, Player.class, 0, false, true, TargetingConditions.forCombat().range(this.getAttributeValue(Attributes.FOLLOW_RANGE)).selector(target -> {
                  if (target instanceof Player) {
                     if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
                        return false;
                     }

                     ItemStack headArmorStack = (ItemStack)((Player)target).getInventory().armor.get(3);
                     return !(headArmorStack.getItem() instanceof UmvuthanaMask) || target == this.getMisbehavedPlayer();
                  } else {
                     return true;
                  }
               }).ignoreLineOfSight()
            ) {
               public void stop() {
                  super.stop();
                  EntityUmvuthi.this.setMisbehavedPlayerId(null);
               }
            }
         );
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, IronGolem.class, 0, false, false, null));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Zombie.class, 0, false, false, e -> !(e instanceof ZombifiedPiglin)));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, 0, false, false, null));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, EntityElokosa.class, 0, false, false, null));
      this.goalSelector.addGoal(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.goalSelector.addGoal(6, new UseAbilityAI<>(this, BELLY_ABILITY, false));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SUNSTRIKE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SOLAR_FLARE_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SOLAR_BEAM_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SUPERNOVA_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SPAWN_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, SPAWN_SUNBLOCKERS_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, BLESS_ABILITY));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, TALK_ABILITY, false));
      this.goalSelector.addGoal(2, new UseAbilityAI<>(this, ROAR_ABILITY, true));
      this.goalSelector.addGoal(5, new LookAtTargetGoal(this, 24.0F));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, EntityUmvuthana.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.ATTACK_DAMAGE, 2.0)
         .add(Attributes.MAX_HEALTH, 150.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.FOLLOW_RANGE, 40.0);
   }

   @Override
   public void registerControllers(ControllerRegistrar controllers) {
      super.registerControllers(controllers);
      controllers.add(this.maskController);
      controllers.add(this.blinkController);
   }

   protected <E extends GeoEntity> PlayState predicateMask(AnimationState<E> state) {
      if (this.isAlive()
         && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY
         && this.getActiveAbilityType() != SUPERNOVA_ABILITY
         && this.getActiveAbilityType() != SPAWN_ABILITY
         && this.getActiveAbilityType() != SPAWN_SUNBLOCKERS_ABILITY) {
         state.getController().setAnimation(MASK_TWITCH_ANIM);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   protected <E extends GeoEntity> PlayState predicateBlink(AnimationState<E> event) {
      if (this.isAlive() && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY) {
         event.getController().setAnimation(BLINK_ANIM);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   protected <E extends GeoEntity> void loopingAnimations(AnimationState<E> event) {
      event.getController().transitionLength(4);
      super.loopingAnimations(event);
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   protected SoundEvent getAmbientSound() {
      if (this.getActiveAbility() == null) {
         this.sendAbilityMessage(TALK_ABILITY);
         return (SoundEvent)MMSounds.ENTITY_UMVUTHI_IDLE.get();
      } else {
         return null;
      }
   }

   public void updateRattleSound(float maskRot) {
      if (!this.rattling) {
         if (Math.abs(maskRot - this.prevMaskRot) > 0.05) {
            this.level()
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  (SoundEvent)MMSounds.ENTITY_UMVUTHANA_RATTLE.get(),
                  SoundSource.HOSTILE,
                  0.04F,
                  this.getVoicePitch() * 0.75F,
                  false
               );
         }
      } else if (Math.abs(maskRot - this.prevMaskRot) < 1.0E-8) {
         this.rattling = false;
      }

      this.prevMaskRot = maskRot;
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_UMVUTHI_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return null;
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public boolean shouldRenderSun() {
      return this.deathTime < 85
         && (this.getActiveAbilityType() != SUPERNOVA_ABILITY || this.getActiveAbility().getTicksInUse() <= 5 || this.getActiveAbility().getTicksInUse() > 90);
   }

   @Override
   public void tick() {
      this.legsUp.updatePrevTimer();
      this.angryEyebrow.updatePrevTimer();
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      super.tick();
      if (this.tickCount == 1) {
         this.direction = this.getDirectionData();
      }

      if (this.getActiveAbilityType() != SOLAR_FLARE_ABILITY || this.getActiveAbility().getTicksInUse() < 12 || this.getActiveAbility().getTicksInUse() > 14) {
         this.repelEntities(1.2F, 1.2F, 1.2F, 1.2F);
      }

      this.setYRot((this.direction - 1) * 90);
      this.yBodyRot = this.getYRot();
      if (this.level().isClientSide() && this.shouldRenderSun() && this.headPos != null && this.headPos.length > 0 && this.headPos[0] != null) {
         if (this.tickCount % 10 == 1) {
            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.GLOW,
               this.getX(),
               this.getY(),
               this.getZ(),
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               1.0,
               1.0,
               0.3,
               0.4,
               1.0,
               9.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PinLocation(this.headPos),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.oscillate(12.5F, 13.5F, 12), false
                  )
               }
            );
         }

         if (this.random.nextFloat() < 0.3F) {
            int amount = this.random.nextInt(2) + 1;

            while (amount-- > 0) {
               float theta = this.random.nextFloat() * (float) (Math.PI * 2);
               float r = this.random.nextFloat() * 0.4F;
               float x = r * Mth.cos(theta);
               float z = r * Mth.sin(theta);
               this.level().addParticle(ParticleTypes.SMOKE, this.headPos[0].x() + x, this.headPos[0].y() + 0.1, this.headPos[0].z() + z, 0.0, 0.0, 0.0);
            }
         }
      }

      if (!this.level().isClientSide
         && this.getHealthLost() >= HEALTH_LOST_BETWEEN_SUNBLOCKERS
         && this.getActiveAbility() == null
         && !this.isNoAi()
         && this.getEntitiesNearby(EntityUmvuthanaCrane.class, 40.0).size() < 3) {
         this.sendAbilityMessage(SPAWN_SUNBLOCKERS_ABILITY);
         this.setHealthLost(0.0F);
      }

      if (this.getTarget() != null) {
         LivingEntity target = this.getTarget();
         this.setAngry(true);
         float entityHitAngle = (float)((Math.atan2(target.getZ() - this.getZ(), target.getX() - this.getX()) * (180.0 / Math.PI) - 90.0) % 360.0);
         float entityAttackingAngle = this.getYRot() % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = Math.abs(entityHitAngle - entityAttackingAngle);
         Vec3 betweenEntitiesVec = this.position().subtract(target.position());
         boolean targetComingCloser = target.getDeltaMovement().dot(betweenEntitiesVec) > 0.0 && target.getDeltaMovement().lengthSqr() > 0.015;
         if (this.getActiveAbility() == null
            && !this.isNoAi()
            && this.random.nextInt(80) == 0
            && (this.targetDistance > 5.5 || this.hasEffect(EffectHandler.SUNBLOCK))
            && this.timeUntilUmvuthana <= 0
            && this.getEntitiesNearby(EntityUmvuthana.class, 50.0).size() < 4) {
            this.sendAbilityMessage(SPAWN_ABILITY);
            this.timeUntilUmvuthana = 200;
         } else if (this.getActiveAbility() == null
            && !this.isNoAi()
            && this.getHealthRatio() <= 0.6
            && this.timeUntilLaser <= 0
            && (entityRelativeAngle < 60.0F || entityRelativeAngle > 300.0F)
            && this.getSensing().hasLineOfSight(target)
            && this.targetDistance < 30.0) {
            this.sendAbilityMessage(SOLAR_BEAM_ABILITY);
            this.timeUntilLaser = 230;
         } else if (this.getActiveAbility() == null
            && !this.isNoAi()
            && this.getHealthRatio() <= 0.6
            && !this.hasEffect(EffectHandler.SUNBLOCK)
            && this.timeUntilSupernova <= 0
            && this.targetDistance <= 10.5) {
            this.sendAbilityMessage(SUPERNOVA_ABILITY);
            this.timeUntilSupernova = 230;
         } else if (this.getActiveAbility() != null
            || this.isNoAi()
            || (!(this.targetDistance <= 6.0F) || !targetComingCloser) && !(this.targetDistance < 4.0F)) {
            if (this.getActiveAbility() == null && !this.isNoAi() && this.timeUntilSunstrike <= 0) {
               this.sendAbilityMessage(SUNSTRIKE_ABILITY);
               this.timeUntilSunstrike = this.getTimeUntilSunstrike();
            }
         } else {
            this.sendAbilityMessage(SOLAR_FLARE_ABILITY);
         }

         if (this.hurtByTargetAI != null && !this.hurtByTargetAI.canContinueToUse()) {
            this.hurtByTargetAI.stop();
         }
      } else if (!this.level().isClientSide) {
         this.setAngry(false);
      }

      if (this.tickCount % 20 == 0) {
         this.blocksByFeet = this.checkBlocksByFeet();
      }

      if (this.blocksByFeet) {
         this.legsUp.increaseTimer();
      } else {
         this.legsUp.decreaseTimer();
      }

      if (this.getAngry()) {
         this.angryEyebrow.increaseTimer();
      } else {
         this.angryEyebrow.decreaseTimer();
      }

      if (this.getActiveAbility() == null && !this.isNoAi() && this.getTarget() == null && this.random.nextInt(200) == 0) {
         this.sendAbilityMessage(BELLY_ABILITY);
      }

      if (this.getActiveAbility() == null && !this.isNoAi() && this.getTarget() == null && this.timeUntilRoar <= 0 && this.random.nextInt(300) == 0) {
         this.sendAbilityMessage(ROAR_ABILITY);
         this.timeUntilRoar = 300;
      }

      if (this.getActiveAbilityType() == SOLAR_FLARE_ABILITY) {
         this.yHeadRot = this.getYRot();
         if (this.getActiveAbility().getTicksInUse() == 10) {
            if (this.level().isClientSide) {
               this.spawnExplosionParticles(30);
            }

            this.playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_ATTACK.get(), 1.7F, 0.9F);
         }

         if (this.getActiveAbility().getTicksInUse() <= 6 && this.level().isClientSide) {
            int particleCount = 8;

            while (--particleCount != 0) {
               double radius = 2.0;
               double yaw = this.random.nextFloat() * 2.0F * Math.PI;
               double pitch = this.random.nextFloat() * 2.0F * Math.PI;
               double ox = radius * Math.sin(yaw) * Math.sin(pitch);
               double oy = radius * Math.cos(pitch);
               double oz = radius * Math.cos(yaw) * Math.sin(pitch);
               float offsetX = (float)(-0.3 * Math.sin(this.getYRot() * Math.PI / 180.0));
               float offsetZ = (float)(-0.3 * Math.cos(this.getYRot() * Math.PI / 180.0));
               float offsetY = 1.0F;
               this.level()
                  .addParticle(
                     ParticleOrb.Data.create((float)this.getX() + offsetX, (float)this.getY() + offsetY, (float)this.getZ() + offsetZ, 6.0F),
                     this.getX() + ox + offsetX,
                     this.getY() + offsetY + oy,
                     this.getZ() + oz + offsetZ,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.getActiveAbilityType() == BLESS_ABILITY) {
         this.yHeadRot = this.getYRot();
         if (this.getActiveAbility().getTicksInUse() == 1) {
            this.blessingPlayer = this.getCustomer();
         }

         if (this.level().isClientSide && this.blessingPlayer != null) {
            this.blessingPlayerPos[0] = this.blessingPlayer.position().add(new Vec3(0.0, this.blessingPlayer.getBbHeight() / 2.0F, 0.0));
            if (this.getActiveAbility().getTicksInUse() > 5 && this.getActiveAbility().getTicksInUse() < 40) {
               int particleCount = 2;

               while (--particleCount != 0) {
                  double radius = 0.7F;
                  double yaw = this.random.nextFloat() * 2.0F * Math.PI;
                  double pitch = this.random.nextFloat() * 2.0F * Math.PI;
                  double ox = radius * Math.sin(yaw) * Math.sin(pitch);
                  double oy = radius * Math.cos(pitch);
                  double oz = radius * Math.cos(yaw) * Math.sin(pitch);
                  AdvancedParticleBase.spawnParticle(
                     this.level(),
                     ParticleHandler.ORB2,
                     this.getX() + ox,
                     this.getY() + 0.8F + oy,
                     this.getZ() + oz,
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     20.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.Attractor(this.blessingPlayerPos, 0.5F, 0.2F, ParticleComponent.Attractor.EnumAttractorBehavior.LINEAR),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_X, new ParticleComponent.Oscillator(0.0F, (float)ox, 6.0F, 2.5F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, new ParticleComponent.Oscillator(0.0F, (float)oy, 6.0F, 2.5F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z, new ParticleComponent.Oscillator(0.0F, (float)oz, 6.0F, 2.5F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 1.0F}, new float[]{0.0F, 0.8F}),
                           false
                        )
                     }
                  );
               }
            }

            if (this.getActiveAbility().getTicksInUse() % 15 == 0) {
               AdvancedParticleBase.spawnParticle(
                  this.level(),
                  ParticleHandler.RING2,
                  this.getX(),
                  this.getY() + 0.8F,
                  this.getZ(),
                  0.0,
                  0.0,
                  0.0,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  3.5,
                  1.0,
                  0.8745098F,
                  0.25882354F,
                  1.0,
                  1.0,
                  15.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(5.0F, 35.0F), false
                     )
                  }
               );
            }
         }
      }

      if (this.tickCount % 40 == 0) {
         for (Player player : this.getPlayersNearby(15.0, 15.0, 15.0, 15.0)) {
            ItemStack headArmorStack = (ItemStack)player.getInventory().armor.get(3);
            if (this.getTarget() != player
               && this.canAttack(player, GIVE_ACHIEVEMENT_PRED)
               && headArmorStack.getItem() instanceof UmvuthanaMask
               && player instanceof ServerPlayer serverPlayer) {
               ((SneakGroveTrigger)AdvancementHandler.SNEAK_VILLAGE_TRIGGER.value()).trigger(serverPlayer);
            }
         }
      }

      if (!this.level().isClientSide
         && this.getTarget() == null
         && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY
         && this.getActiveAbilityType() != SUPERNOVA_ABILITY) {
         this.timeUntilHeal--;
         if ((Boolean)ConfigHandler.COMMON.MOBS.UMVUTHI.healsOutOfBattle.get() && this.timeUntilHeal <= 0) {
            this.heal(0.3F);
         }

         if (this.getHealth() == this.getMaxHealth()) {
            this.setHealthLost(0.0F);
         }
      } else {
         this.timeUntilHeal = 75;
      }

      if (this.timeUntilSunstrike > 0) {
         this.timeUntilSunstrike--;
      }

      if (this.timeUntilLaser > 0 && this.getActiveAbilityType() != SUPERNOVA_ABILITY) {
         this.timeUntilLaser--;
      }

      if (this.timeUntilUmvuthana > 0) {
         this.timeUntilUmvuthana--;
      }

      if (this.timeUntilSupernova > 0 && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY) {
         this.timeUntilSupernova--;
      }

      if (this.timeUntilRoar > 0) {
         this.timeUntilRoar--;
      }
   }

   @Override
   public AbilityType getHurtAbility() {
      return HURT_ABILITY;
   }

   @Override
   public AbilityType getDeathAbility() {
      return DIE_ABILITY;
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (source == this.level().damageSources().hotFloor()) {
         return false;
      }

      if (this.hasEffect(EffectHandler.SUNBLOCK) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         if (source.getDirectEntity() != null) {
            this.playSound((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
         }

         return false;
      } else {
         this.timeUntilHeal = 75;
         float prevHealth = this.getHealth();
         boolean superResult = super.hurt(source, damage);
         if (superResult) {
            float diffHealth = prevHealth - this.getHealth();
            this.setHealthLost(this.getHealthLost() + diffHealth);
         }

         return superResult;
      }
   }

   private boolean checkBlocksByFeet() {
      BlockState blockLeft;
      BlockState blockRight;
      if (this.direction == 1) {
         BlockPos posLeft = new BlockPos(Mth.floor(this.getX()) + 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) + 1);
         BlockPos posRight = new BlockPos(Mth.floor(this.getX()) - 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) + 1);
         blockLeft = this.level().getBlockState(posLeft);
         blockRight = this.level().getBlockState(posRight);
      } else if (this.direction == 2) {
         BlockPos posLeft = new BlockPos(Mth.floor(this.getX()) - 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) + 1);
         BlockPos posRight = new BlockPos(Mth.floor(this.getX()) - 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) - 1);
         blockLeft = this.level().getBlockState(posLeft);
         blockRight = this.level().getBlockState(posRight);
      } else if (this.direction == 3) {
         BlockPos posLeft = new BlockPos(Mth.floor(this.getX()) - 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) - 1);
         BlockPos posRight = new BlockPos(Mth.floor(this.getX()) + 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) - 1);
         blockLeft = this.level().getBlockState(posLeft);
         blockRight = this.level().getBlockState(posRight);
      } else {
         if (this.direction != 4) {
            return false;
         }

         BlockPos posLeft = new BlockPos(Mth.floor(this.getX()) + 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) - 1);
         BlockPos posRight = new BlockPos(Mth.floor(this.getX()) + 1, Math.round((float)(this.getY() - 1.0)), Mth.floor(this.getZ()) + 1);
         blockLeft = this.level().getBlockState(posLeft);
         blockRight = this.level().getBlockState(posRight);
      }

      return blockLeft.blocksMotion() || blockRight.blocksMotion();
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.25F;
         float yaw = i * ((float) (Math.PI * 2) / amount);
         float vy = this.random.nextFloat() * 0.1F - 0.05F;
         float vx = 0.25F * Mth.cos(yaw);
         float vz = 0.25F * Mth.sin(yaw);
         this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 1.0, this.getZ(), vx, vy, vz);
      }
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DIRECTION, 0);
      builder.define(DIALOGUE, 0);
      builder.define(ANGRY, false);
      builder.define(
         DESIRES,
         new ItemStack(
            (ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse((String)ConfigHandler.COMMON.MOBS.UMVUTHI.whichItem.get())),
            (Integer)ConfigHandler.COMMON.MOBS.UMVUTHI.howMany.get()
         )
      );
      builder.define(TRADED_PLAYERS, new CompoundTag());
      builder.define(HEALTH_LOST, 0.0F);
      builder.define(MISBEHAVED_PLAYER, Optional.empty());
      builder.define(IS_TRADING, false);
   }

   public int getDirectionData() {
      return (Integer)this.getEntityData().get(DIRECTION);
   }

   public void setDirection(int direction) {
      this.getEntityData().set(DIRECTION, direction);
   }

   public int getWhichDialogue() {
      return (Integer)this.getEntityData().get(DIALOGUE);
   }

   public void setWhichDialogue(int dialogue) {
      this.getEntityData().set(DIALOGUE, dialogue);
   }

   public boolean getAngry() {
      return (Boolean)this.getEntityData().get(ANGRY);
   }

   public void setAngry(boolean angry) {
      this.getEntityData().set(ANGRY, angry);
   }

   public void setDesires(ItemStack stack) {
      this.getEntityData().set(DESIRES, stack);
   }

   public ItemStack getDesires() {
      return (ItemStack)this.getEntityData().get(DESIRES);
   }

   public void setTradedPlayersCompound(ListTag players) {
      CompoundTag compound = new CompoundTag();
      compound.put("players", players);
      this.getEntityData().set(TRADED_PLAYERS, compound);
   }

   public Set<UUID> getTradedPlayers() {
      Set<UUID> tradedPlayers = new HashSet<>();
      CompoundTag compound = (CompoundTag)this.getEntityData().get(TRADED_PLAYERS);

      for (Tag player : compound.getList("players", 11)) {
         tradedPlayers.add(NbtUtils.loadUUID(player));
      }

      return tradedPlayers;
   }

   public float getHealthLost() {
      return (Float)this.getEntityData().get(HEALTH_LOST);
   }

   public void setHealthLost(float amount) {
      this.getEntityData().set(HEALTH_LOST, amount);
   }

   public boolean doesItemSatisfyDesire(ItemStack stack) {
      return canPayFor(stack, this.getDesires());
   }

   public boolean fulfillDesire(Slot input) {
      ItemStack desires = this.getDesires();
      if (canPayFor(input.getItem(), desires)) {
         input.remove(desires.getCount());
         return true;
      } else {
         return false;
      }
   }

   public boolean hasTradedWith(Player player) {
      return this.getTradedPlayers().contains(player.getGameProfile().getId());
   }

   public void rememberTrade(Player player) {
      CompoundTag compound = (CompoundTag)this.getEntityData().get(TRADED_PLAYERS);
      ListTag players = compound.getList("players", 11);
      players.add(NbtUtils.createUUID(player.getGameProfile().getId()));
      compound.put("players", players);
      this.getEntityData().set(TRADED_PLAYERS, compound);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("direction", this.getDirectionData());
      CompoundTag compoundTradedPlayers = (CompoundTag)this.getEntityData().get(TRADED_PLAYERS);
      ListTag players = compoundTradedPlayers.getList("players", 11);
      compound.put("players", players);
      compound.putInt("HomePosX", this.getRestrictCenter().getX());
      compound.putInt("HomePosY", this.getRestrictCenter().getY());
      compound.putInt("HomePosZ", this.getRestrictCenter().getZ());
      compound.putFloat("healthLost", this.getHealthLost());
      if (this.getMisbehavedPlayerId() != null) {
         compound.putUUID("MisbehavedPlayer", this.getMisbehavedPlayerId());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setDirection(compound.getInt("direction"));
      ListTag players = compound.getList("players", 11);
      this.setTradedPlayersCompound(players);
      int i = compound.getInt("HomePosX");
      int j = compound.getInt("HomePosY");
      int k = compound.getInt("HomePosZ");
      this.restrictTo(new BlockPos(i, j, k), -1);
      this.setHealthLost(compound.getInt("healthLost"));
      UUID uuid;
      if (compound.hasUUID("MisbehavedPlayer")) {
         uuid = compound.getUUID("MisbehavedPlayer");
      } else {
         String s = compound.getString("MisbehavedPlayer");
         uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
      }

      if (uuid != null) {
         try {
            this.setMisbehavedPlayerId(uuid);
         } catch (Throwable var8) {
         }
      }
   }

   @Nullable
   public UUID getMisbehavedPlayerId() {
      return (UUID)((Optional)this.entityData.get(MISBEHAVED_PLAYER)).orElse((UUID)null);
   }

   public void setMisbehavedPlayerId(@Nullable UUID p_184754_1_) {
      this.entityData.set(MISBEHAVED_PLAYER, Optional.ofNullable(p_184754_1_));
   }

   @Nullable
   public LivingEntity getMisbehavedPlayer() {
      try {
         UUID uuid = this.getMisbehavedPlayerId();
         return uuid == null ? null : this.level().getPlayerByUUID(uuid);
      } catch (IllegalArgumentException illegalargumentexception) {
         return null;
      }
   }

   protected void playStepSound(BlockPos pos, BlockState blockState) {
   }

   private int getTimeUntilSunstrike() {
      float damageRatio = 1.0F - this.getHealthRatio();
      if (damageRatio > 0.6) {
         damageRatio = 0.6F;
      }

      return (int)(50.0F - damageRatio / 0.6F * 20.0F);
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{
         DIE_ABILITY,
         HURT_ABILITY,
         BELLY_ABILITY,
         TALK_ABILITY,
         SUNSTRIKE_ABILITY,
         SOLAR_FLARE_ABILITY,
         SPAWN_ABILITY,
         SPAWN_SUNBLOCKERS_ABILITY,
         SOLAR_BEAM_ABILITY,
         BLESS_ABILITY,
         SUPERNOVA_ABILITY,
         ROAR_ABILITY
      };
   }

   @Override
   public void die(DamageSource cause) {
      super.die(cause);

      for (EntityUmvuthana entityUmvuthana : this.getEntitiesNearby(EntityUmvuthana.class, 30.0, 20.0, 30.0, 30.0)) {
         if (entityUmvuthana.isUmvuthiDevoted()) {
            if (entityUmvuthana instanceof EntityUmvuthanaCrane) {
               ((EntityUmvuthanaCrane)entityUmvuthana).hasTriedOrSucceededTeleport = true;
            }

            entityUmvuthana.timeUntilDeath = this.random.nextInt(20);
         }
      }

      super.die(cause);
   }

   public void setTrading(boolean trading) {
      this.entityData.set(IS_TRADING, trading);
   }

   public boolean isTrading() {
      return (Boolean)this.entityData.get(IS_TRADING);
   }

   public Player getCustomer() {
      return this.customer;
   }

   public void setCustomer(Player customer) {
      this.setTrading(customer != null);
      this.customer = customer;
   }

   public void openGUI(Player playerEntity) {
      this.setCustomer(playerEntity);
      MMCommon.PROXY.setReferencedMob(this);
      if (!this.level().isClientSide && this.getTarget() == null && this.isAlive()) {
         playerEntity.openMenu(new MenuProvider() {
            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
               return new ContainerUmvuthiTrade(id, EntityUmvuthi.this, playerInventory);
            }

            public Component getDisplayName() {
               return EntityUmvuthi.this.getDisplayName();
            }
         });
      }
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.canTradeWith(player) && this.getTarget() == null && this.isAlive()) {
         this.openGUI(player);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean canTradeWith(Player player) {
      if (!this.isTrading() && !(this.getHealth() <= 0.0F)) {
         ItemStack headStack = (ItemStack)player.getInventory().armor.get(3);
         return headStack.getItem() instanceof UmvuthanaMask;
      } else {
         return false;
      }
   }

   private static boolean canPayFor(ItemStack stack, ItemStack worth) {
      return stack.getItem() == worth.getItem() && stack.getCount() >= worth.getCount();
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.UMVUTHI.hasBossBar.get();
   }

   @Override
   public BossBarColor bossBarColor() {
      return BossBarColor.YELLOW;
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.UMVUTHI;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig;
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData) {
      if (reason == MobSpawnType.SPAWN_EGG) {
         List<Player> players = this.getPlayersNearby(5.0, 5.0, 5.0, 5.0);
         if (!players.isEmpty()) {
            Player closestPlayer = players.get(0);
            float closestPlayerDist = 6.0F;

            for (Player player : players) {
               if (player.getMainHandItem().getItem() == ItemHandler.UMVUTHI_SPAWN_EGG.get()
                  || player.getMainHandItem().getItem() == ItemHandler.UMVUTHI_SPAWN_EGG.get()) {
                  float thisDist = this.distanceTo(player);
                  if (thisDist < closestPlayerDist) {
                     closestPlayer = player;
                     closestPlayerDist = thisDist;
                  }
               }
            }

            float angle = (float)this.getAngleBetweenEntities(this, closestPlayer) + 225.0F;
            int direction = (int)(angle / 90.0F) % 4 + 1;
            this.setDirection(direction);
         }
      }

      if (reason != MobSpawnType.STRUCTURE) {
         this.restrictTo(this.blockPosition(), -1);
      }

      return super.finalizeSpawn(world, difficulty, reason, livingData);
   }

   public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public void push(double x, double y, double z) {
      super.push(0.0, y, 0.0);
   }

   @Override
   public boolean hasBossMusic() {
      return true;
   }

   @Override
   public BossMusic<?> getBossMusic() {
      return BossMusicPlayer.UMVUTHI_MUSIC;
   }

   @Override
   public boolean resetHealthOnPlayerRespawn() {
      return (Boolean)ConfigHandler.COMMON.MOBS.UMVUTHI.resetHealthWhenRespawn.get();
   }

   public static class SolarBeamAbility extends Ability<EntityUmvuthi> {
      protected LivingEntity entityTarget;
      private EntitySolarBeam solarBeam;
      private static final RawAnimation SOLAR_BEAM_ANIM = RawAnimation.begin().then("solar_beam", LoopType.PLAY_ONCE);

      public SolarBeamAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 22),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 68),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 10)
            }
         );
      }

      @Override
      public void start() {
         super.start();
         this.entityTarget = this.getUser().getTarget();
         this.playAnimation(SOLAR_BEAM_ANIM);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         float radius1 = 0.8F;
         EntityUmvuthi entity = this.getUser();
         if (this.getTicksInUse() == 4 && !entity.level().isClientSide) {
            this.solarBeam = new EntitySolarBeam(
               (EntityType<? extends EntitySolarBeam>)EntityHandler.SOLAR_BEAM.get(),
               this.getUser().level(),
               entity,
               entity.getX() + radius1 * Math.sin(-entity.getYRot() * Math.PI / 180.0),
               entity.getY() + 1.4,
               entity.getZ() + radius1 * Math.cos(-entity.getYRot() * Math.PI / 180.0),
               (float)((entity.yHeadRot + 90.0F) * Math.PI / 180.0),
               (float)(-entity.getXRot() * Math.PI / 180.0),
               55
            );
            entity.level().addFreshEntity(this.solarBeam);
         }

         if (this.getTicksInUse() >= 22 && this.entityTarget != null) {
            entity.getLookControl()
               .setLookAt(this.entityTarget.getX(), this.entityTarget.getY() + this.entityTarget.getBbHeight() / 2.0F, this.entityTarget.getZ(), 2.0F, 90.0F);
         }
      }
   }

   public static class SolarFlareAbility extends Ability<EntityUmvuthi> {
      public static AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 12),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 18)
      };
      private static final RawAnimation FLARE_ANIM = RawAnimation.begin().then("flare", LoopType.PLAY_ONCE);

      public SolarFlareAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(abilityType, user, SECTION_TRACK);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_BURST.get(), 1.7F, 1.5F);
         this.playAnimation(FLARE_ANIM);
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            EntityUmvuthi entity = this.getUser();
            float radius = 4.0F;

            for (LivingEntity aHit : entity.getEntityLivingBaseNearby(radius, 2.0F * radius, radius, radius)) {
               if (!(aHit instanceof LeaderSunstrikeImmune)) {
                  entity.doHurtTarget(aHit, 1.0F, 3.0F);
                  if (!aHit.isInvulnerable() && (!(aHit instanceof Player) || !((Player)aHit).getAbilities().invulnerable)) {
                     double knockback = 3.0;
                     double angle = entity.getAngleBetweenEntities(entity, aHit);
                     double x = knockback * Math.cos(Math.toRadians(angle - 90.0));
                     double z = knockback * Math.sin(Math.toRadians(angle - 90.0));
                     aHit.setDeltaMovement(x, 0.3, z);
                     if (aHit instanceof ServerPlayer) {
                        ((ServerPlayer)aHit).connection.send(new ClientboundSetEntityMotionPacket(aHit));
                     }
                  }
               }
            }
         }
      }
   }

   public static class SpawnFollowersAbility extends Ability<EntityUmvuthi> {
      private boolean spawnSunblockers;
      private static final RawAnimation SPAWN_STRIX_ANIM = RawAnimation.begin().then("spawn_strix", LoopType.PLAY_ONCE);

      public SpawnFollowersAbility(AbilityType abilityType, EntityUmvuthi user, boolean spawnSunblockers) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11)
            }
         );
         this.spawnSunblockers = spawnSunblockers;
      }

      @Override
      public void start() {
         super.start();
         this.getUser().umvuthanaSpawnCount++;
         this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_INHALE.get(), 1.2F, 0.5F);
         this.playAnimation(SPAWN_STRIX_ANIM);
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         EntityUmvuthi entity = this.getUser();
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_INHALE.get(), 1.2F, 0.5F);
            this.playAnimation(SPAWN_STRIX_ANIM);
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getUser().level().isClientSide()) {
            entity.playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_BELLY.get(), 1.5F, 1.0F);
            entity.playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_BLOWDART.get(), 1.5F, 0.5F);
            double angle = entity.yHeadRot;
            if (angle < 0.0) {
               angle += 360.0;
            }

            if (angle - entity.getYRot() > 70.0) {
               angle = 70.0F + entity.getYRot();
            } else if (angle - entity.getYRot() < -70.0) {
               angle = -70.0F + entity.getYRot();
            }

            EntityUmvuthanaMinion umvuthana;
            if (this.spawnSunblockers) {
               umvuthana = new EntityUmvuthanaCrane((EntityType<? extends EntityUmvuthanaMinion>)EntityHandler.UMVUTHANA_CRANE.get(), entity.level());
               ((EntityUmvuthanaCrane)umvuthana).hasTriedOrSucceededTeleport = false;
            } else {
               umvuthana = new EntityUmvuthanaMinion((EntityType<? extends EntityUmvuthanaMinion>)EntityHandler.UMVUTHANA_MINION.get(), entity.level());
            }

            umvuthana.absMoveTo(
               entity.getX() + 2.0 * Math.sin(-angle * (Math.PI / 180.0)),
               entity.getY() + 2.5,
               entity.getZ() + 2.0 * Math.cos(-angle * (Math.PI / 180.0)),
               entity.yHeadRot,
               0.0F
            );
            umvuthana.setActive(false);
            umvuthana.active = false;
            umvuthana.finalizeSpawn(
               (ServerLevelAccessor)entity.getCommandSenderWorld(),
               entity.level().getCurrentDifficultyAt(umvuthana.blockPosition()),
               MobSpawnType.MOB_SUMMONED,
               null
            );
            umvuthana.restrictTo(entity.getRestrictCenter(), 25);
            if (entity.getTeam() instanceof PlayerTeam) {
               umvuthana.level().getScoreboard().addPlayerToTeam(umvuthana.getScoreboardName(), entity.getTeam());
            }

            entity.level().addFreshEntity(umvuthana);
            umvuthana.setDeltaMovement(0.7 * Math.sin(-angle * (Math.PI / 180.0)), 0.5, 0.7 * Math.cos(-angle * (Math.PI / 180.0)));
            if (!this.spawnSunblockers) {
               umvuthana.setTarget(entity.getTarget());
               if (entity.getTarget() instanceof Player) {
                  umvuthana.setMisbehavedPlayerId(entity.getTarget().getUUID());
               }
            }
         }
      }

      @Override
      protected void endSection(AbilitySection section) {
         super.endSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY
            && this.getUser().targetDistance <= 6.0F
            && this.getUser().getTarget() != null
            && !this.spawnSunblockers) {
            this.interrupt();
         }
      }
   }

   public static class SunstrikeAbility extends Ability<EntityUmvuthi> {
      private static int STARTUP_DURATION = 9;
      protected LivingEntity entityTarget;
      public double prevX;
      public double prevZ;
      private int newX;
      private int newZ;
      private int y;
      private static final RawAnimation SUN_STRIKE_ANIM = RawAnimation.begin().then("sun_strike", LoopType.PLAY_ONCE);

      public SunstrikeAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, STARTUP_DURATION),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 12)
            }
         );
      }

      @Override
      public void start() {
         super.start();
         this.entityTarget = this.getUser().getTarget();
         if (this.entityTarget != null) {
            this.prevX = this.entityTarget.getX();
            this.prevZ = this.entityTarget.getZ();
         }

         this.playAnimation(SUN_STRIKE_ANIM);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (!this.getUser().level().isClientSide()) {
            if (this.entityTarget == null) {
               return;
            }

            if (this.getTicksInUse() == STARTUP_DURATION - 2) {
               double x = this.entityTarget.getX();
               this.y = Mth.floor(this.entityTarget.getY() - 1.0);
               double z = this.entityTarget.getZ();
               double vx = (x - this.prevX) / STARTUP_DURATION;
               double vz = (z - this.prevZ) / STARTUP_DURATION;
               int t = 38;
               this.newX = Mth.floor(x + vx * t);
               this.newZ = Mth.floor(z + vz * t);
               double dx = this.newX - this.getUser().getX();
               double dz = this.newZ - this.getUser().getZ();
               double dist2ToUmvuthi = dx * dx + dz * dz;
               if (dist2ToUmvuthi < 3.0) {
                  this.newX = Mth.floor(this.entityTarget.getX());
                  this.newZ = Mth.floor(this.entityTarget.getZ());
               }

               for (int i = 0; i < 5 && !this.getUser().level().canSeeSkyFromBelowWater(new BlockPos(this.newX, this.y, this.newZ)); i++) {
                  this.y++;
               }
            }

            if (this.getTicksInUse() < STARTUP_DURATION - 2) {
               this.getUser().getLookControl().setLookAt(this.entityTarget, 30.0F, 30.0F);
            }

            if (this.getTicksInUse() >= STARTUP_DURATION - 2) {
               this.getUser().getLookControl().setLookAt(this.newX, this.y + this.entityTarget.getEyeHeight(), this.newZ, 50.0F, 50.0F);
            }
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (!this.getUser().level().isClientSide() && section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_ATTACK.get(), 1.4F, 1.0F);
            EntitySunstrike sunstrike = new EntitySunstrike(
               (EntityType<? extends EntitySunstrike>)EntityHandler.SUNSTRIKE.get(), this.getUser().level(), this.getUser(), this.newX, this.y, this.newZ
            );
            sunstrike.onSummon();
            this.getUser().level().addFreshEntity(sunstrike);
         }
      }
   }

   public static class SupernovaAbility extends Ability<EntityUmvuthi> {
      public static AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 44),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 40),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 16)
      };
      private static final RawAnimation SUPERNOVA_ANIM = RawAnimation.begin().then("supernova", LoopType.PLAY_ONCE);
      private static final ParticleComponent.KeyTrack superNovaKeyTrack1 = new ParticleComponent.KeyTrack(
         new float[]{0.0F, 25.0F, 32.0F, 0.0F}, new float[]{0.0F, 0.6F, 0.85F, 1.0F}
      );
      private static final ParticleComponent.KeyTrack superNovaKeyTrack2 = ParticleComponent.KeyTrack.oscillate(0.0F, 7.0F, 24);

      public SupernovaAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(abilityType, user, SECTION_TRACK);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SUPERNOVA_START.get(), 3.0F, 1.0F);
         this.playAnimation(SUPERNOVA_ANIM);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 30) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_SUPERNOVA_BLACKHOLE.get(), 2.0F, 1.2F);
         }

         if (this.getTicksInUse() < 30) {
            for (LivingEntity inRange : this.getUser().getEntityLivingBaseNearby(16.0, 16.0, 16.0, 16.0)) {
               if (!(inRange instanceof LeaderSunstrikeImmune) && (!(inRange instanceof Player) || !((Player)inRange).getAbilities().invulnerable)) {
                  Vec3 diff = inRange.position().subtract(this.getUser().position().add(0.0, 3.0, 0.0));
                  diff = diff.normalize().scale(0.03);
                  inRange.setDeltaMovement(inRange.getDeltaMovement().subtract(diff));
                  if (inRange.getY() < this.getUser().getY() + 3.0) {
                     inRange.setDeltaMovement(inRange.getDeltaMovement().add(0.0, 0.075, 0.0));
                  }
               }
            }
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.getUser().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2, 1, false, false));
         }

         if (this.getTicksInUse() == 40) {
            this.getUser().playSound((SoundEvent)MMSounds.ENTITY_UMVUTHI_ROAR.get(), 3.0F, 1.0F);
         }

         if (this.getLevel().isClientSide) {
            superNovaEffects(this, this.getUser().betweenHandPos, this.getLevel());
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getUser().level().isClientSide) {
            Vec3 offset = new Vec3(1.1F, 0.0, 0.0);
            offset = offset.yRot((float)Math.toRadians(-this.getUser().getYRot() - 90.0F));
            EntitySuperNova superNova = new EntitySuperNova(
               (EntityType<? extends EntitySuperNova>)EntityHandler.SUPER_NOVA.get(),
               this.getUser().level(),
               this.getUser(),
               this.getUser().getX() + offset.x,
               this.getUser().getY() + 0.05,
               this.getUser().getZ() + offset.z
            );
            this.getUser().level().addFreshEntity(superNova);
         }
      }

      public static void superNovaEffects(Ability<?> activeAbility, Vec3[] pinLocation, Level level) {
         Player clientPlayer = MMCommon.PROXY.getLocalPlayer();
         if (clientPlayer != null) {
            double distToCaster = activeAbility.getUser().position().distanceToSqr(clientPlayer.position());
            if (distToCaster < 1000.0) {
               Minecraft.getInstance().gameRenderer.darkenWorldAmount += 0.06F;
               if (Minecraft.getInstance().gameRenderer.darkenWorldAmount > 1.0F) {
                  Minecraft.getInstance().gameRenderer.darkenWorldAmount = 1.0F;
               }
            }

            if (pinLocation != null && pinLocation.length != 0 && pinLocation[0] != null) {
               int ticksInUse = activeAbility.getTicksInUse();
               LivingEntity user = activeAbility.getUser();
               RandomSource random = user.getRandom();
               if (ticksInUse == 1) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.SUN,
                     user.getX(),
                     user.getY(),
                     user.getZ(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     33.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, superNovaKeyTrack1, false),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, superNovaKeyTrack2, true)
                     }
                  );
               }

               if (ticksInUse == 33) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.SUN_NOVA,
                     user.getX(),
                     user.getY(),
                     user.getZ(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     20.0,
                     1.0,
                     1.0,
                     1.0,
                     0.0,
                     1.0,
                     13.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{11.0F, 7.0F, 5.5F, 1.0F, 30.0F}, new float[]{0.0F, 0.15F, 0.8F, 0.89F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 1.0F, 1.0F, 0.0F}, new float[]{0.0F, 0.15F, 0.89F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.PARTICLE_ANGLE, ParticleComponent.KeyTrack.startAndEnd(0.0F, -6.0F), false
                        )
                     }
                  );
               }

               if (ticksInUse == 32) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.FLARE,
                     user.getX(),
                     user.getY(),
                     user.getZ(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     0.7,
                     1.0,
                     3.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, ParticleComponent.constant(-0.15F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 22.0F, 0.0F}, new float[]{0.0F, 0.2F, 1.0F}),
                           false
                        )
                     }
                  );
               }

               if (ticksInUse > 30 && ticksInUse < 41) {
                  for (int i = 0; i < 6; i++) {
                     float phaseOffset = random.nextFloat();
                     double value = random.nextDouble() * 0.3 + 0.05;
                     AdvancedParticleBase.spawnParticle(
                        level,
                        ParticleHandler.PIXEL,
                        pinLocation[0].x,
                        pinLocation[0].y,
                        pinLocation[0].z,
                        0.0,
                        0.0,
                        0.0,
                        true,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        5.0,
                        value,
                        value,
                        value,
                        1.0,
                        1.0,
                        6.0,
                        false,
                        true,
                        new ParticleComponent[]{
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                              new ParticleComponent.KeyTrack(new float[]{0.0F, 3.0F}, new float[]{0.0F, 0.2F}),
                              false
                           ),
                           new ParticleComponent.Orbit(
                              pinLocation,
                              ParticleComponent.KeyTrack.startAndEnd(0.0F + phaseOffset, -0.4F + phaseOffset),
                              ParticleComponent.KeyTrack.startAndEnd(0.5F + random.nextFloat(), 0.0F),
                              ParticleComponent.constant(0.0F),
                              ParticleComponent.constant(0.0F),
                              ParticleComponent.constant(0.0F),
                              true
                           )
                        }
                     );
                  }
               }

               if (ticksInUse > 1 && ticksInUse < 27) {
                  for (int i = 0; i < 6; i++) {
                     Vec3 particlePos = new Vec3(random.nextFloat() * 5.0F, 0.0, 0.0);
                     particlePos = particlePos.yRot((float)(random.nextFloat() * 2.0F * Math.PI));
                     particlePos = particlePos.xRot((float)(random.nextFloat() * 2.0F * Math.PI));
                     particlePos = particlePos.add(pinLocation[0]);
                     double value = random.nextDouble() * 0.5 + 0.1;
                     AdvancedParticleBase.spawnParticle(
                        level,
                        ParticleHandler.PIXEL,
                        particlePos.x,
                        particlePos.y,
                        particlePos.z,
                        0.0,
                        0.0,
                        0.0,
                        true,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        5.0,
                        value,
                        value,
                        value,
                        1.0,
                        1.0,
                        7.0,
                        false,
                        true,
                        new ParticleComponent[]{
                           new ParticleComponent.Attractor(pinLocation, 1.1F, 1.0F, ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL),
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                              new ParticleComponent.KeyTrack(new float[]{0.0F, 3.5F}, new float[]{0.0F, 0.2F}),
                              false
                           )
                        }
                     );
                  }
               }

               float timeFrac = Math.min(ticksInUse / 20.0F, 1.0F);
               if (ticksInUse > 1 && ticksInUse < 25 && ticksInUse % (int)(4.0F * (1.0F - timeFrac) + 1.0F) == 0) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.RING_SPARKS,
                     user.getX(),
                     user.getY(),
                     user.getZ(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     random.nextFloat() * (float) Math.PI * 2.0F,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     6.0F + random.nextFloat() * 3.0F,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           ParticleComponent.KeyTrack.startAndEnd(10.0F + 20.0F * timeFrac * timeFrac + 10.0F * random.nextFloat() * timeFrac, 0.0F),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.0F, 0.7F), false
                        )
                     }
                  );
               }

               if (ticksInUse == 14) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.FLARE,
                     user.getX(),
                     user.getY(),
                     user.getZ(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     18.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, ParticleComponent.constant(-0.1F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 35.0F, 0.0F}, new float[]{0.0F, 0.8F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, new ParticleComponent.Oscillator(-5.0F, 5.0F, 42.0F, 0.0F), true
                        )
                     }
                  );
               }

               if (ticksInUse == 32) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.BURST_IN,
                     user.getX(),
                     user.getY(),
                     user.getZ(),
                     0.0,
                     0.0,
                     0.0,
                     false,
                     0.0,
                     Math.PI / 2,
                     0.0,
                     0.0,
                     5.0,
                     0.0,
                     0.0,
                     0.0,
                     1.0,
                     1.0,
                     10.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(25.0F, 0.0F), false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.0F, 1.0F), false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, new ParticleComponent.Oscillator(-2.0F, 2.0F, 42.0F, 0.0F), true
                        )
                     }
                  );
               }

               if (ticksInUse == 44) {
                  float ringScale = 85.0F;
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.RING_BIG,
                     pinLocation[0].x,
                     pinLocation[0].y,
                     pinLocation[0].z,
                     0.0,
                     0.0,
                     0.0,
                     false,
                     0.0,
                     Math.PI / 2,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     40.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.Expression(t -> (float)Math.pow(t.floatValue(), 0.5) * ringScale),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                        )
                     }
                  );
                  float glowScale = 120.0F;
                  AdvancedParticleBase.spawnParticle(
                     level,
                     ParticleHandler.GLOW,
                     pinLocation[0].x,
                     pinLocation[0].y,
                     pinLocation[0].z,
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     0.95,
                     0.9,
                     0.35,
                     1.0,
                     1.0,
                     40.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.Expression(t -> (float)Math.pow(t.floatValue(), 0.5) * glowScale),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                        )
                     }
                  );
               }
            }
         }
      }
   }
}
