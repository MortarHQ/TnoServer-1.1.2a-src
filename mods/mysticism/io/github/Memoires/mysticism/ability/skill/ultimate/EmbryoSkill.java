package io.github.Memoires.mysticism.ability.skill.ultimate;

import io.github.Memoires.mysticism.ability.MysticSkill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EmbryoSkill extends MysticSkill {
   public EmbryoSkill() {
      super(SkillType.ULTIMATE, ResistType.RESISTANCE, MysticSkill.UltTier.LORD);
   }

   @Override
   public MutableComponent getSkillTitle() {
      return super.getSkillTitle();
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/ultimate/embryo.png");
   }

   @Override
   public MutableComponent getSkillDescription() {
      ResourceLocation id = this.getRegistryName();
      if (id == null) {
         return Component.empty();
      }

      String baseKey = id.getNamespace() + ".skill." + id.getPath().replace('/', '.');
      return Component.translatable(baseKey + ".description_1").append(Component.translatable(baseKey + ".description_2").withStyle(ChatFormatting.RED));
   }
}
