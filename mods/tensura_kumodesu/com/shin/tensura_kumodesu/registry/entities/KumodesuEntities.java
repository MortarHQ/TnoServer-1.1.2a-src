package com.shin.tensura_kumodesu.registry.entities;

import com.shin.tensura_kumodesu.config.race.TaratectConfig;
import com.shin.tensura_kumodesu.entity.taratect.ArachneTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.ArchTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.EdeSaineTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.GreatPoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.GreatTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.GreaterTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.LesserPoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.LesserTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.OrthocadinahtTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.PoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.QueenTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.SmallLesserTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.SmallPoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.SmallTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.TaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.ZanaHorowaTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.ZoaEleTaratectEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.config.ConfigRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class KumodesuEntities {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create("tensura_kumodesu", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<SmallLesserTaratectEntity>> SMALL_LESSER_TARATECT = ENTITIES.register(
      "small_lesser_taratect",
      () -> Builder.of(SmallLesserTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallLesserTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallLesserTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_lesser_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<OrthocadinahtTaratectEntity>> ORTHOCADINAHT = ENTITIES.register(
      "orthocadinaht",
      () -> Builder.of(OrthocadinahtTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Orthocadinaht.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Orthocadinaht.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "orthocadinaht").toString())
   );
   public static final RegistrySupplier<EntityType<SmallTaratectEntity>> SMALL_TARATECT = ENTITIES.register(
      "small_taratect",
      () -> Builder.of(SmallTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<TaratectEntity>> TARATECT = ENTITIES.register(
      "taratect",
      () -> Builder.of(TaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Taratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Taratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "taratect").toString())
   );
   public static final RegistrySupplier<EntityType<GreatTaratectEntity>> GREAT_TARATECT = ENTITIES.register(
      "great_taratect",
      () -> Builder.of(GreatTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).GreatTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).GreatTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "great_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<GreaterTaratectEntity>> GREATER_TARATECT = ENTITIES.register(
      "greater_taratect",
      () -> Builder.of(GreaterTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).GreaterTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).GreaterTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "greater_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<ArchTaratectEntity>> ARCH_TARATECT = ENTITIES.register(
      "arch_taratect",
      () -> Builder.of(ArchTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ArchTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ArchTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "arch_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<QueenTaratectEntity>> QUEEN_TARATECT = ENTITIES.register(
      "queen_taratect",
      () -> Builder.of(QueenTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).QueenTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).QueenTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "queen_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<SmallPoisonTaratectEntity>> SMALL_POISON_TARATECT = ENTITIES.register(
      "small_poison_taratect",
      () -> Builder.of(SmallPoisonTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallPoisonTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallPoisonTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_poison_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<ZoaEleTaratectEntity>> ZOA_ELE = ENTITIES.register(
      "zoa_ele",
      () -> Builder.of(ZoaEleTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ZoaEle.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ZoaEle.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "zoa_ele").toString())
   );
   public static final RegistrySupplier<EntityType<EdeSaineTaratectEntity>> EDE_SAINE = ENTITIES.register(
      "ede_saine",
      () -> Builder.of(EdeSaineTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).EdeSaine.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).EdeSaine.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "ede_saine").toString())
   );
   public static final RegistrySupplier<EntityType<ZanaHorowaTaratectEntity>> ZANA_HOROWA = ENTITIES.register(
      "zana_horowa",
      () -> Builder.of(ZanaHorowaTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ZanaHorowa.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).ZanaHorowa.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "zana_horowa").toString())
   );
   public static final RegistrySupplier<EntityType<ArachneTaratectEntity>> ARACHNE = ENTITIES.register(
      "arachne",
      () -> Builder.of(ArachneTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Arachne.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).Arachne.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "arachne").toString())
   );
   public static final RegistrySupplier<EntityType<LesserTaratectEntity>> LESSER_TARATECT = ENTITIES.register(
      "lesser_taratect",
      () -> Builder.of(LesserTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).LesserTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).LesserTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "lesser_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<LesserPoisonTaratectEntity>> LESSER_POISON_TARATECT = ENTITIES.register(
      "lesser_poison_taratect",
      () -> Builder.of(LesserPoisonTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallPoisonTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).SmallPoisonTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "lesser_poison_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<PoisonTaratectEntity>> POISON_TARATECT = ENTITIES.register(
      "poison_taratect",
      () -> Builder.of(PoisonTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).PoisonTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).PoisonTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "poison_taratect").toString())
   );
   public static final RegistrySupplier<EntityType<GreatPoisonTaratectEntity>> GREAT_POISON_TARATECT = ENTITIES.register(
      "great_poison_taratect",
      () -> Builder.of(GreatPoisonTaratectEntity::new, MobCategory.MONSTER)
         .sized(
            0.6F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).GreatPoisonTaratect.getWidth(),
            1.8F * ((TaratectConfig)ConfigRegistry.getConfig(TaratectConfig.class)).GreatPoisonTaratect.getHeight()
         )
         .clientTrackingRange(8)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "great_poison_taratect").toString())
   );

   public static void register() {
      ENTITIES.register();
   }
}
