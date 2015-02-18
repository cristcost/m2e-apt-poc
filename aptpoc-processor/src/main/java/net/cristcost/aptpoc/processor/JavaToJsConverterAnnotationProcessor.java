package net.cristcost.aptpoc.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

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

  private List<ExecutableElement> getStaticPublicMethodsOfTypeElement(
      TypeElement processedTypeElement) {

    final List<ExecutableElement> ret = new ArrayList<ExecutableElement>();
    List<ExecutableElement> methods =
        ElementFilter.methodsIn(processedTypeElement.getEnclosedElements());

    for (ExecutableElement method : methods) {
      if (method.getModifiers().contains(Modifier.STATIC)) {
        if (method.getModifiers().contains(Modifier.PUBLIC)) {
          ret.add(method);
        } else {
          processingEnv.getMessager().printMessage(
              Kind.MANDATORY_WARNING,
              "The processed class includes a method that is not public and will be ignored: "
                  + method.getSimpleName(), method);
        }
      } else {
        processingEnv.getMessager().printMessage(Kind.ERROR,
            "The processed class includes a method that is not static: " + method.getSimpleName(),
            method);
      }
    }
    return ret;
  }

  private void processConvertToJavascriptElement(TypeElement processedTypeElement) {
    final List<ExecutableElement> methods =
        getStaticPublicMethodsOfTypeElement(processedTypeElement);

    try {

      String javascriptFileName = processedTypeElement.getSimpleName().toString() + ".js";
      FileObject javascrptFile =
          processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "",
              javascriptFileName);

      Writer writer = javascrptFile.openWriter();

      for (ExecutableElement executableElement : methods) {
        String functionName = executableElement.getSimpleName().toString();
        String functionSignature = "/* todo extract the params*/";

        writer.append("var " + functionName + " = function(" + functionSignature + ") {"
            + System.lineSeparator());
        writer.append("   // TODO: process and convert to Javascript this function"
            + System.lineSeparator());
        writer.append("}" + System.lineSeparator());
        writer.append(System.lineSeparator());

      }
      writer.flush();
      writer.close();
    } catch (IOException e) {
      processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
    }
  }

}
