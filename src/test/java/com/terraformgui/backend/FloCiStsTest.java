package com.terraformgui.backend;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FloCiStsTest {

    @Test
    void shouldConnectToFloCi() {

        try (StsClient stsClient = StsClient.builder()
                .endpointOverride(java.net.URI.create("http://localhost:4566"))
                .region(software.amazon.awssdk.regions.Region.US_EAST_1)
                .credentialsProvider(
                        software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(
                                software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(
                                        "test",
                                        "test"
                                )
                        )
                )
                .build()) {

            GetCallerIdentityResponse response =
                    stsClient.getCallerIdentity();

            System.out.println("Account : " + response.account());
            System.out.println("User ID : " + response.userId());
            System.out.println("ARN     : " + response.arn());

            assertNotNull(response.account());
        }
    }
}