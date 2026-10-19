package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityUmvuthanaFollowerToPlayer extends EntityUmvuthanaFollower<Player> {
   private static final EntityDataAccessor<ItemStack> MASK_STORED = SynchedEntityData.defineId(
      EntityUmvuthanaFollowerToPlayer.class, EntityDataSerializers.ITEM_STACK
   );
   public Vec3[] feetPos;

   public EntityUmvuthanaFollowerToPlayer(EntityType<? extends EntityUmvuthanaFollowerToPlayer> type, Level world) {
      this(type, world, null);
   }

   public EntityUmvuthanaFollowerToPlayer(EntityType<? extends EntityUmvuthanaFollowerToPlayer> type, Level world, Player leader) {
      super(type, world, Player.class, leader);
      this.xpReward = 0;
      if (world.isClientSide) {
         this.feetPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(MASK_STORED, new ItemStack((ItemLike)ItemHandler.UMVUTHANA_MASK_FURY.get(), 1));
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.random.nextFloat() < 0.5 ? null : super.getAmbientSound();
   }

   @Override
   public int getAttackChance() {
      return this.getPackSize() < 0 ? super.getAttackChance() : 8 * this.getPackSize() + 30;
   }

   @Override
   public int getAttackCooldown() {
      return this.getPackSize() < 0 ? super.getAttackCooldown() : 8 * this.getPackSize() + 30;
   }

   @Override
   public void tick() {
      if (this.tickCount > 30 && (this.getLeader() == null || this.getLeader().getHealth() <= 0.0F)) {
         this.deactivate();
      }

      super.tick();
      if (this.level().isClientSide && this.feetPos != null && this.feetPos.length > 0 && this.active) {
         this.feetPos[0] = this.position().add(0.0, 0.05F, 0.0);
         if (this.tickCount % 10 == 0) {
            AdvancedParticleBase.spawnParticle(
               this.level(),
               ParticleHandler.RING2,
               this.feetPos[0].x(),
               this.feetPos[0].y(),
               this.feetPos[0].z(),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               1.5,
               1.0,
               0.8745098F,
               0.25882354F,
               1.0,
               1.0,
               15.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PinLocation(this.feetPos),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 10.0F), false
                  )
               }
            );
         }
      }
   }

   protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
      if (playerIn == this.leader) {
         this.deactivate();
      }

      return super.mobInteract(playerIn, hand);
   }

   private void deactivate() {
      if (this.getActive() && this.getActiveAbilityType() != DEACTIVATE_ABILITY) {
         AbilityHandler.INSTANCE.sendAbilityMessage(this, DEACTIVATE_ABILITY);
         this.playSound((SoundEvent)MMSounds.ENTITY_UMVUTHANA_RETRACT.get(), 1.0F, 1.0F);
      }
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return MowzieEntity.createAttributes().add(Attributes.ATTACK_DAMAGE, 7.0).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.STEP_HEIGHT, 1.0);
   }

   @Override
   protected int getGroupCircleTick() {
      PlayerData data = this.getPlayerData();
      return data == null ? 0 : data.getPackCircleTick();
   }

   @Override
   protected int getPackSize() {
      PlayerData data = this.getPlayerData();
      return data == null ? 0 : data.getPackSize();
   }

   @Override
   protected void addAsPackMember() {
      PlayerData data = this.getPlayerData();
      if (data != null) {
         data.addPackMember(this);
      }
   }

   @Override
   protected void removeAsPackMember() {
      PlayerData data = this.getPlayerData();
      if (data != null) {
         data.removePackMember(this);
      }
   }

   @Nullable
   private PlayerData getPlayerData() {
      return this.leader instanceof Player ? DataHandler.getData(this.leader, DataHandler.PLAYER_DATA) : null;
   }

   @Override
   public boolean isUmvuthiDevoted() {
      return false;
   }

   @NotNull
   @Override
   protected ResourceKey<LootTable> getDefaultLootTable() {
      return BuiltInLootTables.EMPTY;
   }

   public boolean isTeleportFriendlyBlock(int x, int z, int y, int xOffset, int zOffset) {
      BlockPos blockpos = new BlockPos(x + xOffset, y - 1, z + zOffset);
      BlockState iblockstate = this.level().getBlockState(blockpos);
      return iblockstate.isValidSpawn(this.level(), blockpos, this.getType())
         && this.level().isEmptyBlock(blockpos.above())
         && this.level().isEmptyBlock(blockpos.above(2));
   }

   public ItemStack getStoredMask() {
      return (ItemStack)this.getEntityData().get(MASK_STORED);
   }

   public void setStoredMask(ItemStack mask) {
      this.getEntityData().set(MASK_STORED, mask);
      this.setItemSlot(EquipmentSlot.HEAD, mask);
   }

   @Override
   protected ItemStack getDeactivatedMask(ItemUmvuthanaMask mask) {
      return this.getStoredMask();
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      CompoundTag compoundnbt = compound.getCompound("storedMask");
      this.setStoredMask(ItemStack.parse(this.registryAccess(), compoundnbt).orElse(ItemStack.EMPTY));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (!this.getStoredMask().isEmpty()) {
         compound.put("storedMask", this.getStoredMask().save(this.registryAccess(), new CompoundTag()));
      }
   }
}
