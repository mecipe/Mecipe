package org.mecipe.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Slf4j
@SpringBootApplication
@MapperScan("org.mecipe.server.mapper")
public class MecipeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplication(MecipeApplication.class).run(args);
        printServerInfo(context);
    }

    public static void printServerInfo(ApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        String ip = getLocalIntranetIP();
        String port = env.getProperty("server.port", "8080");
        log.info(
                 """
                 MecipeApplication is running!
                 -----------------------------------------------------------------
                 Application API    : http://localhost:{}/api
                 Swagger 本机地址     : http://localhost:{}/api/doc.html#/home
                 Swagger 内网地址     : http://{}:{}/api/doc.html#/home
                 -----------------------------------------------------------------
                 """, port, port, ip, port);

    }

    public static String getLocalIntranetIP() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() &&
                            inetAddress instanceof java.net.Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ignored) {
        }
        return null;
    }

}
