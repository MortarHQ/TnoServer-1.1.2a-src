package twilightforest.components.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.network.ParticlePacket;

public class FortificationShieldAttachment {
   public static final Codec<FortificationShieldAttachment> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("temporary_shields").forGetter(o -> o.temporaryShields),
            Codec.INT.fieldOf("permanent_shields").forGetter(o -> o.permanentShields)
         )
         .apply(instance, FortificationShieldAttachment::new)
   );
   public static final StreamCodec<FriendlyByteBuf, FortificationShieldAttachment> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, o -> o.temporaryShields, ByteBufCodecs.INT, o -> o.permanentShields, FortificationShieldAttachment::new
   );
   private int temporaryShields;
   private int permanentShields;
   private int timer;

   public FortificationShieldAttachment() {
      this(0, 0);
   }

   public FortificationShieldAttachment(int temporaryShields, int permanentShields) {
      this.temporaryShields = Math.max(temporaryShields, 0);
      this.permanentShields = Math.max(permanentShields, 0);
      this.resetTimer();
   }

   public void tick(LivingEntity entity) {
      if (this.temporaryShieldsLeft() > 0 && !(entity instanceof Player player && player.getAbilities().invulnerable)) {
         if (this.timer <= 0) {
            this.breakShield(entity, true);
         } else if (this.checkLichCrownBonus(entity)) {
            this.timer--;
         }
      }
   }

   private boolean checkLichCrownBonus(LivingEntity entity) {
      return !entity.getItemBySlot(EquipmentSlot.HEAD).is(TFItems.MYSTIC_CROWN) || entity.tickCount % 3 != 0;
   }

   public int shieldsLeft() {
      return this.temporaryShields + this.permanentShields;
   }

   public int temporaryShieldsLeft() {
      return this.temporaryShields;
   }

   public int permanentShieldsLeft() {
      return this.permanentShields;
   }

   public void breakShield(LivingEntity entity, boolean expired) {
      if (this.temporaryShields > 0) {
         this.temporaryShields--;
         this.resetTimer();
      } else if (this.permanentShields > 0) {
         this.permanentShields--;
      }

      if (entity instanceof ServerPlayer player && !expired) {
         player.awardStat((ResourceLocation)TFStats.TF_SHIELDS_BROKEN.get());
      }

      entity.level()
         .playSound(
            null,
            entity.blockPosition(),
            expired ? (SoundEvent)TFSounds.SHIELD_EXPIRE.get() : (SoundEvent)TFSounds.SHIELD_BREAK.get(),
            SoundSource.PLAYERS,
            1.0F,
            (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.3F + 1.0F
         );
      entity.setData(TFDataAttachments.FORTIFICATION_SHIELDS, this);
   }

   public static void addShieldBreakParticles(DamageSource src, LivingEntity entity) {
      ParticlePacket particlePacket = new ParticlePacket();
      Vec3 pos = src.getSourcePosition();
      if (src.getDirectEntity() instanceof LivingEntity living) {
         pos = living.getEyePosition();
      }

      if (src.getEntity() instanceof TraceableEntity traceable && traceable.getOwner() instanceof LivingEntity living) {
         pos = living.getEyePosition();
      }

      if (pos != null) {
         Vec3 lichPos = entity.position().add(0.0, entity.getBbHeight() * 0.65, 0.0);
         Vec3 offset = pos.subtract(lichPos).multiply(1.0, 0.0, 1.0).normalize();
         pos = lichPos.add(offset.scale(0.55));
         double sizeRange = 0.85;

         for (int j = 0; j < 16; j++) {
            double horizontal = entity.getRandom().nextDouble() - 0.5;
            double x = sizeRange * offset.z * horizontal;
            double y = sizeRange * (entity.getRandom().nextDouble() - 0.5);
            double z = sizeRange * offset.x * -horizontal;
            particlePacket.queueParticle((ParticleOptions)TFParticleType.SHIELD_BREAK.get(), false, pos.x + x, pos.y + y, pos.z + z, x * 0.5, y * 0.5, z * 0.5);
         }
      } else {
         pos = entity.position().add(0.0, entity.getBbHeight() * 0.65, 0.0);

         for (int j = 0; j < 16; j++) {
            double x = entity.getRandom().nextDouble() - 0.5;
            double y = (entity.getRandom().nextDouble() - 0.5) * 0.25;
            double z = entity.getRandom().nextDouble() - 0.5;
            particlePacket.queueParticle(
               (ParticleOptions)TFParticleType.SHIELD_BREAK.get(), false, pos.x + x, pos.y + y, pos.z + z, x * 0.33, y * 0.33, z * 0.33
            );
         }
      }

      PacketDistributor.sendToPlayersTrackingEntity(entity, particlePacket, new CustomPacketPayload[0]);
      if (entity instanceof ServerPlayer player) {
         PacketDistributor.sendToPlayer(player, particlePacket, new CustomPacketPayload[0]);
      }
   }

   public void setShields(LivingEntity entity, int amount, boolean temp) {
      if (temp) {
         this.temporaryShields = Math.clamp(amount, 0, 115);
         this.resetTimer();
      } else {
         this.permanentShields = Math.clamp(amount, 0, 115);
      }

      entity.setData(TFDataAttachments.FORTIFICATION_SHIELDS, this);
   }

   public void addShields(LivingEntity entity, int amount, boolean temp) {
      if (temp) {
         if (this.temporaryShields <= 0) {
            this.resetTimer();
         }

         this.temporaryShields = Math.clamp(this.temporaryShields + amount, 0, 115);
      } else {
         this.permanentShields = Math.clamp(this.permanentShields + amount, 0, 115);
      }

      entity.setData(TFDataAttachments.FORTIFICATION_SHIELDS, this);
   }

   private void resetTimer() {
      this.timer = 240;
   }
}
