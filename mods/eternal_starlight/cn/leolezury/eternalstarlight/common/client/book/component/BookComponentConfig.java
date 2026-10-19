package cn.leolezury.eternalstarlight.common.client.book.component;

import java.util.HashSet;
import net.minecraft.resources.ResourceLocation;

public interface BookComponentConfig {
   HashSet<HashSet<ResourceLocation>> unlockConditions();

   ResourceLocation id();
}
