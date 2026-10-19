package com.illusivesoulworks.polymorph.common.integration;

import com.google.common.collect.ImmutableSet;
import com.illusivesoulworks.polymorph.platform.Services;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PolymorphIntegrations {
   private static final Set<String> ACTIVATED = ConcurrentHashMap.newKeySet();
   private static final Map<String, AbstractCompatibilityModule> ACTIVE_INTEGRATIONS = new ConcurrentHashMap<>();
   private static final Map<String, Supplier<Supplier<AbstractCompatibilityModule>>> INTEGRATIONS = Services.INTEGRATION_PLATFORM.createCompatibilityModules();

   public static void load() {
      for (PolymorphIntegrations.Mod mod : PolymorphIntegrations.Mod.values()) {
         ACTIVATED.add(mod.getId());
      }
   }

   public static void init() {
      INTEGRATIONS.forEach((modid, supplier) -> {
         if (Services.PLATFORM.isModLoaded(modid)) {
            ACTIVE_INTEGRATIONS.put(modid, supplier.get().get());
         }
      });
   }

   public static void setup() {
      for (AbstractCompatibilityModule integration : get()) {
         integration.setup();
      }
   }

   public static void clientSetup() {
      for (AbstractCompatibilityModule integration : get()) {
         integration.clientSetup();
      }
   }

   public static void selectRecipe(BlockEntity blockEntity, AbstractContainerMenu containerMenu, RecipeHolder<?> recipe) {
      for (AbstractCompatibilityModule integration : get()) {
         if (integration.selectRecipe(blockEntity, recipe) || integration.selectRecipe(containerMenu, recipe)) {
            return;
         }
      }
   }

   public static void selectRecipe(AbstractContainerMenu containerMenu, RecipeHolder<?> recipe) {
      for (AbstractCompatibilityModule integration : get()) {
         if (integration.selectRecipe(containerMenu, recipe)) {
            return;
         }
      }
   }

   public static void openContainer(AbstractContainerMenu containerMenu, ServerPlayer serverPlayer) {
      for (AbstractCompatibilityModule integration : get()) {
         if (integration.openContainer(containerMenu, serverPlayer)) {
            return;
         }
      }
   }

   public static Set<AbstractCompatibilityModule> get() {
      return ImmutableSet.copyOf(ACTIVE_INTEGRATIONS.values());
   }

   public static boolean isActive(String id) {
      return ACTIVATED.contains(id);
   }

   public static void disable(String id) {
      ACTIVATED.remove(id);
      INTEGRATIONS.remove(id);
      AbstractCompatibilityModule module = ACTIVE_INTEGRATIONS.remove(id);
      if (module != null) {
         module.disable();
      }
   }

   public enum Loader {
      FABRIC,
      FORGE,
      NEOFORGE;
   }

   public enum Mod {
      REI("roughlyenoughitems", true, PolymorphIntegrations.Loader.FABRIC, PolymorphIntegrations.Loader.NEOFORGE),
      JEI("jei", true, PolymorphIntegrations.Loader.FABRIC, PolymorphIntegrations.Loader.FORGE, PolymorphIntegrations.Loader.NEOFORGE),
      EMI("emi", true, PolymorphIntegrations.Loader.FABRIC, PolymorphIntegrations.Loader.NEOFORGE),
      QUICKBENCH("quickbench", true, PolymorphIntegrations.Loader.FABRIC),
      FASTFURNACE("fastfurnace", true, PolymorphIntegrations.Loader.NEOFORGE),
      FASTWORKBENCH("fastbench", true, PolymorphIntegrations.Loader.NEOFORGE),
      FASTSUITE("fastsuite", true, PolymorphIntegrations.Loader.NEOFORGE);

      private final String id;
      private final boolean defaultValue;
      private final PolymorphIntegrations.Loader[] loaders;

      Mod(String id, PolymorphIntegrations.Loader defaultLoader, PolymorphIntegrations.Loader... extraLoaders) {
         this(id, false, defaultLoader, extraLoaders);
      }

      Mod(String id, boolean defaultValue, PolymorphIntegrations.Loader defaultLoader, PolymorphIntegrations.Loader... extraLoaders) {
         this.id = id;
         this.defaultValue = defaultValue;
         this.loaders = new PolymorphIntegrations.Loader[extraLoaders.length + 1];
         this.loaders[0] = defaultLoader;
         System.arraycopy(extraLoaders, 0, this.loaders, 1, this.loaders.length - 1);
      }

      public boolean getDefaultValue() {
         return this.defaultValue;
      }

      public String getId() {
         return this.id;
      }

      public static PolymorphIntegrations.Mod[] values(PolymorphIntegrations.Loader loader) {
         return Arrays.stream(values()).filter(mod -> Arrays.stream(mod.loaders).anyMatch(test -> test == loader)).toArray(PolymorphIntegrations.Mod[]::new);
      }
   }
}
