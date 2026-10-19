package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.data.TensuraTags.MobEffects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class MysticismMobEffectTagProvider extends TagsProvider<MobEffect> {
   public MysticismMobEffectTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
      super(output, Registries.MOB_EFFECT, lookupProvider, "mysticism", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(MobEffects.SKILL_BUFF)
         .add(
            new ResourceKey[]{
               MysticismMobEffects.AWAKENED_FORESIGHT.getKey(),
               MysticismMobEffects.COLLECTIVE.getKey(),
               MysticismMobEffects.CULTIVATING.getKey(),
               MysticismMobEffects.LIGHTNING_MODE.getKey(),
               MysticismMobEffects.PROVIDER_BOOST.getKey(),
               MysticismMobEffects.PAY_IT_FORWARD.getKey(),
               MysticismMobEffects.REVENANT_HORROR.getKey(),
               MysticismMobEffects.SANCTIFYING_LIGHT.getKey(),
               MysticismMobEffects.STILLNESS.getKey(),
               MysticismMobEffects.UNSTABLE_REQUIEM.getKey()
            }
         );
      this.tag(MobEffects.SKILL_DEBUFF)
         .add(
            new ResourceKey[]{
               MysticismMobEffects.RUPTURING.getKey(),
               MysticismMobEffects.DRAGON_BURN.getKey(),
               MysticismMobEffects.GRAVITY_FLUCTUATION.getKey(),
               MysticismMobEffects.PRESSURE.getKey(),
               MysticismMobEffects.MARKED_FOR_DEATH.getKey(),
               MysticismMobEffects.SEVERED_LEG.getKey(),
               MysticismMobEffects.SEVERED_ARM.getKey(),
               MysticismMobEffects.FIXATION.getKey(),
               MysticismMobEffects.DAZZLED.getKey()
            }
         );
      this.tag(MobEffects.IGNORE_ANTI_MAGICULE)
         .add(
            new ResourceKey[]{
               MysticismMobEffects.FIXATION.getKey(),
               MysticismMobEffects.STAGNATE.getKey(),
               MysticismMobEffects.BRIMSTONE_FLAMES.getKey(),
               MysticismMobEffects.ETERNAL_PERMAFROST.getKey()
            }
         );
      this.tag(MobEffects.HIDDEN_ICON)
         .add(
            new ResourceKey[]{
               MysticismMobEffects.CONSTANT_DESTRUCTION.getKey(), MysticismMobEffects.GRAVITY_FLUCTUATION.getKey(), MysticismMobEffects.AURA_HEALING.getKey()
            }
         );
      this.tag(MobEffects.SPIRITUAL_AFFECTED)
         .add(new ResourceKey[]{MysticismMobEffects.BRIMSTONE_FLAMES.getKey(), MysticismMobEffects.ETERNAL_PERMAFROST.getKey()});
      this.tag(MobEffects.INCURABLE_BY_MILK).addTags(new TagKey[]{MobEffects.SKILL_BUFF, MobEffects.SKILL_DEBUFF, MobEffects.IGNORE_ANTI_MAGICULE});
   }
}
