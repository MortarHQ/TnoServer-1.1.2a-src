package it.hurts.sskirillss.relics.client.models.items;

import com.google.common.collect.ImmutableList;
import it.hurts.sskirillss.relics.client.models.items.utils.ModelSide;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import javax.annotation.Nonnull;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.Item;

public class SidedCurioModel extends CurioModel {
   private int slot;

   public SidedCurioModel(Item item) {
      super(item);
   }

   public ModelSide getSide() {
      return this.slot % 2 == 0 ? ModelSide.RIGHT : ModelSide.LEFT;
   }

   @Nonnull
   @Override
   protected Iterable<ModelPart> headParts() {
      return (Iterable<ModelPart>)(this.getItem() instanceof IRenderableCurio renderable
         ? renderable.headParts().stream().filter(entry -> !entry.startsWith(this.getSide().getOpposite().getId())).map(this::getById).toList()
         : ImmutableList.of());
   }

   @Nonnull
   @Override
   protected Iterable<ModelPart> bodyParts() {
      return (Iterable<ModelPart>)(this.getItem() instanceof IRenderableCurio renderable
         ? renderable.bodyParts().stream().filter(entry -> !entry.startsWith(this.getSide().getOpposite().getId())).map(this::getById).toList()
         : ImmutableList.of());
   }

   public int getSlot() {
      return this.slot;
   }

   public void setSlot(int slot) {
      this.slot = slot;
   }
}
