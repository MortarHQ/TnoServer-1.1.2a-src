package twilightforest.compat.top;

import java.util.Optional;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.TwilightForestMod;
import twilightforest.block.ChiseledCanopyShelfBlock;
import twilightforest.block.entity.bookshelf.BookshelfSpawner;
import twilightforest.block.entity.bookshelf.ChiseledCanopyShelfBlockEntity;
import twilightforest.init.TFBlocks;

public enum ChiseledBookshelfSpawnProvider implements IProbeInfoProvider {
   INSTANCE;

   public ResourceLocation getID() {
      return TwilightForestMod.prefix("chiseled_bookshelf_spawner");
   }

   public void addProbeInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData data) {
      if (blockState.is(TFBlocks.CHISELED_CANOPY_BOOKSHELF)
         && (Boolean)blockState.getValue(ChiseledCanopyShelfBlock.SPAWNER)
         && (player.isHolding(ModItems.CREATIVE_PROBE) || player.isCreative())
         && level.getBlockEntity(data.getPos()) instanceof ChiseledCanopyShelfBlockEntity shelf) {
         BookshelfSpawner logic = shelf.getSpawner();
         CompoundTag tag = logic.getNextSpawnData().entityToSpawn();
         Optional<EntityType<?>> optional = EntityType.by(tag);
         optional.ifPresent(
            type -> info.horizontal(info.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
               .text(CompoundText.create().style(TextStyleClass.LABEL).text("Mob: ").info(type.getDescriptionId()))
         );
      }
   }
}
