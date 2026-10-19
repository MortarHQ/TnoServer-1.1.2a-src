package nadiendev.constructionwand.basics.option;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Locale;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import nadiendev.constructionwand.api.IWandCore;
import nadiendev.constructionwand.api.IWandUpgrade;
import nadiendev.constructionwand.basics.ReplacementRegistry;
import nadiendev.constructionwand.component.ModDataComponents;
import nadiendev.constructionwand.items.core.CoreDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WandOptions {
   public final WandUpgradesSelectable<IWandCore> cores;
   public final OptionEnum<WandOptions.LOCK> lock;
   public final OptionEnum<WandOptions.DIRECTION> direction;
   public final OptionBoolean replace;
   public final OptionEnum<WandOptions.MATCH> match;
   public final OptionBoolean random;
   public final IOption<?>[] allOptions;

   public WandOptions(ItemStack wandStack) {
      this.cores = new WandUpgradesSelectable<>(wandStack, ModDataComponents.CORES.get(), "cores", new CoreDefault());
      this.lock = new OptionEnum<>(wandStack, ModDataComponents.LOCK.get(), "lock", WandOptions.LOCK.class, WandOptions.LOCK.NOLOCK);
      this.direction = new OptionEnum<>(wandStack, ModDataComponents.DIRECTION.get(), "direction", WandOptions.DIRECTION.class, WandOptions.DIRECTION.TARGET);
      this.replace = new OptionBoolean(wandStack, ModDataComponents.REPLACE.get(), "replace", true);
      this.match = new OptionEnum<>(wandStack, ModDataComponents.MATCH.get(), "match", WandOptions.MATCH.class, WandOptions.MATCH.SIMILAR);
      this.random = new OptionBoolean(wandStack, ModDataComponents.RANDOM.get(), "random", false);
      this.allOptions = new IOption[]{this.cores, this.lock, this.direction, this.replace, this.match, this.random};
   }

   @Nullable
   public IOption<?> get(String key) {
      for (IOption<?> option : this.allOptions) {
         if (option.getKey().equals(key)) {
            return option;
         }
      }

      return null;
   }

   public boolean testLock(WandOptions.LOCK l) {
      return this.lock.get() == WandOptions.LOCK.NOLOCK ? true : this.lock.get() == l;
   }

   public boolean matchBlocks(Block b1, Block b2) {
      switch ((WandOptions.MATCH)this.match.get()) {
         case EXACT:
            return b1 == b2;
         case SIMILAR:
            return ReplacementRegistry.matchBlocks(b1, b2);
         case ANY:
            return b1 != Blocks.AIR && b2 != Blocks.AIR;
         default:
            return false;
      }
   }

   public boolean hasUpgrade(IWandUpgrade upgrade) {
      return upgrade instanceof IWandCore ? this.cores.hasUpgrade((IWandCore)upgrade) : false;
   }

   public boolean addUpgrade(IWandUpgrade upgrade) {
      return upgrade instanceof IWandCore ? this.cores.addUpgrade((IWandCore)upgrade) : false;
   }

   public enum DIRECTION implements StringRepresentable {
      TARGET(0),
      PLAYER(1);

      public static final Codec<WandOptions.DIRECTION> CODEC = StringRepresentable.fromEnum(WandOptions.DIRECTION::values);
      private static final IntFunction<WandOptions.DIRECTION> BY_ID = ByIdMap.continuous(WandOptions.DIRECTION::getId, values(), OutOfBoundsStrategy.ZERO);
      public static final StreamCodec<ByteBuf, WandOptions.DIRECTION> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, WandOptions.DIRECTION::getId);
      private final int id;

      DIRECTION(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public enum LOCK implements StringRepresentable {
      HORIZONTAL(0),
      VERTICAL(1),
      NORTHSOUTH(2),
      EASTWEST(3),
      NOLOCK(4);

      public static final Codec<WandOptions.LOCK> CODEC = StringRepresentable.fromEnum(WandOptions.LOCK::values);
      private static final IntFunction<WandOptions.LOCK> BY_ID = ByIdMap.continuous(WandOptions.LOCK::getId, values(), OutOfBoundsStrategy.ZERO);
      public static final StreamCodec<ByteBuf, WandOptions.LOCK> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, WandOptions.LOCK::getId);
      private final int id;

      LOCK(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public enum MATCH implements StringRepresentable {
      EXACT(0),
      SIMILAR(1),
      ANY(2);

      public static final Codec<WandOptions.MATCH> CODEC = StringRepresentable.fromEnum(WandOptions.MATCH::values);
      private static final IntFunction<WandOptions.MATCH> BY_ID = ByIdMap.continuous(WandOptions.MATCH::getId, values(), OutOfBoundsStrategy.ZERO);
      public static final StreamCodec<ByteBuf, WandOptions.MATCH> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, WandOptions.MATCH::getId);
      private final int id;

      MATCH(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
