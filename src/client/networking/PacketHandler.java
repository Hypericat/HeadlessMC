package client.networking;

import auth.AuthUtil;
import auth.EncryptionHandler;
import client.game.*;
import client.networking.packets.C2S.configuration.*;
import client.HeadlessInstance;
import client.networking.packets.C2S.play.KeepAliveC2SPacket;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.*;
import math.Pair;
import math.Vec3d;
import math.Vec3i;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Arrays;

public class PacketHandler implements ClientPacketListener {
    private final HeadlessInstance instance;

    public PacketHandler(HeadlessInstance instance) {
        this.instance = instance;
    }

    @Override
    public void onStatus(StatusResponseS2CPacket packet) {
        String response = packet.getString();
        instance.getLogger().debug("Received status response from server");
        instance.getLogger().debug(response);
    }

    @Override
    public void onCookieRequest(CookieRequestS2CPacket packet) {

    }

    @Override
    public void onEncryption(EncryptionRequestS2CPacket packet) {
        instance.getLogger().debug("Received encryption packet from server");
        Cipher cipher;
        Cipher cipher2;
        String hash;

        EncryptionResponseC2SPacket encryptionResponseC2SPacket;
        try {
            SecretKey secretKey = AuthUtil.generateSecretKey();

            PublicKey publicKey = AuthUtil.decodeEncodedRsaPublicKey(packet.getPublicKey());
            hash = AuthUtil.getStringFromHash(AuthUtil.computeServerId(packet.getServerID(), publicKey, secretKey));
            cipher = AuthUtil.cipherFromKey(2, secretKey);
            cipher2 = AuthUtil.cipherFromKey(1, secretKey);
            byte[] bs = packet.getVerifyToken();
            encryptionResponseC2SPacket = new EncryptionResponseC2SPacket(secretKey, publicKey, bs);
        } catch (Exception ex) {
            throw new IllegalStateException("Protocol error", ex);
        }

        if (!instance.getAccount().isPremium()) throw new IllegalStateException("Server requested authentication on non-premium account!");
        AuthUtil.authenticateSession(instance.getAccount(), hash);

        instance.getNetworkHandler().sendPacket(encryptionResponseC2SPacket);
        instance.getNetworkHandler().enableEncryption(cipher, cipher2);
    }

    @Override
    public void onCompression(CompressionRequestS2CPacket packet) {
        int threshold = packet.getCompressionType();
        if (threshold < 0) return;
        instance.getLogger().debug("Enabling compression with threshold : " + threshold);
        instance.getNetworkHandler().setCompression(threshold);
    }

    @Override
    public void onLoginSuccess(LoginSuccessS2CPacket packet) {
        instance.setUuid(packet.getUuid());
        instance.getNetworkHandler().sendPacket(new LoginAcknowledgedC2SPacket());
        instance.getNetworkHandler().sendPacket(new ClientInformationC2SPacket(instance.getViewDistance()));

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
        instance.getLogger().debug("Received server brand : " + packet.getText());
    }

    @Override
    public void onKnowPacks(ClientBoundKnownPacksS2CPacket packet) {
        instance.getNetworkHandler().sendPacket(new ServerBoundKnownPacksC2SPacket(packet));
    }


    @Override
    public void onKeepAlive(KeepAliveS2CPacket packet) {
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
            entity = new LivingEntity(packet.getEntityID(), 20, 20, new Vec3d(packet.getX(), packet.getY(), packet.getZ()), new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()), packet.getYaw(), packet.getPitch(), true, (EntityType<? extends LivingEntity>) type, packet.getEntityUuid(), instance);
        } else if (type.getEntityClass() == Entity.class) {
            entity = new Entity(packet.getEntityID(), new Vec3d(packet.getX(), packet.getY(), packet.getZ()), new Vec3d(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()), packet.getYaw(), packet.getPitch(), true, (EntityType<? extends Entity>) type, packet.getEntityUuid(), instance);

        }
        if (entity == null) return;
        //instance.getLogger().debug("ADDING ENTITY " + type.getId());
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
        instance.getInteractionManager().setSelectedSlot(packet.getSlot());
    }

    @Override
    public void onSynchronizePlayerPosition(SynchronizePlayerPositionS2CPacket packet) {
        instance.getInteractionManager().onSynchronizePlayerPositions(packet);
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
        instance.getLogger().logToFile("Finished Loading Chunk at X : " + packet.getChunk().getChunkX() * 16 + " Z : " + packet.getChunk().getChunkZ() * 16);
    }

    @Override
    public void onBlockUpdate(BlockUpdateS2CPacket packet) {
        instance.getWorld().setBlock(packet.getPos(), Blocks.getBlockByID(packet.getBlockID()));
    }

    @Override
    public void onChatMessageS2C(PlayerChatMessageS2C packet) {
        instance.getInteractionManager().onReceiveChatMessage(packet);
    }

    @Override
    public void onSetContainerContent(SetContainerContentS2CPacket packet) {
        if (!packet.isClientInventory()) return;
        instance.getPlayer().getInventory().setSlots(0, packet.getItems());
    }

    @Override
    public void onSetContainerSlot(SetContainerSlotS2CPacket packet) {
        if (!packet.isClientInventory()) return;
        instance.getPlayer().getInventory().setSlot(packet.getSlot(), packet.getItemStack());
    }

    @Override
    public void onBlockSectionUpdate(UpdateBlockSectionS2CPacket packet) {
        Pair<Block, Vec3i>[] blocks = packet.getBlocks();
        Vec3i chunkPos = packet.getChunkPos();
        instance.getWorld().setBlocks(chunkPos, blocks);
    }

    @Override
    public void onSetEntityVelocity(SetEntityVelocityS2CPacket packet) {
        if (instance.getPlayer().isPathfinding()) return;
        if (packet.getEntityID() != instance.getPlayer().getEntityID()) return;
        instance.getPlayer().setVelocity(packet.getX(), packet.getY(), packet.getZ());
    }

    @Override
    public void onUnloadChunk(UnloadChunkS2CPacket packet) {
        instance.getWorld().unloadChunkAt(packet.getChunkX(), packet.getChunkZ());
    }
}
