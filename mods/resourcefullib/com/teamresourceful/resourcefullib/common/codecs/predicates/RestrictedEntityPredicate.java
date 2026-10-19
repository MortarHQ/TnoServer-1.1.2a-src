package com.teamresourceful.resourcefullib.common.codecs.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public record RestrictedEntityPredicate(
   EntityType<?> entityType,
   Optional<LocationPredicate> location,
   Optional<MobEffectsPredicate> effects,
   Optional<NbtPredicate> nbt,
   Optional<EntityFlagsPredicate> flags,
   Optional<EntityPredicate> targetedEntity
) {
   public static final Codec<RestrictedEntityPredicate> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(RestrictedEntityPredicate::entityType),
            LocationPredicate.CODEC.optionalFieldOf("location").forGetter(RestrictedEntityPredicate::location),
            MobEffectsPredicate.CODEC.optionalFieldOf("effects").forGetter(RestrictedEntityPredicate::effects),
            NbtPredicate.CODEC.optionalFieldOf("nbt").forGetter(RestrictedEntityPredicate::nbt),
            EntityFlagsPredicate.CODEC.optionalFieldOf("flags").forGetter(RestrictedEntityPredicate::flags),
            EntityPredicate.CODEC.optionalFieldOf("target").forGetter(RestrictedEntityPredicate::targetedEntity)
         )
         .apply(instance, RestrictedEntityPredicate::new)
   );

   public Optional<CompoundTag> getTag() {
      return this.nbt().map(NbtPredicate::tag);
   }

   public boolean matches(ServerLevel level, Entity entity) {
      if (this.entityType == null) {
         return false;
      } else if (this.entityType != entity.getType()) {
         return false;
      } else if (this.location.isPresent() && !this.location.get().matches(level, entity.getX(), entity.getY(), entity.getZ())) {
         return false;
      } else if (this.effects.isPresent() && !this.effects.get().matches(entity)) {
         return false;
      } else if (this.nbt.isPresent() && !this.nbt.get().matches(entity)) {
         return false;
      } else {
         return this.flags.isPresent() && !this.flags.get().matches(entity)
            ? false
            : this.targetedEntity.isEmpty() || this.targetedEntity.get().matches(level, null, entity instanceof Mob mob ? mob.getTarget() : null);
      }
   }
}
