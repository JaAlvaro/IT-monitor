package com.monitor.app.util;

import dev.miku.r2dbc.mysql.MySqlConnection;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
     * @param key  the key
     * @return the string
     */
    public static String encrypt(String data, String key) {
        try {
            var cipher = Cipher.getInstance(Constant.CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), Constant.AES));

            return new String(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new NullPointerException("ERROR - Data encryption has not been possible.");
        }
    }

    /**
     * Decrypt string.
     *
     * @param data the data
     * @param key  the key
     * @return the string
     */
    public static String decrypt(String data, String key) {
        try {
            var cipher = Cipher.getInstance(Constant.CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), Constant.AES));

            return new String(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new NullPointerException("ERROR - Data decryption has not been possible.");
        }
    }
}
