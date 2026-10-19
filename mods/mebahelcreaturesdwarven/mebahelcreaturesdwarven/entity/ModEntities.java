package mebahelcreaturesdwarven.entity;

import mebahelcreaturesdwarven.entity.custom.DwemerCenturionEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSphereEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderGuardianEntity;
import mebahelcreaturesdwarven.entity.projectile.DwarvenArrowEntity;
import mebahelcreaturesdwarven.entity.projectile.SteamProjectileEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1311;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_1299.class_1300;

public class ModEntities {
   public static final class_1299<DwarvenArrowEntity> DWARVEN_ARROW = registerEntity(
      "dwarven_arrow", class_1300.method_5903(DwarvenArrowEntity::new, class_1311.field_17715).method_17687(0.5F, 0.5F)
   );
   public static final class_1299<DwemerSphereEntity> DWARVEN_SPHERE = registerEntity(
      "dwarven_sphere", class_1300.method_5903(DwemerSphereEntity::new, class_1311.field_6302).method_17687(0.75F, 1.99F)
   );
   public static final class_1299<DwemerCenturionEntity> DWARVEN_CENTURION = registerEntity(
      "dwarven_centurion", class_1300.method_5903(DwemerCenturionEntity::new, class_1311.field_6302).method_17687(1.75F, 3.99F)
   );
   public static final class_1299<DwemerSpiderGuardianEntity> DWARVEN_SPIDER_GUARDIAN = registerEntity(
      "dwarven_spider_guardian", class_1300.method_5903(DwemerSpiderGuardianEntity::new, class_1311.field_6302).method_17687(0.99F, 1.25F)
   );
   public static final class_1299<DwemerSpiderEntity> DWARVEN_SPIDER = registerEntity(
      "dwarven_spider", class_1300.method_5903(DwemerSpiderEntity::new, class_1311.field_6302).method_17687(0.8F, 1.0F)
   );
   public static final class_1299<SteamProjectileEntity> STEAM_PROJECTILE = registerEntity(
      "steam_projectile", class_1300.method_5903(SteamProjectileEntity::new, class_1311.field_17715).method_17687(1.0F, 1.0F)
   );

   private static <T extends class_1297> class_1299<T> registerEntity(String id, class_1300<T> builder) {
      class_2960 identifier = class_2960.method_60655("mebahelcreaturesdwarven", id);
      return (class_1299<T>)class_2378.method_10230(class_7923.field_41177, identifier, builder.method_5905(id));
   }
}
