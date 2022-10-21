package Utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Encrypted PW is: " + encoder.encode("TYPE_PASSWORD_HERE"));
    }
}
