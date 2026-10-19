package fuzs.puzzleslib.neoforge.impl.core;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.serialization.MapCodec;
import fuzs.puzzleslib.api.biome.v1.BiomeLoadingContext;
import fuzs.puzzleslib.api.biome.v1.BiomeLoadingPhase;
import fuzs.puzzleslib.api.biome.v1.BiomeModificationContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.resources.v1.AbstractModPackResources;
import fuzs.puzzleslib.api.resources.v1.PackResourcesHelper;
import fuzs.puzzleslib.neoforge.impl.biome.BiomeLoadingContextNeoForge;
import fuzs.puzzleslib.neoforge.impl.biome.ClimateSettingsContextNeoForge;
import fuzs.puzzleslib.neoforge.impl.biome.GenerationSettingsContextNeoForge;
import fuzs.puzzleslib.neoforge.impl.biome.MobSpawnSettingsContextNeoForge;
import fuzs.puzzleslib.neoforge.impl.biome.SpecialEffectsContextNeoForge;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier.Phase;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import org.jetbrains.annotations.Nullable;

public class NeoForgeBiomeLoadingHandler {
   private static final Map<Phase, BiomeLoadingPhase> BIOME_MODIFIER_PHASE_CONVERSIONS = Maps.immutableEnumMap(new HashMap<Phase, BiomeLoadingPhase>() {
      {
         this.put(Phase.ADD, BiomeLoadingPhase.ADDITIONS);
         this.put(Phase.REMOVE, BiomeLoadingPhase.REMOVALS);
         this.put(Phase.MODIFY, BiomeLoadingPhase.MODIFICATIONS);
         this.put(Phase.AFTER_EVERYTHING, BiomeLoadingPhase.POST_PROCESSING);
      }
   });
   private static final String BIOME_MODIFICATIONS_NAME_KEY = "biome_modifications";
   private static final String BIOME_MODIFIERS_DATA_KEY = Keys.BIOME_MODIFIERS.location().toString().replace(":", "/");
   private static final Function<String, ResourceLocation> BIOME_MODIFICATIONS_FILE_KEY = id -> ResourceLocationHelper.fromNamespaceAndPath(
      id, BIOME_MODIFIERS_DATA_KEY + "/" + id + ".json"
   );
   private static final Function<ResourceLocation, String> BIOME_MODIFICATIONS_FILE_CONTENTS = id -> "{\"type\":\"" + id + "\"}";

   public static void register(
      String modId, IEventBus modEventBus, Multimap<BiomeLoadingPhase, NeoForgeBiomeLoadingHandler.BiomeModification> biomeModifications
   ) {
      DeferredRegister<MapCodec<? extends BiomeModifier>> deferredRegister = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, modId);
      deferredRegister.register(modEventBus);
      deferredRegister.register("biome_modifications", new NeoForgeBiomeLoadingHandler.BiomeModifierImpl(biomeModifications)::codec);
   }

   public static RepositorySource buildPack(String modId) {
      ResourceLocation resourceLocation = ResourceLocationHelper.fromNamespaceAndPath(modId, "biome_modifications");
      return PackResourcesHelper.buildServerPack(
         resourceLocation,
         () -> new AbstractModPackResources() {
            @Override
            public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {
               if (path.equals(NeoForgeBiomeLoadingHandler.BIOME_MODIFIERS_DATA_KEY)) {
                  resourceOutput.accept(
                     NeoForgeBiomeLoadingHandler.BIOME_MODIFICATIONS_FILE_KEY.apply(modId),
                     (IoSupplier)() -> new ByteArrayInputStream(
                        NeoForgeBiomeLoadingHandler.BIOME_MODIFICATIONS_FILE_CONTENTS.apply(resourceLocation).getBytes(StandardCharsets.UTF_8)
                     )
                  );
               }
            }
         },
         false
      );
   }

   public record BiomeModification(Predicate<BiomeLoadingContext> selector, Consumer<BiomeModificationContext> modifier) {
      public void tryApply(BiomeLoadingContext filter, BiomeModificationContext context) {
         if (this.selector().test(filter)) {
            this.modifier().accept(context);
         }
      }
   }

   private record BiomeModifierImpl(
      Multimap<BiomeLoadingPhase, NeoForgeBiomeLoadingHandler.BiomeModification> biomeModifications, @Nullable MapCodec<? extends BiomeModifier> codec
   ) implements BiomeModifier {
      private BiomeModifierImpl(
         Multimap<BiomeLoadingPhase, NeoForgeBiomeLoadingHandler.BiomeModification> biomeModifications, @Nullable MapCodec<? extends BiomeModifier> codec
      ) {
         this.biomeModifications = biomeModifications;
         this.codec = MapCodec.unit(this);
      }

      public BiomeModifierImpl(Multimap<BiomeLoadingPhase, NeoForgeBiomeLoadingHandler.BiomeModification> biomeModifications) {
         this(biomeModifications, null);
      }

      private static BiomeModificationContext createBuilderBackedContext(Builder builder) {
         ClimateSettingsContextNeoForge climateSettings = new ClimateSettingsContextNeoForge(builder.getClimateSettings());
         SpecialEffectsContextNeoForge specialEffects = new SpecialEffectsContextNeoForge(builder.getSpecialEffects());
         GenerationSettingsContextNeoForge generationSettings = new GenerationSettingsContextNeoForge(builder.getGenerationSettings());
         MobSpawnSettingsContextNeoForge mobSpawnSettings = new MobSpawnSettingsContextNeoForge(builder.getMobSpawnSettings());
         return new BiomeModificationContext(climateSettings, specialEffects, generationSettings, mobSpawnSettings);
      }

      public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
         BiomeLoadingPhase loadingPhase = NeoForgeBiomeLoadingHandler.BIOME_MODIFIER_PHASE_CONVERSIONS.get(phase);
         if (loadingPhase != null) {
            Collection<NeoForgeBiomeLoadingHandler.BiomeModification> modifications = this.biomeModifications.get(loadingPhase);
            if (!modifications.isEmpty()) {
               BiomeLoadingContext filter = new BiomeLoadingContextNeoForge(biome);
               BiomeModificationContext context = createBuilderBackedContext(builder);

               for (NeoForgeBiomeLoadingHandler.BiomeModification modification : modifications) {
                  modification.tryApply(filter, context);
               }
            }
         }
      }
   }
}
