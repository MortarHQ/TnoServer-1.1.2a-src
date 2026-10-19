package io.github.Memoires.mysticism.storage.player;

import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.manascore.storage.api.StorageEvents.RegisterStorage;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class MysticismPlayerStorage extends Storage implements IMysticismPlayer {
   private CompoundTag butcherMarks = new CompoundTag();
   private UUID butcherLastHitTarget = null;
   private static StorageKey<MysticismPlayerStorage> key;
   private Vec3 postition;

   public static void init() {
      StorageEvents.REGISTER_ENTITY_STORAGE
         .register(
            (RegisterStorage)registry -> {
               ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("mysticism", "mysticism_player_storage");
               key = registry.register(
                  resourceLocation, MysticismPlayerStorage.class, LivingEntity.class::isInstance, target -> new MysticismPlayerStorage((LivingEntity)target)
               );
            }
         );
   }

   protected MysticismPlayerStorage(LivingEntity holder) {
      super(holder);
   }

   public static StorageKey<MysticismPlayerStorage> getKey() {
      return key;
   }

   @Override
   public void setLastHitTarget(UUID uuid) {
      this.butcherLastHitTarget = uuid;
      this.markDirty();
   }

   @Override
   public UUID getLastHitTarget() {
      return this.butcherLastHitTarget;
   }

   @Override
   public int getMarks(UUID uuid) {
      return this.butcherMarks.getInt(uuid.toString());
   }

   @Override
   public void incrementMarks(UUID uuid) {
      String k = uuid.toString();
      this.butcherMarks.putInt(k, this.butcherMarks.getInt(k) + 1);
      this.markDirty();
   }

   @Override
   public void clearMarks(UUID uuid) {
      this.butcherMarks.remove(uuid.toString());
      this.markDirty();
   }

   @Override
   public List<UUID> getTargetsOver(int threshold) {
      return this.butcherMarks.getAllKeys().stream().filter(k -> this.butcherMarks.contains(k, 3) && this.butcherMarks.getInt(k) >= threshold).map(k -> {
         try {
            return UUID.fromString(k);
         } catch (IllegalArgumentException ex) {
            return null;
         }
      }).filter(Objects::nonNull).toList();
   }

   @Override
   public CompoundTag getMarksTag() {
      return this.butcherMarks;
   }

   @Override
   public Vec3 getPosition() {
      return this.postition;
   }

   @Override
   public void setPosition(Vec3 pos) {
      this.postition = pos;
   }

   public void save(CompoundTag compoundTag) {
      if (this.butcherLastHitTarget != null) {
         compoundTag.putUUID("LastHitTarget", this.butcherLastHitTarget);
      }

      compoundTag.put("Marks", this.butcherMarks.copy());
      if (this.postition != null) {
         compoundTag.putDouble("PositionX", this.postition.x);
         compoundTag.putDouble("PositionY", this.postition.y);
         compoundTag.putDouble("PositionZ", this.postition.z);
      }
   }

   public void load(CompoundTag compoundTag) {
      this.butcherLastHitTarget = compoundTag.contains("LastHitTarget", 11) ? compoundTag.getUUID("LastHitTarget") : null;
      this.butcherMarks = compoundTag.contains("Marks", 10) ? compoundTag.getCompound("Marks").copy() : new CompoundTag();
      this.postition = new Vec3(compoundTag.getDouble("PositionX"), compoundTag.getDouble("PositionY"), compoundTag.getDouble("PositionZ"));
   }
}
