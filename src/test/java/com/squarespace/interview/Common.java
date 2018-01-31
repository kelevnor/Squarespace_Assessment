package com.squarespace.interview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Common {

  public static InputStream loadFromClasspath(String path) throws IOException {
    InputStream stream;
    URL url = Common.class.getClassLoader().getResource(path);
    if (null == url) {
      throw new FileNotFoundException("unable to locate path on resource: " + path);
    }
    try {
      stream = Files.newInputStream(Paths.get(url.toURI()));
    } catch (URISyntaxException e) {
      throw new FileNotFoundException("unable to adapt path name to URI: " + e.getMessage());
    }
    if (stream == null) {
      throw new FileNotFoundException("unable to locate file on classpath at: " + path);
    }
    return stream;
  }
}
