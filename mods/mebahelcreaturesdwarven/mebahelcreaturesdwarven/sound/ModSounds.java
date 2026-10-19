package mebahelcreaturesdwarven.sound;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;

public class ModSounds {
   public static class_3414 DWARVEN_SPHERE_AMBIENT_1 = registerSoundEvent("dwarven_sphere_ambient_1");
   public static class_3414 DWARVEN_SPHERE_ATTACK_1 = registerSoundEvent("dwarven_sphere_attack_1");
   public static class_3414 DWARVEN_SPHERE_ATTACK_2 = registerSoundEvent("dwarven_sphere_attack_2");
   public static class_3414 DWARVEN_SPHERE_ATTACK_3 = registerSoundEvent("dwarven_sphere_attack_3");
   public static class_3414 DWARVEN_SPHERE_INJURED_1 = registerSoundEvent("dwarven_sphere_injured_1");
   public static class_3414 DWARVEN_SPHERE_INJURED_2 = registerSoundEvent("dwarven_sphere_injured_2");
   public static class_3414 DWARVEN_SPHERE_BOW_DRAW = registerSoundEvent("dwarven_sphere_bow_draw");
   public static class_3414 DWARVEN_SPHERE_BOW_RELEASE = registerSoundEvent("dwarven_sphere_bow_release");
   public static class_3414 DWARVEN_SPHERE_CLOSE = registerSoundEvent("dwarven_sphere_close");
   public static class_3414 DWARVEN_SPHERE_OPEN = registerSoundEvent("dwarven_sphere_open");
   public static class_3414 DWARVEN_SPHERE_DEATH_1 = registerSoundEvent("dwarven_sphere_death_1");
   public static class_3414 DWARVEN_SPHERE_DEATH_2 = registerSoundEvent("dwarven_sphere_death_2");
   public static class_3414 DWARVEN_SPHERE_WALK = registerSoundEvent("dwarven_sphere_walk");
   public static class_3414 SWING = registerSoundEvent("swing1");
   public static class_3414 DWARVEN_SPIDER_WALK = registerSoundEvent("dwarven_spider_walk");
   public static class_3414 DWARVEN_SPIDER_AMBIENT_1 = registerSoundEvent("dwarven_spider_ambient_1");
   public static class_3414 DWARVEN_SPIDER_INJURED_1 = registerSoundEvent("dwarven_spider_injured_1");
   public static class_3414 DWARVEN_SPIDER_DEATH_1 = registerSoundEvent("dwarven_spider_death_1");
   public static class_3414 DWARVEN_SPIDER_MINING = registerSoundEvent("dwarven_spider_mining");
   public static class_3414 DWARVEN_SPIDER_JUMP_ATTACK = registerSoundEvent("dwarven_spider_jump_attack");
   public static class_3414 DWARVEN_SPIDER_JUMP_ATTACK_2 = registerSoundEvent("dwarven_spider_jump_attack_2");
   public static class_3414 DWARVEN_SPIDER_ATTACK_1 = registerSoundEvent("dwarven_spider_attack_1");
   public static class_3414 DWARVEN_SPIDER_ATTACK_2 = registerSoundEvent("dwarven_spider_attack_2");
   public static class_3414 DWARVEN_SPIDER_ATTACK_DOUBLE_1 = registerSoundEvent("dwarven_spider_attack_double_1");
   public static class_3414 DWARVEN_SPIDER_ATTACK_DELAY_1 = registerSoundEvent("dwarven_spider_attack_delay_1");
   public static class_3414 DWARVEN_CENTURION_ATTACK_1 = registerSoundEvent("dwarven_centurion_attack_1");
   public static class_3414 DWARVEN_CENTURION_ATTACK_2 = registerSoundEvent("dwarven_centurion_attack_2");
   public static class_3414 DWARVEN_CENTURION_DEATH_1 = registerSoundEvent("dwarven_centurion_death_1");
   public static class_3414 DWARVEN_CENTURION_INJURED_1 = registerSoundEvent("dwarven_centurion_injured_1");
   public static class_3414 DWARVEN_CENTURION_INJURED_2 = registerSoundEvent("dwarven_centurion_injured_2");
   public static class_3414 DWARVEN_CENTURION_WALK = registerSoundEvent("dwarven_centurion_walk");
   public static class_3414 DWARVEN_CENTURION_AMBIENT_1 = registerSoundEvent("dwarven_centurion_ambient_1");
   public static class_3414 DWARVEN_CENTURION_SHOOTING = registerSoundEvent("dwarven_centurion_shooting");
   public static class_3414 DWARVEN_CENTURION_POWER_ATTACK = registerSoundEvent("dwarven_centurion_power_attack");
   public static class_3414 DWARVEN_GEAR_MULTIPLE = registerSoundEvent("dwarven_gear_multiple");
   public static class_3414 DWARVEN_CHEST_OPEN = registerSoundEvent("dwarven_chest_open");
   public static class_3414 DWARVEN_CHEST_CLOSE = registerSoundEvent("dwarven_chest_close");

   private static class_3414 registerSoundEvent(String name) {
      class_2960 id = class_2960.method_60655("mebahelcreaturesdwarven", name);
      return (class_3414)class_2378.method_10230(class_7923.field_41172, id, class_3414.method_47908(id));
   }

   public static void registerSounds() {
      MebahelCreaturesDwarven.LOGGER.info("Registering sounds formebahelcreaturesdwarven.");
   }
}
