package terrablender.mixin;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.BiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import terrablender.worldgen.IExtendedBiomeSource;

@Mixin(BiomeSource.class)
public abstract class MixinBiomeSource implements BiomeResolver, IExtendedBiomeSource {
   @Shadow
   public Supplier<Set<Holder<Biome>>> possibleBiomes;
   @Unique
   private boolean hasAppended = false;

   @Override
   public void appendDeferredBiomesList(List<Holder<Biome>> biomesToAppend) {
      if (!this.hasAppended) {
         List<Holder<Biome>> possibleBiomes = new ArrayList<>();
         possibleBiomes.addAll(this.possibleBiomes.get());
         possibleBiomes.addAll(biomesToAppend);
         this.possibleBiomes = () -> new ObjectLinkedOpenHashSet(possibleBiomes.stream().distinct().collect(Collectors.toList()));
         this.hasAppended = true;
      }
   }
}
