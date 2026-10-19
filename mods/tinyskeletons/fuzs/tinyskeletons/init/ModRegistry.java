package fuzs.tinyskeletons.init;

import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.tinyskeletons.world.entity.monster.BabyBogged;
import fuzs.tinyskeletons.world.entity.monster.BabySkeleton;
import fuzs.tinyskeletons.world.entity.monster.BabyStray;
import fuzs.tinyskeletons.world.entity.monster.BabyWitherSkeleton;
import fuzs.tinyskeletons.world.entity.monster.projectile.Mushroom;
import net.minecraft.core.Holder.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModRegistry {
   static final RegistryManager REGISTRIES = RegistryManager.from("tinyskeletons");
   public static final Reference<EntityType<BabySkeleton>> BABY_SKELETON_ENTITY_TYPE = REGISTRIES.registerEntityType(
      "baby_skeleton", () -> Builder.of(BabySkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).eyeHeight(1.74F).ridingOffset(-0.7F).clientTrackingRange(8)
   );
   public static final Reference<EntityType<BabyWitherSkeleton>> BABY_WITHER_SKELETON_ENTITY_TYPE = REGISTRIES.registerEntityType(
      "baby_wither_skeleton",
      () -> Builder.of(BabyWitherSkeleton::new, MobCategory.MONSTER)
         .fireImmune()
         .sized(0.7F, 2.4F)
         .eyeHeight(2.1F)
         .ridingOffset(-0.875F)
         .clientTrackingRange(8)
   );
   public static final Reference<EntityType<BabyStray>> BABY_STRAY_ENTITY_TYPE = REGISTRIES.registerEntityType(
      "baby_stray",
      () -> Builder.of(BabyStray::new, MobCategory.MONSTER)
         .sized(0.6F, 1.99F)
         .eyeHeight(1.74F)
         .ridingOffset(-0.7F)
         .immuneTo(new Block[]{Blocks.POWDER_SNOW})
         .clientTrackingRange(8)
   );
   public static final Reference<EntityType<BabyBogged>> BABY_BOGGED_ENTITY_TYPE = REGISTRIES.registerEntityType(
      "baby_bogged", () -> Builder.of(BabyBogged::new, MobCategory.MONSTER).sized(0.6F, 1.99F).eyeHeight(1.74F).ridingOffset(-0.7F).clientTrackingRange(8)
   );
   public static final Reference<EntityType<Mushroom>> MUSHROOM_ENTITY_TYPE = REGISTRIES.registerEntityType(
      "mushroom", () -> Builder.of(Mushroom::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
   );

   public static void touch() {
   }
}
