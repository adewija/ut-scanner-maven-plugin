package com.gdn.maven.plugin.service_impl;

import com.gdn.maven.plugin.model.ClassScanResult;
import com.gdn.maven.plugin.service_api.WriterService;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriterServiceImpl implements WriterService {
  private static final Logger LOG = LoggerFactory.getLogger(WriterServiceImpl.class);

  @Override
  public void writeListToJSON(List<ClassScanResult> classScanResultList) {
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String stringOfJson = gson.toJson(classScanResultList);
    Path resultPath = Paths.get("").toAbsolutePath().resolve("ut-scanner-result.json");
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(resultPath.toString()));
      writer.write(stringOfJson);
      writer.close();
    } catch (IOException e) {
      LOG.error("Failed writing to JSON file at {} ", resultPath.toAbsolutePath(), e);
    }
  }
}
