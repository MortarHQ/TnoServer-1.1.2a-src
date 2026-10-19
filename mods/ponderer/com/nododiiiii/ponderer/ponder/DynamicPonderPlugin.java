package com.nododiiiii.ponderer.ponder;

import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.blueprint.BlueprintFeature;
import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.mixin.PonderSceneAccessor;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ui.InterfaceSlotOverlayRenderer;
import com.nododiiiii.ponderer.ui.UiAnchorCoords;
import com.nododiiiii.ponderer.ui.UiAnchorViewport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.InputElementBuilder;
import net.createmod.ponder.api.element.TextElementBuilder;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.registration.MultiSceneBuilder;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.SharedTextRegistrationHelper;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.instruction.DisplayWorldSectionInstruction;
import net.createmod.ponder.foundation.instruction.FadeOutOfSceneInstruction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class DynamicPonderPlugin implements PonderPlugin {
   private static final Logger LOGGER = LogUtils.getLogger();

   public String getModId() {
      return "ponderer";
   }

   public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
      NbtSceneFilter.clear();
      SceneRuntime.clearPonderIdMapping();

      for (DslScene scene : SceneRuntime.getScenes()) {
         this.registerScene(helper, scene);
      }

      this.registerBlueprintGuideScene(helper);
   }

   private void registerBlueprintGuideScene(PonderSceneRegistrationHelper<ResourceLocation> helper) {
      Item carrierItem = BlueprintFeature.resolveCarrierItem();
      if (carrierItem != Items.AIR) {
         ResourceLocation carrier = BuiltInRegistries.ITEM.getKey(carrierItem);
         if (carrier != null) {
            helper.forComponents(new ResourceLocation[]{carrier}).addStoryBoard(ResourceLocation.fromNamespaceAndPath("ponderer", "basic"), (scene, util) -> {
               scene.title("blueprint_usage", I18n.get("ponderer.guide.blueprint.title", new Object[0]));
               scene.showBasePlate();
               scene.addKeyframe();
               scene.idle(20);
               Vec3 firstPoint = new Vec3(1.5, 1.0, 1.5);
               Vec3 secondPoint = new Vec3(3.5, 4.0, 3.5);
               Vec3 resizePoint = new Vec3(2.5, 2.5, 1.5);
               Selection firstSelection = util.select().fromTo(1, 1, 1, 3, 3, 3);
               Selection resizedSelection = util.select().fromTo(1, 1, 2, 3, 3, 3);
               ItemStack carrierStack = BlueprintFeature.getCarrierStack();
               scene.overlay().showControls(firstPoint, Pointing.DOWN, 40).rightClick().withItem(carrierStack);
               scene.addKeyframe();
               scene.overlay().showText(40).text(I18n.get("ponderer.guide.blueprint.step1", new Object[0])).pointAt(firstPoint).placeNearTarget();
               scene.idle(50);
               scene.overlay().showControls(secondPoint, Pointing.UP, 40).rightClick().withItem(carrierStack);
               scene.addKeyframe();
               scene.overlay().showText(40).text(I18n.get("ponderer.guide.blueprint.step2", new Object[0])).pointAt(secondPoint).placeNearTarget();
               scene.overlay().showOutline(PonderPalette.BLUE, new Object(), firstSelection, 50);
               scene.idle(50);
               scene.overlay().showControls(resizePoint, Pointing.RIGHT, 40).scroll().whileCTRL().withItem(carrierStack);
               scene.addKeyframe();
               scene.overlay().showText(40).text(I18n.get("ponderer.guide.blueprint.step3_resize", new Object[0])).pointAt(resizePoint).placeNearTarget();
               scene.overlay().showOutline(PonderPalette.BLUE, new Object(), resizedSelection, 50);
               scene.idle(50);
               scene.overlay().showControls(resizePoint, Pointing.RIGHT, 40).rightClick().whileSneaking().withItem(carrierStack);
               scene.addKeyframe();
               scene.overlay().showText(40).text(I18n.get("ponderer.guide.blueprint.step5_discard", new Object[0])).pointAt(resizePoint).placeNearTarget();
               scene.overlay().showOutline(PonderPalette.RED, new Object(), resizedSelection, 40);
            });
         }
      }
   }

   public void registerSharedText(SharedTextRegistrationHelper helper) {
      for (DslScene scene : SceneRuntime.getScenes()) {
         for (DslScene.SceneSegment sc : this.normalizeScenes(scene)) {
            if (sc.steps != null) {
               for (DslScene.DslStep step : sc.steps) {
                  if (step != null
                     && step.type != null
                     && "shared_text".equalsIgnoreCase(step.type)
                     && step.key != null
                     && !step.key.isBlank()
                     && step.text != null
                     && !step.text.resolve().isBlank()) {
                     helper.registerSharedText(step.key, step.text.resolve());
                  }
               }
            }
         }
      }
   }

   private void registerScene(PonderSceneRegistrationHelper<ResourceLocation> helper, DslScene scene) {
      if (scene.items != null && !scene.items.isEmpty()) {
         List<ResourceLocation> components = new ArrayList<>();

         for (String itemId : scene.items) {
            ResourceLocation item = ResourceLocation.tryParse(itemId);
            if (item == null) {
               LOGGER.warn("Invalid item id {} in scene {}", itemId, scene.id);
            } else {
               components.add(item);
            }
         }

         if (components.isEmpty()) {
            LOGGER.warn("Scene {} has no valid items; skipping", scene.id);
         } else {
            ResourceLocation[] tags = this.resolveTags(scene.tags);
            List<DslScene.SceneSegment> sceneList = this.normalizeScenes(scene);
            List<ResourceLocation> schematics = this.resolveSceneSchematics(scene, sceneList);
            MultiSceneBuilder multi = helper.forComponents(components);

            for (int i = 0; i < sceneList.size(); i++) {
               DslScene.SceneSegment sc = sceneList.get(i);
               if (sc.steps != null && !sc.steps.isEmpty()) {
                  ResourceLocation schematic = schematics.get(i);
                  multi.addStoryBoard(schematic, this.createStoryBoard(scene, sc, i, sceneList.size()), tags);
                  ResourceLocation baseId = ResourceLocation.tryParse(scene.id);
                  String basePath = baseId == null ? "scene" : baseId.getPath();
                  String scenePath = sceneList.size() > 1 ? basePath + "_" + this.sceneSuffix(sc, i) : basePath;
                  String fullSceneId = this.getModId() + ":" + scenePath;

                  for (ResourceLocation comp : components) {
                     NbtSceneFilter.registerScene(comp, fullSceneId);
                  }

                  if (scene.nbtFilter != null && !scene.nbtFilter.isBlank()) {
                     CompoundTag nbt = NbtSceneFilter.parseNbt(scene.nbtFilter);
                     if (nbt != null) {
                        NbtSceneFilter.registerFilter(fullSceneId, nbt);
                     }
                  }

                  SceneRuntime.registerPonderIdMapping(fullSceneId, scene.sceneKey(), i);
               }
            }
         }
      } else {
         LOGGER.warn("Scene {} has no items; skipping", scene.id);
      }
   }

   private List<ResourceLocation> resolveSceneSchematics(DslScene scene, List<DslScene.SceneSegment> sceneList) {
      List<ResourceLocation> resolved = new ArrayList<>();
      ResourceLocation current = this.resolveDefaultSchematic(scene);

      for (int i = 0; i < sceneList.size(); i++) {
         DslScene.SceneSegment sc = sceneList.get(i);
         String explicit = this.extractExplicitStructureRef(sc);
         if (explicit != null) {
            ResourceLocation next = this.resolveStructureReference(scene, explicit);
            if (next != null) {
               current = next;
            } else {
               LOGGER.warn("Invalid show_structure.structure '{}' in scene {} segment {}", new Object[]{explicit, scene.id, i + 1});
            }
         }

         resolved.add(current);
      }

      return resolved;
   }

   private ResourceLocation resolveDefaultSchematic(DslScene scene) {
      List<String> pool = this.getStructurePool(scene);
      if (!pool.isEmpty()) {
         ResourceLocation fromPool = this.resolveSchematic(pool.get(0));
         if (fromPool != null) {
            return fromPool;
         }
      }

      return ResourceLocation.fromNamespaceAndPath("ponder", "debug/scene_1");
   }

   private List<String> getStructurePool(DslScene scene) {
      if (scene.structures != null && !scene.structures.isEmpty()) {
         return scene.structures;
      } else {
         return scene.structure != null && !scene.structure.isBlank() ? List.of(scene.structure) : List.of();
      }
   }

   private String extractExplicitStructureRef(DslScene.SceneSegment sc) {
      if (sc != null && sc.steps != null) {
         for (DslScene.DslStep step : sc.steps) {
            if (step != null && step.type != null && "show_structure".equalsIgnoreCase(step.type)) {
               if (step.structure != null && !step.structure.isBlank()) {
                  return step.structure.trim();
               }

               return null;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private ResourceLocation resolveStructureReference(DslScene scene, String ref) {
      if (ref != null && !ref.isBlank()) {
         List<String> pool = this.getStructurePool(scene);
         Integer parsed = this.tryParseInt(ref);
         if (parsed != null) {
            int index = -1;
            if (parsed >= 1 && parsed <= pool.size()) {
               index = parsed - 1;
            } else if (parsed >= 0 && parsed < pool.size()) {
               index = parsed;
            }

            if (index >= 0) {
               return this.resolveSchematic(pool.get(index));
            }
         }

         return this.resolveSchematic(ref);
      } else {
         return null;
      }
   }

   private Integer tryParseInt(String raw) {
      try {
         return Integer.parseInt(raw);
      } catch (Exception ignored) {
         return null;
      }
   }

   private ResourceLocation resolveSchematic(String structure) {
      if (structure == null || structure.isBlank()) {
         return ResourceLocation.fromNamespaceAndPath("ponder", "debug/scene_1");
      } else if (structure.contains(":")) {
         ResourceLocation loc = ResourceLocation.tryParse(structure);
         return loc == null ? ResourceLocation.fromNamespaceAndPath("ponder", "debug/scene_1") : loc;
      } else {
         return ResourceLocation.fromNamespaceAndPath("ponder", structure);
      }
   }

   private ResourceLocation[] resolveTags(List<String> tags) {
      if (tags != null && !tags.isEmpty()) {
         List<ResourceLocation> result = new ArrayList<>();

         for (String tagId : tags) {
            ResourceLocation tag = ResourceLocation.tryParse(tagId);
            if (tag != null) {
               result.add(tag);
            }
         }

         return result.toArray(ResourceLocation[]::new);
      } else {
         return new ResourceLocation[0];
      }
   }

   private PonderStoryBoard createStoryBoard(DslScene scene, DslScene.SceneSegment sc, int index, int total) {
      return (builder, util) -> {
         try {
            ResourceLocation baseId = ResourceLocation.tryParse(scene.id);
            String basePath = baseId == null ? "scene" : baseId.getPath();
            String scenePath = total > 1 ? basePath + "_" + this.sceneSuffix(sc, index) : basePath;
            String title = sc.title != null ? sc.title.resolve() : null;
            if (title == null || title.isBlank()) {
               String sceneTitle = scene.title != null ? scene.title.resolve() : null;
               if (sceneTitle != null && !sceneTitle.isBlank()) {
                  title = total > 1 ? sceneTitle + " #" + (index + 1) : sceneTitle;
               } else {
                  title = scenePath;
               }
            }

            builder.title(scenePath, title);
            if (sc.steps == null) {
               return;
            }

            DynamicPonderPlugin.StepContext context = new DynamicPonderPlugin.StepContext();
            if (this.firstStepIsShowInterface(sc)) {
               builder.removeShadow();
            }

            if (!this.firstStepIsShowStructure(sc)) {
               this.applyShowStructure(builder, new DslScene.DslStep(), context);
               builder.idle(20);
            }

            for (DslScene.DslStep step : sc.steps) {
               if (step != null && step.type != null && !"next_scene".equalsIgnoreCase(step.type)) {
                  this.applyStep(builder, util, scene, step, context);
               }
            }
         } catch (Exception e) {
            LOGGER.error("Error building ponder storyboard for scene {} segment {}: {}", new Object[]{scene.id, index, e.getMessage(), e});
         }
      };
   }

   private void applyStep(SceneBuilder scene, SceneBuildingUtil util, DslScene dsl, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      if (Boolean.TRUE.equals(step.attachKeyFrame)) {
         scene.addKeyframe();
      }

      switch (step.type.toLowerCase(Locale.ROOT)) {
         case "show_structure":
            this.applyShowStructure(scene, step, context);
            break;
         case "idle":
            scene.idle(step.durationOrDefault(20));
            break;
         case "text":
            this.applyText(scene, step, context);
            break;
         case "shared_text":
            this.applySharedText(scene, step, context);
            break;
         case "create_entity":
            this.applyCreateEntity(scene, step);
            break;
         case "create_item_entity":
            this.applyCreateItemEntity(scene, step);
            break;
         case "rotate_camera_y":
            this.applyRotateCameraY(scene, step);
            break;
         case "zoom_scene":
            this.applyZoomScene(scene, step);
            break;
         case "highlight_section":
            this.applyHighlightSection(scene, step);
            break;
         case "show_controls":
            this.applyShowControls(scene, step, context);
            break;
         case "show_interface":
            this.applyShowInterface(scene, step, context);
            break;
         case "change_interface_slot":
            this.applyChangeInterfaceSlot(scene, step);
            break;
         case "click_interface":
            this.applyClickInterface(scene, step);
            break;
         case "encapsulate_bounds":
            this.applyEncapsulateBounds(scene, step);
            break;
         case "play_sound":
            this.applyPlaySound(scene, step);
            break;
         case "set_block":
            this.applySetBlock(scene, step, context);
            break;
         case "destroy_block":
            this.applyDestroyBlock(scene, step, context);
            break;
         case "replace_blocks":
            this.applyReplaceBlocks(scene, step, context);
            break;
         case "hide_section":
            this.applyHideSection(scene, step, context);
            break;
         case "show_section_and_merge":
            this.applyShowSectionAndMerge(scene, step, context);
            break;
         case "rotate_section":
            this.applyRotateSection(scene, step, context);
            break;
         case "move_section":
            this.applyMoveSection(scene, step, context);
            break;
         case "toggle_redstone_power":
            this.applyToggleRedstonePower(scene, step);
            break;
         case "modify_block_entity_nbt":
            this.applyModifyBlockEntityNbt(scene, step);
            break;
         case "indicate_redstone":
            this.applyIndicateRedstone(scene, step);
            break;
         case "indicate_success":
            this.applyIndicateSuccess(scene, step);
            break;
         case "clear_entities":
            this.applyClearEntities(scene, step);
            break;
         case "clear_item_entities":
            this.applyClearItemEntities(scene, step);
            break;
         case "modify_entities_nbt":
            this.applyModifyEntitiesNbt(scene, step);
            break;
         case "modify_item_entities_nbt":
            this.applyModifyItemEntitiesNbt(scene, step);
         case "next_scene":
            break;
         default:
            LOGGER.warn("Unknown step type '{}' in scene {}", step.type, dsl.id);
      }
   }

   private void applyText(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      String text = step.text == null ? "" : step.text.resolve();
      Vec3 point = this.resolveOverlayPoint(scene, step, context);
      int duration = step.durationOrDefault(60);
      TextElementBuilder builder = scene.overlay().showText(duration).text(text).pointAt(point);
      PonderPalette palette = this.parsePalette(step.color);
      if (palette != null) {
         builder.colored(palette);
      }

      if (Boolean.TRUE.equals(step.placeNearTarget)) {
         builder.placeNearTarget();
      }
   }

   private void applySharedText(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      String key = step.key;
      if (key != null && !key.isBlank()) {
         ResourceLocation loc = key.contains(":")
            ? ResourceLocation.tryParse(key)
            : ResourceLocation.fromNamespaceAndPath(scene.getScene().getNamespace(), key);
         if (loc == null) {
            LOGGER.warn("shared_text invalid key: {}", key);
         } else {
            Vec3 point = this.resolveOverlayPoint(scene, step, context);
            int duration = step.durationOrDefault(60);
            TextElementBuilder builder = scene.overlay().showText(duration).sharedText(loc).pointAt(point);
            PonderPalette palette = this.parsePalette(step.color);
            if (palette != null) {
               builder.colored(palette);
            }

            if (Boolean.TRUE.equals(step.placeNearTarget)) {
               builder.placeNearTarget();
            }
         }
      } else {
         LOGGER.warn("shared_text missing key");
      }
   }

   private void applyCreateEntity(SceneBuilder scene, DslScene.DslStep step) {
      ResourceLocation entityId = step.entity == null ? null : ResourceLocation.tryParse(step.entity);
      if (entityId == null) {
         LOGGER.warn("create_entity missing/invalid entity id");
      } else {
         Vec3 pos = this.toPoint(step.pos != null ? step.pos : step.point);
         scene.world()
            .createEntity(
               level -> {
                  EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.getOptional(entityId).orElse(null);
                  if (type == null) {
                     LOGGER.warn("Unknown entity type: {}", entityId);
                     return null;
                  }

                  Entity entity = type.create(level);
                  if (entity != null) {
                     entity.setPosRaw(pos.x, pos.y, pos.z);
                     entity.setOldPosAndRot();
                     Vec3 lookAt = step.lookAt != null && step.lookAt.size() >= 3
                        ? new Vec3(step.lookAt.get(0), step.lookAt.get(1), step.lookAt.get(2))
                        : pos.add(0.0, 0.0, -1.0);
                     entity.lookAt(Anchor.FEET, lookAt);
                     if (step.yaw != null) {
                        entity.setYRot(step.yaw);
                        entity.setYHeadRot(step.yaw);
                        entity.setYBodyRot(step.yaw);
                     }

                     if (step.pitch != null) {
                        entity.setXRot(step.pitch);
                     }

                     if (entity instanceof Mob mob) {
                        mob.setNoAi(true);
                     }

                     entity.setNoGravity(true);
                     entity.setDeltaMovement(Vec3.ZERO);
                     if (step.nbt != null && !step.nbt.isBlank()) {
                        try {
                           CompoundTag patch = TagParser.parseTag(step.nbt);
                           CompoundTag data = new CompoundTag();
                           entity.saveWithoutId(data);
                           data.merge(patch);
                           entity.load(data);
                        } catch (Exception e) {
                           LOGGER.warn("create_entity invalid nbt: {}", step.nbt);
                        }
                     }
                  }

                  return entity;
               }
            );
      }
   }

   private void applyCreateItemEntity(SceneBuilder scene, DslScene.DslStep step) {
      if (step.item != null && !step.item.isBlank()) {
         ResourceLocation itemId = ResourceLocation.tryParse(step.item);
         Item item = itemId == null ? null : (Item)BuiltInRegistries.ITEM.getOptional(itemId).orElse(null);
         if (item == null) {
            LOGGER.warn("create_item_entity unknown item: {}", step.item);
         } else {
            Vec3 pos = this.toPoint(step.pos != null ? step.pos : step.point);
            Vec3 motion = this.toPoint(step.motion);
            int count = step.count == null ? 1 : Math.max(1, step.count);
            CompoundTag patch = null;
            if (step.nbt != null && !step.nbt.isBlank()) {
               try {
                  patch = TagParser.parseTag(step.nbt);
               } catch (Exception e) {
                  LOGGER.warn("create_item_entity invalid nbt: {}", step.nbt);
               }
            }

            CompoundTag finalPatch = patch;
            scene.world().createEntity(level -> {
               ItemStack stack = new ItemStack(item, count);
               if (finalPatch != null) {
                  stack = this.applyPatchToItemStack(stack, finalPatch);
               }

               ItemEntity entity = new ItemEntity(level, pos.x, pos.y, pos.z, stack);
               entity.setDeltaMovement(motion);
               if (finalPatch != null && this.isLikelyEntityPatch(finalPatch)) {
                  CompoundTag data = new CompoundTag();
                  entity.saveWithoutId(data);
                  data.merge(finalPatch.copy());
                  entity.load(data);
               }

               return entity;
            });
         }
      } else {
         LOGGER.warn("create_item_entity missing item id");
      }
   }

   private void applyRotateCameraY(SceneBuilder scene, DslScene.DslStep step) {
      float degrees = step.degrees == null ? 90.0F : step.degrees;
      int duration = step.durationOrDefault(20);
      scene.addInstruction(ponderScene -> {
         LerpedFloat yRotation = ponderScene.getTransform().yRotation;
         float target = yRotation.getChaseTarget() + degrees;
         if (duration == 0) {
            yRotation.startWithValue(target);
         } else {
            yRotation.chaseTimed(target, duration);
         }
      });
      if (duration > 0) {
         scene.idle(duration);
      }
   }

   private void applyZoomScene(SceneBuilder scene, DslScene.DslStep step) {
      float multiplier = step.scale == null ? Float.NaN : step.scale;
      if (multiplier <= 0.0F) {
         LOGGER.warn("zoom_scene scale must be > 0, got {}", multiplier);
      } else {
         boolean useDefaultCenter = step.point == null || step.point.size() < 3;
         Vec3 center = useDefaultCenter ? Vec3.ZERO : this.toPoint(step.point);
         int duration = step.durationOrDefault(20);
         scene.addInstruction(new AdjustViewInstruction(center, useDefaultCenter, multiplier, duration));
      }
   }

   private void applyHighlightSection(SceneBuilder scene, DslScene.DslStep step) {
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         BlockPos pos2 = pos1;
         if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
            pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
         }

         int duration = step.durationOrDefault(40);
         PonderPalette palette = this.parsePalette(step.color);
         if (palette == null) {
            palette = PonderPalette.BLUE;
         }

         Selection selection = scene.getScene().getSceneBuildingUtil().select().fromTo(pos1, pos2);
         scene.overlay().showOutline(palette, new Object(), selection, duration);
      } else {
         LOGGER.warn("highlight_section missing blockPos");
      }
   }

   private void applyShowControls(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      Vec3 point = this.resolveOverlayPoint(scene, step, context);
      Pointing pointing = this.parsePointing(step.direction);
      int duration = step.durationOrDefault(60);
      InputElementBuilder builder = scene.overlay().showControls(point, pointing, duration);
      switch (step.action == null ? "" : step.action.toLowerCase(Locale.ROOT)) {
         case "left":
            builder.leftClick();
            break;
         case "right":
            builder.rightClick();
            break;
         case "scroll":
            builder.scroll();
      }

      if (step.item != null && !step.item.isBlank()) {
         this.resolveIngredient(builder, step.item, step.nbt);
      }

      if (Boolean.TRUE.equals(step.whileSneaking)) {
         builder.whileSneaking();
      }

      if (Boolean.TRUE.equals(step.whileCTRL)) {
         builder.whileCTRL();
      }
   }

   private void applyShowInterface(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      if (step.block != null && !step.block.isBlank()) {
         if (step.blockPos != null && step.blockPos.size() >= 3) {
            PondererServices.PLATFORM.closeInterfaceStep("replace-with-show_interface");
            InterfaceSlotOverlayRenderer.clearRuntimeBindings();
            this.ponderer$resetSceneViewState(scene.getScene());
            scene.addInstruction(this::ponderer$resetSceneViewState);
            scene.addInstruction(new ShowInterfaceInstruction(step));
            context.uiAnchorMode = true;
         } else {
            LOGGER.warn("show_interface missing block context position");
         }
      } else {
         LOGGER.warn("show_interface missing block id");
      }
   }

   private void applyClickInterface(SceneBuilder scene, DslScene.DslStep step) {
      if (step.pos == null || step.pos.size() < 2) {
         LOGGER.warn("click_interface missing point");
      } else if (step.action != null && !step.action.isBlank()) {
         scene.addInstruction(new ClickInterfaceInstruction(step));
      } else {
         LOGGER.warn("click_interface missing action");
      }
   }

   private void applyChangeInterfaceSlot(SceneBuilder scene, DslScene.DslStep step) {
      if (step.interfaceSlots != null && !step.interfaceSlots.isEmpty()) {
         scene.addInstruction(new ChangeInterfaceSlotInstruction(step));
      } else {
         LOGGER.warn("change_interface_slot missing interfaceSlots");
      }
   }

   private void ponderer$resetSceneViewState(PonderScene ps) {
      if (ps instanceof PonderSceneViewOffsetAccess viewOffset) {
         viewOffset.ponderer$resetViewOffset();
         float defaultScale = viewOffset.ponderer$getDefaultScale();
         if (ps instanceof PonderSceneAccessor accessor && !Float.isNaN(defaultScale)) {
            accessor.ponderer$setScaleFactor(defaultScale);
         }

         viewOffset.ponderer$setDefaultScale(Float.NaN);
      }
   }

   private void resolveIngredient(InputElementBuilder builder, String id, @Nullable String nbt) {
      ItemStack stack = this.parseItemStackSpec(id, nbt);
      if (stack != null) {
         builder.withItem(stack);
      } else {
         ResourceLocation loc = ResourceLocation.tryParse(id);
         if (loc != null) {
            Fluid fluid = (Fluid)BuiltInRegistries.FLUID.getOptional(loc).orElse(null);
            if (fluid != null && fluid != Fluids.EMPTY) {
               if (JeiCompat.isAvailable()) {
                  ScreenElement element = JeiCompat.createFluidIngredientElement(fluid, 1000);
                  if (element != null) {
                     builder.showing(element);
                     return;
                  }
               }

               LOGGER.warn("show_controls: fluid '{}' found but JEI is not available for rendering", id);
            } else {
               if (JeiCompat.isAvailable()) {
                  ScreenElement element = JeiCompat.resolveIngredientById(id);
                  if (element != null) {
                     builder.showing(element);
                     return;
                  }
               }

               LOGGER.warn("show_controls: unable to resolve ingredient '{}'", id);
            }
         }
      }
   }

   @Nullable
   private ItemStack parseItemStackSpec(String raw, @Nullable String nbtOverride) {
      if (raw != null && !raw.isBlank()) {
         String trimmed = raw.trim();
         String itemIdPart = trimmed;
         String nbtPart = null;
         int brace = trimmed.indexOf(123);
         if (brace >= 0) {
            itemIdPart = trimmed.substring(0, brace).trim();
            nbtPart = trimmed.substring(brace).trim();
         }

         ResourceLocation itemLoc = ResourceLocation.tryParse(itemIdPart);
         if (itemLoc == null) {
            return null;
         }

         Item item = (Item)BuiltInRegistries.ITEM.getOptional(itemLoc).orElse(null);
         if (item == null) {
            return null;
         }

         ItemStack stack = new ItemStack(item);
         String finalNbtPart = nbtOverride != null && !nbtOverride.isBlank() ? nbtOverride.trim() : nbtPart;
         if (finalNbtPart != null && !finalNbtPart.isBlank() && !"{}".equals(finalNbtPart)) {
            try {
               CompoundTag tag = TagParser.parseTag(finalNbtPart);
               if (!tag.isEmpty()) {
                  ItemStack componentStack = this.tryBuildComponentStack(itemLoc, 1, tag);
                  if (componentStack != null && !componentStack.isEmpty()) {
                     return componentStack;
                  }

                  stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag.copy()));
               }
            } catch (Exception e) {
               LOGGER.warn("show_controls invalid item nbt: {}", finalNbtPart);
            }
         }

         return stack;
      } else {
         return null;
      }
   }

   @Nullable
   private ItemStack tryBuildComponentStack(ResourceLocation itemLoc, int count, CompoundTag tag) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         return null;
      }

      CompoundTag legacy = new CompoundTag();
      legacy.putString("id", itemLoc.toString());
      legacy.putInt("count", Math.max(1, count));
      legacy.put("tag", tag.copy());
      ItemStack fromLegacy = ItemStack.parseOptional(mc.level.registryAccess(), legacy);
      if (!fromLegacy.isEmpty() && !fromLegacy.getComponentsPatch().isEmpty()) {
         return fromLegacy;
      }

      CompoundTag full = new CompoundTag();
      full.putString("id", itemLoc.toString());
      full.putInt("count", Math.max(1, count));
      if (tag.contains("components", 10)) {
         full.put("components", tag.getCompound("components").copy());
      } else {
         CompoundTag components = new CompoundTag();
         if (tag.contains("patterns", 9)) {
            components.put("minecraft:banner_patterns", tag.get("patterns").copy());
         }

         if (tag.contains("Patterns", 9)) {
            components.put("minecraft:banner_patterns", tag.get("Patterns").copy());
         }

         if (tag.contains("BlockEntityTag", 10)) {
            components.put("minecraft:block_entity_data", tag.getCompound("BlockEntityTag").copy());
         }

         if (components.isEmpty()) {
            return null;
         }

         full.put("components", components);
      }

      ItemStack parsed = ItemStack.parseOptional(mc.level.registryAccess(), full);
      return parsed.isEmpty() ? null : parsed;
   }

   private void applyShowStructure(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      PondererServices.PLATFORM.closeInterfaceStep("replace-with-show_structure");
      context.uiAnchorMode = false;
      Selection selection;
      boolean isEverywhere;
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         BlockPos pos2 = pos1;
         if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
            pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
         }

         selection = scene.getScene().getSceneBuildingUtil().select().fromTo(pos1, pos2);
         isEverywhere = false;
      } else {
         selection = scene.getScene().getSceneBuildingUtil().select().everywhere();
         isEverywhere = true;
      }

      scene.world().showSection(selection, Direction.UP);
      if (isEverywhere) {
         context.allBlocksVisible = true;
         context.visibleBlockKeys.clear();
         context.hiddenBlockKeys.clear();
      } else {
         this.updateVisibleRange(context, step, true);
      }

      if (step.scale != null) {
         scene.scaleSceneView(step.scale);
      }

      float rotationOffset = step.rotation == null ? 0.0F : step.rotation;
      scene.addInstruction(ps -> {
         if (ps instanceof PonderSceneAccessor accessor && ps instanceof PonderSceneViewOffsetAccess viewOffset) {
            viewOffset.ponderer$setDefaultScale(accessor.ponderer$getScaleFactor());
         }

         if (rotationOffset != 0.0F) {
            LerpedFloat yRotation = ps.getTransform().yRotation;
            float target = yRotation.getChaseTarget() + rotationOffset;
            yRotation.startWithValue(target);
         }
      });
   }

   private Vec3 resolveOverlayPoint(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      if (!context.uiAnchorMode) {
         return this.toPoint(step.point);
      }

      Minecraft mc = Minecraft.getInstance();
      if (mc != null && mc.getWindow() != null && scene.getScene() != null) {
         double u = 0.0;
         double v = 0.0;
         if (step.point != null && !step.point.isEmpty()) {
            u = step.point.get(0);
            if (step.point.size() >= 2) {
               v = step.point.get(1);
            }
         }

         int guiW = Math.max(1, mc.getWindow().getGuiScaledWidth());
         int guiH = Math.max(1, mc.getWindow().getGuiScaledHeight());
         UiAnchorViewport.Rect viewport = UiAnchorViewport.resolve(mc);
         double xTopLeft = viewport.left() + UiAnchorCoords.decodeToPixelX(u, (int)Math.max(1.0, viewport.width()));
         double yTopLeft = viewport.top() + UiAnchorCoords.decodeToPixelYTopLeft(v, (int)Math.max(1.0, viewport.height()));
         double screenX = UiAnchorCoords.topLeftToTransformX(xTopLeft, guiW);
         double screenY = UiAnchorCoords.topLeftToTransformY(yTopLeft, guiH);
         return scene.getScene().getTransform().screenToScene(screenX, screenY, 0, 0.0F);
      } else {
         return this.toPoint(step.point);
      }
   }

   private void applyEncapsulateBounds(SceneBuilder scene, DslScene.DslStep step) {
      if (step.bounds != null && step.bounds.size() >= 3) {
         BlockPos size = new BlockPos(step.bounds.get(0), step.bounds.get(1), step.bounds.get(2));
         scene.addInstruction(ps -> ps.getWorld().getBounds().encapsulate(size));
      } else {
         LOGGER.warn("encapsulate_bounds missing bounds");
      }
   }

   private void applyPlaySound(SceneBuilder scene, DslScene.DslStep step) {
      if (step.sound != null && !step.sound.isBlank()) {
         ResourceLocation id = ResourceLocation.tryParse(step.sound);
         if (id == null) {
            LOGGER.warn("play_sound invalid sound id: {}", step.sound);
         } else {
            SoundEvent sound = (SoundEvent)BuiltInRegistries.SOUND_EVENT.getOptional(id).orElse(null);
            if (sound == null) {
               LOGGER.warn("play_sound unknown sound: {}", id);
            } else {
               float volume = step.soundVolume == null ? 1.0F : step.soundVolume;
               float pitch = step.pitch == null ? 1.0F : step.pitch;
               SoundSource source = this.parseSoundSource(step.source);
               scene.addInstruction(
                  ps -> {
                     if (Minecraft.getInstance().player != null) {
                        SimpleSoundInstance soundInstance = new SimpleSoundInstance(
                           sound, source, volume, pitch, SoundInstance.createUnseededRandom(), Minecraft.getInstance().player.blockPosition()
                        );
                        Minecraft.getInstance().getSoundManager().play(soundInstance);
                     }
                  }
               );
            }
         }
      } else {
         LOGGER.warn("play_sound missing sound id");
      }
   }

   private void applySetBlock(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      if (step.block != null && !step.block.isBlank()) {
         ResourceLocation blockId = ResourceLocation.tryParse(step.block);
         if (blockId == null) {
            LOGGER.warn("set_block invalid block id: {}", step.block);
         } else {
            Block block = (Block)BuiltInRegistries.BLOCK.getOptional(blockId).orElse(null);
            if (block == null) {
               LOGGER.warn("set_block unknown block: {}", blockId);
            } else {
               BlockState state = block.defaultBlockState();
               state = this.applyBlockProperties(state, step);
               if (step.blockPos != null && step.blockPos.size() >= 3) {
                  BlockPos pos = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
                  boolean immediateDisplay = !Boolean.FALSE.equals(step.immediateDisplay);
                  boolean particles = immediateDisplay && !Boolean.FALSE.equals(step.spawnParticles);
                  BlockPos pos2 = pos;
                  if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
                     pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
                  }

                  String entranceAnimation = this.normalizeEntranceAnimation(step.entranceAnimation);
                  if (entranceAnimation != null && !"none".equals(entranceAnimation)) {
                     this.applyAnimatedSetBlock(scene, step, context, state, pos, pos2, entranceAnimation);
                  } else {
                     this.ensureSceneCanShowRange(scene, pos, pos2, immediateDisplay);
                     this.updateVisibleRange(context, step, immediateDisplay);
                     if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
                        Selection selection = scene.getScene().getSceneBuildingUtil().select().fromTo(pos, pos2);
                        scene.world().setBlocks(selection, state, particles);
                        this.applySetBlockNbtPatch(scene, step, selection);
                     } else {
                        scene.world().setBlock(pos, state, particles);
                        this.applySetBlockNbtPatch(scene, step, scene.getScene().getSceneBuildingUtil().select().position(pos));
                     }
                  }
               } else {
                  LOGGER.warn("set_block missing blockPos");
               }
            }
         }
      } else {
         LOGGER.warn("set_block missing block id");
      }
   }

   private void applyAnimatedSetBlock(
      SceneBuilder scene,
      DslScene.DslStep step,
      DynamicPonderPlugin.StepContext context,
      BlockState state,
      BlockPos pos1,
      BlockPos pos2,
      String entranceAnimation
   ) {
      this.ensureSceneCanShowRange(scene, pos1, pos2, false);
      Selection selection = scene.getScene().getSceneBuildingUtil().select().fromTo(pos1, pos2);
      scene.world().setBlocks(selection, state, false);
      this.applySetBlockNbtPatch(scene, step, selection);
      String linkId = step.linkId == null ? "" : step.linkId.trim();
      if (linkId.isEmpty()) {
         linkId = this.autoLinkId(context);
      }

      Direction direction = this.parseDirection(step.direction);
      ElementLink<WorldSectionElement> existing = context.sectionLinks.get(linkId);
      int rowDuration = step.entranceDuration == null ? 20 : Math.max(0, step.entranceDuration);
      int rowInterval = step.entranceInterval == null ? 1 : Math.max(0, step.entranceInterval);
      boolean smartDisplay = !Boolean.FALSE.equals(step.smartDisplay);
      this.applyAnimatedShowSectionAndMerge(scene, context, linkId, existing, pos1, pos2, entranceAnimation, direction, rowDuration, rowInterval, smartDisplay);
      this.updateVisibleRange(context, step, true);
   }

   private void ensureSceneCanShowRange(SceneBuilder scene, BlockPos pos1, BlockPos pos2, boolean forceVisibleNow) {
      int maxY = Math.max(pos1.getY(), pos2.getY());
      BlockPos requiredBounds = new BlockPos(0, maxY + 1, 0);
      Selection targetSelection = scene.getScene().getSceneBuildingUtil().select().fromTo(pos1, pos2);
      scene.addInstruction(ps -> {
         ps.getWorld().getBounds().encapsulate(requiredBounds);
         if (!forceVisibleNow) {
            if (!ps.getBaseWorldSection().isEmpty()) {
               ps.getBaseWorldSection().erase(targetSelection);
               ps.getBaseWorldSection().queueRedraw();
            }
         } else {
            if (ps.getBaseWorldSection().isEmpty()) {
               Selection all = ps.getSceneBuildingUtil().select().everywhere();
               ps.getBaseWorldSection().set(all);
               ps.getBaseWorldSection().setVisible(true);
               ps.getBaseWorldSection().setFade(1.0F);
            } else {
               ps.getBaseWorldSection().add(targetSelection);
            }

            ps.getBaseWorldSection().queueRedraw();
         }
      });
   }

   private void applySetBlockNbtPatch(SceneBuilder scene, DslScene.DslStep step, Selection selection) {
      if (step.nbt != null && !step.nbt.isBlank()) {
         CompoundTag patch;
         try {
            patch = TagParser.parseTag(step.nbt);
         } catch (Exception e) {
            LOGGER.warn("set_block invalid nbt: {}", step.nbt);
            return;
         }

         scene.world().modifyBlockEntityNBT(selection, BlockEntity.class, nbt -> nbt.merge(patch.copy()), true);
      }
   }

   private void applyDestroyBlock(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         boolean particles = !Boolean.FALSE.equals(step.destroyParticles);
         if (particles) {
            scene.world().destroyBlock(pos);
            this.updateVisibleRange(context, step, false);
         } else {
            scene.world().setBlock(pos, Blocks.AIR.defaultBlockState(), false);
            this.updateVisibleRange(context, step, false);
         }
      } else {
         LOGGER.warn("destroy_block missing blockPos");
      }
   }

   private void applyReplaceBlocks(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      if (step.block != null && !step.block.isBlank()) {
         if (step.blockPos != null && step.blockPos.size() >= 3) {
            ResourceLocation blockId = ResourceLocation.tryParse(step.block);
            if (blockId == null) {
               LOGGER.warn("replace_blocks invalid block id: {}", step.block);
            } else {
               Block block = (Block)BuiltInRegistries.BLOCK.getOptional(blockId).orElse(null);
               if (block == null) {
                  LOGGER.warn("replace_blocks unknown block: {}", blockId);
               } else {
                  BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
                  BlockPos pos2 = pos1;
                  if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
                     pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
                  }

                  boolean particles = !Boolean.FALSE.equals(step.spawnParticles);
                  this.ensureSceneCanShowRange(scene, pos1, pos2, true);
                  Selection selection = scene.getScene().getSceneBuildingUtil().select().fromTo(pos1, pos2);
                  BlockState state = block.defaultBlockState();
                  state = this.applyBlockProperties(state, step);
                  scene.world().replaceBlocks(selection, state, particles);
                  this.updateVisibleRange(context, step, true);
               }
            }
         } else {
            LOGGER.warn("replace_blocks missing blockPos");
         }
      } else {
         LOGGER.warn("replace_blocks missing block id");
      }
   }

   private BlockState applyBlockProperties(BlockState state, DslScene.DslStep step) {
      if (step.blockProperties != null && !step.blockProperties.isEmpty()) {
         StateDefinition<Block, BlockState> definition = state.getBlock().getStateDefinition();

         for (Entry<String, String> entry : step.blockProperties.entrySet()) {
            Property<?> prop = definition.getProperty(entry.getKey());
            if (prop == null) {
               LOGGER.warn("Unknown block property '{}' for {}", entry.getKey(), step.block);
            } else {
               state = setPropertyValue(state, prop, entry.getValue());
            }
         }

         return state;
      } else {
         return state;
      }
   }

   private static <T extends Comparable<T>> BlockState setPropertyValue(BlockState state, Property<T> prop, String value) {
      return prop.getValue(value).map(v -> (BlockState)state.setValue(prop, v)).orElse(state);
   }

   private void applyHideSection(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      Selection selection = this.selectionFromStep(scene, step, "hide_section");
      if (selection != null) {
         this.updateVisibleRange(context, step, false);
         int duration = step.durationOrDefault(20);
         scene.addInstruction(ps -> {
            if (ps.getBaseWorldSection().isEmpty()) {
               LOGGER.warn("hide_section executed before show_structure; auto-showing structure");
               Selection all = ps.getSceneBuildingUtil().select().everywhere();
               ps.getBaseWorldSection().set(all);
               ps.getBaseWorldSection().setVisible(true);
               ps.getBaseWorldSection().setFade(1.0F);
               ps.getBaseWorldSection().queueRedraw();
            }
         });
         Direction direction = this.parseDirection(step.direction);
         ElementLink<WorldSectionElement> link = scene.world().makeSectionIndependent(selection);
         if (duration <= 0) {
            scene.addInstruction(ps -> {
               WorldSectionElement element = (WorldSectionElement)ps.resolve(link);
               if (element != null) {
                  element.setVisible(false);
                  element.setFade(0.0F);
               }
            });
         } else if (duration == 15) {
            scene.world().hideIndependentSection(link, direction);
         } else {
            scene.addInstruction(new FadeOutOfSceneInstruction(duration, direction, link));
         }
      }
   }

   private void applyShowSectionAndMerge(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      Selection selection = this.selectionFromStep(scene, step, "show_section_and_merge");
      if (selection != null) {
         String linkId = step.linkId == null ? "" : step.linkId.trim();
         if (linkId.isEmpty()) {
            linkId = this.autoLinkId(context);
         }

         int duration = step.durationOrDefault(20);
         Direction direction = this.parseDirection(step.direction);
         ElementLink<WorldSectionElement> existing = context.sectionLinks.get(linkId);
         String entranceAnimation = this.normalizeEntranceAnimation(step.entranceAnimation);
         boolean smartDisplay = !Boolean.FALSE.equals(step.smartDisplay);
         if ("none".equals(entranceAnimation)) {
            duration = 0;
         }

         if (entranceAnimation != null && !"none".equals(entranceAnimation)) {
            BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
            BlockPos pos2 = pos1;
            if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
               pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
            }

            int rowDuration = step.entranceDuration == null ? 20 : Math.max(0, step.entranceDuration);
            int rowInterval = step.entranceInterval == null ? 1 : Math.max(0, step.entranceInterval);
            this.applyAnimatedShowSectionAndMerge(
               scene, context, linkId, existing, pos1, pos2, entranceAnimation, direction, rowDuration, rowInterval, smartDisplay
            );
            this.updateVisibleRange(context, step, true);
         } else if (existing == null) {
            ElementLink<WorldSectionElement> created;
            if (duration <= 0) {
               created = scene.world().showIndependentSectionImmediately(selection);
            } else if (duration == 15) {
               created = scene.world().showIndependentSection(selection, direction);
            } else {
               DisplayWorldSectionInstruction instruction = new DisplayWorldSectionInstruction(duration, direction, selection, null);
               scene.addInstruction(instruction);
               created = instruction.createLink(scene.getScene());
            }

            context.sectionLinks.put(linkId, created);
            this.updateVisibleRange(context, step, true);
         } else if (duration <= 0) {
            ElementLink<WorldSectionElement> target = existing;
            scene.addInstruction(ps -> {
               WorldSectionElement element = (WorldSectionElement)ps.resolve(target);
               if (element != null) {
                  element.add(selection);
                  element.queueRedraw();
               }
            });
            this.updateVisibleRange(context, step, true);
         } else if (duration == 15) {
            scene.world().showSectionAndMerge(selection, direction, existing);
            this.updateVisibleRange(context, step, true);
         } else {
            scene.addInstruction(
               new DisplayWorldSectionInstruction(duration, direction, selection, () -> (WorldSectionElement)scene.getScene().resolve(existing))
            );
            this.updateVisibleRange(context, step, true);
         }
      }
   }

   private void applyAnimatedShowSectionAndMerge(
      SceneBuilder scene,
      DynamicPonderPlugin.StepContext context,
      String linkId,
      @Nullable ElementLink<WorldSectionElement> existing,
      BlockPos pos1,
      BlockPos pos2,
      String entranceAnimation,
      Direction entryDirection,
      int rowDuration,
      int rowInterval,
      boolean smartDisplay
   ) {
      List<List<BlockPos>> groups = this.orderedLayerGroups(pos1, pos2, entranceAnimation);
      if (smartDisplay) {
         groups = this.filterVisibleGroups(groups, context);
      }

      if (!groups.isEmpty()) {
         ElementLink<WorldSectionElement> working = existing;

         for (List<BlockPos> group : groups) {
            if (!group.isEmpty()) {
               Selection groupSelection = this.selectionForGroup(scene, group);
               if (working == null) {
                  DisplayWorldSectionInstruction instruction = new DisplayWorldSectionInstruction(rowDuration, entryDirection, groupSelection, null);
                  scene.addInstruction(instruction);
                  working = instruction.createLink(scene.getScene());
                  context.sectionLinks.put(linkId, working);
               } else {
                  ElementLink<WorldSectionElement> target = working;
                  scene.addInstruction(
                     new DisplayWorldSectionInstruction(
                        rowDuration, entryDirection, groupSelection, () -> (WorldSectionElement)scene.getScene().resolve(target)
                     )
                  );
               }

               scene.idle(rowInterval);
            }
         }
      }
   }

   private Selection selectionForGroup(SceneBuilder scene, List<BlockPos> group) {
      BlockPos first = group.get(0);
      int minX = first.getX();
      int minY = first.getY();
      int minZ = first.getZ();
      int maxX = first.getX();
      int maxY = first.getY();
      int maxZ = first.getZ();

      for (int i = 1; i < group.size(); i++) {
         BlockPos pos = group.get(i);
         if (pos.getX() < minX) {
            minX = pos.getX();
         }

         if (pos.getY() < minY) {
            minY = pos.getY();
         }

         if (pos.getZ() < minZ) {
            minZ = pos.getZ();
         }

         if (pos.getX() > maxX) {
            maxX = pos.getX();
         }

         if (pos.getY() > maxY) {
            maxY = pos.getY();
         }

         if (pos.getZ() > maxZ) {
            maxZ = pos.getZ();
         }
      }

      return scene.getScene().getSceneBuildingUtil().select().fromTo(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ));
   }

   private void applyRotateSection(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      ElementLink<WorldSectionElement> link = this.resolveSectionLink(scene, step, context, "rotate_section");
      if (link != null) {
         double x = step.rotX == null ? 0.0 : step.rotX.floatValue();
         double y = step.rotY == null ? (step.degrees == null ? 0.0 : step.degrees.floatValue()) : step.rotY.floatValue();
         double z = step.rotZ == null ? 0.0 : step.rotZ.floatValue();
         int duration = step.durationOrDefault(20);
         scene.world().rotateSection(link, x, y, z, duration);
      }
   }

   private void applyMoveSection(SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context) {
      ElementLink<WorldSectionElement> link = this.resolveSectionLink(scene, step, context, "move_section");
      if (link != null) {
         Vec3 offset = this.toPoint(step.offset);
         int duration = step.durationOrDefault(20);
         scene.world().moveSection(link, offset, duration);
      }
   }

   private void applyToggleRedstonePower(SceneBuilder scene, DslScene.DslStep step) {
      Selection selection = this.selectionFromStep(scene, step, "toggle_redstone_power");
      if (selection != null) {
         scene.world().toggleRedstonePower(selection);
      }
   }

   private void applyModifyBlockEntityNbt(SceneBuilder scene, DslScene.DslStep step) {
      Selection selection = this.selectionFromStep(scene, step, "modify_block_entity_nbt");
      if (selection != null) {
         boolean hasProps = step.blockProperties != null && !step.blockProperties.isEmpty();
         boolean hasNbt = step.nbt != null && !step.nbt.isBlank();
         if (!hasProps && !hasNbt) {
            LOGGER.warn("modify_block_entity_nbt missing both properties and nbt");
         } else {
            boolean redraw = Boolean.TRUE.equals(step.reDrawBlocks);
            if (hasProps) {
               BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
               BlockPos pos2 = pos1;
               if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
                  pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
               }

               for (BlockPos pos : BlockPos.betweenClosed(pos1, pos2)) {
                  BlockPos targetPos = pos.immutable();
                  scene.world().modifyBlock(targetPos, state -> {
                     StateDefinition<Block, BlockState> definition = state.getBlock().getStateDefinition();
                     BlockState result = state;

                     for (Entry<String, String> entry : step.blockProperties.entrySet()) {
                        Property<?> prop = definition.getProperty(entry.getKey());
                        if (prop != null) {
                           result = setPropertyValue(result, prop, entry.getValue());
                        }
                     }

                     return result;
                  }, false);
               }
            }

            if (hasNbt) {
               CompoundTag patch;
               try {
                  patch = TagParser.parseTag(step.nbt);
               } catch (Exception e) {
                  LOGGER.warn("modify_block_entity_nbt invalid nbt: {}", step.nbt);
                  return;
               }

               scene.world().modifyBlockEntityNBT(selection, BlockEntity.class, nbt -> nbt.merge(patch.copy()), redraw);
            }
         }
      }
   }

   private void applyIndicateRedstone(SceneBuilder scene, DslScene.DslStep step) {
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         scene.effects().indicateRedstone(pos);
      } else {
         LOGGER.warn("indicate_redstone missing blockPos");
      }
   }

   private void applyIndicateSuccess(SceneBuilder scene, DslScene.DslStep step) {
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         scene.effects().indicateSuccess(pos);
      } else {
         LOGGER.warn("indicate_success missing blockPos");
      }
   }

   private void applyClearEntities(SceneBuilder scene, DslScene.DslStep step) {
      boolean isFullScene = Boolean.TRUE.equals(step.fullScene);
      String filterId = step.entity;
      ResourceLocation filterLoc = filterId != null && !filterId.isBlank() ? ResourceLocation.tryParse(filterId) : null;
      if (isFullScene) {
         scene.world().modifyEntities(Entity.class, entity -> {
            if (!(entity instanceof ItemEntity)) {
               if (filterLoc == null || EntityType.getKey(entity.getType()).equals(filterLoc)) {
                  entity.discard();
               }
            }
         });
      } else {
         Selection selection = this.selectionFromStep(scene, step, "clear_entities");
         if (selection == null) {
            return;
         }

         scene.world().modifyEntitiesInside(Entity.class, selection, entity -> {
            if (!(entity instanceof ItemEntity)) {
               if (filterLoc == null || EntityType.getKey(entity.getType()).equals(filterLoc)) {
                  entity.discard();
               }
            }
         });
      }
   }

   private void applyClearItemEntities(SceneBuilder scene, DslScene.DslStep step) {
      boolean isFullScene = Boolean.TRUE.equals(step.fullScene);
      String filterId = step.item;
      ResourceLocation filterLoc = filterId != null && !filterId.isBlank() ? ResourceLocation.tryParse(filterId) : null;
      if (isFullScene) {
         scene.world().modifyEntities(ItemEntity.class, entity -> {
            if (filterLoc == null || BuiltInRegistries.ITEM.getKey(entity.getItem().getItem()).equals(filterLoc)) {
               entity.discard();
            }
         });
      } else {
         Selection selection = this.selectionFromStep(scene, step, "clear_item_entities");
         if (selection == null) {
            return;
         }

         scene.world().modifyEntitiesInside(ItemEntity.class, selection, entity -> {
            if (filterLoc == null || BuiltInRegistries.ITEM.getKey(entity.getItem().getItem()).equals(filterLoc)) {
               entity.discard();
            }
         });
      }
   }

   private void applyModifyEntitiesNbt(SceneBuilder scene, DslScene.DslStep step) {
      CompoundTag patch = this.parseEntityNbtPatch(step, "modify_entities_nbt");
      if (patch != null) {
         String filterId = step.entity;
         ResourceLocation filterLoc = filterId != null && !filterId.isBlank() ? ResourceLocation.tryParse(filterId) : null;
         boolean isFullScene = Boolean.TRUE.equals(step.fullScene);
         if (isFullScene) {
            scene.world().modifyEntities(Entity.class, entity -> {
               if (!(entity instanceof ItemEntity)) {
                  if (filterLoc == null || EntityType.getKey(entity.getType()).equals(filterLoc)) {
                     this.mergeEntityNbt(entity, patch);
                  }
               }
            });
         } else {
            Selection selection = this.selectionFromStep(scene, step, "modify_entities_nbt");
            if (selection != null) {
               scene.world().modifyEntitiesInside(Entity.class, selection, entity -> {
                  if (!(entity instanceof ItemEntity)) {
                     if (filterLoc == null || EntityType.getKey(entity.getType()).equals(filterLoc)) {
                        this.mergeEntityNbt(entity, patch);
                     }
                  }
               });
            }
         }
      }
   }

   private void applyModifyItemEntitiesNbt(SceneBuilder scene, DslScene.DslStep step) {
      CompoundTag patch = this.parseEntityNbtPatch(step, "modify_item_entities_nbt");
      if (patch != null) {
         String filterId = step.item;
         ResourceLocation filterLoc = filterId != null && !filterId.isBlank() ? ResourceLocation.tryParse(filterId) : null;
         boolean isFullScene = Boolean.TRUE.equals(step.fullScene);
         if (isFullScene) {
            scene.world().modifyEntities(ItemEntity.class, entity -> {
               if (filterLoc == null || BuiltInRegistries.ITEM.getKey(entity.getItem().getItem()).equals(filterLoc)) {
                  entity.setItem(this.applyPatchToItemStack(entity.getItem(), patch));
                  if (this.isLikelyEntityPatch(patch)) {
                     this.mergeEntityNbt(entity, patch);
                  }
               }
            });
         } else {
            Selection selection = this.selectionFromStep(scene, step, "modify_item_entities_nbt");
            if (selection != null) {
               scene.world().modifyEntitiesInside(ItemEntity.class, selection, entity -> {
                  if (filterLoc == null || BuiltInRegistries.ITEM.getKey(entity.getItem().getItem()).equals(filterLoc)) {
                     entity.setItem(this.applyPatchToItemStack(entity.getItem(), patch));
                     if (this.isLikelyEntityPatch(patch)) {
                        this.mergeEntityNbt(entity, patch);
                     }
                  }
               });
            }
         }
      }
   }

   private ItemStack applyPatchToItemStack(ItemStack base, CompoundTag patch) {
      ItemStack copy = base.copy();
      CompoundTag itemPatch = patch;
      if (patch.contains("Item", 10)) {
         CompoundTag entityItem = patch.getCompound("Item");
         if (entityItem.contains("tag", 10)) {
            itemPatch = entityItem.getCompound("tag");
         } else {
            itemPatch = new CompoundTag();
         }
      }

      if (!itemPatch.isEmpty()) {
         ResourceLocation itemLoc = BuiltInRegistries.ITEM.getKey(copy.getItem());
         ItemStack rebuilt = this.tryBuildComponentStack(itemLoc, copy.getCount(), itemPatch);
         if (rebuilt != null && !rebuilt.isEmpty()) {
            rebuilt.setCount(copy.getCount());
            return rebuilt;
         }

         CompoundTag stackTag = ((CustomData)copy.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
         stackTag.merge(itemPatch.copy());
         copy.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
      }

      return copy;
   }

   private boolean isLikelyEntityPatch(CompoundTag patch) {
      return patch.contains("Item", 10)
         || patch.contains("Age")
         || patch.contains("PickupDelay")
         || patch.contains("Health")
         || patch.contains("Motion")
         || patch.contains("Pos")
         || patch.contains("Rotation")
         || patch.contains("NoGravity")
         || patch.contains("Glowing")
         || patch.contains("Invulnerable")
         || patch.contains("UUID")
         || patch.contains("Tags");
   }

   @Nullable
   private CompoundTag parseEntityNbtPatch(DslScene.DslStep step, String stepName) {
      if (step.nbt != null && !step.nbt.isBlank()) {
         try {
            return TagParser.parseTag(step.nbt);
         } catch (Exception e) {
            LOGGER.warn("{} invalid nbt: {}", stepName, step.nbt);
            return null;
         }
      } else {
         LOGGER.warn("{} missing nbt", stepName);
         return null;
      }
   }

   private void mergeEntityNbt(Entity entity, CompoundTag patch) {
      CompoundTag data = new CompoundTag();
      entity.saveWithoutId(data);
      data.merge(patch.copy());
      entity.load(data);
   }

   private Selection selectionFromStep(SceneBuilder scene, DslScene.DslStep step, String stepName) {
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         BlockPos pos2 = pos1;
         if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
            pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
         }

         return scene.getScene().getSceneBuildingUtil().select().fromTo(pos1, pos2);
      } else {
         LOGGER.warn("{} missing blockPos", stepName);
         return null;
      }
   }

   @Nullable
   private ElementLink<WorldSectionElement> resolveSectionLink(
      SceneBuilder scene, DslScene.DslStep step, DynamicPonderPlugin.StepContext context, String stepName
   ) {
      String linkId = step.linkId == null ? "" : step.linkId.trim();
      if (!linkId.isEmpty()) {
         ElementLink<WorldSectionElement> existing = context.sectionLinks.get(linkId);
         if (existing != null) {
            return existing;
         }
      }

      Selection selection = this.selectionFromStep(scene, step, stepName);
      if (selection == null) {
         if (linkId.isEmpty()) {
            LOGGER.warn("{} requires linkId or blockPos/blockPos2 selection", stepName);
         } else {
            LOGGER.warn("{} missing linkId: {} and no selection to create one", stepName, linkId);
         }

         return null;
      } else {
         ElementLink<WorldSectionElement> created = scene.world().showIndependentSectionImmediately(selection);
         String key = linkId.isEmpty() ? this.autoLinkId(context) : linkId;
         context.sectionLinks.put(key, created);
         this.updateVisibleRange(context, step, true);
         return created;
      }
   }

   private String autoLinkId(DynamicPonderPlugin.StepContext context) {
      return "section_" + (context.sectionLinks.size() + 1);
   }

   @Nullable
   private String normalizeEntranceAnimation(@Nullable String raw) {
      if (raw != null && !raw.isBlank()) {
         return switch (raw.trim().toLowerCase(Locale.ROOT)) {
            case "none", "(无)", "无" -> "none";
            case "simultaneous", "同时" -> "simultaneous";
            case "down", "从上到下", "上到下", "top_to_bottom", "top-down" -> "down";
            case "up", "从下到上", "下到上", "bottom_to_top", "bottom-up" -> "up";
            case "south", "从北到南", "北到南", "north_to_south", "north-south" -> "south";
            case "north", "从南到北", "南到北", "south_to_north", "south-north" -> "north";
            case "east", "从西到东", "西到东", "west_to_east", "west-east" -> "east";
            case "west", "从东到西", "东到西", "east_to_west", "east-west" -> "west";
            default -> null;
         };
      } else {
         return null;
      }
   }

   private List<List<BlockPos>> orderedLayerGroups(BlockPos pos1, BlockPos pos2, String entranceAnimation) {
      int minX = Math.min(pos1.getX(), pos2.getX());
      int minY = Math.min(pos1.getY(), pos2.getY());
      int minZ = Math.min(pos1.getZ(), pos2.getZ());
      int maxX = Math.max(pos1.getX(), pos2.getX());
      int maxY = Math.max(pos1.getY(), pos2.getY());
      int maxZ = Math.max(pos1.getZ(), pos2.getZ());
      List<BlockPos> positions = new ArrayList<>((maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1));

      for (int y = minY; y <= maxY; y++) {
         for (int z = minZ; z <= maxZ; z++) {
            for (int x = minX; x <= maxX; x++) {
               positions.add(new BlockPos(x, y, z));
            }
         }
      }

      Comparator<BlockPos> tieBreaker = Comparator.<BlockPos>comparingInt(p -> p.getY()).thenComparingInt(p -> p.getZ()).thenComparingInt(p -> p.getX());

      Comparator<BlockPos> comparator = switch (entranceAnimation) {
         case "down" -> Comparator.<BlockPos>comparingInt(p -> p.getY()).reversed().thenComparing(tieBreaker);
         case "up" -> Comparator.<BlockPos>comparingInt(p -> p.getY()).thenComparing(tieBreaker);
         case "south" -> Comparator.<BlockPos>comparingInt(p -> p.getZ()).thenComparing(tieBreaker);
         case "north" -> Comparator.<BlockPos>comparingInt(p -> p.getZ()).reversed().thenComparing(tieBreaker);
         case "east" -> Comparator.<BlockPos>comparingInt(p -> p.getX()).thenComparing(tieBreaker);
         case "west" -> Comparator.<BlockPos>comparingInt(p -> p.getX()).reversed().thenComparing(tieBreaker);
         default -> tieBreaker;
      };
      positions.sort(comparator);

      ToIntFunction<BlockPos> layerKey = switch (entranceAnimation) {
         case "simultaneous" -> p -> 0;
         case "south", "north" -> p -> p.getZ();
         case "east", "west" -> p -> p.getX();
         case "down", "up" -> p -> p.getY();
         default -> p -> p.getY();
      };
      List<List<BlockPos>> groups = new ArrayList<>();
      List<BlockPos> currentGroup = null;
      int currentKey = Integer.MIN_VALUE;

      for (BlockPos pos : positions) {
         int key = layerKey.applyAsInt(pos);
         if (currentGroup == null || key != currentKey) {
            currentGroup = new ArrayList<>();
            groups.add(currentGroup);
            currentKey = key;
         }

         currentGroup.add(pos);
      }

      return groups;
   }

   private List<List<BlockPos>> filterVisibleGroups(List<List<BlockPos>> groups, DynamicPonderPlugin.StepContext context) {
      List<List<BlockPos>> filtered = new ArrayList<>();

      for (List<BlockPos> group : groups) {
         List<BlockPos> pending = new ArrayList<>();

         for (BlockPos pos : group) {
            if (!this.isBlockVisible(context, pos.asLong())) {
               pending.add(pos);
            }
         }

         if (!pending.isEmpty()) {
            filtered.add(pending);
         }
      }

      return filtered;
   }

   private void updateVisibleRange(DynamicPonderPlugin.StepContext context, DslScene.DslStep step, boolean visible) {
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         BlockPos pos1 = new BlockPos(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
         BlockPos pos2 = pos1;
         if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
            pos2 = new BlockPos(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
         }

         int minX = Math.min(pos1.getX(), pos2.getX());
         int minY = Math.min(pos1.getY(), pos2.getY());
         int minZ = Math.min(pos1.getZ(), pos2.getZ());
         int maxX = Math.max(pos1.getX(), pos2.getX());
         int maxY = Math.max(pos1.getY(), pos2.getY());
         int maxZ = Math.max(pos1.getZ(), pos2.getZ());

         for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
               for (int x = minX; x <= maxX; x++) {
                  long key = BlockPos.asLong(x, y, z);
                  if (context.allBlocksVisible) {
                     if (visible) {
                        context.hiddenBlockKeys.remove(key);
                     } else {
                        context.hiddenBlockKeys.add(key);
                     }
                  } else if (visible) {
                     context.visibleBlockKeys.add(key);
                  } else {
                     context.visibleBlockKeys.remove(key);
                  }
               }
            }
         }
      }
   }

   private boolean isBlockVisible(DynamicPonderPlugin.StepContext context, long key) {
      return context.allBlocksVisible ? !context.hiddenBlockKeys.contains(key) : context.visibleBlockKeys.contains(key);
   }

   private Direction parseDirection(String raw) {
      if (raw != null && !raw.isBlank()) {
         return switch (raw.toLowerCase(Locale.ROOT)) {
            case "up" -> Direction.UP;
            case "north" -> Direction.NORTH;
            case "south" -> Direction.SOUTH;
            case "west" -> Direction.WEST;
            case "east" -> Direction.EAST;
            default -> Direction.DOWN;
         };
      } else {
         return Direction.DOWN;
      }
   }

   private Vec3 toPoint(List<Double> point) {
      return point != null && point.size() >= 3 ? new Vec3(point.get(0), point.get(1), point.get(2)) : new Vec3(2.5, 1.5, 2.5);
   }

   private Pointing parsePointing(String raw) {
      if (raw != null && !raw.isBlank()) {
         return switch (raw.toLowerCase(Locale.ROOT)) {
            case "up" -> Pointing.UP;
            case "left" -> Pointing.LEFT;
            case "right" -> Pointing.RIGHT;
            default -> Pointing.DOWN;
         };
      } else {
         return Pointing.DOWN;
      }
   }

   private SoundSource parseSoundSource(String raw) {
      if (raw != null && !raw.isBlank()) {
         try {
            return SoundSource.valueOf(raw.toUpperCase(Locale.ROOT));
         } catch (Exception e) {
            return SoundSource.MASTER;
         }
      } else {
         return SoundSource.MASTER;
      }
   }

   private String extractScenePath(String id) {
      if (id != null && !id.isBlank()) {
         ResourceLocation parsed = ResourceLocation.tryParse(id);
         return parsed == null ? id : parsed.getPath();
      } else {
         return "scene";
      }
   }

   private List<DslScene.SceneSegment> normalizeScenes(DslScene scene) {
      if (scene.scenes != null && !scene.scenes.isEmpty()) {
         for (DslScene.SceneSegment seg : scene.scenes) {
            SceneStore.sanitizeScene(scene);
         }

         return scene.scenes;
      } else {
         List<DslScene.SceneSegment> sceneList = new ArrayList<>();
         DslScene.SceneSegment fallback = new DslScene.SceneSegment();
         fallback.steps = List.of();
         sceneList.add(fallback);
         return sceneList;
      }
   }

   private String sceneSuffix(DslScene.SceneSegment sc, int index) {
      return sc.id != null && !sc.id.isBlank() ? sc.id : String.valueOf(index + 1);
   }

   private boolean firstStepIsShowStructure(DslScene.SceneSegment sc) {
      if (sc.steps == null) {
         return false;
      }

      for (DslScene.DslStep step : sc.steps) {
         if (step != null && step.type != null) {
            return "show_structure".equalsIgnoreCase(step.type) || "show_interface".equalsIgnoreCase(step.type);
         }
      }

      return false;
   }

   private boolean firstStepIsShowInterface(DslScene.SceneSegment sc) {
      if (sc.steps == null) {
         return false;
      }

      for (DslScene.DslStep step : sc.steps) {
         if (step != null && step.type != null) {
            return "show_interface".equalsIgnoreCase(step.type);
         }
      }

      return false;
   }

   private PonderPalette parsePalette(String raw) {
      if (raw != null && !raw.isBlank()) {
         return switch (raw.toLowerCase(Locale.ROOT)) {
            case "white" -> PonderPalette.WHITE;
            case "black" -> PonderPalette.BLACK;
            case "red" -> PonderPalette.RED;
            case "green" -> PonderPalette.GREEN;
            case "blue" -> PonderPalette.BLUE;
            case "input" -> PonderPalette.INPUT;
            case "output" -> PonderPalette.OUTPUT;
            case "slow" -> PonderPalette.SLOW;
            case "medium" -> PonderPalette.MEDIUM;
            case "fast" -> PonderPalette.FAST;
            default -> null;
         };
      } else {
         return null;
      }
   }

   private static class StepContext {
      final Map<String, ElementLink<WorldSectionElement>> sectionLinks = new HashMap<>();
      final Set<Long> visibleBlockKeys = new HashSet<>();
      final Set<Long> hiddenBlockKeys = new HashSet<>();
      boolean allBlocksVisible;
      boolean uiAnchorMode;
   }
}
