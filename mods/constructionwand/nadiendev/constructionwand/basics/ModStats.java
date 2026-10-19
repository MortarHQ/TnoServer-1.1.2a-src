package nadiendev.constructionwand.basics;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStats {
   public static final DeferredRegister<ResourceLocation> CUSTOM_STATS = DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, "constructionwand");
   public static final ResourceLocation USE_WAND = ResourceLocation.fromNamespaceAndPath("constructionwand", "use_wand");
   public static final Supplier<ResourceLocation> USE_WAND_STAT = CUSTOM_STATS.register("use_wand", () -> USE_WAND);
}
