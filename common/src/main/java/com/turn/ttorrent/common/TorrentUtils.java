package com.turn.ttorrent.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public final class TorrentUtils {

  private final static char[] HEX_SYMBOLS = "0123456789ABCDEF".toCharArray();
  private static final Map<Integer, byte[]> COMMON_ZERO_HASHES = new HashMap<Integer, byte[]>();

  static {
//    COMMON_ZERO_HASHES.put(256 * 1024, calculateSha1Hash(new byte[256 * 1024]));
//    COMMON_ZERO_HASHES.put(512 * 1024, calculateSha1Hash(new byte[512 * 1024]));
//    COMMON_ZERO_HASHES.put(1024 * 1024, calculateSha1Hash(new byte[1024 * 1024]));
    // precalculated values instead
    COMMON_ZERO_HASHES.put(256 * 1024, new byte[]{46, 0, 15, -89, -24, 87, 89, -57, -12, -62, 84, -44, -39, -61, 62, -12, -127, -28, 89, -89});
    COMMON_ZERO_HASHES.put(512 * 1024, new byte[]{106, 82, 30, 29, 42, 99, 44, 38, -27, 59, -125, -46, -52, 75, 14, -34, -49, -63, -26, -116});
    COMMON_ZERO_HASHES.put(1024 * 1024, new byte[]{59, 113, -12, 63, -13, 15, 75, 21, -75, -51, -123, -35, -98, -107, -21, -57, -24, 78, -75, -93});
  }

  /**
   * @param data for hashing
   * @return sha 1 hash of specified data
   */
  public static byte[] calculateSha1Hash(byte[] data) {
    return DigestUtils.sha1(data);
  }

  /**
   * Convert a byte string to a string containing an hexadecimal
   * representation of the original data.
   *
   * @param bytes The byte array to convert.
   */
  public static String byteArrayToHexString(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_SYMBOLS[v >>> 4];
      hexChars[j * 2 + 1] = HEX_SYMBOLS[v & 0x0F];
    }
    return new String(hexChars);
  }

  public static boolean isTrackerLessInfo(AnnounceableInformation information) {
    return information.getAnnounce() == null && information.getAnnounceList() == null;
  }

  public static List<String> getTorrentFileNames(TorrentMetadata metadata) {
    List<String> result = new ArrayList<String>();

    for (TorrentFile torrentFile : metadata.getFiles()) {
      result.add(torrentFile.getRelativePathAsString());
    }

    return result;
  }

  public static byte[] calculateZeroSha1Hash(int len) {
    byte[] commonValue = COMMON_ZERO_HASHES.get(len);
    if (commonValue != null) {
      return commonValue;
    } else {
      return DigestUtils.sha1(new byte[len]);
    }
  }
}
