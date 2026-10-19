package terrablender.mixin;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.Climate.RTree;
import net.minecraft.world.level.biome.Climate.TargetPoint;
import net.minecraft.world.level.biome.Climate.RTree.Node;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.worldgen.IExtendedParameterList;
import terrablender.worldgen.RegionUtils;
import terrablender.worldgen.noise.Area;
import terrablender.worldgen.noise.InitialLayer;
import terrablender.worldgen.noise.LayeredNoiseUtil;

@Mixin(ParameterList.class)
public abstract class MixinParameterList<T> implements IExtendedParameterList<T> {
   @Shadow
   @Final
   private List<Pair<ParameterPoint, T>> values;
   private boolean initialized = false;
   private boolean treesPopulated = false;
   private Supplier<Area> newUniqueness;
   private Area uniqueness;
   private RegionUtils.SearchTreeEntry[] uniqueTrees;

   @Shadow
   public abstract T findValue(TargetPoint var1);

   @Override
   public void initializeForTerraBlender(RegistryAccess registryAccess, RegionType regionType, long seed) {
      if (!this.initialized) {
         InitialLayer initialLayer = LayeredNoiseUtil.initialUniqueness(registryAccess, regionType);
         this.newUniqueness = () -> LayeredNoiseUtil.finalUniqueness(regionType, seed, initialLayer);
         this.uniqueness = this.newUniqueness.get();
         this.uniqueTrees = new RegionUtils.SearchTreeEntry[Regions.getCount(regionType)];
         Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);

         for (Region region : Regions.get(regionType)) {
            int regionIndex = Regions.getIndex(regionType, region.getName());
            if (regionIndex == 0) {
               this.uniqueTrees[0] = new RegionUtils.SearchTreeEntry(region, RTree.create(this.values));
            } else {
               List<Pair<ParameterPoint, Holder<Biome>>> pairs = new ArrayList<>();
               region.addBiomes(biomeRegistry, pair -> {
                  if (biomeRegistry.getHolder((ResourceKey)pair.getSecond()).isPresent()) {
                     pairs.add(pair.mapSecond(biomeRegistry::getHolderOrThrow));
                  }
               });
               if (!pairs.isEmpty()) {
                  this.uniqueTrees[regionIndex] = new RegionUtils.SearchTreeEntry(region, RTree.create(pairs));
               }
            }
         }

         this.treesPopulated = true;
         this.initialized = true;
      }
   }

   @Override
   public int getUniqueness(int x, int y, int z) {
      return this.uniqueness.get(x, z);
   }

   @Override
   public RTree getTree(int uniqueness) {
      return this.uniqueTrees[uniqueness].tree();
   }

   @Override
   public Region getRegion(int uniqueness) {
      return this.uniqueTrees[uniqueness].region();
   }

   @Override
   public int getTreeCount() {
      return this.uniqueTrees.length;
   }

   @Override
   public boolean isInitialized() {
      return this.initialized && this.treesPopulated;
   }

   @Override
   public T findValuePositional(TargetPoint target, int x, int y, int z) {
      if (!this.initialized) {
         return this.findValue(target);
      }

      if (!this.treesPopulated) {
         throw new RuntimeException("Attempted to call findValuePositional whilst trees remain unpopulated!");
      }

      int uniqueness = this.getUniqueness(x, y, z);
      Holder<Biome> biome = (Holder<Biome>)this.getTree(uniqueness).search(target, Node::distance);
      return (T)(biome.is(Region.DEFERRED_PLACEHOLDER) ? this.uniqueTrees[0].tree().search(target, Node::distance) : biome);
   }

   @Override
   public ParameterList<T> clone() {
      try {
         return (ParameterList<T>)super.clone();
      } catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }
   }

   @Unique
   @Override
   public void recreateUniqueness() {
      if (this.initialized) {
         this.uniqueness = this.newUniqueness.get();
      }
   }
}
