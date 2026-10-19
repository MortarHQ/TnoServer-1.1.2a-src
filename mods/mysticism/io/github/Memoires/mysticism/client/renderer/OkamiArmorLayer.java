package io.github.Memoires.mysticism.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class OkamiArmorLayer<T extends LivingEntity & GeoAnimatable> extends ItemArmorGeoLayer<T> {
   public OkamiArmorLayer(GeoRenderer<T> renderer) {
      super(renderer);
   }

   @Nullable
   protected ItemStack getArmorItemForBone(GeoBone bone, T animatable) {
      return switch (bone.getName()) {
         case "Head" -> this.helmetStack;
         case "Body", "LeftArmcoat", "RightArmcoat" -> this.chestplateStack;
         case "LeftLeg", "RightLeg" -> this.leggingsStack;
         case "LeftBootArmor", "RightBootArmor" -> this.bootsStack;
         default -> null;
      };
   }

   @NotNull
   protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, T animatable, HumanoidModel<?> baseModel) {
      return switch (bone.getName()) {
         case "Body" -> baseModel.body;
         case "LeftArmcoat" -> baseModel.leftArm;
         case "RightArmcoat" -> baseModel.rightArm;
         case "Head" -> baseModel.head;
         case "LeftLeg" -> baseModel.leftLeg;
         case "RightLeg" -> baseModel.rightLeg;
         default -> baseModel.body;
      };
   }
}
