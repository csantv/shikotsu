package pe.tcloud.shikotsu.document.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.tcloud.shikotsu.config.AwsConfiguration;
import pe.tcloud.shikotsu.config.S3Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class S3Service {
    private final S3Client s3Client;
    private final S3Configuration s3Configuration;

    public S3Service(AwsConfiguration awsConfiguration) throws URISyntaxException {
        this.s3Configuration = awsConfiguration.s3();
        var credentials = AwsBasicCredentials.create(s3Configuration.accessKey(), s3Configuration.secretKey());
        var builder = S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .forcePathStyle(true)
                .endpointOverride(new URI(s3Configuration.endpointUrl()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials));

        this.s3Client = builder.build();
    }

    public byte[] getObject(String prefix, String filename) throws IOException {
        var request = GetObjectRequest.builder()
                .bucket(s3Configuration.bucketName())
                .key(String.format("%s/%s", prefix, filename))
                .build();
        return s3Client.getObject(request).readAllBytes();
    }

    public void putObject(byte[] fileData, String prefix, String filename) {
        log.info("Inserting file {}/{} to S3", prefix, filename);
        var request = PutObjectRequest.builder()
                .bucket(s3Configuration.bucketName())
                .key(String.format("%s/%s", prefix, filename))
                .build();
        s3Client.putObject(request, RequestBody.fromBytes(fileData));
    }
}
