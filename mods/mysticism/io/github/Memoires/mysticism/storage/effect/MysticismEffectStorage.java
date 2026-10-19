package io.github.Memoires.mysticism.storage.effect;

import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.manascore.storage.api.StorageEvents.RegisterStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MysticismEffectStorage extends Storage implements IMysticismEffect {
   private boolean onBrimstoneFlames;
   private static StorageKey<MysticismEffectStorage> key;

   public static void init() {
      StorageEvents.REGISTER_ENTITY_STORAGE
         .register(
            (RegisterStorage)registry -> {
               ResourceLocation var10001 = ResourceLocation.fromNamespaceAndPath("mysticism", "effect_storage");
               key = registry.register(
                  var10001, MysticismEffectStorage.class, LivingEntity.class::isInstance, target -> new MysticismEffectStorage((LivingEntity)target)
               );
            }
         );
   }

   public static StorageKey<MysticismEffectStorage> getKey() {
      return key;
   }

   protected MysticismEffectStorage(LivingEntity holder) {
      super(holder);
   }

   public void save(CompoundTag data) {
      data.putBoolean("onBrimstoneFlames", this.onBrimstoneFlames);
   }

   public void load(CompoundTag data) {
      this.onBrimstoneFlames = data.getBoolean("onBrimstoneFlames");
   }

   protected LivingEntity getOwner() {
      return (LivingEntity)this.holder;
   }

   @Override
   public boolean isOnBrimstoneFlames() {
      return this.onBrimstoneFlames;
   }

   @Override
   public void setOnBrimstoneFlames(boolean onBrimstoneFlames) {
      this.onBrimstoneFlames = onBrimstoneFlames;
   }
}
