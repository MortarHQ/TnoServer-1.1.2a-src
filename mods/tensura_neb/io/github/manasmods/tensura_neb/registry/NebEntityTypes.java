package io.github.manasmods.tensura_neb.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura_neb.entity.CarrionEntity;
import io.github.manasmods.tensura_neb.entity.LuminousValentineEntity;
import io.github.manasmods.tensura_neb.entity.RimuruCloneEntity;
import io.github.manasmods.tensura_neb.entity.RimuruOgreFightEntity;
import io.github.manasmods.tensura_neb.entity.VeldoraEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class NebEntityTypes {
   private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create("tensura_neb", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<RimuruOgreFightEntity>> RIMURU_OGRE_FIGHT = ENTITY_TYPES.register(
      "rimuru_ogre_fight",
      () -> Builder.of(RimuruOgreFightEntity::new, MobCategory.MONSTER)
         .sized(0.45F, 1.35F)
         .eyeHeight(1.215F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_neb", "rimuru_ogre_fight").toString())
   );
   public static final RegistrySupplier<EntityType<RimuruCloneEntity>> RIMURU_CLONE = ENTITY_TYPES.register(
      "rimuru_clone",
      () -> Builder.of(RimuruCloneEntity::new, MobCategory.MONSTER)
         .sized(0.45F, 1.35F)
         .eyeHeight(1.215F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_neb", "rimuru_clone").toString())
   );
   public static final RegistrySupplier<EntityType<CarrionEntity>> CARRION = ENTITY_TYPES.register(
      "carrion",
      () -> Builder.of(CarrionEntity::new, MobCategory.MONSTER)
         .sized(0.8F, 2.0F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_neb", "carrion").toString())
   );
   public static final RegistrySupplier<EntityType<LuminousValentineEntity>> LUMINOUS_VALENTINE = ENTITY_TYPES.register(
      "luminous_valentine",
      () -> Builder.of(LuminousValentineEntity::new, MobCategory.MONSTER)
         .sized(0.8F, 2.0F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_neb", "luminous_valentine").toString())
   );
   public static final RegistrySupplier<EntityType<VeldoraEntity>> VELDORA = ENTITY_TYPES.register(
      "veldora",
      () -> Builder.of(VeldoraEntity::new, MobCategory.MONSTER)
         .sized(3.0F, 10.0F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_neb", "veldora").toString())
   );

   public static void init() {
      ENTITY_TYPES.register();
   }
}
