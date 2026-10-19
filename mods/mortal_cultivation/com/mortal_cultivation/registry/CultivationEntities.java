package com.mortal_cultivation.registry;

import com.mortal_cultivation.entity.AzureMossQilinEntity;
import com.mortal_cultivation.entity.FlyingSwordEntity;
import com.mortal_cultivation.entity.MillenniumGinsengEntity;
import com.mortal_cultivation.entity.RuneBurstEntity;
import com.mortal_cultivation.entity.SpiritMonkeyEntity;
import com.mortal_cultivation.entity.TaoistArrayEntity;
import com.mortal_cultivation.entity.TenThousandSoulBannerEntity;
import com.mortal_cultivation.entity.ZhaoYingZhuEntity;
import com.mortal_cultivation.entity.breath.AlchemistFireBreathEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CultivationEntities {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "mortal_cultivation");
   public static final DeferredHolder<EntityType<?>, EntityType<TaoistArrayEntity>> TAOIST_ARRAY = ENTITY_TYPES.register(
      "taoist_array",
      () -> Builder.of(TaoistArrayEntity::new, MobCategory.MISC)
         .sized(3.0F, 0.1F)
         .noSave()
         .clientTrackingRange(64)
         .updateInterval(20)
         .setShouldReceiveVelocityUpdates(false)
         .build("taoist_array")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<TenThousandSoulBannerEntity>> TEN_THOUSAND_SOUL_BANNER = ENTITY_TYPES.register(
      "ten_thousand_soul_banner",
      () -> Builder.of(TenThousandSoulBannerEntity::new, MobCategory.MISC)
         .sized(2.5F, 3.5F)
         .clientTrackingRange(64)
         .updateInterval(10)
         .setShouldReceiveVelocityUpdates(false)
         .build("ten_thousand_soul_banner")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AlchemistFireBreathEntity>> ALCHEMIST_FIRE_BREATH = ENTITY_TYPES.register(
      "alchemist_fire_breath",
      () -> Builder.of(AlchemistFireBreathEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).build("alchemist_fire_breath")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<MillenniumGinsengEntity>> MILLENNIUM_GINSENG = ENTITY_TYPES.register(
      "millennium_ginseng",
      () -> Builder.of(MillenniumGinsengEntity::new, MobCategory.CREATURE)
         .sized(0.65F, 1.25F)
         .clientTrackingRange(10)
         .updateInterval(1)
         .setShouldReceiveVelocityUpdates(true)
         .build("millennium_ginseng")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<FlyingSwordEntity>> FLYING_SWORD = ENTITY_TYPES.register(
      "flying_sword",
      () -> Builder.of(FlyingSwordEntity::new, MobCategory.MISC)
         .sized(1.0F, 0.12F)
         .fireImmune()
         .clientTrackingRange(12)
         .updateInterval(1)
         .setShouldReceiveVelocityUpdates(true)
         .build("flying_sword")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<AzureMossQilinEntity>> AZURE_MOSS_QILIN = ENTITY_TYPES.register(
      "azure_moss_qilin",
      () -> Builder.of(AzureMossQilinEntity::new, MobCategory.MONSTER)
         .sized(1.5625F, 1.9062564F)
         .eyeHeight(2.05F)
         .clientTrackingRange(12)
         .updateInterval(1)
         .build("azure_moss_qilin")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<SpiritMonkeyEntity>> SPIRIT_MONKEY = ENTITY_TYPES.register(
      "spirit_monkey",
      () -> Builder.of(SpiritMonkeyEntity::new, MobCategory.CREATURE).sized(0.6F, 1.4F).clientTrackingRange(10).updateInterval(3).build("spirit_monkey")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<ZhaoYingZhuEntity>> ZHAO_YING_ZHU = ENTITY_TYPES.register(
      "zhao_ying_zhu",
      () -> Builder.of(ZhaoYingZhuEntity::new, MobCategory.MISC)
         .sized(0.4F, 0.4F)
         .fireImmune()
         .clientTrackingRange(10)
         .updateInterval(1)
         .setShouldReceiveVelocityUpdates(true)
         .build("zhao_ying_zhu")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<RuneBurstEntity>> RUNE_BURST = ENTITY_TYPES.register(
      "rune_burst",
      () -> Builder.of(RuneBurstEntity::new, MobCategory.MISC)
         .sized(3.0F, 3.0F)
         .fireImmune()
         .noSave()
         .clientTrackingRange(16)
         .updateInterval(1)
         .setShouldReceiveVelocityUpdates(false)
         .build("rune_burst")
   );

   private static void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)MILLENNIUM_GINSENG.get(), MillenniumGinsengEntity.createAttributes().build());
      event.put((EntityType)SPIRIT_MONKEY.get(), SpiritMonkeyEntity.createAttributes().build());
      event.put((EntityType)AZURE_MOSS_QILIN.get(), AzureMossQilinEntity.createAttributes().build());
   }

   private static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)SPIRIT_MONKEY.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         SpiritMonkeyEntity::checkSpiritMonkeySpawnRules,
         Operation.REPLACE
      );
   }

   public static void register(IEventBus eventBus) {
      ENTITY_TYPES.register(eventBus);
      eventBus.addListener(CultivationEntities::registerAttributes);
      eventBus.addListener(CultivationEntities::registerSpawnPlacements);
   }
}
