package client.networking;

import client.game.*;
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
import math.Pair;
import math.Vec3d;
import math.Vec3i;

import java.util.Arrays;

public class PacketHandler implements ClientPacketListener {
    private final HeadlessInstance instance;

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
        int threshold = packet.getCompressionType();
        if (threshold < 0) return;
        System.out.println("Enabling compression with threshold : " + threshold);
        instance.getNetworkHandler().setCompression(threshold);
    }

    @Override
    public void onLoginSuccess(LoginSuccessS2CPacket packet) {
        instance.setUuid(packet.getUuid());
        instance.getNetworkHandler().sendPacket(new LoginAcknowledgedC2SPacket());
        instance.getNetworkHandler().sendPacket(new ClientInformationC2SPacket());

        instance.getNetworkHandler().setNetworkState(NetworkState.CONFIGURATION);
        instance.config();
    }

    @Override
    public void onFinishConfiguration(FinishConfigurationS2CPacket packet) {
        instance.initWorld();
        instance.getNetworkHandler().setNetworkState(NetworkState.PLAY);
        instance.getNetworkHandler().sendPacket(new AcknowledgedFinishedConfigurationC2SPacket());
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
    public void onLoginPlay(LoginPlayS2CPacket packet) {
        instance.initPlayer(packet.getEntityID());
    }

    @Override
    public void onSetHealth(SetHealthS2CPacket packet) {
        instance.getPlayer().setHealth(packet.getHealth());
    }

    @Override
    public void onTeleportEntityS2CPacket(TeleportEntityS2CPacket packet) {
        Entity entity = instance.getWorld().getEntityByID(packet.getEntityID());
        if (entity == null) return;
        entity.setPos(packet.getX(), packet.getY(), packet.getZ());
        entity.setYaw(packet.getYaw());
        entity.setPitch(packet.getPitch());
        entity.setOnGround(packet.isOnGround());
    }

    @Override
    public void onUpdateEntityPos(UpdateEntityPositionS2CPacket packet) {
        Entity entity = instance.getWorld().getEntityByID(packet.getEntityID());
        if (entity == null) return;
        entity.setPos(entity.getPos().add(((double) packet.getDeltaX()) / 4096d, ((double) packet.getDeltaY()) / 4096d, ((double) packet.getDeltaZ()) / 4096d));
        entity.setOnGround(packet.isOnGround());
    }

    @Override
    public void onUpdateEntityPosAndRotation(UpdateEntityPositionAndRotation packet) {
        Entity entity = instance.getWorld().getEntityByID(packet.getEntityID());
        if (entity == null) return;
        entity.setPos(entity.getPos().add(((double) packet.getDeltaX()) / 4096d, ((double) packet.getDeltaY()) / 4096d, ((double) packet.getDeltaZ()) / 4096d));
        entity.setYaw(packet.getYaw());
        entity.setPitch(packet.getPitch());
        entity.setOnGround(packet.isOnGround());
    }

    @Override
    public void onUpdateEntityRotation(UpdateEntityRotationS2CPacket packet) {
        Entity entity = instance.getWorld().getEntityByID(packet.getEntityID());
        if (entity == null) return;
        entity.setYaw(packet.getYaw());
        entity.setPitch(packet.getPitch());
        entity.setOnGround(packet.isOnGround());
    }

    @Override
    public<T extends Entity> void onSpawnEntity(SpawnEntityS2CPacket packet) {
        EntityType<T> type = EntityTypes.getTypeByID(packet.getEntityType());
        if (type == null) return;
        Entity entity = null;
        if (type.getEntityClass() == LivingEntity.class) {
            entity = new LivingEntity(packet.getEntityID(), 20, 20, new Vec3d(packet.getX(), packet.getY(), packet.getZ()), new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()), packet.getYaw(), packet.getPitch(), true, (EntityType<? extends LivingEntity>) type, instance);
        } else if (type.getEntityClass() == Entity.class) {
            entity = new Entity(packet.getEntityID(), new Vec3d(packet.getX(), packet.getY(), packet.getZ()), new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()), packet.getYaw(), packet.getPitch(), true, (EntityType<? extends Entity>) type, instance);

        }
        if (entity == null) return;
        //System.out.println("ADDING ENTITY " + type.getId());
        instance.getWorld().addEntity(entity);
    }

    @Override
    public void onRemoveEntities(RemoveEntitiesS2CPacket packet) {
        for (int i = 0; i < packet.getEntityIDs().length; i++) {
            Entity ent = instance.getWorld().getEntityByID(packet.getEntityIDs()[i]);
            instance.getWorld().removeEntity(packet.getEntityIDs()[i]);
        }
    }

    @Override
    public void onSetHeldItem(SetHeldItemS2CPacket packet) {
        instance.getPlayer().setSelectedSlot(packet.getSlot());
    }

    @Override
    public void onSynchronizePlayerPosition(SynchronizePlayerPositionS2CPacket packet) {
        Flag flags = packet.getFlags();
        ClientPlayerEntity player = instance.getPlayer();
        System.out.println("Server resetting client position!");

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
        instance.getWorld().setBlock(packet.getPos(), Blocks.getBlockByID(packet.getBlockID()));
    }

    @Override
    public void onBlockSectionUpdate(UpdateBlockSectionS2CPacket packet) {
        Pair<Block, Vec3i>[] blocks = packet.getBlocks();
        Vec3i chunkPos = packet.getChunkPos();
        instance.getWorld().setBlocks(chunkPos, blocks);
    }

    @Override
    public void onSetEntityVelocity(SetEntityVelocityS2CPacket packet) {
        if (packet.getEntityID() != instance.getPlayer().getEntityID()) return;
        instance.getPlayer().setVelocity(packet.getX(), packet.getY(), packet.getZ());
    }
}
