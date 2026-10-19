package artifacts.platform;

import artifacts.component.SwimData;
import artifacts.registry.Register;
import java.nio.file.Path;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.Nullable;

public interface PlatformHelper {
   @Nullable
   SwimData getSwimData(LivingEntity var1);

   Holder<Attribute> getSwimSpeedAttribute();

   boolean isFishingRod(ItemStack var1);

   Path getConfigDir();

   void addItemRegistryCallback(Consumer<Item> var1);

   boolean isModLoaded(String var1);

   <R> Register<R> createRegister(ResourceKey<Registry<R>> var1);

   SpawnEggItem createMimicSpawnEgg(Properties var1);
}
