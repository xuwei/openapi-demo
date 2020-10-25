package co.zip.candidate.userapi;

import java.util.UUID;

public class MockAuthentication {
    static public UUID currentUserId() { return UUID.fromString("12345"); }
}
