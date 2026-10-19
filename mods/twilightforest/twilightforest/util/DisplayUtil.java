package twilightforest.util;

import com.mojang.math.Transformation;
import com.mojang.serialization.DataResult;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Display.BillboardConstraints;
import net.minecraft.world.entity.Display.TextDisplay.Align;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.joml.Matrix4f;
import twilightforest.beanification.Component;

@Component
public class DisplayUtil {
   public final String tag = "twilightforest_debug_display";

   public boolean spawnBlockDisplay(Level level, BoundingBox box, BlockState displayState, float padding) {
      Transformation transform = new Transformation(
         new Matrix4f().scale(box.getXSpan() + padding * 2.0F, box.getYSpan() + padding * 2.0F, box.getZSpan() + padding * 2.0F)
      );
      transform.getScale();
      DataResult<Tag> serializedTransform = Transformation.EXTENDED_CODEC.encodeStart(NbtOps.INSTANCE, transform);
      if (serializedTransform.isError()) {
         return false;
      }

      CompoundTag entityNBT = new CompoundTag();
      entityNBT.put("transformation", (Tag)serializedTransform.resultOrPartial().orElseGet(CompoundTag::new));
      entityNBT.put("block_state", NbtUtils.writeBlockState(displayState));
      entityNBT.put("Pos", this.newDoubleList(box.minX() - padding, box.minY() - padding, box.minZ() - padding));
      ListTag listtag = new ListTag();
      listtag.add(StringTag.valueOf("twilightforest_debug_display"));
      entityNBT.put("Tags", listtag);
      entityNBT.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BLOCK_DISPLAY).toString());
      Optional<Entity> spawned = EntityType.create(entityNBT, level);
      if (spawned.isEmpty()) {
         return false;
      }

      Entity entity = spawned.get();
      return level.addFreshEntity(entity);
   }

   public ListTag newDoubleList(double... numbers) {
      ListTag listtag = new ListTag();

      for (double d0 : numbers) {
         listtag.add(DoubleTag.valueOf(d0));
      }

      return listtag;
   }

   public void setTextEntity(Level level, double x, double y, double z, BillboardConstraints billboardConstraint, net.minecraft.network.chat.Component name) {
      CompoundTag entityNBT = new CompoundTag();
      entityNBT.put("Pos", this.newDoubleList(x, y, z));
      entityNBT.putString("text", Serializer.toJson(name, level.registryAccess()));
      DataResult<Tag> serializedAlignment = Align.CODEC.encodeStart(NbtOps.INSTANCE, Align.CENTER);
      if (serializedAlignment.isSuccess()) {
         entityNBT.put("alignment", (Tag)serializedAlignment.getPartialOrThrow());
      }

      DataResult<Tag> serializedBillboard = BillboardConstraints.CODEC.encodeStart(NbtOps.INSTANCE, billboardConstraint);
      if (serializedBillboard.isSuccess()) {
         entityNBT.put("billboard", (Tag)serializedBillboard.getPartialOrThrow());
      }

      ListTag listtag = new ListTag();
      listtag.add(StringTag.valueOf("twilightforest_debug_display"));
      entityNBT.put("Tags", listtag);
      entityNBT.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.TEXT_DISPLAY).toString());
      Optional<Entity> spawned = EntityType.create(entityNBT, level);
      if (!spawned.isEmpty()) {
         Entity entity = spawned.get();
         level.addFreshEntity(entity);
      }
   }
}
