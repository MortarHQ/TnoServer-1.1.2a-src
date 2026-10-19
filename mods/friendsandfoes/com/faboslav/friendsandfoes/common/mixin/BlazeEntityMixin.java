package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.BlazeEntityAccess;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blaze.class)
public abstract class BlazeEntityMixin extends BlazeLivingEntityMixin implements BlazeEntityAccess {
   private static final String WILDFIRE_UUID_NBT_NAME = "WildfireUuid";
   @Nullable
   private UUID friendsandfoes_wildfireUuid = null;

   protected BlazeEntityMixin(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
   }

   @Override
   public void friendsandfoes_writeCustomDataToNbt(CompoundTag nbt, CallbackInfo ci) {
      VersionedNbt.putUUID(nbt, "WildfireUuid", this.friendsandfoes_getWildfireUuid());
   }

   @Override
   public void friendsandfoes_writeCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci) {
      this.friendsandfoes_setWildfireUuid(VersionedNbt.getUUID(nbt, "WildfireUuid"));
   }

   @Override
   public void friendsandfoes_onDeath(DamageSource damageSource, CallbackInfo ci) {
      if (this.level() instanceof ServerLevel) {
         WildfireEntity wildfireEntity = this.friendsandfoes_getWildfire();
         if (wildfireEntity != null) {
            wildfireEntity.setSummonedBlazesCount(wildfireEntity.getSummonedBlazesCount() - 1);
         }
      }
   }

   @Nullable
   public UUID friendsandfoes_getWildfireUuid() {
      return this.friendsandfoes_wildfireUuid;
   }

   public void friendsandfoes_setWildfireUuid(@Nullable UUID uuid) {
      this.friendsandfoes_wildfireUuid = uuid;
   }

   @Override
   public void friendsandfoes_setWildfire(WildfireEntity wildfire) {
      this.friendsandfoes_setWildfireUuid(wildfire.getUUID());
   }

   @Nullable
   @Override
   public WildfireEntity friendsandfoes_getWildfire() {
      try {
         ServerLevel serverWorld = (ServerLevel)this.level();
         UUID uUID = this.friendsandfoes_getWildfireUuid();
         return uUID == null ? null : (WildfireEntity)serverWorld.getEntity(uUID);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }
}
