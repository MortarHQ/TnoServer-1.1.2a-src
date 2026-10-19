package artifacts.neoforge.data.tags;

import artifacts.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MobEffectTags extends IntrinsicHolderTagsProvider<MobEffect> {
   public MobEffectTags(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(
         packOutput,
         Registries.MOB_EFFECT,
         lookupProvider,
         effect -> (ResourceKey)BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).orElseThrow(),
         "artifacts",
         existingFileHelper
      );
   }

   protected void addTags(Provider provider) {
      this.tag(ModTags.ANTIDOTE_VESSEL_CANCELLABLE)
         .add(
            new MobEffect[]{
               (MobEffect)MobEffects.BLINDNESS.value(),
               (MobEffect)MobEffects.CONFUSION.value(),
               (MobEffect)MobEffects.DIG_SLOWDOWN.value(),
               (MobEffect)MobEffects.HUNGER.value(),
               (MobEffect)MobEffects.LEVITATION.value(),
               (MobEffect)MobEffects.MOVEMENT_SLOWDOWN.value(),
               (MobEffect)MobEffects.POISON.value(),
               (MobEffect)MobEffects.WEAKNESS.value(),
               (MobEffect)MobEffects.WITHER.value()
            }
         );
   }

   public String getName() {
      return "Mob Effect Tags";
   }
}
