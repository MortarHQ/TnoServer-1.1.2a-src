package io.github.Memoires.mysticism.storage.element;

import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageEvents;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.manascore.storage.api.StorageEvents.RegisterStorage;
import io.github.manasmods.tensura.ability.magic.Element;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElementStorage extends Storage implements IElement {
   private static final Logger log = LogManager.getLogger(ElementStorage.class);
   private static StorageKey<ElementStorage> key = null;
   private Element element = null;

   protected ElementStorage(LivingEntity holder) {
      super(holder);
   }

   public static void init() {
      StorageEvents.REGISTER_ENTITY_STORAGE.register((RegisterStorage)registry -> {
         ResourceLocation var10001 = ResourceLocation.fromNamespaceAndPath("mysticism", "element_storage");
         key = registry.register(var10001, ElementStorage.class, LivingEntity.class::isInstance, target -> new ElementStorage((LivingEntity)target));
      });
   }

   public static StorageKey<ElementStorage> getKey() {
      return key;
   }

   @Override
   public Element getElement() {
      return this.element;
   }

   @Override
   public void setElement(Element element) {
      this.element = element;
   }

   public void save(CompoundTag data) {
      if (this.element != null) {
         data.putInt("element", this.element.getId());
      } else {
         data.putInt("element", -1);
      }
   }

   public void load(CompoundTag data) {
      if (data.contains("element")) {
         int id = data.getInt("element");
         if (id >= 0 && id <= 8) {
            try {
               this.element = Element.byId(id);
            } catch (Exception e) {
               log.warn("Invalid element id {} in NBT, clearing element.", id, e);
            }
         }
      }
   }
}
