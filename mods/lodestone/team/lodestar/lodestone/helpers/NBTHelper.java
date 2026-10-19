package team.lodestar.lodestone.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import team.lodestar.lodestone.LodestoneLib;

public class NBTHelper {
   public static BlockPos readBlockPos(CompoundTag tag) {
      return (BlockPos)BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("position")).resultOrPartial(LodestoneLib.LOGGER::error).orElse(null);
   }

   public static BlockPos readBlockPos(ListTag tag, int index) {
      return (BlockPos)BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get(index)).resultOrPartial(LodestoneLib.LOGGER::error).orElse(null);
   }

   public static CompoundTag saveBlockPos(BlockPos pos) {
      return saveBlockPos(new CompoundTag(), pos);
   }

   public static CompoundTag saveBlockPos(CompoundTag tag, BlockPos pos) {
      BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, pos).resultOrPartial(LodestoneLib.LOGGER::error).ifPresent(p -> tag.put("position", p));
      return tag;
   }

   public static ListTag saveBlockPosToList(BlockPos pos) {
      return saveBlockPosToList(new ListTag(), pos);
   }

   public static ListTag saveBlockPosToList(ListTag tag, BlockPos pos) {
      BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, pos).resultOrPartial(LodestoneLib.LOGGER::error).ifPresent(tag::add);
      return tag;
   }
}
