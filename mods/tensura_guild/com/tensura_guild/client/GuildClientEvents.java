package com.tensura_guild.client;

import com.tensura_guild.GuildPlayerEvents;
import com.tensura_guild.client.screen.AdventurerGuildScreen;
import com.tensura_guild.client.screen.YuukiDialogueScreen;
import com.tensura_guild.data.DwarfTaskService;
import com.tensura_guild.data.GuildMemberPublicData;
import com.tensura_guild.data.GuildSignatureData;
import com.tensura_guild.registry.GuildAttachments;
import com.tensura_guild.registry.GuildBlocks;
import com.tensura_guild.registry.GuildEntityTypes;
import io.github.manasmods.tensura.client.entity.human.OtherworlderRenderer;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre;
import net.neoforged.neoforge.client.event.ScreenEvent.Render.Post;

public class GuildClientEvents {
   private static final ResourceLocation BOOKMARK_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "textures/gui/guild_bookmark.png");
   private static final int MAIN_GUI_WIDTH = 256;
   private static final int MAIN_GUI_HEIGHT = 163;
   private static final int BOOKMARK_X = -18;
   private static final int BOOKMARK_Y = 41;
   private static final int BOOKMARK_WIDTH = 18;
   private static final int BOOKMARK_HEIGHT = 12;
   private static boolean dwarfTaskMarkerRegistered = false;

   public static void onRegisterRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(GuildEntityTypes.YUUKI_KAGURAZAKA.get(), context -> new OtherworlderRenderer(context, true));
      event.registerEntityRenderer(GuildEntityTypes.LAPLACE.get(), context -> new OtherworlderRenderer(context, true));
      ItemBlockRenderTypes.setRenderLayer(GuildBlocks.YUUKI_KAGURAZAKA_SPAWNER.get(), RenderType.cutout());
      registerDwarfTaskMarkerPredicate();
   }

   public static void onScreenRenderPost(Post event) {
      if (event.getScreen() instanceof MainScreen && hasGuildSignature()) {
         GuiGraphics graphics = event.getGuiGraphics();
         int left = (event.getScreen().width - 256) / 2;
         int top = (event.getScreen().height - 163) / 2;
         graphics.blit(BOOKMARK_TEXTURE, left + -18, top + 41, 0.0F, 0.0F, 18, 12, 18, 12);
      }
   }

   public static void onMouseButtonPressedPre(Pre event) {
      if (event.getScreen() instanceof MainScreen && event.getButton() == 0 && hasGuildSignature()) {
         int left = (event.getScreen().width - 256) / 2;
         int top = (event.getScreen().height - 163) / 2;
         double mouseX = event.getMouseX();
         double mouseY = event.getMouseY();
         if (mouseX >= left + -18 && mouseX < left + -18 + 18 && mouseY >= top + 41 && mouseY < top + 41 + 12) {
            Minecraft.getInstance().setScreen(new AdventurerGuildScreen());
            ScreenHelper.clicked();
            event.setCanceled(true);
         }
      }
   }

   public static void onRenderGuiPre(net.neoforged.neoforge.client.event.RenderGuiEvent.Pre event) {
      if (Minecraft.getInstance().screen instanceof YuukiDialogueScreen) {
         event.setCanceled(true);
      }
   }

   public static void onRenderNameTag(RenderNameTagEvent event) {
      registerDwarfTaskMarkerPredicate();
      DwarfTaskMarkers.render(event);
      if (event.getEntity() instanceof Player player) {
         GuildMemberPublicData memberData = (GuildMemberPublicData)player.getData(GuildAttachments.GUILD_MEMBER_PUBLIC);
         if (GuildPlayerEvents.hasNameDecoration(memberData) && GuildPlayerEvents.hasTensuraDisplayName(player)) {
            Component formatted = GuildPlayerEvents.buildDisplayName(player, memberData, event.getContent());
            event.setContent(formatted.copy().withStyle(event.getContent().getStyle()));
         }
      }
   }

   private static boolean hasGuildSignature() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player == null) {
         return false;
      }

      GuildSignatureData signature = (GuildSignatureData)minecraft.player.getData(GuildAttachments.GUILD_SIGNATURE);
      return signature.hasSignature();
   }

   private static void registerDwarfTaskMarkerPredicate() {
      if (!dwarfTaskMarkerRegistered) {
         DwarfTaskMarkers.registerMarkerPredicate(dwarf -> {
            Minecraft minecraft = Minecraft.getInstance();
            return minecraft.player != null && DwarfTaskService.shouldShowMarker(minecraft.player, dwarf);
         });
         dwarfTaskMarkerRegistered = true;
      }
   }
}
