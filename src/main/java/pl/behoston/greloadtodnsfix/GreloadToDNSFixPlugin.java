package pl.behoston.greloadtodnsfix;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreloadToDNSFixPlugin extends Plugin {
    // TODO: move to config
    private Long CHECK_TIME = 5L;
    private Level loggingLevel = Level.WARNING;

    private Logger logger;
    private ProxyServer proxy;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.setLevel(loggingLevel);
        logger.info("Loading plugin.");
        proxy = getProxy();
        scheduleCheck();
        logger.info(String.format("Plugin loaded, check scheduled each %d minutes.", CHECK_TIME));
    }

    private void scheduleCheck() {
        proxy.getScheduler().schedule(this, this::check, CHECK_TIME, CHECK_TIME, TimeUnit.MINUTES);
    }

    private void check() {
        for (Map.Entry<String, ServerInfo> serverEntry : proxy.getServers().entrySet()) {
            serverEntry.getValue().ping((result, error) -> {
                if (error != null) {
                    logger.warning(String.format("Server %s doesn't responding!", serverEntry.getKey()));
                    reloadConfig();
                }
            });
        }
    }

    private void reloadConfig() {
        logger.info("Reloading configuration.");
        proxy.getPluginManager().dispatchCommand(proxy.getConsole(), "greload");
        logger.info("Configuration reloaded.");
    }
}
