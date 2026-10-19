package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.entity.AlignmentFieldEntity;
import com.tno_tensura_extras.entity.DiabloDecoyEntity;
import com.tno_tensura_extras.entity.DiabloEntity;
import com.tno_tensura_extras.entity.LooterBlackHoleEntity;
import com.tno_tensura_extras.entity.TeleportPortalEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ExtrasEntityTypes {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "tno_tensura_extras");
   public static final Supplier<EntityType<DiabloEntity>> DIABLO = ENTITY_TYPES.register(
      "diablo",
      () -> Builder.of(DiabloEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 2.0F)
         .eyeHeight(1.8F)
         .fireImmune()
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(TensuraExtras.id("diablo").toString())
   );
   public static final Supplier<EntityType<DiabloDecoyEntity>> DIABLO_DECOY = ENTITY_TYPES.register(
      "diablo_decoy",
      () -> Builder.of(DiabloDecoyEntity::new, MobCategory.MISC)
         .sized(0.6F, 2.0F)
         .eyeHeight(1.8F)
         .noSummon()
         .noSave()
         .clientTrackingRange(10)
         .updateInterval(1)
         .build(TensuraExtras.id("diablo_decoy").toString())
   );
   public static final Supplier<EntityType<TeleportPortalEntity>> TELEPORT_PORTAL = ENTITY_TYPES.register(
      "teleport_portal",
      () -> Builder.of(TeleportPortalEntity::new, MobCategory.MISC)
         .sized(2.5F, 0.2F)
         .noSummon()
         .noSave()
         .fireImmune()
         .clientTrackingRange(64)
         .updateInterval(20)
         .build(TensuraExtras.id("teleport_portal").toString())
   );
   public static final Supplier<EntityType<LooterBlackHoleEntity>> LOOTER_BLACK_HOLE = ENTITY_TYPES.register(
      "looter_black_hole",
      () -> Builder.of(LooterBlackHoleEntity::new, MobCategory.MISC)
         .sized(3.5F, 0.2F)
         .noSummon()
         .noSave()
         .fireImmune()
         .clientTrackingRange(64)
         .updateInterval(1)
         .build(TensuraExtras.id("looter_black_hole").toString())
   );
   public static final Supplier<EntityType<AlignmentFieldEntity>> ALIGNMENT_FIELD = ENTITY_TYPES.register(
      "alignment_field",
      () -> Builder.of(AlignmentFieldEntity::new, MobCategory.MISC)
         .sized(0.1F, 0.1F)
         .noSummon()
         .noSave()
         .fireImmune()
         .clientTrackingRange(16)
         .updateInterval(1)
         .build(TensuraExtras.id("alignment_field").toString())
   );
}
