package plus.dragons.createenchantmentindustry.content.contraptions.fluids.ink;

import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;

public interface InkRenderingCamera {

    boolean isInInk();

    static void handleInkFogColor(FogEvents.ColorData event, float partialTicks) {
        if (((InkRenderingCamera) event.getCamera()).isInInk()) {
            event.setRed(0);
            event.setGreen(0);
            event.setBlue(0);
        }
    }

}
