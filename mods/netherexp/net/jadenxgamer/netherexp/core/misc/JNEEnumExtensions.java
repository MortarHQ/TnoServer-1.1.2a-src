package net.jadenxgamer.netherexp.core.misc;

import java.util.function.UnaryOperator;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class JNEEnumExtensions {
   public static final EnumProxy<Rarity> ARTIFACT_ENUM_PROXY = new EnumProxy(
      Rarity.class, new Object[]{Rarity.getExtensionInfo().totalCount() + 1, "netherexp:artifact", (UnaryOperator)style -> style.withColor(15218975)}
   );
}
