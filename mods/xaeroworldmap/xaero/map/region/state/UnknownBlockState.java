package xaero.map.region.state;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class UnknownBlockState extends BlockState {
   private CompoundTag nbt;
   private String stringRepresentation;

   public UnknownBlockState(CompoundTag nbt) {
      super(Blocks.AIR, new Reference2ObjectArrayMap(), null);
      this.nbt = nbt;
      this.stringRepresentation = "Unknown: " + nbt;
   }

   public void write(DataOutputStream out) throws IOException {
      NbtIo.write(this.nbt, out);
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
