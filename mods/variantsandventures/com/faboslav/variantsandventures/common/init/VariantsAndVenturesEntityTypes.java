package com.faboslav.variantsandventures.common.init;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.entity.mob.GelidEntity;
import com.faboslav.variantsandventures.common.entity.mob.MurkEntity;
import com.faboslav.variantsandventures.common.entity.mob.ThicketEntity;
import com.faboslav.variantsandventures.common.entity.mob.VerdantEntity;
import com.faboslav.variantsandventures.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.variantsandventures.common.events.lifecycle.RegisterEntityAttributesEvent;
import com.faboslav.variantsandventures.common.events.lifecycle.RegisterEntitySpawnRestrictionsEvent;
import com.faboslav.variantsandventures.common.tag.VariantsAndVenturesTags;
import com.faboslav.variantsandventures.common.versions.VersionedEntityTypeResourceId;
import com.faboslav.variantsandventures.common.versions.VersionedSpawnPlacementType;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public final class VariantsAndVenturesEntityTypes {
   public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, "variantsandventures");
   public static boolean previousUseChoiceTypeRegistrations = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
   public static final Supplier<EntityType<GelidEntity>> GELID = ENTITY_TYPES.register(
      "gelid",
      () -> Builder.of(GelidEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.95F)
         .immuneTo(new Block[]{Blocks.POWDER_SNOW})
         .eyeHeight(1.74F)
         .passengerAttachments(new float[]{2.075F})
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(VersionedEntityTypeResourceId.create("gelid"))
   );
   public static final Supplier<EntityType<MurkEntity>> MURK = ENTITY_TYPES.register(
      "murk",
      () -> Builder.of(MurkEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.99F)
         .eyeHeight(1.74F)
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(VersionedEntityTypeResourceId.create("murk"))
   );
   public static final Supplier<EntityType<ThicketEntity>> THICKET = ENTITY_TYPES.register(
      "thicket",
      () -> Builder.of(ThicketEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.95F)
         .eyeHeight(1.74F)
         .passengerAttachments(new float[]{2.075F})
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(VersionedEntityTypeResourceId.create("thicket"))
   );
   public static final Supplier<EntityType<VerdantEntity>> VERDANT = ENTITY_TYPES.register(
      "verdant",
      () -> Builder.of(VerdantEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.99F)
         .eyeHeight(1.74F)
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build(VersionedEntityTypeResourceId.create("verdant"))
   );

   public static void registerEntityAttributes(RegisterEntityAttributesEvent event) {
      event.register(GELID.get(), GelidEntity.createGelidAttributes());
      event.register(MURK.get(), MurkEntity.createMurkAttributes());
      event.register(THICKET.get(), ThicketEntity.createAttributes());
      event.register(VERDANT.get(), VerdantEntity.createAttributes());
   }

   public static void registerEntitySpawnRestrictions(RegisterEntitySpawnRestrictionsEvent event) {
      event.register(MURK.get(), VersionedSpawnPlacementType.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES, MurkEntity::canSpawn);
   }

   public static void addSpawnBiomeModifications(AddSpawnBiomeModificationsEvent event) {
      if (VariantsAndVentures.getConfig().enableMurk && VariantsAndVentures.getConfig().enableMurkSpawns) {
         event.add(VariantsAndVenturesTags.HAS_MURK, MobCategory.MONSTER, MURK.get(), 4, 1, 1);
      }
   }

   static {
      SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;
      SharedConstants.CHECK_DATA_FIXER_SCHEMA = previousUseChoiceTypeRegistrations;
   }
}
