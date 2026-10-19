package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.data.MysticismSkillTags;
import io.github.Memoires.mysticism.registry.skill.ExtraMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.IntrinsicMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UltimateMysticismSkills;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.data.SkillTagProvider;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public class MysticismSkillTagProvider extends SkillTagProvider {
   public MysticismSkillTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
      super(output, lookupProvider);
   }

   protected void addTags(@NotNull Provider arg) {
      this.tag(TensuraSkillTags.ELEMENTAL_MANIPULATION)
         .add(
            new ManasSkill[]{
               (ManasSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.ICE_MANIPULATION.get()
            }
         );
      this.tag(TensuraSkillTags.ELEMENTAL_DOMINATION)
         .add(
            new ManasSkill[]{
               (ManasSkill)ExtraMysticismSkills.LIGHT_DOMINATION.get(),
               (ManasSkill)ExtraMysticismSkills.DARKNESS_DOMINATION.get(),
               (ManasSkill)ExtraMysticismSkills.ICE_DOMINATION.get()
            }
         );
      this.tag(MysticismSkillTags.LIGHT_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)IntrinsicSkills.LIGHT_TRANSFORM.get(),
               (ManasSkill)ExtraSkills.MAGIC_LIGHT_TRANSFORM.get(),
               (ManasSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.LIGHT_DOMINATION.get()
            }
         );
      this.tag(MysticismSkillTags.DARKNESS_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)IntrinsicSkills.DARKNESS_TRANSFORM.get(),
               (ManasSkill)ExtraSkills.MAGIC_DARKNESS_TRANSFORM.get(),
               (ManasSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.DARKNESS_DOMINATION.get()
            }
         );
      this.tag(MysticismSkillTags.ICE_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)IntrinsicSkills.ICE_BREATH.get(),
               (ManasSkill)ExtraMysticismSkills.CRYOGENIC_CESSATION.get(),
               (ManasSkill)ExtraMysticismSkills.ICE_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.ICE_DOMINATION.get(),
               (ManasSkill)SpiritualMagics.BLIZZARD.get()
            }
         );
      this.tag(MysticismSkillTags.VIRTUE_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)UniqueSkills.GREAT_SAGE.get(),
               (ManasSkill)UniqueSkills.UNYIELDING.get(),
               (ManasSkill)UniqueSkills.INFINITY_PRISON.get(),
               (ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get(),
               (ManasSkill)UniqueMysticismSkills.REDUCER.get(),
               (ManasSkill)UniqueMysticismSkills.COALESCENCE.get(),
               (ManasSkill)UniqueMysticismSkills.PROVIDER.get()
            }
         );
      this.tag(MysticismSkillTags.SIN_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)UniqueSkills.PRIDE.get(),
               (ManasSkill)UniqueSkills.GLUTTONY.get(),
               (ManasSkill)UniqueSkills.GREED.get(),
               (ManasSkill)UniqueSkills.LUST.get(),
               (ManasSkill)UniqueSkills.SLOTH.get(),
               (ManasSkill)UniqueSkills.WRATH.get(),
               (ManasSkill)UniqueSkills.ENVY.get(),
               (ManasSkill)UniqueMysticismSkills.MELANCHOLY.get()
            }
         );
      this.tag(TensuraSkillTags.NO_PLUNDERING).add((ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get());
      this.tag(MysticismSkillTags.RESTRICTED_LEARNABLE).addOptionalTag(TensuraSkillTags.BATTLEWILL).addOptionalTag(TensuraSkillTags.RESISTANCE_SKILLS);
      this.tag(MysticismSkillTags.RESTRICTED_LEARNABLE)
         .add(
            new ManasSkill[]{
               (ManasSkill)IntrinsicSkills.EYE_OF_TRUTH.get(),
               (ManasSkill)IntrinsicSkills.UNPREDICTABILITY.get(),
               (ManasSkill)IntrinsicSkills.DIVINE_KI_RELEASE.get(),
               (ManasSkill)IntrinsicMysticismSkills.TENACITY.get(),
               (ManasSkill)ExtraSkills.FLAME_DOMINATION.get()
            }
         );
      this.tag(TensuraSkillTags.INTRINSIC_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)IntrinsicMysticismSkills.BULLET_PUNCH.get(),
               (ManasSkill)IntrinsicMysticismSkills.CLANGOROUS_SOUL.get(),
               (ManasSkill)IntrinsicMysticismSkills.CORROSION_TRANSFORM.get(),
               (ManasSkill)IntrinsicMysticismSkills.DISCHARGE.get(),
               (ManasSkill)IntrinsicMysticismSkills.DISSONANCE.get(),
               (ManasSkill)IntrinsicMysticismSkills.EXOSKELETON.get(),
               (ManasSkill)IntrinsicMysticismSkills.HELL_HALL.get(),
               (ManasSkill)IntrinsicMysticismSkills.LETHAL_POISON.get(),
               (ManasSkill)IntrinsicMysticismSkills.LIGHTNING_MODE.get(),
               (ManasSkill)IntrinsicMysticismSkills.MAGISTEEL_BODY.get(),
               (ManasSkill)IntrinsicMysticismSkills.PARALYSIS_TRANSFORM.get(),
               (ManasSkill)IntrinsicMysticismSkills.POISON_TRANSFORM.get(),
               (ManasSkill)IntrinsicMysticismSkills.RELAPSE.get(),
               (ManasSkill)IntrinsicMysticismSkills.SPARK.get(),
               (ManasSkill)IntrinsicMysticismSkills.TENACITY.get()
            }
         );
      this.tag(TensuraSkillTags.EXTRA_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)ExtraMysticismSkills.CRYOGENIC_CESSATION.get(),
               (ManasSkill)ExtraMysticismSkills.DARKNESS_DOMINATION.get(),
               (ManasSkill)ExtraMysticismSkills.DARKNESS_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.GRAVITY_FLUX.get(),
               (ManasSkill)ExtraMysticismSkills.ICE_DOMINATION.get(),
               (ManasSkill)ExtraMysticismSkills.ICE_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.LIGHT_DOMINATION.get(),
               (ManasSkill)ExtraMysticismSkills.LIGHT_MANIPULATION.get(),
               (ManasSkill)ExtraMysticismSkills.MITHRIL_STRENGTH.get(),
               (ManasSkill)ExtraMysticismSkills.PROFANED_PROMINENCE.get()
            }
         );
      this.tag(TensuraSkillTags.UNIQUE_SKILLS)
         .add(
            new ManasSkill[]{
               (ManasSkill)UniqueMysticismSkills.BUTCHER.get(),
               (ManasSkill)UniqueMysticismSkills.CAPTIVATOR.get(),
               (ManasSkill)UniqueMysticismSkills.COALESCENCE.get(),
               (ManasSkill)UniqueMysticismSkills.CONSTANT.get(),
               (ManasSkill)UniqueMysticismSkills.CORRODER.get(),
               (ManasSkill)UniqueMysticismSkills.CRASHER.get(),
               (ManasSkill)UniqueMysticismSkills.CULTIVATOR.get(),
               (ManasSkill)UniqueMysticismSkills.DREAMER.get(),
               (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get(),
               (ManasSkill)UniqueMysticismSkills.GARDENER.get(),
               (ManasSkill)UniqueMysticismSkills.GATEKEEPER.get(),
               (ManasSkill)UniqueMysticismSkills.HIDDEN_RULER.get(),
               (ManasSkill)UniqueMysticismSkills.JINCHURIKI.get(),
               (ManasSkill)UniqueMysticismSkills.KYUREM.get(),
               (ManasSkill)UniqueMysticismSkills.MALLEABLE.get(),
               (ManasSkill)UniqueMysticismSkills.MELANCHOLY.get(),
               (ManasSkill)UniqueMysticismSkills.PHASER.get(),
               (ManasSkill)UniqueMysticismSkills.PROVIDER.get(),
               (ManasSkill)UniqueMysticismSkills.REDUCER.get(),
               (ManasSkill)UniqueMysticismSkills.REPEATER.get(),
               (ManasSkill)UniqueMysticismSkills.RESHIRAM.get(),
               (ManasSkill)UniqueMysticismSkills.RESTRICTED.get(),
               (ManasSkill)UniqueMysticismSkills.SCHOLAR.get(),
               (ManasSkill)UniqueMysticismSkills.SCHRODINGER.get(),
               (ManasSkill)UniqueMysticismSkills.SPIRITUALIST.get(),
               (ManasSkill)UniqueMysticismSkills.STAGNATOR.get(),
               (ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get(),
               (ManasSkill)UniqueMysticismSkills.VICTORIOUS_HARBINGER.get(),
               (ManasSkill)UniqueMysticismSkills.ZEKROM.get()
            }
         );
      this.tag(TensuraSkillTags.ULTIMATE_SKILLS).add((ManasSkill)UltimateMysticismSkills.EMBRYO.get());
   }
}
