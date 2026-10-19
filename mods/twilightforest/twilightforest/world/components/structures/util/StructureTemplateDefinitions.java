package twilightforest.world.components.structures.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Component;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.world.components.structures.TwilightJigsawPiece;

@Component
public final class StructureTemplateDefinitions extends CodecResourceReloadListener<StructureTemplateDefinition> {
   private final Map<ResourceLocation, Map<ResourceLocation, TemplatePoolInstance>> rawTemplatePools = new HashMap<>();
   private final Map<ResourceLocation, WeightedRandomList<StructureTemplateDefinitions.TemplatePoolEntry>> templatePools = new HashMap<>();
   public static final String DIRECTORY = "twilight/template_definition";
   @Nullable
   private RegistryAccess registryAccess;

   public StructureTemplateDefinitions() {
      super("twilight/template_definition", StructureTemplateDefinition.CODEC);
   }

   protected void forLocation(ResourceManager manager, ResourceLocation templateName, StructureTemplateDefinition templateDefinition) {
      for (Entry<ResourceLocation, TemplatePoolInstance> poolToRegisterWeight : templateDefinition.poolWeights().entrySet()) {
         ResourceLocation templatePoolId = poolToRegisterWeight.getKey();
         TemplatePoolInstance templatePoolInstance = poolToRegisterWeight.getValue();
         Map<ResourceLocation, TemplatePoolInstance> pool = this.rawTemplatePools.computeIfAbsent(templatePoolId, k -> new HashMap<>());
         pool.put(templateName, templatePoolInstance);
      }
   }

   @Override
   protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
      this.rawTemplatePools.clear();
      this.templatePools.clear();
      super.apply(map, manager, profiler);

      for (Entry<ResourceLocation, Map<ResourceLocation, TemplatePoolInstance>> rawTemplatePool : this.rawTemplatePools.entrySet()) {
         ArrayList<StructureTemplateDefinitions.TemplatePoolEntry> poolBuilder = new ArrayList<>();

         for (Entry<ResourceLocation, TemplatePoolInstance> templateIdWeight : rawTemplatePool.getValue()
            .entrySet()
            .stream()
            .sorted(Entry.comparingByKey())
            .toList()) {
            poolBuilder.add(new StructureTemplateDefinitions.TemplatePoolEntry(templateIdWeight.getKey(), templateIdWeight.getValue()));
         }

         ResourceLocation templatePoolId = rawTemplatePool.getKey();
         this.templatePools.put(templatePoolId, WeightedRandomList.create(poolBuilder));
      }

      this.rawTemplatePools.clear();
   }

   @Override
   public void registerListener(AddReloadListenerEvent event) {
      this.registryAccess = event.getRegistryAccess();
      super.registerListener(event);
   }

   @Override
   protected DynamicOps<JsonElement> initDynamicOps() {
      return RegistryOps.create(super.initDynamicOps(), this.registryAccess);
   }

   private Optional<StructureTemplateDefinitions.TemplatePoolEntry> getRandomEntry(RandomSource random, ResourceLocation templatePoolId) {
      WeightedRandomList<StructureTemplateDefinitions.TemplatePoolEntry> templatePool = this.templatePools.get(templatePoolId);
      return templatePool == null ? Optional.empty() : templatePool.getRandom(random);
   }

   @Nullable
   public ResourceLocation getRandomTemplate(RandomSource random, ResourceLocation templatePoolId) {
      return this.getRandomEntry(random, templatePoolId).map(StructureTemplateDefinitions.TemplatePoolEntry::templateId).orElse(null);
   }

   public Iterable<ResourceLocation> getShuffledSequence(RandomSource random, ResourceLocation templatePoolId) {
      WeightedRandomList<StructureTemplateDefinitions.TemplatePoolEntry> templatePool = this.templatePools.get(templatePoolId);
      if (templatePool == null) {
         return Collections.emptyList();
      }

      Map<ResourceLocation, Double> reservoirSampled = new HashMap<>();

      for (StructureTemplateDefinitions.TemplatePoolEntry entry : templatePool.unwrap()) {
         double rand = random.nextDouble();
         reservoirSampled.put(entry.templateId(), -Math.log(rand) / entry.getWeight().asInt());
      }

      return reservoirSampled.entrySet().stream().sorted(Entry.comparingByValue()).map(Entry::getKey).collect(Collectors.toList());
   }

   @Deprecated
   @Nullable
   public TwilightJigsawPiece initializeTemplateFromPool(
      ResourceLocation templatePool,
      BlockPos parentJunctionPos,
      FrontAndTop parentOrientation,
      String selectName,
      RandomSource rand,
      int genDepth,
      StructureTemplateManager structureManager
   ) {
      Optional<StructureTemplateDefinitions.TemplatePoolEntry> entryOptional = this.getRandomEntry(rand, templatePool);
      if (entryOptional.isEmpty()) {
         return null;
      }

      StructureTemplateDefinitions.TemplatePoolEntry templateEntry = entryOptional.get();
      JigsawPlaceContext placeContext = JigsawPlaceContext.pickPlaceableJunction(
         parentJunctionPos, BlockPos.ZERO, parentOrientation, structureManager, templateEntry.templateId, selectName, rand
      );
      return placeContext == null
         ? null
         : TwilightJigsawPiece.defaultForTemplate(
            genDepth, structureManager, templateEntry.templateId, placeContext, templateEntry.instance, templateEntry.instance.chooseRandomProcessors(rand)
         );
   }

   @Nullable
   public TwilightJigsawPiece initializeTemplateFromPool(
      ResourceLocation templatePool,
      BlockPos parentJunctionPos,
      FrontAndTop parentOrientation,
      String selectName,
      GenerationContext generationContext,
      int genDepth,
      boolean parentProjectsTerrain
   ) {
      RandomSource random = generationContext.random();
      Optional<StructureTemplateDefinitions.TemplatePoolEntry> entryOptional = this.getRandomEntry(random, templatePool);
      if (entryOptional.isEmpty()) {
         return null;
      }

      StructureTemplateDefinitions.TemplatePoolEntry templateEntry = entryOptional.get();
      JigsawPlaceContext placeContext = JigsawPlaceContext.pickPlaceableJunction(
         parentJunctionPos, BlockPos.ZERO, parentOrientation, generationContext.structureTemplateManager(), templateEntry.templateId, selectName, random
      );
      return placeContext == null
         ? null
         : TwilightJigsawPiece.defaultForTemplate(
            genDepth,
            generationContext.structureTemplateManager(),
            templateEntry.templateId,
            templateEntry.instance.adjustContextForTerrain(placeContext, generationContext, parentProjectsTerrain),
            templateEntry.instance,
            templateEntry.instance.chooseRandomProcessors(random)
         );
   }

   private record TemplatePoolEntry(ResourceLocation templateId, TemplatePoolInstance instance) implements WeightedEntry {
      public Weight getWeight() {
         return this.instance.getWeight();
      }
   }
}
