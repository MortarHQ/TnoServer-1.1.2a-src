package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public enum ManaType implements StringRepresentable {
   EMPTY("empty", 13724603),
   TERRA("terra", 10705491),
   WIND("wind", 16777148),
   WATER("water", 12114667),
   LUNAR("lunar", 3363434),
   BLAZE("blaze", 12930645),
   LIGHT("light", 9606576);

   public static final Codec<ManaType> CODEC = StringRepresentable.fromEnum(ManaType::values);
   private final String name;
   private final int color;

   ManaType(String name, int color) {
      this.name = name;
      this.color = color;
   }

   public TagKey<Item> getCrystalsTag() {
      return this == EMPTY ? ESTags.Items.MANA_CRYSTALS : TagKey.create(Registries.ITEM, EternalStarlight.id(this.name + "_crystals"));
   }

   public int getColor() {
      return this.color;
   }

   public String getSerializedName() {
      return this.name;
   }
}
