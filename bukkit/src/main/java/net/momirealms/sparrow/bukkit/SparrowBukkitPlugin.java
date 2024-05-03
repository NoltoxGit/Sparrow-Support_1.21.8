package net.momirealms.sparrow.bukkit;

import net.momirealms.sparrow.common.command.SparrowCommandManager;
import net.momirealms.sparrow.common.dependency.Dependency;
import net.momirealms.sparrow.common.plugin.AbstractSparrowPlugin;
import net.momirealms.sparrow.common.sender.SenderFactory;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class SparrowBukkitPlugin extends AbstractSparrowPlugin {

    private static SparrowBukkitPlugin plugin;
    private final SparrowBukkitBootstrap bootstrap;
    private final SparrowBukkitBungeeManager bungeeManager;
    private SparrowBukkitSenderFactory senderFactory;
    private SparrowBukkitCommandManager commandManager;
    private SparrowNMSProxy nmsProxy;

    public SparrowBukkitPlugin(SparrowBukkitBootstrap bootstrap) {
        plugin = this;
        this.bootstrap = bootstrap;
        this.bungeeManager = new SparrowBukkitBungeeManager(this);
    }

    @Override
    protected Set<Dependency> getGlobalDependencies() {
        Set<Dependency> dependencies = super.getGlobalDependencies();
        dependencies.add(Dependency.BSTATS_BUKKIT);
        dependencies.add(Dependency.NBT_API);
        dependencies.add(Dependency.CLOUD_BUKKIT);
        dependencies.add(Dependency.CLOUD_PAPER);
        dependencies.add(Dependency.SPARROW_HEART);
        dependencies.add(Dependency.INVENTORY_ACCESS);
        dependencies.add(Dependency.INVENTORY_ACCESS_NMS.setRawArtifactID(getInventoryAccessArtifact()).setCustomArtifactID(getInventoryAccessArtifact()));
        return dependencies;
    }

    @Override
    public void reload() {
        super.reload();
    }

    @Override
    public void enable() {
        this.nmsProxy = new SparrowNMSProxy();
        super.enable();
        new Metrics(getLoader(), 21789);
    }

    @Override
    public void disable() {
        this.commandManager.unregisterCommandFeatures();
        this.bungeeManager.disable();
        super.disable();
    }

    @Override
    protected void setupSenderFactory() {
        this.senderFactory = new SparrowBukkitSenderFactory(this);
    }

    @Override
    protected void setupCommandManager() {
        this.commandManager = new SparrowBukkitCommandManager(this);
        this.commandManager.registerCommandFeatures();
    }

    public JavaPlugin getLoader() {
        return this.bootstrap.getLoader();
    }

    public static SparrowBukkitPlugin getInstance() {
        return plugin;
    }

    public SparrowNMSProxy getNMSProxy() {
        return nmsProxy;
    }

    public SparrowBukkitBungeeManager getBungeeManager() {
        return bungeeManager;
    }

    public SenderFactory<SparrowBukkitPlugin, CommandSender> getSenderFactory() {
        return senderFactory;
    }

    @Override
    public SparrowBukkitBootstrap getBootstrap() {
        return bootstrap;
    }

    public SparrowCommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }

    private String getInventoryAccessArtifact() {
        String version = bootstrap.getServerVersion();
        String artifact;
        switch (version) {
            case "1.17.1" -> artifact = "r9";
            case "1.18.1" -> artifact = "r10";
            case "1.18.2" -> artifact = "r11";
            case "1.19.1", "1.19.2" -> artifact = "r13";
            case "1.19.3" -> artifact = "r14";
            case "1.19.4" -> artifact = "r15";
            case "1.20.1" -> artifact = "r16";
            case "1.20.2" -> artifact = "r17";
            case "1.20.3", "1.20.4" -> artifact = "r18";
            case "1.20.5", "1.20.6" -> artifact = "r19";
            default -> throw new RuntimeException("Unsupported version: " + version);
        }
        return String.format("inventory-access-%s", artifact);
    }
}
