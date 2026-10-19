package twilightforest.client.event;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import net.neoforged.neoforge.client.event.RenderFrameEvent.Pre;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFAttributeModifiers;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFItems;
import twilightforest.init.TFKeyBinds;
import twilightforest.init.TFSounds;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.TravellersArmorBeltItem;
import twilightforest.item.travellers_gear.TravellersGearLogic;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;
import twilightforest.network.CycleMapSlotPacket;
import twilightforest.network.GogglesZoomPacket;
import twilightforest.network.GradualGlidePacket;
import twilightforest.network.PerformDoubleJumpPacket;
import twilightforest.network.PerformSidestepPacket;
import twilightforest.network.SwapHotbarPacket;

@Component(dist = Dist.CLIENT)
public class TravellersClientEvents {
   private static boolean isZoomKeyHeld(Player player) {
      return TFKeyBinds.ZOOM_KEY.isDown() && !player.isScoping();
   }

   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::handleDoubleJump);
      NeoForge.EVENT_BUS.addListener(this::handleAgileRanger);
      NeoForge.EVENT_BUS.addListener(this::handleStraightAhead);
      NeoForge.EVENT_BUS.addListener(this::speedUpControlledWhileSneaking);
      NeoForge.EVENT_BUS.addListener(this::handleSidestep);
      NeoForge.EVENT_BUS.addListener(this::handleStealth);
      NeoForge.EVENT_BUS.addListener(this::updateZoomState);
      NeoForge.EVENT_BUS.addListener(this::updateGradualGlideState);
      NeoForge.EVENT_BUS.addListener(this::cycleItemDisplayMap);
      NeoForge.EVENT_BUS.addListener(this::slowZoomSensitivity);
      NeoForge.EVENT_BUS.addListener(this::swapHotbar);
      NeoForge.EVENT_BUS.addListener(this::toggleRedThreadVision);
      NeoForge.EVENT_BUS.addListener(this::renderGlovesInFirstPerson);
   }

   private void handleAgileRanger(MovementInputUpdateEvent event) {
      if (event.getEntity() instanceof LocalPlayer localPlayer) {
         ItemStack leggingsStack = localPlayer.getItemBySlot(EquipmentSlot.LEGS);
         Float agileRangerModifier = (Float)leggingsStack.get(TFDataComponents.AGILE_RANGER_MODIFIER);
         if (TravellersModifiersManager.isModifierActive(localPlayer, leggingsStack, TravellersModifiersManager.AGILE_RANGER_MODIFIER)
            && agileRangerModifier != null) {
            ItemStack stack = localPlayer.getUseItem();
            boolean isLegalItem = (stack.getItem() instanceof ProjectileWeaponItem || stack.is(ItemTagGenerator.TRAVELLERS_AGILE_RANGER_WHITELISTED))
               && !stack.is(ItemTagGenerator.TRAVELLERS_AGILE_RANGER_BLACKLISTED);
            if (localPlayer.isUsingItem() && !localPlayer.isPassenger() && isLegalItem) {
               Input input = event.getInput();
               input.leftImpulse = input.leftImpulse * agileRangerModifier;
               input.forwardImpulse = input.forwardImpulse * agileRangerModifier;
            }
         }
      }
   }

   private void handleStraightAhead(MovementInputUpdateEvent event) {
      if (event.getEntity() instanceof LocalPlayer localPlayer) {
         ItemStack bootsStack = localPlayer.getItemBySlot(EquipmentSlot.FEET);
         Double multiplier = (Double)bootsStack.get(TFDataComponents.STRAIGHT_AHEAD_MULTIPLIER);
         AttributeInstance attributeInstance = localPlayer.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
         if (attributeInstance != null) {
            Input input = localPlayer.input;
            if (!TravellersModifiersManager.isModifierActive(localPlayer, bootsStack, TravellersModifiersManager.STRAIGHT_AHEAD_MODIFIER)
               || multiplier == null
               || input.forwardImpulse <= 0.0F) {
               multiplier = 1.0;
            }

            attributeInstance.addOrUpdateTransientModifier(
               new AttributeModifier(TFAttributeModifiers.STRAIGHT_AHEAD_ATTRIBUTE_MODIFIER_LOCATION, multiplier - 1.0, Operation.ADD_MULTIPLIED_TOTAL)
            );
            input.leftImpulse = (float)(input.leftImpulse / multiplier);
         }
      }
   }

   private void speedUpControlledWhileSneaking(MovementInputUpdateEvent event) {
      if (event.getEntity() instanceof LocalPlayer localPlayer
         && (Boolean)localPlayer.getData(TFDataAttachments.IS_GRADUALLY_GLIDING)
         && localPlayer.isShiftKeyDown()) {
         localPlayer.input.forwardImpulse /= 0.2F;
         localPlayer.input.leftImpulse /= 0.2F;
      }
   }

   private void handleSidestep(MovementInputUpdateEvent event) {
      if (event.getEntity() instanceof LocalPlayer localPlayer && localPlayer.onGround()) {
         Input input = localPlayer.input;
         boolean lastImpulseZero = (Float)localPlayer.getData(TFDataAttachments.LAST_HORIZONTAL_IMPULSE) == 0.0F;
         boolean sameImpulseDirection = Math.signum((Float)localPlayer.getData(TFDataAttachments.LAST_NON_ZERO_HORIZONTAL_IMPULSE))
            == Math.signum(input.leftImpulse);
         int currentTime = localPlayer.tickCount;
         int lastWalkingTime = (Integer)localPlayer.getData(TFDataAttachments.LAST_HORIZONTAL_WALKING_TIME);
         boolean hasDoubleTapped = currentTime - lastWalkingTime < 4;
         if (lastImpulseZero && sameImpulseDirection && hasDoubleTapped && input.leftImpulse != 0.0F) {
            boolean isLeftSidestep = input.leftImpulse > 0.0F;
            if (TravellersGearLogic.tryPerformSidestep(localPlayer, isLeftSidestep)) {
               localPlayer.connection.send(new PerformSidestepPacket(isLeftSidestep));
            }
         }

         localPlayer.setData(TFDataAttachments.LAST_HORIZONTAL_IMPULSE, input.leftImpulse);
         if (input.leftImpulse != 0.0F) {
            localPlayer.setData(TFDataAttachments.LAST_HORIZONTAL_WALKING_TIME, currentTime);
            localPlayer.setData(TFDataAttachments.LAST_NON_ZERO_HORIZONTAL_IMPULSE, input.leftImpulse);
         }
      }
   }

   private void handleStealth(Pre event) {
      if (Minecraft.getInstance().level != null) {
         for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
            if (entity instanceof Player player) {
               TravellersGearLogic.travellersStealth(player, player1 -> player1.setInvisible(true));
            }
         }
      }
   }

   private void handleDoubleJump(Key event) {
      if (Minecraft.getInstance().player instanceof LocalPlayer localPlayer && !this.ignoreKeyEvent(event, Minecraft.getInstance().options.keyJump)) {
         int lastJumpKeyPressTime = (Integer)localPlayer.getData(TFDataAttachments.LAST_JUMP_KEY_PRESS_TIME);
         boolean pressedKey = event.getAction() == 1;
         if (pressedKey) {
            localPlayer.setData(TFDataAttachments.LAST_JUMP_KEY_PRESS_TIME, localPlayer.tickCount);
         }

         boolean avoidCreativeFly = localPlayer.mayFly() && localPlayer.tickCount - lastJumpKeyPressTime <= 6;
         if (pressedKey
            && !avoidCreativeFly
            && TravellersModifiersManager.isModifierActive(localPlayer, TravellersModifiersManager.DOUBLE_JUMP_MODIFIER)
            && TravellersGearLogic.performDoubleJump(localPlayer)) {
            localPlayer.connection.send(new PerformDoubleJumpPacket());
         }
      }
   }

   private void updateZoomState(ComputeFovModifierEvent event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         boolean wasUsingZoom = (Boolean)player.getData(TFDataAttachments.IS_USING_GOGGLES_ZOOM_MODIFIER);
         ItemStack headStack = player.getItemBySlot(EquipmentSlot.HEAD);
         Float zoomModifier = (Float)headStack.get(TFDataComponents.ZOOM_ABILITY_MODIFIER);
         boolean isUsingZoom = isZoomKeyHeld(player)
            && TravellersModifiersManager.isModifierActive(player, headStack, TravellersModifiersManager.ZOOM_ABILITY)
            && zoomModifier != null;
         if (isUsingZoom) {
            event.setNewFovModifier(event.getNewFovModifier() * zoomModifier);
         }

         if (isUsingZoom != wasUsingZoom) {
            player.setData(TFDataAttachments.IS_USING_GOGGLES_ZOOM_MODIFIER, isUsingZoom);
            player.playSound(isUsingZoom ? (SoundEvent)TFSounds.GOGGLES_ZOOM_IN.get() : (SoundEvent)TFSounds.GOGGLES_ZOOM_OUT.get());
            player.connection.send(new GogglesZoomPacket(isUsingZoom, player.getUUID()));
         }
      }
   }

   private void updateGradualGlideState(Pre event) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         boolean wasGraduallyGliding = (Boolean)player.getData(TFDataAttachments.IS_GRADUALLY_GLIDING);
         boolean shiftHeld = player.isShiftKeyDown();
         boolean isGraduallyGliding = TFConfig.manualTravellersWingsGradualGlideDefault == shiftHeld
            && player.getKnownMovement().y() < 0.0
            && !player.onGround();
         if (isGraduallyGliding != wasGraduallyGliding) {
            player.setData(TFDataAttachments.IS_GRADUALLY_GLIDING, isGraduallyGliding);
            player.connection.send(new GradualGlidePacket(isGraduallyGliding, player.getUUID()));
         }
      }
   }

   private void cycleItemDisplayMap(Key event) {
      if (Minecraft.getInstance().player instanceof LocalPlayer localPlayer && TFKeyBinds.ITEM_DISPLAY_MAP_CYCLE_KEY.consumeClick()) {
         localPlayer.connection.send(CycleMapSlotPacket.INSTANCE);
      }
   }

   private void swapHotbar(Key event) {
      if (TFKeyBinds.SWAP_HOTBAR_KEY.consumeClick()) {
         Player player = Minecraft.getInstance().player;
         if (player instanceof LocalPlayer localPlayer) {
            ItemStack legArmor = localPlayer.getItemBySlot(EquipmentSlot.LEGS);
            ItemContainerContents containerContents = (ItemContainerContents)legArmor.get(DataComponents.CONTAINER);
            if (TravellersArmorBeltItem.hasSwapHotbar(player, legArmor) && containerContents != null) {
               localPlayer.connection.send(SwapHotbarPacket.INSTANCE);
            }
         }
      }
   }

   private void toggleRedThreadVision(Key event) {
      this.toggleBooleanDataAttachment(
         TFKeyBinds.RED_THREAD_VISION_KEY.consumeClick(),
         TravellersModifiersManager.RED_THREAD_VISION_MODIFIER,
         TFDataAttachments.TRAVELLERS_GOGGLES_RED_THREAD_VISION
      );
   }

   private void toggleBooleanDataAttachment(
      boolean pressed, ResourceKey<TravellersModifier> modifier, DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> attachment
   ) {
      if (pressed) {
         Player player = Minecraft.getInstance().player;
         if (player != null && TravellersModifiersManager.isModifierActive(player, modifier)) {
            boolean current = (Boolean)player.getData((AttachmentType)attachment.get());
            player.setData((AttachmentType)attachment.get(), !current);
         }
      }
   }

   private void slowZoomSensitivity(CalculatePlayerTurnEvent event) {
      Player player = Minecraft.getInstance().player;
      if (!event.getCinematicCameraEnabled() && player != null) {
         ItemStack headStack = player.getItemBySlot(EquipmentSlot.HEAD);
         Float zoomModifier = (Float)headStack.get(TFDataComponents.ZOOM_ABILITY_MODIFIER);
         if (zoomModifier != null && isZoomKeyHeld(player)) {
            double mouseSensitivity = event.getMouseSensitivity();
            double mod = 0.5 - 1.0 / (6.0 * mouseSensitivity);
            double fovMod = zoomModifier + 0.05F;
            event.setMouseSensitivity(mod * mouseSensitivity / fovMod);
         }
      }
   }

   private boolean ignoreKeyEvent(Key event, KeyMapping key) {
      return !key.matches(event.getKey(), event.getScanCode()) || event.getAction() != 1 || Minecraft.getInstance().screen != null;
   }

   private void renderGlovesInFirstPerson(RenderArmEvent event) {
      if (TFConfig.firstPersonGloveOverlay) {
         AbstractClientPlayer player = event.getPlayer();
         ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
         if (chestStack.has(TFDataComponents.TRAVELLERS_HAS_GLOVES) && !chestStack.has(TFDataComponents.EMPERORS_CLOTH)) {
            PlayerRenderer renderer = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
            HumanoidModel<AbstractClientPlayer> model = IClientItemExtensions.of((Item)TFItems.TRAVELLERS_GLOVES.get())
               .getHumanoidArmorModel(player, chestStack, EquipmentSlot.CHEST, (HumanoidModel)renderer.getModel());
            ModelPart armPart = event.getArm() == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
            model.attackTime = 0.0F;
            model.crouching = false;
            model.swimAmount = 0.0F;
            model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            armPart.xRot = 0.0F;
            ResourceLocation gloveLocation = TwilightForestMod.prefix("textures/models/armor/travellers_layer_1.png");
            armPart.render(
               event.getPoseStack(),
               event.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(gloveLocation)),
               event.getPackedLight(),
               OverlayTexture.NO_OVERLAY
            );
         }
      }
   }
}
