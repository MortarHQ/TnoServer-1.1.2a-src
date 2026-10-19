package com.bobmowzie.mowziesmobs.server.capability;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public class LivingData implements INBTSerializable<CompoundTag> {
   float lastDamage = 0.0F;
   boolean hasSunblock;
   private final Map<Holder<MobEffect>, MobEffectInstance> eclipsedEffects = Maps.newHashMap();

   public void setLastDamage(float damage) {
      this.lastDamage = damage;
   }

   public float getLastDamage() {
      return this.lastDamage;
   }

   public void setHasSunblock(boolean hasSunblock) {
      this.hasSunblock = hasSunblock;
   }

   public boolean getHasSunblock() {
      return this.hasSunblock;
   }

   public void tick(LivingEntity entity) {
   }

   public void eclipseEffect(MobEffectInstance mobEffectInstance) {
      this.eclipsedEffects.put(mobEffectInstance.getEffect(), mobEffectInstance);
   }

   public void unEclipseEffects(LivingEntity entity) {
      if (!this.eclipsedEffects.isEmpty()) {
         for (MobEffectInstance mobeffectinstance : this.eclipsedEffects.values()) {
            entity.addEffect(mobeffectinstance);
         }

         this.eclipsedEffects.clear();
      }
   }

   public CompoundTag serializeNBT(@NotNull Provider lookup) {
      CompoundTag compound = new CompoundTag();
      if (!this.eclipsedEffects.isEmpty()) {
         ListTag listtag = new ListTag();

         for (MobEffectInstance mobeffectinstance : this.eclipsedEffects.values()) {
            listtag.add(mobeffectinstance.save());
         }

         compound.put("active_effects", listtag);
      }

      return compound;
   }

   public void deserializeNBT(@NotNull Provider lookup, @NotNull CompoundTag compound) {
      if (compound.contains("active_effects", 9)) {
         ListTag listtag = compound.getList("active_effects", 10);

         for (int i = 0; i < listtag.size(); i++) {
            CompoundTag compoundtag = listtag.getCompound(i);
            MobEffectInstance mobeffectinstance = MobEffectInstance.load(compoundtag);
            if (mobeffectinstance != null) {
               this.eclipsedEffects.put(mobeffectinstance.getEffect(), mobeffectinstance);
            }
         }
      }
   }
}
