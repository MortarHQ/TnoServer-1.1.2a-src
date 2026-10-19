package io.github.Memoires.mysticism.storage.entity;

import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.manascore.storage.api.StorageEvents.RegisterStorage;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MysticismEntityStorage extends Storage implements IMysticismEntity {
   private int butcherMarks = 0;
   private UUID lastMarkApplier = null;
   private static StorageKey<MysticismEntityStorage> key;

   public static void init() {
      StorageEvents.REGISTER_ENTITY_STORAGE
         .register(
            (RegisterStorage)registry -> {
               ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("mysticism", "mysticism_entity_storage");
               key = registry.register(
                  resourceLocation, MysticismEntityStorage.class, LivingEntity.class::isInstance, target -> new MysticismEntityStorage((LivingEntity)target)
               );
            }
         );
   }

   protected MysticismEntityStorage(LivingEntity holder) {
      super(holder);
   }

   public static StorageKey<MysticismEntityStorage> getKey() {
      return key;
   }

   @Override
   public void setButcherMarks(int marks) {
      this.butcherMarks = marks;
   }

   @Override
   public void setLastMarkApplier(UUID lastMarkApplier) {
      this.lastMarkApplier = lastMarkApplier;
   }

   @Override
   public UUID getLastMarkApplier() {
      return this.lastMarkApplier;
   }

   @Override
   public int getButcherMarks() {
      return this.butcherMarks;
   }

   @Override
   public void incrementButcherMarks() {
      this.butcherMarks++;
   }

   @Override
   public void clearButcherMarks() {
      this.butcherMarks = 0;
   }

   public void save(CompoundTag compoundTag) {
      compoundTag.putInt("butcher_marks", this.butcherMarks);
      if (this.lastMarkApplier != null) {
         compoundTag.putUUID("last_mark_applier", this.lastMarkApplier);
      }
   }

   public void load(CompoundTag compoundTag) {
      this.butcherMarks = compoundTag.getInt("butcher_marks");
      if (compoundTag.hasUUID("last_mark_applier")) {
         this.lastMarkApplier = compoundTag.getUUID("last_mark_applier");
      } else {
         this.lastMarkApplier = null;
      }
   }
}
