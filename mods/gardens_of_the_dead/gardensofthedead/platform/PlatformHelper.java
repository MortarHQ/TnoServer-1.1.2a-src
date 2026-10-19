package gardensofthedead.platform;

import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public interface PlatformHelper {
   boolean isSword(ItemStack var1);

   boolean isShears(ItemStack var1);

   FlowerPotBlock createFlowerPot(RegistrySupplier<? extends Block> var1, Properties var2);

   RotatedPillarBlock createStrippableBlock(Supplier<? extends Block> var1, Properties var2);

   Properties copyBlockPropertiesWithLoot(Properties var1, ResourceLocation var2);
}
