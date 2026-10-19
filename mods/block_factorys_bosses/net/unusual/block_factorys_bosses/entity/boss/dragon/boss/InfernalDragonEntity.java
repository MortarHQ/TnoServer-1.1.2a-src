package net.unusual.block_factorys_bosses.entity.boss.dragon.boss;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.block.BossSpawnerBlock;
import net.unusual.block_factorys_bosses.block.entity.BossSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeProjectileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.structures.DragonTowerStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationProcessor.QueuedAnimation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class InfernalDragonEntity extends AbstractBossEntity {
   private static final Logger LOGGER = LoggerFactory.getLogger(InfernalDragonEntity.class);
   private AbstractBossEntity.State headState;
   private int headTimer = 0;
   public static final EntityDataAccessor<Integer> DATA_TRANSFORM_ANIMTIME = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_FLY_ANIMTIME = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DIVE_COOLDOWN = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DIVE_ANIMTIME = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_FALL_ANIMTIME = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> DATA_HAD_SPAWNER = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Boolean> DATA_IS_CIRCLING = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Float> DATA_CIRCLE_ANGLE = SynchedEntityData.defineId(InfernalDragonEntity.class, EntityDataSerializers.FLOAT);
   private static final Map<String, AbstractBossEntity.State> STATES = new HashMap<>();
   private static final Map<String, AbstractBossEntity.State> HEADSTATES = new HashMap<>();
   public static final RawAnimation WALL_IDLE_ANIM = RawAnimation.begin().thenLoop("animation.dragon.wall_idle");
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.fight_idle");
   public static final RawAnimation FLY_IDLE = RawAnimation.begin().thenLoop("animation.flying_idle");
   public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.walk");
   public static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("animation.flying");
   public static final RawAnimation PHASE_TRANSITION_ANIM = RawAnimation.begin().thenPlayAndHold("animation.phase_transition");
   public static final RawAnimation KNOCKED_DOWN_ANIM = RawAnimation.begin().thenPlayAndHold("animation.dragon.wall_knocked");
   public static final RawAnimation HIT_ANIM = RawAnimation.begin().thenPlayAndHold("animation.hit");
   public static final RawAnimation WALL_FIREBALLS_ANIM = RawAnimation.begin().thenPlayAndHold("animation.dragon.fireballs");
   public static final RawAnimation WALL_FIREBREATHING_ANIM = RawAnimation.begin().thenPlayAndHold("animation.dragon.fireballs");
   public static final RawAnimation FIREBALLS_ANIM = RawAnimation.begin().thenPlayAndHold("animation.fireballs_layer");
   public static final RawAnimation FIRE_BREATHING_ANIM = RawAnimation.begin().thenPlayAndHold("animation.firebreathing_layer");
   public static final RawAnimation TAIL_SWIPE_ANIM = RawAnimation.begin().thenPlayAndHold("animation.tail_swipe");
   public static final RawAnimation CLAW_ATTACK_ANIM = RawAnimation.begin().thenLoop("animation.claw_attack");
   public static final RawAnimation BITE_ATTACK_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bite_attack");
   public static final RawAnimation FLIGHT_DASH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.flight_dash");
   public static final RawAnimation INTRO_ANIM = RawAnimation.begin().thenPlayAndHold("animation.dragon.entrance");
   public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlayAndHold("animation.death");
   private static final Vec3 WALL_POS_OFFSET = new Vec3(10.5, 19.0, -18.5);
   @Nullable
   private BlockPos spawnerPosition;
   public double angleDelta = -1.0;
   public Vec3 wallPosition;
   private Vec3 previousFireBreathTarget;
   private AbstractBossEntity.AttackPatternPool previousPool;
   private AbstractBossEntity.AttackPatternPool currentPool;
   private AbstractBossEntity.AttackPattern currentPattern;
   private int attackCounter;
   private int fireBallCount = 0;
   private static final AbstractBossEntity.Attack CLAW_ATTACK = new AbstractBossEntity.Attack(0, "claw_attack", BossesRiseSounds.DRAGON_CLAW, 67);
   private static final AbstractBossEntity.Attack BITE_ATTACK = new AbstractBossEntity.Attack(1, "bite_attack", BossesRiseSounds.DRAGON_BITE, 48);
   private static final AbstractBossEntity.Attack TAIL_SWIPE_ATTACK = new AbstractBossEntity.Attack(2, "tail_swipe", BossesRiseSounds.DRAGON_TAILSWIPE, 62);
   private static final AbstractBossEntity.Attack FIREBALLS_ATTACK = new AbstractBossEntity.Attack(3, "fireballs_attack", BossesRiseSounds.DRAGON_FIREBALLS, 37);
   private static final AbstractBossEntity.Attack FIRE_BREATHING_ATTACK = new AbstractBossEntity.Attack(
      4, "fire_breathing_attack", BossesRiseSounds.DRAGON_FIRELAYER, 62
   );
   private static final AbstractBossEntity.Attack WALL_FIREBALLS_ATTACK = new AbstractBossEntity.Attack(
      4, "wall_fireballs_attack", BossesRiseSounds.DRAGON_FIREBALLS, 62
   );
   private static final AbstractBossEntity.Attack WALL_FIRE_BREATHING_ATTACK = new AbstractBossEntity.Attack(
      4, "wall_fire_breathing_attack", BossesRiseSounds.DRAGON_FIRELAYER, 62
   );
   private static final AbstractBossEntity.Attack FLIGHT_FIRE_BREATHING_ATTACK = new AbstractBossEntity.Attack(
      4, "flight_fire_breathing_attack", BossesRiseSounds.DRAGON_FIRELAYER, 62
   );
   private static final AbstractBossEntity.Attack FLIGHT_FIREBALLS_ATTACK = new AbstractBossEntity.Attack(
      4, "flight_fireballs_attack", BossesRiseSounds.DRAGON_FIREBALLS, 62
   );
   private static final AbstractBossEntity.Attack FLIGHT_DASH_ATTACK = new AbstractBossEntity.Attack(4, "flight_dash", BossesRiseSounds.DRAGON_WING, 85);
   private static final AbstractBossEntity.Attack FLIGHT_CIRCLE = new AbstractBossEntity.Attack(4, "flight_circle", BossesRiseSounds.DRAGON_WING, 62);
   private static final AbstractBossEntity.Attack FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK = new AbstractBossEntity.Attack(
      4, "flight_fire_breathing_attack", BossesRiseSounds.DRAGON_FIRELAYER, 62
   );
   private static final AbstractBossEntity.Attack FLIGHT_CIRCLE_FIREBALLS_ATTACK = new AbstractBossEntity.Attack(
      4, "flight_fireballs_attack", BossesRiseSounds.DRAGON_FIREBALLS, 62
   );
   private static final AbstractBossEntity.AttackPatternPool FIRST_PHASE_WALL_MODE = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(30, new AbstractBossEntity.Attack[]{WALL_FIRE_BREATHING_ATTACK, WALL_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(30, new AbstractBossEntity.Attack[]{WALL_FIRE_BREATHING_ATTACK, WALL_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(25, new AbstractBossEntity.Attack[]{WALL_FIREBALLS_ATTACK, WALL_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(15, new AbstractBossEntity.Attack[]{WALL_FIREBALLS_ATTACK, WALL_FIREBALLS_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool FIRST_PHASE_NORMAL_MODE = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(30, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(30, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(25, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(15, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, FIREBALLS_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool SECOND_PHASE = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(20, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(15, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, CLAW_ATTACK}),
         new AbstractBossEntity.AttackPattern(15, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{BITE_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool THIRD_PHASE_FLIGHT_MODE = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(
            12, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(
            10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE_FIREBALLS_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            8, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE_FIREBALLS_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            8, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            8,
            new AbstractBossEntity.Attack[]{
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK,
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK
            }
         ),
         new AbstractBossEntity.AttackPattern(
            8,
            new AbstractBossEntity.Attack[]{
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK,
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK
            }
         ),
         new AbstractBossEntity.AttackPattern(
            6,
            new AbstractBossEntity.Attack[]{
               FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE_FIREBALLS_ATTACK
            }
         )
      }
   );
   private static final AbstractBossEntity.AttackPatternPool THIRD_PHASE_GROUND_MODE = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{BITE_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{CLAW_ATTACK, BITE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{CLAW_ATTACK, BITE_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, CLAW_ATTACK}),
         new AbstractBossEntity.AttackPattern(5, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(5, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK, BITE_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool FOURTH_PHASE_FLIGHT = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(16, new AbstractBossEntity.Attack[]{FLIGHT_DASH_ATTACK, FLIGHT_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(14, new AbstractBossEntity.Attack[]{FLIGHT_DASH_ATTACK, FLIGHT_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(14, new AbstractBossEntity.Attack[]{FLIGHT_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{FLIGHT_DASH_ATTACK, FLIGHT_DASH_ATTACK, FLIGHT_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{FLIGHT_FIRE_BREATHING_ATTACK, FLIGHT_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FLIGHT_FIREBALLS_ATTACK, FLIGHT_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{FLIGHT_DASH_ATTACK, FLIGHT_FIRE_BREATHING_ATTACK, FLIGHT_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FLIGHT_DASH_ATTACK})
      }
   );
   private static final AbstractBossEntity.AttackPatternPool FOURTH_PHASE_FLIGHT_CIRCLE = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(
            12, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE_FIREBALLS_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            12, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            12,
            new AbstractBossEntity.Attack[]{
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK,
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK
            }
         ),
         new AbstractBossEntity.AttackPattern(
            12,
            new AbstractBossEntity.Attack[]{
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK,
               FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK,
               FLIGHT_CIRCLE,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK,
               FLIGHT_CIRCLE_FIREBALLS_ATTACK
            }
         ),
         new AbstractBossEntity.AttackPattern(
            10,
            new AbstractBossEntity.Attack[]{
               FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE_FIREBALLS_ATTACK
            }
         ),
         new AbstractBossEntity.AttackPattern(
            10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            10, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(
            4, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK, FLIGHT_CIRCLE_FIREBALLS_ATTACK}
         ),
         new AbstractBossEntity.AttackPattern(
            4, new AbstractBossEntity.Attack[]{FLIGHT_CIRCLE, FLIGHT_CIRCLE_FIREBALLS_ATTACK, FLIGHT_CIRCLE_FIRE_BREATHING_ATTACK}
         )
      }
   );
   private static final AbstractBossEntity.AttackPatternPool FOURTH_PHASE_GROUND = new AbstractBossEntity.AttackPatternPool(
      new AbstractBossEntity.AttackPattern[]{
         new AbstractBossEntity.AttackPattern(14, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(14, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(12, new AbstractBossEntity.Attack[]{CLAW_ATTACK, BITE_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK, BITE_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{TAIL_SWIPE_ATTACK, BITE_ATTACK}),
         new AbstractBossEntity.AttackPattern(10, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{FIREBALLS_ATTACK, CLAW_ATTACK}),
         new AbstractBossEntity.AttackPattern(8, new AbstractBossEntity.Attack[]{CLAW_ATTACK, TAIL_SWIPE_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{CLAW_ATTACK, CLAW_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK}),
         new AbstractBossEntity.AttackPattern(6, new AbstractBossEntity.Attack[]{BITE_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK})
      }
   );
   private static final double CIRCLE_RADIUS = 9.0;
   private static final double CIRCLE_HEIGHT = 20.0;
   private static final double CIRCLE_SPEED = 0.08;
   private Vec3 lastCircleCenter = null;

   public InfernalDragonEntity(EntityType<InfernalDragonEntity> type, Level world) {
      super(type, world);
      this.xpReward = 10000;
      this.setInvisible(false);
   }

   @Override
   protected ServerBossEvent createBossEvent() {
      return new ServerBossEvent(this.getDisplayName(), BossBarColor.RED, BossBarOverlay.NOTCHED_6);
   }

   @Override
   protected int getBossType() {
      return 4;
   }

   public boolean isTransformed() {
      return (Integer)this.entityData.get(DATA_BOSS_PHASE) > 4;
   }

   public boolean isCinematic() {
      return this.isIntro() || this.isTransforming() || this.isDeath() || this.isKnockingDown();
   }

   public boolean isIntro() {
      return this.checkCurrentAnim("animation.dragon.entrance", "intro");
   }

   public boolean isKnockingDown() {
      return this.checkCurrentAnim("animation.dragon.wall_knocked", "knocked_down");
   }

   public boolean isTransforming() {
      return this.checkCurrentAnim("animation.phase_transition", "phase_transition");
   }

   public boolean isDeath() {
      return this.checkCurrentAnim("animation.death", "dead");
   }

   public boolean isWalled() {
      return (Integer)this.entityData.get(DATA_BOSS_PHASE) == 0 && (Boolean)this.entityData.get(DATA_HAD_SPAWNER);
   }

   private boolean checkCurrentAnim(String animName, String stateName) {
      if (this.level().isClientSide()) {
         AnimationController<?> controller = this.getController();
         if (controller == null) {
            return false;
         }

         QueuedAnimation anim = controller.getCurrentAnimation();
         return anim != null && anim.animation().name().equals(animName);
      } else {
         return this.isState(stateName);
      }
   }

   public boolean isFlying() {
      return !this.onGround() && this.isNoGravity() || (Boolean)this.entityData.get(DATA_IS_CIRCLING);
   }

   public boolean canFreeze() {
      return false;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new CinematicAnimationController(this, "main_controller", 3, state -> {
               if ((Integer)this.entityData.get(DATA_BOSS_PHASE) == 0 && (Boolean)this.entityData.get(DATA_HAD_SPAWNER)) {
                  return state.setAndContinue(WALL_IDLE_ANIM);
               } else if (state.isMoving()) {
                  return this.isFlying() ? state.setAndContinue(FLY_ANIM) : state.setAndContinue(WALK_ANIM);
               } else {
                  return this.isFlying() ? state.setAndContinue(FLY_IDLE) : state.setAndContinue(IDLE_ANIM);
               }
            })
            .triggerableAnim("intro", INTRO_ANIM)
            .triggerableAnim("phase_transition", PHASE_TRANSITION_ANIM)
            .triggerableAnim("dead", DEATH_ANIM)
            .triggerableAnim("hit", HIT_ANIM)
            .triggerableAnim("claw_attack", CLAW_ATTACK_ANIM)
            .triggerableAnim("bite_attack", BITE_ATTACK_ANIM)
            .triggerableAnim("tail_swipe", TAIL_SWIPE_ANIM)
            .triggerableAnim("knocked_down", KNOCKED_DOWN_ANIM)
            .triggerableAnim("flight_dash", FLIGHT_DASH_ANIM)
      );
      controllers.add(
         new AnimationController(this, "head_controller", 5, state -> PlayState.CONTINUE)
            .triggerableAnim("fire_breathing", FIRE_BREATHING_ANIM)
            .triggerableAnim("fireballs", FIREBALLS_ANIM)
      );
   }

   @Override
   public Map<String, AbstractBossEntity.State> getStates() {
      return STATES;
   }

   @Override
   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_TRANSFORM_ANIMTIME, 200);
      builder.define(DATA_FLY_ANIMTIME, 55);
      builder.define(DATA_DIVE_COOLDOWN, 0);
      builder.define(DATA_DIVE_ANIMTIME, 0);
      builder.define(DATA_FALL_ANIMTIME, 0);
      builder.define(DATA_HAD_SPAWNER, false);
      builder.define(DATA_CIRCLE_ANGLE, 0.0F);
      builder.define(DATA_IS_CIRCLING, false);
   }

   @Override
   public void onBossSpawnerSpawn(BossSpawnerBlockEntity spawner) {
      super.onBossSpawnerSpawn(spawner);
      this.spawnerPosition = spawner.getBlockPos();

      this.angleDelta = switch (((Direction)spawner.getBlockState().getValue(BossSpawnerBlock.FACING)).getOpposite()) {
         case EAST -> 0.0;
         case WEST -> -180.0;
         case SOUTH -> 90.0;
         default -> -90.0;
      };
      this.figureOutWallPosition();
      if (this.wallPosition != null) {
         float rot = (float)this.angleDelta;
         this.setYRot(rot);
         this.yRotO = rot;
         this.setYBodyRot(rot);
         this.yBodyRotO = rot;
         this.setYHeadRot(rot);
         this.yHeadRotO = rot;
         this.setCinematicBlackScreen(true);
         this.onCinematicStarted(230);
         this.closeOffExit();
         this.playSound((SoundEvent)BossesRiseSounds.DRAGON_SPAWN.value());
         this.entityData.set(DATA_HAD_SPAWNER, true);
         this.entityData.set(DATA_BOSS_PHASE, -1);
         Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue((Double)ServerConfiguration.DRAGON_HEALTH.get());
         this.setHealth(((Double)ServerConfiguration.DRAGON_HEALTH.get()).floatValue());
         this.setState("intro");
      }
   }

   private void figureOutWallPosition() {
      if (this.spawnerPosition != null && this.angleDelta != -1.0) {
         Vec3 offset = switch (Direction.fromYRot(this.angleDelta).getOpposite()) {
            case EAST -> new Vec3(-WALL_POS_OFFSET.z(), WALL_POS_OFFSET.y(), WALL_POS_OFFSET.x());
            case WEST -> new Vec3(WALL_POS_OFFSET.z(), WALL_POS_OFFSET.y(), -WALL_POS_OFFSET.x());
            case SOUTH -> new Vec3(-WALL_POS_OFFSET.x(), WALL_POS_OFFSET.y(), -WALL_POS_OFFSET.z());
            default -> WALL_POS_OFFSET;
         };
         this.wallPosition = this.spawnerPosition.getCenter().add(offset);
      }
   }

   private void closeOffExit() {
      BlockPos originPos = new BlockPos(this.getBlockX(), this.getBlockY() - 1, this.getBlockZ());
      int dstFromCenter = DragonTowerStructure.BACKWARD_MARKER_DISTANCE_FROM_SPAWNER;
      Level level = this.level();
      MutableBlockPos mutable = new MutableBlockPos();
      mutable.set(originPos.getX() + dstFromCenter, originPos.getY(), originPos.getZ());
      if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
         this.applyExitCloseBlock(mutable.immutable(), false);
      } else {
         mutable.set(originPos.getX() - dstFromCenter, originPos.getY(), originPos.getZ());
         if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
            this.applyExitCloseBlock(mutable.immutable(), false);
         } else {
            mutable.set(originPos.getX(), originPos.getY(), originPos.getZ() + dstFromCenter);
            if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
               this.applyExitCloseBlock(mutable.immutable(), true);
            } else {
               mutable.set(originPos.getX(), originPos.getY(), originPos.getZ() - dstFromCenter);
               if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
                  this.applyExitCloseBlock(mutable.immutable(), true);
               }
            }
         }
      }
   }

   private void applyExitCloseBlock(BlockPos centerPos, boolean onX) {
      if (onX) {
         for (int x = centerPos.getX() - 1; x <= centerPos.getX() + 1; x++) {
            for (int y = centerPos.getY(); y <= centerPos.getY() + 2; y++) {
               this.level().setBlockAndUpdate(new BlockPos(x, y, centerPos.getZ()), Blocks.DEEPSLATE.defaultBlockState());
            }
         }
      } else {
         for (int z = centerPos.getZ() - 1; z <= centerPos.getZ() + 1; z++) {
            for (int y = centerPos.getY(); y <= centerPos.getY() + 2; y++) {
               this.level().setBlockAndUpdate(new BlockPos(centerPos.getX(), y, z), Blocks.DEEPSLATE.defaultBlockState());
            }
         }
      }
   }

   private void figureOutStructureDirection() {
      BlockPos originPos = new BlockPos(this.getBlockX(), this.getBlockY() - 1, this.getBlockZ());
      int dstFromCenter = DragonTowerStructure.BACKWARD_MARKER_DISTANCE_FROM_SPAWNER;
      Level level = this.level();
      MutableBlockPos mutable = new MutableBlockPos();
      mutable.set(originPos.getX() + dstFromCenter, originPos.getY(), originPos.getZ());
      if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
         this.angleDelta = 0.0;
      } else {
         mutable.set(originPos.getX() - dstFromCenter, originPos.getY(), originPos.getZ());
         if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
            this.angleDelta = -180.0;
         } else {
            mutable.set(originPos.getX(), originPos.getY(), originPos.getZ() + dstFromCenter);
            if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
               this.angleDelta = 90.0;
            } else {
               mutable.set(originPos.getX(), originPos.getY(), originPos.getZ() - dstFromCenter);
               if (level.getBlockState(mutable).is(Blocks.DEEPSLATE)) {
                  this.angleDelta = -90.0;
               }
            }
         }
      }
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new InfernalDragonEntity.CircleFlightGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true, true) {
         public boolean canUse() {
            return InfernalDragonEntity.this.isCinematic() ? false : super.canUse();
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse();
         }
      });
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F, false) {});
      this.goalSelector.addGoal(4, new InfernalDragonEntity.GroundFollowGoal(this, 0.4F, 8.0));
      this.goalSelector
         .addGoal(
            5,
            new InfernalDragonEntity.DirectMoveToTargetGoal(this, 0.4) {
               @Override
               public boolean canUse() {
                  return super.canUse()
                     && InfernalDragonEntity.this.isFlying()
                     && !(Boolean)InfernalDragonEntity.this.entityData.get(InfernalDragonEntity.DATA_IS_CIRCLING)
                     && !InfernalDragonEntity.this.isCinematic();
               }

               public boolean canContinueToUse() {
                  return this.canUse();
               }
            }
         );
      this.targetSelector.addGoal(6, new HurtByTargetGoal(this) {
         public boolean canUse() {
            return super.canUse() && InfernalDragonEntity.this.onGround() && !InfernalDragonEntity.this.isCinematic();
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse();
         }
      });
      this.goalSelector
         .addGoal(
            7,
            new RandomStrollGoal(this, 0.5, 20) {
               public boolean canUse() {
                  if ((Integer)InfernalDragonEntity.this.entityData.get(AbstractBossEntity.DATA_BOSS_PHASE) == 0
                     && (Boolean)InfernalDragonEntity.this.entityData.get(InfernalDragonEntity.DATA_HAD_SPAWNER)) {
                     return false;
                  } else if (InfernalDragonEntity.this.isCinematic()) {
                     return false;
                  } else if (InfernalDragonEntity.this.isFlying() && !(Boolean)InfernalDragonEntity.this.entityData.get(InfernalDragonEntity.DATA_IS_CIRCLING)) {
                     return true;
                  } else {
                     return InfernalDragonEntity.this.getTarget() != null ? false : super.canUse();
                  }
               }

               public boolean canContinueToUse() {
                  return this.canUse();
               }
            }
         );
   }

   public void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound((SoundEvent)BossesRiseSounds.DRAGON_FOOTSTEP.value(), 0.15F, 1.0F);
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return (SoundEvent)BossesRiseSounds.DRAGON_HIT.value();
   }

   public boolean causeFallDamage(float l, float d, DamageSource source) {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         if (this.isIntro()) {
            this.figureOutStructureDirection();
            this.figureOutWallPosition();
         }
      } else {
         this.setPlayingMusic((Integer)this.entityData.get(DATA_BOSS_PHASE) != -1 && !this.isDeath());
         this.headTimer++;
         if (this.isHeadTimerDone()) {
            this.setHeadState(null);
         }

         if (this.tickCount % 20 == 0) {
            LOGGER.debug(
               "Dragon {}: Phase {}, Circling {}, State {}, Cooldown {}, IsNoGravity {}, OnGround {}",
               new Object[]{
                  this.getId(),
                  this.entityData.get(DATA_BOSS_PHASE),
                  this.entityData.get(DATA_IS_CIRCLING),
                  this.getState().name(),
                  this.entityData.get(DATA_ATTACK_COOLDOWN),
                  this.isNoGravity(),
                  this.onGround()
               }
            );
         }

         this.manageState();
         this.handleAttackDamage();
         if (!this.isTimerDone()) {
            if (this.isCinematic()) {
               this.setSpeed(0.0F);
            }
         } else {
            switch (this.getState().name()) {
               case "idle":
                  break;
               case "dead":
                  this.hurt(new DamageSource(this.level().holderOrThrow(DamageTypes.PLAYER_ATTACK), this.getSourceEntity()), 999999.0F);
                  break;
               default:
                  this.setState("idle");
            }
         }
      }
   }

   private void manageState() {
      if (!(this.getHealth() <= 0.1) && !this.isDeath()) {
         switch (this.entityData.get(DATA_BOSS_PHASE)) {
            case -1:
               this.setSpeed(0.0F);
               this.setPos(this.wallPosition);
               super.yBodyRotO = (float)this.angleDelta;
               this.setYBodyRot((float)this.angleDelta);
               this.setNoGravity(true);
               if (this.getTimer() == 10) {
                  this.setCinematicBlackScreen(false);
               }

               if (this.getTimer() == 215) {
                  ((ServerLevel)this.level())
                     .sendParticles((SimpleParticleType)BossesRiseParticleTypes.FIRE_AOE.get(), this.getX(), this.getY(), this.getZ(), 10, 0.0, 0.0, 0.0, 1.0);
               }

               if (this.getTimer() == 225) {
                  this.setCinematicBlackScreen(true);
                  return;
               }

               if (this.isTimerDone()) {
                  this.setCinematicBlackScreen(false);
                  this.entityData.set(DATA_BOSS_PHASE, 0);
                  this.setState("idle");
               }
               break;
            case 0:
               if ((Boolean)this.entityData.get(DATA_HAD_SPAWNER) && this.wallPosition != null) {
                  this.setSpeed(0.0F);
                  this.setPos(this.wallPosition);
                  super.yBodyRotO = (float)this.angleDelta;
                  this.setYBodyRot((float)this.angleDelta);
               }

               if (this.getHealthRatio() <= 0.85) {
                  if ((Boolean)this.entityData.get(DATA_HAD_SPAWNER)) {
                     this.setState("knocked_down");
                     this.setPos(this.spawnerPosition.getCenter());
                     this.playSound((SoundEvent)BossesRiseSounds.DRAGON_ROAR.value(), 1.0F, 1.0F);
                     this.onCinematicStarted(90);
                  }

                  this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 1);
                  return;
               }

               if (this.getHealthRatio() <= 0.5) {
                  this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 3);
                  this.resetAttacks();
                  return;
               }

               if (this.getHealthRatio() <= 0.25) {
                  this.performPhaseTransition();
                  this.resetAttacks();
                  return;
               }

               this.doAttack(this.chooseAttack());
               break;
            case 1:
               this.resetAttacks();
               if (!(Boolean)this.entityData.get(DATA_HAD_SPAWNER)) {
                  this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 2);
                  return;
               }

               this.setNoGravity(false);
               this.setSpeed(0.0F);
               if (!this.isTimerDone()) {
                  if (this.getTimer() == 35) {
                     this.playSound((SoundEvent)BossesRiseSounds.DRAGON_FOOTSTEP.value(), 1.0F, 1.0F);
                  }

                  return;
               }

               this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 2);
               break;
            case 2:
               if (this.getHealthRatio() <= 0.5) {
                  this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 3);
                  this.resetAttacks();
                  return;
               }

               if (this.getHealthRatio() <= 0.25) {
                  this.performPhaseTransition();
                  this.resetAttacks();
                  return;
               }

               this.setNoGravity(false);
               this.lookAtTarget();
               this.doAttack(this.chooseAttack());
               break;
            case 3:
               if (this.getHealthRatio() <= 0.25) {
                  this.entityData.set(DATA_IS_CIRCLING, false);
                  this.setNoGravity(false);
                  if (!this.onGround()) {
                     return;
                  }

                  this.performPhaseTransition();
                  return;
               }

               if (this.onGround()) {
                  this.lookAtTarget();
               }

               this.doAttack(this.chooseAttack());
               break;
            case 4:
               this.resetAttacks();
               if (!this.isTimerDone()) {
                  if (this.getTimer() != 227) {
                     return;
                  }

                  this.setCinematicBlackScreen(true);
                  return;
               }

               this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 5);
               break;
            case 5:
               if (this.getTimer() > 5 && this.getCinematicBlackScreen()) {
                  this.setCinematicBlackScreen(false);
               }

               if (this.isTransforming()) {
                  this.setState("idle");
               }

               if (this.isInvisible()) {
                  this.setInvisible(false);
               }

               if (this.onGround()) {
                  this.lookAtTarget();
               }

               this.doAttack(this.chooseAttack());
         }
      }
   }

   private void performPhaseTransition() {
      this.setState("phase_transition");
      this.entityData.set(AbstractBossEntity.DATA_BOSS_PHASE, 4);
      this.playSound((SoundEvent)BossesRiseSounds.DRAGON_TRANSITION.value(), 1.0F, 1.0F);
      this.onCinematicStarted(225);
   }

   private void onCinematicStarted(int ticks) {
      this.forEachNearbyPlayer(128.0, player -> {
         player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ticks, 4, false, false, false, null));
         player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ticks, 255, false, false, false, null));
      });
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      boolean isWalled = (Integer)this.entityData.get(DATA_BOSS_PHASE) == 0 && (Boolean)this.entityData.get(DATA_HAD_SPAWNER) && this.wallPosition != null;
      if (isWalled || Math.abs((Integer)this.entityData.get(DATA_BOSS_PHASE)) == 1) {
         this.setYBodyRot((float)this.angleDelta);
         this.yBodyRotO = (float)this.angleDelta;
         this.setYRot((float)this.angleDelta);
         this.yRotO = (float)this.angleDelta;
         this.setYHeadRot((float)this.angleDelta);
         this.yHeadRotO = (float)this.angleDelta;
      }

      if (this.isDeath() || this.isTransforming()) {
         this.setSpeed(0.0F);
         this.setYBodyRot(this.yBodyRotO);
         this.setYRot(this.yRotO);
         this.setYHeadRot(this.yHeadRotO);
      }
   }

   @Override
   public boolean shouldCancelDeath() {
      if (this.isState("dead")) {
         return !this.isTimerDone();
      }

      this.playSound((SoundEvent)BossesRiseSounds.DRAGON_DEATH.value(), 8.0F, 1.0F);
      this.setState("dead");
      this.setNoGravity(false);
      this.onCinematicStarted(180);
      return true;
   }

   public boolean hurt(DamageSource damagesource, float amount) {
      if (damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return super.hurt(damagesource, amount);
      }

      SynchedEntityData entityData = this.getEntityData();
      int phase = (Integer)entityData.get(DATA_BOSS_PHASE);
      if (phase == -1 || phase == 1 || phase == 4) {
         return false;
      }

      if (!damagesource.is(DamageTypes.IN_FIRE)
         && !damagesource.is(DamageTypes.FALL)
         && !damagesource.is(DamageTypes.CACTUS)
         && (Integer)entityData.get(DATA_HIT_ANIMTIME) <= 0
         && (Integer)entityData.get(DATA_SPAWN_ANIMTIME) <= 0) {
         if ((Integer)entityData.get(DATA_BOSS_PHASE) == 0 && damagesource.getEntity() instanceof Projectile) {
            amount *= 2.0F;
         }

         if (damagesource.getEntity() instanceof IceSpikeProjectileEntity) {
            amount /= 2.0F;
         }

         this.playSound(this.getHurtSound(damagesource), 1.0F, 1.0F);
         return super.hurt(damagesource, amount);
      } else {
         return false;
      }
   }

   @Override
   protected ResourceLocation getNoHitAdvancement() {
      return BossesRise.prefix("no_hit_dragon");
   }

   @Override
   protected ResourceLocation getKillAdvancement() {
      return BossesRise.prefix("kill_dragon");
   }

   public boolean fireImmune() {
      return true;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("FlyAnimtime", (Integer)this.entityData.get(DATA_FLY_ANIMTIME));
      compound.putInt("DiveCooldown", (Integer)this.entityData.get(DATA_DIVE_COOLDOWN));
      compound.putInt("DiveAnimtime", (Integer)this.entityData.get(DATA_DIVE_ANIMTIME));
      compound.putInt("FallAnimtime", (Integer)this.entityData.get(DATA_FALL_ANIMTIME));
      compound.putBoolean("HadSpawner", (Boolean)this.entityData.get(DATA_HAD_SPAWNER));
      if (this.spawnerPosition != null) {
         compound.putInt("SpawnerX", this.spawnerPosition.getX());
         compound.putInt("SpawnerY", this.spawnerPosition.getY());
         compound.putInt("SpawnerZ", this.spawnerPosition.getZ());
      }

      if (this.angleDelta != -1.0) {
         compound.putFloat("AngleDelta", (float)this.angleDelta);
      }

      if (this.lastCircleCenter != null) {
         compound.putDouble("CircleCenterX", this.lastCircleCenter.x);
         compound.putDouble("CircleCenterY", this.lastCircleCenter.y);
         compound.putDouble("CircleCenterZ", this.lastCircleCenter.z);
      }

      this.saveCurrentPools(compound);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("BossPhase")) {
         this.entityData.set(DATA_FLY_ANIMTIME, compound.getInt("FlyAnimtime"));
         this.entityData.set(DATA_DIVE_COOLDOWN, compound.getInt("DiveCooldown"));
         this.entityData.set(DATA_DIVE_ANIMTIME, compound.getInt("DiveAnimtime"));
         this.entityData.set(DATA_FALL_ANIMTIME, compound.getInt("FallAnimtime"));
         this.entityData.set(DATA_HAD_SPAWNER, compound.getBoolean("HadSpawner"));
         this.readCurrentPools(compound);
         if (compound.contains("CircleCenterX")) {
            this.lastCircleCenter = new Vec3(compound.getDouble("CircleCenterX"), compound.getDouble("CircleCenterY"), compound.getDouble("CircleCenterZ"));
         }

         if ((Boolean)this.entityData.get(DATA_HAD_SPAWNER)) {
            this.angleDelta = compound.getFloat("AngleDelta");
            this.spawnerPosition = new BlockPos(compound.getInt("SpawnerX"), compound.getInt("SpawnerY"), compound.getInt("SpawnerZ"));
            this.figureOutWallPosition();
         }
      }
   }

   private void saveCurrentPools(CompoundTag compound) {
      if (this.currentPool == FIRST_PHASE_NORMAL_MODE) {
         compound.putString("CurrentPool", "FIRST_PHASE_NORMAL_MODE");
      } else if (this.currentPool == FIRST_PHASE_WALL_MODE) {
         compound.putString("CurrentPool", "FIRST_PHASE_WALL_MODE");
      } else if (this.currentPool == SECOND_PHASE) {
         compound.putString("CurrentPool", "SECOND_PHASE");
      } else if (this.currentPool == THIRD_PHASE_FLIGHT_MODE) {
         compound.putString("CurrentPool", "THIRD_PHASE_FLIGHT_MODE");
      } else if (this.currentPool == THIRD_PHASE_GROUND_MODE) {
         compound.putString("CurrentPool", "THIRD_PHASE_GROUND_MODE");
      } else if (this.currentPool == FOURTH_PHASE_FLIGHT) {
         compound.putString("CurrentPool", "FOURTH_PHASE_FLIGHT");
      } else if (this.currentPool == FOURTH_PHASE_FLIGHT_CIRCLE) {
         compound.putString("CurrentPool", "FOURTH_PHASE_FLIGHT_CIRCLE");
      } else if (this.currentPool == FOURTH_PHASE_GROUND) {
         compound.putString("CurrentPool", "FOURTH_PHASE_GROUND");
      }
   }

   private void readCurrentPools(CompoundTag compound) {
      String pool = compound.getString("CurrentPool");

      this.currentPool = switch (pool) {
         case "FIRST_PHASE_NORMAL_MODE" -> FIRST_PHASE_NORMAL_MODE;
         case "FIRST_PHASE_WALL_MODE" -> FIRST_PHASE_WALL_MODE;
         case "SECOND_PHASE" -> SECOND_PHASE;
         case "THIRD_PHASE_FLIGHT_MODE" -> THIRD_PHASE_FLIGHT_MODE;
         case "THIRD_PHASE_GROUND_MODE" -> THIRD_PHASE_GROUND_MODE;
         case "FOURTH_PHASE_FLIGHT" -> FOURTH_PHASE_FLIGHT;
         case "FOURTH_PHASE_FLIGHT_CIRCLE" -> FOURTH_PHASE_FLIGHT_CIRCLE;
         case "FOURTH_PHASE_GROUND" -> FOURTH_PHASE_GROUND;
         default -> this.currentPool;
      };
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata) {
      SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)ServerConfiguration.DRAGON_HEALTH.get());
      this.getAttribute(Attributes.ARMOR).setBaseValue((Double)ServerConfiguration.DRAGON_ARMOR.get());
      this.setHealth((float)((Double)ServerConfiguration.DRAGON_HEALTH.get()).doubleValue());
      return retval;
   }

   public void baseTick() {
      super.baseTick();
      if (!this.level().isClientSide()) {
         this.entityData.set(AbstractBossEntity.DATA_BATTLE_TIME, (Integer)this.entityData.get(AbstractBossEntity.DATA_BATTLE_TIME) + 1);
         if (this.isTargetNear(256.0)) {
            SynchedEntityData entityData = this.getEntityData();
            entityData.set(DATA_ATTACK_COOLDOWN, (Integer)entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN) + 1);
         }
      }
   }

   public boolean isPushable() {
      return false;
   }

   @Nonnull
   public AABB getBoundingBoxForCulling() {
      return AABB.ofSize(this.position(), 30.0, 30.0, 30.0);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.MAX_HEALTH, 250.0)
         .add(Attributes.ARMOR, 15.0)
         .add(Attributes.ATTACK_DAMAGE, 14.0)
         .add(Attributes.FOLLOW_RANGE, 128.0)
         .add(Attributes.STEP_HEIGHT, 1.1)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.ATTACK_KNOCKBACK, 0.6)
         .add(Attributes.FLYING_SPEED, 1.6);
   }

   @Nullable
   @Override
   public AbstractBossEntity.Attack chooseAttack() {
      if (this.attack != null && this.getState().name().equals(this.attack.name())) {
         return null;
      }

      int cooldown = (Integer)this.entityData.get(DATA_ATTACK_COOLDOWN);
      if (cooldown < (this.attack == null ? 30 : 20)) {
         return null;
      }

      if (this.currentPool != null
         && this.currentPool.attackPatterns() != null
         && this.onGround()
         && !this.isWalled()
         && (
            this.currentPool == SECOND_PHASE
               || this.currentPool != null
                  && this.attackCounter != this.currentPool.attackPatterns().length - 1
                  && (this.currentPool == THIRD_PHASE_GROUND_MODE || this.currentPool == FOURTH_PHASE_GROUND)
         )) {
         if (this.getTarget() != null) {
            this.log(this.getTarget().distanceTo(this));
         }

         if (this.getTarget() == null || this.getTarget().distanceTo(this) > 5.0F) {
            return null;
         }
      }

      this.entityData.set(DATA_ATTACK_COOLDOWN, 0);
      int bossPhase = (Integer)this.entityData.get(DATA_BOSS_PHASE);
      boolean wallMode = (Boolean)this.entityData.get(DATA_HAD_SPAWNER);

      return switch (bossPhase) {
         case 0 -> {
            if (wallMode && this.currentPool == null) {
               this.currentPool = FIRST_PHASE_WALL_MODE;
               yield WALL_FIRE_BREATHING_ATTACK;
            } else {
               if (this.currentPattern == null || this.attackCounter + 1 >= this.currentPattern.length()) {
                  if (this.currentPool == null) {
                     this.currentPool = wallMode ? FIRST_PHASE_WALL_MODE : FIRST_PHASE_NORMAL_MODE;
                  }

                  this.currentPattern = this.currentPool.selectRandom();
                  this.attackCounter = -1;
               }

               this.attackCounter++;
               yield this.currentPattern.attacks()[this.attackCounter];
            }
         }
         default -> null;
         case 2 -> {
            if (this.currentPool == null) {
               this.currentPool = SECOND_PHASE;
               this.currentPattern = new AbstractBossEntity.AttackPattern(
                  100, new AbstractBossEntity.Attack[]{FIRE_BREATHING_ATTACK, CLAW_ATTACK, CLAW_ATTACK, TAIL_SWIPE_ATTACK}
               );
            }

            if (this.currentPattern == null || this.attackCounter + 1 >= this.currentPattern.length()) {
               this.currentPattern = this.currentPool.selectRandom();
               this.attackCounter = -1;
            }

            this.attackCounter++;
            yield this.currentPattern.attacks()[this.attackCounter];
         }
         case 3 -> {
            if (this.currentPool == null) {
               this.currentPool = THIRD_PHASE_FLIGHT_MODE;
               this.entityData.set(DATA_IS_CIRCLING, true);
            }

            if (this.currentPattern == null) {
               this.currentPattern = this.currentPool.selectRandom();
            }

            if (this.attackCounter + 1 >= this.currentPattern.length()) {
               boolean shouldChangePool = this.random.nextDouble() < 0.2;
               if (shouldChangePool) {
                  this.currentPool = this.currentPool == THIRD_PHASE_FLIGHT_MODE ? THIRD_PHASE_GROUND_MODE : THIRD_PHASE_FLIGHT_MODE;
                  this.entityData.set(DATA_IS_CIRCLING, !(Boolean)this.entityData.get(DATA_IS_CIRCLING));
               }

               this.currentPattern = this.currentPool.selectRandom();
               this.attackCounter = -1;
            }

            this.attackCounter++;
            yield this.currentPattern.attacks()[this.attackCounter];
         }
         case 5 -> {
            if (this.currentPool == null) {
               this.currentPool = FOURTH_PHASE_FLIGHT;
               this.log("Current pool is FLIGHT");
               this.setNoGravity(true);
               this.push(0.0, 0.5, 0.0);

               for (int i = 0; i < 10; i++) {
                  ((ServerLevel)this.level())
                     .sendParticles(
                        (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        5,
                        Math.random() - 0.5,
                        0.0,
                        Math.random() - 0.5,
                        1.0
                     );
               }
            }

            if (this.currentPattern == null) {
               this.currentPattern = this.currentPool.selectRandom();
            }

            if (this.attackCounter + 1 >= this.currentPattern.length()) {
               boolean shouldChangePool = this.random.nextDouble() < 0.33;
               if (shouldChangePool) {
                  if (this.currentPool == FOURTH_PHASE_FLIGHT) {
                     this.currentPool = FOURTH_PHASE_GROUND;
                     this.previousPool = FOURTH_PHASE_FLIGHT;
                     this.attackCounter = -2;
                     this.log("Current pool is GROUND");
                     this.push(0.0, -1.0, 0.0);

                     for (int i = 0; i < 10; i++) {
                        ((ServerLevel)this.level())
                           .sendParticles(
                              (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                              this.getX(),
                              this.getY(),
                              this.getZ(),
                              5,
                              Math.random() - 0.5,
                              0.0,
                              Math.random() - 0.5,
                              1.0
                           );
                     }

                     this.setNoGravity(false);
                  }

                  if (this.currentPool == FOURTH_PHASE_GROUND && this.attackCounter != -2) {
                     this.currentPool = this.previousPool == FOURTH_PHASE_FLIGHT_CIRCLE ? FOURTH_PHASE_FLIGHT : FOURTH_PHASE_FLIGHT_CIRCLE;
                     this.previousPool = FOURTH_PHASE_GROUND;
                     this.log("Current pool is " + (this.currentPool == FOURTH_PHASE_FLIGHT_CIRCLE ? "FLIGHT_CIRCLE" : "FLIGHT"));
                     if (this.currentPool == FOURTH_PHASE_FLIGHT_CIRCLE) {
                        this.entityData.set(DATA_IS_CIRCLING, true);
                        this.setNoGravity(true);
                     }

                     if (this.currentPool == FOURTH_PHASE_FLIGHT) {
                        this.setNoGravity(true);
                        this.push(0.0, 1.0, 0.0);

                        for (int i = 0; i < 10; i++) {
                           ((ServerLevel)this.level())
                              .sendParticles(
                                 (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                                 this.getX(),
                                 this.getY(),
                                 this.getZ(),
                                 5,
                                 Math.random() - 0.5,
                                 0.0,
                                 Math.random() - 0.5,
                                 1.0
                              );
                        }
                     }

                     this.attackCounter = -2;
                  }

                  if (this.currentPool == FOURTH_PHASE_FLIGHT_CIRCLE && this.attackCounter != -2) {
                     this.currentPool = FOURTH_PHASE_GROUND;
                     this.setNoGravity(false);
                     this.previousPool = FOURTH_PHASE_FLIGHT_CIRCLE;
                     this.entityData.set(DATA_IS_CIRCLING, false);

                     for (int i = 0; i < 10; i++) {
                        ((ServerLevel)this.level())
                           .sendParticles(
                              (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                              this.getX(),
                              this.getY(),
                              this.getZ(),
                              5,
                              Math.random() - 0.5,
                              0.0,
                              Math.random() - 0.5,
                              1.0
                           );
                     }

                     this.attackCounter = -2;
                     this.log("Current pool is GROUND");
                  }
               }

               this.log("reselected pattern");
               this.currentPattern = this.currentPool.selectRandom();
               this.attackCounter = -1;
            }

            this.attackCounter++;
            yield this.currentPattern.attacks()[this.attackCounter];
         }
      };
   }

   @Override
   public void doAttack(@Nullable AbstractBossEntity.Attack attack) {
      if (attack != null) {
         super.doAttack(attack);
         String matchingHeadState = HEADSTATES.keySet().stream().filter(s -> attack.name().contains(s)).findFirst().orElse(null);
         if (matchingHeadState == null) {
            this.setHeadState("idle");
         } else {
            this.setHeadState(matchingHeadState);
         }
      }
   }

   @Override
   public void setState(String state) {
      if (state.contains("flight")) {
         state = "idle";
      }

      if (state.contains("fire")) {
         state = "idle";
      }

      if (state.contains("wall")) {
         state = "idle";
      }

      super.setState(state);
   }

   private void resetAttacks() {
      this.attackCounter = -1;
      this.currentPattern = null;
      this.currentPool = null;
   }

   private void handleAttackDamage() {
      if (this.attack != null) {
         String attackName = this.attack.name();
         int timer = this.getTimer();
         switch (attackName) {
            case "claw_attack":
               if (timer == 43) {
                  this.dealMeleeAttackDamage(6.0, 0.834F, 120.0);

                  for (int i = 0; i < 10; i++) {
                     ((ServerLevel)this.level())
                        .sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                           this.getX(),
                           this.getY(),
                           this.getZ(),
                           5,
                           Math.random() * 6.0 - 3.0,
                           0.0,
                           Math.random() * 6.0 - 3.0,
                           1.0
                        );
                  }
               }
               break;
            case "bite_attack":
               if (timer == 25) {
                  this.dealMeleeAttackDamage(7.0, 0.667F, 120.0);

                  for (int i = 0; i < 10; i++) {
                     ((ServerLevel)this.level())
                        .sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                           this.getX(),
                           this.getY(),
                           this.getZ(),
                           5,
                           Math.random() * 6.0 - 3.0,
                           0.0,
                           Math.random() * 6.0 - 3.0,
                           1.0
                        );
                  }
               }
               break;
            case "tail_swipe":
               if (timer == 32) {
                  this.dealMeleeAttackDamage(8.0, 1.0F, 120.0);

                  for (int i = 0; i < 10; i++) {
                     ((ServerLevel)this.level())
                        .sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.FIRE_CLOUD_SMOKE.get(),
                           this.getX(),
                           this.getY(),
                           this.getZ(),
                           5,
                           Math.random() * 6.0 - 3.0,
                           0.0,
                           Math.random() * 6.0 - 3.0,
                           1.0
                        );
                  }
               }
               break;
            default:
               if (attackName.contains("fireballs")) {
                  if (this.getHeadTimer() == 9 || this.getHeadTimer() == 19 || this.getHeadTimer() == 29) {
                     this.spawnFireball();
                     ((ServerLevel)this.level())
                        .sendParticles(
                           (SimpleParticleType)BossesRiseParticleTypes.FIRE_AOE.get(),
                           this.getEyePosition().x,
                           this.getEyePosition().y,
                           this.getEyePosition().z,
                           5,
                           Math.random() - 0.5,
                           0.0,
                           Math.random() - 0.5,
                           1.0
                        );
                  }

                  if (this.getHeadTimer() > 29) {
                     this.fireBallCount = 0;
                  }

                  return;
               }

               if (attackName.contains("fire_breathing")) {
                  if (this.getHeadTimer() >= 25 && this.getHeadTimer() <= 60 && this.getHeadTimer() % 5 == 0) {
                     this.spawnFireBreath();
                  }

                  if (this.getHeadTimer() >= 60) {
                     this.previousFireBreathTarget = null;
                  }

                  return;
               }
         }
      }
   }

   private void spawnFireball() {
      this.fireBallCount++;
      LivingEntity target = this.getTarget();
      if (target != null) {
         Vec3 directionToTarget = target.getEyePosition().subtract(this.getEyePosition()).normalize();
         Projectile fireball = (new Object() {
            public Projectile getArrow(Level level, Entity shooter, float damage, final int knockback, final byte piercing) {
               AbstractArrow entityToSpawn = new BlazingFireBallEntity((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get(), level) {
                  public byte getPierceLevel() {
                     return piercing;
                  }

                  @Override
                  protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
                     if (knockback > 0) {
                        double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
                        if (vec3.lengthSqr() > 0.0) {
                           livingEntity.push(vec3.x, 0.1, vec3.z);
                        }
                     }
                  }
               };
               entityToSpawn.setOwner(shooter);
               entityToSpawn.setBaseDamage(InfernalDragonEntity.this.fireBallCount == 3 ? damage * 1.5F : damage);
               entityToSpawn.setSilent(true);
               return entityToSpawn;
            }
         }).getArrow(this.level(), this, 1.0F, 1, (byte)0);
         fireball.setPos(this.getX(), this.getEyeY() - 0.1, this.getZ());
         fireball.shoot(directionToTarget.x, directionToTarget.y, directionToTarget.z, this.fireBallCount == 3 ? 3.0F : 2.0F, 0.0F);
         this.level().addFreshEntity(fireball);
      }
   }

   private void spawnFireBreath() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         if (this.previousFireBreathTarget == null) {
            Vec3 playerPos = target.getEyePosition();
            double offsetX = (this.random.nextDouble() - 0.5) * 3.0;
            double offsetY = (this.random.nextDouble() - 0.5) * 3.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 3.0;
            Vec3 targetPosition = playerPos.add(offsetX, offsetY, offsetZ);
            if (target.isSprinting()) {
               Vec3 awayDirection = targetPosition.subtract(this.getEyePosition()).normalize();
               targetPosition = targetPosition.add(awayDirection.scale(1.5));
            }

            this.previousFireBreathTarget = targetPosition;
         }

         Vec3 currentPlayerPos = target.getEyePosition();
         double distanceToPlayer = this.previousFireBreathTarget.distanceTo(currentPlayerPos);
         if (distanceToPlayer > 0.5) {
            this.previousFireBreathTarget = this.previousFireBreathTarget.lerp(currentPlayerPos, 0.5);
         }

         Vec3 directionToTarget = this.previousFireBreathTarget.subtract(this.getEyePosition()).normalize();
         Vec3 start = new Vec3(this.getEyePosition().toVector3f());
         Vec3 step = directionToTarget.scale(0.5);
         MutableBlockPos pos = new MutableBlockPos();

         for (int i = 0; i < 100; i++) {
            start = start.add(step);
            pos.set(start.x, start.y, start.z);

            for (int j = 0; j < 3; j++) {
               ((ServerLevel)this.level())
                  .sendParticles(
                     (SimpleParticleType)BossesRiseParticleTypes.BLAZING_FLAME.get(),
                     start.x(),
                     start.y(),
                     start.z(),
                     1,
                     Math.random() - 0.5,
                     0.0,
                     Math.random() - 0.5,
                     0.0
                  );
            }

            if (!this.level().getBlockState(pos).isAir() && this.getEyePosition().distanceToSqr(start) > 25.0) {
               for (int x = -1; x <= 1; x++) {
                  for (int z = -1; z <= 1; z++) {
                     BlockPos pos1 = pos.offset(x, 1, z);
                     BlockState block = this.level().getBlockState(pos1);
                     if (!block.isAir()) {
                        if (!block.is(Blocks.COBBLESTONE)) {
                           continue;
                        }

                        if (Math.random() < 0.5) {
                           this.level().setBlock(pos1, Blocks.AIR.defaultBlockState(), 0);
                        }
                     }

                     this.level().setBlock(pos1, Blocks.FIRE.defaultBlockState(), 0);
                  }
               }
               break;
            }

            Vec3 finalStart = start;
            this.level()
               .players()
               .stream()
               .filter(p -> p.distanceToSqr(finalStart.x, finalStart.y, finalStart.z) < (p.isSprinting() ? 0.5 : 1.25))
               .forEach(p -> {
                  if (!p.isSprinting()) {
                     p.setRemainingFireTicks(250);
                     if (p.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                        p.hurt(this.damageSources().magic(), 2.0F);
                     }
                  } else {
                     p.setRemainingFireTicks(100);
                     if (p.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                        p.hurt(this.damageSources().magic(), 1.5F);
                     }
                  }
               });
         }
      }
   }

   private void dealMeleeAttackDamage(double range, float damageScale, double arcAngle) {
      Vec3 dragonPos = this.position();
      double dragonYaw = Math.toRadians(this.getYRot());
      this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(range)).forEach(player -> {
         if (!player.isSpectator() && !player.isCreative()) {
            Vec3 toPlayer = player.position().subtract(dragonPos).normalize();
            double angleToPlayer = Math.atan2(toPlayer.z, toPlayer.x);
            double angleDiff = Math.abs(Math.toDegrees(angleToPlayer - dragonYaw));
            if (angleDiff > 180.0) {
               angleDiff = 360.0 - angleDiff;
            }

            if (angleDiff <= arcAngle && dragonPos.distanceTo(player.position()) <= range) {
               this.attackEntity(player, damageScale);
               Vec3 knockback = toPlayer.scale(1.2);
               player.push(knockback.x, 0.4, knockback.z);
            }
         }
      });
   }

   public AbstractBossEntity.State getHeadState() {
      return this.headState;
   }

   public void setHeadState(String name) {
      if (name == null) {
         name = "idle";
      }

      this.headTimer = 0;
      if (HEADSTATES.containsKey(name) || name.equals("idle")) {
         this.headState = HEADSTATES.get(name);
         if (name.equals("idle")) {
            this.stopTriggeredAnim("head_controller", null);
         } else {
            this.triggerAnim("head_controller", name);
         }
      }
   }

   public boolean isHeadState(String name) {
      return this.headState == null ? false : this.headState.name().equals(name);
   }

   public int getHeadTimer() {
      return this.headTimer;
   }

   public boolean isHeadTimerDone() {
      return this.headState == null ? true : this.headTimer > this.headState.duration();
   }

   public AnimationController<GeoAnimatable> getController() {
      AnimatableInstanceCache cache = this.getAnimatableInstanceCache();
      return cache == null ? null : (AnimationController)cache.getManagerForId(this.getId()).getAnimationControllers().get("main_controller");
   }

   private static void addState(AbstractBossEntity.State state) {
      STATES.put(state.name(), state);
   }

   private static void addHeadState(AbstractBossEntity.State state) {
      HEADSTATES.put(state.name(), state);
   }

   static {
      addState(new AbstractBossEntity.State("idle", 30));
      addState(new AbstractBossEntity.State("intro", 230));
      addState(new AbstractBossEntity.State("phase_transition", 230));
      addState(new AbstractBossEntity.State("dead", 180));
      addState(new AbstractBossEntity.State("knocked_down", 90));
      addState(new AbstractBossEntity.State("hit", 15));
      addState(new AbstractBossEntity.State("claw_attack", 67));
      addState(new AbstractBossEntity.State("bite_attack", 48));
      addState(new AbstractBossEntity.State("tail_swipe", 62));
      addState(new AbstractBossEntity.State("flight_dash", 85));
      addHeadState(new AbstractBossEntity.State("fireballs", 37));
      addHeadState(new AbstractBossEntity.State("fire_breathing", 62));
   }

   private static class CircleFlightGoal extends Goal {
      private final InfernalDragonEntity dragon;
      private Vec3 circleCenter;
      private int recenterTimer = 0;
      private static final int RECENTER_INTERVAL = 60;
      private boolean isDescending = false;
      private Vec3 landingTarget = null;
      private float previousYaw = 0.0F;

      public CircleFlightGoal(InfernalDragonEntity dragon) {
         this.dragon = dragon;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         return ((Boolean)this.dragon.entityData.get(InfernalDragonEntity.DATA_IS_CIRCLING) || this.isDescending)
            && this.dragon.isFlying()
            && !this.dragon.isCinematic()
            && this.dragon.getTarget() != null;
      }

      public boolean canContinueToUse() {
         return (Boolean)this.dragon.entityData.get(InfernalDragonEntity.DATA_IS_CIRCLING) || this.isDescending;
      }

      public void start() {
         this.updateCircleCenter();
         this.dragon.setNoGravity(true);
         this.isDescending = false;
         this.previousYaw = this.dragon.getYRot();
      }

      public void stop() {
         this.dragon.setNoGravity(false);
         this.isDescending = false;
         this.landingTarget = null;
      }

      public void tick() {
         if (!(Boolean)this.dragon.entityData.get(InfernalDragonEntity.DATA_IS_CIRCLING) && !this.isDescending) {
            this.startDescending();
         } else if (this.isDescending) {
            this.performDescent();
         } else {
            this.recenterTimer++;
            if (this.recenterTimer >= 60) {
               this.updateCircleCenter();
               this.recenterTimer = 0;
            }

            if (this.circleCenter == null) {
               this.updateCircleCenter();
               if (this.circleCenter == null) {
                  return;
               }
            }

            float currentAngle = (Float)this.dragon.entityData.get(InfernalDragonEntity.DATA_CIRCLE_ANGLE);
            currentAngle += 0.08F;
            if (currentAngle >= Math.PI * 2) {
               currentAngle -= (float) (Math.PI * 2);
            }

            this.dragon.entityData.set(InfernalDragonEntity.DATA_CIRCLE_ANGLE, currentAngle);
            double targetX = this.circleCenter.x + Math.cos(currentAngle) * 9.0;
            double targetZ = this.circleCenter.z + Math.sin(currentAngle) * 9.0;
            double targetY = this.circleCenter.y + 20.0;
            Vec3 targetPos = new Vec3(targetX, targetY, targetZ);
            Vec3 currentPos = this.dragon.position();
            BlockPos targetBlock = BlockPos.containing(targetPos);
            if (!this.dragon.level().getBlockState(targetBlock).isAir()) {
               Vec3 awayFromObstacle = currentPos.subtract(new Vec3(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ())).normalize();
               this.dragon.setDeltaMovement(awayFromObstacle.scale(0.3));
            } else {
               Vec3 movement = targetPos.subtract(currentPos);
               double distance = movement.length();
               this.dragon.setDeltaMovement(Vec3.ZERO);
               if (distance > 0.1) {
                  double speed = Math.min(0.4, distance * 0.3);
                  movement = movement.normalize().scale(speed);
                  this.dragon.setDeltaMovement(movement);
               }

               Vec3 movementDir = movement.normalize();
               float targetYaw = (float)Math.toDegrees(Math.atan2(movementDir.z, movementDir.x)) - 90.0F;
               float yawDiff = targetYaw - this.previousYaw;
               yawDiff = (yawDiff % 360.0F + 540.0F) % 360.0F - 180.0F;
               float maxRotationPerTick = 10.0F;
               yawDiff = Math.max(-maxRotationPerTick, Math.min(maxRotationPerTick, yawDiff));
               float smoothYaw = this.previousYaw + yawDiff;
               this.previousYaw = smoothYaw;
               this.dragon.setYRot(smoothYaw);
               this.dragon.setYHeadRot(smoothYaw);
               this.dragon.yBodyRot = smoothYaw;
            }
         }
      }

      private void startDescending() {
         this.isDescending = true;
         LivingEntity target = this.dragon.getTarget();
         BlockPos targetBlockPos = this.dragon.blockPosition();
         if (target != null) {
            targetBlockPos = target.blockPosition();
         }

         BlockPos groundPos = this.dragon.level().getHeightmapPos(Types.MOTION_BLOCKING, targetBlockPos);
         this.landingTarget = new Vec3(this.dragon.getX(), groundPos.getY(), this.dragon.getZ());
      }

      private void performDescent() {
         if (this.landingTarget == null) {
            BlockPos currentBlockPos = this.dragon.blockPosition();
            BlockPos groundPos = this.dragon.level().getHeightmapPos(Types.MOTION_BLOCKING, currentBlockPos);
            this.landingTarget = new Vec3(this.dragon.getX(), groundPos.getY(), this.dragon.getZ());
         }

         Vec3 currentPos = this.dragon.position();
         Vec3 horizontalTarget = new Vec3(this.landingTarget.x, currentPos.y, this.landingTarget.z);
         Vec3 horizontalMovement = horizontalTarget.subtract(currentPos);
         double horizontalDist = horizontalMovement.horizontalDistance();
         Vec3 movement = new Vec3(0.0, -0.3, 0.0);
         if (horizontalDist > 1.0) {
            Vec3 horizontal = horizontalMovement.normalize().scale(0.3);
            Vec3 vertical = new Vec3(0.0, -0.2, 0.0);
            movement = horizontal.add(vertical);
         }

         this.dragon.setDeltaMovement(movement);
         if (horizontalDist > 0.1) {
            float yaw = (float)Math.toDegrees(Math.atan2(horizontalMovement.z, horizontalMovement.x)) - 90.0F;
            this.dragon.setYRot(yaw);
            this.dragon.setYHeadRot(yaw);
            this.dragon.yBodyRot = yaw;
         }

         boolean closeToGround = currentPos.y <= this.landingTarget.y + 1.0;
         if (this.dragon.onGround() || closeToGround) {
            this.dragon.setDeltaMovement(Vec3.ZERO);
            this.dragon.setNoGravity(false);
            this.isDescending = false;
            this.landingTarget = null;
            if (!this.dragon.onGround()) {
               this.dragon.setPos(this.dragon.getX(), this.landingTarget.y, this.dragon.getZ());
            }
         }
      }

      private void updateCircleCenter() {
         Vec3 arenaCenter = this.dragon.position();
         if (this.dragon.spawnerPosition != null) {
            arenaCenter = this.dragon.spawnerPosition.getCenter();
         }

         LivingEntity target = this.dragon.getTarget();
         if (target != null) {
            Vec3 targetPos = new Vec3(target.getX(), target.getY(), target.getZ());
            double maxDistFromCenter = 9.0;
            Vec3 offsetFromArena = targetPos.subtract(arenaCenter);
            double distFromArena = Math.sqrt(offsetFromArena.x * offsetFromArena.x + offsetFromArena.z * offsetFromArena.z);
            this.circleCenter = targetPos;
            if (distFromArena > maxDistFromCenter) {
               double scale = maxDistFromCenter / distFromArena;
               this.circleCenter = new Vec3(arenaCenter.x + offsetFromArena.x * scale, targetPos.y, arenaCenter.z + offsetFromArena.z * scale);
            }

            this.dragon.lastCircleCenter = this.circleCenter;
         } else if (this.dragon.lastCircleCenter != null) {
            this.circleCenter = this.dragon.lastCircleCenter;
         } else {
            this.circleCenter = arenaCenter;
            this.dragon.lastCircleCenter = this.circleCenter;
         }
      }
   }

   private static class DirectMoveToTargetGoal extends Goal {
      private final InfernalDragonEntity dragon;
      private final double speed;

      public DirectMoveToTargetGoal(InfernalDragonEntity dragon, double speed) {
         this.dragon = dragon;
         this.speed = speed;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = this.dragon.getTarget();
         if (target == null || !target.isAlive()) {
            return false;
         } else {
            return this.dragon.isCinematic() ? false : this.dragon.distanceToSqr(target) > 4.0;
         }
      }

      public void tick() {
         LivingEntity target = this.dragon.getTarget();
         if (target != null) {
            Vec3 targetPos = target.position();
            Vec3 currentPos = this.dragon.position();
            Vec3 direction = targetPos.subtract(currentPos).normalize();
            this.dragon.setDeltaMovement(direction.scale(this.speed));
            this.dragon.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }
   }

   public static class GroundFollowGoal extends Goal {
      private final InfernalDragonEntity entity;
      private final double speed;
      private final double minDistance;

      public GroundFollowGoal(InfernalDragonEntity entity, double speed, double minDistance) {
         this.entity = entity;
         this.speed = speed;
         this.minDistance = minDistance;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         if (this.entity.getTarget() == null) {
            return false;
         }

         if (this.entity.isCinematic()) {
            return false;
         }

         double distSq = this.entity.distanceToSqr(this.entity.getTarget());
         return distSq > this.minDistance * this.minDistance;
      }

      public boolean canContinueToUse() {
         return this.canUse();
      }

      public void start() {
      }

      public void stop() {
         this.entity.getNavigation().stop();
      }

      public void tick() {
         if (this.entity.getTarget() != null) {
            Vec3 targetPos = this.entity.getTarget().position();
            Vec3 dragonPos = this.entity.position();
            Vec3 desiredDir = targetPos.subtract(dragonPos).normalize();
            Vec3 moveDir = this.steerAroundObstacles(dragonPos, desiredDir);
            this.entity.setDeltaMovement(moveDir.x * this.speed, this.entity.getDeltaMovement().y, moveDir.z * this.speed);
            this.entity.getLookControl().setLookAt(this.entity.getTarget(), 10.0F, this.entity.getMaxHeadXRot());
         }
      }

      private Vec3 steerAroundObstacles(Vec3 from, Vec3 desiredDir) {
         Vec3 ahead = from.add(desiredDir.scale(3.0));
         if (this.entity.level().clip(new ClipContext(from, ahead, Block.COLLIDER, Fluid.NONE, this.entity)).getType() == Type.MISS) {
            return desiredDir;
         }

         for (int angle : new int[]{45, -45, 90, -90}) {
            Vec3 deflected = this.rotateY(desiredDir, Math.toRadians(angle));
            Vec3 probe = from.add(deflected.scale(3.0));
            if (this.entity.level().clip(new ClipContext(from, probe, Block.COLLIDER, Fluid.NONE, this.entity)).getType() == Type.MISS) {
               return deflected;
            }
         }

         return desiredDir;
      }

      private Vec3 rotateY(Vec3 vec, double radians) {
         double cos = Math.cos(radians);
         double sin = Math.sin(radians);
         return new Vec3(vec.x * cos - vec.z * sin, vec.y, vec.x * sin + vec.z * cos);
      }
   }
}
