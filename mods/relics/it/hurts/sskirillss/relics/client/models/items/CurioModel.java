package it.hurts.sskirillss.relics.client.models.items;

import com.google.common.collect.ImmutableList;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class CurioModel extends HumanoidModel<LivingEntity> {
   private final Item item;
   private final ModelPart root;

   private CurioModel(Item item, ModelPart root) {
      super(root);
      this.item = item;
      this.root = root;
   }

   public CurioModel(Item item) {
      this(item, constructRoot(item));
   }

   public static ModelLayerLocation getLayerLocation(Item item) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
      return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath()), id.getPath());
   }

   public static ModelPart constructRoot(Item item) {
      return Minecraft.getInstance().getEntityModels().bakeLayer(getLayerLocation(item));
   }

   public ModelPart getById(String id) {
      return switch (id) {
         case "head" -> this.head;
         case "hat" -> this.hat;
         case "body" -> this.body;
         case "right_arm" -> this.rightArm;
         case "left_arm" -> this.leftArm;
         case "right_leg" -> this.rightLeg;
         case "left_leg" -> this.leftLeg;
         default -> this.root.getChild(id);
      };
   }

   @Nonnull
   protected Iterable<ModelPart> headParts() {
      return (Iterable<ModelPart>)(this.item instanceof IRenderableCurio renderable
         ? renderable.headParts().stream().map(this::getById).toList()
         : ImmutableList.of());
   }

   @Nonnull
   protected Iterable<ModelPart> bodyParts() {
      return (Iterable<ModelPart>)(this.item instanceof IRenderableCurio renderable
         ? renderable.bodyParts().stream().map(this::getById).toList()
         : ImmutableList.of());
   }

   public Item getItem() {
      return this.item;
   }

   public ModelPart getRoot() {
      return this.root;
   }
}
