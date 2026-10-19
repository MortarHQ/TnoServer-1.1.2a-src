package cn.leolezury.eternalstarlight.neoforge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ESMobEffectTagsProvider extends MobEffectTagsProvider {
   public ESMobEffectTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "eternal_starlight", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(ESTags.MobEffects.DEEPSILVER_ARMOR_CAN_REMOVE)
         .add(
            new MobEffect[]{
               (MobEffect)MobEffects.POISON.value(),
               (MobEffect)MobEffects.WITHER.value(),
               (MobEffect)MobEffects.CONFUSION.value(),
               (MobEffect)MobEffects.HUNGER.value(),
               (MobEffect)MobEffects.MOVEMENT_SLOWDOWN.value(),
               (MobEffect)MobEffects.INFESTED.value(),
               (MobEffect)MobEffects.BLINDNESS.value()
            }
         );
   }
}
