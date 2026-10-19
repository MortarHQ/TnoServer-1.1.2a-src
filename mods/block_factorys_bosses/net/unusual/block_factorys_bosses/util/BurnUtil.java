package net.unusual.block_factorys_bosses.util;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BurnUtil {
   public static void regItemBurn(FireBlock fire) {
      fire.setFlammable((Block)BossesRiseBlocks.BIG_OAK_PLANKS.get(), 5, 20);
      fire.setFlammable((Block)BossesRiseBlocks.BIG_OAK_PLANKS_BROKEN.get(), 5, 20);
      fire.setFlammable((Block)BossesRiseBlocks.BIG_OAK_PLANKS_CRACKED.get(), 5, 20);
      fire.setFlammable((Block)BossesRiseBlocks.BIG_OAK_PLANKS_STRAIGHT.get(), 5, 20);
      fire.setFlammable((Block)BossesRiseBlocks.BIG_OAK_PLANKS_VARIATION.get(), 5, 20);
   }
}
