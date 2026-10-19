package io.github.Memoires.mysticism.entity.golems;

import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ClayGolemEntity extends PlayerLikeEntity {
   private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER = SynchedEntityData.defineId(ClayGolemEntity.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final String NBT_OWNER = "Owner";

   public ClayGolemEntity(EntityType<? extends ClayGolemEntity> type, Level level) {
      super(type, level);
      this.setNoAi(true);
   }

   public static Builder setAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.ATTACK_KNOCKBACK, 1.0)
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.1)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
         .add(Attributes.STEP_HEIGHT, 1.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_OWNER, Optional.empty());
   }

   public void setOwnerUUID(@Nullable UUID id) {
      this.entityData.set(DATA_OWNER, Optional.ofNullable(id));
   }

   public Optional<UUID> getOwnerUuid() {
      return (Optional<UUID>)this.entityData.get(DATA_OWNER);
   }

   @Nullable
   public Player getOwnerPlayer() {
      return this.level() instanceof ServerLevel sl ? this.getOwnerUuid().<Player>map(sl::getPlayerByUUID).orElse(null) : null;
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.getOwnerUuid().ifPresent(u -> tag.putUUID("Owner", u));
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.hasUUID("Owner")) {
         this.setOwnerUUID(tag.getUUID("Owner"));
      }
   }
}
