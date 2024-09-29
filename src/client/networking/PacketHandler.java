package client.networking;

import client.game.ClientPlayerEntity;
import client.game.World;
import client.networking.packets.C2S.configuration.AcknowledgedFinishedConfigurationC2SPacket;
import client.networking.packets.C2S.configuration.ClientInformationC2SPacket;
import client.networking.packets.C2S.configuration.LoginAcknowledgedC2SPacket;
import client.networking.packets.C2S.configuration.ServerBoundKnownPacksC2SPacket;
import client.HeadlessInstance;
import client.networking.packets.C2S.play.ConfirmTeleportationC2SPacket;
import client.networking.packets.C2S.play.KeepAliveC2SPacket;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.*;
import client.utils.Flag;
import client.utils.Pair;
import client.utils.Vec3i;

import java.util.Arrays;

public class PacketHandler implements ClientPacketListener {
    private HeadlessInstance instance;

    public PacketHandler(HeadlessInstance instance) {
        this.instance = instance;
    }

    @Override
    public void onStatus(StatusResponseS2CPacket packet) {
        String response = packet.getString();
        System.out.println("Received status response from server");
        System.out.println(response);
    }

    @Override
    public void onCookieRequest(CookieRequestS2CPacket packet) {

    }

    @Override
    public void onEncryption(EncryptionRequestS2CPacket packet) {
        System.out.println("Received encryption packet from server");
        System.out.println("Server id " + packet.getServerID());
        System.out.println("Public Key Length " + packet.getPublicKeyLength());
        System.out.println("Public Key " + Arrays.toString(packet.getPublicKey()));
        System.out.println("Token Length " + packet.getVerifyTokenLength());
        System.out.println("Token " + Arrays.toString(packet.getVerifyToken()));
        System.out.println("Should Authenticate " + packet.isShouldAuthenticate());
    }

    @Override
    public void onCompression(CompressionRequestS2CPacket packet) {
        int compressionType = packet.getCompressionType();
        System.out.println("Received compression request from server");
        System.out.println(compressionType);
    }

    @Override
    public void onLoginSuccess(LoginSuccessfulS2CPacket packet) {
        instance.getNetworkHandler().setCompressionEnabled(false);
        instance.getNetworkHandler().sendPacket(new LoginAcknowledgedC2SPacket());
        instance.getNetworkHandler().sendPacket(new ClientInformationC2SPacket());

        instance.getNetworkHandler().setNetworkState(NetworkState.CONFIGURATION);
        instance.config();
    }

    @Override
    public void onFinishConfiguration(FinishConfigurationS2CPacket packet) {
        instance.getNetworkHandler().setNetworkState(NetworkState.PLAY);
        instance.getNetworkHandler().sendPacket(new AcknowledgedFinishedConfigurationC2SPacket());
        instance.initPlayer();
    }

    @Override
    public void onClientPluginMessage(ClientBoundPluginMessageS2CPacket packet) {
        System.out.println("Received server brand : " + packet.getText());
    }

    @Override
    public void onKnowPacks(ClientBoundKnownPacksS2CPacket packet) {
        instance.getNetworkHandler().sendPacket(new ServerBoundKnownPacksC2SPacket(packet));
    }


    @Override
    public void onKeepAlive(KeepAliveS2CPacket packet) {
        System.out.println("Received Keep Alive Packet, ID : " + packet.getId());
        instance.getNetworkHandler().sendPacket(new KeepAliveC2SPacket(packet.getId()));
    }

    @Override
    public void onSetHealth(SetHealthS2CPacket packet) {
        instance.getPlayer().setHealth(packet.getHealth());
    }

    @Override
    public void onSetHeldItem(SetHeldItemS2CPacket packet) {
        instance.getPlayer().setSelectedSlot(packet.getSlot());
    }

    @Override
    public void onSynchronizePlayerPosition(SynchronizePlayerPositionS2CPacket packet) {
        Flag flags = packet.getFlags();
        ClientPlayerEntity player = instance.getPlayer();
        System.out.println("Setting Pos");

        player.setX(flags.contains(0x01) ? player.getX() + packet.getX() : packet.getX());
        player.setY(flags.contains(0x02) ? player.getY() + packet.getY() : packet.getY());
        player.setZ(flags.contains(0x04) ? player.getZ() + packet.getZ() : packet.getZ());
        player.setYaw(flags.contains(0x08) ? player.getYaw() + packet.getYaw() : packet.getYaw());
        player.setPitch(flags.contains(0x10) ? player.getPitch() + packet.getPitch() : packet.getPitch());

        instance.getNetworkHandler().sendPacket(new ConfirmTeleportationC2SPacket(packet.getTeleportID()));
    }

    @Override
    public void onSetCenterChunk(SetCenterChunkS2CPacket packet) {
        instance.getPlayer().setChunkX(packet.getChunkX());
        instance.getPlayer().setChunkZ(packet.getChunkZ());
    }

    @Override
    public void onChunkData(ChunkDataS2CPacket packet) {
        World world = instance.getWorld();
        world.addChunk(packet.getChunk());
        System.out.println("Finished Loading Chunk at X : " + packet.getChunk().getChunkX() * 16 + " Z : " + packet.getChunk().getChunkZ() * 16);
    }

    @Override
    public void onBlockUpdate(BlockUpdateS2CPacket packet) {
        instance.getWorld().setBlock(packet.getPos(), packet.getBlockID());
    }

    @Override
    public void onBlockSectionUpdate(UpdateBlockSectionS2CPacket packet) {
        Pair<Integer, Vec3i>[] blocks = packet.getBlocks();
        Vec3i chunkPos = packet.getChunkPos();
        instance.getWorld().setBlocks(chunkPos, blocks);
    }
}
