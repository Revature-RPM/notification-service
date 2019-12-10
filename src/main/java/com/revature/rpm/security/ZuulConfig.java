package com.revature.rpm.security;

import org.springframework.beans.factory.annotation.Value;

/** Provides configuration which will be used during Zuul Header Authentication. */
public class ZuulConfig {

  /**
   * Indicates the name of the HTTP header that will contain the prefix and Zuul header as a value
   */
  @Value("${security.zsign.header:RPM_ZUUL_ACCESS_HEADER}")
  private String header;

  /**
   * Indicates the salt value that will be combined with the secret to form the hash that will be
   * transferred with the request
   */
  @Value("${security.zsign.salt}")
  private String salt;
  /** Used as a key with the encryption algorithm SHA-512 to generate hash */
  @Value("${security.zsign.secret}")
  private String secret;

  public ZuulConfig() {}

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
}
