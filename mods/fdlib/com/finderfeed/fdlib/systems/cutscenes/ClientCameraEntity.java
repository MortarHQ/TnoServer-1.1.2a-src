package com.finderfeed.fdlib.systems.cutscenes;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.init.FDEntities;
import java.util.ArrayList;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ClientCameraEntity extends LivingEntity {
   public ClientCameraEntity(EntityType<? extends LivingEntity> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   public ClientCameraEntity(Level level) {
      this(FDEntities.CLIENT_CAMERA.get(), level);
   }

   public void tick() {
      super.tick();
   }

   public HumanoidArm getMainArm() {
      return HumanoidArm.LEFT;
   }

   public void setPos(double p_20210_, double p_20211_, double p_20212_) {
      super.setPos(p_20210_, p_20211_, p_20212_);
   }

   public boolean hasEffect(Holder<MobEffect> effect) {
      return FDClientHelpers.getClientPlayer().hasEffect(effect);
   }

   @Nullable
   public MobEffectInstance getEffect(Holder<MobEffect> effect) {
      return FDClientHelpers.getClientPlayer().getEffect(effect);
   }

   public void readAdditionalSaveData(CompoundTag p_20052_) {
   }

   public Iterable<ItemStack> getArmorSlots() {
      return new ArrayList<>();
   }

   public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
      return ItemStack.EMPTY;
   }

   public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {
   }

   public void addAdditionalSaveData(CompoundTag p_20139_) {
   }

   public void push(Vec3 p_347665_) {
   }

   public void push(Entity p_21294_) {
   }

   public void push(double p_20286_, double p_20287_, double p_20288_) {
   }

   protected void pushEntities() {
   }
}
