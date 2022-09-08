package ru.csu.iit.distsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

@SpringBootApplication
@EnableSwagger2
@Slf4j
public class DistsysApplication {

	public static void main(String[] args) throws UnknownHostException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.setProperty("user.timezone", "UTC");
		var context = SpringApplication.run(DistsysApplication.class, args);

		var port = context.getEnvironment().getProperty("server.port");

		InetAddress ip = InetAddress.getLocalHost();
		String hostname = ip.getHostName();

		log.info("http://" + hostname + ":" + port + "/");
	}
}
