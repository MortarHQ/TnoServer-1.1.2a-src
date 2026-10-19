package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MysticismEntityTagProvider extends EntityTypeTagsProvider {
   public MysticismEntityTagProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, completableFuture, "mysticism", existingFileHelper);
   }

   protected void addTags(@NotNull Provider provider) {
      this.tag(TensuraEntityTags.NAMEABLE)
         .add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.OKAMI.get(), (EntityType)MysticismMonsterEntityTypes.SHADOW_IMP.get()});
      this.tag(TensuraEntityTags.HERO_BOSS).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.HUMAN_LIKE).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.NO_BLOOD).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.SPIRITUAL)
         .add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(), (EntityType)MysticismMonsterEntityTypes.SHADOW_IMP.get()});
      this.tag(TensuraEntityTags.NO_MIND_CONTROL).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.NO_POSSESSION)
         .add(
            new EntityType[]{
               (EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(),
               (EntityType)MysticismSkillEntityTypes.DISPENSER.get(),
               (EntityType)MysticismSkillEntityTypes.SENTRY.get()
            }
         );
      this.tag(TensuraEntityTags.NO_SYNTHESISE).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.NO_TSUKUMOGAMI_UPDATE).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.SPIRITUAL).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.EP_DROP_EXCLUDED)
         .add(new EntityType[]{(EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(), (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()});
      this.tag(TensuraEntityTags.EP_INITIATE_EXCLUDED)
         .add(new EntityType[]{(EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(), (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()});
      this.tag(TensuraEntityTags.NO_EP_PLUNDER)
         .add(new EntityType[]{(EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(), (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()});
      this.tag(TensuraEntityTags.NO_SKILL_PLUNDER)
         .add(new EntityType[]{(EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(), (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()});
      this.tag(TensuraEntityTags.NO_MIND_CONTROL)
         .add(
            new EntityType[]{
               (EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(),
               (EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(),
               (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_POSSESSION)
         .add(
            new EntityType[]{
               (EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(),
               (EntityType)MysticismSkillEntityTypes.DISPENSER.get(),
               (EntityType)MysticismSkillEntityTypes.SENTRY.get(),
               (EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(),
               (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_SYNTHESISE)
         .add(
            new EntityType[]{
               (EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(),
               (EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(),
               (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()
            }
         );
      this.tag(TensuraEntityTags.NO_TSUKUMOGAMI_UPDATE)
         .add(
            new EntityType[]{
               (EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(),
               (EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get(),
               (EntityType)MysticismSkillEntityTypes.RELAPSE_CLONE.get()
            }
         );
      this.tag(TensuraEntityTags.FULL_GRAVITY_CONTROL).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.NO_FORCED_MOVE).add(new EntityType[]{(EntityType)MysticismMonsterEntityTypes.MEMOIRES.get()});
      this.tag(TensuraEntityTags.NO_CHARM)
         .add(
            new EntityType[]{
               (EntityType)MysticismSkillEntityTypes.DISPENSER.get(),
               (EntityType)MysticismSkillEntityTypes.SENTRY.get(),
               (EntityType)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get()
            }
         );
      this.tag(TensuraEntityTags.NO_SPIRITUAL_DAMAGE)
         .add(new EntityType[]{(EntityType)MysticismSkillEntityTypes.DISPENSER.get(), (EntityType)MysticismSkillEntityTypes.SENTRY.get()});
      this.tag(TensuraEntityTags.NON_LIVING)
         .add(new EntityType[]{(EntityType)MysticismSkillEntityTypes.DISPENSER.get(), (EntityType)MysticismSkillEntityTypes.SENTRY.get()});
   }
}
