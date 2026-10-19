package twilightforest.block.entity;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity.WobbleStyle;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.block.JarBlock;
import twilightforest.components.item.JarLid;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;

public class JarBlockEntity extends BlockEntity {
   public static final Codec<Item> ITEM_CODEC = BuiltInRegistries.ITEM.byNameCodec();
   public static final Map<Item, BooleanSupplier> REGISTERED_LOG_LIDS = new HashMap<>();
   public static final String TAG_LID = "lid";
   public static final ResourceLocation JAR_LID = TwilightForestMod.prefix("jar_lid");
   public static final int EVENT_POT_WOBBLES = 1;
   @NotNull
   public Item lid = TFBlocks.TWILIGHT_OAK_LOG.asItem();
   public long wobbleStartedAtTick;
   @Nullable
   public WobbleStyle lastWobbleStyle;

   public static void addLid(Item item, BooleanSupplier supplier) {
      REGISTERED_LOG_LIDS.put(item, supplier);
   }

   public static void addLid(Item item) {
      addLid(item, () -> true);
   }

   public JarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
      super(type, pos, blockState);
      if (blockState.getBlock() instanceof JarBlock jarBlock) {
         this.lid = jarBlock.getDefaultLid();
      }
   }

   public JarBlockEntity(BlockPos pos, BlockState state) {
      this((BlockEntityType<?>)TFBlockEntities.JAR.get(), pos, state);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.put("lid", (Tag)ITEM_CODEC.encodeStart(NbtOps.INSTANCE, this.lid).getOrThrow());
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.lid = tag.contains("lid")
         ? ITEM_CODEC.parse(NbtOps.INSTANCE, tag.get("lid")).result().orElse(TFBlocks.TWILIGHT_OAK_LOG.asItem())
         : TFBlocks.TWILIGHT_OAK_LOG.asItem();
   }

   public ItemStack getJarAsItem() {
      return (ItemStack)Util.make(this.getBlockState().getBlock().asItem().getDefaultInstance(), jar -> jar.applyComponents(this.collectComponents()));
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      return this.saveCustomOnly(registries);
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      builder.set(TFDataComponents.JAR_LID, new JarLid(this.lid));
   }

   protected void applyImplicitComponents(DataComponentInput input) {
      super.applyImplicitComponents(input);
      this.lid = ((JarLid)input.getOrDefault(TFDataComponents.JAR_LID, new JarLid(TFBlocks.TWILIGHT_OAK_LOG.asItem()))).lid();
   }

   public void removeComponentsFromTag(CompoundTag tag) {
      super.removeComponentsFromTag(tag);
      tag.remove("lid");
   }

   public void wobble(WobbleStyle style) {
      if (this.level != null && !this.level.isClientSide()) {
         this.level.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), 1, style.ordinal());
      }
   }

   public boolean triggerEvent(int id, int type) {
      if (this.level != null && id == 1 && type >= 0 && type < WobbleStyle.values().length) {
         this.wobbleStartedAtTick = this.level.getGameTime();
         this.lastWobbleStyle = WobbleStyle.values()[type];
         return true;
      } else {
         return super.triggerEvent(id, type);
      }
   }
}
