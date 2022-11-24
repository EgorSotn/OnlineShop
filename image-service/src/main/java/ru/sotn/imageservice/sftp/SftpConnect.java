package ru.sotn.imageservice.sftp;

import lombok.RequiredArgsConstructor;
import net.schmizz.sshj.SSHClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

public class SftpConnect {
    private SSHClient sshClient;
    private final String username;
    private final String password;
    private final String host;
    private final String port;

    public SftpConnect(@Value("${sftp.username}") String username, @Value("${sftp.password}") String password,
                       @Value("${sftp.host}") String host, @Value("${sftp.port}") String port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public SSHClient connect() throws IOException {
        sshClient = new SSHClient();
        sshClient.loadKnownHosts();
        sshClient.connect(host,Integer.parseInt(port));
        sshClient.authPassword(username, password);

        return sshClient;
    }
}
