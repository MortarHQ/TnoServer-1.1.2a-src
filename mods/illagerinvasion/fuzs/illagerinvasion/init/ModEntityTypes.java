package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.entity.monster.Alchemist;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import fuzs.illagerinvasion.world.entity.monster.Basher;
import fuzs.illagerinvasion.world.entity.monster.Firecaller;
import fuzs.illagerinvasion.world.entity.monster.Inquisitor;
import fuzs.illagerinvasion.world.entity.monster.Invoker;
import fuzs.illagerinvasion.world.entity.monster.InvokerFangs;
import fuzs.illagerinvasion.world.entity.monster.Marauder;
import fuzs.illagerinvasion.world.entity.monster.Necromancer;
import fuzs.illagerinvasion.world.entity.monster.Provoker;
import fuzs.illagerinvasion.world.entity.monster.Sorcerer;
import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import fuzs.illagerinvasion.world.entity.projectile.FlyingMagma;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import net.minecraft.core.Holder.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;

public class ModEntityTypes {
   public static final Reference<EntityType<Provoker>> PROVOKER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("provoker", () -> Builder.of(Provoker::new, MobCategory.MONSTER).sized(0.5F, 1.92F));
   public static final Reference<EntityType<Invoker>> INVOKER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("invoker", () -> Builder.of(Invoker::new, MobCategory.MONSTER).sized(0.5F, 1.92F).fireImmune());
   public static final Reference<EntityType<Necromancer>> NECROMANCER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("necromancer", () -> Builder.of(Necromancer::new, MobCategory.MONSTER).sized(0.5F, 1.92F));
   public static final Reference<EntityType<Basher>> BASHER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("basher", () -> Builder.of(Basher::new, MobCategory.MONSTER).sized(0.5F, 1.92F));
   public static final Reference<EntityType<Sorcerer>> SORCERER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("sorcerer", () -> Builder.of(Sorcerer::new, MobCategory.MONSTER).sized(0.5F, 1.92F));
   public static final Reference<EntityType<Archivist>> ARCHIVIST_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("archivist", () -> Builder.of(Archivist::new, MobCategory.MONSTER).sized(0.5F, 1.92F));
   public static final Reference<EntityType<Inquisitor>> INQUISITOR_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("inquisitor", () -> Builder.of(Inquisitor::new, MobCategory.MONSTER).sized(0.5F, 2.48F));
   public static final Reference<EntityType<Marauder>> MARAUDER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("marauder", () -> Builder.of(Marauder::new, MobCategory.MONSTER).sized(0.5F, 1.92F).canSpawnFarFromPlayer());
   public static final Reference<EntityType<Alchemist>> ALCHEMIST_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("alchemist", () -> Builder.of(Alchemist::new, MobCategory.MONSTER).sized(0.5F, 1.92F));
   public static final Reference<EntityType<Firecaller>> FIRECALLER_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("firecaller", () -> Builder.of(Firecaller::new, MobCategory.MONSTER).sized(0.5F, 1.92F).fireImmune());
   public static final Reference<EntityType<Surrendered>> SURRENDERED_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("surrendered", () -> Builder.of(Surrendered::new, MobCategory.MONSTER).sized(0.5F, 1.42F).eyeHeight(1.1F).fireImmune());
   public static final Reference<EntityType<SkullBolt>> SKULL_BOLT_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("skull_bolt", () -> Builder.of(SkullBolt::new, MobCategory.MISC).sized(0.3F, 0.3F).clientTrackingRange(4).updateInterval(10));
   public static final Reference<EntityType<Hatchet>> HATCHET_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("hatchet", () -> Builder.of(Hatchet::new, MobCategory.MISC).sized(0.35F, 0.35F).clientTrackingRange(4).updateInterval(10));
   public static final Reference<EntityType<InvokerFangs>> INVOKER_FANGS_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("invoker_fangs", () -> Builder.of(InvokerFangs::new, MobCategory.MISC).sized(0.65F, 1.05F));
   public static final Reference<EntityType<FlyingMagma>> FLYING_MAGMA_ENTITY_TYPE = ModRegistry.REGISTRIES
      .registerEntityType("flying_magma", () -> Builder.of(FlyingMagma::new, MobCategory.MISC).sized(0.95F, 1.05F));

   public static void touch() {
   }
}
