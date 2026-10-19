package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.SmoothGroundPathNavigation;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.goals.YetiChaseGoal;
import net.unusual.block_factorys_bosses.entity.boss.yeti.goals.YetiLookAroundGoal;
import net.unusual.block_factorys_bosses.geckolib.boss.yeti.YetiRenderer;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController.ParticleKeyframeHandler;
import software.bernie.geckolib.animation.AnimationController.SoundKeyframeHandler;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.animation.keyframe.event.data.SoundKeyframeData;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiEntity extends AbstractBossEntity {
   public static final EntityDataAccessor<Integer> DATA_HAS_USED_ULTIMATE = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_IS_ENRAGED = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_GROUNDSMASH_ANIMTIME = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Long> DATA_HIT_TICK = SynchedEntityData.defineId(YetiEntity.class, EntityDataSerializers.LONG);
   public static final int ANIMATION_TRANSITION_TIME = 3;
   public static final double ULTIMATE_HEALTH_THRESHOLD = 0.2;
   public static final int HURT_ANIMATION_TIME = 27;
   private static final Map<String, AbstractBossEntity.State> STATES = new HashMap<>();
   public static final RawAnimation FROZEN_ANIM = RawAnimation.begin().thenLoop("animation.spawn_pose");
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.idle");
   public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.run");
   public static final RawAnimation INTRO_ANIM = RawAnimation.begin().thenPlay("animation.spawn");
   public static final RawAnimation ENRAGED_ANIM = RawAnimation.begin().thenPlay("animation.enraged");
   public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.death");
   public static final RawAnimation SWIPE_1_ANIM = RawAnimation.begin().thenPlay("animation.hand_swipe_1");
   public static final RawAnimation SWIPE_2_ANIM = RawAnimation.begin().thenPlay("animation.hand_swipe_2");
   public static final RawAnimation PUNCH_ANIM = RawAnimation.begin().thenPlay("animation.hand_punch_1");
   public static final RawAnimation GROUND_SMASH_ANIM = RawAnimation.begin().thenPlay("animation.ground_smash");
   public static final RawAnimation SPIKE_ANIM = RawAnimation.begin().thenPlay("animation.throw_icespike");
   public static final RawAnimation RANGE_CONTROL_ANIM = RawAnimation.begin().thenPlay("animation.range_control");
   public static final RawAnimation ICE_BARRAGE_ANIM = RawAnimation.begin().thenPlay("animation.ice_barrage");
   public static final RawAnimation LEAP_START = RawAnimation.begin().thenPlay("animation.leap_start");
   public static final RawAnimation LEAP_AIR = RawAnimation.begin().thenPlay("animation.leap_in_air");
   public static final RawAnimation LEAP_SMASH = RawAnimation.begin().thenPlay("animation.leap_smash");
   private static final List<Vec3i> BREAK_1 = List.of(
      new Vec3i(1, 4, -1),
      new Vec3i(2, 4, -1),
      new Vec3i(0, 4, 0),
      new Vec3i(1, 4, 0),
      new Vec3i(2, 4, 0),
      new Vec3i(0, 4, 1),
      new Vec3i(1, 4, 1),
      new Vec3i(2, 5, -1),
      new Vec3i(-1, 5, 0),
      new Vec3i(0, 5, 0),
      new Vec3i(1, 5, 0),
      new Vec3i(2, 5, 0),
      new Vec3i(-1, 5, 1),
      new Vec3i(0, 5, 1),
      new Vec3i(1, 5, 1),
      new Vec3i(-1, 5, 2),
      new Vec3i(1, 5, 2),
      new Vec3i(0, 6, 0),
      new Vec3i(-1, 6, 0),
      new Vec3i(-1, 6, -1)
   );
   private static final List<Vec3i> BREAK_2 = List.of(
      new Vec3i(-1, 3, 0),
      new Vec3i(-1, 3, 1),
      new Vec3i(-1, 3, 2),
      new Vec3i(-2, 3, 0),
      new Vec3i(-2, 3, 1),
      new Vec3i(-1, 4, 0),
      new Vec3i(-1, 4, 1),
      new Vec3i(-1, 4, 2)
   );
   private static final List<Vec3i> BREAK_3 = List.of(new Vec3i(0, 1, -3), new Vec3i(-1, 1, -2), new Vec3i(0, 2, -3), new Vec3i(0, 3, -2), new Vec3i(1, 3, -3));
   private static final List<Vec3i> BREAK_4 = List.of(
      new Vec3i(3, 0, -1),
      new Vec3i(3, 0, 0),
      new Vec3i(3, 0, 1),
      new Vec3i(3, 1, -1),
      new Vec3i(3, 1, 0),
      new Vec3i(3, 1, 1),
      new Vec3i(3, 2, -1),
      new Vec3i(3, 2, 0),
      new Vec3i(3, 2, 1),
      new Vec3i(3, 3, -1),
      new Vec3i(3, 3, 0),
      new Vec3i(3, 3, 1),
      new Vec3i(3, 4, -1),
      new Vec3i(3, 4, 0),
      new Vec3i(3, 4, 1),
      new Vec3i(2, 4, 1),
      new Vec3i(3, 5, 0),
      new Vec3i(3, 5, 1),
      new Vec3i(2, 5, 1),
      new Vec3i(-2, 1, -1),
      new Vec3i(0, 1, -1),
      new Vec3i(1, 1, -3),
      new Vec3i(2, 2, 1),
      new Vec3i(1, 2, 1),
      new Vec3i(0, 2, 1),
      new Vec3i(-1, 2, 1),
      new Vec3i(2, 2, 0),
      new Vec3i(1, 2, 0),
      new Vec3i(0, 2, 0),
      new Vec3i(-1, 2, 0),
      new Vec3i(-2, 2, 0),
      new Vec3i(2, 2, -1),
      new Vec3i(1, 2, -1),
      new Vec3i(0, 2, -1),
      new Vec3i(-1, 2, -1),
      new Vec3i(-2, 2, -1),
      new Vec3i(2, 2, -2),
      new Vec3i(1, 2, -2),
      new Vec3i(0, 2, -2),
      new Vec3i(1, 2, -3),
      new Vec3i(0, 2, -3),
      new Vec3i(2, 3, 1),
      new Vec3i(1, 3, 1),
      new Vec3i(0, 3, 1),
      new Vec3i(2, 3, 0),
      new Vec3i(1, 3, 0),
      new Vec3i(0, 3, 0),
      new Vec3i(2, 3, -1),
      new Vec3i(1, 3, -1),
      new Vec3i(0, 3, -1),
      new Vec3i(2, 3, -2),
      new Vec3i(1, 3, -2)
   );
   public static final int ENRAGE_FLAG_ICED = 0;
   public static final int ENRAGE_FLAG_EMERGING = 1;
   public static final int ENRAGE_FLAG_CALM = 2;
   public static final int ENRAGE_FLAG_READY = 3;
   public static final int ENRAGE_FLAG_START = 4;
   public static final int ENRAGE_FLAG_PISSED = 5;
   public static final int ENRAGE_FLAG_POPSICLE = 6;
   private YetiEntity.YetiAttackPattern attackPattern;
   private int attackPatternIndex = 0;
   public AbstractBossEntity.Attack attackBuffer = null;
   private int targetTooFarTicks = 0;
   private static final YetiEntity.YetiAttackPatternPool POOL;
   private static final YetiEntity.YetiAttackPatternPool ENRAGED_POOL;

   public YetiEntity(EntityType<YetiEntity> type, Level world) {
      super(type, world);
      this.xpReward = 500;
   }

   @Override
   protected ServerBossEvent createBossEvent() {
      return (ServerBossEvent)Util.make(
         new ServerBossEvent(this.getDisplayName(), BossBarColor.BLUE, BossBarOverlay.NOTCHED_6), bossEvent -> bossEvent.setVisible(false)
      );
   }

   @Override
   protected int getBossType() {
      return 3;
   }

   protected PathNavigation createNavigation(Level level) {
      return new SmoothGroundPathNavigation(this, level);
   }

   public AABB getBoundingBoxForCulling() {
      return super.getBoundingBoxForCulling().inflate(3.0);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<YetiEntity> controller = new CinematicAnimationController(this, "main_controller", 3, statex -> {
         if (this.isDead()) {
            return PlayState.STOP;
         } else if ((Integer)this.getEntityData().get(DATA_IS_ENRAGED) == 0) {
            return statex.setAndContinue(FROZEN_ANIM);
         } else {
            return this.isTimerDone() && statex.isMoving() ? statex.setAndContinue(WALK_ANIM) : statex.setAndContinue(IDLE_ANIM);
         }
      });

      for (YetiEntity.YetiState state : YetiEntity.YetiState.values()) {
         state.getAnimation().ifPresent(rawAnimation -> controller.triggerableAnim(state.toString(), rawAnimation));
      }

      controller.triggerableAnim("leap_air", LEAP_AIR);
      controller.triggerableAnim("leap_smash", LEAP_SMASH);
      ParticleKeyframeHandler<YetiEntity> particleHandler = event -> {
         ParticleKeyframeData data = event.getKeyframeData();
         if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((YetiEntity)event.getAnimatable()) instanceof YetiRenderer renderer) {
            renderer.getGeoModel()
               .getBone(data.getLocator())
               .ifPresent(
                  geoBone -> {
                     Vec3 position = ((YetiEntity)event.getAnimatable())
                        .position()
                        .add(geoBone.getLocalPosition().x, geoBone.getLocalPosition().y, geoBone.getLocalPosition().z);
                     if (Minecraft.getInstance().level != null) {
                        switch (data.getEffect()) {
                           case "leap_smash_1":
                              Minecraft.getInstance()
                                 .level
                                 .addParticle(
                                    (ParticleOptions)BossesRiseParticleTypes.YETI_SMOKE_IMPACT_1.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0
                                 );
                              Minecraft.getInstance()
                                 .level
                                 .addParticle(
                                    (ParticleOptions)BossesRiseParticleTypes.YETI_SMOKE_IMPACT_2.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0
                                 );
                              break;
                           case "hand_punch_1":
                           case "run":
                           case "hand_swipe_1_1":
                           case "hand_swipe_1_2":
                           case "throw_snowball_1":
                           case "throw_snowball_2":
                           case "throw_snowball_3":
                           case "leap_in_air":
                              if (this.isEnraged()) {
                                 Minecraft.getInstance()
                                    .level
                                    .addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                                 Minecraft.getInstance()
                                    .level
                                    .addParticle(
                                       (ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(),
                                       position.x + 0.25,
                                       position.y + 0.25,
                                       position.z + 0.25,
                                       0.0,
                                       0.0,
                                       0.0
                                    );
                              } else {
                                 Minecraft.getInstance()
                                    .level
                                    .addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                              }
                              break;
                           case "ice_chunks":
                              Minecraft.getInstance()
                                 .level
                                 .addParticle((ParticleOptions)BossesRiseParticleTypes.YETI_ICE_CHUNK.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                              break;
                           case "get_hit":
                              Minecraft.getInstance()
                                 .level
                                 .addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                              break;
                           case "leap_start_1":
                              Minecraft.getInstance()
                                 .level
                                 .addParticle(
                                    (ParticleOptions)BossesRiseParticleTypes.YETI_SMOKE_IMPACT_2.get(), position.x, position.y, position.z, 0.0, 0.0, 0.0
                                 );
                              break;
                           default:
                              BossesRise.LOGGER.debug("Unknown Particle in Yeti: {}", data.getEffect());
                        }
                     }
                  }
               );
         }
      };
      controller.setParticleKeyframeHandler(particleHandler);
      SoundKeyframeHandler<YetiEntity> soundHandler = event -> {
         SoundKeyframeData data = event.getKeyframeData();
         if (((YetiEntity)event.getAnimatable()).level() instanceof ClientLevel var2x) {
            ;
         }
      };
      controller.setSoundKeyframeHandler(soundHandler);
      controllers.add(controller);
   }

   @Override
   public Map<String, AbstractBossEntity.State> getStates() {
      return STATES;
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_HAS_USED_ULTIMATE, 0);
      builder.define(DATA_IS_ENRAGED, 0);
      builder.define(DATA_GROUNDSMASH_ANIMTIME, 0);
      builder.define(DATA_HIT_TICK, -1L);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new YetiChaseGoal(this, 1.2));
      this.goalSelector.addGoal(2, new YetiLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
   }

   public boolean isEnraged() {
      return (Integer)this.entityData.get(DATA_IS_ENRAGED) == 5;
   }

   public boolean isDead() {
      return (Integer)this.entityData.get(DATA_IS_ENRAGED) == 6;
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      YetiEntity.YetiState state = YetiEntity.YetiState.byState(this.getState());
      if (state == null || !state.stopsMovement || this.isTimerDone()) {
         this.setYBodyRot(this.yBodyRotO);
         this.setYRot(this.yRotO);
         this.setYHeadRot(this.yHeadRotO);
      }
   }

   public float getSpeed() {
      YetiEntity.YetiState state = YetiEntity.YetiState.byState(this.getState());
      return state != null && state.stopsMovement && !this.isTimerDone() ? 0.0F : super.getSpeed();
   }

   public void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound((SoundEvent)BossesRiseSounds.YETI_FOOTSTEP.value(), 0.15F, 1.0F);
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return (SoundEvent)BossesRiseSounds.YETI_HIT.value();
   }

   public boolean canCollideWith(Entity entity) {
      return entity instanceof IceSpikeEntity spike && spike.getOwner() == this ? false : super.canCollideWith(entity);
   }

   @Override
   public void tick() {
      super.tick();
      this.setInvisible(false);
      if (this.level() instanceof ServerLevel serverLevel) {
         int animTime = (Integer)this.entityData.get(DATA_HIT_ANIMTIME);
         if (animTime > 0) {
            this.entityData.set(DATA_HIT_ANIMTIME, animTime - 1);
            if (animTime - 1 <= 0) {
               this.entityData.set(DATA_HIT_TICK, -1L);
            }
         }

         if (this.getTicksFrozen() > 0) {
            this.setTicksFrozen(0);
         }

         int timer = this.getTimer();
         int frame = timer - 2;
         YetiEntity.YetiState state = YetiEntity.YetiState.byState(this.getState());
         Path path = this.navigation.getPath();
         if (path != null && !path.canReach() && this.getTarget() != null && state != YetiEntity.YetiState.LEAP_SMASH) {
            this.doAttack(YetiEntity.YetiState.LEAP_SMASH.attackOrDefault());
            this.navigation.stop();
         } else {
            switch (state) {
               case INTRO:
                  if (timer == 1) {
                     this.playSound((SoundEvent)BossesRiseSounds.YETI_SPAWN.value());
                     this.level()
                        .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)BossesRiseSounds.YETI_INTRO.value(), SoundSource.MUSIC, 8.0F, 1.0F);
                     this.forEachNearbyPlayer(32.0, player -> {
                        PlayerVariables playerVars = (PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES);
                        playerVars.boss_no_hit = true;
                        playerVars.syncPlayerVariables(player);
                     });
                     this.setBarVisible(true);
                  } else if (frame == 31) {
                     BREAK_1.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                           case EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                           case SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                           case WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                           default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                     });
                  } else if (frame == 55) {
                     BREAK_2.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                           case EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                           case SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                           case WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                           default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                     });
                  } else if (frame == 75) {
                     BREAK_3.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                           case EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                           case SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                           case WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                           default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                     });
                  } else if (frame == 90) {
                     BREAK_4.forEach(vec3i -> {
                        Vec3i offset = switch (this.getDirection()) {
                           case EAST -> new Vec3i(-vec3i.getZ(), vec3i.getY(), vec3i.getX());
                           case SOUTH -> new Vec3i(-vec3i.getX(), vec3i.getY(), -vec3i.getZ());
                           case WEST -> new Vec3i(vec3i.getZ(), vec3i.getY(), -vec3i.getX());
                           default -> vec3i;
                        };
                        serverLevel.setBlock(this.blockPosition().offset(offset), Blocks.AIR.defaultBlockState(), 3);
                     });
                  } else if (frame == 124) {
                     for (int x = -4; x <= 4; x++) {
                        for (int y = 0; y <= 6; y++) {
                           for (int z = -4; z <= 4; z++) {
                              BlockPos pos = this.blockPosition().offset(x, y, z);
                              if (serverLevel.getBlockState(pos).is(BlockTags.ICE)) {
                                 serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                              }
                           }
                        }
                     }
                  } else if (frame == 144) {
                     this.push(this.getViewVector(1.0F).x * 0.75, 0.63, this.getViewVector(1.0F).z * 0.75);
                  } else if (frame == 190) {
                     this.getEntityData().set(DATA_IS_ENRAGED, 2);
                  }
                  break;
               case SWIPE_1:
                  if (frame == 21) {
                     this.forEachNearbyEntity(
                        8.0,
                        this.getLookOffset(2.5, 2.0),
                        entity -> {
                           entity.setTicksFrozen(160);
                           entity.invulnerableTime = 0;
                           this.attackEntity(entity, 0.8F);
                           double distance = this.distanceTo(entity);
                           entity.setDeltaMovement(
                              new Vec3(
                                 entity.getDeltaMovement().x()
                                    + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * 2.5) * 1.5),
                                 entity.getDeltaMovement().y(),
                                 entity.getDeltaMovement().z()
                                    + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * -2.5) * 1.5)
                              )
                           );
                        }
                     );
                  }

                  if (frame <= 31 && frame >= 24) {
                     this.forEachNearbyEntity(
                        6.0,
                        this.getLookOffset(-2.5, 2.0),
                        entity -> {
                           double distance = this.distanceTo(entity);
                           entity.setDeltaMovement(
                              new Vec3(
                                 entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : (entity.getX() - this.getX()) / distance * 0.5),
                                 entity.getDeltaMovement().y(),
                                 entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : (entity.getZ() - this.getZ()) / distance * 0.5)
                              )
                           );
                        }
                     );
                  }
                  break;
               case SWIPE_2:
                  if (frame == 28) {
                     this.forEachNearbyEntity(
                        8.0,
                        this.getLookOffset(2.5, 2.0),
                        entity -> {
                           entity.setTicksFrozen(160);
                           entity.invulnerableTime = 0;
                           this.attackEntity(entity, 0.8F);
                           double distance = this.distanceTo(entity);
                           entity.setDeltaMovement(
                              new Vec3(
                                 entity.getDeltaMovement().x()
                                    + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 1.5),
                                 entity.getDeltaMovement().y(),
                                 entity.getDeltaMovement().z()
                                    + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 1.5)
                              )
                           );
                        }
                     );
                  }

                  if (frame == 25) {
                     this.forEachNearbyEntity(
                        6.0,
                        this.getLookOffset(-2.5, 2.0),
                        entity -> {
                           double distance = this.distanceTo(entity);
                           entity.setDeltaMovement(
                              new Vec3(
                                 entity.getDeltaMovement().x() + (distance == 0.0 ? 0.0 : (entity.getX() - this.getX()) / distance * 1.5),
                                 entity.getDeltaMovement().y(),
                                 entity.getDeltaMovement().z() + (distance == 0.0 ? 0.0 : (entity.getZ() - this.getZ()) / distance * 1.5)
                              )
                           );
                        }
                     );
                  }
                  break;
               case PUNCH:
                  if (frame == 18) {
                     this.forEachNearbyEntity(
                        8.0,
                        this.getLookOffset(2.5),
                        entity -> {
                           entity.setTicksFrozen(320);
                           entity.invulnerableTime = 0;
                           this.attackEntity(entity, 0.8F);
                           double distance = this.distanceTo(entity);
                           entity.setDeltaMovement(
                              new Vec3(
                                 entity.getDeltaMovement().x()
                                    + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 2.0),
                                 entity.getDeltaMovement().y(),
                                 entity.getDeltaMovement().z()
                                    + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 2.0)
                              )
                           );
                        }
                     );
                  }

                  if (frame == 20) {
                     for (int index8 = 0; index8 < 26; index8++) {
                        this.level()
                           .addParticle(
                              (ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(),
                              this.getX() + this.getLookAngle().x * 4.0,
                              this.getY() + 0.1,
                              this.getZ() + this.getLookAngle().z * 4.0,
                              (Math.random() - 0.5) * 2.0,
                              Math.random() * 0.3,
                              (Math.random() - 0.5) * 2.0
                           );
                        this.level()
                           .addParticle(
                              (ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(),
                              this.getX() + this.getLookAngle().x * 4.0,
                              this.getY() + 0.1,
                              this.getZ() + this.getLookAngle().z * 4.0,
                              (Math.random() - 0.5) * 2.5,
                              Math.random() * 0.4,
                              (Math.random() - 0.5) * 2.5
                           );
                        if (Math.random() < 0.3) {
                           this.level()
                              .addParticle(
                                 (ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(),
                                 this.getX() + this.getLookAngle().x * 4.0,
                                 this.getY() + 0.1,
                                 this.getZ() + this.getLookAngle().z * 4.0,
                                 (Math.random() - 0.5) * 1.5,
                                 Math.random() * 0.3,
                                 (Math.random() - 0.5) * 1.5
                              );
                        }
                     }
                  }

                  if (frame < 20) {
                     double nb = 0.0;
                     double nb2 = 2.0 + (18 - frame) * 0.1;

                     for (int index9 = 0; index9 < Math.round(18.0 * nb2); index9++) {
                        double new_x = this.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.0;
                        double new_z = this.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.0;
                        ItemStack stack = new ItemStack(
                           this.level()
                              .getBlockState(BlockPos.containing(Math.floor(this.getX() + new_x), this.getY() - 1.0, Math.floor(this.getZ() + new_z)))
                              .getBlock()
                        );
                        if (stack.getItem() != ItemStack.EMPTY.getItem() && this.level() instanceof ServerLevel _level) {
                           _level.sendParticles(
                              new ItemParticleOption(ParticleTypes.ITEM, stack),
                              this.getX() + new_x,
                              this.getY() + 0.1,
                              this.getZ() + new_z,
                              3,
                              0.0,
                              0.0,
                              0.0,
                              0.05
                           );
                        }

                        nb += nb2;
                     }
                  }
                  break;
               case GROUND_SMASH:
                  if (frame == 19) {
                     for (int index10 = 0; index10 < 26; index10++) {
                        this.level()
                           .addParticle(
                              (ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(),
                              this.getX() + this.getLookAngle().x * 4.0,
                              this.getY() + 0.1,
                              this.getZ() + this.getLookAngle().z * 4.0,
                              (Math.random() - 0.5) * 2.0,
                              Math.random() * 0.3,
                              (Math.random() - 0.5) * 2.0
                           );
                        this.level()
                           .addParticle(
                              (ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(),
                              this.getX() + this.getLookAngle().x * 4.0,
                              this.getY() + 0.1,
                              this.getZ() + this.getLookAngle().z * 4.0,
                              (Math.random() - 0.5) * 2.5,
                              Math.random() * 0.4,
                              (Math.random() - 0.5) * 2.5
                           );
                        if (Math.random() < 0.3) {
                           this.level()
                              .addParticle(
                                 (ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(),
                                 this.getX() + this.getLookAngle().x * 4.0,
                                 this.getY() + 0.1,
                                 this.getZ() + this.getLookAngle().z * 4.0,
                                 (Math.random() - 0.5) * 1.5,
                                 Math.random() * 0.3,
                                 (Math.random() - 0.5) * 1.5
                              );
                        }
                     }

                     this.forEachNearbyEntity(
                        8.0,
                        this.getLookOffset(3.0),
                        entity -> {
                           entity.setTicksFrozen(320);
                           entity.invulnerableTime = 0;
                           this.attackEntity(entity, 1.2F);
                           double distance = this.distanceTo(entity);
                           entity.setDeltaMovement(
                              new Vec3(
                                 entity.getDeltaMovement().x()
                                    + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 3.5),
                                 entity.getDeltaMovement().y() + 0.8,
                                 entity.getDeltaMovement().z()
                                    + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 3.5)
                              )
                           );
                        }
                     );
                     GauntletAttachment.iceWave(
                        this,
                        serverLevel,
                        20.0F,
                        60,
                        this.position(),
                        this.getViewVector(1.0F),
                        (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.7F,
                        1.5F
                     );
                  }

                  if (frame < 20) {
                     double nb = 0.0;
                     double nb2 = 3.0 + (18 - frame) * 0.1;

                     for (int index11 = 0; index11 < Math.round(18.0 * nb2); index11++) {
                        double new_x = this.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.0;
                        double new_z = this.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.0;
                        ItemStack stack = new ItemStack(
                           this.level()
                              .getBlockState(BlockPos.containing(Math.floor(this.getX() + new_x), this.getY() - 1.0, Math.floor(this.getZ() + new_z)))
                              .getBlock()
                        );
                        if (stack.getItem() != ItemStack.EMPTY.getItem() && this.level() instanceof ServerLevel _level) {
                           _level.sendParticles(
                              new ItemParticleOption(ParticleTypes.ITEM, stack),
                              this.getX() + new_x,
                              this.getY() + 0.1,
                              this.getZ() + new_z,
                              3,
                              0.0,
                              0.0,
                              0.0,
                              0.05
                           );
                        }

                        nb += nb2;
                     }
                  }
                  break;
               case THROW_SPIKE:
                  LivingEntity targetxx = this.getTarget();
                  if (frame < 25) {
                     this.lookAtTarget();
                  } else if (frame == 25 && targetxx != null) {
                     if (this.isEnraged()) {
                        float xRot = this.getXRot() * (float) (Math.PI / 180.0);

                        for (int i = -1; i <= 1; i++) {
                           float yRot = -Mth.wrapDegrees(this.getYRot() + i * 20) * (float) (Math.PI / 180.0);
                           float cosY = Mth.cos(yRot);
                           float sinY = Mth.sin(yRot);
                           float cosX = Mth.cos(xRot);
                           float sinX = Mth.sin(xRot);
                           Vec3 viewVector = new Vec3(sinY * cosX, -sinX, cosY * cosX);
                           IceSpikeProjectileEntity spike = new IceSpikeProjectileEntity(
                              (EntityType<? extends IceSpikeProjectileEntity>)BossesRiseEntities.ICE_SPIKE_PR.get(), this, serverLevel
                           );
                           spike.setPos(this.getX(), this.getEyeY() - 0.1F, this.getZ());
                           spike.shoot(viewVector.x, viewVector.y, viewVector.z, 4.0F, 0.0F);
                           spike.setSilent(true);
                           spike.setOwner(this);
                           spike.setBaseDamage((int)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.3F));
                           spike.setKnockback(8);
                           serverLevel.addFreshEntity(spike);
                        }

                        this.level()
                           .playSound(
                              null,
                              this.blockPosition(),
                              (SoundEvent)BossesRiseSounds.THROW_ICICLE.value(),
                              SoundSource.PLAYERS,
                              0.9F,
                              (float)Mth.nextDouble(this.random, 1.15, 1.75)
                           );
                     } else {
                        Vec3 viewVector = this.getViewVector(1.0F);
                        IceSpikeProjectileEntity spike = new IceSpikeProjectileEntity(
                           (EntityType<? extends IceSpikeProjectileEntity>)BossesRiseEntities.ICE_SPIKE_PR.get(), this, serverLevel
                        );
                        spike.setPos(this.getX(), this.getEyeY() - 0.1F, this.getZ());
                        spike.shoot(viewVector.x, viewVector.y, viewVector.z, 4.0F, 0.0F);
                        spike.setSilent(true);
                        spike.setOwner(this);
                        spike.setBaseDamage((int)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.2F));
                        spike.setKnockback(8);
                        serverLevel.addFreshEntity(spike);
                        this.level()
                           .playSound(
                              null,
                              this.blockPosition(),
                              (SoundEvent)BossesRiseSounds.THROW_ICICLE.value(),
                              SoundSource.PLAYERS,
                              0.9F,
                              (float)Mth.nextDouble(this.random, 1.15, 1.75)
                           );
                     }
                  }
                  break;
               case RANGED_CONTROL:
                  LivingEntity target = this.getTarget();
                  int hit = 20;
                  if (timer == 5 + hit && target instanceof ServerPlayer player) {
                     GlacialShoveEntity glacialShove = new GlacialShoveEntity((EntityType<?>)BossesRiseEntities.GLACIAL_SHOVE.get(), this.level());
                     glacialShove.moveTo(target.position());
                     glacialShove.setTarget(player);
                     glacialShove.setOwnerUUID(this.getUUID());
                     glacialShove.setDelay(31 - hit);
                     this.level().addFreshEntity(glacialShove);
                  }

                  if (timer <= 31) {
                     this.lookAtTarget();
                  }
                  break;
               case ENRAGED:
                  if (timer <= YetiEntity.YetiState.ENRAGED.state.duration()) {
                     this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, (Integer)this.entityData.get(DATA_GROUNDSMASH_ANIMTIME) + 1);
                     if (timer >= 180) {
                        this.entityData.set(DATA_IS_ENRAGED, 5);
                     }

                     return;
                  }
                  break;
               case ICE_BARRAGE:
                  LivingEntity target = this.getTarget();
                  int delay = 10;
                  if (timer <= YetiEntity.YetiState.ICE_BARRAGE.state.duration()) {
                     if ((Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) > 1 || timer > 20) {
                        serverLevel.getEntities(
                              this, this.getBoundingBox().inflate(3.0, 5.0, 3.0), entity1 -> entity1.canFreeze() && !(entity1 instanceof YetiEntity)
                           )
                           .forEach(entity -> {
                              if (entity instanceof LivingEntity living) {
                                 this.attackEntity(entity, 0.225F, this.damageSources().freeze());
                                 living.setTicksFrozen(200);
                              }
                           });
                     }

                     if (timer == 1) {
                        this.playSound((SoundEvent)BossesRiseSounds.ICE_BARRAGE_WHIRLPOOL.value());
                        serverLevel.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_1.get(),
                           this.getX(),
                           this.getY() + 0.1,
                           this.getZ(),
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                        serverLevel.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_2.get(),
                           this.getX(),
                           this.getY() + 0.1,
                           this.getZ(),
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                        serverLevel.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_3.get(),
                           this.getX(),
                           this.getY() + 0.1,
                           this.getZ(),
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                        serverLevel.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_4.get(),
                           this.getX(),
                           this.getY() + 0.1,
                           this.getZ(),
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                        serverLevel.sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.ICE_WHIRLPOOL_5.get(),
                           this.getX(),
                           this.getY() + 0.1,
                           this.getZ(),
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                     }

                     if (target != null) {
                        if (frame == 43) {
                           GauntletAttachment.delayedBurst(
                              this, target.position(), serverLevel, 1.0F, 10, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.2F, delay
                           );
                        } else if (frame == 74) {
                           GauntletAttachment.delayedBurst(
                              this, target.position(), serverLevel, 1.0F, 20, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.4F, delay
                           );
                        } else if (frame == 109) {
                           GlacialShoveEntity glacialShove = new GlacialShoveEntity((EntityType<?>)BossesRiseEntities.GLACIAL_SHOVE.get(), this.level());
                           glacialShove.moveTo(target.position());
                           glacialShove.setTarget(target);
                           glacialShove.setDelay(delay);
                           glacialShove.setOwnerUUID(this.getUUID());
                           glacialShove.shouldSetAttack = false;
                           this.level().addFreshEntity(glacialShove);
                           if ((Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) < 3) {
                              this.entityData.set(DATA_HAS_USED_ULTIMATE, (Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE) + 1);
                              this.doAttack(YetiEntity.YetiState.ICE_BARRAGE.getAttack());
                           }
                        }
                     }
                  }
                  break;
               case LEAP_SMASH:
                  LivingEntity targetx = this.getTarget();
                  this.entityData.set(AbstractBossEntity.DATA_ATTACK_ANIMTIME, 0);
                  timer = YetiEntity.YetiState.LEAP_SMASH.state.duration() - timer;
                  if (this.isAlive()) {
                     double x = this.getX();
                     double y = this.getY();
                     double z = this.getZ();
                     if (timer == 68) {
                        this.playSound((SoundEvent)BossesRiseSounds.YETI_JUMP.value());
                     }

                     if (timer == 60) {
                        this.push(0.0, this.isInWaterOrBubble() ? 3.8 : 1.8, 0.0);
                        this.triggerAnim("main_controller", "leap_air");
                     }

                     if (timer < 80 && timer > 26 && targetx != null) {
                        this.push((targetx.getX() - this.getX()) * 0.05, 0.0, (targetx.getZ() - this.getZ()) * 0.05);

                        for (int sx = -2; sx <= 2; sx++) {
                           for (int sy = 3; sy <= 6; sy++) {
                              for (int sz = -2; sz <= 2; sz++) {
                                 if (serverLevel.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
                                    && serverLevel.getBlockState(BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                                          .getDestroySpeed(serverLevel, BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                                       > 0.0F
                                    && serverLevel.getBlockState(BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                                          .getDestroySpeed(serverLevel, BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                                       < 50.0F) {
                                    BlockPos _pos = BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz);
                                    Block.dropResources(
                                       serverLevel.getBlockState(_pos),
                                       serverLevel,
                                       BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz),
                                       null
                                    );
                                    serverLevel.destroyBlock(_pos, false);
                                 }
                              }
                           }
                        }
                     }

                     if (timer == 23) {
                        this.playSound((SoundEvent)BossesRiseSounds.YETI_LAND.value());
                        this.triggerAnim("main_controller", "leap_smash");
                        this.push(0.0, -1.0, 0.0);
                     }

                     if (timer == 20) {
                        this.forEachNearbyEntity(
                           4.0,
                           new Vec3(0.0, 2.0, 0.0),
                           entity -> {
                              entity.setTicksFrozen(320);
                              this.attackEntity(entity, 1.0F);
                              double distance = this.distanceTo(entity);
                              entity.setDeltaMovement(
                                 new Vec3(
                                    entity.getDeltaMovement().x()
                                       + (distance == 0.0 ? 0.0 : ((entity.getX() - this.getX()) / distance + this.getLookAngle().z * -2.5) * 1.5),
                                    entity.getDeltaMovement().y(),
                                    entity.getDeltaMovement().z()
                                       + (distance == 0.0 ? 0.0 : ((entity.getZ() - this.getZ()) / distance + this.getLookAngle().x * 2.5) * 1.5)
                                 )
                              );
                           }
                        );
                     }

                     if (timer == 20) {
                        GauntletAttachment.iceBurst(
                           this,
                           this.position(),
                           serverLevel,
                           8.0F,
                           40,
                           (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.7F,
                           (random, integer) -> random.nextInt(20) == 0
                        );

                        for (int index5 = 0; index5 < 26; index5++) {
                           serverLevel.addParticle(
                              (ParticleOptions)BossesRiseParticleTypes.SNOW_CLOUD.get(),
                              x + this.getLookAngle().x * 4.0,
                              y + 0.1,
                              z + this.getLookAngle().z * 4.0,
                              (Math.random() - 0.5) * 2.0,
                              Math.random() * 0.3,
                              (Math.random() - 0.5) * 2.0
                           );
                           serverLevel.addParticle(
                              (ParticleOptions)BossesRiseParticleTypes.FALLING_DUST.get(),
                              x + this.getLookAngle().x * 4.0,
                              y + 0.1,
                              z + this.getLookAngle().z * 4.0,
                              (Math.random() - 0.5) * 2.5,
                              Math.random() * 0.4,
                              (Math.random() - 0.5) * 2.5
                           );
                           if (Math.random() < 0.3) {
                              serverLevel.addParticle(
                                 (ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(),
                                 x + this.getLookAngle().x * 4.0,
                                 y + 0.1,
                                 z + this.getLookAngle().z * 4.0,
                                 (Math.random() - 0.5) * 1.5,
                                 Math.random() * 0.3,
                                 (Math.random() - 0.5) * 1.5
                              );
                           }
                        }
                     }

                     if (timer < 20) {
                        double nb = 0.0;
                        double nb2 = (20 - timer) * 0.1;

                        for (int index6 = 0; index6 < Math.round(18.0 * nb2); index6++) {
                           double new_x = this.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.5;
                           double new_z = this.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.5;
                           ItemStack stack = new ItemStack(
                              serverLevel.getBlockState(BlockPos.containing(Math.floor(x + new_x), y - 1.0, Math.floor(z + new_z))).getBlock()
                           );
                           if (stack.getItem() != ItemStack.EMPTY.getItem()) {
                              serverLevel.sendParticles(
                                 new ItemParticleOption(ParticleTypes.ITEM, stack), x + new_x, this.getY() + 0.1, z + new_z, 3, 0.0, 0.0, 0.0, 0.05
                              );
                           }

                           nb += nb2;
                        }
                     }
                  }
                  break;
               case DEATH:
                  if (timer == YetiEntity.YetiState.DEATH.state.duration()) {
                     serverLevel.sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.YETI_DEATH_SOLID.get(), this.getX(), this.getY() + 0.1, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0
                     );
                     this.getEntityData().set(DATA_IS_ENRAGED, 6);
                     this.setNoAi(true);
                     this.setBarVisible(false);
                  }
               case null:
            }

            this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, 0);
         }
      }
   }

   protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
   }

   public Vec3 getDeltaMovement() {
      return this.entityData.get(DATA_IS_ENRAGED) == 0 ? Vec3.ZERO : super.getDeltaMovement();
   }

   public boolean hurt(DamageSource param1, float param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 004: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_IS_ENRAGED Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 007: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 00a: checkcast java/lang/Integer
      // 00d: invokevirtual java/lang/Integer.intValue ()I
      // 010: istore 3
      // 011: iload 3
      // 012: bipush 6
      // 014: if_icmpne 127
      // 017: aload 0
      // 018: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.level ()Lnet/minecraft/world/level/Level;
      // 01b: astore 5
      // 01d: aload 5
      // 01f: instanceof net/minecraft/server/level/ServerLevel
      // 022: ifeq 127
      // 025: aload 5
      // 027: checkcast net/minecraft/server/level/ServerLevel
      // 02a: astore 4
      // 02c: bipush 0
      // 02d: istore 5
      // 02f: iload 5
      // 031: bipush 8
      // 033: if_icmpge 121
      // 036: aload 0
      // 037: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.level ()Lnet/minecraft/world/level/Level;
      // 03a: aconst_null
      // 03b: aload 0
      // 03c: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.blockPosition ()Lnet/minecraft/core/BlockPos;
      // 03f: getstatic net/unusual/block_factorys_bosses/init/BossesRiseSounds.ICICLE_BREAK Lnet/minecraft/core/Holder;
      // 042: invokeinterface net/minecraft/core/Holder.value ()Ljava/lang/Object; 1
      // 047: checkcast net/minecraft/sounds/SoundEvent
      // 04a: getstatic net/minecraft/sounds/SoundSource.PLAYERS Lnet/minecraft/sounds/SoundSource;
      // 04d: ldc_w 0.9
      // 050: aload 0
      // 051: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 054: ldc2_w 0.15
      // 057: ldc2_w 0.35
      // 05a: invokestatic net/minecraft/util/Mth.nextDouble (Lnet/minecraft/util/RandomSource;DD)D
      // 05d: d2f
      // 05e: invokevirtual net/minecraft/world/level/Level.playSound (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V
      // 061: aload 4
      // 063: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_ICE_CHUNK Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 066: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 069: checkcast net/minecraft/core/particles/SimpleParticleType
      // 06c: aload 0
      // 06d: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 070: aload 0
      // 071: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 074: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 079: dadd
      // 07a: aload 0
      // 07b: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 07e: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 083: dsub
      // 084: aload 0
      // 085: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getEyeY ()D
      // 088: aload 0
      // 089: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 08c: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 091: dadd
      // 092: aload 0
      // 093: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 096: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 09b: dsub
      // 09c: aload 0
      // 09d: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 0a0: aload 0
      // 0a1: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 0a4: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 0a9: dadd
      // 0aa: aload 0
      // 0ab: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 0ae: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 0b3: dsub
      // 0b4: bipush 1
      // 0b5: dconst_0
      // 0b6: dconst_0
      // 0b7: dconst_0
      // 0b8: dconst_0
      // 0b9: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 0bc: pop
      // 0bd: aload 4
      // 0bf: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_ICE_CHUNK Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 0c2: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 0c5: checkcast net/minecraft/core/particles/SimpleParticleType
      // 0c8: aload 0
      // 0c9: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 0cc: aload 0
      // 0cd: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 0d0: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 0d5: dadd
      // 0d6: aload 0
      // 0d7: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 0da: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 0df: dsub
      // 0e0: aload 0
      // 0e1: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getY ()D
      // 0e4: dconst_1
      // 0e5: dadd
      // 0e6: aload 0
      // 0e7: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 0ea: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 0ef: dadd
      // 0f0: aload 0
      // 0f1: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 0f4: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 0f9: dsub
      // 0fa: aload 0
      // 0fb: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 0fe: aload 0
      // 0ff: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 102: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 107: dadd
      // 108: aload 0
      // 109: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.random Lnet/minecraft/util/RandomSource;
      // 10c: invokeinterface net/minecraft/util/RandomSource.nextDouble ()D 1
      // 111: dsub
      // 112: bipush 1
      // 113: dconst_0
      // 114: dconst_0
      // 115: dconst_0
      // 116: dconst_0
      // 117: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 11a: pop
      // 11b: iinc 5 1
      // 11e: goto 02f
      // 121: aload 0
      // 122: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.discard ()V
      // 125: bipush 1
      // 126: ireturn
      // 127: iload 3
      // 128: ifne 137
      // 12b: aload 1
      // 12c: getstatic net/minecraft/tags/DamageTypeTags.BYPASSES_INVULNERABILITY Lnet/minecraft/tags/TagKey;
      // 12f: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/tags/TagKey;)Z
      // 132: ifne 137
      // 135: bipush 0
      // 136: ireturn
      // 137: iload 3
      // 138: bipush 1
      // 139: if_icmpne 148
      // 13c: aload 1
      // 13d: getstatic net/minecraft/tags/DamageTypeTags.BYPASSES_INVULNERABILITY Lnet/minecraft/tags/TagKey;
      // 140: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/tags/TagKey;)Z
      // 143: ifne 148
      // 146: bipush 0
      // 147: ireturn
      // 148: iload 3
      // 149: bipush 3
      // 14a: if_icmpeq 1d4
      // 14d: iload 3
      // 14e: bipush 4
      // 14f: if_icmpeq 1d4
      // 152: aload 1
      // 153: getstatic net/minecraft/world/damagesource/DamageTypes.FALL Lnet/minecraft/resources/ResourceKey;
      // 156: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/resources/ResourceKey;)Z
      // 159: ifne 1d4
      // 15c: aload 1
      // 15d: getstatic net/minecraft/world/damagesource/DamageTypes.DROWN Lnet/minecraft/resources/ResourceKey;
      // 160: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/resources/ResourceKey;)Z
      // 163: ifne 1d4
      // 166: aload 1
      // 167: getstatic net/minecraft/world/damagesource/DamageTypes.FALLING_ANVIL Lnet/minecraft/resources/ResourceKey;
      // 16a: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/resources/ResourceKey;)Z
      // 16d: ifne 1d4
      // 170: aload 1
      // 171: getstatic net/minecraft/world/damagesource/DamageTypes.WITHER Lnet/minecraft/resources/ResourceKey;
      // 174: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/resources/ResourceKey;)Z
      // 177: ifne 1d4
      // 17a: aload 1
      // 17b: getstatic net/minecraft/world/damagesource/DamageTypes.WITHER_SKULL Lnet/minecraft/resources/ResourceKey;
      // 17e: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/resources/ResourceKey;)Z
      // 181: ifne 1d4
      // 184: aload 0
      // 185: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 188: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_SPAWN_ANIMTIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 18b: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 18e: checkcast java/lang/Integer
      // 191: invokevirtual java/lang/Integer.intValue ()I
      // 194: ifgt 1d4
      // 197: aload 0
      // 198: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 19b: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_HIT_ANIMTIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 19e: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 1a1: checkcast java/lang/Integer
      // 1a4: invokevirtual java/lang/Integer.intValue ()I
      // 1a7: bipush 13
      // 1a9: if_icmpgt 1d4
      // 1ac: aload 0
      // 1ad: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 1b0: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_GROUNDSMASH_ANIMTIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 1b3: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 1b6: checkcast java/lang/Integer
      // 1b9: invokevirtual java/lang/Integer.intValue ()I
      // 1bc: ifle 1d6
      // 1bf: aload 0
      // 1c0: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 1c3: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_GROUNDSMASH_ANIMTIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 1c6: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 1c9: checkcast java/lang/Integer
      // 1cc: invokevirtual java/lang/Integer.intValue ()I
      // 1cf: bipush 119
      // 1d1: if_icmpge 1d6
      // 1d4: bipush 0
      // 1d5: ireturn
      // 1d6: aload 1
      // 1d7: invokevirtual net/minecraft/world/damagesource/DamageSource.getDirectEntity ()Lnet/minecraft/world/entity/Entity;
      // 1da: instanceof net/minecraft/world/entity/projectile/AbstractArrow
      // 1dd: ifeq 1e6
      // 1e0: fload 2
      // 1e1: ldc_w 0.15
      // 1e4: fmul
      // 1e5: fstore 2
      // 1e6: aload 1
      // 1e7: getstatic net/minecraft/world/damagesource/DamageTypes.TRIDENT Lnet/minecraft/resources/ResourceKey;
      // 1ea: invokevirtual net/minecraft/world/damagesource/DamageSource.is (Lnet/minecraft/resources/ResourceKey;)Z
      // 1ed: ifeq 1f6
      // 1f0: fload 2
      // 1f1: ldc_w 0.3
      // 1f4: fmul
      // 1f5: fstore 2
      // 1f6: aload 0
      // 1f7: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 1fa: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_HIT_ANIMTIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 1fd: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 200: checkcast java/lang/Integer
      // 203: invokevirtual java/lang/Integer.intValue ()I
      // 206: ifne 236
      // 209: aload 0
      // 20a: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.level ()Lnet/minecraft/world/level/Level;
      // 20d: invokevirtual net/minecraft/world/level/Level.isClientSide ()Z
      // 210: ifne 236
      // 213: aload 0
      // 214: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 217: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_HIT_ANIMTIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 21a: bipush 27
      // 21c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 21f: invokevirtual net/minecraft/network/syncher/SynchedEntityData.set (Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V
      // 222: aload 0
      // 223: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 226: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_HIT_TICK Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 229: aload 0
      // 22a: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.level ()Lnet/minecraft/world/level/Level;
      // 22d: invokevirtual net/minecraft/world/level/Level.getGameTime ()J
      // 230: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 233: invokevirtual net/minecraft/network/syncher/SynchedEntityData.set (Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V
      // 236: aload 0
      // 237: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getHealth ()F
      // 23a: fload 2
      // 23b: fsub
      // 23c: f2d
      // 23d: dconst_0
      // 23e: dcmpg
      // 23f: ifgt 4c8
      // 242: aload 0
      // 243: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getState ()Lnet/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity$State;
      // 246: ifnull 25c
      // 249: aload 0
      // 24a: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getState ()Lnet/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity$State;
      // 24d: invokevirtual net/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity$State.name ()Ljava/lang/String;
      // 250: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState.DEATH Lnet/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState;
      // 253: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState.toString ()Ljava/lang/String;
      // 256: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 259: ifne 4bf
      // 25c: aload 0
      // 25d: getstatic net/unusual/block_factorys_bosses/init/BossesRiseSounds.YETI_DEATH Lnet/minecraft/core/Holder;
      // 260: invokeinterface net/minecraft/core/Holder.value ()Ljava/lang/Object; 1
      // 265: checkcast net/minecraft/sounds/SoundEvent
      // 268: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.playSound (Lnet/minecraft/sounds/SoundEvent;)V
      // 26b: aload 0
      // 26c: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState.DEATH Lnet/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState;
      // 26f: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState.toString ()Ljava/lang/String;
      // 272: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.setState (Ljava/lang/String;)V
      // 275: aconst_null
      // 276: astore 4
      // 278: aload 1
      // 279: invokevirtual net/minecraft/world/damagesource/DamageSource.getDirectEntity ()Lnet/minecraft/world/entity/Entity;
      // 27c: astore 8
      // 27e: aload 8
      // 280: instanceof net/minecraft/world/entity/LivingEntity
      // 283: ifeq 294
      // 286: aload 8
      // 288: checkcast net/minecraft/world/entity/LivingEntity
      // 28b: astore 5
      // 28d: aload 5
      // 28f: astore 4
      // 291: goto 2d8
      // 294: aload 1
      // 295: invokevirtual net/minecraft/world/damagesource/DamageSource.getEntity ()Lnet/minecraft/world/entity/Entity;
      // 298: astore 8
      // 29a: aload 8
      // 29c: instanceof net/minecraft/world/entity/LivingEntity
      // 29f: ifeq 2b0
      // 2a2: aload 8
      // 2a4: checkcast net/minecraft/world/entity/LivingEntity
      // 2a7: astore 6
      // 2a9: aload 6
      // 2ab: astore 4
      // 2ad: goto 2d8
      // 2b0: aload 1
      // 2b1: invokevirtual net/minecraft/world/damagesource/DamageSource.getEntity ()Lnet/minecraft/world/entity/Entity;
      // 2b4: astore 8
      // 2b6: aload 8
      // 2b8: instanceof net/minecraft/world/entity/OwnableEntity
      // 2bb: ifeq 2d8
      // 2be: aload 8
      // 2c0: checkcast net/minecraft/world/entity/OwnableEntity
      // 2c3: astore 7
      // 2c5: aload 7
      // 2c7: invokeinterface net/minecraft/world/entity/OwnableEntity.getOwner ()Lnet/minecraft/world/entity/LivingEntity; 1
      // 2cc: ifnull 2d8
      // 2cf: aload 7
      // 2d1: invokeinterface net/minecraft/world/entity/OwnableEntity.getOwner ()Lnet/minecraft/world/entity/LivingEntity; 1
      // 2d6: astore 4
      // 2d8: aload 0
      // 2d9: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.deathScore I
      // 2dc: iflt 2ef
      // 2df: aload 4
      // 2e1: ifnull 2ef
      // 2e4: aload 4
      // 2e6: aload 0
      // 2e7: aload 0
      // 2e8: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.deathScore I
      // 2eb: aload 1
      // 2ec: invokevirtual net/minecraft/world/entity/LivingEntity.awardKillScore (Lnet/minecraft/world/entity/Entity;ILnet/minecraft/world/damagesource/DamageSource;)V
      // 2ef: aload 0
      // 2f0: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.level ()Lnet/minecraft/world/level/Level;
      // 2f3: astore 6
      // 2f5: aload 6
      // 2f7: instanceof net/minecraft/server/level/ServerLevel
      // 2fa: ifeq 4bf
      // 2fd: aload 6
      // 2ff: checkcast net/minecraft/server/level/ServerLevel
      // 302: astore 5
      // 304: aload 5
      // 306: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_DEATH_FROST Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 309: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 30c: checkcast net/minecraft/core/particles/SimpleParticleType
      // 30f: aload 0
      // 310: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 313: aload 0
      // 314: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getY ()D
      // 317: ldc2_w 0.1
      // 31a: dadd
      // 31b: aload 0
      // 31c: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 31f: bipush 1
      // 320: dconst_0
      // 321: dconst_0
      // 322: dconst_0
      // 323: dconst_0
      // 324: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 327: pop
      // 328: aload 5
      // 32a: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_DEATH_LIGHT Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 32d: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 330: checkcast net/minecraft/core/particles/SimpleParticleType
      // 333: aload 0
      // 334: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 337: aload 0
      // 338: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getY ()D
      // 33b: ldc2_w 0.1
      // 33e: dadd
      // 33f: aload 0
      // 340: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 343: bipush 1
      // 344: dconst_0
      // 345: dconst_0
      // 346: dconst_0
      // 347: dconst_0
      // 348: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 34b: pop
      // 34c: aload 5
      // 34e: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_DEATH_SOFT Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 351: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 354: checkcast net/minecraft/core/particles/SimpleParticleType
      // 357: aload 0
      // 358: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 35b: aload 0
      // 35c: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getY ()D
      // 35f: ldc2_w 0.1
      // 362: dadd
      // 363: aload 0
      // 364: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 367: bipush 1
      // 368: dconst_0
      // 369: dconst_0
      // 36a: dconst_0
      // 36b: dconst_0
      // 36c: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 36f: pop
      // 370: aload 5
      // 372: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_DEATH_SOLID Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 375: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 378: checkcast net/minecraft/core/particles/SimpleParticleType
      // 37b: aload 0
      // 37c: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 37f: aload 0
      // 380: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getY ()D
      // 383: ldc2_w 0.1
      // 386: dadd
      // 387: aload 0
      // 388: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 38b: bipush 1
      // 38c: dconst_0
      // 38d: dconst_0
      // 38e: dconst_0
      // 38f: dconst_0
      // 390: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 393: pop
      // 394: aload 5
      // 396: getstatic net/unusual/block_factorys_bosses/init/BossesRiseParticleTypes.YETI_DEATH_SHOCKWAVE Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 399: invokevirtual net/neoforged/neoforge/registries/DeferredHolder.get ()Ljava/lang/Object;
      // 39c: checkcast net/minecraft/core/particles/SimpleParticleType
      // 39f: aload 0
      // 3a0: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getX ()D
      // 3a3: aload 0
      // 3a4: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getY ()D
      // 3a7: ldc2_w 0.1
      // 3aa: dadd
      // 3ab: aload 0
      // 3ac: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getZ ()D
      // 3af: bipush 1
      // 3b0: dconst_0
      // 3b1: dconst_0
      // 3b2: dconst_0
      // 3b3: dconst_0
      // 3b4: invokevirtual net/minecraft/server/level/ServerLevel.sendParticles (Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I
      // 3b7: pop
      // 3b8: aload 1
      // 3b9: invokevirtual net/minecraft/world/damagesource/DamageSource.getEntity ()Lnet/minecraft/world/entity/Entity;
      // 3bc: astore 7
      // 3be: aload 7
      // 3c0: instanceof net/minecraft/server/level/ServerPlayer
      // 3c3: ifeq 4b8
      // 3c6: aload 7
      // 3c8: checkcast net/minecraft/server/level/ServerPlayer
      // 3cb: astore 6
      // 3cd: aload 6
      // 3cf: invokevirtual net/minecraft/server/level/ServerPlayer.getAdvancements ()Lnet/minecraft/server/PlayerAdvancements;
      // 3d2: astore 7
      // 3d4: aload 5
      // 3d6: invokevirtual net/minecraft/server/level/ServerLevel.getServer ()Lnet/minecraft/server/MinecraftServer;
      // 3d9: invokevirtual net/minecraft/server/MinecraftServer.getAdvancements ()Lnet/minecraft/server/ServerAdvancementManager;
      // 3dc: astore 8
      // 3de: aload 8
      // 3e0: ldc_w "kill_boss_under_minute"
      // 3e3: invokestatic net/unusual/block_factorys_bosses/BossesRise.prefix (Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;
      // 3e6: invokevirtual net/minecraft/server/ServerAdvancementManager.get (Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/advancements/AdvancementHolder;
      // 3e9: astore 9
      // 3eb: aload 8
      // 3ed: aload 0
      // 3ee: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getNoHitAdvancement ()Lnet/minecraft/resources/ResourceLocation;
      // 3f1: invokevirtual net/minecraft/server/ServerAdvancementManager.get (Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/advancements/AdvancementHolder;
      // 3f4: astore 10
      // 3f6: aload 9
      // 3f8: ifnull 451
      // 3fb: aload 0
      // 3fc: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getEntityData ()Lnet/minecraft/network/syncher/SynchedEntityData;
      // 3ff: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_BATTLE_TIME Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 402: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 405: checkcast java/lang/Integer
      // 408: invokevirtual java/lang/Integer.intValue ()I
      // 40b: sipush 1200
      // 40e: if_icmpgt 451
      // 411: aload 7
      // 413: aload 9
      // 415: invokevirtual net/minecraft/server/PlayerAdvancements.getOrStartProgress (Lnet/minecraft/advancements/AdvancementHolder;)Lnet/minecraft/advancements/AdvancementProgress;
      // 418: astore 11
      // 41a: aload 11
      // 41c: invokevirtual net/minecraft/advancements/AdvancementProgress.isDone ()Z
      // 41f: ifne 451
      // 422: aload 11
      // 424: invokevirtual net/minecraft/advancements/AdvancementProgress.getRemainingCriteria ()Ljava/lang/Iterable;
      // 427: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 42c: astore 12
      // 42e: aload 12
      // 430: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 435: ifeq 451
      // 438: aload 12
      // 43a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 43f: checkcast java/lang/String
      // 442: astore 13
      // 444: aload 7
      // 446: aload 9
      // 448: aload 13
      // 44a: invokevirtual net/minecraft/server/PlayerAdvancements.award (Lnet/minecraft/advancements/AdvancementHolder;Ljava/lang/String;)Z
      // 44d: pop
      // 44e: goto 42e
      // 451: aload 6
      // 453: getstatic net/unusual/block_factorys_bosses/init/BossesRiseDataAttachments.PLAYER_VARIABLES Lnet/neoforged/neoforge/registries/DeferredHolder;
      // 456: invokevirtual net/minecraft/server/level/ServerPlayer.getData (Ljava/util/function/Supplier;)Ljava/lang/Object;
      // 459: checkcast net/unusual/block_factorys_bosses/attachment/entity/PlayerVariables
      // 45c: astore 11
      // 45e: aload 10
      // 460: ifnull 4ab
      // 463: aload 11
      // 465: getfield net/unusual/block_factorys_bosses/attachment/entity/PlayerVariables.boss_no_hit Z
      // 468: ifeq 4ab
      // 46b: aload 7
      // 46d: aload 10
      // 46f: invokevirtual net/minecraft/server/PlayerAdvancements.getOrStartProgress (Lnet/minecraft/advancements/AdvancementHolder;)Lnet/minecraft/advancements/AdvancementProgress;
      // 472: astore 12
      // 474: aload 12
      // 476: invokevirtual net/minecraft/advancements/AdvancementProgress.isDone ()Z
      // 479: ifne 4ab
      // 47c: aload 12
      // 47e: invokevirtual net/minecraft/advancements/AdvancementProgress.getRemainingCriteria ()Ljava/lang/Iterable;
      // 481: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 486: astore 13
      // 488: aload 13
      // 48a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 48f: ifeq 4ab
      // 492: aload 13
      // 494: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 499: checkcast java/lang/String
      // 49c: astore 14
      // 49e: aload 7
      // 4a0: aload 10
      // 4a2: aload 14
      // 4a4: invokevirtual net/minecraft/server/PlayerAdvancements.award (Lnet/minecraft/advancements/AdvancementHolder;Ljava/lang/String;)Z
      // 4a7: pop
      // 4a8: goto 488
      // 4ab: aload 11
      // 4ad: bipush 1
      // 4ae: putfield net/unusual/block_factorys_bosses/attachment/entity/PlayerVariables.boss_no_hit Z
      // 4b1: aload 11
      // 4b3: aload 6
      // 4b5: invokevirtual net/unusual/block_factorys_bosses/attachment/entity/PlayerVariables.syncPlayerVariables (Lnet/minecraft/world/entity/Entity;)V
      // 4b8: aload 0
      // 4b9: aload 5
      // 4bb: aload 1
      // 4bc: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.dropAllDeathLoot (Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;)V
      // 4bf: aload 0
      // 4c0: ldc_w 0.1
      // 4c3: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.setHealth (F)V
      // 4c6: bipush 1
      // 4c7: ireturn
      // 4c8: iload 3
      // 4c9: bipush 2
      // 4ca: if_icmpne 50d
      // 4cd: aload 0
      // 4ce: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getHealth ()F
      // 4d1: fload 2
      // 4d2: fsub
      // 4d3: aload 0
      // 4d4: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getMaxHealth ()F
      // 4d7: fdiv
      // 4d8: f2d
      // 4d9: ldc2_w 0.5
      // 4dc: dcmpg
      // 4dd: ifgt 50d
      // 4e0: aload 0
      // 4e1: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.level ()Lnet/minecraft/world/level/Level;
      // 4e4: aload 0
      // 4e5: aload 0
      // 4e6: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getBoundingBox ()Lnet/minecraft/world/phys/AABB;
      // 4e9: ldc2_w 32.0
      // 4ec: invokevirtual net/minecraft/world/phys/AABB.inflate (D)Lnet/minecraft/world/phys/AABB;
      // 4ef: invokevirtual net/minecraft/world/level/Level.getEntities (Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;
      // 4f2: invokedynamic accept ()Ljava/util/function/Consumer; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)V, net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.lambda$hurt$21 (Lnet/minecraft/world/entity/Entity;)V, (Lnet/minecraft/world/entity/Entity;)V ]
      // 4f7: invokeinterface java/util/List.forEach (Ljava/util/function/Consumer;)V 2
      // 4fc: aload 0
      // 4fd: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 500: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_IS_ENRAGED Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 503: bipush 3
      // 504: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 507: invokevirtual net/minecraft/network/syncher/SynchedEntityData.set (Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V
      // 50a: goto 55c
      // 50d: aload 0
      // 50e: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getHealth ()F
      // 511: aload 0
      // 512: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getMaxHealth ()F
      // 515: fdiv
      // 516: f2d
      // 517: ldc2_w 0.2
      // 51a: dcmpl
      // 51b: ifle 55c
      // 51e: aload 0
      // 51f: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getHealth ()F
      // 522: fload 2
      // 523: fsub
      // 524: aload 0
      // 525: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.getMaxHealth ()F
      // 528: fdiv
      // 529: f2d
      // 52a: ldc2_w 0.2
      // 52d: dcmpg
      // 52e: ifgt 55c
      // 531: aload 0
      // 532: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 535: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_HAS_USED_ULTIMATE Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 538: invokevirtual net/minecraft/network/syncher/SynchedEntityData.get (Lnet/minecraft/network/syncher/EntityDataAccessor;)Ljava/lang/Object;
      // 53b: checkcast java/lang/Integer
      // 53e: invokevirtual java/lang/Integer.intValue ()I
      // 541: ifne 55c
      // 544: aload 0
      // 545: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState.ICE_BARRAGE Lnet/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState;
      // 548: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity$YetiState.getAttack ()Lnet/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity$Attack;
      // 54b: invokevirtual net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.doAttack (Lnet/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity$Attack;)V
      // 54e: aload 0
      // 54f: getfield net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.entityData Lnet/minecraft/network/syncher/SynchedEntityData;
      // 552: getstatic net/unusual/block_factorys_bosses/entity/boss/yeti/YetiEntity.DATA_HAS_USED_ULTIMATE Lnet/minecraft/network/syncher/EntityDataAccessor;
      // 555: bipush 1
      // 556: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 559: invokevirtual net/minecraft/network/syncher/SynchedEntityData.set (Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V
      // 55c: aload 0
      // 55d: aload 1
      // 55e: fload 2
      // 55f: invokespecial net/unusual/block_factorys_bosses/entity/boss/AbstractBossEntity.hurt (Lnet/minecraft/world/damagesource/DamageSource;F)Z
      // 562: ireturn
   }

   @Override
   public boolean shouldCancelDeath() {
      return false;
   }

   @Override
   protected ResourceLocation getNoHitAdvancement() {
      return BossesRise.prefix("no_hit_yeti");
   }

   @Override
   protected ResourceLocation getKillAdvancement() {
      return BossesRise.prefix("kill_yeti");
   }

   @Override
   public void addAdditionalSaveData(CompoundTag tag) {
      if (this.getState() == null) {
         this.setState(YetiEntity.YetiState.IDLE.name());
      }

      super.addAdditionalSaveData(tag);
      tag.putInt("has_used_ultimate", (Integer)this.entityData.get(DATA_HAS_USED_ULTIMATE));
      tag.putInt("is_enraged", (Integer)this.entityData.get(DATA_IS_ENRAGED));
      tag.putInt("GroundsmashAnimtime", (Integer)this.entityData.get(DATA_GROUNDSMASH_ANIMTIME));
   }

   @Override
   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("BossPhase")) {
         this.entityData.set(DATA_HAS_USED_ULTIMATE, tag.getInt("has_used_ultimate"));
         this.entityData.set(DATA_IS_ENRAGED, tag.getInt("is_enraged"));
         this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, tag.getInt("GroundsmashAnimtime"));
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData groupData) {
      SpawnGroupData data = super.finalizeSpawn(world, difficulty, reason, groupData);
      this.assignIfPresent(this.getAttribute(Attributes.MAX_HEALTH), (Double)ServerConfiguration.YETI_HEALTH.get());
      this.assignIfPresent(this.getAttribute(Attributes.ARMOR), (Double)ServerConfiguration.YETI_ARMOR.get());
      this.assignIfPresent(this.getAttribute(Attributes.ATTACK_DAMAGE), (Double)ServerConfiguration.YETI_ATK.get());
      this.setHealth((float)((Double)ServerConfiguration.YETI_HEALTH.get()).doubleValue());
      if (reason != MobSpawnType.STRUCTURE) {
         this.getEntityData().set(DATA_IS_ENRAGED, 2);
         this.setBarVisible(true);
      }

      return data;
   }

   private void assignIfPresent(@Nullable AttributeInstance holder, double value) {
      if (holder != null) {
         holder.setBaseValue(value);
      }
   }

   public void baseTick() {
      super.baseTick();
      if (this.getTicksFrozen() > 0) {
         this.setTicksFrozen(0);
      }

      if ((Integer)this.getEntityData().get(DATA_IS_ENRAGED) != 0 && (Integer)this.getEntityData().get(DATA_IS_ENRAGED) != 6) {
         Supplier<Integer> attackCooldown = () -> (Integer)this.entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN);
         Supplier<Integer> hitAnimtime = () -> (Integer)this.entityData.get(AbstractBossEntity.DATA_HIT_ANIMTIME);
         int dieAnimtime = (Integer)this.entityData.get(AbstractBossEntity.DATA_DIE_ANIMTIME);
         if (dieAnimtime > 0) {
            this.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
            this.entityData.set(AbstractBossEntity.DATA_ATTACK_ANIMTIME, 0);
            this.entityData.set(AbstractBossEntity.DATA_HIT_ANIMTIME, 0);
            this.entityData.set(AbstractBossEntity.DATA_DIE_ANIMTIME, --dieAnimtime);
            this.entityData.set(DATA_GROUNDSMASH_ANIMTIME, 0);
            if (dieAnimtime == 1) {
               this.hurt(new DamageSource(this.level().holderOrThrow(DamageTypes.PLAYER_ATTACK), this.getSourceEntity()), 999.0F);
            }

            this.setPlayingMusic(false);
         } else {
            this.setPlayingMusic(true);
            this.entityData.set(AbstractBossEntity.DATA_BATTLE_TIME, (Integer)this.entityData.get(AbstractBossEntity.DATA_BATTLE_TIME) + 1);
            if (hitAnimtime.get() == 26) {
               for (int index7 = 0; index7 < 4; index7++) {
                  this.level()
                     .addParticle(
                        (ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(),
                        this.getX() + this.getLookAngle().x * 2.0,
                        this.getY() + this.getBbHeight() * 0.5,
                        this.getZ() + this.getLookAngle().z * 2.0,
                        (Math.random() - 0.5) * 2.5,
                        (Math.random() - 0.5) * 1.2,
                        (Math.random() - 0.5) * 2.5
                     );
               }
            }

            if (!this.level().isClientSide) {
               if ((Integer)this.entityData.get(DATA_IS_ENRAGED) == 3 && this.isTimerDone()) {
                  this.stopTriggeredAnim("main_controller", null);
                  this.setState(YetiEntity.YetiState.ENRAGED.toString());
                  this.entityData.set(DATA_IS_ENRAGED, 4);
                  this.playSound((SoundEvent)BossesRiseSounds.YETI_ENRAGE.value(), 1.0F, 1.0F);
               } else if ((Integer)this.entityData.get(DATA_IS_ENRAGED) == 4 && this.getState() != YetiEntity.YetiState.ENRAGED.getState()) {
                  this.entityData.set(DATA_IS_ENRAGED, 5);
               } else if (this.getTarget() != null) {
                  if (this.targetTooFarTicks >= 50 && this.isTimerDone() && this.getTarget().onGround()) {
                     this.doAttack(YetiEntity.YetiState.RANGED_CONTROL.attack);
                     this.targetTooFarTicks = 0;
                  } else if (attackCooldown.get() >= 1) {
                     AbstractBossEntity.Attack attack = this.attackBuffer == null ? this.chooseAttack() : this.attackBuffer;
                     YetiEntity.YetiState yetiState = YetiEntity.YetiState.byAttack(attack);
                     if (yetiState == YetiEntity.YetiState.THROW_SPIKE) {
                        this.doAttack(attack);
                     } else {
                        if (yetiState == YetiEntity.YetiState.ICE_BARRAGE) {
                           this.entityData.set(DATA_HAS_USED_ULTIMATE, 1);
                        }

                        if (!new AABB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY(), this.getZ())
                           .inflate(5.0)
                           .contains(this.getTarget().position())) {
                           this.attackBuffer = attack;
                        } else {
                           if (this.attackBuffer != null) {
                              this.attackBuffer = null;
                           }

                           this.doAttack(attack);
                        }
                     }
                  } else {
                     if (this.isTargetNear(32.0)) {
                        if (this.targetTooFarTicks > 0) {
                           this.targetTooFarTicks--;
                        }

                        if (this.isTimerDone()) {
                           this.entityData
                              .set(AbstractBossEntity.DATA_ATTACK_COOLDOWN, (Integer)this.entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN) + 1);
                        }
                     } else {
                        this.targetTooFarTicks++;
                     }
                  }
               }
            }
         }
      } else {
         this.setPlayingMusic(false);
      }
   }

   @Override
   public boolean isTimerDone() {
      return this.getState() == null || super.isTimerDone();
   }

   @Override
   public void setState(String name) {
      if (this.getState() != null) {
         if (YetiEntity.YetiState.byState(this.getState()) == YetiEntity.YetiState.DEATH) {
            return;
         }

         if (name.equals(this.getState().name())) {
            this.stopTriggeredAnim("main_controller", null);
         }
      }

      super.setState(name);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.5)
         .add(Attributes.MAX_HEALTH, 250.0)
         .add(Attributes.ARMOR, 2.0)
         .add(Attributes.ATTACK_DAMAGE, 18.0)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.STEP_HEIGHT, 2.3)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   @Override
   public AbstractBossEntity.Attack chooseAttack() {
      this.navigation.stop();
      if (this.attackPattern == null || this.attackPatternIndex >= this.attackPattern.length()) {
         this.attackPattern = this.isEnraged() ? ENRAGED_POOL.selectRandom(this) : POOL.selectRandom(this);
         this.attackPatternIndex = 0;
      }

      return this.attackPattern != null ? this.attackPattern.get(this.attackPatternIndex++).attackOrDefault() : YetiEntity.YetiState.PUNCH.attackOrDefault();
   }

   @Override
   public void doAttack(@Nullable AbstractBossEntity.Attack attack) {
      if (this.attackBuffer != null) {
         this.attackBuffer = null;
      }

      super.doAttack(attack);
   }

   private static void addState(AbstractBossEntity.State state) {
      STATES.put(state.name(), state);
   }

   static {
      for (YetiEntity.YetiState yetiState : YetiEntity.YetiState.values()) {
         addState(yetiState.getState());
      }

      POOL = new YetiEntity.YetiAttackPatternPool(
         new YetiEntity.YetiAttackPattern[]{
            new YetiEntity.YetiAttackPattern(yeti -> {
               LivingEntity target = yeti.getTarget();
               return target != null && target.distanceTo(yeti) < 15.0F ? 22 : 12;
            }, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1}),
            new YetiEntity.YetiAttackPattern(yeti -> {
               LivingEntity target = yeti.getTarget();
               return target != null && target.distanceTo(yeti) < 15.0F ? 12 : 2;
            }, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2}),
            new YetiEntity.YetiAttackPattern(yeti -> {
               LivingEntity target = yeti.getTarget();
               return target != null && target.distanceTo(yeti) < 15.0F ? 10 : 30;
            }, new YetiEntity.YetiState[]{YetiEntity.YetiState.THROW_SPIKE}),
            new YetiEntity.YetiAttackPattern(yeti -> 10, new YetiEntity.YetiState[]{YetiEntity.YetiState.GROUND_SMASH}),
            new YetiEntity.YetiAttackPattern(yeti -> 8, new YetiEntity.YetiState[]{YetiEntity.YetiState.PUNCH}),
            new YetiEntity.YetiAttackPattern(yeti -> 10, new YetiEntity.YetiState[]{YetiEntity.YetiState.GROUND_SMASH, YetiEntity.YetiState.SWIPE_1}),
            new YetiEntity.YetiAttackPattern(
               yeti -> 8, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2, YetiEntity.YetiState.PUNCH}
            ),
            new YetiEntity.YetiAttackPattern(yeti -> 6, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.THROW_SPIKE}),
            new YetiEntity.YetiAttackPattern(
               yeti -> 4, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2, YetiEntity.YetiState.THROW_SPIKE}
            ),
            new YetiEntity.YetiAttackPattern(
               yeti -> 2, new YetiEntity.YetiState[]{YetiEntity.YetiState.GROUND_SMASH, YetiEntity.YetiState.PUNCH, YetiEntity.YetiState.PUNCH}
            )
         }
      );
      ENRAGED_POOL = new YetiEntity.YetiAttackPatternPool(
         new YetiEntity.YetiAttackPattern[]{
            new YetiEntity.YetiAttackPattern(yeti -> {
               LivingEntity target = yeti.getTarget();
               return target != null && target.distanceTo(yeti) < 15.0F ? 14 : 4;
            }, new YetiEntity.YetiState[]{
               YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2, YetiEntity.YetiState.PUNCH, YetiEntity.YetiState.GROUND_SMASH
            }),
            new YetiEntity.YetiAttackPattern(
               yeti -> 10, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2, YetiEntity.YetiState.THROW_SPIKE}
            ),
            new YetiEntity.YetiAttackPattern(
               yeti -> {
                  LivingEntity target = yeti.getTarget();
                  return target != null && target.distanceTo(yeti) < 15.0F ? 14 : 4;
               },
               new YetiEntity.YetiState[]{
                  YetiEntity.YetiState.LEAP_SMASH, YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2, YetiEntity.YetiState.GROUND_SMASH
               }
            ),
            new YetiEntity.YetiAttackPattern(yeti -> 10, new YetiEntity.YetiState[]{YetiEntity.YetiState.LEAP_SMASH, YetiEntity.YetiState.GROUND_SMASH}),
            new YetiEntity.YetiAttackPattern(
               yeti -> 10, new YetiEntity.YetiState[]{YetiEntity.YetiState.GROUND_SMASH, YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.LEAP_SMASH}
            ),
            new YetiEntity.YetiAttackPattern(yeti -> 10, new YetiEntity.YetiState[]{YetiEntity.YetiState.THROW_SPIKE, YetiEntity.YetiState.LEAP_SMASH}),
            new YetiEntity.YetiAttackPattern(
               yeti -> 8, new YetiEntity.YetiState[]{YetiEntity.YetiState.PUNCH, YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2}
            ),
            new YetiEntity.YetiAttackPattern(yeti -> 7, new YetiEntity.YetiState[]{YetiEntity.YetiState.SWIPE_1, YetiEntity.YetiState.SWIPE_2}),
            new YetiEntity.YetiAttackPattern(yeti -> 6, new YetiEntity.YetiState[]{YetiEntity.YetiState.GROUND_SMASH}),
            new YetiEntity.YetiAttackPattern(yeti -> {
               LivingEntity target = yeti.getTarget();
               return target != null && target.distanceTo(yeti) < 15.0F ? 1 : 21;
            }, new YetiEntity.YetiState[]{YetiEntity.YetiState.THROW_SPIKE}),
            new YetiEntity.YetiAttackPattern(
               yeti -> yeti.entityData.get(DATA_HAS_USED_ULTIMATE) > 0 ? 5 : 0, new YetiEntity.YetiState[]{YetiEntity.YetiState.ICE_BARRAGE}
            )
         }
      );
   }

   public record YetiAttackPattern(Function<YetiEntity, Integer> weight, YetiEntity.YetiState[] attacks) {
      public int length() {
         return this.attacks.length;
      }

      public YetiEntity.YetiState get(int index) {
         return this.attacks[index];
      }
   }

   public record YetiAttackPatternPool(YetiEntity.YetiAttackPattern[] attackPatterns) {
      public int totalWeight(YetiEntity yeti) {
         return Arrays.stream(this.attackPatterns).mapToInt(yetiAttackPattern -> yetiAttackPattern.weight().apply(yeti)).sum();
      }

      @Nullable
      public YetiEntity.YetiAttackPattern selectRandom(YetiEntity yeti) {
         int randomWeight = (int)(Math.random() * this.totalWeight(yeti));
         int weightSum = 0;

         for (YetiEntity.YetiAttackPattern pattern : this.attackPatterns) {
            weightSum += pattern.weight().apply(yeti);
            if (randomWeight < weightSum) {
               return pattern;
            }
         }

         return null;
      }
   }

   @EventBusSubscriber
   public static class YetiEvents {
      @SubscribeEvent
      private static void onBreakBlock(BreakEvent event) {
         if (event.getPlayer().level() instanceof ServerLevel level && event.getState().is(Blocks.ICE)) {
            for (YetiEntity yeti : level.getEntitiesOfClass(YetiEntity.class, new AABB(event.getPos()).inflate(10.0))) {
               if ((Integer)yeti.getEntityData().get(YetiEntity.DATA_IS_ENRAGED) == 0) {
                  yeti.setState(YetiEntity.YetiState.INTRO.toString());
                  yeti.entityData.set(YetiEntity.DATA_IS_ENRAGED, 1);
                  event.setCanceled(true);
                  level.setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
                  return;
               }
            }
         }
      }
   }

   public enum YetiState {
      IDLE(30, null, false),
      INTRO(193, YetiEntity.INTRO_ANIM, true),
      ENRAGED(200, YetiEntity.ENRAGED_ANIM, true),
      DEATH(112, YetiEntity.DEATH_ANIM, true),
      SWIPE_1(41, BossesRiseSounds.YETI_HANDSWIPE, YetiEntity.SWIPE_1_ANIM, true),
      SWIPE_2(53, BossesRiseSounds.YETI_HANDSWIPE2, YetiEntity.SWIPE_2_ANIM, true),
      LEAP_SMASH(70, BossesRiseSounds.YETI_JUMP, YetiEntity.LEAP_START, true),
      GROUND_SMASH(42, BossesRiseSounds.YETI_GROUNDSMASH, YetiEntity.GROUND_SMASH_ANIM, true),
      THROW_SPIKE(43, BossesRiseSounds.THROW_ICICLE, YetiEntity.SPIKE_ANIM, true),
      PUNCH(40, BossesRiseSounds.YETI_PUNCH, YetiEntity.PUNCH_ANIM, true),
      RANGED_CONTROL(50, BossesRiseSounds.YETI_RANGED_CONTROL, YetiEntity.RANGE_CONTROL_ANIM, true),
      ICE_BARRAGE(180, BossesRiseSounds.YETI_ICE_BARRAGE, YetiEntity.ICE_BARRAGE_ANIM, true);

      private final AbstractBossEntity.State state;
      @Nullable
      private final AbstractBossEntity.Attack attack;
      @Nullable
      private final RawAnimation animation;
      public final boolean stopsMovement;

      YetiState(int duration, @Nullable RawAnimation animation, boolean stopsMovement) {
         this.state = new AbstractBossEntity.State(this.toString(), duration);
         this.attack = null;
         this.animation = animation;
         this.stopsMovement = stopsMovement;
      }

      YetiState(int duration, Holder<SoundEvent> attackSound, RawAnimation animation, boolean stopsMovement) {
         this.state = new AbstractBossEntity.State(this.toString(), duration);
         this.attack = new AbstractBossEntity.Attack(this.toString(), attackSound);
         this.animation = animation;
         this.stopsMovement = stopsMovement;
      }

      public AbstractBossEntity.State getState() {
         return this.state;
      }

      public boolean isAttack() {
         return this.attack != null;
      }

      @Nullable
      public AbstractBossEntity.Attack getAttack() {
         return this.attack;
      }

      public AbstractBossEntity.Attack attackOrDefault() {
         return this.attack != null ? this.attack : new AbstractBossEntity.Attack(SWIPE_1.toString(), BossesRiseSounds.YETI_HANDSWIPE);
      }

      public Optional<RawAnimation> getAnimation() {
         return Optional.ofNullable(this.animation);
      }

      @Nullable
      public static YetiEntity.YetiState byState(@Nullable AbstractBossEntity.State state) {
         return state == null ? null : valueOf(state.name());
      }

      @Nullable
      public static YetiEntity.YetiState byAttack(@Nullable AbstractBossEntity.Attack attack) {
         return attack == null ? null : valueOf(attack.name());
      }
   }
}
