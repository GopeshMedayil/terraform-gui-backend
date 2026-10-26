package com.terraformgui.backend;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;

import java.net.URI;

class FloCiIamTest {

    @Test
    void assumeRoleAndGetIdentity() {

        try (StsClient sts = StsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("test", "test")
                        )
                )
                .build()) {

            AssumeRoleResponse assumeRoleResponse = sts.assumeRole(
                    AssumeRoleRequest.builder()
                            .roleArn("arn:aws:iam::000000000000:role/TerraformExecutionRole")
                            .roleSessionName("terraform-gui-test")
                            .build()
            );

            var credentials = assumeRoleResponse.credentials();

            try (StsClient assumedRoleSts = StsClient.builder()
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .region(Region.US_EAST_1)
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(
                                            credentials.accessKeyId(),
                                            credentials.secretAccessKey()
                                    )
                            )
                    )
                    .build()) {

                var identity = assumedRoleSts.getCallerIdentity();

                System.out.println("Account : " + identity.account());
                System.out.println("User ID : " + identity.userId());
                System.out.println("ARN     : " + identity.arn());
            }
        }
    }
}