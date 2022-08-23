package com.monitor.app.util;

import dev.miku.r2dbc.mysql.MySqlConnection;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import static com.monitor.app.util.Constant.DATETIME_PATTERN;

/**
 * The type Util.
 */
public class Util {

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public static Mono<MySqlConnection> getConnection() {
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host("localhost")
                .user("root")
                .password("IT-Monitor.UC3M!")
                .database("ITMONITOR")
                .serverZoneId(ZoneId.of("Europe/Paris"))
                .build();

        return Mono.from(MySqlConnectionFactory.from(configuration).create());
    }

    /**
     * Gets datetime.
     *
     * @return the datetime
     */
    public static String getDatetime() {
        return new SimpleDateFormat(DATETIME_PATTERN).format(new Date());
    }

    /**
     * Encrypt string.
     *
     * @param data the data
     * @return the string encoded
     */
    public static String encrypt(String data) {
        return new BCryptPasswordEncoder().encode(data);
    }

    /**
     * Decrypt string.
     *
     * @param data the data
     * @param pass  the pass
     * @return the string
     */
    public static String decrypt(String data, String pass) {
        try {
            var cipher = Cipher.getInstance(Constant.CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(generateAesKey(pass), Constant.AES), new IvParameterSpec(new byte[16]));

            return new String(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new NullPointerException("ERROR - Data decryption has not been possible.");
        }
    }

    private static byte[] generateAesKey(String key){
        //YYYY-MM-DD HH:MM:SS.SSS         23 B
        var split = key.split("-");

        //DD HH:MM:SS.SSSkZ)4eSMM         24 B
        var aesKey = split[2] + "kZ)4eS!" + split[1];
        aesKey = aesKey.replace(':', '#');
        aesKey = aesKey.replace(' ', 'r');

        //DDrHH#MM#SS.SSSkZ)4eSMM         24 B
        return aesKey.getBytes(StandardCharsets.UTF_8);
    }
}
