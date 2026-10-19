package xaero.hud.minimap.radar.icon.definition;

import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.ArmadilloModel;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.LavaSlimeModel;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.PufferfishMidModel;
import net.minecraft.client.model.PufferfishSmallModel;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.RavagerModel;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer.DragonModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xaero.common.minimap.render.radar.EntityIconDefinitions;
import xaero.common.misc.OptimizedMath;
import xaero.hud.minimap.radar.icon.cache.id.variant.EndermanVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.HorseVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.IronGolemVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.LlamaVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.SaddleVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.TamableVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.TropicalFishVariant;
import xaero.hud.minimap.radar.icon.cache.id.variant.VillagerVariant;
import xaero.hud.minimap.radar.icon.creator.render.form.model.custom.RadarIconCustomPrerenderer;

public class BuiltInRadarIconDefinitions {
   public static float slimeSquishBU;
   public static final Method BUILD_VARIANT_ID_STRING_METHOD;
   public static final Method GET_VARIANT_ID_STRING_METHOD;

   public static List<String> getMainModelPartFields(EntityRenderer<?> renderer, EntityModel<?> model, Entity entity) {
      List<String> result = new ArrayList<>();
      if (model instanceof BatModel) {
         String modelClassPath = BatModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3321"));
         result.add(String.format("%s;%s", modelClassPath, "f_102184_"));
      } else if (model instanceof BlazeModel) {
         String modelClassPath = BlazeModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3329"));
         result.add(String.format("%s;%s", modelClassPath, "f_102245_"));
      } else if (model instanceof SpiderModel) {
         String modelClassPath = SpiderModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3583"));
         result.add(String.format("%s;%s", modelClassPath, "f_103852_"));
      } else if (model instanceof CreeperModel) {
         String modelClassPath = CreeperModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3360"));
         result.add(String.format("%s;%s", modelClassPath, "f_102451_"));
      } else if (model instanceof LlamaModel) {
         String modelClassPath = LlamaModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_27443"));
         result.add(String.format("%s;%s", modelClassPath, "f_103031_"));
      } else if (model instanceof ParrotModel) {
         String modelClassPath = ParrotModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3452"));
         result.add(String.format("%s;%s", modelClassPath, "f_103188_"));
      } else if (model instanceof RabbitModel) {
         String modelClassPath = RabbitModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_27486"));
         result.add(String.format("%s;%s", modelClassPath, "f_103523_"));
      } else if (model instanceof RavagerModel) {
         String modelClassPath = RavagerModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3386"));
         result.add(String.format("%s;%s", modelClassPath, "f_103598_"));
      } else if (model instanceof IronGolemModel) {
         String modelClassPath = IronGolemModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3415"));
         result.add(String.format("%s;%s", modelClassPath, "f_102936_"));
      } else if (model instanceof SnowGolemModel) {
         String modelClassPath = SnowGolemModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3568"));
         result.add(String.format("%s;%s", modelClassPath, "f_103839_"));
      } else if (model instanceof DragonModel) {
         String modelClassPath = DragonModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3630"));
         result.add(String.format("%s;%s", modelClassPath, "f_114235_"));
      } else if (model instanceof ShulkerModel) {
         String modelClassPath = ShulkerModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_3554"));
         result.add(String.format("%s;%s", modelClassPath, "f_103724_"));
      } else if (model instanceof SlimeModel) {
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("children['%s']", "cube")));
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("field_3661['%s']", "cube")));
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("f_104213_['%s']", "cube")));
      } else if (model instanceof AxolotlModel) {
         String modelClassPath = AxolotlModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_28379"));
         result.add(String.format("%s;%s", modelClassPath, "f_170365_"));
      } else if (model instanceof LavaSlimeModel) {
         result.add(String.format("%s;%s", ModelPart.class.getName(), "children['inside_cube']"));
         result.add(String.format("%s;%s", ModelPart.class.getName(), "field_3661['inside_cube']"));
         result.add(String.format("%s;%s", ModelPart.class.getName(), "f_104213_['inside_cube']"));
      } else if (model instanceof SquidModel || model instanceof GhastModel || model instanceof StriderModel || model instanceof PhantomModel) {
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("children['%s']", "body")));
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("field_3661['%s']", "body")));
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("f_104213_['%s']", "body")));
      } else if (model instanceof WardenModel || model instanceof FrogModel) {
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("children['%s']", "head")));
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("field_3661['%s']", "head")));
         result.add(String.format("%s;%s", ModelPart.class.getName(), String.format("f_104213_['%s']", "head")));
      } else if (model instanceof SnifferModel) {
         String modelClassPath = SnifferModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_43085"));
         result.add(String.format("%s;%s", modelClassPath, "f_273862_"));
      } else if (model instanceof CamelModel) {
         String modelClassPath = CamelModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_40464"));
         result.add(String.format("%s;%s", modelClassPath, "f_243837_"));
      } else if (model instanceof BreezeModel) {
         String modelClassPath = BreezeModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_47435"));
         result.add(String.format("%s;%s", modelClassPath, "f_302678_"));
      } else if (model instanceof ArmadilloModel) {
         String modelClassPath = ArmadilloModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "head"));
         result.add(String.format("%s;%s", modelClassPath, "field_47872"));
      } else if (model instanceof WolfModel) {
         String modelClassPath = WolfModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "realHead"));
         result.add(String.format("%s;%s", modelClassPath, "field_20788"));
      }

      return result;
   }

   public static List<String> getSecondaryModelPartsFields(EntityRenderer<?> renderer, EntityModel<?> model, Entity entity) {
      List<String> result = new ArrayList<>();
      if (model instanceof RabbitModel) {
         String modelClassPath = RabbitModel.class.getName();
         result.add(String.format("%s;%s", modelClassPath, "rightEar"));
         result.add(String.format("%s;%s", modelClassPath, "field_27487"));
         result.add(String.format("%s;%s", modelClassPath, "f_170877_"));
         result.add(String.format("%s;%s", modelClassPath, "leftEar"));
         result.add(String.format("%s;%s", modelClassPath, "field_27488"));
         result.add(String.format("%s;%s", modelClassPath, "f_170878_"));
         result.add(String.format("%s;%s", modelClassPath, "nose"));
         result.add(String.format("%s;%s", modelClassPath, "field_3530"));
         result.add(String.format("%s;%s", modelClassPath, "f_103527_"));
      }

      return result;
   }

   public static Object getModelRoot(EntityModel<?> entityModel) {
      if (entityModel instanceof SquidModel
         || entityModel instanceof GhastModel
         || entityModel instanceof SlimeModel
         || entityModel instanceof PhantomModel
         || entityModel instanceof StriderModel
         || entityModel instanceof LavaSlimeModel) {
         return ((HierarchicalModel)entityModel).root();
      } else if (entityModel instanceof WardenModel) {
         return ((HierarchicalModel)entityModel).root().getChild("bone").getChild("body");
      } else {
         return entityModel instanceof FrogModel ? ((HierarchicalModel)entityModel).root().getChild("body") : entityModel;
      }
   }

   public static boolean forceFieldCheck(EntityModel<?> entityModel) {
      return entityModel instanceof AxolotlModel || entityModel instanceof WolfModel;
   }

   public static void defaultTransformation(PoseStack matrixStack, EntityModel em, Entity entity) {
      if (em instanceof CodModel || em instanceof SalmonModel) {
         OptimizedMath.rotatePose(matrixStack, 90.0F, OptimizedMath.YP);
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      } else if (em instanceof ColorableHierarchicalModel) {
         OptimizedMath.rotatePose(matrixStack, 90.0F, OptimizedMath.YP);
      } else if (em instanceof BatModel) {
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      } else if (em instanceof HorseModel) {
         OptimizedMath.rotatePose(matrixStack, 65.0F, OptimizedMath.XP);
         matrixStack.scale(0.7F, 0.7F, 0.7F);
      } else if (em instanceof DolphinModel || em instanceof GoatModel) {
         matrixStack.scale(0.7F, 0.7F, 0.7F);
      } else if (em instanceof GuardianModel || em instanceof SquidModel) {
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      } else if (em instanceof HoglinModel) {
         OptimizedMath.rotatePose(matrixStack, 45.0F, OptimizedMath.XP);
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      } else if (em instanceof LlamaModel || em instanceof CamelModel || em instanceof SnifferModel) {
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      } else if (entity instanceof Slime slime) {
         slimeSquishBU = slime.squish;
         slime.squish = 0.0F;
      } else if (em instanceof GhastModel || em instanceof RavagerModel || em instanceof StriderModel || em instanceof DragonModel) {
         matrixStack.scale(0.5F, 0.5F, 0.5F);
      } else if (em instanceof WitherBossModel) {
         matrixStack.scale(0.35F, 0.35F, 0.35F);
      } else if (em instanceof PhantomModel) {
         matrixStack.scale(0.3F, 0.3F, 0.3F);
         OptimizedMath.rotatePose(matrixStack, 90.0F, OptimizedMath.XP);
      } else if (em instanceof PandaModel) {
         matrixStack.scale(0.7F, 0.7F, 0.7F);
      } else if (em instanceof WardenModel) {
         matrixStack.scale(0.7F, 0.7F, 0.7F);
      }
   }

   public static void defaultPostIconModelRender(PoseStack matrixStack, EntityModel entityModel, Entity entity) {
      if (entity instanceof Slime slime) {
         slime.squish = slimeSquishBU;
      }
   }

   public static boolean fullModelIcon(EntityModel em) {
      return em instanceof CodModel
         || em instanceof SalmonModel
         || em instanceof ColorableHierarchicalModel
         || em instanceof BeeModel
         || em instanceof DolphinModel
         || em instanceof GuardianModel
         || em instanceof EndermiteModel
         || em instanceof LavaSlimeModel
         || em instanceof SlimeModel
         || em instanceof PufferfishBigModel
         || em instanceof PufferfishMidModel
         || em instanceof PufferfishSmallModel
         || em instanceof SilverfishModel
         || em instanceof WitherBossModel;
   }

   public static <T extends Entity> RadarIconCustomPrerenderer getCustomLayer(EntityRenderer<? super T> entityRenderer, T entity) {
      return null;
   }

   public static <E extends Entity> Object getVariant(ResourceLocation entityTexture, EntityRenderer<? super E> entityRenderer, E entity) {
      if (entity instanceof Horse) {
         return new HorseVariant(entityTexture, ((Horse)entity).getMarkings());
      } else if (entity instanceof VillagerDataHolder) {
         VillagerData villagerdata = ((VillagerDataHolder)entity).getVillagerData();
         VillagerType villagertype = villagerdata.getType();
         VillagerProfession villagerprofession = villagerdata.getProfession();
         int villagerprofessionlevel = villagerdata.getLevel();
         return new VillagerVariant(entityTexture, ((LivingEntity)entity).isBaby(), villagertype, villagerprofession, villagerprofessionlevel);
      } else if (entity instanceof Cat || entity instanceof Wolf) {
         return new TamableVariant(entityTexture, ((TamableAnimal)entity).isTame());
      } else if (entity instanceof IronGolem) {
         return new IronGolemVariant(entityTexture, ((IronGolem)entity).getCrackiness());
      } else if (entity instanceof Llama llama) {
         return new LlamaVariant(entityTexture, llama.isTraderLlama(), llama.getSwag());
      } else if (entity instanceof Pig) {
         return new SaddleVariant(entityTexture, ((Pig)entity).isSaddled());
      } else if (entity instanceof Strider) {
         return new SaddleVariant(entityTexture, ((Strider)entity).isSaddled());
      } else if (entity instanceof TropicalFish fish) {
         return new TropicalFishVariant(entityTexture, fish.getVariant(), fish.getBaseColor(), fish.getPatternColor());
      } else if (entity instanceof EnderMan enderman) {
         return new EndermanVariant(entityTexture, enderman.isCreepy());
      } else if (entity instanceof ItemEntity itemEntity) {
         return BuiltInRegistries.ITEM.getKey(itemEntity.getItem().getItem());
      } else if (entity instanceof ItemFrame itemFrame) {
         ItemStack itemFrameStack = itemFrame.getItem();
         Item item = itemFrameStack == null ? Items.AIR : itemFrameStack.getItem();
         return BuiltInRegistries.ITEM.getKey(item);
      } else {
         return entityTexture == null ? "default" : entityTexture;
      }
   }

   static {
      try {
         BUILD_VARIANT_ID_STRING_METHOD = EntityIconDefinitions.class
            .getDeclaredMethod("buildVariantIdString", StringBuilder.class, EntityRenderer.class, Entity.class);
         GET_VARIANT_ID_STRING_METHOD = EntityIconDefinitions.class.getDeclaredMethod("getVariantString", EntityRenderer.class, Entity.class);
      } catch (NoSuchMethodException e) {
         throw new RuntimeException(e);
      }
   }
}
