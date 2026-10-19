package twilightforest.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.vehicle.Boat.Type;

public class TextureGeneratorReloadListener implements ResourceManagerReloadListener {
   public static final TextureGeneratorReloadListener INSTANCE = new TextureGeneratorReloadListener();
   private static final EnumMap<Type, AbstractTexture> BOAT_CACHE = new EnumMap<>(Type.class);
   private static final AtomicReference<NativeImage> ref = new AtomicReference<>();

   public void onResourceManagerReload(ResourceManager manager) {
      ResourceLocation oak = getTextureLocation(Type.OAK);
      manager.getResource(oak)
         .ifPresent(
            vanillaResource -> {
               try (InputStream vanillaStream = vanillaResource.open()) {
                  NativeImage vanillaImage = NativeImage.read(vanillaStream);

                  try {
                     int defaultScale = 128;
                     int vanillaScale = vanillaImage.getWidth() / defaultScale;

                     for (Type type : Type.values()) {
                        ResourceLocation location = getTextureLocation(type);
                        if (location.getNamespace().equals("twilightforest")) {
                           manager.getResource(location)
                              .ifPresent(
                                 tfResource -> {
                                    try (InputStream tfStream = tfResource.open()) {
                                       NativeImage tfImage = NativeImage.read(tfStream);

                                       label176: {
                                          try {
                                             int tfScale = tfImage.getWidth() / defaultScale;
                                             int x = 0;

                                             label133:
                                             while (true) {
                                                if (x >= 48 * tfScale) {
                                                   if (vanillaScale > tfScale) {
                                                      NativeImage newImage = new NativeImage(defaultScale * vanillaScale, defaultScale * vanillaScale, false);

                                                      try {
                                                         newImage.copyFrom(vanillaImage);

                                                         for (int xx = 0; xx < 102 * vanillaScale; xx++) {
                                                            for (int y = 0; y < 52 * vanillaScale; y++) {
                                                               newImage.setPixelRGBA(
                                                                  xx, y, tfImage.getPixelRGBA(xx / (vanillaScale / tfScale), y / (vanillaScale / tfScale))
                                                               );
                                                            }
                                                         }

                                                         ref.set(newImage);
                                                         if (BOAT_CACHE.containsKey(type)) {
                                                            BOAT_CACHE.get(type).load(manager);
                                                         } else {
                                                            AbstractTexture texture = new AbstractTexture() {
                                                               public void load(ResourceManager resourceManager) {
                                                                  if (TextureGeneratorReloadListener.ref.get() != null) {
                                                                     TextureUtil.prepareImage(
                                                                        this.getId(),
                                                                        0,
                                                                        TextureGeneratorReloadListener.ref.get().getWidth(),
                                                                        TextureGeneratorReloadListener.ref.get().getHeight()
                                                                     );
                                                                     TextureGeneratorReloadListener.ref
                                                                        .get()
                                                                        .upload(
                                                                           0,
                                                                           0,
                                                                           0,
                                                                           0,
                                                                           0,
                                                                           TextureGeneratorReloadListener.ref.get().getWidth(),
                                                                           TextureGeneratorReloadListener.ref.get().getHeight(),
                                                                           false,
                                                                           false,
                                                                           false,
                                                                           true
                                                                        );
                                                                  }
                                                               }
                                                            };
                                                            Minecraft.getInstance().getTextureManager().register(location, texture);
                                                            BOAT_CACHE.put(type, texture);
                                                         }
                                                      } catch (Throwable var17) {
                                                         try {
                                                            newImage.close();
                                                         } catch (Throwable var16x) {
                                                            var17.addSuppressed(var16x);
                                                         }

                                                         throw var17;
                                                      }

                                                      newImage.close();
                                                      break label176;
                                                   }

                                                   for (int xx = 0; xx < 48 * tfScale; xx++) {
                                                      for (int y = 58 * tfScale; y < 96 * tfScale; y++) {
                                                         tfImage.setPixelRGBA(
                                                            xx, y, vanillaImage.getPixelRGBA(xx / (tfScale / vanillaScale), y / (tfScale / vanillaScale))
                                                         );
                                                      }
                                                   }

                                                   ref.set(tfImage);
                                                   if (BOAT_CACHE.containsKey(type)) {
                                                      BOAT_CACHE.get(type).load(manager);
                                                   } else {
                                                      AbstractTexture texture = new AbstractTexture() {
                                                         public void load(ResourceManager resourceManager) {
                                                            if (TextureGeneratorReloadListener.ref.get() != null) {
                                                               TextureUtil.prepareImage(
                                                                  this.getId(),
                                                                  0,
                                                                  TextureGeneratorReloadListener.ref.get().getWidth(),
                                                                  TextureGeneratorReloadListener.ref.get().getHeight()
                                                               );
                                                               TextureGeneratorReloadListener.ref
                                                                  .get()
                                                                  .upload(
                                                                     0,
                                                                     0,
                                                                     0,
                                                                     0,
                                                                     0,
                                                                     TextureGeneratorReloadListener.ref.get().getWidth(),
                                                                     TextureGeneratorReloadListener.ref.get().getHeight(),
                                                                     false,
                                                                     false,
                                                                     false,
                                                                     true
                                                                  );
                                                            }
                                                         }
                                                      };
                                                      Minecraft.getInstance().getTextureManager().register(location, texture);
                                                      BOAT_CACHE.put(type, texture);
                                                   }
                                                   break label176;
                                                }

                                                for (int y = 58 * tfScale; y < 96 * tfScale; y++) {
                                                   if (tfImage.getPixelRGBA(x, y) != 0) {
                                                      break label133;
                                                   }
                                                }

                                                x++;
                                             }
                                          } catch (Throwable var18) {
                                             if (tfImage != null) {
                                                try {
                                                   tfImage.close();
                                                } catch (Throwable var15) {
                                                   var18.addSuppressed(var15);
                                                }
                                             }

                                             throw var18;
                                          }

                                          if (tfImage != null) {
                                             tfImage.close();
                                          }

                                          return;
                                       }

                                       if (tfImage != null) {
                                          tfImage.close();
                                       }
                                    } catch (IOException var20) {
                                    }
                                 }
                              );
                        }
                     }
                  } catch (Throwable var14) {
                     if (vanillaImage != null) {
                        try {
                           vanillaImage.close();
                        } catch (Throwable var13) {
                           var14.addSuppressed(var13);
                        }
                     }

                     throw var14;
                  }

                  if (vanillaImage != null) {
                     vanillaImage.close();
                  }
               } catch (IOException var16) {
               }
            }
         );
      ref.set(null);
   }

   private static ResourceLocation getTextureLocation(Type type) {
      return ResourceLocation.parse(type.getName()).withPrefix("textures/entity/chest_boat/").withSuffix(".png");
   }
}
