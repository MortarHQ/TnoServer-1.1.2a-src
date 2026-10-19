package uk.co.extraspecialstudio.lootr_liaison.mimic;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import uk.co.extraspecialstudio.lootr_liaison.Config;
import uk.co.extraspecialstudio.lootr_liaison.LiaisonNbt;

public final class MimicStabilityLayer {
   private static final Random RAND = new Random();
   private static final double DEFAULT_MIMIC_CHANCE = 0.1;

   public static void rollAtFinalization(Level level, BlockPos pos, RandomizableContainerBlockEntity be) {
      if (Config.enableArtifactsMimicCompat && ModList.get().isLoaded("artifacts")) {
         CompoundTag tag = be.getPersistentData();
         if (!LiaisonNbt.isMimicChecked(tag)) {
            if (!isExcluded(level, pos, be)) {
               double chance = resolveMimicChance();
               boolean mimic = RAND.nextDouble() < chance;
               tag.putBoolean("lootr_liaison:mimic_checked", true);
               tag.putString("lootr_liaison:mimic_result", mimic ? "armed" : "none");
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onRightClickBlock(RightClickBlock event) {
      if (!event.getLevel().isClientSide()) {
         if (Config.enableArtifactsMimicCompat && ModList.get().isLoaded("artifacts")) {
            if ("first_interact".equalsIgnoreCase(Config.mimicRollTiming)) {
               BlockPos pos = event.getPos();
               BlockEntity be = event.getLevel().getBlockEntity(pos);
               if (be instanceof RandomizableContainerBlockEntity rcb) {
                  CompoundTag tag = be.getPersistentData();
                  if (!LiaisonNbt.isMimicChecked(tag)) {
                     if (LiaisonNbt.isFinalized(tag)) {
                        if (!isExcluded(event.getLevel(), pos, rcb)) {
                           double chance = resolveMimicChance();
                           boolean mimic = RAND.nextDouble() < chance;
                           tag.putBoolean("lootr_liaison:mimic_checked", true);
                           tag.putString("lootr_liaison:mimic_result", mimic ? "armed" : "none");
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isExcluded(Level level, BlockPos pos, RandomizableContainerBlockEntity be) {
      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock());
      String idStr = id.toString();
      return Config.excludeContainerTypes.stream().anyMatch(s -> idStr.equals(s));
   }

   private static double resolveMimicChance() {
      if (Config.respectArtifactsConfig && ModList.get().isLoaded("artifacts")) {
      }

      return 0.1;
   }
}
