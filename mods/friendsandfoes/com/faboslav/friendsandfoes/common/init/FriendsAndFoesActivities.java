package com.faboslav.friendsandfoes.common.init;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;

public final class FriendsAndFoesActivities {
   public static final ResourcefulRegistry<Activity> ACTIVITIES = ResourcefulRegistries.create(BuiltInRegistries.ACTIVITY, "friendsandfoes");
   public static final RegistryEntry<Activity> COPPER_GOLEM_SPIN_HEAD = ACTIVITIES.register(
      "copper_golem_spin_head", () -> new Activity("copper_golem_spin_head")
   );
   public static final RegistryEntry<Activity> COPPER_GOLEM_PRESS_BUTTON = ACTIVITIES.register(
      "copper_golem_press_button", () -> new Activity("copper_golem_press_button")
   );
   public static final RegistryEntry<Activity> CRAB_DANCE = ACTIVITIES.register("crab_dance", () -> new Activity("crab_dance"));
   public static final RegistryEntry<Activity> CRAB_WAVE = ACTIVITIES.register("crab_wave", () -> new Activity("crab_wave"));
   public static final RegistryEntry<Activity> CRAB_LAY_EGG = ACTIVITIES.register("crab_lay_egg", () -> new Activity("crab_lay_egg"));
   public static final RegistryEntry<Activity> GLARE_EAT_GLOW_BERRIES = ACTIVITIES.register(
      "glare_eat_glow_berries", () -> new Activity("glare_eat_glow_berries")
   );
   public static final RegistryEntry<Activity> GLARE_SHOW_DARK_SPOT = ACTIVITIES.register("glare_show_dark_spot", () -> new Activity("glare_dark_spot"));
   public static final RegistryEntry<Activity> TUFF_GOLEM_HOME = ACTIVITIES.register("tuff_golem_home", () -> new Activity("tuff_golem_home"));
   public static final RegistryEntry<Activity> RASCAL_WAIT = ACTIVITIES.register("rascal_wait", () -> new Activity("rascal_wait"));

   private FriendsAndFoesActivities() {
   }
}
