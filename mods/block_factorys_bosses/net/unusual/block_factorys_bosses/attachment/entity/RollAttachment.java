package net.unusual.block_factorys_bosses.attachment.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.UnknownNullability;
import org.joml.Vector2d;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RollAttachment implements INBTSerializable<CompoundTag> {
   private static final int DEFAULT_ROLL_DURATION = 14;
   private static final int ROLL_I_FRAMES = 7;
   public static final int ROLL_DELAY = 5;
   public static final double ROLL_DISTANCE = 6.0;
   protected static final ResourceLocation PATH = BossesRise.prefix("animations/player/rolls.animation.json");
   protected static final PlayerAnimationHandler.PlayerAnimation FORWARD_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_forward");
   protected static final PlayerAnimationHandler.PlayerAnimation BACKWARD_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_back");
   protected static final PlayerAnimationHandler.PlayerAnimation LEFT_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_left");
   protected static final PlayerAnimationHandler.PlayerAnimation RIGHT_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_right");
   protected RollAttachment.RollType direction = RollAttachment.RollType.FORWARD;
   protected int rollDuration = 14;
   protected int roll = 0;
   protected List<Float> cooldowns = new ArrayList<>();

   public static RollAttachment fromPlayer(Player player) {
      return (RollAttachment)player.getData(BossesRiseDataAttachments.ROLL_ATTACHMENT);
   }

   public int rollCount() {
      return this.cooldowns.size();
   }

   public float getCooldown(int i) {
      return this.cooldowns.get(i);
   }

   public void startCooldown(int i) {
      this.cooldowns.set(i, 1.0F);
      Collections.sort(this.cooldowns);
   }

   public int getRoll() {
      return this.roll;
   }

   public boolean isRolling() {
      return this.roll > 0;
   }

   public boolean isInvulnerable() {
      return this.roll > 7;
   }

   public int getRollDuration() {
      return this.rollDuration;
   }

   public RollAttachment.RollType getDirection() {
      return this.direction;
   }

   public boolean startRoll(Player player, float leftImpulse, float forwardImpulse) {
      if (this.roll > -5) {
         return false;
      }

      if (!player.onGround()) {
         return false;
      }

      if (!player.isSilent()) {
         player.playSound((SoundEvent)BossesRiseSounds.ROLL.value(), 1.0F, 1.0F);
      }

      player.setForcedPose(Pose.CROUCHING);
      this.rollDuration = 14;
      this.roll = this.rollDuration;
      float speed = player.getSpeed();
      float left = speed * leftImpulse;
      float forward = speed * forwardImpulse;
      float sin = Mth.sin(player.getYRot() * (float) (Math.PI / 180.0));
      float cos = Mth.cos(player.getYRot() * (float) (Math.PI / 180.0));
      Vector2d move = new Vector2d(left * cos - forward * sin, forward * cos + left * sin);
      if (move.lengthSquared() <= 0.001F) {
         move = new Vector2d(-sin, cos);
         this.direction = RollAttachment.RollType.FORWARD;
      } else if (Math.abs(forwardImpulse) >= Math.abs(leftImpulse)) {
         this.direction = forwardImpulse > 0.0F ? RollAttachment.RollType.FORWARD : RollAttachment.RollType.BACKWARD;
      } else {
         this.direction = leftImpulse > 0.0F ? RollAttachment.RollType.LEFT : RollAttachment.RollType.RIGHT;
      }

      this.move(player, move);

      PlayerAnimationHandler.PlayerAnimation animation = switch (this.direction) {
         case FORWARD -> FORWARD_ROLL;
         case BACKWARD -> BACKWARD_ROLL;
         case LEFT -> LEFT_ROLL;
         case RIGHT -> RIGHT_ROLL;
      };
      PlayerAnimationHandler.fromPlayer(player).startAnimation(player, animation, this.rollDuration);
      return true;
   }

   public void tick(Player player) {
      if (this.isRolling()) {
         player.setShiftKeyDown(false);
         if (player.onGround()) {
            double radians = Math.toRadians(180.0 - (Math.atan2(player.getDeltaMovement().x(), player.getDeltaMovement().z()) * (-180.0 / Math.PI) + 180.0));
            this.move(player, new Vector2d(Math.sin(radians) * 0.4, Math.cos(radians) * 0.4));
         }
      }

      int stat = (Integer)ServerConfiguration.DEFAULT_ROLL_COUNT.get() + (int)player.getAttributeValue(BossesRiseAttributes.ROLL_COUNT);
      if (stat < 0) {
         stat = 0;
      }

      int diff = stat - this.rollCount();
      if (diff > 0) {
         for (int i = 0; i < diff; i++) {
            this.cooldowns.add(0.0F);
         }

         Collections.sort(this.cooldowns);
      } else if (diff < 0) {
         for (int i = 0; i > diff; i--) {
            this.cooldowns.removeLast();
         }

         Collections.sort(this.cooldowns);
      }

      if (this.roll > -5) {
         boolean wasRolling = this.isRolling();
         this.roll--;
         if (wasRolling && !this.isRolling()) {
            Vec3 delta = player.getDeltaMovement();
            Vector2d horizontal = new Vector2d(delta.x, delta.z).normalize().mul(player.getAttributeValue(Attributes.MOVEMENT_SPEED));
            player.setDeltaMovement(horizontal.x, delta.y, horizontal.y);
            player.hasImpulse = true;
            if (player.getForcedPose() == Pose.CROUCHING) {
               player.setForcedPose(null);
            }
         }
      } else {
         int rollCount = this.rollCount();
         if (rollCount < 1) {
            return;
         }

         for (int i = 0; i < rollCount; i++) {
            if (this.cooldowns.get(i) > 0.0F) {
               float cooldownInTicks = ((Integer)ServerConfiguration.ROLL_COOLDOWN.get()).intValue();
               if (cooldownInTicks < 0.0F) {
                  this.cooldowns.set(i, 0.0F);
               } else {
                  this.cooldowns.set(i, this.cooldowns.get(i) - 1.0F / cooldownInTicks);
               }
               break;
            }
         }
      }
   }

   private void move(Player player, Vector2d move) {
      Vec3 delta = player.getDeltaMovement();
      Vector2d horizontal = new Vector2d(delta.x + move.x, delta.z + move.y)
         .normalize()
         .mul(player.getAttributeValue(Attributes.MOVEMENT_SPEED) * (6.0 * (10.0F / this.rollDuration)));
      player.setDeltaMovement(horizontal.x, delta.y, horizontal.y);
      player.hasImpulse = true;
      float rot = player.yHeadRot;
      player.setYRot(player.getYHeadRot());
      player.yRotO = rot;
      player.setYBodyRot(player.getYHeadRot());
      player.yBodyRotO = rot;
   }

   public @UnknownNullability CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      tag.putInt("direction", this.direction.ordinal());
      tag.putInt("duration", this.rollDuration);
      tag.putInt("roll", this.roll);
      tag.putInt("roll_count", this.rollCount());

      for (int i = 0; i < this.rollCount(); i++) {
         tag.putFloat("cooldown_" + i, this.cooldowns.get(i));
      }

      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      this.direction = RollAttachment.RollType.values()[tag.getInt("direction")];
      this.rollDuration = tag.getInt("duration");
      this.roll = tag.getInt("roll");
      this.cooldowns = new ArrayList<>();
      int count = tag.getInt("roll_count");

      for (int i = 0; i < count; i++) {
         this.cooldowns.add(tag.getFloat("cooldown_" + i));
      }
   }

   @SubscribeEvent
   private static void tickEvent(Post event) {
      fromPlayer(event.getEntity()).tick(event.getEntity());
   }

   @SubscribeEvent
   private static void preDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && fromPlayer(player).isInvulnerable()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   private static void preImpact(ProjectileImpactEvent event) {
      if (event.getEntity() instanceof Player player && fromPlayer(player).isInvulnerable()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   private static void preEffect(Applicable event) {
      if (event.getEntity() instanceof Player player && fromPlayer(player).isInvulnerable()) {
         event.setResult(Result.DO_NOT_APPLY);
      }
   }

   public enum RollType implements StringRepresentable {
      FORWARD,
      BACKWARD,
      LEFT,
      RIGHT;

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
