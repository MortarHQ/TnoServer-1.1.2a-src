package twilightforest.entity.boss;

import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.network.MovePlayerPacket;

public class HydraHeadContainer {
   private static final int FLAME_BURN_FACTOR = 3;
   private static final int FLAME_DAMAGE = 19;
   private static final int BITE_DAMAGE = 48;
   private static double FLAME_BREATH_TRACKING_SPEED = 0.04;
   @Nullable
   private static final HydraHeadContainer.State NEXT_AUTOMATIC = null;
   public final HydraHead headEntity;
   public final HydraNeck necka;
   public final HydraNeck neckb;
   public final HydraNeck neckc;
   public final HydraNeck neckd;
   public final HydraNeck necke;
   @Nullable
   public Entity targetEntity;
   private double targetX;
   private double targetY;
   private double targetZ;
   @Nullable
   private HydraHeadContainer.State prevState;
   private HydraHeadContainer.State currentState;
   @Nullable
   private HydraHeadContainer.State nextState;
   public boolean isSecondaryAttacking;
   private int ticksNeeded;
   private int ticksProgress;
   private final int headNum;
   private int damageTaken;
   private int respawnCounter;
   private int deathTime;
   private final Hydra hydra;
   private final Map<HydraHeadContainer.State, Float>[] stateNeckLength;
   private final Map<HydraHeadContainer.State, Float>[] stateXRotations;
   private final Map<HydraHeadContainer.State, Float>[] stateYRotations;
   private final Map<HydraHeadContainer.State, Float>[] stateMouthOpen;

   public HydraHeadContainer(Hydra hydra, int number, boolean startActive) {
      this.headNum = number;
      this.hydra = hydra;
      this.damageTaken = 0;
      this.respawnCounter = -1;
      this.headEntity = new HydraHead(hydra);
      this.headEntity.setPos(hydra.getX(), hydra.getY(), hydra.getZ());
      this.necka = new HydraNeck(this.headEntity);
      this.neckb = new HydraNeck(this.headEntity);
      this.neckc = new HydraNeck(this.headEntity);
      this.neckd = new HydraNeck(this.headEntity);
      this.necke = new HydraNeck(this.headEntity);
      this.stateNeckLength = new Map[7];
      this.stateXRotations = new Map[7];
      this.stateYRotations = new Map[7];
      this.stateMouthOpen = new Map[7];

      for (int i = 0; i < 7; i++) {
         this.stateNeckLength[i] = new EnumMap<>(HydraHeadContainer.State.class);
         this.stateXRotations[i] = new EnumMap<>(HydraHeadContainer.State.class);
         this.stateYRotations[i] = new EnumMap<>(HydraHeadContainer.State.class);
         this.stateMouthOpen[i] = new EnumMap<>(HydraHeadContainer.State.class);
      }

      this.setupStateRotations();
      if (startActive) {
         this.prevState = HydraHeadContainer.State.IDLE;
         this.currentState = HydraHeadContainer.State.IDLE;
         this.nextState = NEXT_AUTOMATIC;
         this.ticksNeeded = 60;
         this.ticksProgress = 60;
      } else {
         this.prevState = HydraHeadContainer.State.DEAD;
         this.currentState = HydraHeadContainer.State.DEAD;
         this.nextState = NEXT_AUTOMATIC;
         this.ticksNeeded = 20;
         this.ticksProgress = 20;
      }

      this.setHeadPosition();
      this.setNeckPosition();
   }

   protected void setupStateRotations() {
      this.setAnimation(0, HydraHeadContainer.State.IDLE, 60.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.IDLE, 10.0F, 60.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.IDLE, 10.0F, -60.0F, 9.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.IDLE, 50.0F, 90.0F, 8.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.IDLE, 50.0F, -90.0F, 8.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.IDLE, -10.0F, 90.0F, 9.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.IDLE, -10.0F, -90.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.ATTACK_COOLDOWN, 60.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.ATTACK_COOLDOWN, 10.0F, 60.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.ATTACK_COOLDOWN, 10.0F, -60.0F, 9.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.ATTACK_COOLDOWN, 50.0F, 90.0F, 8.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.ATTACK_COOLDOWN, 50.0F, -90.0F, 8.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.ATTACK_COOLDOWN, -10.0F, 90.0F, 9.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.ATTACK_COOLDOWN, -10.0F, -90.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.FLAME_BEGINNING, 50.0F, 0.0F, 8.0F, 0.75F);
      this.setAnimation(1, HydraHeadContainer.State.FLAME_BEGINNING, 30.0F, 45.0F, 9.0F, 0.75F);
      this.setAnimation(2, HydraHeadContainer.State.FLAME_BEGINNING, 30.0F, -45.0F, 9.0F, 0.75F);
      this.setAnimation(3, HydraHeadContainer.State.FLAME_BEGINNING, 50.0F, 90.0F, 8.0F, 0.75F);
      this.setAnimation(4, HydraHeadContainer.State.FLAME_BEGINNING, 50.0F, -90.0F, 8.0F, 0.75F);
      this.setAnimation(5, HydraHeadContainer.State.FLAME_BEGINNING, -10.0F, 90.0F, 9.0F, 0.75F);
      this.setAnimation(6, HydraHeadContainer.State.FLAME_BEGINNING, -10.0F, -90.0F, 9.0F, 0.75F);
      this.setAnimation(0, HydraHeadContainer.State.FLAMING, 45.0F, 0.0F, 8.0F, 1.0F);
      this.setAnimation(1, HydraHeadContainer.State.FLAMING, 30.0F, 60.0F, 9.0F, 1.0F);
      this.setAnimation(2, HydraHeadContainer.State.FLAMING, 30.0F, -60.0F, 9.0F, 1.0F);
      this.setAnimation(3, HydraHeadContainer.State.FLAMING, 50.0F, 90.0F, 8.0F, 1.0F);
      this.setAnimation(4, HydraHeadContainer.State.FLAMING, 50.0F, -90.0F, 8.0F, 1.0F);
      this.setAnimation(5, HydraHeadContainer.State.FLAMING, -10.0F, 90.0F, 9.0F, 1.0F);
      this.setAnimation(6, HydraHeadContainer.State.FLAMING, -10.0F, -90.0F, 9.0F, 1.0F);
      this.setAnimation(0, HydraHeadContainer.State.FLAME_ENDING, 60.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.FLAME_ENDING, 10.0F, 45.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.FLAME_ENDING, 10.0F, -45.0F, 9.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.FLAME_ENDING, 50.0F, 90.0F, 8.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.FLAME_ENDING, 50.0F, -90.0F, 8.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.FLAME_ENDING, -10.0F, 90.0F, 9.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.FLAME_ENDING, -10.0F, -90.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.BITE_BEGINNING, -5.0F, 60.0F, 5.0F, 0.25F);
      this.setAnimation(1, HydraHeadContainer.State.BITE_BEGINNING, -10.0F, 60.0F, 9.0F, 0.25F);
      this.setAnimation(2, HydraHeadContainer.State.BITE_BEGINNING, -10.0F, -60.0F, 9.0F, 0.25F);
      this.setAnimation(0, HydraHeadContainer.State.BITE_READY, -5.0F, 60.0F, 5.0F, 1.0F);
      this.setAnimation(1, HydraHeadContainer.State.BITE_READY, -10.0F, 60.0F, 9.0F, 1.0F);
      this.setAnimation(2, HydraHeadContainer.State.BITE_READY, -10.0F, -60.0F, 9.0F, 1.0F);
      this.setAnimation(0, HydraHeadContainer.State.BITING, -5.0F, -30.0F, 5.0F, 0.2F);
      this.setAnimation(1, HydraHeadContainer.State.BITING, -10.0F, -30.0F, 5.0F, 0.2F);
      this.setAnimation(2, HydraHeadContainer.State.BITING, -10.0F, 30.0F, 5.0F, 0.2F);
      this.setAnimation(0, HydraHeadContainer.State.BITE_ENDING, 60.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.BITE_ENDING, -10.0F, 60.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.BITE_ENDING, -10.0F, -60.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.MORTAR_BEGINNING, 50.0F, 0.0F, 8.0F, 0.75F);
      this.setAnimation(1, HydraHeadContainer.State.MORTAR_BEGINNING, 30.0F, 45.0F, 9.0F, 0.75F);
      this.setAnimation(2, HydraHeadContainer.State.MORTAR_BEGINNING, 30.0F, -45.0F, 9.0F, 0.75F);
      this.setAnimation(3, HydraHeadContainer.State.MORTAR_BEGINNING, 50.0F, 90.0F, 8.0F, 0.75F);
      this.setAnimation(4, HydraHeadContainer.State.MORTAR_BEGINNING, 50.0F, -90.0F, 8.0F, 0.75F);
      this.setAnimation(5, HydraHeadContainer.State.MORTAR_BEGINNING, -10.0F, 90.0F, 9.0F, 0.75F);
      this.setAnimation(6, HydraHeadContainer.State.MORTAR_BEGINNING, -10.0F, -90.0F, 9.0F, 0.75F);
      this.setAnimation(0, HydraHeadContainer.State.MORTAR_SHOOTING, 45.0F, 0.0F, 8.0F, 1.0F);
      this.setAnimation(1, HydraHeadContainer.State.MORTAR_SHOOTING, 30.0F, 60.0F, 9.0F, 1.0F);
      this.setAnimation(2, HydraHeadContainer.State.MORTAR_SHOOTING, 30.0F, -60.0F, 9.0F, 1.0F);
      this.setAnimation(3, HydraHeadContainer.State.MORTAR_SHOOTING, 50.0F, 90.0F, 8.0F, 1.0F);
      this.setAnimation(4, HydraHeadContainer.State.MORTAR_SHOOTING, 50.0F, -90.0F, 8.0F, 1.0F);
      this.setAnimation(5, HydraHeadContainer.State.MORTAR_SHOOTING, -10.0F, 90.0F, 9.0F, 1.0F);
      this.setAnimation(6, HydraHeadContainer.State.MORTAR_SHOOTING, -10.0F, -90.0F, 9.0F, 1.0F);
      this.setAnimation(0, HydraHeadContainer.State.MORTAR_ENDING, 60.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.MORTAR_ENDING, 10.0F, 45.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.MORTAR_ENDING, 10.0F, -45.0F, 9.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.MORTAR_ENDING, 50.0F, 90.0F, 8.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.MORTAR_ENDING, 50.0F, -90.0F, 8.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.MORTAR_ENDING, -10.0F, 90.0F, 9.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.MORTAR_ENDING, -10.0F, -90.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.DYING, -20.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.DYING, -20.0F, 60.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.DYING, -20.0F, -60.0F, 9.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.DYING, -20.0F, 90.0F, 8.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.DYING, -20.0F, -90.0F, 8.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.DYING, -10.0F, 90.0F, 9.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.DYING, -10.0F, -90.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.DEAD, 0.0F, 179.0F, 4.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.DEAD, 0.0F, 179.0F, 4.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.DEAD, 0.0F, -180.0F, 4.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.DEAD, 0.0F, 179.0F, 4.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.DEAD, 0.0F, -180.0F, 4.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.DEAD, 0.0F, 179.0F, 4.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.DEAD, 0.0F, -180.0F, 4.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.BORN, 60.0F, 0.0F, 7.0F, 0.0F);
      this.setAnimation(1, HydraHeadContainer.State.BORN, 10.0F, 60.0F, 9.0F, 0.0F);
      this.setAnimation(2, HydraHeadContainer.State.BORN, 10.0F, -60.0F, 9.0F, 0.0F);
      this.setAnimation(3, HydraHeadContainer.State.BORN, 50.0F, 90.0F, 8.0F, 0.0F);
      this.setAnimation(4, HydraHeadContainer.State.BORN, 50.0F, -90.0F, 8.0F, 0.0F);
      this.setAnimation(5, HydraHeadContainer.State.BORN, -10.0F, 90.0F, 9.0F, 0.0F);
      this.setAnimation(6, HydraHeadContainer.State.BORN, -10.0F, -90.0F, 9.0F, 0.0F);
      this.setAnimation(0, HydraHeadContainer.State.ROAR_START, 60.0F, 0.0F, 7.0F, 0.25F);
      this.setAnimation(1, HydraHeadContainer.State.ROAR_START, 10.0F, 60.0F, 9.0F, 0.25F);
      this.setAnimation(2, HydraHeadContainer.State.ROAR_START, 10.0F, -60.0F, 9.0F, 0.25F);
      this.setAnimation(3, HydraHeadContainer.State.ROAR_START, 50.0F, 90.0F, 8.0F, 0.25F);
      this.setAnimation(4, HydraHeadContainer.State.ROAR_START, 50.0F, -90.0F, 8.0F, 0.25F);
      this.setAnimation(5, HydraHeadContainer.State.ROAR_START, -10.0F, 90.0F, 9.0F, 0.25F);
      this.setAnimation(6, HydraHeadContainer.State.ROAR_START, -10.0F, -90.0F, 9.0F, 0.25F);
      this.setAnimation(0, HydraHeadContainer.State.ROAR_RAWR, 60.0F, 0.0F, 9.0F, 1.0F);
      this.setAnimation(1, HydraHeadContainer.State.ROAR_RAWR, 10.0F, 60.0F, 11.0F, 1.0F);
      this.setAnimation(2, HydraHeadContainer.State.ROAR_RAWR, 10.0F, -60.0F, 11.0F, 1.0F);
      this.setAnimation(3, HydraHeadContainer.State.ROAR_RAWR, 50.0F, 90.0F, 10.0F, 1.0F);
      this.setAnimation(4, HydraHeadContainer.State.ROAR_RAWR, 50.0F, -90.0F, 10.0F, 1.0F);
      this.setAnimation(5, HydraHeadContainer.State.ROAR_RAWR, -10.0F, 90.0F, 11.0F, 1.0F);
      this.setAnimation(6, HydraHeadContainer.State.ROAR_RAWR, -10.0F, -90.0F, 11.0F, 1.0F);
   }

   private void setAnimation(int head, HydraHeadContainer.State state, float xRotation, float yRotation, float neckLength, float mouthOpen) {
      this.stateXRotations[head].put(state, xRotation);
      this.stateYRotations[head].put(state, yRotation);
      this.stateNeckLength[head].put(state, neckLength);
      this.stateMouthOpen[head].put(state, mouthOpen);
   }

   public HydraNeck[] getNeckArray() {
      return new HydraNeck[]{this.necka, this.neckb, this.neckc, this.neckd, this.necke};
   }

   public void performOnAllNecks(Consumer<HydraNeck> consumer) {
      for (int i = 0; i < this.getNeckArray().length; i++) {
         consumer.accept(this.getNeckArray()[i]);
      }
   }

   public void tick() {
      this.headEntity.tick();
      this.performOnAllNecks(HydraPart::tick);
      this.setDifficultyVariables();
      if (!this.hydra.level().isClientSide()) {
         if (!this.isDead() && this.headEntity.dimensions.width() == 0.0F) {
            this.headEntity.activate();
            this.performOnAllNecks(HydraPart::activate);
         } else if (!this.isActive() && this.headEntity.dimensions.width() > 0.0F) {
            this.headEntity.deactivate();
            this.performOnAllNecks(HydraPart::deactivate);
         }

         this.advanceRespawnCounter();
         this.advanceHeadState();
         this.setHeadPosition();
         this.setHeadFacing();
         this.executeAttacks();
         this.playSounds();
      } else {
         this.addMouthParticles();
      }

      this.animateHeadDeath();
      this.setNeckPosition();
   }

   public boolean canRespawn() {
      return this.currentState == HydraHeadContainer.State.DEAD && this.respawnCounter == -1;
   }

   private void advanceRespawnCounter() {
      if (this.currentState == HydraHeadContainer.State.DEAD && this.respawnCounter > -1 && --this.respawnCounter <= 0) {
         this.setNextState(HydraHeadContainer.State.BORN);
         this.damageTaken = 0;
         this.endCurrentAction();
         this.respawnCounter = -1;
      }
   }

   private void animateHeadDeath() {
      if (this.headEntity.getState() == HydraHeadContainer.State.DYING) {
         this.deathTime++;
         if (this.deathTime == 1) {
            this.headEntity.markedDead = true;
            this.hydra.setHeadNameFor(this.headNum, "");
            this.headEntity.setCustomName(Component.literal(""));
         } else if (this.deathTime == 10) {
            this.getNeckArray()[0].markedDead = true;
         } else if (this.deathTime == 20) {
            this.getNeckArray()[1].markedDead = true;
         } else if (this.deathTime == 30) {
            this.getNeckArray()[2].markedDead = true;
         } else if (this.deathTime == 40) {
            this.getNeckArray()[3].markedDead = true;
         } else if (this.deathTime == 50) {
            this.getNeckArray()[4].markedDead = true;
         }

         this.headEntity.hurtTime = 20;
         this.performOnAllNecks(neck -> neck.hurtTime = 20);
      } else {
         this.deathTime = 0;
      }
   }

   private void advanceHeadState() {
      if (++this.ticksProgress >= this.ticksNeeded) {
         HydraHeadContainer.State myNext;
         if (this.nextState == NEXT_AUTOMATIC) {
            myNext = HydraHeadContainer.State.NEXT_STATE.get(this.currentState);
            if (myNext != this.currentState && this.isSecondaryAttacking && myNext == HydraHeadContainer.State.ATTACK_COOLDOWN) {
               this.isSecondaryAttacking = false;
               myNext = HydraHeadContainer.State.IDLE;
            }
         } else {
            myNext = this.nextState;
            this.nextState = NEXT_AUTOMATIC;
         }

         this.ticksNeeded = myNext.duration;
         this.ticksProgress = 0;
         this.prevState = this.currentState;
         this.currentState = myNext;
      }

      if (this.headEntity.getState() != this.currentState) {
         this.headEntity.setState(this.currentState);
      }
   }

   private void setHeadFacing() {
      if (this.currentState == HydraHeadContainer.State.BITE_READY) {
         this.faceEntity(this.targetEntity, 5.0F, this.hydra.getMaxHeadXRot());
         float biteMaxYaw = -60.0F;
         float biteMinYaw = -90.0F;
         if (this.headNum == 2) {
            biteMaxYaw = 60.0F;
            biteMinYaw = 90.0F;
         }

         float yawOffOffset = Mth.wrapDegrees(this.headEntity.getYRot() - this.hydra.yBodyRot);
         if (yawOffOffset > biteMaxYaw) {
            this.headEntity.setYRot(this.hydra.yBodyRot + biteMaxYaw);
         }

         if (yawOffOffset < biteMinYaw) {
            this.headEntity.setYRot(this.hydra.yBodyRot + biteMinYaw);
         }

         Vec3 look = this.headEntity.getLookAngle();
         double distance = 16.0;
         this.targetX = this.headEntity.getX() + look.x() * distance;
         this.targetY = this.headEntity.getY() + 1.5 + look.y() * distance;
         this.targetZ = this.headEntity.getZ() + look.z() * distance;
      } else if (this.currentState == HydraHeadContainer.State.BITING || this.currentState == HydraHeadContainer.State.BITE_ENDING) {
         this.faceEntity(this.targetEntity, 5.0F, this.hydra.getMaxHeadXRot());
         this.headEntity.setXRot((float)(this.headEntity.getXRot() + (Math.PI / 4)));
      } else if (this.currentState == HydraHeadContainer.State.ROAR_RAWR) {
         this.faceVec(this.targetX, this.targetY, this.targetZ, 10.0F, this.hydra.getMaxHeadXRot());
      } else if (this.currentState == HydraHeadContainer.State.FLAMING || this.currentState == HydraHeadContainer.State.FLAME_BEGINNING) {
         this.moveTargetCoordsTowardsTargetEntity(FLAME_BREATH_TRACKING_SPEED);
         this.faceVec(this.targetX, this.targetY, this.targetZ, 5.0F, this.hydra.getMaxHeadXRot());
      } else if (!this.isDead()) {
         if (this.targetEntity != null) {
            this.faceEntity(this.targetEntity, 5.0F, this.hydra.getMaxHeadXRot());
         } else {
            this.faceIdle(1.5F, this.hydra.getMaxHeadXRot());
         }
      }
   }

   private void moveTargetCoordsTowardsTargetEntity(double distance) {
      if (this.targetEntity != null) {
         Vec3 vect = new Vec3(this.targetEntity.getX() - this.targetX, this.targetEntity.getY() - this.targetY, this.targetEntity.getZ() - this.targetZ);
         vect = vect.normalize();
         this.targetX = this.targetX + vect.x() * distance;
         this.targetY = this.targetY + vect.y() * distance;
         this.targetZ = this.targetZ + vect.z() * distance;
      }
   }

   private void addMouthParticles() {
      Vec3 vector = this.headEntity.getLookAngle();
      double dist = 3.5;
      double px = this.headEntity.getX() + vector.x() * dist;
      double py = this.headEntity.getY() + 1.0 + vector.y() * dist;
      double pz = this.headEntity.getZ() + vector.z() * dist;
      if (this.headEntity.getState() == HydraHeadContainer.State.FLAME_BEGINNING) {
         this.headEntity
            .level()
            .addAlwaysVisibleParticle(
               ParticleTypes.FLAME,
               px + this.headEntity.level().getRandom().nextDouble() - 0.5,
               py + this.headEntity.level().getRandom().nextDouble() - 0.5,
               pz + this.headEntity.level().getRandom().nextDouble() - 0.5,
               0.0,
               0.0,
               0.0
            );
         this.headEntity
            .level()
            .addAlwaysVisibleParticle(
               ParticleTypes.SMOKE,
               px + this.headEntity.level().getRandom().nextDouble() - 0.5,
               py + this.headEntity.level().getRandom().nextDouble() - 0.5,
               pz + this.headEntity.level().getRandom().nextDouble() - 0.5,
               0.0,
               0.0,
               0.0
            );
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.FLAMING) {
         Vec3 look = this.headEntity.getLookAngle();

         for (int i = 0; i < 5; i++) {
            double dx = look.x();
            double dy = look.y();
            double dz = look.z();
            double spread = 5.0 + this.headEntity.level().getRandom().nextDouble() * 2.5;
            double velocity = 1.0 + this.headEntity.level().getRandom().nextDouble();
            dx += this.headEntity.level().getRandom().nextGaussian() * 0.0075 * spread;
            dy += this.headEntity.level().getRandom().nextGaussian() * 0.0075 * spread;
            dz += this.headEntity.level().getRandom().nextGaussian() * 0.0075 * spread;
            dx *= velocity;
            dy *= velocity;
            dz *= velocity;
            this.headEntity.level().addAlwaysVisibleParticle((ParticleOptions)TFParticleType.LARGE_FLAME.get(), px, py, pz, dx, dy, dz);
         }
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.BITE_BEGINNING || this.headEntity.getState() == HydraHeadContainer.State.BITE_READY) {
         this.headEntity
            .level()
            .addAlwaysVisibleParticle(
               ParticleTypes.SPLASH,
               px + this.headEntity.level().getRandom().nextDouble() - 0.5,
               py + this.headEntity.level().getRandom().nextDouble() - 0.5,
               pz + this.headEntity.level().getRandom().nextDouble() - 0.5,
               0.0,
               0.0,
               0.0
            );
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.MORTAR_BEGINNING) {
         this.headEntity
            .level()
            .addAlwaysVisibleParticle(
               ParticleTypes.LARGE_SMOKE,
               px + this.headEntity.level().getRandom().nextDouble() - 0.5,
               py + this.headEntity.level().getRandom().nextDouble() - 0.5,
               pz + this.headEntity.level().getRandom().nextDouble() - 0.5,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void playSounds() {
      if (this.headEntity.getState() == HydraHeadContainer.State.FLAMING && this.headEntity.tickCount % 5 == 0) {
         this.headEntity
            .playSound(
               (SoundEvent)TFSounds.HYDRA_SHOOT_FIRE.get(),
               0.5F + this.headEntity.level().getRandom().nextFloat(),
               this.headEntity.level().getRandom().nextFloat() * 0.7F + 0.3F
            );
         this.headEntity.gameEvent(GameEvent.PROJECTILE_SHOOT);
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.ROAR_RAWR) {
         this.headEntity.playSound((SoundEvent)TFSounds.HYDRA_ROAR.get(), 1.25F, this.headEntity.level().getRandom().nextFloat() * 0.3F + 0.7F);
         this.headEntity.gameEvent(GameEvent.ENTITY_ACTION);
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.BITE_READY && this.ticksProgress == 60) {
         this.headEntity.playSound((SoundEvent)TFSounds.HYDRA_WARN.get(), 2.0F, this.headEntity.level().getRandom().nextFloat() * 0.3F + 0.7F);
      }
   }

   protected void setNeckPosition() {
      Vec3 vector = null;
      float neckRotation = 0.0F;
      if (this.headNum == 0) {
         vector = new Vec3(0.0, 3.0, -1.0);
         neckRotation = 0.0F;
      }

      if (this.headNum == 1) {
         vector = new Vec3(-1.0, 3.0, 3.0);
         neckRotation = 90.0F;
      }

      if (this.headNum == 2) {
         vector = new Vec3(1.0, 3.0, 3.0);
         neckRotation = -90.0F;
      }

      if (this.headNum == 3) {
         vector = new Vec3(-1.0, 2.5, 3.0);
         neckRotation = 135.0F;
      }

      if (this.headNum == 4) {
         vector = new Vec3(1.0, 2.5, 3.0);
         neckRotation = -135.0F;
      }

      if (this.headNum == 5) {
         vector = new Vec3(-1.0, 2.0, 5.0);
         neckRotation = 135.0F;
      }

      if (this.headNum == 6) {
         vector = new Vec3(1.0, 2.0, 5.0);
         neckRotation = -135.0F;
      }

      vector = vector.yRot(-(this.hydra.yBodyRot + neckRotation) * (float) Math.PI / 180.0F);
      this.setNeckPosition(this.hydra.getX() + vector.x(), this.hydra.getY() + vector.y(), this.hydra.getZ() + vector.z(), this.hydra.yBodyRot);
   }

   protected void setHeadPosition() {
      float neckLength = this.getCurrentNeckLength();
      float xRotation = this.getCurrentHeadXRotation();
      float yRotation = this.getCurrentHeadYRotation();
      float periodX = this.headNum != 0 && this.headNum != 3 ? (this.headNum != 1 && this.headNum != 4 ? 7.0F : 5.0F) : 20.0F;
      float periodY = this.headNum != 0 && this.headNum != 4 ? (this.headNum != 1 && this.headNum != 6 ? 5.0F : 6.0F) : 10.0F;
      float xSwing = Mth.sin(this.hydra.tickCount / periodX) * 3.0F;
      float ySwing = Mth.sin(this.hydra.tickCount / periodY) * 5.0F;
      if (this.isDead()) {
         ySwing = 0.0F;
         xSwing = 0.0F;
      }

      Vec3 vector = new Vec3(0.0, 0.0, neckLength);
      vector = vector.xRot((xRotation * (float) Math.PI + xSwing) / 180.0F);
      vector = vector.yRot(-(this.hydra.yBodyRot + yRotation + ySwing) * (float) Math.PI / 180.0F);
      double dx = this.hydra.getX() + vector.x();
      double dy = this.hydra.getY() + vector.y() + 3.0;
      double dz = this.hydra.getZ() + vector.z();
      this.headEntity.setPos(dx, dy, dz);
      this.headEntity.setMouthOpen(this.getCurrentMouthOpen());
   }

   private void executeAttacks() {
      if (this.currentState == HydraHeadContainer.State.MORTAR_SHOOTING && this.ticksProgress % 10 == 0) {
         HydraMortar mortar = new HydraMortar((EntityType<? extends HydraMortar>)TFEntities.HYDRA_MORTAR.get(), this.headEntity.level(), this.headEntity);
         if (this.targetEntity != null && !this.headEntity.canEntityBeSeen(this.targetEntity)) {
            mortar.setToBlasting();
         }

         this.headEntity
            .playSound(
               (SoundEvent)TFSounds.HYDRA_SHOOT.get(),
               10.0F,
               (this.headEntity.level().getRandom().nextFloat() - this.headEntity.level().getRandom().nextFloat()) * 0.2F + 1.0F
            );
         this.headEntity.level().addFreshEntity(mortar);
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.BITING) {
         for (Entity nearby : this.headEntity.level().getEntities(this.headEntity, this.headEntity.getBoundingBox().inflate(0.0, 1.0, 0.0))) {
            if (nearby instanceof LivingEntity living && nearby != this.hydra) {
               if (nearby instanceof Player player && player.isBlocking()) {
                  if (!player.getCooldowns().isOnCooldown(player.getUseItem().getItem())) {
                     this.headEntity
                        .level()
                        .playSound(
                           null,
                           player.blockPosition(),
                           player.getUseItem().is(Items.SHIELD)
                              ? (SoundEvent)TFSounds.WOOD_SHIELD_SHATTERS.get()
                              : (SoundEvent)TFSounds.METAL_SHIELD_SHATTERS.get(),
                           SoundSource.PLAYERS,
                           1.0F,
                           player.getVoicePitch()
                        );
                     player.getUseItem().hurtAndBreak(112, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                  }

                  player.getCooldowns().addCooldown(player.getUseItem().getItem(), 200);
                  player.stopUsingItem();
                  PacketDistributor.sendToPlayer(
                     (ServerPlayer)player,
                     new MovePlayerPacket(-this.headEntity.getDirection().getStepX() * 0.5F, 0.15F, -this.headEntity.getDirection().getStepZ() * 0.5F),
                     new CustomPacketPayload[0]
                  );
               }

               nearby.hurt(TFDamageTypes.getEntityDamageSource(living.level(), TFDamageTypes.HYDRA_BITE, this.hydra, (EntityType)TFEntities.HYDRA.get()), 48.0F);
               if (living instanceof Player player) {
                  PacketDistributor.sendToPlayer(
                     (ServerPlayer)player,
                     new MovePlayerPacket(-this.headEntity.getDirection().getStepX() * 0.5F, 0.1F, -this.headEntity.getDirection().getStepZ() * 0.5F),
                     new CustomPacketPayload[0]
                  );
               } else {
                  living.knockback(-this.headEntity.getDirection().getStepX(), 0.1F, -this.headEntity.getDirection().getStepZ());
               }
            }
         }
      }

      if (this.headEntity.getState() == HydraHeadContainer.State.FLAMING) {
         Entity target = this.getHeadLookTarget();
         if (target != null
            && target != this.headEntity.getParent()
            && (!(target instanceof HydraPart) || ((HydraPart)target).getParent() != this.headEntity.getParent())
            && !target.fireImmune()
            && target.hurt(TFDamageTypes.getEntityDamageSource(target.level(), TFDamageTypes.HYDRA_FIRE, this.hydra, (EntityType)TFEntities.HYDRA.get()), 19.0F)
            )
          {
            target.igniteForSeconds(3.0F);
         }
      }
   }

   private void setDifficultyVariables() {
      if (this.hydra.level().getDifficulty() != Difficulty.HARD) {
         FLAME_BREATH_TRACKING_SPEED = 0.04;
      } else {
         FLAME_BREATH_TRACKING_SPEED = 0.1;
      }
   }

   @Nullable
   private Entity getHeadLookTarget() {
      Entity pointedEntity = null;
      double range = 30.0;
      Vec3 srcVec = new Vec3(this.headEntity.getX(), this.headEntity.getY() + 1.0, this.headEntity.getZ());
      Vec3 lookVec = this.headEntity.getViewVector(1.0F);
      BlockHitResult raytrace = this.headEntity
         .level()
         .clip(new ClipContext(srcVec, srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range), Block.OUTLINE, Fluid.ANY, this.headEntity));
      BlockPos hitpos = raytrace != null ? raytrace.getBlockPos() : null;
      double rx = hitpos == null ? range : Math.min(range, Math.abs(this.headEntity.getX() - hitpos.getX()));
      double ry = hitpos == null ? range : Math.min(range, Math.abs(this.headEntity.getY() - hitpos.getY()));
      double rz = hitpos == null ? range : Math.min(range, Math.abs(this.headEntity.getZ() - hitpos.getZ()));
      Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
      float var9 = 3.0F;
      List<Entity> possibleList = this.headEntity
         .level()
         .getEntities(this.headEntity, this.headEntity.getBoundingBox().move(lookVec.x() * rx, lookVec.y() * ry, lookVec.z() * rz).inflate(var9, var9, var9));
      double hitDist = 0.0;

      for (Entity possibleEntity : possibleList) {
         if (possibleEntity.isPickable()
            && possibleEntity != this.headEntity
            && possibleEntity != this.necka
            && possibleEntity != this.neckb
            && possibleEntity != this.neckc) {
            float borderSize = possibleEntity.getPickRadius();
            AABB collisionBB = possibleEntity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
            if (collisionBB.contains(srcVec)) {
               if (0.0 < hitDist || hitDist == 0.0) {
                  pointedEntity = possibleEntity;
                  hitDist = 0.0;
               }
            } else if (interceptPos.isPresent()) {
               double possibleDist = srcVec.distanceTo(interceptPos.get());
               if (possibleDist < hitDist || hitDist == 0.0) {
                  pointedEntity = possibleEntity;
                  hitDist = possibleDist;
               }
            }
         }
      }

      return pointedEntity;
   }

   public void setNextState(HydraHeadContainer.State next) {
      this.nextState = next;
   }

   public void endCurrentAction() {
      this.ticksProgress = this.ticksNeeded;
   }

   private float getCurrentNeckLength() {
      float prevLength = this.stateNeckLength[this.headNum].get(this.prevState);
      float curLength = this.stateNeckLength[this.headNum].get(this.currentState);
      float progress = (float)this.ticksProgress / this.ticksNeeded;
      return Mth.clampedLerp(prevLength, curLength, progress);
   }

   private float getCurrentHeadXRotation() {
      float prevRotation = this.stateXRotations[this.headNum].get(this.prevState);
      float currentRotation = this.stateXRotations[this.headNum].get(this.currentState);
      float progress = (float)this.ticksProgress / this.ticksNeeded;
      return Mth.clampedLerp(prevRotation, currentRotation, progress);
   }

   private float getCurrentHeadYRotation() {
      float prevRotation = this.stateYRotations[this.headNum].get(this.prevState);
      float currentRotation = this.stateYRotations[this.headNum].get(this.currentState);
      float progress = (float)this.ticksProgress / this.ticksNeeded;
      return Mth.clampedLerp(prevRotation, currentRotation, progress);
   }

   protected float getCurrentMouthOpen() {
      float prevOpen = this.stateMouthOpen[this.headNum].get(this.prevState);
      float curOpen = this.stateMouthOpen[this.headNum].get(this.currentState);
      float progress = (float)this.ticksProgress / this.ticksNeeded;
      return Mth.clampedLerp(prevOpen, curOpen, progress);
   }

   protected void setNeckPosition(double startX, double startY, double startZ, float startYaw) {
      double endX = this.headEntity.getX();
      double endY = this.headEntity.getY() - 0.5;
      double endZ = this.headEntity.getZ();
      float endYaw = this.headEntity.getYRot();
      float endPitch = this.headEntity.getXRot();

      while (startYaw - endYaw < -180.0F) {
         endYaw -= 360.0F;
      }

      while (startYaw - endYaw >= 180.0F) {
         endYaw += 360.0F;
      }

      while (0.0F - endPitch < -180.0F) {
         endPitch -= 360.0F;
      }

      while (0.0F - endPitch >= 180.0F) {
         endPitch += 360.0F;
      }

      if (endPitch > 0.0F) {
         Vec3 vector = new Vec3(0.0, 0.0, -1.8).yRot(-endYaw * 3.141593F / 180.0F);
         endX += vector.x();
         endY += vector.y();
         endZ += vector.z();
      } else {
         Vec3 vector = this.headEntity.getLookAngle();
         float dist = 1.0F;
         endX -= vector.x() * dist;
         endY -= vector.y() * dist;
         endZ -= vector.z() * dist;
      }

      float factor = 0.0F;
      this.necka.setPos(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
      this.necka.setYRot(endYaw + (startYaw - endYaw) * factor);
      this.necka.setXRot(endPitch + (0.0F - endPitch) * factor);
      factor = 0.25F;
      this.neckb.setPos(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
      this.neckb.setYRot(endYaw + (startYaw - endYaw) * factor);
      this.neckb.setXRot(endPitch + (0.0F - endPitch) * factor);
      factor = 0.5F;
      this.neckc.setPos(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
      this.neckc.setYRot(endYaw + (startYaw - endYaw) * factor);
      this.neckc.setXRot(endPitch + (0.0F - endPitch) * factor);
      factor = 0.75F;
      this.neckd.setPos(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
      this.neckd.setYRot(endYaw + (startYaw - endYaw) * factor);
      this.neckd.setXRot(endPitch + (0.0F - endPitch) * factor);
      factor = 1.0F;
      this.necke.setPos(endX + (startX - endX) * factor, endY + (startY - endY) * factor, endZ + (startZ - endZ) * factor);
      this.necke.setYRot(endYaw + (startYaw - endYaw) * factor);
      this.necke.setXRot(endPitch + (0.0F - endPitch) * factor);
   }

   private void faceIdle(float yawConstraint, float pitchConstraint) {
      float angle = this.hydra.getYRot() * 3.141593F / 180.0F;
      float distance = 30.0F;
      double dx = this.hydra.getX() - Mth.sin(angle) * distance;
      double dy = this.hydra.getY() + 3.0;
      double dz = this.hydra.getZ() + Mth.cos(angle) * distance;
      this.faceVec(dx, dy, dz, yawConstraint, pitchConstraint);
   }

   public void faceEntity(@Nullable Entity entity, float yawConstraint, float pitchConstraint) {
      double yTarget;
      if (entity instanceof LivingEntity entityliving) {
         yTarget = entityliving.getY() + entityliving.getEyeHeight();
      } else {
         yTarget = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0;
      }

      this.faceVec(entity.getX(), yTarget, entity.getZ(), yawConstraint, pitchConstraint);
      this.targetX = entity.getX();
      this.targetY = entity.getY();
      this.targetZ = entity.getZ();
   }

   private void faceVec(double x, double y, double z, float yawConstraint, float pitchConstraint) {
      double xOffset = x - this.headEntity.getX();
      double zOffset = z - this.headEntity.getZ();
      double yOffset = this.headEntity.getY() + 1.0 - y;
      double distance = Mth.sqrt((float)(xOffset * xOffset + zOffset * zOffset));
      float xyAngle = (float)(Math.atan2(zOffset, xOffset) * 180.0 / Math.PI) - 90.0F;
      float zdAngle = (float)(-(Math.atan2(yOffset, distance) * 180.0 / Math.PI));
      this.headEntity.setXRot(-this.updateRotation(this.headEntity.getXRot(), zdAngle, pitchConstraint));
      this.headEntity.setYRot(this.updateRotation(this.headEntity.getYRot(), xyAngle, yawConstraint));
   }

   private float updateRotation(float current, float intended, float increment) {
      float delta = Mth.wrapDegrees(intended - current);
      if (delta > increment) {
         delta = increment;
      }

      if (delta < -increment) {
         delta = -increment;
      }

      return Mth.wrapDegrees(current + delta);
   }

   public void setTargetEntity(@Nullable Entity targetEntity) {
      this.targetEntity = targetEntity;
   }

   public void setHurtTime(int hurtTime) {
      if (this.headEntity != null) {
         this.headEntity.hurtTime = hurtTime;
      }

      this.performOnAllNecks(neck -> neck.hurtTime = hurtTime);
   }

   public boolean isActive() {
      return this.currentState != HydraHeadContainer.State.DEAD;
   }

   public boolean isDead() {
      return this.currentState == HydraHeadContainer.State.DYING || this.currentState == HydraHeadContainer.State.DEAD;
   }

   public boolean isIdle() {
      return this.currentState == HydraHeadContainer.State.IDLE && (this.nextState == NEXT_AUTOMATIC || this.nextState == HydraHeadContainer.State.IDLE);
   }

   public boolean isAttacking() {
      return this.currentState == HydraHeadContainer.State.BITE_BEGINNING
         || this.currentState == HydraHeadContainer.State.BITE_READY
         || this.currentState == HydraHeadContainer.State.BITING
         || this.currentState == HydraHeadContainer.State.FLAME_BEGINNING
         || this.currentState == HydraHeadContainer.State.FLAMING
         || this.currentState == HydraHeadContainer.State.MORTAR_BEGINNING
         || this.currentState == HydraHeadContainer.State.MORTAR_SHOOTING;
   }

   public boolean isBiting() {
      return this.currentState == HydraHeadContainer.State.BITE_BEGINNING
         || this.currentState == HydraHeadContainer.State.BITE_READY
         || this.currentState == HydraHeadContainer.State.BITING
         || this.nextState == HydraHeadContainer.State.BITE_BEGINNING;
   }

   public void addDamage(float damageAmount) {
      this.damageTaken += (int)damageAmount;
   }

   public int getDamageTaken() {
      return this.damageTaken;
   }

   public void setRespawnCounter(int count) {
      this.respawnCounter = count;
   }

   enum State {
      IDLE(10),
      BITE_BEGINNING(40),
      BITE_READY(80),
      BITING(7),
      BITE_ENDING(40),
      FLAME_BEGINNING(40),
      FLAMING(100),
      FLAME_ENDING(30),
      MORTAR_BEGINNING(40),
      MORTAR_SHOOTING(25),
      MORTAR_ENDING(30),
      DYING(70),
      DEAD(20),
      ATTACK_COOLDOWN(80),
      BORN(20),
      ROAR_START(10),
      ROAR_RAWR(50);

      private static final Map<HydraHeadContainer.State, HydraHeadContainer.State> NEXT_STATE;
      public final int duration;

      State(int duration) {
         this.duration = duration;
      }

      static {
         EnumMap<HydraHeadContainer.State, HydraHeadContainer.State> b = new EnumMap<>(HydraHeadContainer.State.class);
         b.put(IDLE, IDLE);
         b.put(BITE_BEGINNING, BITE_READY);
         b.put(BITE_READY, BITING);
         b.put(BITING, BITE_ENDING);
         b.put(BITE_ENDING, ATTACK_COOLDOWN);
         b.put(FLAME_BEGINNING, FLAMING);
         b.put(FLAMING, FLAME_ENDING);
         b.put(FLAME_ENDING, ATTACK_COOLDOWN);
         b.put(MORTAR_BEGINNING, MORTAR_SHOOTING);
         b.put(MORTAR_SHOOTING, MORTAR_ENDING);
         b.put(MORTAR_ENDING, ATTACK_COOLDOWN);
         b.put(ATTACK_COOLDOWN, IDLE);
         b.put(DYING, DEAD);
         b.put(DEAD, DEAD);
         b.put(BORN, ROAR_START);
         b.put(ROAR_START, ROAR_RAWR);
         b.put(ROAR_RAWR, IDLE);
         NEXT_STATE = ImmutableMap.copyOf(b);
      }
   }
}
