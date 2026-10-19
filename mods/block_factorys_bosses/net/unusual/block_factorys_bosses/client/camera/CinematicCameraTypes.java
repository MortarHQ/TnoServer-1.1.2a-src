package net.unusual.block_factorys_bosses.client.camera;

public enum CinematicCameraTypes {
   FOV,
   SHAKE,
   BARS,
   BLACK,
   CAMERA,
   HIDE_GUI,
   BLUR;

   final String id = this.name().toLowerCase();
}
