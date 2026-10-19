package fuzs.puzzleslib.api.core.v1;

import fuzs.puzzleslib.api.core.v1.context.BiomeModificationsContext;
import fuzs.puzzleslib.api.core.v1.context.BlockInteractionsContext;
import fuzs.puzzleslib.api.core.v1.context.BuildCreativeModeTabContentsContext;
import fuzs.puzzleslib.api.core.v1.context.CompostableBlocksContext;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesModifyContext;
import fuzs.puzzleslib.api.core.v1.context.FlammableBlocksContext;
import fuzs.puzzleslib.api.core.v1.context.FuelBurnTimesContext;
import fuzs.puzzleslib.api.core.v1.context.GameplayContentContext;
import fuzs.puzzleslib.api.core.v1.context.PackRepositorySourcesContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.impl.PuzzlesLib;
import fuzs.puzzleslib.impl.core.CommonFactories;
import fuzs.puzzleslib.impl.core.ModContext;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.util.Strings;

public interface ModConstructor extends BaseModConstructor {
   static void construct(String modId, Supplier<ModConstructor> modConstructor) {
      if (Strings.isBlank(modId)) {
         throw new IllegalArgumentException("mod id is empty");
      }

      ModConstructor instance = modConstructor.get();
      ResourceLocation identifier = ModContext.getPairingIdentifier(modId, instance);
      PuzzlesLib.LOGGER.info("Constructing common components for {}", identifier);
      ModContext modContext = ModContext.get(modId);
      Set<ContentRegistrationFlags> availableFlags = Set.of(instance.getContentRegistrationFlags());
      Set<ContentRegistrationFlags> flagsToHandle = modContext.getFlagsToHandle(availableFlags);
      modContext.beforeModConstruction();
      CommonFactories.INSTANCE.constructMod(modId, instance, availableFlags, flagsToHandle);
      modContext.afterModConstruction(identifier);
   }

   default void onConstructMod() {
   }

   default void onCommonSetup() {
   }

   default void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
   }

   default void onEntityAttributeCreation(EntityAttributesCreateContext context) {
   }

   default void onEntityAttributeModification(EntityAttributesModifyContext context) {
   }

   default void onRegisterBiomeModifications(BiomeModificationsContext context) {
   }

   default void onRegisterGameplayContent(GameplayContentContext context) {
   }

   @Deprecated
   default void onRegisterFuelBurnTimes(FuelBurnTimesContext context) {
   }

   @Deprecated
   default void onRegisterFlammableBlocks(FlammableBlocksContext context) {
   }

   @Deprecated
   default void onRegisterCompostableBlocks(CompostableBlocksContext context) {
   }

   @Deprecated
   default void onRegisterBlockInteractions(BlockInteractionsContext context) {
   }

   @Deprecated
   default void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
   }

   default void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsContext context) {
   }

   default void onAddDataPackFinders(PackRepositorySourcesContext context) {
   }
}
