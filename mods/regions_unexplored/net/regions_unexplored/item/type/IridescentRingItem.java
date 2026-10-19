package net.regions_unexplored.item.type;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.regions_unexplored.client.color.RuColors;
import net.regions_unexplored.registry.RUParticleTypes;
import org.jetbrains.annotations.NotNull;

public class IridescentRingItem extends Item {
   public IridescentRingItem(Properties properties) {
      super(properties);
   }

   public void inventoryTick(ItemStack itemStack, Level level, Entity owner, int i, boolean inSlot) {
      RandomSource random = level.getRandom();
      if (level instanceof ServerLevel serverLevel && inSlot && !random.nextBoolean()) {
         serverLevel.sendParticles(
            RUParticleTypes.PRISMARITE_SPARKLE.get(),
            owner.getX() + random.nextGaussian(),
            owner.getY(0.5) + random.nextGaussian(),
            owner.getZ() + random.nextGaussian(),
            1,
            0.0,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @NotNull
   public Component getName(ItemStack stack) {
      String string = super.getName(stack).getString();
      char[] characters = string.toCharArray();
      MutableComponent name = Component.empty();
      float step = 50.0F / characters.length;

      for (int i = 0; i < characters.length; i++) {
         char character = characters[i];
         name.append(Component.literal(String.valueOf(character)).withColor(RuColors.getRainbowColor(0.0F, i * step)));
      }

      return name;
   }
}
