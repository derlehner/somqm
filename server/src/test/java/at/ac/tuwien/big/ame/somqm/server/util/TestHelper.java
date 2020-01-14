package at.ac.tuwien.big.ame.somqm.server.util;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public final class TestHelper {

  private TestHelper() {
  }

  public static byte[] getValidTxtContent() {
    return loadTestDataContent("test.txt");
  }

  public static byte[] getValidXmlContent() {
    return loadTestDataContent("note.xml");
  }

  public static byte[] getInvalidXmiContent() {
    return loadTestDataContent("invalid_xmi.xmi");
  }

  public static byte[] getXmiContentWithoutUmlModelElement() {
    return loadTestDataContent("contains_no_umlmodel_element.xmi");
  }

  public static byte[] getValidClassModelContent() {
    return loadTestDataContent("genmymodel/test_class_diagram_genmymodel.xmi");
  }

  public static byte[] getValidClassModelWithNestedPackagesContent() {
    return loadTestDataContent("genmymodel/test_class_diagram_nested_packages_genmymodel.xmi");
  }

  public static byte[] getValidActivityModelContent() {
    return loadTestDataContent("genmymodel/test_activity_diagram_genmymodel.xmi");
  }

  public static byte[] getValidStateMachineModelContent() {
    return loadTestDataContent("genmymodel/test_statemachine_diagram_genmymodel.xmi");
  }

  private static byte[] loadTestDataContent(String fileName) {
    String resourceLocation = "test-data/" + fileName;
    Resource resource = new ClassPathResource(resourceLocation);
    byte[] content;
    try {
      content = FileCopyUtils.copyToByteArray(resource.getInputStream());
    } catch (IOException e) {
      throw new RuntimeException(
          "Failed to load resource '" + resourceLocation + "' as byte-array: " + e.getMessage(), e);
    }
    return content;
  }

}
