package net.unusual.block_factorys_bosses.entity.boss.knight;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.client.ClientParticleHandler;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.RiftEntity;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SoulShockwaveEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;
import net.unusual.block_factorys_bosses.geckolib.boss.knight.UnderworldKnightRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseEntityDataSerializers;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.network.ParticleEventMessage;
import net.unusual.block_factorys_bosses.procedures.UnderworldKnightOnEntityTickUpdateProcedure;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.joml.Vector3d;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController.ParticleKeyframeHandler;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnderworldKnightEntity extends AbstractBossEntity {
   public static final EntityDataAccessor<Integer> DATA_UNDEAD_CINEMATIC = SynchedEntityData.defineId(UnderworldKnightEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> DATA_CINEMATIC = SynchedEntityData.defineId(UnderworldKnightEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> DATA_IMMUNE_STACKS = SynchedEntityData.defineId(UnderworldKnightEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_IMMUNE_MAX = SynchedEntityData.defineId(UnderworldKnightEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> DATA_TRANSFORMED = SynchedEntityData.defineId(UnderworldKnightEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> DATA_INTRO_ATTACK = SynchedEntityData.defineId(UnderworldKnightEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Optional<BlockPos>> DATA_HOME = SynchedEntityData.defineId(
      UnderworldKnightEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   public static final EntityDataAccessor<Optional<Direction>> DATA_HOME_FACING = SynchedEntityData.defineId(
      UnderworldKnightEntity.class, (EntityDataSerializer)BossesRiseEntityDataSerializers.OPTIONAL_DIRECTION.get()
   );
   public static String KNOCKED_DOWN_MESSAGE = "message.block_factorys_bosses.helvar.knocked_down";
   public static final int FIRST_PHASE = 0;
   public static final int FAKE_DEATH_PHASE = 1;
   public static final int SECOND_PHASE = 2;
   public static final int REVENGE_PHASE = 3;
   public static final int TICKS_BEFORE_INTRO_ATTACK = 10;
   private static final int ATTACK_RADIUS = 10;
   private static final int THRUST_RADIUS = 15;
   private static final int TURN_DELAY = 5;
   private static final int ANIM_TRANSITION_TIME = 3;
   private static final double RANGED_ATTACK_HP_THRESHOLD_75 = 0.75;
   private static final double RANGED_ATTACK_HP_THRESHOLD_50 = 0.5;
   private static final double RANGED_ATTACK_HP_THRESHOLD_25 = 0.25;
   private static final int RANGED_ATTACK_INDEX_1 = 1;
   private static final int RANGED_ATTACK_INDEX_2 = 2;
   private static final int RANGED_ATTACK_INDEX_3 = 3;
   private static final int RANGED_ATTACK_INDEX_4 = 4;
   private static final int RANGED_ATTACK_INDEX_5 = 5;
   private AbstractBossEntity.AttackPattern attackPattern;
   private int attackPatternIndex = 0;
   private AbstractBossEntity.Attack lastAttack;
   private boolean isStuck = false;
   private int lightCounter = 0;
   public int heavyCounter = 0;
   private boolean hpGate75 = true;
   private boolean hpGate50 = true;
   private boolean hpGate25 = true;
   private int timeSinceLastJumpAttack = 400;
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.idle1");
   public static final RawAnimation INTRO_POSE = RawAnimation.begin().thenLoop("animation.intro_animation_pose");
   public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.walk");
   public static final RawAnimation DASH_ANIM = RawAnimation.begin().thenLoop("animation.dodge");
   public static final RawAnimation INTRO_ANIM = RawAnimation.begin().thenPlayAndHold("intro_animation");
   public static final RawAnimation RESURRECT_ANIM = RawAnimation.begin().thenPlayAndHold("resurrection_cinematic_camera zoom_out");
   public static final RawAnimation FAKE_DIE_ANIM = RawAnimation.begin().thenPlayAndHold("fake_death_cinematic");
   public static final RawAnimation DIE_ANIM = RawAnimation.begin().thenPlayAndHold("revenge_to_death");
   public static final RawAnimation STUCK_ANIM = RawAnimation.begin().thenLoop("animation.general_stuck");
   public static final RawAnimation KNOCKED_DOWN_ANIM = RawAnimation.begin().thenLoop("knockdown_idle");
   public static final RawAnimation REVENGE_KNOCKED_DOWN_ANIM = RawAnimation.begin().thenLoop("revenge_idle");
   public static final RawAnimation JUMP_BACK_ANIM = RawAnimation.begin().thenPlayAndHold("animation.knocked_down_ranged_jump_back_full");
   public static final RawAnimation RANGED_KNOCK_DOWN_ANIM = RawAnimation.begin().thenPlayAndHold("animation.knocked_down_ranged_attack");
   public static final RawAnimation STUCK_TO_IDLE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.general_stuck_to_idle_1");
   public static final RawAnimation IDLE_TO_KNOCKED_DOWN_ANIM = RawAnimation.begin().thenPlayAndHold("idle_1_to_knockdown");
   public static final RawAnimation KNOCKED_DOWN_TO_IDLE_ANIM = RawAnimation.begin().thenPlayAndHold("knockdown_to_idle_1");
   public static final RawAnimation REVENGE_KNOCKED_DOWN_TO_IDLE_ANIM = RawAnimation.begin().thenPlayAndHold("revenge_idle_to_idle_1");
   public static final RawAnimation INTRO_ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("animation.introduction_attack");
   public static final RawAnimation LIGHT_ATTACK_ANIM_1 = RawAnimation.begin().thenPlayAndHold("Light_attack_1_idle_1");
   public static final RawAnimation LIGHT_ATTACK_ANIM_2 = RawAnimation.begin().thenPlayAndHold("Light_attack_2_idle_1");
   public static final RawAnimation HEAVY_ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("animation.heavy_attack_idle_1");
   public static final RawAnimation LIGHT_HEAVY_ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("Light_attack_heavy_attack_combo_spline");
   public static final RawAnimation COMBO_ATTACK_1_ANIM = RawAnimation.begin().thenPlayAndHold("animation.combo1_full");
   public static final RawAnimation COMBO_ATTACK_2_ANIM = RawAnimation.begin().thenPlayAndHold("animation.combo2_full");
   public static final RawAnimation JUMP_ATTACK_1_ANIM = RawAnimation.begin().thenPlayAndHold("animation.jumpspin1_to_idle2");
   public static final RawAnimation JUMP_ATTACK_2_ANIM = RawAnimation.begin().thenPlayAndHold("animation.jumpspin2_to_idle2");
   public static final RawAnimation JUMP_ATTACK_3_ANIM = RawAnimation.begin().thenPlayAndHold("animation.jumpspin3_to_idle2");
   public static final RawAnimation RANGED_VERTICAL_TO_IDLE = RawAnimation.begin().thenPlayAndHold("animation.vertical_slash_idle_to_idle");
   public static final RawAnimation RANGED_VERTICAL_TO_TRANS = RawAnimation.begin().thenPlayAndHold("animation.vertical_slash_to_transition");
   public static final RawAnimation RANGED_HORIZONTAL_TO_IDLE = RawAnimation.begin().thenPlayAndHold("animation.horizontal_slash_to_idle");
   public static final RawAnimation RANGED_HORIZONTAL_TO_TRANS = RawAnimation.begin().thenPlayAndHold("animation.horizontal_slash_transition");
   public static final RawAnimation THRUST_ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("animation.thurst");
   public static final RawAnimation REVENGE_ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("revenge_attack_idle_1");
   public static final RawAnimation WISP_IDLE_ANIM = RawAnimation.begin().thenLoop("animation.stack");
   public static final RawAnimation STAGGER_ANIM = RawAnimation.begin().thenPlayAndHold("animation.combo_stagger_idle");
   private static final Map<String, AbstractBossEntity.State> STATES = new HashMap<>();
   public static final AbstractBossEntity.Attack INTRO_ATTACK = new AbstractBossEntity.Attack("intro_attack");
   public static final AbstractBossEntity.Attack LIGHT_ATTACK = new AbstractBossEntity.Attack("light_attack");
   public static final AbstractBossEntity.Attack LIGHT_ATTACK_1 = new AbstractBossEntity.Attack("light_attack_1", BossesRiseSounds.KNIGHT_LIGHT_ATTACK_1);
   public static final AbstractBossEntity.Attack LIGHT_ATTACK_2 = new AbstractBossEntity.Attack("light_attack_2", BossesRiseSounds.KNIGHT_LIGHT_ATTACK_2);
   public static final AbstractBossEntity.Attack HEAVY_ATTACK = new AbstractBossEntity.Attack("heavy_attack", BossesRiseSounds.KNIGHT_HEAVY_ATTACK);
   public static final AbstractBossEntity.Attack LIGHT_HEAVY_ATTACK = new AbstractBossEntity.Attack(
      "light_heavy_attack", BossesRiseSounds.KNIGHT_LIGHT_HEAVY_ATTACK
   );
   public static final AbstractBossEntity.Attack COMBO_ATTACK = new AbstractBossEntity.Attack("combo_attack");
   public static final AbstractBossEntity.Attack COMBO_ATTACK_1 = new AbstractBossEntity.Attack("combo_attack_1", BossesRiseSounds.KNIGHT_COMBO_1);
   public static final AbstractBossEntity.Attack COMBO_ATTACK_2 = new AbstractBossEntity.Attack("combo_attack_2", BossesRiseSounds.KNIGHT_COMBO_2);
   public static final AbstractBossEntity.Attack JUMP_ATTACK = new AbstractBossEntity.Attack("jump_attack");
   public static final AbstractBossEntity.Attack JUMP_ATTACK_1 = new AbstractBossEntity.Attack("jump_attack_1", BossesRiseSounds.KNIGHT_JUMPSPIN_1);
   public static final AbstractBossEntity.Attack JUMP_ATTACK_2 = new AbstractBossEntity.Attack("jump_attack_2", BossesRiseSounds.KNIGHT_JUMPSPIN_2);
   public static final AbstractBossEntity.Attack JUMP_ATTACK_3 = new AbstractBossEntity.Attack("jump_attack_3", BossesRiseSounds.KNIGHT_JUMPSPIN_3);
   public static final AbstractBossEntity.Attack THRUST_ATTACK = new AbstractBossEntity.Attack("thrust_attack", BossesRiseSounds.KNIGHT_THRUST);
   public static final AbstractBossEntity.Attack RANGED_ATTACK_V_IDLE = new AbstractBossEntity.Attack("ranged_attack_v_idle");
   public static final AbstractBossEntity.Attack RANGED_ATTACK_V_TRANS = new AbstractBossEntity.Attack("ranged_attack_v_trans");
   public static final AbstractBossEntity.Attack RANGED_ATTACK_H_IDLE = new AbstractBossEntity.Attack("ranged_attack_h_idle");
   public static final AbstractBossEntity.Attack RANGED_ATTACK_H_TRANS = new AbstractBossEntity.Attack("ranged_attack_h_trans");
   public static final AbstractBossEntity.Attack REVENGE_ATTACK = new AbstractBossEntity.Attack("revenge_attack");
   private static final AbstractBossEntity.AttackPatternPool PHASE_ONE_ABOVE_50 = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(35, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, LIGHT_ATTACK, LIGHT_ATTACK, LIGHT_HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, HEAVY_ATTACK, COMBO_ATTACK}),
         new AbstractBossEntity.AttackPattern(25, new AbstractBossEntity.Attack[]{LIGHT_HEAVY_ATTACK, LIGHT_ATTACK, LIGHT_ATTACK}),
         new AbstractBossEntity.AttackPattern(18, new AbstractBossEntity.Attack[]{JUMP_ATTACK_2, LIGHT_HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(7, new AbstractBossEntity.Attack[]{COMBO_ATTACK, HEAVY_ATTACK, LIGHT_HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(5, new AbstractBossEntity.Attack[]{COMBO_ATTACK, COMBO_ATTACK, HEAVY_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool PHASE_ONE_BELOW_50 = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(20, new AbstractBossEntity.Attack[]{HEAVY_ATTACK, COMBO_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(19, new AbstractBossEntity.Attack[]{COMBO_ATTACK, HEAVY_ATTACK, LIGHT_ATTACK}),
         new AbstractBossEntity.AttackPattern(28, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, HEAVY_ATTACK, LIGHT_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{COMBO_ATTACK, COMBO_ATTACK, LIGHT_HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(5, new AbstractBossEntity.Attack[]{LIGHT_HEAVY_ATTACK, HEAVY_ATTACK, JUMP_ATTACK_3, COMBO_ATTACK}),
         new AbstractBossEntity.AttackPattern(18, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, LIGHT_ATTACK, LIGHT_ATTACK, LIGHT_HEAVY_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool PHASE_TWO_ABOVE_50 = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(22, new AbstractBossEntity.Attack[]{HEAVY_ATTACK, COMBO_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(18, new AbstractBossEntity.Attack[]{COMBO_ATTACK, HEAVY_ATTACK, LIGHT_ATTACK}),
         new AbstractBossEntity.AttackPattern(16, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, HEAVY_ATTACK, LIGHT_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(14, new AbstractBossEntity.Attack[]{LIGHT_HEAVY_ATTACK, HEAVY_ATTACK, JUMP_ATTACK_2}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, LIGHT_ATTACK, COMBO_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{HEAVY_ATTACK, COMBO_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{JUMP_ATTACK_2, COMBO_ATTACK, COMBO_ATTACK, LIGHT_HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, LIGHT_ATTACK, LIGHT_ATTACK, LIGHT_HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(4, new AbstractBossEntity.Attack[]{LIGHT_HEAVY_ATTACK, HEAVY_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool PHASE_TWO_BELOW_50 = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(24, new AbstractBossEntity.Attack[]{HEAVY_ATTACK, COMBO_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(20, new AbstractBossEntity.Attack[]{COMBO_ATTACK, HEAVY_ATTACK, COMBO_ATTACK}),
         new AbstractBossEntity.AttackPattern(18, new AbstractBossEntity.Attack[]{HEAVY_ATTACK, LIGHT_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(14, new AbstractBossEntity.Attack[]{COMBO_ATTACK, LIGHT_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{LIGHT_ATTACK, HEAVY_ATTACK, LIGHT_ATTACK, HEAVY_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{JUMP_ATTACK_2, COMBO_ATTACK}),
         new AbstractBossEntity.AttackPattern(4, new AbstractBossEntity.Attack[]{JUMP_ATTACK_3, COMBO_ATTACK, COMBO_ATTACK, COMBO_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPattern RANGED_75 = new AbstractBossEntity.AttackPattern(
      24, new AbstractBossEntity.Attack[]{RANGED_ATTACK_V_TRANS, RANGED_ATTACK_H_TRANS, RANGED_ATTACK_V_IDLE}
   );
   private static final AbstractBossEntity.AttackPattern RANGED_50 = new AbstractBossEntity.AttackPattern(
      24, new AbstractBossEntity.Attack[]{RANGED_ATTACK_V_TRANS, RANGED_ATTACK_H_TRANS, RANGED_ATTACK_V_IDLE, REVENGE_ATTACK}
   );
   private static final AbstractBossEntity.AttackPattern RANGED_25 = new AbstractBossEntity.AttackPattern(
      24, new AbstractBossEntity.Attack[]{RANGED_ATTACK_V_TRANS, RANGED_ATTACK_H_TRANS, RANGED_ATTACK_V_TRANS, RANGED_ATTACK_H_TRANS, RANGED_ATTACK_V_IDLE}
   );
   private static final AbstractBossEntity.AttackPattern RANGED_0 = new AbstractBossEntity.AttackPattern(
      24,
      new AbstractBossEntity.Attack[]{
         RANGED_ATTACK_V_TRANS, RANGED_ATTACK_H_TRANS, RANGED_ATTACK_V_TRANS, RANGED_ATTACK_H_TRANS, RANGED_ATTACK_V_IDLE, REVENGE_ATTACK
      }
   );

   public UnderworldKnightEntity(EntityType<UnderworldKnightEntity> type, Level world) {
      super(type, world);
   }

   @Override
   protected ServerBossEvent createBossEvent() {
      return (ServerBossEvent)Util.make(
         new ServerBossEvent(this.getDisplayName(), BossBarColor.WHITE, BossBarOverlay.NOTCHED_6), bossEvent -> bossEvent.setVisible(false)
      );
   }

   @Override
   protected int getBossType() {
      return this.entityData.get(DATA_TRANSFORMED) ? 7 : 6;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<UnderworldKnightEntity> controller = new CinematicAnimationController(this, "main_controller", 3, state -> {
            if ((Integer)this.entityData.get(DATA_BOSS_PHASE) == -1) {
               return state.setAndContinue(INTRO_POSE);
            } else {
               return this.isState("idle") && state.isMoving() ? state.setAndContinue(WALK_ANIM) : state.setAndContinue(IDLE_ANIM);
            }
         })
         .triggerableAnim("dash", DASH_ANIM)
         .triggerableAnim("intro", INTRO_ANIM)
         .triggerableAnim("resurrect", RESURRECT_ANIM)
         .triggerableAnim("fake_dead", FAKE_DIE_ANIM)
         .triggerableAnim("dead", DIE_ANIM)
         .triggerableAnim("stuck", STUCK_ANIM)
         .triggerableAnim("stagger", STAGGER_ANIM)
         .triggerableAnim("knocked_down", KNOCKED_DOWN_ANIM)
         .triggerableAnim("revenge_knocked_down", REVENGE_KNOCKED_DOWN_ANIM)
         .triggerableAnim("jump_back", JUMP_BACK_ANIM)
         .triggerableAnim("ranged_knocked_down", RANGED_KNOCK_DOWN_ANIM)
         .triggerableAnim("stuck_to_idle", STUCK_TO_IDLE_ANIM)
         .triggerableAnim("idle_to_knocked_down", IDLE_TO_KNOCKED_DOWN_ANIM)
         .triggerableAnim("knocked_down_to_idle", KNOCKED_DOWN_TO_IDLE_ANIM)
         .triggerableAnim("revenge_knocked_down_to_idle", REVENGE_KNOCKED_DOWN_TO_IDLE_ANIM)
         .triggerableAnim("intro_attack", INTRO_ATTACK_ANIM)
         .triggerableAnim("light_attack_1", LIGHT_ATTACK_ANIM_1)
         .triggerableAnim("light_attack_2", LIGHT_ATTACK_ANIM_2)
         .triggerableAnim("heavy_attack", HEAVY_ATTACK_ANIM)
         .triggerableAnim("light_heavy_attack", LIGHT_HEAVY_ATTACK_ANIM)
         .triggerableAnim("combo_attack_1", COMBO_ATTACK_1_ANIM)
         .triggerableAnim("combo_attack_2", COMBO_ATTACK_2_ANIM)
         .triggerableAnim("jump_attack_1", JUMP_ATTACK_1_ANIM)
         .triggerableAnim("jump_attack_2", JUMP_ATTACK_2_ANIM)
         .triggerableAnim("jump_attack_3", JUMP_ATTACK_3_ANIM)
         .triggerableAnim("ranged_attack_v_idle", RANGED_VERTICAL_TO_IDLE)
         .triggerableAnim("ranged_attack_v_trans", RANGED_VERTICAL_TO_TRANS)
         .triggerableAnim("ranged_attack_h_idle", RANGED_HORIZONTAL_TO_IDLE)
         .triggerableAnim("ranged_attack_h_trans", RANGED_HORIZONTAL_TO_TRANS)
         .triggerableAnim("thrust_attack", THRUST_ATTACK_ANIM)
         .triggerableAnim("revenge_attack", REVENGE_ATTACK_ANIM);
      ParticleKeyframeHandler<UnderworldKnightEntity> particleHandler = new ParticleKeyframeHandler<UnderworldKnightEntity>() {
         @OnlyIn(Dist.CLIENT)
         public void handle(ParticleKeyframeEvent<UnderworldKnightEntity> event) {
            ParticleKeyframeData data = event.getKeyframeData();
            ParticleType<?> particleType = (ParticleType<?>)((Registry)BossesRiseParticleTypes.REGISTRY.getRegistry().get())
               .get(BossesRise.prefix(data.getEffect()));
            if (particleType != null) {
               ParticleOptions particle = (ParticleOptions)particleType.codec()
                  .codec()
                  .parse(
                     ((UnderworldKnightEntity)event.getAnimatable()).level().registryAccess().createSerializationContext(NbtOps.INSTANCE), new CompoundTag()
                  )
                  .getOrThrow();
               ParticleLocator locator = UnderworldKnightRenderer.BONER.get(data.getLocator());
               SubParticleEmitter.attachNewParticle((UnderworldKnightEntity)event.getAnimatable(), particle, locator);
            }
         }
      };
      controller.setParticleKeyframeHandler(particleHandler);
      controllers.add(controller);
      controllers.add(new AnimationController(this, "wisp_controller", 0, state -> state.setAndContinue(WISP_IDLE_ANIM)).setParticleKeyframeHandler(e -> {}));
   }

   @Override
   public int getAnimTransitionTime() {
      return 3;
   }

   @Override
   public Map<String, AbstractBossEntity.State> getStates() {
      return STATES;
   }

   public int getImmuneStacks() {
      return (Integer)this.entityData.get(DATA_IMMUNE_STACKS);
   }

   public void setImmuneStacks(int immuneStacks) {
      this.entityData.set(DATA_IMMUNE_STACKS, immuneStacks);
   }

   public boolean isInvulnerable() {
      return this.getImmuneStacks() > 0 || super.isInvulnerable();
   }

   public boolean canBeSeenAsEnemy() {
      return this.canBeSeenByAnyone();
   }

   public void removeOneImmuneStack() {
      this.entityData.set(DATA_IMMUNE_STACKS, this.getImmuneStacks() - 1);
   }

   public boolean isTransformed() {
      return (Boolean)this.entityData.get(DATA_TRANSFORMED);
   }

   public boolean isCinematic() {
      return (Boolean)this.entityData.get(DATA_CINEMATIC);
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_UNDEAD_CINEMATIC, 0);
      builder.define(DATA_CINEMATIC, false);
      builder.define(DATA_IMMUNE_STACKS, 1);
      builder.define(DATA_IMMUNE_MAX, 1);
      builder.define(DATA_TRANSFORMED, false);
      builder.define(DATA_INTRO_ATTACK, 10);
      builder.define(DATA_HOME, Optional.empty());
      builder.define(DATA_HOME_FACING, Optional.empty());
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
         protected boolean canPerformAttack(LivingEntity entity) {
            return false;
         }

         public boolean canUse() {
            return super.canUse() && UnderworldKnightEntity.this.isState("idle");
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse() && UnderworldKnightEntity.this.isState("idle");
         }
      });
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, false, false));
   }

   public void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_FOOTSTEP.value(), 0.15F, 1.0F);
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return (SoundEvent)BossesRiseSounds.KNIGHT_HURT.value();
   }

   public boolean hurt(DamageSource damagesource, float amount) {
      return this.processHurt(damagesource, amount, false);
   }

   public boolean processHurt(DamageSource damagesource, float amount, boolean fromMark) {
      if (!this.isState("dead") && (!damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !(this.getHealth() - amount < 0.0))) {
         if (this.isCinematic()) {
            return false;
         }

         boolean flag = fromMark || damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
         if (flag
            || !this.level().isClientSide()
               && (Integer)this.entityData.get(DATA_UNDEAD_CINEMATIC) <= 0
               && !damagesource.is(DamageTypes.IN_FIRE)
               && !damagesource.is(DamageTypes.FALL)
               && !damagesource.is(DamageTypes.LIGHTNING_BOLT)
               && !damagesource.is(DamageTypes.WITHER)
               && !damagesource.is(DamageTypes.WITHER_SKULL)) {
            if (flag || !this.isInvulnerable()) {
               double nextHealth = this.getHealth() - amount;
               double ratio = nextHealth / this.getMaxHealth();
               if (ratio <= 0.75 && this.hpGate75) {
                  this.hpGate75 = false;
                  if ((Integer)this.entityData.get(DATA_BOSS_PHASE) == 2) {
                     double threshold = this.getMaxHealth() * 0.75;
                     double diff = threshold - nextHealth;
                     if (diff > 0.0) {
                        amount = (float)Math.max(amount - diff, 1.0);
                     }

                     this.attackPattern = RANGED_75;
                     this.attackPatternIndex = 0;
                     this.setState("jump_back");
                     return super.hurt(damagesource, amount);
                  }
               } else {
                  if (ratio <= 0.5 && this.hpGate50) {
                     this.hpGate50 = false;
                     if ((Integer)this.entityData.get(DATA_BOSS_PHASE) == 2) {
                        double threshold = this.getMaxHealth() * 0.5;
                        double diff = threshold - nextHealth;
                        if (diff > 0.0) {
                           amount = (float)Math.max(amount - diff, 1.0);
                        }

                        this.attackPattern = RANGED_50;
                        this.attackPatternIndex = 0;
                        this.setState("jump_back");
                        this.entityData.set(DATA_IMMUNE_MAX, 2);
                        this.setImmuneStacks(2);
                     } else {
                        this.setImmuneStacks(1);
                     }

                     return super.hurt(damagesource, amount);
                  }

                  if (ratio <= 0.25 && this.hpGate25) {
                     this.hpGate25 = false;
                     if ((Integer)this.entityData.get(DATA_BOSS_PHASE) == 2) {
                        double threshold = this.getMaxHealth() * 0.25;
                        double diff = threshold - nextHealth;
                        if (diff > 0.0) {
                           amount = (float)Math.max(amount - diff, 1.0);
                        }

                        this.attackPattern = RANGED_25;
                        this.attackPatternIndex = 0;
                        this.setState("jump_back");
                        return super.hurt(damagesource, amount);
                     }
                  }
               }

               this.triggerAnim("stagger_controller", "stagger");
               return super.hurt(damagesource, amount);
            } else if (this.isState("stuck")) {
               int immuneStacks = this.getImmuneStacks();
               this.handleParticleEvent("wisp_explode " + immuneStacks);
               this.removeOneImmuneStack();
               if (this.isInvulnerable()) {
                  this.setState("stagger");
                  this.triggerAnim("stagger_controller", "stagger");
                  this.playSound((SoundEvent)BossesRiseSounds.WISP_EXPLODE.value(), 2.0F, 1.0F);
               } else {
                  this.setState("idle_to_knocked_down");
                  this.playSound((SoundEvent)BossesRiseSounds.WISP_EXPLODE_LAST.value(), 2.0F, 1.0F);
                  this.handleParticleEvent("glint");
                  if (damagesource.getEntity() instanceof Player player) {
                     player.displayClientMessage(Component.translatable(KNOCKED_DOWN_MESSAGE), true);
                  }
               }

               return super.hurt(damagesource, Math.min(20.0F, amount * 0.1F));
            } else {
               this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_BLOCK.value(), 2.0F, Mth.nextInt(RandomSource.create(), 7, 10) * 0.1F);
               return false;
            }
         } else {
            return false;
         }
      } else {
         return super.hurt(damagesource, amount);
      }
   }

   public boolean canFreeze() {
      return false;
   }

   @Override
   public boolean shouldCancelDeath() {
      if (this.isState("dead")) {
         return !this.isTimerDone();
      }

      switch (this.entityData.get(DATA_BOSS_PHASE)) {
         case 0:
            this.entityData.set(DATA_BOSS_PHASE, 1);
            this.setImmuneStacks(1);
            this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_FAKE_DEATH.value());
            this.setState("fake_dead");
            break;
         case 1:
            return true;
         case 2:
            this.attackPattern = RANGED_0;
            this.attackPatternIndex = 0;
            this.setState("jump_back");
            this.entityData.set(DATA_BOSS_PHASE, 3);
            this.setImmuneStacks(1);
            break;
         case 3:
            this.setState("dead");
            this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_DEATH.value());
      }

      return true;
   }

   private void spawnSmokeImpact(Vec3 position) {
      for (int i = 0; i < 80; i++) {
         double rand = Math.random();
         double angle = (Math.PI * 2) * Math.random();
         Vec3 dir = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
         Vec3 offset = dir.scale(2.5 + rand);
         Vec3 pos = position.add(offset.x, 0.1, offset.z);
         Vec3 vel = dir.scale(rand);
         ClientParticleHandler.addParticle((ParticleOptions)BossesRiseParticleTypes.SMOKE.get(), pos, vel);
      }
   }

   @Override
   public void handleParticleEvent(String name) {
      if (this.level().isClientSide) {
         this.handleParticleEventClient(name);
      } else {
         PacketDistributor.sendToPlayersNear(
            (ServerLevel)this.level(),
            null,
            this.getX(),
            this.getY(),
            this.getZ(),
            64.0,
            new ParticleEventMessage(this.getId(), name),
            new CustomPacketPayload[0]
         );
      }
   }

   protected void handleParticleEventClient(String name) {
      GeoModel<UnderworldKnightEntity> geoModel = ((UnderworldKnightRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(this))
         .getGeoModel();
      switch (name) {
         case "wisp_explode 1":
         case "wisp_explode 2":
         case "wisp_explode 3":
            String boneName = switch (name) {
               case "wisp_explode 1" -> "bone9";
               case "wisp_explode 2" -> "bone10";
               case "wisp_explode 3" -> "bone12";
               default -> "bone9";
            };
            if (geoModel.getBone(boneName).orElse(null) instanceof GeoBone bone) {
               Vec3 pos = this.toVec3(bone.getWorldPosition());
               ClientParticleHandler.addParticle((ParticleOptions)BossesRiseParticleTypes.EXPLOSION.get(), pos.subtract(0.0, 0.1, 0.0), Vec3.ZERO);
            }
            break;
         case "fake_dead smoke1":
            this.spawnSmokeImpact(this.position());
            break;
         case "fake_dead smoke2":
            if (geoModel.getBone("chest").orElse(null) instanceof GeoBone bone) {
               Vector3d pos = bone.getWorldPosition();
               this.spawnSmokeImpact(new Vec3(pos.x, this.getY(), pos.z));
            }
            break;
         case "resurrect smoke":
            if (geoModel.getBone("right_hand").orElse(null) instanceof GeoBone bone) {
               Vector3d pos = bone.getWorldPosition();
               this.spawnSmokeImpact(new Vec3(pos.x, this.getY(), pos.z));
            }
            break;
         case "knock_off_attack smoke":
            if (geoModel.getBone("left_hand").orElse(null) instanceof GeoBone bone) {
               Vector3d pos = bone.getWorldPosition();
               this.spawnSmokeImpact(new Vec3(pos.x, this.getY(), pos.z));
            }
            break;
         case "glint":
            if (geoModel.getBone("chest").orElse(null) instanceof GeoBone bone) {
               Vec3 pos = this.toVec3(bone.getWorldPosition());
               ClientParticleHandler.addParticle((ParticleOptions)BossesRiseParticleTypes.GLINT.get(), pos, Vec3.ZERO);
            }
            break;
         case "torso_explode":
            if (geoModel.getBone("chest").orElse(null) instanceof GeoBone bone) {
               Vec3 pos = this.toVec3(bone.getWorldPosition());
               ClientParticleHandler.addParticle((ParticleOptions)BossesRiseParticleTypes.EXPLOSION.get(), pos, Vec3.ZERO);
            }
            break;
         case "torso_explode big":
            if (geoModel.getBone("chest").orElse(null) instanceof GeoBone bone) {
               Vec3 pos = this.toVec3(bone.getWorldPosition());
               ClientParticleHandler.addParticle((ParticleOptions)BossesRiseParticleTypes.BIG_EXPLOSION.get(), pos, Vec3.ZERO);
            }
            break;
         case "red_smoke":
            if (geoModel.getBone("chest").orElse(null) instanceof GeoBone bone) {
               Vec3 pos = this.toVec3(bone.getWorldPosition());
               ClientParticleHandler.addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.RED_SMOKE.get(),
                  pos.add((Math.random() - 0.5) * 1.5, (Math.random() - 0.5) * 1.5, (Math.random() - 0.5) * 1.5),
                  new Vec3((Math.random() - 0.5) * 0.25, 0.0, (Math.random() - 0.5) * 0.25)
               );

               for (int i = 0; i < 2; i++) {
                  ClientParticleHandler.addParticle(
                     (ParticleOptions)BossesRiseParticleTypes.RED_SPARK.get(),
                     pos.add((Math.random() - 0.5) * 2.0, (Math.random() - 0.5) * 1.5, (Math.random() - 0.5) * 2.0),
                     Vec3.ZERO
                  );
               }
            }
      }
   }

   @Override
   public void tick() {
      super.tick();
      int bossPhase = (Integer)this.entityData.get(DATA_BOSS_PHASE);
      if (this.level().isClientSide()) {
         if (bossPhase == 0 || bossPhase == 2) {
            int immuneStacks = this.getImmuneStacks();
            GeoModel<UnderworldKnightEntity> geoModel = ((UnderworldKnightRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(this))
               .getGeoModel();
            ClientParticleHandler.renderInvulWisp(geoModel, "bone9", immuneStacks < 1);
            if (bossPhase == 2) {
               if ((Integer)this.entityData.get(DATA_IMMUNE_MAX) == 2) {
                  ClientParticleHandler.renderInvulWisp(geoModel, "bone10", immuneStacks < 2);
               }

               if (geoModel.getBone("chest").orElse(null) instanceof GeoBone bone) {
                  Vec3 pos = this.toVec3(bone.getWorldPosition());
                  ClientParticleHandler.addParticle(
                     (ParticleOptions)BossesRiseParticleTypes.SOUL.get(), pos.add(Math.random() - 0.5, Math.random(), Math.random() - 0.5), Vec3.ZERO
                  );
               }
            }
         }
      } else {
         this.timeSinceLastJumpAttack++;
         if (bossPhase == -1) {
            this.setDeltaMovement(Vec3.ZERO);
            if (this.isState("idle")
               && !this.level()
                  .getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(this.getX(), this.getY() + 8.0, this.getZ()), 32.0, 16.0, 32.0))
                  .isEmpty()) {
               this.entityData.set(DATA_CINEMATIC, true);
               this.setState("intro");
               this.forEachNearbyPlayer(32.0, player -> {
                  PlayerVariables playerVars = (PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES);
                  playerVars.boss_no_hit = true;
                  playerVars.syncPlayerVariables(player);
               });
            }

            this.setPlayingMusic(false);
         } else if (!this.isState("dead") && !this.isState("fake_dead") && !this.isState("resurrect")) {
            this.entityData.set(AbstractBossEntity.DATA_BATTLE_TIME, (Integer)this.entityData.get(AbstractBossEntity.DATA_BATTLE_TIME) + 1);
            this.setPlayingMusic(true);
            this.setBarVisible(true);
         } else {
            this.setPlayingMusic(false);
         }

         if (!this.isAnimTransitioning()) {
            int timer = this.getTimer();
            if (timer == 0 && this.attack != null && this.attack.sound() != null) {
               this.playSound(this.attack.sound(), 8.0F, 1.0F);
            }

            switch (this.getState().name()) {
               case "intro_attack":
                  if (timer == 1) {
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_INTRODUCTION_ATTACK.value());
                  }

                  if (timer < 18) {
                     this.lookAtTarget();
                  } else if (timer < 28) {
                     LivingEntity target = this.getTarget();
                     if (target != null) {
                        this.setDeltaMovement(this.position().subtract(target.position()).scale(-0.75));
                     }
                  } else if (timer == 28) {
                     this.meleeAttack(6.0, 7.0, 1.5);
                     this.getOutOfMe();
                  }
                  break;
               case "intro":
                  this.cleanNearbyPests();
                  if (timer == 0) {
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_INTRO.value());
                  } else if (timer == 370) {
                     this.setCinematicBlackScreen(true);
                  }
                  break;
               case "dash":
                  if (timer == 0) {
                     double distance = this.getTarget() != null ? this.distanceTo(this.getTarget()) : 1.0;
                     this.lookAtTarget();
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_DODGE.value());
                     this.push(this.getLookOffset(distance * 0.33, 0.0));
                  }
                  break;
               case "jump_back":
                  if (timer <= 5) {
                     this.lookAtTarget();
                  } else if (timer < 25) {
                     this.setDeltaMovement(0.0, 0.5, 0.0);
                  } else if (timer < 35) {
                     Vec3 target = this.getJumpTargetPos().add(0.0, 5.0, 0.0);
                     Vec3 diff = target.subtract(this.position());
                     this.lookAtTarget();
                     this.setDeltaMovement(diff.scale((timer - 24.0) / 10.0));
                  } else if (timer == 35) {
                     this.setDeltaMovement(Vec3.ZERO);
                     this.lookAtTarget();
                  }
                  break;
               case "light_attack_1":
                  if (timer < 5) {
                     this.lookAtTarget();
                  } else if (timer == 10) {
                     this.meleeAttack(6.0, 7.0, 1.5);
                     this.getOutOfMe();
                  }
                  break;
               case "light_attack_2":
                  if (timer < 13) {
                     this.lookAtTarget();
                  } else if (timer == 18) {
                     this.meleeAttack(6.0, 7.0, 1.5);
                     this.getOutOfMe();
                  }
                  break;
               case "heavy_attack":
                  if (timer >= 0 && timer <= 16) {
                     this.move(0.1);
                  }

                  if (timer == 5) {
                     this.placeMark(0.5F);
                  }

                  if (timer < 18) {
                     this.lookAtTarget();
                  } else if (timer == 23) {
                     this.meleeAttack(5.0, 7.0, 2.5);
                     this.getOutOfMe();
                  }
                  break;
               case "light_heavy_attack":
                  if (timer >= 0 && timer <= 8) {
                     this.move(0.1);
                  }

                  if (timer == 5) {
                     this.placeMark(0.5F);
                  }

                  if (timer < 3) {
                     this.lookAtTarget();
                  } else if (timer == 8) {
                     this.meleeAttack(6.0, 7.0, 1.5);
                     this.getOutOfMe();
                  } else if (timer > 16 && timer < 32) {
                     this.lookAtTarget();
                  } else if (timer == 37) {
                     this.lookAtTarget();
                     this.meleeAttack(5.0, 7.0, 2.5);
                     this.getOutOfMe();
                  }
                  break;
               case "combo_attack_1":
                  UnderworldKnightOnEntityTickUpdateProcedure.attackCombo1(this, this.level());
                  break;
               case "combo_attack_2":
                  if (timer == 33 || timer == 74 || timer == 113 || timer == 93 || timer == 120) {
                     this.lookAtTarget();
                  }

                  if (timer == 115 && this.heavyCounter < (this.getHealthRatio() > 0.5 ? 3 : 5)) {
                     this.placeMark(0.5F);
                  }

                  if (timer == 38 || timer == 79 || timer == 118) {
                     double distance = 1.0;
                     if (this.getTarget() != null) {
                        distance = this.distanceTo(this.getTarget()) * 0.08;
                     }

                     this.push(this.getLookAngle().x * distance, 0.3, this.getLookAngle().z * distance);
                     this.meleeAttack(14.0, 2.5, 1.5);
                  } else if (timer == 98 || timer == 125) {
                     double distance = 1.0;
                     if (this.getTarget() != null) {
                        distance = this.distanceTo(this.getTarget()) * 0.08;
                     }

                     this.push(this.getLookAngle().x * distance, 0.3, this.getLookAngle().z * distance);
                     this.meleeAttack(6.0, 2.5, 1.5);
                  }
                  break;
               case "jump_attack_1":
               case "jump_attack_2":
               case "jump_attack_3":
                  String name = this.getState().name();
                  if (timer < 10) {
                     this.lookAtTarget();
                  } else if (timer == 15) {
                     this.push(0.0, this.isInWaterOrBubble() ? 2.1 : 1.2, 0.0);
                  } else if (timer == 38) {
                     this.jumpSlash(0.0);
                  } else if ((!name.equals("jump_attack_2") || timer != 56) && (!name.equals("jump_attack_3") || timer != 49)) {
                     if (name.equals("jump_attack_3") && timer == 70) {
                        this.jumpSlash(7.0);
                     }
                  } else {
                     this.jumpSlash(3.0);
                  }
                  int endTime = switch (name) {
                     case "jump_attack_1" -> 38;
                     case "jump_attack_2" -> 56;
                     case "jump_attack_3" -> 70;
                     default -> 0;
                  };
                  if (this.getTarget() != null && timer > 15 && timer <= endTime + 10) {
                     this.push((this.getTarget().getX() - this.getX()) * 0.05, 0.0, (this.getTarget().getZ() - this.getZ()) * 0.05);
                     Vec3 delta = this.getDeltaMovement();
                     if (delta.y < -0.075) {
                        this.setDeltaMovement(delta.x, -0.075, delta.z);
                     }
                  }
                  break;
               case "thrust_attack":
                  if (timer < 40) {
                     this.lookAtTarget();
                  } else if (timer == 45) {
                     double distance = 1.0;
                     if (this.getTarget() != null) {
                        distance = this.distanceTo(this.getTarget()) * 0.33;
                     }

                     this.push(this.getLookOffset(distance, 0.0));
                  } else if (timer > 45 && timer <= 60) {
                     if (this.level().getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        for (int sx = -3; sx <= 3; sx++) {
                           for (int sy = 0; sy <= 7; sy++) {
                              for (int sz = -3; sz <= 3; sz++) {
                                 BlockPos blockPos = BlockPos.containing(this.getX() + sx, this.getY() + sy, this.getZ() + sz);
                                 double destroySpeed = this.level().getBlockState(blockPos).getDestroySpeed(this.level(), blockPos);
                                 if (destroySpeed > 0.0 && destroySpeed < 50.0) {
                                    this.level().setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                                 }
                              }
                           }
                        }
                     }

                     this.meleeAttack(4.0, 9.0, 0.2, 0.5F);
                  }
                  break;
               case "ranged_attack_h_trans":
               case "ranged_attack_h_idle":
                  if (timer == 1) {
                     if (this.getState().name().equals("ranged_attack_h_trans")) {
                        this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_HORIZONTAL_SLASH_TRANS.value());
                     } else {
                        this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_HORIZONTAL_SLASH.value());
                     }
                  }

                  if (timer < 14) {
                     this.lookAtTarget();
                  } else if (timer == 14) {
                     SwordWaveEntity swordWave = new SwordWaveEntity((EntityType<? extends SwordWaveEntity>)BossesRiseEntities.SWORD_WAVE.get(), this.level());
                     swordWave.setOwner(this);
                     swordWave.setNoGravity(true);
                     swordWave.setBaseDamage(5.0);
                     swordWave.setSilent(true);
                     swordWave.setKnockback(0);
                     swordWave.setPos(this.getX(), this.getY() + 0.5, this.getZ());
                     double dx = this.getLookAngle().x;
                     double dy = this.getLookAngle().y;
                     double dz = this.getLookAngle().z;
                     if (this.getTarget() instanceof LivingEntity target) {
                        dx = target.getX() - this.getX();
                        dy = target.getEyeY() - (this.getEyeY() - 0.1);
                        dz = target.getZ() - this.getZ();
                     }

                     swordWave.shoot(dx, dy, dz, 2.0F, 0.0F);
                     if (this.attackPatternIndex == 2) {
                        swordWave.setScale(4.0F);
                     } else {
                        swordWave.setScale(2.0F);
                     }

                     this.level().addFreshEntity(swordWave);
                  } else if (timer == 24 && this.attackPatternIndex == 4) {
                     SwordWaveEntity swordWave = new SwordWaveEntity((EntityType<? extends SwordWaveEntity>)BossesRiseEntities.SWORD_WAVE.get(), this.level());
                     swordWave.setOwner(this);
                     swordWave.setNoGravity(true);
                     swordWave.setBaseDamage(5.0);
                     swordWave.setSilent(true);
                     swordWave.setKnockback(0);
                     swordWave.setPos(this.getX(), this.getY() + 1.5, this.getZ());
                     double dx = this.getLookAngle().x;
                     double dy = this.getLookAngle().y;
                     double dz = this.getLookAngle().z;
                     if (this.getTarget() instanceof LivingEntity target) {
                        dx = target.getX() - this.getX();
                        dy = target.getEyeY() - (this.getEyeY() - 0.1);
                        dz = target.getZ() - this.getZ();
                     }

                     swordWave.shoot(dx, dy, dz, 2.0F, 0.0F);
                     swordWave.setScale(2.0F);
                     this.level().addFreshEntity(swordWave);
                  }
                  break;
               case "ranged_attack_v_trans":
               case "ranged_attack_v_idle":
                  if (timer == 1) {
                     if (this.getState().name().equals("ranged_attack_v_trans")) {
                        this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_VERTICAL_SLASH_TRANS.value());
                     } else {
                        this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_VERTICAL_SLASH.value());
                     }
                  }

                  if (timer == 1 && this.attackPatternIndex == 3) {
                     this.placeMark(1.0F);
                  }

                  if (timer < 7) {
                     this.lookAtTarget();
                  } else if (timer == 7) {
                     int count = 5;
                     if (this.attackPatternIndex == 3) {
                        count = 7;
                     } else if (this.attackPatternIndex == 5) {
                        count = 9;
                     }

                     int halfCount = count / 2;

                     for (int i = -halfCount; i <= halfCount; i++) {
                        SwordWaveEntity swordWave = new SwordWaveEntity(
                           (EntityType<? extends SwordWaveEntity>)BossesRiseEntities.SWORD_WAVE.get(), this.level()
                        );
                        swordWave.setVertical(true);
                        swordWave.setOwner(this);
                        swordWave.setNoGravity(true);
                        swordWave.setBaseDamage(5.0);
                        swordWave.setSilent(true);
                        swordWave.setKnockback(0);
                        swordWave.setPos(this.getX(), this.getY() + 1.0, this.getZ());
                        double dx = this.getLookAngle().x;
                        double dy = this.getLookAngle().y;
                        double dz = this.getLookAngle().z;
                        if (this.getTarget() instanceof LivingEntity target) {
                           dx = target.getX() - this.getX();
                           dy = target.getEyeY() - (this.getEyeY() - 0.1);
                           dz = target.getZ() - this.getZ();
                        }

                        swordWave.shoot(dx, dy, dz, 2.0F, 0.0F);
                        Vec3 offset = swordWave.getDeltaMovement().yRot((30.0F - 1.5F * count) * i * (float) (Math.PI / 180.0));
                        swordWave.setDeltaMovement(offset);
                        this.level().addFreshEntity(swordWave);
                     }
                  }
                  break;
               case "fake_dead":
                  if (timer == 35) {
                     this.handleParticleEvent("fake_dead smoke1");
                  } else if (timer == 63) {
                     this.handleParticleEvent("fake_dead smoke2");
                  }
                  break;
               case "resurrect":
                  this.cleanNearbyPests();
                  ((Optional)this.getEntityData().get(DATA_HOME_FACING)).ifPresent(direction -> {
                     float rot = direction.toYRot();
                     this.setYRot(rot);
                     this.yRotO = rot;
                     this.setYBodyRot(rot);
                     this.yBodyRotO = rot;
                     this.setYHeadRot(rot);
                     this.yHeadRotO = rot;
                  });
                  if (timer == 18) {
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_RESURRECT.value());
                  } else if (timer == 104) {
                     this.handleParticleEvent("resurrect smoke");
                  } else if (timer == 160) {
                     this.entityData.set(DATA_TRANSFORMED, true);
                     BossHandling.updateBossType(this.bossEvent, 7);
                  }
                  break;
               case "revenge_attack":
                  if (timer == 0) {
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_REVENGE_ATTACK.value());
                  } else if (timer == 24) {
                     ((ServerLevel)this.level())
                        .sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.RIFT.get(), this.getX(), this.getY() + 5.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0
                        );
                     RiftEntity rift = new RiftEntity((EntityType<RiftEntity>)BossesRiseEntities.RIFT.get(), this.level());
                     rift.setOwnerAndTarget(this, this.getTarget());
                     rift.setPos(this.getX(), this.getY() + 5.0, this.getZ());
                     this.level().addFreshEntity(rift);
                     this.move(-5.0);
                  }
                  break;
               case "stuck_to_idle":
                  if (timer >= 25 && timer <= 43) {
                     this.move(-0.1);
                  }
                  break;
               case "idle_to_knocked_down":
                  if (timer == 23 || timer == 27) {
                     this.handleParticleEvent("glint");
                     this.handleParticleEvent("torso_explode");
                  } else if (timer == 30) {
                     this.handleParticleEvent("glint");
                     this.handleParticleEvent("torso_explode big");
                  }
                  break;
               case "knocked_down":
                  this.handleParticleEvent("red_smoke");
                  break;
               case "knocked_down_to_idle":
                  if (timer == 20) {
                     this.meleeAttack(10.0, 0.0, 3.5, 0.1F);
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_KNOCK_OFF.value());
                     this.handleParticleEvent("knock_off_attack smoke");
                  }
            }

            if (this.isTimerDone()) {
               this.attack = null;
               switch (this.getState().name()) {
                  case "intro":
                     this.entityData.set(DATA_CINEMATIC, false);
                     this.entityData.set(DATA_BOSS_PHASE, 0);
                     this.setState("idle");
                     this.setBarVisible(true);
                     break;
                  case "idle":
                     this.setCinematicBlackScreen(false);
                     if (this.getTarget() == null || bossPhase == -1) {
                        return;
                     }

                     int intro = (Integer)this.entityData.get(DATA_INTRO_ATTACK);
                     if (intro > 0) {
                        if (intro == 1) {
                           this.doAttack(INTRO_ATTACK);
                        }

                        this.entityData.set(DATA_INTRO_ATTACK, intro - 1);
                        return;
                     }

                     double distance = this.distanceTo(this.getTarget());
                     if (distance > 15.0) {
                        if ((Integer)this.entityData.get(DATA_BOSS_PHASE) == 2) {
                           this.doAttack(INTRO_ATTACK);
                        } else {
                           this.doAttack(this.random.nextBoolean() && this.lastAttack != THRUST_ATTACK ? THRUST_ATTACK : JUMP_ATTACK);
                        }
                     } else if (distance > 10.0) {
                        if (timer > 20) {
                           if (this.random.nextBoolean()) {
                              this.doAttack(THRUST_ATTACK);
                           } else {
                              this.setState("dash");
                           }
                        }
                     } else {
                        AbstractBossEntity.Attack atk = this.chooseAttack();
                        if (this.timeSinceLastJumpAttack < 400 && (atk == JUMP_ATTACK_1 || atk == JUMP_ATTACK_2 || atk == JUMP_ATTACK_3)) {
                           return;
                        }

                        this.doAttack(atk);
                     }
                     break;
                  case "dash":
                     this.setState("idle");
                     break;
                  case "jump_back":
                  case "ranged_attack_v_trans":
                  case "ranged_attack_h_trans":
                  case "ranged_attack_v_idle":
                  case "ranged_attack_h_idle":
                     if (this.attackPattern == null) {
                        this.setState("idle");
                     }

                     AbstractBossEntity.Attack ranged = this.attackPattern.get(this.attackPatternIndex++);
                     if (ranged != null) {
                        this.doAttack(ranged);
                     } else {
                        this.setState("idle");
                     }
                     break;
                  case "light_attack_1":
                  case "light_attack_2":
                  case "thrust_attack":
                  case "jump_attack_1":
                  case "jump_attack_2":
                  case "jump_attack_3":
                  case "intro_attack":
                  case "ranged_knocked_down":
                     this.setState("idle");
                     break;
                  case "heavy_attack":
                  case "light_heavy_attack":
                     if (this.isStuck) {
                        this.isStuck = false;
                        this.lightCounter = 0;
                        this.heavyCounter = 0;
                        this.setState("stuck");
                     } else {
                        this.setState("stuck_to_idle");
                     }
                     break;
                  case "combo_attack_1":
                  case "combo_attack_2":
                     if (Math.random() < (this.getHealthRatio() > 0.5 ? 0.25 : 0.1)) {
                        this.doAttack(LIGHT_HEAVY_ATTACK);
                     } else {
                        this.setState("idle");
                     }
                     break;
                  case "dead":
                     if (this.getSourceEntity() instanceof Player player) {
                        this.hurt(this.damageSources().playerAttack(player), 999.0F);
                     } else {
                        this.hurt(this.damageSources().fellOutOfWorld(), 999.0F);
                     }
                     break;
                  case "fake_dead":
                     if (this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 20.0, false) != null) {
                        this.setState("resurrect");
                        ((Optional)this.entityData.get(DATA_HOME))
                           .ifPresent(
                              pos -> this.moveTo(
                                 Vec3.atBottomCenterOf(pos),
                                 ((Optional)this.entityData.get(DATA_HOME_FACING)).<Float>map(Direction::toYRot).orElse(this.getYRot()),
                                 0.0F
                              )
                           );
                        this.entityData.set(DATA_CINEMATIC, true);
                     }
                     break;
                  case "resurrect":
                     this.entityData.set(DATA_CINEMATIC, false);
                     this.entityData.set(DATA_BOSS_PHASE, 2);
                     this.hpGate75 = true;
                     this.hpGate50 = true;
                     this.hpGate25 = true;
                     this.setImmuneStacks(1);
                     this.setHealth(this.getMaxHealth());
                     this.setState("idle");
                     break;
                  case "revenge_attack":
                     if ((Integer)this.entityData.get(DATA_BOSS_PHASE) != 3) {
                        this.setState("ranged_knocked_down");
                     } else {
                        this.setImmuneStacks(0);
                        this.setState("revenge_knocked_down");
                     }
                     break;
                  case "revenge_knocked_down":
                     this.entityData.set(DATA_BOSS_PHASE, 2);
                     this.setImmuneStacks(1);
                     this.setState("revenge_knocked_down_to_idle");
                     break;
                  case "stuck":
                  case "stagger":
                     this.setState("stuck_to_idle");
                     break;
                  case "stuck_to_idle":
                     this.setState("idle");
                     break;
                  case "idle_to_knocked_down":
                     this.setState("knocked_down");
                     this.playSound((SoundEvent)BossesRiseSounds.KNIGHT_KNOCKED_DOWN.value(), 2.0F, 1.0F);
                     break;
                  case "knocked_down":
                     this.setState("knocked_down_to_idle");
                     break;
                  case "knocked_down_to_idle":
                  case "revenge_knocked_down_to_idle":
                  case "ranged_attack_1":
                  case "ranged_attack_2":
                     LivingEntity target = this.getTarget();
                     if (target == null) {
                        this.setState("idle");
                     } else {
                        double distance = target.position().distanceTo(this.position());
                        if (distance > 15.0) {
                           this.doAttack(JUMP_ATTACK);
                        } else {
                           this.setState("idle");
                        }
                     }
               }
            }
         }
      }
   }

   private void cleanNearbyPests() {
      this.level().getEntities(this, this.getBoundingBox().inflate(64.0)).forEach(entity -> {
         if (entity instanceof SoulSkeletonEntity || entity instanceof SoulKnightWitherSkeletonEntity) {
            entity.discard();
         }
      });
   }

   private Vec3 getJumpTargetPos() {
      BlockPos home = ((Optional)this.entityData.get(DATA_HOME)).orElseGet(() -> {
         this.entityData.set(DATA_HOME, Optional.of(this.blockPosition()));
         return this.blockPosition();
      });
      Vec3 targetPosA = Vec3.atCenterOf(home);
      Vec3 targetPosB = ((Optional)this.entityData.get(DATA_HOME_FACING)).<Vec3>map(facing -> Vec3.atCenterOf(home.relative(facing, 25))).orElse(null);
      Vec3 position = this.getTarget() != null ? this.getTarget().position() : this.position();
      return targetPosB == null ? targetPosA : (targetPosA.distanceToSqr(position) > targetPosB.distanceToSqr(position) ? targetPosA : targetPosB);
   }

   @Override
   public void setState(String name) {
      if ("stuck".equals(name)) {
         this.placeMark(1.0F);
      }

      super.setState(name);
   }

   private void getOutOfMe() {
      this.meleeAttack(7.0, 0.0, 1.0);
   }

   private void move(double speed) {
      this.push(this.getLookAngle().x * speed, 0.0, this.getLookAngle().z * speed);
   }

   public boolean isPushable() {
      return false;
   }

   private void jumpSlash(double yOffset) {
      this.lookAt(Anchor.EYES, new Vec3(this.getX(), this.getY() - 10.0, this.getZ()));
      if (this.level() instanceof ServerLevel level) {
         SoulShockwaveEntity shockwave = new SoulShockwaveEntity((EntityType<? extends SoulShockwaveEntity>)BossesRiseEntities.SOUL_SHOCKWAVE.get(), level);
         shockwave.setOwner(this);
         shockwave.setBaseDamage(21.0);
         shockwave.setSilent(true);
         shockwave.setPos(this.getX(), this.getEyeY() - 0.1 + yOffset, this.getZ());
         shockwave.shoot(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 1.0F, 0.0F);
         level.addFreshEntity(shockwave);
      }
   }

   public boolean fireImmune() {
      return true;
   }

   @Override
   protected ResourceLocation getNoHitAdvancement() {
      return BossesRise.prefix("no_hit_underworld_knight");
   }

   @Override
   protected ResourceLocation getKillAdvancement() {
      return BossesRise.prefix("kill_underworld_knight");
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("UndeadCinematic", (Integer)this.entityData.get(DATA_UNDEAD_CINEMATIC));
      compound.putInt("ImmuneStacks", this.getImmuneStacks());
      compound.putInt("ImmuneStackMax", (Integer)this.entityData.get(DATA_IMMUNE_MAX));
      compound.putBoolean("IsStuck", this.isStuck);
      compound.putInt("LightCounter", this.lightCounter);
      compound.putInt("HeavyCounter", this.heavyCounter);
      compound.putBoolean("HpGate75", this.hpGate75);
      compound.putBoolean("HpGate50", this.hpGate50);
      compound.putBoolean("HpGate25", this.hpGate25);
      compound.putBoolean("Transformed", (Boolean)this.entityData.get(DATA_TRANSFORMED));
      compound.putInt("HasDoneIntroAttack", (Integer)this.entityData.get(DATA_INTRO_ATTACK));
      ((Optional)this.entityData.get(DATA_HOME)).ifPresent(pos -> compound.put("HomePos", NbtUtils.writeBlockPos(pos)));
      ((Optional)this.entityData.get(DATA_HOME_FACING)).ifPresent(facing -> compound.putString("HomeFacing", facing.getSerializedName()));
      if (this.attackPattern != null) {
         compound.putInt("AttackPatternSize", this.attackPattern.length());

         for (int i = 0; i < this.attackPattern.length(); i++) {
            compound.putString(Objects.requireNonNull(this.attackPattern.get(i)).name(), "Attack" + i);
         }

         compound.putInt("AttackIndex", this.attackPatternIndex);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("BossPhase")) {
         this.entityData.set(DATA_UNDEAD_CINEMATIC, compound.getInt("UndeadCinematic"));
         if (compound.contains("ImmuneStacks")) {
            this.setImmuneStacks(compound.getInt("ImmuneStacks"));
         }

         if (compound.contains("ImmuneStackMax")) {
            this.entityData.set(DATA_IMMUNE_MAX, compound.getInt("ImmuneStackMax"));
         }

         this.isStuck = compound.getBoolean("IsStuck");
         this.lightCounter = compound.getInt("LightCounter");
         this.heavyCounter = compound.getInt("HeavyCounter");
         this.hpGate75 = compound.getBoolean("HpGate75");
         this.hpGate50 = compound.getBoolean("HpGate50");
         this.hpGate25 = compound.getBoolean("HpGate25");
         this.entityData.set(DATA_TRANSFORMED, compound.getBoolean("Transformed"));
         if (compound.contains("HasDoneIntroAttack")) {
            this.entityData.set(DATA_INTRO_ATTACK, compound.getInt("HasDoneIntroAttack"));
         } else {
            this.entityData.set(DATA_INTRO_ATTACK, 10);
         }

         this.entityData.set(DATA_HOME, NbtUtils.readBlockPos(compound, "HomePos"));
         this.entityData
            .set(DATA_HOME_FACING, Optional.ofNullable(compound.contains("HomeFacing") ? Direction.byName(compound.getString("HomeFacing")) : null));
         if (compound.contains("AttackPatternSize")) {
            int size = compound.getInt("AttackPatternSize");
            AbstractBossEntity.Attack[] attacks = new AbstractBossEntity.Attack[size];

            for (int i = 0; i < size; i++) {
               attacks[i] = new AbstractBossEntity.Attack(compound.getString("Attack" + i));
            }

            this.attackPattern = new AbstractBossEntity.AttackPattern(1, attacks);
            this.attackPatternIndex = compound.getInt("AttackIndex");
         }
      }
   }

   public void onAddedToLevel() {
      super.onAddedToLevel();
      if (((Optional)this.getEntityData().get(DATA_HOME)).isEmpty() && !this.level().isClientSide()) {
         this.getEntityData().set(DATA_HOME, Optional.of(this.blockPosition()));
         this.getEntityData().set(DATA_HOME_FACING, Optional.of(this.getDirection()));
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata) {
      SpawnGroupData groupData = super.finalizeSpawn(world, difficulty, reason, livingdata);
      Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue((Double)ServerConfiguration.KNIGHT_HEALTH.get());
      Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue((Double)ServerConfiguration.KNIGHT_ARMOR.get());
      Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue((Double)ServerConfiguration.KNIGHT_ATK.get());
      Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.3);
      this.setHealth((float)((Double)ServerConfiguration.KNIGHT_HEALTH.get()).doubleValue());
      return groupData;
   }

   public void baseTick() {
      super.baseTick();
      UnderworldKnightOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.MAX_HEALTH, 250.0)
         .add(Attributes.ARMOR, 0.0)
         .add(Attributes.ATTACK_DAMAGE, 15.0)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.STEP_HEIGHT, 1.2)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   @Nonnull
   public AABB getBoundingBoxForCulling() {
      return this.getBoundingBox().inflate(15.0);
   }

   @Override
   public AbstractBossEntity.Attack chooseAttack() {
      if (this.attackPattern == null || this.attackPatternIndex >= this.attackPattern.length()) {
         this.attackPattern = this.getAttackPatternPool().selectRandom();
         this.attackPatternIndex = 0;
      }

      return this.attackPattern.get(this.attackPatternIndex++);
   }

   protected void positionRider(Entity passenger, MoveFunction callback) {
      if (passenger instanceof KnightMarkEntity mark) {
         Vec3 offset = new Vec3(mark.getOffset());
         Vec3 vec3 = this.position().add(offset);
         callback.accept(passenger, vec3.x, vec3.y, vec3.z);
      } else {
         super.positionRider(passenger, callback);
      }
   }

   public void placeMark(float scale) {
      if (this.level() instanceof ServerLevel level) {
         if (this.getFirstPassenger() instanceof KnightMarkEntity mark) {
            mark.discard();
         }

         KnightMarkEntity mark = new KnightMarkEntity((EntityType<?>)BossesRiseEntities.KNIGHT_MARK.get(), this.level());
         double width = this.getBbWidth();
         if (this.random.nextBoolean()) {
            mark.setOffset(
               new Vec3(
                     width * -0.5 + width * this.random.nextDouble(),
                     this.random.nextDouble() + this.random.nextDouble(),
                     width * (this.random.nextBoolean() ? 0.5 : -0.5)
                  )
                  .toVector3f()
            );
         } else {
            mark.setOffset(
               new Vec3(
                     width * (this.random.nextBoolean() ? 0.5 : -0.5),
                     this.random.nextDouble() + this.random.nextDouble(),
                     width * -0.5 + width * this.random.nextDouble()
                  )
                  .toVector3f()
            );
         }

         mark.moveTo(this.position().add(new Vec3(mark.getOffset())));
         mark.setScale(scale);
         mark.setOwnerUUID(this.getUUID());
         mark.startRiding(this);
         level.addFreshEntity(mark);
      }
   }

   @Override
   public void doAttack(@Nullable AbstractBossEntity.Attack attack) {
      AbstractBossEntity.Attack atk = attack;
      if (attack == COMBO_ATTACK) {
         if (!this.isTransformed()) {
            atk = COMBO_ATTACK_1;
         } else {
            atk = Math.random() > 0.5 ? COMBO_ATTACK_1 : COMBO_ATTACK_2;
         }
      } else if (attack == LIGHT_ATTACK) {
         atk = Math.random() > 0.5 ? LIGHT_ATTACK_1 : LIGHT_ATTACK_2;
      } else if (attack == JUMP_ATTACK) {
         if ((Integer)this.entityData.get(AbstractBossEntity.DATA_BOSS_PHASE) == 0) {
            double val = Math.random();
            atk = val < 0.7 ? JUMP_ATTACK_1 : (val < 0.9 ? JUMP_ATTACK_2 : JUMP_ATTACK_3);
         } else {
            atk = Math.random() < 0.6 ? JUMP_ATTACK_2 : JUMP_ATTACK_3;
         }
      }

      super.doAttack(atk);
      if (!this.level().isClientSide()) {
         this.lastAttack = attack;
         if (!this.isInvulnerable()) {
            this.lightCounter = 0;
            this.heavyCounter = 0;
         } else {
            if (attack == HEAVY_ATTACK || attack == LIGHT_HEAVY_ATTACK) {
               this.heavyCounter++;
               if (this.lightCounter >= 3 || this.heavyCounter >= (this.getHealthRatio() > 0.5 ? 3 : 5)) {
                  this.isStuck = true;
               }
            }

            if (attack == LIGHT_ATTACK) {
               this.lightCounter++;
            } else {
               this.lightCounter = 0;
            }
         }
      }
   }

   public AbstractBossEntity.AttackPatternPool getAttackPatternPool() {
      return switch (this.entityData.get(AbstractBossEntity.DATA_BOSS_PHASE)) {
         case 0 -> this.getHealthRatio() > 0.5 ? PHASE_ONE_ABOVE_50 : PHASE_ONE_BELOW_50;
         case 2 -> this.getHealthRatio() > 0.5 ? PHASE_TWO_ABOVE_50 : PHASE_TWO_BELOW_50;
         default -> PHASE_ONE_ABOVE_50;
      };
   }

   private static void addState(AbstractBossEntity.State state) {
      STATES.put(state.name(), state);
   }

   static {
      addState(new AbstractBossEntity.State("idle", 10));
      addState(new AbstractBossEntity.State("dash", 20));
      addState(new AbstractBossEntity.State("intro", 377));
      addState(new AbstractBossEntity.State("resurrect", 440));
      addState(new AbstractBossEntity.State("fake_dead", 85));
      addState(new AbstractBossEntity.State("dead", 155));
      addState(new AbstractBossEntity.State("stuck", 70));
      addState(new AbstractBossEntity.State("stagger", 6));
      addState(new AbstractBossEntity.State("knocked_down", 35));
      addState(new AbstractBossEntity.State("revenge_knocked_down", 700));
      addState(new AbstractBossEntity.State("jump_back", 60));
      addState(new AbstractBossEntity.State("ranged_knocked_down", 108));
      addState(new AbstractBossEntity.State("stuck_to_idle", 55));
      addState(new AbstractBossEntity.State("idle_to_knocked_down", 46));
      addState(new AbstractBossEntity.State("knocked_down_to_idle", 41));
      addState(new AbstractBossEntity.State("revenge_knocked_down_to_idle", 14));
      addState(new AbstractBossEntity.State("intro_attack", 43));
      addState(new AbstractBossEntity.State("light_attack_1", 51));
      addState(new AbstractBossEntity.State("light_attack_2", 36));
      addState(new AbstractBossEntity.State("heavy_attack", 33));
      addState(new AbstractBossEntity.State("light_heavy_attack", 60));
      addState(new AbstractBossEntity.State("combo_attack_1", 158));
      addState(new AbstractBossEntity.State("combo_attack_2", 151));
      addState(new AbstractBossEntity.State("jump_attack_1", 71));
      addState(new AbstractBossEntity.State("jump_attack_2", 94));
      addState(new AbstractBossEntity.State("jump_attack_3", 105));
      addState(new AbstractBossEntity.State("ranged_attack_v_idle", 27));
      addState(new AbstractBossEntity.State("ranged_attack_v_trans", 31));
      addState(new AbstractBossEntity.State("ranged_attack_h_idle", 36));
      addState(new AbstractBossEntity.State("ranged_attack_h_trans", 32));
      addState(new AbstractBossEntity.State("thrust_attack", 80));
      addState(new AbstractBossEntity.State("revenge_attack", 56));
   }
}
