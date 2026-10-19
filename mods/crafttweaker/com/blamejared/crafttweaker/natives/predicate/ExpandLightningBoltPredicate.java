package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Optional;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LightningBoltPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LightningBoltPredicate")
@NativeTypeRegistration(value = LightningBoltPredicate.class, zenCodeName = "crafttweaker.api.predicate.LightningBoltPredicate")
public final class ExpandLightningBoltPredicate {
   @ZenCodeType.StaticExpansionMethod
   public static LightningBoltPredicate create(Ints blocksSetOnFire) {
      return LightningBoltPredicate.blockSetOnFire(blocksSetOnFire);
   }

   @ZenCodeType.StaticExpansionMethod
   public static LightningBoltPredicate create(EntityPredicate struckEntity) {
      return create(Ints.ANY, struckEntity);
   }

   @ZenCodeType.StaticExpansionMethod
   public static LightningBoltPredicate create(Ints blocksSetOnFire, EntityPredicate struckEntity) {
      return new LightningBoltPredicate(blocksSetOnFire, Optional.of(struckEntity));
   }

   @ZenCodeType.StaticExpansionMethod
   public static LightningBoltPredicate create(Ints blockSetOnFire, Builder struckEntity) {
      return create(blockSetOnFire, struckEntity.build());
   }

   @ZenCodeType.Method
   public static boolean matches(LightningBoltPredicate internal, Entity entity, ServerLevel level, @ZenCodeType.Nullable Vec3 pos) {
      return internal.matches(entity, level, pos);
   }
}
