package net.cristcost.aptpoc.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

@SupportedAnnotationTypes({
  "net.cristcost.aptpoc.processor.ConvertToJavascript"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class JavaToJsConverterAnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(final Set<? extends TypeElement> annotations,
      final RoundEnvironment roundEnv) {

    if (!roundEnv.processingOver()) {
      final Set<? extends Element> elementsAnnotatedConvertToJavascript =
          roundEnv.getElementsAnnotatedWith(ConvertToJavascript.class);

      for (final TypeElement convertToJavascriptElement : ElementFilter.typesIn(elementsAnnotatedConvertToJavascript)) {
        processConvertToJavascriptElement(convertToJavascriptElement);
      }
    }
    return true;
  }

  private void processConvertToJavascriptElement(TypeElement convertToJavascriptElement) {
    // TODO: process the element
  }
}
