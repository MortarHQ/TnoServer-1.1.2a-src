package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.network.UpdateCameraPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SoulitSpectator extends ThrowableItemProjectile {
   private static final String TAG_NO_MOVEMENT = "no_movement";
   private static final TicketType<ChunkPos> CHUNK_LOADING_TICKET_TYPE = TicketType.create(
      "eternal_starlight:soulit_spectator", Comparator.comparingLong(ChunkPos::toLong)
   );
   private boolean noMovement;
   private final List<ChunkPos> loadedChunks = new ArrayList<>();

   public SoulitSpectator(EntityType<? extends SoulitSpectator> entityType, Level level) {
      super(entityType, level);
   }

   public SoulitSpectator(Level level, LivingEntity livingEntity) {
      super(ESEntities.SOULIT_SPECTATOR.get(), livingEntity, level);
   }

   public SoulitSpectator(Level level, double x, double y, double z) {
      super(ESEntities.SOULIT_SPECTATOR.get(), x, y, z, level);
   }

   public void tick() {
      this.setNoGravity(true);
      super.tick();
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         if (!this.loadedChunks.contains(this.chunkPosition())) {
            serverLevel.getChunkSource().addRegionTicket(CHUNK_LOADING_TICKET_TYPE, this.chunkPosition(), 3, this.chunkPosition());
            this.loadedChunks.add(this.chunkPosition());
         }

         if (this.tickCount == 40 && this.getOwner() instanceof ServerPlayer serverPlayer) {
            ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateCameraPacket(this.getId()));
            serverLevel.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 5, 0.3, 0.3, 0.3, 0.0);
         }

         if (this.tickCount > 140) {
            this.noMovement = true;
         }

         if (this.tickCount > 200) {
            for (ChunkPos chunkPos : this.loadedChunks) {
               serverLevel.getChunkSource().removeRegionTicket(CHUNK_LOADING_TICKET_TYPE, chunkPos, 3, chunkPos);
            }

            if (this.getOwner() instanceof ServerPlayer serverPlayer) {
               ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateCameraPacket(-1));
            }

            serverLevel.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 5, 0.3, 0.3, 0.3, 0.0);
            this.discard();
         }
      }
   }

   public float getViewXRot(float partialTicks) {
      return this.getOwner() instanceof LivingEntity livingEntity ? livingEntity.getViewXRot(partialTicks) : super.getViewXRot(partialTicks);
   }

   public float getViewYRot(float partialTicks) {
      return this.getOwner() instanceof LivingEntity livingEntity ? livingEntity.getViewYRot(partialTicks) : super.getViewYRot(partialTicks);
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      this.noMovement = true;
   }

   public void setDeltaMovement(Vec3 vec3) {
      super.setDeltaMovement(this.noMovement ? Vec3.ZERO : vec3);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.noMovement = compoundTag.getBoolean("no_movement");
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("no_movement", this.noMovement);
   }

   @NotNull
   protected Item getDefaultItem() {
      return ESItems.SOULIT_SPECTATOR.get();
   }
}
