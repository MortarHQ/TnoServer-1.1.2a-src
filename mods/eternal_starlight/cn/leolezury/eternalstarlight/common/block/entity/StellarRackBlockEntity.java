package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StellarRackBlockEntity extends RandomizableContainerBlockEntity {
   private static final SmoothSegmentedValue RED = SmoothSegmentedValue.of(Easing.IN_OUT_EXPO, 0.9843137F, 0.5921569F, 0.5F)
      .add(Easing.IN_OUT_EXPO, 0.5921569F, 0.9843137F, 0.5F);
   private static final SmoothSegmentedValue GREEN = SmoothSegmentedValue.of(Easing.IN_OUT_EXPO, 0.5058824F, 0.88235295F, 0.5F)
      .add(Easing.IN_OUT_EXPO, 0.88235295F, 0.5058824F, 0.5F);
   private static final SmoothSegmentedValue BLUE = SmoothSegmentedValue.of(Easing.IN_OUT_EXPO, 0.007843138F, 0.98039216F, 0.5F)
      .add(Easing.IN_OUT_EXPO, 0.98039216F, 0.007843138F, 0.5F);
   private static final SmoothSegmentedValue ALPHA = SmoothSegmentedValue.of(Easing.IN_OUT_EXPO, 0.9F, 1.0F, 0.5F).add(Easing.IN_OUT_EXPO, 1.0F, 0.9F, 0.5F);
   private NonNullList<ItemStack> items;
   private int tickCount = 0;
   private float oldStarRotation;
   private float starRotation;

   public StellarRackBlockEntity(BlockPos pos, BlockState state) {
      this(ESBlockEntities.STELLAR_RACK.get(), pos, state);
   }

   public StellarRackBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
      this.items = NonNullList.withSize(5, ItemStack.EMPTY);
   }

   public int getTickCount() {
      return this.tickCount;
   }

   public boolean anyEmpty() {
      return this.items.stream().anyMatch(ItemStack::isEmpty);
   }

   public boolean placeItem(ItemStack itemStack) {
      for (int i = 0; i < this.getContainerSize(); i++) {
         if (this.getItem(i).isEmpty()) {
            this.setItem(i, itemStack);
            this.markUpdated();
            return true;
         }
      }

      return false;
   }

   private void markUpdated() {
      if (this.getLevel() != null) {
         this.setChanged();
         this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, StellarRackBlockEntity entity) {
      entity.tickCount++;
      entity.oldStarRotation = entity.starRotation;
      entity.starRotation += (float) (Math.PI / 18);
      if (level.isClientSide) {
         EternalStarlight.getClientHelper().spawnStellarRackParticles(pos.getCenter());
         float angle = 360.0F / (float)entity.getItems().stream().filter(i -> !i.isEmpty()).count();
         float accumulatedAngle = entity.getStarRotation(0.0F) * 0.4F * (180.0F / (float)Math.PI);

         for (ItemStack itemStack : entity.getItems()) {
            if (!itemStack.isEmpty()) {
               accumulatedAngle += angle;
               Vec3 vec3 = ESMathUtil.rotationToPosition(0.9F, 0.0F, accumulatedAngle);
               EternalStarlight.getClientHelper().spawnStellarRackItemParticles(entity.getBlockPos().getBottomCenter().add(0.0, 0.9, 0.0).add(vec3));
            }
         }
      }

      if (level.getGameTime() % 600L == 0L) {
         level.playSound(null, pos, ESSoundEvents.STELLAR_RACK_AMBIENT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
      }
   }

   public int getColor(float partialTicks) {
      float ticks = (this.getTickCount() + partialTicks) % 250.0F;
      return ARGB32.colorFromFloat(
         ALPHA.calculate(ticks / 250.0F), RED.calculate(ticks / 250.0F), GREEN.calculate(ticks / 250.0F), BLUE.calculate(ticks / 250.0F)
      );
   }

   public float getStarRotation(float partialTicks) {
      return Mth.lerp(partialTicks, this.oldStarRotation, this.starRotation);
   }

   protected Component getDefaultName() {
      return Component.translatable("container.eternal_starlight.stellar_rack");
   }

   public NonNullList<ItemStack> getItems() {
      return this.items;
   }

   protected void setItems(NonNullList<ItemStack> items) {
      this.items = items;
   }

   protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
      return null;
   }

   public int getContainerSize() {
      return 5;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag compoundTag = new CompoundTag();
      ContainerHelper.saveAllItems(compoundTag, this.items, true, provider);
      return compoundTag;
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(tag)) {
         ContainerHelper.loadAllItems(tag, this.items, provider);
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      if (!this.trySaveLootTable(tag)) {
         ContainerHelper.saveAllItems(tag, this.items, provider);
      }
   }
}
