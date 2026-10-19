package com.notunanancyowen.spears.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.notunanancyowen.spears.PacketCodecHelper;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.dataholders.SpearUser;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public record KineticWeapon(
   float hitboxMargin,
   int contactCooldownTicks,
   int delayTicks,
   Optional<KineticWeapon.Condition> dismountConditions,
   Optional<KineticWeapon.Condition> knockbackConditions,
   Optional<KineticWeapon.Condition> damageConditions,
   float forwardMovement,
   float damageMultiplier,
   Optional<Holder<SoundEvent>> sound,
   Optional<Holder<SoundEvent>> hitSound
) {
   public static final Codec<KineticWeapon> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Spears.rangedInclusiveFloat(0.0F, 1.0F).optionalFieldOf("hitbox_margin", 0.3F).forGetter(KineticWeapon::hitboxMargin),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("contact_cooldown_ticks", 10).forGetter(KineticWeapon::contactCooldownTicks),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("delay_ticks", 0).forGetter(KineticWeapon::delayTicks),
            KineticWeapon.Condition.CODEC.optionalFieldOf("dismount_conditions").forGetter(KineticWeapon::dismountConditions),
            KineticWeapon.Condition.CODEC.optionalFieldOf("knockback_conditions").forGetter(KineticWeapon::knockbackConditions),
            KineticWeapon.Condition.CODEC.optionalFieldOf("damage_conditions").forGetter(KineticWeapon::damageConditions),
            Codec.FLOAT.optionalFieldOf("forward_movement", 0.0F).forGetter(KineticWeapon::forwardMovement),
            Codec.FLOAT.optionalFieldOf("damage_multiplier", 1.0F).forGetter(KineticWeapon::damageMultiplier),
            SoundEvent.CODEC.optionalFieldOf("sound").forGetter(KineticWeapon::sound),
            SoundEvent.CODEC.optionalFieldOf("hit_sound").forGetter(KineticWeapon::hitSound)
         )
         .apply(instance, KineticWeapon::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, KineticWeapon> PACKET_CODEC = PacketCodecHelper.tuple(
      ByteBufCodecs.FLOAT,
      KineticWeapon::hitboxMargin,
      ByteBufCodecs.VAR_INT,
      KineticWeapon::contactCooldownTicks,
      ByteBufCodecs.VAR_INT,
      KineticWeapon::delayTicks,
      KineticWeapon.Condition.PACKET_CODEC.apply(ByteBufCodecs::optional),
      KineticWeapon::dismountConditions,
      KineticWeapon.Condition.PACKET_CODEC.apply(ByteBufCodecs::optional),
      KineticWeapon::knockbackConditions,
      KineticWeapon.Condition.PACKET_CODEC.apply(ByteBufCodecs::optional),
      KineticWeapon::damageConditions,
      ByteBufCodecs.FLOAT,
      KineticWeapon::forwardMovement,
      ByteBufCodecs.FLOAT,
      KineticWeapon::damageMultiplier,
      SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional),
      KineticWeapon::sound,
      SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional),
      KineticWeapon::hitSound,
      KineticWeapon::new
   );

   public static Vec3 getAmplifiedMovement(Entity entity) {
      if (!(entity instanceof Player) && entity.isPassenger()) {
         entity = entity.getRootVehicle();
      }

      return (entity instanceof Player p ? p.getKnownMovement() : entity.position().subtract(entity.xo, entity.yo, entity.zo)).scale(20.0);
   }

   public void playSound(Entity entity) {
      if (entity instanceof Player p) {
         this.sound
            .ifPresent(
               sound -> entity.getCommandSenderWorld().playSound(p, entity.getX(), entity.getY(), entity.getZ(), sound, entity.getSoundSource(), 1.0F, 1.0F)
            );
      }
   }

   public void playHitSound(Entity entity) {
      this.hitSound
         .ifPresent(
            sound -> entity.getCommandSenderWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, entity.getSoundSource(), 1.0F, 1.0F)
         );
   }

   public int getUseTicks() {
      return this.delayTicks + this.damageConditions.map(KineticWeapon.Condition::maxDurationTicks).orElse(0);
   }

   public void usageTick(ItemStack stack, int remainingUseTicks, LivingEntity user, EquipmentSlot slot) {
      int i = stack.getUseDuration(user) - remainingUseTicks;
      if (i >= this.delayTicks && user instanceof SpearUser s) {
         i -= this.delayTicks;
         Vec3 vec3d = user.getLookAngle();
         double d = vec3d.dot(getAmplifiedMovement(user));
         float f = user instanceof Player ? 1.0F : 0.2F;
         float g = user instanceof Player ? 1.0F : 0.5F;
         double e = user.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
         boolean bl = false;
         float minReach = 0.0F;
         float maxReach = 3.0F;
         if (stack.get(Spears.ATTACK_RANGE) instanceof AttackRange(float reachMin, float reachMax)) {
            minReach = reachMin;
            maxReach = reachMax;
         }

         for (EntityHitResult entityHitResult : Spears.collectPiercingCollisions(
            user, g * minReach, g * maxReach, this.hitboxMargin, target -> PiercingWeapon.canHit(user, target)
         )) {
            Entity entity = entityHitResult.getEntity();
            boolean bl2 = s.isInPiercingCooldown(entity, this.contactCooldownTicks);
            s.startPiercingCooldown(entity);
            if (!bl2) {
               double j = Math.max(0.0, d - vec3d.dot(getAmplifiedMovement(entity)));
               boolean bl3 = this.dismountConditions.isPresent() && this.dismountConditions.get().isSatisfied(i, d, j, f);
               boolean bl4 = this.knockbackConditions.isPresent() && this.knockbackConditions.get().isSatisfied(i, d, j, f);
               boolean bl5 = this.damageConditions.isPresent() && this.damageConditions.get().isSatisfied(i, d, j, f);
               if (bl3 || bl4 || bl5) {
                  float k = (float)e + Mth.floor(j * this.damageMultiplier);
                  bl |= s.pierce(slot, entity, k, bl5, bl4, bl3);
               }
            }
         }

         if (bl) {
            this.playHitSound(user);
            user.getCommandSenderWorld().broadcastEntityEvent(user, (byte)2);
            if (user instanceof ServerPlayer serverPlayerEntity) {
               Spears.SPEARED_MOBS.spearMob(serverPlayerEntity, s.countSpearedMobs());
            }
         }
      }
   }

   public record Condition(int maxDurationTicks, float minSpeed, float minRelativeSpeed) {
      public static final Codec<KineticWeapon.Condition> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("max_duration_ticks").forGetter(KineticWeapon.Condition::maxDurationTicks),
               Codec.FLOAT.optionalFieldOf("min_speed", 0.0F).forGetter(KineticWeapon.Condition::minSpeed),
               Codec.FLOAT.optionalFieldOf("min_relative_speed", 0.0F).forGetter(KineticWeapon.Condition::minRelativeSpeed)
            )
            .apply(instance, KineticWeapon.Condition::new)
      );
      public static final StreamCodec<ByteBuf, KineticWeapon.Condition> PACKET_CODEC = StreamCodec.composite(
         ByteBufCodecs.VAR_INT,
         KineticWeapon.Condition::maxDurationTicks,
         ByteBufCodecs.FLOAT,
         KineticWeapon.Condition::minSpeed,
         ByteBufCodecs.FLOAT,
         KineticWeapon.Condition::minRelativeSpeed,
         KineticWeapon.Condition::new
      );

      public boolean isSatisfied(int durationTicks, double speed, double relativeSpeed, double minSpeedMultiplier) {
         return durationTicks <= this.maxDurationTicks
            && speed >= this.minSpeed * minSpeedMultiplier
            && relativeSpeed >= this.minRelativeSpeed * minSpeedMultiplier;
      }

      public static Optional<KineticWeapon.Condition> ofMinSpeed(int maxDurationTicks, float minSpeed) {
         return Optional.of(new KineticWeapon.Condition(maxDurationTicks, minSpeed, 0.0F));
      }

      public static Optional<KineticWeapon.Condition> ofMinRelativeSpeed(int maxDurationTicks, float minRelativeSpeed) {
         return Optional.of(new KineticWeapon.Condition(maxDurationTicks, 0.0F, minRelativeSpeed));
      }
   }
}
