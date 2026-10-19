package com.mortal_cultivation.spiritroot;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;

public enum SpiritRootType {
   HEAVEN("heaven", SpiritRootType.Category.HEAVENLY, Element.SPACE, SpiritLevel.LESSER, ExtraSkills.SPATIAL_MANIPULATION::get, 5),
   EARTH("earth", SpiritRootType.Category.COMMON, Element.EARTH, SpiritLevel.LESSER, ExtraSkills.EARTH_MANIPULATION::get, 10),
   FIRE("fire", SpiritRootType.Category.COMMON, Element.FLAME, SpiritLevel.LESSER, ExtraSkills.FLAME_MANIPULATION::get, 10),
   WATER("water", SpiritRootType.Category.COMMON, Element.WATER, SpiritLevel.LESSER, ExtraSkills.WATER_MANIPULATION::get, 10),
   WIND("wind", SpiritRootType.Category.COMMON, Element.WIND, SpiritLevel.LESSER, ExtraSkills.WIND_MANIPULATION::get, 10),
   SPACE("space", SpiritRootType.Category.COMMON, Element.SPACE, SpiritLevel.LESSER, ExtraSkills.SPATIAL_MANIPULATION::get, 10),
   OVERLORD_BODY("overlord_body", SpiritRootType.Category.MUTATED, null, null, null, 0);

   private final String id;
   private final SpiritRootType.Category category;
   private final Element element;
   private final SpiritLevel spiritLevel;
   private final Supplier<? extends ManasSkill> inspirationSkill;
   private final int randomWeight;

   SpiritRootType(
      String id, SpiritRootType.Category category, Element element, SpiritLevel spiritLevel, Supplier<? extends ManasSkill> inspirationSkill, int randomWeight
   ) {
      this.id = id;
      this.category = category;
      this.element = element;
      this.spiritLevel = spiritLevel;
      this.inspirationSkill = inspirationSkill;
      this.randomWeight = randomWeight;
   }

   public String getId() {
      return this.id;
   }

   public SpiritRootType.Category getCategory() {
      return this.category;
   }

   public Element getElement() {
      return this.element;
   }

   public SpiritLevel getSpiritLevel() {
      return this.spiritLevel;
   }

   public ManasSkill getInspirationSkill() {
      return this.inspirationSkill == null ? null : this.inspirationSkill.get();
   }

   public int getColor() {
      if (this == HEAVEN) {
         return 16175973;
      } else {
         return this == OVERLORD_BODY ? 11872818 : this.element.getColor();
      }
   }

   public int getRandomWeight() {
      return this.randomWeight;
   }

   public boolean canRollRandomly() {
      return this.randomWeight > 0 && this.category == SpiritRootType.Category.COMMON;
   }

   public List<Element> getResonantElements() {
      if (this == OVERLORD_BODY) {
         return List.of();
      } else {
         return this == HEAVEN ? List.of(Element.EARTH, Element.FLAME, Element.WATER, Element.WIND, Element.SPACE) : List.of(this.element);
      }
   }

   public List<ManasSkill> getGrantedSkills() {
      return switch (this) {
         case HEAVEN -> {
            LinkedHashSet<ManasSkill> skills = new LinkedHashSet<>();

            for (SpiritRootType root : List.of(EARTH, FIRE, WATER, WIND, SPACE)) {
               skills.addAll(root.getGrantedSkills());
            }

            yield List.copyOf(skills);
         }
         case EARTH -> List.of(
            (ManasSkill)SpiritualMagics.EARTH.get(),
            this.getInspirationSkill(),
            (ManasSkill)IntrinsicSkills.EARTH_TRANSFORM.get(),
            (ManasSkill)ResistanceSkills.EARTH_ATTACK_RESISTANCE.get(),
            (ManasSkill)ExtraSkills.MAGIC_SENSE.get()
         );
         case FIRE -> List.of(
            (ManasSkill)SpiritualMagics.FIRE.get(),
            this.getInspirationSkill(),
            (ManasSkill)IntrinsicSkills.FLAME_TRANSFORM.get(),
            (ManasSkill)ResistanceSkills.FLAME_ATTACK_RESISTANCE.get(),
            (ManasSkill)IntrinsicSkills.FLAME_BREATH.get()
         );
         case WATER -> List.of(
            (ManasSkill)SpiritualMagics.WATER.get(),
            this.getInspirationSkill(),
            (ManasSkill)IntrinsicSkills.WATER_TRANSFORM.get(),
            (ManasSkill)ResistanceSkills.WATER_ATTACK_RESISTANCE.get(),
            (ManasSkill)CommonSkills.POISON.get(),
            (ManasSkill)ResistanceSkills.POISON_RESISTANCE.get()
         );
         case WIND -> List.of(
            (ManasSkill)SpiritualMagics.WIND.get(),
            this.getInspirationSkill(),
            (ManasSkill)IntrinsicSkills.WIND_TRANSFORM.get(),
            (ManasSkill)ResistanceSkills.WIND_ATTACK_RESISTANCE.get()
         );
         case SPACE -> List.of(
            (ManasSkill)SpiritualMagics.SPACE.get(),
            this.getInspirationSkill(),
            (ManasSkill)IntrinsicSkills.SPACE_TRANSFORM.get(),
            (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get()
         );
         case OVERLORD_BODY -> List.of();
      };
   }

   public MutableComponent getDisplayName() {
      return Component.translatable("mortal_cultivation.spirit_root." + this.id).setStyle(Style.EMPTY.withColor(this.getColor()));
   }

   public MutableComponent getSpiritDisplayName() {
      if (this == HEAVEN) {
         return Component.translatable("mortal_cultivation.spirit_root.heaven.spirit").setStyle(Style.EMPTY.withColor(this.getColor()));
      } else {
         return this == OVERLORD_BODY
            ? Component.translatable("mortal_cultivation.spirit_root.overlord_body.spirit").setStyle(Style.EMPTY.withColor(this.getColor()))
            : this.spiritLevel.getSpiritName(this.element).setStyle(Style.EMPTY.withColor(this.spiritLevel.getChatFormatting()));
      }
   }

   public MutableComponent getInspirationSkillName() {
      if (this == HEAVEN) {
         return Component.translatable("mortal_cultivation.spirit_root.heaven.inspiration").setStyle(Style.EMPTY.withColor(this.getColor()));
      } else {
         return this == OVERLORD_BODY
            ? Component.translatable("mortal_cultivation.spirit_root.overlord_body.inspiration").setStyle(Style.EMPTY.withColor(this.getColor()))
            : this.getInspirationSkill().getName().copy().setStyle(Style.EMPTY.withColor(this.getColor()));
      }
   }

   public static Optional<SpiritRootType> byId(String id) {
      if (id != null && !id.isBlank()) {
         String normalized = id.toLowerCase(Locale.ROOT);
         return Arrays.stream(values()).filter(type -> type.id.equals(normalized)).findFirst();
      } else {
         return Optional.empty();
      }
   }

   public static List<String> ids() {
      return Arrays.stream(values()).map(SpiritRootType::getId).toList();
   }

   public static SpiritRootType randomCommonRoot(RandomSource random) {
      if (random.nextFloat() < 0.05F) {
         return HEAVEN;
      }

      List<SpiritRootType> pool = Arrays.stream(values()).filter(SpiritRootType::canRollRandomly).toList();
      int totalWeight = pool.stream().mapToInt(SpiritRootType::getRandomWeight).sum();
      int roll = random.nextInt(totalWeight);

      for (SpiritRootType root : pool) {
         roll -= root.getRandomWeight();
         if (roll < 0) {
            return root;
         }
      }

      return pool.getFirst();
   }

   public enum Category {
      COMMON("common"),
      DUAL("dual"),
      MUTATED("mutated"),
      HEAVENLY("heavenly");

      private final String id;

      Category(String id) {
         this.id = id;
      }

      public String getId() {
         return this.id;
      }
   }
}
