package io.github.Memoires.mysticism.storage.soulEnergy;

import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.manascore.storage.api.StorageEvents.RegisterStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoulEnergyStorage extends Storage implements ISoulEnergy {
   private static final Logger log = LogManager.getLogger(SoulEnergyStorage.class);
   private static StorageKey<SoulEnergyStorage> key = null;
   private double soulEnergy = 50.0;
   private boolean namingBoost = false;

   public static void init() {
      StorageEvents.REGISTER_ENTITY_STORAGE.register((RegisterStorage)registry -> {
         ResourceLocation var10001 = ResourceLocation.fromNamespaceAndPath("mysticism", "soul_energy_storage");
         key = registry.register(var10001, SoulEnergyStorage.class, LivingEntity.class::isInstance, target -> new SoulEnergyStorage((LivingEntity)target));
      });
   }

   protected SoulEnergyStorage(LivingEntity holder) {
      super(holder);
   }

   @Override
   public void setSoulEnergy(double amount) {
      this.soulEnergy = Math.min(amount, 2.147483647E9);
   }

   @Override
   public void setNamingBoost(boolean bool) {
      this.namingBoost = bool;
   }

   @Override
   public void reduceSoulEnergy(double amount) {
      this.soulEnergy -= amount;
   }

   @Override
   public void increaseSoulEnergy(double amount) {
      this.soulEnergy += amount;
   }

   public void save(CompoundTag tag) {
      tag.putDouble("soulEnergy", this.soulEnergy);
      tag.putBoolean("namingBoost", this.namingBoost);
   }

   public void load(CompoundTag tag) {
      this.soulEnergy = tag.getDouble("soulEnergy");
      this.namingBoost = tag.getBoolean("namingBoost");
   }

   public static StorageKey<SoulEnergyStorage> getKey() {
      return key;
   }

   @Override
   public double getSoulEnergy() {
      return this.soulEnergy;
   }

   @Override
   public boolean getNamingBoost() {
      return this.namingBoost;
   }
}
