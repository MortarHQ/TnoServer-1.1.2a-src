package dev.ftb.mods.ftbquests.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.ftb.mods.ftblibrary.api.sidebar.ButtonOverlayRender;
import dev.ftb.mods.ftblibrary.api.sidebar.SidebarButtonCreatedEvent;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.CustomClickEvent;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenBlockEntity;
import dev.ftb.mods.ftbquests.client.neoforge.FTBQuestsClientEventHandlerImpl;
import dev.ftb.mods.ftbquests.events.ClearFileCacheEvent;
import dev.ftb.mods.ftbquests.item.LootCrateItem;
import dev.ftb.mods.ftbquests.net.RequestTranslationTableMessage;
import dev.ftb.mods.ftbquests.net.SubmitTaskMessage;
import dev.ftb.mods.ftbquests.quest.BaseQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import dev.ftb.mods.ftbquests.quest.task.ObservationTask;
import dev.ftb.mods.ftbquests.quest.task.StructureTask;
import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import dev.ftb.mods.ftbquests.registry.ModItems;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class FTBQuestsClientEventHandler {
   private static final ResourceLocation QUESTS_BUTTON = FTBQuestsAPI.rl("quests");
   static boolean creativeTabRebuildPending = false;
   private List<ObservationTask> observationTasks = null;
   private ObservationTask currentlyObserving = null;
   private long currentlyObservingTicks = 0L;
   public static TextureAtlasSprite inputOnlySprite;
   public static TextureAtlasSprite tankSprite;
   public static TextureAtlasSprite feEnergyEmptySprite;
   public static TextureAtlasSprite feEnergyFullSprite;
   public static TextureAtlasSprite trEnergyEmptySprite;
   public static TextureAtlasSprite trEnergyFullSprite;

   public void init() {
      ClientLifecycleEvent.CLIENT_SETUP.register(this::registerItemColors);
      ClientLifecycleEvent.CLIENT_SETUP.register(this::registerBERs);
      SidebarButtonCreatedEvent.EVENT.register(this::onSidebarButtonCreated);
      ClearFileCacheEvent.EVENT.register(this::onFileCacheClear);
      ClientTickEvent.CLIENT_PRE.register(this::onKeyEvent);
      CustomClickEvent.EVENT.register(this::onCustomClick);
      ClientTickEvent.CLIENT_PRE.register(this::onClientTick);
      ClientGuiEvent.RENDER_HUD.register(this::onScreenRender);
      ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(this::onPlayerLogin);
      ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(this::onPlayerLogout);
   }

   public static void onTextureStitchPost(TextureAtlas textureAtlas) {
      if (textureAtlas.location().equals(InventoryMenu.BLOCK_ATLAS)) {
         inputOnlySprite = textureAtlas.getSprite(TaskScreenRenderer.INPUT_ONLY_TEXTURE);
         tankSprite = textureAtlas.getSprite(TaskScreenRenderer.TANK_TEXTURE);
         feEnergyEmptySprite = textureAtlas.getSprite(TaskScreenRenderer.FE_ENERGY_EMPTY_TEXTURE);
         feEnergyFullSprite = textureAtlas.getSprite(TaskScreenRenderer.FE_ENERGY_FULL_TEXTURE);
         trEnergyEmptySprite = textureAtlas.getSprite(TaskScreenRenderer.TR_ENERGY_EMPTY_TEXTURE);
         trEnergyFullSprite = textureAtlas.getSprite(TaskScreenRenderer.TR_ENERGY_FULL_TEXTURE);
      }
   }

   private void registerBERs(Minecraft minecraft) {
      BlockEntityRendererRegistry.register((BlockEntityType)ModBlockEntityTypes.CORE_TASK_SCREEN.get(), taskScreenRenderer());
   }

   @ExpectPlatform
   @Transformed
   public static BlockEntityRendererProvider<TaskScreenBlockEntity> taskScreenRenderer() {
      return FTBQuestsClientEventHandlerImpl.taskScreenRenderer();
   }

   private void registerItemColors(Minecraft minecraft) {
      ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> {
         LootCrate crate = LootCrateItem.getCrate(stack, true);
         return crate == null ? -1 : 0xFF000000 | crate.getColor().rgb();
      }, new ItemLike[]{(ItemLike)ModItems.LOOTCRATE.get()});
   }

   private void onSidebarButtonCreated(SidebarButtonCreatedEvent event) {
      if (event.getButton().getId().equals(QUESTS_BUTTON)) {
         event.getButton().addOverlayRender(ButtonOverlayRender.ofSimpleString(() -> {
            if (ClientQuestFile.exists()) {
               if (ClientQuestFile.INSTANCE.isDisableGui() && !ClientQuestFile.INSTANCE.canEdit()) {
                  return "[X]";
               }

               if (ClientQuestFile.INSTANCE.selfTeamData.isLocked()) {
                  return "[X]";
               }

               if (ClientQuestFile.INSTANCE.selfTeamData.hasUnclaimedRewards(Minecraft.getInstance().player.getUUID(), ClientQuestFile.INSTANCE)) {
                  return "[!]";
               }
            }

            return "";
         }));
      }
   }

   private void onFileCacheClear(BaseQuestFile file) {
      if (!file.isServerSide()) {
         this.observationTasks = null;
      }
   }

   private void onKeyEvent(Minecraft mc) {
      if (ClientQuestFile.exists()
         && (!ClientQuestFile.INSTANCE.isDisableGui() || ClientQuestFile.INSTANCE.canEdit())
         && FTBQuestsClient.KEY_QUESTS.consumeClick()) {
         ClientQuestFile.openGui();
      }
   }

   private EventResult onCustomClick(CustomClickEvent event) {
      if (event.id().getNamespace().equals("ftbquests") && "open_gui".equals(event.id().getPath())) {
         double mx = Minecraft.getInstance().mouseHandler.xpos();
         double my = Minecraft.getInstance().mouseHandler.ypos();
         Minecraft.getInstance().setScreen(null);
         if (ClientQuestFile.openGui() != null) {
            InputConstants.grabOrReleaseMouse(Minecraft.getInstance().getWindow().getWindow(), 212993, mx, my);
         }

         return EventResult.interruptFalse();
      } else {
         return EventResult.pass();
      }
   }

   private void onClientTick(Minecraft mc) {
      if (mc.level != null && ClientQuestFile.exists() && mc.player != null) {
         PinnedQuestsTracker.INSTANCE.tick(ClientQuestFile.INSTANCE);
         if (this.observationTasks == null) {
            this.observationTasks = ClientQuestFile.INSTANCE.collect(ObservationTask.class);
         }

         if (this.observationTasks.isEmpty()) {
            return;
         }

         this.currentlyObserving = null;
         TeamData selfTeamData = ClientQuestFile.INSTANCE.selfTeamData;
         if (mc.hitResult != null && mc.hitResult.getType() != Type.MISS) {
            for (ObservationTask task : this.observationTasks) {
               if (!selfTeamData.isCompleted(task.getQuest())
                  && !selfTeamData.isCompleted(task)
                  && task.observe(mc.player, mc.hitResult)
                  && selfTeamData.canStartTasks(task.getQuest())) {
                  this.currentlyObserving = task;
                  break;
               }
            }
         }

         if (this.currentlyObserving != null) {
            if (!mc.isPaused()) {
               this.currentlyObservingTicks++;
            }

            if (this.currentlyObservingTicks >= this.currentlyObserving.getTimer()) {
               NetworkManager.sendToServer(new SubmitTaskMessage(this.currentlyObserving.id));
               selfTeamData.addProgress(this.currentlyObserving, 1L);
               this.currentlyObserving = null;
               this.currentlyObservingTicks = 0L;
            }
         } else {
            this.currentlyObservingTicks = 0L;
         }
      }
   }

   private void onPlayerLogin(LocalPlayer localPlayer) {
      if (creativeTabRebuildPending) {
         FTBQuestsClient.rebuildCreativeTabs();
         creativeTabRebuildPending = false;
      }

      String locale = (String)FTBQuestsClientConfig.EDITING_LOCALE.get();
      if (!locale.isEmpty() && !locale.equals(Minecraft.getInstance().options.languageCode)) {
         NetworkManager.sendToServer(new RequestTranslationTableMessage(locale));
      }
   }

   private void onPlayerLogout(@Nullable LocalPlayer localPlayer) {
      StructureTask.syncKnownStructureList(List.of());
   }

   private void onScreenRender(GuiGraphics graphics, DeltaTracker tickDelta) {
      if (ClientQuestFile.exists()) {
         if (this.currentlyObserving != null) {
            this.renderCurrentlyObserving(Minecraft.getInstance(), graphics, tickDelta);
         }

         PinnedQuestsTracker.INSTANCE.render(Minecraft.getInstance(), graphics);
      }
   }

   private void renderCurrentlyObserving(Minecraft mc, GuiGraphics graphics, DeltaTracker tickDelta) {
      int cx = mc.getWindow().getGuiScaledWidth() / 2;
      int cy = mc.getWindow().getGuiScaledHeight() / 2;
      MutableComponent txt = this.currentlyObserving.getMutableTitle().withStyle(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.UNDERLINE});
      int txtWidth = mc.font.width(txt);
      int boxWidth = Math.max(txtWidth, 100);
      Color4I.DARK_GRAY.withAlpha(130).draw(graphics, cx - boxWidth / 2 - 3, cy - 63, boxWidth + 6, 29);
      GuiHelper.drawHollowRect(graphics, cx - boxWidth / 2 - 3, cy - 63, boxWidth + 6, 29, Color4I.DARK_GRAY, false);
      graphics.drawString(mc.font, txt, cx - txtWidth / 2, cy - 60, 16777215);
      double completed = (double)((float)this.currentlyObservingTicks + tickDelta.getGameTimeDeltaPartialTick(false)) / this.currentlyObserving.getTimer();
      GuiHelper.drawHollowRect(graphics, cx - boxWidth / 2, cy - 49, boxWidth, 12, Color4I.DARK_GRAY, false);
      Color4I.LIGHT_BLUE.withAlpha(130).draw(graphics, cx - boxWidth / 2 + 1, cy - 48, (int)((boxWidth - 2.0) * completed), 10);
      String pctTxt = this.currentlyObservingTicks * 100L / this.currentlyObserving.getTimer() + "%";
      graphics.drawString(mc.font, pctTxt, cx - mc.font.width(pctTxt) / 2, cy - 47, 16777215);
   }
}
