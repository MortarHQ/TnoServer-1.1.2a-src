package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public abstract class MobEffectTagsProvider extends IntrinsicHolderTagsProvider<MobEffect> {
   public MobEffectTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(
         output,
         Registries.MOB_EFFECT,
         lookupProvider,
         effect -> (ResourceKey)BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).orElseThrow(),
         modId,
         existingFileHelper
      );
   }
}
