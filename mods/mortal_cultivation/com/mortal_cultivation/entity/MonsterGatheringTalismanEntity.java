package com.mortal_cultivation.entity;

import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MonsterGatheringTalismanEntity extends Entity {
   private static final double ATTRACTION_RANGE = 15.0;
   private static final double ATTRACTION_STRENGTH = 0.06;
   private static final float HEIGHT_OFFSET = 0.1F;
   private static final int MAX_LIFETIME = 48000;
   private static final int EXTENSION_TIME = 12000;
   private int remainingTicks = 48000;
   private static final String PROGRESS_CHAR = "■";
   private static final int PROGRESS_BAR_LENGTH = 10;
   private static final String REMAINING_TIME_KEY = "entity.mortal_cultivation.monster_gathering_talisman.remaining_time";

   public MonsterGatheringTalismanEntity(EntityType<? extends MonsterGatheringTalismanEntity> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = true;
      this.setPos(this.getX(), this.getY() + 0.1F, this.getZ());
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.tickCount == 1) {
            CompoundTag persistentData = this.getPersistentData();
            if (persistentData.contains("RemainingTicks")) {
               this.remainingTicks = persistentData.getInt("RemainingTicks");
               persistentData.remove("RemainingTicks");
               this.updateProgressBar();
            }
         }

         if (this.remainingTicks <= 0) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            this.discard();
            return;
         }

         this.remainingTicks--;
         if (this.remainingTicks % 20 == 0) {
            this.updateProgressBar();
         }

         this.checkBlockOverlap();
         AABB attractionBox = new AABB(this.getX() - 15.0, this.getY() - 15.0, this.getZ() - 15.0, this.getX() + 15.0, this.getY() + 15.0, this.getZ() + 15.0);

         for (Entity entity : this.level()
            .getEntities(this, attractionBox, entityx -> entityx instanceof LivingEntity && !(entityx instanceof Player) && entityx != this)) {
            Vec3 direction = new Vec3(this.getX() - entity.getX(), this.getY() + 0.3 - entity.getY(), this.getZ() - entity.getZ()).normalize();
            double distance = entity.distanceTo(this);
            double strength = Math.max(0.02, 0.06 * (1.0 - distance / 15.0));
            Vec3 motion = direction.scale(strength);
            entity.setDeltaMovement(entity.getDeltaMovement().add(motion));
         }
      }
   }

   private void updateProgressBar() {
      float percentage = this.remainingTicks / 48000.0F;
      ChatFormatting color;
      if (percentage > 0.7F) {
         color = ChatFormatting.GREEN;
      } else if (percentage > 0.4F) {
         color = ChatFormatting.YELLOW;
      } else if (percentage > 0.2F) {
         color = ChatFormatting.GOLD;
      } else {
         color = ChatFormatting.DARK_RED;
      }

      int filledChars = Math.round(percentage * 10.0F);
      StringBuilder progressBar = new StringBuilder();

      for (int i = 0; i < 10; i++) {
         if (i < filledChars) {
            progressBar.append("■");
         } else {
            progressBar.append("□");
         }
      }

      MutableComponent remainingTimeText = Component.translatable("entity.mortal_cultivation.monster_gathering_talisman.remaining_time");
      Component displayName = Component.literal("")
         .append(remainingTimeText.withStyle(ChatFormatting.WHITE))
         .append(" ")
         .append(Component.literal(progressBar.toString()).withStyle(color));
      this.setCustomName(displayName);
      this.setCustomNameVisible(true);
   }

   private boolean canBeExtended() {
      return this.remainingTicks / 48000.0F < 0.9F;
   }

   private void checkBlockOverlap() {
      BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
      BlockState blockState = this.level().getBlockState(pos);
      if (!blockState.isAir()) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
         this.discard();
      }
   }

   public void setPos(double x, double y, double z) {
      super.setPos(x, y, z);
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (!this.level().isClientSide()) {
         ItemStack heldItem = player.getItemInHand(hand);
         if (heldItem.is((Item)CultivationItems.CINNABAR_BLOOD_INK.get())) {
            if (this.canBeExtended()) {
               this.remainingTicks = Math.min(this.remainingTicks + 12000, 48000);
               this.updateProgressBar();
               this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
               heldItem.setDamageValue(heldItem.getDamageValue() + 1);
               if (heldItem.getDamageValue() >= heldItem.getMaxDamage()) {
                  heldItem.shrink(1);
                  this.level()
                     .playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ITEM_BREAK,
                        SoundSource.PLAYERS,
                        0.8F,
                        0.8F + this.level().random.nextFloat() * 0.4F
                     );
               }

               return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
         }

         if (player.isShiftKeyDown() && heldItem.isEmpty()) {
            ItemStack talismanItem = new ItemStack((ItemLike)CultivationItems.MONSTER_GATHERING_TALISMAN.get());
            CompoundTag itemTag = ((CustomData)talismanItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
            itemTag.putInt("RemainingTicks", this.remainingTicks);
            float percentageRemaining = this.remainingTicks / 48000.0F;
            itemTag.putFloat("PercentageRemaining", percentageRemaining);
            talismanItem.set(DataComponents.CUSTOM_DATA, CustomData.of(itemTag));
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() - 0.1F, this.getZ(), talismanItem);
            this.level().addFreshEntity(itemEntity);
            this.discard();
            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.PASS;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean isPickable() {
      return true;
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   public boolean ignoreExplosion() {
      return true;
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound.contains("RemainingTicks")) {
         this.remainingTicks = compound.getInt("RemainingTicks");
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("RemainingTicks", this.remainingTicks);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   protected float getEyeHeight(Pose pPose, EntityDimensions pDimensions) {
      return pDimensions.height() + 0.1F;
   }
}
