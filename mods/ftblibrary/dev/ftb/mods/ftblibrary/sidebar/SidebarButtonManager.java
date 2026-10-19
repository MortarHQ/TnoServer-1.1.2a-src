package dev.ftb.mods.ftblibrary.sidebar;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.api.sidebar.SidebarButtonCreatedEvent;
import dev.ftb.mods.ftblibrary.config.FTBLibraryClientConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringSidebarMapValue;
import dev.ftb.mods.ftblibrary.util.MapUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class SidebarButtonManager extends SimpleJsonResourceReloadListener {
   public static final SidebarButtonManager INSTANCE = new SidebarButtonManager();
   private final Map<ResourceLocation, RegisteredSidebarButton> buttons = new HashMap<>();
   private final List<SidebarGuiButton> buttonList = new ArrayList<>();

   public SidebarButtonManager() {
      super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "sidebar_buttons");
   }

   protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      this.buttons.clear();
      this.loadResources(object, SidebarButtonData.CODEC, (id, buttonData) -> this.buttons.put(id, new RegisteredSidebarButton(id, buttonData)));
      this.buttonList.clear();
      List<RegisteredSidebarButton> sortedButtons = this.buttons
         .values()
         .stream()
         .sorted(Comparator.comparingInt(valuex -> valuex.getData().sortIndex()))
         .toList();
      int y = 0;
      int x = 0;
      Map<String, StringSidebarMapValue.SideButtonInfo> buttonConfig = FTBLibraryClientConfig.SIDEBAR_BUTTONS.get();
      Map<String, StringSidebarMapValue.SideButtonInfo> prevConfig = Map.copyOf(buttonConfig);

      for (RegisteredSidebarButton buttonEntry : sortedButtons) {
         StringSidebarMapValue.SideButtonInfo buttonSettings = buttonConfig.get(buttonEntry.getId().toString());
         if (buttonSettings == null) {
            buttonSettings = new StringSidebarMapValue.SideButtonInfo(true, x, y);
            buttonConfig.put(buttonEntry.getId().toString(), buttonSettings);
         }

         this.buttonList.add(new SidebarGuiButton(new GridLocation(buttonSettings.xPos(), buttonSettings.yPos()), buttonSettings.enabled(), buttonEntry));
         if (++x >= 4) {
            x = 0;
            y++;
         }
      }

      for (RegisteredSidebarButton value : this.buttons.values()) {
         ((Consumer)SidebarButtonCreatedEvent.EVENT.invoker()).accept(new SidebarButtonCreatedEvent(value));
      }

      if (!prevConfig.equals(buttonConfig)) {
         FTBLibraryClientConfig.save();
      }
   }

   private <T> void loadResources(Map<ResourceLocation, JsonElement> objects, Codec<T> codec, BiConsumer<ResourceLocation, T> consumer) {
      for (Entry<ResourceLocation, JsonElement> resource : objects.entrySet()) {
         codec.parse(JsonOps.INSTANCE, resource.getValue())
            .resultOrPartial(err -> FTBLibrary.LOGGER.error("Failed to parse json: {}", err))
            .ifPresent(result -> {
               ResourceLocation key = resource.getKey();
               ResourceLocation fixed = ResourceLocation.fromNamespaceAndPath(key.getNamespace(), key.getPath());
               consumer.accept(fixed, (T)result);
            });
      }
   }

   public void saveConfigFromButtonList() {
      Int2ObjectMap<List<SidebarGuiButton>> buttonMap = new Int2ObjectOpenHashMap();

      for (SidebarGuiButton button : this.getButtonList()) {
         int y = button.isEnabled() ? button.getGridLocation().y() : -1;
         ((List)buttonMap.computeIfAbsent(y, k -> new LinkedList())).add(button);
      }

      Map<String, StringSidebarMapValue.SideButtonInfo> sidebarConfig = FTBLibraryClientConfig.SIDEBAR_BUTTONS.get();
      Map<String, StringSidebarMapValue.SideButtonInfo> prevConfig = Map.copyOf(sidebarConfig);
      int y = 0;

      for (Entry<Integer, List<SidebarGuiButton>> buttonsByYpos : MapUtils.sortMapByKey(buttonMap).entrySet()) {
         if (buttonsByYpos.getKey() == -1) {
            for (SidebarGuiButton button : buttonsByYpos.getValue()) {
               button.setGridLocation(-1, -1);
               sidebarConfig.put(button.toString(), StringSidebarMapValue.SideButtonInfo.DISABLED);
            }
         }

         int x = 0;
         buttonsByYpos.getValue().sort(Comparator.comparingInt(buttonx -> buttonx.getGridLocation().x()));

         for (SidebarGuiButton sidebarButton : buttonsByYpos.getValue()) {
            if (sidebarButton.isEnabled()) {
               sidebarButton.setGridLocation(x, y);
               sidebarConfig.put(sidebarButton.toString(), StringSidebarMapValue.SideButtonInfo.at(x, y));
               x++;
            }
         }

         if (x > 0) {
            y++;
         }
      }

      for (SidebarGuiButton button : this.buttonList) {
         StringSidebarMapValue.SideButtonInfo buttonSettings = sidebarConfig.get(button.toString());
         if (buttonSettings != null) {
            sidebarConfig.put(button.toString(), StringSidebarMapValue.SideButtonInfo.ofButton(button));
         }
      }

      if (!sidebarConfig.equals(prevConfig)) {
         FTBLibraryClientConfig.save();
      }
   }

   public List<SidebarGuiButton> getButtonList() {
      return this.buttonList;
   }

   public List<SidebarGuiButton> getEnabledButtonList(boolean all) {
      return this.buttonList.stream().filter(SidebarGuiButton::isEnabled).filter(button -> all || button.getSidebarButton().canSee()).toList();
   }

   public List<SidebarGuiButton> getDisabledButtonList(boolean all) {
      return this.buttonList
         .stream()
         .filter(button -> !button.isEnabled())
         .filter(button -> all || button.getSidebarButton().canSee())
         .collect(Collectors.toList());
   }

   public Collection<RegisteredSidebarButton> getButtons() {
      return this.buttons.values();
   }

   public Optional<RegisteredSidebarButton> getButton(ResourceLocation id) {
      return Optional.ofNullable(this.buttons.get(id));
   }
}
