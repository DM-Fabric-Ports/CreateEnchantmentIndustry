package plus.dragons.createenchantmentindustry.entry;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import io.github.fabricators_of_create.porting_lib.util.NetworkDirection;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.EnchantingGuideEditPacket;

public enum CeiPackets {

    // Client to Server
    CONFIGURE_ENCHANTING_GUIDE_FOR_BLAZE(EnchantingGuideEditPacket.class, EnchantingGuideEditPacket::new,
            NetworkDirection.PLAY_TO_SERVER);

    public static final ResourceLocation CHANNEL_NAME = EnchantmentIndustry.genRL("main");
    public static final int NETWORK_VERSION = 1;
    public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
    public static SimpleChannel channel;

    private CeiPackets.LoadedPacket<?> packet;

    <T extends SimplePacketBase> CeiPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
            NetworkDirection direction) {
        packet = new CeiPackets.LoadedPacket<>(type, factory, direction);
    }

    public static void registerPackets() {
        channel = new SimpleChannel(CHANNEL_NAME);
        int i = 0;
        for (CeiPackets packet : values()) {
            boolean r = false;
            if (packet.packet.direction == NetworkDirection.PLAY_TO_SERVER) {
                channel.registerC2SPacket(packet.packet.type, i++);
                r = true;
            }
            if (packet.packet.direction == NetworkDirection.PLAY_TO_CLIENT) {
                channel.registerS2CPacket(packet.packet.type, i++);
                r = true;
            }
            if (!r)
                EnchantmentIndustry.LOGGER.error("Could not register packet with type " + packet.packet.type);
        }
    }

    public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
        channel.sendToClientsAround((S2CPacket) message, (ServerLevel) world, pos, range);
    }

    @SuppressWarnings("unused")
    private static class LoadedPacket<T extends SimplePacketBase> {
        private static int index = 0;

        private BiConsumer<T, FriendlyByteBuf> encoder;
        private Function<FriendlyByteBuf, T> decoder;
        private BiConsumer<T, Supplier<SimplePacketBase.Context>> handler;
        private Class<T> type;
        private NetworkDirection direction;

        private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
            encoder = T::write;
            decoder = factory;
            handler = T::handle;
            this.type = type;
            this.direction = direction;
        }
    }
}
